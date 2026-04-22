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

type SkillCellTargetLayout = {
  spanRows: number;
  spanCols: number;
};

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

const SKILL_CELL_TARGET_LAYOUTS: Partial<
  Record<BattleSkillRuntimePacket['familyCode'], SkillCellTargetLayout>
> = {
  // Java 1000 lands on the center of each 2x2 blast region even though the
  // packet target currently arrives as that region's top-left anchor.
  1000: { spanRows: 2, spanCols: 2 },
};

const clampCellTargetAnchor = (
  [row, col]: BattleCell,
  { spanRows, spanCols }: SkillCellTargetLayout,
): BattleCell => [
  Math.max(0, Math.min(row, BOARD_ROWS - spanRows)),
  Math.max(0, Math.min(col, BOARD_COLS - spanCols)),
];

const regionTargetToPoint = (
  cell: BattleCell,
  region: SkillCellTargetLayout,
  layout: Pick<BattleSkillPacketLayout, 'panelLeft' | 'panelTop'>,
): SkillTargetPoint => {
  const [anchorRow, anchorCol] = clampCellTargetAnchor(cell, region);
  const point = cellToTargetPoint([anchorRow, anchorCol], layout);

  return {
    row: cell[0],
    col: cell[1],
    x: point.x + ((region.spanCols - 1) * GEM_SIZE) / 2,
    y: point.y + ((region.spanRows - 1) * GEM_SIZE) / 2,
  };
};

const skillCellTargetToPoint = (
  familyCode: BattleSkillRuntimePacket['familyCode'],
  cell: BattleCell,
  layout: Pick<BattleSkillPacketLayout, 'panelLeft' | 'panelTop'>,
): SkillTargetPoint => {
  const region = SKILL_CELL_TARGET_LAYOUTS[familyCode];
  return region ? regionTargetToPoint(cell, region, layout) : cellToTargetPoint(cell, layout);
};

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
  const impactDelayMs = packet.impactDelayMs ?? skill.impactDelayMs;

  const boardMutationDelayMs = (() => {
    switch (packet.familyCode) {
      case 1000: {
        if (cellTargets.length === 0) return impactDelayMs;

        // Java 1000 fires the actor projectile first, then starts the follow-up
        // board volley. Delay the actual board mutation until the last visible
        // fireball has had time to reach its target.
        const volleyDelayMs = 72;
        return impactDelayMs + impactDelayMs + volleyDelayMs * (cellTargets.length - 1);
      }
      default:
        return impactDelayMs;
    }
  })();

  return {
    key: `${packet.castId}-${packet.familyCode}`,
    familyCode: packet.familyCode,
    startedAt: Date.now(),
    casterSide: packet.casterSide,
    sourceX: sourcePoint.x,
    sourceY: sourcePoint.y,
    actorTarget: packet.actorTarget
      ? getActorPoint(packet.actorTarget.side, packet.actorTarget.anchor, layout)
      : null,
    boardClearTargets: boardCells.map(cell => cellToTargetPoint(cell, layout)),
    cellTargets: cellTargets.map(cell => skillCellTargetToPoint(packet.familyCode, cell, layout)),
    runtimeSource: packet.runtimeSource,
    hitsActor: packet.impact.hitsActor,
    impactDelayMs,
    boardMutationDelayMs,
    durationMs: packet.durationMs ?? skill.totalMs,
  };
};

export const collectSkillPacketBoardKeys = (packet: BattleSkillRuntimePacket): Set<string> =>
  new Set(dedupeBoardCells(packet.boardMutation.cells).map(([row, col]) => toKey(row, col)));
