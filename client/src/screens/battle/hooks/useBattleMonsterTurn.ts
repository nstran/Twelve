import { useCallback, useEffect, useRef, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  buildActiveBattleSkillCastFromPacket,
  findMatchesFromAffected,
  getAllValidMoves,
  type ActiveBattleSkillCast,
  type BattleCell,
  type BattlePhase,
  type BattleResult,
  type BattleScreenProps,
  type BattleSkillRuntimePacket,
  type BattleTurn,
  type Board,
  type MoveSpec,
} from '../core';
import type { useBattleMatchFlow } from './useBattleMatchFlow';

interface UseBattleMonsterTurnArgs {
  applyServerPacketBoardMutation: (packet: BattleSkillRuntimePacket) => void;
  applyServerPacketMarkCell: (cell: BattleCell, stateId: number) => void;
  boardRef: MutableRefObject<Board>;
  charsRowHeight: number;
  charsTop: number;
  doDirectSwapRef: MutableRefObject<(r1: number, c1: number, r2: number, c2: number) => void>;
  extraTurnsRef: MutableRefObject<number>;
  flashExtraTurnsBadge: (turns: number) => void;
  maxEHP: number;
  maxHP: number;
  maxMP: number;
  maxPow: number;
  enemyMaxMP: number;
  enemyMaxPow: number;
  monsterBaseLeft: number;
  monsterGroundOffset: number;
  monsterSize: { w: number; h: number };
  mountedRef: MutableRefObject<boolean>;
  panelLeft: number;
  panelTop: number;
  phase: BattlePhase;
  phaseRef: MutableRefObject<BattlePhase>;
  playerBaseLeft: number;
  playerSize: { w: number; h: number; groundOffset?: number };
  processMatchesRef: MutableRefObject<ReturnType<typeof useBattleMatchFlow>['processMatches'] | null>;
  resolveEnemyTurnPlan?: BattleScreenProps['resolveEnemyTurnPlan'];
  result: BattleResult | null;
  sessionId: string;
  setActiveSkillCasts: Dispatch<SetStateAction<ActiveBattleSkillCast[]>>;
  setAiStep: Dispatch<SetStateAction<'think' | 'pick1' | 'pick2' | null>>;
  setCursorCell: Dispatch<SetStateAction<BattleCell>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setEnemyMana: Dispatch<SetStateAction<number>>;
  setEnemyPower: Dispatch<SetStateAction<number>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setMana: Dispatch<SetStateAction<number>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setPlayerHP: Dispatch<SetStateAction<number>>;
  setPower: Dispatch<SetStateAction<number>>;
  setSelected: Dispatch<SetStateAction<BattleCell | null>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setTurnCycle: Dispatch<SetStateAction<number>>;
  showBonusBanner: (msg: string) => void;
  showDamagePopup: (side: 'player' | 'enemy', amount: number) => void;
  skillCastTimersRef: MutableRefObject<ReturnType<typeof setTimeout>[]>;
  startPlayerDefeatSequence: () => void;
  playPlayerHitReaction: () => void;
  turn: BattleTurn;
  turnRef: MutableRefObject<BattleTurn>;
}

