import React from 'react';
import { Animated, Image, Text, View } from 'react-native';
import { AnimatedTBar, GemCell, TBar } from './BattleScreen.components';
import {
  BATTLE_ASSETS,
  BOARD_LEFT,
  BOARD_TOP,
  ENEMY_HUD_LAYOUT,
  EXTRA_TURNS_SHIFT_X,
  EXTRA_TURNS_SHIFT_Y,
  HUD_BASE_Y,
  PLAYER_HUD_LAYOUT,
  TURN_TIMER_SHIFT_X,
  TURN_TIMER_TOP,
  type BattleCell,
  type BattleTurn,
  type Board,
  type GemType,
  type MatchFXItem,
  BG_H,
  BG_W,
  BOARD_COLS,
  BOARD_ROWS,
  BOARD_SCALE,
  GEM_SIZE,
  s,
} from '../core';

interface BattlePanelProps {
  panelLeft: number;
  panelTop: number;
  board: Board;
  cursorCell: BattleCell | null;
  selected: BattleCell | null;
  hintCell: BattleCell | null;
  explodeFrames: Record<string, number>;
  fireSwordMarkBaseGems: Record<string, GemType>;
  fireSwordMarkTriggers: Record<string, number>;
  turn: BattleTurn;
  matchFX: MatchFXItem[];
  showExtraTurnsBadge: boolean;
  extraTurnsBadgeValue: number;
  showComboBadge: boolean;
  comboMultiplier: number;
  turnTimeLeft: number;
  mana: number;
  power: number;
  enemyMana: number;
  enemyPower: number;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  enemyMaxMP: number;
  enemyMaxPow: number;
  offsets: Animated.Value[][];
  swapOffsetsX: Animated.Value[][];
  swapOffsetsY: Animated.Value[][];
  extraTurnsBadgeAnim: Animated.Value;
  comboBadgeAnim: Animated.Value;
  playerHPBarAnim: Animated.Value;
  enemyHPBarAnim: Animated.Value;
  powerBlinkAnim: Animated.Value;
  enemyPowerBlinkAnim: Animated.Value;
  playerRageReady: boolean;
  enemyRageReady: boolean;
  onGemPress: (row: number, col: number) => void;
}

