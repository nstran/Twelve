import { BOARD_LEFT, BOARD_TOP } from './BattleScreen.layout';
import { BATTLE_SKILLS } from './BattleScreen.skills';
import { BOARD_COLS, BOARD_ROWS, GEM_SIZE } from './BattleScreen.styles';
import type {
  ActiveBattleSkillCast,
  BattleCell,
  BattleSide,
  BattleSkillActorAnchor,
  BattleSkillRuntimePacket,
  ScreenPoint,
  SkillTargetPoint,
} from './BattleScreen.types';

type ActorSpriteBox = {
  w: number;
  h: number;
  groundOffset?: number;
};

interface BattleSkillPacketLayout {
  panelLeft: number;
  panelTop: number;
  charsTop: number;
  charsRowHeight: number;
  playerBaseLeft: number;
  monsterBaseLeft: number;
  playerSize: ActorSpriteBox;
  monsterSize: ActorSpriteBox;
  monsterGroundOffset: number;
}

const toKey = (row: number, col: number) => `${row},${col}`;

const clampBoardCell = ([row, col]: BattleCell): BattleCell | null => {
  if (row < 0 || row >= BOARD_ROWS || col < 0 || col >= BOARD_COLS) return null;
  return [row, col];
};

const dedupeBoardCells = (cells: BattleCell[]): BattleCell[] =>
  Array.from(
    new Map(
      cells
        .map(clampBoardCell)
        .filter((cell): cell is BattleCell => cell !== null)
        .map(cell => [toKey(cell[0], cell[1]), cell]),
    ).values(),
  );

const cellToTargetPoint = (
  [row, col]: BattleCell,
  layout: Pick<BattleSkillPacketLayout, 'panelLeft' | 'panelTop'>,
): SkillTargetPoint => ({
  row,
  col,
  x: layout.panelLeft + BOARD_LEFT + col * GEM_SIZE + GEM_SIZE / 2,
  y: layout.panelTop + BOARD_TOP + row * GEM_SIZE + GEM_SIZE / 2,
});

const getActorPoint = (
  side: BattleSide,
  anchor: BattleSkillActorAnchor,
  layout: BattleSkillPacketLayout,
): ScreenPoint => {
  if (side === 'player') {
    const baseX = layout.panelLeft + layout.playerBaseLeft;
    const groundY = layout.charsTop + layout.charsRowHeight - (layout.playerSize.groundOffset ?? 0);
    return anchor === 'bottom'
      ? {
        x: baseX + layout.playerSize.w * 0.7,
        y: groundY,
      }
      : {
        x: baseX + layout.playerSize.w * 0.55,
        y: groundY - layout.playerSize.h * 0.55,
      };
  }

  const baseX = layout.panelLeft + layout.monsterBaseLeft;
  const groundY = layout.charsTop + layout.charsRowHeight - layout.monsterGroundOffset;
  return anchor === 'bottom'
    ? {
      x: baseX + layout.monsterSize.w * 0.42,
      y: groundY,
    }
    : {
      x: baseX + layout.monsterSize.w * 0.42,
      y: groundY - layout.monsterSize.h * 0.52,
    };
};

export const normalizeJavaBoardCell = (javaRow: number, javaCol: number): BattleCell | null =>
  clampBoardCell([javaRow - 2, javaCol]);

export const buildActiveBattleSkillCastFromPacket = (
  packet: BattleSkillRuntimePacket,
  layout: BattleSkillPacketLayout,
): ActiveBattleSkillCast => {
  const skill = BATTLE_SKILLS[packet.familyCode];
  const boardCells = dedupeBoardCells(packet.boardMutation.cells);
  const cellTargets = dedupeBoardCells(packet.cellTargets);
  const sourcePoint = getActorPoint(packet.casterSide, 'center', layout);

  return {
    key: `${packet.castId}-${packet.familyCode}`,
    familyCode: packet.familyCode,
    startedAt: Date.now(),
    sourceX: sourcePoint.x,
    sourceY: sourcePoint.y,
    actorTarget: packet.actorTarget
      ? getActorPoint(packet.actorTarget.side, packet.actorTarget.anchor, layout)
      : null,
    boardClearTargets: boardCells.map(cell => cellToTargetPoint(cell, layout)),
    cellTargets: cellTargets.map(cell => cellToTargetPoint(cell, layout)),
    runtimeSource: packet.runtimeSource,
    hitsActor: packet.impact.hitsActor,
    impactDelayMs: packet.impactDelayMs ?? skill.impactDelayMs,
    durationMs: packet.durationMs ?? skill.totalMs,
  };
};

export const collectSkillPacketBoardKeys = (packet: BattleSkillRuntimePacket): Set<string> =>
  new Set(dedupeBoardCells(packet.boardMutation.cells).map(([row, col]) => toKey(row, col)));
