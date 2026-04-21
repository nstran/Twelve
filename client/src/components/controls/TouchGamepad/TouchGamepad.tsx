import React from 'react';
import { Image, Pressable, View } from 'react-native';
import { styles } from './TouchGamepad.styles';

type HorizontalDirection = 'left' | 'right';
type DirectionGlyph = 'up' | 'left' | 'right' | 'down';

const ARROW_ICON = require('../../../../assets/ui/10_focus_confirmed/arrowfocus1.png');

interface TouchGamepadProps {
  visible?: boolean;
  onMoveStart: (direction: HorizontalDirection) => void;
  onMoveStop: () => void;
  onAttack: () => void;
  onUpPress: () => void;
  onDownPress?: () => void;
}

interface ButtonProps {
  direction?: DirectionGlyph;
  onPress?: () => void;
  onPressIn?: () => void;
  onPressOut?: () => void;
  disabled?: boolean;
  variant?: 'dpad' | 'center';
}

function GamepadButton({
  direction,
  onPress,
  onPressIn,
  onPressOut,
  disabled = false,
  variant = 'dpad',
}: ButtonProps) {
  const rotation =
    direction === 'up' ? '0deg'
      : direction === 'right' ? '90deg'
        : direction === 'down' ? '180deg'
          : '-90deg';

  return (
    <Pressable
      disabled={disabled}
      onPress={onPress}
      onPressIn={onPressIn}
      onPressOut={onPressOut}
      style={({ pressed }) => [
        styles.button,
        variant === 'center' ? styles.centerButton : styles.dpadButton,
        pressed && styles.buttonPressed,
        disabled && styles.buttonDisabled,
      ]}
    >
      {({ pressed }) => (
        variant === 'center' ? (
          <View
            style={[
              styles.centerDot,
              pressed && styles.centerDotPressed,
            ]}
          />
        ) : (
          <Image
            source={ARROW_ICON}
            style={[
              styles.arrowIcon,
              { transform: [{ rotate: rotation }] },
              pressed && styles.arrowIconPressed,
            ]}
            resizeMode="contain"
          />
        )
      )}
    </Pressable>
  );
}

export const TouchGamepad: React.FC<TouchGamepadProps> = ({
  visible = true,
  onMoveStart,
  onMoveStop,
  onAttack,
  onUpPress,
  onDownPress,
}) => {
  if (!visible) {
    return null;
  }

  return (
    <View style={styles.shell} pointerEvents="box-none">
      <View style={styles.dock}>
        <View style={[styles.cluster, styles.dpadCluster]}>
          <View style={styles.dpadTopRow}>
            <GamepadButton
              direction="up"
              onPress={onUpPress}
            />
          </View>

          <View style={styles.dpadMiddleRow}>
            <GamepadButton
              direction="left"
              onPressIn={() => onMoveStart('left')}
              onPressOut={onMoveStop}
            />

            <GamepadButton
              onPress={onAttack}
              variant="center"
            />

            <GamepadButton
              direction="right"
              onPressIn={() => onMoveStart('right')}
              onPressOut={onMoveStop}
            />
          </View>

          <View style={styles.dpadBottomRow}>
            <GamepadButton
              direction="down"
              onPress={onDownPress}
              disabled={!onDownPress}
            />
          </View>
        </View>
      </View>
    </View>
  );
};
