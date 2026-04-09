/**
 * BattleScreen — Match-3 chiến đấu
 *
 * Animation:
 *   offsets[r][c] = Animated.Value cho translateY (0 = đúng vị trí logic)
 *   Vào trận: rơi từ trên xuống theo stagger cột
 *   Sau match: gravity collapse với fall animation
 *
 * AI System:
 *   7 cấp độ từ Thằng Bờm (ngẫu nhiên) → Thiên Tài (tối ưu chain)
 *   AI tự đánh khi phase=idle và aiLevel != null
 */
import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View, Image, Text, TouchableOpacity,
  Animated, Easing, StyleSheet,
} from 'react-native';
import {
  MonsterSprite, MonsterType, WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize,
} from '../engine/MonsterSprite';
import {
  s,
  SCREEN_W, SCREEN_H,
  BG_W, BG_H, BOARD_SCALE,
  BOARD_COLS, BOARD_ROWS, GEM_SIZE,
  BOARD_POS_LEFT, BOARD_POS_TOP,
} from './BattleScreen.styles';

// ── Sprite sheet: 196 × 28 = 7 frames × 28px ────────────────────────────────
const TOTAL_FRAMES  = 7;
const EXPLODE_START = 1;
const EXPLODE_END   = 6;

// ── Gem types ─────────────────────────────────────────────────────────────────
const GEM_TYPES = [0, 1, 2, 3, 4, 5, 6, 8] as const;
type GemType = typeof GEM_TYPES[number];

const GEM_SHEETS: Record<GemType, any> = {
  0: require('../../assets/chess0.png'),
  1: require('../../assets/chess1.png'),
  2: require('../../assets/chess2.png'),
  3: require('../../assets/chess3.png'),
  4: require('../../assets/chess4.png'),
  5: require('../../assets/chess5.png'),
  6: require('../../assets/chess6.png'),
  8: require('../../assets/chess8.png'),
};

// chess0 (kiếm trắng) + chess8 (kiếm đỏ) cùng category → match nhau
const GEM_CATEGORY: Record<GemType, number> = {
  0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 8: 0,
};
const SWORD_CAT = 0;

const GEM_FX: Record<GemType, { dmg: number; heal: number; mana: number; pow: number }> = {
  0: { dmg: 20, heal:  0, mana:  0, pow: 5 },
  1: { dmg:  0, heal: 28, mana:  0, pow: 2 },
  2: { dmg:  8, heal:  5, mana: 15, pow: 3 },
  3: { dmg: 22, heal:  0, mana:  0, pow: 5 },
  4: { dmg: 10, heal: 10, mana:  5, pow: 3 },
  5: { dmg: 12, heal:  0, mana:  8, pow: 4 },
  6: { dmg:  5, heal:  0, mana:  0, pow: 2 },
  8: { dmg: 22, heal:  0, mana:  0, pow: 5 },
};

const MONSTER_HP: Record<MonsterType, number> = { fire: 120, ice: 150, zap: 180 };

// Gem → particle effect type mapping
type FXKind = 'gold' | 'mp' | 'sword' | 'crystal_red';
const GEM_FX_KIND: Record<GemType, FXKind> = {
  0: 'sword',       // kiếm trắng
  1: 'crystal_red',  // trái tim  → heal
  2: 'mp',           // âm dương  → MP
  3: 'crystal_red',  // đào       → heal
  4: 'mp',           // giọt tím  → MP
  5: 'crystal_red',  // ngôi sao  → power
  6: 'gold',         // vàng
  8: 'sword',        // kiếm đỏ
};

// ════════════════════════════════════════════════════════════════════════════
// AI SYSTEM
// ════════════════════════════════════════════════════════════════════════════
type AILevel =
  | 'borm'        // Thằng Bờm   — đánh ngẫu nhiên kể cả không có combo
  | 'dan_thuong'  // Dân Thường  — chọn nước hợp lệ ngẫu nhiên
  | 'linh_canh'  // Lính Canh   — ăn nhiều gem nhất
  | 'thu_linh'   // Thủ Lĩnh    — ưu tiên gem sát thương (kiếm/lửa)
  | 'tuong_quan' // Tướng Quân  — cân bằng tấn/thủ theo máu
  | 'quan_su'    // Quân Sư     — tính combo chuỗi 1 lần
  | 'thien_tai'; // Thiên Tài   — mô phỏng 2 lớp chain, tối đa sát thương

const AI_CONFIGS: Record<AILevel, {
  name: string; emoji: string; desc: string; thinkMs: number;
}> = {
  borm:       { name: 'Thằng Bờm',  emoji: '🐸', desc: 'Đánh ngẫu nhiên, kể cả không combo', thinkMs: 2800 },
  dan_thuong: { name: 'Dân Thường', emoji: '👨‍🌾', desc: 'Chọn nước hợp lệ ngẫu nhiên',        thinkMs: 2200 },
  linh_canh:  { name: 'Lính Canh', emoji: '🗡',  desc: 'Ưu tiên ăn nhiều gem nhất có thể',   thinkMs: 1700 },
  thu_linh:   { name: 'Thủ Lĩnh',  emoji: '⚔',  desc: 'Ưu tiên gem kiếm/lửa, sát thương',  thinkMs: 1400 },
  tuong_quan: { name: 'Tướng Quân',emoji: '🏯',  desc: 'Tấn công + hồi máu theo tình huống', thinkMs: 1100 },
  quan_su:    { name: 'Quân Sư',   emoji: '📜',  desc: 'Tính trước 1 lần chuỗi combo',       thinkMs: 800  },
  thien_tai:  { name: 'Thiên Tài', emoji: '⭐',  desc: 'Mô phỏng 2 lớp chain, tối đa đòn',  thinkMs: 500  },
};

const AI_ORDER: AILevel[] = [
  'borm', 'dan_thuong', 'linh_canh', 'thu_linh', 'tuong_quan', 'quan_su', 'thien_tai',
];

// ════════════════════════════════════════════════════════════════════════════
// BOARD LOGIC (pure functions)
// ════════════════════════════════════════════════════════════════════════════
type Board = (GemType | null)[][];

const sameCat = (a: GemType | null, b: GemType | null) =>
  a !== null && b !== null && GEM_CATEGORY[a] === GEM_CATEGORY[b];

function randomGem(): GemType {
  return GEM_TYPES[Math.floor(Math.random() * GEM_TYPES.length)];
}

