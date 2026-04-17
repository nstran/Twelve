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

const ASSET_ORNATE      = require('../../assets/ui_legacy/00_corner_frames/cornerskb.png');
const ASSET_BASE_FRAME  = require('../../assets/ui_legacy/00_corner_frames/1.png');

/** 
 * Unified MenuItem interface. 
 * Supports flat lists and recursive sub-menus.
 */
export interface MenuItem {
  id: number | string;
  label: string;
  children?: MenuItem[];
  onPress?: () => void;
}

interface PopupMenuProps {
  /** Visibility of the entire menu */
  visible: boolean;
  /** Initial items appearing at the root level */
  items: MenuItem[];
  /** Optional header title for the root level */
  title?: string;
  /** Current selected index at the ACTIVE level */
  selectedIndex: number;
  /** Callback when selection changes (used for hover effect in some screens) */
  onIndexChange: (index: number) => void;
  /** Callback when item is pressed/executed */
  onSelect: (item: MenuItem) => void;
  /** Callback to close the menu completely */
  onClose: () => void;
  /** Distance from screen bottom */
  bottomOffset?: number;
}

/**
 * Global PopupMenu Component. 
 * Standardized across Login, Map, and all other screens.
 */
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
  // Navigation stack: [{ items: MenuItem[], title: string }]
  const [navStack, setNavStack] = useState<{ items: MenuItem[]; title: string }[]>([]);

  // Reset navigation when menu opens/closes
  useEffect(() => {
    if (visible) {
      setNavStack([]);
    }
  }, [visible]);

  if (!visible) return null;

  // Determine current context
  const isSubMenu     = navStack.length > 0;
  const activeItems   = isSubMenu ? navStack[navStack.length - 1].items : items;
  const activeTitle   = isSubMenu ? navStack[navStack.length - 1].title : (title || '');

  const handleItemPress = (item: MenuItem, idx: number) => {
    if (item.children && item.children.length > 0) {
      // Navigate deeper
      setNavStack(prev => [...prev, { items: item.children!, title: item.label }]);
      onIndexChange(0); // Reset selection for the new level
    } else {
      // Execute leaf action
      if (item.onPress) item.onPress();
      onSelect(item);
      // Close menu unless handled specifically
      onClose();
    }
  };

  const handleBack = () => {
    setNavStack(prev => prev.slice(0, -1));
    onIndexChange(0);
  };

  return (
    <View style={[StyleSheet.absoluteFill, { zIndex: 1000, elevation: 1000 }]} pointerEvents="box-none">
      {/* Tap backdrop to close */}
      <Pressable style={styles.menuBackdrop} onPress={onClose} />

      <View style={[styles.menuBox, { bottom: bottomOffset }]}>
        {/* Optional Title Bar (Matching selected item style but static) */}
        {activeTitle !== '' && (
          <View style={[styles.menuItem, { marginBottom: 4 }]}>
             <View style={styles.menuItemSelectedBg}>
                <Image source={ASSET_BASE_FRAME} style={styles.menuSelectedBaseImage} resizeMode="stretch" />
                <View style={[styles.menuOrnateClip, { left: 0 }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
             </View>
             <Text style={[styles.menuItemText, { color: '#fff', textAlign: 'center', paddingLeft: 0 }]}>
                {activeTitle}
             </Text>
          </View>
        )}

        <View style={styles.menuInnerBox}>
          {/* Back Button for Sub-menus */}
          {isSubMenu && (
            <TouchableOpacity
              style={styles.menuItem}
              onPress={handleBack}
              onPressIn={() => onIndexChange(-1)} // Special index for back
              activeOpacity={1}
            >
              {selectedIndex === -1 && (
                <View style={styles.menuItemSelectedBg}>
                  <Image source={ASSET_BASE_FRAME} style={styles.menuSelectedBaseImage} resizeMode="stretch" />
                  <View style={[styles.menuOrnateClip, { left: 0 }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                  <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}><Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" /></View>
                </View>
              )}
              <Text style={[styles.menuItemText, selectedIndex === -1 && styles.menuItemTextSelected]}>
                {'< Quay lại'}
              </Text>
            </TouchableOpacity>
          )}

          {activeItems.map((item, idx) => {
            const isSelected = idx === selectedIndex;
            const hasChildren = item.children && item.children.length > 0;

            return (
              <TouchableOpacity
                key={item.id}
                style={styles.menuItem}
                onPressIn={() => onIndexChange(idx)}
                onPress={() => handleItemPress(item, idx)}
                activeOpacity={1}
              >
                {isSelected && (
                  <View style={styles.menuItemSelectedBg}>
                    <Image
                      source={ASSET_BASE_FRAME}
                      style={styles.menuSelectedBaseImage}
                      resizeMode="stretch"
                    />
                    <View style={[styles.menuOrnateClip, { left: 0 }]}>
                      <Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" />
                    </View>
                    <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}>
                      <Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" />
                    </View>
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
    </View>
  );
};