export const BattlePanel: React.FC<BattlePanelProps> = ({
  panelLeft,
  panelTop,
  board,
  cursorCell,
  selected,
  hintCell,
  explodeFrames,
  fireSwordMarkBaseGems,
  fireSwordMarkTriggers,
  turn,
  matchFX,
  showExtraTurnsBadge,
  extraTurnsBadgeValue,
  showComboBadge,
  comboMultiplier,
  turnTimeLeft,
  mana,
  power,
  enemyMana,
  enemyPower,
  maxHP,
  maxEHP,
  maxMP,
  maxPow,
  enemyMaxMP,
  enemyMaxPow,
  offsets,
  swapOffsetsX,
  swapOffsetsY,
  extraTurnsBadgeAnim,
  comboBadgeAnim,
  playerHPBarAnim,
  enemyHPBarAnim,
  powerBlinkAnim,
  enemyPowerBlinkAnim,
  playerRageReady,
  enemyRageReady,
  onGemPress,
}) => {
  const playerHud = PLAYER_HUD_LAYOUT;
  const enemyHud = ENEMY_HUD_LAYOUT;
  const hasBoardFocus = selected !== null || hintCell !== null;

  return (
    <View
      style={{
        position: 'absolute',
        top: panelTop,
        left: panelLeft,
        width: BG_W,
        height: BG_H,
      }}
    >
      <Image
        source={BATTLE_ASSETS.boardFrame}
        style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
        resizeMode="stretch"
      />

      <View
        style={{
          position: 'absolute',
          top: HUD_BASE_Y + playerHud.shiftY,
          left: playerHud.offsetX,
          width: playerHud.boxW,
          height: playerHud.boxH,
          zIndex: 20,
        }}
      >
        <View style={{ position: 'absolute', top: playerHud.hp.y, left: playerHud.hp.x }}>
          <AnimatedTBar
            asset={BATTLE_ASSETS.hpBar}
            fillAnim={playerHPBarAnim}
            max={maxHP}
            w={playerHud.hp.w}
            h={playerHud.hp.h}
            direction="ltr"
          />
        </View>
        <View style={{ position: 'absolute', top: playerHud.mp.y, left: playerHud.mp.x }}>
          <TBar
            asset={BATTLE_ASSETS.manaBar}
            fill={mana / maxMP}
            w={playerHud.mp.w}
            h={playerHud.mp.h}
            direction="ltr"
          />
        </View>
        <Animated.View
          style={{
            position: 'absolute',
            top: playerHud.power.y,
            left: playerHud.power.x,
            opacity: playerRageReady ? powerBlinkAnim : 1,
          }}
        >
          <TBar
            asset={BATTLE_ASSETS.powerBar}
            fill={power / maxPow}
            w={playerHud.power.w}
            h={playerHud.power.h}
            direction="ltr"
          />
        </Animated.View>
        {playerRageReady && (
          <Animated.View
            style={{
              position: 'absolute',
              top: playerHud.power.y - 9,
              left: playerHud.power.x + playerHud.power.w - 22,
              minWidth: 22,
              height: 10,
              paddingHorizontal: 4,
              borderRadius: 5,
              borderWidth: 1,
              borderColor: '#ffe39a',
              backgroundColor: 'rgba(126, 33, 6, 0.95)',
              alignItems: 'center',
              justifyContent: 'center',
              opacity: powerBlinkAnim,
              transform: [{
                scale: powerBlinkAnim.interpolate({
                  inputRange: [0.15, 1],
                  outputRange: [0.92, 1.06],
                }),
              }],
            }}
          >
            <Text style={{ color: '#fff1c8', fontSize: 7, fontWeight: '900' }}>x2</Text>
          </Animated.View>
        )}
      </View>

      <View
        style={{
          position: 'absolute',
          top: HUD_BASE_Y + enemyHud.shiftY,
          right: enemyHud.offsetX,
          width: enemyHud.boxW,
          height: enemyHud.boxH,
          zIndex: 20,
        }}
      >
        <View style={{ position: 'absolute', top: enemyHud.hp.y, right: enemyHud.hp.x }}>
          <AnimatedTBar
            asset={BATTLE_ASSETS.hpBar}
            fillAnim={enemyHPBarAnim}
            max={maxEHP}
            w={enemyHud.hp.w}
            h={enemyHud.hp.h}
            direction="rtl"
          />
        </View>
        <View style={{ position: 'absolute', top: enemyHud.mp.y, right: enemyHud.mp.x }}>
          <TBar
            asset={BATTLE_ASSETS.manaBar}
            fill={enemyMaxMP <= 0 ? 0 : enemyMana / enemyMaxMP}
            w={enemyHud.mp.w}
            h={enemyHud.mp.h}
            direction="rtl"
          />
        </View>
        <Animated.View
          style={{
            position: 'absolute',
            top: enemyHud.power.y,
            right: enemyHud.power.x,
            opacity: enemyRageReady ? enemyPowerBlinkAnim : 1,
          }}
        >
          <TBar
            asset={BATTLE_ASSETS.powerBar}
            fill={enemyMaxPow <= 0 ? 0 : enemyPower / enemyMaxPow}
            w={enemyHud.power.w}
            h={enemyHud.power.h}
            direction="rtl"
          />
        </Animated.View>
        {enemyRageReady && (
          <Animated.View
            style={{
              position: 'absolute',
              top: enemyHud.power.y - 9,
              right: enemyHud.power.x + enemyHud.power.w - 22,
              minWidth: 22,
              height: 10,
              paddingHorizontal: 4,
              borderRadius: 5,
              borderWidth: 1,
              borderColor: '#9de8ff',
              backgroundColor: 'rgba(10, 56, 118, 0.95)',
              alignItems: 'center',
              justifyContent: 'center',
              opacity: enemyPowerBlinkAnim,
              transform: [{
                scale: enemyPowerBlinkAnim.interpolate({
                  inputRange: [0.15, 1],
                  outputRange: [0.92, 1.06],
                }),
              }],
            }}
          >
            <Text style={{ color: '#e9faff', fontSize: 7, fontWeight: '900' }}>x2</Text>
          </Animated.View>
        )}
      </View>

      <Animated.View
        style={[
          s.gemBoard,
          {
            top: BOARD_TOP,
            left: BOARD_LEFT,
            width: GEM_SIZE * BOARD_COLS,
            height: GEM_SIZE * BOARD_ROWS,
            zIndex: hasBoardFocus ? 30 : 10,
          },
        ]}
      >
        {board.map((row, r) =>
          row.map((gemType, c) =>
            gemType !== null ? (
              <Animated.View
                key={`${r}-${c}`}
                style={{
                  position: 'absolute',
                  left: c * GEM_SIZE,
                  top: r * GEM_SIZE,
                  width: GEM_SIZE,
                  height: GEM_SIZE,
                  zIndex:
                    (selected?.[0] === r && selected?.[1] === c) ||
                    (hintCell?.[0] === r && hintCell?.[1] === c)
                      ? 120
                      : 1,
                  transform: [
                    { translateX: swapOffsetsX[r][c] },
                    { translateY: swapOffsetsY[r][c] },
                    { translateY: offsets[r][c] },
                  ],
                }}
              >
                  <GemCell
                    gemType={gemType}
                    frameIndex={explodeFrames[`${r},${c}`] ?? 0}
                    fireSwordBaseGemType={fireSwordMarkBaseGems[`${r},${c}`]}
                    size={GEM_SIZE}
                    fireSwordMarkTrigger={fireSwordMarkTriggers[`${r},${c}`]}
                    selected={
                    (selected?.[0] === r && selected?.[1] === c) ||
                    (hintCell?.[0] === r && hintCell?.[1] === c)
                  }
                  focusVariant={turn === 'monster' ? 'enemy' : 'player'}
                  onPress={() => onGemPress(r, c)}
                />
              </Animated.View>
            ) : null,
          ),
        )}

        {matchFX.map(fx => {
          const opacity = fx.anim.interpolate({
            inputRange: [0, 0.12, 0.75, 1],
            outputRange: [0, 1, 0.9, 0],
          });
          const scale = fx.anim.interpolate({
            inputRange: [0, 0.2, 0.55, 1],
            outputRange: [0.15, 0.95, fx.kind === 'sword' ? 1.42 : 1.18, 0.52],
          });
          const translateY = fx.anim.interpolate({
            inputRange: [0, 1],
            outputRange: [0, fx.driftY],
          });
          const translateX = fx.anim.interpolate({
            inputRange: [0, 1],
            outputRange: [0, fx.driftX],
          });
          const cx = fx.c * GEM_SIZE + GEM_SIZE / 2 - fx.size / 2 + fx.startOffsetX;
          const cy = fx.r * GEM_SIZE + GEM_SIZE / 2 - fx.size / 2 + fx.startOffsetY;

          return (
            <Animated.Image
              key={fx.key}
              source={fx.source}
              resizeMode="contain"
              style={{
                position: 'absolute',
                left: cx,
                top: cy,
                width: fx.size,
                height: fx.size,
                opacity,
                transform: [{ translateX }, { translateY }, { scale }, { rotate: fx.rotate }],
              }}
            />
          );
        })}
      </Animated.View>

      {extraTurnsBadgeValue > 0 && showExtraTurnsBadge && (
        <Animated.View
          style={{
            position: 'absolute',
            top: BOARD_TOP + GEM_SIZE * BOARD_ROWS / 2 - 14 + EXTRA_TURNS_SHIFT_Y * BOARD_SCALE,
            left: BOARD_LEFT + GEM_SIZE * BOARD_COLS / 2 - 55 + EXTRA_TURNS_SHIFT_X * BOARD_SCALE,
            width: 110,
            height: 20,
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 50,
            opacity: extraTurnsBadgeAnim,
          }}
        >
          <Text style={{ color: extraTurnsBadgeValue === 1 ? '#fff' : '#111', fontSize: 12, fontWeight: 'bold' }}>
            Còn {extraTurnsBadgeValue} lượt
          </Text>
        </Animated.View>
      )}

      {showComboBadge && comboMultiplier >= 2 && (
        <Animated.View
          style={{
            position: 'absolute',
            top: BOARD_TOP + GEM_SIZE * 5.9,
            left: BOARD_LEFT + GEM_SIZE * BOARD_COLS / 2 - 26,
            width: 52,
            height: 52,
            borderRadius: 26,
            borderWidth: 3,
            borderColor: '#6fdcff',
            backgroundColor: 'rgba(23, 45, 89, 0.78)',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 55,
            opacity: comboBadgeAnim,
            transform: [{
              scale: comboBadgeAnim.interpolate({
                inputRange: [0, 0.2, 1],
                outputRange: [0.55, 1.15, 1],
              }),
            }],
          }}
        >
          <Text style={s.comboTxt}>{`x${comboMultiplier}`}</Text>
        </Animated.View>
      )}

      <View
        style={{
          position: 'absolute',
          top: TURN_TIMER_TOP,
          left: 0,
          right: 0,
          zIndex: 20,
          alignItems: 'center',
          transform: [{ translateX: TURN_TIMER_SHIFT_X * BOARD_SCALE }],
        }}
      >
        <Text
          style={{
            color: '#dff6ff',
            fontSize: 10 * BOARD_SCALE,
            fontWeight: 'bold',
            textShadowColor: '#000',
            textShadowOffset: { width: 1, height: 1 },
            textShadowRadius: 2,
          }}
        >
          {turnTimeLeft}s
        </Text>
      </View>
    </View>
  );
};
