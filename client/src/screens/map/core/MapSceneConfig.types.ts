import type { GroundSurface } from '../../../engine/character';

export type MapSceneAssetBundle = {
  background: ReturnType<typeof require>;
  groundLeft: ReturnType<typeof require>;
  groundCenter: ReturnType<typeof require>;
  groundRight: ReturnType<typeof require>;
};

export type MapSceneDecorLayer = 'background' | 'behindActors' | 'frontDecor';

export type MapSceneDecorObject = {
  key: string;
  asset: ReturnType<typeof require>;
  layer: MapSceneDecorLayer;
  xRatio: number;
  yRatio?: number;
  groundOffset?: number;
  width: number;
  height: number;
  zIndex: number;
  resizeMode?: 'cover' | 'contain' | 'stretch' | 'repeat' | 'center';
};

export type LocalMonsterSpawnGroupProfile = {
  spawnGroupKey: string;
  surfaceId: string;
  patrolStartRatio: number;
  patrolEndRatio: number;
  spawnStartRatio: number;
  spawnEndRatio: number;
  moveSpeed: number;
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
  decorObjects?: ReadonlyArray<MapSceneDecorObject>;
  buildSurfaces: (mapScale: number) => GroundSurface[];
  monsterSpawnGroups?: ReadonlyArray<LocalMonsterSpawnGroupProfile>;
};
