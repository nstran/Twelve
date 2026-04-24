import type {
  BattleCell,
  BattleSide,
  BattleSkillActorAnchor,
  BattleSkillLevelSource,
  MonsterBattleBootstrapRequest,
  MonsterBattleBootstrapResponse,
  BattleSkillBoardMutationKind,
  BattleSkillPacketRequest,
  BattleSkillRuntimePacket,
  BattleEnemyTurnRequest,
  BattleEnemyMoveRequest,
  BattleEnemyMoveResponse,
  BattleEnemyTurnPlanRequest,
  BattleEnemyTurnPlanResponse,
  BattleSessionSyncRequest,
  BattleSessionSnapshotRequest,
  BattleSessionSnapshotResponse,
  BattleResultClaimRequest,
  BattleResultRewardResponse,
  ResolveMonsterBattleBootstrap,
  ResolvePvpBattleBootstrap,
  ResolvePvpChallengeApi,
  ResolvePvpOpponents,
  ResolveBattleResult,
  ResolveBattleSkillPacket,
  ResolveEnemyBattleMove,
  ResolveBattleSessionSync,
  ResolveBattleSessionSnapshot,
  ResolveEnemyBattleTurn,
  ResolveEnemyBattleTurnPlan,
} from './BattleScreen.types';
import { SocketClient, type MonsterBattleBootstrapSocketResponse } from '../../../network/SocketClient';

type ServerBattleSide = 'Player' | 'Enemy';
type ServerBattleSkillActorAnchor = 'Center' | 'Bottom';
type ServerBattleSkillBoardMutationKind = 'Clear' | 'Mark' | 'Helper' | 'None';
type ServerBattleSkillLevelSource = 'ServerAuthority' | 'ClientDebugRequest' | 'ServerFallback';

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
  actorDeltas?: Array<{
    side: ServerBattleSide;
    hpDelta?: number | null;
    manaDelta?: number | null;
    powerDelta?: number | null;
  }> | null;
  turnDelta?: {
    remainingTurnsDelta?: number | null;
    timeLeftSecondsDelta?: number | null;
  } | null;
  skillLevelSource?: ServerBattleSkillLevelSource | null;
  grantsExtraTurn?: boolean | null;
  extraTurnChancePercent?: number | null;
  impactDelayMs?: number | null;
  durationMs?: number | null;
};

type ServerBattleEnemyMoveResponse = BattleEnemyMoveResponse;
type ServerBattleEnemyTurnPlanKind = 'Move' | 'Skill' | 'Pass';
type ServerBattleEnemyTurnPlanResponse = {
  action: ServerBattleEnemyTurnPlanKind;
  move?: BattleEnemyMoveResponse['move'] | null;
  skillPacket?: ServerBattleSkillRuntimePacket | null;
};

type ServerBattleSessionSnapshotResponse = Omit<BattleSessionSnapshotResponse, 'activeTurn' | 'kind'> & {
  activeTurn: ServerBattleSide;
  kind: 'Monster' | 'PvpShadow';
};

