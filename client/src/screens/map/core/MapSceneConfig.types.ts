import type { GroundSurface } from '../../../engine/character';

export type MapSceneAssetBundle = {
  background: ReturnType<typeof require>;
  groundLeft: ReturnType<typeof require>;
  groundCenter: ReturnType<typeof require>;
  groundRight: ReturnType<typeof require>;
};

export type SideScrollMapSceneConfig = {
  mapId: string;
  roomId: number;
  roomLabel: string;
  nativeWidth: number;
  nativeHeight: number;
  playerScale: number;
  playerSpeed: number;
  playerSpawnRatio: number;
  createCharacterDefaultScale: number;
  playerFootSinkSourcePx: number;
  groundTileWidth: number;
  groundTileHeight: number;
  groundTileOverlap: number;
  groundRows: number;
  groundSink: number;
  groundContactRatio: number;
  groundContactVisualDrop: number;
  primaryGroundSurfaceId: string;
  assets: MapSceneAssetBundle;
  buildSurfaces: (mapScale: number) => GroundSurface[];
};
