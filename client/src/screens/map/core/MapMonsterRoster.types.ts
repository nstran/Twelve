import type { MonsterSharedSheetFamily } from '../../battle';

export interface MapMonsterRosterRequest {
  mapId: string;
  roomId: number;
}

export interface MapMonsterRosterEntry {
  monsterKey: string;
  spawnGroupKey: string;
  spawnInstanceIndex: number;
  spawnTemplateKey: string;
  displayName: string;
  visualTypeByte: number;
  displayLevel: number;
  iqValue: number;
  nameColorMode: number;
  sharedSheetFamily: MonsterSharedSheetFamily;
  surfaceId: string;
  patrolStartRatio: number;
  patrolEndRatio: number;
  spawnRatio: number;
  moveSpeed: number;
  assetCatalogId?: string;
  framePaths?: string[];
}

export interface MapMonsterRosterResponse {
  mapId: string;
  roomId: number;
  encounters: MapMonsterRosterEntry[];
}

export type ResolveMapMonsterRoster =
  (request: MapMonsterRosterRequest) =>
    MapMonsterRosterResponse | Promise<MapMonsterRosterResponse | null> | null;
