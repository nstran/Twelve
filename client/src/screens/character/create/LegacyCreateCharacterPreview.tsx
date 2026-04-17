import React, { useEffect, useMemo, useState } from 'react';
import { Image, type ImageSourcePropType, View } from 'react-native';
import { styles } from './CreateCharacterScreen.styles';
import {
  BODY_SHEET,
  DEFAULT_OVERLAY,
  GENDER_OPTIONS,
  SLOT_ZERO_META,
  type LegacyFrame,
  type LegacyImageOption,
  type LegacySlotMeta,
} from './legacyCatalog';

interface LegacyCreateCharacterPreviewProps {
  genderIndex: number;
  faceIndex: number;
  hairIndex: number;
  hairColorIndex: number;
}

interface LayerRect {
  key: string;
  source: ImageSourcePropType;
  sourceWidth: number;
  sourceHeight: number;
  frameWidth: number;
  frameHeight: number;
  sourceIndex: number;
  x: number;
  y: number;
  zIndex: number;
  tintColor?: string;
}

const BODY_FRAME_WIDTH = BODY_SHEET.width / 2;
const BODY_FRAME_HEIGHT = BODY_SHEET.height;

function resolveMetaRect(
  key: string,
  asset: Pick<LegacyImageOption, 'source' | 'width' | 'height'>,
  meta: LegacySlotMeta,
  frameStep: number,
  zIndex: number,
  tintColor?: string,
): LayerRect {
  const frameWidth = asset.width / meta.frameWidthDivisor;
  const frame: LegacyFrame = meta.frames[frameStep % meta.frames.length];

  return {
    key,
    source: asset.source,
    sourceWidth: asset.width,
    sourceHeight: asset.height,
    frameWidth,
    frameHeight: asset.height,
    sourceIndex: frame.sourceIndex,
    x: frame.xOffset,
    y: frame.yOffset,
    zIndex,
    tintColor,
  };
}

export const LegacyCreateCharacterPreview: React.FC<LegacyCreateCharacterPreviewProps> = ({
  genderIndex,
  faceIndex,
  hairIndex,
  hairColorIndex,
}) => {
  const [frameStep, setFrameStep] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setFrameStep((current) => (current + 1) % 2);
    }, 420);

    return () => clearInterval(timer);
  }, []);

  const gender = GENDER_OPTIONS[genderIndex] ?? GENDER_OPTIONS[0];

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

    const genderRect = resolveMetaRect('gender', gender, SLOT_ZERO_META[gender.metaId], frameStep, 4);
    const overlayRect = resolveMetaRect('overlay', DEFAULT_OVERLAY, SLOT_ZERO_META[DEFAULT_OVERLAY.metaId], frameStep, 5);

    const layers = [bodyRect, genderRect, overlayRect];

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
  }, [frameStep, gender]);

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
              left: -(layer.sourceIndex * layer.frameWidth) * 2.2,
              top: 0,
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
