/**
 * character.types.ts
 * Type definitions for the character sprite system.
 */

// ── Animation States ──────────────────────────────────────────────────────
export type CharacterAction = 'idle' | 'run' | 'attack';

// ── Facing Direction ──────────────────────────────────────────────────────
export type FacingDirection = 'left' | 'right';
export type CharacterPlacementPreset = 'default' | 'battle';

// ── Input / Control Mode ──────────────────────────────────────────────────
export type CharacterControlMode = 'swipe' | 'tap-to-move';

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
}
