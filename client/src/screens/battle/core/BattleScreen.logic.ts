import { BOARD_COLS, BOARD_ROWS } from './BattleScreen.styles';
import {
  AILevel,
  Board,
  FallEntry,
  GEM_TYPES,
  GemType,
  MoveSpec,
  RED_SWORD_GEM,
  SWORD_DAMAGE,
  WHITE_SWORD_GEM,
  getGemCategory,
  getGemFX,
  getGemRenderType,
  getGemMatchMask,
  getGemStateClass,
  isRedSwordGem,
} from './BattleScreen.shared';

type BattleCell = [number, number];
type PackedSpan = number;

export interface JavaBoardEngine {
  seed: number;
  state: number;
  refillQueue: GemType[];
  refillCursor: number;
}

export interface JavaSwapMeta {
  a: number;
  b: number;
  c: number;
  d: number;
  e: [PackedSpan, PackedSpan, PackedSpan, PackedSpan];
}

export interface CollapseResult {
  newBoard: Board;
  fallMap: FallEntry[];
  affectedKeys: string[];
}

interface JavaAxisLine {
  gem: GemType;
  category: number;
  row: number;
  startCol: number;
  hLen: number;
  startRow: number;
  col: number;
  vLen: number;
  spawnRow: number;
  spawnCol: number;
}

export interface JavaBoardResolveResult {
  triggerKeys: Set<string>;
  clearedKeys: Set<string>;
  boardAfterClear: Board;
  spawnedSpecials: Array<{ r: number; c: number; gem: GemType }>;
  bonusTurnCandidate: boolean;
}

const REFILL_BATCH_SIZE = 96;
const randomSeed = () => ((Date.now() ^ Math.floor(Math.random() * 0x100000000)) >>> 0);
const MATCH_LEN_MASK = 0xff;
const MATCH_NEG_SHIFT = 16;
const MATCH_POS_SHIFT = 8;
const TYPE2_SPECIAL_BY_BASE: readonly GemType[] = [10, 11, 12, 13, 14, 15];
const TYPE4_SPECIAL_BY_BASE: readonly GemType[] = [20, 21, 22, 23, 24, 25];
const TYPE2_OFFSETS: readonly BattleCell[] = [
  [-1, -1],
  [-1, 0],
  [-1, 1],
  [0, 1],
  [1, 1],
  [1, 0],
  [1, -1],
  [0, -1],
] as const;

const keyOf = (r: number, c: number) => `${r},${c}`;

const inBounds = (r: number, c: number) =>
  r >= 0 && r < BOARD_ROWS && c >= 0 && c < BOARD_COLS;

const unpackNeg = (packed: PackedSpan) => (packed >> MATCH_NEG_SHIFT) & 0xff;
const unpackPos = (packed: PackedSpan) => (packed >> MATCH_POS_SHIFT) & 0xff;
const unpackLen = (packed: PackedSpan) => packed & MATCH_LEN_MASK;

const cloneBoard = (board: Board): Board => board.map(row => [...row]);

const applySwap = (board: Board, r1: number, c1: number, r2: number, c2: number): Board => {
  const nextBoard = cloneBoard(board);
  [nextBoard[r1][c1], nextBoard[r2][c2]] = [nextBoard[r2][c2], nextBoard[r1][c1]];
  return nextBoard;
};

const lcgNext = (engine: JavaBoardEngine) => {
  engine.state = (Math.imul(engine.state, 1664525) + 1013904223) >>> 0;
  let x = engine.state || 0xa341316c;
  x ^= x << 13;
  x ^= x >>> 17;
  x ^= x << 5;
  engine.state = x >>> 0;
  return engine.state;
};

const randomIndex = (engine: JavaBoardEngine, length: number) =>
  length <= 1 ? 0 : Math.floor((lcgNext(engine) / 0x100000000) * length);

const ensureRefillQueue = (engine: JavaBoardEngine) => {
  if (engine.refillCursor < engine.refillQueue.length) return;
  const nextBatch: GemType[] = [];
  for (let i = 0; i < REFILL_BATCH_SIZE; i++) {
    nextBatch.push(GEM_TYPES[randomIndex(engine, GEM_TYPES.length)] ?? GEM_TYPES[0]);
  }
  engine.refillQueue = nextBatch;
  engine.refillCursor = 0;
};

