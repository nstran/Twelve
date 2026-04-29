import type { FacingDirection, GroundSurface } from './character.types';
import { getSurfaceYAtFootX, surfaceContainsX } from './surface';

export const JAVA_TILE_SIZE = 32;

/**
 * Java source: reference/redecoded/cfr_fresh/km.java game-loop style movement.
 *
 * The old React Native controller used a 16ms / ~60fps reference tick, but Java
 * map movement applies `x += i`, `y -= s; s--`, and `y += s; s += 2` once per
 * legacy game tick. Use a fixed 40ms physics step so Java px/tick constants do
 * not run ~2.5x too fast on requestAnimationFrame.
 */
export const JAVA_MAP_TICK_MS = 40;
export const JAVA_MAP_MAX_STEPS_PER_FRAME = 3;

export const enum JavaMapActorState {
  Idle = 0,
  Running = 1,
  Climb = 2,
  DropTransition = 3,
  Attack = 4,
  JumpRising = 5,
  Falling = 6,
  Landing = 7,
  ClimbEnter = 8,
}

export const enum JavaMoveBit {
  Up = 1,
  Down = 2,
  Left = 4,
  Right = 8,
}

export type JavaTileFlagGrid = ReadonlyArray<ReadonlyArray<number>>;

export interface JavaMapCollisionGrid {
  tileSize: number;
  rows: number;
  cols: number;
  flags: JavaTileFlagGrid;
}

export interface JavaMapActorRect {
  a: number;
  b: number;
  c: number;
  d: number;
}

export interface JavaMapActorRuntime {
  t: JavaMapActorRect;
  u: JavaMapActorRect;
  j: JavaMapActorState;
  k: number;
  s: number;
  i: number;
  a: number;
  facing: FacingDirection;
}

/**
 * Java source: reference/redecoded/cfr_fresh/kl.java.
 * Actor runtime hitbox defaults are `t = new k(m, n, E, 32)` with E=17 and
 * `u = new k(m, n, 26, 32)`.
 */
export const createJavaMapActorRuntime = (
  x: number,
  y: number,
  level: number,
): JavaMapActorRuntime => {
  const runSpeed = Math.min(9, 4 + Math.trunc(level / 10));
  const jumpGravityCap = Math.min(16, 11 + Math.trunc(level / 10));

  return {
    t: { a: x, b: y, c: 17, d: 32 },
    u: { a: x, b: y, c: 26, d: 32 },
    j: JavaMapActorState.Idle,
    k: 0,
    s: jumpGravityCap,
    i: runSpeed,
    a: jumpGravityCap,
    facing: 'right',
  };
};

/**
 * Java source: reference/redecoded/cfr_fresh/kl.java vector tables.
 * kl.b[4/5/6] = -1, kl.b[8/9/10] = 1
 * kl.c[1/5/9] = -1, kl.c[2/6/10] = 1
 */
export const javaMoveVectorX = (moveBit: number): -1 | 0 | 1 => {
  if (moveBit === 4 || moveBit === 5 || moveBit === 6) return -1;
  if (moveBit === 8 || moveBit === 9 || moveBit === 10) return 1;
  return 0;
};

export const javaMoveVectorY = (moveBit: number): -1 | 0 | 1 => {
  if (moveBit === 1 || moveBit === 5 || moveBit === 9) return -1;
  if (moveBit === 2 || moveBit === 6 || moveBit === 10) return 1;
  return 0;
};

/**
 * Java source: reference/redecoded/cfr_fresh/kh.java.
 * These helpers preserve the original bit semantics for the later full tile
 * matrix port. Names follow the Java decompile methods.
 */
export const khFlag = {
  a: (tile: number): boolean => (tile & 8) !== 0,
  b: (tile: number): boolean => (tile & 0x20) !== 0,
  c: (tile: number): boolean => (tile & 0x10) !== 0,
  d: (tile: number): boolean => (tile & 0x40) !== 0 && (tile & 2) !== 0,
  l: (tile: number): boolean => (tile & 0x40) !== 0 && (tile & 1) !== 0,
  o: (tile: number): boolean => (tile & 4) !== 0,
  m: (tile: number): boolean => (tile & 1) === 0 && (tile & 4) === 0,
  n: (tile: number): boolean => (tile & 2) === 0 && (tile & 4) === 0,
};

export const kfGetFlag = (grid: JavaMapCollisionGrid, row: number, col: number): number => {
  if (row < 0 || col < 0 || row >= grid.rows || col >= grid.cols) return 0;
  return grid.flags[row]?.[col] ?? 0;
};

/**
 * Java source: reference/redecoded/cfr_fresh/kf.java + kh.java.
 * The original map runtime probes 32x32 collision flags (`kf.d`) through
 * helpers in `kh`. These utilities keep all Java tile-grid probing in one
 * place so the React controller does not depend on visual sprite dimensions.
 */
