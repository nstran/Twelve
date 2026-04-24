import type { CharacterAppearance, CharacterEquipmentItem } from './characterAppearance';
import type { CharacterEquipmentLayerConfig } from './characterEquipmentLayer';
import {
  EQUIPMENT_ICON_ASSETS_BY_BAND,
  EQUIPMENT_LAYER_ASSETS_BY_BAND,
} from './equipmentAssets.generated';

export const getEquipmentBandId = (resourceId: number) => resourceId - (resourceId % 10);

export const getEquipmentIconId = (resourceId: number) => getEquipmentBandId(resourceId) + 98;

export const resolveEquipmentIconAsset = (entry: CharacterEquipmentItem) =>
  EQUIPMENT_ICON_ASSETS_BY_BAND[getEquipmentBandId(entry.resourceId)];

const EQUIPMENT_RENDER_SLOT: Record<number, {
  key: string;
  zIndex: number;
  fallbackMetaId: number;
  replacesDefaultLayer?: CharacterEquipmentLayerConfig['replacesDefaultLayer'];
}> = {
  0: { key: 'armor', zIndex: 5, fallbackMetaId: 79899, replacesDefaultLayer: 'outfit' },
  1: { key: 'weapon', zIndex: 7, fallbackMetaId: 89999, replacesDefaultLayer: 'weapon' },
  2: { key: 'helmet', zIndex: 6, fallbackMetaId: 89999 },
};

export const buildEquippedCharacterEquipmentLayers = (
  appearance: CharacterAppearance,
): CharacterEquipmentLayerConfig[] => {
  const equipment = appearance.equipment ?? [];
  const layers: CharacterEquipmentLayerConfig[] = [];

  for (const entry of equipment) {
    if (!entry.isEquipped) {
      continue;
    }

    const slotConfig = EQUIPMENT_RENDER_SLOT[entry.slot];
    if (!slotConfig) {
      continue;
    }

    const bandId = getEquipmentBandId(entry.resourceId);
    const assetsBySlot = EQUIPMENT_LAYER_ASSETS_BY_BAND[bandId];
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
