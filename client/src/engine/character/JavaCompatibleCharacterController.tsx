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
import type { JavaMapActorRuntime } from './javaMapMovement';
import {
  createJavaMapActorRuntime,
  findSurfaceSupport,
  JAVA_MAP_MAX_STEPS_PER_FRAME,
  JAVA_MAP_TICK_MS,
  JavaMapActorState,
  JavaMoveBit,
  javaCanClimbAtRect,
  javaCanMoveHorizontally,
  javaFindCeilingBottom,
  javaFindGroundYForRect,
  javaFindLandingTileTop,
  javaHasGroundSupport,
  javaMoveVectorX,
  javaMoveVectorY,
} from './javaMapMovement';
import { getSurfaceYAtFootX } from './surface';

const ON_MOVE_THROTTLE_PX = 1;
/**
 * Java client advances map actors on fixed ticks (km.java), but old J2ME draws
 * the actor immediately after each fixed update. The previous RN port tweened
 * every 40ms tick with Animated.timing; on mobile this can visually "float" or
 * rubber-band because a new native animation is started before the previous
 * tick visually settles. Keep physics Java-tick based, but render a lightweight
 * visual-only interpolation between the previous and latest fixed-step actor
 * positions. Collision, range checks and map logic still read the Java runtime
 * rect `kl.t`; interpolation never feeds back into gameplay state.
 *
 * Source: Java-inspired/reconstructed policy from decompiled km/mh actor loop.
 */
const USE_INTERPOLATED_JAVA_VISUAL_COMMIT = false;
/**
 * Java-inspired/reconstructed visual policy for vertical motion:
 * keep ground running committed directly after each Java fixed-step (closer to
 * J2ME draw-after-update), but blend jump/fall Y offset between 40ms physics
 * ticks. Collision and encounter logic still read runtime `kl.t`; this only
 * smooths the rendered sprite during airborne states where Java's per-tick
 * vertical deltas are visually chunky on RN/web refresh rates.
 */
const USE_AIRBORNE_Y_VISUAL_INTERPOLATION = true;
/**
 * Java-inspired/reconstructed visual policy for air-control:
 * when the actor is in Java airborne states (`j=5/6`), horizontal runtime X
 * still advances only on fixed 40ms ticks, but the rendered sprite may blend
 * from the previous visual X to the latest runtime X. This keeps jump + left/right
 * from looking like a staircase on 60fps RN/web while collision/monster triggers
 * continue to read the authoritative compact `kl.t` runtime rect.
 */
const USE_AIRBORNE_X_VISUAL_INTERPOLATION = true;
/**
 * Temporary movement diagnostics for Hoa Lư jitter investigation.
 * Keep this lightweight and throttled so the player can move while Metro logs
 * expose whether jitter comes from runtime X, ground snapping, render offset,
 * parent position reset, or frame/tick cadence.
 *
 * Source: Java-inspired/reconstructed debugging policy for map runtime parity.
 */
const DEBUG_JAVA_MOVEMENT_JITTER = false;
const DEBUG_JAVA_MOVEMENT_LOG_MS = 1000;
const DOUBLE_TAP_MS = 260;
const DOUBLE_TAP_DIST = 26;
const JUMP_TRIGGER_RATIO = 0.42;
const LANDING_HOLD_MS = 90;
/**
 * Remake tuning for Hoa Lư tap/swipe runtime:
 * Java `kl.a` is still the base value (`min(16, 11 + level / 10)`), but the
 * React Native playable scene is authored with taller visual gaps/platforms
 * than the recovered 32x32 Java collision grid. Use this multiplier only for
 * the initial upward impulse until exact legacy map tile data is recovered.
 */
const DEFAULT_JUMP_IMPULSE_MULTIPLIER = 1.15;
/**
 * Java-inspired/reconstructed vertical pacing for the authored RN Hoa Lư scene.
 * Java still owns the state machine (`j=5/6`), velocity counter `s`, gravity
 * increment and landing checks; only the screen-space Y delta is damped because
 * the recovered RN map/sprite scale makes raw Java px/tick jump/fall read too
 * fast without the exact legacy map render scale.
 */
