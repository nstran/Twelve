import { Animated } from 'react-native';
import type { MonsterType } from '../../../../engine/MonsterSprite';
import {
  WALK_FRAMES,
  monsterCollisionSize,
  monsterDisplaySize,
  monsterPlacementMetrics,
} from '../../../../engine/MonsterSprite';
import type { GroundSurface, MonsterTarget } from '../../../../engine/character';
import { getSurfaceStartY } from '../../../../engine/character';
import type { MapNpcRosterRecord } from '../../../../network/SocketClient';
import { resolveMonsterTypeFromVisuals } from '../../../battle';
import type { MapMonsterRosterEntry } from '../../core';

export interface MonsterRuntime {
  id: string;
  type: MonsterType;
  roster: MapMonsterRosterEntry;
  surfaceId: string;
  groundY: number;
  minX: number;
  maxX: number;
  x: number;
  direction: 1 | -1;
  tickCount: number;
  attacking: boolean;
  worldState: 'patrol' | 'alert' | 'engaging';
  aggroTicks: number;
  engageQueued: boolean;
  frameIndex: number;
  xAnim: Animated.Value;
  size: ReturnType<typeof monsterDisplaySize>;
  collisionSize: ReturnType<typeof monsterCollisionSize>;
  topY: number;
}

export interface MonsterVisual {
  id: string;
  frameIndex: number;
  direction: 1 | -1;
  attacking: boolean;
  worldState: 'patrol' | 'alert' | 'engaging';
}

export interface NpcRuntime {
  id: string;
  displayName: string;
  type: MonsterType;
  x: number;
  y: number;
  nameColorMode: number;
}

export const resolveNpcSpriteType = (visualTypeByte: number): MonsterType => {
  const family = visualTypeByte >> 1;
  if (family === 0) {
    return 'fire';
  }
  if (family === 1) {
    return 'zap';
  }
  return 'ice';
};

export const resolveNpcNameColor = (nameColorMode: number): string => {
  if (nameColorMode === 1) {
    return '#ff3a28';
  }
  if (nameColorMode === 2) {
    return '#897c92';
  }
  return '#f8f5d8';
};

export function createNpcRuntime(entry: MapNpcRosterRecord, mapScale: number, groundY: number): NpcRuntime {
  return {
    id: entry.npcId,
    displayName: entry.displayName,
    type: resolveNpcSpriteType(entry.visualTypeByte),
    x: Math.round(entry.tileX * 32 * mapScale),
    y: Math.round(groundY - 2),
    nameColorMode: entry.nameColorMode,
  };
}

export function buildNpcRuntimes(roster: MapNpcRosterRecord[], mapScale: number, groundY: number): NpcRuntime[] {
  return roster.map((entry) => createNpcRuntime(entry, mapScale, groundY));
}

export function createMonsterRuntime(entry: MapMonsterRosterEntry, surfaces: GroundSurface[]): MonsterRuntime {
  const surface = surfaces.find((candidate) => candidate.id === entry.surfaceId);
  if (!surface) {
    throw new Error(`Surface '${entry.surfaceId}' not found in side-scroll map navigation data.`);
  }

  const span = Math.max(0, surface.x2 - surface.x1);
  const minX = surface.x1 + Math.max(0, Math.min(1, entry.patrolStartRatio)) * span;
  const maxX = surface.x1 + Math.max(0, Math.min(1, entry.patrolEndRatio)) * span;
  const startX = minX + Math.max(0, maxX - minX) * Math.max(0, Math.min(1, entry.spawnRatio));
  const type = resolveMonsterTypeFromVisuals(entry.visualTypeByte, entry.sharedSheetFamily);
  const size = monsterDisplaySize(type);
  const collisionSize = monsterCollisionSize(type);
  const placement = monsterPlacementMetrics(type);
  const leftX = startX - size.w / 2;
  const surfaceGroundY = getSurfaceStartY(surface);

  return {
    id: entry.monsterKey,
    type,
    roster: entry,
    surfaceId: surface.id,
    groundY: surfaceGroundY,
    minX,
    maxX,
    x: startX,
    direction: (entry.spawnInstanceIndex & 1) === 0 ? 1 : -1,
    tickCount: 0,
    attacking: false,
    worldState: 'patrol',
    aggroTicks: 0,
    engageQueued: false,
    frameIndex: WALK_FRAMES[0],
    xAnim: new Animated.Value(leftX),
    size,
    collisionSize,
    topY: surfaceGroundY - size.h + placement.groundOffset,
  };
}

