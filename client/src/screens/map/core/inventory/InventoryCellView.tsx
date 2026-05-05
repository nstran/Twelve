import React, { useEffect, useMemo, useState } from 'react';
import { Image, Pressable, View } from 'react-native';
import type { ImageSourcePropType, StyleProp, ViewStyle } from 'react-native';
import type { CharacterEquipmentItem, CharacterInventoryItem } from '../../../character/shared';
import { resolveEquipmentIconAsset } from '../../../character/shared';
import { GRID_CELL_H, GRID_CELL_W, STAR_FRAME_COUNT, STAR_RANKS } from './InventoryLayout';
import { styles } from './InventoryScreen.styles';
import { JAVA_BITMAP_FONT_HEIGHT, JavaBitmapText } from './javaFont/JavaBitmapText';

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
  renderBevel?: boolean;
  onPress?: () => void;
  resolveItemIcon: (item: CharacterInventoryItem) => ImageSourcePropType | undefined;
}

const BROKEN_HEART_ASSET = require('../../../../../assets/ui/08_misc_confirmed/broken_heart.png');
const CRYSTAL_BLUE_ASSET = require('../../../../../assets/battle/03_crystals_casting/crystalblue.png');
const FOCUS_MOVE_CHESS_ASSET = require('../../../../../assets/battle/08_focus_cursor/focusmovechess1.png');

const CELL_ASSET_SIZES = {
  brokenHeart: { width: 12, height: 10 },
  crystalBlue: { width: 30, height: 10 },
  focusMoveChess: { width: 28, height: 28 },
};

const BrokenHeart = ({ scale }: { scale: number }) => {
  const width = CELL_ASSET_SIZES.brokenHeart.width * scale;
  const height = CELL_ASSET_SIZES.brokenHeart.height * scale;

  return (
    <View
      style={[
        styles.brokenHeart,
        {
          width,
          height,
          right: -width,
          bottom: 0,
        },
      ]}
    >
      <Image source={BROKEN_HEART_ASSET} style={[styles.brokenHeartImage, { width, height }]} resizeMode="stretch" />
    </View>
  );
};

const RankStar = ({ scale }: { scale: number }) => {
  const [frame, setFrame] = useState(0);
  const sprite = useMemo(() => CELL_ASSET_SIZES.crystalBlue, []);
  const frameWidth = sprite.width / STAR_FRAME_COUNT;
  const width = frameWidth * scale;
  const height = sprite.height * scale;

  useEffect(() => {
    const timer = setInterval(() => {
      setFrame((current) => (current + 1) % STAR_FRAME_COUNT);
    }, 180);

    return () => clearInterval(timer);
  }, []);

  return (
    <View style={[styles.rankStar, { width, height, right: -width, top: 0 }]}>
      <Image
        source={CRYSTAL_BLUE_ASSET}
        style={[styles.rankStarSheet, { width: sprite.width * scale, height, left: -frame * width }]}
        resizeMode="stretch"
      />
    </View>
  );
};

const JavaThreeColorBevel = ({ scale, fillColor, accentColor }: { scale: number; fillColor: string; accentColor: string }) => {
  const width = GRID_CELL_W * scale;
  const height = GRID_CELL_H * scale;

  return (
    <View pointerEvents="none" style={styles.javaBevelFrame}>
      <View style={[styles.javaBevelFill, { left: 1 * scale, top: 1 * scale, width: width - 2 * scale, height: height - 2 * scale, backgroundColor: fillColor }]} />
      <View style={[styles.javaBevelOuter, { left: 0, top: 0, width, height, borderColor: fillColor }]} />
      <View style={[styles.javaBevelAccentBottom, { left: 1 * scale, top: height - 2 * scale, width: width - 2 * scale, backgroundColor: accentColor }]} />
      <View style={[styles.javaBevelAccentRight, { left: width - 2 * scale, top: 1 * scale, height: height - 2 * scale, backgroundColor: accentColor }]} />
    </View>
  );
};