const nextRefillGem = (engine: JavaBoardEngine): GemType => {
  ensureRefillQueue(engine);
  const gem = engine.refillQueue[engine.refillCursor] ?? GEM_TYPES[0];
  engine.refillCursor += 1;
  return gem;
};

const pickInitialGem = (
  engine: JavaBoardEngine,
  board: Board,
  r: number,
  c: number,
  forbiddenCategories: Set<number>,
): GemType => {
  const pool = GEM_TYPES.filter(gem => !forbiddenCategories.has(getGemCategory(gem)));
  const source = pool.length > 0 ? pool : GEM_TYPES;

  let bestScore = Infinity;
  let best: GemType[] = [];

  for (const gem of source) {
    const cat = getGemCategory(gem);
    let score = 0;

    const left = c > 0 ? board[r][c - 1] : null;
    const left2 = c > 1 ? board[r][c - 2] : null;
    const up = r > 0 ? board[r - 1][c] : null;
    const up2 = r > 1 ? board[r - 2][c] : null;
    const upLeft = r > 0 && c > 0 ? board[r - 1][c - 1] : null;
    const upRight = r > 0 && c + 1 < BOARD_COLS ? board[r - 1][c + 1] : null;

    if (left !== null) {
      if (getGemCategory(left) === cat) score += 8;
      if (left === gem) score += 4;
    }
    if (up !== null) {
      if (getGemCategory(up) === cat) score += 8;
      if (up === gem) score += 4;
    }
    if (left2 !== null && left !== null && getGemCategory(left2) === cat && getGemCategory(left) !== cat) score += 3;
    if (up2 !== null && up !== null && getGemCategory(up2) === cat && getGemCategory(up) !== cat) score += 3;
    if (upLeft !== null && getGemCategory(upLeft) === cat) score += 2;
    if (upRight !== null && getGemCategory(upRight) === cat) score += 2;

    // Break stripe-looking boards by penalizing repeated local motifs.
    if (
      left !== null &&
      up !== null &&
      getGemCategory(left) === cat &&
      getGemCategory(up) === cat
    ) {
      score += 6;
    }
    if (
      upLeft !== null &&
      left !== null &&
      up !== null &&
      getGemCategory(upLeft) === cat &&
      getGemCategory(left) === cat &&
      getGemCategory(up) === cat
    ) {
      score += 10;
    }

    score += randomIndex(engine, 3);

    if (score < bestScore) {
      bestScore = score;
      best = [gem];
    } else if (score === bestScore) {
      best.push(gem);
    }
  }

  return best[randomIndex(engine, best.length)] ?? source[randomIndex(engine, source.length)] ?? source[0];
};

const pickInitialGemSimple = (
  engine: JavaBoardEngine,
  forbiddenCategories: Set<number>,
): GemType => {
  const pool = GEM_TYPES.filter(gem => !forbiddenCategories.has(getGemCategory(gem)));
  const source = pool.length > 0 ? pool : GEM_TYPES;
  return source[randomIndex(engine, source.length)] ?? source[0];
};

const buildInitialBoard = (
  engine: JavaBoardEngine,
  preferVariety: boolean,
): Board => {
  const board: Board = Array.from({ length: BOARD_ROWS }, () =>
    Array.from({ length: BOARD_COLS }, () => null),
  );

  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      const forbidden = new Set<number>();
      if (c >= 2) {
        const g1 = board[r][c - 1];
        const g2 = board[r][c - 2];
        if (g1 !== null && g2 !== null && getGemCategory(g1) === getGemCategory(g2)) {
          forbidden.add(getGemCategory(g1));
        }
      }
      if (r >= 2) {
        const g1 = board[r - 1][c];
        const g2 = board[r - 2][c];
        if (g1 !== null && g2 !== null && getGemCategory(g1) === getGemCategory(g2)) {
          forbidden.add(getGemCategory(g1));
        }
      }

      board[r][c] = preferVariety
        ? pickInitialGem(engine, board, r, c, forbidden)
        : pickInitialGemSimple(engine, forbidden);
    }
  }

  return board;
};

