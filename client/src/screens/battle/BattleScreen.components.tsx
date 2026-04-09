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
  ARROW_IMG,
  FOCUS_IMG,
  GEM_SHEETS,
  GemType,
  TOTAL_FRAMES,
} from './BattleScreen.shared';

const GEM_RENDER_SCALE = 0.9;

const ArrowSet: React.FC<{ size: number }> = ({ size }) => {
  const pulse = useRef(new Animated.Value(0)).current;

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
  const outward = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, travel] });
  const inward = pulse.interpolate({ inputRange: [0, 1], outputRange: [0, -travel] });

  return (
    <>
      <Animated.Image
        source={ARROW_IMG}
        resizeMode="contain"
        style={{
          position: 'absolute',
          width: arrowW,
          height: arrowH,
          left: (size - arrowW) / 2,
          top: -arrowH + 1,
          transform: [{ translateY: inward }],
        }}
      />
      <Animated.Image
        source={ARROW_IMG}
        resizeMode="contain"
        style={{
          position: 'absolute',
          width: arrowW,
          height: arrowH,
          left: (size - arrowW) / 2,
          bottom: -arrowH + 1,
          transform: [{ rotate: '180deg' }, { translateY: inward }],
        }}
      />
      <Animated.Image
        source={ARROW_IMG}
        resizeMode="contain"
        style={{
          position: 'absolute',
          width: arrowH,
          height: arrowW,
          top: (size - arrowW) / 2,
          left: -arrowH + 1,
          transform: [{ translateX: inward }],
        }}
      />
      <Animated.Image
        source={ARROW_IMG}
        resizeMode="contain"
        style={{
          position: 'absolute',
          width: arrowH,
          height: arrowW,
          top: (size - arrowW) / 2,
          right: -arrowH + 1,
          transform: [{ translateX: outward }],
        }}
      />
    </>
  );
};

export const GemCell = React.memo(({
  gemType,
  frameIndex,
  size,
  selected,
  onPress,
}: {
  gemType: GemType;
  frameIndex: number;
  size: number;
  selected: boolean;
  onPress: () => void;
}) => {
  const spriteSize = Math.round(size * GEM_RENDER_SCALE);
  const inset = Math.floor((size - spriteSize) / 2);

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
        <View style={styles.focusWrap}>
          <Image source={FOCUS_IMG} style={styles.focus} resizeMode="stretch" />
          <ArrowSet size={size} />
        </View>
      )}
    </TouchableOpacity>
  );
});

export const TBar: React.FC<{ asset: any; fill: number; w: number; h: number }> = ({
  asset,
  fill,
  w,
  h,
}) => (
  <View style={[styles.barBase, { width: w, height: h }]}>
    <Image
      source={asset}
      style={{
        width: w,
        height: h,
        transform: [{ translateX: -w * (1 - Math.max(0, Math.min(1, fill))) }],
      }}
      resizeMode="stretch"
    />
  </View>
);

const styles = StyleSheet.create({
  base: { borderRadius: 2 },
  focusWrap: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    overflow: 'visible',
  },
  focus: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  },
  barBase: {
    backgroundColor: 'transparent',
    overflow: 'hidden',
    borderRadius: 1,
  },
});
