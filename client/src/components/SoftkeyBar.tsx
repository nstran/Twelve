import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  StyleSheet,
  ImageSourcePropType,
} from 'react-native';
import { styles } from './SoftkeyBar.styles';

const ASSET_ORNATE     = require('../../assets/ui/frames/cornerskb.png'); 
const ASSET_BASE_FRAME = require('../../assets/ui/frames/1.png'); 
const ASSET_SHARP_ICON = require('../../assets/ui/icons/icon_sharpest_1.png'); 

interface SoftkeyBarProps {
  onLeftPress?: () => void;
  onRightPress?: () => void;
  onCenterPress?: () => void;
  menuVisible?: boolean;
  width: number;
  // New flexible icon props
  leftIcon?: ImageSourcePropType;
  rightIcon?: ImageSourcePropType;
}

export const SoftkeyBar: React.FC<SoftkeyBarProps> = ({
  onLeftPress,
  onRightPress,
  onCenterPress,
  width,
  leftIcon,
  rightIcon,
}) => {
  const [time, setTime] = useState('00:00');

  useEffect(() => {
    const timer = setInterval(() => {
      const now = new Date();
      setTime(`${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`);
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  return (
    <View style={[styles.container, { width }]}>
      {/* ─── LAYER 1: BASE BACKGROUND ─── */}
      <View style={styles.baseFrameLayer}>
         <Image 
            source={ASSET_BASE_FRAME} 
            style={[styles.fullBaseImage, { width: width + 20 }]} 
            resizeMode="stretch" 
         />
      </View>

      {/* ─── LAYER 2: CONTENT & TIME ─── */}
      <View style={styles.content}>
        <View style={styles.leftPlaceholder} />
        <View style={styles.centerContent}>
           <Text style={styles.timeText}>{time}</Text>
        </View>
        <View style={styles.rightPlaceholder} />
      </View>

      {/* ─── LAYER 3: ORNATE DECORATION ─── */}
      <View style={StyleSheet.absoluteFill} pointerEvents="none">
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

      {/* ─── LAYER 4: TOPMOST ICON (DYNAMIC) ─── */}
      <View style={styles.topmostLayer}>
        <TouchableOpacity style={styles.softkeyArea} onPress={onLeftPress} activeOpacity={0.6}>
           <Image 
             source={leftIcon || ASSET_SHARP_ICON} 
             style={styles.sharpIconTop} 
             resizeMode="contain" 
           />
        </TouchableOpacity>
        
        <View style={{ flex: 1 }} />
        
        <TouchableOpacity style={styles.softkeyArea} onPress={onRightPress} activeOpacity={0.6}>
           {rightIcon && (
             <Image 
               source={rightIcon} 
               style={[styles.sharpIconTop, { marginLeft: 0, marginRight: 6, alignSelf: 'flex-end' }]} 
               resizeMode="contain" 
             />
           )}
        </TouchableOpacity>
      </View>
    </View>
  );
};
