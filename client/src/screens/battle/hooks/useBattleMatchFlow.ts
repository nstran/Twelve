import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  buildAffectedScanFromSwap,
  calcSwordDamage,
  collapseResolvedBoard,
  getGemFX,
  getGemRenderType,
  getAllValidMoves,
  scaleManaGainByMagic,
  scalePeachGainByStrength,
  scalePowerGainByStrength,
  type BattleGemResourceConfig,
  type BattleResourceProfile,
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
  playerPowerRef: MutableRefObject<number>;
  enemyPowerRef: MutableRefObject<number>;
  pendingVictoryRef: MutableRefObject<boolean>;
  boardRef: MutableRefObject<Board>;
  boardEngineRef: MutableRefObject<JavaBoardEngine>;
  maxHP: number;
  maxEHP: number;
  maxMP: number;
  maxPow: number;
  enemyMaxMP: number;
  enemyMaxPow: number;
  gemResourceConfig?: BattleGemResourceConfig | null;
  playerResourceProfile: BattleResourceProfile;
  enemyResourceProfile: BattleResourceProfile;
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
  animateValidSwap: (r1: number, c1: number, r2: number, c2: number, onDone: () => void) => void;
  animateInvalidSwapBounce: (r1: number, c1: number, r2: number, c2: number, onDone: () => void) => void;
  resetBoardAnim: (nextBoard: Board | undefined, onDone: () => void) => void;
}

interface RageBurstState {
  active: boolean;
  consumed: boolean;
}

interface BonusTurnState {
  granted: boolean;
}

const getServerGemResourceBase = (
  gem: GemType,
  config?: BattleGemResourceConfig | null,
): { heal: number; mana: number; pow: number } => {
  const fx = getGemFX(gem);
  const perGemBases = config?.perGemBases;
  if (!perGemBases || perGemBases.length === 0) {
    // Reconstruction/remake boundary:
    // Java client only proves HP/MP/Power bars and board color families; it does not prove
    // an old server scalar that should be applied to every gem family. If a stale bootstrap
    // payload misses `perGemBases`, keep the local per-color semantic table instead of using
    // global BaseHeal/BaseMana/BasePower for all gems. Otherwise MP-family gems with small
    // `fx.heal > 0` would inherit BaseHealPerGem and incorrectly heal HP when collecting MP.
    // Source: BATTLE_SYSTEM_RECONSTRUCTION.md §HP / MP / Nộ / Combo and
    // docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
    return fx;
  }

  const renderType = getGemRenderType(gem);
  const entry = perGemBases.find(base => base.gemType === gem || base.gemType === renderType);
  return {
    heal: entry?.baseHeal ?? fx.heal,
    mana: entry?.baseMana ?? fx.mana,
    pow: entry?.basePower ?? fx.pow,
  };
};

