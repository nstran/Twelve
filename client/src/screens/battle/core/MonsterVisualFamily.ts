import type { MonsterType } from '../../../engine/MonsterSprite';
import type { MonsterSharedSheetFamily } from './BattleScreen.types';

export function resolveMonsterSharedSheetFamily(
  visualTypeByte: number,
  sharedSheetFamily?: MonsterSharedSheetFamily | null,
): MonsterSharedSheetFamily {
  if (sharedSheetFamily) {
    return sharedSheetFamily;
  }

  switch (visualTypeByte >> 1) {
    case 1:
      return 'Zap';
    case 0:
      return 'Monster';
    default:
      return 'Ice';
  }
}

export function resolveMonsterTypeFromVisuals(
  visualTypeByte: number,
  sharedSheetFamily?: MonsterSharedSheetFamily | null,
): MonsterType {
  switch (resolveMonsterSharedSheetFamily(visualTypeByte, sharedSheetFamily)) {
    case 'Ice':
      return 'ice';
    case 'Zap':
      return 'zap';
    case 'Monster':
    default:
      return 'fire';
  }
}

export function resolveMonsterBadgeFromVisuals(
  visualTypeByte: number,
  sharedSheetFamily?: MonsterSharedSheetFamily | null,
): string {
  switch (resolveMonsterSharedSheetFamily(visualTypeByte, sharedSheetFamily)) {
    case 'Zap':
      return '⚡';
    case 'Ice':
      return '💧';
    case 'Monster':
    default:
      return '🔥';
  }
}
