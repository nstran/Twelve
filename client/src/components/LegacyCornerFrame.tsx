import React from 'react';
import {
  Image,
  StyleSheet,
  View,
  type StyleProp,
  type ViewStyle,
} from 'react-native';

const ASSET_CORNER = require('../../assets/ui_legacy/00_corner_frames/2.png');

interface LegacyCornerFrameProps {
  children?: React.ReactNode;
  style?: StyleProp<ViewStyle>;
  contentStyle?: StyleProp<ViewStyle>;
  backgroundColor?: string;
  outerColor?: string;
  innerColor?: string;
  accentColor?: string;
  showTopFill?: boolean;
  showSideFill?: boolean;
}

export const LegacyCornerFrame: React.FC<LegacyCornerFrameProps> = ({
  children,
  style,
  contentStyle,
  backgroundColor = '#E3F6FF',
  outerColor = '#325BD6',
  innerColor = '#32D6FF',
  accentColor = '#8432FF',
  showTopFill = true,
  showSideFill = true,
}) => {
  return (
    <View style={[styles.container, { backgroundColor }, style]}>
      <View style={styles.frame} pointerEvents="none">
        <View style={[styles.cornerSlot, styles.cornerTopLeft]}>
          <Image source={ASSET_CORNER} style={styles.cornerImage} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerTopRight]}>
          <Image source={ASSET_CORNER} style={[styles.cornerImage, styles.cornerMirrorX]} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerBottomLeft]}>
          <Image source={ASSET_CORNER} style={[styles.cornerImage, styles.cornerMirrorY]} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerBottomRight]}>
          <Image source={ASSET_CORNER} style={[styles.cornerImage, styles.cornerMirrorXY]} resizeMode="contain" />
        </View>

        <View style={[styles.lineHorizontal, styles.lineOuterTop, { backgroundColor: outerColor }]} />
        <View style={[styles.lineHorizontal, styles.lineOuterBottom, { backgroundColor: outerColor }]} />
        <View style={[styles.lineVertical, styles.lineOuterLeft, { backgroundColor: outerColor }]} />
        <View style={[styles.lineVertical, styles.lineOuterRight, { backgroundColor: outerColor }]} />

        {showTopFill ? <View style={[styles.topFill, { backgroundColor: innerColor }]} /> : null}
        {showSideFill ? <View style={[styles.leftFill, { backgroundColor: innerColor }]} /> : null}
        {showSideFill ? <View style={[styles.rightFill, { backgroundColor: innerColor }]} /> : null}

        <View style={[styles.lineHorizontal, styles.lineInnerTop, { backgroundColor: innerColor }]} />
        <View style={[styles.lineHorizontal, styles.lineInnerBottom, { backgroundColor: innerColor }]} />
        <View style={[styles.lineVertical, styles.lineInnerLeft, { backgroundColor: innerColor }]} />
        <View style={[styles.lineVertical, styles.lineInnerRight, { backgroundColor: innerColor }]} />

        <View style={[styles.lineHorizontal, styles.lineAccentTop, { backgroundColor: accentColor }]} />
        <View style={[styles.lineHorizontal, styles.lineAccentBottom, { backgroundColor: accentColor }]} />
        <View style={[styles.lineVertical, styles.lineAccentLeft, { backgroundColor: accentColor }]} />
        <View style={[styles.lineVertical, styles.lineAccentRight, { backgroundColor: accentColor }]} />
      </View>

      <View style={[styles.content, { backgroundColor }, contentStyle]}>
        {children}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
    overflow: 'visible',
  },
  frame: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1,
  },
  content: {
    position: 'relative',
    zIndex: 2,
    overflow: 'hidden',
  },
  cornerSlot: {
    position: 'absolute',
    width: 9,
    height: 12,
    zIndex: 3,
    overflow: 'visible',
  },
  cornerTopLeft: {
    top: 0,
    left: 0,
  },
  cornerTopRight: {
    top: 0,
    right: 0,
  },
  cornerBottomLeft: {
    bottom: 0,
    left: 0,
  },
  cornerBottomRight: {
    bottom: 0,
    right: 0,
  },
  cornerImage: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: 9,
    height: 12,
  },
  cornerMirrorX: {
    transform: [{ scaleX: -1 }],
  },
  cornerMirrorY: {
    transform: [{ scaleY: -1 }],
  },
  cornerMirrorXY: {
    transform: [{ scaleX: -1 }, { scaleY: -1 }],
  },
  lineHorizontal: {
    position: 'absolute',
    height: 1,
    zIndex: 2,
  },
  lineVertical: {
    position: 'absolute',
    width: 1,
    zIndex: 2,
  },
  lineOuterTop: {
    top: 0,
    left: 1,
    right: 1,
  },
  lineOuterBottom: {
    bottom: 0,
    left: 1,
    right: 1,
  },
  lineOuterLeft: {
    left: 0,
    top: 1,
    bottom: 1,
  },
  lineOuterRight: {
    right: 0,
    top: 1,
    bottom: 1,
  },
  topFill: {
    position: 'absolute',
    top: 1,
    left: 1,
    right: 1,
    height: 3,
    zIndex: 1,
  },
  leftFill: {
    position: 'absolute',
    top: 1,
    bottom: 1,
    left: 1,
    width: 2,
    zIndex: 1,
  },
  rightFill: {
    position: 'absolute',
    top: 1,
    bottom: 1,
    right: 1,
    width: 2,
    zIndex: 1,
  },
  lineInnerTop: {
    top: 1,
    left: 1,
    right: 1,
  },
  lineInnerBottom: {
    bottom: 1,
    left: 1,
    right: 1,
  },
  lineInnerLeft: {
    left: 1,
    top: 1,
    bottom: 1,
  },
  lineInnerRight: {
    right: 1.5,
    top: 1,
    bottom: 1,
  },
  lineAccentTop: {
    top: 3.5,
    left: 3,
    right: 3,
  },
  lineAccentBottom: {
    bottom: 3,
    left: 2,
    right: 2,
  },
  lineAccentLeft: {
    left: 2,
    top: 2,
    bottom: 2,
  },
  lineAccentRight: {
    right: 2,
    top: 2,
    bottom: 2,
  },
});
