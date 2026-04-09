import { BOARD_COLS, BOARD_ROWS } from './BattleScreen.styles';
import {
  AILevel,
  Board,
  FallEntry,
  GEM_CATEGORY,
  GEM_FX,
  GEM_TYPES,
  GemType,
  MoveSpec,
  RED_SWORD_GEM,
  SWORD_CAT,
  SWORD_DAMAGE,
  WHITE_SWORD_GEM,
} from './BattleScreen.shared';

const sameCat = (a: GemType | null, b: GemType | null) =>
  a !== null && b !== null && GEM_CATEGORY[a] === GEM_CATEGORY[b];

function randomGem() {
  return GEM_TYPES[Math.floor(Math.random() * GEM_TYPES.length)];
}

export function makeBoard(): Board {
  const board: Board = Array.from({ length: BOARD_ROWS }, () =>
    Array.from({ length: BOARD_COLS }, () => null)
  );

  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      const forbidden = new Set<number>();
      if (c >= 2) {
        const g1 = board[r][c - 1];
        const g2 = board[r][c - 2];
        if (g1 !== null && g2 !== null && GEM_CATEGORY[g1] === GEM_CATEGORY[g2]) {
          forbidden.add(GEM_CATEGORY[g1]);
        }
      }
      if (r >= 2) {
        const g1 = board[r - 1][c];
        const g2 = board[r - 2][c];
        if (g1 !== null && g2 !== null && GEM_CATEGORY[g1] === GEM_CATEGORY[g2]) {
          forbidden.add(GEM_CATEGORY[g1]);
        }
      }

      const valid = GEM_TYPES.filter(t => !forbidden.has(GEM_CATEGORY[t]));
      const pool = valid.length > 0 ? valid : [...GEM_TYPES];
      board[r][c] = pool[Math.floor(Math.random() * pool.length)];
    }
  }

  return board;
}

export function findMatches(b: Board): Set<string> {
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
      } else {
        c++;
      }
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
      } else {
        r++;
      }
    }
  }

  return hit;
}

/**
 * Kiểm tra có nhóm gem CÙNG MÀU (category) match liền kề ≥ 4 viên hay không.
 * Dùng connected-component (BFS), chỉ gom ô cùng category.
 */
export function hasBonusTurn(matched: Set<string>, b: Board): boolean {
  if (matched.size < 4) return false;

  const visited = new Set<string>();
  const dirs = [[0, 1], [0, -1], [1, 0], [-1, 0]];

  for (const start of Array.from(matched)) {
    if (visited.has(start)) continue;

    const [sr, sc] = start.split(',').map(Number);
    const startGem = b[sr][sc];
    if (startGem === null) {
      visited.add(start);
      continue;
    }

    const cat = GEM_CATEGORY[startGem];
    const queue = [start];
    visited.add(start);
    let size = 0;

    while (queue.length > 0) {
      const key = queue.shift()!;
      size++;
      const [r, c] = key.split(',').map(Number);

      for (const [dr, dc] of dirs) {
        const nr = r + dr;
        const nc = c + dc;
        const nextKey = `${nr},${nc}`;
        if (!matched.has(nextKey) || visited.has(nextKey)) continue;

        const nextGem = b[nr]?.[nc];
        if (nextGem !== null && nextGem !== undefined && GEM_CATEGORY[nextGem] === cat) {
          visited.add(nextKey);
          queue.push(nextKey);
        }
      }
    }

    if (size >= 4) return true;
  }

  return false;
}

export function expandSword(matched: Set<string>, b: Board): Set<string> {
  const out = new Set<string>(matched);
  const explodedReds = new Set<string>();
  const queue: string[] = [];

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    if (b[r][c] === RED_SWORD_GEM) {
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
        if (nr < 0 || nr >= BOARD_ROWS || nc < 0 || nc >= BOARD_COLS) continue;

        const nextKey = `${nr},${nc}`;
        out.add(nextKey);

        if (b[nr][nc] === RED_SWORD_GEM && !explodedReds.has(nextKey)) {
          explodedReds.add(nextKey);
          queue.push(nextKey);
        }
      }
    }
  }

  return out;
}

export function calcSwordDamage(b: Board, matched: Set<string>): number {
  let total = 0;

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = b[r][c];
    if (gem === WHITE_SWORD_GEM) {
      total += SWORD_DAMAGE[WHITE_SWORD_GEM];
    } else if (gem === RED_SWORD_GEM) {
      total += SWORD_DAMAGE[RED_SWORD_GEM];
    }
  });

  return total;
}

export function collapseLogic(b: Board, matched: Set<string>): { newBoard: Board; fallMap: FallEntry[] } {
  const nextBoard: Board = b.map(row => [...row]);
  const fallMap: FallEntry[] = [];

  for (let c = 0; c < BOARD_COLS; c++) {
    const removed = new Set<number>();
    for (let r = 0; r < BOARD_ROWS; r++) {
      if (matched.has(`${r},${c}`)) removed.add(r);
    }
    if (removed.size === 0) continue;

    const fill = removed.size;
    const targetToSource: number[] = new Array(BOARD_ROWS);
    let target = BOARD_ROWS - 1;

    for (let r = BOARD_ROWS - 1; r >= 0; r--) {
      if (!removed.has(r)) {
        targetToSource[target] = r;
        target--;
      }
    }

    for (let i = 0; i < fill; i++) {
      targetToSource[i] = -(fill - i);
    }

    for (let r = 0; r < BOARD_ROWS; r++) {
      const sourceRow = targetToSource[r];
      nextBoard[r][c] = sourceRow >= 0 ? b[sourceRow][c] : randomGem();
      if (sourceRow !== r) {
        fallMap.push({ r, c, srcRow: sourceRow });
      }
    }
  }

  return { newBoard: nextBoard, fallMap };
}

