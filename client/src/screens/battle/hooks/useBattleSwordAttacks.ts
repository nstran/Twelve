import { useCallback, useEffect, useRef, useState } from 'react';
import { Animated, Easing } from 'react-native';
import type { CharacterAction } from '../../../engine/character';
import { ATTACK_FRAMES, WALK_FRAMES } from '../../../engine/MonsterSprite';

const JAVA_BATTLE_TICK_MS = 40;
const JAVA_ATTACK_MIN_STEP_PX = 5;
const JAVA_ATTACK_MIN_TRAVEL_TICKS = 10;
const JAVA_ATTACK_HOLD_TICKS = 20;
const JAVA_ATTACK_FRAME_2_TICKS = 6;
const JAVA_ATTACK_IMPACT_TICKS = 11;
const JAVA_ATTACK_FRAME_4_TICKS = 16;
const MONSTER_ANIM_TICK_MS = 240;

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
  const [monFrame, setMonFrame] = useState<number>(WALK_FRAMES[0]);
  const [monAtk, setMonAtk] = useState(false);
  const swordAttackTiming = getSwordAttackTiming(attackTravelX);

  const playerAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const monsterAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerAttackQueueRef = useRef<QueuedAttack[]>([]);
  const monsterAttackQueueRef = useRef<QueuedAttack[]>([]);
  const playerAttackRunningRef = useRef(false);
  const monsterAttackRunningRef = useRef(false);
  const monTick = useRef(0);

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
  }, [enemyAttackTranslateX, enemyHitTranslateX, playerAttackTranslateX, playerHitTranslateX]);

  useEffect(() => {
    const timer = setInterval(() => {
      if (!mountedRef.current) return;
      monTick.current++;
      const frames = monAtk ? ATTACK_FRAMES : WALK_FRAMES;
      setMonFrame(frames[monTick.current % frames.length]);
    }, MONSTER_ANIM_TICK_MS);
    return () => clearInterval(timer);
  }, [monAtk, mountedRef]);

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

    const hitTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      nextAttack.onImpact();
    }, swordAttackTiming.contactMs);

    const returnTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      Animated.timing(enemyAttackTranslateX, {
        toValue: 0,
        duration: swordAttackTiming.returnMs,
        easing: Easing.linear,
        useNativeDriver: true,
      }).start();
    }, swordAttackTiming.returnStartMs);

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setMonAtk(false);
      nextAttack.onComplete();
      monsterAttackRunningRef.current = false;
      monsterAttackTimersRef.current = [];
      runNextMonsterSwordAttack();
    }, swordAttackTiming.totalMs);

    monsterAttackTimersRef.current = [hitTimer, returnTimer, completeTimer];

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
    monFrame,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
  };
};
