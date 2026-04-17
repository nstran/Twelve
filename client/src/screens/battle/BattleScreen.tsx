import React, { useState, useEffect, useCallback, useMemo, useRef } from 'react';
import {
  View,
  TouchableOpacity,
  Animated, Easing,
  Text,
} from 'react-native';
import { PopupMenu, type MenuItem } from '../../components/PopupMenu';
import {
  WALK_FRAMES, ATTACK_FRAMES,
} from '../../engine/MonsterSprite';
import {
  BattlePanel,
  BattleActorsRow,
  BattleEffects,
  BattleResultOverlay,
} from './ui';
import {
  EXTRA_TURNS_BADGE_TOTAL_MS,
  RESULT_ART_META,
  ENEMY_HUD_LAYOUT,
  getBattleActorLayout,
  getBattleStageLayout,
  makeBoard,
  MONSTER_HP,
  PLAYER_HUD_LAYOUT,
  type AILevel,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
  type BattleScreenProps,
  type BattleTurn,
  type Board,
  type MoveSpec,
  s,
  BG_H,
  BG_W,
  SCREEN_H,
} from './core';
import {
  useBattleAI,
  useBattleBoardAnimations,
  useBattleEffects,
  useBattleMatchFlow,
  useBattleTurnTimer,
} from './hooks';

const PLAYER_ATTACK_TOTAL_MS = 1500;
const PLAYER_ATTACK_RUN_MS = 560;
const PLAYER_ATTACK_PREP_MS = 180;
const PLAYER_ATTACK_HIT_MS = PLAYER_ATTACK_RUN_MS + PLAYER_ATTACK_PREP_MS;
const PLAYER_ATTACK_RETURN_MS = PLAYER_ATTACK_TOTAL_MS - PLAYER_ATTACK_HIT_MS;
const PLAYER_ATTACK_RECOVER_MS = 130;
interface QueuedAttack {
  onImpact: () => void;
  onComplete: () => void;
}

