import React, { useEffect, useRef } from 'react';
import {
  Text,
  StyleSheet,
  View,
  Animated,
  Image,
} from 'react-native';
import { LegacyBaseDialog } from './LegacyBaseDialog';
import { CREATE_CHARACTER_ASSETS } from '../screens/character/create/assets';

interface LegacyLoadingDialogProps {
  visible: boolean;
  message?: string;
}

export const LegacyLoadingDialog: React.FC<LegacyLoadingDialogProps> = ({
  visible,
  message = 'Vui lòng chờ...',
}) => {
  const animValue = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    if (visible) {
      Animated.loop(
        Animated.sequence([
          Animated.timing(animValue, {
            toValue: 1,
            duration: 1000,
            useNativeDriver: true,
          }),
          Animated.timing(animValue, {
            toValue: 0,
            duration: 1000,
            useNativeDriver: true,
          }),
        ])
      ).start();
    } else {
      animValue.setValue(0);
    }
  }, [visible, animValue]);

  const star1Scale = animValue.interpolate({
    inputRange: [0, 0.3, 0.6, 1],
    outputRange: [0.5, 1.2, 0.8, 0.5],
  });

  const star2Scale = animValue.interpolate({
    inputRange: [0, 0.2, 0.5, 0.8, 1],
    outputRange: [0.8, 0.5, 1.2, 0.8, 0.8],
  });

  const star3Scale = animValue.interpolate({
    inputRange: [0, 0.4, 0.7, 1],
    outputRange: [1.2, 0.8, 0.5, 1.2],
  });

  return (
    <LegacyBaseDialog visible={visible} width={220}>
      <View style={styles.container}>
        <View style={styles.starsRow}>
          <Animated.Image
            source={CREATE_CHARACTER_ASSETS.star}
            style={[styles.star, { transform: [{ scale: star1Scale }] }]}
          />
          <Animated.Image
            source={CREATE_CHARACTER_ASSETS.star}
            style={[styles.star, styles.starMain, { transform: [{ scale: star2Scale }] }]}
          />
          <Animated.Image
            source={CREATE_CHARACTER_ASSETS.star}
            style={[styles.star, { transform: [{ scale: star3Scale }] }]}
          />
        </View>
        <Text style={styles.message}>{message}</Text>
      </View>
    </LegacyBaseDialog>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingVertical: 10,
    alignItems: 'center',
  },
  starsRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 15,
    height: 30,
  },
  star: {
    width: 12,
    height: 12,
    marginHorizontal: 4,
  },
  starMain: {
    width: 20,
    height: 20,
    marginTop: -10,
  },
  message: {
    color: '#000',
    fontSize: 15,
    fontWeight: '500',
    textAlign: 'center',
  },
});
