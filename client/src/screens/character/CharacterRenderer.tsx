import React, { useMemo } from 'react';
import type { StyleProp, ViewStyle } from 'react-native';
import type { CharacterAction, CharacterPoseFamilySlot } from '../../engine/character';
import { CreateCharacterPreview, measureCreateCharacterPreview } from './create/CreateCharacterPreview';
import type {
  CharacterAppearance,
  CharacterEquipmentLayerConfig,
  CharacterHairLayerOverride,
} from './shared';
import { buildEquippedCharacterEquipmentLayers } from './shared';

type CharacterRendererFacing = 'left' | 'right';

export interface CharacterRendererProps {
  appearance: CharacterAppearance;
  hairLayerOverride?: CharacterHairLayerOverride;
  equipmentLayers?: readonly CharacterEquipmentLayerConfig[];
  scale?: number;
  style?: StyleProp<ViewStyle>;
  action?: CharacterAction;
  actionFrameIndex?: number;
  frameStepOverride?: number;
  facing?: CharacterRendererFacing;
  anchorToBody?: boolean;
  poseFamilySlotOverride?: CharacterPoseFamilySlot;
  poseFrameIndexOverride?: number;
}

export function measureCharacterRenderer(
  appearance: CharacterAppearance,
  scale?: number,
  anchorToBody = false,
  hairLayerOverride?: CharacterHairLayerOverride,
  equipmentLayers?: readonly CharacterEquipmentLayerConfig[],
) {
  return measureCreateCharacterPreview(
    appearance,
    scale,
    anchorToBody,
    hairLayerOverride,
    equipmentLayers ?? buildEquippedCharacterEquipmentLayers(appearance),
  );
}

export const CharacterRenderer: React.FC<CharacterRendererProps> = ({
  appearance,
  hairLayerOverride,
  equipmentLayers: equipmentLayersProp,
  ...rest
}) => {
  const appearanceEquipmentLayers = useMemo(
    () => buildEquippedCharacterEquipmentLayers(appearance),
    [appearance],
  );
  const equipmentLayers = equipmentLayersProp ?? appearanceEquipmentLayers;

  return (
    <CreateCharacterPreview
      genderIndex={appearance.genderIndex}
      faceIndex={appearance.faceIndex}
      hairIndex={appearance.hairIndex}
      hairColorIndex={appearance.hairColorIndex}
      skinColorIndex={appearance.skinColorIndex}
      hairLayerOverride={hairLayerOverride}
      equipmentLayers={equipmentLayers}
      {...rest}
    />
  );
};
