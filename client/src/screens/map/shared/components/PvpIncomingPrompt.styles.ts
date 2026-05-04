import { Dimensions, StyleSheet } from 'react-native';

const { width: SCREEN_W } = Dimensions.get('window');

export const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 100,
    justifyContent: 'center',
    alignItems: 'center',
  },
  backdrop: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.55)',
  },
  frame: {
    width: Math.min(SCREEN_W * 0.86, 340),
  },
  content: {
    padding: 12,
    backgroundColor: '#fff4d8',
  },
  title: {
    color: '#8a5700',
    fontSize: 24,
    fontWeight: '800',
  },
  message: {
    color: '#2f1d12',
    fontSize: 14,
    fontWeight: '700',
    marginTop: 8,
  },
  meta: {
    color: '#7b4c18',
    fontSize: 12,
    fontWeight: '800',
    marginTop: 6,
  },
  footer: {
    marginTop: 8,
    flexDirection: 'row',
    justifyContent: 'flex-end',
    gap: 8,
  },
  button: {
    minWidth: 82,
    minHeight: 30,
    borderWidth: 1,
    borderColor: '#c88b2f',
    backgroundColor: '#fff1cd',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 12,
  },
  buttonPrimary: {
    backgroundColor: '#b26a2f',
  },
  buttonText: {
    color: '#5a3a0f',
    fontSize: 14,
    fontWeight: '800',
  },
  buttonPrimaryText: {
    color: '#fff4c9',
    fontSize: 14,
    fontWeight: '900',
  },
});
