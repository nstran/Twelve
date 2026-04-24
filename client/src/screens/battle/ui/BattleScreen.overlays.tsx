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
  RESULT_ART_INDEX,
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

interface BattleActorsRowProps {
  panelLeft: number;
  charsTop: number;
  charsHeight: number;
  appearance: CharacterAppearance;
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
        <BattleMonsterSprite
          assetCatalogId={monsterAssetCatalogId}
          fallbackType={monsterType}
          frameIndex={0}
          poseKey={monsterPoseKey}
          facingRight={false}
        />
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
  panelTop: number;
  resultArtAnim: Animated.Value;
  resultArtLift: Animated.AnimatedInterpolation<number>;
  resultArtScale: Animated.AnimatedInterpolation<number>;
  resultArtTilt: Animated.AnimatedInterpolation<string>;
  reward: BattleResultRewardResponse | null;
  onVictory: () => void;
  onDefeat: () => void;
}

export const BattleResultOverlay: React.FC<BattleResultOverlayProps> = ({
  result,
  resultMeta,
  panelTop,
  resultArtAnim,
  resultArtLift,
  resultArtScale,
  resultArtTilt,
  reward,
  onVictory,
  onDefeat,
}) => {
  if (result === null || resultMeta === null) return null;

  const currentHp = reward?.currentHp ?? 0;
  const maxHp = Math.max(1, reward?.maxHp ?? 1);
  const expFloor = reward?.expFloor ?? 0;
  const expCeiling = Math.max(expFloor + 1, reward?.expCeiling ?? 100);
  const expAfter = reward?.expAfter ?? expFloor;
  const quanAfter = reward?.quanAfter ?? 0;
  const hpPercent = Math.max(0, Math.min(100, (currentHp * 100) / maxHp));
  const expPercent = Math.max(0, Math.min(100, ((expAfter - expFloor) * 100) / (expCeiling - expFloor)));
  const close = result === 'victory' ? onVictory : onDefeat;

  return (
    <View pointerEvents="box-none" style={s.resultBannerLayer}>
      <TouchableOpacity
        activeOpacity={1}
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
        onPress={reward ? close : undefined}
      >
        <View style={{
          width: 184,
          minHeight: 198,
          backgroundColor: '#E7F4FF',
          borderWidth: 2,
          borderColor: '#4895FF',
          padding: 6,
        }}>
          <Text style={{ fontSize: 12, fontWeight: '700', color: '#202020', marginBottom: 5 }}>
            Cấp: {reward?.levelAfter ?? '-'}
          </Text>
          <ResultBar color="#D23A32" value={hpPercent} label={`${currentHp}/${maxHp}`} />
          <ResultBar color="#3CBD38" value={expPercent} label={`${Math.floor(expPercent * 10) / 10}%`} />
          <ResultBar color="#C99A2E" value={Math.min(100, (quanAfter % 10000) / 100)} label={`${quanAfter}/10000`} />

          <Text style={{ fontSize: 12, fontWeight: '700', color: '#333', marginTop: 10, marginBottom: 4 }}>
            Điểm Thu Thập
          </Text>
          <ResultRewardLine color="#C99A2E" label={`${reward?.quanGained ?? 0}`} />
          <ResultRewardLine color="#3CBD38" label={`${reward?.expGained ?? 0}`} />

          <Text style={{ fontSize: 12, fontWeight: '700', color: '#333', marginTop: 8 }}>
            Thưởng
          </Text>
          <ResultRewardLine color="#3CBD38" label={`+${reward?.expGained ?? 0}`} />
          {reward && reward.levelUps > 0 ? (
            <Text style={{ fontSize: 11, color: '#D05500', fontWeight: '700', marginTop: 2 }}>
              Lên cấp +{reward.levelUps}
            </Text>
          ) : null}

          <View style={{
            position: 'absolute',
            right: 6,
            bottom: 18,
            width: resultMeta.frameWidth * BOARD_SCALE,
            height: resultMeta.frameHeight * BOARD_SCALE,
            overflow: 'hidden',
          }}>
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
          <Text
            style={{
              position: 'absolute',
              bottom: 2,
              left: 0,
              right: 0,
              textAlign: 'center',
              color: '#516070',
              fontSize: 11,
            }}
          >
            {reward ? 'Đóng' : 'Đang nhận...'}
          </Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const ResultBar: React.FC<{ color: string; value: number; label: string }> = ({ color, value, label }) => (
  <View style={{
    height: 16,
    marginBottom: 4,
    borderWidth: 1,
    borderColor: '#94A8B8',
    backgroundColor: '#FFF8E8',
    overflow: 'hidden',
  }}>
    <View style={{ width: `${Math.max(0, Math.min(100, value))}%`, height: '100%', backgroundColor: color }} />
    <Text style={{
      position: 'absolute',
      left: 0,
      right: 0,
      top: 1,
      textAlign: 'center',
      fontSize: 10,
      fontWeight: '700',
      color: '#6E1818',
    }}>
      {label}
    </Text>
  </View>
);

const ResultRewardLine: React.FC<{ color: string; label: string }> = ({ color, label }) => (
  <View style={{ flexDirection: 'row', alignItems: 'center', height: 16 }}>
    <View style={{ width: 10, height: 10, borderRadius: 5, backgroundColor: color, marginRight: 8 }} />
    <Text style={{ fontSize: 12, color: '#222', fontWeight: '600' }}>{label}</Text>
  </View>
);
