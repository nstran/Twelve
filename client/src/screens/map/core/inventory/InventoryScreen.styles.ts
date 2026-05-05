import { StyleSheet } from 'react-native';

/**
 * Styles for the inventory screen components.
 *
 * Colors derived from original decompiled source:
 * - Empty cell bg: #93c6fa (fg.java slot color 6647295)
 * - Filled cell bg: #ced1cf (original grey)
 * - Cell border top/left: #ffffff, bottom/right: #808080 (3D bevel)
 * - Grid container border: #4d83a1
 * - Grid container bg: #edf7ff
 * - Target highlight: #FEFF77 / #FFF930 / #FFFDD3 (hh.java)
 * - Selected cell: #00bfa5 border
 */

export const styles = StyleSheet.create({
  // ---------------------------------------------------------------------------
  // Screen container
  // ---------------------------------------------------------------------------
  screenOverlay: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 900,
    elevation: 900,
    alignItems: 'center',
    justifyContent: 'center',
  },
  backdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(0, 0, 0, 0.28)',
  },
  canvas: {
    position: 'relative',
    overflow: 'hidden',
    backgroundColor: '#1a3a5c',
  },

  // ---------------------------------------------------------------------------
  // Header: name + level
  // ---------------------------------------------------------------------------
  nameText: {
    position: 'absolute',
    fontSize: 10,
    fontWeight: '800',
    color: '#FFFFFF',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  levelText: {
    position: 'absolute',
    fontSize: 9,
    fontWeight: '700',
    color: '#FFFF00',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },

  // ---------------------------------------------------------------------------
  // Equipped slots
  // ---------------------------------------------------------------------------
  slotBackground: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: '#5a7a8a',
    backgroundColor: '#2a4a6a',
    alignItems: 'center',
    justifyContent: 'center',
  },
  slotPlaceholder: {
    overflow: 'hidden',
  },
  slotPlaceholderSheet: {
    position: 'absolute',
    top: 0,
  },

  // ---------------------------------------------------------------------------
  // Avatar preview
  // ---------------------------------------------------------------------------
  avatarBox: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: '#7c9eb2',
    backgroundColor: '#e7fbff',
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },

  // ---------------------------------------------------------------------------
  // Capacity text
  // ---------------------------------------------------------------------------
  capacityText: {
    position: 'absolute',
    fontSize: 8,
    fontWeight: '700',
    color: '#FFFFFF',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },

  // ---------------------------------------------------------------------------
  // Bag grid container
  // ---------------------------------------------------------------------------
  gridContainer: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: '#4d83a1',
    backgroundColor: '#edf7ff',
    overflow: 'hidden',
  },

  // ---------------------------------------------------------------------------
  // Cell (shared by equipped slot cells and bag grid cells)
  // ---------------------------------------------------------------------------
  cell: {
    position: 'absolute',
    borderTopWidth: 1,
    borderLeftWidth: 1,
    borderBottomWidth: 1,
    borderRightWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  cellEmpty: {
    backgroundColor: '#93c6fa',
    borderTopColor: '#ffffff',
    borderLeftColor: '#ffffff',
    borderBottomColor: '#808080',
    borderRightColor: '#808080',
  },
  cellFilled: {
    backgroundColor: '#ced1cf',
    borderTopColor: '#ffffff',
    borderLeftColor: '#ffffff',
    borderBottomColor: '#808080',
    borderRightColor: '#808080',
  },
  cellSelected: {
    borderColor: '#00bfa5',
    borderWidth: 2,
  },
  cellTarget: {
    borderColor: '#FEFF77',
    borderWidth: 2,
  },
  cellIcon: {
    // width/height set dynamically
  },
  missingIconText: {
    color: '#999',
    fontWeight: '700',
  },

  // ---------------------------------------------------------------------------
  // Cell overlays — dc.java evidence
  // ---------------------------------------------------------------------------
  brokenHeart: {
    position: 'absolute',
  },
  brokenHeartText: {
    color: '#FF0000',
    fontWeight: '900',
  },
  rankStar: {
    position: 'absolute',
    color: '#FFD700',
    fontWeight: '900',
    textShadowColor: '#FF8C00',
    textShadowOffset: { width: 0, height: 0 },
    textShadowRadius: 2,
  },
  enhancementText: {
    position: 'absolute',
    color: '#FFFF00',
    fontWeight: '900',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  quantityText: {
    position: 'absolute',
    color: '#FFFFFF',
    fontWeight: '900',
    textShadowColor: '#000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },

  // ---------------------------------------------------------------------------
  // Tooltip — fw.java evidence
  // ---------------------------------------------------------------------------
  tooltipContainer: {
    position: 'absolute',
    left: 0,
    right: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.85)',
    borderWidth: 1,
    borderColor: '#5a7a8a',
    paddingHorizontal: 6,
    paddingVertical: 4,
    zIndex: 2000,
    elevation: 2000,
  },
  tooltipName: {
    fontSize: 11,
    fontWeight: '800',
    marginBottom: 2,
  },
  tooltipLine: {
    fontSize: 9,
    color: '#FFFFFF',
    lineHeight: 13,
  },
  tooltipLineRed: {
    fontSize: 9,
    color: '#FF4444',
    lineHeight: 13,
    fontWeight: '700',
  },
  tooltipLineGreen: {
    fontSize: 9,
    color: '#44FF44',
    lineHeight: 13,
  },
  tooltipLineYellow: {
    fontSize: 9,
    color: '#FFFF00',
    lineHeight: 13,
  },

  // ---------------------------------------------------------------------------
  // Detail dialog — hg.java evidence
  // ---------------------------------------------------------------------------
  detailOverlay: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 3000,
    elevation: 3000,
    alignItems: 'center',
    justifyContent: 'center',
  },
  detailBackdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  detailPanel: {
    backgroundColor: '#1a3a5c',
    borderWidth: 2,
    borderColor: '#5a7a8a',
    paddingHorizontal: 10,
    paddingVertical: 8,
    maxWidth: '90%',
  },
  detailIconRow: {
    alignItems: 'center',
    marginBottom: 4,
  },
  detailIcon: {
    width: 32,
    height: 32,
  },
  detailNameRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    marginBottom: 4,
  },
  detailName: {
    fontSize: 12,
    fontWeight: '800',
  },
  detailEnhancement: {
    fontSize: 11,
    fontWeight: '800',
    color: '#FFFF00',
  },
  detailSeparator: {
    height: 1,
    backgroundColor: '#5a7a8a',
    marginVertical: 4,
  },
  detailLine: {
    fontSize: 10,
    color: '#FFFFFF',
    lineHeight: 14,
  },
  detailLineGreen: {
    fontSize: 10,
    color: '#44FF44',
    lineHeight: 14,
  },
  detailLineRed: {
    fontSize: 10,
    color: '#FF4444',
    lineHeight: 14,
    fontWeight: '700',
  },
  detailLineYellow: {
    fontSize: 10,
    color: '#FFFF00',
    lineHeight: 14,
  },
  detailDescription: {
    fontSize: 9,
    color: '#CCCCCC',
    lineHeight: 13,
    marginTop: 4,
  },
  detailCloseRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 6,
  },
  detailCloseButton: {
    paddingHorizontal: 16,
    paddingVertical: 4,
    backgroundColor: '#2a4a6a',
    borderWidth: 1,
    borderColor: '#5a7a8a',
  },
  detailCloseText: {
    fontSize: 10,
    color: '#FFFFFF',
    fontWeight: '700',
  },
});
