/**
 * CreateCharacterPreview.tsx
 *
 * Modular legacy character compositor used by:
 * - create-character preview
 * - character status
 * - map / battle runtime
 *
 * Java legacy mapping confirmed from `mb.java`:
 * - slot 0 = idle
 * - slot 1 = run
 * - slot 2 = attack
 */

import React, { useEffect, useMemo, useState } from 'react';
import { Image, View, type ImageSourcePropType, type StyleProp, type ViewStyle } from 'react-native';
import type { CharacterAction, CharacterPoseFamilySlot } from '../../../engine/character';
import { styles } from './CreateCharacterScreen.styles';
import {
  DEFAULT_OVERLAY,
  EYE_STYLE_OPTIONS,
  GENDER_OPTIONS,
  HAIR_COLOR_SOURCES,
  HAIR_COLOR_OPTIONS,
  HAIR_PALETTE_FROM,
  HAIR_STYLE_OPTIONS,
  SKIN_COLOR_OPTIONS,
  SKIN_PALETTE_FROM,
  SLOT_ZERO_META,
  type SlotMeta,
} from './createCatalog';
import { ASSET_REGISTRY, type AssetRegistryEntry } from './assetRegistry';
import { usePaletteSwappedImage } from './usePaletteSwappedImage';
import { ACTION_SLOT_META } from './actionSlotMeta';
import type { CharacterAppearance } from '../shared';

type PreviewFacing = 'left' | 'right';
type ActionFamilySlot = CharacterPoseFamilySlot;
type CharacterPreviewAppearance = Pick<
  CharacterAppearance,
  'genderIndex' | 'faceIndex' | 'hairIndex' | 'hairColorIndex' | 'skinColorIndex'
>;

interface CreateCharacterPreviewProps extends CharacterPreviewAppearance {
  scale?: number;
  style?: StyleProp<ViewStyle>;
  action?: CharacterAction;
  actionFrameIndex?: number;
  /** Backward-compatible alias used by old callers for idle 2-frame preview. */
  frameStepOverride?: number;
  facing?: PreviewFacing;
  /** Use body bounds as the render anchor to avoid trailing transparent padding. */
  anchorToBody?: boolean;
  poseFamilySlotOverride?: CharacterPoseFamilySlot;
  poseFrameIndexOverride?: number;
}

interface LayerRect {
  key: string;
  source: ImageSourcePropType;
  sourceWidth: number;
  sourceHeight: number;
  frameWidth: number;
  frameHeight: number;
  frameStride?: number;
  sourceIndex: number;
  cropX?: number;
  cropY?: number;
  x: number;
  y: number;
  zIndex: number;
}

interface BodyFamilyAsset {
  source: ImageSourcePropType;
  width: number;
  height: number;
  frameWidthDivisor: number;
  frameCount: number;
}

interface SimpleFamilyAsset {
  source: ImageSourcePropType;
  width: number;
  height: number;
}

const DEFAULT_SCALE = 2.2;
const ACTION_FAMILY_SLOTS = [0, 1, 2, 3, 4] as const;

const ACTION_SLOT_BY_ACTION: Record<CharacterAction, ActionFamilySlot> = {
  idle: 0,
  run: 1,
  attack: 2,
};

const LOCAL_ANIM_MS: Record<CharacterAction, number> = {
  idle: 420,
  run: 140,
  attack: 150,
};

