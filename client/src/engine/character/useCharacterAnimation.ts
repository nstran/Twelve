/**
 * useCharacterAnimation.ts
 *
 * Hook that manages the character animation loop.
 * Returns the current frameIndex based on the active action.
 *
 * - idle: holds on frame 0
 * - run: holds on frame 1
 * - attack: cycles [2, 3] for ATTACK_DURATION then calls onFinish
 */

import { useRef, useEffect, useCallback, useState } from 'react';
import type { CharacterAction } from './character.types';
import { ACTION_FRAME_COUNTS, ANIM_FRAMES, ANIM_SPEED, ATTACK_DURATION } from './character.constants';

interface UseCharacterAnimationOptions {
  /** Called when attack animation completes */
  onAttackFinish?: () => void;
}

interface UseCharacterAnimationReturn {
  /** Current frame index to render (0-3) */
  frameIndex: number;
  /** Frame index within the active action family (legacy compositor slot frame) */
  actionFrameIndex: number;
  /** Current action state */
  action: CharacterAction;
  /** Transition to a new action */
  setAction: (action: CharacterAction) => void;
}

export function useCharacterAnimation(
  options: UseCharacterAnimationOptions = {}
): UseCharacterAnimationReturn {
  const { onAttackFinish } = options;

  const [action, setActionState] = useState<CharacterAction>('idle');
  const [frameIndex, setFrameIndex] = useState(0);
  const [actionFrameIndex, setActionFrameIndex] = useState(0);

  // Track the sequence step within current animation
  const seqIndex = useRef(0);
  const intervalRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const attackTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  // Cleanup all timers
  const cleanup = useCallback(() => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
    if (attackTimerRef.current) {
      clearTimeout(attackTimerRef.current);
      attackTimerRef.current = null;
    }
  }, []);

  // Start animation loop for a given action
  const startAnimation = useCallback((act: CharacterAction) => {
    cleanup();
    seqIndex.current = 0;

    const frames = ANIM_FRAMES[act];
    const speed = ANIM_SPEED[act];
    const actionFrameCount = ACTION_FRAME_COUNTS[act];

    // Set initial frame immediately
    setFrameIndex(frames[0]);
    setActionFrameIndex(0);

    // If only 1 frame (idle), no need for interval
    if (frames.length <= 1 && actionFrameCount <= 1) return;

    intervalRef.current = setInterval(() => {
      seqIndex.current = (seqIndex.current + 1) % actionFrameCount;
      setActionFrameIndex(seqIndex.current);
      setFrameIndex(frames[seqIndex.current % frames.length]);
    }, speed);
  }, [cleanup]);

  // Public API: change action
  const setAction = useCallback((newAction: CharacterAction) => {
    setActionState((prev) => {
      // Don't interrupt attack unless switching to different action
      if (prev === 'attack' && newAction === 'attack') return prev;
      return newAction;
    });
  }, []);

  // React to action changes
  useEffect(() => {
    startAnimation(action);

    // Auto-return to idle after attack finishes
    if (action === 'attack') {
      attackTimerRef.current = setTimeout(() => {
        setActionState('idle');
        onAttackFinish?.();
      }, ATTACK_DURATION);
    }

    return cleanup;
  }, [action, startAnimation, cleanup, onAttackFinish]);

  // Cleanup on unmount
  useEffect(() => cleanup, [cleanup]);

  return { frameIndex, actionFrameIndex, action, setAction };
}
