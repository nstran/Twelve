import React, { useEffect, useRef } from 'react';
import {
  Animated,
  Easing,
  Image,
  StyleSheet,
  TouchableOpacity,
  View,
} from 'react-native';
import {
  ARROW_ENEMY_IMG,
  ARROW_PLAYER_IMG,
  FOCUS_IMG,
  GEM_SHEETS,
  GemType,
  TOTAL_FRAMES,
} from '../core';

const GEM_RENDER_SCALE = 0.9;
const FOCUS_RENDER_SCALE = 1.24;
const FOCUS_OFFSET_X = -1;
const FOCUS_OFFSET_Y = 1;
const ARROW_TOP_INSET = 1.1;
const ARROW_BOTTOM_INSET = 1.1;
const ARROW_LEFT_INSET = 0.5;
const ARROW_RIGHT_INSET = 6;

const ArrowSet: React.FC<{ size: number; variant?: 'player' | 'enemy' }> = ({
  size,
  variant = 'player',
}) => {
  const pulse = useRef(new Animated.Value(0)).current;
  const arrowAsset = variant === 'enemy' ? ARROW_ENEMY_IMG : ARROW_PLAYER_IMG;

  useEffect(() => {
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(pulse, {
          toValue: 1,
          duration: 400,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(pulse, {
          toValue: 0,
          duration: 400,
          easing: Easing.in(Easing.quad),
          useNativeDriver: true,
        }),
      ]),
    );
    loop.start();
    return () => loop.stop();
  }, [pulse]);

  const arrowW = Math.round(size * 0.34);
  const arrowH = Math.round(arrowW * 7 / 10);
  const travel = Math.round(size * 0.14);
  const upShift = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, -travel] });
  const downShift = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, travel] });
  const leftShift = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, -travel] });
  const rightShift = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, travel] });

  return (
    <>
      <Animated.View
        style={{
          position: 'absolute',
          width: arrowW,
          height: arrowH,
          left: (size - arrowW) / 2,
          top: -arrowH + ARROW_TOP_INSET,
          transform: [{ translateY: upShift }],
        }}
      >
        <Image
          source={arrowAsset}
          resizeMode="contain"
          style={{
            width: arrowW,
            height: arrowH,
          }}
        />
      </Animated.View>
      <Animated.View
        style={{
          position: 'absolute',
          width: arrowW,
          height: arrowH,
          left: (size - arrowW) / 2,
          bottom: -arrowH + ARROW_BOTTOM_INSET,
          transform: [{ translateY: downShift }],
        }}
      >
        <Image
          source={arrowAsset}
          resizeMode="contain"
          style={{
            width: arrowW,
            height: arrowH,
            transform: [{ rotate: '180deg' }],
          }}
        />
      </Animated.View>
      <Animated.View
        style={{
          position: 'absolute',
          width: arrowH,
          height: arrowW,
          top: (size - arrowW) / 2,
          left: -arrowH + ARROW_LEFT_INSET,
          transform: [{ translateX: leftShift }],
        }}
      >
        <Image
          source={arrowAsset}
          resizeMode="contain"
          style={{
            width: arrowW,
            height: arrowH,
            transform: [{ rotate: '-90deg' }],
          }}
        />
      </Animated.View>
      <Animated.View
        style={{
          position: 'absolute',
          width: arrowH,
          height: arrowW,
          top: (size - arrowW) / 2,
          right: -arrowH + ARROW_RIGHT_INSET,
          transform: [{ translateX: rightShift }],
        }}
      >
        <Image
          source={arrowAsset}
          resizeMode="contain"
          style={{
            width: arrowW,
            height: arrowH,
            transform: [{ rotate: '90deg' }],
          }}
        />
      </Animated.View>
    </>
  );
};