function makeBoard(): Board {
  const board: Board = Array.from({ length: BOARD_ROWS }, () =>
    Array.from({ length: BOARD_COLS }, () => null as GemType | null)
  );
  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      const forbidden = new Set<number>();
      if (c >= 2) {
        const g1 = board[r][c - 1], g2 = board[r][c - 2];
        if (g1 !== null && g2 !== null && GEM_CATEGORY[g1] === GEM_CATEGORY[g2])
          forbidden.add(GEM_CATEGORY[g1]);
      }
      if (r >= 2) {
        const g1 = board[r - 1][c], g2 = board[r - 2][c];
        if (g1 !== null && g2 !== null && GEM_CATEGORY[g1] === GEM_CATEGORY[g2])
          forbidden.add(GEM_CATEGORY[g1]);
      }
      const valid = GEM_TYPES.filter(t => !forbidden.has(GEM_CATEGORY[t]));
      const pool  = valid.length > 0 ? valid : [...GEM_TYPES];
      board[r][c] = pool[Math.floor(Math.random() * pool.length)];
    }
  }
  return board as Board;
}

function findMatches(b: Board): Set<string> {
  const hit = new Set<string>();
  for (let r = 0; r < BOARD_ROWS; r++) {
    let c = 0;
    while (c < BOARD_COLS - 2) {
      const g = b[r][c];
      if (g !== null && sameCat(g, b[r][c + 1]) && sameCat(g, b[r][c + 2])) {
        let e = c + 2;
        while (e + 1 < BOARD_COLS && sameCat(g, b[r][e + 1])) e++;
        for (let i = c; i <= e; i++) hit.add(`${r},${i}`);
        c = e + 1;
      } else c++;
    }
  }
  for (let c = 0; c < BOARD_COLS; c++) {
    let r = 0;
    while (r < BOARD_ROWS - 2) {
      const g = b[r][c];
      if (g !== null && sameCat(g, b[r + 1][c]) && sameCat(g, b[r + 2][c])) {
        let e = r + 2;
        while (e + 1 < BOARD_ROWS && sameCat(g, b[e + 1][c])) e++;
        for (let i = r; i <= e; i++) hit.add(`${i},${c}`);
        r = e + 1;
      } else r++;
    }
  }
  return hit;
}

/**
 * Kiểm tra có nhóm gem CÙNG MÀU (category) match liền kề ≥ 4 viên hay không.
 * Dùng connected-component (BFS), chỉ gom ô cùng category.
 *
 * Quy tắc: mỗi bước chain có BẤT KỲ nhóm cùng màu ≥ 4 gem → +1 lượt (flat).
 * 3 máu + 3 vàng sát nhau KHÔNG tính (khác category).
 * Hình L/T/+ cùng màu (≥ 4 gem) → +1 lượt.
 */
function hasBonusTurn(matched: Set<string>, b: Board): boolean {
  if (matched.size < 4) return false;

  const visited = new Set<string>();
  const DIRS = [[0, 1], [0, -1], [1, 0], [-1, 0]];

  for (const start of Array.from(matched)) {
    if (visited.has(start)) continue;
    const [sr, sc] = start.split(',').map(Number);
    const startGem = b[sr][sc];
    if (startGem === null) { visited.add(start); continue; }
    const cat = GEM_CATEGORY[startGem];

    // BFS: chỉ gom ô matched liền kề CÙNG category
    const queue = [start];
    visited.add(start);
    let size = 0;
    while (queue.length > 0) {
      const k = queue.shift()!;
      size++;
      const [r, c] = k.split(',').map(Number);
      for (const [dr, dc] of DIRS) {
        const nr = r + dr, nc = c + dc;
        const nk = `${nr},${nc}`;
        if (matched.has(nk) && !visited.has(nk)) {
          const ng = b[nr]?.[nc];
          if (ng !== null && ng !== undefined && GEM_CATEGORY[ng] === cat) {
            visited.add(nk);
            queue.push(nk);
          }
        }
      }
    }
    if (size >= 4) return true;
  }

  return false;
}

function expandSword(matched: Set<string>, b: Board): Set<string> {
  const out = new Set<string>(matched);
  matched.forEach(k => {
    const [r, c] = k.split(',').map(Number);
    const g = b[r][c];
    if (g !== null && GEM_CATEGORY[g] === SWORD_CAT) {
      for (let dr = -1; dr <= 1; dr++)
        for (let dc = -1; dc <= 1; dc++) {
          const nr = r + dr, nc = c + dc;
          if (nr >= 0 && nr < BOARD_ROWS && nc >= 0 && nc < BOARD_COLS)
            out.add(`${nr},${nc}`);
        }
    }
  });
  return out;
}

interface FallEntry { r: number; c: number; srcRow: number; }

function collapseLogic(b: Board, matched: Set<string>): { newBoard: Board; fallMap: FallEntry[] } {
  const nb: Board = b.map(row => [...row]);
  const fallMap: FallEntry[] = [];

  for (let c = 0; c < BOARD_COLS; c++) {
    const removed = new Set<number>();
    for (let r = 0; r < BOARD_ROWS; r++)
      if (matched.has(`${r},${c}`)) removed.add(r);
    if (removed.size === 0) continue;

    const fill = removed.size;
    const tgt2src: number[] = new Array(BOARD_ROWS);
    let tgt = BOARD_ROWS - 1;
    for (let r = BOARD_ROWS - 1; r >= 0; r--)
      if (!removed.has(r)) { tgt2src[tgt] = r; tgt--; }
    for (let i = 0; i < fill; i++)
      tgt2src[i] = -(fill - i);

    for (let r = 0; r < BOARD_ROWS; r++) {
      const src = tgt2src[r];
      nb[r][c] = src >= 0 ? b[src][c] : randomGem();
      if (src !== r) fallMap.push({ r, c, srcRow: src });
    }
  }
  return { newBoard: nb, fallMap };
}

// ── AI helpers ────────────────────────────────────────────────────────────────
function applySwap(b: Board, r1: number, c1: number, r2: number, c2: number): Board {
  const nb = b.map(row => [...row]);
  [nb[r1][c1], nb[r2][c2]] = [nb[r2][c2], nb[r1][c1]];
  return nb;
}

type MoveSpec = { r1: number; c1: number; r2: number; c2: number };

/** Tất cả swap hợp lệ tạo ra ít nhất 1 combo */
function getAllValidMoves(b: Board): MoveSpec[] {
  const moves: MoveSpec[] = [];
  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      if (c + 1 < BOARD_COLS) {
        if (findMatches(applySwap(b, r, c, r, c + 1)).size > 0)
          moves.push({ r1: r, c1: c, r2: r, c2: c + 1 });
      }
      if (r + 1 < BOARD_ROWS) {
        if (findMatches(applySwap(b, r, c, r + 1, c)).size > 0)
          moves.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    }
  }
  return moves;
}