function applySwap(b: Board, r1: number, c1: number, r2: number, c2: number): Board {
  const nextBoard = b.map(row => [...row]);
  [nextBoard[r1][c1], nextBoard[r2][c2]] = [nextBoard[r2][c2], nextBoard[r1][c1]];
  return nextBoard;
}

export function getAllValidMoves(b: Board): MoveSpec[] {
  const moves: MoveSpec[] = [];

  for (let r = 0; r < BOARD_ROWS; r++) {
    for (let c = 0; c < BOARD_COLS; c++) {
      if (c + 1 < BOARD_COLS && findMatches(applySwap(b, r, c, r, c + 1)).size > 0) {
        moves.push({ r1: r, c1: c, r2: r, c2: c + 1 });
      }
      if (r + 1 < BOARD_ROWS && findMatches(applySwap(b, r, c, r + 1, c)).size > 0) {
        moves.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    }
  }

  return moves;
}

function calcEffect(b: Board, matched: Set<string>, swordArea: Set<string> = matched): { dmg: number; heal: number; mp: number } {
  let dmg = calcSwordDamage(b, swordArea);
  let heal = 0;
  let mp = 0;
  const counts: Partial<Record<number, number>> = {};

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = b[r][c];
    if (gem !== null) counts[gem] = (counts[gem] ?? 0) + 1;
  });

  Object.entries(counts).forEach(([gem, count]) => {
    const fx = GEM_FX[Number(gem) as keyof typeof GEM_FX];
    heal += Math.round(fx.heal * count! / 3);
    mp += Math.round(fx.mana * count!);
  });

  return { dmg, heal, mp };
}

function scoreMove(
  b: Board,
  move: MoveSpec,
  level: AILevel,
  enemyHP: number,
  playerHP: number,
): number {
  if (level === 'borm') return Math.random() * 100;

  const nextBoard = applySwap(b, move.r1, move.c1, move.r2, move.c2);
  const raw = findMatches(nextBoard);
  if (raw.size === 0) return -1;

  const expanded = expandSword(raw, nextBoard);
  const { dmg, heal } = calcEffect(nextBoard, raw, expanded);
  const blastBonus = (expanded.size - raw.size) * 6;
  const count = raw.size;

  switch (level) {
    case 'dan_thuong':
      return Math.random() * 100 + 1;
    case 'linh_canh':
      return count * 10 + blastBonus;
    case 'thu_linh':
      return dmg * 3 + blastBonus + count * 2;
    case 'tuong_quan': {
      const urgency = playerHP < 30 ? 3.5 : 1;
      return dmg * 2 + heal * urgency + blastBonus + count;
    }
    case 'quan_su': {
      const { newBoard } = collapseLogic(nextBoard, expanded);
      const chainRaw = findMatches(newBoard);
      const chainExpanded = chainRaw.size > 0 ? expandSword(chainRaw, newBoard) : new Set<string>();
      const chain = chainRaw.size > 0 ? calcEffect(newBoard, chainRaw, chainExpanded) : { dmg: 0, heal: 0, mp: 0 };
      return dmg * 2 + chain.dmg * 4 + blastBonus + heal + count * 2;
    }
    case 'thien_tai': {
      const { newBoard } = collapseLogic(nextBoard, expanded);
      const chain2Raw = findMatches(newBoard);
      const chain2Expanded = chain2Raw.size > 0 ? expandSword(chain2Raw, newBoard) : new Set<string>();
      const chain2Fx = chain2Raw.size > 0 ? calcEffect(newBoard, chain2Raw, chain2Expanded) : { dmg: 0, heal: 0, mp: 0 };

      let chain3Fx = { dmg: 0, heal: 0, mp: 0 };
      if (chain2Raw.size > 0) {
        const { newBoard: nextLayerBoard } = collapseLogic(newBoard, chain2Expanded);
        const chain3Raw = findMatches(nextLayerBoard);
        const chain3Expanded = chain3Raw.size > 0 ? expandSword(chain3Raw, nextLayerBoard) : new Set<string>();
        if (chain3Raw.size > 0) chain3Fx = calcEffect(nextLayerBoard, chain3Raw, chain3Expanded);
      }

      return dmg * 2 + chain2Fx.dmg * 4 + chain3Fx.dmg * 6 + heal + blastBonus + count * 2;
    }
    default:
      return count;
  }
}

export function pickAIMove(b: Board, level: AILevel, enemyHP: number, playerHP: number): MoveSpec | null {
  if (level === 'borm') {
    const allMoves: MoveSpec[] = [];
    for (let r = 0; r < BOARD_ROWS; r++) {
      for (let c = 0; c < BOARD_COLS; c++) {
        if (c + 1 < BOARD_COLS) allMoves.push({ r1: r, c1: c, r2: r, c2: c + 1 });
        if (r + 1 < BOARD_ROWS) allMoves.push({ r1: r, c1: c, r2: r + 1, c2: c });
      }
    }
    return allMoves[Math.floor(Math.random() * allMoves.length)] ?? null;
  }

  const valid = getAllValidMoves(b);
  if (valid.length === 0) return null;

  if (level === 'dan_thuong') {
    return valid[Math.floor(Math.random() * valid.length)];
  }

  let best = valid[0];
  let bestScore = -Infinity;

  for (const move of valid) {
    const score = scoreMove(b, move, level, enemyHP, playerHP);
    if (score > bestScore) {
      bestScore = score;
      best = move;
    }
  }

  return best;
}
