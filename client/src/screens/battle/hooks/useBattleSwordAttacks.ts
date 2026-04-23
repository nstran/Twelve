import { useCallback, useEffect, useRef, useState } from 'react';
import { Animated, Easing } from 'react-native';
import type { CharacterAction } from '../../../engine/character';
import type { BattleMonsterPoseKey } from '../../../engine/BattleMonsterAssetManifest';
import type { SkillFamilyCode } from '../core';

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
const MONSTER_DEFEAT_HIT_HOLD_MS = 140;
const MONSTER_DEFEAT_SINK_MS = 280;
const MONSTER_DEFEAT_DROP_PX = 10;
const MONSTER_DEFEAT_SCALE = 0.9;

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

interface MonsterSkillCastProfile {
  prepRatio: number;
  settleLeadMs: number;
  castTravelRatio: number;
  minTravelPx: number;
  maxTravelPx: number;
  easing: (value: number) => number;
}

const FIRE_CAST_PROFILE: MonsterSkillCastProfile = {
  prepRatio: 0.52,
  settleLeadMs: 100,
  castTravelRatio: 0.22,
  minTravelPx: 8,
  maxTravelPx: 20,
  easing: Easing.out(Easing.cubic),
};

const THUNDER_CAST_PROFILE: MonsterSkillCastProfile = {
  prepRatio: 0.34,
  settleLeadMs: 72,
  castTravelRatio: 0.28,
  minTravelPx: 10,
  maxTravelPx: 24,
  easing: Easing.out(Easing.exp),
};

const WATER_CAST_PROFILE: MonsterSkillCastProfile = {
  prepRatio: 0.46,
  settleLeadMs: 118,
  castTravelRatio: 0.18,
  minTravelPx: 6,
  maxTravelPx: 16,
  easing: Easing.out(Easing.quad),
};

