export const TOTAL_FRAMES = 7;
export const EXPLODE_START = 1;
// Skip the last sprite-sheet frame because it leaves a visible "residual line"
// on sword gems before the cell clears.
export const EXPLODE_END = 5;

export const GEM_RENDER_TYPES = [0, 1, 2, 3, 4, 5, 6, 8] as const;
export type VisibleGemType = typeof GEM_RENDER_TYPES[number];

// Java `nj` nodes carry both a visible piece index and a state/type flag.
// Keep the extra ids in the board model so the local engine can follow Java's
// swap/span semantics while the renderer still maps them back to the right art.
export type GemType =
  | VisibleGemType
  | 10 | 11 | 12 | 13 | 14 | 15
  | 20 | 21 | 22 | 23 | 24 | 25
  | 70;

export type FXKind = 'gold' | 'mp' | 'sword' | 'crystal_red';

export interface BattleResourceProfile {
  // Source: server-owned remake resource formula from
  // docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
  // FE must not recalculate raw stat -> percent; it only applies server-provided coefficients.
  healGainPercent: number;
  manaGainPercent: number;
  powerGainPercent: number;
}

export interface BattleAttackProfile {
  // Source: BATTLE_SYSTEM_RECONSTRUCTION.md §Resource / damage formulas + gameplay memory.
  // Sword match damage must come from the active combatant attack range supplied by server
  // bootstrap, not from the old remake-local fixed 5 damage per white sword gem.
  minDamage: number;
  maxDamage: number;
}

export type AILevel =
  | 'borm'
  | 'dan_thuong'
  | 'linh_canh'
  | 'thu_linh'
  | 'tuong_quan'
  | 'quan_su'
  | 'thien_tai';

export type Board = (GemType | null)[][];

export type MoveSpec = { r1: number; c1: number; r2: number; c2: number };

export interface FallEntry {
  r: number;
  c: number;
  srcRow: number;
}

export const GEM_SHEETS: Record<VisibleGemType, any> = {
  0: require('../../../../assets/battle/02_chess_pieces/chess0.png'),
  1: require('../../../../assets/battle/02_chess_pieces/chess1.png'),
  2: require('../../../../assets/battle/02_chess_pieces/chess2.png'),
  3: require('../../../../assets/battle/02_chess_pieces/chess3.png'),
  4: require('../../../../assets/battle/02_chess_pieces/chess4.png'),
  5: require('../../../../assets/battle/02_chess_pieces/chess5.png'),
  6: require('../../../../assets/battle/02_chess_pieces/chess6.png'),
  8: require('../../../../assets/battle/02_chess_pieces/chess8.png'),
};

export const GEM_CRYSTAL_OVERLAY = require('../../../../assets/battle/03_crystals_casting/chesscrystal.png');

// Gameplay memory/user evidence chốt board active có 8 icon từ `/chess0..8`
// nhưng bỏ `chess7`: `0,1,2,3,4,5,6,8`. Java `nj` decompile vẫn giữ node
// id/mask/type riêng; `8` được port như kiếm đỏ/fire sword active có mask kiếm
// để match chung kiếm trắng và trigger nổ 3x3 khi bị touch/clear.
export const GEM_TYPES: readonly GemType[] = [0, 1, 2, 3, 4, 5, 6, 8];

const GEM_RENDER_MAP: Record<GemType, VisibleGemType> = {
  0: 0,
  1: 1,
  2: 2,
  3: 3,
  4: 4,
  5: 5,
  6: 6,
  8: 8,
  10: 8,
  11: 1,
  12: 2,
  13: 3,
  14: 4,
  15: 5,
  20: 0,
  21: 1,
  22: 2,
  23: 3,
  24: 4,
  25: 5,
  70: 6,
};

const GEM_MATCH_MASKS: Record<GemType, number> = {
  0: 1,
  1: 2,
  2: 4,
  3: 8,
  4: 16,
  5: 32,
  6: 64,
  8: 1,
  10: 1,
  11: 2,
  12: 4,
  13: 8,
  14: 16,
  15: 32,
  20: 1,
  21: 2,
  22: 4,
  23: 8,
  24: 16,
  25: 32,
  70: 64,
};

const GEM_CATEGORIES: Record<GemType, number> = {
  0: 0,
  1: 1,
  2: 2,
  3: 3,
  4: 4,
  5: 5,
  6: 6,
  8: 0,
  10: 0,
  11: 1,
  12: 2,
  13: 3,
  14: 4,
  15: 5,
  20: 0,
  21: 1,
  22: 2,
  23: 3,
  24: 4,
  25: 5,
  70: 6,
};