const BODY_FAMILY_ASSETS: Record<ActionFamilySlot, BodyFamilyAsset> = {
  0: {
    source: require('../../../../assets/createcs_runtime/body_pose_family_990xx/99000.png'),
    width: 110,
    height: 53,
    frameWidthDivisor: 2,
    frameCount: 2,
  },
  1: {
    source: require('../../../../assets/createcs_runtime/body_pose_family_990xx/99001.png'),
    width: 330,
    height: 53,
    frameWidthDivisor: 6,
    frameCount: 6,
  },
  2: {
    source: require('../../../../assets/createcs_runtime/body_pose_family_990xx/99002.png'),
    width: 220,
    height: 53,
    frameWidthDivisor: 4,
    frameCount: 4,
  },
  3: {
    source: require('../../../../assets/createcs_runtime/body_pose_family_990xx/99003.png'),
    width: 110,
    height: 53,
    frameWidthDivisor: 2,
    frameCount: 2,
  },
  4: {
    source: require('../../../../assets/createcs_runtime/body_pose_family_990xx/99004.png'),
    width: 165,
    height: 53,
    frameWidthDivisor: 3,
    frameCount: 3,
  },
};

const GENDER_FAMILY_ASSETS = {
  male: {
    0: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79800.png'),
      width: 38,
      height: 23,
    },
    1: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79801.png'),
      width: 138,
      height: 23,
    },
    2: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79802.png'),
      width: 124,
      height: 30,
    },
    3: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79803.png'),
      width: 48,
      height: 22,
    },
    4: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79804.png'),
      width: 81,
      height: 25,
    },
  },
  female: {
    0: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79900.png'),
      width: 32,
      height: 23,
    },
    1: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79901.png'),
      width: 115,
      height: 26,
    },
    2: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79902.png'),
      width: 100,
      height: 22,
    },
    3: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79903.png'),
      width: 36,
      height: 25,
    },
    4: {
      source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79904.png'),
      width: 66,
      height: 23,
    },
  },
} as const satisfies Record<'male' | 'female', Record<ActionFamilySlot, SimpleFamilyAsset>>;

const OVERLAY_FAMILY_ASSETS: Record<ActionFamilySlot, SimpleFamilyAsset> = {
  0: {
    source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89900.png'),
    width: 41,
    height: 15,
  },
  1: {
    source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89901.png'),
    width: 26,
    height: 11,
  },
  2: {
    source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89902.png'),
    width: 180,
    height: 56,
  },
  3: {
    source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89903.png'),
    width: 80,
    height: 21,
  },
  4: {
    source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89904.png'),
    width: 120,
    height: 36,
  },
};

function useBodySourcesBySlot(
  palette: readonly number[],
): Record<ActionFamilySlot, ImageSourcePropType> {
  const slot0 = usePaletteSwappedImage(BODY_FAMILY_ASSETS[0].source, SKIN_PALETTE_FROM, palette);
  const slot1 = usePaletteSwappedImage(BODY_FAMILY_ASSETS[1].source, SKIN_PALETTE_FROM, palette);
  const slot2 = usePaletteSwappedImage(BODY_FAMILY_ASSETS[2].source, SKIN_PALETTE_FROM, palette);
  const slot3 = usePaletteSwappedImage(BODY_FAMILY_ASSETS[3].source, SKIN_PALETTE_FROM, palette);
  const slot4 = usePaletteSwappedImage(BODY_FAMILY_ASSETS[4].source, SKIN_PALETTE_FROM, palette);

  return useMemo(() => ({
    0: slot0,
    1: slot1,
    2: slot2,
    3: slot3,
    4: slot4,
  }), [slot0, slot1, slot2, slot3, slot4]);
}

function useHairSourcesBySlot(
  hairAssets: Record<ActionFamilySlot, AssetRegistryEntry>,
  palette: readonly number[],
): Record<ActionFamilySlot, ImageSourcePropType> {
  const slot0 = usePaletteSwappedImage(hairAssets[0].source, HAIR_PALETTE_FROM, palette);
  const slot1 = usePaletteSwappedImage(hairAssets[1].source, HAIR_PALETTE_FROM, palette);
  const slot2 = usePaletteSwappedImage(hairAssets[2].source, HAIR_PALETTE_FROM, palette);
  const slot3 = usePaletteSwappedImage(hairAssets[3].source, HAIR_PALETTE_FROM, palette);
  const slot4 = usePaletteSwappedImage(hairAssets[4].source, HAIR_PALETTE_FROM, palette);

  return useMemo(() => ({
    0: slot0,
    1: slot1,
    2: slot2,
    3: slot3,
    4: slot4,
  }), [slot0, slot1, slot2, slot3, slot4]);
}