export const javaTileColAtX = (grid: JavaMapCollisionGrid, x: number): number => (
  Math.floor(x / grid.tileSize)
);

export const javaTileRowAtY = (grid: JavaMapCollisionGrid, y: number): number => (
  Math.floor(y / grid.tileSize)
);

export const javaTileTopY = (grid: JavaMapCollisionGrid, row: number): number => (
  row * grid.tileSize
);

export const javaGetFlagAtPixel = (
  grid: JavaMapCollisionGrid,
  x: number,
  y: number,
): number => (
  kfGetFlag(grid, javaTileRowAtY(grid, y), javaTileColAtX(grid, x))
);

const javaProbeYsForSide = (rect: JavaMapActorRect): number[] => [
  rect.b + 1,
  rect.b + rect.d / 2,
  rect.b + rect.d - 2,
];

export const javaHasGroundSupport = (
  grid: JavaMapCollisionGrid,
  rect: JavaMapActorRect,
): boolean => {
  const footY = rect.b + rect.d;
  const leftX = rect.a;
  const midX = rect.a + rect.c / 2;
  const rightX = rect.a + rect.c - 1;

  return (
    khFlag.c(javaGetFlagAtPixel(grid, leftX, footY)) ||
    khFlag.c(javaGetFlagAtPixel(grid, midX, footY)) ||
    khFlag.c(javaGetFlagAtPixel(grid, rightX, footY))
  );
};

export const javaFindLandingTileTop = (
  grid: JavaMapCollisionGrid,
  rect: JavaMapActorRect,
  previousFootY: number,
  projectedFootY: number,
): number | null => {
  const startY = Math.min(previousFootY, projectedFootY);
  const endY = Math.max(previousFootY, projectedFootY);
  const probeXs = [rect.a, rect.a + rect.c / 2, rect.a + rect.c - 1];

  for (let y = startY; y <= endY; y += Math.max(1, grid.tileSize / 8)) {
    for (const x of probeXs) {
      const row = javaTileRowAtY(grid, y);
      const flag = kfGetFlag(grid, row, javaTileColAtX(grid, x));
      if (khFlag.c(flag)) {
        return javaTileTopY(grid, row);
      }
    }
  }

  return null;
};

export const javaCanMoveHorizontally = (
  grid: JavaMapCollisionGrid,
  rect: JavaMapActorRect,
  nextX: number,
  direction: FacingDirection,
): boolean => {
  const probeX = direction === 'right' ? nextX + rect.c - 1 : nextX;
  const probeRect = { ...rect, a: nextX };
  const passable = direction === 'right' ? khFlag.m : khFlag.n;

  return javaProbeYsForSide(probeRect).every((y) => (
    passable(javaGetFlagAtPixel(grid, probeX, y))
  ));
};

export const javaFindCeilingBottom = (
  grid: JavaMapCollisionGrid,
  rect: JavaMapActorRect,
  nextY: number,
): number | null => {
  const topY = nextY;
  const probeXs = [rect.a, rect.a + rect.c / 2, rect.a + rect.c - 1];

  for (const x of probeXs) {
    const row = javaTileRowAtY(grid, topY);
    const flag = kfGetFlag(grid, row, javaTileColAtX(grid, x));
    if (khFlag.a(flag) || khFlag.o(flag) || khFlag.c(flag)) {
      return javaTileTopY(grid, row + 1);
    }
  }

  return null;
};

export const findSurfaceSupport = (
  surfaces: ReadonlyArray<GroundSurface>,
  leftX: number,
  width: number,
  currentFootY: number,
  maxSnapDown = JAVA_TILE_SIZE,
): GroundSurface | null => {
  const footX = leftX + width / 2;
  let best: GroundSurface | null = null;
  let bestY = Number.POSITIVE_INFINITY;

  for (const surface of surfaces) {
    if (!surfaceContainsX(surface, footX)) continue;
    const y = getSurfaceYAtFootX(surface, leftX, width);
    if (y < currentFootY - 2) continue;
    if (y > currentFootY + maxSnapDown) continue;
    if (y < bestY) {
      best = surface;
      bestY = y;
    }
  }

  return best;
};

export const buildFlatGroundJavaGrid = (
  widthPx: number,
  heightPx: number,
  groundY: number,
): JavaMapCollisionGrid => {
  const cols = Math.ceil(widthPx / JAVA_TILE_SIZE);
  const rows = Math.ceil(heightPx / JAVA_TILE_SIZE);
  const groundRow = Math.max(0, Math.min(rows - 1, Math.floor(groundY / JAVA_TILE_SIZE)));
  const flags = Array.from({ length: rows }, (_, row) => (
    Array.from({ length: cols }, () => (row >= groundRow ? 0x10 : 0))
  ));

  return {
    tileSize: JAVA_TILE_SIZE,
    rows,
    cols,
    flags,
  };
};