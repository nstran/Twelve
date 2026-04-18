/**
 * CreateCharacterPreview.tsx
 *
 * Preview nhân vật trong màn tạo nhân vật.
 * Dùng React Native Image thuần để render từng layer.
 *
 * Palette color swap được thực hiện tại BUILD TIME (Python script):
 *   - Hair: HAIR_COLOR_SOURCES[baseImageId][colorIndex] → cùng pose, màu PLTE đã swap
 *   - Body: BODY_SKIN_SHEETS[skinColorIndex] → spritesheet đã tô màu da sẵn
 */

import React, { useEffect, useMemo, useState } from 'react';
import { Image, View, type ImageSourcePropType } from 'react-native';
import { styles } from './CreateCharacterScreen.styles';
import {
  BODY_SHEET,
  BODY_SKIN_SHEETS,
  GENDER_OPTIONS,
  SLOT_ZERO_META,
  HAIR_STYLE_OPTIONS,
  HAIR_COLOR_SOURCES,
  EYE_STYLE_OPTIONS,
  type SlotMeta,
} from './createCatalog';
import { ASSET_REGISTRY, type AssetRegistryEntry } from './assetRegistry';

// ---------------------------------------------------------------------------
// Types
// ---------------------------------------------------------------------------

interface CreateCharacterPreviewProps {
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
}

const SCALE = 2.2;
const BODY_FRAME_W = (BODY_SHEET.width as number) / 2;
const BODY_FRAME_H = BODY_SHEET.height as number;

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

function resolveMetaRect(
  key: string,
  source: ImageSourcePropType,
  sourceWidth: number,
  sourceHeight: number,
  meta: SlotMeta,
  frameStep: number,
  zIndex: number,
  preferFirstFrame = false,
): LayerRect {
  const frameWidth  = sourceWidth / meta.frameWidthDivisor;
  const frame       = meta.frames[frameStep % meta.frames.length];
  const sourceIndex = preferFirstFrame && meta.frameWidthDivisor > 1 ? 0 : frame.sourceIndex;
  return {
    key, source, sourceWidth, sourceHeight,
    frameWidth, frameHeight: sourceHeight,
    sourceIndex,
    x: frame.xOffset, y: frame.yOffset, zIndex,
  };
}

function resolveStaticAssetRect(
  key: string,
  asset: AssetRegistryEntry,
  coloredSource: ImageSourcePropType | undefined,
  meta: SlotMeta,
  frameStep: number,
  zIndex: number,
): LayerRect {
  const frame = meta.frames[frameStep % meta.frames.length];
  return {
    key,
    source:       coloredSource ?? asset.source,   // dùng colored variant nếu có
    sourceWidth:  asset.width,
    sourceHeight: asset.height,
    frameWidth:   asset.cropWidth,
    frameHeight:  asset.cropHeight,
    sourceIndex:  0,
    cropX:        asset.cropX,
    cropY:        asset.cropY,
    x: frame.xOffset,
    y: frame.yOffset,
    zIndex,
  };
}

const DEFAULT_OVERLAY = {
  metaId: 89999,
  source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89900.png'),
  width: 41,
  height: 15,
};

// ---------------------------------------------------------------------------
// Component
// ---------------------------------------------------------------------------

