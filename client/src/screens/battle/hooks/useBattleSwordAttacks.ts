import { useCallback, useEffect, useRef, useState } from 'react';
import { Animated, Easing } from 'react-native';
import type { CharacterAction } from '../../../engine/character';
import type { BattleMonsterPoseKey } from '../../../engine/BattleMonsterAssetManifest';

const JAVA_BATTLE_TICK_MS = 40;
const JAVA_ATTACK_MIN_STEP_PX = 5;
const JAVA_ATTACK_MIN_TRAVEL_TICKS = 10;
const JAVA_ATTACK_HOLD_TICKS = 20;
const JAVA_ATTACK_FRAME_2_TICKS = 6;
const JAVA_ATTACK_IMPACT_TICKS = 11;
const JAVA_ATTACK_FRAME_4_TICKS = 16;
const MONSTER_ANIM_TICK_MS = 240;
const MONSTER_NORMAL_ATTACK_REPEAT_COUNT = 3;
const MONSTER_NORMAL_ATTACK_SWING_MS = 110;
const MONSTER_NORMAL_ATTACK_RECOVER_MS = 70;

interface QueuedAttack {
  onImpact: () => void;
  onComplete: () => void;
}

interface SwordAttackTiming {
  approachMs: number;
  contactMs: number;
  frame2Ms: number;
  impactMs: number;
  frame4Ms: number;
  returnStartMs: number;
  returnMs: number;
  totalMs: number;
}

interface UseBattleSwordAttacksArgs {
  attackTravelX: number;
  mountedRef: React.MutableRefObject<boolean>;
  enemyAttackTranslateX: Animated.Value;
  enemyHitTranslateX: Animated.Value;
  playerAttackTranslateX: Animated.Value;
  playerHitTranslateX: Animated.Value;
  setPlayerAction: React.Dispatch<React.SetStateAction<CharacterAction>>;
  setPlayerActionFrameIndex: React.Dispatch<React.SetStateAction<number | null>>;
  setPlayerRetreatPose: React.Dispatch<React.SetStateAction<boolean>>;
}

const ticksToMs = (ticks: number) => ticks * JAVA_BATTLE_TICK_MS;

const getSwordAttackTiming = (distancePx: number): SwordAttackTiming => {
  const safeDistance = Math.max(0, Math.round(distancePx));
  const stepPxPerTick = Math.max(JAVA_ATTACK_MIN_STEP_PX, Math.floor(safeDistance / 2));
  const approachTicks = safeDistance > 0
    ? Math.max(JAVA_ATTACK_MIN_TRAVEL_TICKS, Math.ceil(safeDistance / stepPxPerTick))
    : JAVA_ATTACK_MIN_TRAVEL_TICKS;
  const approachMs = ticksToMs(approachTicks);
  const holdMs = ticksToMs(JAVA_ATTACK_HOLD_TICKS);
  const contactMs = approachMs;
  const frame2Ms = contactMs + ticksToMs(JAVA_ATTACK_FRAME_2_TICKS);
  const impactMs = contactMs + ticksToMs(JAVA_ATTACK_IMPACT_TICKS);
  const frame4Ms = contactMs + ticksToMs(JAVA_ATTACK_FRAME_4_TICKS);
  const returnStartMs = contactMs + holdMs;
  const returnMs = approachMs;

  return {
    approachMs,
    contactMs,
    frame2Ms,
    impactMs,
    frame4Ms,
    returnStartMs,
    returnMs,
    totalMs: returnStartMs + returnMs,
  };
};

