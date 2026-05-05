/**
 * PvpCornerFrame - Java-accurate frame component matching ap.java rendering
 *
 * Evidence: ap.java (CornerFrame base class)
 * ap.a() rendering pattern:
 *   1. Fill rect: v.aj (#f2fbff) at offset +3,+3 with size -6,-6
 *   2. Outer rect: v.al (#ffffff) at offset +2,+2 with size -5,-5
 *   3. Inner rect: v.ak (#ef0000) at offset +1,+1 with size -3,-3
 *   4. Corner sprites: /_corner image at 4 corners with transform anchors
 *      - Top-left: (n2, n3) anchor=20
 *      - Top-right: (n2+n4, n3) anchor=24
 *      - Bottom-left: (n2, n3+n5) anchor=36
 *      - Bottom-right: (n2+n4, n3+n5) anchor=40
 *
 * Fix: Use nested View with borders instead of absolute overlay to avoid covering content.
 * Original Java uses f.d("/_corner") with drawRegion transforms.
 */

import React from 'react';
import { View, StyleProp, ViewStyle, Image } from 'react-native';

// Java color palette (from v.java + ap.java computation)
export const JAVA_BG_FILL = '#f2fbff';      // v.aj = computed lighter shade
export const JAVA_BORDER_WHITE = '#ffffff'; // v.al = white outer
export const JAVA_BORDER_RED = '#ef0000';   // v.ak = red inner accent

interface PvpCornerFrameProps {
  children?: React.ReactNode;
  style?: StyleProp<ViewStyle>;
  contentStyle?: StyleProp<ViewStyle>;
  backgroundColor?: string;
  borderOuterColor?: string;
  borderInnerColor?: string;
  showCorners?: boolean;
  cornerAsset?: any;
  cornerWidth?: number;
  cornerHeight?: number;
}

/**
 * PvpCornerFrame - Matches ap.java frame rendering with triple-border pattern
 * 
 * Java ap.a(int n2, int n3, int n4, int n5) algorithm:
 *   fill: (n2+3, n3+3) to (n4-6, n5-6) - v.aj background
 *   rect1: (n2+2, n3+2) to (n4-5, n5-5) - v.al white outer
 *   rect2: (n2+1, n3+1) to (n4-3, n5-3) - v.ak red inner
 *   + corner sprites at 4 corners
 * 
 * Implementation: Use nested View with borderWidth to create the triple-border effect
 * without covering content. The structure is:
 *   Container (bg fill) -> White border (1px) -> Red border (1px) -> Content area
 */
export const PvpCornerFrame: React.FC<PvpCornerFrameProps> = ({
  children,
  style,
  contentStyle,
  backgroundColor = JAVA_BG_FILL,
  borderOuterColor = JAVA_BORDER_WHITE,
  borderInnerColor = JAVA_BORDER_RED,
  showCorners = false,
  cornerAsset,
  cornerWidth = 9,
  cornerHeight = 12,
}) => {
  return (
    <View 
      style={[
        {
          backgroundColor,
          padding: 3, // Java fill offset: fillRect(n2+3, n3+3, ...)
        },
        style
      ]}
    >
      {/* White outer border layer (v.al) - drawRect(n2+2, n3+2, n4-5, n5-5) */}
      <View
        style={{
          borderWidth: 1,
          borderColor: borderOuterColor,
          flex: 1,
        }}
      >
        {/* Red inner border layer (v.ak) - drawRect(n2+1, n3+1, n4-3, n5-3) */}
        <View
          style={{
            borderWidth: 1,
            borderColor: borderInnerColor,
            flex: 1,
            position: 'relative',
          }}
        >
          {/* Content area - matches Java fill area (n2+3 to n4-6) */}
          <View style={[{ flex: 1 }, contentStyle]}>
            {children}
          </View>

          {/* Corner sprites (if showCorners && cornerAsset) */}
          {showCorners && cornerAsset && (
            <>
              {/* Top-left corner: anchor=20 (no transform) */}
              <Image 
                source={cornerAsset}
                style={{
                  position: 'absolute',
                  top: -2, // Offset to align with outer border
                  left: -2,
                  width: cornerWidth,
                  height: cornerHeight,
                }}
                resizeMode="contain"
              />
              {/* Top-right corner: anchor=24 (flip X) */}
              <Image 
                source={cornerAsset}
                style={{
                  position: 'absolute',
                  top: -2,
                  right: -2,
                  width: cornerWidth,
                  height: cornerHeight,
                  transform: [{ scaleX: -1 }],
                }}
                resizeMode="contain"
              />
              {/* Bottom-left corner: anchor=36 (flip Y) */}
              <Image 
                source={cornerAsset}
                style={{
                  position: 'absolute',
                  bottom: -2,
                  left: -2,
                  width: cornerWidth,
                  height: cornerHeight,
                  transform: [{ scaleY: -1 }],
                }}
                resizeMode="contain"
              />
              {/* Bottom-right corner: anchor=40 (flip XY) */}
              <Image 
                source={cornerAsset}
                style={{
                  position: 'absolute',
                  bottom: -2,
                  right: -2,
                  width: cornerWidth,
                  height: cornerHeight,
                  transform: [{ scaleX: -1 }, { scaleY: -1 }],
                }}
                resizeMode="contain"
              />
            </>
          )}
        </View>
      </View>
    </View>
  );
};
