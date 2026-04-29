import type {
  MapMonsterRosterRequest,
  MapMonsterRosterEntry,
  MapMonsterRosterResponse,
  ResolveMapMonsterRoster,
} from './MapMonsterRoster.types';
import { SocketClient, type MapMonsterRosterPacket, type MapMonsterSpawnRecord } from '../../../network/SocketClient';
import { resolveMonsterSharedSheetFamily } from '../../battle';
import { resolveSideScrollMapSceneConfig } from './MapSceneConfig.registry';

const toHttpBaseUrl = (socketUrl: string): string => {
  try {
    const parsed = new URL(socketUrl);
    const protocol = parsed.protocol === 'wss:' ? 'https:' : 'http:';
    return `${protocol}//${parsed.host}`;
  } catch {
    return socketUrl;
  }
};

const clamp01 = (value: number): number => Math.max(0, Math.min(1, value));

const resolveSpawnRatio = (
  spawnStartRatio: number,
  spawnEndRatio: number,
  spawnInstanceIndex: number,
  spawnCount: number,
): number => {
  let start = clamp01(spawnStartRatio);
  let end = clamp01(spawnEndRatio);

  if (end < start) {
    [start, end] = [end, start];
  }

  if (spawnCount <= 1) {
    return start + ((end - start) * 0.5);
  }

  const step = (end - start) / Math.max(1, spawnCount - 1);
  const centeredOffset = step * 0.18;
  let ratio = start + (step * spawnInstanceIndex);

  if ((spawnInstanceIndex & 1) === 1) {
    ratio -= centeredOffset;
  } else {
    ratio += centeredOffset;
  }

  return clamp01(ratio);
};

const isServerDrivenPlacement = (record: MapMonsterSpawnRecord): boolean =>
  typeof record.surfaceId === 'string' &&
  record.surfaceId.length > 0 &&
  typeof record.patrolStartRatio === 'number' &&
  typeof record.patrolEndRatio === 'number' &&
  typeof record.spawnRatio === 'number' &&
  typeof record.moveSpeed === 'number';

const buildRosterEntry = (
  request: MapMonsterRosterRequest,
  record: MapMonsterSpawnRecord,
): MapMonsterRosterEntry | null => {
  let surfaceId: string;
  let patrolStartRatio: number;
  let patrolEndRatio: number;
  let spawnRatio: number;
  let moveSpeed: number;

  if (isServerDrivenPlacement(record)) {
    surfaceId = record.surfaceId as string;
    patrolStartRatio = record.patrolStartRatio as number;
    patrolEndRatio = record.patrolEndRatio as number;
    spawnRatio = record.spawnRatio as number;
    moveSpeed = record.moveSpeed as number;
  } else {
    const sceneConfig = resolveSideScrollMapSceneConfig(request.mapId, request.roomId);
    if (!sceneConfig || !sceneConfig.monsterSpawnGroups || sceneConfig.monsterSpawnGroups.length === 0) {
      return null;
    }

    const profile = sceneConfig.monsterSpawnGroups.find(
      (candidate) => candidate.spawnGroupKey === record.spawnGroupKey,
    );
    if (!profile) {
      return null;
    }

    surfaceId = profile.surfaceId;
    patrolStartRatio = profile.patrolStartRatio;
    patrolEndRatio = profile.patrolEndRatio;
    moveSpeed = profile.moveSpeed;
    const effectiveSpawnCount = Math.max(1, record.spawnCount);
    spawnRatio = resolveSpawnRatio(
      profile.spawnStartRatio,
      profile.spawnEndRatio,
      record.spawnInstanceIndex,
      effectiveSpawnCount,
    );
  }

  const spawnTemplateKey =
    typeof record.spawnTemplateKey === 'string' && record.spawnTemplateKey.length > 0
      ? record.spawnTemplateKey
      : `${record.spawnGroupKey}_runtime`;

  return {
    monsterKey: record.monsterKey,
    spawnGroupKey: record.spawnGroupKey,
    spawnInstanceIndex: record.spawnInstanceIndex,
    spawnTemplateKey,
    displayName: record.displayName,
    visualTypeByte: record.visualTypeByte,
    displayLevel: record.displayLevel,
    iqValue: record.iqValue,
    nameColorMode: record.nameColorMode,
    sharedSheetFamily: resolveMonsterSharedSheetFamily(record.visualTypeByte),
    surfaceId,
    patrolStartRatio,
    patrolEndRatio,
    spawnRatio,
    moveSpeed,
    assetCatalogId: record.assetCatalogId,
    framePaths: record.framePaths,
  };
};

