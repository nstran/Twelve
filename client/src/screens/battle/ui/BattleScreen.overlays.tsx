import React from 'react';
import { Animated, Image, Text, TouchableOpacity, View } from 'react-native';
import { MonsterSprite, monsterDisplaySize, type MonsterType } from '../../../engine/MonsterSprite';
import type { CharacterAction } from '../../../engine/character';
import { CharacterRenderer } from '../../character';
import type { CharacterAppearance } from '../../character/shared';
import {
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
  monsterType: MonsterType;
  monFrame: number;
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
}

export const BattleActorsRow: React.FC<BattleActorsRowProps> = ({
  panelLeft,
  charsTop,
  charsHeight,
  appearance,
  monsterType,
  monFrame,
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
}) => {
  const { groundOffset } = monsterDisplaySize(monsterType);
  const { stageWidth, playerBaseLeft, monsterBaseLeft, playerSize } =
    React.useMemo(() => getBattleActorLayout(monsterType, appearance), [monsterType, appearance]);
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
          bottom: -groundOffset,
          width: monsterWidth,
          height: monsterHeight,
          transform: [
            { translateX: Animated.add(enemyAttackTranslateX, enemyHitTranslateX) },
            { translateX: ENEMY_SPRITE_SHIFT_X * BOARD_SCALE },
            { translateY: ENEMY_SPRITE_SHIFT_Y * BOARD_SCALE },
          ],
        }}
      >
        <MonsterSprite type={monsterType} frameIndex={monFrame} facingRight={false} />
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
  onVictory,
  onDefeat,
}) => {
  if (result === null || resultMeta === null) return null;

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
        onPress={result === 'victory' ? onVictory : onDefeat}
      >
        <View
          style={{
            width: resultMeta.frameWidth * BOARD_SCALE,
            height: resultMeta.frameHeight * BOARD_SCALE,
            overflow: 'hidden',
          }}
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
      </TouchableOpacity>
    </View>
  );
};
