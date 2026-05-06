/**
 * PvpStatusBadge - Renders status icons from /olaicons spritesheet
 *
 * Evidence: pc.java lines 36-65, 240-245; do.java lines 1-15
 *
 * Java uses pc.d(graphics, iconIndex, x, y, anchor) to render icons from
 * the /olaicons spritesheet. The spritesheet contains 25 icons indexed 0-24
 * with coordinate arrays:
 *   - n[]: x positions of each icon in spritesheet
 *   - g[]: widths of each icon
 *   - p[]: heights of each icon
 *
 * For PvP arena rows (ew.java:77), the status badge shows icon based on
 * `do.c` status byte (do.java:7):
 *   - 0 = ready/available (green/idle state)
 *   - 1 = in battle (red/busy state)
 *   - 2 = in arena lobby (yellow/waiting state)
 *   - 3 = recovering/cooldown (gray/disabled state)
 *
 * Icon mapping (best-effort based on size/visual conventions):
 *   - statusByte 0 (ready): index 0 (9x9px) - small green dot
 *   - statusByte 1 (battle): index 1 (15x15px) - larger red indicator
 *   - statusByte 2 (arena): index 2 (9x15px) - vertical bar (waiting)
 *   - statusByte 3 (recovering): index 3 (10x12px) - medium gray icon
 *
 * Implementation: Use View with overflow:hidden + Image with negative positioning
 * to crop the spritesheet. This simulates Java's drawRegion behavior.
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

/**
 * Maps Java status byte (do.c) to /olaicons spritesheet index.
 *
 * Java evidence:
 * - do.java:7 defines `byte c` as status field
 * - ew.java:77 calls `pc.a(graphics, n2, n3, this.i.c)` to render status icon
 * - pc.java:240-245 implements pc.d() which draws icon from /olaicons at index `n2`
 *
 * Status byte values (from do.java toString and server PvpArenaService):
 * - 0 = ready/available for challenge
 * - 1 = currently in battle
 * - 2 = in arena lobby (waiting/browsing)
 * - 3 = recovering/cooldown after battle
 *
 * Icon index mapping (best-effort based on visual size/convention):
 * - 0 → icon 0 (9x9px) - small dot for ready state
 * - 1 → icon 1 (15x15px) - larger indicator for busy/battle
 * - 2 → icon 2 (9x15px) - vertical bar for waiting/arena
 * - 3 → icon 3 (10x12px) - medium icon for recovering
 */
const STATUS_BYTE_TO_ICON_INDEX: Record<number, number> = {
  0: 0, // ready
  1: 1, // battle
  2: 2, // arena
  3: 3, // recovering
};

interface PvpStatusBadgeProps {
  /**
   * Raw status byte from server (do.c field).
   * Java evidence: do.java:7, ew.java:77
   */
  statusByte: number;
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
  statusByte,
  size,
}) => {
  const iconIndex = STATUS_BYTE_TO_ICON_INDEX[statusByte] ?? 0;
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
