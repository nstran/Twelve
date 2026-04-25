import React from 'react';
import { Animated, Image, Text, TouchableOpacity, View } from 'react-native';
import type { BattleMonsterPoseKey } from '../../../engine/BattleMonsterAssetManifest';
import { type MonsterType } from '../../../engine/MonsterSprite';
import { BattleMonsterSprite } from '../../../engine/BattleMonsterSprite';
import type { CharacterAction } from '../../../engine/character';
import { CharacterRenderer } from '../../character';
import type { CharacterAppearance } from '../../character/shared';
import {
  AURA1_IMG,
  AURA2_IMG,
  BATTLE_PLAYER_SCALE,
  RESULT_AUTO_CLOSE_DELAY_MS,
  RESULT_ART_INDEX,
  RESULT_REWARD_ANIMATION_MS,
  BOARD_TOP,
  CHARS_ROW_SHIFT_X,
  ENEMY_HUD_LAYOUT,
  ENEMY_SPRITE_SHIFT_X,
  ENEMY_SPRITE_SHIFT_Y,
  getBattleActorLayout,
  HUD_BASE_Y,
  PLAYER_HUD_LAYOUT,
  PLAYER_SPRITE_SHIFT_X,
  PLAYER_SPRITE_SHIFT_Y,
  type BattleResult,
  type BattleResultRewardResponse,
  type CollectFXItem,
  type DamagePopupItem,
  type GainPopupItem,
  ResultArtMeta,
  BG_W,
  BOARD_SCALE,
  GEM_SIZE,
  s,
} from '../core';
import { resultStyles } from './BattleResultOverlay.styles';

const RESULT_POPUP_ART_SCALE = 0.72;
const RESULT_REWARD_ZERO_EPSILON = 0.002;
const RESULT_GEM_ICON_SIZE = 16;
const RESULT_GEM_FRAME_COUNT = 7;
const RESULT_HP_ICON = require('../../../../assets/battle/02_chess_pieces/chess1.png');
const RESULT_EXP_ICON = require('../../../../assets/battle/02_chess_pieces/chess5.png');
const RESULT_GOLD_ICON = require('../../../../assets/battle/02_chess_pieces/chess6.png');

function animateDeltaTowardsZero(delta: number, progress: number): number {
  const remaining = delta * (1 - progress);
  if (Math.abs(remaining) <= RESULT_REWARD_ZERO_EPSILON) {
    return 0;
  }

  return remaining > 0 ? Math.ceil(remaining) : Math.floor(remaining);
}

function formatSignedDelta(delta: number): string {
  if (delta > 0) {
    return `+${delta}`;
  }

  return `${delta}`;
}

interface BattleActorsRowProps {
  panelLeft: number;
  charsTop: number;
  charsHeight: number;
  appearance: CharacterAppearance;
  enemyAppearance?: CharacterAppearance | null;
  monsterAssetCatalogId?: string | null;
  monsterType: MonsterType;
  monsterDefeatOpacity: Animated.Value;
  monsterDefeatScale: Animated.Value;
  monsterDefeatTranslateY: Animated.Value;
  monsterPoseKey: BattleMonsterPoseKey;
  playerAction: CharacterAction;
  playerActionFrameIndex: number | null;
  playerReactionPose: boolean;
  playerDefeatPose: boolean;
  playerRetreatPose: boolean;
  monsterWidth: number;
  monsterHeight: number;
  playerAttackTranslateX: Animated.Value;
  playerHitTranslateX: Animated.Value;
  enemyAttackTranslateX: Animated.Value;
  enemyHitTranslateX: Animated.Value;
  playerRageReady: boolean;
  enemyRageReady: boolean;
  rageAuraPulseAnim: Animated.Value;
}

