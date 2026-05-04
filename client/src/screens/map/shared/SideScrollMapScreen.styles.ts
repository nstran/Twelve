import { Dimensions, StyleSheet } from 'react-native';

const { width: SCREEN_W } = Dimensions.get('window');
const LAYER_BG = 0;

export const styles = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#000' },
  scroll: { flex: 1 },
  bg: { position: 'absolute', top: 0, left: 0, zIndex: LAYER_BG },
  mapDebugOverlay: {
    position: 'absolute',
    left: 8,
    top: 124,
    zIndex: 250,
    maxWidth: Math.min(SCREEN_W - 16, 460),
    paddingHorizontal: 8,
    paddingVertical: 6,
    borderWidth: 1,
    borderColor: '#f7e26b',
    backgroundColor: 'rgba(0, 0, 0, 0.72)',
  },
  mapDebugTitle: {
    color: '#f7e26b',
    fontSize: 11,
    fontWeight: '900',
    lineHeight: 14,
  },
  mapDebugText: {
    color: '#ffffff',
    fontSize: 10,
    fontWeight: '700',
    lineHeight: 13,
  },
});
