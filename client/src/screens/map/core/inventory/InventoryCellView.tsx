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
  /** Cell index in grid for capacity/locked logic. Source: fg.java:77-92 */
  cellIndex?: number;
  /** Total capacity limit. Source: fg.java:83 checks n7 >= go.n */
  capacityLimit?: number;
  /** Is this cell locked/beyond available slots? Source: fg.java:88-91 */
  isLocked?: boolean;
  onPress?: () => void;
  resolveItemIcon: (item: CharacterInventoryItem) => ImageSourcePropType | undefined;
}

const BROKEN_HEART_ASSET = require('../../../../../assets/ui/08_misc_confirmed/broken_heart.png');
const CRYSTAL_BLUE_ASSET = require('../../../../../assets/battle/03_crystals_casting/crystalblue.png');
const FOCUS_MOVE_CHESS_ASSET = require('../../../../../assets/battle/08_focus_cursor/focusmovechess1.png');
const SLOT_LOCK_ASSET = require('../../../../../assets/ui/03_inventory_slot/slotlock.png');

const CELL_ASSET_SIZES = {
  brokenHeart: { width: 12, height: 10 },
  crystalBlue: { width: 30, height: 10 },
  focusMoveChess: { width: 28, height: 28 },
  slotLock: { width: 32, height: 32 },
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

/**
 * Java 3-color bevel from pc.b().
 * Source: fg.java:84,86,91 — different colors for over-capacity, normal, locked cells.
 */
const JavaThreeColorBevel = ({ scale, fillColor, accentColor, whiteHighlight }: { scale: number; fillColor: string; accentColor: string; whiteHighlight?: string }) => {
  const width = GRID_CELL_W * scale;
  const height = GRID_CELL_H * scale;

  return (
    <View pointerEvents="none" style={styles.javaBevelFrame}>
      <View style={[styles.javaBevelFill, { left: 1 * scale, top: 1 * scale, width: width - 2 * scale, height: height - 2 * scale, backgroundColor: fillColor }]} />
      <View style={[styles.javaBevelOuter, { left: 0, top: 0, width, height, borderColor: fillColor }]} />
      <View style={[styles.javaBevelAccentBottom, { left: 1 * scale, top: height - 2 * scale, width: width - 2 * scale, backgroundColor: accentColor }]} />
      <View style={[styles.javaBevelAccentRight, { left: width - 2 * scale, top: 1 * scale, height: height - 2 * scale, backgroundColor: accentColor }]} />
      {whiteHighlight ? (
        <View style={[styles.javaBevelOuter, { left: 0, top: 0, width, height, borderColor: whiteHighlight }]} />
      ) : null}
    </View>
  );
};

/**
 * Focus frame with Java blink effect.
 * Source: hh.java:1399 — J = 0/-2 blink offset creates alternating frame position.
 * The blink alternates between offset 0 and -2 for visual feedback.
 */
const FocusFrame = ({ scale }: { scale: number }) => {
  const sprite = CELL_ASSET_SIZES.focusMoveChess;
  const corner = 7 * scale;
  const sheetWidth = sprite.width * scale;
  const sheetHeight = sprite.height * scale;
  const sourceRight = (sprite.width - 7) * scale;
  const sourceBottom = (sprite.height - 7) * scale;

  // Java blink offset J alternates between 0 and -2
  const [blinkOffset, setBlinkOffset] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setBlinkOffset((current) => (current === 0 ? -2 : 0));
    }, 300); // ~300ms blink interval, matching typical Java game tick rate

    return () => clearInterval(timer);
  }, []);

  return (
    <View pointerEvents="none" style={[styles.focusFrame, { top: blinkOffset * scale }]}>
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
  cellIndex,
  capacityLimit,
  isLocked = false,
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

  // Java evidence fg.java:82-92 — cell color/render logic based on capacity
  // n7 < this.q: unlocked cells (normal or over-capacity bevel)
  // n7 >= this.q: locked cells (draw /slotlock image OR gray bevel if image null)
  const isOverCapacity = cellIndex !== undefined && capacityLimit !== undefined && cellIndex >= capacityLimit && !isLocked;
  
  // Locked cells: fg.java:88-91 — if (this.k != null) drawImage(this.k) else pc.b(gray bevel)
  const shouldRenderLockIcon = isLocked && cell.kind === 'empty';
  const shouldRenderBevel = renderBevel && !shouldRenderLockIcon;
  
  let bevelFillColor = '#657FFF'; // Normal: 6647295
  let bevelAccentColor = '#7FBFFF'; // Normal: 8369663
  let bevelWhiteHighlight: string | undefined = '#FFFFFF';

  if (isLocked) {
    // fg.java:91 — locked cell fallback when this.k == null: 0x787881, 0xFFFFFF, 11382450
    bevelFillColor = '#787881';
    bevelAccentColor = '#ADAD92'; // 11382450 = 0xADAD92
    bevelWhiteHighlight = '#FFFFFF';
  } else if (isOverCapacity) {
    // fg.java:84 — over-capacity cell: 0xFF0000, 0xFFFFFF, 15385573
    bevelFillColor = '#FF0000';
    bevelAccentColor = '#EAD5E5'; // 15385573 = 0xEAD5E5
    bevelWhiteHighlight = '#FFFFFF';
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
      {shouldRenderBevel ? (
        <JavaThreeColorBevel scale={scale} fillColor={bevelFillColor} accentColor={bevelAccentColor} whiteHighlight={bevelWhiteHighlight} />
      ) : null}
      {shouldRenderLockIcon ? (
        <Image source={SLOT_LOCK_ASSET} style={[styles.cellIcon, sizeStyle]} resizeMode="contain" />
      ) : null}
      {!isLocked && icon ? (
        <Image source={icon} style={[styles.cellIcon, sizeStyle]} resizeMode="contain" />
      ) : null}
      {!isLocked && cell.kind === 'equipment' && !icon ? (
        <JavaBitmapText text="?" x={GRID_CELL_W * scale / 2} y={9 * scale} scale={scale} anchor={1} bold />
      ) : null}
      {targetHighlighted ? <TargetFrame scale={scale} /> : null}
      {!isLocked && isBroken ? <BrokenHeart scale={scale} /> : null}
      {!isLocked && showRankStar ? <RankStar scale={scale} /> : null}
      {!isLocked && enhancement !== null ? (
        <JavaBitmapText text={`+${enhancement}`} x={GRID_CELL_W * scale} y={(GRID_CELL_H - JAVA_BITMAP_FONT_HEIGHT) * scale} scale={scale} anchor={2} bold />
      ) : null}
      {!isLocked && quantity !== null ? (
        <JavaBitmapText text={String(quantity)} x={GRID_CELL_W * scale} y={(GRID_CELL_H - JAVA_BITMAP_FONT_HEIGHT) * scale} scale={scale} anchor={2} bold />
      ) : null}
      {selected ? <FocusFrame scale={scale} /> : null}
    </Pressable>
  );
};
