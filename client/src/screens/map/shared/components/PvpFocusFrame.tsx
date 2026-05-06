/**
 * PvpFocusFrame - Renders 4-corner focus frame from /focusmovechess1
 *
 * Java evidence: pc.java lines 185-192 (pc.e function)
 * 
 * ```java
 * public static void e(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
 *     int n7 = i.getWidth() - 7;  // i = "/focusmovechess1" image
 *     int n8 = i.getHeight() - 7;
 *     cw.a(graphics, i, 0, 0, 7, 7, n2 + n6, n3 + n6, 20);       // top-left
 *     cw.a(graphics, i, n7, 0, 7, 7, n2 + n4 - n6, n3 + n6, 24); // top-right
 *     cw.a(graphics, i, 0, n8, 7, 7, n2 + n6, n3 + n5 - n6, 36); // bottom-left
 *     cw.a(graphics, i, n7, n8, 7, 7, n2 + n4 - n6, n3 + n5 - n6, 40); // bottom-right
 * }
 * ```
 * 
 * Called from ew.java:63 with default n6=0 (no inset):
 * ```java
 * pc.e(graphics, n2 - 2, n3, this.e(), this.f());
 * ```
 * 
 * Which delegates to pc.java:275-276:
 * ```java
 * public static void e(Graphics graphics, int n2, int n3, int n4, int n5) {
 *     pc.b(graphics, n2, n3, n4, n5, true);
 * }
 * ```
 * 
 * Then pc.java:271-272:
 * ```java
 * public static void b(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2) {
 *     pc.a(graphics, n2, n3, n4, n5, bl2, 0xFFFFFF);
 * }
 * ```
 * 
 * Finally pc.java:247-268 draws gradient fill + optional scroll indicator.
 * 
 * For PvP arena row selection, we only need the 4 corner sprites from pc.e(...)
 * at lines 185-192, which crops 7x7px corners from /focusmovechess1 (28x28px).
 * 
 * Implementation: Reuse pattern from InventoryCellView.tsx FocusFrame component.
 */

import React from 'react';
import { Image, StyleSheet, View } from 'react-native';

const FOCUS_MOVE_CHESS_ASSET = require('../../../../../assets/battle/08_focus_cursor/focusmovechess1.png');

// Asset dimensions from client/assets/battle/index.csv and inventory evidence
const FOCUS_ASSET = {
  width: 28,
  height: 28,
  cornerSize: 7, // Java: crops 7x7 from each corner
};

interface PvpFocusFrameProps {
  /**
   * Inset offset for corner positioning.
   * Java default: n6 = 0 (corners at edges)
   * Inventory uses 0, PvP arena row uses 0 per ew.java:63
   */
  inset?: number;
}

/**
 * Renders 4 corner sprites cropped from /focusmovechess1 spritesheet.
 * Matches Java pc.e() algorithm: crop 7x7px from each corner of 28x28px image.
 * 
 * Java evidence:
 * - pc.java:185-192: draws 4 corners with cw.a(graphics, i, srcX, srcY, 7, 7, dstX, dstY, anchor)
 * - ew.java:63: calls pc.e(graphics, n2 - 2, n3, this.e(), this.f()) for selected row
 * - Anchors: 20=top-left, 24=top-right, 36=bottom-left, 40=bottom-right
 */
export const PvpFocusFrame: React.FC<PvpFocusFrameProps> = ({ inset = 0 }) => {
  const corner = FOCUS_ASSET.cornerSize;
  const sheetWidth = FOCUS_ASSET.width;
  const sheetHeight = FOCUS_ASSET.height;
  const sourceRight = FOCUS_ASSET.width - FOCUS_ASSET.cornerSize;
  const sourceBottom = FOCUS_ASSET.height - FOCUS_ASSET.cornerSize;

  return (
    <View pointerEvents="none" style={styles.frame}>
      {/* Top-left corner: crop (0, 0, 7, 7) */}
      <View style={[styles.cornerClip, { left: inset, top: inset, width: corner, height: corner }]}>
        <Image
          source={FOCUS_MOVE_CHESS_ASSET}
          style={[styles.cornerSheet, { width: sheetWidth, height: sheetHeight, left: 0, top: 0 }]}
        />
      </View>

      {/* Top-right corner: crop (21, 0, 7, 7) */}
      <View style={[styles.cornerClip, { right: inset, top: inset, width: corner, height: corner }]}>
        <Image
          source={FOCUS_MOVE_CHESS_ASSET}
          style={[styles.cornerSheet, { width: sheetWidth, height: sheetHeight, left: -sourceRight, top: 0 }]}
        />
      </View>

      {/* Bottom-left corner: crop (0, 21, 7, 7) */}
      <View style={[styles.cornerClip, { left: inset, bottom: inset, width: corner, height: corner }]}>
        <Image
          source={FOCUS_MOVE_CHESS_ASSET}
          style={[styles.cornerSheet, { width: sheetWidth, height: sheetHeight, left: 0, top: -sourceBottom }]}
        />
      </View>

      {/* Bottom-right corner: crop (21, 21, 7, 7) */}
      <View style={[styles.cornerClip, { right: inset, bottom: inset, width: corner, height: corner }]}>
        <Image
          source={FOCUS_MOVE_CHESS_ASSET}
          style={[styles.cornerSheet, { width: sheetWidth, height: sheetHeight, left: -sourceRight, top: -sourceBottom }]}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  frame: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 10,
  },
  cornerClip: {
    position: 'absolute',
    overflow: 'hidden',
  },
  cornerSheet: {
    position: 'absolute',
  },
});
