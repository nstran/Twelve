import { Animated } from 'react-native';
import {
  COLLECT_FX_DURATION_MS,
  MATCH_SPARKLE_MAX_MS,
  MATCH_SPARKLE_MIN_MS,
  MATCH_SPARKLE_STAGGER_MS,
} from './BattleScreen.constants';
import { BOARD_LEFT, BOARD_TOP, HUD_BASE_Y, PLAYER_HUD_LAYOUT } from './BattleScreen.layout';
import {
  AURA1_IMG,
  AURA2_IMG,
  AURA3_IMG,
  CRYS_BLUE,
  CRYS_RED,
  GEM_CATEGORY,
  GEM_FX,
  GEM_FX_KIND,
  SWORD_CAT,
} from './BattleScreen.shared';
import type { Board, FXKind } from './BattleScreen.shared';
import type { BattleSide, CollectFXItem, MatchFXItem } from './BattleScreen.types';
import { BG_W, GEM_SIZE } from './BattleScreen.styles';

type HudLayout = typeof PLAYER_HUD_LAYOUT;
type TargetPoint = { x: number; y: number };
type TargetRect = { x: number; y: number; w: number; h: number };

const rand = (min: number, max: number) => min + Math.random() * (max - min);

const pickMatchFXSource = (kind: FXKind) => {
  switch (kind) {
    case 'gold':
      return Math.random() > 0.35 ? AURA1_IMG : AURA2_IMG;
    case 'mp':
      return Math.random() > 0.5 ? CRYS_BLUE : AURA3_IMG;
    case 'crystal_red':
      return Math.random() > 0.55 ? CRYS_RED : AURA1_IMG;
    case 'sword':
      return Math.random() > 0.5 ? AURA2_IMG : AURA3_IMG;
  }
};

export const buildMatchFXItems = (
  matched: Set<string>,
  board: Board,
  expanded: Set<string>,
  nextKey: () => string,
) => {
  const items: MatchFXItem[] = [];
  const hasSwordExpansion = expanded.size > matched.size;

  const pushBurst = (
    r: number,
    c: number,
    kind: FXKind,
    count: number,
    spreadMul: number,
    sizeMul: [number, number],
  ) => {
    for (let i = 0; i < count; i++) {
      const angle = rand(-Math.PI, Math.PI);
      const startRadius = rand(GEM_SIZE * 0.04, GEM_SIZE * 0.24);
      const spreadRadius = rand(GEM_SIZE * 0.42, GEM_SIZE * spreadMul);
      const lift = rand(GEM_SIZE * 0.18, GEM_SIZE * 0.6);
      items.push({
        key: nextKey(),
        r,
        c,
        kind,
        source: pickMatchFXSource(kind),
        size: rand(GEM_SIZE * sizeMul[0], GEM_SIZE * sizeMul[1]),
        startOffsetX: Math.cos(angle) * startRadius,
        startOffsetY: Math.sin(angle) * startRadius * 0.7,
        driftX: Math.cos(angle) * spreadRadius,
        driftY: Math.sin(angle) * spreadRadius * 0.65 - lift,
        rotate: `${rand(-28, 28)}deg`,
        anim: new Animated.Value(0),
        delayMs: rand(0, MATCH_SPARKLE_STAGGER_MS),
        durationMs: rand(MATCH_SPARKLE_MIN_MS, MATCH_SPARKLE_MAX_MS),
      });
    }
  };

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = board[r][c];
    if (gem === null) return;
    const kind = GEM_FX_KIND[gem];
    if (kind !== 'sword') {
      pushBurst(r, c, kind, 5, 1.1, [0.24, 0.62]);
    }
  });

  if (hasSwordExpansion) {
    matched.forEach(key => {
      const [r, c] = key.split(',').map(Number);
      const gem = board[r][c];
      if (gem !== null && GEM_CATEGORY[gem] === SWORD_CAT) {
        pushBurst(r, c, 'sword', 9, 1.35, [0.32, 0.78]);
      }
    });
  }

  return items;
};

