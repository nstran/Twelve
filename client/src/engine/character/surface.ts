import type { GroundSurface } from './character.types';

function getSurfaceEndpointY(surface: GroundSurface, side: 'start' | 'end'): number {
  if (side === 'start') {
    if (surface.y1 !== undefined) return surface.y1;
    if (surface.y !== undefined) return surface.y;
    if (surface.y2 !== undefined) return surface.y2;
    return 0;
  }

  if (surface.y2 !== undefined) return surface.y2;
  if (surface.y !== undefined) return surface.y;
  if (surface.y1 !== undefined) return surface.y1;
  return 0;
}

export function surfaceContainsX(surface: GroundSurface, x: number): boolean {
  return x >= surface.x1 && x <= surface.x2;
}

export function getSurfaceStartY(surface: GroundSurface): number {
  return getSurfaceEndpointY(surface, 'start');
}

export function getSurfaceEndY(surface: GroundSurface): number {
  return getSurfaceEndpointY(surface, 'end');
}

export function getSurfaceYAtX(surface: GroundSurface, x: number): number {
  const startY = getSurfaceStartY(surface);
  const endY = getSurfaceEndY(surface);
  const span = surface.x2 - surface.x1;

  if (span === 0 || startY === endY) {
    return startY;
  }

  const clampedX = Math.max(surface.x1, Math.min(surface.x2, x));
  const t = (clampedX - surface.x1) / span;
  return startY + (endY - startY) * t;
}

export function getSurfaceYAtFootX(
  surface: GroundSurface,
  leftX: number,
  characterWidth: number,
): number {
  return getSurfaceYAtX(surface, leftX + characterWidth / 2);
}

export function getSurfaceCeilingYAtX(surface: GroundSurface, x: number): number {
  return getSurfaceYAtX(surface, x) + (surface.ceilingOffset ?? 0);
}

export function getSurfaceCeilingYAtFootX(
  surface: GroundSurface,
  leftX: number,
  characterWidth: number,
): number {
  return getSurfaceCeilingYAtX(surface, leftX + characterWidth / 2);
}
