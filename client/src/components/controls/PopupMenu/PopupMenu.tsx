import React, { useState, useEffect } from 'react';
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
}

export const PopupMenu: React.FC<PopupMenuProps> = ({
  visible,
  items,
  title,
  selectedIndex,
  onIndexChange,
  onSelect,
  onClose,
  bottomOffset = 26,
}) => {
  const [navStack, setNavStack] = useState<{ items: MenuItem[]; title: string; openedIndex: number }[]>([]);

  useEffect(() => {
    if (visible) {
      setNavStack([]);
    }
  }, [visible]);

  if (!visible) return null;

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
                bottom: Math.max(0, bottomOffset - depth * 15), 
                left: 4 + depth * 15,
                zIndex: 1001 + depth,
                elevation: 1001 + depth
              }
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
                         <Text style={{ color: isSelected ? '#fff' : '#666', fontWeight: 'bold' }}>{'>'}</Text>
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
