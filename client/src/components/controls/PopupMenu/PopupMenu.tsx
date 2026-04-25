import React, { useState, useEffect, useRef } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  Pressable,
  StyleSheet,
} from 'react-native';
import { styles } from './PopupMenu.styles';

const ASSET_ORNATE      = require('../../../../assets/ui/00_corner_frames/cornerskb.png');
const ASSET_BASE_FRAME  = require('../../../../assets/ui/00_corner_frames/1.png');

export interface MenuItem {
  id: number | string;
  label: string;
  children?: MenuItem[];
  onPress?: () => void;
}

interface PopupMenuProps {
  visible: boolean;
  items: MenuItem[];
  title?: string;
  selectedIndex: number;
  onIndexChange: (index: number) => void;
  onSelect: (item: MenuItem) => void;
  onClose: () => void;
  bottomOffset?: number;
  top?: number;
  left?: number;
  /**
   * Tín hiệu từ Softkey OK/tick. Mỗi lần số này tăng, PopupMenu sẽ chọn
   * item đang focus ở level sâu nhất, giống phím trái trong Java client.
   */
  selectSignal?: number;
}

export const PopupMenu: React.FC<PopupMenuProps> = ({
  visible,
  items,
  title,
  selectedIndex,
  onIndexChange,
  onSelect,
  onClose,
  bottomOffset,
  top,
  left,
  selectSignal,
}) => {
  const [navStack, setNavStack] = useState<{ items: MenuItem[]; title: string; openedIndex: number }[]>([]);
  const lastSelectSignalRef = useRef(selectSignal);

  useEffect(() => {
    if (visible) {
      setNavStack([]);
    }
  }, [visible]);

  const handleItemPress = (item: MenuItem, idx: number, depth: number) => {
    if (item.children && item.children.length > 0) {
      setNavStack(prev => {
        const newStack = prev.slice(0, depth);
        return [...newStack, { items: item.children!, title: item.label, openedIndex: idx }];
      });
      onIndexChange(0);
    } else {
      if (item.onPress) item.onPress();
      onSelect(item);
      onClose();
    }
  };

  useEffect(() => {
    if (!visible || selectSignal === undefined || lastSelectSignalRef.current === selectSignal) {
      lastSelectSignalRef.current = selectSignal;
      return;
    }

    lastSelectSignalRef.current = selectSignal;

    const activeDepth = navStack.length;
    const activeItems = activeDepth > 0 ? navStack[activeDepth - 1].items : items;
    const focusedItem = activeItems[selectedIndex];

    if (focusedItem) {
      handleItemPress(focusedItem, selectedIndex, activeDepth);
    }
  }, [items, navStack, onClose, onIndexChange, onSelect, selectSignal, selectedIndex, visible]);

  if (!visible) return null;

  const allLevels = [
    { items, title: title || '', openedIndex: navStack.length > 0 ? navStack[0].openedIndex : selectedIndex },
    ...navStack.map((level, i) => ({
      items: level.items,
      title: level.title,
      openedIndex: i === navStack.length - 1 ? selectedIndex : navStack[i + 1].openedIndex,
    }))
  ];

  return (
    <View style={[StyleSheet.absoluteFill, { zIndex: 1000, elevation: 1000 }]} pointerEvents="box-none">
      <Pressable style={styles.menuBackdrop} onPress={onClose} />

      {allLevels.map((level, depth) => {
        return (
          <View 
            key={`menu-level-${depth}`} 
            style={[
              styles.menuBox, 
              { 
                zIndex: 1001 + depth,
                elevation: 1001 + depth,
              },
              top !== undefined ? { top: top + depth * 15, bottom: undefined } : { bottom: Math.max(0, (bottomOffset ?? 26) - depth * 15) },
              left !== undefined ? { left: left + depth * 15 } : { left: 4 + depth * 15 }
            ]}
          >
            <View style={styles.menuInnerBox}>
              {level.items.map((item, idx) => {
                const isSelected = idx === level.openedIndex;
                const hasChildren = item.children && item.children.length > 0;

                return (
                  <TouchableOpacity
                    key={item.id}
                    style={styles.menuItem}
                    onPressIn={() => {
                       if (depth === navStack.length) {
                         onIndexChange(idx);
                       }
                    }}
                    onPress={() => handleItemPress(item, idx, depth)}
                    activeOpacity={1}
                  >
                    {isSelected && (
                      <View style={styles.menuItemSelectedBg}>
                        <Image source={ASSET_BASE_FRAME} style={styles.menuSelectedBaseImage} resizeMode="stretch" />
                        <View style={[styles.menuOrnateClip, { left: 0 }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                        <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                      </View>
                    )}
                    <View style={{ flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', paddingRight: 10 }}>
                       <Text style={[styles.menuItemText, isSelected && styles.menuItemTextSelected]}>
                         {item.label}
                       </Text>
                       {hasChildren && (
                         <Text style={[styles.menuArrowText, isSelected && styles.menuArrowTextSelected]}>{'>'}</Text>
                       )}
                    </View>
                  </TouchableOpacity>
                );
              })}
            </View>
          </View>
        );
      })}
    </View>
  );
};
