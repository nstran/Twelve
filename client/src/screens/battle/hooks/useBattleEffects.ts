import { useCallback, useRef, useState, type MutableRefObject } from 'react';
import { Animated, Easing } from 'react-native';
import {
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
}

export const useBattleEffects = ({
  mountedRef,
  panelLeft,
  panelTop,
  charsTop,
  playerHud,
  enemyHud,
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
    const { items, hitHP } = buildCollectFXItems({
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
    });
  }, [
    charsTop,
    enemyHud,
    mountedRef,
    panelLeft,
    panelTop,
    playerHud,
    showGainPopup,
  ]);

  return {
    matchFX,
    damagePopups,
    collectFX,
    gainPopups,
    showDamagePopup,
    showGainPopup,
    spawnMatchFX,
    spawnCollectFX,
  };
};