export const CreateCharacterPreview: React.FC<CreateCharacterPreviewProps> = ({
  genderIndex,
  faceIndex,
  hairIndex,
  hairColorIndex,
  skinColorIndex,
}) => {
  const [frameStep, setFrameStep] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => setFrameStep((s) => (s + 1) % 2), 420);
    return () => clearInterval(timer);
  }, []);

  const genderKey = genderIndex === 0 ? 'male' : 'female';
  const gender    = GENDER_OPTIONS[genderIndex] ?? GENDER_OPTIONS[0];

  // ------ Hair ------
  // Luôn dùng baseImageId cho kích thước/crop.
  // Source lấy từ HAIR_COLOR_SOURCES[baseImageId][colorIndex] — cùng pose, khác màu.
  const hairOption      = HAIR_STYLE_OPTIONS[genderKey][hairIndex] ?? HAIR_STYLE_OPTIONS[genderKey][0];
  const hairAsset       = ASSET_REGISTRY[hairOption.baseImageId];
  const hairColoredSources = HAIR_COLOR_SOURCES[hairOption.baseImageId];
  const hairColoredSource  = hairColoredSources?.[hairColorIndex] ?? hairColoredSources?.[0];

  // ------ Eyes ------
  // baseImageId là ảnh tĩnh của bộ mắt — KHÔNG cộng skinColorIndex
  const eyeOption = EYE_STYLE_OPTIONS[genderKey][faceIndex] ?? EYE_STYLE_OPTIONS[genderKey][0];
  const eyeAsset  = ASSET_REGISTRY[eyeOption.baseImageId];

  // ------ Body skin sheet ------
  // BODY_SKIN_SHEETS[skinColorIndex] → spritesheet đã tô đúng tông da
  const bodySource = BODY_SKIN_SHEETS[skinColorIndex] ?? BODY_SKIN_SHEETS[0];

  // ------ Build layer layout ------
  const layout = useMemo(() => {
    const bodyRect: LayerRect = {
      key:          'body',
      source:       bodySource,
      sourceWidth:  BODY_SHEET.width as number,
      sourceHeight: BODY_SHEET.height as number,
      frameWidth:   BODY_FRAME_W,
      frameHeight:  BODY_FRAME_H,
      sourceIndex:  frameStep,
      x: 0, y: 0, zIndex: 1,
    };

    const layers: LayerRect[] = [bodyRect];

    if (eyeAsset) {
      layers.push(resolveMetaRect(
        'eyes',
        eyeAsset.source,
        eyeAsset.width,
        eyeAsset.height,
        SLOT_ZERO_META[eyeOption.metaId],
        frameStep, 2, true,
      ));
    }

    if (hairAsset) {
      layers.push(resolveStaticAssetRect(
        'hair',
        hairAsset,
        hairColoredSource,
        SLOT_ZERO_META[hairOption.metaId],
        frameStep, 3,
      ));
    }

    layers.push(resolveMetaRect(
      'gender',
      gender.source, gender.width, gender.height,
      SLOT_ZERO_META[gender.metaId],
      frameStep, 4, true,
    ));

    layers.push(resolveMetaRect(
      'overlay',
      DEFAULT_OVERLAY.source, DEFAULT_OVERLAY.width, DEFAULT_OVERLAY.height,
      SLOT_ZERO_META[DEFAULT_OVERLAY.metaId],
      frameStep, 5, true,
    ));

    let minX = 0, minY = 0, maxX = BODY_FRAME_W, maxY = BODY_FRAME_H;
    for (const layer of layers) {
      minX = Math.min(minX, layer.x);
      minY = Math.min(minY, layer.y);
      maxX = Math.max(maxX, layer.x + layer.frameWidth);
      maxY = Math.max(maxY, layer.y + layer.frameHeight);
    }

    return {
      width:  maxX - minX,
      height: maxY - minY,
      layers: layers.map((l) => ({ ...l, x: l.x - minX, y: l.y - minY })),
    };
  }, [bodySource, eyeAsset, eyeOption.metaId, hairAsset, hairColoredSource, hairOption.metaId, frameStep, gender]);

  return (
    <View
      style={[
        styles.previewSpriteCanvas,
        { width: layout.width * SCALE, height: layout.height * SCALE },
      ]}
    >
      {layout.layers.map((layer) => (
        <View
          key={layer.key}
          style={{
            position: 'absolute',
            left:     layer.x * SCALE,
            top:      layer.y * SCALE,
            width:    layer.frameWidth  * SCALE,
            height:   layer.frameHeight * SCALE,
            overflow: 'hidden',
            zIndex:   layer.zIndex,
          }}
        >
          <Image
            source={layer.source}
            resizeMode="stretch"
            style={{
              position: 'absolute',
              left: -(((layer.cropX ?? 0) + layer.sourceIndex * layer.frameWidth) * SCALE),
              top:  -((layer.cropY ?? 0) * SCALE),
              width:  layer.sourceWidth  * SCALE,
              height: layer.sourceHeight * SCALE,
            }}
          />
        </View>
      ))}
    </View>
  );
};
