import React, {
  forwardRef,
  useCallback,
  useEffect,
  useImperativeHandle,
  useMemo,
  useRef,
  useState,
} from 'react';
import { Animated, PanResponder, StyleSheet, View } from 'react-native';
import type {
  CharacterControllerProps,
  CharacterControllerRef,
  CharacterPoseFamilySlot,
  FacingDirection,
  MonsterTarget,
  VirtualJumpDirection,
} from './character.types';
import { CharacterSprite, characterDisplaySize } from './CharacterSprite';
import {
  DEFAULT_ATTACK_RANGE,
  DEFAULT_SCALE,
  DEFAULT_SPEED,
  SWIPE_THRESHOLD,
} from './character.constants';
import { useCharacterAnimation } from './useCharacterAnimation';
import {
  createJavaMapActorRuntime,
  findSurfaceSupport,
  JAVA_MAP_MAX_STEPS_PER_FRAME,
  JAVA_MAP_TICK_MS,
  JavaMapActorState,
  JavaMoveBit,
  javaCanMoveHorizontally,
  javaFindCeilingBottom,
  javaFindLandingTileTop,
  javaHasGroundSupport,
  javaMoveVectorX,
} from './javaMapMovement';
import { getSurfaceYAtFootX } from './surface';

const ON_MOVE_THROTTLE_PX = 1;
const VISUAL_SMOOTHING_MS = 38;
const DOUBLE_TAP_MS = 260;
const DOUBLE_TAP_DIST = 26;
const JUMP_TRIGGER_RATIO = 0.42;
const LANDING_HOLD_MS = 150;

interface JumpPoseState {
  slot: CharacterPoseFamilySlot;
  frame: number;
}

const inferLevelFromSpeed = (speed: number): number => {
  const javaSpeed = Math.max(4, Math.min(9, Math.round(speed)));
  return (javaSpeed - 4) * 10;
};

/**
 * Java-compatible map controller for runtime side-scroll maps.
 *
 * Sources:
 * - reference/redecoded/cfr_fresh/kl.java: actor fields j/k/s/t, hitbox t/u,
 *   run speed `i = min(9, 4 + lh.G / 10)`, jump/fall cap `a`.
 * - reference/redecoded/cfr_fresh/km.java: state machine 0/1/5/6/7, ground
 *   running, airborne horizontal control, landing transition.
 * - reference/redecoded/cfr_fresh/kf.java + kh.java: original engine is based
 *   on 32x32 collision flags. Until full `kf.d` data is recovered, this
 *   controller uses the Hoa Lư navigation surfaces as a grid adapter while
 *   preserving the Java state/step model.
 */
