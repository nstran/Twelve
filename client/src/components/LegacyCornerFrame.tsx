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
  cornerAsset?: any;
  backgroundPattern?: any;
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
  cornerAsset = ASSET_CORNER,
  backgroundPattern,
}) => {
  const cornerWidth = 9;
  const cornerHeight = 12;

  return (
    <View style={[styles.container, { backgroundColor }, style]}>
      {backgroundPattern && (
        <View style={styles.patternContainer} pointerEvents="none">
           <Image 
            source={backgroundPattern} 
            style={styles.patternBackground} 
            resizeMode="stretch" 
          />
        </View>
      )}
      
      {/* Frame Layer - Background fill lines */}
      <View style={styles.frameFill} pointerEvents="none">
        {showTopFill ? <View style={[styles.topFill, { backgroundColor: innerColor }]} /> : null}
        {showSideFill ? <View style={[styles.leftFill, { backgroundColor: innerColor }]} /> : null}
        {showSideFill ? <View style={[styles.rightFill, { backgroundColor: innerColor }]} /> : null}
      </View>

      {/* Content Layer */}
      <View style={[styles.content, contentStyle]}>
        {children}
      </View>

      {/* Frame Layer - Lines and Corners (On top) */}
      <View style={styles.frameLines} pointerEvents="none">
        <View style={[styles.cornerSlot, styles.cornerTopLeft, { width: cornerWidth, height: cornerHeight }]}>
          <Image source={cornerAsset} style={[styles.cornerImage, { width: cornerWidth, height: cornerHeight }]} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerTopRight, { width: cornerWidth, height: cornerHeight }]}>
          <Image source={cornerAsset} style={[styles.cornerImage, styles.cornerMirrorX, { width: cornerWidth, height: cornerHeight }]} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerBottomLeft, { width: cornerWidth, height: cornerHeight }]}>
          <Image source={cornerAsset} style={[styles.cornerImage, styles.cornerMirrorY, { width: cornerWidth, height: cornerHeight }]} resizeMode="contain" />
        </View>
        <View style={[styles.cornerSlot, styles.cornerBottomRight, { width: cornerWidth, height: cornerHeight }]}>
          <Image source={cornerAsset} style={[styles.cornerImage, styles.cornerMirrorXY, { width: cornerWidth, height: cornerHeight }]} resizeMode="contain" />
        </View>

        <View style={[styles.lineHorizontal, styles.lineOuterTop, { backgroundColor: outerColor }]} />
        <View style={[styles.lineHorizontal, styles.lineOuterBottom, { backgroundColor: outerColor }]} />
        <View style={[styles.lineVertical, styles.lineOuterLeft, { backgroundColor: outerColor }]} />
        <View style={[styles.lineVertical, styles.lineOuterRight, { backgroundColor: outerColor }]} />

        <View style={[styles.lineHorizontal, styles.lineInnerTop, { backgroundColor: innerColor }]} />
        <View style={[styles.lineHorizontal, styles.lineInnerBottom, { backgroundColor: innerColor }]} />
        <View style={[styles.lineVertical, styles.lineInnerLeft, { backgroundColor: innerColor }]} />
        <View style={[styles.lineVertical, styles.lineInnerRight, { backgroundColor: innerColor }]} />

        {/* All sides get the standard Purple accent line */}
        <View style={[styles.lineHorizontal, styles.lineAccentTop, { backgroundColor: accentColor }]} />
        <View style={[styles.lineHorizontal, styles.lineAccentBottom, { backgroundColor: accentColor }]} />
        <View style={[styles.lineVertical, styles.lineAccentLeft, { backgroundColor: accentColor }]} />
        <View style={[styles.lineVertical, styles.lineAccentRight, { backgroundColor: accentColor }]} />
        
        {/* Additional Full Blue accent for the bottom only */}
        <View style={[styles.lineHorizontal, styles.lineAccentBottomBlue, { backgroundColor: '#33CCFF' }]} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
    overflow: 'visible',
    width: '100%',
    height: '100%',
  },
  patternContainer: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1,
    overflow: 'hidden',
    alignItems: 'flex-end',
  },
  patternBackground: {
    width: '50%',
    height: '100%',
    opacity: 1,
  },
  frameFill: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 2,
  },
  content: {
    flex: 1,
    position: 'relative',
    zIndex: 5,
    overflow: 'hidden',
  },
  frameLines: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 10,
  },
  cornerSlot: {
    position: 'absolute',
    zIndex: 11,
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
    bottom: 3.5,
    left: 3,
    right: 3,
  },
  lineAccentBottomBlue: {
    bottom: 1.5,
    left: 1,
    right: 1,
    height: 1.5,
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
