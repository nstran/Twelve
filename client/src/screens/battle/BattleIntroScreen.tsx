import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import {
  Animated,
  Dimensions,
  Easing,
  Image,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import type { MonsterType } from '../../engine/MonsterSprite';
import { MonsterSprite, monsterDisplaySize } from '../../engine/MonsterSprite';
import { CharacterSprite, characterDisplaySize } from '../../engine/character';
import { loadSession } from '../../storage/SessionStorage';

const AUTO_ADVANCE_MS = 5000;
const SHOW_CARDS_AFTER_MS = 1500;
const ATTACK_PREP_MS = 80;
const SLASH_HOLD_MS = 110;
const ATTACK_LOOP_GAP_MS = 140;
const ASSET_HIDDEN_DRAGON = require('../../../assets/hiddendragon.png');

interface BattleIntroScreenProps {
  monsterType: MonsterType;
  playerLeft: number;
  monsterLeft: number;
  groundY: number;
  playerScale?: number;
  onConfirm: () => void;
}

interface EncounterInfo {
  name: string;
  level: number;
  note: string;
  badge: string;
}

const MONSTER_INFO: Record<MonsterType, EncounterInfo> = {
  fire: {
    name: 'Heo Mọi',
    level: 7,
    note: 'IQ: Siêu gà',
    badge: '⚡',
  },
  ice: {
    name: 'Băng Linh',
    level: 8,
    note: 'IQ: Tỉnh ngủ',
    badge: '❄',
  },
  zap: {
    name: 'Lôi Thú',
    level: 9,
    note: 'IQ: Lém lỉnh',
    badge: '⚡',
  },
};

const PLAYER_INFO = {
  level: 10,
  note: 'Thường dân',
  badge: '💧',
};

interface BattleInfoCardProps {
  align: 'left' | 'right';
  name: string;
  level: number;
  note: string;
  badge: string;
  children: React.ReactNode;
}

const BattleInfoCard: React.FC<BattleInfoCardProps> = ({
  align,
  name,
  level,
  note,
  badge,
  children,
}) => {
  const isLeft = align === 'left';

  return (
    <View style={[styles.cardOuter, isLeft ? styles.cardTop : styles.cardBottom]}>
      <View style={styles.cardInner}>
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
  playerLeft,
  monsterLeft,
  groundY,
  playerScale = 0.7,
  onConfirm,
}) => {
  const [username, setUsername] = useState('Lữ khách');
  const [previewFrameIndex, setPreviewFrameIndex] = useState(0);
  const [showCards, setShowCards] = useState(false);
  const handledRef = useRef(false);
  const playerLungeAnim = useRef(new Animated.Value(0)).current;
  const monsterShakeAnim = useRef(new Animated.Value(0)).current;
  const hitFlashAnim = useRef(new Animated.Value(0)).current;
  const cardsAnim = useRef(new Animated.Value(0)).current;

  const monsterInfo = useMemo(() => MONSTER_INFO[monsterType], [monsterType]);
  const playerSize = useMemo(() => characterDisplaySize(playerScale), [playerScale]);
  const monsterSize = useMemo(() => monsterDisplaySize(monsterType), [monsterType]);

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

    const timer = setTimeout(() => {
      finish(onConfirm);
    }, AUTO_ADVANCE_MS);

    return () => {
      mounted = false;
      if (cardsTimer) clearTimeout(cardsTimer);
      clearTimeout(timer);
    };
  }, [finish, onConfirm]);

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

      setPreviewFrameIndex(0);
      prepTimer = setTimeout(() => {
        if (!active) return;
        setPreviewFrameIndex(2);

        slashTimer = setTimeout(() => {
          if (!active) return;
          setPreviewFrameIndex(3);
          runImpactAnim();

          resetTimer = setTimeout(() => {
            if (!active) return;
            setPreviewFrameIndex(0);

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
        <Animated.View
          style={[
            styles.cardsLayer,
            {
              opacity: cardsAnim,
              transform: [
                {
                  translateY: cardsAnim.interpolate({
                    inputRange: [0, 1],
                    outputRange: [-18, 0],
                  }),
                },
              ],
            },
          ]}
        >
          <BattleInfoCard
            align="left"
            name={username}
            level={PLAYER_INFO.level}
            note={PLAYER_INFO.note}
            badge={PLAYER_INFO.badge}
          >
            <View style={styles.playerSpriteWrap}>
              <CharacterSprite frameIndex={0} facing="right" scale={0.42} />
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
          >
            <View style={styles.monsterSpriteWrap}>
              <MonsterSprite type={monsterType} frameIndex={0} facingRight={false} />
            </View>
          </BattleInfoCard>
        </Animated.View>

        <Animated.View
          style={[
            styles.previewPlayer,
            {
              left: playerLeft,
              top: groundY - playerSize.h,
              transform: [{ translateX: playerLungeAnim }],
            },
          ]}
        >
          <CharacterSprite frameIndex={previewFrameIndex} facing="right" scale={playerScale} />
        </Animated.View>

        <Animated.View
          style={[
            styles.previewMonster,
            {
              left: monsterLeft,
              top: groundY - monsterSize.h + monsterSize.groundOffset,
              transform: [{ translateX: monsterShakeAnim }],
            },
          ]}
        >
          <MonsterSprite type={monsterType} frameIndex={0} facingRight={false} />
          <Animated.View style={[styles.hitFlash, { opacity: hitFlashAnim }]} pointerEvents="none" />
        </Animated.View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  root: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  },
  stage: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 10,
    paddingTop: 46,
    paddingBottom: 44,
  },
  cardsLayer: {
    zIndex: 4,
  },
  cardOuter: {
    borderWidth: 2,
    borderColor: '#5aa8ff',
    backgroundColor: '#edf7ff',
    borderRadius: 6,
    padding: 2,
    shadowColor: '#4d88d9',
    shadowOpacity: 0.35,
    shadowRadius: 4,
  },
  cardTop: {
    marginBottom: 12,
  },
  cardBottom: {
    marginTop: 12,
  },
  cardInner: {
    minHeight: 118,
    borderWidth: 1,
    borderColor: '#c9e3ff',
    backgroundColor: 'rgba(247, 252, 255, 0.96)',
    borderRadius: 4,
    overflow: 'hidden',
    justifyContent: 'center',
  },
  hiddenDragon: {
    position: 'absolute',
    width: 107,
    height: 78,
    opacity: 0.22,
    top: 24,
  },
  hiddenDragonRight: {
    right: 16,
  },
  hiddenDragonLeft: {
    left: 16,
  },
  cardContent: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 10,
    paddingVertical: 8,
  },
  cardContentLeft: {
    justifyContent: 'flex-start',
  },
  cardContentRight: {
    flexDirection: 'row-reverse',
    justifyContent: 'space-between',
  },
  actorSlot: {
    width: 96,
    alignItems: 'center',
    justifyContent: 'center',
  },
  textSlot: {
    flex: 1,
    marginLeft: 4,
  },
  textSlotRight: {
    marginLeft: 4,
    marginRight: 0,
    alignItems: 'flex-end',
  },
  nameRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6,
  },
  nameRowRight: {
    justifyContent: 'flex-end',
  },
  badgeText: {
    fontSize: 18,
    marginRight: 6,
  },
  nameText: {
    flexShrink: 1,
    color: '#111',
    fontSize: 20,
    fontWeight: '800',
  },
  levelText: {
    color: '#1f1f1f',
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 4,
  },
  levelTextRight: {
    textAlign: 'right',
  },
  noteText: {
    color: '#242424',
    fontSize: 16,
    fontWeight: '500',
  },
  noteTextRight: {
    textAlign: 'right',
  },
  versusRow: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  versusIcon: {
    color: '#f1c54d',
    fontSize: 36,
    textShadowColor: 'rgba(120, 60, 0, 0.35)',
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 3,
  },
  playerSpriteWrap: {
    width: 82,
    height: 74,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  monsterSpriteWrap: {
    width: 82,
    height: 74,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  previewPlayer: {
    position: 'absolute',
    zIndex: 1,
  },
  previewMonster: {
    position: 'absolute',
    zIndex: 0,
  },
  hitFlash: {
    position: 'absolute',
    left: -8,
    top: -8,
    right: -8,
    bottom: -8,
    backgroundColor: 'rgba(255,255,255,0.92)',
    borderRadius: 999,
  },
});