const getSpanHorizontal = (board: Board, r: number, c: number): PackedSpan => {
  const gem = board[r]?.[c];
  if (gem === null || gem === undefined) return 0;

  let len = 1;
  let left = 0;
  let right = 0;
  const mask = getGemMatchMask(gem);

  while (c - (left + 1) >= 0) {
    const nextGem = board[r][c - (left + 1)];
    if (nextGem === null || (mask & getGemMatchMask(nextGem)) === 0) break;
    left += 1;
    len += 1;
  }

  while (c + (right + 1) < BOARD_COLS) {
    const nextGem = board[r][c + (right + 1)];
    if (nextGem === null || (mask & getGemMatchMask(nextGem)) === 0) break;
    right += 1;
    len += 1;
  }

  return ((left & 0xff) << MATCH_NEG_SHIFT) | ((right & 0xff) << MATCH_POS_SHIFT) | (len & MATCH_LEN_MASK);
};

const getSpanVertical = (board: Board, r: number, c: number): PackedSpan => {
  const gem = board[r]?.[c];
  if (gem === null || gem === undefined) return 0;

  let len = 1;
  let up = 0;
  let down = 0;
  const mask = getGemMatchMask(gem);

  while (r - (up + 1) >= 0) {
    const nextGem = board[r - (up + 1)][c];
    if (nextGem === null || (mask & getGemMatchMask(nextGem)) === 0) break;
    up += 1;
    len += 1;
  }

  while (r + (down + 1) < BOARD_ROWS) {
    const nextGem = board[r + (down + 1)][c];
    if (nextGem === null || (mask & getGemMatchMask(nextGem)) === 0) break;
    down += 1;
    len += 1;
  }

  return ((up & 0xff) << MATCH_NEG_SHIFT) | ((down & 0xff) << MATCH_POS_SHIFT) | (len & MATCH_LEN_MASK);
};

const appendSpanKeys = (
  out: Set<string>,
  r: number,
  c: number,
  horizontal: PackedSpan,
  vertical: PackedSpan,
) => {
  if (unpackLen(horizontal) >= 3) {
    const left = unpackNeg(horizontal);
    const right = unpackPos(horizontal);
    for (let nc = c - left; nc <= c + right; nc++) out.add(keyOf(r, nc));
  }

  if (unpackLen(vertical) >= 3) {
    const up = unpackNeg(vertical);
    const down = unpackPos(vertical);
    for (let nr = r - up; nr <= r + down; nr++) out.add(keyOf(nr, c));
  }
};

const collectMatchesFromCells = (board: Board, cells: Iterable<BattleCell>): Set<string> => {
  const matches = new Set<string>();
  const seen = new Set<string>();

  for (const [r, c] of cells) {
    if (!inBounds(r, c)) continue;
    const key = keyOf(r, c);
    if (seen.has(key)) continue;
    seen.add(key);

    const horizontal = getSpanHorizontal(board, r, c);
    const vertical = getSpanVertical(board, r, c);
    appendSpanKeys(matches, r, c, horizontal, vertical);
  }

  return matches;
};

const allBoardCells = function* (): Generator<BattleCell> {
  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      yield [r, c];
    }
  }
};

const scanTargetsToCells = (targets: Iterable<string | BattleCell>): BattleCell[] => {
  const cells: BattleCell[] = [];
  for (const target of targets) {
    if (Array.isArray(target)) {
      cells.push(target);
      continue;
    }
    const [r, c] = target.split(',').map(Number);
    if (Number.isFinite(r) && Number.isFinite(c)) cells.push([r, c]);
  }
  return cells;
};

const addHorizontalKeys = (out: Set<string>, row: number, startCol: number, len: number) => {
  for (let c = startCol; c < startCol + len; c++) out.add(keyOf(row, c));
};

const addVerticalKeys = (out: Set<string>, startRow: number, col: number, len: number) => {
  for (let r = startRow; r < startRow + len; r++) out.add(keyOf(r, col));
};

const makeHorizontalLine = (board: Board, r: number, c: number, span: PackedSpan): JavaAxisLine | null => {
  const len = unpackLen(span);
  if (len < 3) return null;

  const gem = board[r]?.[c];
  if (gem === null || gem === undefined) return null;

  const left = unpackNeg(span);
  const startCol = c - left;
  return {
    gem,
    category: getGemCategory(gem),
    row: r,
    startCol,
    hLen: len,
    startRow: r,
    col: c,
    vLen: 1,
    spawnRow: r,
    spawnCol: startCol + ((len - 1) >> 1),
  };
};

const makeVerticalLine = (board: Board, r: number, c: number, span: PackedSpan): JavaAxisLine | null => {
  const len = unpackLen(span);
  if (len < 3) return null;

  const gem = board[r]?.[c];
  if (gem === null || gem === undefined) return null;

  const up = unpackNeg(span);
  const startRow = r - up;
  return {
    gem,
    category: getGemCategory(gem),
    row: r,
    startCol: c,
    hLen: 1,
    startRow,
    col: c,
    vLen: len,
    spawnRow: startRow + ((len - 1) >> 1),
    spawnCol: c,
  };
};

