import { useCallback, useEffect, useRef, useState, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import { TURN_TIME_LIMIT_SEC, type BattleCell, type BattlePhase, type BattleResult, type BattleTurn, type Board, type MoveSpec } from '../core';

interface UseBattleTurnTimerArgs {
  phase: BattlePhase;
  turn: BattleTurn;
  result: BattleResult | null;
  turnCycle: number;
  mountedRef: MutableRefObject<boolean>;
  phaseRef: MutableRefObject<BattlePhase>;
  turnRef: MutableRefObject<BattleTurn>;
  extraTurnsRef: MutableRefObject<number>;
  boardRef: MutableRefObject<Board>;
  selectedRef: MutableRefObject<BattleCell | null>;
  hintMoveRef: MutableRefObject<MoveSpec | null>;
  clearAiTimers: () => void;
  pickRandomValidMove: (board: Board) => MoveSpec | null;
  runAutoPlayerMove: (move: MoveSpec, onStarted?: () => void) => void;
  setAiStep: Dispatch<SetStateAction<'think' | 'pick1' | 'pick2' | null>>;
  setSelected: Dispatch<SetStateAction<BattleCell | null>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setTurnCycle: Dispatch<SetStateAction<number>>;
  showBonusBanner: (msg: string) => void;
}

export const useBattleTurnTimer = ({
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
}: UseBattleTurnTimerArgs) => {
  const [turnTimeLeft, setTurnTimeLeft] = useState(TURN_TIME_LIMIT_SEC);
  const playerHintShownRef = useRef(false);
  const turnTimerRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const turnDeadlineRef = useRef(0);
  const turnDeadlineKeyRef = useRef('');

  const clearTurnTimer = useCallback(() => {
    if (turnTimerRef.current !== null) {
      clearInterval(turnTimerRef.current);
      turnTimerRef.current = null;
    }
  }, []);

  const handleTurnTimeout = useCallback(() => {
    if (!mountedRef.current || phaseRef.current !== 'idle' || result !== null) return;

    clearTurnTimer();
    clearAiTimers();
    setAiStep(null);
    setSelected(null);
    setHintCell(null);

    if (turnRef.current === 'player') {
      const move = hintMoveRef.current ?? pickRandomValidMove(boardRef.current);
      if (move) {
        setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
        runAutoPlayerMove(move, () => {
          playerHintShownRef.current = true;
        });
        return;
      }
    }

    const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
    if (extraTurnsRef.current > 0) {
      const remaining = extraTurnsRef.current - 1;
      extraTurnsRef.current = remaining;
      setExtraTurns(remaining);
      setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      setTurnCycle(v => v + 1);
      return;
    }

    const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
    turnRef.current = nextTurn;
    setTurn(nextTurn);
    setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    showBonusBanner(`⏳ ${who} hết giờ, đổi lượt!`);
  }, [
    boardRef,
    clearAiTimers,
    clearTurnTimer,
    extraTurnsRef,
    hintMoveRef,
    mountedRef,
    phaseRef,
    pickRandomValidMove,
    result,
    runAutoPlayerMove,
    setAiStep,
    setExtraTurns,
    setHintCell,
    setSelected,
    setTurn,
    setTurnCycle,
    showBonusBanner,
    turnRef,
  ]);

  useEffect(() => {
    if (turn === 'player' && phase === 'idle' && result === null) {
      playerHintShownRef.current = false;
      setHintCell(null);
      setHintMove(null);
      return;
    }

    setHintCell(null);
    setHintMove(null);
  }, [turn, phase, result, turnCycle, setHintCell, setHintMove]);

  useEffect(() => {
    clearTurnTimer();

    if (phase !== 'idle' || result !== null) {
      if (result !== null) setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
      return;
    }

    const turnKey = `${turn}-${turnCycle}`;
    const now = Date.now();
    if (turnDeadlineKeyRef.current !== turnKey) {
      turnDeadlineKeyRef.current = turnKey;
      turnDeadlineRef.current = now + TURN_TIME_LIMIT_SEC * 1000;
      setTurnTimeLeft(TURN_TIME_LIMIT_SEC);
    } else {
      const remainingMs = turnDeadlineRef.current - now;
      const next = Math.max(0, Math.ceil(remainingMs / 1000));
      setTurnTimeLeft(next);
      if (remainingMs <= 0) {
        handleTurnTimeout();
        return;
      }
    }

    turnTimerRef.current = setInterval(() => {
      const remainingMs = turnDeadlineRef.current - Date.now();
      const next = Math.max(0, Math.ceil(remainingMs / 1000));
      if (mountedRef.current) setTurnTimeLeft(prev => (prev === next ? prev : next));

      if (
        turnRef.current === 'player' &&
        !playerHintShownRef.current &&
        selectedRef.current === null &&
        remainingMs <= 20000 &&
        remainingMs > 0
      ) {
        const move = pickRandomValidMove(boardRef.current);
        if (move && mountedRef.current) {
          const pickFirst = Math.random() > 0.5;
          setHintCell(pickFirst ? [move.r1, move.c1] : [move.r2, move.c2]);
          setHintMove(move);
          playerHintShownRef.current = true;
        }
      }

      if (remainingMs <= 0) handleTurnTimeout();
    }, 250);

    return clearTurnTimer;
  }, [
    boardRef,
    clearTurnTimer,
    handleTurnTimeout,
    mountedRef,
    phase,
    pickRandomValidMove,
    result,
    selectedRef,
    setHintCell,
    setHintMove,
    turn,
    turnCycle,
    turnRef,
  ]);

  return { turnTimeLeft };
};
