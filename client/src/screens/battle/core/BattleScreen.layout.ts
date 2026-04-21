import { monsterDisplaySize, monsterPlacementMetrics, type MonsterType } from '../../../engine/MonsterSprite';
import { measureCharacterRenderer } from '../../character';
import type { CharacterAppearance } from '../../character/shared';
import {
  BG_H,
  BG_W,
  BOARD_LEFT_OFFSET,
  BOARD_SCALE,
  BOARD_TOP_OFFSET,
  SCREEN_H,
  SCREEN_W,
} from './BattleScreen.styles';

type RawBarLayout = { x: number; y: number; w: number; h: number };
type RawHudLayout = {
  offsetX: number;
  shiftY: number;
  bars: {
    hp: RawBarLayout;
    mp: RawBarLayout;
    power: RawBarLayout;
  };
};

const scaleRect = (rect: RawBarLayout) => ({
  x: rect.x * BOARD_SCALE,
  y: rect.y * BOARD_SCALE,
  w: rect.w * BOARD_SCALE,
  h: rect.h * BOARD_SCALE,
});

const buildHudLayout = (layout: RawHudLayout) => {
  const hp = scaleRect(layout.bars.hp);
  const mp = scaleRect(layout.bars.mp);
  const power = scaleRect(layout.bars.power);
  return {
    offsetX: layout.offsetX * BOARD_SCALE,
    shiftY: layout.shiftY * BOARD_SCALE,
    hp,
    mp,
    power,
    boxW: Math.max(hp.x + hp.w, mp.x + mp.w, power.x + power.w),
    boxH: Math.max(hp.y + hp.h, mp.y + mp.h, power.y + power.h),
  };
};

export const PLAYER_HUD_LAYOUT = buildHudLayout({
  offsetX: 8,
  shiftY: -1,
  bars: {
    hp: { x: 2.6, y: 3, w: 74.7, h: 5 },
    mp: { x: 2.6, y: 10.3, w: 75, h: 5 },
    power: { x: 2, y: 17.4, w: 74.7, h: 5 },
  },
});

export const ENEMY_HUD_LAYOUT = buildHudLayout({
  offsetX: 8,
  shiftY: -1,
  bars: {
    hp: { x: 2.6, y: 3, w: 74.7, h: 5 },
    mp: { x: 2.6, y: 10.3, w: 74.7, h: 5 },
    power: { x: 0, y: 17.4, w: 74.7, h: 5 },
  },
});

export const HUD_BASE_Y = 233 * BOARD_SCALE;
export const HUD_STACK_H = Math.max(PLAYER_HUD_LAYOUT.boxH, ENEMY_HUD_LAYOUT.boxH);
export const TURN_TIMER_SHIFT_X = 0;
export const TURN_TIMER_SHIFT_Y = 0;
export const TURN_TIMER_TOP =
  HUD_BASE_Y + HUD_STACK_H - 20 * BOARD_SCALE + TURN_TIMER_SHIFT_Y * BOARD_SCALE;

export const BOARD_LEFT = BOARD_LEFT_OFFSET;
export const BOARD_TOP = BOARD_TOP_OFFSET;
export const EXTRA_TURNS_SHIFT_X = 0;
export const EXTRA_TURNS_SHIFT_Y = 0;

export const CHARS_ROW_SHIFT_X = 0;
export const CHARS_ROW_SHIFT_Y = 0;
export const PLAYER_SPRITE_SHIFT_X = 0;
export const PLAYER_SPRITE_SHIFT_Y = 0;
export const ENEMY_SPRITE_SHIFT_X = 0;
export const ENEMY_SPRITE_SHIFT_Y = 0;
export const BATTLE_PLAYER_SCALE = 1;
const CREATE_CHARACTER_DEFAULT_SCALE = 2.2;

const CHARS_ROW_H = 104;
const BATTLE_PANEL_LEFT_SHIFT = 0;
const ACTOR_BASELINE_NATIVE_Y = 316;
const ACTOR_STAGE_W = BG_W;
const PLAYER_BASE_LEFT = 8 * BOARD_SCALE;
const MONSTER_BASE_RIGHT = 8 * BOARD_SCALE;
const ATTACK_CONTACT_OVERLAP = Math.round(18 * BOARD_SCALE);

export const measureBattlePlayerSize = (appearance: CharacterAppearance) => {
  const measured = measureCharacterRenderer(appearance, BATTLE_PLAYER_SCALE, true);
  return {
    ...measured,
    groundOffset: measured.groundOffset + Math.round(3 * BATTLE_PLAYER_SCALE / CREATE_CHARACTER_DEFAULT_SCALE),
  };
};

export const getBattleActorLayout = (monsterType: MonsterType, appearance: CharacterAppearance) => {
  const playerSize = measureBattlePlayerSize(appearance);
  const monsterSize = monsterDisplaySize(monsterType);
  const monsterPlacement = monsterPlacementMetrics(monsterType);
  const monsterBaseLeft = ACTOR_STAGE_W - monsterSize.w - MONSTER_BASE_RIGHT;
  const attackStopLeft = monsterBaseLeft - playerSize.w + ATTACK_CONTACT_OVERLAP;
  const attackTravelX = Math.max(0, attackStopLeft - PLAYER_BASE_LEFT);

  return {
    stageWidth: ACTOR_STAGE_W,
    playerSize,
    monsterSize,
    monsterGroundOffset: monsterPlacement.groundOffset,
    playerBaseLeft: PLAYER_BASE_LEFT,
    monsterBaseLeft,
    attackTravelX,
  };
};

export const getBattleStageLayout = (monsterType: MonsterType) => {
  const stageTotalH = BG_H;
  const panelLeft =
    Math.round((SCREEN_W - BG_W) / 2) + Math.round(BATTLE_PANEL_LEFT_SHIFT * BOARD_SCALE);
  const panelTop = Math.round((SCREEN_H - stageTotalH) / 2);
  const charsTop =
    panelTop + Math.round(ACTOR_BASELINE_NATIVE_Y * BOARD_SCALE) - CHARS_ROW_H
    + CHARS_ROW_SHIFT_Y * BOARD_SCALE;
  const damagePopupTop = charsTop - 8 * BOARD_SCALE;

  return {
    panelLeft,
    panelTop,
    charsTop,
    damagePopupTop,
    charsRowHeight: CHARS_ROW_H,
    monsterSize: monsterDisplaySize(monsterType),
  };
};