export const BattleScreen: React.FC<BattleScreenProps> = ({
  monsterType, onVictory, onDefeat, onFlee,
}) => {
  const maxHP  = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP  = 100;
  const maxPow = 100;

  const [board,         setBoard]         = useState<Board>(makeBoard);
  const [selected,      setSelected]      = useState<BattleCell | null>(null);
  const [hintCell,      setHintCell]      = useState<BattleCell | null>(null);
  const [hintMove,      setHintMove]      = useState<MoveSpec | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(maxHP);
  const [enemyHP,   setEnemyHP]   = useState(maxEHP);
  const [mana,      setMana]      = useState(30);
  const [power,     setPower]     = useState(40);
  const [phase,     setPhase]     = useState<BattlePhase>('idle');
  const [result,    setResult]    = useState<BattleResult | null>(null);
  const [monFrame,  setMonFrame]  = useState<number>(WALK_FRAMES[0]);
  const [monAtk,    setMonAtk]   = useState(false);
  const [playerFrame, setPlayerFrame] = useState(0);
  const [aiLevel]  = useState<AILevel | null>('linh_canh');
  const [menuVisible, setMenuVisible] = useState(false);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<BattleTurn>('player');
  const turnRef = useRef<BattleTurn>('player');

  // ── Extra turns: match 4+ → bonus lượt ────────────────────────────────────
  const [extraTurns, setExtraTurns] = useState(0);
  const extraTurnsRef = useRef(0);
  const [showExtraTurnsBadge, setShowExtraTurnsBadge] = useState(false);
  const extraTurnsBadgeAnim = useRef(new Animated.Value(1)).current;
  const extraTurnsBadgeLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const extraTurnsBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const [turnCycle, setTurnCycle] = useState(0);

  const resultArtAnim = useRef(new Animated.Value(0)).current;
  const powerBlinkAnim = useRef(new Animated.Value(1)).current;
  const playerHPBarAnim = useRef(new Animated.Value(maxHP)).current;
  const enemyHPBarAnim = useRef(new Animated.Value(maxEHP)).current;
  const playerCollectAnim = useRef(new Animated.Value(0)).current;
  const enemyCollectAnim = useRef(new Animated.Value(0)).current;
  const playerAttackTranslateX = useRef(new Animated.Value(0)).current;
  const playerHitTranslateX = useRef(new Animated.Value(0)).current;
  const enemyAttackTranslateX = useRef(new Animated.Value(0)).current;
  const enemyHitTranslateX = useRef(new Animated.Value(0)).current;
  const powerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const playerAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const monsterAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerAttackQueueRef = useRef<QueuedAttack[]>([]);
  const monsterAttackQueueRef = useRef<QueuedAttack[]>([]);
  const playerAttackRunningRef = useRef(false);
  const monsterAttackRunningRef = useRef(false);
  const phaseRef   = useRef<BattlePhase>('idle');
  const mountedRef = useRef(true);
  const boardRef   = useRef<Board>(board);
  const enemyHPRef = useRef(enemyHP);
  const playerHPRef= useRef(playerHP);
  const selectedRef = useRef<BattleCell | null>(selected);
  const hintMoveRef = useRef<MoveSpec | null>(hintMove);

  useEffect(() => { phaseRef.current = phase; }, [phase]);
  useEffect(() => () => { mountedRef.current = false; }, []);
  useEffect(() => { boardRef.current = board; }, [board]);
  useEffect(() => { enemyHPRef.current = enemyHP; }, [enemyHP]);
  useEffect(() => { playerHPRef.current = playerHP; }, [playerHP]);
  useEffect(() => { selectedRef.current = selected; }, [selected]);
  useEffect(() => { hintMoveRef.current = hintMove; }, [hintMove]);
  useEffect(() => { turnRef.current = turn; }, [turn]);
  useEffect(() => { extraTurnsRef.current = extraTurns; }, [extraTurns]);
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
  useEffect(() => () => {
    extraTurnsBadgeLoopRef.current?.stop();
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }
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
    extraTurnsBadgeLoopRef.current?.stop();
    extraTurnsBadgeLoopRef.current = null;
    if (extraTurnsBadgeTimeoutRef.current !== null) {
      clearTimeout(extraTurnsBadgeTimeoutRef.current);
      extraTurnsBadgeTimeoutRef.current = null;
    }

    if (extraTurns <= 0) {
      setShowExtraTurnsBadge(false);
      extraTurnsBadgeAnim.setValue(1);
      return;
    }

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
      extraTurnsBadgeTimeoutRef.current = null;
    }, EXTRA_TURNS_BADGE_TOTAL_MS);
  }, [extraTurns, extraTurnsBadgeAnim]);
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
    if (result === null) {
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
  }, [result, resultArtAnim]);

  const { panelLeft, panelTop, charsTop, damagePopupTop, charsRowHeight, monsterSize } =
    getBattleStageLayout(monsterType);
  const { attackTravelX } = getBattleActorLayout(monsterType);
  const playerHud = PLAYER_HUD_LAYOUT;
  const enemyHud = ENEMY_HUD_LAYOUT;
  const {
    matchFX,
    damagePopups,
    collectFX,
    gainPopups,
    showDamagePopup,
    spawnMatchFX,
    spawnCollectFX,
  } = useBattleEffects({
    mountedRef,
    panelLeft,
    panelTop,
    charsTop,
    playerHud,
    enemyHud,
    playerCollectAnim,
    enemyCollectAnim,
  });
  const {
    offsets,
    swapOffsetsX,
    swapOffsetsY,
    playExplosion,
    animateFall,
    animateInvalidSwapBounce,
    resetBoardAnim,
  } = useBattleBoardAnimations({
    mountedRef,
    boardRef,
    setBoard,
    setExplodeFrames,
    onSpawnFX: spawnMatchFX,
  });

  // ── Monster animation ──────────────────────────────────────────────────────
  const monTick = useRef(0);
  useEffect(() => {
    const t = setInterval(() => {
      if (!mountedRef.current) return;
      monTick.current++;
      const frames = monAtk ? ATTACK_FRAMES : WALK_FRAMES;
      setMonFrame(frames[monTick.current % frames.length]);
    }, 200);
    return () => clearInterval(t);
  }, [monAtk]);

  // ── Enemy auto-attack REMOVED — monster now plays turn-based on the board ──
  const showBonusBanner = useCallback((_msg: string) => {}, []);
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
    setPlayerFrame(1);

    const prepTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerFrame(2);
    }, PLAYER_ATTACK_RUN_MS);

    const hitTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerFrame(3);
      nextAttack.onImpact();

      Animated.sequence([
        Animated.timing(enemyHitTranslateX, {
          toValue: 14,
          duration: 70,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(enemyHitTranslateX, {
          toValue: -8,
          duration: 90,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(enemyHitTranslateX, {
          toValue: 0,
          duration: 110,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();
    }, PLAYER_ATTACK_HIT_MS);

    const recoverTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerFrame(1);
    }, Math.min(PLAYER_ATTACK_TOTAL_MS - 80, PLAYER_ATTACK_HIT_MS + PLAYER_ATTACK_RECOVER_MS));

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setPlayerFrame(0);
      nextAttack.onComplete();
      playerAttackRunningRef.current = false;
      playerAttackTimersRef.current = [];
      runNextPlayerSwordAttack();
    }, PLAYER_ATTACK_TOTAL_MS);

    playerAttackTimersRef.current = [prepTimer, hitTimer, recoverTimer, completeTimer];

    Animated.sequence([
      Animated.timing(playerAttackTranslateX, {
        toValue: attackTravelX,
        duration: PLAYER_ATTACK_HIT_MS,
        easing: Easing.out(Easing.cubic),
        useNativeDriver: true,
      }),
      Animated.timing(playerAttackTranslateX, {
        toValue: 0,
        duration: PLAYER_ATTACK_RETURN_MS,
        easing: Easing.inOut(Easing.quad),
        useNativeDriver: true,
      }),
    ]).start();
  }, [attackTravelX, enemyHitTranslateX, mountedRef, playerAttackTranslateX]);
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

      Animated.sequence([
        Animated.timing(playerHitTranslateX, {
          toValue: -14,
          duration: 70,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(playerHitTranslateX, {
          toValue: 8,
          duration: 90,
          easing: Easing.inOut(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(playerHitTranslateX, {
          toValue: 0,
          duration: 110,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();
    }, PLAYER_ATTACK_HIT_MS);

    const completeTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setMonAtk(false);
      nextAttack.onComplete();
      monsterAttackRunningRef.current = false;
      monsterAttackTimersRef.current = [];
      runNextMonsterSwordAttack();
    }, PLAYER_ATTACK_TOTAL_MS);

    monsterAttackTimersRef.current = [hitTimer, completeTimer];

    Animated.sequence([
      Animated.timing(enemyAttackTranslateX, {
        toValue: -attackTravelX,
        duration: PLAYER_ATTACK_HIT_MS,
        easing: Easing.out(Easing.cubic),
        useNativeDriver: true,
      }),
      Animated.timing(enemyAttackTranslateX, {
        toValue: 0,
        duration: PLAYER_ATTACK_RETURN_MS,
        easing: Easing.inOut(Easing.quad),
        useNativeDriver: true,
      }),
    ]).start();
  }, [attackTravelX, enemyAttackTranslateX, mountedRef, playerHitTranslateX]);
  const playMonsterSwordAttack = useCallback((onImpact: () => void, onComplete: () => void) => {
    monsterAttackQueueRef.current.push({ onImpact, onComplete });
    runNextMonsterSwordAttack();
  }, [runNextMonsterSwordAttack]);
  const { doDirectSwap } = useBattleMatchFlow({
    mountedRef,
    phaseRef,
    turnRef,
    extraTurnsRef,
    boardRef,
    maxHP,
    maxEHP,
    maxMP,
    maxPow,
    setBoard,
    setPhase,
    setTurn,
    setExtraTurns,
    setEnemyHP,
    setPlayerHP,
    setMana,
    setPower,
    setResult,
    playPlayerSwordAttack,
    playMonsterSwordAttack,
    showBonusBanner,
    showDamagePopup,
    spawnCollectFX,
    playExplosion,
    animateFall,
    animateInvalidSwapBounce,
    resetBoardAnim,
  });

  const doDirectSwapRef = useRef(doDirectSwap);
  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);

  const {
    clearAiTimers,
    pickRandomValidMove,
    runAutoPlayerMove,
    setAiStep,
  } = useBattleAI({
    phase,
    turn,
    aiLevel,
    result,
    turnCycle,
    mountedRef,
    phaseRef,
    turnRef,
    boardRef,
    playerHPRef,
    enemyHPRef,
    doDirectSwapRef,
    setSelected,
    setHintCell,
    setHintMove,
    setTurn,
  });
  const { turnTimeLeft } = useBattleTurnTimer({
    phase,
    turn,
    result,
    turnCycle,
    mountedRef,
    phaseRef,
    turnRef,
    extraTurnsRef,
    boardRef,
    selectedRef,
    hintMoveRef,
    clearAiTimers,
    pickRandomValidMove,
    runAutoPlayerMove,
    setAiStep,
    setSelected,
    setHintCell,
    setHintMove,
    setExtraTurns,
    setTurn,
    setTurnCycle,
    showBonusBanner,
  });

  // ── Human tap ─────────────────────────────────────────────────────────────
  const handleGemPress = useCallback((row: number, col: number) => {
    if (phase !== 'idle' || turn !== 'player') return; // Chỉ cho tap khi lượt player
    setHintCell(null);
    setHintMove(null);
    if (!selected) { setSelected([row, col]); return; }
    const [sr, sc] = selected;
    if (sr === row && sc === col) { setSelected(null); return; }
    const adj = (Math.abs(sr - row) === 1 && sc === col) ||
                (sr === row && Math.abs(sc - col) === 1);
    if (!adj) { setSelected([row, col]); return; }
    setSelected(null);
    doDirectSwapRef.current(sr, sc, row, col);
  }, [selected, phase, turn]);

  // ── Skill ──────────────────────────────────────────────────────────────────
  const handleSkill = useCallback(() => {
    if (mana < 30 || phase !== 'idle' || turn !== 'player') return;
    setHintCell(null);
    setHintMove(null);
    setMana(m => m - 30);
    const dmg = 40 + Math.floor(Math.random() * 20);
    showDamagePopup('enemy', dmg);
    setEnemyHP(hp => {
      const next = Math.max(0, hp - dmg);
      if (next === 0 && phaseRef.current !== 'over') {
        phaseRef.current = 'over'; setPhase('over'); setResult('victory');
      }
      return next;
    });
    // Skill also ends player turn → switch to monster
    turnRef.current = 'monster'; setTurn('monster');
  }, [mana, phase, showDamagePopup, turn]);

  const { w: mW, h: mH } = monsterSize;
  const resultMeta = result ? RESULT_ART_META[result] : null;
  const resultArtScale = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [0.72, 1],
  });
  const resultArtTilt = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: ['-8deg', '-5deg'],
  });
  const resultArtLift = resultArtAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [14, 0],
  });
  const playerDamageLeft = panelLeft + 4;
  const enemyDamageLeft = panelLeft + BG_W - 84;
  const battleBottomGap = Math.max(0, SCREEN_H - (panelTop + BG_H));
  const battleMenuBottomOffset = battleBottomGap + 23;
  const battleMenuItems = useMemo<MenuItem[]>(() => [
    {
      id: 'battle-exit',
      label: 'Thoát trận',
      onPress: onFlee,
    },
  ], [onFlee]);

  useEffect(() => {
    if (result !== null && menuVisible) {
      setMenuVisible(false);
    }
  }, [menuVisible, result]);

  return (
    <View style={s.root}>
      <TouchableOpacity
        activeOpacity={0.85}
        onPress={() => {
          if (result !== null) return;
          setMenuVisible((prev) => !prev);
        }}
        disabled={result !== null}
        style={[
          s.fleeIconBtn,
          {
            left: panelLeft + 4,
            top: panelTop + BG_H - 26,
          },
          result !== null && s.fleeIconBtnDisabled,
        ]}
      >
        <Text style={s.fleeTextBtn}>Menu</Text>
      </TouchableOpacity>

      <BattlePanel
        panelLeft={panelLeft}
        panelTop={panelTop}
        board={board}
        selected={selected}
        hintCell={hintCell}
        explodeFrames={explodeFrames}
        turn={turn}
        matchFX={matchFX}
        extraTurns={extraTurns}
        showExtraTurnsBadge={showExtraTurnsBadge}
        turnTimeLeft={turnTimeLeft}
        mana={mana}
        power={power}
        maxHP={maxHP}
        maxEHP={maxEHP}
        maxMP={maxMP}
        maxPow={maxPow}
        offsets={offsets}
        swapOffsetsX={swapOffsetsX}
        swapOffsetsY={swapOffsetsY}
        extraTurnsBadgeAnim={extraTurnsBadgeAnim}
        playerHPBarAnim={playerHPBarAnim}
        enemyHPBarAnim={enemyHPBarAnim}
        powerBlinkAnim={powerBlinkAnim}
        onGemPress={handleGemPress}
      />

      <BattleActorsRow
        panelLeft={panelLeft}
        charsTop={charsTop}
        charsHeight={charsRowHeight}
        monsterType={monsterType}
        monFrame={monFrame}
        playerFrame={playerFrame}
        monsterWidth={mW}
        monsterHeight={mH}
        playerCollectAnim={playerCollectAnim}
        enemyCollectAnim={enemyCollectAnim}
        playerAttackTranslateX={playerAttackTranslateX}
        playerHitTranslateX={playerHitTranslateX}
        enemyAttackTranslateX={enemyAttackTranslateX}
        enemyHitTranslateX={enemyHitTranslateX}
      />

      <BattleEffects
        panelLeft={panelLeft}
        panelTop={panelTop}
        charsTop={charsTop}
        damagePopupTop={damagePopupTop}
        playerDamageLeft={playerDamageLeft}
        enemyDamageLeft={enemyDamageLeft}
        damagePopups={damagePopups}
        gainPopups={gainPopups}
        collectFX={collectFX}
      />

      <PopupMenu
        visible={menuVisible}
        items={battleMenuItems}
        selectedIndex={menuSelectedIndex}
        onIndexChange={setMenuSelectedIndex}
        onSelect={() => {}}
        onClose={() => setMenuVisible(false)}
        bottomOffset={battleMenuBottomOffset}
      />

      <BattleResultOverlay
        result={result}
        resultMeta={resultMeta}
        panelTop={panelTop}
        resultArtAnim={resultArtAnim}
        resultArtLift={resultArtLift}
        resultArtScale={resultArtScale}
        resultArtTilt={resultArtTilt}
        onVictory={onVictory}
        onDefeat={onDefeat}
      />
    </View>
  );
};
