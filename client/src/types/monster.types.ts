export type MonsterVisualFamily = 'monster' | 'zap' | 'ice';

export type MonsterRosterEntry = {
  monsterKey: string;
  displayName: string;
  visualTypeByte: number;
  visualFamily: MonsterVisualFamily;
  displayLevel: number;
  iqValue: number;
  spawnCount: number;
  nameColorMode: number;
  assetCatalogId: string;
  framePaths: string[];
  placement?: {
    surfaceId?: string;
    patrolStartRatio: number;
    patrolEndRatio: number;
    spawnRatio: number;
    moveSpeed: number;
  };
};

export type MonsterBattleBootstrap = {
  combatantId: string;
  monsterKey: string;
  battleTemplateId: number;
  displayName: string;
  element: 1 | 2 | 4;
  level: number;
  currentHp: number;
  maxHp: number;
  currentMp: number;
  maxMp: number;
  currentPower: number;
  maxPower: number;
  stats: {
    strength: number;
    agility: number;
    magic: number;
    vitality: number;
    minDamage: number;
    maxDamage: number;
    defense: number;
    hitRate: number;
    dodgeRate: number;
    criticalDamage: number;
  };
  skills: Array<{
    skillId: number;
    level: number;
    manaCost: number;
  }>;
  assetCatalogId?: string;
  framePaths?: string[];
};

export type MonsterBattleDelta = {
  combatantKey: string;
  damage?: number;
  hp?: number;
  mp?: number;
  power?: number;
  timingByte?: number;
};

export function resolveVisualFamily(visualTypeByte: number): MonsterVisualFamily {
  const shifted = visualTypeByte >> 1;
  if (shifted === 0) return 'monster';
  if (shifted === 1) return 'zap';
  return 'ice';
}

export function resolveIqLabel(iqValue: number): string {
  if (iqValue < 3) return 'Siêu gà';
  if (iqValue < 7) return 'Bờm';
  if (iqValue < 10) return 'Ma lanh';
  if (iqValue === 11) return 'Tốc chiến';
  return 'Tuyệt đỉnh';
}
