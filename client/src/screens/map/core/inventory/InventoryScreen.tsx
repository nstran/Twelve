import React, { useEffect, useMemo, useState } from 'react';
import { Dimensions, Image, Pressable, View } from 'react-native';
import type { ImageSourcePropType } from 'react-native';
import { PopupMenu } from '../../../../components/controls/PopupMenu/PopupMenu';
import { CharacterRenderer } from '../../../character/CharacterRenderer';
import type { CharacterAppearance, CharacterEquipmentItem, CharacterInventoryItem } from '../../../character/shared';
import { EquipmentDetailDialog } from './EquipmentDetailDialog';
import { InventoryCellView, TargetFrame, type InventoryCellData } from './InventoryCellView';
import {
  computeGridColumns,
  computeInventoryScale,
  EQUIPMENT_SLOT_NAMES,
  GRID_CELL_H,
  GRID_CELL_W,
  GRID_PADDING_Y,
  GRID_SPACING,
  INVENTORY_LAYOUT,
  scaleRect,
} from './InventoryLayout';
import { styles } from './InventoryScreen.styles';
import { InventoryTooltip } from './InventoryTooltip';
import { JavaBitmapText } from './javaFont/JavaBitmapText';

type DialogActionRunner = () => Promise<string | null> | undefined;

interface InventoryScreenProps {
  appearance: CharacterAppearance;
  pending: string | null;
  onClose: () => void;
  onRunAction: (key: string, runner?: DialogActionRunner) => void;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}

const INFO_ASSETS = {
  hidenobj: require('../../../../../assets/ui/12_info/hidenobj.png'),
  corner2: require('../../../../../assets/ui/00_corner_frames/2.png'),
  hiddendragon: require('../../../../../assets/battle/09_hidden_pieces/hiddendragon.png'),
  tab: require('../../../../../assets/ui/04_tabs_and_numbers/tab.png'),
};

const INVENTORY_ITEM_ASSETS: Record<string, ImageSourcePropType> = {
  potion_red: require('../../../../../assets/items/hp.png'),
  potion_blue: require('../../../../../assets/items/mp.png'),
  peach: require('../../../../../assets/items/ostrich_egg.png'),
  chicken_egg: require('../../../../../assets/items/chicken_egg.png'),
  ostrich_egg: require('../../../../../assets/items/ostrich_egg.png'),
  dinosaur_egg: require('../../../../../assets/items/dinosaur_egg.png'),
  phoenix_egg: require('../../../../../assets/items/phoenix_egg.png'),
  dragon_egg: require('../../../../../assets/items/dragon_egg.png'),
  hammer: require('../../../../../assets/items/repair_hammer.png'),
  repair_hammer: require('../../../../../assets/items/repair_hammer.png'),
  kim_thach: require('../../../../../assets/items/kim_thach.png'),
  huyet_thach: require('../../../../../assets/items/huyet_thach.png'),
  charm_1: require('../../../../../assets/items/charm_1.png'),
  charm_2: require('../../../../../assets/items/charm_2.png'),
  charm_3: require('../../../../../assets/items/charm_3.png'),
};

const REPAIR_HAMMER_ITEM_ID = 30099;
const BASE_CAPACITY = 50;

const INFO_ASSET_SIZES = {
  corner2: { width: 9, height: 12 },
  hiddendragon: { width: 107, height: 78 },
  tab: { width: 315, height: 37 },
};

const buildEquippedKeySet = (equipment: CharacterEquipmentItem[]) => new Set(
  equipment.filter((entry) => entry.isEquipped).map((entry) => entry.equipKey),
);

const sameKeySet = (left: Set<string>, right: Set<string>) => {
  if (left.size !== right.size) {
    return false;
  }

  for (const key of left) {
    if (!right.has(key)) {
      return false;
    }
  }

  return true;
};

const resolveInventoryItemIcon = (item: CharacterInventoryItem) => INVENTORY_ITEM_ASSETS[item.iconKind];

const getPlayerLevel = (appearance: CharacterAppearance) => appearance.level ?? 1;
const getPlayerName = (appearance: CharacterAppearance) => appearance.username ?? 'Twelve';