export const SWORD_CAT = 0;
export const WHITE_SWORD_GEM = 0 as const;
export const RED_SWORD_GEM = 8 as const;
export const SWORD_DAMAGE: Record<typeof WHITE_SWORD_GEM | typeof RED_SWORD_GEM, number> = {
  // Legacy fallback only. Runtime board damage is scaled by active actor min/max damage in
  // calcSwordDamage(). Keep this as a safe default for tests/tools that do not pass actor stats.
  0: 5,
  // Gameplay memory chốt kiếm đỏ/fire sword gây sát thương ngay với hệ số x1.5
  // so với kiếm trắng. Formula damage cuối vẫn là remake/server-owned.
  8: 7.5,
};

const GEM_FX_BASE: Record<VisibleGemType, { dmg: number; heal: number; mana: number; pow: number }> = {
  // Java reconstruction note:
  // Java client confirms `lh.s/r`, `lh.u/t`, `lh.w/v` battle bars and the board node
  // families, but the old server-side exact resource gain table is not present in
  // decompiled client code. These are remake-local per-3-match base gains chosen to
  // keep level-1 pacing close to observed Java feel: MP should not refill from only
  // a few small matches, HP/peach should not let monsters heal back too quickly, and
  // Power/nộ should build gradually rather than replacing skill/combat damage.
  0: { dmg: 5, heal: 0, mana: 0, pow: 3 },
  1: { dmg: 0, heal: 12, mana: 0, pow: 1 },
  2: { dmg: 0, heal: 2, mana: 8, pow: 1 },
  3: { dmg: 0, heal: 0, mana: 0, pow: 3 },
  4: { dmg: 0, heal: 4, mana: 3, pow: 1 },
  5: { dmg: 0, heal: 0, mana: 5, pow: 2 },
  6: { dmg: 0, heal: 0, mana: 0, pow: 1 },
  8: { dmg: 5, heal: 0, mana: 0, pow: 3 },
};

export const GEM_FX: Record<VisibleGemType, { dmg: number; heal: number; mana: number; pow: number }> = GEM_FX_BASE;

const GEM_FX_KIND_BASE: Record<VisibleGemType, FXKind> = {
  0: 'sword',
  1: 'crystal_red',
  2: 'mp',
  3: 'crystal_red',
  4: 'mp',
  5: 'crystal_red',
  6: 'gold',
  8: 'sword',
};

export const GEM_FX_KIND: Record<VisibleGemType, FXKind> = GEM_FX_KIND_BASE;

export const getGemRenderType = (gem: GemType): VisibleGemType => GEM_RENDER_MAP[gem];

export const getGemSheet = (gem: GemType) => GEM_SHEETS[getGemRenderType(gem)];

export const getGemStateClass = (gem: GemType): 1 | 2 | 4 => {
  if (gem >= 10 && gem <= 15) return 2;
  if (gem >= 20 && gem <= 25) return 4;
  return 1;
};

export const getGemMatchMask = (gem: GemType): number => GEM_MATCH_MASKS[gem];

export const getGemCategory = (gem: GemType): number => GEM_CATEGORIES[gem];

export const getGemFX = (gem: GemType) => GEM_FX_BASE[getGemRenderType(gem)];

const scalePercent = (value: number, percent: number): number =>
  Math.max(0, Math.trunc((Math.max(0, value) * percent) / 100));

export const calcPeachGainByStrength = (
  maxHp: number,
  peachCount: number,
  profile?: BattleResourceProfile | null,
): number => {
  if (peachCount <= 0 || maxHp <= 0) return 0;

  // Reconstruction/remake formula, chốt 2026-04-27:
  // HpScale = clamp(... server-owned percent from TotalStrength ...)
  // HealGainBase = max(3, floor(MaxHp * 6 / 100))
  // HealGain = floor(HealGainBase * peachCount / 3 * HpScale / 100)
  // Source: BATTLE_SYSTEM_RECONSTRUCTION.md §Resource / damage formulas.
  const basePerThree = Math.max(3, Math.trunc((maxHp * 6) / 100));
  return scalePercent(Math.trunc((basePerThree * peachCount) / 3), profile?.healGainPercent ?? 100);
};