export const useBattleMatchFlow = ({
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
  gemResourceConfig,
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
  onPlayerHit,
  onPlayerDefeat,
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
}: UseBattleMatchFlowArgs) => {
  const finalizeVictory = useCallback(() => {
    pendingVictoryRef.current = false;
    phaseRef.current = 'over';
    setPhase('over');
    setResult('victory');
  }, [pendingVictoryRef, phaseRef, setPhase, setResult]);

  const processMatches = useCallback((
    board: Board,
    chain: number,
    scanTargets?: Iterable<string | [number, number]>,
    rageBurstState?: RageBurstState,
    bonusTurnState?: BonusTurnState,
    isPassiveObserver = false,
  ) => {
    if (!mountedRef.current) return;

    const activeBonusTurn = bonusTurnState ?? { granted: false };
    const activeRageBurst = rageBurstState ?? {
      active: turnRef.current === 'player'
        ? maxPow > 0 && playerPowerRef.current >= maxPow
        : enemyMaxPow > 0 && enemyPowerRef.current >= enemyMaxPow,
      consumed: false,
    };

      const resolved = resolveJavaBoardStep(board, scanTargets);
      if (resolved === null) {
        setBoard(board);
        if (pendingVictoryRef.current) {
          if (!isPassiveObserver) finalizeVictory();
          return;
        }

        if (getAllValidMoves(board).length === 0) {
          const reshuffled = reshuffleBoard(board, boardEngineRef.current);
          showBonusBanner('🔀 Hết nước đi!');
          resetBoardAnim(reshuffled, () => {
            if (!mountedRef.current) return;
            boardRef.current = reshuffled;
            if (!isPassiveObserver) {
              setTurnCycle(v => v + 1);
            }
            phaseRef.current = 'idle';
            setPhase('idle');
          });
          return;
        }

        if (isPassiveObserver) {
          phaseRef.current = 'idle';
          setPhase('idle');
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
          setTurnCycle(v => v + 1);
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

      if (resolved.bonusTurnCandidate && !activeBonusTurn.granted) {
        // Java mq/mt reconstruction:
        // - Java does not prove Candy-Crush style natural special spawning, but mt.a(mw,int)
        //   routes matched spans with len >= 4 through the highlighted combat/effect path.
        // - Client-side remake keeps the old "match 4/5 grants one retained turn" contract.
        // - Store banked extra turns only once per full swap/cascade, otherwise chain falls can
        //   incorrectly report multiple remaining turns.
        activeBonusTurn.granted = true;
        if (!isPassiveObserver) {
          const newExtra = extraTurnsRef.current + 1;
          extraTurnsRef.current = newExtra;
          setExtraTurns(newExtra);
          flashExtraTurnsBadge(newExtra);
        } else {
          flashExtraTurnsBadge(extraTurnsRef.current);
        }
      }

    let dmg = calcSwordDamage(board, matched);
    if (activeRageBurst.active && dmg > 0) {
      dmg *= 2;
    }
    let heal = 0;
    let mp = 0;
    let pow = 0;
    let baseHeal = 0;
    let baseMp = 0;
    const counts: Partial<Record<GemType, number>> = {};

    raw.forEach(key => {
      const [r, c] = key.split(',').map(Number);
      const gem = board[r][c];
      if (gem !== null) counts[gem] = (counts[gem] ?? 0) + 1;
    });

    // Java mq.java: resource gain không nhân theo chain global; combo chỉ là
    // visual popup `xN` theo màu. Damage/heal/mp/pow dùng raw count từ
    // triggerKeys — KHÔNG nhân chain multiplier global ở đây.
    // Nguồn: mq.java:667, mq.java:705, mt.java:844.
    Object.entries(counts).forEach(([gemKey, count]) => {
      const gem = Number(gemKey) as GemType;
      const fx = getGemFX(gem);
      const resourceBase = getServerGemResourceBase(gem, gemResourceConfig);
      // Server authority note:
      // PvE/PvP battle resource base values come from MonsterBattleBootstrapResponse.GemResourceConfig,
      // which is produced by the .NET battle rule factory from
      // docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
      // FE only preserves Java board color semantics (which gem family can grant HP/MP/Power)
      // and applies server-provided per-gem base coefficients + server-provided gain percents.
      baseHeal += Math.trunc((fx.heal > 0 ? resourceBase.heal : 0) * count! / 3);
      baseMp += Math.trunc((fx.mana > 0 ? resourceBase.mana : 0) * count! / 3);
      pow += Math.trunc((fx.pow > 0 ? resourceBase.pow : 0) * count! / 3);
    });
    // dmg từ calcSwordDamage đã tính đúng raw, không nhân chain.

    const collectorProfile = turnRef.current === 'player'
      ? playerResourceProfile
      : enemyResourceProfile;
    heal = scalePeachGainByStrength(baseHeal, collectorProfile);
    mp = scaleManaGainByMagic(baseMp, collectorProfile);
    pow = scalePowerGainByStrength(pow, collectorProfile);

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
          setTimeout(() => processMatches(newBoard, chain + 1, affectedKeys, activeRageBurst, activeBonusTurn, isPassiveObserver), 80);
        };
        animateFall(newBoard, fallMap, continueAfterFall);

        const consumeRageIfNeeded = () => {
          if (!activeRageBurst.active || activeRageBurst.consumed || dmg <= 0) {
            return;
          }

          activeRageBurst.consumed = true;
          showBonusBanner('No day x2');

          if (turnRef.current === 'player') {
            playerPowerRef.current = 0;
            setPower(() => 0);
            return;
          }

          enemyPowerRef.current = 0;
          setEnemyPower(() => 0);
        };

        if (turnRef.current === 'player') {
          const applyPlayerRewards = () => {
            if (heal > 0) setPlayerHP(hp => Math.min(maxHP, hp + heal));
            if (mp > 0) setMana(value => Math.min(maxMP, value + mp));
            if (pow > 0) {
              setPower(value => {
                const next = Math.min(maxPow, value + pow);
                playerPowerRef.current = next;
                return next;
              });
            }
          };

          const applyPlayerDamage = () => {
            if (dmg <= 0) return;
            consumeRageIfNeeded();
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
                  if (!isPassiveObserver) applyPlayerDamage();
                },
                () => {
                  if (!mountedRef.current) return;
                  if (lethalPlayerResolution) {
                    playMonsterDefeatSequence(() => {
                      if (!mountedRef.current) return;
                      lethalAttackCompleted = true;
                      if (!isPassiveObserver) tryFinalizeLethalVictory();
                    });
                    return;
                  }

                  if (pendingVictoryRef.current && !isPassiveObserver) {
                    finalizeVictory();
                  }
                },
              );
            }

            if (!isPassiveObserver) applyPlayerRewards();
        } else {
            if (dmg > 0) {
              playMonsterSwordAttack(
                () => {
                  if (!mountedRef.current) return;
                  if (!isPassiveObserver) consumeRageIfNeeded();
                  onPlayerHit();
                  showDamagePopup('player', dmg);
                  if (!isPassiveObserver) {
                    setPlayerHP(hp => {
                      const next = Math.max(0, hp - dmg);
                      if (next === 0 && phaseRef.current !== 'over') {
                        phaseRef.current = 'over';
                        setPhase('over');
                        onPlayerDefeat();
                      }
                      return next;
                    });
                  }
                },
                () => {},
              );
            }
            if (!isPassiveObserver) {
              if (heal > 0) setEnemyHP(hp => Math.min(maxEHP, hp + heal));
              if (mp > 0) setEnemyMana(value => Math.min(enemyMaxMP, value + mp));
              if (pow > 0) {
                setEnemyPower(value => {
                  const next = Math.min(enemyMaxPow, value + pow);
                  enemyPowerRef.current = next;
                  return next;
                });
              }
            }
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
    gemResourceConfig,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    enemyResourceProfile,
    enemyPowerRef,
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
    playerPowerRef,
    playerResourceProfile,
  ]);

  const doDirectSwap = useCallback((r1: number, c1: number, r2: number, c2: number, isPassiveObserver = false) => {
    const board = boardRef.current;
    const swap = validateSwap(board, r1, c1, r2, c2);
    if (swap === null) {
      phaseRef.current = 'busy';
      setPhase('busy');
      animateInvalidSwapBounce(r1, c1, r2, c2, () => {
        if (!mountedRef.current) return;
        if (!isPassiveObserver && turnRef.current === 'monster') {
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
    
    animateValidSwap(r1, c1, r2, c2, () => {
      if (!mountedRef.current) return;
      boardRef.current = nextBoard;
      setBoard(nextBoard);
      processMatches(nextBoard, 0, buildAffectedScanFromSwap({ r1, c1, r2, c2 }), undefined, undefined, isPassiveObserver);
    });
  }, [animateInvalidSwapBounce, animateValidSwap, boardRef, mountedRef, phaseRef, processMatches, setBoard, setPhase, setTurn, turnRef]);

  return { doDirectSwap, processMatches };
};
