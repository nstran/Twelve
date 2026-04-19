/**
 * useCharacterAnimation.ts
 *
 * Hook that manages the character animation loop.
 * Returns the current frameIndex based on the active action.
 *
 * - idle: holds on frame 0
 * - run: holds on frame 1
 * - attack: plays a fixed frame timeline, then returns to idle
 */

import { useRef, useEffect, useCallback, useState } from 'react';
import type { CharacterAction } from './character.types';
import { ACTION_FRAME_DURATIONS, ANIM_FRAMES } from './character.constants';

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
  const [attackCycleId, setAttackCycleId] = useState(0);
  const rafRef = useRef<number | null>(null);
  const currentStepRef = useRef(0);

  // Cleanup all timers
  const cleanup = useCallback(() => {
    if (rafRef.current !== null) {
      cancelAnimationFrame(rafRef.current);
      rafRef.current = null;
    }
  }, []);

  const commitStep = useCallback((act: CharacterAction, nextStep: number) => {
    if (currentStepRef.current === nextStep) return;
    currentStepRef.current = nextStep;
    setActionFrameIndex(nextStep);
    const frames = ANIM_FRAMES[act];
    setFrameIndex(frames[nextStep % frames.length]);
  }, []);

  const resolveFrameStep = useCallback((elapsedMs: number, durations: readonly number[]) => {
    let remaining = elapsedMs;

    for (let i = 0; i < durations.length; i += 1) {
      if (remaining < durations[i]) {
        return i;
      }
      remaining -= durations[i];
    }

    return durations.length - 1;
  }, []);

  // Public API: change action
  const setAction = useCallback((newAction: CharacterAction) => {
    if (newAction === 'attack') {
      setAttackCycleId((current) => current + 1);
      setActionState('attack');
      return;
    }

    setActionState(newAction);
  }, []);

  // React to action changes
  useEffect(() => {
    cleanup();

    const durations = ACTION_FRAME_DURATIONS[action];
    const totalDuration = durations.reduce((sum, duration) => sum + duration, 0);
    const initialFrame = ANIM_FRAMES[action][0];
    currentStepRef.current = 0;
    setFrameIndex(initialFrame);
    setActionFrameIndex(0);

    if (durations.length === 0 || totalDuration <= 0) {
      return cleanup;
    }

    let startTime: number | null = null;
    let cancelled = false;

    const tick = (now: number) => {
      if (cancelled) return;

      if (startTime === null) {
        startTime = now;
      }

      const elapsed = now - startTime;

      if (action === 'attack' && elapsed >= totalDuration) {
        commitStep(action, durations.length - 1);
        setActionState('idle');
        onAttackFinish?.();
        return;
      }

      const loopElapsed = action === 'attack'
        ? elapsed
        : (elapsed % totalDuration);
      const nextStep = resolveFrameStep(loopElapsed, durations);
      commitStep(action, nextStep);
      rafRef.current = requestAnimationFrame(tick);
    };

    rafRef.current = requestAnimationFrame(tick);

    return () => {
      cancelled = true;
      cleanup();
    };
  }, [action, attackCycleId, cleanup, commitStep, onAttackFinish, resolveFrameStep]);

  // Cleanup on unmount
  useEffect(() => cleanup, [cleanup]);

  return { frameIndex, actionFrameIndex, action, setAction };
}
