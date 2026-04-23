import { HOA_LU_SCENE_CONFIG } from '../hoa-lu/hoaLu.scene';
import type { SideScrollMapSceneConfig } from './MapSceneConfig.types';

const SIDE_SCROLL_MAP_SCENES: ReadonlyArray<SideScrollMapSceneConfig> = [
  HOA_LU_SCENE_CONFIG,
];

export function resolveSideScrollMapSceneConfig(
  mapId: string,
  roomId: number,
): SideScrollMapSceneConfig | null {
  for (const scene of SIDE_SCROLL_MAP_SCENES) {
    if (scene.mapId === mapId && scene.roomId === roomId) {
      return scene;
    }
  }

  return null;
}
