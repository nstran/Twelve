import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  buildAffectedScanFromSwap,
  calcSwordDamage,
  clearMatchedCells,
  collapseLogic,
  expandSword,
  findMatches,
  findMatchesFromAffected,
  getGemFX,
  getAllValidMoves,
  hasBonusTurn,
  type JavaBoardEngine,
  type BattlePhase,
  type BattleResult,
  type BattleTurn,
  type Board,
  type GemType,
  validateSwap,
} from '../core';

interface UseBattleMatchFlowArgs {
  mountedRef: MutableRefObject<boolean>;
  phaseRef: MutableRefObject<BattlePhase>;
  turnRef: MutableRefObject<BattleTurn>;
  extraTurnsRef: MutableRefObject<number>;
  boardRef: MutableRefObject<Board>;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  setBoard: Dispatch<SetStateAction<Board>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setTurnCycle: Dispatch<SetStateAction<number>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setPlayerHP: Dispatch<SetStateAction<number>>;
  setMana: Dispatch<SetStateAction<number>>;
  setPower: Dispatch<SetStateAction<number>>;
  setResult: Dispatch<SetStateAction<BattleResult | null>>;
  playPlayerSwordAttack: (onImpact: () => void, onComplete: () => void) => void;
  playMonsterSwordAttack: (onImpact: () => void, onComplete: () => void) => void;
  onPlayerHit: () => void;
  onPlayerDefeat: () => void;
  showBonusBanner: (msg: string) => void;
  showDamagePopup: (side: 'player' | 'enemy', amount: number) => void;
  spawnCollectFX: (matched: Set<string>, board: Board, collectorSide: 'player' | 'enemy', healAmount: number) => void;
  playExplosion: (matched: Set<string>, expanded: Set<string>, board: Board, onDone: () => void) => void;
  animateFall: (newBoard: Board, fallMap: ReturnType<typeof collapseLogic>['fallMap'], onDone: () => void) => void;
  animateInvalidSwapBounce: (r1: number, c1: number, r2: number, c2: number, onDone: () => void) => void;
  resetBoardAnim: (onDone: () => void) => void;
}

