import React, { useEffect, useState } from 'react';
import { Image, Pressable, Text, View } from 'react-native';
import type { ImageSourcePropType, StyleProp, ViewStyle } from 'react-native';
import type { CharacterEquipmentItem, CharacterInventoryItem } from '../../../character/shared';
import { resolveEquipmentIconAsset } from '../../../character/shared';
import { GRID_CELL_H, GRID_CELL_W, STAR_RANKS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';

export type InventoryCellData =
  | { kind: 'equipment'; key: string; entry: CharacterEquipmentItem; equipped: boolean }
  | { kind: 'item'; key: string; item: CharacterInventoryItem }
  | { kind: 'empty'; key: string };

interface InventoryCellViewProps {
  cell: InventoryCellData;
  selected: boolean;
  targetHighlighted?: boolean;
  scale: number;
  style?: StyleProp<ViewStyle>;
  onPress?: () => void;
  resolveItemIcon: (item: CharacterInventoryItem) => ImageSourcePropType | undefined;
}

const BrokenHeart = ({ scale }: { scale: number }) => (
  <View style={[styles.brokenHeart, { width: 8 * scale, height: 7 * scale, right: -1 * scale, bottom: 1 * scale }]}> 
    <Text style={[styles.brokenHeartText, { fontSize: 8 * scale }]}>♥</Text>
  </View>
);

const RankStar = ({ scale }: { scale: number }) => {
  const [frame, setFrame] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setFrame((current) => (current + 1) % 3);
    }, 180);

    return () => clearInterval(timer);
  }, []);

  return (
    <Text
      style={[
        styles.rankStar,
        {
          fontSize: (frame === 1 ? 10 : 9) * scale,
          right: -3 * scale,
          top: -4 * scale,
        },
      ]}
    >
      ★
    </Text>
  );
};

export const InventoryCellView: React.FC<InventoryCellViewProps> = ({
  cell,
  selected,
  targetHighlighted,
  scale,
  style,
  onPress,
  resolveItemIcon,
}) => {
  const sizeStyle = {
    width: GRID_CELL_W * scale,
    height: GRID_CELL_H * scale,
  };

  let icon: ImageSourcePropType | undefined;
  let quantity: number | null = null;
  let enhancement: number | null = null;
  let isBroken = false;
  let showRankStar = false;

  if (cell.kind === 'equipment') {
    icon = resolveEquipmentIconAsset(cell.entry);
    enhancement = cell.entry.level > 0 ? cell.entry.level : null;
    isBroken = cell.entry.maxDurability > 0 && cell.entry.durability <= 0;
    showRankStar = STAR_RANKS.includes(cell.entry.rank as 4 | 7 | 8);
  }

  if (cell.kind === 'item') {
    icon = resolveItemIcon(cell.item);
    quantity = cell.item.quantity > 1 ? cell.item.quantity : null;
  }

  return (
    <Pressable
      onPress={onPress}
      hitSlop={6}
      style={[
        styles.cell,
        sizeStyle,
        cell.kind === 'empty' ? styles.cellEmpty : styles.cellFilled,
        selected && styles.cellSelected,
        targetHighlighted && styles.cellTarget,
        style,
      ]}
    >
      {icon ? (
        <Image source={icon} style={[styles.cellIcon, sizeStyle]} resizeMode="contain" />
      ) : null}
      {cell.kind === 'equipment' && !icon ? (
        <Text style={[styles.missingIconText, { fontSize: 8 * scale }]}>?</Text>
      ) : null}
      {isBroken ? <BrokenHeart scale={scale} /> : null}
      {showRankStar ? <RankStar scale={scale} /> : null}
      {enhancement !== null ? (
        <Text style={[styles.enhancementText, { fontSize: 9 * scale, right: 1 * scale, bottom: -1 * scale }]}>+{enhancement}</Text>
      ) : null}
      {quantity !== null ? (
        <Text style={[styles.quantityText, { fontSize: 9 * scale, right: 1 * scale, bottom: -1 * scale }]}>{quantity}</Text>
      ) : null}
    </Pressable>
  );
};
