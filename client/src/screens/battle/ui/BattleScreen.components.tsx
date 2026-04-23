import React, { useEffect, useRef, useState } from 'react';
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
  GEM_CRYSTAL_OVERLAY,
  GemType,
  getHiddenGemAsset,
  TOTAL_FRAMES,
  VisibleGemType,
  getGemRenderType,
  getGemSheet,
  isCrystalGem,
  isHiddenGem,
} from '../core';

const GEM_NATIVE_SIZE = 28;
const FOCUS_RENDER_SCALE = 1.24;
const FOCUS_OFFSET_X = -1;
const FOCUS_OFFSET_Y = 1;
const ARROW_TOP_INSET = 1.1;
const ARROW_BOTTOM_INSET = 1.1;
const ARROW_LEFT_INSET = 0.5;
const ARROW_RIGHT_INSET = 6;
const FIRE_SWORD_MARK_SOURCE = require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1001_it/runtime_png/1001001.png');
const FIRE_SWORD_MARK_FRAME_COUNT = 5;
const JAVA_TICK_MS = 40;
const FIRE_SWORD_MARK_FRAME_DURATION_MS = 3 * JAVA_TICK_MS;
const FIRE_SWORD_MARK_TOTAL_MS = FIRE_SWORD_MARK_FRAME_COUNT * FIRE_SWORD_MARK_FRAME_DURATION_MS;
const FIRE_SWORD_MARK_STATE = 10 as const;