export const useBattleSwordAttacks = ({
  attackTravelX,
  mountedRef,
  enemyAttackTranslateX,
  enemyHitTranslateX,
  playerAttackTranslateX,
  playerHitTranslateX,
  setPlayerAction,
  setPlayerActionFrameIndex,
  setPlayerRetreatPose,
}: UseBattleSwordAttacksArgs) => {
  const [monsterPoseKey, setMonsterPoseKey] = useState<BattleMonsterPoseKey>('idle_a');
  const [monAtk, setMonAtk] = useState(false);
  const [monHit, setMonHit] = useState(false);
  const swordAttackTiming = getSwordAttackTiming(attackTravelX);
  const idlePoses: readonly BattleMonsterPoseKey[] = ['idle_a', 'idle_b', 'idle_c'];

  const playerAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const monsterAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerAttackQueueRef = useRef<QueuedAttack[]>([]);
  const monsterAttackQueueRef = useRef<QueuedAttack[]>([]);
  const playerAttackRunningRef = useRef(false);
  const monsterAttackRunningRef = useRef(false);
  const monTick = useRef(0);
  const monsterHitTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  useEffect(() => () => {
    playerAttackTimersRef.current.forEach(clearTimeout);
    monsterAttackTimersRef.current.forEach(clearTimeout);
    playerAttackTimersRef.current = [];
    monsterAttackTimersRef.current = [];
    playerAttackQueueRef.current = [];
    monsterAttackQueueRef.current = [];
    playerAttackRunningRef.current = false;
    monsterAttackRunningRef.current = false;
    playerAttackTranslateX.stopAnimation();
    playerHitTranslateX.stopAnimation();
    enemyAttackTranslateX.stopAnimation();
    enemyHitTranslateX.stopAnimation();
    if (monsterHitTimerRef.current) {
      clearTimeout(monsterHitTimerRef.current);
      monsterHitTimerRef.current = null;
    }
  }, [enemyAttackTranslateX, enemyHitTranslateX, playerAttackTranslateX, playerHitTranslateX]);

  useEffect(() => {
    const timer = setInterval(() => {
      if (!mountedRef.current) return;
      if (monAtk || monHit) return;
      monTick.current++;
      setMonsterPoseKey(idlePoses[monTick.current % idlePoses.length] ?? 'idle_a');
    }, MONSTER_ANIM_TICK_MS);
    return () => clearInterval(timer);
  }, [idlePoses, monAtk, monHit, mountedRef]);

  const triggerMonsterHitPose = useCallback(() => {
    if (!mountedRef.current) {
      return;
    }

    if (monsterHitTimerRef.current) {
      clearTimeout(monsterHitTimerRef.current);
      monsterHitTimerRef.current = null;
    }

    setMonHit(true);
    setMonsterPoseKey('hit');
    monsterHitTimerRef.current = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      setMonHit(false);
      if (!monsterAttackRunningRef.current) {
        setMonsterPoseKey('idle_a');
      }
    }, 200);
  }, [mountedRef]);

  const runNextPlayerSwordAttack = useCallback(() => {
    if (playerAttackRunningRef.current) return;

    const nextAttack = playerAttackQueueRef.current.shift();
    if (!nextAttack) return;

    playerAttackRunningRef.current = true;
    playerAttackTimersRef.current.forEach(clearTimeout);
    playerAttackTimersRef.current = [];

    playerAttackTranslateX.stopAnimation();
    enemyHitTranslateX.stopAnimation();
    playerAttackTranslateX.setValue(0);
    enemyHitTranslateX.setValue(0);
    setPlayerRetreatPose(false);
    setPlayerAction('run');
    setPlayerActionFrameIndex(null);

    const contactTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerAction('attack');
      setPlayerActionFrameIndex(0);
    }, swordAttackTiming.contactMs);

    const hitTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerActionFrameIndex(1);
    }, swordAttackTiming.frame2Ms);

    const impactTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerActionFrameIndex(2);
      triggerMonsterHitPose();
      nextAttack.onImpact();
    }, swordAttackTiming.impactMs);

    const recoverTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerActionFrameIndex(3);
    }, swordAttackTiming.frame4Ms);

    const returnTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerRetreatPose(true);
      setPlayerAction('run');
      setPlayerActionFrameIndex(null);

      Animated.timing(playerAttackTranslateX, {
        toValue: 0,
        duration: swordAttackTiming.returnMs,
        easing: Easing.linear,
        useNativeDriver: true,
      }).start();
    }, swordAttackTiming.returnStartMs);

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerRetreatPose(false);
      setPlayerAction('idle');
      setPlayerActionFrameIndex(null);
      nextAttack.onComplete();
      playerAttackRunningRef.current = false;
      playerAttackTimersRef.current = [];
      runNextPlayerSwordAttack();
    }, swordAttackTiming.totalMs);

    playerAttackTimersRef.current = [
      contactTimer,
      hitTimer,
      impactTimer,
      recoverTimer,
      returnTimer,
      completeTimer,
    ];

    Animated.timing(playerAttackTranslateX, {
      toValue: attackTravelX,
      duration: swordAttackTiming.approachMs,
      easing: Easing.linear,
      useNativeDriver: true,
    }).start();
  }, [
    attackTravelX,
    enemyHitTranslateX,
    mountedRef,
    playerAttackTranslateX,
    setPlayerAction,
    setPlayerActionFrameIndex,
    setPlayerRetreatPose,
    swordAttackTiming.approachMs,
    swordAttackTiming.contactMs,
    swordAttackTiming.frame2Ms,
    swordAttackTiming.frame4Ms,
    swordAttackTiming.impactMs,
    swordAttackTiming.returnMs,
    swordAttackTiming.returnStartMs,
    swordAttackTiming.totalMs,
    triggerMonsterHitPose,
  ]);

  const playPlayerSwordAttack = useCallback((onImpact: () => void, onComplete: () => void) => {
    playerAttackQueueRef.current.push({ onImpact, onComplete });
    runNextPlayerSwordAttack();
  }, [runNextPlayerSwordAttack]);

  const runNextMonsterSwordAttack = useCallback(() => {
    if (monsterAttackRunningRef.current) return;

    const nextAttack = monsterAttackQueueRef.current.shift();
    if (!nextAttack) return;

    monsterAttackRunningRef.current = true;
    monsterAttackTimersRef.current.forEach(clearTimeout);
    monsterAttackTimersRef.current = [];

    enemyAttackTranslateX.stopAnimation();
    playerHitTranslateX.stopAnimation();
    enemyAttackTranslateX.setValue(0);
    playerHitTranslateX.setValue(0);
    setMonAtk(true);
    setMonHit(false);
    setMonsterPoseKey('prepare_attack');
    const attackLoopDurationMs =
      MONSTER_NORMAL_ATTACK_REPEAT_COUNT * MONSTER_NORMAL_ATTACK_SWING_MS
      + Math.max(0, MONSTER_NORMAL_ATTACK_REPEAT_COUNT - 1) * MONSTER_NORMAL_ATTACK_RECOVER_MS;
    const returnStartMs = swordAttackTiming.contactMs + attackLoopDurationMs;

    const runTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setMonsterPoseKey('run_attack');
    }, Math.max(50, Math.min(160, Math.floor(swordAttackTiming.contactMs * 0.55))));

    const hitTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setMonsterPoseKey('attack');
      nextAttack.onImpact();
    }, swordAttackTiming.contactMs);

    const repeatPoseTimers: ReturnType<typeof setTimeout>[] = [];
    for (let repeatIndex = 1; repeatIndex < MONSTER_NORMAL_ATTACK_REPEAT_COUNT; repeatIndex++) {
      const recoverTimer = setTimeout(() => {
        if (!mountedRef.current) return;
        setMonsterPoseKey('prepare_attack');
      }, swordAttackTiming.contactMs + repeatIndex * MONSTER_NORMAL_ATTACK_SWING_MS + (repeatIndex - 1) * MONSTER_NORMAL_ATTACK_RECOVER_MS);

      const nextSwingTimer = setTimeout(() => {
        if (!mountedRef.current) return;
        setMonsterPoseKey('attack');
      }, swordAttackTiming.contactMs + repeatIndex * (MONSTER_NORMAL_ATTACK_SWING_MS + MONSTER_NORMAL_ATTACK_RECOVER_MS));

      repeatPoseTimers.push(recoverTimer, nextSwingTimer);
    }

    const returnTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      Animated.timing(enemyAttackTranslateX, {
        toValue: 0,
        duration: swordAttackTiming.returnMs,
        easing: Easing.linear,
        useNativeDriver: true,
      }).start();
    }, returnStartMs);

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setMonAtk(false);
      setMonsterPoseKey('idle_a');
      nextAttack.onComplete();
      monsterAttackRunningRef.current = false;
      monsterAttackTimersRef.current = [];
      runNextMonsterSwordAttack();
    }, returnStartMs + swordAttackTiming.returnMs);

    monsterAttackTimersRef.current = [runTimer, hitTimer, ...repeatPoseTimers, returnTimer, completeTimer];

    Animated.timing(enemyAttackTranslateX, {
      toValue: -attackTravelX,
      duration: swordAttackTiming.approachMs,
      easing: Easing.linear,
      useNativeDriver: true,
    }).start();
  }, [
    attackTravelX,
    enemyAttackTranslateX,
    mountedRef,
    playerHitTranslateX,
    swordAttackTiming.approachMs,
    swordAttackTiming.contactMs,
    swordAttackTiming.returnMs,
    swordAttackTiming.returnStartMs,
    swordAttackTiming.totalMs,
  ]);

  const playMonsterSwordAttack = useCallback((onImpact: () => void, onComplete: () => void) => {
    monsterAttackQueueRef.current.push({ onImpact, onComplete });
    runNextMonsterSwordAttack();
  }, [runNextMonsterSwordAttack]);

  return {
    monsterPoseKey,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
  };
};