const collectJavaAxisLines = (
  board: Board,
  scanTargets?: Iterable<string | BattleCell>,
): { horizontal: JavaAxisLine[]; vertical: JavaAxisLine[]; triggerKeys: Set<string> } => {
  const horizontal = new Map<string, JavaAxisLine>();
  const vertical = new Map<string, JavaAxisLine>();
  const triggerKeys = new Set<string>();
  const cells = scanTargets ? scanTargetsToCells(scanTargets) : Array.from(allBoardCells());

  for (const [r, c] of cells) {
    if (!inBounds(r, c)) continue;
    if (board[r]?.[c] === null || board[r]?.[c] === undefined) continue;

    const horizontalSpan = getSpanHorizontal(board, r, c);
    if (unpackLen(horizontalSpan) >= 3) {
      const line = makeHorizontalLine(board, r, c, horizontalSpan);
      if (line) {
        horizontal.set(
          `${line.category}:h:${line.row}:${line.startCol}:${line.hLen}`,
          line,
        );
        addHorizontalKeys(triggerKeys, line.row, line.startCol, line.hLen);
      }
    }

    const verticalSpan = getSpanVertical(board, r, c);
    if (unpackLen(verticalSpan) >= 3) {
      const line = makeVerticalLine(board, r, c, verticalSpan);
      if (line) {
        vertical.set(
          `${line.category}:v:${line.startRow}:${line.col}:${line.vLen}`,
          line,
        );
        addVerticalKeys(triggerKeys, line.startRow, line.col, line.vLen);
      }
    }
  }

  return {
    horizontal: [...horizontal.values()].sort((left, right) => right.hLen - left.hLen),
    vertical: [...vertical.values()].sort((left, right) => right.vLen - left.vLen),
    triggerKeys,
  };
};

const mergeJavaAxisLines = (
  horizontal: JavaAxisLine[],
  vertical: JavaAxisLine[],
): JavaAxisLine[] => {
  const merged: JavaAxisLine[] = [];
  const usedVertical = new Set<number>();

  horizontal.forEach(horizontalLine => {
    let consumed = false;
    for (let index = 0; index < vertical.length; index++) {
      if (usedVertical.has(index)) continue;
      const verticalLine = vertical[index];
      if (horizontalLine.category !== verticalLine.category) continue;

      const intersectsHorizontally =
        verticalLine.col >= horizontalLine.startCol &&
        verticalLine.col < horizontalLine.startCol + horizontalLine.hLen;
      const intersectsVertically =
        horizontalLine.row >= verticalLine.startRow &&
        horizontalLine.row < verticalLine.startRow + verticalLine.vLen;

      if (!intersectsHorizontally || !intersectsVertically) continue;

      merged.push({
        gem: horizontalLine.gem,
        category: horizontalLine.category,
        row: horizontalLine.row,
        startCol: horizontalLine.startCol,
        hLen: horizontalLine.hLen,
        startRow: verticalLine.startRow,
        col: verticalLine.col,
        vLen: verticalLine.vLen,
        spawnRow: horizontalLine.row,
        spawnCol: verticalLine.col,
      });
      usedVertical.add(index);
      consumed = true;
      break;
    }

    if (!consumed) merged.push(horizontalLine);
  });

  vertical.forEach((verticalLine, index) => {
    if (!usedVertical.has(index)) merged.push(verticalLine);
  });

  return merged;
};

const addSpecialClearTarget = (
  board: Board,
  cleared: Set<string>,
  queue: string[],
  r: number,
  c: number,
) => {
  if (!inBounds(r, c)) return;
  const gem = board[r]?.[c];
  if (gem === null || gem === undefined) return;

  const key = keyOf(r, c);
  if (!cleared.has(key)) {
    cleared.add(key);
    queue.push(key);
  }
};

