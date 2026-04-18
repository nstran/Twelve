import React, { useEffect, useMemo, useState } from 'react';
import { Image, type ImageSourcePropType, View } from 'react-native';
import { styles } from './CreateCharacterScreen.styles';
import {
  BODY_SHEET,
  DEFAULT_OVERLAY,
  GENDER_OPTIONS,
  SLOT_ZERO_META,
  HAIR_STYLE_OPTIONS,
  EYE_STYLE_OPTIONS,
  type LegacyFrame,
  type LegacySlotMeta,
} from './legacyCatalog';
import { ASSET_REGISTRY, type AssetRegistryEntry } from './assetRegistry';

interface LegacyCreateCharacterPreviewProps {
  genderIndex: number;
  faceIndex: number;
  hairIndex: number;
  hairColorIndex: number;
  skinColorIndex: number;
}

interface LayerRect {
  key: string;
  source: ImageSourcePropType;
  sourceWidth: number;
  sourceHeight: number;
  frameWidth: number;
  frameHeight: number;
  sourceIndex: number;
  cropX?: number;
  cropY?: number;
  x: number;
  y: number;
  zIndex: number;
  tintColor?: string;
}

const BODY_FRAME_WIDTH = BODY_SHEET.width / 2;
const BODY_FRAME_HEIGHT = BODY_SHEET.height;

function resolveMetaRect(
  key: string,
  source: ImageSourcePropType,
  sourceWidth: number,
  sourceHeight: number,
  meta: LegacySlotMeta,
  frameStep: number,
  zIndex: number,
  preferFirstFrame = false,
  tintColor?: string,
): LayerRect {
  const frameWidth = sourceWidth / meta.frameWidthDivisor;
  const frame: LegacyFrame = meta.frames[frameStep % meta.frames.length];
  const sourceIndex = preferFirstFrame && meta.frameWidthDivisor > 1 ? 0 : frame.sourceIndex;

  return {
    key,
    source,
    sourceWidth,
    sourceHeight,
    frameWidth,
    frameHeight: sourceHeight,
    sourceIndex,
    x: frame.xOffset,
    y: frame.yOffset,
    zIndex,
    tintColor,
  };
}

function resolveStaticAssetRect(
  key: string,
  asset: AssetRegistryEntry,
  frameWidth: number,
  meta: LegacySlotMeta,
  frameStep: number,
  zIndex: number,
): LayerRect {
  const frame: LegacyFrame = meta.frames[frameStep % meta.frames.length];

  return {
    key,
    source: asset.source,
    sourceWidth: asset.width,
    sourceHeight: asset.height,
    frameWidth: asset.cropWidth,
    frameHeight: asset.cropHeight,
    sourceIndex: 0,
    cropX: asset.cropX,
    cropY: asset.cropY,
    x: frame.xOffset,
    y: frame.yOffset,
    zIndex,
  };
}

export const LegacyCreateCharacterPreview: React.FC<LegacyCreateCharacterPreviewProps> = ({
  genderIndex,
  faceIndex,
  hairIndex,
  hairColorIndex,
  skinColorIndex,
}) => {
  const [frameStep, setFrameStep] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setFrameStep((current) => (current + 1) % 2);
    }, 420);

    return () => clearInterval(timer);
  }, []);

  const genderKey = genderIndex === 0 ? 'male' : 'female';
  const gender = GENDER_OPTIONS[genderIndex] ?? GENDER_OPTIONS[0];

  const hairOption = HAIR_STYLE_OPTIONS[genderKey][hairIndex] ?? HAIR_STYLE_OPTIONS[genderKey][0];
  const hairImageId = hairOption.previewImageIds[hairColorIndex] ?? hairOption.previewImageIds[0] ?? hairOption.baseImageId;
  const hairAsset = ASSET_REGISTRY[hairImageId];

  const eyeOption = EYE_STYLE_OPTIONS[genderKey][faceIndex] ?? EYE_STYLE_OPTIONS[genderKey][0];
  const eyeImageId = eyeOption.baseImageId + skinColorIndex;
  const eyeAsset = ASSET_REGISTRY[eyeImageId];

  const layout = useMemo(() => {
    const bodyRect: LayerRect = {
      key: 'body',
      source: BODY_SHEET.source,
      sourceWidth: BODY_SHEET.width,
      sourceHeight: BODY_SHEET.height,
      frameWidth: BODY_FRAME_WIDTH,
      frameHeight: BODY_FRAME_HEIGHT,
      sourceIndex: frameStep,
      x: 0,
      y: 0,
      zIndex: 1,
    };

    const layers: LayerRect[] = [bodyRect];

    if (eyeAsset) {
      layers.push(resolveMetaRect('eyes', eyeAsset.source, eyeAsset.width, eyeAsset.height, SLOT_ZERO_META[eyeOption.metaId], frameStep, 2, true));
    }

    if (hairAsset) {
      layers.push(resolveStaticAssetRect('hair', hairAsset, hairOption.width, SLOT_ZERO_META[hairOption.metaId], frameStep, 3));
    }

    layers.push(resolveMetaRect('gender', gender.source, gender.width, gender.height, SLOT_ZERO_META[gender.metaId], frameStep, 4, true));

    layers.push(resolveMetaRect('overlay', DEFAULT_OVERLAY.source, DEFAULT_OVERLAY.width, DEFAULT_OVERLAY.height, SLOT_ZERO_META[DEFAULT_OVERLAY.metaId], frameStep, 5, true));

    let minX = 0;
    let minY = 0;
    let maxX: number = BODY_FRAME_WIDTH;
    let maxY: number = BODY_FRAME_HEIGHT;

    for (const layer of layers) {
      minX = Math.min(minX, layer.x);
      minY = Math.min(minY, layer.y);
      maxX = Math.max(maxX, layer.x + layer.frameWidth);
      maxY = Math.max(maxY, layer.y + layer.frameHeight);
    }

    const width = maxX - minX;
    const height = maxY - minY;

    return {
      width,
      height,
      layers: layers.map((layer) => ({
        ...layer,
        x: layer.x - minX,
        y: layer.y - minY,
      })),
    };
  }, [eyeAsset, eyeOption.metaId, eyeOption.width, frameStep, gender, hairAsset, hairOption.metaId, hairOption.width]);

  return (
    <View
      style={[
        styles.previewSpriteCanvas,
        {
          width: layout.width * 2.2,
          height: layout.height * 2.2,
        },
      ]}
    >
      {layout.layers.map((layer) => (
        <View
          key={layer.key}
          style={{
            position: 'absolute',
            left: layer.x * 2.2,
            top: layer.y * 2.2,
            width: layer.frameWidth * 2.2,
            height: layer.frameHeight * 2.2,
            overflow: 'hidden',
            zIndex: layer.zIndex,
          }}
        >
          <Image
            source={layer.source}
            resizeMode="stretch"
            style={{
              position: 'absolute',
              left: -(((layer.cropX ?? 0) + layer.sourceIndex * layer.frameWidth) * 2.2),
              top: -(layer.cropY ?? 0) * 2.2,
              width: layer.sourceWidth * 2.2,
              height: layer.sourceHeight * 2.2,
              tintColor: layer.tintColor,
            }}
          />
        </View>
      ))}
    </View>
  );
};