// These sprites were authored on 28x28 frames, but some visible pixels are not
// perfectly centered inside the transparent frame. Nudge them by their native
// delta so they sit optically centered in each board cell like the Java client.
const GEM_VISUAL_OFFSETS: Record<VisibleGemType, { x: number; y: number }> = {
  0: { x: 1, y: -1 },
  1: { x: 0, y: -0.5 },
  2: { x: 0, y: 0 },
  3: { x: 0, y: -0.5 },
  4: { x: 0.5, y: -0.5 },
  5: { x: 0, y: 0 },
  6: { x: 0, y: -0.5 },
  8: { x: 0, y: 0 },
};

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
  fireSwordBaseGemType,
  fireSwordMarkTrigger,
  onPress,
}: {
  gemType: GemType;
  frameIndex: number;
  size: number;
  selected: boolean;
  focusVariant?: 'player' | 'enemy';
  fireSwordBaseGemType?: GemType | null;
  fireSwordMarkTrigger?: number;
  onPress: () => void;
}) => {
  const renderType = getGemRenderType(gemType);
  const visualOffset = GEM_VISUAL_OFFSETS[renderType];
  const spriteOffsetX = Math.round(visualOffset.x * size / GEM_NATIVE_SIZE);
  const spriteOffsetY = Math.round(visualOffset.y * size / GEM_NATIVE_SIZE);
  const focusSize = Math.round(size * FOCUS_RENDER_SCALE);
  const focusInset = Math.floor((size - focusSize) / 2);
  const focusLeft = focusInset + Math.round(FOCUS_OFFSET_X * size / 28);
  const focusTop = focusInset + Math.round(FOCUS_OFFSET_Y * size / 28);
  const [fireSwordMarkFrame, setFireSwordMarkFrame] = useState(0);
  const [showFireSwordMarkAnim, setShowFireSwordMarkAnim] = useState(false);
  const previousMarkTriggerRef = useRef(fireSwordMarkTrigger ?? 0);
  const pendingFireSwordMarkStart =
    gemType === FIRE_SWORD_MARK_STATE &&
    (fireSwordMarkTrigger ?? 0) > previousMarkTriggerRef.current;
  const fireSwordBaseGemTypeResolved =
    gemType === FIRE_SWORD_MARK_STATE &&
    fireSwordBaseGemType !== undefined && fireSwordBaseGemType !== null
      ? fireSwordBaseGemType
      : gemType;
  const fireSwordBaseFrameIndex = fireSwordBaseGemTypeResolved === gemType ? frameIndex : 0;

  useEffect(() => {
    if (gemType !== FIRE_SWORD_MARK_STATE) {
      setShowFireSwordMarkAnim(false);
      setFireSwordMarkFrame(0);
      return undefined;
    }

    const nextTrigger = fireSwordMarkTrigger ?? 0;
    if (nextTrigger <= 0 || previousMarkTriggerRef.current === nextTrigger) {
      return undefined;
    }
    previousMarkTriggerRef.current = nextTrigger;

    setShowFireSwordMarkAnim(true);
    setFireSwordMarkFrame(0);

    const startedAt = Date.now();
    const timer = setInterval(() => {
      const elapsedMs = Date.now() - startedAt;
      const frameIndex = Math.min(
        FIRE_SWORD_MARK_FRAME_COUNT - 1,
        Math.floor(elapsedMs / FIRE_SWORD_MARK_FRAME_DURATION_MS),
      );

      setFireSwordMarkFrame(frameIndex);

      if (elapsedMs >= FIRE_SWORD_MARK_TOTAL_MS) {
        clearInterval(timer);
        setShowFireSwordMarkAnim(false);
      }
    }, JAVA_TICK_MS);

    return () => clearInterval(timer);
  }, [fireSwordMarkTrigger, gemType]);

  const showCrystalOverlay = isCrystalGem(gemType) && gemType !== FIRE_SWORD_MARK_STATE;
  const isFireSwordFinalFrame =
    showFireSwordMarkAnim && fireSwordMarkFrame >= FIRE_SWORD_MARK_FRAME_COUNT - 1;
  const showFireSwordBaseGem =
    gemType !== FIRE_SWORD_MARK_STATE ||
    pendingFireSwordMarkStart ||
    (showFireSwordMarkAnim && !isFireSwordFinalFrame);
  const showPersistedFireSwordGem =
    gemType === FIRE_SWORD_MARK_STATE &&
    !pendingFireSwordMarkStart &&
    (!showFireSwordMarkAnim || isFireSwordFinalFrame);

  return (
    <TouchableOpacity
      activeOpacity={0.8}
      onPress={onPress}
      style={[styles.base, { width: size, height: size }]}
    >
      <View style={{
        width: size,
        height: size,
        overflow: 'hidden',
        position: 'absolute',
        top: spriteOffsetY,
        left: spriteOffsetX,
      }}>
        {isHiddenGem(gemType) && (
          <Image
            source={getHiddenGemAsset(gemType)}
            style={{
              position: 'absolute',
              width: size,
              height: size,
              opacity: 0.9,
            }}
            resizeMode="contain"
          />
        )}
        {showFireSwordBaseGem && (
          <Image
            source={getGemSheet(fireSwordBaseGemTypeResolved)}
            style={{
              width: size * TOTAL_FRAMES,
              height: size,
              transform: [{ translateX: -fireSwordBaseFrameIndex * size }],
            }}
            resizeMode="stretch"
          />
        )}
        {showPersistedFireSwordGem && (
          <Image
            source={getGemSheet(gemType)}
            style={{
              position: 'absolute',
              width: size * TOTAL_FRAMES,
              height: size,
              transform: [{ translateX: -frameIndex * size }],
            }}
            resizeMode="stretch"
          />
        )}
        {showCrystalOverlay && (
          <Image
            source={GEM_CRYSTAL_OVERLAY}
            style={{
              position: 'absolute',
              width: size,
              height: size,
              opacity: 0.92,
            }}
            resizeMode="stretch"
          />
        )}
        {showFireSwordMarkAnim && (
          <View
            style={{
              position: 'absolute',
              width: size,
              height: size,
              overflow: 'hidden',
            }}
          >
            <Image
              source={FIRE_SWORD_MARK_SOURCE}
              style={{
                width: size * FIRE_SWORD_MARK_FRAME_COUNT,
                height: size,
                transform: [{ translateX: -fireSwordMarkFrame * size }],
              }}
              resizeMode="stretch"
            />
          </View>
        )}
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
