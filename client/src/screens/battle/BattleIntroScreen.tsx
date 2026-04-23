import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import {
  Animated,
  Easing,
  Image,
  Text,
  View,
} from 'react-native';
import { MonsterSprite, monsterDisplaySize, monsterPlacementMetrics, type MonsterType } from '../../engine/MonsterSprite';
import { CharacterRenderer, measureCharacterRenderer } from '../character';
import type { CharacterAppearance } from '../character/shared';
import { loadSession } from '../../storage/SessionStorage';
import type { MonsterBattleBootstrapResponse, MonsterSharedSheetFamily } from './core';
import { resolveMonsterBadgeFromVisuals } from './core';
import {
  getBattleIntroCardsLayerStyle,
  getBattleIntroCardInnerStyle,
  getBattleIntroCardOuterStyle,
  getBattleIntroHitFlashStyle,
  getBattleIntroPreviewMonsterStyle,
  getBattleIntroPreviewPlayerStyle,
  styles,
} from './BattleIntroScreen.styles';

const AUTO_ADVANCE_MS = 5000;
const SHOW_CARDS_AFTER_MS = 1500;
const ATTACK_PREP_MS = 80;
const SLASH_HOLD_MS = 110;
const ATTACK_LOOP_GAP_MS = 140;
const ASSET_HIDDEN_DRAGON = require('../../../assets/battle/09_hidden_pieces/hiddendragon.png');

interface BattleIntroScreenProps {
  monsterType: MonsterType;
  monsterBootstrap: MonsterBattleBootstrapResponse | null;
  bootstrapStatus: 'loading' | 'ready' | 'error';
  encounterDisplayName?: string;
  encounterDisplayLevel?: number;
  encounterIqValue?: number;
  encounterVisualTypeByte?: number;
  encounterSharedSheetFamily?: MonsterSharedSheetFamily;
  encounterNameColorMode?: number;
  monsterPreviewFrameIndex?: number;
  monsterPreviewFacingRight?: boolean;
  monsterPreviewWorldState?: 'patrol' | 'alert' | 'engaging';
  playerLeft: number;
  monsterLeft: number;
  groundY: number;
  playerScale?: number;
  appearance: CharacterAppearance;
  onConfirm: () => void;
}

interface EncounterInfo {
  name: string;
  level: number | string;
  note: string;
  badge: string;
}

interface BattleInfoCardProps {
  align: 'left' | 'right';
  name: string;
  level: number | string;
  note: string;
  badge: string;
  accentColor?: string;
  children: React.ReactNode;
}

function resolveEncounterIqLabel(iqValue: number): string {
  if (iqValue < 3) {
    return 'Siêu gà';
  }

  if (iqValue < 7) {
    return 'Bờm';
  }

  if (iqValue < 10) {
    return 'Ma lanh';
  }

  if (iqValue === 11) {
    return 'Tốc chiến';
  }

  return 'Tuyệt đỉnh';
}

function resolveEncounterAccentColor(
  displayLevel: number,
  nameColorMode: number | undefined,
  playerLevel: number,
): string {
  if (nameColorMode === 1) {
    return '#d94141';
  }

  if (nameColorMode === 2) {
    return '#b38a1a';
  }

  const levelDelta = displayLevel - playerLevel;
  if (levelDelta >= 5) {
    return '#1673FF';
  }

  if (levelDelta < -9) {
    return '#818181';
  }

  return '#5aa8ff';
}

const BattleInfoCard: React.FC<BattleInfoCardProps> = ({
  align,
  name,
  level,
  note,
  badge,
  accentColor = '#5aa8ff',
  children,
}) => {
  const isLeft = align === 'left';

  return (
    <View
      style={getBattleIntroCardOuterStyle(isLeft, accentColor)}
    >
      <View style={getBattleIntroCardInnerStyle(accentColor)}>
        <Image
          source={ASSET_HIDDEN_DRAGON}
          style={[
            styles.hiddenDragon,
            isLeft ? styles.hiddenDragonRight : styles.hiddenDragonLeft,
          ]}
          resizeMode="stretch"
        />

        <View style={[styles.cardContent, isLeft ? styles.cardContentLeft : styles.cardContentRight]}>
          <View style={styles.actorSlot}>
            {children}
          </View>

          <View style={[styles.textSlot, !isLeft && styles.textSlotRight]}>
            <View style={[styles.nameRow, !isLeft && styles.nameRowRight]}>
              <Text style={styles.badgeText}>{badge}</Text>
              <Text style={styles.nameText} numberOfLines={1}>{name}</Text>
            </View>
            <Text style={[styles.levelText, !isLeft && styles.levelTextRight]}>Cấp: {level}</Text>
            <Text style={[styles.noteText, !isLeft && styles.noteTextRight]}>{note}</Text>
          </View>
        </View>
      </View>
    </View>
  );
};