const JavaThreeColorBevel = ({ width, height, scale, fillColor, accentColor }: { width: number; height: number; scale: number; fillColor: string; accentColor: string }) => (
  <View pointerEvents="none" style={styles.javaBevelFrame}>
    <View style={[styles.javaBevelFill, { left: 1 * scale, top: 1 * scale, width: width - 2 * scale, height: height - 2 * scale, backgroundColor: fillColor }]} />
    <View style={[styles.javaBevelOuter, { left: 0, top: 0, width, height, borderColor: fillColor }]} />
    <View style={[styles.javaBevelAccentBottom, { left: 1 * scale, top: height - 2 * scale, width: width - 2 * scale, backgroundColor: accentColor }]} />
    <View style={[styles.javaBevelAccentRight, { left: width - 2 * scale, top: 1 * scale, height: height - 2 * scale, backgroundColor: accentColor }]} />
  </View>
);

const JavaTwoColorBevel = ({ width, height, scale, fillColor }: { width: number; height: number; scale: number; fillColor: string }) => (
  <View pointerEvents="none" style={styles.javaBevelFrame}>
    <View style={[styles.javaBevelFill, { left: 2 * scale, top: 2 * scale, width: width - 3 * scale, height: height - 3 * scale, backgroundColor: fillColor }]} />
    <View style={[styles.javaBevelOuter, { left: 0, top: 0, width, height }]} />
    <View style={[styles.javaBevelInner, { left: 1 * scale, top: 1 * scale, width: width - 2 * scale, height: height - 2 * scale }]} />
  </View>
);

