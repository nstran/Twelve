import React from 'react';
import {
  Image,
  View,
  type StyleProp,
  type ViewStyle,
} from 'react-native';
import { styles } from './CornerFrame.styles';

const ASSET_CORNER = require('../../../../assets/ui_legacy/00_corner_frames/2.png');

interface CornerFrameProps {
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

export const CornerFrame: React.FC<CornerFrameProps> = ({
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
        
        {/* Symmetric Blue accents for both Top and Bottom */}
        <View style={[styles.lineHorizontal, styles.lineAccentTopBlue, { backgroundColor: '#33CCFF' }]} />
        <View style={[styles.lineHorizontal, styles.lineAccentBottomBlue, { backgroundColor: '#33CCFF' }]} />
      </View>
    </View>
  );
};
