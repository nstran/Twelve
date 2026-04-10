import { useCallback, useRef, useState, type MutableRefObject } from 'react';
import { Animated, Easing } from 'react-native';
import { GAIN_POPUP_DURATION_MS, type BattleSide, type DamagePopupItem, type GainPopupItem } from '../core';

interface UseBattlePopupsArgs {
  mountedRef: MutableRefObject<boolean>;
}

export const useBattlePopups = ({ mountedRef }: UseBattlePopupsArgs) => {
  const [damagePopups, setDamagePopups] = useState<DamagePopupItem[]>([]);
  const [gainPopups, setGainPopups] = useState<GainPopupItem[]>([]);
  const damagePopupKeyRef = useRef(0);
  const gainPopupKeyRef = useRef(0);

  const showDamagePopup = useCallback((side: BattleSide, amount: number) => {
    if (amount <= 0) return;

    const anim = new Animated.Value(0);
    const key = `dmg-${++damagePopupKeyRef.current}`;
    setDamagePopups(prev => [...prev, { key, side, amount, anim }]);
    Animated.timing(anim, {
      toValue: 1,
      duration: 700,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start(() => {
      if (!mountedRef.current) return;
      setDamagePopups(prev => prev.filter(item => item.key !== key));
    });
  }, [mountedRef]);

  const showGainPopup = useCallback((side: BattleSide, text: string) => {
    const anim = new Animated.Value(0);
    const key = `gain-${++gainPopupKeyRef.current}`;
    setGainPopups(prev => [...prev, { key, side, text, anim }]);
    Animated.timing(anim, {
      toValue: 1,
      duration: GAIN_POPUP_DURATION_MS,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: true,
    }).start(() => {
      if (!mountedRef.current) return;
      setGainPopups(prev => prev.filter(item => item.key !== key));
    });
  }, [mountedRef]);

  return {
    damagePopups,
    gainPopups,
    showDamagePopup,
    showGainPopup,
  };
};