export const GemCell = React.memo(({
  gemType,
  frameIndex,
  size,
  selected,
  focusVariant,
  onPress,
}: {
  gemType: GemType;
  frameIndex: number;
  size: number;
  selected: boolean;
  focusVariant?: 'player' | 'enemy';
  onPress: () => void;
}) => {
  const spriteSize = Math.round(size * GEM_RENDER_SCALE);
  const inset = Math.floor((size - spriteSize) / 2);
  const focusSize = Math.round(size * FOCUS_RENDER_SCALE);
  const focusInset = Math.floor((size - focusSize) / 2);
  const focusLeft = focusInset + Math.round(FOCUS_OFFSET_X * size / 28);
  const focusTop = focusInset + Math.round(FOCUS_OFFSET_Y * size / 28);

  return (
    <TouchableOpacity
      activeOpacity={0.8}
      onPress={onPress}
      style={[styles.base, { width: size, height: size }]}
    >
      <View style={{
        width: spriteSize,
        height: spriteSize,
        overflow: 'hidden',
        position: 'absolute',
        top: inset,
        left: inset,
      }}>
        <Image
          source={GEM_SHEETS[gemType]}
          style={{
            width: spriteSize * TOTAL_FRAMES,
            height: spriteSize,
            transform: [{ translateX: -frameIndex * spriteSize }],
          }}
          resizeMode="stretch"
        />
      </View>

      {selected && (
        <View style={[styles.focusWrap, {
          top: focusTop,
          left: focusLeft,
          width: focusSize,
          height: focusSize,
        }]}>
          <Image source={FOCUS_IMG} style={styles.focus} resizeMode="stretch" />
          <ArrowSet size={focusSize} variant={focusVariant} />
        </View>
      )}
    </TouchableOpacity>
  );
});

export const TBar: React.FC<{
  asset: any;
  fill: number;
  w: number;
  h: number;
  direction?: 'ltr' | 'rtl';
}> = ({
  asset,
  fill,
  w,
  h,
  direction = 'ltr',
}) => {
  const clampedFill = Math.max(0, Math.min(1, fill));
  const fillWidth = w * clampedFill;
  const isRTL = direction === 'rtl';

  return (
    <View style={[styles.barBase, { width: w, height: h }]}>
      <View
        style={[
          styles.barClip,
          {
            width: fillWidth,
            height: h,
            left: isRTL ? undefined : 0,
            right: isRTL ? 0 : undefined,
          },
        ]}
      >
        <Image
          source={asset}
          style={{
            width: w,
            height: h,
            transform: isRTL ? [{ translateX: -(w - fillWidth) }] : undefined,
          }}
          resizeMode="stretch"
        />
      </View>
    </View>
  );
};

export const AnimatedTBar: React.FC<{
  asset: any;
  fillAnim: Animated.Value;
  max: number;
  w: number;
  h: number;
  direction?: 'ltr' | 'rtl';
}> = ({
  asset,
  fillAnim,
  max,
  w,
  h,
  direction = 'ltr',
}) => {
  const isRTL = direction === 'rtl';
  const fillWidth = fillAnim.interpolate({
    inputRange: [0, max],
    outputRange: [0, w],
    extrapolate: 'clamp',
  });
  const translateX = fillAnim.interpolate({
    inputRange: [0, max],
    outputRange: [-w, 0],
    extrapolate: 'clamp',
  });

  return (
    <View style={[styles.barBase, { width: w, height: h }]}>
      <Animated.View
        style={[
          styles.barClip,
          {
            width: fillWidth,
            height: h,
            left: isRTL ? undefined : 0,
            right: isRTL ? 0 : undefined,
          },
        ]}
      >
        <Animated.Image
          source={asset}
          style={{
            width: w,
            height: h,
            transform: isRTL ? [{ translateX }] : undefined,
          }}
          resizeMode="stretch"
        />
      </Animated.View>
    </View>
  );
};

const styles = StyleSheet.create({
  base: { borderRadius: 2 },
  focusWrap: {
    position: 'absolute',
    zIndex: 50,
    elevation: 50,
    overflow: 'visible',
    pointerEvents: 'none',
  },
  focus: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
  },
  barBase: {
    backgroundColor: 'transparent',
    borderRadius: 1,
  },
  barClip: {
    position: 'absolute',
    top: 0,
    overflow: 'hidden',
  },
});
