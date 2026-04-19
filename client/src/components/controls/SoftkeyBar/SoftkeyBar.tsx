import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  ImageSourcePropType,
} from 'react-native';
import { styles } from './SoftkeyBar.styles';

const ASSET_ORNATE      = require('../../../../assets/ui_legacy/00_corner_frames/cornerskb.png'); 
const ASSET_BASE_FRAME  = require('../../../../assets/ui_legacy/00_corner_frames/1.png'); 
const ASSET_DEFAULT_ICON = require('../../../../assets/ui_legacy/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_CANCEL_ICON  = require('../../../../assets/ui_legacy/11_softkey_icons_confirmed/icon_cancel.png');
const ASSET_OK_ICON      = require('../../../../assets/ui_legacy/11_softkey_icons_confirmed/icon_ok.png');

interface SoftkeyBarProps {
  onLeftPress?: () => void;
  onRightPress?: () => void;
  onCenterPress?: () => void;
  width: number;
  leftIcon?: ImageSourcePropType;
  rightIcon?: ImageSourcePropType;
  leftLabel?: string;
  rightLabel?: string;
  centerLabel?: string;
  isMenuOpen?: boolean;
}

export const SoftkeyBar: React.FC<SoftkeyBarProps> = ({
  onLeftPress,
  onRightPress,
  onCenterPress,
  width,
  leftIcon,
  rightIcon,
  leftLabel,
  rightLabel,
  centerLabel,
  isMenuOpen,
}) => {
  const getCurrentTime = () => {
    const now = new Date();
    return `${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;
  };

  const [time, setTime] = useState(getCurrentTime());

  useEffect(() => {
    const timer = setInterval(() => {
      setTime(getCurrentTime());
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  return (
    <View style={[styles.container, { width }]}>
      {/* ─── LAYER 1: BASE BACKGROUND (SILK) ─── */}
      <View style={styles.baseFrameLayer}>
         <Image 
            source={ASSET_BASE_FRAME} 
            style={[styles.fullBaseImage, { width: width + 20 }]} 
            resizeMode="stretch" 
         />
      </View>

      {/* ─── LAYER 2: ORNATE DECORATION (AS DECORATION ONLY) ─── */}
      <View style={[styles.absoluteFill, { zIndex: 2 }]} pointerEvents="none">
        <View style={styles.overlayRow}>
          <View style={styles.ornateClip}>
             <Image source={ASSET_ORNATE} style={styles.ornateImage} resizeMode="stretch" />
          </View>
          <View style={{ flex: 1 }} />
          <View style={[styles.ornateClip, { transform: [{ scaleX: -1 }] }]}>
             <Image source={ASSET_ORNATE} style={styles.ornateImage} resizeMode="stretch" />
          </View>
        </View>
      </View>

      <View style={[styles.content, { zIndex: 3 }]}>
        <View style={styles.centerContent}>
           <Text style={styles.timeText}>{centerLabel || time}</Text>
        </View>
      </View>

      {/* ─── LAYER 4: TOPMOST INTERACTION / LABELS / ICONS ─── */}
      <View style={[styles.topmostLayer, { zIndex: 10 }]}>
        <TouchableOpacity 
          style={[
            styles.softkeyArea, 
            leftLabel ? { width: 'auto', minWidth: 40 } : { marginLeft: 6 } // Icon lùi vào 6px (tổng 12px), Chữ không lùi (tổng 6px)
          ]} 
          onPress={onLeftPress} 
          activeOpacity={0.6}
        >
           {leftIcon ? (
             <Image 
               source={leftIcon} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : isMenuOpen ? (
             <Image 
               source={ASSET_OK_ICON} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : leftLabel ? (
             <Text style={styles.softkeyLabelText}>{leftLabel}</Text>
           ) : onLeftPress ? (
             <Image 
               source={ASSET_DEFAULT_ICON} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : null}
        </TouchableOpacity>
        
        <View style={{ flex: 1 }} />
        
        <TouchableOpacity 
          style={styles.softkeyArea} 
          onPress={onCenterPress} 
          activeOpacity={0.6}
          disabled={!onCenterPress}
        />

        <View style={{ flex: 1 }} />
        
        <TouchableOpacity 
          style={[
            styles.softkeyArea, 
            rightLabel ? { width: 'auto', minWidth: 40 } : { marginRight: 6 } // Icon lùi vào 6px (tổng 12px), Chữ không lùi (tổng 6px)
          ]} 
          onPress={onRightPress} 
          activeOpacity={0.6}
        >
           {rightIcon ? (
             <Image 
               source={rightIcon} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : isMenuOpen ? (
             <Image 
               source={ASSET_CANCEL_ICON} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : rightLabel ? (
             <Text style={styles.softkeyLabelText}>{rightLabel}</Text>
           ) : onRightPress ? (
             <Image 
               source={ASSET_CANCEL_ICON} 
               style={[styles.sharpIconTop, { marginLeft: 0 }]} 
               resizeMode="contain" 
             />
           ) : null}
        </TouchableOpacity>
      </View>
    </View>
  );
};
