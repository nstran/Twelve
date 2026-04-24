import type { ImageSourcePropType } from 'react-native';
import type { CharacterPoseFamilySlot } from '../../../engine/character';

export interface CharacterLayerFamilySlotAsset {
  source: ImageSourcePropType;
  width: number;
  height: number;
  cropX?: number;
  cropY?: number;
}

export type CharacterLayerFamilyAssets = Partial<Record<CharacterPoseFamilySlot, CharacterLayerFamilySlotAsset>>;

export interface CharacterLayerSheetSlotRect {
  width: number;
  height: number;
  cropX?: number;
  cropY?: number;
}

export interface CharacterLayerAssetFamilyConfig {
  /** Legacy base id for slot 0; runtime loads `baseImageId + familySlot`. */
  baseImageId?: number;
  /**
   * Explicit slot mapping. You can point every slot to the same source image and
   * vary `cropX/cropY/width/height` to cut idle/run/attack/jump from one sheet.
   */
  assetsBySlot?: CharacterLayerFamilyAssets;
}

export interface CharacterHairLayerOverride extends CharacterLayerAssetFamilyConfig {
  /** Meta family, e.g. 50199 -> 50100..50104 offsets per pose slot. */
  metaId: number;
}

export interface CharacterEquipmentLayerConfig extends CharacterLayerAssetFamilyConfig {
  /** Stable layer id, e.g. `hat`, `armor`, `weapon`. */
  key: string;
  /** Legacy meta family, e.g. 70099 -> 70000..70004 offsets per pose slot. */
  metaId: number;
  /** Used while only a subset of legacy equipment .meta files is decoded. */
  fallbackMetaId?: number;
  /** Legacy compositor slot this layer replaces when equipped. */
  replacesDefaultLayer?: 'outfit' | 'weapon';
  /** Painter order inside the character compositor. */
  zIndex: number;
}

export function buildSheetFamilyAssets(
  source: ImageSourcePropType,
  slots: Partial<Record<CharacterPoseFamilySlot, CharacterLayerSheetSlotRect>>,
): CharacterLayerFamilyAssets {
  const assets: CharacterLayerFamilyAssets = {};

  for (const slotKey of Object.keys(slots)) {
    const slot = Number(slotKey) as CharacterPoseFamilySlot;
    const rect = slots[slot];
    if (!rect) {
      continue;
    }

    assets[slot] = {
      source,
      width: rect.width,
      height: rect.height,
      cropX: rect.cropX,
      cropY: rect.cropY,
    };
  }

  return assets;
}
