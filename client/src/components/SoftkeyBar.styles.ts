import { StyleSheet, Platform } from 'react-native';

const BAR_HEIGHT = 26; 
const ORNATE_CLIP_W = 20; 

export const styles = StyleSheet.create({
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
    paddingLeft: 60, // Exactly as in your current file
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
    width: 25, // Exactly as in your current file
    height: 25,
    marginLeft: 6, 
  },
  
  timeText: {
    color: '#ffffff', // Your latest manual revert color
    fontSize: 18,     // Your latest manual revert size
    fontWeight: 'bold',
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
});
