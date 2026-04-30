import type {
  CharacterAppearance,
  CharacterEquipmentItem,
  CharacterInventoryItem,
  CharacterSkillNode,
} from '../shared';

export interface PlayerRuntimeSnapshot {
  username: string;
  element: number;
  level: number;
  currentHp: number;
  maxHp: number;
  exp: number;
  expFloor: number;
  expCeiling: number;
  gold: number;
  quanProgress: number;
  quanProgressCap: number;
  freePoints: number;
  skillPoints: number;
  currentMp: number;
  maxMp: number;
  currentPower: number;
  maxPower: number;
  cuongLuc: number;
  thanPhap: number;
  noiLuc: number;
  theLuc: number;
  bonusCuongLuc: number;
  bonusThanPhap: number;
  bonusNoiLuc: number;
  bonusTheLuc: number;
  minDamage: number;
  maxDamage: number;
  defense: number;
  dodge: number;
  hit: number;
  crit: number;
  /** Tốc độ di chuyển ngang ngoài map (px/frame ~60fps). Nguồn: MapMovementCalculator server. */
  mapMoveSpeed: number;
  inventory: CharacterInventoryItem[];
  equipment: CharacterEquipmentItem[];
  skills: CharacterSkillNode[];
}

export interface PlayerRuntimeResponse {
  snapshot: PlayerRuntimeSnapshot;
  message?: string | null;
}

export interface PlayerRuntimeApi {
  load: (username: string) => Promise<PlayerRuntimeResponse | null>;
  allocateStat: (username: string, stat: 'CuongLuc' | 'ThanPhap' | 'NoiLuc' | 'TheLuc') => Promise<PlayerRuntimeResponse | null>;
  allocateSkill: (username: string, familyCode: number) => Promise<PlayerRuntimeResponse | null>;
  toggleEquipment: (username: string, equipKey: string, equip: boolean) => Promise<PlayerRuntimeResponse | null>;
  previewEquipmentLoadout: (username: string, equipKeys: string[]) => Promise<PlayerRuntimeResponse | null>;
  commitEquipmentLoadout: (username: string, equipKeys: string[]) => Promise<PlayerRuntimeResponse | null>;
  useItem: (username: string, itemId: number) => Promise<PlayerRuntimeResponse | null>;
  discardEquipment: (username: string, equipKeys: string[]) => Promise<PlayerRuntimeResponse | null>;
  discardItem: (username: string, itemId: number, quantity: number) => Promise<PlayerRuntimeResponse | null>;
  repairEquipment: (username: string, equipKey: string, repairItemId: number) => Promise<PlayerRuntimeResponse | null>;
}

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

const requestJson = async <T>(input: RequestInfo, init?: RequestInit): Promise<T | null> => {
  try {
    const response = await fetch(input, init);
    if (!response.ok) {
      return null;
    }

    return await response.json() as T;
  } catch {
    return null;
  }
};

export const createPlayerRuntimeApi = (socketUrl: string): PlayerRuntimeApi => {
  const baseUrl = toHttpBaseUrl(socketUrl);

  return {
    load: (username) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime?username=${encodeURIComponent(username)}`),

    allocateStat: (username, stat) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/stat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, stat, amount: 1 }),
      }),

    allocateSkill: (username, familyCode) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/skill`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, familyCode }),
      }),

    toggleEquipment: (username, equipKey, equip) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/equipment`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, equipKey, equip }),
      }),

    previewEquipmentLoadout: (username, equipKeys) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/equipment/preview`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, equipKeys }),
      }),

    commitEquipmentLoadout: (username, equipKeys) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/equipment/loadout`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, equipKeys }),
      }),

    useItem: (username, itemId) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/item-use`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, itemId }),
      }),

    discardEquipment: (username, equipKeys) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/equipment/discard`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, equipKeys }),
      }),

    discardItem: (username, itemId, quantity) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/item/discard`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, itemId, quantity }),
      }),

    repairEquipment: (username, equipKey, repairItemId) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/equipment/repair`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, equipKey, repairItemId }),
      }),
  };
};

export const mergePlayerRuntimeAppearance = (
  current: CharacterAppearance,
  runtime: PlayerRuntimeSnapshot,
): CharacterAppearance => {
  const expDenominator = Math.max(1, runtime.expCeiling - runtime.expFloor);
  const expPct = Math.max(0, Math.min(100, Math.floor(((runtime.exp - runtime.expFloor) * 100) / expDenominator)));
  // Java/remake wallet lock: server `gold` is raw KEN/gold. Only each full
  // QuanProgressCap chunk (10_000 by default) becomes one displayed Quan;
  // the remainder stays on the gold progress bar.
  // Source: gameplay memory + BATTLE_SYSTEM_RECONSTRUCTION.md §EXP/Gold/Quan.
  const quanProgressCap = Math.max(1, runtime.quanProgressCap || 10000);
  const rawGold = Math.max(0, Math.floor(runtime.gold));
  const walletQuan = Math.floor(rawGold / quanProgressCap);
  const goldProgress = rawGold % quanProgressCap;

  return {
    ...current,
    username: runtime.username,
    elementIndex: runtime.element,
    level: runtime.level,
    walletQuan,
    quan: `${walletQuan} Quan`,
    quanProgress: { cur: goldProgress, max: quanProgressCap },
    hp: { cur: runtime.currentHp, max: runtime.maxHp },
    exp: { cur: expPct, max: 100 },
    expRange: { value: runtime.exp, floor: runtime.expFloor, ceiling: runtime.expCeiling },
    stats: {
      cuongLuc: runtime.cuongLuc,
      thanPhap: runtime.thanPhap,
      noiLuc: runtime.noiLuc,
      theLuc: runtime.theLuc,
    },
    points: runtime.freePoints,
    freePoints: runtime.freePoints,
    skillPoints: runtime.skillPoints,
    combat: {
      // Java status UI (`da.java`) shows a single "Tấn Công" value.
      // Source bridge has min/max (`lh.x/lh.y`), but the old character panel displays
      // the main attack number (`lh.x`/jz.b + equipment flat), not a "min-max" range.
      // Battle damage can still use minDamage/maxDamage internally.
      attack: runtime.minDamage,
      def: runtime.defense,
      acc: runtime.hit,
      dodge: runtime.dodge,
      hp: runtime.maxHp,
      crit: `${runtime.crit}%`,
    },
    mapMovement: {
      moveSpeed: runtime.mapMoveSpeed,
    },
    inventory: runtime.inventory,
    equipment: runtime.equipment,
    skills: runtime.skills,
  };
};