const collectSpecialChainKeys = (board: Board, initialKeys: Set<string>): Set<string> => {
  const cleared = new Set<string>(initialKeys);
  const queue = [...initialKeys];
  const activated = new Set<string>();

  while (queue.length > 0) {
    const key = queue.shift()!;
    if (activated.has(key)) continue;

    const [r, c] = key.split(',').map(Number);
    const gem = board[r]?.[c];
    if (gem === null || gem === undefined) continue;

    const stateClass = getGemStateClass(gem);
    if (stateClass === 1) continue;
    activated.add(key);

    if (stateClass === 2) {
      TYPE2_OFFSETS.forEach(([dr, dc]) => {
        addSpecialClearTarget(board, cleared, queue, r + dr, c + dc);
      });
      continue;
    }

    if (stateClass === 4) {
      for (let nc = 0; nc < BOARD_COLS; nc++) addSpecialClearTarget(board, cleared, queue, r, nc);
      for (let nr = 0; nr < BOARD_ROWS; nr++) addSpecialClearTarget(board, cleared, queue, nr, c);
    }
  }

  return cleared;
};

const resolveSpawnGem = (line: JavaAxisLine): GemType | null => {
  // Java reconstruction note:
  // `mq.a(nj[][])` lines 691-699 spawns `mr.y[baseId]` (20..25/type 4)
  // for cross or len >= 5, and `mr.x[baseId]` (10..15/type 2) for len >= 4.
  // `nj.java` line 60 shows mask 64 node `70` is not upgraded by this branch
  // (`object.a.e < 64`), so only base categories 0..5 can naturally spawn specials.
  const base = line.category;
  if (base < 0 || base >= TYPE2_SPECIAL_BY_BASE.length) return null;

  if ((line.hLen >= 3 && line.vLen >= 3) || line.hLen >= 5 || line.vLen >= 5) {
    return TYPE4_SPECIAL_BY_BASE[base] ?? null;
  }

  if (line.hLen >= 4 || line.vLen >= 4) {
    return TYPE2_SPECIAL_BY_BASE[base] ?? null;
  }

  return null;
};

const mergeSpawnGemPriority = (current: GemType | undefined, next: GemType): GemType =>
  current === undefined ? next : getGemStateClass(next) >= getGemStateClass(current) ? next : current;

export function resolveJavaBoardStep(
  board: Board,
  scanTargets?: Iterable<string | BattleCell>,
): JavaBoardResolveResult | null {
  const { horizontal, vertical, triggerKeys } = collectJavaAxisLines(board, scanTargets);
  if (triggerKeys.size === 0) return null;

  const mergedLines = mergeJavaAxisLines(horizontal, vertical);
  const clearedKeys = collectSpecialChainKeys(board, triggerKeys);
  const nextBoard = cloneBoard(board);

  clearedKeys.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    if (inBounds(r, c)) nextBoard[r][c] = null;
  });

  const spawnMap = new Map<string, GemType>();
  mergedLines.forEach(line => {
    const spawnGem = resolveSpawnGem(line);
    if (spawnGem === null) return;

    const key = keyOf(line.spawnRow, line.spawnCol);
    spawnMap.set(key, mergeSpawnGemPriority(spawnMap.get(key), spawnGem));
  });

  const spawnedSpecials: Array<{ r: number; c: number; gem: GemType }> = [];
  spawnMap.forEach((gem, key) => {
    const [r, c] = key.split(',').map(Number);
    if (!inBounds(r, c)) return;
    nextBoard[r][c] = gem;
    spawnedSpecials.push({ r, c, gem });
  });

  return {
    triggerKeys,
    clearedKeys,
    boardAfterClear: nextBoard,
    spawnedSpecials,
    bonusTurnCandidate: mergedLines.some(line => line.hLen >= 4 || line.vLen >= 4),
  };
}

export const createJavaBoardEngine = (seed = randomSeed()): JavaBoardEngine => ({
  seed: seed >>> 0,
  state: seed >>> 0,
  refillQueue: [],
  refillCursor: 0,
});

export const cloneJavaBoardEngine = (engine: JavaBoardEngine): JavaBoardEngine => ({
  seed: engine.seed,
  state: engine.state,
  refillQueue: [...engine.refillQueue],
  refillCursor: engine.refillCursor,
});

export function makeBoard(engine: JavaBoardEngine = createJavaBoardEngine()): Board {
  for (let attempt = 0; attempt < 24; attempt++) {
    const board = buildInitialBoard(engine, true);
    if (getAllValidMoves(board).length > 0) return board;
  }

  for (let attempt = 0; attempt < 64; attempt++) {
    const board = buildInitialBoard(engine, false);
    if (getAllValidMoves(board).length > 0) return board;
  }

  // Final fallback: still generate a mixed board instead of collapsing to one gem type.
  return buildInitialBoard(engine, false);
}