export function reconcileMonsterRuntimes(
  previousRuntimes: MonsterRuntime[],
  previousTargets: MonsterTarget[],
  roster: MapMonsterRosterEntry[],
  surfaces: GroundSurface[],
): { runtimes: MonsterRuntime[]; targets: MonsterTarget[] } {
  const previousById = new Map(previousRuntimes.map((runtime) => [runtime.id, runtime]));
  const previousTargetsById = new Map(previousTargets.map((target) => [target.id, target]));

  const runtimes = roster.map((entry) => {
    const existing = previousById.get(entry.monsterKey);
    if (!existing) {
      return createMonsterRuntime(entry, surfaces);
    }

    const surface = surfaces.find((candidate) => candidate.id === entry.surfaceId);
    if (!surface) {
      throw new Error(`Surface '${entry.surfaceId}' not found in side-scroll map navigation data.`);
    }

    const span = Math.max(0, surface.x2 - surface.x1);
    const minX = surface.x1 + Math.max(0, Math.min(1, entry.patrolStartRatio)) * span;
    const maxX = surface.x1 + Math.max(0, Math.min(1, entry.patrolEndRatio)) * span;
    const type = resolveMonsterTypeFromVisuals(entry.visualTypeByte, entry.sharedSheetFamily);
    const size = monsterDisplaySize(type);
    const collisionSize = monsterCollisionSize(type);
    const placement = monsterPlacementMetrics(type);
    const surfaceGroundY = getSurfaceStartY(surface);
    const clampedX = Math.max(minX, Math.min(maxX, existing.x));
    const nextTopY = surfaceGroundY - size.h + placement.groundOffset;
    const nextLeftX = clampedX - size.w / 2;

    existing.type = type;
    existing.roster = entry;
    existing.surfaceId = surface.id;
    existing.groundY = surfaceGroundY;
    existing.minX = minX;
    existing.maxX = maxX;
    existing.x = clampedX;
    existing.size = size;
    existing.collisionSize = collisionSize;
    existing.topY = nextTopY;
    existing.xAnim.setValue(nextLeftX);

    return existing;
  });

  const targets = runtimes.map((runtime) => {
    const existing = previousTargetsById.get(runtime.id);
    const leftX = runtime.x - runtime.size.w / 2;
    if (existing) {
      existing.x = leftX;
      existing.y = runtime.topY;
      existing.width = runtime.size.w;
      existing.height = runtime.size.h;
      existing.collisionWidth = runtime.collisionSize.w;
      existing.collisionHeight = runtime.collisionSize.h;
      existing.groundY = runtime.groundY;
      return existing;
    }

    return {
      id: runtime.id,
      x: leftX,
      y: runtime.topY,
      width: runtime.size.w,
      height: runtime.size.h,
      collisionWidth: runtime.collisionSize.w,
      collisionHeight: runtime.collisionSize.h,
      groundY: runtime.groundY,
    };
  });

  return { runtimes, targets };
}

export function hasMonsterCollision(
  playerLeft: number,
  playerWidth: number,
  playerFootY: number,
  monsterCenterX: number,
  monsterGroundY: number,
  monsterWidth: number,
  monsterHeight: number,
): boolean {
  // Java-inspired/reconstructed policy: player map actor collision uses the
  // compact runtime body (`kl.t.c`, recovered around 17px in Java scale), not
  // the full rendered sprite width.
  const playerRuntimeWidth = Math.max(17, Math.round(playerWidth * 0.36));
  const playerCenterX = playerLeft + playerWidth / 2;
  const playerHitboxLeft = playerCenterX - playerRuntimeWidth / 2;
  const playerHitboxRight = playerCenterX + playerRuntimeWidth / 2;
  const monsterLeft = monsterCenterX - monsterWidth / 2;
  const monsterRight = monsterCenterX + monsterWidth / 2;
  const horizontalOverlap = playerHitboxRight >= monsterLeft &&
    playerHitboxLeft <= monsterRight;

  if (!horizontalOverlap) {
    return false;
  }

  // Monster exact Java hitboxes are not recovered yet; anchor the reconstructed
  // AABB to ground/body so jump-over does not trigger battle from alpha padding.
  const monsterTop = monsterGroundY - monsterHeight;
  const bodyTop = monsterTop + Math.max(3, Math.round(monsterHeight * 0.12));
  const bodyBottom = monsterGroundY + Math.max(2, Math.round(monsterHeight * 0.08));
  return playerFootY >= bodyTop && playerFootY <= bodyBottom;
}

export function buildInitialVisuals(runtimes: MonsterRuntime[]): MonsterVisual[] {
  return runtimes.map(m => ({
    id: m.id,
    frameIndex: m.frameIndex,
    direction: m.direction,
    attacking: m.attacking,
    worldState: m.worldState,
  }));
}
