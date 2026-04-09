import type { MonsterType } from '../../engine/MonsterSprite';

export const TOTAL_FRAMES = 7;
export const EXPLODE_START = 1;
export const EXPLODE_END = 6;

export const GEM_TYPES = [0, 1, 2, 3, 4, 5, 6, 8] as const;
export type GemType = typeof GEM_TYPES[number];

export type FXKind = 'gold' | 'mp' | 'sword' | 'crystal_red';

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

export const GEM_SHEETS: Record<GemType, any> = {
  0: require('../../../assets/chess0.png'),
  1: require('../../../assets/chess1.png'),
  2: require('../../../assets/chess2.png'),
  3: require('../../../assets/chess3.png'),
  4: require('../../../assets/chess4.png'),
  5: require('../../../assets/chess5.png'),
  6: require('../../../assets/chess6.png'),
  8: require('../../../assets/chess8.png'),
};

// chess0 (kiếm trắng) + chess8 (kiếm đỏ) cùng category → match nhau
export const GEM_CATEGORY: Record<GemType, number> = {
  0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 8: 0,
};

export const SWORD_CAT = 0;
export const WHITE_SWORD_GEM = 0 as const;
export const RED_SWORD_GEM = 8 as const;
export const SWORD_DAMAGE: Record<typeof WHITE_SWORD_GEM | typeof RED_SWORD_GEM, number> = {
  0: 5,
  8: 5,
};

export const GEM_FX: Record<GemType, { dmg: number; heal: number; mana: number; pow: number }> = {
  0: { dmg: 5, heal: 0, mana: 0, pow: 5 },
  1: { dmg: 0, heal: 28, mana: 0, pow: 2 },
  2: { dmg: 0, heal: 5, mana: 15, pow: 3 },
  3: { dmg: 0, heal: 0, mana: 0, pow: 5 },
  4: { dmg: 0, heal: 10, mana: 5, pow: 3 },
  5: { dmg: 0, heal: 0, mana: 8, pow: 4 },
  6: { dmg: 0, heal: 0, mana: 0, pow: 2 },
  8: { dmg: 5, heal: 0, mana: 0, pow: 5 },
};

export const MONSTER_HP: Record<MonsterType, number> = {
  fire: 120,
  ice: 150,
  zap: 180,
};

export const GEM_FX_KIND: Record<GemType, FXKind> = {
  0: 'sword',
  1: 'crystal_red',
  2: 'mp',
  3: 'crystal_red',
  4: 'mp',
  5: 'crystal_red',
  6: 'gold',
  8: 'sword',
};

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

export const FOCUS_IMG = require('../../../assets/focusmovechess1.png');
export const ARROW_PLAYER_IMG = require('../../../assets/arrowfocus1.png');
export const ARROW_ENEMY_IMG = require('../../../assets/arrowfocus2.png');
export const AURA1_IMG = require('../../../assets/battle/effects/aura1.png');
export const AURA2_IMG = require('../../../assets/battle/effects/aura2.png');
export const AURA3_IMG = require('../../../assets/battle/effects/aura3.png');
export const CRYS_BLUE = require('../../../assets/battle/effects/crystalblue.png');
export const CRYS_RED = require('../../../assets/battle/effects/crystalred.png');
