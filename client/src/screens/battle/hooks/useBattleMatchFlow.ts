import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  MATCH_HOLD_BEFORE_EXPLODE_MS,
  buildAffectedScanFromSwap,
  calcSwordDamage,
  collapseResolvedBoard,
  getAllValidMoves,
  getGemRenderType,
  calcManaGainByMagic,
  calcPeachGainByStrength,
  calcPowerGainByStrength,
  type BattleAttackProfile,
  type BattleResourceProfile,
  type JavaBoardEngine,
  type BattlePhase,
  type BattleResult,
  type BattleTurn,
  type Board,
  type CollapseResult,
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
  playerResourceProfile: BattleResourceProfile;
  enemyResourceProfile: BattleResourceProfile;
  playerAttackProfile: BattleAttackProfile;
  enemyAttackProfile: BattleAttackProfile;
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
  addPlayerBoardPendingReward: (expUnit2Delta: number, goldUnit10Delta: number) => void;
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
  playerResourceProfile,
  enemyResourceProfile,
  playerAttackProfile,
  enemyAttackProfile,
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
  addPlayerBoardPendingReward,
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

  const markPendingVictory = useCallback(() => {
    pendingVictoryRef.current = true;
    enemyHPRef.current = 0;
  }, [enemyHPRef, pendingVictoryRef]);

  const isBattleResultLocked = useCallback(() => (
    phaseRef.current === 'over' || pendingVictoryRef.current
  ), [pendingVictoryRef, phaseRef]);

  const processMatches = useCallback((
    board: Board,
    chain: number,
    scanTargets?: Iterable<string | [number, number]>,
    rageBurstState?: RageBurstState,
    bonusTurnState?: BonusTurnState,
    isPassiveObserver = false,
  ) => {
    if (!mountedRef.current) return;

    const resultLocked = isBattleResultLocked();
    const passiveAfterResult = isPassiveObserver;

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
        if ((pendingVictoryRef.current || enemyHPRef.current <= 0) && !passiveAfterResult) {
          finalizeVictory();
          return;
        }

        if (pendingVictoryRef.current || enemyHPRef.current <= 0) {
          return;
        }

        if (passiveAfterResult) {
          phaseRef.current = 'over';
          setPhase('over');
          return;
        }

        if (getAllValidMoves(board).length === 0) {
          const reshuffled = reshuffleBoard(board, boardEngineRef.current);
          showBonusBanner('🔀 Hết nước đi!');
          resetBoardAnim(reshuffled, () => {
            if (!mountedRef.current) return;
            boardRef.current = reshuffled;
            if (!passiveAfterResult) {
              setTurnCycle(v => v + 1);
            }
            phaseRef.current = 'idle';
            setPhase('idle');
          });
          return;
        }

        if (passiveAfterResult) {
          phaseRef.current = 'over';
          setPhase('over');
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
         if (!passiveAfterResult) {
           const newExtra = extraTurnsRef.current + 1;
          extraTurnsRef.current = newExtra;
          setExtraTurns(newExtra);
          flashExtraTurnsBadge(newExtra);
        } else {
          flashExtraTurnsBadge(extraTurnsRef.current);
        }
      }

    const attackerProfile = turnRef.current === 'player'
      ? playerAttackProfile
      : enemyAttackProfile;
    let dmg = calcSwordDamage(board, matched, attackerProfile);
    if (activeRageBurst.active && dmg > 0) {
      dmg *= 2;
    }
    let heal = 0;
    let mp = 0;
    let pow = 0;
    let peachCount = 0;
    let manaGemCount = 0;
    let powerPeachCount = 0;
    let boardStarExpCount = 0;
    let boardWaterExpHalfCount = 0;
    let boardGoldIconCount = 0;

    matched.forEach(key => {
      const [r, c] = key.split(',').map(Number);
      const gem = board[r][c];
      if (gem === null) return;

      const renderType = getGemRenderType(gem);
      if (renderType === 1) peachCount += 1;
      if (renderType === 2) manaGemCount += 1;
      if (renderType === 3) powerPeachCount += 1;
      if (renderType === 4) boardWaterExpHalfCount += 1;
      if (renderType === 5) boardStarExpCount += 1;
      if (renderType === 6) boardGoldIconCount += 1;
    });

    // Java mq.java: resource gain không nhân theo chain global; combo chỉ là
    // visual popup `xN` theo màu. Damage/heal/mp/pow dùng số item thật sự bị
    // clear/apply trong resolve step (`clearedKeys`), vì kiếm đỏ nổ 3x3 hấp thụ
    // mọi item trong vùng nổ và item đó phải apply effect dù không match 3.
    //
    // Reconstruction/remake formula chốt 2026-04-27:
    // - HP/tim chess1 dùng MaxHp + TotalStrength percent từ server.
    // - MP/Âm Dương chess2 dùng MaxMp + TotalMagic percent từ server.
    // - Nộ/đào chess3 dùng MaxPower + TotalStrength percent từ server.
    // Bỏ công thức cũ BaseValue * matchedCount / 3 để thống nhất với
    // BATTLE_SYSTEM_RECONSTRUCTION.md §Resource / damage formulas.
    const collectorProfile = turnRef.current === 'player'
      ? playerResourceProfile
      : enemyResourceProfile;
    const collectorMaxHp = turnRef.current === 'player' ? maxHP : maxEHP;
    const collectorMaxMp = turnRef.current === 'player' ? maxMP : enemyMaxMP;
    const collectorMaxPower = turnRef.current === 'player' ? maxPow : enemyMaxPow;

    heal = calcPeachGainByStrength(collectorMaxHp, peachCount, collectorProfile);
    mp = calcManaGainByMagic(collectorMaxMp, manaGemCount, collectorProfile);
    pow = calcPowerGainByStrength(collectorMaxPower, powerPeachCount, collectorProfile);

    // Reconstruction/remake pending board reward (source:
    // BATTLE_SYSTEM_RECONSTRUCTION.md §Nhóm EXP/Gold/Quan). Java client ky/hs
    // only renders the final result; old server formula is unavailable.
    // Count all truly cleared cells (`clearedKeys`), including fire-sword 3x3
    // absorption/chain cells, not only the initial match trigger cells.
     if (turnRef.current === 'player' && !passiveAfterResult) {
      addPlayerBoardPendingReward(
        boardStarExpCount * 2 + boardWaterExpHalfCount,
        boardGoldIconCount * 2,
      );
    }

    setTimeout(() => {
      if (!mountedRef.current) return;

      const collectorSide = turnRef.current === 'player' ? 'player' : 'enemy';
      spawnCollectFX(matched, board, collectorSide, heal);
      playExplosion(raw, matched, board, () => {
        if (!mountedRef.current) return;

        const clearedBoard = resolved.boardAfterClear;
        const { newBoard, fallMap, affectedKeys } = collapseResolvedBoard(clearedBoard, boardEngineRef.current);
        const lethalPlayerResolution =
          turnRef.current === 'player' &&
          dmg > 0 &&
          enemyHPRef.current - dmg <= 0;
        boardRef.current = newBoard;
        setBoard(clearedBoard);

        const continueAfterFall = () => {
          if (!mountedRef.current) return;

          // Result-lock drain rule (reconstruction/gameplay memory):
          // Even after lethal damage has marked pendingVictory, do not show the
          // result/stat panel immediately. Java-like board playback must first
          // drain any matches that already exist after clear/drop/refill (for
          // example 3 hearts formed by the last fall), collect their resource/
          // pending reward effects, then finalize only when resolve returns no
          // further match. Sword damage is suppressed separately by resultLocked.
          setTimeout(() => processMatches(newBoard, chain + 1, affectedKeys, activeRageBurst, activeBonusTurn, passiveAfterResult), 80);
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
              if (next === 0) markPendingVictory();
              return next;
            });
          };

            if (dmg > 0 && !passiveAfterResult && !resultLocked) {
              playPlayerSwordAttack(
                () => {
                  if (!mountedRef.current) return;
                  if (!passiveAfterResult) applyPlayerDamage();
                },
                () => {
                  if (!mountedRef.current) return;
                  if (lethalPlayerResolution) {
                    playMonsterDefeatSequence(() => {
                      if (!mountedRef.current) return;

                      // Lethal sword hit fallback:
                      // Damage is applied on the actor impact frame, while the
                      // board cascade resolver has already been scheduled after
                      // clear/drop. If that resolver reaches "no match" before
                      // the HP state/ref is marked as pending victory, the old
                      // flow can leave the monster at 0 HP without opening the
                      // victory result. Java-like behavior still drains existing
                      // cascades first; this fallback only finalizes when the
                      // current board has no pending match left.
                      // Source: gameplay bug report 2026-04-27 + 
                      // BATTLE_SYSTEM_RECONSTRUCTION.md result-lock drain rule.
                      setTimeout(() => {
                        if (
                          !mountedRef.current ||
                          phaseRef.current === 'over' ||
                          !pendingVictoryRef.current ||
                          resolveJavaBoardStep(boardRef.current) !== null
                        ) {
                          return;
                        }

                        finalizeVictory();
                      }, 0);
                    });
                    return;
                  }
                },
              );
            }

            if (!passiveAfterResult) applyPlayerRewards();
        } else {
            if (dmg > 0 && !passiveAfterResult && !resultLocked) {
              playMonsterSwordAttack(
                () => {
                  if (!mountedRef.current) return;
                  if (!passiveAfterResult) consumeRageIfNeeded();
                  if (!passiveAfterResult) onPlayerHit();
                  if (!passiveAfterResult) showDamagePopup('player', dmg);
                  if (!passiveAfterResult) {
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
            if (!passiveAfterResult) {
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
    maxEHP,
    maxHP,
    isBattleResultLocked,
    markPendingVictory,
    maxMP,
    maxPow,
    enemyMaxMP,
    enemyMaxPow,
    enemyResourceProfile,
    enemyAttackProfile,
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
    playerAttackProfile,
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