export const BattleIntroScreen: React.FC<BattleIntroScreenProps> = ({
  monsterType,
  monsterBootstrap,
  bootstrapStatus,
  encounterDisplayName,
  encounterDisplayLevel,
  encounterIqValue,
  encounterVisualTypeByte,
  encounterSharedSheetFamily,
  encounterNameColorMode,
  monsterPreviewFrameIndex = 0,
  monsterPreviewFacingRight = false,
  monsterPreviewWorldState = 'patrol',
  playerLeft,
  monsterLeft,
  groundY,
  playerScale = 1,
  appearance,
  onConfirm,
}) => {
  const [username, setUsername] = useState('Lữ khách');
  const [attackFrameIndex, setAttackFrameIndex] = useState(0);
  const [isAttacking, setIsAttacking] = useState(false);
  const [showCards, setShowCards] = useState(false);
  const handledRef = useRef(false);
  const playerLungeAnim = useRef(new Animated.Value(0)).current;
  const monsterShakeAnim = useRef(new Animated.Value(0)).current;
  const hitFlashAnim = useRef(new Animated.Value(0)).current;
  const cardsAnim = useRef(new Animated.Value(0)).current;

  const canConfirm = monsterBootstrap !== null && bootstrapStatus === 'ready';
  const playerInfo = useMemo(
    () => ({
      level: appearance.level ?? 1,
      note: appearance.quanHam?.trim() || 'Luong khach',
    }),
    [appearance.level, appearance.quanHam],
  );
  const playerBadge = useMemo(() => {
    if (appearance.elementIndex === 1) return '⚡';
    if (appearance.elementIndex === 2) return '💧';
    return '🔥';
  }, [appearance.elementIndex]);
  const encounterLevel = encounterDisplayLevel ?? monsterBootstrap?.displayLevel ?? 1;
  const encounterIq = encounterIqValue ?? monsterBootstrap?.iqValue ?? 0;
  const encounterBadge = resolveMonsterBadgeFromVisuals(
    encounterVisualTypeByte ?? monsterBootstrap?.visualTypeByte ?? 0,
    encounterSharedSheetFamily ?? monsterBootstrap?.sharedSheetFamily ?? 'Monster',
  );
  const monsterAccentColor = useMemo(
    () => resolveEncounterAccentColor(
      encounterLevel,
      encounterNameColorMode,
      appearance.level ?? 1,
    ),
    [appearance.level, encounterLevel, encounterNameColorMode],
  );
  const monsterInfo = useMemo<EncounterInfo>(() => {
    if (encounterDisplayName) {
      return {
        name: encounterDisplayName,
        level: encounterLevel,
        note: `IQ: ${resolveEncounterIqLabel(encounterIq)}`,
        badge: encounterBadge,
      };
    }

    if (monsterBootstrap) {
      return {
        name: monsterBootstrap.enemy.displayName,
        level: encounterLevel,
        note: `IQ: ${resolveEncounterIqLabel(encounterIq)}`,
        badge: encounterBadge,
      };
    }

    if (bootstrapStatus === 'error') {
      return {
        name: 'Không tải được quái',
        level: '--',
        note: 'Thiếu bootstrap từ server',
        badge: '!',
      };
    }

    return {
      name: 'Đang dò quái...',
      level: '--',
      note: 'Đang tải dữ liệu battle',
      badge: '…',
    };
  }, [
    bootstrapStatus,
    encounterBadge,
    encounterDisplayName,
    encounterIq,
    encounterLevel,
    monsterBootstrap,
  ]);
  // Cùng thuật toán với HoaLuMapScreen: anchorToBody=true + SPRITE_FOOT_SINK.
  // Đảm bảo preview trong encounter không bị "nhảy" vị trí so với map screen.
  const playerSize = useMemo(() => {
    const measured = measureCharacterRenderer(appearance, playerScale, true);
    const footSink = Math.round(5 * playerScale / 2.2); // transparent below feet, tại playerScale
    return { ...measured, groundOffset: measured.groundOffset + footSink };
  }, [appearance, playerScale]);
  const monsterSize = useMemo(
    () => monsterDisplaySize(monsterType),
    [monsterType],
  );
  const monsterPlacement = useMemo(
    () => monsterPlacementMetrics(monsterType),
    [monsterType],
  );
  const previewMonsterScale = monsterPreviewWorldState === 'engaging'
    ? 1.06
    : monsterPreviewWorldState === 'alert'
      ? 1.03
      : 1;

  const finish = useCallback((next: () => void) => {
    if (handledRef.current) return;
    handledRef.current = true;
    next();
  }, []);

  useEffect(() => {
    let mounted = true;
    let cardsTimer: ReturnType<typeof setTimeout> | null = null;

    loadSession()
      .then((session) => {
        if (!mounted || !session?.username) return;
        setUsername(session.username);
      })
      .catch(() => {});

    cardsTimer = setTimeout(() => {
      if (!mounted) return;
      setShowCards(true);
    }, SHOW_CARDS_AFTER_MS);

    const timer = canConfirm
      ? setTimeout(() => {
        finish(onConfirm);
      }, AUTO_ADVANCE_MS)
      : null;

    return () => {
      mounted = false;
      if (cardsTimer) clearTimeout(cardsTimer);
      if (timer) clearTimeout(timer);
    };
  }, [canConfirm, finish, onConfirm]);

  useEffect(() => {
    Animated.timing(cardsAnim, {
      toValue: showCards ? 1 : 0,
      duration: showCards ? 260 : 0,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start();
  }, [cardsAnim, showCards]);

  useEffect(() => {
    let active = true;
    let prepTimer: ReturnType<typeof setTimeout> | null = null;
    let slashTimer: ReturnType<typeof setTimeout> | null = null;
    let resetTimer: ReturnType<typeof setTimeout> | null = null;
    let loopTimer: ReturnType<typeof setTimeout> | null = null;

    const runImpactAnim = () => {
      playerLungeAnim.stopAnimation();
      monsterShakeAnim.stopAnimation();
      hitFlashAnim.stopAnimation();

      playerLungeAnim.setValue(0);
      monsterShakeAnim.setValue(0);
      hitFlashAnim.setValue(0);

      Animated.sequence([
        Animated.timing(playerLungeAnim, {
          toValue: 8,
          duration: 90,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(playerLungeAnim, {
          toValue: 0,
          duration: 130,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();

      Animated.sequence([
        Animated.timing(monsterShakeAnim, {
          toValue: 7,
          duration: 50,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(monsterShakeAnim, {
          toValue: -5,
          duration: 70,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(monsterShakeAnim, {
          toValue: 0,
          duration: 90,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();

      Animated.sequence([
        Animated.timing(hitFlashAnim, {
          toValue: 0.55,
          duration: 70,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(hitFlashAnim, {
          toValue: 0,
          duration: 160,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();
    };

    const runLoop = () => {
      if (!active) return;

      setIsAttacking(false);
      setAttackFrameIndex(0);
      prepTimer = setTimeout(() => {
        if (!active) return;
        setIsAttacking(true);
        setAttackFrameIndex(0);

        slashTimer = setTimeout(() => {
          if (!active) return;
          setAttackFrameIndex(1);
          runImpactAnim();

          resetTimer = setTimeout(() => {
            if (!active) return;
            setIsAttacking(false);
            setAttackFrameIndex(0);

            loopTimer = setTimeout(runLoop, ATTACK_LOOP_GAP_MS);
          }, SLASH_HOLD_MS);
        }, ATTACK_PREP_MS);
      }, 240);
    };

    runLoop();

    return () => {
      active = false;
      if (prepTimer) clearTimeout(prepTimer);
      if (slashTimer) clearTimeout(slashTimer);
      if (resetTimer) clearTimeout(resetTimer);
      if (loopTimer) clearTimeout(loopTimer);
      playerLungeAnim.stopAnimation();
      monsterShakeAnim.stopAnimation();
      hitFlashAnim.stopAnimation();
    };
  }, [hitFlashAnim, monsterShakeAnim, playerLungeAnim]);

  return (
    <View style={styles.root} pointerEvents="none">
      <View style={styles.stage}>
        <Animated.View style={getBattleIntroCardsLayerStyle(cardsAnim)}>
          <BattleInfoCard
            align="left"
            name={username}
            level={playerInfo.level}
            note={playerInfo.note}
            badge={playerBadge}
          >
            <View style={styles.playerSpriteWrap}>
              <CharacterRenderer appearance={appearance} scale={0.42} action="idle" facing="right" />
            </View>
          </BattleInfoCard>

          <View style={styles.versusRow}>
            <Text style={styles.versusIcon}>⚔</Text>
          </View>

          <BattleInfoCard
            align="right"
            name={monsterInfo.name}
            level={monsterInfo.level}
            note={monsterInfo.note}
            badge={monsterInfo.badge}
            accentColor={monsterAccentColor}
          >
            <View style={styles.monsterSpriteWrap}>
              <MonsterSprite
                type={monsterType}
                frameIndex={monsterPreviewFrameIndex}
                facingRight={monsterPreviewFacingRight}
              />
            </View>
          </BattleInfoCard>
        </Animated.View>

        <Animated.View style={getBattleIntroPreviewPlayerStyle(
          playerLeft,
          groundY,
          playerSize,
          playerLungeAnim,
        )}
        >
          <CharacterRenderer
            appearance={appearance}
            scale={playerScale}
            anchorToBody
            action={isAttacking ? 'attack' : 'idle'}
            actionFrameIndex={attackFrameIndex}
            facing="right"
          />
        </Animated.View>

        <Animated.View style={getBattleIntroPreviewMonsterStyle(
          monsterLeft,
          groundY,
          monsterSize,
          monsterPlacement,
          monsterShakeAnim,
          previewMonsterScale,
        )}
        >
          <MonsterSprite
            type={monsterType}
            frameIndex={monsterPreviewFrameIndex}
            facingRight={monsterPreviewFacingRight}
          />
          <Animated.View style={getBattleIntroHitFlashStyle(hitFlashAnim)} pointerEvents="none" />
        </Animated.View>
      </View>
    </View>
  );
};
