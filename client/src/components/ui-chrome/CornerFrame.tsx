/**
 * CornerFrame — 9-slice panel frame component
 * 
 * Java Evidence: ap.java:35-48
 * - Load `/_corner` asset (ap.java:12)
 * - Fill rect with v.aj color (ap.java:36)
 * - Draw 2 border rects with v.al (outer) and v.ak (inner) (ap.java:38-41)
 * - Draw 4 corners using drawRegion with transform flags 0/1/2/3 (ap.java:44-47)
 * - Anchors: 20 (top-left), 24 (top-right), 36 (bottom-left), 40 (bottom-right)
 * 
 * Variants from UI_SYSTEM_RECONSTRUCTION.md:
 * - `_corner` — base panel (ap.java:12)
 * - `1` — ig.java:19 (chat/input panel)
 * - `2` — pc.java:18 (static cache)
 * - `3` — ig.java:13 (soft keyboard)
 * - `4` — fc.java:38 (friend/skill list)
 * - `5` — fc.java:39 (friend/skill list variant)
 * - `cornerskb` — ig.java:18 (soft keyboard wrap)
 */

import React from 'react';
import { View, Image, StyleSheet } from 'react-native';

// Java theme colors from ap.java:36-41 and v.java runtime
const JAVA_PANEL_FILL = 0xF0FBFF; // v.aj — panel fill color
const JAVA_PANEL_OUTER_BORDER = 0xDEF7FF; // v.al — outer border
const JAVA_PANEL_INNER_BORDER = 0x0357A7; // v.ak — inner border

const CORNER_ASSETS = {
  '_corner': require('../../../assets/ui/00_corner_frames/_corner.png'),
  '1': require('../../../assets/ui/00_corner_frames/1.png'),
  '2': require('../../../assets/ui/00_corner_frames/2.png'),
  '3': require('../../../assets/ui/00_corner_frames/3.png'),
  '4': require('../../../assets/ui/00_corner_frames/4.png'),
  '5': require('../../../assets/ui/00_corner_frames/5.png'),
  'cornerskb': require('../../../assets/ui/00_corner_frames/cornerskb.png'),
} as const;

export type CornerFrameVariant = keyof typeof CORNER_ASSETS;

interface CornerFrameProps {
  x: number;
  y: number;
  w: number;
  h: number;
  variant?: CornerFrameVariant;
  scale?: number;
}

/**
 * CornerFrame component — renders 9-slice panel frame matching Java ap.java:35-48
 * 
 * @param x - Logical X position
 * @param y - Logical Y position
 * @param w - Logical width
 * @param h - Logical height
 * @param variant - Corner asset variant (default: '_corner')
 * @param scale - Display scale factor (default: 1)
 */
export const CornerFrame: React.FC<CornerFrameProps> = ({
  x,
  y,
  w,
  h,
  variant = '_corner',
  scale = 1,
}) => {
  const cornerAsset = CORNER_ASSETS[variant];
  
  // Java ap.java:42-43 — corner dimensions from image
  // Actual asset: _corner.png is 15x15px (verified via PIL)
  const cornerSize = 15;
  
  const scaledX = x * scale;
  const scaledY = y * scale;
  const scaledW = w * scale;
  const scaledH = h * scale;
  const scaledCorner = cornerSize * scale;

  return (
    <View style={[styles.container, { left: scaledX, top: scaledY, width: scaledW, height: scaledH }]}>
      {/* ap.java:36 — fill rect with v.aj */}
      <View
        style={[
          styles.fill,
          {
            left: 3 * scale,
            top: 3 * scale,
            width: scaledW - 6 * scale,
            height: scaledH - 6 * scale,
            backgroundColor: `#${JAVA_PANEL_FILL.toString(16).padStart(6, '0')}`,
          },
        ]}
      />

      {/* ap.java:38 — outer border with v.al */}
      <View
        style={[
          styles.border,
          {
            left: 2 * scale,
            top: 2 * scale,
            width: scaledW - 5 * scale,
            height: scaledH - 5 * scale,
            borderColor: `#${JAVA_PANEL_OUTER_BORDER.toString(16).padStart(6, '0')}`,
            borderWidth: scale,
          },
        ]}
      />

      {/* ap.java:40 — inner border with v.ak */}
      <View
        style={[
          styles.border,
          {
            left: 1 * scale,
            top: 1 * scale,
            width: scaledW - 3 * scale,
            height: scaledH - 3 * scale,
            borderColor: `#${JAVA_PANEL_INNER_BORDER.toString(16).padStart(6, '0')}`,
            borderWidth: scale,
          },
        ]}
      />

      {/* ap.java:44 — top-left corner, transform=0 (identity), anchor=20 */}
      <Image
        source={cornerAsset}
        style={[styles.corner, { left: 0, top: 0, width: scaledCorner, height: scaledCorner }]}
        resizeMode="stretch"
      />

      {/* ap.java:45 — top-right corner, transform=2 (flip-H), anchor=24 */}
      <Image
        source={cornerAsset}
        style={[styles.corner, { right: 0, top: 0, width: scaledCorner, height: scaledCorner, transform: [{ scaleX: -1 }] }]}
        resizeMode="stretch"
      />

      {/* ap.java:46 — bottom-left corner, transform=1 (flip-V), anchor=36 */}
      <Image
        source={cornerAsset}
        style={[styles.corner, { left: 0, bottom: 0, width: scaledCorner, height: scaledCorner, transform: [{ scaleY: -1 }] }]}
        resizeMode="stretch"
      />

      {/* ap.java:47 — bottom-right corner, transform=3 (rotate-180), anchor=40 */}
      <Image
        source={cornerAsset}
        style={[styles.corner, { right: 0, bottom: 0, width: scaledCorner, height: scaledCorner, transform: [{ scaleX: -1 }, { scaleY: -1 }] }]}
        resizeMode="stretch"
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
  },
  fill: {
    position: 'absolute',
  },
  border: {
    position: 'absolute',
  },
  corner: {
    position: 'absolute',
  },
});
