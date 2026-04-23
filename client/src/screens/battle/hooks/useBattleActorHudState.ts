import { useCallback, useEffect, useRef, useState, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import { Animated, Easing } from 'react-native';
import type { CharacterAction } from '../../../engine/character';
import type { BattleResult } from '../core';

const PLAYER_HIT_REACT_TOTAL_MS = 320;
const PLAYER_DEFEAT_RESULT_DELAY_MS = 360;

interface UseBattleActorHudStateArgs {
  enemyHP: number;
  maxEHP: number;
  maxHP: number;
  maxPow: number;
  enemyMaxPow: number;
  mountedRef: MutableRefObject<boolean>;
  playerHP: number;
  power: number;
  enemyPower: number;
  result: BattleResult | null;
  setPlayerAction: Dispatch<SetStateAction<CharacterAction>>;
  setPlayerActionFrameIndex: Dispatch<SetStateAction<number | null>>;
  setResult: Dispatch<SetStateAction<BattleResult | null>>;
}

export const useBattleActorHudState = ({
  enemyHP,
  maxEHP,
  maxHP,
  maxPow,
  enemyMaxPow,
  mountedRef,
  playerHP,
  power,
  enemyPower,
  result,
  setPlayerAction,
  setPlayerActionFrameIndex,
  setResult,
}: UseBattleActorHudStateArgs) => {
  const [playerReactionPose, setPlayerReactionPose] = useState(false);
  const [playerDefeatPose, setPlayerDefeatPose] = useState(false);
  const [playerRetreatPose, setPlayerRetreatPose] = useState(false);

  const resultArtAnim = useRef(new Animated.Value(0)).current;
  const powerBlinkAnim = useRef(new Animated.Value(1)).current;
  const enemyPowerBlinkAnim = useRef(new Animated.Value(1)).current;
  const rageAuraPulseAnim = useRef(new Animated.Value(1)).current;
  const playerHPBarAnim = useRef(new Animated.Value(maxHP)).current;
  const enemyHPBarAnim = useRef(new Animated.Value(maxEHP)).current;
  const powerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const enemyPowerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const rageAuraLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const playerReactionTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerResultTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerDefeatStartedRef = useRef(false);

  useEffect(() => () => {
    playerReactionTimersRef.current.forEach(clearTimeout);
    playerResultTimersRef.current.forEach(clearTimeout);
    playerReactionTimersRef.current = [];
    playerResultTimersRef.current = [];
    powerBlinkLoopRef.current?.stop();
    enemyPowerBlinkLoopRef.current?.stop();
    rageAuraLoopRef.current?.stop();
  }, []);

  useEffect(() => {
    Animated.timing(playerHPBarAnim, {
      toValue: playerHP,
      duration: 420,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: false,
    }).start();
  }, [playerHP, playerHPBarAnim]);

  useEffect(() => {
    Animated.timing(enemyHPBarAnim, {
      toValue: enemyHP,
      duration: 420,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: false,
    }).start();
  }, [enemyHP, enemyHPBarAnim]);

  useEffect(() => {
    powerBlinkLoopRef.current?.stop();
    powerBlinkLoopRef.current = null;

    if (power < maxPow || result !== null) {
      powerBlinkAnim.setValue(1);
      return;
    }

    powerBlinkAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(powerBlinkAnim, {
          toValue: 0.15,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(powerBlinkAnim, {
          toValue: 1,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    powerBlinkLoopRef.current = loop;
    loop.start();

    return () => {
      loop.stop();
      powerBlinkLoopRef.current = null;
      powerBlinkAnim.setValue(1);
    };
  }, [power, maxPow, result, powerBlinkAnim]);

  useEffect(() => {
    enemyPowerBlinkLoopRef.current?.stop();
    enemyPowerBlinkLoopRef.current = null;

    if (enemyPower < enemyMaxPow || result !== null) {
      enemyPowerBlinkAnim.setValue(1);
      return;
    }

    enemyPowerBlinkAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(enemyPowerBlinkAnim, {
          toValue: 0.15,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(enemyPowerBlinkAnim, {
          toValue: 1,
          duration: 200,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    enemyPowerBlinkLoopRef.current = loop;
    loop.start();

    return () => {
      loop.stop();
      enemyPowerBlinkLoopRef.current = null;
      enemyPowerBlinkAnim.setValue(1);
    };
  }, [enemyPower, enemyMaxPow, result, enemyPowerBlinkAnim]);

  useEffect(() => {
    rageAuraLoopRef.current?.stop();
    rageAuraLoopRef.current = null;

    const rageReady = (power >= maxPow && maxPow > 0) || (enemyPower >= enemyMaxPow && enemyMaxPow > 0);
    if (!rageReady || result !== null) {
      rageAuraPulseAnim.setValue(1);
      return;
    }

    rageAuraPulseAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(rageAuraPulseAnim, {
          toValue: 0.72,
          duration: 260,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(rageAuraPulseAnim, {
          toValue: 1.08,
          duration: 260,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(rageAuraPulseAnim, {
          toValue: 1,
          duration: 220,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    rageAuraLoopRef.current = loop;
    loop.start();

    return () => {
      loop.stop();
      rageAuraLoopRef.current = null;
      rageAuraPulseAnim.setValue(1);
    };
  }, [enemyMaxPow, enemyPower, maxPow, power, rageAuraPulseAnim, result]);

  useEffect(() => {
    if (result === null) {
      playerDefeatStartedRef.current = false;
      setPlayerReactionPose(false);
      setPlayerDefeatPose(false);
      setPlayerRetreatPose(false);
      setPlayerAction('idle');
      setPlayerActionFrameIndex(null);
      playerResultTimersRef.current.forEach(clearTimeout);
      playerResultTimersRef.current = [];
      resultArtAnim.setValue(0);
      return;
    }

    resultArtAnim.setValue(0);
    Animated.spring(resultArtAnim, {
      toValue: 1,
      friction: 6,
      tension: 80,
      useNativeDriver: true,
    }).start();
  }, [result, resultArtAnim, setPlayerAction, setPlayerActionFrameIndex]);

  const clearPlayerReactionTimers = useCallback(() => {
    playerReactionTimersRef.current.forEach(clearTimeout);
    playerReactionTimersRef.current = [];
  }, []);

  const startPlayerDefeatSequence = useCallback(() => {
    if (playerDefeatStartedRef.current) return;

    playerDefeatStartedRef.current = true;
    clearPlayerReactionTimers();
    setPlayerReactionPose(false);
    setPlayerRetreatPose(false);
    setPlayerDefeatPose(true);

    const resultTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setResult('defeat');
    }, PLAYER_DEFEAT_RESULT_DELAY_MS);

    playerReactionTimersRef.current = [];
    playerResultTimersRef.current = [resultTimer];
  }, [clearPlayerReactionTimers, mountedRef, setResult]);

  const playPlayerHitReaction = useCallback(() => {
    if (playerDefeatStartedRef.current) return;

    clearPlayerReactionTimers();
    setPlayerReactionPose(true);
    setPlayerRetreatPose(false);

    const resetTimer = setTimeout(() => {
      if (!mountedRef.current || playerDefeatStartedRef.current) return;
      setPlayerReactionPose(false);
    }, PLAYER_HIT_REACT_TOTAL_MS);

    playerReactionTimersRef.current = [resetTimer];
  }, [clearPlayerReactionTimers, mountedRef]);

  return {
    enemyHPBarAnim,
    playPlayerHitReaction,
    playerDefeatPose,
    playerHPBarAnim,
    playerReactionPose,
    playerRetreatPose,
    enemyPowerBlinkAnim,
    powerBlinkAnim,
    rageAuraPulseAnim,
    resultArtAnim,
    setPlayerRetreatPose,
    startPlayerDefeatSequence,
  };
};