type ServerMonsterBattleBootstrapResponse = Omit<MonsterBattleBootstrapResponse, 'initialTurnSide'> & {
  initialTurnSide: ServerBattleSide;
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
const mapSkillLevelSource = (source?: ServerBattleSkillLevelSource | null): BattleSkillLevelSource => {
  switch (source) {
    case 'ServerAuthority':
      return 'server_authority';
    case 'ClientDebugRequest':
      return 'client_debug_request';
    case 'ServerFallback':
    default:
      return 'server_fallback';
  }
};

const mapCell = (cell: ServerBattleCell): BattleCell => [cell.row, cell.col];

const mapBootstrapResponse = (
  response: ServerMonsterBattleBootstrapResponse,
): MonsterBattleBootstrapResponse => ({
  ...response,
  initialTurnSide: mapSide(response.initialTurnSide),
});

const mapRuntimePacket = (
  packet: ServerBattleSkillRuntimePacket,
  familyCodeOverride?: BattleSkillRuntimePacket['familyCode'],
): BattleSkillRuntimePacket => ({
  castId: packet.castId,
  familyCode: (familyCodeOverride ?? packet.familyCode) as BattleSkillRuntimePacket['familyCode'],
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
  actorDeltas: packet.actorDeltas?.map(delta => ({
    side: mapSide(delta.side),
    hpDelta: delta.hpDelta ?? 0,
    manaDelta: delta.manaDelta ?? 0,
    powerDelta: delta.powerDelta ?? 0,
  })) ?? null,
  turnDelta: packet.turnDelta
    ? {
      remainingTurnsDelta: packet.turnDelta.remainingTurnsDelta ?? 0,
      timeLeftSecondsDelta: packet.turnDelta.timeLeftSecondsDelta ?? 0,
    }
    : null,
  skillLevelSource: mapSkillLevelSource(packet.skillLevelSource),
  grantsExtraTurn: packet.grantsExtraTurn ?? null,
  extraTurnChancePercent: packet.extraTurnChancePercent ?? null,
  impactDelayMs: packet.impactDelayMs ?? null,
  durationMs: packet.durationMs ?? null,
});

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
          sessionId: request.sessionId,
          familyCode: request.familyCode,
          casterSide: request.casterSide === 'enemy' ? 'Enemy' : 'Player',
          selectedRow: request.selectedCell[0],
          selectedCol: request.selectedCell[1],
          debugSkillLevel: request.debugSkillLevel ?? null,
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

      return mapRuntimePacket(packet, request.familyCode);
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createEnemyBattleTurnResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveEnemyBattleTurn => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleEnemyTurnRequest): Promise<BattleSkillRuntimePacket | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const response = await fetch(`${baseUrl}/battle/enemy-turn`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(request),
        signal: controller.signal,
      });

      if (!response.ok || response.status === 204) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      const packet = JSON.parse(rawBody) as ServerBattleSkillRuntimePacket;

      return mapRuntimePacket(packet);
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createEnemyBattleTurnPlanResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveEnemyBattleTurnPlan => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleEnemyTurnPlanRequest): Promise<BattleEnemyTurnPlanResponse | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const response = await fetch(`${baseUrl}/battle/enemy-turn-plan`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(request),
        signal: controller.signal,
      });

      if (!response.ok || response.status === 204) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      const plan = JSON.parse(rawBody) as ServerBattleEnemyTurnPlanResponse;
      const mappedPlan: BattleEnemyTurnPlanResponse = {
        action:
          plan.action === 'Skill'
            ? 'skill'
            : plan.action === 'Move'
              ? 'move'
              : 'pass',
        move: plan.move ?? null,
        skillPacket: plan.skillPacket ? mapRuntimePacket(plan.skillPacket) : null,
      };
      return mappedPlan;
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createEnemyBattleMoveResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveEnemyBattleMove => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleEnemyMoveRequest): Promise<BattleEnemyMoveResponse | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const response = await fetch(`${baseUrl}/battle/enemy-move`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(request),
        signal: controller.signal,
      });

      if (!response.ok || response.status === 204) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      return JSON.parse(rawBody) as ServerBattleEnemyMoveResponse;
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createBattleSessionSyncResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveBattleSessionSync => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleSessionSyncRequest): Promise<void> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      await fetch(`${baseUrl}/battle/session-sync`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          sessionId: request.sessionId,
          board: request.board,
          activeTurn: request.activeTurn === 'enemy' ? 'Enemy' : 'Player',
          playerCurrentHp: request.playerCurrentHp,
          playerCurrentMp: request.playerCurrentMp,
          playerCurrentPower: request.playerCurrentPower,
          enemyCurrentHp: request.enemyCurrentHp,
          enemyCurrentMp: request.enemyCurrentMp,
          enemyCurrentPower: request.enemyCurrentPower,
        }),
        signal: controller.signal,
      });
    } catch {
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createBattleSessionSnapshotResolver = (
  socketUrl: string,
  timeoutMs = 2000,
): ResolveBattleSessionSnapshot => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleSessionSnapshotRequest): Promise<BattleSessionSnapshotResponse | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const url = `${baseUrl}/battle/session-snapshot?sessionId=${encodeURIComponent(request.sessionId)}`;
      const response = await fetch(url, { method: 'GET', signal: controller.signal });
      if (!response.ok) {
        return null;
      }

      const data = await response.json() as ServerBattleSessionSnapshotResponse;
      return {
        ...data,
        activeTurn: mapSide(data.activeTurn),
        kind: data.kind === 'PvpShadow' ? 'pvpShadow' : 'monster',
      };
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

type ServerBattleResultKind = 'Victory' | 'Defeat';
type ServerBattleResultRewardResponse = Omit<BattleResultRewardResponse, 'result'> & {
  result: ServerBattleResultKind;
};