function resolveMetaRect(
  key: string,
  source: ImageSourcePropType,
  sourceWidth: number,
  sourceHeight: number,
  meta: SlotMeta,
  frameStep: number,
  zIndex: number,
): LayerRect {
  const frameWidth = sourceWidth / meta.frameWidthDivisor;
  const frame = meta.frames[frameStep % meta.frames.length];
  return {
    key,
    source,
    sourceWidth,
    sourceHeight,
    frameWidth,
    frameHeight: sourceHeight,
    frameStride: frameWidth,
    sourceIndex: frame.sourceIndex,
    x: frame.xOffset,
    y: frame.yOffset,
    zIndex,
  };
}

function resolveBodyRect(
  source: ImageSourcePropType,
  bodyAsset: BodyFamilyAsset,
  frameStep: number,
): LayerRect {
  const frameWidth = bodyAsset.width / bodyAsset.frameWidthDivisor;
  return {
    key: 'body',
    source,
    sourceWidth: bodyAsset.width,
    sourceHeight: bodyAsset.height,
    frameWidth,
    frameHeight: bodyAsset.height,
    frameStride: frameWidth,
    sourceIndex: frameStep % bodyAsset.frameCount,
    x: 0,
    y: 0,
    zIndex: 1,
  };
}

function getActionSlotMeta(metaId: number, familySlot: ActionFamilySlot): SlotMeta {
  const actionMetaBySlot = ACTION_SLOT_META[metaId] as Partial<Record<ActionFamilySlot, SlotMeta>> | undefined;
  const runtimeMeta = actionMetaBySlot?.[familySlot];
  if (runtimeMeta) {
    return runtimeMeta;
  }

  const fallbackMeta = SLOT_ZERO_META[metaId];
  if (fallbackMeta) {
    return fallbackMeta;
  }

  throw new Error(`Missing action slot meta for metaId=${metaId}, slot=${familySlot}`);
}

function getFamilyAsset<T extends { source: ImageSourcePropType }>(
  record: Record<number, T>,
  imageId: number,
): T {
  const asset = record[imageId];
  if (!asset) {
    throw new Error(`Missing asset ${imageId}`);
  }
  return asset;
}

