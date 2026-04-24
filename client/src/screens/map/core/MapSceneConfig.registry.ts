import { HOA_LU_SCENE_CONFIG } from '../hoa-lu/hoaLu.scene';
import type { SideScrollMapSceneConfig } from './MapSceneConfig.types';

const SIDE_SCROLL_MAP_SCENES: ReadonlyArray<SideScrollMapSceneConfig> = [
  HOA_LU_SCENE_CONFIG,
];

const SIDE_SCROLL_MAP_SCENE_INDEX = new Map(
  SIDE_SCROLL_MAP_SCENES.map((scene) => [buildSceneKey(scene.mapId, scene.roomId), scene]),
);

export function resolveSideScrollMapSceneConfig(
  mapId: string,
  roomId: number,
): SideScrollMapSceneConfig | null {
  return SIDE_SCROLL_MAP_SCENE_INDEX.get(buildSceneKey(mapId, roomId)) ?? null;
}

function buildSceneKey(mapId: string, roomId: number): string {
  return `${mapId.trim().toLowerCase()}:${roomId}`;
}
