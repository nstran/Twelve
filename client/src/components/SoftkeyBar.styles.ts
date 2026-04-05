import { StyleSheet, Platform } from 'react-native';

export const styles = StyleSheet.create({
  container: {
    height: 26,
    flexDirection: 'row',
    backgroundColor: '#0055cc', 
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    zIndex: 100, 
  },
  
  // ─── LAYER 1: BASE IMAGE (SILK) ───
  baseFrameLayer: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1,
  },
  fullBaseImage: {
    height: 26,
    marginLeft: -10,
  },

  // ─── LAYER 2: CONTENT (CENTER) ───
  content: {
    ...StyleSheet.absoluteFillObject,
    flexDirection: 'row',
    alignItems: 'center',
    zIndex: 2,
    justifyContent: 'center',
  },
  centerContent: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    // Removed the problematic padding to keep it centered
  },
  timeText: {
    color: '#ffffff',
    fontSize: 18,
    fontWeight: 'bold',
    textShadowColor: 'rgba(0,0,0,0.5)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
    textAlign: 'center',
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },

  // ─── LAYER 3: TOPMOST INTERACTION / LABELS ───
  topmostLayer: {
    ...StyleSheet.absoluteFillObject,
    flexDirection: 'row',
    zIndex: 4,
  },
  softkeyArea: {
    flex: 1,
    height: 26,
    justifyContent: 'center',
  },
  
  sharpIconTop: {
    width: 25,
    height: 25,
    marginLeft: 6,
  },

  softkeyLabelText: {
    color: '#ffffff',
    fontSize: 13,
    fontWeight: 'bold',
    paddingLeft: 10, 
    textShadowColor: 'rgba(0,0,0,0.8)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },

  // ─── LAYER 4: ORNATE DECORATION ───
  overlayRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    height: 26,
    zIndex: 99,
  },
  ornateClip: {
    width: 28, 
    height: 26,
    overflow: 'hidden',
  },
  ornateImage: {
    width: 60,
    height: 26,
  },
});