export function findMatches(board: Board): Set<string> {
  return collectMatchesFromCells(board, allBoardCells());
}

export function findMatchesFromAffected(board: Board, affected: Iterable<string | BattleCell>): Set<string> {
  return collectMatchesFromCells(board, scanTargetsToCells(affected));
}

export const buildAffectedScanFromSwap = (move: MoveSpec): BattleCell[] => [
  [move.r1, move.c1],
  [move.r2, move.c2],
];

export function validateSwap(board: Board, r1: number, c1: number, r2: number, c2: number): JavaSwapMeta | null {
  const first = board[r1]?.[c1];
  const second = board[r2]?.[c2];
  if (first === null || second === null || first === undefined || second === undefined) return null;
  if (first === second) return null;

  const swapped = applySwap(board, r1, c1, r2, c2);
  const spanAHorizontal = getSpanHorizontal(swapped, r1, c1);
  const spanAVertical = getSpanVertical(swapped, r1, c1);
  const spanBHorizontal = getSpanHorizontal(swapped, r2, c2);
  const spanBVertical = getSpanVertical(swapped, r2, c2);

  const valid =
    unpackLen(spanAHorizontal) >= 3 ||
    unpackLen(spanAVertical) >= 3 ||
    unpackLen(spanBHorizontal) >= 3 ||
    unpackLen(spanBVertical) >= 3;

  if (!valid) return null;

  return {
    a: r1,
    b: c1,
    c: r2,
    d: c2,
    e: [spanAHorizontal, spanAVertical, spanBHorizontal, spanBVertical],
  };
}

export function hasBonusTurn(matched: Set<string>, board: Board): boolean {
  for (const key of matched) {
    const [r, c] = key.split(',').map(Number);
    if (!inBounds(r, c)) continue;
    if (unpackLen(getSpanHorizontal(board, r, c)) >= 4 || unpackLen(getSpanVertical(board, r, c)) >= 4) {
      return true;
    }
  }
  return false;
}

export function expandSword(matched: Set<string>, board: Board): Set<string> {
  const out = new Set<string>(matched);
  const explodedReds = new Set<string>();
  const queue: string[] = [];

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = board[r]?.[c];
    if (gem !== null && gem !== undefined && isRedSwordGem(gem)) {
      explodedReds.add(key);
      queue.push(key);
    }
  });

  while (queue.length > 0) {
    const key = queue.shift()!;
    const [r, c] = key.split(',').map(Number);
    for (let dr = -1; dr <= 1; dr++) {
      for (let dc = -1; dc <= 1; dc++) {
        const nr = r + dr;
        const nc = c + dc;
        if (!inBounds(nr, nc)) continue;

        const nextKey = keyOf(nr, nc);
        out.add(nextKey);

        const nextGem = board[nr][nc];
        if (nextGem !== null && isRedSwordGem(nextGem) && !explodedReds.has(nextKey)) {
          explodedReds.add(nextKey);
          queue.push(nextKey);
        }
      }
    }
  }

  return out;
}

export function calcSwordDamage(board: Board, matched: Set<string>): number {
  let total = 0;

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = board[r]?.[c];
    if (gem !== null && gem !== undefined && getGemCategory(gem) === getGemCategory(WHITE_SWORD_GEM)) {
      const renderType = getGemRenderType(gem);
      if (renderType === WHITE_SWORD_GEM) {
      total += SWORD_DAMAGE[WHITE_SWORD_GEM];
      } else {
        total += SWORD_DAMAGE[renderType as typeof WHITE_SWORD_GEM | typeof RED_SWORD_GEM];
      }
    }
  });

  return total;
}

