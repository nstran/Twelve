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
    paddingHorizontal: 6, // Khoảng cách cơ bản (dùng cho chữ)
    zIndex: 4,
  },
  softkeyArea: {
    width: 12, // Vùng bấm thực tế cực nhỏ (12px)
    height: 12,
    alignSelf: 'center',
    justifyContent: 'center',
    alignItems: 'center',
    overflow: 'visible', // Để icon to hiển thị ra ngoài vùng bấm nhỏ
  },
  
  sharpIconTop: {
    width: 24, // Trả lại kích thước icon to rõ (24px)
    height: 24,
    position: 'absolute', // Để nó không bị bó hẹp bởi cái "vùng" 12px
  },

  softkeyLabelText: {
    color: '#ffffff',
    fontSize: 13,
    fontWeight: 'bold',
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
