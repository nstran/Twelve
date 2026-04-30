/**
 * character.types.ts
 * Type definitions for the character sprite system.
 */

import type { ReactNode } from 'react';
import type { JavaMapCollisionGrid } from './javaMapMovement';

// ── Animation States ──────────────────────────────────────────────────────
export type CharacterAction = 'idle' | 'run' | 'attack';
export type CharacterPoseFamilySlot = 0 | 1 | 2 | 3 | 4 | 7 | 8 | 9;

// ── Facing Direction ──────────────────────────────────────────────────────
export type FacingDirection = 'left' | 'right';
export type CharacterPlacementPreset = 'default' | 'battle';

// ── Input / Control Mode ──────────────────────────────────────────────────
export type CharacterControlMode = 'swipe' | 'tap-to-move';
export type VirtualJumpDirection = FacingDirection | 'up';

// ── Monster info for proximity detection ──────────────────────────────────
export interface MonsterTarget {
  id: string;
  x: number;
  y: number;
  width: number;
  height: number;
  /**
   * Java-inspired/reconstructed runtime collision box. Visual sprite sheets can
   * contain alpha padding; when present these dimensions represent the actor
   * body used for touch/proximity checks instead of the full visual rectangle.
   */
  collisionWidth?: number;
  collisionHeight?: number;
  /** Runtime ground line used to bottom-anchor the reconstructed monster box. */
  groundY?: number;
}

export interface GroundSurface {
  id: string;
  x1: number;
  x2: number;
  /**
   * Flat surface height. Use this for horizontal ground/platform segments.
   */
  y?: number;
  /**
   * Optional sloped segment endpoints. When either value is provided, the
   * controller linearly interpolates the foot Y from x1->x2.
   */
  y1?: number;
  y2?: number;
  /**
   * Optional thickness below the walkable line.
   * Used for head collision while jumping under solid surfaces.
   */
  ceilingOffset?: number;
  kind?: 'ground' | 'platform';
  oneWay?: boolean;
}

// ── Character position & state ────────────────────────────────────────────
export interface CharacterState {
  x: number;
  y: number;
  action: CharacterAction;
  facing: FacingDirection;
  frameIndex: number;
}

// ── CharacterSprite props ─────────────────────────────────────────────────
export interface CharacterSpriteProps {
  /** Current frame index (0-3) */
  frameIndex: number;
  /** Character facing direction */
  facing: FacingDirection;
  /** Display scale multiplier (default: 2.0) */
  scale?: number;
  /** Optional per-scene sprite alignment preset */
  placementPreset?: CharacterPlacementPreset;
}

export interface CharacterRenderSpriteArgs {
  action: CharacterAction;
  actionFrameIndex: number;
  frameIndex: number;
  facing: FacingDirection;
  scale: number;
  poseFamilySlot?: CharacterPoseFamilySlot;
  poseFrameIndex?: number;
}

export interface CharacterControllerRef {
  startMove: (direction: FacingDirection) => void;
  stopMove: () => void;
  jump: (direction?: VirtualJumpDirection) => void;
  attack: () => void;
  face: (direction: FacingDirection) => void;
}

// ── CharacterController props ─────────────────────────────────────────────
export interface CharacterControllerProps {
  /** Initial X position (pixel) */
  initialX: number;
  /**
   * Increment only when the parent intentionally respawns/teleports the actor.
   * Prevents server echo packets from resetting local Java-style movement mid-jump.
   */
  positionRevision?: number;
  /** Initial facing direction */
  initialFacing?: FacingDirection;
  /** Ground Y position (pixel, bottom of character) */
  groundY: number;
  /** Input mode for the controller */
  controlMode?: CharacterControlMode;
  /** Character level used by Java map formulas: kl.i / kl.a */
  level?: number;
  /** Movement speed in pixels per frame; used only as fallback to infer Java level when `level` is absent. */
  speed?: number;
  /** Display scale */
  scale?: number;
  /** List of monsters to detect proximity & attack */
  monsters?: MonsterTarget[];
  /** Walkable surface segments for platforming maps */
  surfaces?: GroundSurface[];
  /**
   * Java-compatible 32x32 collision flag grid (`kf.d`).
   * When provided, movement/collision uses Java tile probing first and only
   * falls back to `surfaces` when the grid cannot resolve support.
   */
  collisionGrid?: JavaMapCollisionGrid;
  /** Distance threshold to trigger attack range (px) */
  attackRange?: number;
  /**
   * Called when character position changes.
   * `footY` is Java runtime hitbox bottom (`kl.t.b + kl.t.d`) in map pixels.
   */
  onMove?: (x: number, facing: FacingDirection, footY?: number) => void;
  /**
   * Called when character reaches the requested movement target.
   * `footY` is Java runtime hitbox bottom (`kl.t.b + kl.t.d`) in map pixels.
   */
  onMoveEnd?: (x: number, facing: FacingDirection, footY?: number) => void;
  /** Called when character attacks a monster */
  onAttackMonster?: (monsterId: string) => void;
  /** Called when attack animation finishes */
  onAttackEnd?: () => void;
  /** Map boundary: min X */
  minX?: number;
  /** Map boundary: max X */
  maxX?: number;
  /** Container width for gesture area */
  containerWidth: number;
  /** Container height for gesture area */
  containerHeight: number;
  /** Optional visual layer priority for the controller wrapper */
  zIndex?: number;
  /** Disable direct pointer/touch gestures on the character layer */
  allowPointerInput?: boolean;
  /** Disable movement / attacks temporarily */
  disabled?: boolean;
  /** Optional custom sprite renderer for modular / skinned characters */
  renderSprite?: (args: CharacterRenderSpriteArgs) => ReactNode;
  /** Override the collision / placement size used by the controller */
  spriteSize?: { w: number; h: number; groundOffset?: number };
}
