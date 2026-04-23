import { useEffect, useRef, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import {
  buildActiveBattleSkillCastFromPacket,
  findMatchesFromAffected,
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

interface UseBattleEnemyTurnArgs {
  applyServerPacketBoardMutation: (packet: BattleSkillRuntimePacket) => void;
  applyServerPacketMarkCell: (cell: BattleCell, stateId: number) => void;
  boardRef: MutableRefObject<Board>;
  charsRowHeight: number;
  charsTop: number;
  enemyTurnRequestRef: MutableRefObject<boolean>;
  extraTurnsRef: MutableRefObject<number>;
  flashExtraTurnsBadge: (turns: number) => void;
  maxEHP: number;
  maxHP: number;
  maxMP: number;
  maxPow: number;
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
  resolveEnemyTurn?: BattleScreenProps['resolveEnemyTurn'];
  result: BattleResult | null;
  sessionId: string;
  setActiveSkillCasts: Dispatch<SetStateAction<ActiveBattleSkillCast[]>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
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

export const useBattleEnemyTurn = ({
  applyServerPacketBoardMutation,
  applyServerPacketMarkCell,
  boardRef,
  charsRowHeight,
  charsTop,
  enemyTurnRequestRef,
  extraTurnsRef,
  flashExtraTurnsBadge,
  maxEHP,
  maxHP,
  maxMP,
  maxPow,
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
  resolveEnemyTurn,
  result,
  sessionId,
  setActiveSkillCasts,
  setEnemyHP,
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
}: UseBattleEnemyTurnArgs) => {
  const enemyThinkTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  useEffect(() => () => {
    if (enemyThinkTimerRef.current) {
      clearTimeout(enemyThinkTimerRef.current);
      enemyThinkTimerRef.current = null;
    }
  }, []);

  useEffect(() => {
    if (!resolveEnemyTurn || phase !== 'idle' || turn !== 'monster' || result !== null) {
      return;
    }

    if (enemyTurnRequestRef.current) {
      return;
    }

    enemyTurnRequestRef.current = true;
    setSelected(null);
    setHintCell(null);
    setHintMove(null);

    enemyThinkTimerRef.current = setTimeout(() => {
      void Promise.resolve(resolveEnemyTurn({
        sessionId,
        board: boardRef.current,
      }))
        .then((packet) => {
          if (!mountedRef.current) {
            return;
          }

          if (!packet || packet.runtimeSource !== 'server_packet') {
            showBonusBanner('Quái không tạo được lượt từ server');
            turnRef.current = 'player';
            setTurn('player');
            setTurnCycle(cycle => cycle + 1);
            return;
          }

          setPhase('busy');
          phaseRef.current = 'busy';

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

            for (const delta of packet.actorDeltas ?? []) {
              if (delta.side === 'player') {
                playerHpDelta += delta.hpDelta;
                playerManaDelta += delta.manaDelta;
                playerPowerDelta += delta.powerDelta;
                continue;
              }

              enemyHpDelta += delta.hpDelta;
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
        })
        .catch((error) => {
          console.warn('[BattleScreen] resolveEnemyTurn failed', error);
          if (mountedRef.current) {
            showBonusBanner('Lượt quái lỗi packet runtime');
            turnRef.current = 'player';
            setTurn('player');
            setTurnCycle(cycle => cycle + 1);
          }
        })
        .finally(() => {
          enemyTurnRequestRef.current = false;
        });
    }, 550);

    return () => {
      if (enemyThinkTimerRef.current) {
        clearTimeout(enemyThinkTimerRef.current);
        enemyThinkTimerRef.current = null;
      }

      enemyTurnRequestRef.current = false;
    };
  }, [
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    boardRef,
    charsRowHeight,
    charsTop,
    enemyTurnRequestRef,
    extraTurnsRef,
    flashExtraTurnsBadge,
    maxEHP,
    maxHP,
    maxMP,
    maxPow,
    monsterBaseLeft,
    monsterGroundOffset,
    monsterSize,
    mountedRef,
    panelLeft,
    panelTop,
    phase,
    phaseRef,
    playPlayerHitReaction,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    resolveEnemyTurn,
    result,
    sessionId,
    setActiveSkillCasts,
    setEnemyHP,
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
    turn,
    turnRef,
  ]);
};
