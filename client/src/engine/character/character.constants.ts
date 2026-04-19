/**
 * character.constants.ts
 * Placeholder geometry and animation configuration used while
 * the original character asset pack is removed.
 */

import type { CharacterAction } from './character.types';

// ── Sprite Sheet Dimensions ───────────────────────────────────────────────
export const SHEET_WIDTH = 612;
export const SHEET_HEIGHT = 408;
export const FRAME_COUNT = 4;
export const FRAME_WIDTH = SHEET_WIDTH / FRAME_COUNT; // 153px
export const FRAME_HEIGHT = SHEET_HEIGHT; // 408px

/** Inclusive crop bounds for each frame: x=left..right, y=top..bottom */
export const FRAME_BOUNDS = [
  { left: 46, right: 125, top: 132, bottom: 247 }, // idle
  { left: -20, right: 122, top: 140, bottom: 246 }, // run
  { left: -20, right: 132, top: 135, bottom: 245 },  // attack_windup
  { left: 0, right: 132, top: 136, bottom: 246 },  // attack_slash
] as const;

/** Optional per-frame placement offsets (in sheet px before scale). */
export const DEFAULT_FRAME_OFFSETS = [
  { x: 0, y: 0 },
  { x: 0, y: 0 },
  { x: 0, y: 0 },
  { x: 0, y: 0 },
] as const;

/**
 * Battle preset keeps the character anchored more consistently while
 * idle/run/attack frames have different empty margins in the source sheet.
 */
export const BATTLE_FRAME_OFFSETS = [
  { x: -18, y: 0 }, // idle: pull left to reduce visible left padding
  { x: -2, y: 0 },  // run
  { x: 18, y: 0 },  // attack_windup
  { x: -2, y: 0 },  // attack_slash
] as const;

export const CONTENT_LEFT = Math.min(...FRAME_BOUNDS.map((bounds) => bounds.left));
export const CONTENT_RIGHT = Math.max(...FRAME_BOUNDS.map((bounds) => bounds.right)) + 1;
export const CONTENT_TOP = Math.min(...FRAME_BOUNDS.map((bounds) => bounds.top));
export const CONTENT_BOTTOM = Math.max(...FRAME_BOUNDS.map((bounds) => bounds.bottom)) + 1;
export const CONTENT_WIDTH = CONTENT_RIGHT - CONTENT_LEFT;
export const CONTENT_HEIGHT = CONTENT_BOTTOM - CONTENT_TOP;

/** Offset from frame bottom to character feet (for ground alignment) */
export const GROUND_OFFSET = SHEET_HEIGHT - CONTENT_BOTTOM; // 160px transparent below

// ── Animation Frame Sequences ─────────────────────────────────────────────
export const ANIM_FRAMES: Record<CharacterAction, readonly number[]> = {
  idle: [0],           // single frame: standing with sword
  run: [1],            // dedicated running frame
  attack: [2, 3],      // cycle: windup → slash → repeat
} as const;

/** Legacy create-character compositor exposes 3 main action families:
 *  slot 0 = idle (2 frames), slot 1 = run (6 frames), slot 2 = attack (4 frames).
 */
export const ACTION_FRAME_COUNTS: Record<CharacterAction, number> = {
  idle: 2,
  run: 6,
  attack: 4,
};

// ── Animation Timing (ms per frame) ──────────────────────────────────────
export const ANIM_SPEED: Record<CharacterAction, number> = {
  idle: 500,    // slow breathing cycle
  run: 140,     // keep footsteps visible even when map speed is very low
  attack: 120,  // faster attack response
};

export const ACTION_FRAME_DURATIONS: Record<CharacterAction, readonly number[]> = {
  idle: [420, 420],
  run: [82, 82, 82, 82, 82, 82],
  attack: [42, 52, 64, 78],
};

/** How long the full attack animation lasts before returning to idle */
export const ATTACK_DURATION = ACTION_FRAME_DURATIONS.attack.reduce((sum, duration) => sum + duration, 0); // ~236ms

// ── Movement ──────────────────────────────────────────────────────────────
/**
 * Reference speed: pixels per MOVE_TICK_MS frame at 60fps.
 * Actual per-frame step is scaled by delta-time in the rAF loop so speed
 * stays stable when frame rate dips (e.g. speed * dt / MOVE_TICK_MS).
 */
export const DEFAULT_SPEED = 3.4;        // px per reference frame (60fps)
export const MOVE_TICK_MS = 16;          // reference frame duration (~60fps)
export const SWIPE_THRESHOLD = 10;       // min px to register as swipe (not tap)
export const DEFAULT_ATTACK_RANGE = 60;  // px distance to trigger attack

// ── Display ───────────────────────────────────────────────────────────────
export const DEFAULT_SCALE = 1.8;
