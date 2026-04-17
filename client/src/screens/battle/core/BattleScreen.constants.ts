import type { BattleResult, ResultArtMeta } from './BattleScreen.types';

export const TURN_TIME_LIMIT_SEC = 30;
export const MATCH_HOLD_BEFORE_EXPLODE_MS = 120;
export const MATCH_SPARKLE_MIN_MS = 720;
export const MATCH_SPARKLE_MAX_MS = 980;
export const MATCH_SPARKLE_STAGGER_MS = 140;
export const BONUS_BANNER_FADE_IN_MS = 250;
export const BONUS_BANNER_TOTAL_MS = 2000;
export const BONUS_BANNER_FADE_OUT_MS = 300;
export const BONUS_BANNER_HOLD_MS =
  BONUS_BANNER_TOTAL_MS - BONUS_BANNER_FADE_IN_MS - BONUS_BANNER_FADE_OUT_MS;
export const EXTRA_TURNS_BADGE_TOTAL_MS = 3000;
export const COLLECT_FX_DURATION_MS = 2600;
export const GAIN_POPUP_DURATION_MS = 1200;
export const COLLECT_PULSE_IN_MS = 240;
export const COLLECT_PULSE_OUT_MS = 320;
export const RESULT_ART_INDEX = 1;

export const RESULT_ART_META: Record<BattleResult, ResultArtMeta> = {
  victory: {
    asset: require('../../../../assets/battle_legacy/07_result_splash/strwin.png'),
    frameWidth: 172,
    frameHeight: 65,
    sheetWidth: 516,
    sheetHeight: 65,
  },
  defeat: {
    asset: require('../../../../assets/battle_legacy/07_result_splash/strlose.png'),
    frameWidth: 146,
    frameHeight: 56,
    sheetWidth: 438,
    sheetHeight: 56,
  },
};

export const BATTLE_ASSETS = {
  boardFrame: require('../../../../assets/battle_legacy/00_board_background/bkboardv.png'),
  hpBar: require('../../../../assets/battle_legacy/01_hud_bars/hpbar.png'),
  manaBar: require('../../../../assets/battle_legacy/01_hud_bars/manabar.png'),
  powerBar: require('../../../../assets/battle_legacy/01_hud_bars/powerbar.png'),
} as const;
