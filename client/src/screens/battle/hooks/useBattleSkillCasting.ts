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

interface UseBattleSkillCastingArgs {
  applyServerPacketBoardMutation: (packet: BattleSkillRuntimePacket) => void;
  applyServerPacketMarkCell: (cell: BattleCell, stateId: number) => void;
  boardRef: MutableRefObject<Board>;
  charsRowHeight: number;
  charsTop: number;
  cursorCell: BattleCell;
  flashExtraTurnsBadge: (turns: number) => void;
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
  processMatchesRef: MutableRefObject<ReturnType<typeof useBattleMatchFlow>['processMatches'] | null>;
  resolveSkillPacket?: BattleScreenProps['resolveSkillPacket'];
  result: BattleResult | null;
  setActiveSkillCasts: Dispatch<SetStateAction<ActiveBattleSkillCast[]>>;
  setEnemyHP: Dispatch<SetStateAction<number>>;
  setHintCell: Dispatch<SetStateAction<BattleCell | null>>;
  setHintMove: Dispatch<SetStateAction<MoveSpec | null>>;
  setMenuVisible: Dispatch<SetStateAction<boolean>>;
  setPhase: Dispatch<SetStateAction<BattlePhase>>;
  setPlayerAction: Dispatch<SetStateAction<CharacterAction>>;
  setPlayerActionFrameIndex: Dispatch<SetStateAction<number | null>>;
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
  boardRef,
  charsRowHeight,
  charsTop,
  cursorCell,
  flashExtraTurnsBadge,
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
  processMatchesRef,
  resolveSkillPacket,
  result,
  setActiveSkillCasts,
  setEnemyHP,
  setHintCell,
  setHintMove,
  setMenuVisible,
  setPhase,
  setPlayerAction,
  setPlayerActionFrameIndex,
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
        familyCode,
        casterSide: 'player',
        board: boardRef.current,
        selectedCell: cursorCell,
        // Battle mode currently opens the full skill sandbox without the
        // character skill tree wired in, so request the reconstructed
        // max-level packet shape until real per-skill levels are available.
        skillLevel: DEFAULT_BATTLE_SKILL_LEVEL,
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
      setPlayerAction('attack');
      setPlayerActionFrameIndex(0);

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

        if ((packet.impact.damage ?? 0) > 0) {
          showDamagePopup('enemy', packet.impact.damage ?? 0);
          setEnemyHP(hp => {
            const next = Math.max(0, hp - (packet.impact.damage ?? 0));
            if (next === 0 && phaseRef.current !== 'over') {
              phaseRef.current = 'over';
              setPhase('over');
              setResult('victory');
            }
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
        if (packet.grantsExtraTurn) {
          flashExtraTurnsBadge(1);
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

      skillCastTimersRef.current.push(...boardMutationTimers, impactTimer, finishTimer);
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
    boardRef,
    charsRowHeight,
    charsTop,
    cursorCell,
    flashExtraTurnsBadge,
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
    processMatchesRef,
    resolveSkillPacket,
    result,
    setActiveSkillCasts,
    setEnemyHP,
    setHintCell,
    setHintMove,
    setMenuVisible,
    setPhase,
    setPlayerAction,
    setPlayerActionFrameIndex,
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
