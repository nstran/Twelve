import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  buildAffectedScanFromSwap,
  calcSwordDamage,
  collapseResolvedBoard,
  getGemFX,
  getAllValidMoves,
  type JavaBoardEngine,
  type BattlePhase,
  type BattleResult,
  type BattleTurn,
  type Board,
  type CollapseResult,
  type GemType,
  reshuffleBoard,
  resolveJavaBoardStep,
  validateSwap,
} from '../core';

interface UseBattleMatchFlowArgs {
  mountedRef: MutableRefObject<boolean>;
  phaseRef: MutableRefObject<BattlePhase>;
  turnRef: MutableRefObject<BattleTurn>;
  extraTurnsRef: MutableRefObject<number>;
  enemyHPRef: MutableRefObject<number>;
  pendingVictoryRef: MutableRefObject<boolean>;
  boardRef: MutableRefObject<Board>;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  enemyMaxMP: number;
  enemyMaxPow: number;
  setBoard: Dispatch<SetStateAction<Board>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setTurnCycle: Dispatch<SetStateAction<number>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setEnemyMana: Dispatch<SetStateAction<number>>;
  setEnemyPower: Dispatch<SetStateAction<number>>;
  setPlayerHP: Dispatch<SetStateAction<number>>;
  setMana: Dispatch<SetStateAction<number>>;
  setPower: Dispatch<SetStateAction<number>>;
  setResult: Dispatch<SetStateAction<BattleResult | null>>;
  playMonsterDefeatSequence: (onComplete: () => void) => void;
  playPlayerSwordAttack: (onImpact: () => void, onComplete: () => void) => void;
  playMonsterSwordAttack: (onImpact: () => void, onComplete: () => void) => void;
  onPlayerHit: () => void;
  onPlayerDefeat: () => void;
  showBonusBanner: (msg: string) => void;
  flashExtraTurnsBadge: (turns: number) => void;
  flashComboBadge: (multiplier: number) => void;
  showDamagePopup: (side: 'player' | 'enemy', amount: number) => void;
  spawnCollectFX: (matched: Set<string>, board: Board, collectorSide: 'player' | 'enemy', healAmount: number) => void;
  playExplosion: (matched: Set<string>, expanded: Set<string>, board: Board, onDone: () => void) => void;
  animateFall: (newBoard: Board, fallMap: CollapseResult['fallMap'], onDone: () => void) => void;
  animateInvalidSwapBounce: (r1: number, c1: number, r2: number, c2: number, onDone: () => void) => void;
  resetBoardAnim: (nextBoard: Board | undefined, onDone: () => void) => void;
}

export const useBattleMatchFlow = ({
  mountedRef,
  phaseRef,
  turnRef,
  extraTurnsRef,
  enemyHPRef,
  pendingVictoryRef,
  boardRef,
  boardEngineRef,
  maxHP,
  maxEHP,
  maxMP,
  maxPow,
  enemyMaxMP,
  enemyMaxPow,
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
  onPlayerHit,
  onPlayerDefeat,
  showBonusBanner,
  flashExtraTurnsBadge,
  flashComboBadge,
  showDamagePopup,
  spawnCollectFX,
  playExplosion,
  animateFall,
  animateInvalidSwapBounce,
  resetBoardAnim,
}: UseBattleMatchFlowArgs) => {
  const finalizeVictory = useCallback(() => {
    pendingVictoryRef.current = false;
    phaseRef.current = 'over';
    setPhase('over');
    setResult('victory');
  }, [pendingVictoryRef, phaseRef, setPhase, setResult]);

  const processMatches = useCallback((board: Board, chain: number, scanTargets?: Iterable<string | [number, number]>) => {
    if (!mountedRef.current) return;

    const resolved = resolveJavaBoardStep(board, scanTargets);
    if (resolved === null) {
      setBoard(board);
      if (pendingVictoryRef.current) {
        finalizeVictory();
        return;
      }

      if (getAllValidMoves(board).length === 0) {
        const reshuffled = reshuffleBoard(board, boardEngineRef.current);
        showBonusBanner('🔀 Hết nước đi!');
        resetBoardAnim(reshuffled, () => {
          if (!mountedRef.current) return;
          boardRef.current = reshuffled;
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

    const raw = resolved.triggerKeys;
    const matched = resolved.clearedKeys;

    if (chain > 0) {
      flashComboBadge(chain + 1);
    }

    if (resolved.bonusTurnCandidate) {
      const newExtra = extraTurnsRef.current + 1;
      extraTurnsRef.current = newExtra;
      setExtraTurns(newExtra);
      flashExtraTurnsBadge(newExtra);
    }

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

        const clearedBoard = resolved.boardAfterClear;
        const { newBoard, fallMap, affectedKeys } = collapseResolvedBoard(clearedBoard, boardEngineRef.current);
        const lethalPlayerResolution =
          turnRef.current === 'player' &&
          dmg > 0 &&
          enemyHPRef.current - dmg <= 0;
        let lethalFallCompleted = false;
        let lethalAttackCompleted = false;

        const tryFinalizeLethalVictory = () => {
          if (!lethalPlayerResolution || !mountedRef.current) {
            return;
          }

          if (!pendingVictoryRef.current) {
            return;
          }

          if (!lethalFallCompleted || !lethalAttackCompleted) {
            return;
          }

          finalizeVictory();
        };

        boardRef.current = newBoard;
        setBoard(clearedBoard);

        const continueAfterFall = () => {
          if (!mountedRef.current) return;
          if (lethalPlayerResolution) {
            lethalFallCompleted = true;
            tryFinalizeLethalVictory();
            return;
          }

          if (pendingVictoryRef.current) {
            finalizeVictory();
            return;
          }
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
              if (next === 0) pendingVictoryRef.current = true;
              return next;
            });
          };

          if (dmg > 0) {
            playPlayerSwordAttack(
              () => {
                if (!mountedRef.current) return;
                applyPlayerDamage();
              },
              () => {
                if (!mountedRef.current) return;
                if (lethalPlayerResolution) {
                  playMonsterDefeatSequence(() => {
                    if (!mountedRef.current) {
                      return;
                    }

                    lethalAttackCompleted = true;
                    tryFinalizeLethalVictory();
                  });
                  return;
                }

                if (pendingVictoryRef.current) {
                  finalizeVictory();
                }
              },
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
          if (mp > 0) setEnemyMana(value => Math.min(enemyMaxMP, value + mp));
          if (pow > 0) setEnemyPower(value => Math.min(enemyMaxPow, value + pow));
        }
      });
    }, MATCH_HOLD_BEFORE_EXPLODE_MS);
  }, [
    animateFall,
    enemyHPRef,
    extraTurnsRef,
    finalizeVictory,
    flashComboBadge,
    flashExtraTurnsBadge,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    mountedRef,
    onPlayerDefeat,
    onPlayerHit,
    pendingVictoryRef,
    phaseRef,
    playMonsterDefeatSequence,
    playMonsterSwordAttack,
    playPlayerSwordAttack,
    playExplosion,
    resetBoardAnim,
    setBoard,
    boardEngineRef,
    setEnemyHP,
    setEnemyMana,
    setEnemyPower,
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
