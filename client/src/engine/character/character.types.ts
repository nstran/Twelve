/**
 * character.types.ts
 * Type definitions for the character sprite system.
 */

import type { ReactNode } from 'react';

// ── Animation States ──────────────────────────────────────────────────────
export type CharacterAction = 'idle' | 'run' | 'attack';
export type CharacterPoseFamilySlot = 0 | 1 | 2 | 3 | 4;

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
}

// ── CharacterController props ─────────────────────────────────────────────
export interface CharacterControllerProps {
  /** Initial X position (pixel) */
  initialX: number;
  /** Ground Y position (pixel, bottom of character) */
  groundY: number;
  /** Input mode for the controller */
  controlMode?: CharacterControlMode;
  /** Movement speed in pixels per frame */
  speed?: number;
  /** Display scale */
  scale?: number;
  /** List of monsters to detect proximity & attack */
  monsters?: MonsterTarget[];
  /** Distance threshold to trigger attack range (px) */
  attackRange?: number;
  /** Called when character position changes */
  onMove?: (x: number, facing: FacingDirection) => void;
  /** Called when character reaches the requested movement target */
  onMoveEnd?: (x: number, facing: FacingDirection) => void;
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
  /** Disable movement / attacks temporarily */
  disabled?: boolean;
  /** Optional custom sprite renderer for modular / skinned characters */
  renderSprite?: (args: CharacterRenderSpriteArgs) => ReactNode;
  /** Override the collision / placement size used by the controller */
  spriteSize?: { w: number; h: number };
}
