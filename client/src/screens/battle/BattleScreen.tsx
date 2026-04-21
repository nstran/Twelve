import React, { useState, useEffect, useCallback, useMemo, useRef } from 'react';
import {
  View,
  Animated, Easing,
} from 'react-native';
import type { CharacterAction } from '../../engine/character';
import { SoftkeyBar } from '../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu, type MenuItem } from '../../components/controls/PopupMenu/PopupMenu';
import {
  WALK_FRAMES, ATTACK_FRAMES,
} from '../../engine/MonsterSprite';
import {
  BattlePanel,
  BattleActorsRow,
  BattleEffects,
  BattleSkillCastOverlay,
  BattleSkillPanel,
  BattleResultOverlay,
} from './ui';
import {
  BOARD_LEFT,
  BOARD_TOP,
  EXTRA_TURNS_BADGE_TOTAL_MS,
  RESULT_ART_META,
  ENEMY_HUD_LAYOUT,
  BATTLE_SKILLS,
  collapseLogic,
  clearMatchedCells,
  createJavaBoardEngine,
  getBattleActorLayout,
  getBattleElement,
  getBattleStageLayout,
  GEM_SIZE,
  makeBoard,
  MONSTER_HP,
  PLAYER_HUD_LAYOUT,
  type ActiveBattleSkillCast,
  type AILevel,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
  type BattleScreenProps,
  type SkillFamilyCode,
  type BattleTurn,
  type Board,
  type MoveSpec,
  s,
  BG_W,
} from './core';
import {
  useBattleAI,
  useBattleBoardAnimations,
  useBattleEffects,
  useBattleMatchFlow,
  useBattleTurnTimer,
} from './hooks';

const JAVA_BATTLE_TICK_MS = 40;
const JAVA_ATTACK_MIN_STEP_PX = 5;
const JAVA_ATTACK_MIN_TRAVEL_TICKS = 10;
const JAVA_ATTACK_HOLD_TICKS = 20;
const JAVA_ATTACK_FRAME_2_TICKS = 6;
const JAVA_ATTACK_IMPACT_TICKS = 11;
const JAVA_ATTACK_FRAME_4_TICKS = 16;
const MONSTER_ANIM_TICK_MS = 240;
const PLAYER_HIT_REACT_TOTAL_MS = 320;
const PLAYER_DEFEAT_RESULT_DELAY_MS = 360;
const SKILL_MANA_COST = 0;
const LOCAL_SKILL_DAMAGE = 50;
const ASSET_SOFTKEY_MENU = require('../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');
const JAVA_DEFAULT_CURSOR_CELL: BattleCell = [3, 4];
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

