import { Dimensions, StyleSheet } from 'react-native';

const { width: SCREEN_W } = Dimensions.get('window');

export const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 28,
    zIndex: 95,
    alignItems: 'center',
  },
  frame: {
    width: Math.min(SCREEN_W * 0.88, 360),
  },
  content: {
    paddingHorizontal: 12,
    paddingVertical: 10,
    backgroundColor: '#fff2c8',
  },
  title: {
    color: '#8a5700',
    fontSize: 15,
    fontWeight: '900',
    marginBottom: 4,
  },
  message: {
    color: '#2f1d12',
    fontSize: 13,
    fontWeight: '700',
    lineHeight: 17,
  },
});
