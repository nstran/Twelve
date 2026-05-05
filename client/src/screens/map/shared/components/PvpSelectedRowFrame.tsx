/**
 * PvpSelectedRowFrame - Java-accurate selected arena row frame.
 *
 * Java evidence:
 * - ew.java:63 calls pc.e(graphics, n2 - 2, n3, this.e(), this.f()) for selected rows.
 * - pc.java:275-276 delegates to pc.b(graphics, n2, n3, n4, n5, true).
 * - pc.java:271-272 delegates to pc.a(graphics, n2, n3, n4, n5, true, 0xFFFFFF).
 * - pc.java:247-268 draws the yellow/salmon strips, white center fill, /hiddenphoenix,
 *   and /corner/2 sprites at the four corners.
 */

import React from 'react';
import { Image, StyleSheet, View } from 'react-native';

const CORNER_2_ASSET = require('../../../../../assets/ui/00_corner_frames/2.png');
const HIDDEN_PHOENIX_ASSET = require('../../../../../assets/battle/09_hidden_pieces/hiddenphoenix.png');

export const PvpSelectedRowFrame: React.FC = () => {
  return (
    <View pointerEvents="none" style={styles.root}>
      {/* pc.java:247-261 - selected row strip fill, n6 = 0xFFFFFF */}
      <View style={[styles.horizontalStrip, styles.topOuter]} />
      <View style={[styles.horizontalStrip, styles.bottomOuter]} />
      <View style={[styles.horizontalStrip, styles.topOrange]} />
      <View style={[styles.horizontalStrip, styles.bottomOrange]} />
      <View style={[styles.yellowBand, styles.topYellow]} />
      <View style={[styles.yellowBand, styles.bottomYellow]} />
      <View style={[styles.horizontalStrip, styles.topLightYellow]} />
      <View style={[styles.horizontalStrip, styles.bottomLightYellow]} />
      <View style={styles.centerFill} />

      {/* pc.java:262-268 - optional /hiddenphoenix when bl2 is true. */}
      <Image source={HIDDEN_PHOENIX_ASSET} style={styles.hiddenPhoenix} resizeMode="contain" />

      {/* pc.java:132-135 equivalent corner placement for /corner/2. */}
      <Image source={CORNER_2_ASSET} style={[styles.corner, styles.topLeft]} resizeMode="contain" />
      <Image source={CORNER_2_ASSET} style={[styles.corner, styles.topRight]} resizeMode="contain" />
      <Image source={CORNER_2_ASSET} style={[styles.corner, styles.bottomLeft]} resizeMode="contain" />
      <Image source={CORNER_2_ASSET} style={[styles.corner, styles.bottomRight]} resizeMode="contain" />
    </View>
  );
};

const styles = StyleSheet.create({
  root: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 0,
  },
  horizontalStrip: {
    position: 'absolute',
    left: 0,
    right: 0,
    height: 1,
  },
  yellowBand: {
    position: 'absolute',
    left: 0,
    right: 0,
    height: 2,
    backgroundColor: '#ffff8b', // pc.java:254
  },
  topOuter: {
    top: 0,
    backgroundColor: '#ff8085', // pc.java:248, 16742661
  },
  bottomOuter: {
    bottom: 0,
    backgroundColor: '#ff8085',
  },
  topOrange: {
    top: 1,
    backgroundColor: '#f6b000', // pc.java:251, 16167168
  },
  bottomOrange: {
    bottom: 1,
    backgroundColor: '#f6b000',
  },
  topYellow: {
    top: 2,
  },
  bottomYellow: {
    bottom: 2,
  },
  topLightYellow: {
    top: 4,
    backgroundColor: '#ffffb7', // pc.java:257
  },
  bottomLightYellow: {
    bottom: 4,
    backgroundColor: '#ffffb7',
  },
  centerFill: {
    position: 'absolute',
    left: 0,
    right: 0,
    top: 5,
    bottom: 5,
    backgroundColor: '#ffffff', // pc.java:260 n6 = 0xFFFFFF
  },
  hiddenPhoenix: {
    position: 'absolute',
    right: 0,
    bottom: 2,
    width: 78,
    height: 42,
    opacity: 1,
  },
  corner: {
    position: 'absolute',
    width: 9,
    height: 9,
    zIndex: 2,
  },
  topLeft: {
    left: 0,
    top: 0,
  },
  topRight: {
    right: 0,
    top: 0,
    transform: [{ scaleX: -1 }],
  },
  bottomLeft: {
    left: 0,
    bottom: 0,
    transform: [{ scaleY: -1 }],
  },
  bottomRight: {
    right: 0,
    bottom: 0,
    transform: [{ scaleX: -1 }, { scaleY: -1 }],
  },
});
