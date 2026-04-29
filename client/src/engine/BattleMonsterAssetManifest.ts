import type { MonsterType } from './MonsterSprite';

export type BattleMonsterChunkSpec = {
  source: ReturnType<typeof require>;
  frameCount: number;
};

export type BattleMonsterPoseKey =
  | 'idle_a'
  | 'idle_b'
  | 'idle_c'
  | 'prepare_attack'
  | 'run_attack'
  | 'attack'
  | 'hit';

export type BattleMonsterAssetSpec = {
  assetCatalogId: string;
  fallbackType: MonsterType;
  frameWidth: number;
  frameHeight: number;
  displayScale: number;
  groundOffset: number;
  visualFootSink: number;
  baseFacingRight: boolean;
  chunks: ReadonlyArray<BattleMonsterChunkSpec>;
  legacyFrameMap?: Partial<Record<number, { chunkIndex: number; frameOffset: number }>>;
  poses?: Partial<Record<BattleMonsterPoseKey, { chunkIndex: number; frameOffset: number }>>;
};

export const BATTLE_MONSTER_ASSET_MANIFEST: Record<string, BattleMonsterAssetSpec> = {
  MONSTER_1000_SLOT_0: {
    assetCatalogId: 'MONSTER_1000_SLOT_0',
    fallbackType: 'fire',
    frameWidth: 78,
    frameHeight: 47,
    displayScale: 1.08,
    groundOffset: 1,
    visualFootSink: 1,
    baseFacingRight: false,
    chunks: [
      { source: require('../../assets/monster/100001.png'), frameCount: 2 },
      { source: require('../../assets/monster/100003.png'), frameCount: 1 },
      { source: require('../../assets/monster/100004.png'), frameCount: 1 },
      { source: require('../../assets/monster/100005.png'), frameCount: 3 },
    ],
    legacyFrameMap: {
      0: { chunkIndex: 3, frameOffset: 0 },
      1: { chunkIndex: 1, frameOffset: 0 },
      2: { chunkIndex: 2, frameOffset: 0 },
      3: { chunkIndex: 0, frameOffset: 0 },
      4: { chunkIndex: 3, frameOffset: 1 },
      5: { chunkIndex: 3, frameOffset: 2 },
    },
    poses: {
      idle_a: { chunkIndex: 3, frameOffset: 0 },
      idle_b: { chunkIndex: 3, frameOffset: 1 },
      idle_c: { chunkIndex: 3, frameOffset: 2 },
      prepare_attack: { chunkIndex: 0, frameOffset: 1 },
      run_attack: { chunkIndex: 2, frameOffset: 0 },
      attack: { chunkIndex: 0, frameOffset: 0 },
      hit: { chunkIndex: 1, frameOffset: 0 },
    },
  },
  MONSTER_1002_SLOT_0: {
    assetCatalogId: 'MONSTER_1002_SLOT_0',
    fallbackType: 'ice',
    frameWidth: 78,
    frameHeight: 47,
    displayScale: 1.08,
    groundOffset: 1,
    visualFootSink: 1,
    baseFacingRight: false,
    chunks: [
      { source: require('../../assets/monster/100201.png'), frameCount: 2 },
      { source: require('../../assets/monster/100203.png'), frameCount: 1 },
      { source: require('../../assets/monster/100204.png'), frameCount: 1 },
      { source: require('../../assets/monster/100205.png'), frameCount: 3 },
    ],
    legacyFrameMap: {
      0: { chunkIndex: 3, frameOffset: 0 },
      1: { chunkIndex: 1, frameOffset: 0 },
      2: { chunkIndex: 2, frameOffset: 0 },
      3: { chunkIndex: 0, frameOffset: 0 },
      4: { chunkIndex: 3, frameOffset: 1 },
      5: { chunkIndex: 3, frameOffset: 2 },
    },
    poses: {
      idle_a: { chunkIndex: 3, frameOffset: 0 },
      idle_b: { chunkIndex: 3, frameOffset: 1 },
      idle_c: { chunkIndex: 3, frameOffset: 2 },
      prepare_attack: { chunkIndex: 0, frameOffset: 1 },
      run_attack: { chunkIndex: 2, frameOffset: 0 },
      attack: { chunkIndex: 0, frameOffset: 0 },
      hit: { chunkIndex: 1, frameOffset: 0 },
    },
  },
  MONSTER_1003_SLOT_0: {
    assetCatalogId: 'MONSTER_1003_SLOT_0',
    fallbackType: 'zap',
    frameWidth: 42,
    frameHeight: 48,
    displayScale: 1.2,
    groundOffset: 0,
    visualFootSink: 0,
    baseFacingRight: false,
    chunks: [
      { source: require('../../assets/monster/100303.png'), frameCount: 1 },
      { source: require('../../assets/monster/100305.png'), frameCount: 2 },
      { source: require('../../assets/monster/100306.png'), frameCount: 4 },
    ],
    legacyFrameMap: {
      0: { chunkIndex: 1, frameOffset: 0 },
      1: { chunkIndex: 0, frameOffset: 0 },
      2: { chunkIndex: 2, frameOffset: 0 },
      3: { chunkIndex: 2, frameOffset: 1 },
      4: { chunkIndex: 1, frameOffset: 1 },
      5: { chunkIndex: 1, frameOffset: 0 },
    },
    poses: {
      idle_a: { chunkIndex: 1, frameOffset: 0 },
      idle_b: { chunkIndex: 1, frameOffset: 1 },
      idle_c: { chunkIndex: 1, frameOffset: 0 },
      prepare_attack: { chunkIndex: 0, frameOffset: 0 },
      run_attack: { chunkIndex: 2, frameOffset: 0 },
      attack: { chunkIndex: 2, frameOffset: 1 },
      hit: { chunkIndex: 2, frameOffset: 3 },
    },
  },
};

export const getBattleMonsterAssetSpec = (assetCatalogId?: string | null) =>
  assetCatalogId ? BATTLE_MONSTER_ASSET_MANIFEST[assetCatalogId] ?? null : null;