const resolveMonsterSkillCastProfile = (
  familyCode: SkillFamilyCode,
  hitsActor: boolean,
  hitShakePx: number,
): MonsterSkillCastProfile => {
  const familyGroup = Math.floor(familyCode / 1000);
  const baseProfile = familyGroup === 2
    ? THUNDER_CAST_PROFILE
    : familyGroup === 4
      ? WATER_CAST_PROFILE
      : FIRE_CAST_PROFILE;

  if (!hitsActor && hitShakePx <= 10) {
    return {
      ...baseProfile,
      castTravelRatio: Math.max(0.12, baseProfile.castTravelRatio - 0.05),
      maxTravelPx: Math.max(baseProfile.minTravelPx, baseProfile.maxTravelPx - 4),
    };
  }

  return {
    ...baseProfile,
    castTravelRatio: Math.min(0.32, baseProfile.castTravelRatio + Math.min(0.05, hitShakePx / 220)),
    maxTravelPx: Math.min(28, baseProfile.maxTravelPx + Math.min(4, Math.floor(hitShakePx / 6))),
  };
};

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
  const monsterDefeatRunningRef = useRef(false);
  const monsterDefeatQueuedCallbacksRef = useRef<(() => void)[]>([]);
  const monTick = useRef(0);
  const monsterHitTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const monsterDefeatTranslateY = useRef(new Animated.Value(0)).current;
  const monsterDefeatOpacity = useRef(new Animated.Value(1)).current;
  const monsterDefeatScale = useRef(new Animated.Value(1)).current;

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
    monsterDefeatTranslateY.stopAnimation();
    monsterDefeatOpacity.stopAnimation();
    monsterDefeatScale.stopAnimation();
    if (monsterHitTimerRef.current) {
      clearTimeout(monsterHitTimerRef.current);
      monsterHitTimerRef.current = null;
    }
  }, [
    enemyAttackTranslateX,
    enemyHitTranslateX,
    monsterDefeatOpacity,
    monsterDefeatScale,
    monsterDefeatTranslateY,
    playerAttackTranslateX,
    playerHitTranslateX,
  ]);

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

    if (monsterDefeatRunningRef.current) {
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
      if (!monsterAttackRunningRef.current && !monsterDefeatRunningRef.current) {
        setMonsterPoseKey('idle_a');
      }
    }, 200);
  }, [mountedRef]);

  const playMonsterDefeatSequence = useCallback((onComplete: () => void) => {
    if (monsterDefeatRunningRef.current) {
      monsterDefeatQueuedCallbacksRef.current.push(onComplete);
      return;
    }

    monsterDefeatRunningRef.current = true;
    monsterDefeatQueuedCallbacksRef.current = [onComplete];
    monsterAttackTimersRef.current.forEach(clearTimeout);
    monsterAttackTimersRef.current = [];

    if (monsterHitTimerRef.current) {
      clearTimeout(monsterHitTimerRef.current);
      monsterHitTimerRef.current = null;
    }

    enemyAttackTranslateX.stopAnimation();
    enemyHitTranslateX.stopAnimation();
    monsterDefeatTranslateY.stopAnimation();
    monsterDefeatOpacity.stopAnimation();
    monsterDefeatScale.stopAnimation();

    enemyAttackTranslateX.setValue(0);
    enemyHitTranslateX.setValue(0);
    monsterDefeatTranslateY.setValue(0);
    monsterDefeatOpacity.setValue(1);
    monsterDefeatScale.setValue(1);

    setMonAtk(false);
    setMonHit(true);
    setMonsterPoseKey('hit');

    Animated.sequence([
      Animated.timing(monsterDefeatTranslateY, {
        toValue: 2,
        duration: MONSTER_DEFEAT_HIT_HOLD_MS,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
      Animated.parallel([
        Animated.timing(monsterDefeatTranslateY, {
          toValue: MONSTER_DEFEAT_DROP_PX,
          duration: MONSTER_DEFEAT_SINK_MS,
          easing: Easing.in(Easing.cubic),
          useNativeDriver: true,
        }),
        Animated.timing(monsterDefeatOpacity, {
          toValue: 0,
          duration: MONSTER_DEFEAT_SINK_MS,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(monsterDefeatScale, {
          toValue: MONSTER_DEFEAT_SCALE,
          duration: MONSTER_DEFEAT_SINK_MS,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    ]).start(() => {
      const callbacks = monsterDefeatQueuedCallbacksRef.current.splice(0);
      monsterDefeatRunningRef.current = false;
      callbacks.forEach((callback) => callback());
    });
  }, [
    enemyAttackTranslateX,
    enemyHitTranslateX,
    monsterDefeatOpacity,
    monsterDefeatScale,
    monsterDefeatTranslateY,
  ]);

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
    monsterDefeatTranslateY.stopAnimation();
    monsterDefeatOpacity.stopAnimation();
    monsterDefeatScale.stopAnimation();
    monsterDefeatTranslateY.setValue(0);
    monsterDefeatOpacity.setValue(1);
    monsterDefeatScale.setValue(1);
    monsterDefeatRunningRef.current = false;
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
    monsterDefeatOpacity,
    monsterDefeatScale,
    monsterDefeatTranslateY,
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

  const playMonsterSkillCast = useCallback((
    familyCode: SkillFamilyCode,
    impactDelayMs: number,
    totalDurationMs: number,
    hitsActor: boolean,
    hitShakePx: number,
  ) => {
    monsterAttackTimersRef.current.forEach(clearTimeout);
    monsterAttackTimersRef.current = [];

    enemyAttackTranslateX.stopAnimation();
    enemyAttackTranslateX.setValue(0);

    if (monsterHitTimerRef.current) {
      clearTimeout(monsterHitTimerRef.current);
      monsterHitTimerRef.current = null;
    }

    const profile = resolveMonsterSkillCastProfile(familyCode, hitsActor, hitShakePx);
    const safeImpactDelayMs = Math.max(120, impactDelayMs);
    const safeTotalDurationMs = Math.max(safeImpactDelayMs + 180, totalDurationMs);
    const approachMs = Math.max(70, Math.min(190, Math.floor(safeImpactDelayMs * profile.prepRatio)));
    const settleStartMs = Math.max(approachMs + 32, safeImpactDelayMs - profile.settleLeadMs);
    const castTravel = Math.max(
      profile.minTravelPx,
      Math.min(profile.maxTravelPx, Math.round(attackTravelX * profile.castTravelRatio)),
    );

    monsterAttackRunningRef.current = true;
    setMonAtk(true);
    setMonHit(false);
    setMonsterPoseKey('prepare_attack');

    const runTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      setMonsterPoseKey('run_attack');
    }, Math.max(40, Math.floor(approachMs * 0.45)));

    const attackPoseTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      setMonsterPoseKey('attack');
    }, Math.max(70, safeImpactDelayMs - 70));

    const settleTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      Animated.timing(enemyAttackTranslateX, {
        toValue: 0,
        duration: Math.max(110, safeTotalDurationMs - settleStartMs - 40),
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }).start();
    }, settleStartMs);

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      enemyAttackTranslateX.setValue(0);
      setMonAtk(false);
      setMonHit(false);
      setMonsterPoseKey('idle_a');
      monsterAttackRunningRef.current = false;
      monsterAttackTimersRef.current = [];
      runNextMonsterSwordAttack();
    }, safeTotalDurationMs);

    monsterAttackTimersRef.current = [runTimer, attackPoseTimer, settleTimer, completeTimer];

    Animated.timing(enemyAttackTranslateX, {
      toValue: -castTravel,
      duration: approachMs,
      easing: profile.easing,
      useNativeDriver: true,
    }).start();
  }, [attackTravelX, enemyAttackTranslateX, mountedRef, runNextMonsterSwordAttack]);

  return {
    monsterDefeatOpacity,
    monsterDefeatScale,
    monsterDefeatTranslateY,
    monsterPoseKey,
    playMonsterDefeatSequence,
    playMonsterSkillCast,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
  };
};