export const createBattleResultResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveBattleResult => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request: BattleResultClaimRequest): Promise<BattleResultRewardResponse | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);
    try {
      const response = await fetch(`${baseUrl}/battle/result`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          sessionId: request.sessionId,
          result: request.result === 'victory' ? 'Victory' : 'Defeat',
          playerCurrentHp: request.playerCurrentHp,
          playerCurrentMp: request.playerCurrentMp,
          playerCurrentPower: request.playerCurrentPower,
        }),
        signal: controller.signal,
      });

      if (!response.ok) {
        return null;
      }

      const data = await response.json() as ServerBattleResultRewardResponse;
      return {
        ...data,
        result: data.result === 'Victory' ? 'victory' : 'defeat',
      };
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createMonsterBattleBootstrapResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveMonsterBattleBootstrap => {
  const baseUrl = toHttpBaseUrl(socketUrl);
  const client = SocketClient.getInstance();

  return async (
    request: MonsterBattleBootstrapRequest,
  ): Promise<MonsterBattleBootstrapResponse | null> => {
    try {
      const socketResult = await new Promise<MonsterBattleBootstrapResponse | null>((resolve) => {
        let settled = false;
        const timer = setTimeout(() => {
          if (settled) return;
          settled = true;
          client.off('monsterBootstrapResponse', handleResponse);
          resolve(null);
        }, timeoutMs);

        const finish = (result: MonsterBattleBootstrapResponse | null) => {
          if (settled) return;
          settled = true;
          clearTimeout(timer);
          client.off('monsterBootstrapResponse', handleResponse);
          resolve(result);
        };

        const handleResponse = (
          envelope: MonsterBattleBootstrapSocketResponse<ServerMonsterBattleBootstrapResponse>,
        ) => {
          if (!envelope.ok || !envelope.data) {
            finish(null);
            return;
          }

          finish(mapBootstrapResponse(envelope.data));
        };

        client.on('monsterBootstrapResponse', handleResponse);
        client.requestMonsterBootstrap(
          request.mapId,
          request.roomId,
          request.monsterKey,
          request.initialTurnSide,
        );
      });

      if (socketResult) {
        return socketResult;
      }

      const controller = new AbortController();
      const timeout = setTimeout(() => controller.abort(), timeoutMs);
      try {
        const response = await fetch(`${baseUrl}/battle/monster-bootstrap`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            mapId: request.mapId,
            roomId: request.roomId,
            monsterKey: request.monsterKey,
            initialTurnSide: request.initialTurnSide === 'enemy' ? 'Enemy' : 'Player',
          }),
          signal: controller.signal,
        });

        if (!response.ok) {
          return null;
        }

        const rawBody = await response.text();
        if (!rawBody.trim()) {
          return null;
        }

        const responseBody = JSON.parse(rawBody) as ServerMonsterBattleBootstrapResponse;
        return mapBootstrapResponse(responseBody);
      } finally {
        clearTimeout(timeout);
      }
    } catch {
      return null;
    }
  };
};

export const createPvpOpponentListResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolvePvpOpponents => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request) => {
    if (!request.username.trim()) {
      return null;
    }

    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);
    try {
      if (request.registerPresence) {
        const enterResponse = await fetch(`${baseUrl}/pvp/arena/enter`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username: request.username }),
          signal: controller.signal,
        });

        if (!enterResponse.ok) {
          return null;
        }
      }

      const query = new URLSearchParams({ username: request.username });
      const response = await fetch(`${baseUrl}/pvp/opponents?${query.toString()}`, {
        method: 'GET',
        signal: controller.signal,
      });

      if (!response.ok) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      return JSON.parse(rawBody);
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createPvpBattleBootstrapResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolvePvpBattleBootstrap => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (request) => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);

    try {
      const response = await fetch(`${baseUrl}/pvp/bootstrap`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: request.username,
          targetUsername: request.targetUsername,
          initialTurnSide: request.initialTurnSide === 'enemy' ? 'Enemy' : 'Player',
          stake: request.stake ?? 0,
          allowSpectators: request.allowSpectators ?? true,
          oneWay: request.oneWay ?? false,
          disableSpecialSkills: request.disableSpecialSkills ?? false,
        }),
        signal: controller.signal,
      });

      if (!response.ok) {
        return null;
      }

      const rawBody = await response.text();
      if (!rawBody.trim()) {
        return null;
      }

      const responseBody = JSON.parse(rawBody) as ServerMonsterBattleBootstrapResponse;
      return mapBootstrapResponse(responseBody);
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };
};

export const createPvpChallengeApi = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolvePvpChallengeApi => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  const fetchJson = async <T,>(path: string, init: RequestInit): Promise<T | null> => {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), timeoutMs);
    try {
      const response = await fetch(`${baseUrl}${path}`, { ...init, signal: controller.signal });
      if (!response.ok) return null;
      const rawBody = await response.text();
      if (!rawBody.trim()) return null;
      return JSON.parse(rawBody) as T;
    } catch {
      return null;
    } finally {
      clearTimeout(timeout);
    }
  };

  return {
    create: (request) => fetchJson('/pvp/challenges', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: request.username,
        targetUsername: request.targetUsername,
        stake: request.stake ?? 0,
      }),
    }),
    list: (username) => fetchJson('/pvp/challenges/inbox', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username }),
    }),
    status: async (ticketId, username) => {
      const response = await fetchJson<any>(`/pvp/challenges/${ticketId}/status`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username }),
      });
      return response?.bootstrap
        ? { ...response, bootstrap: mapBootstrapResponse(response.bootstrap) }
        : response;
    },
    accept: async (request) => {
      const response = await fetchJson<any>(`/pvp/challenges/${request.ticketId}/accept`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: request.username }),
      });
      return response?.bootstrap
        ? { ...response, bootstrap: mapBootstrapResponse(response.bootstrap) }
        : response;
    },
    decline: (request) => fetchJson(`/pvp/challenges/${request.ticketId}/decline`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: request.username }),
    }),
    cancel: (request) => fetchJson(`/pvp/challenges/${request.ticketId}/cancel`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: request.username }),
    }),
  };
};