export const BattleActorsRow: React.FC<BattleActorsRowProps> = ({
  panelLeft,
  charsTop,
  charsHeight,
  appearance,
  enemyAppearance,
  monsterAssetCatalogId,
  monsterType,
  monsterDefeatOpacity,
  monsterDefeatScale,
  monsterDefeatTranslateY,
  monsterPoseKey,
  playerAction,
  playerActionFrameIndex,
  playerReactionPose,
  playerDefeatPose,
  playerRetreatPose,
  monsterWidth,
  monsterHeight,
  playerAttackTranslateX,
  playerHitTranslateX,
  enemyAttackTranslateX,
  enemyHitTranslateX,
  playerRageReady,
  enemyRageReady,
  rageAuraPulseAnim,
}) => {
  const { stageWidth, playerBaseLeft, monsterBaseLeft, monsterGroundOffset, playerSize } =
    React.useMemo(
      () => getBattleActorLayout(monsterType, appearance, monsterAssetCatalogId),
      [appearance, monsterAssetCatalogId, monsterType],
    );
  const playerPoseFamilySlotOverride =
    playerDefeatPose ? 8 : playerReactionPose ? 7 : playerRetreatPose ? 9 : undefined;
  const playerPoseFrameIndexOverride = playerPoseFamilySlotOverride !== undefined ? 0 : undefined;

  return (
    <View
      style={[
        s.charsRow,
        {
          top: charsTop,
          left: panelLeft + CHARS_ROW_SHIFT_X * BOARD_SCALE,
          width: stageWidth,
          height: charsHeight,
        },
      ]}
    >
      <Animated.View
        style={{
          position: 'absolute',
          left: playerBaseLeft,
          bottom: -(playerSize.groundOffset ?? 0),
          width: playerSize.w,
          height: playerSize.h,
          transform: [
            { translateX: Animated.add(playerAttackTranslateX, playerHitTranslateX) },
          ],
        }}
      >
        {playerRageReady && (
          <Animated.Image
            source={AURA1_IMG}
            resizeMode="contain"
            style={{
              position: 'absolute',
              left: -18,
              top: -20,
              width: playerSize.w + 36,
              height: playerSize.h + 24,
              opacity: rageAuraPulseAnim.interpolate({
                inputRange: [0.72, 1.08],
                outputRange: [0.32, 0.8],
              }),
              transform: [{ scale: rageAuraPulseAnim }],
            }}
          />
        )}
        <View
          style={{
            transform: [
              { translateX: PLAYER_SPRITE_SHIFT_X * BOARD_SCALE },
              { translateY: PLAYER_SPRITE_SHIFT_Y * BOARD_SCALE },
            ],
          }}
        >
          <CharacterRenderer
            appearance={appearance}
            scale={BATTLE_PLAYER_SCALE}
            anchorToBody
            action={playerAction}
            actionFrameIndex={playerActionFrameIndex ?? undefined}
            facing="right"
            poseFamilySlotOverride={playerPoseFamilySlotOverride}
            poseFrameIndexOverride={playerPoseFrameIndexOverride}
          />
        </View>
      </Animated.View>
      <Animated.View
        style={{
          position: 'absolute',
          left: monsterBaseLeft,
          bottom: -monsterGroundOffset,
          width: monsterWidth,
          height: monsterHeight,
          opacity: monsterDefeatOpacity,
          transform: [
            { translateX: Animated.add(enemyAttackTranslateX, enemyHitTranslateX) },
            { translateY: monsterDefeatTranslateY },
            { scale: monsterDefeatScale },
            { translateX: ENEMY_SPRITE_SHIFT_X * BOARD_SCALE },
            { translateY: ENEMY_SPRITE_SHIFT_Y * BOARD_SCALE },
          ],
        }}
      >
        {enemyRageReady && (
          <Animated.Image
            source={AURA2_IMG}
            resizeMode="contain"
            style={{
              position: 'absolute',
              left: -20,
              top: -24,
              width: monsterWidth + 40,
              height: monsterHeight + 30,
              opacity: rageAuraPulseAnim.interpolate({
                inputRange: [0.72, 1.08],
                outputRange: [0.3, 0.76],
              }),
              transform: [{ scale: rageAuraPulseAnim }],
            }}
          />
        )}
        {enemyAppearance ? (
          <View
            style={{
              position: 'absolute',
              left: Math.max(0, (monsterWidth - playerSize.w) / 2),
              bottom: Math.max(0, monsterGroundOffset - playerSize.groundOffset),
              width: playerSize.w,
              height: playerSize.h,
            }}
          >
            <CharacterRenderer
              appearance={enemyAppearance}
              scale={BATTLE_PLAYER_SCALE}
              anchorToBody
              facing="left"
            />
          </View>
        ) : (
          <BattleMonsterSprite
            assetCatalogId={monsterAssetCatalogId}
            fallbackType={monsterType}
            frameIndex={0}
            poseKey={monsterPoseKey}
            facingRight={false}
          />
        )}
      </Animated.View>
    </View>
  );
};