function calcEffect(b: Board, matched: Set<string>): { dmg: number; heal: number; mp: number } {
  let dmg = 0, heal = 0, mp = 0;
  const counts: Partial<Record<GemType, number>> = {};
  matched.forEach(k => {
    const [r, c] = k.split(',').map(Number);
    const g = b[r][c];
    if (g !== null) counts[g] = (counts[g] ?? 0) + 1;
  });
  Object.entries(counts).forEach(([gs, cnt]) => {
    const g = Number(gs) as GemType;
    const fx = GEM_FX[g];
    dmg  += Math.round(fx.dmg  * cnt! / 3);
    heal += Math.round(fx.heal * cnt! / 3);
    mp   += Math.round(fx.mana * cnt!);
  });
  return { dmg, heal, mp };
}

function scoreMove(
  b: Board, m: MoveSpec, level: AILevel, eHP: number, pHP: number,
): number {
  if (level === 'borm') return Math.random() * 100;

  const nb  = applySwap(b, m.r1, m.c1, m.r2, m.c2);
  const raw = findMatches(nb);
  if (raw.size === 0) return -1;

  const expanded       = expandSword(raw, nb);
  const { dmg, heal }  = calcEffect(nb, raw);
  const blastBonus     = (expanded.size - raw.size) * 6;
  const count          = raw.size;

  switch (level) {
    case 'dan_thuong':  return Math.random() * 100 + 1;
    case 'linh_canh':   return count * 10 + blastBonus;
    case 'thu_linh':    return dmg * 3 + blastBonus + count * 2;
    case 'tuong_quan': {
      const urgency = pHP < 30 ? 3.5 : 1;
      return dmg * 2 + heal * urgency + blastBonus + count;
    }
    case 'quan_su': {
      const { newBoard: nb2 } = collapseLogic(nb, expanded);
      const c2raw = findMatches(nb2);
      const chain = c2raw.size > 0 ? calcEffect(nb2, c2raw) : { dmg: 0, heal: 0, mp: 0 };
      return dmg * 2 + chain.dmg * 4 + blastBonus + heal + count * 2;
    }
    case 'thien_tai': {
      const { newBoard: nb2 } = collapseLogic(nb, expanded);
      const c2raw = findMatches(nb2);
      const c2exp = c2raw.size > 0 ? expandSword(c2raw, nb2) : new Set<string>();
      const c2fx  = c2raw.size > 0 ? calcEffect(nb2, c2raw) : { dmg: 0, heal: 0, mp: 0 };

      let c3fx = { dmg: 0, heal: 0, mp: 0 };
      if (c2raw.size > 0) {
        const { newBoard: nb3 } = collapseLogic(nb2, c2exp);
        const c3raw = findMatches(nb3);
        if (c3raw.size > 0) c3fx = calcEffect(nb3, c3raw);
      }
      return dmg * 2 + c2fx.dmg * 4 + c3fx.dmg * 6 + heal + blastBonus + count * 2;
    }
    default: return count;
  }
}

function pickAIMove(b: Board, level: AILevel, eHP: number, pHP: number): MoveSpec | null {
  if (level === 'borm') {
    // Bờm: có thể chọn swap hoàn toàn ngẫu nhiên (kể cả không combo)
    const all: MoveSpec[] = [];
    for (let r = 0; r < BOARD_ROWS; r++)
      for (let c = 0; c < BOARD_COLS; c++) {
        if (c + 1 < BOARD_COLS) all.push({ r1: r, c1: c, r2: r, c2: c + 1 });
        if (r + 1 < BOARD_ROWS) all.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    return all[Math.floor(Math.random() * all.length)] ?? null;
  }

  const valid = getAllValidMoves(b);
  if (valid.length === 0) return null;

  if (level === 'dan_thuong')
    return valid[Math.floor(Math.random() * valid.length)];

  // Score all, pick best (tie-break randomly)
  let best: MoveSpec = valid[0];
  let bestScore = -Infinity;
  for (const m of valid) {
    const sc = scoreMove(b, m, level, eHP, pHP);
    if (sc > bestScore) { bestScore = sc; best = m; }
  }
  return best;
}

// ── Focus / arrow / effect assets ────────────────────────────────────────────
const FOCUS_IMG  = require('../../assets/focusmovechess1.png');   // 29×29 khung xanh
const ARROW_IMG  = require('../../assets/arrowfocus1.png');        // 10×7  mũi tên xanh ▲
const AURA1_IMG  = require('../../assets/aura1.png');              // 45×48 vàng sparkle
const AURA2_IMG  = require('../../assets/aura2.png');              // 40×54 sword sparkle
const AURA3_IMG  = require('../../assets/aura3.png');              // 88×95 sword sparkle
const CRYS_BLUE  = require('../../assets/crystalblue.png');        // 45×15 → 3 frames 15px
const CRYS_RED   = require('../../assets/crystalred.png');         // 45×15 → 3 frames 15px

const CRYS_FRAMES = 3;
const CRYS_FRAME_W = 15;

// ════════════════════════════════════════════════════════════════════════════
// ArrowSet — 4 mũi tên pulsing outward/inward liên tục
// ════════════════════════════════════════════════════════════════════════════
const ArrowSet: React.FC<{ size: number }> = ({ size }) => {
  const pulse = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(pulse, { toValue: 1, duration: 400, easing: Easing.out(Easing.quad), useNativeDriver: true }),
        Animated.timing(pulse, { toValue: 0, duration: 400, easing: Easing.in(Easing.quad),  useNativeDriver: true }),
      ]),
    );
    loop.start();
    return () => loop.stop();
  }, [pulse]);

  const arrowW = Math.round(size * 0.34);
  const arrowH = Math.round(arrowW * 7 / 10);   // keep aspect 10:7
  const travel = Math.round(size * 0.14);         // max outward travel

  // pulse: 0→1→0  ⇒  translate 0→travel→0
  const outward = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, travel] });
  const inward  = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, -travel] });

  // Each arrow: positioned centered on its edge, translated outward
  return (
    <>
      {/* ▲ UP */}
      <Animated.Image source={ARROW_IMG} resizeMode="contain" style={{
        position: 'absolute',
        width: arrowW, height: arrowH,
        left: (size - arrowW) / 2, top: -arrowH + 1,
        transform: [{ translateY: inward }],
      }} />
      {/* ▼ DOWN */}
      <Animated.Image source={ARROW_IMG} resizeMode="contain" style={{
        position: 'absolute',
        width: arrowW, height: arrowH,
        left: (size - arrowW) / 2, bottom: -arrowH + 1,
        transform: [{ rotate: '180deg' }, { translateY: inward }],
      }} />
      {/* ◄ LEFT */}
      <Animated.Image source={ARROW_IMG} resizeMode="contain" style={{
        position: 'absolute',
        width: arrowH, height: arrowW,
        top: (size - arrowW) / 2, left: -arrowH + 1,
        transform: [{ translateX: inward }],
      }} />
      {/* ► RIGHT */}
      <Animated.Image source={ARROW_IMG} resizeMode="contain" style={{
        position: 'absolute',
        width: arrowH, height: arrowW,
        top: (size - arrowW) / 2, right: -arrowH + 1,
        transform: [{ translateX: outward }],
      }} />
    </>
  );
};