export const JavaCompatibleCharacterController = forwardRef<CharacterControllerRef, CharacterControllerProps>(({
  initialX,
  positionRevision = 0,
  initialFacing = 'right',
  groundY,
  controlMode = 'swipe',
  level,
  speed = DEFAULT_SPEED,
  scale = DEFAULT_SCALE,
  monsters = [],
  surfaces,
  collisionGrid,
  attackRange = DEFAULT_ATTACK_RANGE,
  onMove,
  onMoveEnd,
  onAttackMonster,
  onAttackEnd,
  minX = 0,
  maxX = 9999,
  containerWidth,
  containerHeight,
  zIndex,
  allowPointerInput = true,
  disabled = false,
  renderSprite,
  spriteSize,
}, ref) => {
  const charSize = useMemo(
    () => spriteSize ?? characterDisplaySize(scale),
    [scale, spriteSize],
  );
  const charGroundOffset = spriteSize?.groundOffset ?? 0;

  const resolvedSurfaces = useMemo(() => (
    surfaces && surfaces.length > 0
      ? surfaces
      : [{ id: 'default-ground', x1: minX, x2: maxX, y: groundY, kind: 'ground' as const }]
  ), [groundY, maxX, minX, surfaces]);

  const javaLevel = level ?? inferLevelFromSpeed(speed);
  const initialRuntimeY = groundY - 32;
  const runtimeRef = useRef(createJavaMapActorRuntime(initialX, initialRuntimeY, javaLevel));
  const posAnim = useRef(new Animated.Value(initialX)).current;
  const groundOffsetAnim = useRef(new Animated.Value(0)).current;
  const posYAnim = useRef(new Animated.Value(0)).current;
  const lastEmittedXRef = useRef(initialX);
  const currentGroundYRef = useRef(groundY);
  const visualYOffsetRef = useRef(0);

  const monstersRef = useRef(monsters);
  const surfacesRef = useRef(resolvedSurfaces);
  const collisionGridRef = useRef(collisionGrid);
  const moveDirection = useRef<FacingDirection | null>(null);
  const moveTargetX = useRef<number | null>(null);
  const pendingAttackMonsterId = useRef<string | null>(null);
  const lastTapRef = useRef<{ time: number; x: number; y: number } | null>(null);
  const rafIdRef = useRef<number | null>(null);
  const lastFrameTimeRef = useRef(0);
  const javaTickAccumulatorRef = useRef(0);
  const isTap = useRef(true);

  const [facing, setFacing] = useState<FacingDirection>(initialFacing);
  const facingRef = useRef<FacingDirection>(initialFacing);
  const [jumpPoseState, setJumpPoseState] = useState<JumpPoseState | null>(null);
  const actionRef = useRef<'idle' | 'run' | 'attack'>('idle');

  const handleAttackFinish = useCallback(() => {
    actionRef.current = 'idle';
    onAttackEnd?.();
  }, [onAttackEnd]);

  const { frameIndex, actionFrameIndex, action, setAction } = useCharacterAnimation({
    onAttackFinish: handleAttackFinish,
  });

  useEffect(() => {
    actionRef.current = action;
  }, [action]);

  useEffect(() => {
    monstersRef.current = monsters;
  }, [monsters]);

  useEffect(() => {
    surfacesRef.current = resolvedSurfaces;
  }, [resolvedSurfaces]);

  useEffect(() => {
    collisionGridRef.current = collisionGrid;
  }, [collisionGrid]);

  useEffect(() => {
    const runtime = runtimeRef.current;
    runtime.i = Math.min(9, 4 + Math.trunc(javaLevel / 10));
    runtime.a = Math.min(16, 11 + Math.trunc(javaLevel / 10));
  }, [javaLevel]);

  const clampX = useCallback((x: number) => (
    Math.max(minX, Math.min(maxX - runtimeRef.current.t.c, x))
  ), [maxX, minX]);

  const setFacingIfChanged = useCallback((dir: FacingDirection) => {
    if (facingRef.current === dir) return;
    facingRef.current = dir;
    runtimeRef.current.facing = dir;
    setFacing(dir);
  }, []);

  const setActionIfChanged = useCallback((nextAction: 'idle' | 'run' | 'attack') => {
    if (actionRef.current === nextAction) return;
    actionRef.current = nextAction;
    setAction(nextAction);
  }, [setAction]);

  const clearMovementLoop = useCallback(() => {
    if (rafIdRef.current !== null) {
      cancelAnimationFrame(rafIdRef.current);
      rafIdRef.current = null;
    }
    lastFrameTimeRef.current = 0;
    javaTickAccumulatorRef.current = 0;
  }, []);

  const emitMove = useCallback((x: number, footY: number, force = false) => {
    if (!force && Math.abs(x - lastEmittedXRef.current) < ON_MOVE_THROTTLE_PX) return;
    lastEmittedXRef.current = x;
    onMove?.(x, facingRef.current, footY);
  }, [onMove]);

  const commitVisualPosition = useCallback((forceEmit = false) => {
    const runtime = runtimeRef.current;
    const leftX = clampX(runtime.t.a);
    if (leftX !== runtime.t.a) {
      runtime.t.a = leftX;
      runtime.u.a = leftX;
    }
    const footY = runtime.t.b + runtime.t.d;
    const yOffset = footY - currentGroundYRef.current;
    Animated.timing(posAnim, {
      toValue: leftX,
      duration: VISUAL_SMOOTHING_MS,
      useNativeDriver: true,
    }).start();

    if (Math.abs(yOffset - visualYOffsetRef.current) >= 0.1) {
      visualYOffsetRef.current = yOffset;
      Animated.timing(posYAnim, {
        toValue: yOffset,
        duration: VISUAL_SMOOTHING_MS,
        useNativeDriver: true,
      }).start();
    }
    emitMove(leftX, footY, forceEmit);
  }, [clampX, emitMove, posAnim, posYAnim]);

  const setGroundYIfChanged = useCallback((nextGroundY: number) => {
    if (Math.abs(nextGroundY - currentGroundYRef.current) < 0.1) return;
    currentGroundYRef.current = nextGroundY;
    groundOffsetAnim.setValue(nextGroundY - groundY);
  }, [groundOffsetAnim, groundY]);

  const finishMovement = useCallback((emitEnd = true) => {
    const runtime = runtimeRef.current;
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    clearMovementLoop();
    runtime.j = JavaMapActorState.Idle;
    runtime.k = 0;
    runtime.s = runtime.a;
    setJumpPoseState(null);
    visualYOffsetRef.current = 0;
    posYAnim.setValue(0);
    if (actionRef.current !== 'attack') {
      setActionIfChanged('idle');
    }
    commitVisualPosition(true);
    if (emitEnd) onMoveEnd?.(runtime.t.a, facingRef.current, runtime.t.b + runtime.t.d);
  }, [clearMovementLoop, commitVisualPosition, onMoveEnd, posYAnim, setActionIfChanged]);

  const findMonsterInRange = useCallback((monsterId?: string): MonsterTarget | null => {
    const cx = runtimeRef.current.t.a + charSize.w / 2;
    let nearest: MonsterTarget | null = null;
    let nearestDist = Infinity;

    for (const monster of monstersRef.current) {
      if (monsterId && monster.id !== monsterId) continue;
      const mx = monster.x + monster.width / 2;
      const dist = Math.abs(cx - mx);
      if (dist < attackRange && dist < nearestDist) {
        nearest = monster;
        nearestDist = dist;
      }
    }

    return nearest;
  }, [attackRange, charSize.w]);

  const findMonsterAtPoint = useCallback((x: number, y: number): MonsterTarget | null => {
    for (const monster of monstersRef.current) {
      if (x >= monster.x && x <= monster.x + monster.width && y >= monster.y && y <= monster.y + monster.height) {
        return monster;
      }
    }
    return null;
  }, []);

  const performAttack = useCallback((preferredTarget?: MonsterTarget | null, triggerMonster = true) => {
    const runtime = runtimeRef.current;
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    clearMovementLoop();
    runtime.j = JavaMapActorState.Attack;
    runtime.k = 0;
    setJumpPoseState(null);
    setActionIfChanged('attack');

    const target = triggerMonster ? (preferredTarget ?? findMonsterInRange()) : preferredTarget;
    if (target) {
      const cx = runtime.t.a + charSize.w / 2;
      const mx = target.x + target.width / 2;
      setFacingIfChanged(mx > cx ? 'right' : 'left');
      onAttackMonster?.(target.id);
    }
  }, [charSize.w, clearMovementLoop, findMonsterInRange, onAttackMonster, setActionIfChanged, setFacingIfChanged]);

  const applyGroundSupportOrFall = useCallback((): boolean => {
    const runtime = runtimeRef.current;
    const grid = collisionGridRef.current;
    if (grid) {
      if (!javaHasGroundSupport(grid, runtime.t)) {
        runtime.j = JavaMapActorState.Falling;
        runtime.s = 0;
        setActionIfChanged('run');
        return false;
      }

      const footY = runtime.t.b + runtime.t.d;
      const landingTop = javaFindLandingTileTop(grid, runtime.t, footY, footY);
      if (landingTop !== null) {
        runtime.t.b = landingTop - runtime.t.d;
        runtime.u.b = runtime.t.b;
        setGroundYIfChanged(landingTop);
      }
      return true;
    }

    const footY = runtime.t.b + runtime.t.d;
    const support = findSurfaceSupport(surfacesRef.current, runtime.t.a, runtime.t.c, footY);
    if (!support) {
      runtime.j = JavaMapActorState.Falling;
      runtime.s = 0;
      setActionIfChanged('run');
      return false;
    }

    const supportY = getSurfaceYAtFootX(support, runtime.t.a, runtime.t.c);
    runtime.t.b = supportY - runtime.t.d;
    runtime.u.b = runtime.t.b;
    setGroundYIfChanged(supportY);
    return true;
  }, [setActionIfChanged, setGroundYIfChanged]);

  const applyHorizontalStep = useCallback((dir: FacingDirection) => {
    const runtime = runtimeRef.current;
    runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;
    runtime.facing = dir;
    setFacingIfChanged(dir);

    const delta = javaMoveVectorX(runtime.k) * runtime.i;
    const nextX = clampX(runtime.t.a + delta);
    const grid = collisionGridRef.current;
    if (grid && !javaCanMoveHorizontally(grid, runtime.t, nextX, dir)) {
      runtime.k = 0;
      return;
    }

    runtime.t.a = nextX;
    runtime.u.a = nextX;
  }, [clampX, scale, setFacingIfChanged]);

  const updateJumpPoseFromJavaState = useCallback(() => {
    const runtime = runtimeRef.current;
    if (runtime.j === JavaMapActorState.JumpRising) {
      setJumpPoseState({ slot: 4, frame: 0 });
      return;
    }
    if (runtime.j === JavaMapActorState.Falling) {
      setJumpPoseState({ slot: 4, frame: runtime.s > runtime.a / 2 ? 1 : 0 });
      return;
    }
    if (runtime.j === JavaMapActorState.Landing) {
      setJumpPoseState({ slot: 4, frame: 2 });
      return;
    }
    setJumpPoseState(null);
  }, []);

  const startMovementLoop = useCallback(() => {
    if (rafIdRef.current !== null || disabled) return;

    let landingTicks = Math.max(1, Math.ceil(LANDING_HOLD_MS / JAVA_MAP_TICK_MS));

    const runJavaStep = (): boolean => {
      const runtime = runtimeRef.current;

      const queuedMonsterId = pendingAttackMonsterId.current;
      if (queuedMonsterId && runtime.j !== JavaMapActorState.JumpRising && runtime.j !== JavaMapActorState.Falling) {
        const inRange = findMonsterInRange(queuedMonsterId);
        if (inRange) {
          performAttack(inRange);
          return false;
        }

        const queuedMonster = monstersRef.current.find((m) => m.id === queuedMonsterId);
        if (queuedMonster) {
          moveTargetX.current = clampX(queuedMonster.x + queuedMonster.width / 2 - charSize.w / 2);
        }
      }

      if (runtime.j === JavaMapActorState.JumpRising) {
        const nextY = runtime.t.b - runtime.s;
        const grid = collisionGridRef.current;
        const ceilingBottom = grid ? javaFindCeilingBottom(grid, runtime.t, nextY) : null;
        if (ceilingBottom !== null) {
          runtime.t.b = ceilingBottom;
          runtime.u.b = runtime.t.b;
          runtime.s = 0;
          runtime.j = JavaMapActorState.Falling;
        } else {
          runtime.t.b = nextY;
          runtime.u.b = runtime.t.b;
          runtime.s -= 1;
          if (runtime.s <= 0) {
            runtime.s = 0;
            runtime.j = JavaMapActorState.Falling;
          }
        }
        if (moveDirection.current) {
          applyHorizontalStep(moveDirection.current);
        }
      } else if (runtime.j === JavaMapActorState.Falling) {
        const previousFootY = runtime.t.b + runtime.t.d;
        runtime.t.b += runtime.s;
        runtime.u.b = runtime.t.b;
        runtime.s = Math.min(runtime.a, runtime.s + 2);

        const projectedFootY = runtime.t.b + runtime.t.d;
        const grid = collisionGridRef.current;
        const landingTop = grid
          ? javaFindLandingTileTop(grid, runtime.t, previousFootY, projectedFootY)
          : null;
        if (landingTop !== null) {
          runtime.t.b = landingTop - runtime.t.d;
          runtime.u.b = runtime.t.b;
          setGroundYIfChanged(landingTop);
          runtime.j = JavaMapActorState.Landing;
          runtime.s = runtime.a;
          landingTicks = Math.max(1, Math.ceil(LANDING_HOLD_MS / JAVA_MAP_TICK_MS));
        } else if (!grid) {
          const support = findSurfaceSupport(surfacesRef.current, runtime.t.a, runtime.t.c, previousFootY, Math.max(32, projectedFootY - previousFootY + 4));
          if (support) {
            const supportY = getSurfaceYAtFootX(support, runtime.t.a, runtime.t.c);
            if (projectedFootY >= supportY) {
              runtime.t.b = supportY - runtime.t.d;
              runtime.u.b = runtime.t.b;
              setGroundYIfChanged(supportY);
              runtime.j = JavaMapActorState.Landing;
              runtime.s = runtime.a;
              landingTicks = Math.max(1, Math.ceil(LANDING_HOLD_MS / JAVA_MAP_TICK_MS));
            }
          }
        }

        if (moveDirection.current) {
          applyHorizontalStep(moveDirection.current);
        }
      } else if (runtime.j === JavaMapActorState.Landing) {
        landingTicks -= 1;
        if (landingTicks <= 0) {
          runtime.j = JavaMapActorState.Idle;
          setJumpPoseState(null);
          if (moveDirection.current || moveTargetX.current !== null || pendingAttackMonsterId.current) {
            setActionIfChanged('run');
          } else {
            finishMovement();
            return false;
          }
        }
      } else if (controlMode === 'tap-to-move' && moveTargetX.current !== null) {
        const targetX = moveTargetX.current;
        const deltaToTarget = targetX - runtime.t.a;
        if (Math.abs(deltaToTarget) <= runtime.i) {
          runtime.t.a = clampX(targetX);
          runtime.u.a = runtime.t.a;
          applyGroundSupportOrFall();
          finishMovement();
          return false;
        }

        const dir: FacingDirection = deltaToTarget > 0 ? 'right' : 'left';
        runtime.j = JavaMapActorState.Running;
        applyHorizontalStep(dir);
        applyGroundSupportOrFall();
      } else if (moveDirection.current) {
        runtime.j = JavaMapActorState.Running;
        applyHorizontalStep(moveDirection.current);
        applyGroundSupportOrFall();
      } else {
        finishMovement();
        return false;
      }

      return true;
    };

    const tick = (now: number) => {
      const last = lastFrameTimeRef.current;
      const dt = last === 0 ? JAVA_MAP_TICK_MS : Math.min(now - last, JAVA_MAP_TICK_MS * JAVA_MAP_MAX_STEPS_PER_FRAME);
      lastFrameTimeRef.current = now;
      javaTickAccumulatorRef.current += dt;

      let steps = 0;
      let keepRunning = true;
      while (javaTickAccumulatorRef.current >= JAVA_MAP_TICK_MS && steps < JAVA_MAP_MAX_STEPS_PER_FRAME && keepRunning) {
        javaTickAccumulatorRef.current -= JAVA_MAP_TICK_MS;
        keepRunning = runJavaStep();
        steps += 1;
      }

      if (!keepRunning) return;

      updateJumpPoseFromJavaState();
      commitVisualPosition();

      rafIdRef.current = requestAnimationFrame(tick);
    };

    lastFrameTimeRef.current = 0;
    javaTickAccumulatorRef.current = JAVA_MAP_TICK_MS;
    rafIdRef.current = requestAnimationFrame(tick);
  }, [
    applyGroundSupportOrFall,
    applyHorizontalStep,
    charSize.w,
    clampX,
    commitVisualPosition,
    controlMode,
    disabled,
    findMonsterInRange,
    finishMovement,
    performAttack,
    scale,
    setActionIfChanged,
    setGroundYIfChanged,
    updateJumpPoseFromJavaState,
  ]);

  const startMoving = useCallback((dir: FacingDirection) => {
    if (disabled || actionRef.current === 'attack') return;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    moveDirection.current = dir;
    setFacingIfChanged(dir);

    const runtime = runtimeRef.current;
    const isAirborne = runtime.j === JavaMapActorState.Falling || runtime.j === JavaMapActorState.JumpRising;
    runtime.j = isAirborne ? runtime.j : JavaMapActorState.Running;
    runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;

    // Java km.java state 5/6 vẫn đọc phím trái/phải mỗi tick khi đang ở trên không.
    // Nếu người chơi bấm hướng sau khi đã nhảy, cập nhật k ngay để tick hiện tại
    // không bị giữ k=0 khiến nhân vật đứng yên tại X cũ cho tới lần input khác.
    if (isAirborne) {
      applyHorizontalStep(dir);
      commitVisualPosition();
    }

    setActionIfChanged('run');
    startMovementLoop();
  }, [
    applyHorizontalStep,
    commitVisualPosition,
    disabled,
    setActionIfChanged,
    setFacingIfChanged,
    startMovementLoop,
  ]);

  const stopMoving = useCallback(() => {
    moveDirection.current = null;
    moveTargetX.current = null;
    pendingAttackMonsterId.current = null;
    const runtime = runtimeRef.current;
    if (runtime.j === JavaMapActorState.JumpRising || runtime.j === JavaMapActorState.Falling) return;
    finishMovement();
  }, [finishMovement]);

  const startJump = useCallback((direction: VirtualJumpDirection = 'up') => {
    if (disabled || actionRef.current === 'attack') return;
    const runtime = runtimeRef.current;
    if (runtime.j === JavaMapActorState.JumpRising || runtime.j === JavaMapActorState.Falling) return;

    if (direction === 'left' || direction === 'right') {
      moveDirection.current = direction;
      setFacingIfChanged(direction);
    }

    runtime.j = JavaMapActorState.JumpRising;
    runtime.s = runtime.a;
    runtime.k = direction === 'left'
      ? JavaMoveBit.Left
      : direction === 'right'
        ? JavaMoveBit.Right
        : 0;
    setActionIfChanged('run');
    setJumpPoseState({ slot: 3, frame: 0 });
    startMovementLoop();
  }, [disabled, setActionIfChanged, setFacingIfChanged, startMovementLoop]);

  const moveToX = useCallback((rawTargetX: number) => {
    if (disabled || actionRef.current === 'attack') return;
    const targetLeft = clampX(rawTargetX - charSize.w / 2);
    const runtime = runtimeRef.current;
    const currentCenterX = runtime.t.a + charSize.w / 2;
    const dir: FacingDirection = rawTargetX >= currentCenterX ? 'right' : 'left';
    const isAirborne = runtime.j === JavaMapActorState.Falling || runtime.j === JavaMapActorState.JumpRising;

    setFacingIfChanged(dir);
    moveTargetX.current = targetLeft;
    pendingAttackMonsterId.current = null;

    if (isAirborne) {
      // Tap-to-move trong lúc nhảy phải biến thành air-control hướng X,
      // không được đổi j=Running vì sẽ cắt state 5/6 của Java và làm đứng/giật.
      moveDirection.current = dir;
      runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;
      applyHorizontalStep(dir);
      commitVisualPosition();
    } else {
      moveDirection.current = null;
      runtime.j = JavaMapActorState.Running;
    }

    setActionIfChanged('run');
    startMovementLoop();
  }, [
    applyHorizontalStep,
    charSize.w,
    clampX,
    commitVisualPosition,
    disabled,
    setActionIfChanged,
    setFacingIfChanged,
    startMovementLoop,
  ]);

  const moveToMonster = useCallback((monster: MonsterTarget) => {
    const targetCenterX = monster.x + monster.width / 2;
    const runtime = runtimeRef.current;
    const dir: FacingDirection = targetCenterX >= runtime.t.a + charSize.w / 2 ? 'right' : 'left';
    const isAirborne = runtime.j === JavaMapActorState.Falling || runtime.j === JavaMapActorState.JumpRising;

    pendingAttackMonsterId.current = monster.id;
    moveTargetX.current = clampX(targetCenterX - charSize.w / 2);
    setFacingIfChanged(dir);

    if (isAirborne) {
      moveDirection.current = dir;
      runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;
      applyHorizontalStep(dir);
      commitVisualPosition();
    } else {
      moveDirection.current = null;
      runtime.j = JavaMapActorState.Running;
    }

    setActionIfChanged('run');
    startMovementLoop();
  }, [
    applyHorizontalStep,
    charSize.w,
    clampX,
    commitVisualPosition,
    setActionIfChanged,
    setFacingIfChanged,
    startMovementLoop,
  ]);

  const isPointOnCharacter = useCallback((x: number, y: number) => {
    const top = currentGroundYRef.current - charSize.h + charGroundOffset + visualYOffsetRef.current;
    const left = runtimeRef.current.t.a;
    return x >= left && x <= left + charSize.w && y >= top && y <= top + charSize.h;
  }, [charGroundOffset, charSize.h, charSize.w]);

  const shouldJumpToPoint = useCallback((x: number, y: number) => {
    const top = currentGroundYRef.current - charSize.h + charGroundOffset + visualYOffsetRef.current;
    return y < top + charSize.h * JUMP_TRIGGER_RATIO && !isPointOnCharacter(x, y);
  }, [charGroundOffset, charSize.h, isPointOnCharacter]);

  const handleTapToMove = useCallback((x: number, y: number) => {
    if (disabled) return;

    const now = Date.now();
    const lastTap = lastTapRef.current;
    const isDoubleTap = lastTap !== null
      && now - lastTap.time <= DOUBLE_TAP_MS
      && Math.abs(lastTap.x - x) <= DOUBLE_TAP_DIST
      && Math.abs(lastTap.y - y) <= DOUBLE_TAP_DIST;

    if (isPointOnCharacter(x, y) || isDoubleTap) {
      lastTapRef.current = null;
      performAttack(null, false);
      return;
    }

    lastTapRef.current = { time: now, x, y };

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

    if (shouldJumpToPoint(x, y)) {
      const centerX = runtimeRef.current.t.a + charSize.w / 2;
      if (x < centerX - charSize.w * 0.25) {
        startJump('left');
      } else if (x > centerX + charSize.w * 0.25) {
        startJump('right');
      } else {
        startJump('up');
      }
      return;
    }

    moveToX(x);
  }, [
    charSize.w,
    disabled,
    findMonsterAtPoint,
    findMonsterInRange,
    isPointOnCharacter,
    moveToMonster,
    moveToX,
    performAttack,
    shouldJumpToPoint,
    startJump,
  ]);

  useImperativeHandle(ref, () => ({
    startMove: startMoving,
    stopMove: stopMoving,
    jump: startJump,
    attack: () => performAttack(),
    face: setFacingIfChanged,
  }), [performAttack, setFacingIfChanged, startJump, startMoving, stopMoving]);

  const panResponder = useMemo(() => (
    PanResponder.create({
      onStartShouldSetPanResponder: () => !disabled && allowPointerInput,
      onMoveShouldSetPanResponder: (_, gesture) =>
        !disabled && allowPointerInput && controlMode === 'swipe' && Math.abs(gesture.dx) > SWIPE_THRESHOLD,
      onPanResponderGrant: () => {
        isTap.current = true;
      },
      onPanResponderMove: (_, gesture) => {
        if (disabled || !allowPointerInput || controlMode !== 'swipe') return;
        if (Math.abs(gesture.dx) > SWIPE_THRESHOLD) {
          isTap.current = false;
          startMoving(gesture.dx > 0 ? 'right' : 'left');
        }
      },
      onPanResponderRelease: (evt) => {
        if (disabled || !allowPointerInput) return;
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
        if (!allowPointerInput) return;
        stopMoving();
      },
    })
  ), [allowPointerInput, controlMode, disabled, handleTapToMove, performAttack, startMoving, stopMoving]);

  useEffect(() => {
    const clampedX = clampX(initialX);
    const runtime = runtimeRef.current;
    runtime.t.a = clampedX;
    runtime.u.a = clampedX;
    runtime.t.b = groundY - runtime.t.d;
    runtime.u.b = runtime.t.b;
    runtime.j = JavaMapActorState.Idle;
    runtime.k = 0;
    runtime.s = runtime.a;
    runtime.facing = initialFacing;
    currentGroundYRef.current = groundY;
    visualYOffsetRef.current = 0;
    lastEmittedXRef.current = clampedX;
    posAnim.setValue(clampedX);
    posYAnim.setValue(0);
    groundOffsetAnim.setValue(0);
    setFacing(initialFacing);
    facingRef.current = initialFacing;
    setJumpPoseState(null);
    onMove?.(clampedX, initialFacing, groundY);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [positionRevision]);

  useEffect(() => {
    if (disabled) stopMoving();
  }, [disabled, stopMoving]);

  useEffect(() => () => clearMovementLoop(), [clearMovementLoop]);

  const charTop = groundY - charSize.h + charGroundOffset;

  const gestureStyle = useMemo(
    () => [styles.gestureLayer, { width: containerWidth, height: containerHeight, zIndex }],
    [containerHeight, containerWidth, zIndex],
  );

  const wrapperStyle = useMemo(
    () => [
      styles.characterWrapper,
      {
        top: charTop,
        transform: [{ translateX: posAnim }, { translateY: Animated.add(groundOffsetAnim, posYAnim) }],
      },
    ],
    [charTop, groundOffsetAnim, posAnim, posYAnim],
  );

  const spriteNode = renderSprite
    ? renderSprite({
      action,
      actionFrameIndex,
      frameIndex,
      facing,
      scale,
      poseFamilySlot: jumpPoseState?.slot,
      poseFrameIndex: jumpPoseState?.frame,
    })
    : (
      <CharacterSprite
        frameIndex={frameIndex}
        facing={facing}
        scale={scale}
      />
    );

  return (
    <View style={gestureStyle} {...panResponder.panHandlers}>
      <Animated.View style={wrapperStyle} pointerEvents="none">
        {spriteNode}
      </Animated.View>
    </View>
  );
});

JavaCompatibleCharacterController.displayName = 'JavaCompatibleCharacterController';

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