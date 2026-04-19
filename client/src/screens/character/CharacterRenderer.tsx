import React from 'react';
import type { StyleProp, ViewStyle } from 'react-native';
import type { CharacterAction, CharacterPoseFamilySlot } from '../../engine/character';
import { CreateCharacterPreview, measureCreateCharacterPreview } from './create/CreateCharacterPreview';
import type {
  CharacterAppearance,
  CharacterEquipmentLayerConfig,
  CharacterHairLayerOverride,
} from './shared';

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
  equipmentLayers: readonly CharacterEquipmentLayerConfig[] = [],
) {
  return measureCreateCharacterPreview(appearance, scale, anchorToBody, hairLayerOverride, equipmentLayers);
}

export const CharacterRenderer: React.FC<CharacterRendererProps> = ({
  appearance,
  hairLayerOverride,
  equipmentLayers,
  ...rest
}) => (
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