interface BattleEffectsProps {
  panelLeft: number;
  panelTop: number;
  charsTop: number;
  damagePopupTop: number;
  playerDamageLeft: number;
  enemyDamageLeft: number;
  damagePopups: DamagePopupItem[];
  gainPopups: GainPopupItem[];
  collectFX: CollectFXItem[];
}

export const BattleEffects: React.FC<BattleEffectsProps> = ({
  panelLeft,
  panelTop,
  charsTop,
  damagePopupTop,
  playerDamageLeft,
  enemyDamageLeft,
  damagePopups,
  gainPopups,
  collectFX,
}) => {
  const playerHud = PLAYER_HUD_LAYOUT;
  const enemyHud = ENEMY_HUD_LAYOUT;

  return (
    <>
      {damagePopups.map(item => {
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.12, 0.85, 1],
          outputRange: [0, 1, 1, 0],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 1],
          outputRange: [10, -18],
        });
        const scale = item.anim.interpolate({
          inputRange: [0, 0.2, 1],
          outputRange: [0.8, 1.05, 1],
        });

        return (
          <Animated.View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              top: damagePopupTop,
              left: item.side === 'player' ? playerDamageLeft : enemyDamageLeft,
              width: 80,
              alignItems: 'center',
              opacity,
              transform: [{ translateY }, { scale }],
              zIndex: 40,
            }}
          >
            <Text
              style={{
                color: '#ff4db8',
                fontSize: 14,
                textShadowColor: '#580026',
                textShadowOffset: { width: 1, height: 1 },
                textShadowRadius: 2,
              }}
            >
              -{item.amount}
            </Text>
          </Animated.View>
        );
      })}

      {gainPopups.map(item => {
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.12, 0.85, 1],
          outputRange: [0, 1, 1, 0],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 1],
          outputRange: [10, -14],
        });

        return (
          <Animated.View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              top: panelTop + HUD_BASE_Y - 18 * BOARD_SCALE,
              left: item.side === 'player'
                ? panelLeft + playerHud.offsetX + playerHud.hp.x + 8
                : panelLeft + BG_W - enemyHud.offsetX - enemyHud.hp.x - enemyHud.hp.w + 8,
              opacity,
              transform: [{ translateY }],
              zIndex: 42,
            }}
          >
            <Text
              style={{
                color: '#ffeef7',
                fontSize: 12,
                textShadowColor: '#8a295e',
                textShadowOffset: { width: 1, height: 1 },
                textShadowRadius: 2,
              }}
            >
              {item.text}
            </Text>
          </Animated.View>
        );
      })}

      {collectFX.map(item => {
        const translateX = item.anim.interpolate({
          inputRange: [0, 0.24, 0.68, 1],
          outputRange: [0, item.curve1X - item.startX, item.curve2X - item.startX, item.endX - item.startX],
        });
        const translateY = item.anim.interpolate({
          inputRange: [0, 0.24, 0.68, 1],
          outputRange: [0, item.curve1Y - item.startY, item.curve2Y - item.startY, item.endY - item.startY],
        });
        const scale = item.anim.interpolate({
          inputRange: [0, 0.1, 0.34, 0.76, 1],
          outputRange: [0.18, 1.18, 1.02, 0.92, item.endScale],
        });
        const opacity = item.anim.interpolate({
          inputRange: [0, 0.06, 0.32, item.fadeOutAt, 1],
          outputRange: [0, 1, 1, 0.96, 0],
        });
        const glowOpacity = item.anim.interpolate({
          inputRange: [0, 0.05, 0.28, item.fadeOutAt, 1],
          outputRange: [0, item.glowOpacity, item.glowOpacity, item.glowOpacity * 0.92, 0],
        });
        const cropScale = item.renderW / item.cropWidth;
        const spriteW = 45 * cropScale;
        const spriteH = 15 * cropScale;
        const spriteOffsetX = -item.cropLeft * cropScale;

        return (
          <View
            key={item.key}
            pointerEvents="none"
            style={{
              position: 'absolute',
              left: item.startX,
              top: item.startY,
              width: item.renderW,
              height: item.renderH,
              overflow: 'visible',
              zIndex: 41,
            }}
          >
            {item.isCrystal ? (
              <>
                <Animated.View
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.renderW,
                    height: item.renderH,
                    overflow: 'hidden',
                    opacity: glowOpacity,
                    transform: [{ translateX }, { translateY }, { scale: Animated.multiply(scale, item.glowScale) }],
                  }}
                >
                  <Image
                    source={item.source}
                    resizeMode="stretch"
                    style={{
                      position: 'absolute',
                      left: spriteOffsetX,
                      top: 0,
                      width: spriteW,
                      height: spriteH,
                    }}
                  />
                </Animated.View>
                <Animated.View
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.renderW,
                    height: item.renderH,
                    overflow: 'hidden',
                    opacity,
                    transform: [{ translateX }, { translateY }, { scale }],
                  }}
                >
                  <Image
                    source={item.source}
                    resizeMode="stretch"
                    style={{
                      position: 'absolute',
                      left: spriteOffsetX,
                      top: 0,
                      width: spriteW,
                      height: spriteH,
                    }}
                  />
                </Animated.View>
              </>
            ) : (
              <>
                <Animated.Image
                  source={item.source}
                  resizeMode="contain"
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.size,
                    height: item.size,
                    opacity: glowOpacity,
                    transform: [{ translateX }, { translateY }, { scale: Animated.multiply(scale, item.glowScale) }],
                  }}
                />
                <Animated.Image
                  source={item.source}
                  resizeMode="contain"
                  style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    width: item.size,
                    height: item.size,
                    opacity,
                    transform: [{ translateX }, { translateY }, { scale }],
                  }}
                />
              </>
            )}
          </View>
        );
      })}
    </>
  );
};

