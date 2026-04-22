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
  turn: BattleTurn;
  matchFX: MatchFXItem[];
  extraTurns: number;
  showExtraTurnsBadge: boolean;
  turnTimeLeft: number;
  mana: number;
  power: number;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  offsets: Animated.Value[][];
  swapOffsetsX: Animated.Value[][];
  swapOffsetsY: Animated.Value[][];
  extraTurnsBadgeAnim: Animated.Value;
  playerHPBarAnim: Animated.Value;
  enemyHPBarAnim: Animated.Value;
  powerBlinkAnim: Animated.Value;
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
  turn,
  matchFX,
  extraTurns,
  showExtraTurnsBadge,
  turnTimeLeft,
  mana,
  power,
  maxHP,
  maxEHP,
  maxMP,
  maxPow,
  offsets,
  swapOffsetsX,
  swapOffsetsY,
  extraTurnsBadgeAnim,
  playerHPBarAnim,
  enemyHPBarAnim,
  powerBlinkAnim,
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
            opacity: powerBlinkAnim,
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
            fill={0}
            w={enemyHud.mp.w}
            h={enemyHud.mp.h}
            direction="rtl"
          />
        </View>
        <View style={{ position: 'absolute', top: enemyHud.power.y, right: enemyHud.power.x }}>
          <TBar
            asset={BATTLE_ASSETS.powerBar}
            fill={0}
            w={enemyHud.power.w}
            h={enemyHud.power.h}
            direction="rtl"
          />
        </View>
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
                  size={GEM_SIZE}
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

      {extraTurns > 0 && showExtraTurnsBadge && (
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
          <Text style={{ color: extraTurns === 1 ? '#fff' : '#111', fontSize: 12, fontWeight: 'bold' }}>
            Còn {extraTurns} lượt
          </Text>
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
