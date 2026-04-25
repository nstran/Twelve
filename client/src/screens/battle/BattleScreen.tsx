import React, { useState, useEffect, useCallback, useMemo, useRef } from 'react';
import {
  View,
  Animated, Easing,
} from 'react-native';
import type { CharacterAction } from '../../engine/character';
import { SoftkeyBar } from '../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu } from '../../components/controls/PopupMenu/PopupMenu';
import {
  BattlePanel,
  BattleActorsRow,
  BattleEffects,
  BattleResultSplash,
  BattleSkillCastOverlay,
  BattleSkillPanel,
  BattleResultOverlay,
} from './ui';
import {
  RESULT_POPUP_DELAY_AFTER_SPLASH_MS,
  RESULT_ART_META,
  RESULT_SPLASH_HOLD_MS,
  ENEMY_HUD_LAYOUT,
  createJavaBoardEngine,
  getBattleActorLayout,
  getBattleElement,
  getBattleStageLayout,
  makeBoard,
  PLAYER_HUD_LAYOUT,
  type ActiveBattleSkillCast,
  type AILevel,
  type BattleCell,
  type BattlePhase,
  type BattlePvpActionEvent,
  type BattleResult,
  type BattleResultRewardResponse,
  type BattleScreenProps,
  type BattleSide,
  type BattleTurn,
  type Board,
  type GemType,
  type MoveSpec,
  s,
  BG_W,
} from './core';
import {
  useBattleAI,
  useBattleActorHudState,
  useBattleBoardBadges,
  useBattleBoardAnimations,
  useBattleEffects,
  useBattleMatchFlow,
  useBattleMenuControls,
  useBattleMonsterTurn,
  useBattlePlayerInput,
  useBattleSkillBoardMutation,
  useBattleSkillCasting,
  useBattleSwordAttacks,
  useBattleTurnTimer,
} from './hooks';
const ASSET_SOFTKEY_MENU = require('../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');
const JAVA_DEFAULT_CURSOR_CELL: BattleCell = [3, 4];

const deriveBattleSeed = (sessionId: string): number => {
  let hash = 0;
  for (let i = 0; i < sessionId.length; i++) {
    hash = ((hash << 5) - hash) + sessionId.charCodeAt(i);
    hash |= 0;
  }

  return Math.abs(hash);
};

