import type {
  BattleCell,
  BattleSide,
  BattleSkillActorAnchor,
  BattleSkillBoardMutationKind,
  BattleSkillPacketRequest,
  BattleSkillRuntimePacket,
  ResolveBattleSkillPacket,
} from './BattleScreen.types';

type ServerBattleSide = 'Player' | 'Enemy';
type ServerBattleSkillActorAnchor = 'Center' | 'Bottom';
type ServerBattleSkillBoardMutationKind = 'Clear' | 'Mark' | 'Helper' | 'None';

type ServerBattleCell = {
  row: number;
  col: number;
};

type ServerBattleSkillActorTarget = {
  side: ServerBattleSide;
  anchor: ServerBattleSkillActorAnchor;
};

type ServerBattleSkillRuntimePacket = {
  castId: string;
  familyCode: number;
  casterSide: ServerBattleSide;
  actorTarget: ServerBattleSkillActorTarget | null;
  boardMutation: {
    kind: ServerBattleSkillBoardMutationKind;
    cells: ServerBattleCell[];
    stateId?: number | null;
  };
  cellTargets: ServerBattleCell[];
  impact: {
    hitsActor: boolean;
    damage?: number | null;
    hitShakePx?: number | null;
  };
  impactDelayMs?: number | null;
  durationMs?: number | null;
};

const mapSide = (side: ServerBattleSide): BattleSide => (side === 'Enemy' ? 'enemy' : 'player');
const mapAnchor = (anchor: ServerBattleSkillActorAnchor): BattleSkillActorAnchor =>
  anchor === 'Bottom' ? 'bottom' : 'center';
const mapMutationKind = (kind: ServerBattleSkillBoardMutationKind): BattleSkillBoardMutationKind => {
  switch (kind) {
    case 'Clear':
      return 'clear';
    case 'Mark':
      return 'mark';
    case 'Helper':
      return 'helper';
    case 'None':
    default:
      return 'none';
  }
};

const mapCell = (cell: ServerBattleCell): BattleCell => [cell.row, cell.col];

const toHttpBaseUrl = (socketUrl: string): string => {
  try {
    const parsed = new URL(socketUrl);
    parsed.protocol = parsed.protocol === 'wss:' ? 'https:' : 'http:';
    parsed.pathname = '';
    parsed.search = '';
    parsed.hash = '';
    return parsed.toString().replace(/\/$/, '');
  } catch {
    return 'http://localhost:5102';
  }
};

export const createBattleSkillPacketResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveBattleSkillPacket => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleSkillPacketRequest): Promise<BattleSkillRuntimePacket | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const response = await fetch(`${baseUrl}/battle/skill-cast`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          familyCode: request.familyCode,
          casterSide: request.casterSide === 'enemy' ? 'Enemy' : 'Player',
          selectedRow: request.selectedCell[0],
          selectedCol: request.selectedCell[1],
          board: request.board,
        }),
        signal: controller.signal,
      });

      if (!response.ok) {
        return null;
      }

      if (response.status === 204) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      const packet = JSON.parse(rawBody) as ServerBattleSkillRuntimePacket;

      return {
        castId: packet.castId,
        familyCode: request.familyCode,
        runtimeSource: 'server_packet',
        casterSide: mapSide(packet.casterSide),
        actorTarget: packet.actorTarget
          ? {
            side: mapSide(packet.actorTarget.side),
            anchor: mapAnchor(packet.actorTarget.anchor),
          }
          : null,
        boardMutation: {
          kind: mapMutationKind(packet.boardMutation.kind),
          cells: packet.boardMutation.cells.map(mapCell),
          stateId: packet.boardMutation.stateId ?? null,
        },
        cellTargets: packet.cellTargets.map(mapCell),
        impact: {
          hitsActor: packet.impact.hitsActor,
          damage: packet.impact.damage ?? null,
          hitShakePx: packet.impact.hitShakePx ?? null,
        },
        impactDelayMs: packet.impactDelayMs ?? null,
        durationMs: packet.durationMs ?? null,
      };
    } catch (error) {
      console.warn('[BattleSkillPacketResolver] skill packet request failed', error);
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};