export const useBattleMatchFlow = ({
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
  onPlayerHit,
  onPlayerDefeat,
  showBonusBanner,
  showDamagePopup,
  spawnCollectFX,
  playExplosion,
  animateFall,
  animateInvalidSwapBounce,
  resetBoardAnim,
}: UseBattleMatchFlowArgs) => {
  const processMatches = useCallback((board: Board, chain: number, scanTargets?: Iterable<string | [number, number]>) => {
    if (!mountedRef.current) return;

    const raw = scanTargets ? findMatchesFromAffected(board, scanTargets) : findMatches(board);
    if (raw.size === 0) {
      setBoard(board);

      if (getAllValidMoves(board).length === 0) {
        showBonusBanner('🔀 Hết nước! Bàn cờ mới!');
        resetBoardAnim(() => {
          if (!mountedRef.current) return;
          setTurnCycle(v => v + 1);
          phaseRef.current = 'idle';
          setPhase('idle');
        });
        return;
      }

      if (extraTurnsRef.current > 0) {
        const remaining = extraTurnsRef.current - 1;
        extraTurnsRef.current = remaining;
        setExtraTurns(remaining);
        const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
        showBonusBanner(`🔄 ${who} được thêm lượt! ${remaining > 0 ? `Còn ${remaining} lượt` : ''}`);
        setTurnCycle(v => v + 1);
        phaseRef.current = 'idle';
        setPhase('idle');
      } else {
        const nextTurn = turnRef.current === 'player' ? 'monster' : 'player';
        turnRef.current = nextTurn;
        setTurn(nextTurn);
        phaseRef.current = 'idle';
        setPhase('idle');
      }
      return;
    }

    if (hasBonusTurn(raw, board)) {
      const newExtra = extraTurnsRef.current + 1;
      extraTurnsRef.current = newExtra;
      setExtraTurns(newExtra);
      const who = turnRef.current === 'player' ? 'Bạn' : 'Quái';
      showBonusBanner(`✨ ${who} +1 lượt!${newExtra > 1 ? ` (tổng ${newExtra})` : ''}`);
    }

    const matched = expandSword(raw, board);
    let dmg = calcSwordDamage(board, matched);
    let heal = 0;
    let mp = 0;
    let pow = 0;
    const counts: Partial<Record<GemType, number>> = {};

    raw.forEach(key => {
      const [r, c] = key.split(',').map(Number);
      const gem = board[r][c];
      if (gem !== null) counts[gem] = (counts[gem] ?? 0) + 1;
    });

    Object.entries(counts).forEach(([gemKey, count]) => {
      const gem = Number(gemKey) as GemType;
      const fx = getGemFX(gem);
      const mul = 1 + chain * 0.4;
      heal += Math.round(fx.heal * (count! / 3) * mul);
      mp += Math.round(fx.mana * count!);
      pow += Math.round(fx.pow * count!);
    });
    dmg = Math.round(dmg * (1 + chain * 0.4));

    setTimeout(() => {
      if (!mountedRef.current || phaseRef.current === 'over') return;

      const collectorSide = turnRef.current === 'player' ? 'player' : 'enemy';
      spawnCollectFX(raw, board, collectorSide, heal);
      playExplosion(raw, matched, board, () => {
        if (!mountedRef.current) return;

        const clearedBoard = clearMatchedCells(board, matched);
        const { newBoard, fallMap, affectedKeys } = collapseLogic(board, matched, boardEngineRef.current);
        setBoard(clearedBoard);
        const continueAfterFall = () => {
          if (!mountedRef.current) return;
          setTimeout(() => processMatches(newBoard, chain + 1, affectedKeys), 80);
        };
        animateFall(newBoard, fallMap, continueAfterFall);

        if (turnRef.current === 'player') {
          const applyPlayerRewards = () => {
            if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
            if (mp > 0) setMana(value => Math.min(maxMP, value + mp));
            if (pow > 0) setPower(value => Math.min(maxPow, value + pow));
          };

          const applyPlayerDamage = () => {
            if (dmg <= 0) return;
            showDamagePopup('enemy', dmg);
            setEnemyHP(hp => {
              const next = Math.max(0, hp - dmg);
              if (next === 0 && phaseRef.current !== 'over') {
                phaseRef.current = 'over';
                setPhase('over');
                setResult('victory');
              }
              return next;
            });
          };

          if (dmg > 0) {
            playPlayerSwordAttack(
              () => {
                if (!mountedRef.current) return;
                applyPlayerDamage();
              },
              () => {},
            );
          }

          applyPlayerRewards();
        } else {
          if (dmg > 0) {
            playMonsterSwordAttack(
              () => {
                if (!mountedRef.current) return;
                onPlayerHit();
                showDamagePopup('player', dmg);
                setPlayerHP(hp => {
                  const next = Math.max(0, hp - dmg);
                  if (next === 0 && phaseRef.current !== 'over') {
                    phaseRef.current = 'over';
                    setPhase('over');
                    onPlayerDefeat();
                  }
                  return next;
                });
              },
              () => {},
            );
          }
          if (heal > 0) setEnemyHP(hp => Math.min(maxEHP, hp + heal));
        }
      });
    }, MATCH_HOLD_BEFORE_EXPLODE_MS);
  }, [
    animateFall,
    extraTurnsRef,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    mountedRef,
    onPlayerDefeat,
    onPlayerHit,
    phaseRef,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
    playExplosion,
    resetBoardAnim,
    setBoard,
    boardEngineRef,
    setEnemyHP,
    setExtraTurns,
    setMana,
    setPhase,
    setPlayerHP,
    setPower,
    setResult,
    setTurn,
    showBonusBanner,
    showDamagePopup,
    spawnCollectFX,
    setTurnCycle,
    turnRef,
  ]);

  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    const board = boardRef.current;
    const swap = validateSwap(board, r1, c1, r2, c2);
    if (swap === null) {
      phaseRef.current = 'busy';
      setPhase('busy');
      animateInvalidSwapBounce(r1, c1, r2, c2, () => {
        if (!mountedRef.current) return;
        if (turnRef.current === 'monster') {
          turnRef.current = 'player';
          setTurn('player');
        }
        phaseRef.current = 'idle';
        setPhase('idle');
      });
      return;
    }

    const nextBoard: Board = board.map(row => [...row]);
    [nextBoard[r1][c1], nextBoard[r2][c2]] = [nextBoard[r2][c2], nextBoard[r1][c1]];
    phaseRef.current = 'busy';
    setPhase('busy');
    setBoard(nextBoard);
    processMatches(nextBoard, 0, buildAffectedScanFromSwap({ r1, c1, r2, c2 }));
  }, [animateInvalidSwapBounce, boardRef, mountedRef, phaseRef, processMatches, setBoard, setPhase, setTurn, turnRef]);

  return { doDirectSwap, processMatches };
};