interface BattleResultOverlayProps {
  result: BattleResult | null;
  resultMeta: ResultArtMeta | null;
  visible: boolean;
  panelTop: number;
  reward: BattleResultRewardResponse | null;
  onVictory: () => void;
  onDefeat: () => void;
}

interface BattleResultSplashProps {
  result: BattleResult | null;
  resultMeta: ResultArtMeta | null;
  visible: boolean;
  panelTop: number;
  resultArtAnim: Animated.Value;
  resultArtLift: Animated.AnimatedInterpolation<number>;
  resultArtScale: Animated.AnimatedInterpolation<number>;
  resultArtTilt: Animated.AnimatedInterpolation<string>;
}

export const BattleResultSplash: React.FC<BattleResultSplashProps> = ({
  result,
  resultMeta,
  visible,
  panelTop,
  resultArtAnim,
  resultArtLift,
  resultArtScale,
  resultArtTilt,
}) => {
  if (!visible || result === null || resultMeta === null) return null;

  return (
    <View pointerEvents="none" style={s.resultBannerLayer}>
      <Animated.View
        style={[
          s.resultBannerStage,
          {
            top: panelTop + BOARD_TOP + GEM_SIZE * 4 - 34 * BOARD_SCALE,
            opacity: resultArtAnim,
            transform: [
              { translateY: resultArtLift },
              { scale: resultArtScale },
              { rotate: resultArtTilt },
            ],
          },
        ]}
      >
        <View
          style={[
            resultStyles.splashClip,
            {
              width: resultMeta.frameWidth * BOARD_SCALE,
              height: resultMeta.frameHeight * BOARD_SCALE,
            },
          ]}
        >
          <Image
            source={resultMeta.asset}
            resizeMode="stretch"
            style={{
              width: resultMeta.sheetWidth * BOARD_SCALE,
              height: resultMeta.sheetHeight * BOARD_SCALE,
              transform: [{ translateX: -RESULT_ART_INDEX * resultMeta.frameWidth * BOARD_SCALE }],
            }}
          />
        </View>
      </Animated.View>
    </View>
  );
};

