import type {
  MapMonsterRosterRequest,
  MapMonsterRosterResponse,
  ResolveMapMonsterRoster,
} from './MapMonsterRoster.types';

const toHttpBaseUrl = (socketUrl: string): string => {
  try {
    const parsed = new URL(socketUrl);
    const protocol = parsed.protocol === 'wss:' ? 'https:' : 'http:';
    return `${protocol}//${parsed.host}`;
  } catch {
    return socketUrl;
  }
};

export const createMapMonsterRosterResolver = (
  socketUrl: string,
  timeoutMs = 3500,
): ResolveMapMonsterRoster => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return async (
    request: MapMonsterRosterRequest,
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
};
