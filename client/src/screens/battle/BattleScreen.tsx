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
  MONSTER_HP,
  PLAYER_HUD_LAYOUT,
  type ActiveBattleSkillCast,
  type AILevel,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
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
  const [fireSwordMarkBaseGems, setFireSwordMarkBaseGems] = useState<Record<string, GemType>>({});
  const [fireSwordMarkTriggers, setFireSwordMarkTriggers] = useState<Record<string, number>>({});
  const [playerHP,  setPlayerHP]  = useState(maxHP);
  const [enemyHP,   setEnemyHP]   = useState(maxEHP);
  const [mana,      setMana]      = useState(30);
  const [power,     setPower]     = useState(40);
  const [phase,     setPhase]     = useState<BattlePhase>('idle');
  const [result,    setResult]    = useState<BattleResult | null>(null);
  const [playerAction, setPlayerAction] = useState<CharacterAction>('idle');
  const [playerActionFrameIndex, setPlayerActionFrameIndex] = useState<number | null>(null);
  const [aiLevel]  = useState<AILevel | null>('linh_canh');
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
    playPlayerHitReaction,
    playerDefeatPose,
    playerHPBarAnim,
    playerReactionPose,
    playerRetreatPose,
    powerBlinkAnim,
    resultArtAnim,
    setPlayerRetreatPose,
    startPlayerDefeatSequence,
  } = useBattleActorHudState({
    enemyHP,
    maxEHP,
    maxHP,
    maxPow,
    mountedRef,
    playerHP,
    power,
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
  const { monFrame, playMonsterSwordAttack, playPlayerSwordAttack } = useBattleSwordAttacks({
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
    pendingVictoryRef,
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

  const { handleSkillCast } = useBattleSkillCasting({
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    boardRef,
    charsRowHeight,
    charsTop,
    cursorCell,
    fireSwordMarkBaseGems,
    flashExtraTurnsBadge,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize,
    mountedRef,
    panelLeft,
    panelTop,
    phase,
    phaseRef,
    pendingVictoryRef,
    playEnemySkillImpact,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    resolveSkillPacket,
    result,
    setActiveSkillCasts,
    setEnemyHP,
    setHintCell,
    setHintMove,
    setMenuVisible,
    setPhase,
    setPlayerAction,
    setPlayerActionFrameIndex,
    setPlayerRetreatPose,
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
