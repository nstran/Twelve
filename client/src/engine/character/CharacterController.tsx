/**
 * CharacterController.tsx
 *
 * Optimized side-scrolling controller.
 *
 * Performance strategy (to eliminate movement jank):
 * - requestAnimationFrame loop (vsync-locked, no setInterval drift)
 * - Animated.Value + translateX with useNativeDriver → position updates
 *   bypass React reconciliation entirely (compositor thread on native,
 *   rAF on web)
 * - Delta-time based stepping → speed is stable regardless of frame drops
 * - onMove fires only on "meaningful" movement (throttle by pixel delta)
 *   to avoid thrashing scrollToCharacter on the JS thread
 * - Facing/action state only updates on real change (rare events)
 *
 * Supports two control styles:
 * - swipe: drag left/right to keep running until release
 * - tap-to-move: tap a point or monster to run there, then attack in range
 */

import React, { useRef, useCallback, useEffect, useMemo, useState } from 'react';
import { Animated, View, PanResponder, StyleSheet } from 'react-native';
import type {
  CharacterControllerProps,
  FacingDirection,
  MonsterTarget,
} from './character.types';
import { CharacterSprite, characterDisplaySize } from './CharacterSprite';
import { useCharacterAnimation } from './useCharacterAnimation';
import {
  DEFAULT_SPEED,
  DEFAULT_ATTACK_RANGE,
  DEFAULT_SCALE,
  MOVE_TICK_MS,
  SWIPE_THRESHOLD,
} from './character.constants';

/**
 * Minimum movement (in px) before onMove is forwarded to the parent.
 * Camera/scroll logic doesn't need sub-pixel updates; this halves the
 * work the JS thread does during a long run.
 */
const ON_MOVE_THROTTLE_PX = 1;

