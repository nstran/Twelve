import React from 'react';
import { Image, ImageSourcePropType, StyleSheet, View } from 'react-native';
import { TinyNumber, measureTinyNumber } from './TinyNumber';

const BADGE_ASSETS = {
  gtm: require('../../../assets/ui/01_notification_badges/notifygtmicon.png'),
  quest: require('../../../assets/ui/01_notification_badges/questnotifyicon.png'),
  news: require('../../../assets/ui/01_notification_badges/notificationnewsicon.png'),
} as const;

export type NotificationBadgeType = keyof typeof BADGE_ASSETS;

export interface NotificationBadgeProps {
  type: NotificationBadgeType;
  x: number;
  y: number;
  width: number;
  height: number;
  count?: number | string;
  scale?: number;
}

const resolveBadgeAsset = (type: NotificationBadgeType): ImageSourcePropType => BADGE_ASSETS[type];

export const NotificationBadge: React.FC<NotificationBadgeProps> = ({ type, x, y, width, height, count, scale = 1 }) => {
  const countText = count === undefined ? '' : String(count);
  const hasCount = countText.length > 0;
  const countWidth = hasCount ? measureTinyNumber(countText) : 0;

  return (
    <View style={[styles.root, { left: x * scale, top: y * scale, width: width * scale, height: height * scale }]} pointerEvents="none">
      <Image source={resolveBadgeAsset(type)} style={styles.icon} resizeMode="stretch" />
      {hasCount ? (
        <TinyNumber
          value={countText}
          x={Math.max(0, width - countWidth - 1)}
          y={Math.max(0, height - 7)}
          scale={scale}
        />
      ) : null}
    </View>
  );
};

const styles = StyleSheet.create({
  root: {
    position: 'absolute',
  },
  icon: {
    position: 'absolute',
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',
  },
});