const AIRBORNE_VERTICAL_DELTA_RATIO = 0.82;
const TOUCH_PAD = 4;
const ACTOR_TOUCH_PAD_X = 5;
const ACTOR_TOUCH_PAD_Y = 4;
const JAVA_ACTOR_RUNTIME_WIDTH = 17;

interface VisualInterpolationFrame {
  fromX: number;
  toX: number;
  fromYOffset: number;
  toYOffset: number;
  startedAt: number;
  durationMs: number;
}

/**
 * Java-inspired/reconstructed policy for map-space player body math.
 * Java uses compact runtime rect `kl.t` (17x32) for movement/collision and a
 * wider `kl.u` (26x32) auxiliary rect. Do not use rendered sprite width for
 * target centering/range decisions because the sprite contains visual padding.
 */
const getActorRuntimeCenterX = (runtime: JavaMapActorRuntime): number => (
  runtime.t.a + runtime.t.c / 2
);

const getActorRuntimeFootY = (runtime: JavaMapActorRuntime): number => (
  runtime.t.b + runtime.t.d
);

const getActorVisualLeft = (runtime: JavaMapActorRuntime, visualWidth: number): number => (
  getActorRuntimeCenterX(runtime) - visualWidth / 2
);

interface ActorRuntimeTouchBox {
  left: number;
  right: number;
  top: number;
  bottom: number;
}

/**
 * Java-inspired/reconstructed policy from `kl.t`/`kl.u`:
 * direct self taps should target the compact actor runtime body, not the full
 * rendered sprite. Use the wider auxiliary rect `u` when available because Java
 * keeps it as the interaction/body envelope around the 17px movement rect.
 */
const getActorRuntimeTouchBox = (runtime: JavaMapActorRuntime): ActorRuntimeTouchBox => {
  const runtimeCenterX = getActorRuntimeCenterX(runtime);
  const width = Math.max(runtime.t.c, runtime.u.c);
  const left = runtimeCenterX - width / 2;

  return {
    left: left - ACTOR_TOUCH_PAD_X,
    right: left + width + ACTOR_TOUCH_PAD_X,
    top: runtime.t.b - ACTOR_TOUCH_PAD_Y,
    bottom: getActorRuntimeFootY(runtime) + ACTOR_TOUCH_PAD_Y,
  };
};

interface MonsterRuntimeBox {
  left: number;
  right: number;
  top: number;
  bottom: number;
  centerX: number;
}

