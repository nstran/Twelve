import React, { useState, useEffect } from 'react';
import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  Image,
  Platform,
} from 'react-native';

const ASSET_ORNATE     = require('../../assets/ui/frames/cornerskb.png'); 
const ASSET_BASE_FRAME = require('../../assets/ui/frames/1.png'); 
const ASSET_SHARP_ICON = require('../../assets/ui/icons/icon_sharpest_1.png'); 

interface SoftkeyBarProps {
  onLeftPress?: () => void;
  onRightPress?: () => void;
  onCenterPress?: () => void;
  menuVisible?: boolean;
  width: number;
}

const BAR_HEIGHT = 26; 
const ORNATE_CLIP_W = 20; 

export const SoftkeyBar: React.FC<SoftkeyBarProps> = ({
  onLeftPress,
  onRightPress,
  onCenterPress,
  width,
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
      {/* ─── LAYER 1: BASE BACKGROUND (1.png) ─── */}
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

      {/* ─── LAYER 3: ORNATE DECORATION OVERLAY ─── */}
      <View style={StyleSheet.absoluteFill} pointerEvents="none">
        <View style={styles.overlayRow}>
          {/* Left Corner */}
          <View style={styles.ornateClip}>
             <Image source={ASSET_ORNATE} style={styles.ornateImage} resizeMode="stretch" />
          </View>
          
          <View style={{ flex: 1 }} />
          
          {/* Right Corner (Flipped) */}
          <View style={[styles.ornateClip, { transform: [{ scaleX: -1 }] }]}>
             <Image source={ASSET_ORNATE} style={styles.ornateImage} resizeMode="stretch" />
          </View>
        </View>
      </View>

      {/* ─── LAYER 4: TOPMOST ICON & CLICK AREA ─── */}
      <View style={styles.topmostLayer}>
        <TouchableOpacity style={styles.softkeyArea} onPress={onLeftPress} activeOpacity={0.6}>
           <Image 
             source={ASSET_SHARP_ICON} 
             style={styles.sharpIconTop} 
             resizeMode="contain" 
           />
        </TouchableOpacity>
        
        <View style={{ flex: 1 }} />
        
        <TouchableOpacity style={styles.softkeyArea} onPress={onRightPress} activeOpacity={0.6} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    height: BAR_HEIGHT,
    position: 'absolute',
    bottom: 0,
    zIndex: 1000,
  },
  baseFrameLayer: {
    ...StyleSheet.absoluteFillObject,
    left: -10,
  },
  fullBaseImage: {
    height: '100%',
  },
  overlayRow: {
    flexDirection: 'row',
    height: '100%',
  },
  ornateClip: {
    width: ORNATE_CLIP_W,
    height: '100%',
    overflow: 'hidden',
  },
  ornateImage: {
    width: 44, 
    height: '100%',
  },
  
  // Content Layer
  content: {
    ...StyleSheet.absoluteFillObject,
    flexDirection: 'row',
    alignItems: 'center',
  },
  centerContent: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingLeft: 60, // Updated per user's latest manual change
  },
  leftPlaceholder: { width: 50 },
  rightPlaceholder: { width: 50 },

  // Top Layer
  topmostLayer: {
    ...StyleSheet.absoluteFillObject,
    flexDirection: 'row',
  },
  softkeyArea: {
    width: 50, 
    height: '100%',
    justifyContent: 'center',
    alignItems: 'flex-start',
    paddingLeft: 0, 
  },
  sharpIconTop: {
    width: 25, // Updated per user's latest manual change
    height: 25,
    marginLeft: 6, 
  },
  
  timeText: {
    color: '#ffffff', // Changed to BLACK as requested
    fontSize: 18, 
    fontWeight: 'bold',
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
});
