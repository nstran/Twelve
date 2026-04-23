import { useCallback, useEffect, useRef, useState } from 'react';
import { Animated, Easing } from 'react-native';
import { EXTRA_TURNS_BADGE_TOTAL_MS } from '../core';

interface UseBattleBoardBadgesArgs {
  mountedRef: React.MutableRefObject<boolean>;
}

export const useBattleBoardBadges = ({
  mountedRef,
}: UseBattleBoardBadgesArgs) => {
  const [extraTurnsBadgeValue, setExtraTurnsBadgeValue] = useState(0);
  const [showExtraTurnsBadge, setShowExtraTurnsBadge] = useState(false);
  const extraTurnsBadgeAnim = useRef(new Animated.Value(1)).current;
  const extraTurnsBadgeLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const extraTurnsBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const [comboMultiplier, setComboMultiplier] = useState(0);
  const [showComboBadge, setShowComboBadge] = useState(false);
  const comboBadgeAnim = useRef(new Animated.Value(0)).current;
  const comboBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  useEffect(() => () => {
    extraTurnsBadgeLoopRef.current?.stop();
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }
    if (comboBadgeTimeoutRef.current !== null) {
      clearTimeout(comboBadgeTimeoutRef.current);
      comboBadgeTimeoutRef.current = null;
    }
  }, []);

  const flashExtraTurnsBadge = useCallback((turns: number) => {
    extraTurnsBadgeLoopRef.current?.stop();
    extraTurnsBadgeLoopRef.current = null;
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }

    if (turns <= 0) {
      setShowExtraTurnsBadge(false);
      setExtraTurnsBadgeValue(0);
      extraTurnsBadgeAnim.setValue(1);
      return;
    }

    setExtraTurnsBadgeValue(turns);
    setShowExtraTurnsBadge(true);
    extraTurnsBadgeAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(extraTurnsBadgeAnim, {
          toValue: 0.25,
          duration: 220,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(extraTurnsBadgeAnim, {
          toValue: 1,
          duration: 220,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    extraTurnsBadgeLoopRef.current = loop;
    loop.start();

    extraTurnsBadgeTimeoutRef.current = setTimeout(() => {
      if (!mountedRef.current) return;
      extraTurnsBadgeLoopRef.current?.stop();
      extraTurnsBadgeLoopRef.current = null;
      extraTurnsBadgeAnim.setValue(1);
      setShowExtraTurnsBadge(false);
      setExtraTurnsBadgeValue(0);
      extraTurnsBadgeTimeoutRef.current = null;
    }, EXTRA_TURNS_BADGE_TOTAL_MS);
  }, [extraTurnsBadgeAnim, mountedRef]);

  const flashComboBadge = useCallback((multiplier: number) => {
    if (comboBadgeTimeoutRef.current !== null) {
      clearTimeout(comboBadgeTimeoutRef.current);
      comboBadgeTimeoutRef.current = null;
    }

    setComboMultiplier(multiplier);
    setShowComboBadge(true);
    comboBadgeAnim.stopAnimation();
    comboBadgeAnim.setValue(0);
    Animated.timing(comboBadgeAnim, {
      toValue: 1,
      duration: 220,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start();

    comboBadgeTimeoutRef.current = setTimeout(() => {
      if (!mountedRef.current) return;
      Animated.timing(comboBadgeAnim, {
        toValue: 0,
        duration: 180,
        easing: Easing.in(Easing.quad),
        useNativeDriver: true,
      }).start(() => {
        if (!mountedRef.current) return;
        setShowComboBadge(false);
        setComboMultiplier(0);
      });
      comboBadgeTimeoutRef.current = null;
    }, 760);
  }, [comboBadgeAnim, mountedRef]);

  return {
    comboBadgeAnim,
    comboMultiplier,
    extraTurnsBadgeAnim,
    extraTurnsBadgeValue,
    flashComboBadge,
    flashExtraTurnsBadge,
    showComboBadge,
    showExtraTurnsBadge,
  };
};
