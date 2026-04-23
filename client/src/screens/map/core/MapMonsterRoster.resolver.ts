import type {
  MapMonsterRosterRequest,
  MapMonsterRosterEntry,
  MapMonsterRosterResponse,
  ResolveMapMonsterRoster,
} from './MapMonsterRoster.types';
import { SocketClient, type MapMonsterRosterPacket } from '../../../network/SocketClient';
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

const buildMonsterKey = (spawnGroupKey: string, instanceNumber: number): string =>
  `${spawnGroupKey}:${instanceNumber.toString().padStart(3, '0')}`.toUpperCase();

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

const mapPacketToRoster = (
  request: MapMonsterRosterRequest,
  packet: MapMonsterRosterPacket,
): MapMonsterRosterResponse | null => {
  const sceneConfig = resolveSideScrollMapSceneConfig(request.mapId, request.roomId);
  if (!sceneConfig || !sceneConfig.monsterSpawnGroups || sceneConfig.monsterSpawnGroups.length === 0) {
    return null;
  }

  const encounters: MapMonsterRosterEntry[] = [];
  for (const packetMonster of packet.monsters) {
    const profile = sceneConfig.monsterSpawnGroups.find(
      (candidate) => candidate.spawnGroupKey === packetMonster.monsterKey,
    );

    if (!profile) {
      continue;
    }

    const spawnCount = Math.max(0, packetMonster.spawnCount);
    for (let i = 0; i < spawnCount; i++) {
      encounters.push({
        monsterKey: buildMonsterKey(packetMonster.monsterKey, i + 1),
        spawnGroupKey: packetMonster.monsterKey,
        spawnInstanceIndex: i,
        spawnTemplateKey: `${packetMonster.monsterKey}_runtime`,
        displayName: packetMonster.displayName,
        visualTypeByte: packetMonster.visualTypeByte,
        displayLevel: packetMonster.displayLevel,
        iqValue: packetMonster.iqValue,
        nameColorMode: packetMonster.nameColorMode,
        sharedSheetFamily: resolveMonsterSharedSheetFamily(packetMonster.visualTypeByte),
        surfaceId: profile.surfaceId,
        patrolStartRatio: profile.patrolStartRatio,
        patrolEndRatio: profile.patrolEndRatio,
        spawnRatio: resolveSpawnRatio(
          profile.spawnStartRatio,
          profile.spawnEndRatio,
          i,
          spawnCount,
        ),
        moveSpeed: profile.moveSpeed,
      });
    }
  }

  return {
    mapId: request.mapId,
    roomId: request.roomId,
    encounters,
  };
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
          if (packet.mapId !== request.mapId) {
            return;
          }

          finish(mapPacketToRoster(request, packet));
        };

        client.on('mapMonsterRoster', handleRoster);
        client.joinMap();
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
