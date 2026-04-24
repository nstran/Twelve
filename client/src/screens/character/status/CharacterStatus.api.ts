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
  useItem: (username: string, itemId: number) => Promise<PlayerRuntimeResponse | null>;
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

    useItem: (username, itemId) =>
      requestJson<PlayerRuntimeResponse>(`${baseUrl}/player/runtime/item-use`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, itemId }),
      }),
  };
};

export const mergePlayerRuntimeAppearance = (
  current: CharacterAppearance,
  runtime: PlayerRuntimeSnapshot,
): CharacterAppearance => {
  const expDenominator = Math.max(1, runtime.expCeiling - runtime.expFloor);
  const expPct = Math.max(0, Math.min(100, Math.floor(((runtime.exp - runtime.expFloor) * 100) / expDenominator)));

  return {
    ...current,
    username: runtime.username,
    elementIndex: runtime.element,
    level: runtime.level,
    walletQuan: runtime.gold,
    quan: `${runtime.gold} Quan`,
    quanProgress: { cur: runtime.quanProgress, max: runtime.quanProgressCap },
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
      attack: runtime.maxDamage,
      def: runtime.defense,
      acc: runtime.hit,
      dodge: runtime.dodge,
      hp: runtime.maxHp,
      crit: `${runtime.crit}%`,
    },
    inventory: runtime.inventory,
    equipment: runtime.equipment,
    skills: runtime.skills,
  };
};
