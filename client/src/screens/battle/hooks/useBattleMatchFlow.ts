import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  calcSwordDamage,
  collapseLogic,
  expandSword,
  findMatches,
  GEM_FX,
  getAllValidMoves,
  hasBonusTurn,
  type BattlePhase,
  type BattleResult,
  type BattleTurn,
  type Board,
  type GemType,
} from '../core';

interface UseBattleMatchFlowArgs {
  mountedRef: MutableRefObject<boolean>;
  phaseRef: MutableRefObject<BattlePhase>;
  turnRef: MutableRefObject<BattleTurn>;
  extraTurnsRef: MutableRefObject<number>;
  boardRef: MutableRefObject<Board>;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  setBoard: Dispatch<SetStateAction<Board>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setPlayerHP: Dispatch<SetStateAction<number>>;
  setMana: Dispatch<SetStateAction<number>>;
  setPower: Dispatch<SetStateAction<number>>;
  setResult: Dispatch<SetStateAction<BattleResult | null>>;
  setMonAtk: Dispatch<SetStateAction<boolean>>;
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
  setMonAtk,
  showBonusBanner,
  showDamagePopup,
  spawnCollectFX,
  playExplosion,
  animateFall,
  animateInvalidSwapBounce,
  resetBoardAnim,
}: UseBattleMatchFlowArgs) => {
  const processMatches = useCallback((board: Board, chain: number) => {
    if (!mountedRef.current) return;

    const raw = findMatches(board);
    if (raw.size === 0) {
      setBoard(board);

      if (getAllValidMoves(board).length === 0) {
        showBonusBanner('🔀 Hết nước! Bàn cờ mới!');
        resetBoardAnim(() => {
          if (!mountedRef.current) return;
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
      const fx = GEM_FX[gem];
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

        const { newBoard, fallMap } = collapseLogic(board, matched);
        if (turnRef.current === 'player') {
          if (dmg > 0) showDamagePopup('enemy', dmg);
          setEnemyHP(hp => {
            const next = Math.max(0, hp - dmg);
            if (next === 0 && phaseRef.current !== 'over') {
              phaseRef.current = 'over';
              setPhase('over');
              setResult('victory');
            }
            return next;
          });
          if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
          if (mp > 0) setMana(value => Math.min(maxMP, value + mp));
          if (pow > 0) setPower(value => Math.min(maxPow, value + pow));
        } else {
          if (dmg > 0) {
            showDamagePopup('player', dmg);
            setMonAtk(true);
            setTimeout(() => {
              if (mountedRef.current) setMonAtk(false);
            }, 600);
            setPlayerHP(hp => {
              const next = Math.max(0, hp - dmg);
              if (next === 0 && phaseRef.current !== 'over') {
                phaseRef.current = 'over';
                setPhase('over');
                setResult('defeat');
              }
              return next;
            });
          }
          if (heal > 0) setEnemyHP(hp => Math.min(maxEHP, hp + heal));
        }

        animateFall(newBoard, fallMap, () => {
          setTimeout(() => processMatches(newBoard, chain + 1), 80);
        });
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
    phaseRef,
    playExplosion,
    resetBoardAnim,
    setBoard,
    setEnemyHP,
    setExtraTurns,
    setMana,
    setMonAtk,
    setPhase,
    setPlayerHP,
    setPower,
    setResult,
    setTurn,
    showBonusBanner,
    showDamagePopup,
    spawnCollectFX,
    turnRef,
  ]);

  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number) => {
    const board = boardRef.current;
    const nextBoard: Board = board.map(row => [...row]);
    [nextBoard[r1][c1], nextBoard[r2][c2]] = [nextBoard[r2][c2], nextBoard[r1][c1]];

    if (findMatches(nextBoard).size === 0) {
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

    phaseRef.current = 'busy';
    setPhase('busy');
    setBoard(nextBoard);
    processMatches(nextBoard, 0);
  }, [animateInvalidSwapBounce, boardRef, mountedRef, phaseRef, processMatches, setBoard, setPhase, setTurn, turnRef]);

  return { doDirectSwap, processMatches };
};
