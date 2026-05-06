import type { ImageSourcePropType } from 'react-native';
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
  entry.iconKind === 'weapon' || entry.slot === 1;

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

/**
 * Aura assets for high-rank equipment (rank 4+).
 * Source: lc.java:233-288 — aura initialization by rank
 * - Rank 1: /aura1 (45x50) shadow effect
 * - Rank 2: /aura2 (40x55) blue aura
 * - Rank 3: /aura3 (88x95) purple aura replaces main body
 * - Rank 4: /aura3 + /aura2 combined (purple body + blue overlay)
 */
export const AURA_LAYER_ASSETS = {
  aura1: {
    source: require('../../../../assets/battle/06_auras/aura1.png'),
    width: 45,
    height: 50,
  },
  aura2: {
    source: require('../../../../assets/battle/06_auras/aura2.png'),
    width: 40,
    height: 55,
  },
  aura3: {
    source: require('../../../../assets/battle/06_auras/aura3.png'),
    width: 88,
    height: 95,
  },
} as const satisfies Record<string, { source: ImageSourcePropType; width: number; height: number }>;

export const buildEquippedCharacterEquipmentLayers = (
  appearance: CharacterAppearance,
): CharacterEquipmentLayerConfig[] => {
  const equipment = appearance.equipment ?? [];
  const layers: CharacterEquipmentLayerConfig[] = [];
  let maxAuraRank = 0;

  for (const entry of equipment) {
    if (!entry.isEquipped) {
      continue;
    }

    // Track highest rank with aura effect (lc.java:233-288)
    // Rank 1-4 have aura effects, rank 0 has none
    if (entry.rank >= 1 && entry.rank <= 4 && entry.rank > maxAuraRank) {
      maxAuraRank = entry.rank;
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

  // Add aura layers based on highest rank (lc.java:233-288)
  // Render order from lc.java:291-301: y (main) -> w (shadow) -> x (aura)
  // In RN zIndex (higher = on top): y=1, w=2, x=3
  if (maxAuraRank >= 1) {
    const poseSlots = [0, 1, 2, 3, 4, 7, 8, 9] as const;

    if (maxAuraRank === 1) {
      // Rank 1: w (shadow) = aura1, renders after body (zIndex 2)
      const aura1Assets: CharacterLayerFamilyAssets = {};
      for (const slot of poseSlots) {
        aura1Assets[slot] = {
          source: AURA_LAYER_ASSETS.aura1.source,
          width: AURA_LAYER_ASSETS.aura1.width,
          height: AURA_LAYER_ASSETS.aura1.height,
        };
      }
      layers.push({
        key: 'aura1-shadow',
        metaId: 99997,
        fallbackMetaId: 99997,
        assetsBySlot: aura1Assets,
        zIndex: 2, // renders after body (zIndex 1)
      });
    } else if (maxAuraRank === 2) {
      // Rank 2: x (aura) = aura2, renders on top (zIndex 3)
      const aura2Assets: CharacterLayerFamilyAssets = {};
      for (const slot of poseSlots) {
        aura2Assets[slot] = {
          source: AURA_LAYER_ASSETS.aura2.source,
          width: AURA_LAYER_ASSETS.aura2.width,
          height: AURA_LAYER_ASSETS.aura2.height,
        };
      }
      layers.push({
        key: 'aura2-overlay',
        metaId: 99998,
        fallbackMetaId: 99998,
        assetsBySlot: aura2Assets,
        zIndex: 3, // renders on top of body and shadow
      });
    } else if (maxAuraRank === 3) {
      // Rank 3: y (main) = aura3 replaces body (zIndex 1, replaces default body)
      const aura3Assets: CharacterLayerFamilyAssets = {};
      for (const slot of poseSlots) {
        aura3Assets[slot] = {
          source: AURA_LAYER_ASSETS.aura3.source,
          width: AURA_LAYER_ASSETS.aura3.width,
          height: AURA_LAYER_ASSETS.aura3.height,
        };
      }
      layers.push({
        key: 'aura3-body',
        metaId: 99999,
        fallbackMetaId: 99999,
        replacesDefaultLayer: 'body',
        assetsBySlot: aura3Assets,
        zIndex: 1, // replaces body at same zIndex
      });
    } else if (maxAuraRank === 4) {
      // Rank 4: y (main) = aura3 + x (aura) = aura2 combined
      // aura3 replaces body (zIndex 1)
      const aura3Assets: CharacterLayerFamilyAssets = {};
      const aura2Assets: CharacterLayerFamilyAssets = {};
      for (const slot of poseSlots) {
        aura3Assets[slot] = {
          source: AURA_LAYER_ASSETS.aura3.source,
          width: AURA_LAYER_ASSETS.aura3.width,
          height: AURA_LAYER_ASSETS.aura3.height,
        };
        aura2Assets[slot] = {
          source: AURA_LAYER_ASSETS.aura2.source,
          width: AURA_LAYER_ASSETS.aura2.width,
          height: AURA_LAYER_ASSETS.aura2.height,
        };
      }
      layers.push({
        key: 'aura3-body',
        metaId: 99999,
        fallbackMetaId: 99999,
        replacesDefaultLayer: 'body',
        assetsBySlot: aura3Assets,
        zIndex: 1, // replaces body
      });
      layers.push({
        key: 'aura2-overlay',
        metaId: 99998,
        fallbackMetaId: 99998,
        assetsBySlot: aura2Assets,
        zIndex: 3, // overlays on top
      });
    }
  }

  return layers;
};