export const BattleScreen: React.FC<BattleScreenProps> = ({
  monsterType, appearance, initialTurn = 'player', onVictory, onDefeat, onFlee,
}) => {
  const maxHP  = 100;
  const maxEHP = MONSTER_HP[monsterType] ?? 150;
  const maxMP  = 100;
  const maxPow = 100;

  const boardEngineRef = useRef(createJavaBoardEngine());
  const [board,         setBoard]         = useState<Board>(() => makeBoard(boardEngineRef.current));
  const [cursorCell,    setCursorCell]    = useState<BattleCell>(JAVA_DEFAULT_CURSOR_CELL);
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
  const [playerAction, setPlayerAction] = useState<CharacterAction>('idle');
  const [playerActionFrameIndex, setPlayerActionFrameIndex] = useState<number | null>(null);
  const [playerReactionPose, setPlayerReactionPose] = useState(false);
  const [playerDefeatPose, setPlayerDefeatPose] = useState(false);
  const [playerRetreatPose, setPlayerRetreatPose] = useState(false);
  const [aiLevel]  = useState<AILevel | null>('linh_canh');
  const [menuVisible, setMenuVisible] = useState(false);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);
  const [skillPanelVisible, setSkillPanelVisible] = useState(false);
  const [selectedSkillFamily, setSelectedSkillFamily] = useState<SkillFamilyCode | null>(null);
  const [activeSkillCasts, setActiveSkillCasts] = useState<ActiveBattleSkillCast[]>([]);
  const battleElement = getBattleElement(appearance.elementIndex);

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<BattleTurn>(initialTurn);
  const turnRef = useRef<BattleTurn>(initialTurn);

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
  const playerAttackTranslateX = useRef(new Animated.Value(0)).current;
  const playerHitTranslateX = useRef(new Animated.Value(0)).current;
  const enemyAttackTranslateX = useRef(new Animated.Value(0)).current;
  const enemyHitTranslateX = useRef(new Animated.Value(0)).current;
  const powerBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const playerAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const monsterAttackTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerReactionTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerResultTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerAttackQueueRef = useRef<QueuedAttack[]>([]);
  const monsterAttackQueueRef = useRef<QueuedAttack[]>([]);
  const skillCastTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const playerAttackRunningRef = useRef(false);
  const monsterAttackRunningRef = useRef(false);
  const playerDefeatStartedRef = useRef(false);
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
    playerReactionTimersRef.current.forEach(clearTimeout);
    playerResultTimersRef.current.forEach(clearTimeout);
    playerAttackTimersRef.current = [];
    monsterAttackTimersRef.current = [];
    playerReactionTimersRef.current = [];
    playerResultTimersRef.current = [];
    skillCastTimersRef.current.forEach(clearTimeout);
    skillCastTimersRef.current = [];
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
  }, [result, resultArtAnim]);

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
  }, [clearPlayerReactionTimers, setResult]);

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
  }, [clearPlayerReactionTimers]);
  const playEnemySkillImpact = useCallback((shakePx: number) => {
    const amplitude = Math.max(4, shakePx);
    enemyHitTranslateX.stopAnimation();
    enemyHitTranslateX.setValue(0);

    Animated.sequence([
      Animated.timing(enemyHitTranslateX, {
        toValue: -amplitude,
        duration: 50,
        easing: Easing.linear,
        useNativeDriver: true,
      }),
      Animated.timing(enemyHitTranslateX, {
        toValue: amplitude * 0.7,
        duration: 60,
        easing: Easing.linear,
        useNativeDriver: true,
      }),
      Animated.timing(enemyHitTranslateX, {
        toValue: -amplitude * 0.4,
        duration: 50,
        easing: Easing.linear,
        useNativeDriver: true,
      }),
      Animated.timing(enemyHitTranslateX, {
        toValue: 0,
        duration: 70,
        easing: Easing.linear,
        useNativeDriver: true,
      }),
    ]).start();
  }, [enemyHitTranslateX]);
  const { panelLeft, panelTop, charsTop, damagePopupTop, charsRowHeight, monsterSize } =
    getBattleStageLayout(monsterType);
  const {
    attackTravelX,
    playerBaseLeft,
    monsterBaseLeft,
    playerSize,
    monsterGroundOffset,
  } = useMemo(
    () => getBattleActorLayout(monsterType, appearance),
    [appearance, monsterType],
  );
  const swordAttackTiming = useMemo(
    () => getSwordAttackTiming(attackTravelX),
    [attackTravelX],
  );
  const playerHud = PLAYER_HUD_LAYOUT;
  const enemyHud = ENEMY_HUD_LAYOUT;
  const {
    matchFX,
    damagePopups,
    collectFX,
    gainPopups,
    showDamagePopup,
    showGainPopup,
    spawnMatchFX,
    spawnCollectFX,
  } = useBattleEffects({
    mountedRef,
    panelLeft,
    panelTop,
    charsTop,
    playerHud,
    enemyHud,
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
    boardEngineRef,
    setBoard,
    setExplodeFrames,
    onSpawnFX: spawnMatchFX,
  });
  const applyLocalSkillBoardBreak = useCallback((familyCode: SkillFamilyCode, boardPoints: ActiveBattleSkillCast['boardPoints']) => {
    if (
      familyCode !== 1000 &&
      familyCode !== 1006 &&
      familyCode !== 2003 &&
      familyCode !== 4000 &&
      familyCode !== 4006 &&
      familyCode !== 4008
    ) {
      return;
    }

    const matched = new Set(
      boardPoints.map(point => `${point.row},${point.col}`),
    );
    if (matched.size === 0) return;

    const currentBoard = boardRef.current;
    const clearedBoard = clearMatchedCells(currentBoard, matched);

    playExplosion(matched, matched, currentBoard, () => {
      if (!mountedRef.current) return;
      const { newBoard, fallMap } = collapseLogic(clearedBoard, matched, boardEngineRef.current);
      boardRef.current = newBoard;
      animateFall(newBoard, fallMap, () => {});
    });
  }, [animateFall, boardEngineRef, boardRef, mountedRef, playExplosion]);

  // ── Monster animation ──────────────────────────────────────────────────────
  const monTick = useRef(0);
  useEffect(() => {
    const t = setInterval(() => {
      if (!mountedRef.current) return;
      monTick.current++;
      const frames = monAtk ? ATTACK_FRAMES : WALK_FRAMES;
      setMonFrame(frames[monTick.current % frames.length]);
    }, MONSTER_ANIM_TICK_MS);
    return () => clearInterval(t);
  }, [monAtk]);

  // ── Enemy auto-attack REMOVED — monster now plays turn-based on the board ──
  const showBonusBanner = useCallback((msg: string) => {
    showGainPopup('player', msg);
  }, [showGainPopup]);
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
  }, [attackTravelX, enemyHitTranslateX, mountedRef, playerAttackTranslateX, swordAttackTiming]);
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
  }, [attackTravelX, enemyAttackTranslateX, mountedRef, playerHitTranslateX, swordAttackTiming]);
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
    boardEngineRef,
    maxHP,
    maxEHP,
    maxMP,
    maxPow,
    setBoard,
    setPhase,
    setTurn,
    setExtraTurns,
    setTurnCycle,
    setEnemyHP,
    setPlayerHP,
    setMana,
    setPower,
    setResult,
    playPlayerSwordAttack,
    playMonsterSwordAttack,
    onPlayerHit: playPlayerHitReaction,
    onPlayerDefeat: startPlayerDefeatSequence,
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
    boardEngineRef,
    playerHPRef,
    enemyHPRef,
    doDirectSwapRef,
    setCursorCell,
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
    setCursorCell([row, col]);
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

  const getCellCenter = useCallback((row: number, col: number) => ({
    x: panelLeft + BOARD_LEFT + col * GEM_SIZE + GEM_SIZE / 2,
    y: panelTop + BOARD_TOP + row * GEM_SIZE + GEM_SIZE / 2,
  }), [panelLeft, panelTop]);

  const buildLocalSkillCastPoints = useCallback((familyCode: SkillFamilyCode) => {
    const [cursorRow, cursorCol] = cursorCell;
    const clampRow = (row: number) => Math.max(0, Math.min(9, row));
    const clampCol = (col: number) => Math.max(0, Math.min(7, col));
    const uniqueCells = (cells: BattleCell[]) => Array.from(
      new Map(
        cells.map(([row, col]) => [
          `${clampRow(row)}-${clampCol(col)}`,
          [clampRow(row), clampCol(col)] as BattleCell,
        ]),
      ).values(),
    );
    const toPoints = (cells: BattleCell[]) => uniqueCells(cells).map(([row, col]) => {
      const point = getCellCenter(row, col);
      return { ...point, row, col };
    });

    const tileBurstPoints = toPoints([
      [cursorRow, cursorCol],
      [cursorRow, cursorCol + 1],
      [cursorRow + 1, cursorCol],
      [cursorRow + 1, cursorCol + 1],
    ]);
    const singleEffectPoint = toPoints([[cursorRow, cursorCol]]);
    const pillarPoints = toPoints([
      [cursorRow, cursorCol],
      [cursorRow - 1, cursorCol],
      [cursorRow + 1, cursorCol],
      [cursorRow, cursorCol + 1],
    ]);
    const stagedColumnPoints = toPoints([
      [0, cursorCol - 1],
      [0, cursorCol],
      [0, cursorCol + 1],
      [0, cursorCol + 2],
    ]);
    const sweepColumns = toPoints([
      [0, cursorCol - 1],
      [0, cursorCol],
      [0, cursorCol + 1],
    ]);

    switch (familyCode) {
      case 1000:
      case 1006:
      case 4000:
      case 4006:
      case 4008:
        return { boardPoints: tileBurstPoints, effectPoints: tileBurstPoints };
      case 2003:
        return { boardPoints: tileBurstPoints, effectPoints: [] };
      case 1001:
        return { boardPoints: tileBurstPoints, effectPoints: tileBurstPoints };
      case 2000:
        return { boardPoints: tileBurstPoints, effectPoints: tileBurstPoints };
      case 1007:
      case 2007:
      case 4007:
        return { boardPoints: pillarPoints, effectPoints: pillarPoints };
      case 1008:
        return {
          boardPoints: stagedColumnPoints,
          effectPoints: toPoints([[0, cursorCol]]),
        };
      case 2006:
        return { boardPoints: sweepColumns, effectPoints: sweepColumns };
      case 2008:
        return {
          boardPoints: toPoints([
            [cursorRow, cursorCol],
            [cursorRow - 1, cursorCol],
            [cursorRow + 1, cursorCol],
            [cursorRow, cursorCol - 1],
            [cursorRow, cursorCol + 1],
            [cursorRow - 1, cursorCol - 1],
            [cursorRow - 1, cursorCol + 1],
            [cursorRow + 1, cursorCol + 1],
          ]),
          effectPoints: toPoints([
            [cursorRow, cursorCol],
            [cursorRow - 1, cursorCol],
            [cursorRow + 1, cursorCol],
            [cursorRow, cursorCol - 1],
            [cursorRow, cursorCol + 1],
          ]),
        };
      default:
        return { boardPoints: singleEffectPoint, effectPoints: singleEffectPoint };
    }
  }, [cursorCell, getCellCenter]);

  const handleSkillCast = useCallback((familyCode: SkillFamilyCode) => {
    const skill = BATTLE_SKILLS[familyCode];
    if (mana < SKILL_MANA_COST || phase !== 'idle' || turn !== 'player' || result !== null) return;

    setSkillPanelVisible(false);
    setMenuVisible(false);
    setSelectedSkillFamily(familyCode);
    setSelected(null);
    setHintCell(null);
    setHintMove(null);
    setMana(current => Math.max(0, current - SKILL_MANA_COST));
    setPhase('busy');
    phaseRef.current = 'busy';
    setPlayerAction('attack');
    setPlayerActionFrameIndex(0);

    const sourceX = panelLeft + playerBaseLeft + playerSize.w * 0.7;
    const sourceY = charsTop + charsRowHeight - (playerSize.groundOffset ?? 0) - playerSize.h * 0.6;
    const targetX = panelLeft + monsterBaseLeft + monsterSize.w * 0.42;
    const targetY = charsTop + charsRowHeight - monsterGroundOffset - monsterSize.h * 0.52;
    const castKey = `skill-${familyCode}-${Date.now()}`;
    const { boardPoints, effectPoints } = buildLocalSkillCastPoints(familyCode);
    const cast: ActiveBattleSkillCast = {
      key: castKey,
      familyCode,
      startedAt: Date.now(),
      sourceX,
      sourceY,
      targetX,
      targetY,
      boardPoints,
      effectPoints,
      durationMs: skill.totalMs,
    };

    setActiveSkillCasts(prev => [...prev, cast]);

    const impactTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      applyLocalSkillBoardBreak(familyCode, boardPoints);
      playEnemySkillImpact(skill.hitShakePx);
      showDamagePopup('enemy', LOCAL_SKILL_DAMAGE);
      setEnemyHP(hp => {
        const next = Math.max(0, hp - LOCAL_SKILL_DAMAGE);
        if (next === 0 && phaseRef.current !== 'over') {
          phaseRef.current = 'over';
          setPhase('over');
          setResult('victory');
        }
        return next;
      });
    }, skill.impactDelayMs);

    const finishTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setActiveSkillCasts(prev => prev.filter(item => item.key !== castKey));
      setPlayerAction('idle');
      setPlayerActionFrameIndex(null);
      if (phaseRef.current === 'over') return;
      turnRef.current = 'monster';
      setTurn('monster');
      setTurnCycle(cycle => cycle + 1);
      phaseRef.current = 'idle';
      setPhase('idle');
    }, skill.totalMs);

    skillCastTimersRef.current.push(impactTimer, finishTimer);
  }, [
    applyLocalSkillBoardBreak,
    buildLocalSkillCastPoints,
    charsRowHeight,
    charsTop,
    mana,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize.h,
    monsterSize.w,
    panelLeft,
    phase,
    playerBaseLeft,
    playerSize.groundOffset,
    playerSize.h,
    result,
    showDamagePopup,
    playEnemySkillImpact,
    turn,
  ]);

  // ── Skill ──────────────────────────────────────────────────────────────────
  const handleSkill = useCallback(() => {
    if (mana < SKILL_MANA_COST || phase !== 'idle' || turn !== 'player' || result !== null) return;
    setHintCell(null);
    setHintMove(null);
    setSelectedSkillFamily(prev => prev ?? (battleElement === 0 ? 1000 : battleElement === 1 ? 2000 : 4000));
    setSkillPanelVisible(true);
    setMenuVisible(false);
  }, [battleElement, mana, phase, result, turn]);

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
  const battleMenuItems = useMemo<MenuItem[]>(() => [
    {
      id: 'battle-skill',
      label: 'Tuyệt Chiêu',
      onPress: handleSkill,
    },
    {
      id: 'battle-bag',
      label: 'Túi đồ',
      onPress: () => showBonusBanner('Túi đồ chưa phục dựng'),
    },
    {
      id: 'battle-surrender',
      label: 'Đầu hàng',
      onPress: onFlee,
    },
  ], [handleSkill, onFlee, showBonusBanner]);

  useEffect(() => {
    if (result !== null && menuVisible) {
      setMenuVisible(false);
    }
  }, [menuVisible, result]);
  useEffect(() => {
    if (result !== null && skillPanelVisible) {
      setSkillPanelVisible(false);
    }
  }, [result, skillPanelVisible]);

  const handleBattleMenuConfirm = useCallback(() => {
    const item = battleMenuItems[menuSelectedIndex];
    if (!item) return;
    item.onPress?.();
    setMenuVisible(false);
  }, [battleMenuItems, menuSelectedIndex]);

  const handleLeftSoftkey = useCallback(() => {
    if (result !== null) return;
    if (menuVisible) {
      handleBattleMenuConfirm();
      return;
    }
    setMenuSelectedIndex(0);
    setMenuVisible(true);
  }, [handleBattleMenuConfirm, menuVisible, result]);

  const handleRightSoftkey = useCallback(() => {
    if (menuVisible) {
      setMenuVisible(false);
    }
  }, [menuVisible]);

  return (
    <View style={s.root}>
      <BattlePanel
        panelLeft={panelLeft}
        panelTop={panelTop}
        board={board}
        cursorCell={turn === 'player' && result === null ? cursorCell : null}
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
        appearance={appearance}
        monsterType={monsterType}
        monFrame={monFrame}
        playerAction={playerAction}
        playerActionFrameIndex={playerActionFrameIndex}
        playerReactionPose={playerReactionPose}
        playerDefeatPose={playerDefeatPose}
        playerRetreatPose={playerRetreatPose}
        monsterWidth={mW}
        monsterHeight={mH}
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

      <BattleSkillCastOverlay casts={activeSkillCasts} />

      <BattleSkillPanel
        visible={skillPanelVisible}
        elementIndex={appearance.elementIndex}
        selectedFamily={selectedSkillFamily}
        onHighlight={setSelectedSkillFamily}
        onCast={handleSkillCast}
        onClose={() => setSkillPanelVisible(false)}
      />

      <PopupMenu
        visible={menuVisible}
        items={battleMenuItems}
        selectedIndex={menuSelectedIndex}
        onIndexChange={setMenuSelectedIndex}
        onSelect={() => {}}
        onClose={() => setMenuVisible(false)}
        bottomOffset={27}
      />

      <SoftkeyBar
        width={BG_W}
        onLeftPress={handleLeftSoftkey}
        onRightPress={menuVisible ? handleRightSoftkey : undefined}
        leftIcon={menuVisible ? ASSET_SOFTKEY_OK : ASSET_SOFTKEY_MENU}
        rightIcon={menuVisible ? ASSET_SOFTKEY_CANCEL : undefined}
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