export const InventoryScreen: React.FC<InventoryScreenProps> = ({
  appearance,
  pending,
  onClose,
  onRunAction,
  onPreviewEquipmentLoadout,
  onCommitEquipmentLoadout,
  onUseItem,
  onDiscardEquipment,
  onDiscardItem,
  onRepairEquipment,
}) => {
  const { width: screenWidth, height: screenHeight } = Dimensions.get('window');
  const scaled = computeInventoryScale(screenWidth * 0.96, screenHeight * 0.9);
  const { layout, scale, canvasWidth, canvasHeight } = scaled;
  const equipmentSource = appearance.equipment ?? [];
  const equipmentSignature = equipmentSource.map((entry) => `${entry.equipKey}:${entry.isEquipped ? 1 : 0}`).join('|');
  const serverEquippedKeys = useMemo(() => buildEquippedKeySet(equipmentSource), [equipmentSource]);
  const [draftEquippedKeys, setDraftEquippedKeys] = useState(() => buildEquippedKeySet(equipmentSource));
  const [previewRuntime, setPreviewRuntime] = useState<CharacterAppearance | null>(null);
  const [selectedKey, setSelectedKey] = useState<string>('');
  const [menuState, setMenuState] = useState<{ left: number; top: number } | null>(null);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);
  const [showTooltip, setShowTooltip] = useState(false);
  const [detailEntry, setDetailEntry] = useState<CharacterEquipmentItem | null>(null);

  useEffect(() => {
    setDraftEquippedKeys(buildEquippedKeySet(equipmentSource));
    setPreviewRuntime(null);
    setSelectedKey('');
  }, [equipmentSignature, equipmentSource]);

  const previewEquipment = equipmentSource.map((entry) => ({
    ...entry,
    isEquipped: draftEquippedKeys.has(entry.equipKey),
  }));
  const previewAppearance: CharacterAppearance = {
    ...appearance,
    combat: previewRuntime?.combat ?? appearance.combat,
    equipmentStats: previewRuntime?.equipmentStats ?? appearance.equipmentStats,
    hp: previewRuntime?.hp ?? appearance.hp,
    equipment: previewEquipment,
  };
  const equipped = previewEquipment.filter((entry) => entry.isEquipped && entry.slot !== 8 && entry.slot < 6);
  const bagEquipment = previewEquipment.filter((entry) => !entry.isEquipped || entry.slot === 8 || entry.slot >= 6);
  const rawCells: InventoryCellData[] = [
    ...bagEquipment.map((entry): InventoryCellData => ({ kind: 'equipment', key: `equip-${entry.equipKey}`, entry, equipped: false })),
    ...(appearance.inventory ?? []).map((item): InventoryCellData => ({ kind: 'item', key: `item-${item.itemId}`, item })),
  ];
  const visibleCapacity = Math.max(BASE_CAPACITY, rawCells.length);
  const columns = computeGridColumns(layout.bag.w);
  const cells = Array.from({ length: visibleCapacity }, (_, index): InventoryCellData => rawCells[index] ?? { kind: 'empty', key: `empty-${index}` });
  const selectedCell = rawCells.find((cell) => cell.key === selectedKey)
    ?? equipped.map((entry): InventoryCellData => ({ kind: 'equipment', key: `equipped-${entry.equipKey}`, entry, equipped: true })).find((cell) => cell.key === selectedKey)
    ?? null;
  const hasLoadoutChanges = !sameKeySet(serverEquippedKeys, draftEquippedKeys);
  const playerLevel = getPlayerLevel(appearance);
  const playerGender = appearance.genderIndex;
  const cornerAsset = INFO_ASSET_SIZES.corner2;
  const cornerWidth = cornerAsset.width * scale;
  const cornerHeight = cornerAsset.height * scale;
  const dragonAsset = INFO_ASSET_SIZES.hiddendragon;
  const tabAsset = INFO_ASSET_SIZES.tab;
  // Java pc.b(...) slices /tab in fixed 35x37 frames: cw.a(j, n4 * 35, 0, 35, 37, ...).
  const tabFrameWidth = 35;
  const elementIndex = Math.max(0, Math.min(8, appearance.elementIndex ?? 0));

  useEffect(() => {
    if (!onPreviewEquipmentLoadout || !hasLoadoutChanges) {
      setPreviewRuntime(null);
      return;
    }

    let active = true;
    const timer = setTimeout(() => {
      void onPreviewEquipmentLoadout(Array.from(draftEquippedKeys)).then((preview) => {
        if (active) {
          setPreviewRuntime(preview);
        }
      });
    }, 120);

    return () => {
      active = false;
      clearTimeout(timer);
    };
  }, [draftEquippedKeys, hasLoadoutChanges, onPreviewEquipmentLoadout]);

  useEffect(() => {
    if (!selectedCell || menuState) {
      setShowTooltip(false);
      return;
    }

    const timer = setTimeout(() => setShowTooltip(true), 350);
    return () => clearTimeout(timer);
  }, [selectedCell, menuState]);

  const getEquipped = (slot: number) => equipped.find((entry) => entry.slot === slot);
  const selectedTargetSlot = selectedCell?.kind === 'equipment' && !selectedCell.equipped && selectedCell.entry.slot < layout.slots.length
    ? selectedCell.entry.slot
    : null;
  // fg.java centers columns with m=(containerWidth - columns*(cell+spacing))/2, not a fixed padding.
  const gridPaddingX = (layout.bag.w - columns * (GRID_CELL_W + GRID_SPACING)) / 2;
  const selectCell = (cell: InventoryCellData, left: number, top: number) => {
    if (cell.kind === 'empty') {
      setSelectedKey('');
      setMenuState(null);
      setShowTooltip(false);
      return;
    }

    setSelectedKey(cell.key);
    setShowTooltip(false);
    setMenuState({ left, top });
  };

  const previewEquipmentChange = (entry: CharacterEquipmentItem, equip: boolean) => {
    setDraftEquippedKeys((current) => {
      const next = new Set(current);
      if (equip) {
        for (const other of previewEquipment) {
          if (other.slot === entry.slot) {
            next.delete(other.equipKey);
          }
        }
        next.add(entry.equipKey);
      } else {
        next.delete(entry.equipKey);
      }
      return next;
    });
  };

  const menuItems = useMemo(() => {
    if (!selectedCell || selectedCell.kind === 'empty') {
      return [];
    }

    if (selectedCell.kind === 'item') {
      const item = selectedCell.item;
      return [
        {
          id: 'use',
          label: item.isUsable ? 'Dùng' : 'Giữ',
          disabled: pending !== null || !item.isUsable || !onUseItem,
          onPress: () => {
            setMenuState(null);
            onRunAction(`item-${item.itemId}`, () => onUseItem?.(item.itemId));
          },
        },
        {
          id: 'drop',
          label: 'Vứt bỏ',
          disabled: pending !== null || !onDiscardItem,
          onPress: () => {
            setMenuState(null);
            onRunAction(`discard-item-${item.itemId}`, () => onDiscardItem?.(item.itemId, item.quantity));
          },
        },
      ].filter((itemEntry) => !itemEntry.disabled);
    }

    const entry = selectedCell.entry;
    const selectedIsEquipped = selectedCell.equipped;
    const canEquip = selectedIsEquipped || (
      playerLevel >= entry.requiredLevel
      && (entry.gender === 2 || entry.gender === undefined || entry.gender === playerGender)
    );
    const isBroken = entry.maxDurability > 0 && entry.durability <= 0;
    const hasHammer = (appearance.inventory ?? []).some((item) => item.itemId === REPAIR_HAMMER_ITEM_ID && item.quantity > 0);
    const canRepair = isBroken && hasHammer && !!onRepairEquipment;
    const commitLoadout = onCommitEquipmentLoadout
      ? () => onCommitEquipmentLoadout(Array.from(draftEquippedKeys))
      : undefined;

    return [
      {
        id: 'repair',
        label: 'Sửa chữa',
        disabled: pending !== null || !canRepair,
        onPress: () => {
          setMenuState(null);
          onRunAction(`repair-${entry.equipKey}`, () => onRepairEquipment?.(entry.equipKey));
        },
      },
      {
        id: 'equip',
        label: selectedIsEquipped ? 'Cởi ra' : (entry.slot === 8 ? 'Dùng' : 'Trang bị'),
        disabled: pending !== null || !canEquip,
        onPress: () => {
          previewEquipmentChange(entry, !selectedIsEquipped);
          setMenuState(null);
        },
      },
      {
        id: 'detail',
        label: 'Chi Tiết',
        disabled: false,
        onPress: () => {
          setMenuState(null);
          setDetailEntry(entry);
        },
      },
      {
        id: 'upgrade',
        label: 'Nâng cấp',
        disabled: pending !== null || selectedIsEquipped || !entry.canUpgrade,
        onPress: () => setMenuState(null),
      },
      {
        id: 'sell',
        label: 'Rao bán',
        disabled: selectedIsEquipped || !entry.tradeable,
        onPress: () => setMenuState(null),
      },
      {
        id: 'drop',
        label: 'Vứt bỏ',
        disabled: pending !== null || selectedIsEquipped || !onDiscardEquipment,
        onPress: () => {
          setMenuState(null);
          onRunAction(`discard-equip-${entry.equipKey}`, () => onDiscardEquipment?.(entry.equipKey));
        },
      },
      ...(hasLoadoutChanges ? [{
        id: 'commit',
        label: 'Cập nhật',
        disabled: pending !== null || !commitLoadout,
        onPress: () => {
          setMenuState(null);
          onRunAction('equipment-loadout', commitLoadout);
        },
      }] : []),
    ].filter((itemEntry) => !itemEntry.disabled);
  }, [appearance.inventory, draftEquippedKeys, hasLoadoutChanges, onCommitEquipmentLoadout, onDiscardEquipment, onDiscardItem, onRepairEquipment, onRunAction, onUseItem, pending, playerGender, playerLevel, selectedCell]);

  return (
    <View style={styles.screenOverlay}>
      <Pressable style={styles.backdrop} onPress={onClose} />
      <View style={[styles.canvas, { width: canvasWidth, height: canvasHeight }]}>
        <Image
          source={INFO_ASSETS.hiddendragon}
          style={[
            styles.watermark,
            {
              width: dragonAsset.width * scale,
              height: dragonAsset.height * scale,
              right: 0,
              bottom: 4 * scale,
            },
          ]}
          resizeMode="stretch"
        />
        <View pointerEvents="none" style={[styles.innerBorder, { left: 1 * scale, top: 1 * scale, width: canvasWidth - 3 * scale, height: canvasHeight - 3 * scale }]} />
        <View pointerEvents="none" style={[styles.innerBorder2, { left: 2 * scale, top: 3 * scale, width: canvasWidth - 5 * scale, height: canvasHeight - 7 * scale }]} />
        <View pointerEvents="none" style={[styles.panelTopAccent, { left: 2 * scale, top: 2 * scale, width: canvasWidth - 4 * scale }]} />
        <View pointerEvents="none" style={[styles.panelBottomAccent, { left: 2 * scale, top: canvasHeight - 3 * scale, width: canvasWidth - 4 * scale }]} />
        <View pointerEvents="none" style={[styles.panelEdgeHorizontal, { left: 4 * scale, top: 0, width: canvasWidth - 8 * scale }]} />
        <View pointerEvents="none" style={[styles.panelEdgeHorizontal, { left: 4 * scale, top: canvasHeight - scale, width: canvasWidth - 8 * scale }]} />
        <View pointerEvents="none" style={[styles.panelEdgeVertical, { left: 0, top: 4 * scale, height: canvasHeight - 8 * scale }]} />
        <View pointerEvents="none" style={[styles.panelEdgeVertical, { left: canvasWidth - scale, top: 4 * scale, height: canvasHeight - 8 * scale }]} />
        <Image source={INFO_ASSETS.corner2} style={[styles.cornerImage, { left: 0, top: 0, width: cornerWidth, height: cornerHeight }]} />
        <Image source={INFO_ASSETS.corner2} style={[styles.cornerImage, { right: 0, top: 0, width: cornerWidth, height: cornerHeight, transform: [{ scaleX: -1 }] }]} />
        <Image source={INFO_ASSETS.corner2} style={[styles.cornerImage, { left: 0, bottom: 0, width: cornerWidth, height: cornerHeight, transform: [{ scaleY: -1 }] }]} />
        <Image source={INFO_ASSETS.corner2} style={[styles.cornerImage, { right: 0, bottom: 0, width: cornerWidth, height: cornerHeight, transform: [{ scaleX: -1 }, { scaleY: -1 }] }]} />
        <View
          style={[
            styles.elementIcon,
            {
              left: layout.elementPos.x * scale,
              top: layout.elementPos.y * scale,
              width: tabFrameWidth * scale,
              height: tabAsset.height * scale,
            },
          ]}
        >
          <Image
            source={INFO_ASSETS.tab}
            style={[
              styles.elementIconSheet,
              {
                width: tabAsset.width * scale,
                height: tabAsset.height * scale,
                left: -elementIndex * tabFrameWidth * scale,
              },
            ]}
            resizeMode="stretch"
          />
        </View>
        <JavaBitmapText text={getPlayerName(appearance)} x={layout.namePos.x * scale} y={layout.namePos.y * scale} scale={scale} bold />
        <JavaBitmapText text={`Cấp: ${playerLevel}`} x={canvasWidth - 14 * scale} y={6 * scale} scale={scale} anchor={2} bold />

        {layout.slots.map((slotRect, slot) => {
          const slotStyle = scaleRect(slotRect, scale);
          const entry = getEquipped(slot);
          const cell: InventoryCellData = entry
            ? { kind: 'equipment', key: `equipped-${entry.equipKey}`, entry, equipped: true }
            : { kind: 'empty', key: `slot-${slot}` };
          return (
            <View key={`slot-${slot}`} style={[styles.slotBackground, slotStyle]}>
              <JavaThreeColorBevel width={slotStyle.width} height={slotStyle.height} scale={scale} fillColor="#657FFF" accentColor="#7FBFFF" />
              {!entry ? (
                <View style={[styles.slotPlaceholder, { width: 32 * scale, height: 32 * scale }]}> 
                  <Image
                    source={INFO_ASSETS.hidenobj}
                    style={[styles.slotPlaceholderSheet, { width: 192 * scale, height: 32 * scale, left: -slot * 32 * scale }]}
                    resizeMode="stretch"
                  />
                </View>
              ) : null}
              <InventoryCellView
                cell={cell}
                selected={selectedKey === cell.key}
                scale={scale}
                renderBevel={false}
                style={{ left: 0, top: 0 }}
                resolveItemIcon={resolveInventoryItemIcon}
                onPress={() => selectCell(cell, slotStyle.left + slotStyle.width, slotStyle.top)}
              />
              {selectedTargetSlot === slot ? <TargetFrame scale={scale} /> : null}
            </View>
          );
        })}

        <View style={[styles.avatarBox, scaleRect(layout.avatar, scale)]}>
          <CharacterRenderer
            appearance={previewAppearance}
            scale={1.05 * scale}
            style={{ position: 'relative', bottom: 0 }}
            anchorToBody
          />
          <View pointerEvents="none" style={styles.avatarFrameOuter} />
          <View pointerEvents="none" style={[styles.avatarFrameOuter, { left: 1 * scale, top: 1 * scale, right: 1 * scale, bottom: 1 * scale }]} />
          <View pointerEvents="none" style={[styles.avatarFrameAccentTop, { left: 2 * scale, top: 2 * scale, right: 2 * scale }]} />
          <View pointerEvents="none" style={[styles.avatarFrameAccentBottom, { left: 2 * scale, bottom: 2 * scale, right: 2 * scale }]} />
          <View pointerEvents="none" style={[styles.avatarFrameAccentLeft, { left: 2 * scale, top: 3 * scale, bottom: 3 * scale }]} />
          <View pointerEvents="none" style={[styles.avatarFrameAccentRight, { right: 2 * scale, top: 3 * scale, bottom: 3 * scale }]} />
        </View>

        <JavaBitmapText text={`${rawCells.length}/${BASE_CAPACITY}`} x={layout.capacityPos.x * scale} y={layout.capacityPos.y * scale} scale={scale} bold />

        <View style={[styles.gridContainer, scaleRect(layout.bag, scale)]}>
          <JavaTwoColorBevel width={layout.bag.w * scale} height={layout.bag.h * scale} scale={scale} fillColor="#F0FBFF" />
          {cells.map((cell, index) => {
            const col = index % columns;
            const row = Math.floor(index / columns);
            const left = (gridPaddingX + col * (GRID_CELL_W + GRID_SPACING)) * scale;
            const top = (GRID_PADDING_Y + row * (GRID_CELL_H + GRID_SPACING)) * scale;
            return (
              <InventoryCellView
                key={cell.key}
                cell={cell}
                selected={selectedKey === cell.key}
                scale={scale}
                style={{ left, top }}
                resolveItemIcon={resolveInventoryItemIcon}
                onPress={() => selectCell(cell, layout.bag.x * scale + left + GRID_CELL_W * scale, layout.bag.y * scale + top)}
              />
            );
          })}
        </View>

        {showTooltip ? (
          <InventoryTooltip
            cell={selectedCell}
            playerLevel={playerLevel}
            playerGender={playerGender}
            scale={scale}
            canvasHeight={canvasHeight}
          />
        ) : null}

      </View>

      {menuState ? (
        <PopupMenu
          visible={menuItems.length > 0}
          left={(screenWidth - canvasWidth) / 2 + menuState.left}
          top={(screenHeight - canvasHeight) / 2 + menuState.top}
          items={menuItems}
          selectedIndex={menuSelectedIndex}
          onIndexChange={setMenuSelectedIndex}
          onSelect={() => undefined}
          onClose={() => {
            setMenuState(null);
            setMenuSelectedIndex(0);
          }}
          javaCompact
        />
      ) : null}

      {detailEntry ? (
        <EquipmentDetailDialog
          entry={detailEntry}
          playerLevel={playerLevel}
          playerGender={playerGender}
          onClose={() => setDetailEntry(null)}
        />
      ) : null}
    </View>
  );
};