const FocusFrame = ({ scale }: { scale: number }) => {
  const sprite = CELL_ASSET_SIZES.focusMoveChess;
  const corner = 7 * scale;
  const sheetWidth = sprite.width * scale;
  const sheetHeight = sprite.height * scale;
  const sourceRight = (sprite.width - 7) * scale;
  const sourceBottom = (sprite.height - 7) * scale;

  return (
    <View pointerEvents="none" style={styles.focusFrame}>
      <View style={[styles.focusCornerClip, { left: 0, top: 0, width: corner, height: corner }]}>
        <Image source={FOCUS_MOVE_CHESS_ASSET} style={[styles.focusCornerSheet, { width: sheetWidth, height: sheetHeight, left: 0, top: 0 }]} />
      </View>
      <View style={[styles.focusCornerClip, { right: 0, top: 0, width: corner, height: corner }]}>
        <Image source={FOCUS_MOVE_CHESS_ASSET} style={[styles.focusCornerSheet, { width: sheetWidth, height: sheetHeight, left: -sourceRight, top: 0 }]} />
      </View>
      <View style={[styles.focusCornerClip, { left: 0, bottom: 0, width: corner, height: corner }]}>
        <Image source={FOCUS_MOVE_CHESS_ASSET} style={[styles.focusCornerSheet, { width: sheetWidth, height: sheetHeight, left: 0, top: -sourceBottom }]} />
      </View>
      <View style={[styles.focusCornerClip, { right: 0, bottom: 0, width: corner, height: corner }]}>
        <Image source={FOCUS_MOVE_CHESS_ASSET} style={[styles.focusCornerSheet, { width: sheetWidth, height: sheetHeight, left: -sourceRight, top: -sourceBottom }]} />
      </View>
    </View>
  );
};

export const TargetFrame = ({ scale }: { scale: number }) => (
  <View pointerEvents="none" style={[styles.targetFrameOuter, { left: -2 * scale, top: -2 * scale, width: 36 * scale, height: 36 * scale }]}>
    <View style={[styles.targetFrameMiddle, { left: 1 * scale, top: 1 * scale, width: 34 * scale, height: 34 * scale }]} />
    <View style={[styles.targetFrameInner, { left: 2 * scale, top: 2 * scale, width: 32 * scale, height: 32 * scale }]} />
  </View>
);

export const InventoryCellView: React.FC<InventoryCellViewProps> = ({
  cell,
  selected,
  targetHighlighted,
  scale,
  style,
  renderBevel = true,
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
        !renderBevel ? styles.cellTransparent : null,
        style,
      ]}
    >
      {renderBevel ? <JavaThreeColorBevel scale={scale} fillColor="#657FFF" accentColor="#7FBFFF" /> : null}
      {icon ? (
        <Image source={icon} style={[styles.cellIcon, sizeStyle]} resizeMode="contain" />
      ) : null}
      {cell.kind === 'equipment' && !icon ? (
        <JavaBitmapText text="?" x={GRID_CELL_W * scale / 2} y={9 * scale} scale={scale} anchor={1} bold />
      ) : null}
      {targetHighlighted ? <TargetFrame scale={scale} /> : null}
      {isBroken ? <BrokenHeart scale={scale} /> : null}
      {showRankStar ? <RankStar scale={scale} /> : null}
      {enhancement !== null ? (
        <JavaBitmapText text={`+${enhancement}`} x={GRID_CELL_W * scale} y={(GRID_CELL_H - JAVA_BITMAP_FONT_HEIGHT) * scale} scale={scale} anchor={2} bold />
      ) : null}
      {quantity !== null ? (
        <JavaBitmapText text={String(quantity)} x={GRID_CELL_W * scale} y={(GRID_CELL_H - JAVA_BITMAP_FONT_HEIGHT) * scale} scale={scale} anchor={2} bold />
      ) : null}
      {selected ? <FocusFrame scale={scale} /> : null}
    </Pressable>
  );
};
