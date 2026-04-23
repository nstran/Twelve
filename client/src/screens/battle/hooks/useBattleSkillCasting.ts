import { useCallback, type Dispatch, type MutableRefObject, type SetStateAction } from 'react';
import type { CharacterAction } from '../../../engine/character';
import {
  BATTLE_SKILLS,
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
  type SkillFamilyCode,
} from '../core';
import type { useBattleMatchFlow } from './useBattleMatchFlow';

const JAVA_BATTLE_TICK_MS = 40;
const DEFAULT_BATTLE_SKILL_LEVEL = 12;
const JAVA_SKILL_ATTACK_FRAME_2_TICKS = 6;
const JAVA_SKILL_ATTACK_IMPACT_TICKS = 11;
const JAVA_SKILL_ATTACK_FRAME_4_TICKS = 16;
const JAVA_SKILL_ATTACK_RESET_TICKS = 20;

interface UseBattleSkillCastingArgs {
  applyServerPacketBoardMutation: (packet: BattleSkillRuntimePacket) => void;
  applyServerPacketMarkCell: (cell: BattleCell, stateId: number) => void;
  battleSessionId: string;
  boardRef: MutableRefObject<Board>;
  charsRowHeight: number;
  charsTop: number;
  cursorCell: BattleCell;
  extraTurnsRef: MutableRefObject<number>;
  flashExtraTurnsBadge: (turns: number) => void;
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
  playEnemySkillImpact: (shakePx: number) => void;
  playerBaseLeft: number;
  playerSize: { w: number; h: number; groundOffset?: number };
  pendingVictoryRef: MutableRefObject<boolean>;
  processMatchesRef: MutableRefObject<ReturnType<typeof useBattleMatchFlow>['processMatches'] | null>;
  resolveSkillPacket?: BattleScreenProps['resolveSkillPacket'];
  result: BattleResult | null;
  setActiveSkillCasts: Dispatch<SetStateAction<ActiveBattleSkillCast[]>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setMenuVisible: Dispatch<SetStateAction<boolean>>;
  setExtraTurns: Dispatch<SetStateAction<number>>;
  setMana: Dispatch<SetStateAction<number>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setPlayerAction: Dispatch<SetStateAction<CharacterAction>>;
  setPlayerActionFrameIndex: Dispatch<SetStateAction<number | null>>;
  setPlayerHP: Dispatch<SetStateAction<number>>;
  setPlayerRetreatPose: Dispatch<SetStateAction<boolean>>;
  setPower: Dispatch<SetStateAction<number>>;
  setResult: Dispatch<SetStateAction<BattleResult | null>>;
  setSelected: Dispatch<SetStateAction<BattleCell | null>>;
  setSelectedSkillFamily: Dispatch<SetStateAction<SkillFamilyCode | null>>;
  setSkillPanelVisible: Dispatch<SetStateAction<boolean>>;
  setTurn: Dispatch<SetStateAction<BattleTurn>>;
  setTurnCycle: Dispatch<SetStateAction<number>>;
  showBonusBanner: (msg: string) => void;
  showDamagePopup: (side: 'player' | 'enemy', amount: number) => void;
  skillCastTimersRef: MutableRefObject<ReturnType<typeof setTimeout>[]>;
  skillPacketRequestRef: MutableRefObject<boolean>;
  turn: BattleTurn;
  turnRef: MutableRefObject<BattleTurn>;
}