export function collapseLogic(
  board: Board,
  matched: Set<string>,
  engine: JavaBoardEngine = createJavaBoardEngine(),
): CollapseResult {
  const nextBoard = cloneBoard(board);
  const fallMap: FallEntry[] = [];
  const affectedKeys = new Set<string>();

  for (let c = 0; c < BOARD_COLS; c++) {
    const removedRows = new Set<number>();
    for (let r = 0; r < BOARD_ROWS; r++) {
      if (matched.has(keyOf(r, c))) removedRows.add(r);
    }
    if (removedRows.size === 0) continue;

    let target = BOARD_ROWS - 1;
    for (let r = BOARD_ROWS - 1; r >= 0; r--) {
      if (removedRows.has(r)) continue;
      const gem = board[r][c];
      if (gem === null) continue;

      nextBoard[target][c] = gem;
      if (target !== r) {
        fallMap.push({ r: target, c, srcRow: r });
        affectedKeys.add(keyOf(target, c));
      }
      target -= 1;
    }

    const fillCount = target + 1;
    for (let r = fillCount - 1; r >= 0; r--) {
      const sourceRow = -(fillCount - r);
      nextBoard[r][c] = nextRefillGem(engine);
      fallMap.push({ r, c, srcRow: sourceRow });
      affectedKeys.add(keyOf(r, c));
    }

    for (let r = fillCount; r < BOARD_ROWS; r++) {
      if (removedRows.has(r)) affectedKeys.add(keyOf(r, c));
    }
  }

  return { newBoard: nextBoard, fallMap, affectedKeys: [...affectedKeys] };
}

export function collapseResolvedBoard(
  boardAfterClear: Board,
  engine: JavaBoardEngine = createJavaBoardEngine(),
): CollapseResult {
  const nextBoard = cloneBoard(boardAfterClear);
  const fallMap: FallEntry[] = [];
  const affectedKeys = new Set<string>();

  for (let c = 0; c < BOARD_COLS; c++) {
    let target = BOARD_ROWS - 1;

    for (let r = BOARD_ROWS - 1; r >= 0; r--) {
      const gem = boardAfterClear[r][c];
      if (gem === null || gem === undefined) continue;

      nextBoard[target][c] = gem;
      if (target !== r) {
        nextBoard[r][c] = null;
        fallMap.push({ r: target, c, srcRow: r });
        affectedKeys.add(keyOf(target, c));
      }
      target -= 1;
    }

    for (let r = target; r >= 0; r--) {
      const srcRow = -(target - r + 1);
      nextBoard[r][c] = nextRefillGem(engine);
      fallMap.push({ r, c, srcRow });
      affectedKeys.add(keyOf(r, c));
    }
  }

  return { newBoard: nextBoard, fallMap, affectedKeys: [...affectedKeys] };
}

export function clearMatchedCells(board: Board, matched: Set<string>): Board {
  const nextBoard = cloneBoard(board);
  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    if (inBounds(r, c)) nextBoard[r][c] = null;
  });
  return nextBoard;
}

export function reshuffleBoard(
  board: Board,
  engine: JavaBoardEngine = createJavaBoardEngine(),
): Board {
  const gems = board.flat().filter((gem): gem is GemType => gem !== null && gem !== undefined);
  if (gems.length !== BOARD_ROWS * BOARD_COLS) {
    return makeBoard(engine);
  }

  for (let attempt = 0; attempt < 128; attempt++) {
    const pool = [...gems];
    const candidate: Board = Array.from({ length: BOARD_ROWS }, () =>
      Array.from({ length: BOARD_COLS }, () => null),
    );

    for (let r = 0; r < BOARD_ROWS; r++) {
      for (let c = 0; c < BOARD_COLS; c++) {
        const index = randomIndex(engine, pool.length);
        const [gem] = pool.splice(index, 1);
        candidate[r][c] = gem ?? GEM_TYPES[0];
      }
    }

    if (findMatches(candidate).size > 0) continue;
    if (getAllValidMoves(candidate).length <= 0) continue;
    return candidate;
  }

  return makeBoard(engine);
}

export function getAllValidMoves(board: Board): MoveSpec[] {
  const moves: MoveSpec[] = [];

  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      if (c + 1 < BOARD_COLS && validateSwap(board, r, c, r, c + 1) !== null) {
        moves.push({ r1: r, c1: c, r2: r, c2: c + 1 });
      }
      if (r + 1 < BOARD_ROWS && validateSwap(board, r, c, r + 1, c) !== null) {
        moves.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    }
  }

  return moves;
}

function calcEffect(board: Board, matched: Set<string>, swordArea: Set<string> = matched): { dmg: number; heal: number; mp: number } {
  let dmg = calcSwordDamage(board, swordArea);
  let heal = 0;
  let mp = 0;
  const counts: Partial<Record<number, number>> = {};

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = board[r]?.[c];
    if (gem !== null && gem !== undefined) counts[gem] = (counts[gem] ?? 0) + 1;
  });

  Object.entries(counts).forEach(([gemKey, count]) => {
    const gem = Number(gemKey) as GemType;
    const fx = getGemFX(gem);
    heal += Math.trunc(fx.heal * count! / 3);
    mp += Math.trunc(fx.mana * count! / 3);
  });

  return { dmg, heal, mp };
}

