import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
} from 'react-native';
import { styles } from './PopupMenu.styles';

const ASSET_ORNATE      = require('../../assets/ui/frames/cornerskb.png');
const ASSET_BASE_FRAME  = require('../../assets/ui/frames/1.png');

export interface MenuItem {
  id: number | string;
  label: string;
}

interface PopupMenuProps {
  visible: boolean;
  items: MenuItem[];
  selectedIndex: number;
  onSelect: (item: MenuItem) => void;
  onIndexChange: (index: number) => void;
  onClose: () => void;
  bottomOffset?: number;
}

export const PopupMenu: React.FC<PopupMenuProps> = ({
  visible,
  items,
  selectedIndex,
  onSelect,
  onIndexChange,
  onClose,
  bottomOffset = 26,
}) => {
  if (!visible) return null;

  return (
    <View style={[styles.menuBox, { bottom: bottomOffset }]}>
        <View style={styles.menuInnerBox}>
          {items.map((item, idx) => {
            const isSelected = idx === selectedIndex;
            return (
              <TouchableOpacity
                key={item.id}
                style={styles.menuItem}
                onPressIn={() => onIndexChange(idx)}
                onPress={() => onSelect(item)}
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
                <Text style={[styles.menuItemText, isSelected && styles.menuItemTextSelected]}>
                  {item.label}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>
      </View>
  );
};
