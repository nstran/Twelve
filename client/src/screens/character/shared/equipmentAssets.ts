import type { CharacterAppearance, CharacterEquipmentItem } from './characterAppearance';
import type { CharacterEquipmentLayerConfig, CharacterLayerFamilyAssets } from './characterEquipmentLayer';
import {
  EQUIPMENT_ICON_ASSETS_BY_BAND,
  EQUIPMENT_LAYER_ASSETS_BY_BAND,
} from './equipmentAssets.generated';

export type EquipmentFamilyKey = number | string;

export const getEquipmentBandId = (resourceId: number) => resourceId - (resourceId % 10);

export const getEquipmentIconId = (resourceId: number) => getEquipmentBandId(resourceId) + 98;

export const resolveEquipmentIconAsset = (entry: CharacterEquipmentItem) =>
  EQUIPMENT_ICON_ASSETS_BY_BAND[getEquipmentBandId(entry.resourceId)];

export const normalizeEquipmentFamilyKey = (key: EquipmentFamilyKey): number | null => {
  const numericKey = typeof key === 'number'
    ? key
    : Number(String(key).match(/\d+/)?.[0]);

  if (!Number.isFinite(numericKey)) {
    return null;
  }

  return getEquipmentBandId(numericKey);
};

export const resolveEquipmentFamilyAssets = (
  key: EquipmentFamilyKey,
): CharacterLayerFamilyAssets | undefined => {
  const bandId = normalizeEquipmentFamilyKey(key);
  return bandId === null ? undefined : EQUIPMENT_LAYER_ASSETS_BY_BAND[bandId];
};

export const resolveEquipmentFamilyIconAsset = (key: EquipmentFamilyKey) => {
  const bandId = normalizeEquipmentFamilyKey(key);
  return bandId === null ? undefined : EQUIPMENT_ICON_ASSETS_BY_BAND[bandId];
};

export const isWeaponEquipment = (entry: CharacterEquipmentItem) =>
  entry.iconKind === 'weapon' || entry.slot === 4;

const EQUIPMENT_RENDER_SLOT: Record<number, {
  key: string;
  zIndex: number;
  fallbackMetaId: number;
  replacesDefaultLayer?: CharacterEquipmentLayerConfig['replacesDefaultLayer'];
}> = {
  0: { key: 'armor', zIndex: 5, fallbackMetaId: 79899, replacesDefaultLayer: 'outfit' },
  2: { key: 'helmet', zIndex: 6, fallbackMetaId: 89999 },
};

const WEAPON_RENDER_SLOT = {
  key: 'weapon',
  zIndex: 7,
  fallbackMetaId: 89999,
  replacesDefaultLayer: 'weapon',
} as const satisfies {
  key: string;
  zIndex: number;
  fallbackMetaId: number;
  replacesDefaultLayer: CharacterEquipmentLayerConfig['replacesDefaultLayer'];
};

function resolveEquipmentRenderSlot(entry: CharacterEquipmentItem) {
  if (isWeaponEquipment(entry)) {
    return WEAPON_RENDER_SLOT;
  }

  return EQUIPMENT_RENDER_SLOT[entry.slot];
}

export const buildEquippedCharacterEquipmentLayers = (
  appearance: CharacterAppearance,
): CharacterEquipmentLayerConfig[] => {
  const equipment = appearance.equipment ?? [];
  const layers: CharacterEquipmentLayerConfig[] = [];

  for (const entry of equipment) {
    if (!entry.isEquipped) {
      continue;
    }

    const slotConfig = resolveEquipmentRenderSlot(entry);
    if (!slotConfig) {
      continue;
    }

    const bandId = normalizeEquipmentFamilyKey(entry.resourceId);
    if (bandId === null) {
      continue;
    }

    const assetsBySlot = resolveEquipmentFamilyAssets(bandId);
    if (!assetsBySlot) {
      continue;
    }

    layers.push({
      key: `${slotConfig.key}-${entry.equipKey}`,
      metaId: bandId + 99,
      fallbackMetaId: slotConfig.fallbackMetaId,
      replacesDefaultLayer: slotConfig.replacesDefaultLayer,
      assetsBySlot,
      zIndex: slotConfig.zIndex,
    });
  }

  return layers;
};
