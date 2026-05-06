import React, { useState, useEffect, useRef } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  Pressable,
  StyleSheet,
} from 'react-native';
import { JavaBitmapText, measureJavaBitmapText } from '../../../screens/map/core/inventory/javaFont/JavaBitmapText';
import { styles, JAVA_MENU_FILL } from './PopupMenu.styles';

const ASSET_ORNATE      = require('../../../../assets/ui/00_corner_frames/cornerskb.png');
const ASSET_BASE_FRAME  = require('../../../../assets/ui/00_corner_frames/1.png');
/**
 * Corner asset for Java popup frame.
 * Source: ap.java:12 — /_corner.png asset, drawn with drawRegion anchors 20,24,36,40 for 4 corners.
 */
const ASSET_CORNER = require('../../../../assets/ui/00_corner_frames/_corner.png');

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
  /** Java bs popup renderer parity: item height 20, left text inset 14, compact frame. */
  javaCompact?: boolean;
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
  javaCompact,
}) => {
  console.log('[PopupMenu] Render, visible:', visible, 'items:', items.length);
  const [navStack, setNavStack] = useState<{ items: MenuItem[]; title: string; openedIndex: number }[]>([]);
  const lastSelectSignalRef = useRef(selectSignal);

  useEffect(() => {
    if (visible) {
      console.log('[PopupMenu] Visible changed to true, clearing navStack');
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

  const javaMenuLogicalWidth = javaCompact
    ? Math.max(50, ...items.map((item) => measureJavaBitmapText(item.label))) + 42
    : undefined;

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
        // Java popup frame dimensions from bs.java evidence
        const itemHeight = 20; // bs.java: j = 20
        const textXOffset = 14; // bs.java: text x+14
        
        return (
          <View
            key={`menu-level-${depth}`}
            style={[
              {
                position: 'absolute',
                zIndex: 1001 + depth,
                elevation: 1001 + depth,
              },
              top !== undefined ? { top: top + depth * 15, bottom: undefined } : { bottom: Math.max(0, (bottomOffset ?? 26) - depth * 15) },
              left !== undefined ? { left: left + depth * 15 } : { left: 4 + depth * 15 }
            ]}
          >
            {/* Java bs popup frame - Source: bs.java:96-119, ap.java:35-48 */}
            <View style={[
              {
                width: javaCompact ? javaMenuLogicalWidth : undefined,
                minWidth: javaCompact ? 92 : 120,
                backgroundColor: JAVA_MENU_FILL,
                borderWidth: 2,
                borderColor: '#0056E6',
                paddingVertical: 6,
                paddingHorizontal: 4,
              },
              javaCompact ? null : styles.menuInnerBox
            ]}>
              {level.items.map((item, idx) => {
                const isSelected = idx === level.openedIndex;
                const hasChildren = item.children && item.children.length > 0;

                return (
                  <TouchableOpacity
                    key={item.id}
                    style={javaCompact ? styles.javaMenuItem : styles.menuItem}
                    onPressIn={() => {
                       if (depth === navStack.length) {
                         onIndexChange(idx);
                       }
                    }}
                    onPress={() => handleItemPress(item, idx, depth)}
                    activeOpacity={1}
                  >
                    {isSelected && javaCompact ? (
                      <View style={styles.javaMenuItemSelectedBg} />
                    ) : null}
                    {isSelected && !javaCompact ? (
                      <View style={styles.menuItemSelectedBg}>
                        <Image source={ASSET_BASE_FRAME} style={styles.menuSelectedBaseImage} resizeMode="stretch" />
                        <View style={[styles.menuOrnateClip, { left: 0 }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                        <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                      </View>
                    ) : null}
                    <View style={styles.menuItemContent}>
                      <Text style={[styles.menuItemText, isSelected && styles.menuItemTextSelected]}>
                        {item.label}
                      </Text>
                      {hasChildren ? (
                        <Text style={[styles.menuArrowText, isSelected && styles.menuArrowTextSelected]}>{'>'}</Text>
                      ) : null}
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
