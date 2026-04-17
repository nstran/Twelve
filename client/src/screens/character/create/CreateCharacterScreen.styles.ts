import { StyleSheet, Dimensions, Platform } from 'react-native';

const { width, height } = Dimensions.get('window');

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  background: {
    position: 'absolute',
    width: width,
    height: height,
  },

  legacyScene: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 12,
    paddingBottom: 32,
    gap: 12,
  },
  previewColumn: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  previewStage: {
    width: 180,
    height: 190,
    alignItems: 'center',
    justifyContent: 'flex-end',
    position: 'relative',
  },
  previewSpriteCanvas: {
    position: 'absolute',
    bottom: 38,
    alignItems: 'center',
    justifyContent: 'center',
  },
  stonePlatform: {
    width: 116,
    height: 60,
    position: 'absolute',
    bottom: 0,
  },

  selectionPanel: {
    width: 264,
    minHeight: 350,
    paddingHorizontal: 24,
    paddingVertical: 28,
    position: 'relative',
    overflow: 'hidden',
  },
  selectionRow: {
    marginBottom: 12,
    zIndex: 2,
  },
  selectionRowActive: {
    transform: [{ scale: 1.01 }],
  },
  label: {
    color: '#111111',
    fontSize: 19,
    fontWeight: '900',
    marginBottom: 5,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },
  valueBox: {
    minHeight: 34,
    backgroundColor: '#e1d4bf',
    borderWidth: 1,
    borderColor: '#b79f72',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 6,
  },
  valueBoxActive: {
    backgroundColor: '#7ba9f6',
    borderColor: '#235dd3',
  },
  valueText: {
    flex: 1,
    color: '#1b1b1b',
    fontSize: 16,
    fontWeight: '700',
    textAlign: 'center',
  },
  valueTextActive: {
    color: '#ffffff',
  },
  arrowButton: {
    width: 20,
    height: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  arrowSpacer: {
    width: 20,
    height: 20,
  },
  arrowIcon: {
    width: 18,
    height: 18,
  },
  panelWatermark: {
    position: 'absolute',
    right: 8,
    bottom: 10,
    width: 86,
    height: 86,
    borderRadius: 43,
    borderWidth: 2,
    borderColor: 'rgba(80, 120, 160, 0.12)',
    opacity: 0.7,
  },

  softKeyBarContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
  },
});
