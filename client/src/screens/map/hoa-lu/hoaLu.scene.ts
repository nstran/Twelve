import { HOA_LU_MAP_ASSETS } from './assets';
import { buildHoaLuSurfaces } from './hoaLu.navigation';
import type { SideScrollMapSceneConfig } from '../core';

export const HOA_LU_SCENE_CONFIG: SideScrollMapSceneConfig = {
  mapId: 'Hoa Lu',
  roomId: 1,
  roomLabel: 'Khu 1',
  nativeWidth: 1536,
  nativeHeight: 1024,
  playerScale: 1.6,
  playerSpeed: 1.35,
  playerSpawnRatio: 0.08,
  createCharacterDefaultScale: 2.2,
  playerFootSinkSourcePx: 5,
  groundTileWidth: 124,
  groundTileHeight: 102,
  groundTileOverlap: 20,
  groundRows: 1,
  groundSink: 18,
  groundContactRatio: 0.16,
  groundContactVisualDrop: 4,
  primaryGroundSurfaceId: 'ground_main',
  assets: HOA_LU_MAP_ASSETS,
  buildSurfaces: buildHoaLuSurfaces,
};