export const BattleScreen: React.FC<BattleScreenProps> = ({
  monsterType,
  monsterBootstrap,
  appearance,
  initialTurn = 'player',
  onVictory,
  onDefeat,
  onBattleResult,
  onFlee,
  resolveBattleResult,
  resolveBattleSessionSync,
  resolveBattleSessionSnapshot,
  resolveBattlePvpAction,
  resolveEnemyMove,
  resolveSkillPacket,
  resolveEnemyTurn,
  resolveEnemyTurnPlan,
}) => {
  const battleSeed = useMemo(() => deriveBattleSeed(monsterBootstrap.sessionId), [monsterBootstrap.sessionId]);

  const initialBoard = useMemo<Board>(() => {
    const board = monsterBootstrap.initialBoard;
    if (
      Array.isArray(board) &&
      board.length === 8 &&
      board.every((row) => Array.isArray(row) && row.length === 8)
    ) {
      return board.map((row) => row.map((cell) => cell ?? null));
    }

    return makeBoard(createJavaBoardEngine(battleSeed));
  }, [battleSeed, monsterBootstrap.initialBoard]);
  const playerBootstrap = monsterBootstrap.player;
  const maxHP  = Math.max(1, playerBootstrap.maxHp);
  const maxEHP = Math.max(1, monsterBootstrap.enemy.maxHp);
  const maxMP  = Math.max(1, playerBootstrap.maxMp);
  const maxPow = Math.max(1, playerBootstrap.maxPower);
  const enemyMaxMP = Math.max(1, monsterBootstrap.enemy.maxMp);
  const enemyMaxPow = Math.max(1, monsterBootstrap.enemy.maxPower);
  const playerResourceProfile = useMemo(
    () => ({
      strength: playerBootstrap.strength,
      magic: playerBootstrap.magic,
    }),
    [playerBootstrap.magic, playerBootstrap.strength],
  );
  const enemyResourceProfile = useMemo(
    () => ({
      strength: monsterBootstrap.enemy.strength,
      magic: monsterBootstrap.enemy.magic,
    }),
    [monsterBootstrap.enemy.magic, monsterBootstrap.enemy.strength],
  );
  const isPvpBattle = monsterBootstrap.battleKind === 'pvp';

  const boardEngineRef = useRef(createJavaBoardEngine(battleSeed));
  const [board,         setBoard]         = useState<Board>(() => initialBoard);
  const doDirectSwapRef = useRef<(r1: number, c1: number, r2: number, c2: number, isPassiveObserver?: boolean) => void>(() => {});
  const [cursorCell,    setCursorCell]    = useState<BattleCell>(JAVA_DEFAULT_CURSOR_CELL);
  const [selected,      setSelected]      = useState<BattleCell | null>(null);
  const [hintCell,      setHintCell]      = useState<BattleCell | null>(null);
  const [hintMove,      setHintMove]      = useState<MoveSpec | null>(null);
  const [explodeFrames, setExplodeFrames] = useState<Record<string, number>>({});
  const [fireSwordMarkBaseGems, setFireSwordMarkBaseGems] = useState<Record<string, GemType>>({});
  const [fireSwordMarkTriggers, setFireSwordMarkTriggers] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(() => Math.min(playerBootstrap.currentHp, maxHP));
  const [enemyHP,   setEnemyHP]   = useState(() => Math.min(monsterBootstrap.enemy.currentHp, maxEHP));
  const [mana,      setMana]      = useState(() => Math.min(playerBootstrap.currentMp, maxMP));
  const [power,     setPower]     = useState(() => Math.min(playerBootstrap.currentPower, maxPow));
  const [enemyMana, setEnemyMana] = useState(() => Math.min(monsterBootstrap.enemy.currentMp, enemyMaxMP));
  const [enemyPower, setEnemyPower] = useState(() => Math.min(monsterBootstrap.enemy.currentPower, enemyMaxPow));
  const [pvpTurnSeq, setPvpTurnSeq] = useState(0);
  const playerRageReady = maxPow > 0 && power >= maxPow;
  const enemyRageReady = enemyMaxPow > 0 && enemyPower >= enemyMaxPow;
  const [phase,     setPhase]     = useState<BattlePhase>('idle');
  const [result,    setResult]    = useState<BattleResult | null>(null);
  const [battleReward, setBattleReward] = useState<BattleResultRewardResponse | null>(null);
  const [resultSplashVisible, setResultSplashVisible] = useState(false);
  const [resultPopupVisible, setResultPopupVisible] = useState(false);
  const [playerAction, setPlayerAction] = useState<CharacterAction>('idle');
  const [playerActionFrameIndex, setPlayerActionFrameIndex] = useState<number | null>(null);
  const aiLevel: AILevel | null = isPvpBattle || resolveEnemyTurnPlan ? null : 'linh_canh';
  const [activeSkillCasts, setActiveSkillCasts] = useState<ActiveBattleSkillCast[]>([]);
  const battleElement = getBattleElement(appearance.elementIndex);

  // ── Turn-based system ──────────────────────────────────────────────────────
  const [turn, setTurn] = useState<BattleTurn>(initialTurn);
  const turnRef = useRef<BattleTurn>(initialTurn);

  // ── Extra turns: match 4+ → bonus lượt ────────────────────────────────────
  const [extraTurns, setExtraTurns] = useState(0);
  const extraTurnsRef = useRef(0);
  const [turnCycle, setTurnCycle] = useState(0);

  const playerAttackTranslateX = useRef(new Animated.Value(0)).current;
  const playerHitTranslateX = useRef(new Animated.Value(0)).current;
  const enemyAttackTranslateX = useRef(new Animated.Value(0)).current;
  const enemyHitTranslateX = useRef(new Animated.Value(0)).current;
  const skillCastTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const skillPacketRequestRef = useRef(false);
  const pendingVictoryRef = useRef(false);
  const resultClaimedRef = useRef<string | null>(null);
  const resultRevealTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);
  const phaseRef   = useRef<BattlePhase>('idle');
  const mountedRef = useRef(true);
  const boardRef   = useRef<Board>(board);
  const enemyHPRef = useRef(enemyHP);
  const playerPowerRef = useRef(power);
  const enemyPowerRef = useRef(enemyPower);
  const playerHPRef= useRef(playerHP);
  const selectedRef = useRef<BattleCell | null>(selected);
  const hintMoveRef = useRef<MoveSpec | null>(hintMove);

  useEffect(() => { phaseRef.current = phase; }, [phase]);
  useEffect(() => () => { mountedRef.current = false; }, []);
  useEffect(() => { boardRef.current = board; }, [board]);
  useEffect(() => {
    boardEngineRef.current = createJavaBoardEngine(battleSeed);
    boardRef.current = initialBoard;
    setBoard(initialBoard);
    setExplodeFrames({});
    setSelected(null);
    setHintCell(null);
    setHintMove(null);
    setPlayerHP(Math.min(playerBootstrap.currentHp, maxHP));
    setMana(Math.min(playerBootstrap.currentMp, maxMP));
    setPower(Math.min(playerBootstrap.currentPower, maxPow));
    setEnemyHP(Math.min(monsterBootstrap.enemy.currentHp, maxEHP));
    setEnemyMana(Math.min(monsterBootstrap.enemy.currentMp, enemyMaxMP));
    setEnemyPower(Math.min(monsterBootstrap.enemy.currentPower, enemyMaxPow));
    setResult(null);
    setBattleReward(null);
    setResultSplashVisible(false);
    setResultPopupVisible(false);
    resultRevealTimersRef.current.forEach(clearTimeout);
    resultRevealTimersRef.current = [];
    resultClaimedRef.current = null;
    setPvpTurnSeq(0);
  }, [
    battleSeed,
    enemyMaxMP,
    enemyMaxPow,
    initialBoard,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    monsterBootstrap.enemy.currentHp,
    monsterBootstrap.enemy.currentMp,
    monsterBootstrap.enemy.currentPower,
    playerBootstrap.currentHp,
    playerBootstrap.currentMp,
    playerBootstrap.currentPower,
  ]);
  useEffect(() => {
    setExplodeFrames({});
  }, [board]);
  useEffect(() => {
    if (!resolveBattleSessionSync) {
      return;
    }

    if (phase !== 'idle' && phase !== 'over') {
      return;
    }

    void Promise.resolve(resolveBattleSessionSync({
      sessionId: monsterBootstrap.sessionId,
      board,
      activeTurn: turn === 'monster' ? 'enemy' : 'player',
      playerCurrentHp: playerHP,
      playerCurrentMp: mana,
      playerCurrentPower: power,
      enemyCurrentHp: enemyHP,
      enemyCurrentMp: enemyMana,
      enemyCurrentPower: enemyPower,
    }));
  }, [
    board,
    enemyHP,
    enemyMana,
    enemyPower,
    mana,
    monsterBootstrap.sessionId,
    phase,
    playerHP,
    power,
    resolveBattleSessionSync,
    result,
    turn,
    turnCycle,
  ]);
  const pvpTurnSeqRef = useRef(0);
  useEffect(() => { pvpTurnSeqRef.current = pvpTurnSeq; }, [pvpTurnSeq]);
  const applySessionSnapshot = useCallback((snapshot: {
    board: Board;
    activeTurn: BattleSide;
    playerCurrentHp: number;
    playerCurrentMp: number;
    playerCurrentPower: number;
    enemyCurrentHp: number;
    enemyCurrentMp: number;
    enemyCurrentPower: number;
    turnSeq?: number;
    lastPvpAction?: BattlePvpActionEvent | null;
  }, force = false) => {
    // Skip stale/same snapshots in PvP polling to avoid overwriting locally-resolved
    // board with an older server state (pre-cascade). Rejected PvP actions can force
    // a resync because server rejection is authoritative even if turnSeq is unchanged.
    if (!force && typeof snapshot.turnSeq === 'number' && snapshot.turnSeq <= pvpTurnSeqRef.current) {
      return;
    }

    const isImmediateSwap = snapshot.lastPvpAction?.action === 'swap' && 
                            snapshot.lastPvpAction.move &&
                            snapshot.lastPvpAction.turnSeq === snapshot.turnSeq;

    if (isImmediateSwap && snapshot.lastPvpAction?.move) {
      const { fromRow, fromCol, toRow, toCol } = snapshot.lastPvpAction.move;
      // Do not snap the board! Animate the swap and subsequent cascades
      // smoothly as a passive observer so we see exactly what the active player saw.
      doDirectSwapRef.current(fromRow, fromCol, toRow, toCol, true);
    } else {
      boardRef.current = snapshot.board;
      setBoard(snapshot.board);
      setExplodeFrames({});
    }
    
    setFireSwordMarkBaseGems({});
    setFireSwordMarkTriggers({});
    setSelected(null);
    setHintCell(null);
    setHintMove(null);

    setPlayerHP(Math.max(0, Math.min(maxHP, snapshot.playerCurrentHp)));
    setMana(Math.max(0, Math.min(maxMP, snapshot.playerCurrentMp)));
    setPower(Math.max(0, Math.min(maxPow, snapshot.playerCurrentPower)));
    setEnemyHP(Math.max(0, Math.min(maxEHP, snapshot.enemyCurrentHp)));
    setEnemyMana(Math.max(0, Math.min(enemyMaxMP, snapshot.enemyCurrentMp)));
    setEnemyPower(Math.max(0, Math.min(enemyMaxPow, snapshot.enemyCurrentPower)));
    if (typeof snapshot.turnSeq === 'number') {
      setPvpTurnSeq(snapshot.turnSeq);
    }

    const nextTurn = snapshot.activeTurn === 'enemy' ? 'monster' : 'player';
    if (nextTurn !== turnRef.current) {
      turnRef.current = nextTurn;
      setTurn(nextTurn);
      setTurnCycle(cycle => cycle + 1);
    }
  }, [enemyMaxMP, enemyMaxPow, maxEHP, maxHP, maxMP, maxPow, turnRef]);
  useEffect(() => {
    if (!resolveBattleSessionSnapshot || phase !== 'idle' || result !== null) {
      return;
    }

    // Only poll during opponent's turn — the active player's board is
    // authoritative so polling would overwrite locally-resolved cascade state.
    if (turn !== 'monster') {
      return;
    }

    let cancelled = false;
    const poll = () => {
      void Promise.resolve(resolveBattleSessionSnapshot({ sessionId: monsterBootstrap.sessionId }))
        .then((snapshot) => {
          if (!snapshot || cancelled || !mountedRef.current || phaseRef.current !== 'idle') {
            return;
          }

          applySessionSnapshot(snapshot);
        });
    };

    poll();
    // Tăng tốc độ polling từ 850ms -> 150ms để PVP cảm giác gần realtime hơn (chưa có WebSocket)
    const timer = setInterval(poll, 150);
    return () => {
      cancelled = true;
      clearInterval(timer);
    };
  }, [
    applySessionSnapshot,
    isPvpBattle,
    monsterBootstrap.sessionId,
    phase,
    resolveBattleSessionSnapshot,
    result,
    turn,
    turnRef,
  ]);
  useEffect(() => { enemyHPRef.current = enemyHP; }, [enemyHP]);
  useEffect(() => { playerPowerRef.current = power; }, [power]);
  useEffect(() => { enemyPowerRef.current = enemyPower; }, [enemyPower]);
  useEffect(() => { playerHPRef.current = playerHP; }, [playerHP]);
  useEffect(() => { selectedRef.current = selected; }, [selected]);
  useEffect(() => { hintMoveRef.current = hintMove; }, [hintMove]);
  useEffect(() => { turnRef.current = turn; }, [turn]);
  useEffect(() => { extraTurnsRef.current = extraTurns; }, [extraTurns]);
  useEffect(() => {
    if (result === null || resultClaimedRef.current === monsterBootstrap.sessionId) {
      return;
    }

    resultClaimedRef.current = monsterBootstrap.sessionId;
    const fallback: BattleResultRewardResponse = {
      result,
      levelBefore: appearance.level ?? monsterBootstrap.player.level,
      levelAfter: appearance.level ?? monsterBootstrap.player.level,
      levelUps: 0,
      currentHp: playerHP,
      maxHp: maxHP,
      expBefore: 0,
      expAfter: 0,
      expFloor: 0,
      expCeiling: 100,
      expGained: 0,
      quanBefore: appearance.walletQuan ?? 0,
      quanAfter: appearance.walletQuan ?? 0,
      quanGained: 0,
    };

    if (!resolveBattleResult) {
      setBattleReward(fallback);
      onBattleResult?.(fallback);
      return;
    }

    void Promise.resolve(resolveBattleResult({
      sessionId: monsterBootstrap.sessionId,
      result,
      playerCurrentHp: playerHP,
      playerCurrentMp: mana,
      playerCurrentPower: power,
    }))
      .then((response) => {
        const resolved = response ?? fallback;
        setBattleReward(resolved);
        onBattleResult?.(resolved);
      })
      .catch(() => {
        setBattleReward(fallback);
        onBattleResult?.(fallback);
      });
  }, [
    appearance.level,
    appearance.walletQuan,
    mana,
    maxHP,
    monsterBootstrap.player.level,
    monsterBootstrap.sessionId,
    onBattleResult,
    playerHP,
    power,
    resolveBattleResult,
    result,
  ]);
  useEffect(() => {
    resultRevealTimersRef.current.forEach(clearTimeout);
    resultRevealTimersRef.current = [];

    if (result === null) {
      setResultSplashVisible(false);
      setResultPopupVisible(false);
      return;
    }

    setResultSplashVisible(true);
    setResultPopupVisible(false);

    const hideSplashTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setResultSplashVisible(false);
    }, RESULT_SPLASH_HOLD_MS);
    const showPopupTimer = setTimeout(() => {
      if (!mountedRef.current) return;
      setResultPopupVisible(true);
    }, RESULT_SPLASH_HOLD_MS + RESULT_POPUP_DELAY_AFTER_SPLASH_MS);

    resultRevealTimersRef.current = [hideSplashTimer, showPopupTimer];

    return () => {
      resultRevealTimersRef.current.forEach(clearTimeout);
      resultRevealTimersRef.current = [];
    };
  }, [result]);
  useEffect(() => () => {
    skillCastTimersRef.current.forEach(clearTimeout);
    skillCastTimersRef.current = [];
    resultRevealTimersRef.current.forEach(clearTimeout);
    resultRevealTimersRef.current = [];
  }, []);
  const {
    comboBadgeAnim,
    comboMultiplier,
    extraTurnsBadgeAnim,
    extraTurnsBadgeValue,
    flashComboBadge,
    flashExtraTurnsBadge,
    showComboBadge,
    showExtraTurnsBadge,
  } = useBattleBoardBadges({ mountedRef });
  const {
    enemyHPBarAnim,
    enemyPowerBlinkAnim,
    playPlayerHitReaction,
    playerDefeatPose,
    playerHPBarAnim,
    playerReactionPose,
    playerRetreatPose,
    powerBlinkAnim,
    rageAuraPulseAnim,
    resultArtAnim,
    setPlayerRetreatPose,
    startPlayerDefeatSequence,
  } = useBattleActorHudState({
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
  });
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
  const enemyAssetCatalogId = monsterBootstrap.enemy.appearance.assetCatalogId;
  const enemyPlayerAppearance = monsterBootstrap.enemyPlayerAppearance ?? null;
  const { panelLeft, panelTop, charsTop, damagePopupTop, charsRowHeight, monsterSize } =
    getBattleStageLayout(monsterType, enemyAssetCatalogId);
  const {
    attackTravelX,
    playerBaseLeft,
    monsterBaseLeft,
    playerSize,
    monsterGroundOffset,
  } = useMemo(
    () => getBattleActorLayout(monsterType, appearance, enemyAssetCatalogId),
    [appearance, enemyAssetCatalogId, monsterType],
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
    animateValidSwap,
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
  const { applyServerPacketBoardMutation, applyServerPacketMarkCell } = useBattleSkillBoardMutation({
    animateFall,
    boardEngineRef,
    boardRef,
    mountedRef,
    playExplosion,
    processMatchesRef,
    setBoard,
    setFireSwordMarkBaseGems,
    setFireSwordMarkTriggers,
  });

  const showBonusBanner = useCallback((msg: string) => {
    showGainPopup('player', msg);
  }, [showGainPopup]);
  const {
    battleMenuItems,
    handleLeftSoftkey,
    handleRightSoftkey,
    menuSelectedIndex,
    menuVisible,
    selectedSkillFamily,
    setMenuSelectedIndex,
    setMenuVisible,
    setSelectedSkillFamily,
    setSkillPanelVisible,
    skillPanelVisible,
  } = useBattleMenuControls({
    battleElement,
    onFlee,
    phase,
    result,
    setHintCell,
    setHintMove,
    showBonusBanner,
    turn,
  });
  const {
    monsterDefeatOpacity,
    monsterDefeatScale,
    monsterDefeatTranslateY,
    monsterPoseKey,
    playMonsterDefeatSequence,
    playMonsterSkillCast,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
  } = useBattleSwordAttacks({
    attackTravelX,
    mountedRef,
    enemyAttackTranslateX,
    enemyHitTranslateX,
    playerAttackTranslateX,
    playerHitTranslateX,
    setPlayerAction,
    setPlayerActionFrameIndex,
    setPlayerRetreatPose,
  });
  const { doDirectSwap, processMatches } = useBattleMatchFlow({
    mountedRef,
    phaseRef,
    turnRef,
    extraTurnsRef,
    enemyHPRef,
    playerPowerRef,
    enemyPowerRef,
    pendingVictoryRef,
    boardRef,
    boardEngineRef,
    maxHP,
    maxEHP,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    playerResourceProfile,
    enemyResourceProfile,
    setBoard,
    setPhase,
    setTurn,
    setExtraTurns,
    setTurnCycle,
    setEnemyHP,
    setEnemyMana,
    setEnemyPower,
    setPlayerHP,
    setMana,
    setPower,
    setResult,
    playMonsterDefeatSequence,
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
    animateValidSwap,
    animateInvalidSwapBounce,
    resetBoardAnim,
  });

  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);
  const submitPlayerSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    if (!isPvpBattle || !resolveBattlePvpAction) {
      doDirectSwapRef.current(r1, c1, r2, c2);
      return;
    }

    void Promise.resolve(resolveBattlePvpAction({
      sessionId: monsterBootstrap.sessionId,
      turnSeq: pvpTurnSeq,
      action: 'swap',
      fromRow: r1,
      fromCol: c1,
      toRow: r2,
      toCol: c2,
    })).then((response) => {
      if (!response) {
        return;
      }

      if (!response.lastAction.accepted) {
        applySessionSnapshot(response, true);
        return;
      }

      // PvP action endpoint is the server-side turn/validation gate only.
      // Board cascade, damage, resource gain and final bars are still resolved by the
      // reconstructed Java-like local battle flow, then persisted through /battle/session-sync.
      // If we apply the pvp-action snapshot directly here, the board is only swapped once and
      // match resolution never runs, causing PvP turns to look stuck/desynced.
      setPvpTurnSeq(response.turnSeq);
      doDirectSwapRef.current(r1, c1, r2, c2);
    });
  }, [applySessionSnapshot, isPvpBattle, monsterBootstrap.sessionId, pvpTurnSeq, resolveBattlePvpAction]);
  const submitPlayerSwapRef = useRef(submitPlayerSwap);
  useEffect(() => { submitPlayerSwapRef.current = submitPlayerSwap; }, [submitPlayerSwap]);
  useEffect(() => { processMatchesRef.current = processMatches; }, [processMatches]);
  const { handleGemPress } = useBattlePlayerInput({
    doDirectSwapRef: submitPlayerSwapRef,
    phase,
    selected,
    setCursorCell,
    setHintCell,
    setHintMove,
    setSelected,
    turn,
  });

  const {
    clearAiTimers,
    pickRandomValidMove,
    runAutoPlayerMove,
    setAiStep,
  } = useBattleAI({
    phase,
    turn,
    aiLevel,
    isPvpBattle,
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
    enemyBattleSessionId: monsterBootstrap.sessionId,
    resolveEnemyMove,
    setCursorCell,
    setSelected,
    setHintCell,
    setHintMove,
    setTurn,
  });
  useBattleMonsterTurn({
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    boardRef,
    charsRowHeight,
    charsTop,
    doDirectSwapRef,
    extraTurnsRef,
    flashExtraTurnsBadge,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize,
    mountedRef,
    panelLeft,
    panelTop,
    phase,
    phaseRef,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    resolveEnemyTurnPlan,
    result,
    sessionId: monsterBootstrap.sessionId,
    setActiveSkillCasts,
    setAiStep,
    setCursorCell,
    setEnemyHP,
    setEnemyMana,
    setEnemyPower,
    setExtraTurns,
    setHintCell,
    setHintMove,
    setMana,
    setPhase,
    setPlayerHP,
    setPower,
    setSelected,
    setTurn,
    setTurnCycle,
    showBonusBanner,
    showDamagePopup,
    skillCastTimersRef,
    startPlayerDefeatSequence,
    playMonsterSkillCast,
    playPlayerHitReaction,
    turn,
    turnRef,
    isPvpBattle,
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

  const { handleSkillCast } = useBattleSkillCasting({
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    battleSessionId: monsterBootstrap.sessionId,
    boardRef,
    charsRowHeight,
    charsTop,
    cursorCell,
    extraTurnsRef,
    flashExtraTurnsBadge,
    maxHP,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize,
    mountedRef,
    panelLeft,
    panelTop,
    phase,
    phaseRef,
    pendingVictoryRef,
    playMonsterDefeatSequence,
    playEnemySkillImpact,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    resolveSkillPacket,
    result,
    setActiveSkillCasts,
    setEnemyHP,
    setEnemyMana,
    setEnemyPower,
    setExtraTurns,
    setHintCell,
    setHintMove,
    setMana,
    setMenuVisible,
    setPhase,
    setPlayerAction,
    setPlayerActionFrameIndex,
    setPlayerHP,
    setPlayerRetreatPose,
    setPower,
    setResult,
    setSelected,
    setSelectedSkillFamily,
    setSkillPanelVisible,
    setTurn,
    setTurnCycle,
    showBonusBanner,
    showDamagePopup,
    skillCastTimersRef,
    skillPacketRequestRef,
    turn,
    turnRef,
  });

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
        fireSwordMarkBaseGems={fireSwordMarkBaseGems}
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
        enemyMana={enemyMana}
        enemyPower={enemyPower}
        maxHP={maxHP}
        maxEHP={maxEHP}
        maxMP={maxMP}
        maxPow={maxPow}
        enemyMaxMP={enemyMaxMP}
        enemyMaxPow={enemyMaxPow}
        offsets={offsets}
        swapOffsetsX={swapOffsetsX}
        swapOffsetsY={swapOffsetsY}
        extraTurnsBadgeAnim={extraTurnsBadgeAnim}
        comboBadgeAnim={comboBadgeAnim}
        playerHPBarAnim={playerHPBarAnim}
        enemyHPBarAnim={enemyHPBarAnim}
        powerBlinkAnim={powerBlinkAnim}
        enemyPowerBlinkAnim={enemyPowerBlinkAnim}
        playerRageReady={playerRageReady}
        enemyRageReady={enemyRageReady}
        onGemPress={handleGemPress}
      />

      <BattleActorsRow
        panelLeft={panelLeft}
        charsTop={charsTop}
        charsHeight={charsRowHeight}
        appearance={appearance}
        enemyAppearance={enemyPlayerAppearance}
        monsterAssetCatalogId={enemyAssetCatalogId}
        monsterType={monsterType}
        monsterDefeatOpacity={monsterDefeatOpacity}
        monsterDefeatScale={monsterDefeatScale}
        monsterDefeatTranslateY={monsterDefeatTranslateY}
        monsterPoseKey={monsterPoseKey}
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
        playerRageReady={playerRageReady}
        enemyRageReady={enemyRageReady}
        rageAuraPulseAnim={rageAuraPulseAnim}
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

      <BattleResultSplash
        result={result}
        resultMeta={resultMeta}
        visible={resultSplashVisible}
        panelTop={panelTop}
        resultArtAnim={resultArtAnim}
        resultArtLift={resultArtLift}
        resultArtScale={resultArtScale}
        resultArtTilt={resultArtTilt}
      />

      <BattleSkillPanel
        visible={skillPanelVisible}
        elementIndex={appearance.elementIndex}
        availableFamilyCodes={monsterBootstrap.player.skills.map((skill) => skill.skillId)}
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
        visible={resultPopupVisible}
        panelTop={panelTop}
        reward={battleReward}
        onVictory={onVictory}
        onDefeat={onDefeat}
      />
    </View>
  );
};
