import { useCallback, useEffect, useRef, useState, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import { AI_CONFIGS, getAllValidMoves, pickAIMove, type AILevel, type BattleCell, type BattlePhase, type BattleResult, type BattleScreenProps, type BattleTurn, type Board, type JavaBoardEngine, type MoveSpec } from '../core';

interface UseBattleAIArgs {
  phase: BattlePhase;
  turn: BattleTurn;
  aiLevel: AILevel | null;
  result: BattleResult | null;
  turnCycle: number;
  mountedRef: MutableRefObject<boolean>;
  phaseRef: MutableRefObject<BattlePhase>;
  turnRef: MutableRefObject<BattleTurn>;
  boardRef: MutableRefObject<Board>;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  playerHPRef: MutableRefObject<number>;
  enemyHPRef: MutableRefObject<number>;
  doDirectSwapRef: MutableRefObject<(r1: number, c1: number, r2: number, c2: number) => void>;
  enemyBattleSessionId?: string | null;
  resolveEnemyMove?: BattleScreenProps['resolveEnemyMove'];
  setCursorCell: Dispatch<SetStateAction<BattleCell>>;
  setSelected: Dispatch<SetStateAction<BattleCell | null>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
}

export const useBattleAI = ({
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
  enemyBattleSessionId,
  resolveEnemyMove,
  setCursorCell,
  setSelected,
  setHintCell,
  setHintMove,
  setTurn,
}: UseBattleAIArgs) => {
  const [aiStep, setAiStep] = useState<'think' | 'pick1' | 'pick2' | null>(null);
  const aiTimers = useRef<ReturnType<typeof setTimeout>[]>([]);
  const moveRequestVersionRef = useRef(0);

  const clearAiTimers = useCallback(() => {
    aiTimers.current.forEach(clearTimeout);
    aiTimers.current = [];
  }, []);

  useEffect(() => () => { clearAiTimers(); }, [clearAiTimers]);

  const pickRandomValidMove = useCallback((board: Board): MoveSpec | null => {
    const valid = getAllValidMoves(board);
    if (valid.length === 0) return null;
    return valid[Math.floor(Math.random() * valid.length)] ?? null;
  }, []);

  const runAutoPlayerMove = useCallback((move: MoveSpec, onStarted?: () => void) => {
    clearAiTimers();
    onStarted?.();
    setHintCell(null);
    setHintMove(null);
    setAiStep(null);
    setCursorCell([move.r1, move.c1]);
    setSelected([move.r1, move.c1]);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'player') return;
      setCursorCell([move.r2, move.c2]);
      setSelected([move.r2, move.c2]);

      const t2 = setTimeout(() => {
        if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'player') return;
        setSelected(null);
        doDirectSwapRef.current(move.r1, move.c1, move.r2, move.c2);
      }, 350);
      aiTimers.current.push(t2);
    }, 350);
    aiTimers.current.push(t1);
  }, [clearAiTimers, doDirectSwapRef, mountedRef, phaseRef, setCursorCell, setHintCell, setHintMove, setSelected, turnRef]);

  useEffect(() => {
    if (phase !== 'idle' || turn !== 'monster' || aiLevel === null || result !== null) return;
    const cfg = AI_CONFIGS[aiLevel];

    clearAiTimers();
    setHintCell(null);
    setHintMove(null);
    setAiStep('think');
    setSelected(null);

    const t1 = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') return;
      const playbackMove = (move: MoveSpec | null) => {
        if (!move) {
          setAiStep(null);
          turnRef.current = 'player';
          setTurn('player');
          return;
        }

        setAiStep('pick1');
        setCursorCell([move.r1, move.c1]);
        setSelected([move.r1, move.c1]);

        const t2 = setTimeout(() => {
          if (!mountedRef.current) return;
          setAiStep('pick2');
          setCursorCell([move.r2, move.c2]);
          setSelected([move.r2, move.c2]);

          const t3 = setTimeout(() => {
            if (!mountedRef.current) return;
            setAiStep(null);
            setSelected(null);
            doDirectSwapRef.current(move.r1, move.c1, move.r2, move.c2);
          }, 350);
          aiTimers.current.push(t3);
        }, 400);
        aiTimers.current.push(t2);
      };

      if (resolveEnemyMove && enemyBattleSessionId) {
        const requestVersion = moveRequestVersionRef.current + 1;
        moveRequestVersionRef.current = requestVersion;

        void Promise.resolve(resolveEnemyMove({
          sessionId: enemyBattleSessionId,
          board: boardRef.current,
        }))
          .then((response) => {
            if (
              !mountedRef.current ||
              requestVersion !== moveRequestVersionRef.current ||
              phaseRef.current !== 'idle' ||
              turnRef.current !== 'monster'
            ) {
              return;
            }

            const move = response
              ? {
                r1: response.move.fromRow,
                c1: response.move.fromCol,
                r2: response.move.toRow,
                c2: response.move.toCol,
              }
              : pickAIMove(boardRef.current, boardEngineRef.current, aiLevel, playerHPRef.current, enemyHPRef.current);

            playbackMove(move);
          })
          .catch(() => {
            if (!mountedRef.current || requestVersion !== moveRequestVersionRef.current) {
              return;
            }

            const fallbackMove = pickAIMove(
              boardRef.current,
              boardEngineRef.current,
              aiLevel,
              playerHPRef.current,
              enemyHPRef.current,
            );
            playbackMove(fallbackMove);
          });
        return;
      }

      const move = pickAIMove(boardRef.current, boardEngineRef.current, aiLevel, playerHPRef.current, enemyHPRef.current);
      playbackMove(move);
    }, cfg.thinkMs);
    aiTimers.current.push(t1);

    return clearAiTimers;
  }, [
    aiLevel,
    boardRef,
    boardEngineRef,
    clearAiTimers,
    doDirectSwapRef,
    enemyBattleSessionId,
    enemyHPRef,
    mountedRef,
    phase,
    phaseRef,
    playerHPRef,
    result,
    resolveEnemyMove,
    setCursorCell,
    setHintCell,
    setHintMove,
    setSelected,
    setTurn,
    turn,
    turnCycle,
    turnRef,
  ]);

  return {
    clearAiTimers,
    pickRandomValidMove,
    runAutoPlayerMove,
    setAiStep,
  };
};
