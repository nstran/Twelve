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
  BattleSkillCastOverlay,
  BattleSkillPanel,
  BattleResultOverlay,
} from './ui';
import {
  RESULT_ART_META,
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
  type BattleResult,
  type BattleResultRewardResponse,
  type BattleScreenProps,
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
  resolveEnemyMove,
  resolveSkillPacket,
  resolveEnemyTurn,
  resolveEnemyTurnPlan,
}) => {
  const initialBoard = useMemo<Board>(() => {
    const board = monsterBootstrap.initialBoard;
    if (
      Array.isArray(board) &&
      board.length === 8 &&
      board.every((row) => Array.isArray(row) && row.length === 8)
    ) {
      return board.map((row) => row.map((cell) => cell ?? null));
    }

    return makeBoard(createJavaBoardEngine());
  }, [monsterBootstrap.initialBoard]);
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

  const boardEngineRef = useRef(createJavaBoardEngine());
  const [board,         setBoard]         = useState<Board>(() => initialBoard);
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
  const playerRageReady = maxPow > 0 && power >= maxPow;
  const enemyRageReady = enemyMaxPow > 0 && enemyPower >= enemyMaxPow;
  const [phase,     setPhase]     = useState<BattlePhase>('idle');
  const [result,    setResult]    = useState<BattleResult | null>(null);
  const [battleReward, setBattleReward] = useState<BattleResultRewardResponse | null>(null);
  const [playerAction, setPlayerAction] = useState<CharacterAction>('idle');
  const [playerActionFrameIndex, setPlayerActionFrameIndex] = useState<number | null>(null);
  const aiLevel: AILevel | null = resolveEnemyTurnPlan ? null : 'linh_canh';
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
    resultClaimedRef.current = null;
  }, [
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
  useEffect(() => () => {
    skillCastTimersRef.current.forEach(clearTimeout);
    skillCastTimersRef.current = [];
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
    animateInvalidSwapBounce,
    resetBoardAnim,
  });

  const doDirectSwapRef = useRef(doDirectSwap);
  useEffect(() => { doDirectSwapRef.current = doDirectSwap; }, [doDirectSwap]);
  useEffect(() => { processMatchesRef.current = processMatches; }, [processMatches]);
  const { handleGemPress } = useBattlePlayerInput({
    doDirectSwapRef,
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
        reward={battleReward}
        onVictory={onVictory}
        onDefeat={onDefeat}
      />
    </View>
  );
};