const getMonsterRuntimeBox = (monster: MonsterTarget): MonsterRuntimeBox => {
  const width = monster.collisionWidth ?? Math.max(18, Math.round(monster.width * 0.52));
  const height = monster.collisionHeight ?? Math.max(18, Math.round(monster.height * 0.68));
  const visualCenterX = monster.x + monster.width / 2;
  const bottom = monster.groundY ?? (monster.y + monster.height);
  const left = visualCenterX - width / 2;
  const top = bottom - height;
  return {
    left,
    right: left + width,
    top,
    bottom,
    centerX: visualCenterX,
  };
};

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
  const initialRuntimeX = initialX + charSize.w / 2 - JAVA_ACTOR_RUNTIME_WIDTH / 2;
  const initialRuntimeY = groundY - 32;
  const runtimeRef = useRef(createJavaMapActorRuntime(initialRuntimeX, initialRuntimeY, javaLevel));
  const posAnim = useRef(new Animated.Value(initialX)).current;
  const groundOffsetAnim = useRef(new Animated.Value(0)).current;
  const posYAnim = useRef(new Animated.Value(0)).current;
  const lastEmittedXRef = useRef(initialX);
  const currentGroundYRef = useRef(groundY);
  const visualLeftXRef = useRef(initialX);
  const visualYOffsetRef = useRef(0);
  const visualInterpolationRef = useRef<VisualInterpolationFrame | null>(null);

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
  const debugLastMoveLogAtRef = useRef(0);
  const debugLastGroundLogAtRef = useRef(0);
  const debugTickFrameRef = useRef(0);
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

  const renderInterpolatedVisualPosition = useCallback((now: number): void => {
    const frame = visualInterpolationRef.current;
    if (!frame) return;

    const alpha = Math.min(1, Math.max(0, (now - frame.startedAt) / frame.durationMs));
    const nextX = frame.fromX + (frame.toX - frame.fromX) * alpha;
    const nextYOffset = frame.fromYOffset + (frame.toYOffset - frame.fromYOffset) * alpha;

    visualLeftXRef.current = nextX;
    visualYOffsetRef.current = nextYOffset;
    posAnim.setValue(nextX);
    posYAnim.setValue(nextYOffset);

    if (alpha >= 1) {
      visualInterpolationRef.current = null;
    }
  }, [posAnim, posYAnim]);

  const commitVisualPosition = useCallback((forceEmit = false, frameNow?: number) => {
    const runtime = runtimeRef.current;
    const leftX = clampX(runtime.t.a);
    if (leftX !== runtime.t.a) {
      runtime.t.a = leftX;
      runtime.u.a = leftX;
    }
    const footY = getActorRuntimeFootY(runtime);
    const visualLeftX = getActorVisualLeft(runtime, charSize.w);
    const yOffset = footY - currentGroundYRef.current;

    if (forceEmit) {
      // Hard commit only for spawn/stop/landing endpoints. During continuous
      // movement, renderInterpolatedVisualPosition() blends frames on rAF while
      // Java runtime/collision remains fixed-step and authoritative.
      visualInterpolationRef.current = null;
      posAnim.stopAnimation();
      posAnim.setValue(visualLeftX);
      posYAnim.stopAnimation();
      posYAnim.setValue(yOffset);
      visualLeftXRef.current = visualLeftX;
      visualYOffsetRef.current = yOffset;
    } else if (
      USE_INTERPOLATED_JAVA_VISUAL_COMMIT ||
      (
        USE_AIRBORNE_Y_VISUAL_INTERPOLATION &&
        (
          runtime.j === JavaMapActorState.JumpRising ||
          runtime.j === JavaMapActorState.Falling
        )
      )
    ) {
      const now = frameNow ?? Date.now();
      const isAirborne = runtime.j === JavaMapActorState.JumpRising ||
        runtime.j === JavaMapActorState.Falling;
      const shouldInterpolateX = USE_INTERPOLATED_JAVA_VISUAL_COMMIT ||
        (USE_AIRBORNE_X_VISUAL_INTERPOLATION && isAirborne);
      const fromX = shouldInterpolateX ? visualLeftXRef.current : visualLeftX;
      const fromYOffset = visualYOffsetRef.current;
      const toX = visualLeftX;
      const dx = Math.abs(toX - fromX);
      const dy = Math.abs(yOffset - fromYOffset);

      if (dx < 0.1 && dy < 0.1) {
        visualInterpolationRef.current = null;
        posAnim.setValue(visualLeftX);
        posYAnim.setValue(yOffset);
        visualLeftXRef.current = visualLeftX;
        visualYOffsetRef.current = yOffset;
      } else {
        if (!shouldInterpolateX) {
          posAnim.setValue(visualLeftX);
          visualLeftXRef.current = visualLeftX;
        }

        visualInterpolationRef.current = {
          fromX,
          toX,
          fromYOffset,
          toYOffset: yOffset,
          startedAt: now,
          durationMs: JAVA_MAP_TICK_MS,
        };
        renderInterpolatedVisualPosition(now);
      }
    } else {
      visualInterpolationRef.current = null;
      posAnim.setValue(visualLeftX);
      posYAnim.setValue(yOffset);
      visualLeftXRef.current = visualLeftX;
      visualYOffsetRef.current = yOffset;
    }
    emitMove(visualLeftX, footY, forceEmit);
  }, [charSize.w, clampX, emitMove, posAnim, posYAnim, renderInterpolatedVisualPosition]);

  const setGroundYIfChanged = useCallback((nextGroundY: number) => {
    const previousGroundY = currentGroundYRef.current;
    if (Math.abs(nextGroundY - previousGroundY) < 0.1) return;
    currentGroundYRef.current = nextGroundY;
    groundOffsetAnim.setValue(nextGroundY - groundY);

    if (DEBUG_JAVA_MOVEMENT_JITTER) {
      const now = Date.now();
      if (now - debugLastGroundLogAtRef.current >= DEBUG_JAVA_MOVEMENT_LOG_MS) {
        debugLastGroundLogAtRef.current = now;
        const runtime = runtimeRef.current;
        console.log('[JavaMoveDebug][ground-snap]', {
          prevGroundY: Number(previousGroundY.toFixed(2)),
          nextGroundY: Number(nextGroundY.toFixed(2)),
          delta: Number((nextGroundY - previousGroundY).toFixed(2)),
          runtimeX: Number(runtime.t.a.toFixed(2)),
          runtimeY: Number(runtime.t.b.toFixed(2)),
          footY: Number(getActorRuntimeFootY(runtime).toFixed(2)),
          state: runtime.j,
          hasGrid: Boolean(collisionGridRef.current),
        });
      }
    }
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
    if (emitEnd) onMoveEnd?.(getActorVisualLeft(runtime, charSize.w), facingRef.current, getActorRuntimeFootY(runtime));
  }, [charSize.w, clearMovementLoop, commitVisualPosition, onMoveEnd, posYAnim, setActionIfChanged]);

  const findMonsterInRange = useCallback((monsterId?: string): MonsterTarget | null => {
    const runtime = runtimeRef.current;
    const cx = getActorRuntimeCenterX(runtime);
    const actorFootY = getActorRuntimeFootY(runtime);
    let nearest: MonsterTarget | null = null;
    let nearestDist = Infinity;

    for (const monster of monstersRef.current) {
      if (monsterId && monster.id !== monsterId) continue;

      const box = getMonsterRuntimeBox(monster);
      if (monster.groundY !== undefined) {
        const verticalRange = Math.max(32, box.bottom - box.top);
        if (Math.abs(actorFootY - box.bottom) > verticalRange) continue;
      }

      const dist = Math.abs(cx - box.centerX);
      if (dist < attackRange && dist < nearestDist) {
        nearest = monster;
        nearestDist = dist;
      }
    }

    return nearest;
  }, [attackRange]);

  const findMonsterAtPoint = useCallback((x: number, y: number): MonsterTarget | null => {
    for (const monster of monstersRef.current) {
      const box = getMonsterRuntimeBox(monster);
      if (
        x >= box.left - TOUCH_PAD &&
        x <= box.right + TOUCH_PAD &&
        y >= box.top - TOUCH_PAD &&
        y <= box.bottom + TOUCH_PAD
      ) {
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
      const cx = getActorRuntimeCenterX(runtime);
      const mx = getMonsterRuntimeBox(target).centerX;
      setFacingIfChanged(mx > cx ? 'right' : 'left');
      onAttackMonster?.(target.id);
    }
  }, [clearMovementLoop, findMonsterInRange, onAttackMonster, setActionIfChanged, setFacingIfChanged]);

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

      const groundAtRect = javaFindGroundYForRect(grid, runtime.t, 3);
      if (groundAtRect !== null) {
        runtime.t.b = groundAtRect - runtime.t.d;
        runtime.u.b = runtime.t.b;
        setGroundYIfChanged(groundAtRect);
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

    if (grid && runtime.j === JavaMapActorState.Running) {
      // Java-inspired/reconstructed slope follow from km/kf/kh:
      // after horizontal movement, snap compact runtime rect `kl.t` to the
      // current tile surface, including diagonal kh.d/kh.l tiles.
      const groundAtRect = javaFindGroundYForRect(grid, runtime.t, 8);
      if (groundAtRect !== null) {
        runtime.t.b = groundAtRect - runtime.t.d;
        runtime.u.b = runtime.t.b;
        setGroundYIfChanged(groundAtRect);
      }
    }
  }, [clampX, setFacingIfChanged, setGroundYIfChanged]);

  const updateJumpPoseFromJavaState = useCallback(() => {
    const runtime = runtimeRef.current;
    if (runtime.j === JavaMapActorState.Climb || runtime.j === JavaMapActorState.ClimbEnter || runtime.j === JavaMapActorState.DropTransition) {
      setJumpPoseState({ slot: 7, frame: 0 });
      return;
    }
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
          moveTargetX.current = clampX(getMonsterRuntimeBox(queuedMonster).centerX - runtime.t.c / 2);
        }
      }

      if (runtime.j === JavaMapActorState.Climb || runtime.j === JavaMapActorState.ClimbEnter || runtime.j === JavaMapActorState.DropTransition) {
        const grid = collisionGridRef.current;
        if (!grid || !javaCanClimbAtRect(grid, runtime.t)) {
          runtime.j = JavaMapActorState.Falling;
          runtime.s = 0;
        } else {
          const dy = javaMoveVectorY(runtime.k) * runtime.i;
          runtime.t.b += dy;
          runtime.u.b = runtime.t.b;
          setGroundYIfChanged(getActorRuntimeFootY(runtime));
          setActionIfChanged('run');
        }
      } else if (runtime.j === JavaMapActorState.JumpRising) {
        const riseDelta = Math.max(1, Math.round(runtime.s * AIRBORNE_VERTICAL_DELTA_RATIO));
        const nextY = runtime.t.b - riseDelta;
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
        const fallDelta = Math.max(1, Math.round(runtime.s * AIRBORNE_VERTICAL_DELTA_RATIO));
        runtime.t.b += fallDelta;
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

      if (steps > 0) {
        updateJumpPoseFromJavaState();
        commitVisualPosition(false, now);

        if (DEBUG_JAVA_MOVEMENT_JITTER) {
          const logNow = Date.now();
          if (logNow - debugLastMoveLogAtRef.current >= DEBUG_JAVA_MOVEMENT_LOG_MS) {
            debugLastMoveLogAtRef.current = logNow;
            debugTickFrameRef.current += 1;

            const runtime = runtimeRef.current;
            const footY = getActorRuntimeFootY(runtime);
            const visualLeftX = getActorVisualLeft(runtime, charSize.w);
            const yOffset = footY - currentGroundYRef.current;

            console.log('[JavaMoveDebug][tick]', {
              frame: debugTickFrameRef.current,
              dt: Number(dt.toFixed(2)),
              steps,
              accumulator: Number(javaTickAccumulatorRef.current.toFixed(2)),
              state: runtime.j,
              moveDir: moveDirection.current,
              targetX: moveTargetX.current === null ? null : Number(moveTargetX.current.toFixed(2)),
              runtimeX: Number(runtime.t.a.toFixed(2)),
              runtimeY: Number(runtime.t.b.toFixed(2)),
              runtimeW: runtime.t.c,
              runtimeH: runtime.t.d,
              visualLeftX: Number(visualLeftX.toFixed(2)),
              footY: Number(footY.toFixed(2)),
              currentGroundY: Number(currentGroundYRef.current.toFixed(2)),
              yOffset: Number(yOffset.toFixed(2)),
              visualYOffset: Number(visualYOffsetRef.current.toFixed(2)),
              groundOffset: Number((currentGroundYRef.current - groundY).toFixed(2)),
              facing: facingRef.current,
              hasGrid: Boolean(collisionGridRef.current),
            });
          }
        }
      }

      renderInterpolatedVisualPosition(now);
      rafIdRef.current = requestAnimationFrame(tick);
    };

    lastFrameTimeRef.current = 0;
    javaTickAccumulatorRef.current = JAVA_MAP_TICK_MS;
    rafIdRef.current = requestAnimationFrame(tick);
  }, [
    applyGroundSupportOrFall,
    applyHorizontalStep,
    clampX,
    commitVisualPosition,
    controlMode,
    renderInterpolatedVisualPosition,
    disabled,
    findMonsterInRange,
    finishMovement,
    performAttack,
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
    // Chỉ cập nhật direction bitmask ở đây; horizontal step vẫn chạy trong fixed
    // Java tick để tránh double-step/snap X ngay tại frame input, còn render X
    // được blend bởi USE_AIRBORNE_X_VISUAL_INTERPOLATION.
    setActionIfChanged('run');
    startMovementLoop();
  }, [
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
    runtime.s = Math.max(runtime.a, Math.round(runtime.a * DEFAULT_JUMP_IMPULSE_MULTIPLIER));
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
    const runtime = runtimeRef.current;
    const targetLeft = clampX(rawTargetX - runtime.t.c / 2);
    const currentCenterX = getActorRuntimeCenterX(runtime);
    const dir: FacingDirection = rawTargetX >= currentCenterX ? 'right' : 'left';
    const isAirborne = runtime.j === JavaMapActorState.Falling || runtime.j === JavaMapActorState.JumpRising;

    setFacingIfChanged(dir);
    moveTargetX.current = targetLeft;
    pendingAttackMonsterId.current = null;

    if (isAirborne) {
      // Tap-to-move trong lúc nhảy phải biến thành air-control hướng X,
      // không được đổi j=Running vì sẽ cắt state 5/6 của Java. Không step X
      // ngay trong input frame; để fixed Java tick xử lý và visual X blend mượt.
      moveDirection.current = dir;
      runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;
    } else {
      moveDirection.current = null;
      runtime.j = JavaMapActorState.Running;
    }

    setActionIfChanged('run');
    startMovementLoop();
  }, [
    clampX,
    disabled,
    setActionIfChanged,
    setFacingIfChanged,
    startMovementLoop,
  ]);

  const moveToMonster = useCallback((monster: MonsterTarget) => {
    const targetCenterX = getMonsterRuntimeBox(monster).centerX;
    const runtime = runtimeRef.current;
    const dir: FacingDirection = targetCenterX >= getActorRuntimeCenterX(runtime) ? 'right' : 'left';
    const isAirborne = runtime.j === JavaMapActorState.Falling || runtime.j === JavaMapActorState.JumpRising;

    pendingAttackMonsterId.current = monster.id;
    moveTargetX.current = clampX(targetCenterX - runtime.t.c / 2);
    setFacingIfChanged(dir);

    if (isAirborne) {
      // Move-to-monster while airborne follows the same Java-inspired air-control
      // rule as manual/tap movement: set direction now, step only on fixed tick,
      // and let visual X interpolation smooth the rendered sprite.
      moveDirection.current = dir;
      runtime.k = dir === 'left' ? JavaMoveBit.Left : JavaMoveBit.Right;
    } else {
      moveDirection.current = null;
      runtime.j = JavaMapActorState.Running;
    }

    setActionIfChanged('run');
    startMovementLoop();
  }, [
    clampX,
    setActionIfChanged,
    setFacingIfChanged,
    startMovementLoop,
  ]);

  const isPointOnCharacter = useCallback((x: number, y: number) => {
    const box = getActorRuntimeTouchBox(runtimeRef.current);
    return x >= box.left && x <= box.right && y >= box.top && y <= box.bottom;
  }, []);

  const shouldJumpToPoint = useCallback((x: number, y: number) => {
    const box = getActorRuntimeTouchBox(runtimeRef.current);
    return y < box.top + (box.bottom - box.top) * JUMP_TRIGGER_RATIO && !isPointOnCharacter(x, y);
  }, [isPointOnCharacter]);

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
      const centerX = getActorRuntimeCenterX(runtimeRef.current);
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
    const clampedX = clampX(initialX + charSize.w / 2 - runtimeRef.current.t.c / 2);
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
    visualInterpolationRef.current = null;
    const visualLeftX = getActorVisualLeft(runtime, charSize.w);
    visualLeftXRef.current = visualLeftX;
    lastEmittedXRef.current = visualLeftX;
    posAnim.setValue(visualLeftX);
    posYAnim.setValue(0);
    groundOffsetAnim.setValue(0);
    setFacing(initialFacing);
    facingRef.current = initialFacing;
    setJumpPoseState(null);
    onMove?.(visualLeftX, initialFacing, groundY);

    if (DEBUG_JAVA_MOVEMENT_JITTER) {
      console.log('[JavaMoveDebug][position-revision-reset]', {
        positionRevision,
        initialX: Number(initialX.toFixed(2)),
        clampedRuntimeX: Number(clampedX.toFixed(2)),
        visualLeftX: Number(visualLeftX.toFixed(2)),
        groundY: Number(groundY.toFixed(2)),
        facing: initialFacing,
      });
    }
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