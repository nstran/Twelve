/**
 * CharacterController.tsx
 *
 * Supports two side-scrolling control styles:
 * - swipe: drag left/right to keep running until release
 * - tap-to-move: tap a point or monster to run there, then attack in range
 */

import React, { useRef, useCallback, useEffect, useMemo, useState } from 'react';
import { View, PanResponder, StyleSheet } from 'react-native';
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
  const [posX, setPosX] = useState(initialX);
  const [facing, setFacing] = useState<FacingDirection>('right');
  const posXRef = useRef(initialX);
  const facingRef = useRef<FacingDirection>('right');
  const actionRef = useRef<'idle' | 'run' | 'attack'>('idle');
  const monstersRef = useRef(monsters);
  const moveDirection = useRef<'left' | 'right' | null>(null);
  const moveTargetX = useRef<number | null>(null);
  const pendingAttackMonsterId = useRef<string | null>(null);
  const moveTickRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const isTap = useRef(true);

  const charSize = characterDisplaySize(scale);

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
    if (moveTickRef.current) {
      clearInterval(moveTickRef.current);
      moveTickRef.current = null;
    }
  }, []);

  const finishMovement = useCallback((x: number) => {
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    clearMovementLoop();

    if (actionRef.current !== 'attack') {
      actionRef.current = 'idle';
      setAction('idle');
    }
    onMoveEnd?.(x, facingRef.current);
  }, [clearMovementLoop, onMoveEnd, setAction]);

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
      const dir: FacingDirection = mx > cx ? 'right' : 'left';
      facingRef.current = dir;
      setFacing(dir);

      onAttackMonster?.(target.id);
    }
  }, [charSize.w, clearMovementLoop, findMonsterInRange, onAttackMonster, setAction]);

  const updatePosition = useCallback((nextX: number) => {
    posXRef.current = nextX;
    setPosX(nextX);
    onMove?.(nextX, facingRef.current);
  }, [onMove]);

  const startMovementLoop = useCallback(() => {
    if (moveTickRef.current || disabled) return;

    moveTickRef.current = setInterval(() => {
      const queuedMonsterId = pendingAttackMonsterId.current;
      if (queuedMonsterId) {
        const target = findMonsterInRange(queuedMonsterId);
        if (target) {
          performAttack(target);
          return;
        }

        const queuedMonster = monstersRef.current.find((monster) => monster.id === queuedMonsterId);
        if (queuedMonster) {
          const queuedCenterX = queuedMonster.x + queuedMonster.width / 2;
          moveTargetX.current = clampX(queuedCenterX - charSize.w / 2);
        }
      }

      if (controlMode === 'tap-to-move' && moveTargetX.current !== null) {
        const targetX = moveTargetX.current;
        const deltaToTarget = targetX - posXRef.current;

        if (Math.abs(deltaToTarget) <= speed) {
          updatePosition(targetX);
          finishMovement(targetX);
          return;
        }

        const dir: FacingDirection = deltaToTarget > 0 ? 'right' : 'left';
        facingRef.current = dir;
        setFacing(dir);
        const currentX = posXRef.current;
        const nextX = clampX(currentX + (dir === 'right' ? speed : -speed));
        updatePosition(nextX);

        if (nextX === currentX) {
          finishMovement(nextX);
        }

        return;
      }

      if (moveDirection.current) {
        const delta = moveDirection.current === 'right' ? speed : -speed;
        const currentX = posXRef.current;
        const nextX = clampX(currentX + delta);

        if (nextX === currentX) {
          finishMovement(nextX);
          return;
        }

        updatePosition(nextX);
        return;
      }

      finishMovement(posXRef.current);
    }, MOVE_TICK_MS);
  }, [
    charSize.w,
    clampX,
    controlMode,
    disabled,
    findMonsterInRange,
    finishMovement,
    performAttack,
    speed,
    updatePosition,
  ]);

  const startMoving = useCallback((dir: 'left' | 'right') => {
    if (disabled || actionRef.current === 'attack') return;

    pendingAttackMonsterId.current = null;
    moveTargetX.current = null;
    moveDirection.current = dir;
    facingRef.current = dir;
    setFacing(dir);
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [disabled, setAction, startMovementLoop]);

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
    facingRef.current = delta > 0 ? 'right' : 'left';
    setFacing(facingRef.current);
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [charSize.w, clampX, disabled, finishMovement, setAction, speed, startMovementLoop, updatePosition]);

  const moveToMonster = useCallback((monster: MonsterTarget) => {
    const targetCenterX = monster.x + monster.width / 2;
    pendingAttackMonsterId.current = monster.id;
    moveTargetX.current = clampX(targetCenterX - charSize.w / 2);
    moveDirection.current = null;
    facingRef.current = targetCenterX >= posXRef.current + charSize.w / 2 ? 'right' : 'left';
    setFacing(facingRef.current);
    actionRef.current = 'run';
    setAction('run');
    startMovementLoop();
  }, [charSize.w, clampX, setAction, startMovementLoop]);

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

  useEffect(() => {
    posXRef.current = initialX;
    setPosX(initialX);
  }, [initialX]);

  useEffect(() => {
    if (disabled) {
      stopMoving();
    }
  }, [disabled, stopMoving]);

  useEffect(() => () => clearMovementLoop(), [clearMovementLoop]);

  const charTop = groundY - charSize.h;

  return (
    <View
      style={[styles.gestureLayer, { width: containerWidth, height: containerHeight }]}
      {...panResponder.panHandlers}
    >
      <View
        style={[
          styles.characterWrapper,
          { left: posX, top: charTop },
        ]}
        pointerEvents="none"
      >
        <CharacterSprite
          frameIndex={frameIndex}
          facing={facing}
          scale={scale}
        />
      </View>
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
  },
});