export const CharacterController: React.FC<CharacterControllerProps> = ({
  initialX,
  groundY,
  controlMode = 'swipe',
  speed = DEFAULT_SPEED,
  scale = DEFAULT_SCALE,
  monsters = [],
  attackRange = DEFAULT_ATTACK_RANGE,
  onMove,
  onMoveEnd,
  onAttackMonster,
  onAttackEnd,
  minX = 0,
  maxX = 9999,
  containerWidth,
  containerHeight,
  disabled = false,
}) => {
  // ── Position is animated value (native-driven translateX). ──────────────
  // posXRef keeps the authoritative numeric value for reads (physics, AI).
  const posXRef = useRef(initialX);
  const posAnim = useRef(new Animated.Value(initialX)).current;
  const lastEmittedXRef = useRef(initialX);

  // Facing is rare state change — keep in React state so sprite flips.
  const [facing, setFacing] = useState<FacingDirection>('right');
  const facingRef = useRef<FacingDirection>('right');

  const actionRef = useRef<'idle' | 'run' | 'attack'>('idle');
  const monstersRef = useRef(monsters);
  const moveDirection = useRef<'left' | 'right' | null>(null);
  const moveTargetX = useRef<number | null>(null);
  const pendingAttackMonsterId = useRef<string | null>(null);

  // rAF loop state
  const rafIdRef = useRef<number | null>(null);
  const lastFrameTimeRef = useRef<number>(0);

  const isTap = useRef(true);

  const charSize = useMemo(() => characterDisplaySize(scale), [scale]);

  const { frameIndex, action, setAction } = useCharacterAnimation({
    onAttackFinish: () => {
      actionRef.current = 'idle';
      onAttackEnd?.();
    },
  });

  useEffect(() => {
    actionRef.current = action;
  }, [action]);

  useEffect(() => {
    monstersRef.current = monsters;
  }, [monsters]);

  const clampX = useCallback((x: number) => (
    Math.max(minX, Math.min(maxX - charSize.w, x))
  ), [charSize.w, maxX, minX]);

  const findMonsterInRange = useCallback((monsterId?: string): MonsterTarget | null => {
    const cx = posXRef.current + charSize.w / 2;
    let nearest: MonsterTarget | null = null;
    let nearestDist = Infinity;

    for (const m of monstersRef.current) {
      if (monsterId && m.id !== monsterId) continue;
      const mx = m.x + m.width / 2;
      const dist = Math.abs(cx - mx);
      if (dist < attackRange && dist < nearestDist) {
        nearest = m;
        nearestDist = dist;
      }
    }

    return nearest;
  }, [attackRange, charSize.w]);

  const findMonsterAtPoint = useCallback((x: number, y: number): MonsterTarget | null => {
    for (const monster of monstersRef.current) {
      const withinX = x >= monster.x && x <= monster.x + monster.width;
      const withinY = y >= monster.y && y <= monster.y + monster.height;
      if (withinX && withinY) {
        return monster;
      }
    }

    return null;
  }, []);

  const clearMovementLoop = useCallback(() => {
    if (rafIdRef.current !== null) {
      cancelAnimationFrame(rafIdRef.current);
      rafIdRef.current = null;
    }
    lastFrameTimeRef.current = 0;
  }, []);

  const setFacingIfChanged = useCallback((dir: FacingDirection) => {
    if (facingRef.current === dir) return;
    facingRef.current = dir;
    setFacing(dir);
  }, []);

  /**
   * Commits a new position: updates the animated value (native driver),
   * the numeric ref, and throttles the onMove callback to avoid
   * re-rendering parents every frame.
   */
  const updatePosition = useCallback((nextX: number) => {
    if (nextX === posXRef.current) return;
    posXRef.current = nextX;
    posAnim.setValue(nextX);

    if (onMove && Math.abs(nextX - lastEmittedXRef.current) >= ON_MOVE_THROTTLE_PX) {
      lastEmittedXRef.current = nextX;
      onMove(nextX, facingRef.current);
    }
  }, [onMove, posAnim]);

  const finishMovement = useCallback((x: number) => {
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    clearMovementLoop();

    if (actionRef.current !== 'attack') {
      actionRef.current = 'idle';
      setAction('idle');
    }
    // Always emit final position so camera snaps exactly.
    lastEmittedXRef.current = x;
    onMove?.(x, facingRef.current);
    onMoveEnd?.(x, facingRef.current);
  }, [clearMovementLoop, onMove, onMoveEnd, setAction]);

  const performAttack = useCallback((preferredTarget?: MonsterTarget | null) => {
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    clearMovementLoop();
    actionRef.current = 'attack';
    setAction('attack');

    const target = preferredTarget ?? findMonsterInRange();
    if (target) {
      const cx = posXRef.current + charSize.w / 2;
      const mx = target.x + target.width / 2;
      setFacingIfChanged(mx > cx ? 'right' : 'left');
      onAttackMonster?.(target.id);
    }
  }, [charSize.w, clearMovementLoop, findMonsterInRange, onAttackMonster, setAction, setFacingIfChanged]);

  const startMovementLoop = useCallback(() => {
    if (rafIdRef.current !== null || disabled) return;

    const tick = (now: number) => {
      // Delta-time: keeps speed stable across FPS variance.
      const last = lastFrameTimeRef.current;
      const dt = last === 0 ? MOVE_TICK_MS : Math.min(now - last, 64); // clamp to avoid giant steps after tab backgrounding
      lastFrameTimeRef.current = now;

      const stepPx = speed * (dt / MOVE_TICK_MS);

      // 1. Attack-on-reach check: if queued monster is now in range, attack.
      const queuedMonsterId = pendingAttackMonsterId.current;
      if (queuedMonsterId) {
        const inRange = findMonsterInRange(queuedMonsterId);
        if (inRange) {
          performAttack(inRange);
          return;
        }

        // Monster may have moved — refresh the target X.
        const queuedMonster = monstersRef.current.find((m) => m.id === queuedMonsterId);
        if (queuedMonster) {
          const centerX = queuedMonster.x + queuedMonster.width / 2;
          moveTargetX.current = clampX(centerX - charSize.w / 2);
        }
      }

      // 2. Tap-to-move (moving toward a fixed target X).
      if (controlMode === 'tap-to-move' && moveTargetX.current !== null) {
        const targetX = moveTargetX.current;
        const deltaToTarget = targetX - posXRef.current;

        if (Math.abs(deltaToTarget) <= stepPx) {
          updatePosition(targetX);
          finishMovement(targetX);
          return;
        }

        const dir: FacingDirection = deltaToTarget > 0 ? 'right' : 'left';
        setFacingIfChanged(dir);

        const currentX = posXRef.current;
        const nextX = clampX(currentX + (dir === 'right' ? stepPx : -stepPx));
        updatePosition(nextX);

        if (nextX === currentX) {
          finishMovement(nextX);
          return;
        }

        rafIdRef.current = requestAnimationFrame(tick);
        return;
      }

      // 3. Swipe (continuous direction until release).
      if (moveDirection.current) {
        const delta = moveDirection.current === 'right' ? stepPx : -stepPx;
        const currentX = posXRef.current;
        const nextX = clampX(currentX + delta);

        if (nextX === currentX) {
          finishMovement(nextX);
          return;
        }

        updatePosition(nextX);
        rafIdRef.current = requestAnimationFrame(tick);
        return;
      }

      // No active intent → stop.
      finishMovement(posXRef.current);
    };

    lastFrameTimeRef.current = 0;
    rafIdRef.current = requestAnimationFrame(tick);
  }, [
    charSize.w,
    clampX,
    controlMode,
    disabled,
    findMonsterInRange,
    finishMovement,
    performAttack,
    setFacingIfChanged,
    speed,
    updatePosition,
  ]);

  const startMoving = useCallback((dir: 'left' | 'right') => {
    if (disabled || actionRef.current === 'attack') return;

    pendingAttackMonsterId.current = null;
    moveTargetX.current = null;
    moveDirection.current = dir;
    setFacingIfChanged(dir);
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [disabled, setAction, setFacingIfChanged, startMovementLoop]);

  const moveToX = useCallback((rawTargetX: number) => {
    if (disabled || actionRef.current === 'attack') return;

    const targetLeft = clampX(rawTargetX - charSize.w / 2);
    const delta = targetLeft - posXRef.current;

    if (Math.abs(delta) <= speed) {
      updatePosition(targetLeft);
      finishMovement(targetLeft);
      return;
    }

    moveTargetX.current = targetLeft;
    moveDirection.current = null;
    pendingAttackMonsterId.current = null;
    setFacingIfChanged(delta > 0 ? 'right' : 'left');
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [charSize.w, clampX, disabled, finishMovement, setAction, setFacingIfChanged, speed, startMovementLoop, updatePosition]);

  const moveToMonster = useCallback((monster: MonsterTarget) => {
    const targetCenterX = monster.x + monster.width / 2;
    pendingAttackMonsterId.current = monster.id;
    moveTargetX.current = clampX(targetCenterX - charSize.w / 2);
    moveDirection.current = null;
    setFacingIfChanged(targetCenterX >= posXRef.current + charSize.w / 2 ? 'right' : 'left');
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [charSize.w, clampX, setAction, setFacingIfChanged, startMovementLoop]);

  const stopMoving = useCallback(() => {
    pendingAttackMonsterId.current = null;
    moveDirection.current = null;
    moveTargetX.current = null;
    clearMovementLoop();

    if (actionRef.current !== 'attack') {
      actionRef.current = 'idle';
      setAction('idle');
    }
  }, [clearMovementLoop, setAction]);

  const handleTapToMove = useCallback((x: number, y: number) => {
    if (disabled) return;

    const tappedMonster = findMonsterAtPoint(x, y);
    if (tappedMonster) {
      const inRangeMonster = findMonsterInRange(tappedMonster.id);
      if (inRangeMonster) {
        performAttack(inRangeMonster);
      } else {
        moveToMonster(tappedMonster);
      }
      return;
    }

    moveToX(x);
  }, [disabled, findMonsterAtPoint, findMonsterInRange, moveToMonster, moveToX, performAttack]);

  const panResponder = useMemo(() => (
    PanResponder.create({
      onStartShouldSetPanResponder: () => !disabled,
      onMoveShouldSetPanResponder: (_, gesture) =>
        !disabled && controlMode === 'swipe' && Math.abs(gesture.dx) > SWIPE_THRESHOLD,

      onPanResponderGrant: () => {
        isTap.current = true;
      },

      onPanResponderMove: (_, gesture) => {
        if (disabled || controlMode !== 'swipe') return;

        if (Math.abs(gesture.dx) > SWIPE_THRESHOLD) {
          isTap.current = false;
          const dir = gesture.dx > 0 ? 'right' : 'left';
          if (moveDirection.current !== dir) {
            startMoving(dir);
          }
        }
      },

      onPanResponderRelease: (evt) => {
        if (disabled) return;

        if (controlMode === 'tap-to-move') {
          handleTapToMove(evt.nativeEvent.locationX, evt.nativeEvent.locationY);
          return;
        }

        if (isTap.current) {
          performAttack();
        } else {
          stopMoving();
        }
      },

      onPanResponderTerminate: () => {
        stopMoving();
      },
    })
  ), [controlMode, disabled, handleTapToMove, performAttack, startMoving, stopMoving]);

  // External initialX changes (e.g. scene reset) — snap instantly.
  useEffect(() => {
    posXRef.current = initialX;
    lastEmittedXRef.current = initialX;
    posAnim.setValue(initialX);
  }, [initialX, posAnim]);

  useEffect(() => {
    if (disabled) {
      stopMoving();
    }
  }, [disabled, stopMoving]);

  useEffect(() => () => clearMovementLoop(), [clearMovementLoop]);

  const charTop = groundY - charSize.h;

  // Style objects are memoized to avoid allocating new objects each render.
  const gestureStyle = useMemo(
    () => [styles.gestureLayer, { width: containerWidth, height: containerHeight }],
    [containerWidth, containerHeight],
  );

  const wrapperStyle = useMemo(
    () => [
      styles.characterWrapper,
      {
        top: charTop,
        transform: [{ translateX: posAnim }],
      },
    ],
    [charTop, posAnim],
  );

  return (
    <View style={gestureStyle} {...panResponder.panHandlers}>
      <Animated.View style={wrapperStyle} pointerEvents="none">
        <CharacterSprite
          frameIndex={frameIndex}
          facing={facing}
          scale={scale}
        />
      </Animated.View>
    </View>
  );
};

const styles = StyleSheet.create({
  gestureLayer: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
  characterWrapper: {
    position: 'absolute',
    left: 0,
  },
});