function buildCreateCharacterLayout(
  appearance: CharacterPreviewAppearance,
  familySlot: ActionFamilySlot,
  frameStep: number,
  resolvedSources: {
    bodySource: ImageSourcePropType;
    hairSource: ImageSourcePropType;
  },
) {
  const { genderIndex, faceIndex, hairIndex, hairColorIndex, skinColorIndex } = appearance;
  void hairColorIndex;
  void skinColorIndex;

  const genderKey = genderIndex === 0 ? 'male' : 'female';
  const genderOption = GENDER_OPTIONS[genderIndex] ?? GENDER_OPTIONS[0];
  const genderAsset = GENDER_FAMILY_ASSETS[genderKey][familySlot];
  const overlayAsset = OVERLAY_FAMILY_ASSETS[familySlot];
  const bodyAsset = BODY_FAMILY_ASSETS[familySlot];

  const hairOption = HAIR_STYLE_OPTIONS[genderKey][hairIndex] ?? HAIR_STYLE_OPTIONS[genderKey][0];
  const hairAsset = getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + familySlot);

  const eyeOption = EYE_STYLE_OPTIONS[genderKey][faceIndex] ?? EYE_STYLE_OPTIONS[genderKey][0];
  const eyeAsset = getFamilyAsset(ASSET_REGISTRY, eyeOption.baseImageId + familySlot);

  const bodyRect = resolveBodyRect(resolvedSources.bodySource, bodyAsset, frameStep);
  const layers: LayerRect[] = [bodyRect];

  layers.push(resolveMetaRect(
    'hair',
    resolvedSources.hairSource,
    hairAsset.width,
    hairAsset.height,
    getActionSlotMeta(hairOption.metaId, familySlot),
    frameStep,
    2,
  ));

  layers.push(resolveMetaRect(
    'eyes',
    eyeAsset.source,
    eyeAsset.width,
    eyeAsset.height,
    getActionSlotMeta(eyeOption.metaId, familySlot),
    frameStep,
    3,
  ));

  layers.push(resolveMetaRect(
    'gender',
    genderAsset.source,
    genderAsset.width,
    genderAsset.height,
    getActionSlotMeta(genderOption.metaId, familySlot),
    frameStep,
    4,
  ));

  layers.push(resolveMetaRect(
    'overlay',
    overlayAsset.source,
    overlayAsset.width,
    overlayAsset.height,
    getActionSlotMeta(DEFAULT_OVERLAY.metaId, familySlot),
    frameStep,
    5,
  ));

  let minX = 0;
  let minY = 0;
  let maxX = bodyRect.frameWidth;
  let maxY = bodyRect.frameHeight;
  for (const layer of layers) {
    minX = Math.min(minX, layer.x);
    minY = Math.min(minY, layer.y);
    maxX = Math.max(maxX, layer.x + layer.frameWidth);
    maxY = Math.max(maxY, layer.y + layer.frameHeight);
  }

  return {
    width: maxX - minX,
    height: maxY - minY,
    bodyX: bodyRect.x - minX,
    bodyY: bodyRect.y - minY,
    bodyWidth: bodyRect.frameWidth,
    bodyHeight: bodyRect.frameHeight,
    layers: layers.map((layer) => ({ ...layer, x: layer.x - minX, y: layer.y - minY })),
  };
}

export function measureCreateCharacterPreview(
  appearance: CharacterPreviewAppearance,
  scale: number = DEFAULT_SCALE,
  anchorToBody = false,
) {
  let maxWidth = 0;
  let maxHeight = 0;

  const genderKey = appearance.genderIndex === 0 ? 'male' : 'female';
  const hairOption = HAIR_STYLE_OPTIONS[genderKey][appearance.hairIndex] ?? HAIR_STYLE_OPTIONS[genderKey][0];

  for (const familySlot of ACTION_FAMILY_SLOTS) {
    const bodySource = BODY_FAMILY_ASSETS[familySlot].source;
    const hairSource = getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + familySlot).source;
    const frameCount = BODY_FAMILY_ASSETS[familySlot].frameCount;

    for (let frame = 0; frame < frameCount; frame++) {
      const layout = buildCreateCharacterLayout(
        appearance,
        familySlot,
        frame,
        { bodySource, hairSource },
      );
      maxWidth = Math.max(maxWidth, anchorToBody ? layout.bodyWidth : layout.width);
      maxHeight = Math.max(maxHeight, layout.height);
    }
  }

  return {
    w: maxWidth * scale,
    h: maxHeight * scale,
  };
}