export const useBattleSkillCasting = ({
  applyServerPacketBoardMutation,
  applyServerPacketMarkCell,
  battleSessionId,
  boardRef,
  charsRowHeight,
  charsTop,
  cursorCell,
  extraTurnsRef,
  flashExtraTurnsBadge,
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
  playEnemySkillImpact,
  playerBaseLeft,
  playerSize,
  pendingVictoryRef,
  processMatchesRef,
  resolveSkillPacket,
  result,
  setActiveSkillCasts,
  setEnemyHP,
  setExtraTurns,
  setHintCell,
  setHintMove,
  setMana,
  setMenuVisible,
  setPhase,
  setPlayerAction,
  setPlayerActionFrameIndex,
  setPlayerHP,
  setPlayerRetreatPose,
  setPower,
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
}: UseBattleSkillCastingArgs) => {
  const applyPacketActorDeltas = useCallback((packet: BattleSkillRuntimePacket) => {
    if (!packet.actorDeltas || packet.actorDeltas.length === 0) {
      return {
        hasAnyDelta: false,
        appliedEnemyHpDelta: false,
      };
    }

    let hasAnyDelta = false;
    let appliedEnemyHpDelta = false;
    let enemyHpDelta = 0;
    let playerHpDelta = 0;
    let playerManaDelta = 0;
    let playerPowerDelta = 0;

    for (const delta of packet.actorDeltas) {
      if (delta.side === 'enemy') {
        enemyHpDelta += delta.hpDelta;
        hasAnyDelta = true;
        continue;
      }

      playerHpDelta += delta.hpDelta;
      playerManaDelta += delta.manaDelta;
      playerPowerDelta += delta.powerDelta;
      hasAnyDelta = true;
    }

    if (enemyHpDelta < 0) {
      showDamagePopup('enemy', Math.abs(enemyHpDelta));
    }

    if (enemyHpDelta !== 0) {
      appliedEnemyHpDelta = true;
      setEnemyHP(hp => {
        const next = Math.max(0, hp + enemyHpDelta);
        if (next === 0) {
          pendingVictoryRef.current = true;
        }

        return next;
      });
    }

    if (playerHpDelta !== 0) {
      setPlayerHP(hp => Math.max(0, Math.min(maxHP, hp + playerHpDelta)));
    }

    if (playerManaDelta !== 0) {
      setMana(value => Math.max(0, Math.min(maxMP, value + playerManaDelta)));
    }

    if (playerPowerDelta !== 0) {
      setPower(value => Math.max(0, Math.min(maxPow, value + playerPowerDelta)));
    }

    return {
      hasAnyDelta,
      appliedEnemyHpDelta,
    };
  }, [
    maxHP,
    maxMP,
    maxPow,
    pendingVictoryRef,
    setEnemyHP,
    setMana,
    setPlayerHP,
    setPower,
    showDamagePopup,
  ]);

  const handleSkillCast = useCallback(async (familyCode: SkillFamilyCode) => {
    const skill = BATTLE_SKILLS[familyCode];
    if (phase !== 'idle' || turn !== 'player' || result !== null || skillPacketRequestRef.current) return;

    setSelectedSkillFamily(familyCode);
    if (!resolveSkillPacket) {
      showBonusBanner(`Skill ${skill.familyCode} tạm khóa: chờ packet server để render đúng Java`);
      return;
    }

    skillPacketRequestRef.current = true;

    try {
      const packet = await resolveSkillPacket({
        sessionId: battleSessionId,
        familyCode,
        casterSide: 'player',
        board: boardRef.current,
        selectedCell: cursorCell,
        // Battle mode currently opens the full skill sandbox without the
        // character skill tree wired in, so request the reconstructed
        // max-level packet shape until real per-skill levels are available.
        debugSkillLevel: DEFAULT_BATTLE_SKILL_LEVEL,
      });

      if (!mountedRef.current) return;
      if (!packet || packet.runtimeSource !== 'server_packet' || packet.familyCode !== familyCode) {
        showBonusBanner(`Skill ${skill.familyCode} chưa có packet hợp lệ từ server`);
        return;
      }

      setSkillPanelVisible(false);
      setMenuVisible(false);
      setSelected(null);
      setHintCell(null);
      setHintMove(null);
      setPhase('busy');
      phaseRef.current = 'busy';
      setPlayerRetreatPose(false);
      setPlayerAction('attack');
      setPlayerActionFrameIndex(0);

      const actorFrameTimers = [
        setTimeout(() => {
          if (!mountedRef.current) return;
          setPlayerActionFrameIndex(1);
        }, JAVA_SKILL_ATTACK_FRAME_2_TICKS * JAVA_BATTLE_TICK_MS),
        setTimeout(() => {
          if (!mountedRef.current) return;
          setPlayerActionFrameIndex(2);
        }, JAVA_SKILL_ATTACK_IMPACT_TICKS * JAVA_BATTLE_TICK_MS),
        setTimeout(() => {
          if (!mountedRef.current) return;
          setPlayerActionFrameIndex(3);
        }, JAVA_SKILL_ATTACK_FRAME_4_TICKS * JAVA_BATTLE_TICK_MS),
        setTimeout(() => {
          if (!mountedRef.current) return;
          setPlayerAction('idle');
          setPlayerActionFrameIndex(null);
        }, JAVA_SKILL_ATTACK_RESET_TICKS * JAVA_BATTLE_TICK_MS),
      ];

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
            if (!mountedRef.current) return;
            applyServerPacketMarkCell(cell, packet.boardMutation.stateId ?? 10);
          }, cast.boardMutationDelayMs + index * 4 * JAVA_BATTLE_TICK_MS))
          : [setTimeout(() => {
            if (!mountedRef.current) return;
            applyServerPacketBoardMutation(packet);
          }, cast.boardMutationDelayMs)];

      const impactTimer = setTimeout(() => {
        if (!mountedRef.current) return;

        if (packet.impact.hitsActor) {
          playEnemySkillImpact(packet.impact.hitShakePx ?? skill.hitShakePx);
        }

        const deltaApplication = applyPacketActorDeltas(packet);
        if (!deltaApplication.appliedEnemyHpDelta && (packet.impact.damage ?? 0) > 0) {
          showDamagePopup('enemy', packet.impact.damage ?? 0);
          setEnemyHP(hp => {
            const next = Math.max(0, hp - (packet.impact.damage ?? 0));
            if (next === 0) pendingVictoryRef.current = true;
            return next;
          });
        }
      }, cast.impactDelayMs);

      const finishTimer = setTimeout(() => {
        if (!mountedRef.current) return;
        setActiveSkillCasts(prev => prev.filter(item => item.key !== cast.key));
        setPlayerAction('idle');
        setPlayerActionFrameIndex(null);
        if (phaseRef.current === 'over') return;
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
        if (pendingVictoryRef.current) {
          pendingVictoryRef.current = false;
          phaseRef.current = 'over';
          setPhase('over');
          setResult('victory');
          return;
        }
        const remainingTurnsDelta = Math.max(
          0,
          packet.turnDelta?.remainingTurnsDelta ?? (packet.grantsExtraTurn ? 1 : 0),
        );
        if (remainingTurnsDelta > 0) {
          // Skill-granted extra turn is consumed immediately by keeping the
          // current side's turn. Only bank the remainder if the server ever
          // returns more than one additional turn in a single cast.
          const bankedExtraTurns = Math.max(0, remainingTurnsDelta - 1);
          extraTurnsRef.current = bankedExtraTurns;
          setExtraTurns(bankedExtraTurns);
          flashExtraTurnsBadge(remainingTurnsDelta);
          turnRef.current = 'player';
          setTurn('player');
          setTurnCycle(cycle => cycle + 1);
          phaseRef.current = 'idle';
          setPhase('idle');
          return;
        }
        if (phaseRef.current !== 'busy') return;
        turnRef.current = 'monster';
        setTurn('monster');
        setTurnCycle(cycle => cycle + 1);
        phaseRef.current = 'idle';
        setPhase('idle');
      }, cast.durationMs);

      skillCastTimersRef.current.push(...actorFrameTimers, ...boardMutationTimers, impactTimer, finishTimer);
    } catch (error) {
      console.warn('[BattleScreen] resolveSkillPacket failed', error);
      if (mountedRef.current) {
        showBonusBanner(`Skill ${skill.familyCode} lỗi packet runtime`);
      }
    } finally {
      skillPacketRequestRef.current = false;
    }
  }, [
    applyServerPacketBoardMutation,
    applyServerPacketMarkCell,
    applyPacketActorDeltas,
    battleSessionId,
    boardRef,
    charsRowHeight,
    charsTop,
    cursorCell,
    extraTurnsRef,
    flashExtraTurnsBadge,
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
    pendingVictoryRef,
    playEnemySkillImpact,
    playerBaseLeft,
    playerSize,
    processMatchesRef,
    resolveSkillPacket,
    result,
    setActiveSkillCasts,
    setEnemyHP,
    setExtraTurns,
    setHintCell,
    setHintMove,
    setMana,
    setMenuVisible,
    setPhase,
    setPlayerAction,
    setPlayerActionFrameIndex,
    setPlayerHP,
    setPlayerRetreatPose,
    setPower,
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
  ]);

  return { handleSkillCast };
};
