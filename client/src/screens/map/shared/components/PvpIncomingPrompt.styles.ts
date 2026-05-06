import { Dimensions, StyleSheet } from 'react-native';
import { PvpFontStyles } from '../JavaFontMetrics';

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
    width: Math.min(SCREEN_W * 0.86, 320),
  },
  content: {
    padding: 10,
    backgroundColor: '#f2fbff',  // v.aj (Java computed lighter fill)
  },
  title: {
    color: '#1f2f4d',
    ...PvpFontStyles.dialogTitle,
    marginBottom: 8,
  },
  message: {
    color: '#2d3a52',
    ...PvpFontStyles.arenaPrimary,
    marginTop: 4,
  },
  meta: {
    color: '#496ca5',
    ...PvpFontStyles.arenaSecondary,
    fontWeight: '700',
    marginTop: 6,
  },
  footer: {
    marginTop: 12,
    flexDirection: 'row',
    justifyContent: 'flex-end',
    gap: 8,
  },
  button: {
    minWidth: 80,
    minHeight: 32,
    borderWidth: 1,
    borderColor: '#96add3',
    backgroundColor: '#f0fbff',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 14,
  },
  buttonPrimary: {
    backgroundColor: '#6ef0ef',
    borderColor: '#20a5de',
  },
  buttonText: {
    color: '#1f2f4d',
    ...PvpFontStyles.buttonText,
  },
  buttonPrimaryText: {
    color: '#ffffff',
    ...PvpFontStyles.buttonText,
  },
});
