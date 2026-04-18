import { StyleSheet, Dimensions, Platform } from 'react-native';

const { width, height } = Dimensions.get('window');
const STAGE_WIDTH = 348;
const STAGE_HEIGHT = 430;
const PANEL_WIDTH = 208;
const PANEL_HEIGHT = 318;
const PANEL_TOP = 18;
const PANEL_RIGHT = 0;
const PREVIEW_STAGE_WIDTH = 140;
const PREVIEW_STAGE_HEIGHT = 188;
const STONE_WIDTH = 112;
const STONE_HEIGHT = 40;
const STONE_BOTTOM = 16;
const STONE_TO_PANEL_BOTTOM_OFFSET = 60;
const STONE_PANEL_GAP = 5;
const CHARACTER_TO_STONE_SURFACE_OFFSET = 5;

const panelLeft = STAGE_WIDTH - PANEL_WIDTH - PANEL_RIGHT;
const stoneTop = PANEL_TOP + PANEL_HEIGHT - STONE_TO_PANEL_BOTTOM_OFFSET;
const stoneTopInPreview = PREVIEW_STAGE_HEIGHT - STONE_BOTTOM - STONE_HEIGHT;
const previewTop = stoneTop - stoneTopInPreview;
const stoneLeft = panelLeft - STONE_WIDTH - STONE_PANEL_GAP;
const previewLeft = stoneLeft + Math.round((STONE_WIDTH - PREVIEW_STAGE_WIDTH) / 2);
const spriteBottom = STONE_BOTTOM + STONE_HEIGHT - CHARACTER_TO_STONE_SURFACE_OFFSET;

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
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    paddingBottom: 30,
  },
  stageLayout: {
    width: STAGE_WIDTH,
    height: STAGE_HEIGHT,
    position: 'relative',
  },
  previewColumn: {
    position: 'absolute',
    left: previewLeft,
    top: previewTop,
    width: PREVIEW_STAGE_WIDTH,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  previewStage: {
    width: PREVIEW_STAGE_WIDTH,
    height: PREVIEW_STAGE_HEIGHT,
    alignItems: 'center',
    justifyContent: 'flex-end',
    position: 'relative',
  },
  previewSpriteCanvas: {
    position: 'absolute',
    bottom: spriteBottom,
    alignItems: 'center',
    justifyContent: 'center',
    transform: [{ translateX: 0 }],
  },
  stonePlatform: {
    width: STONE_WIDTH,
    height: STONE_HEIGHT,
    position: 'absolute',
    bottom: STONE_BOTTOM,
  },

  selectionPanel: {
    position: 'absolute',
    top: PANEL_TOP,
    right: PANEL_RIGHT - 10,
    width: PANEL_WIDTH,
    minHeight: PANEL_HEIGHT,
    paddingHorizontal: 10,
    paddingVertical: 12,
  },
  selectionPanelInner: {
    minHeight: 294,
  },
  selectionRow: {
    marginBottom: 5,
    zIndex: 2,
  },
  selectionRowActive: {
    transform: [{ scale: 1.01 }],
  },
  label: {
    color: '#111111',
    fontSize: 15,
    fontWeight: '900',
    marginBottom: 3,
    fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  },
  valueBox: {
    minHeight: 25,
    backgroundColor: '#e1d4bf',
    borderWidth: 1,
    borderColor: '#b79f72',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 5,
  },
  valueBoxActive: {
    backgroundColor: '#7ba9f6',
    borderColor: '#235dd3',
  },
  valueText: {
    flex: 1,
    color: '#1b1b1b',
    fontSize: 13,
    fontWeight: '700',
    textAlign: 'center',
  },
  valueTextActive: {
    color: '#ffffff',
  },
  arrowButton: {
    width: 18,
    height: 18,
    justifyContent: 'center',
    alignItems: 'center',
  },
  arrowSpacer: {
    width: 18,
    height: 18,
  },
  arrowIcon: {
    width: 16,
    height: 16,
    transform: [{ rotate: '90deg' }],
  },
  arrowIconLeft: {
    transform: [{ rotate: '-90deg' }],
  },
  panelWatermark: {
    position: 'absolute',
    right: 10,
    bottom: 12,
    width: 64,
    height: 64,
    borderRadius: 32,
    borderWidth: 1.5,
    borderColor: 'rgba(80, 120, 160, 0.1)',
    opacity: 0.45,
  },

  softKeyBarContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
  },
});