export const BattleResultOverlay: React.FC<BattleResultOverlayProps> = ({
  result,
  resultMeta,
  visible,
  panelTop,
  reward,
  onVictory,
  onDefeat,
}) => {
  const rewardProgress = React.useRef(new Animated.Value(0)).current;
  const [animatedRewardProgress, setAnimatedRewardProgress] = React.useState(0);
  const closeTimerRef = React.useRef<ReturnType<typeof setTimeout> | null>(null);
  const didAutoCloseRef = React.useRef(false);

  React.useEffect(() => {
    const id = rewardProgress.addListener(({ value }) => {
      setAnimatedRewardProgress(value);
    });

    return () => {
      rewardProgress.removeListener(id);
    };
  }, [rewardProgress]);

  React.useEffect(() => {
    if (closeTimerRef.current) {
      clearTimeout(closeTimerRef.current);
      closeTimerRef.current = null;
    }
    didAutoCloseRef.current = false;
    rewardProgress.stopAnimation();
    rewardProgress.setValue(0);

    if (!visible || result === null || reward === null) {
      return;
    }

    Animated.timing(rewardProgress, {
      toValue: 1,
      duration: RESULT_REWARD_ANIMATION_MS,
      useNativeDriver: false,
    }).start(({ finished }) => {
      if (!finished || didAutoCloseRef.current) {
        return;
      }

      closeTimerRef.current = setTimeout(() => {
        didAutoCloseRef.current = true;
        closeTimerRef.current = null;
        if (result === 'victory') {
          onVictory();
        } else {
          onDefeat();
        }
      }, RESULT_AUTO_CLOSE_DELAY_MS);
    });

    return () => {
      if (closeTimerRef.current) {
        clearTimeout(closeTimerRef.current);
        closeTimerRef.current = null;
      }
      rewardProgress.stopAnimation();
    };
  }, [onDefeat, onVictory, result, reward, rewardProgress, visible]);

  if (!visible || result === null || resultMeta === null) return null;

  const currentHp = reward?.currentHp ?? 0;
  const maxHp = Math.max(1, reward?.maxHp ?? 1);
  const expFloor = reward?.expFloor ?? 0;
  const expCeiling = Math.max(expFloor + 1, reward?.expCeiling ?? 100);
  const expBefore = reward?.expBefore ?? expFloor;
  const expAfter = reward?.expAfter ?? expFloor;
  const expNow = reward
    ? expBefore + ((expAfter - expBefore) * animatedRewardProgress)
    : expFloor;
  const goldBefore = reward?.goldBefore ?? reward?.quanBefore ?? 0;
  const goldAfter = reward?.goldAfter ?? reward?.quanAfter ?? 0;
  const goldNow = reward
    ? goldBefore + ((goldAfter - goldBefore) * animatedRewardProgress)
    : goldBefore;
  const expRewardRemaining = animateDeltaTowardsZero(reward?.expGained ?? 0, animatedRewardProgress);
  const goldRewardRemaining = animateDeltaTowardsZero(reward?.goldGained ?? reward?.quanGained ?? 0, animatedRewardProgress);
  const hpPercent = Math.max(0, Math.min(100, (currentHp * 100) / maxHp));
  const expPercent = Math.max(0, Math.min(100, ((expNow - expFloor) * 100) / (expCeiling - expFloor)));
  const goldProgress = Math.max(0, Math.min(100, (goldNow % 10000) / 100));
  const displayGold = Math.max(0, Math.floor(goldNow));
  const canClose = reward !== null && animatedRewardProgress >= 1 - RESULT_REWARD_ZERO_EPSILON;
  const close = result === 'victory' ? onVictory : onDefeat;
  const lootLines = [
    ...(reward?.itemRewards ?? []).map((item) => `${item.displayName} x${item.quantity}`),
    ...(reward?.equipmentRewards ?? []).map((equipment) => equipment.displayName),
  ].slice(0, 3);

  return (
    <View pointerEvents="box-none" style={s.resultBannerLayer}>
      <TouchableOpacity
        activeOpacity={1}
        style={[
          s.resultBannerStage,
          {
            top: panelTop + BOARD_TOP + GEM_SIZE * 4 - 34 * BOARD_SCALE,
          },
        ]}
        onPress={canClose ? close : undefined}
      >
        <View style={resultStyles.resultCard}>
          <Text style={resultStyles.levelText}>
            Cấp: {reward?.levelAfter ?? '-'}
          </Text>
          <ResultBar
            color="#D23A32"
            value={hpPercent}
            label={`${currentHp}/${maxHp}`}
            iconSource={RESULT_HP_ICON}
          />
          <ResultBar
            color="#3CBD38"
            value={expPercent}
            label={`${Math.floor(expPercent * 10) / 10}%`}
            iconSource={RESULT_EXP_ICON}
          />
          <ResultBar
            color="#C99A2E"
            value={goldProgress}
            label={`${displayGold}/10000`}
            iconSource={RESULT_GOLD_ICON}
          />

          <Text style={[resultStyles.sectionTitle, resultStyles.collectionTitle]}>
            Điểm Thu Thập
          </Text>
          <ResultRewardLine iconSource={RESULT_GOLD_ICON} label={`${goldRewardRemaining}`} />
          <ResultRewardLine iconSource={RESULT_EXP_ICON} label={`${expRewardRemaining}`} />

          <Text style={[resultStyles.sectionTitle, resultStyles.rewardTitle]}>
            Thưởng
          </Text>
          <ResultRewardLine iconSource={RESULT_GOLD_ICON} label={formatSignedDelta(goldRewardRemaining)} />
          <ResultRewardLine iconSource={RESULT_EXP_ICON} label={formatSignedDelta(expRewardRemaining)} />
          {lootLines.length > 0 ? (
            <>
              <Text style={[resultStyles.sectionTitle, resultStyles.lootTitle]}>
                Vật phẩm
              </Text>
              {lootLines.map((line) => (
                <Text key={line} style={resultStyles.lootText}>
                  • {line}
                </Text>
              ))}
            </>
          ) : null}
          {reward && reward.levelUps > 0 ? (
            <Text style={resultStyles.levelUpText}>
              Lên cấp +{reward.levelUps}
            </Text>
          ) : null}

          <View
            style={[
              resultStyles.resultArt,
              {
                width: resultMeta.frameWidth * RESULT_POPUP_ART_SCALE,
                height: resultMeta.frameHeight * RESULT_POPUP_ART_SCALE,
              },
            ]}
          >
            <Image
              source={resultMeta.asset}
              resizeMode="stretch"
              style={{
                width: resultMeta.sheetWidth * RESULT_POPUP_ART_SCALE,
                height: resultMeta.sheetHeight * RESULT_POPUP_ART_SCALE,
                transform: [{ translateX: -RESULT_ART_INDEX * resultMeta.frameWidth * RESULT_POPUP_ART_SCALE }],
              }}
            />
          </View>
          <Text style={resultStyles.closeText}>
            {canClose ? 'Đóng' : 'Đang nhận...'}
          </Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const ResultGemIcon: React.FC<{ source: any; size?: number }> = ({ source, size = RESULT_GEM_ICON_SIZE }) => (
  <View style={[resultStyles.barIconFrame, { width: size, height: size }]}>
    <Image
      source={source}
      resizeMode="stretch"
      style={{
        width: size * RESULT_GEM_FRAME_COUNT,
        height: size,
      }}
    />
  </View>
);

const ResultBar: React.FC<{ color: string; value: number; label: string; iconSource: any }> = ({
  color,
  value,
  label,
  iconSource,
}) => (
  <View style={resultStyles.barRow}>
    <ResultGemIcon source={iconSource} />
    <View style={[resultStyles.bar, resultStyles.barTrack]}>
      <View
        style={[
          resultStyles.barFill,
          {
            width: `${Math.max(0, Math.min(100, value))}%`,
            backgroundColor: color,
          },
        ]}
      />
      <Text style={resultStyles.barLabel}>
        {label}
      </Text>
    </View>
  </View>
);

const ResultRewardLine: React.FC<{ iconSource: any; label: string }> = ({ iconSource, label }) => (
  <View style={resultStyles.rewardLine}>
    <View style={resultStyles.rewardIconWrap}>
      <ResultGemIcon source={iconSource} size={14} />
    </View>
    <Text style={resultStyles.rewardLabel}>{label}</Text>
  </View>
);