const buildRosterEntries = (
  request: MapMonsterRosterRequest,
  packet: MapMonsterRosterPacket,
): MapMonsterRosterEntry[] => {
  const entries: MapMonsterRosterEntry[] = [];
  for (const record of packet.monsters) {
    const entry = buildRosterEntry(request, record);
    if (entry) {
      entries.push(entry);
    }
  }

  return entries;
};

export const applyMapMonsterRuntimePacket = (
  current: MapMonsterRosterEntry[],
  request: MapMonsterRosterRequest,
  packet: MapMonsterRosterPacket,
): MapMonsterRosterEntry[] => {
  if (packet.mapId !== request.mapId || packet.roomId !== request.roomId) {
    return current;
  }

  switch (packet.mode) {
    case 1: {
      if (packet.monsters.length === 0) {
        return current;
      }

      const removedKeys = new Set(packet.monsters.map((monster) => monster.monsterKey));
      return current.filter((entry) => !removedKeys.has(entry.monsterKey));
    }

    case 0: {
      const additions = buildRosterEntries(request, packet);
      if (additions.length === 0) {
        return current;
      }

      const byKey = new Map<string, MapMonsterRosterEntry>();
      for (const entry of current) {
        byKey.set(entry.monsterKey, entry);
      }
      for (const entry of additions) {
        byKey.set(entry.monsterKey, entry);
      }

      return Array.from(byKey.values());
    }

    case 3:
    default:
      return buildRosterEntries(request, packet);
  }
};

const fetchHttpRoster = async (
  baseUrl: string,
  request: MapMonsterRosterRequest,
  timeoutMs: number,
): Promise<MapMonsterRosterResponse | null> => {
  const controller = new AbortController();
  const timeout = setTimeout(() => controller.abort(), timeoutMs);

  try {
    const query = new URLSearchParams({
      mapId: request.mapId,
      roomId: String(request.roomId),
    });
    const response = await fetch(`${baseUrl}/map/monster-roster?${query.toString()}`, {
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

    return JSON.parse(rawBody) as MapMonsterRosterResponse;
  } catch {
    return null;
  } finally {
    clearTimeout(timeout);
  }
};

export const createMapMonsterRosterResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveMapMonsterRoster => {
  const baseUrl = toHttpBaseUrl(socketUrl);
  const client = SocketClient.getInstance();

  return async (
    request: MapMonsterRosterRequest,
  ): Promise<MapMonsterRosterResponse | null> => {
    try {
      const socketResult = await new Promise<MapMonsterRosterResponse | null>((resolve) => {
        let settled = false;
        const timer = setTimeout(() => {
          if (settled) return;
          settled = true;
          client.off('mapMonsterRoster', handleRoster);
          resolve(null);
        }, timeoutMs);

        const finish = (result: MapMonsterRosterResponse | null) => {
          if (settled) return;
          settled = true;
          clearTimeout(timer);
          client.off('mapMonsterRoster', handleRoster);
          resolve(result);
        };

        const handleRoster = (packet: MapMonsterRosterPacket) => {
          if (packet.mapId !== request.mapId || packet.roomId !== request.roomId || packet.mode !== 3) {
            return;
          }

          finish({
            mapId: request.mapId,
            roomId: request.roomId,
            encounters: applyMapMonsterRuntimePacket([], request, packet),
          });
        };

        client.on('mapMonsterRoster', handleRoster);
        client.joinMap(request.mapId, request.roomId);
      });

      if (socketResult) {
        return socketResult;
      }

      return await fetchHttpRoster(baseUrl, request, timeoutMs);
    } catch {
      return await fetchHttpRoster(baseUrl, request, timeoutMs);
    }
  };
};
