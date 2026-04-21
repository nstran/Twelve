/**
 * Character module — reusable player character with sprite animation & controls.
 *
 * Usage:
 *   import { CharacterController, CharacterSprite, characterDisplaySize } from '../engine/character';
 */

export { CharacterSprite, characterDisplaySize } from './CharacterSprite';
export { CharacterController } from './CharacterController';
export { useCharacterAnimation } from './useCharacterAnimation';
export {
  surfaceContainsX,
  getSurfaceStartY,
  getSurfaceEndY,
  getSurfaceYAtX,
  getSurfaceYAtFootX,
  getSurfaceCeilingYAtX,
  getSurfaceCeilingYAtFootX,
} from './surface';
export type {
  CharacterAction,
  CharacterPoseFamilySlot,
  FacingDirection,
  CharacterControlMode,
  MonsterTarget,
  CharacterState,
  CharacterSpriteProps,
  CharacterRenderSpriteArgs,
  CharacterControllerProps,
  CharacterControllerRef,
  VirtualJumpDirection,
  GroundSurface,
} from './character.types';
export {
  ANIM_FRAMES,
  ACTION_FRAME_COUNTS,
  ANIM_SPEED,
  ACTION_FRAME_DURATIONS,
  ATTACK_DURATION,
  DEFAULT_SPEED,
  DEFAULT_SCALE,
  DEFAULT_ATTACK_RANGE,
  FRAME_BOUNDS,
  DEFAULT_FRAME_OFFSETS,
  BATTLE_FRAME_OFFSETS,
  FRAME_WIDTH,
  FRAME_HEIGHT,
  CONTENT_WIDTH,
  CONTENT_HEIGHT,
} from './character.constants';