export const calcManaGainByMagic = (
  maxMp: number,
  manaGemCount: number,
  profile?: BattleResourceProfile | null,
): number => {
  if (manaGemCount <= 0 || maxMp <= 0) return 0;

  // Reconstruction/remake formula, chốt 2026-04-27:
  // ManaScale = clamp(... server-owned percent from TotalMagic ...)
  // ManaGain = floor(MaxMp * 625 * gemCount * ManaScale / (10000 * 3 * 100))
  // Equivalent before server percent: floor(MaxMp * 625 * gemCount / (10000 * 3)).
  // Source: BATTLE_SYSTEM_RECONSTRUCTION.md §Resource / damage formulas.
  const raw = Math.trunc((maxMp * 625 * manaGemCount) / (10000 * 3));
  const scaled = scalePercent(raw, profile?.manaGainPercent ?? 100);
  return scaled > 0 ? scaled : 1;
};

export const calcPowerGainByStrength = (
  maxPower: number,
  peachCount: number,
  profile?: BattleResourceProfile | null,
): number => {
  if (peachCount <= 0 || maxPower <= 0) return 0;

  // Reconstruction/remake formula, chốt 2026-04-27:
  // PowerScale = clamp(... server-owned percent from TotalStrength ...)
  // PowerGain = floor(MaxPower * peachCount * PowerScale / (19 * 100)).
  // Source: BATTLE_SYSTEM_RECONSTRUCTION.md §Resource / damage formulas.
  const raw = Math.trunc((maxPower * peachCount) / 19);
  return scalePercent(raw, profile?.powerGainPercent ?? 100);
};

export const getGemFXKind = (gem: GemType): FXKind => GEM_FX_KIND_BASE[getGemRenderType(gem)];

export const isSwordGem = (gem: GemType): gem is typeof WHITE_SWORD_GEM | typeof RED_SWORD_GEM | 10 | 20 =>
  getGemCategory(gem) === SWORD_CAT;

export const isRedSwordGem = (gem: GemType): gem is typeof RED_SWORD_GEM | 10 =>
  getGemCategory(gem) === SWORD_CAT && getGemRenderType(gem) === RED_SWORD_GEM;

export const isCrystalGem = (gem: GemType): boolean => getGemStateClass(gem) === 2;

export const AI_CONFIGS: Record<AILevel, {
  name: string;
  emoji: string;
  desc: string;
  thinkMs: number;
}> = {
  borm: { name: 'Thằng Bờm', emoji: '🐸', desc: 'Đánh ngẫu nhiên, kể cả không combo', thinkMs: 2800 },
  dan_thuong: { name: 'Dân Thường', emoji: '👨‍🌾', desc: 'Chọn nước hợp lệ ngẫu nhiên', thinkMs: 2200 },
  linh_canh: { name: 'Lính Canh', emoji: '🗡', desc: 'Ưu tiên ăn nhiều gem nhất có thể', thinkMs: 1700 },
  thu_linh: { name: 'Thủ Lĩnh', emoji: '⚔', desc: 'Ưu tiên gem kiếm/lửa, sát thương', thinkMs: 1400 },
  tuong_quan: { name: 'Tướng Quân', emoji: '🏯', desc: 'Tấn công + hồi máu theo tình huống', thinkMs: 1100 },
  quan_su: { name: 'Quân Sư', emoji: '📜', desc: 'Tính trước 1 lần chuỗi combo', thinkMs: 800 },
  thien_tai: { name: 'Thiên Tài', emoji: '⭐', desc: 'Mô phỏng 2 lớp chain, tối đa đòn', thinkMs: 500 },
};

export const AI_ORDER: AILevel[] = [
  'borm',
  'dan_thuong',
  'linh_canh',
  'thu_linh',
  'tuong_quan',
  'quan_su',
  'thien_tai',
];

export const FOCUS_IMG = require('../../../../assets/battle/08_focus_cursor/focusmovechess1.png');
export const ARROW_PLAYER_IMG = require('../../../../assets/ui/10_focus_confirmed/arrowfocus1.png');
export const ARROW_ENEMY_IMG = require('../../../../assets/ui/09_focus_candidate/arrowfocus2.png');
export const AURA1_IMG = require('../../../../assets/battle/06_auras/aura1.png');
export const AURA2_IMG = require('../../../../assets/battle/06_auras/aura2.png');
export const AURA3_IMG = require('../../../../assets/battle/06_auras/aura3.png');
export const CRYS_BLUE = require('../../../../assets/battle/03_crystals_casting/crystalblue.png');
export const CRYS_RED = require('../../../../assets/battle/03_crystals_casting/crystalred.png');
