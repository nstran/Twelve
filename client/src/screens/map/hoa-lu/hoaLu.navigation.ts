import type { GroundSurface } from '../../../engine/character';

interface NativeSurface {
  id: string;
  x1: number;
  x2: number;
  y: number;
  kind?: 'ground' | 'platform';
  oneWay?: boolean;
}

// Authored in the original background's native space (1536 x 1024).
// These are the walkable grass-top segments, not the full stone bodies.
const HOA_LU_NATIVE_SURFACES: NativeSurface[] = [
  { id: 'ground_main', x1: 0, x2: 1536, y: 738, kind: 'ground' },
  { id: 'island_lower_left', x1: 6, x2: 132, y: 620, kind: 'platform', oneWay: true },
  { id: 'island_mid_left', x1: 586, x2: 760, y: 472, kind: 'platform', oneWay: true },
  { id: 'island_mid_right', x1: 798, x2: 1126, y: 520, kind: 'platform', oneWay: true },
  { id: 'island_top_left', x1: 506, x2: 612, y: 353, kind: 'platform', oneWay: true },
];

export function buildHoaLuSurfaces(mapScale: number): GroundSurface[] {
  return HOA_LU_NATIVE_SURFACES.map((surface) => ({
    ...surface,
    x1: Math.round(surface.x1 * mapScale),
    x2: Math.round(surface.x2 * mapScale),
    y: Math.round(surface.y * mapScale),
  }));
}
