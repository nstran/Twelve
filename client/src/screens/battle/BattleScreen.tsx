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
  EXTRA_TURNS_BADGE_TOTAL_MS,
  RESULT_ART_META,
  ENEMY_HUD_LAYOUT,
  BATTLE_SKILLS,
  buildActiveBattleSkillCastFromPacket,
  clearMatchedCells,
  collapseLogic,
  collectSkillPacketBoardKeys,
  createJavaBoardEngine,
  findMatchesFromAffected,
  getFirstBattleSkillServerPacketReadyFamily,
  getBattleActorLayout,
  getBattleElement,
  getBattleStageLayout,
  isBattleSkillServerPacketReady,
  makeBoard,
  MONSTER_HP,
  PLAYER_HUD_LAYOUT,
  type ActiveBattleSkillCast,
  type AILevel,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
  type BattleScreenProps,
  type BattleSkillRuntimePacket,
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
const ASSET_SOFTKEY_MENU = require('../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');
const JAVA_DEFAULT_CURSOR_CELL: BattleCell = [3, 4];
const DEFAULT_BATTLE_SKILL_LEVEL = 12;
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
  monsterType, appearance, initialTurn = 'player', onVictory, onDefeat, onFlee, resolveSkillPacket,
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
  const [fireSwordMarkTriggers, setFireSwordMarkTriggers] = useState<Record<string, number>>({});
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
  const [extraTurnsBadgeValue, setExtraTurnsBadgeValue] = useState(0);
  const [showExtraTurnsBadge, setShowExtraTurnsBadge] = useState(false);
  const extraTurnsBadgeAnim = useRef(new Animated.Value(1)).current;
  const extraTurnsBadgeLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const extraTurnsBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const [comboMultiplier, setComboMultiplier] = useState(0);
  const [showComboBadge, setShowComboBadge] = useState(false);
  const comboBadgeAnim = useRef(new Animated.Value(0)).current;
  const comboBadgeTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
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
  const skillPacketRequestRef = useRef(false);
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
    if (comboBadgeTimeoutRef.current !== null) {
      clearTimeout(comboBadgeTimeoutRef.current);
      comboBadgeTimeoutRef.current = null;
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
  }, [extraTurnsBadgeAnim]);
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
  const processMatchesRef = useRef<ReturnType<typeof useBattleMatchFlow>['processMatches'] | null>(null);
  const applyServerPacketMarkCell = useCallback((cell: BattleCell, stateId: number) => {
    const [row, col] = cell;
    if (stateId === 10) {
      const key = `${row},${col}`;
      setFireSwordMarkTriggers(current => ({
        ...current,
        [key]: (current[key] ?? 0) + 1,
      }));
    }
    setBoard(currentBoard => {
      if (row < 0 || row >= currentBoard.length || col < 0 || col >= currentBoard[row].length) {
        return currentBoard;
      }

      if (currentBoard[row][col] === stateId) {
        return currentBoard;
      }

      const nextBoard = currentBoard.map(boardRow => [...boardRow]);
      nextBoard[row][col] = stateId as Board[number][number];
      boardRef.current = nextBoard;
      return nextBoard;
    });
  }, [setBoard]);

  const applyServerPacketBoardMutation = useCallback((packet: BattleSkillRuntimePacket) => {
    switch (packet.boardMutation.kind) {
      case 'clear': {
        const matched = collectSkillPacketBoardKeys(packet);
        if (matched.size === 0) return;

        const currentBoard = boardRef.current;
        const clearedBoard = clearMatchedCells(currentBoard, matched);

        playExplosion(matched, matched, currentBoard, () => {
          if (!mountedRef.current) return;
          const { newBoard, fallMap, affectedKeys } = collapseLogic(clearedBoard, matched, boardEngineRef.current);
          boardRef.current = newBoard;
          animateFall(newBoard, fallMap, () => {
            if (!mountedRef.current || !processMatchesRef.current) return;
            if (findMatchesFromAffected(newBoard, affectedKeys).size === 0) return;
            processMatchesRef.current(newBoard, 0, affectedKeys);
          });
        });
        return;
      }
      case 'mark': {
        const stateId = packet.boardMutation.stateId ?? 10;
        if (packet.boardMutation.cells.length === 0) return;
        for (const cell of packet.boardMutation.cells) {
          applyServerPacketMarkCell(cell, stateId);
        }
        return;
      }
      case 'helper':
      case 'none':
      default:
        return;
    }
  }, [animateFall, applyServerPacketMarkCell, boardEngineRef, mountedRef, playExplosion]);

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
  const { doDirectSwap, processMatches } = useBattleMatchFlow({
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
    flashExtraTurnsBadge,
    flashComboBadge,
    showDamagePopup,
    spawnCollectFX,
    playExplosion,
    animateFall,
    animateInvalidSwapBounce,
    resetBoardAnim,
  });

  const doDirectSwapRef = useRef(doDirectSwap);
  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);
  useEffect(() => { processMatchesRef.current = processMatches; }, [processMatches]);

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

  const handleSkillCast = useCallback(async (familyCode: SkillFamilyCode) => {
    const skill = BATTLE_SKILLS[familyCode];
    if (phase !== 'idle' || turn !== 'player' || result !== null || skillPacketRequestRef.current) return;

    setSelectedSkillFamily(familyCode);
    if (!resolveSkillPacket) {
      showBonusBanner(`Skill ${skill.familyCode} tạm khóa: chờ packet server để render đúng Java`);
      return;
    }

    skillPacketRequestRef.current = true;

    try {
      const packet = await resolveSkillPacket({
        familyCode,
        casterSide: 'player',
        board: boardRef.current,
        selectedCell: cursorCell,
        // Battle mode currently opens the full skill sandbox without the
        // character skill tree wired in, so request the reconstructed
        // max-level packet shape until real per-skill levels are available.
        skillLevel: DEFAULT_BATTLE_SKILL_LEVEL,
      });

      if (!mountedRef.current) return;
      if (!packet || packet.runtimeSource !== 'server_packet' || packet.familyCode !== familyCode) {
        showBonusBanner(`Skill ${skill.familyCode} chưa có packet hợp lệ từ server`);
        return;
      }

      setSkillPanelVisible(false);
      setMenuVisible(false);
      setSelected(null);
      setHintCell(null);
      setHintMove(null);
      setPhase('busy');
      phaseRef.current = 'busy';
      setPlayerAction('attack');
      setPlayerActionFrameIndex(0);

      const cast = buildActiveBattleSkillCastFromPacket(packet, {
        panelLeft,
        panelTop,
        charsTop,
        charsRowHeight,
        playerBaseLeft,
        monsterBaseLeft,
        playerSize,
        monsterSize,
        monsterGroundOffset,
      });

      setActiveSkillCasts(prev => [...prev, cast]);

      const boardMutationTimers =
        packet.boardMutation.kind === 'mark'
          ? packet.boardMutation.cells.map((cell, index) => setTimeout(() => {
            if (!mountedRef.current) return;
            applyServerPacketMarkCell(cell, packet.boardMutation.stateId ?? 10);
          }, cast.boardMutationDelayMs + index * 4 * JAVA_BATTLE_TICK_MS))
          : [setTimeout(() => {
            if (!mountedRef.current) return;
            applyServerPacketBoardMutation(packet);
          }, cast.boardMutationDelayMs)];

      const impactTimer = setTimeout(() => {
        if (!mountedRef.current) return;

        if (packet.impact.hitsActor) {
          playEnemySkillImpact(packet.impact.hitShakePx ?? skill.hitShakePx);
        }

        if ((packet.impact.damage ?? 0) > 0) {
          showDamagePopup('enemy', packet.impact.damage ?? 0);
          setEnemyHP(hp => {
            const next = Math.max(0, hp - (packet.impact.damage ?? 0));
            if (next === 0 && phaseRef.current !== 'over') {
              phaseRef.current = 'over';
              setPhase('over');
              setResult('victory');
            }
            return next;
          });
        }
      }, cast.impactDelayMs);

      const finishTimer = setTimeout(() => {
        if (!mountedRef.current) return;
        setActiveSkillCasts(prev => prev.filter(item => item.key !== cast.key));
        setPlayerAction('idle');
        setPlayerActionFrameIndex(null);
        if (phaseRef.current === 'over') return;
        if (
          packet.boardMutation.kind === 'mark' &&
          processMatchesRef.current
        ) {
          const markMatches = findMatchesFromAffected(boardRef.current, packet.boardMutation.cells);
          if (markMatches.size > 0) {
            processMatchesRef.current(boardRef.current, 0, packet.boardMutation.cells);
            return;
          }
        }
        if (packet.grantsExtraTurn) {
          flashExtraTurnsBadge(1);
          turnRef.current = 'player';
          setTurn('player');
          setTurnCycle(cycle => cycle + 1);
          phaseRef.current = 'idle';
          setPhase('idle');
          return;
        }
        if (phaseRef.current !== 'busy') return;
        turnRef.current = 'monster';
        setTurn('monster');
        setTurnCycle(cycle => cycle + 1);
        phaseRef.current = 'idle';
        setPhase('idle');
      }, cast.durationMs);

      skillCastTimersRef.current.push(...boardMutationTimers, impactTimer, finishTimer);
    } catch (error) {
      console.warn('[BattleScreen] resolveSkillPacket failed', error);
      if (mountedRef.current) {
        showBonusBanner(`Skill ${skill.familyCode} lỗi packet runtime`);
      }
    } finally {
      skillPacketRequestRef.current = false;
    }
  }, [
    applyServerPacketBoardMutation,
    charsRowHeight,
    charsTop,
    cursorCell,
    flashExtraTurnsBadge,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize,
    mountedRef,
    panelLeft,
    panelTop,
    phase,
    playEnemySkillImpact,
    playerBaseLeft,
    playerSize,
    result,
    resolveSkillPacket,
    showDamagePopup,
    showBonusBanner,
    turn,
  ]);

  // ── Skill ──────────────────────────────────────────────────────────────────
  const handleSkill = useCallback(() => {
    if (phase !== 'idle' || turn !== 'player' || result !== null) return;
    setHintCell(null);
    setHintMove(null);
    setSelectedSkillFamily(prev => {
      if (prev && isBattleSkillServerPacketReady(prev)) {
        return prev;
      }
      return getFirstBattleSkillServerPacketReadyFamily(battleElement)
        ?? (battleElement === 0 ? 1000 : battleElement === 1 ? 2000 : 4000);
    });
    setSkillPanelVisible(true);
    setMenuVisible(false);
  }, [battleElement, phase, result, turn]);

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
        fireSwordMarkTriggers={fireSwordMarkTriggers}
        turn={turn}
        matchFX={matchFX}
        showExtraTurnsBadge={showExtraTurnsBadge}
        extraTurnsBadgeValue={extraTurnsBadgeValue}
        showComboBadge={showComboBadge}
        comboMultiplier={comboMultiplier}
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
        comboBadgeAnim={comboBadgeAnim}
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
