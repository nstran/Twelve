import React, { useEffect, useRef, useState } from 'react';
import { Animated, LayoutChangeEvent, Text, TouchableOpacity, View } from 'react-native';
import { PvpStatusBadge } from './PvpStatusBadge';
import { PvpFocusFrame } from './PvpFocusFrame';
import { styles } from './PvpDialog.styles';
import type { PvpOpponentEntry } from '../../../battle';

interface PvpArenaRowProps {
  opponent: PvpOpponentEntry;
  selected: boolean;
  disabled: boolean;
  onPress: () => void;
}

const formatPvpHonorLine = (level: number, honor: number): string =>
  `Cấp ${Math.max(0, level)}  --  Danh vọng ${Math.max(0, honor)}`;

/**
 * Arena row component with marquee animation for selected row.
 * 
 * Java evidence (ew.java lines 86-104):
 * - Marquee only active when selected AND text overflows
 * - Offset `m` decrements (scroll left) until limit, then increments (scroll right)
 * - Limit: `n2 = availableWidth - textWidth`
 * - Direction reverses at boundaries (0 and limit)
 */
export const PvpArenaRow: React.FC<PvpArenaRowProps> = ({
  opponent,
  selected,
  disabled,
  onPress,
}) => {
  const [textWidth, setTextWidth] = useState(0);
  const [containerWidth, setContainerWidth] = useState(0);
  const marqueeOffset = useRef(new Animated.Value(0)).current;
  const directionRef = useRef<'left' | 'right'>('left');

  const primaryText = opponent.statusMessage
    ? `${opponent.username} - ${opponent.statusMessage}`
    : opponent.username;

  // Java: availableWidth = this.e() - 25 - 2
  // In RN: containerWidth - badge(23) - padding(2) = containerWidth - 25
  const availableWidth = containerWidth - 25;
  const shouldMarquee = selected && textWidth > availableWidth && availableWidth > 0;

  useEffect(() => {
    if (!shouldMarquee) {
      marqueeOffset.setValue(0);
      directionRef.current = 'left';
      return;
    }

    // Java: limit = availableWidth - textWidth (negative value)
    const limit = availableWidth - textWidth;

    // Java: tick-based animation (ew.n() called every frame)
    // RN: use timing animation with loop
    const animate = () => {
      const toValue = directionRef.current === 'left' ? limit : 0;
      const duration = Math.abs(toValue - (marqueeOffset as any)._value) * 50; // ~50ms per pixel

      Animated.timing(marqueeOffset, {
        toValue,
        duration,
        useNativeDriver: true,
      }).start(({ finished }) => {
        if (finished && shouldMarquee) {
          // Reverse direction
          directionRef.current = directionRef.current === 'left' ? 'right' : 'left';
          animate();
        }
      });
    };

    animate();

    return () => {
      marqueeOffset.stopAnimation();
    };
  }, [shouldMarquee, textWidth, availableWidth, marqueeOffset]);

  const handleTextLayout = (event: LayoutChangeEvent) => {
    setTextWidth(event.nativeEvent.layout.width);
  };

  const handleContainerLayout = (event: LayoutChangeEvent) => {
    setContainerWidth(event.nativeEvent.layout.width);
  };

  return (
    <TouchableOpacity
      activeOpacity={0.86}
      style={[styles.legacyRow, selected && styles.legacyRowActive]}
      onPress={onPress}
      disabled={disabled}
      onLayout={handleContainerLayout}
    >
      {/* Java: pc.e(graphics, n2 - 2, n3, this.e(), this.f()) - focus frame when selected (ew.java:63) */}
      {selected && <PvpFocusFrame />}

      {/* Java: pc.a(graphics, n2, n3, this.i.c) - status icon at left edge */}
      <View style={[styles.legacyBadge, selected ? styles.legacyBadgeActive : undefined]}>
        <PvpStatusBadge statusByte={opponent.statusByte} />
      </View>

      {/* Java: text starts at n2 + 25 */}
      <View style={[styles.legacyTextWrap, selected ? styles.legacyTextWrapActive : undefined]}>
        {/* Java: this.o.a(graphics, this.j, n4 + (this.g ? this.m : 0), n3, 0) */}
        <View style={{ overflow: 'hidden' }}>
          <Animated.Text
            style={[
              styles.legacyName,
              shouldMarquee && {
                transform: [{ translateX: marqueeOffset }],
              },
            ]}
            numberOfLines={shouldMarquee ? undefined : 1}
            onLayout={handleTextLayout}
          >
            {primaryText}
          </Animated.Text>
        </View>

        {/* Java: this.p.a(graphics, this.k, n4, n3 + 13, 0) - secondary text +13px below */}
        <Text style={styles.legacyMeta} numberOfLines={1}>
          {formatPvpHonorLine(opponent.level, opponent.honor)}
        </Text>
      </View>

      {/* Right label (not in Java ew.java, but useful for UX) */}
      <Text style={styles.legacyStake}>{opponent.stake > 0 ? 'Đánh!' : 'K.Chiến'}</Text>
    </TouchableOpacity>
  );
};