function scoreMove(
  board: Board,
  engine: JavaBoardEngine,
  move: MoveSpec,
  level: AILevel,
  enemyHP: number,
  playerHP: number,
): number {
  if (level === 'borm') return randomIndex(engine, 1000);

  const nextBoard = applySwap(board, move.r1, move.c1, move.r2, move.c2);
  const resolved = resolveJavaBoardStep(nextBoard, buildAffectedScanFromSwap(move));
  if (resolved === null) return -1;

  const raw = resolved.triggerKeys;
  const expanded = resolved.clearedKeys;
  const { dmg, heal } = calcEffect(nextBoard, raw, expanded);
  const blastBonus = (expanded.size - raw.size) * 6;
  const count = raw.size;

  switch (level) {
    case 'dan_thuong':
      return randomIndex(engine, 1000) + 1;
    case 'linh_canh':
      return count * 10 + blastBonus;
    case 'thu_linh':
      return dmg * 3 + blastBonus + count * 2;
    case 'tuong_quan': {
      const urgency = playerHP < 30 ? 3.5 : 1;
      return dmg * 2 + heal * urgency + blastBonus + count;
    }
    case 'quan_su': {
      const simEngine = cloneJavaBoardEngine(engine);
      const { newBoard, affectedKeys } = collapseResolvedBoard(resolved.boardAfterClear, simEngine);
      const chainResolved = resolveJavaBoardStep(newBoard, affectedKeys);
      const chain = chainResolved
        ? calcEffect(newBoard, chainResolved.triggerKeys, chainResolved.clearedKeys)
        : { dmg: 0, heal: 0, mp: 0 };
      return dmg * 2 + chain.dmg * 4 + blastBonus + heal + count * 2;
    }
    case 'thien_tai': {
      const simEngine = cloneJavaBoardEngine(engine);
      const { newBoard, affectedKeys } = collapseResolvedBoard(resolved.boardAfterClear, simEngine);
      const chain2Resolved = resolveJavaBoardStep(newBoard, affectedKeys);
      const chain2Fx = chain2Resolved
        ? calcEffect(newBoard, chain2Resolved.triggerKeys, chain2Resolved.clearedKeys)
        : { dmg: 0, heal: 0, mp: 0 };

      let chain3Fx = { dmg: 0, heal: 0, mp: 0 };
      if (chain2Resolved) {
        const secondEngine = cloneJavaBoardEngine(simEngine);
        const { newBoard: nextLayerBoard, affectedKeys: nextAffected } =
          collapseResolvedBoard(chain2Resolved.boardAfterClear, secondEngine);
        const chain3Resolved = resolveJavaBoardStep(nextLayerBoard, nextAffected);
        if (chain3Resolved) {
          chain3Fx = calcEffect(nextLayerBoard, chain3Resolved.triggerKeys, chain3Resolved.clearedKeys);
        }
      }

      return dmg * 2 + chain2Fx.dmg * 4 + chain3Fx.dmg * 6 + heal + blastBonus + count * 2;
    }
    default:
      return count + enemyHP * 0;
  }
}

export function pickAIMove(
  board: Board,
  engine: JavaBoardEngine,
  level: AILevel,
  enemyHP: number,
  playerHP: number,
): MoveSpec | null {
  if (level === 'borm') {
    const allMoves: MoveSpec[] = [];
    for (let r = 0; r < BOARD_ROWS; r++) {
      for (let c = 0; c < BOARD_COLS; c++) {
        if (c + 1 < BOARD_COLS) allMoves.push({ r1: r, c1: c, r2: r, c2: c + 1 });
        if (r + 1 < BOARD_ROWS) allMoves.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    }
    return allMoves[randomIndex(engine, allMoves.length)] ?? null;
  }

  const valid = getAllValidMoves(board);
  if (valid.length === 0) return null;

  if (level === 'dan_thuong') {
    return valid[randomIndex(engine, valid.length)] ?? null;
  }

  let best = valid[0];
  let bestScore = -Infinity;

  for (const move of valid) {
    const score = scoreMove(board, engine, move, level, enemyHP, playerHP);
    if (score > bestScore) {
      bestScore = score;
      best = move;
    }
  }

  return best;
}
