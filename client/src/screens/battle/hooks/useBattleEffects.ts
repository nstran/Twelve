import { useCallback, useRef, useState, type MutableRefObject } from 'react';
import { Animated, Easing } from 'react-native';
import {
  COLLECT_PULSE_IN_MS,
  COLLECT_PULSE_OUT_MS,
  PLAYER_HUD_LAYOUT,
  buildCollectFXItems,
  buildMatchFXItems,
  type Board,
  type BattleSide,
  type CollectFXItem,
  type MatchFXItem,
} from '../core';
import { useBattlePopups } from './useBattlePopups';

type HudLayout = typeof PLAYER_HUD_LAYOUT;

interface UseBattleEffectsArgs {
  mountedRef: MutableRefObject<boolean>;
  panelLeft: number;
  panelTop: number;
  charsTop: number;
  playerHud: HudLayout;
  enemyHud: HudLayout;
  playerCollectAnim: Animated.Value;
  enemyCollectAnim: Animated.Value;
}

export const useBattleEffects = ({
  mountedRef,
  panelLeft,
  panelTop,
  charsTop,
  playerHud,
  enemyHud,
  playerCollectAnim,
  enemyCollectAnim,
}: UseBattleEffectsArgs) => {
  const [matchFX, setMatchFX] = useState<MatchFXItem[]>([]);
  const [collectFX, setCollectFX] = useState<CollectFXItem[]>([]);
  const fxKeyRef = useRef(0);
  const collectFXKeyRef = useRef(0);
  const {
    damagePopups,
    gainPopups,
    showDamagePopup,
    showGainPopup,
  } = useBattlePopups({ mountedRef });

  const pulseCollector = useCallback((side: BattleSide) => {
    const anim = side === 'player' ? playerCollectAnim : enemyCollectAnim;
    anim.stopAnimation();
    anim.setValue(0);
    Animated.sequence([
      Animated.timing(anim, {
        toValue: 1,
        duration: COLLECT_PULSE_IN_MS,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
      Animated.timing(anim, {
        toValue: 0,
        duration: COLLECT_PULSE_OUT_MS,
        easing: Easing.in(Easing.quad),
        useNativeDriver: true,
      }),
    ]).start();
  }, [enemyCollectAnim, playerCollectAnim]);

  const spawnMatchFX = useCallback((matched: Set<string>, board: Board, expanded: Set<string>) => {
    const items = buildMatchFXItems(
      matched,
      board,
      expanded,
      () => `fx-${++fxKeyRef.current}`,
    );
    if (items.length === 0) return;

    setMatchFX(prev => [...prev, ...items]);
    Animated.parallel(
      items.map(item => Animated.sequence([
        Animated.delay(item.delayMs),
        Animated.timing(item.anim, {
          toValue: 1,
          duration: item.durationMs,
          easing: Easing.out(Easing.cubic),
          useNativeDriver: true,
        }),
      ])),
    ).start(() => {
      if (!mountedRef.current) return;
      const keys = new Set(items.map(item => item.key));
      setMatchFX(prev => prev.filter(item => !keys.has(item.key)));
    });
  }, [mountedRef]);

  const spawnCollectFX = useCallback((
    matched: Set<string>,
    board: Board,
    collectorSide: BattleSide,
    healAmount: number,
  ) => {
    const { items, hitHP, hitMP, hitPow, hitCharacter } = buildCollectFXItems({
      matched,
      board,
      collectorSide,
      panelLeft,
      panelTop,
      charsTop,
      playerHud,
      enemyHud,
      nextKey: () => `cfx-${++collectFXKeyRef.current}`,
    });
    if (items.length === 0) return;

    setCollectFX(prev => [...prev, ...items]);
    Animated.parallel(
      items.map(item => Animated.sequence([
        Animated.delay(item.delayMs),
        Animated.timing(item.anim, {
          toValue: 1,
          duration: item.durationMs,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
      ])),
    ).start(() => {
      if (!mountedRef.current) return;
      const keys = new Set(items.map(item => item.key));
      setCollectFX(prev => prev.filter(item => !keys.has(item.key)));
      if (hitHP && healAmount > 0) showGainPopup(collectorSide, `+${healAmount} HP`);
      if (hitHP || hitMP || hitPow || hitCharacter) pulseCollector(collectorSide);
    });
  }, [
    charsTop,
    enemyHud,
    mountedRef,
    panelLeft,
    panelTop,
    playerHud,
    pulseCollector,
    showGainPopup,
  ]);

  return {
    matchFX,
    damagePopups,
    collectFX,
    gainPopups,
    showDamagePopup,
    spawnMatchFX,
    spawnCollectFX,
  };
};