// ════════════════════════════════════════════════════════════════════════════
// GemCell
// ════════════════════════════════════════════════════════════════════════════
const GemCell = React.memo(({
  gemType, frameIndex, size, selected, onPress,
}: {
  gemType: GemType; frameIndex: number; size: number;
  selected: boolean; onPress: () => void;
}) => (
  <TouchableOpacity
    activeOpacity={0.8}
    onPress={onPress}
    style={[gst.base, { width: size, height: size }]}
  >
    {/* Gem sprite */}
    <View style={{ width: size, height: size, overflow: 'hidden' }}>
      <Image
        source={GEM_SHEETS[gemType]}
        style={{
          width:  size * TOTAL_FRAMES,
          height: size,
          transform: [{ translateX: -frameIndex * size }],
        }}
        resizeMode="stretch"
      />
    </View>

    {/* Selected overlay: focus frame + 4 pulsing arrows */}
    {selected && (
      <View style={gst.focusWrap}>
        <Image source={FOCUS_IMG} style={gst.focus} resizeMode="stretch" />
        <ArrowSet size={size} />
      </View>
    )}
  </TouchableOpacity>
));

const gst = StyleSheet.create({
  base: { borderRadius: 2 },
  focusWrap: {
    position: 'absolute', top: 0, left: 0, right: 0, bottom: 0,
    overflow: 'visible',    // arrows protrude beyond cell
  },
  focus: {
    position: 'absolute', top: 0, left: 0, right: 0, bottom: 0,
  },
});

// ════════════════════════════════════════════════════════════════════════════
// BattleScreen
// ════════════════════════════════════════════════════════════════════════════
interface Props {
  monsterType: MonsterType;
  onVictory:  () => void;
  onDefeat:   () => void;
  onFlee:     () => void;
}