export const useBattleMonsterTurn = ({
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
  sessionId,
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
  playPlayerHitReaction,
  turn,
  turnRef,
}: UseBattleMonsterTurnArgs) => {
  const enemyTurnRequestRef = useRef(false);
  const monsterTurnStateRef = useRef<'idle' | 'planning' | 'playback_move' | 'skill'>('idle');
  const monsterTurnTimersRef = useRef<ReturnType<typeof setTimeout>[]>([]);

  const clearMonsterTurnTimers = useCallback(() => {
    monsterTurnTimersRef.current.forEach(clearTimeout);
    monsterTurnTimersRef.current = [];
  }, []);

  const pickFallbackMove = useCallback(() => {
    const validMoves = getAllValidMoves(boardRef.current);
    if (validMoves.length <= 0) {
      return null;
    }

    return validMoves[0] ?? null;
  }, [boardRef]);

  const handBackTurnToPlayer = useCallback((message?: string) => {
    if (!mountedRef.current) {
      return;
    }

    enemyTurnRequestRef.current = false;
    monsterTurnStateRef.current = 'idle';
    if (message) {
      showBonusBanner(message);
    }

    setAiStep(null);
    turnRef.current = 'player';
    setTurn('player');
    setTurnCycle(cycle => cycle + 1);
  }, [mountedRef, setAiStep, setTurn, setTurnCycle, showBonusBanner, turnRef]);

  const playbackMove = useCallback((move: {
    fromRow: number;
    fromCol: number;
    toRow: number;
    toCol: number;
  }) => {
    monsterTurnStateRef.current = 'playback_move';
    setAiStep('pick1');
    setCursorCell([move.fromRow, move.fromCol]);
    setSelected([move.fromRow, move.fromCol]);

    const pickSecondTimer = setTimeout(() => {
      if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') {
        return;
      }

      setAiStep('pick2');
      setCursorCell([move.toRow, move.toCol]);
      setSelected([move.toRow, move.toCol]);

      const swapTimer = setTimeout(() => {
        if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') {
          return;
        }

        enemyTurnRequestRef.current = false;
        monsterTurnStateRef.current = 'idle';
        setAiStep(null);
        setSelected(null);
        doDirectSwapRef.current(move.fromRow, move.fromCol, move.toRow, move.toCol);
      }, 350);

      monsterTurnTimersRef.current.push(swapTimer);
    }, 400);

    monsterTurnTimersRef.current.push(pickSecondTimer);
  }, [
    doDirectSwapRef,
    mountedRef,
    phaseRef,
    setAiStep,
    setCursorCell,
    setSelected,
    turnRef,
  ]);

  const playbackSkillPacket = useCallback((packet: BattleSkillRuntimePacket) => {
    setPhase('busy');
    phaseRef.current = 'busy';
    monsterTurnStateRef.current = 'skill';

    const cast = buildActiveBattleSkillCastFromPacket(packet, {
      panelLeft,
      panelTop,
      charsTop,
      charsRowHeight,
      playerBaseLeft,
      monsterBaseLeft,
      playerSize,
      monsterSize,
      monsterGroundOffset,
    });

    setActiveSkillCasts(prev => [...prev, cast]);

    const boardMutationTimers =
      packet.boardMutation.kind === 'mark'
        ? packet.boardMutation.cells.map((cell, index) => setTimeout(() => {
          if (!mountedRef.current) {
            return;
          }

          applyServerPacketMarkCell(cell, packet.boardMutation.stateId ?? 10);
        }, cast.boardMutationDelayMs + index * 4 * 40))
        : [setTimeout(() => {
          if (!mountedRef.current) {
            return;
          }

          applyServerPacketBoardMutation(packet);
        }, cast.boardMutationDelayMs)];

    const impactTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      if (packet.impact.hitsActor) {
        playPlayerHitReaction();
      }

      let playerHpDelta = 0;
      let playerManaDelta = 0;
      let playerPowerDelta = 0;
      let enemyHpDelta = 0;
      let enemyManaDelta = 0;
      let enemyPowerDelta = 0;

      for (const delta of packet.actorDeltas ?? []) {
        if (delta.side === 'player') {
          playerHpDelta += delta.hpDelta;
          playerManaDelta += delta.manaDelta;
          playerPowerDelta += delta.powerDelta;
          continue;
        }

        enemyHpDelta += delta.hpDelta;
        enemyManaDelta += delta.manaDelta;
        enemyPowerDelta += delta.powerDelta;
      }

      if (playerHpDelta < 0) {
        showDamagePopup('player', Math.abs(playerHpDelta));
      }

      if (playerHpDelta !== 0) {
        setPlayerHP((hp) => {
          const next = Math.max(0, Math.min(maxHP, hp + playerHpDelta));
          if (next === 0) {
            startPlayerDefeatSequence();
          }

          return next;
        });
      } else if ((packet.impact.damage ?? 0) > 0) {
        showDamagePopup('player', packet.impact.damage ?? 0);
        setPlayerHP((hp) => {
          const next = Math.max(0, Math.min(maxHP, hp - (packet.impact.damage ?? 0)));
          if (next === 0) {
            startPlayerDefeatSequence();
          }

          return next;
        });
      }

      if (playerManaDelta !== 0) {
        setMana((value) => Math.max(0, Math.min(maxMP, value + playerManaDelta)));
      }

      if (playerPowerDelta !== 0) {
        setPower((value) => Math.max(0, Math.min(maxPow, value + playerPowerDelta)));
      }

      if (enemyHpDelta !== 0) {
        setEnemyHP((hp) => Math.max(0, Math.min(maxEHP, hp + enemyHpDelta)));
      }

      if (enemyManaDelta !== 0) {
        setEnemyMana((value) => Math.max(0, Math.min(enemyMaxMP, value + enemyManaDelta)));
      }

      if (enemyPowerDelta !== 0) {
        setEnemyPower((value) => Math.max(0, Math.min(enemyMaxPow, value + enemyPowerDelta)));
      }
    }, cast.impactDelayMs);

    const finishTimer = setTimeout(() => {
      if (!mountedRef.current) {
        return;
      }

      setActiveSkillCasts(prev => prev.filter(item => item.key !== cast.key));

      if (
        packet.boardMutation.kind === 'mark' &&
        processMatchesRef.current
      ) {
        const markMatches = findMatchesFromAffected(boardRef.current, packet.boardMutation.cells);
        if (markMatches.size > 0) {
          processMatchesRef.current(boardRef.current, 0, packet.boardMutation.cells);
          return;
        }
      }

      const remainingTurnsDelta = Math.max(
        0,
        packet.turnDelta?.remainingTurnsDelta ?? (packet.grantsExtraTurn ? 1 : 0),
      );

      if (remainingTurnsDelta > 0) {
        const bankedExtraTurns = Math.max(0, remainingTurnsDelta - 1);
        extraTurnsRef.current = bankedExtraTurns;
        setExtraTurns(bankedExtraTurns);
        flashExtraTurnsBadge(remainingTurnsDelta);
        turnRef.current = 'monster';
        setTurn('monster');
        setTurnCycle(cycle => cycle + 1);
        phaseRef.current = 'idle';
        setPhase('idle');
        return;
      }

      turnRef.current = 'player';
      setTurn('player');
      setTurnCycle(cycle => cycle + 1);
      phaseRef.current = 'idle';
      setPhase('idle');
    }, cast.durationMs);

    skillCastTimersRef.current.push(...boardMutationTimers, impactTimer, finishTimer);
  }, [
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    boardRef,
    charsRowHeight,
    charsTop,
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
    phaseRef,
    playPlayerHitReaction,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    setActiveSkillCasts,
    setEnemyHP,
    setEnemyMana,
    setEnemyPower,
    setExtraTurns,
    setMana,
    setPhase,
    setPlayerHP,
    setPower,
    setTurn,
    setTurnCycle,
    showDamagePopup,
    skillCastTimersRef,
    startPlayerDefeatSequence,
    turnRef,
  ]);

  useEffect(() => () => {
    clearMonsterTurnTimers();
    enemyTurnRequestRef.current = false;
    monsterTurnStateRef.current = 'idle';
  }, [clearMonsterTurnTimers]);

  useEffect(() => {
    if (phase !== 'idle' || turn !== 'monster' || result !== null) {
      return;
    }

    if (monsterTurnStateRef.current !== 'idle') {
      return;
    }

    if (enemyTurnRequestRef.current) {
      return;
    }

    enemyTurnRequestRef.current = true;
    monsterTurnStateRef.current = 'planning';
    clearMonsterTurnTimers();
    setAiStep('think');
    setSelected(null);
    setHintCell(null);
    setHintMove(null);

    const thinkTimer = setTimeout(() => {
      const plannerPromise = resolveEnemyTurnPlan
        ? Promise.resolve(resolveEnemyTurnPlan({
          sessionId,
          board: boardRef.current,
        }))
        : Promise.resolve(null);

      void plannerPromise
        .then((plan) => {
          if (!mountedRef.current || phaseRef.current !== 'idle' || turnRef.current !== 'monster') {
            return;
          }

          if (!plan || plan.action === 'pass') {
            const fallbackMove = pickFallbackMove();
            if (!fallbackMove) {
              handBackTurnToPlayer('Quái hết nước đi');
              return;
            }

            playbackMove({
              fromRow: fallbackMove.r1,
              fromCol: fallbackMove.c1,
              toRow: fallbackMove.r2,
              toCol: fallbackMove.c2,
            });
            return;
          }

          if (plan.action === 'move') {
            const move = plan.move;
            if (!move) {
              const fallbackMove = pickFallbackMove();
              if (!fallbackMove) {
                handBackTurnToPlayer('Quái thiếu nước đi');
                return;
              }

              playbackMove({
                fromRow: fallbackMove.r1,
                fromCol: fallbackMove.c1,
                toRow: fallbackMove.r2,
                toCol: fallbackMove.c2,
              });
              return;
            }
            playbackMove(move);
            return;
          }

          if (!plan.skillPacket || plan.skillPacket.runtimeSource !== 'server_packet') {
            const fallbackMove = pickFallbackMove();
            if (!fallbackMove) {
              handBackTurnToPlayer('Quái không tạo được skill plan');
              return;
            }

            playbackMove({
              fromRow: fallbackMove.r1,
              fromCol: fallbackMove.c1,
              toRow: fallbackMove.r2,
              toCol: fallbackMove.c2,
            });
            return;
          }

          enemyTurnRequestRef.current = false;
          setAiStep(null);
          playbackSkillPacket(plan.skillPacket);
        })
        .catch(() => {
          const fallbackMove = pickFallbackMove();
          if (!fallbackMove) {
            handBackTurnToPlayer('Lượt quái lỗi server planner');
            return;
          }

          playbackMove({
            fromRow: fallbackMove.r1,
            fromCol: fallbackMove.c1,
            toRow: fallbackMove.r2,
            toCol: fallbackMove.c2,
          });
        });
    }, 550);

    monsterTurnTimersRef.current.push(thinkTimer);
  }, [
    boardRef,
    clearMonsterTurnTimers,
    doDirectSwapRef,
    handBackTurnToPlayer,
    mountedRef,
    phase,
    phaseRef,
    pickFallbackMove,
    playbackMove,
    playbackSkillPacket,
    resolveEnemyTurnPlan,
    result,
    sessionId,
    setAiStep,
    setCursorCell,
    setHintCell,
    setHintMove,
    setSelected,
    turn,
    turnRef,
    sessionId,
  ]);
};
