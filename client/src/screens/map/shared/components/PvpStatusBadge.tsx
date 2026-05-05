/**
 * PvpStatusBadge - Renders status icons from /olaicons spritesheet
 *
 * Evidence: pc.java lines 36-65, 240-245
 *
 * Java uses pc.d(graphics, iconIndex, x, y, anchor) to render icons from
 * the /olaicons spritesheet. The spritesheet contains 25 icons indexed 0-24
 * with coordinate arrays:
 *   - n[]: x positions of each icon in spritesheet
 *   - g[]: widths of each icon
 *   - p[]: heights of each icon
 *
 * For PvP arena rows (ew.java), the status badge shows:
 *   - alive (currentHp > 0): icon indicating available for challenge
 *   - dead (currentHp <= 0): icon indicating unavailable
 *
 * Based on Java badge area (~28px wide badge, small font 10-11px) and
 * J2ME icon conventions, the two smallest icons are used for status:
 *   - Index 0: 9x9px - alive/available indicator
 *   - Index 1: 15x15px - dead/unavailable indicator
 *
 * Implementation: Use ImageBackground with absolute positioning to crop
 * the spritesheet at runtime. This matches Java's drawRegion behavior.
 */

import React, { memo } from 'react';
import { StyleSheet, View, Image } from 'react-native';

// Sprite coordinate data from pc.java evidence
// n[] = x positions in spritesheet, g[] = widths, p[] = heights
export const OLA_ICON_COORDS: {
  x: number;
  y: number;
  width: number;
  height: number;
}[] = [
  { x: 0,  y: 0,  width: 9,  height: 9  },  // 0
  { x: 9,  y: 0,  width: 15, height: 15 },  // 1
  { x: 24, y: 0,  width: 9,  height: 15 },  // 2
  { x: 33, y: 0,  width: 10, height: 12 },  // 3
  { x: 43, y: 0,  width: 16, height: 16 },  // 4
  { x: 59, y: 0,  width: 8,  height: 11 },  // 5
  { x: 67, y: 0,  width: 6,  height: 10 },  // 6
  { x: 73, y: 0,  width: 16, height: 16 },  // 7
  { x: 89, y: 0,  width: 16, height: 16 },  // 8
  { x: 105,y: 0,  width: 16, height: 16 },  // 9
  { x: 121,y: 0,  width: 16, height: 16 },  // 10
  { x: 137,y: 0,  width: 15, height: 13 },  // 11
  { x: 152,y: 0,  width: 11, height: 15 },  // 12
  { x: 163,y: 0,  width: 16, height: 16 },  // 13
  { x: 179,y: 0,  width: 23, height: 20 },  // 14
  { x: 202,y: 0,  width: 8,  height: 8  },  // 15
  { x: 210,y: 0,  width: 11, height: 10 },  // 16
  { x: 221,y: 0,  width: 8,  height: 14 },  // 17
  { x: 229,y: 0,  width: 14, height: 8  },  // 18
  { x: 243,y: 0,  width: 16, height: 16 },  // 19
  { x: 259,y: 0,  width: 16, height: 16 },  // 20
  { x: 275,y: 0,  width: 16, height: 16 },  // 21
  { x: 291,y: 0,  width: 14, height: 14 },  // 22
  { x: 305,y: 0,  width: 10, height: 11 },  // 23
  { x: 315,y: 0,  width: 6,  height: 12 },  // 24
];

// Status type for PvP arena rows
export type PvpStatusType = 'alive' | 'dead';

// Mapping from status to icon index (best-effort based on size matching)
// alive: index 0 (9x9) - small indicator icon
// dead: index 1 (15x15) - larger unavailable indicator  
const STATUS_TO_ICON_INDEX: Record<PvpStatusType, number> = {
  alive: 0,
  dead: 1,
};

interface PvpStatusBadgeProps {
  status: PvpStatusType;
  size?: number; // Override default badge size
}

/**
 * Renders a single status icon from the /olaicons spritesheet.
 * Used in PvP arena lobby rows to indicate opponent availability.
 *
 * Implementation: Use View with overflow:hidden + Image with negative positioning
 * to crop the spritesheet. This simulates Java's drawRegion behavior.
 */
export const PvpStatusBadge: React.FC<PvpStatusBadgeProps> = memo(({
  status,
  size,
}) => {
  const iconIndex = STATUS_TO_ICON_INDEX[status];
  const iconCoord = OLA_ICON_COORDS[iconIndex];
  
  // Use icon's natural size if no override provided
  const displayWidth = size ?? iconCoord.width;
  const displayHeight = size ?? iconCoord.height;
  
  return (
    <View 
      style={[
        styles.container,
        {
          width: displayWidth,
          height: displayHeight,
          overflow: 'hidden',
        }
      ]}
    >
      {/* 
        Crop spritesheet by positioning the full image with negative offset.
        This simulates Java's drawRegion(image, srcX, srcY, width, height, ...).
        The View's overflow:hidden clips everything outside the icon bounds.
      */}
      <Image
        source={require('../../../../../assets/ui/07_ola_icons/olaicons.png')}
        style={{
          position: 'absolute',
          top: -iconCoord.y,
          left: -iconCoord.x,
          width: 321, // Full spritesheet width (last icon x + width = 315 + 6)
          height: 20, // Full spritesheet height (tallest icon = 20px at index 14)
        }}
        resizeMode="stretch"
      />
    </View>
  );
});

PvpStatusBadge.displayName = 'PvpStatusBadge';

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
  },
});
