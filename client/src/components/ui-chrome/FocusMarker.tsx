import React, { useEffect, useMemo, useState } from 'react';
import { Image, StyleSheet, View } from 'react-native';

const FOCUS_TAB_ASSET = require('../../../assets/ui/10_focus_confirmed/focustab.png');
const ARROW_FOCUS_1_ASSET = require('../../../assets/ui/10_focus_confirmed/arrowfocus1.png');
const ARROW_FOCUS_2_ASSET = require('../../../assets/ui/09_focus_candidate/arrowfocus2.png');

export type FocusMarkerKind = 'tab' | 'arrow';

export interface FocusMarkerTarget {
  x: number;
  y: number;
  w: number;
  h: number;
}

export interface FocusMarkerProps {
  kind: FocusMarkerKind;
  target: FocusMarkerTarget;
  scale?: number;
  arrowWidth?: number;
  arrowHeight?: number;
  tabHeight?: number;
  animate?: boolean;
}

export const FocusMarker: React.FC<FocusMarkerProps> = ({
  kind,
  target,
  scale = 1,
  arrowWidth = 10,  // Actual asset: arrowfocus1.png is 10x7px (verified via PIL)
  arrowHeight = 7,
  tabHeight = 40,   // Actual asset: focustab.png is 41x40px (verified via PIL)
  animate = true,
}) => {
  const [frame, setFrame] = useState(0);

  useEffect(() => {
    if (!animate || kind !== 'arrow') {
      return undefined;
    }

    const id = setInterval(() => {
      setFrame((current) => (current === 0 ? 1 : 0));
    }, 180);

    return () => clearInterval(id);
  }, [animate, kind]);

  const arrowSource = useMemo(() => {
    if (!animate) {
      return ARROW_FOCUS_1_ASSET;
    }
    return frame === 0 ? ARROW_FOCUS_1_ASSET : ARROW_FOCUS_2_ASSET;
  }, [animate, frame]);

  if (kind === 'tab') {
    return (
      <Image
        source={FOCUS_TAB_ASSET}
        style={[
          styles.marker,
          {
            left: target.x * scale,
            top: target.y * scale,
            width: target.w * scale,
            height: tabHeight * scale,
          },
        ]}
        resizeMode="stretch"
      />
    );
  }

  return (
    <View
      pointerEvents="none"
      style={[
        styles.marker,
        {
          left: (target.x - arrowWidth) * scale,
          top: (target.y + (target.h - arrowHeight) / 2) * scale,
          width: arrowWidth * scale,
          height: arrowHeight * scale,
        },
      ]}
    >
      <Image source={arrowSource} style={styles.fullImage} resizeMode="stretch" />
    </View>
  );
};

const styles = StyleSheet.create({
  marker: {
    position: 'absolute',
  },
  fullImage: {
    position: 'absolute',
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',
  },
});