const pickSpacedTarget = (
  bucket: TargetPoint[],
  rect: TargetRect,
  minDistance: number,
  yOvershoot: number,
) => {
  let best = {
    x: rect.x + rect.w / 2,
    y: rect.y + rect.h / 2,
    score: -Infinity,
  };

  for (let i = 0; i < 16; i++) {
    const x = rect.x + rand(-rect.w * 0.08, rect.w * 1.08);
    const y = rect.y + rand(-yOvershoot, rect.h + yOvershoot);
    const minScore = bucket.length === 0
      ? Infinity
      : Math.min(...bucket.map(point => Math.hypot(x - point.x, y - point.y)));

    if (minScore > best.score) best = { x, y, score: minScore };
    if (minScore >= minDistance) {
      bucket.push({ x, y });
      return { x, y };
    }
  }

  bucket.push({ x: best.x, y: best.y });
  return { x: best.x, y: best.y };
};

interface BuildCollectFXItemsArgs {
  matched: Set<string>;
  board: Board;
  collectorSide: BattleSide;
  panelLeft: number;
  panelTop: number;
  charsTop: number;
  playerHud: HudLayout;
  enemyHud: HudLayout;
  nextKey: () => string;
}

export const buildCollectFXItems = ({
  matched,
  board,
  collectorSide,
  panelLeft,
  panelTop,
  charsTop,
  playerHud,
  enemyHud,
  nextKey,
}: BuildCollectFXItemsArgs) => {
  const hpRect = collectorSide === 'player'
    ? {
        x: panelLeft + playerHud.offsetX + playerHud.hp.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.hp.y,
        w: playerHud.hp.w,
        h: playerHud.hp.h,
      }
    : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.hp.x - enemyHud.hp.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.hp.y,
        w: enemyHud.hp.w,
        h: enemyHud.hp.h,
      };
  const mpRect = collectorSide === 'player'
    ? {
        x: panelLeft + playerHud.offsetX + playerHud.mp.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.mp.y,
        w: playerHud.mp.w,
        h: playerHud.mp.h,
      }
    : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.mp.x - enemyHud.mp.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.mp.y,
        w: enemyHud.mp.w,
        h: enemyHud.mp.h,
      };
  const powRect = collectorSide === 'player'
    ? {
        x: panelLeft + playerHud.offsetX + playerHud.power.x,
        y: panelTop + HUD_BASE_Y + playerHud.shiftY + playerHud.power.y,
        w: playerHud.power.w,
        h: playerHud.power.h,
      }
    : {
        x: panelLeft + BG_W - enemyHud.offsetX - enemyHud.power.x - enemyHud.power.w,
        y: panelTop + HUD_BASE_Y + enemyHud.shiftY + enemyHud.power.y,
        w: enemyHud.power.w,
        h: enemyHud.power.h,
      };
  const charRect = collectorSide === 'player'
    ? { x: panelLeft + 24, y: charsTop + 10, w: 42, h: 30 }
    : { x: panelLeft + BG_W - 92, y: charsTop + 6, w: 46, h: 34 };

  const usedTargets = {
    hp: [] as TargetPoint[],
    mp: [] as TargetPoint[],
    pow: [] as TargetPoint[],
    char: [] as TargetPoint[],
  };
  const items: CollectFXItem[] = [];
  let hitHP = false;
  let hitMP = false;
  let hitPow = false;
  let hitCharacter = false;

  const pushCollectBurst = (
    source: any,
    startX: number,
    startY: number,
    rect: TargetRect,
    targetBucket: TargetPoint[],
    count: number,
    sizeRange: [number, number],
    fadeOutAt: number,
    endScale: number,
    arcLiftRange: [number, number],
    glow: { scale: number; opacity: number },
    minDistance: number,
    crop?: { left: number; width: number },
    sizeProfile?: number[],
    delayRangeMs: [number, number] = [0, 320],
    durationRangeMs: [number, number] = [COLLECT_FX_DURATION_MS - 380, COLLECT_FX_DURATION_MS + 260],
  ) => {
    for (let i = 0; i < count; i++) {
      const particleStartX = startX + rand(-GEM_SIZE * 0.34, GEM_SIZE * 0.34);
      const particleStartY = startY + rand(-GEM_SIZE * 0.26, GEM_SIZE * 0.26);
      const target = pickSpacedTarget(targetBucket, rect, minDistance, rect.h * 0.9);
      const baseSize = sizeProfile !== undefined
        ? sizeProfile[Math.min(i, sizeProfile.length - 1)]
        : rand(sizeRange[0], sizeRange[1]);
      const size = baseSize + rand(-1.2, 1.2);
      const cropLeft = crop?.left ?? 0;
      const cropWidth = crop?.width ?? 45;

      items.push({
        key: nextKey(),
        source,
        startX: particleStartX,
        startY: particleStartY,
        curve1X: particleStartX + (target.x - particleStartX) * rand(0.14, 0.3) + rand(-GEM_SIZE * 1.9, GEM_SIZE * 1.9),
        curve1Y: particleStartY + (target.y - particleStartY) * rand(0.1, 0.22) - rand(arcLiftRange[0] * 1.08, arcLiftRange[1] * 1.22),
        curve2X: particleStartX + (target.x - particleStartX) * rand(0.5, 0.78) + rand(-GEM_SIZE * 1.45, GEM_SIZE * 1.45),
        curve2Y: particleStartY + (target.y - particleStartY) * rand(0.44, 0.68) - rand(arcLiftRange[0] * 0.22, arcLiftRange[1] * 0.5),
        endX: target.x,
        endY: target.y,
        size,
        isCrystal: crop !== undefined,
        renderW: size,
        renderH: crop !== undefined ? size * (15 / cropWidth) : size,
        cropLeft,
        cropWidth,
        fadeOutAt,
        endScale,
        glowScale: glow.scale,
        glowOpacity: glow.opacity,
        delayMs: rand(delayRangeMs[0], delayRangeMs[1]),
        durationMs: rand(durationRangeMs[0], durationRangeMs[1]),
        anim: new Animated.Value(0),
      });
    }
  };

  matched.forEach(key => {
    const [r, c] = key.split(',').map(Number);
    const gem = board[r][c];
    if (gem === null) return;
    const fx = GEM_FX[gem];
    const startX = panelLeft + BOARD_LEFT + c * GEM_SIZE + GEM_SIZE / 2;
    const startY = panelTop + BOARD_TOP + r * GEM_SIZE + GEM_SIZE / 2;

    if (fx.heal > 0) {
      hitHP = true;
      pushCollectBurst(
        CRYS_RED, startX, startY, hpRect, usedTargets.hp, 2, [44, 56], 0.95, 0.9,
        [GEM_SIZE * 0.48, GEM_SIZE * 1.14], { scale: 1.86, opacity: 0.62 }, 36,
        { left: 7, width: 38 }, [56, 46], [0, 280], [COLLECT_FX_DURATION_MS - 220, COLLECT_FX_DURATION_MS + 320],
      );
    }
    if (fx.mana > 0) {
      hitMP = true;
      pushCollectBurst(
        CRYS_BLUE, startX, startY, mpRect, usedTargets.mp, 2, [42, 54], 0.95, 0.88,
        [GEM_SIZE * 0.44, GEM_SIZE * 1.08], { scale: 1.82, opacity: 0.58 }, 34,
        { left: 7, width: 38 }, [54, 44], [0, 280], [COLLECT_FX_DURATION_MS - 220, COLLECT_FX_DURATION_MS + 320],
      );
    }
    if (fx.pow > 0) {
      hitPow = true;
      pushCollectBurst(
        AURA1_IMG, startX, startY, powRect, usedTargets.pow, 3, [24, 34], 0.93, 0.54,
        [GEM_SIZE * 0.36, GEM_SIZE * 1.04], { scale: 1.62, opacity: 0.34 }, 22,
        undefined, [34, 29, 24], [20, 260], [COLLECT_FX_DURATION_MS - 260, COLLECT_FX_DURATION_MS + 280],
      );
    }
    if (gem === 2 || gem === 5) {
      hitCharacter = true;
      pushCollectBurst(
        gem === 2 ? AURA3_IMG : AURA2_IMG,
        startX,
        startY,
        charRect,
        usedTargets.char,
        4,
        [24, 34],
        0.84,
        0.38,
        [GEM_SIZE * 0.3, GEM_SIZE * 0.94],
        { scale: 1.58, opacity: 0.3 },
        20,
        undefined,
        [34, 30, 27, 24],
        [10, 260],
        [COLLECT_FX_DURATION_MS - 300, COLLECT_FX_DURATION_MS + 260],
      );
    }
  });

  return { items, hitHP, hitMP, hitPow, hitCharacter };
};