export const CreateCharacterPreview: React.FC<CreateCharacterPreviewProps> = ({
  genderIndex,
  faceIndex,
  hairIndex,
  hairColorIndex,
  skinColorIndex,
  scale: scaleProp,
  style,
  action = 'idle',
  actionFrameIndex,
  frameStepOverride,
  facing = 'right',
  anchorToBody = false,
  poseFamilySlotOverride,
  poseFrameIndexOverride,
}) => {
  const scale = scaleProp ?? DEFAULT_SCALE;
  const [localFrameIndex, setLocalFrameIndex] = useState(0);

  useEffect(() => {
    if (actionFrameIndex !== undefined || frameStepOverride !== undefined) {
      return;
    }

    const familySlot = ACTION_SLOT_BY_ACTION[action];
    const frameCount = BODY_FAMILY_ASSETS[familySlot].frameCount;
    const timer = setInterval(() => {
      setLocalFrameIndex((current) => (current + 1) % frameCount);
    }, LOCAL_ANIM_MS[action]);

    return () => clearInterval(timer);
  }, [action, actionFrameIndex, frameStepOverride]);

  const familySlot = poseFamilySlotOverride ?? ACTION_SLOT_BY_ACTION[action];
  const genderKey = genderIndex === 0 ? 'male' : 'female';
  const currentFrameIndex = poseFrameIndexOverride ?? actionFrameIndex ?? frameStepOverride ?? localFrameIndex;
  const bodyPalette = SKIN_COLOR_OPTIONS[skinColorIndex] ?? SKIN_COLOR_OPTIONS[0];
  const hairPalette = HAIR_COLOR_OPTIONS[hairColorIndex] ?? HAIR_COLOR_OPTIONS[0];

  const hairOption = HAIR_STYLE_OPTIONS[genderKey][hairIndex] ?? HAIR_STYLE_OPTIONS[genderKey][0];
  const hairAssetsBySlot = useMemo<Record<ActionFamilySlot, AssetRegistryEntry>>(() => ({
    0: getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + 0),
    1: getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + 1),
    2: getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + 2),
    3: getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + 3),
    4: getFamilyAsset(ASSET_REGISTRY, hairOption.baseImageId + 4),
  }), [hairOption.baseImageId]);

  const bodySourcesBySlot = useBodySourcesBySlot(bodyPalette.toColors);
  const hairSourcesBySlot = useHairSourcesBySlot(hairAssetsBySlot, hairPalette.toColors);

  const bodySource = bodySourcesBySlot[familySlot];
  const hairSource = familySlot === 0
    ? (HAIR_COLOR_SOURCES[hairOption.baseImageId]?.[hairColorIndex] ?? hairSourcesBySlot[0])
    : hairSourcesBySlot[familySlot];

  const layout = useMemo(() => buildCreateCharacterLayout(
    {
      genderIndex,
      faceIndex,
      hairIndex,
      hairColorIndex,
      skinColorIndex,
    },
    familySlot,
    currentFrameIndex,
    { bodySource, hairSource },
  ), [
    bodySource,
    currentFrameIndex,
    faceIndex,
    familySlot,
    genderIndex,
    hairColorIndex,
    hairIndex,
    hairSource,
    skinColorIndex,
  ]);

  const canvasWidth = layout.width * scale;
  const bodyAnchorX = anchorToBody ? layout.bodyX : 0;

  return (
    <View
      style={[
        styles.previewSpriteCanvas,
        { width: canvasWidth, height: layout.height * scale },
        style,
      ]}
    >
      {layout.layers.map((layer) => {
        const frameLeft = (layer.x - bodyAnchorX) * scale;
        const layerWidth = layer.frameWidth * scale;
        const sourceX = ((layer.cropX ?? 0) + layer.sourceIndex * (layer.frameStride ?? layer.frameWidth)) * scale;

        const layerLeft = facing === 'right'
          ? canvasWidth - frameLeft - layerWidth
          : frameLeft;

        return (
          <View
            key={layer.key}
            style={{
              position: 'absolute',
              left: layerLeft,
              top: layer.y * scale,
              width: layerWidth,
              height: layer.frameHeight * scale,
              overflow: 'hidden',
              zIndex: layer.zIndex,
              transform: facing === 'right' ? [{ scaleX: -1 }] : undefined,
            }}
          >
            <Image
              source={layer.source}
              resizeMode="stretch"
              style={{
                position: 'absolute',
                left: -sourceX,
                top: -((layer.cropY ?? 0) * scale),
                width: layer.sourceWidth * scale,
                height: layer.sourceHeight * scale,
              }}
            />
          </View>
        );
      })}
    </View>
  );
};