export const BattleScreen: React.FC<Props> = ({
  monsterType, onVictory, onDefeat, onFlee,
}) => {
  const maxHP  = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP  = 100;
  const maxPow = 100;

  const [board,         setBoard]         = useState<Board>(makeBoard);
  const [selected,      setSelected]      = useState<[number, number] | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(maxHP);
  const [enemyHP,   setEnemyHP]   = useState(maxEHP);
  const [mana,      setMana]      = useState(30);
  const [power,     setPower]     = useState(40);
  const [log,       setLog]       = useState('Trận đấu bắt đầu!');
  const [combo,     setCombo]     = useState(0);
  const [phase,     setPhase]     = useState<'idle' | 'busy' | 'over'>('idle');
  const [result,    setResult]    = useState<'victory' | 'defeat' | null>(null);
  const [monFrame,  setMonFrame]  = useState<number>(WALK_FRAMES[0]);
  const [monAtk,    setMonAtk]   = useState(false);
  const [aiLevel,   setAiLevel]  = useState<AILevel | null>('linh_canh');

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<'player' | 'monster'>('player');
  const turnRef = useRef<'player' | 'monster'>('player');

  // ── Extra turns: match 4+ → bonus lượt ────────────────────────────────────
  const [extraTurns, setExtraTurns] = useState(0);
  const extraTurnsRef = useRef(0);
  const [bonusBanner, setBonusBanner] = useState<string | null>(null);
  const bonusBannerAnim = useRef(new Animated.Value(0)).current;

  // Match particle effects
  interface MatchFXItem { key: string; r: number; c: number; kind: FXKind; anim: Animated.Value }
  const [matchFX, setMatchFX] = useState<MatchFXItem[]>([]);
  const fxKeyRef = useRef(0);

  const shakeAnim  = useRef(new Animated.Value(0)).current;
  const phaseRef   = useRef<'idle' | 'busy' | 'over'>('idle');
  const mountedRef = useRef(true);
  const boardRef   = useRef<Board>(board);
  const enemyHPRef = useRef(enemyHP);
  const playerHPRef= useRef(playerHP);

  const offsets = useRef<Animated.Value[][]>(
    Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => new Animated.Value(0))
    )
  ).current;

  useEffect(() => { phaseRef.current = phase; }, [phase]);
  useEffect(() => () => { mountedRef.current = false; }, []);
  useEffect(() => { boardRef.current = board; }, [board]);
  useEffect(() => { enemyHPRef.current = enemyHP; }, [enemyHP]);
  useEffect(() => { playerHPRef.current = playerHP; }, [playerHP]);
  useEffect(() => { turnRef.current = turn; }, [turn]);
  useEffect(() => { extraTurnsRef.current = extraTurns; }, [extraTurns]);

  // ── Entry animation: gem rơi theo cột ─────────────────────────────────────
  useEffect(() => {
    const TOP = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++)
      for (let c = 0; c < BOARD_COLS; c++)
        offsets[r][c].setValue(TOP);

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++)
      for (let r = 0; r < BOARD_ROWS; r++)
        anims.push(Animated.timing(offsets[r][c], {
          toValue: 0, duration: 480,
          delay: c * 55,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }));
    Animated.parallel(anims).start();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // ── Monster animation ──────────────────────────────────────────────────────
  const monTick = useRef(0);
  useEffect(() => {
    const t = setInterval(() => {
      if (!mountedRef.current) return;
      monTick.current++;
      const frames = monAtk ? ATTACK_FRAMES : WALK_FRAMES;
      setMonFrame(frames[monTick.current % frames.length]);
    }, 200);
    return () => clearInterval(t);
  }, [monAtk]);

  // ── Enemy auto-attack REMOVED — monster now plays turn-based on the board ──

  // ── Spawn particle effects at matched positions ────────────────────────────
  const spawnFX = useCallback((matched: Set<string>, b: Board, expanded: Set<string>) => {
    const items: MatchFXItem[] = [];
    const hasSwordExpansion = expanded.size > matched.size;

    matched.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g === null) return;
      const kind = GEM_FX_KIND[g];
      // For non-sword gems, spawn individual particles
      if (kind !== 'sword') {
        const anim = new Animated.Value(0);
        items.push({ key: `fx-${++fxKeyRef.current}`, r, c, kind, anim });
      }
    });

    // Sword explosion: spawn aura2/aura3 at center of sword matches
    if (hasSwordExpansion) {
      // Find sword cells in original match
      const swordCells: Array<[number, number]> = [];
      matched.forEach(k => {
        const [r, c] = k.split(',').map(Number);
        const g = b[r][c];
        if (g !== null && GEM_CATEGORY[g] === SWORD_CAT) swordCells.push([r, c]);
      });
      swordCells.forEach(([r, c]) => {
        const anim = new Animated.Value(0);
        items.push({ key: `fx-${++fxKeyRef.current}`, r, c, kind: 'sword', anim });
      });
    }

    if (items.length === 0) return;
    setMatchFX(prev => [...prev, ...items]);

    // Animate: 0→1 over 500ms (scale up + float up + fade out)
    Animated.parallel(
      items.map(fx => Animated.timing(fx.anim, {
        toValue: 1, duration: 550, easing: Easing.out(Easing.quad), useNativeDriver: true,
      }))
    ).start(() => {
      if (!mountedRef.current) return;
      // Remove finished particles
      const keys = new Set(items.map(f => f.key));
      setMatchFX(prev => prev.filter(f => !keys.has(f.key)));
    });
  }, []);

  // ── Explosion animation ────────────────────────────────────────────────────
  const playExplosion = useCallback((
    matched: Set<string>, expanded: Set<string>, b: Board, onDone: () => void,
  ) => {
    // Spawn particle effects
    spawnFX(matched, b, expanded);

    let frame = EXPLODE_START;
    const apply = (f: number) => {
      const next: Record<string, number> = {};
      expanded.forEach(k => { next[k] = f; });
      setExplodeFrames(next);
    };
    apply(frame);
    const tick = setInterval(() => {
      frame++;
      if (frame > EXPLODE_END || !mountedRef.current) {
        clearInterval(tick); setExplodeFrames({}); onDone(); return;
      }
      apply(frame);
    }, 65);
  }, [spawnFX]);

  // ── Fall animation ─────────────────────────────────────────────────────────
  const animateFall = useCallback((
    newBoard: Board, fallMap: FallEntry[], onDone: () => void,
  ) => {
    if (fallMap.length === 0) { setBoard(newBoard); onDone(); return; }

    fallMap.forEach(({ r, c, srcRow }) => {
      offsets[r][c].setValue((srcRow - r) * GEM_SIZE);
    });
    setBoard(newBoard);

    const maxDist = Math.max(...fallMap.map(({ r, srcRow }) => Math.abs(srcRow - r)));
    Animated.parallel(
      fallMap.map(({ r, c, srcRow }) => Animated.timing(offsets[r][c], {
        toValue: 0,
        duration: 120 + Math.abs(srcRow - r) * 55,
        easing: Easing.in(Easing.quad),
        useNativeDriver: true,
      }))
    ).start(() => { if (mountedRef.current) onDone(); });
  }, [offsets]);

  // ── Board reset: khi không còn nước đi, tạo board mới rơi từ trên xuống ──
  const resetBoardAnim = useCallback((onDone: () => void) => {
    const nb = makeBoard();
    boardRef.current = nb;
    setBoard(nb);

    // Tất cả gem bắt đầu ở trên cao rồi rơi xuống (giống entry animation)
    const TOP = -(BOARD_ROWS + 2) * GEM_SIZE;
    for (let r = 0; r < BOARD_ROWS; r++)
      for (let c = 0; c < BOARD_COLS; c++)
        offsets[r][c].setValue(TOP);

    const anims: Animated.CompositeAnimation[] = [];
    for (let c = 0; c < BOARD_COLS; c++)
      for (let r = 0; r < BOARD_ROWS; r++)
        anims.push(Animated.timing(offsets[r][c], {
          toValue: 0, duration: 480,
          delay: c * 55,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }));

    Animated.parallel(anims).start(() => {
      if (mountedRef.current) onDone();
    });
  }, [offsets]);

  // ── Bonus turn banner flash ─────────────────────────────────────────────────
  const showBonusBanner = useCallback((msg: string) => {
    setBonusBanner(msg);
    bonusBannerAnim.setValue(0);
    Animated.sequence([
      Animated.timing(bonusBannerAnim, { toValue: 1, duration: 250, useNativeDriver: true }),
      Animated.delay(800),
      Animated.timing(bonusBannerAnim, { toValue: 0, duration: 300, useNativeDriver: true }),
    ]).start(() => { if (mountedRef.current) setBonusBanner(null); });
  }, [bonusBannerAnim]);

  // ── Chain processor ────────────────────────────────────────────────────────
  const processMatches = useCallback((b: Board, chain: number) => {
    if (!mountedRef.current) return;
    const raw = findMatches(b);
    if (raw.size === 0) {
      setBoard(b); setCombo(0);

      // ── Check deadlock: không còn nước đi → reset board ──
      if (getAllValidMoves(b).length === 0) {
        setLog('🔀 Hết nước đi! Xáo trộn bàn cờ...');
        showBonusBanner('🔀 Hết nước! Bàn cờ mới!');
        resetBoardAnim(() => {
          if (!mountedRef.current) return;
          // Sau khi reset, giữ nguyên turn + extraTurns, quay lại idle
          setPhase('idle'); phaseRef.current = 'idle';
        });
        return;
      }

      // ── Turn switch: check extraTurns before switching ──
      if (extraTurnsRef.current > 0) {
        // Còn lượt bonus → giữ nguyên turn, trừ 1 lượt
        const remaining = extraTurnsRef.current - 1;
        extraTurnsRef.current = remaining;
        setExtraTurns(remaining);
        const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
        showBonusBanner(`🔄 ${who} được thêm lượt! ${remaining > 0 ? `Còn ${remaining} lượt` : ''}`);
        setLog(`🔄 Thêm lượt! ${remaining > 0 ? `(còn ${remaining} lượt nữa)` : '(lượt cuối)'}`);
        setPhase('idle'); phaseRef.current = 'idle';
      } else {
        // Hết lượt bonus → chuyển turn
        const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
        turnRef.current = nextTurn;
        setTurn(nextTurn);
        setPhase('idle'); phaseRef.current = 'idle';
      }
      return;
    }

    // ── Detect 4+ match cùng màu → +1 bonus turn (flat, mỗi bước chain) ──
    if (hasBonusTurn(raw, b)) {
      const newExtra = extraTurnsRef.current + 1;
      extraTurnsRef.current = newExtra;
      setExtraTurns(newExtra);
      const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
      showBonusBanner(`✨ ${who} +1 lượt!${newExtra > 1 ? ` (tổng ${newExtra})` : ''}`);
    }

    const matched = expandSword(raw, b);
    if (matched.size > 4 || chain > 0) {
      Animated.sequence([
        Animated.timing(shakeAnim, { toValue:  7, duration: 35, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue: -7, duration: 35, useNativeDriver: true }),
        Animated.timing(shakeAnim, { toValue:  0, duration: 35, useNativeDriver: true }),
      ]).start();
    }

    let dmg = 0, heal = 0, mp = 0, pow = 0;
    const counts: Partial<Record<GemType, number>> = {};
    raw.forEach(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      if (g !== null) counts[g] = (counts[g] ?? 0) + 1;
    });
    Object.entries(counts).forEach(([gs, cnt]) => {
      const g = Number(gs) as GemType;
      const fx = GEM_FX[g]; const mul = 1 + chain * 0.4;
      dmg  += Math.round(fx.dmg  * (cnt! / 3) * mul);
      heal += Math.round(fx.heal * (cnt! / 3) * mul);
      mp   += Math.round(fx.mana * cnt!);
      pow  += Math.round(fx.pow  * cnt!);
    });
    const hasSword = [...raw].some(k => {
      const [r, c] = k.split(',').map(Number);
      const g = b[r][c];
      return g !== null && GEM_CATEGORY[g] === SWORD_CAT;
    });

    playExplosion(raw, matched, b, () => {
      if (!mountedRef.current) return;
      const { newBoard, fallMap } = collapseLogic(b, matched);

      // ── Turn-based effects: player turn → hurt monster, monster turn → hurt player ──
      if (turnRef.current === 'player') {
        // Player's turn: damage to enemy, heal/mp/pow to player
        setEnemyHP(hp => {
          const next = Math.max(0, hp - dmg);
          if (next === 0 && phaseRef.current !== 'over') {
            phaseRef.current = 'over'; setPhase('over'); setResult('victory');
          }
          return next;
        });
        if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
        if (mp   > 0) setMana(m       => Math.min(maxMP, m + mp));
        if (pow  > 0) setPower(p      => Math.min(maxPow, p + pow));
      } else {
        // Monster's turn: damage to player, heal to monster
        if (dmg > 0) {
          setMonAtk(true);
          setTimeout(() => mountedRef.current && setMonAtk(false), 600);
          setPlayerHP(hp => {
            const next = Math.max(0, hp - dmg);
            if (next === 0 && phaseRef.current !== 'over') {
              phaseRef.current = 'over'; setPhase('over'); setResult('defeat');
            }
            return next;
          });
        }
        if (heal > 0) setEnemyHP(hp => Math.min(maxEHP, hp + heal));
        // Monster doesn't gain player's mana/power
      }

      const isMonTurn = turnRef.current === 'monster';
      const parts: string[] = [];
      if (hasSword) parts.push('💥 Kiếm nổ 3×3!');
      if (dmg > 0)  parts.push(isMonTurn ? `🐉 Quái đánh -${dmg} HP` : `⚔ -${dmg}`);
      if (heal > 0) parts.push(isMonTurn ? `🐉 Quái hồi +${heal} HP` : `❤ +${heal}`);
      setLog((chain > 0 ? `COMBO ×${chain + 1}!  ` : '') + parts.join('  '));
      setCombo(chain + 1);

      animateFall(newBoard, fallMap, () => {
        setTimeout(() => processMatches(newBoard, chain + 1), 80);
      });
    });
  }, [playExplosion, animateFall, resetBoardAnim, shakeAnim, showBonusBanner, maxHP, maxEHP, maxMP, maxPow]);

  // ── Direct swap (dùng cho cả AI và human) ─────────────────────────────────
  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    const b = boardRef.current;
    const nb: Board = b.map(r => [...r]);
    [nb[r1][c1], nb[r2][c2]] = [nb[r2][c2], nb[r1][c1]];
    if (findMatches(nb).size === 0) {
      // Bờm có thể fail — nếu là monster turn, vẫn switch sang player
      if (turnRef.current === 'monster') {
        turnRef.current = 'player'; setTurn('player');
        setLog('🐉 Quái đánh trượt!');
      }
      return;
    }
    setPhase('busy'); phaseRef.current = 'busy';
    setBoard(nb); processMatches(nb, 0);
  }, [processMatches]);

  const doDirectSwapRef = useRef(doDirectSwap);
  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);

  // ── Monster AI loop: monster plays on the board during its turn ────────────
  // Dùng state aiStep để hiển thị monster đang "click" trên bàn cờ
  // step 0: thinking... → step 1: click gem1 (focus+arrows) → step 2: click gem2 → swap
  const [aiStep, setAiStep] = useState<'think' | 'pick1' | 'pick2' | null>(null);
  const aiTimers = useRef<ReturnType<typeof setTimeout>[]>([]);

  // Cleanup AI timers on unmount
  useEffect(() => () => { aiTimers.current.forEach(clearTimeout); }, []);

  useEffect(() => {
    // Monster AI triggers only when it's monster's turn, phase is idle, and AI level is set
    if (phase !== 'idle' || turn !== 'monster' || aiLevel === null || result !== null) return;
    const cfg = AI_CONFIGS[aiLevel];

    // Clear old timers
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];

    // Step 0: Monster "thinking" — chờ thinkMs
    setAiStep('think');
    setSelected(null);
    setLog(`🐉 Lượt quái vật — ${cfg.emoji} ${cfg.name} đang suy nghĩ...`);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') return;
      const b  = boardRef.current;
      // Monster AI: eHP & pHP swapped — monster wants to maximize its own benefit
      const mv = pickAIMove(b, aiLevel, playerHPRef.current, enemyHPRef.current);
      if (!mv) {
        // No valid moves — skip monster turn
        setAiStep(null);
        turnRef.current = 'player'; setTurn('player');
        setLog('🐉 Quái không tìm được nước đi!');
        return;
      }

      // Step 1: Monster "click" gem1 — hiện focus + mũi tên trên gem1
      setAiStep('pick1');
      setSelected([mv.r1, mv.c1]);

      const t2 = setTimeout(() => {
        if (!mountedRef.current) return;
        // Step 2: Monster "click" gem2 — focus chuyển sang gem2
        setAiStep('pick2');
        setSelected([mv.r2, mv.c2]);

        const t3 = setTimeout(() => {
          if (!mountedRef.current) return;
          // Execute swap
          setAiStep(null);
          setSelected(null);
          doDirectSwapRef.current(mv.r1, mv.c1, mv.r2, mv.c2);
        }, 350);
        aiTimers.current.push(t3);
      }, 400);
      aiTimers.current.push(t2);
    }, cfg.thinkMs);
    aiTimers.current.push(t1);

    return () => { aiTimers.current.forEach(clearTimeout); aiTimers.current = []; };
  }, [phase, turn, aiLevel, result]);

  // ── Human tap ─────────────────────────────────────────────────────────────
  const handleGemPress = useCallback((row: number, col: number) => {
    if (phase !== 'idle' || turn !== 'player') return; // Chỉ cho tap khi lượt player
    if (!selected) { setSelected([row, col]); return; }
    const [sr, sc] = selected;
    if (sr === row && sc === col) { setSelected(null); return; }
    const adj = (Math.abs(sr - row) === 1 && sc === col) ||
                (sr === row && Math.abs(sc - col) === 1);
    if (!adj) { setSelected([row, col]); return; }
    setSelected(null);
    doDirectSwapRef.current(sr, sc, row, col);
  }, [selected, phase, turn]);

  // ── Skill ──────────────────────────────────────────────────────────────────
  const handleSkill = useCallback(() => {
    if (mana < 30 || phase !== 'idle' || turn !== 'player') return;
    setMana(m => m - 30);
    const dmg = 40 + Math.floor(Math.random() * 20);
    setEnemyHP(hp => {
      const next = Math.max(0, hp - dmg);
      if (next === 0 && phaseRef.current !== 'over') {
        phaseRef.current = 'over'; setPhase('over'); setResult('victory');
      }
      return next;
    });
    setLog(`💫 Kỹ năng! -${dmg} HP quái!`);
    // Skill also ends player turn → switch to monster
    turnRef.current = 'monster'; setTurn('monster');
  }, [mana, phase, turn]);

  // ── Layout ─────────────────────────────────────────────────────────────────
  const BG_LEFT    = Math.round((SCREEN_W - BG_W) / 2);
  const HUD_LEFT_X = BG_LEFT + 12 * BOARD_SCALE;
  const HUD_MID_X  = BG_LEFT + BG_W / 2 + 2 * BOARD_SCALE;
  const HUD_BASE_Y = 245 * BOARD_SCALE;
  const HUD_GAP_Y  = 12 * BOARD_SCALE;
  const HUD_W      = 90 * BOARD_SCALE;
  const HUD_H      = 7  * BOARD_SCALE;
  const LOG_TOP    = BOARD_POS_TOP + GEM_SIZE * BOARD_ROWS + 4;
  const CHARS_TOP  = SCREEN_H - 178;
  const GND_TOP    = CHARS_TOP + 80;
  const BTN_TOP    = SCREEN_H - 70;
  const AI_ROW_TOP = BTN_TOP + 46;
  const AI_LBL_TOP = AI_ROW_TOP + 38;
  const { w: mW, h: mH } = monsterDisplaySize(monsterType);

  return (
    <View style={s.root}>

      {/* ── Background ───────────────────────────────────────── */}
      <View style={{ position: 'absolute', top: 0, left: BG_LEFT, width: BG_W, height: BG_H }}>
        <Image
          source={require('../../assets/play/bkboardv.png')}
          style={{ width: '100%', height: '100%' }}
          resizeMode="stretch"
        />
      </View>

      {/* ── Player bars ──────────────────────────────────────── */}
      <View style={{
        position: 'absolute', top: HUD_BASE_Y,
        left: HUD_LEFT_X, width: HUD_W, gap: HUD_GAP_Y - HUD_H, zIndex: 20,
      }}>
        <TBar asset={require('../../assets/play/hpbar.png')}    fill={playerHP / maxHP}  w={HUD_W} h={HUD_H} />
        <TBar asset={require('../../assets/play/manabar.png')}  fill={mana / maxMP}      w={HUD_W} h={HUD_H} />
        <TBar asset={require('../../assets/play/powerbar.png')} fill={power / maxPow}    w={HUD_W} h={HUD_H} />
      </View>

      {/* ── Enemy bars ───────────────────────────────────────── */}
      <View style={{
        position: 'absolute', top: HUD_BASE_Y,
        left: HUD_MID_X, width: HUD_W, gap: HUD_GAP_Y - HUD_H, zIndex: 20,
      }}>
        <TBar asset={require('../../assets/play/hpbar.png')}    fill={enemyHP / maxEHP} w={HUD_W} h={HUD_H} />
        <TBar asset={require('../../assets/play/manabar.png')}  fill={0}                w={HUD_W} h={HUD_H} />
        <TBar asset={require('../../assets/play/powerbar.png')} fill={0}                w={HUD_W} h={HUD_H} />
      </View>

      {/* ── Gem board ────────────────────────────────────────── */}
      <Animated.View style={[s.gemBoard, {
        top:    BOARD_POS_TOP,
        left:   BOARD_POS_LEFT,
        width:  GEM_SIZE * BOARD_COLS,
        height: GEM_SIZE * BOARD_ROWS,
        transform: [{ translateX: shakeAnim }],
      }]}>
        {board.map((row, r) =>
          row.map((gemType, c) =>
            gemType !== null ? (
              <Animated.View
                key={`${r}-${c}`}
                style={{
                  position: 'absolute',
                  left: c * GEM_SIZE, top: r * GEM_SIZE,
                  width: GEM_SIZE,    height: GEM_SIZE,
                  transform: [{ translateY: offsets[r][c] }],
                }}
              >
                <GemCell
                  gemType={gemType}
                  frameIndex={explodeFrames[`${r},${c}`] ?? 0}
                  size={GEM_SIZE}
                  selected={selected?.[0] === r && selected?.[1] === c}
                  onPress={() => handleGemPress(r, c)}
                />
              </Animated.View>
            ) : null
          )
        )}

        {/* ── Match particle effects overlay ──────────────────── */}
        {matchFX.map(fx => {
          const fxSize = fx.kind === 'sword' ? GEM_SIZE * 2.5 : GEM_SIZE * 1.4;
          const opacity = fx.anim.interpolate({
            inputRange: [0, 0.3, 1], outputRange: [0, 1, 0],
          });
          const scale = fx.anim.interpolate({
            inputRange: [0, 0.4, 1],
            outputRange: [0.3, 1.1, fx.kind === 'sword' ? 1.6 : 0.7],
          });
          const translateY = fx.anim.interpolate({
            inputRange: [0, 1],
            outputRange: [0, fx.kind === 'sword' ? -GEM_SIZE * 0.4 : -GEM_SIZE * 0.8],
          });
          const cx = fx.c * GEM_SIZE + GEM_SIZE / 2 - fxSize / 2;
          const cy = fx.r * GEM_SIZE + GEM_SIZE / 2 - fxSize / 2;

          let source: any;
          switch (fx.kind) {
            case 'gold':        source = AURA1_IMG;  break;
            case 'mp':          source = CRYS_BLUE;  break;
            case 'crystal_red': source = CRYS_RED;   break;
            case 'sword':       source = Math.random() > 0.5 ? AURA2_IMG : AURA3_IMG; break;
          }

          return (
            <Animated.Image
              key={fx.key}
              source={source}
              resizeMode="contain"
              style={{
                position: 'absolute',
                left: cx, top: cy,
                width: fxSize, height: fxSize,
                opacity,
                transform: [{ scale }, { translateY }],
              }}
            />
          );
        })}
      </Animated.View>

      {/* ── Extra turns badge (persistent, on board center) ──── */}
      {extraTurns > 0 && (
        <View style={{
          position: 'absolute',
          top: BOARD_POS_TOP + GEM_SIZE * BOARD_ROWS / 2 - 14,
          left: BOARD_POS_LEFT + GEM_SIZE * BOARD_COLS / 2 - 55,
          width: 110, height: 28,
          backgroundColor: turn === 'player' ? 'rgba(33,150,243,0.88)' : 'rgba(255,87,34,0.88)',
          borderRadius: 14,
          justifyContent: 'center', alignItems: 'center',
          zIndex: 50,
          borderWidth: 1.5, borderColor: '#fff',
        }}>
          <Text style={{ color: '#fff', fontSize: 12, fontWeight: 'bold' }}>
            🔄 Còn {extraTurns} lượt
          </Text>
        </View>
      )}

      {/* ── Bonus turn flash banner (animated) ───────────────── */}
      {bonusBanner !== null && (
        <Animated.View style={{
          position: 'absolute',
          top: BOARD_POS_TOP + GEM_SIZE * BOARD_ROWS / 2 - 24,
          left: BOARD_POS_LEFT - 10,
          width: GEM_SIZE * BOARD_COLS + 20,
          height: 48,
          backgroundColor: 'rgba(255,215,0,0.92)',
          borderRadius: 8,
          justifyContent: 'center', alignItems: 'center',
          zIndex: 60,
          opacity: bonusBannerAnim,
          transform: [{
            scale: bonusBannerAnim.interpolate({
              inputRange: [0, 0.5, 1],
              outputRange: [0.6, 1.1, 1],
            }),
          }],
        }}>
          <Text style={{ color: '#333', fontSize: 14, fontWeight: 'bold', textAlign: 'center' }}>
            {bonusBanner}
          </Text>
        </Animated.View>
      )}

      {/* ── Log ──────────────────────────────────────────────── */}
      <View style={[s.logBar, { top: LOG_TOP, width: BG_W - 30 }]}>
        <Text style={s.logTxt} numberOfLines={2}>{log}</Text>
        {combo > 1 && <Text style={s.comboTxt}>×{combo}</Text>}
      </View>

      {/* ── Ground ───────────────────────────────────────────── */}
      <Image
        source={require('../../assets/play/ground.png')}
        style={[s.ground, { top: GND_TOP }]}
        resizeMode="repeat"
      />

      {/* ── Characters ───────────────────────────────────────── */}
      <View style={[s.charsRow, { top: CHARS_TOP }]}>
        <Image source={require('../../assets/character/Full.png')}
          style={s.playerSprite} resizeMode="contain" />
        <View style={{ flex: 1 }} />
        <View style={{ width: mW, height: mH, alignSelf: 'flex-end' }}>
          <MonsterSprite type={monsterType} frameIndex={monFrame} facingRight={false} />
        </View>
      </View>

      {/* ── Action buttons ───────────────────────────────────── */}
      <View style={[s.btnRow, { top: BTN_TOP }]}>
        <TouchableOpacity style={s.btnFlee} onPress={onFlee}>
          <Text style={s.btnTxt}>🏃 Tháo Chạy</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[s.btnSkill, mana < 30 && s.btnOff]}
          onPress={handleSkill}
          disabled={mana < 30 || phase !== 'idle' || turn !== 'player'}
        >
          <Text style={s.btnTxt}>💫 Kỹ Năng (30 MP)</Text>
        </TouchableOpacity>
      </View>

      {/* ── AI level selector ────────────────────────────────── */}
      <View style={[s.aiRow, { top: AI_ROW_TOP }]}>
        {/* Monster difficulty label */}
        <Text style={{ color: '#aaa', fontSize: 10, marginRight: 4 }}>Quái:</Text>

        {/* Monster AI level chips */}
        {AI_ORDER.map(lv => (
          <TouchableOpacity
            key={lv}
            style={[s.aiChip, aiLevel === lv && s.aiChipActive]}
            onPress={() => setAiLevel(lv)}
          >
            <Text style={s.aiChipTxt}>{AI_CONFIGS[lv].emoji}</Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Turn indicator + AI info label */}
      <View style={[s.aiLabelRow, { top: AI_LBL_TOP }]}>
        <Text style={[s.aiLabelTxt, {
          color: turn === 'player' ? '#4fc3f7' : '#ff8a65',
          fontWeight: 'bold',
        }]}>
          {turn === 'player' ? '⚔ LƯỢT CỦA BẠN' : '🐉 LƯỢT QUÁI VẬT'}
          {turn === 'monster' && aiLevel !== null && (
            aiStep === 'think' ? ` — ${AI_CONFIGS[aiLevel].emoji} đang suy nghĩ...` :
            aiStep === 'pick1' ? ` — ${AI_CONFIGS[aiLevel].emoji} chọn viên 1...` :
            aiStep === 'pick2' ? ` — ${AI_CONFIGS[aiLevel].emoji} di chuyển...` :
            ` — ${AI_CONFIGS[aiLevel].emoji} ${AI_CONFIGS[aiLevel].name}`
          )}
          {aiLevel === null && turn === 'monster' ? ' — chọn độ khó quái!' : ''}
        </Text>
      </View>

      {/* ── Overlay ──────────────────────────────────────────── */}
      {result !== null && (
        <View style={s.overlay}>
          <Text style={s.overlayTitle}>
            {result === 'victory' ? '🏆 CHIẾN THẮNG!' : '💀 THẤT BẠI!'}
          </Text>
          <Text style={s.overlaySub}>
            {result === 'victory' ? 'Quái vật đã bị tiêu diệt!' : 'Nhân vật đã ngã xuống...'}
          </Text>
          <TouchableOpacity
            style={s.overlayBtn}
            onPress={result === 'victory' ? onVictory : onDefeat}
          >
            <Text style={s.overlayBtnTxt}>
              {result === 'victory' ? '▶ Tiếp tục' : '↺ Thử lại'}
            </Text>
          </TouchableOpacity>
        </View>
      )}
    </View>
  );
};

// ── Texture bar ───────────────────────────────────────────────────────────────
const TBar: React.FC<{ asset: any; fill: number; w: number; h: number }> = ({
  asset, fill, w, h,
}) => (
  <View style={{
    width: w, height: h,
    backgroundColor: 'rgba(0,0,0,0.5)', overflow: 'hidden', borderRadius: 1,
  }}>
    <Image
      source={asset}
      style={{
        width: w, height: h,
        transform: [{ translateX: -w * (1 - Math.max(0, Math.min(1, fill))) }],
      }}
      resizeMode="stretch"
    />
  </View>
);
