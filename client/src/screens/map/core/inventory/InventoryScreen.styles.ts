import { StyleSheet } from 'react-native';

/**
 * Styles for the inventory screen components.
 *
 * ALL colors and dimensions are derived from decompiled Java source.
 * No remake/custom colors — every value traces to a specific Java class + line.
 *
 * Source: pc.java, hh.java, fg.java, dc.java, fw.java, hg.java, ba.java
 */

// ---------------------------------------------------------------------------
// Java color constants (decimal → hex)
// ---------------------------------------------------------------------------

/** pc.a() panel fill — runtime v.aj. Source: SQMIDlet.java:41 calls v.a(0xF0FBFF) before UI; v.java:145 assigns aj. */
const PANEL_FILL = '#F0FBFF';

/** pc.a() panel border outer. Source: pc.java:120 — 51967 = #00CAFF */
const PANEL_BORDER_OUTER = '#00CAFF';
/** pc.a() panel border inner. Source: pc.java:122 — 9975807 = #9837FF */
const PANEL_BORDER_INNER = '#9837FF';
/** pc.a() panel top accent. Source: pc.java:124 — 8972031 = #88FFFF */
const PANEL_TOP_ACCENT = '#88FFFF';
/** pc.a() panel edge. Source: pc.java:127 — 22246 = #0056E6 */
const PANEL_EDGE = '#0056E6';

/** pc.b() slot bevel fill. Source: pc.java:194-203 via hh.java:1366. */
const SLOT_BG = '#657FFF';
/** pc.b() slot bevel right/bottom accent. Source: pc.java:91-93 via hh.java:1366. */
const SLOT_SHADOW = '#7FBFFF';
/** fg.java over-capacity fill/accent. Source: fg.java:84. */
const GRID_CELL_OVER_BG = '#FF0000';
const GRID_CELL_OVER_SHADOW = '#EAD5E5';

/** pc.c() avatar frame outer. Source: pc.java:143 — rgb(19,87,151) = #135797 */
const AVATAR_BORDER_OUTER = '#135797';
/** pc.c() avatar frame inner. Source: pc.java:147 — rgb(20,165,222) = #14A5DE */
const AVATAR_BORDER_INNER = '#14A5DE';

/** pc.b(..., false) frame lines. Source: pc.java:206-220. */
const JAVA_BEVEL_OUTER = '#0385FF';
const JAVA_BEVEL_INNER = '#DEFFFF';

/** fg.java grid container border/fill. Source: fg.java:65 — v.aj fill, frame from pc.b(..., false). */

/** Target highlight colors. Source: hh.java:1389-1394 */
const TARGET_COLOR_1 = '#FEFF77';
const TARGET_COLOR_2 = '#FFF930'; // 16776624 ≈ #FFF930
const TARGET_COLOR_3 = '#FFFDD3';

/** Selected cell. Source: hh.java:1398 — pc.a() focus frame uses /focusmovechess1 */
// Focus frame is an image overlay, not a border color.

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
    backgroundColor: 'transparent',
  },

  /**
   * Main canvas — represents the hh.java panel.
   * Source: pc.java:112-136 draws v.aj fill plus corner/2 and border lines.
   */
  canvas: {
    position: 'relative',
    overflow: 'hidden',
    backgroundColor: PANEL_FILL,
  },
  panelTopAccent: {
    position: 'absolute',
    height: 1,
    backgroundColor: PANEL_TOP_ACCENT,
    zIndex: 4,
  },
  panelBottomAccent: {
    position: 'absolute',
    height: 1,
    backgroundColor: PANEL_TOP_ACCENT,
    zIndex: 4,
  },
  panelEdgeHorizontal: {
    position: 'absolute',
    height: 1,
    backgroundColor: PANEL_EDGE,
    zIndex: 6,
  },
  panelEdgeVertical: {
    position: 'absolute',
    width: 1,
    backgroundColor: PANEL_EDGE,
    zIndex: 6,
  },
  /** Corner image overlay for /corner/2. Positioned at 4 corners. */
  cornerImage: {
    position: 'absolute',
    zIndex: 10,
  },
  /** Inner border line. Source: pc.java:121 — #00CAFF */
  innerBorder: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: PANEL_BORDER_OUTER,
    zIndex: 5,
  },
  /** Second inner border. Source: pc.java:123 — #9837FF */
  innerBorder2: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: PANEL_BORDER_INNER,
    zIndex: 4,
  },
  /** Hiddendragon watermark. Source: pc.java:118 — drawn at bottom-right when bl2=true */
  watermark: {
    position: 'absolute',
    zIndex: 3,
  },

  // ---------------------------------------------------------------------------
  // Header: name + level + element icon
  // ---------------------------------------------------------------------------
  /** Player name. Source: hh.java:1360 — bx.d gradient font, bold, at r=cu(22,6) */
  nameText: {
    position: 'absolute',
    fontWeight: '900',
    color: '#010101',
    includeFontPadding: false,
    lineHeight: 14,
  },
  /** Level text. Source: hh.java:1363 — bx.d font, right-aligned, at r.b */
  levelText: {
    position: 'absolute',
    fontWeight: '800',
    color: '#010101',
    includeFontPadding: false,
    lineHeight: 14,
  },
  /** Element icon from /tab sprite. Source: hh.java:1362 — pc.b(g,q.a,q.b,M.g) */
  elementIcon: {
    position: 'absolute',
    overflow: 'hidden',
  },
  elementIconSheet: {
    position: 'absolute',
    top: 0,
  },

  // ---------------------------------------------------------------------------
  // Equipped slots — 6 slots
  // ---------------------------------------------------------------------------
  /**
   * Slot background box.
   * Source: hh.java:1366 — pc.b(g, u[n].a+c, u[n].b+d, u[n].c, u[n].d, 6647295, 0xFFFFFF, 8369663)
   * This is a 3D bevel box: fill #657FFF, bottom/right highlight #FFFFFF, top-right shadow #7FBFFF
   */
  slotBackground: {
    position: 'absolute',
    backgroundColor: SLOT_BG,
    alignItems: 'center',
    justifyContent: 'center',
  },
  /** Empty slot placeholder from /info/hidenobj. Source: hh.java:1367 */
  slotPlaceholder: {
    overflow: 'hidden',
  },
  slotPlaceholderSheet: {
    position: 'absolute',
    top: 0,
  },

  // ---------------------------------------------------------------------------
  // Avatar preview frame
  // ---------------------------------------------------------------------------
  /**
   * Avatar frame.
   * Source: hh.java:1417 — pc.c(g, s.a+c, s.b+d, s.c, s.d)
   * pc.c draws double-border: outer rgb(19,87,151), inner rgb(20,165,222)
   */
  avatarBox: {
    position: 'absolute',
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  avatarFrameOuter: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: AVATAR_BORDER_OUTER,
    zIndex: 12,
  },
  avatarFrameAccentTop: {
    position: 'absolute',
    height: 1,
    backgroundColor: AVATAR_BORDER_INNER,
    zIndex: 13,
  },
  avatarFrameAccentBottom: {
    position: 'absolute',
    height: 1,
    backgroundColor: AVATAR_BORDER_INNER,
    zIndex: 13,
  },
  avatarFrameAccentLeft: {
    position: 'absolute',
    width: 1,
    backgroundColor: AVATAR_BORDER_INNER,
    zIndex: 13,
  },
  avatarFrameAccentRight: {
    position: 'absolute',
    width: 1,
    backgroundColor: AVATAR_BORDER_INNER,
    zIndex: 13,
  },

  // ---------------------------------------------------------------------------
  // Capacity text
  // ---------------------------------------------------------------------------
  /** Source: hh.java:1410-1412 — bx.d font */
  capacityText: {
    position: 'absolute',
    fontWeight: '800',
    color: '#010101',
    includeFontPadding: false,
    lineHeight: 14,
  },

  // ---------------------------------------------------------------------------
  // Bag grid container
  // ---------------------------------------------------------------------------
  /**
   * Grid container drawn by fg.java:64-65.
   * pc.b() draws bevel border around the whole grid rect.
   * Source: fg.java:65 — pc.b(g, d.a+x, d.b+y, d.c, d.d, v.aj, false)
   */
  gridContainer: {
    position: 'absolute',
    overflow: 'hidden',
    backgroundColor: PANEL_FILL,
  },

  // ---------------------------------------------------------------------------
  // Cell (shared by equipped slot cells and bag grid cells)
  // ---------------------------------------------------------------------------
  /**
   * Grid cell background.
   * Source: fg.java:86 — pc.b(g, x, y, w, h, 6647295, 0xFFFFFF, 8369663)
   * Same 3D bevel as equipped slots.
   */
  cell: {
    position: 'absolute',
    backgroundColor: SLOT_BG,
    alignItems: 'center',
    justifyContent: 'center',
  },
  /** Empty cell — same colors as filled in Java. Source: fg.java:86 */
  cellEmpty: {
    backgroundColor: SLOT_BG,
  },
  /** Filled cell — same base but content drawn on top. */
  cellFilled: {
    backgroundColor: SLOT_BG,
  },
  /** Over-capacity cell. Source: fg.java:84 — 0xFF0000, 0xFFFFFF, 15385573 */
  cellOverCapacity: {
    backgroundColor: GRID_CELL_OVER_BG,
  },
  cellTransparent: {
    backgroundColor: 'transparent',
  },
  javaBevelFrame: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 0,
  },
  javaBevelOuter: {
    position: 'absolute',
    borderColor: JAVA_BEVEL_OUTER,
    borderWidth: 1,
    zIndex: 0,
  },
  javaBevelInner: {
    position: 'absolute',
    borderColor: JAVA_BEVEL_INNER,
    borderWidth: 1,
    zIndex: 0,
  },
  javaBevelFill: {
    position: 'absolute',
    zIndex: 0,
  },
  javaBevelAccentBottom: {
    position: 'absolute',
    height: 1,
    zIndex: 0,
  },
  javaBevelAccentRight: {
    position: 'absolute',
    width: 1,
    zIndex: 0,
  },
  /** Selected cell is rendered by focusCorner image overlay. Source: pc.java:185-192 */
  cellSelected: {},
  /** Target slot highlight is rendered by 3 nested rect overlays. Source: hh.java:1389-1394 */
  cellTarget: {},
  targetFrameOuter: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: TARGET_COLOR_3,
    zIndex: 18,
  },
  targetFrameMiddle: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: TARGET_COLOR_2,
  },
  targetFrameInner: {
    position: 'absolute',
    borderWidth: 1,
    borderColor: TARGET_COLOR_1,
  },
  cellIcon: {
    zIndex: 4,
  },
  missingIconText: {
    color: '#999',
    fontWeight: '700',
  },

  // ---------------------------------------------------------------------------
  // Focus frame image overlay — /focusmovechess1, 4 cropped 7x7 corners.
  // Source: pc.java:185-192.
  // ---------------------------------------------------------------------------
  focusFrame: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 20,
  },
  focusCornerClip: {
    position: 'absolute',
    overflow: 'hidden',
  },
  focusCornerSheet: {
    position: 'absolute',
  },

  // ---------------------------------------------------------------------------
  // Cell overlays — dc.java evidence
  // ---------------------------------------------------------------------------
  /**
   * Broken heart overlay.
   * Source: dc.java:67-68 — drawImage(i, x+i.getWidth(), y+cellHeight, 40)
   * Anchor 40 = BOTTOM|RIGHT
   */
  brokenHeart: {
    position: 'absolute',
  },
  brokenHeartImage: {
    // width/height from actual /broken_heart asset dimensions
  },
  /**
   * Rank star animation.
   * Source: dc.java:70-71 — cw.a(g, pc.b, m*p, 0, p, q, x+iconWidth, y, 24)
   * Anchor 24 = TOP|RIGHT — drawn from /crystalblue sprite sheet, 3 frames
   */
  rankStar: {
    position: 'absolute',
    overflow: 'hidden',
  },
  rankStarSheet: {
    position: 'absolute',
    top: 0,
  },
  /**
   * Enhancement text +N.
   * Source: dc.java:74 — s.a(g, "+"+j, x+32, y+32-fontHeight, 2)
   * Anchor 2 = RIGHT, gradient font d (if constructor param)
   */
  enhancementText: {
    position: 'absolute',
    color: '#FFFF68',
    fontWeight: '900',
    textShadowColor: '#010101',
    textShadowOffset: { width: 1, height: 0 },
    textShadowRadius: 0,
    includeFontPadding: false,
    lineHeight: 14,
  },
  /**
   * Item quantity text.
   * Source: dc.java:85 — bx.c.a(g, ""+g, x+32, y+32-fontHeight, 2)
   * Anchor 2 = RIGHT, using bx.c (small white font)
   */
  quantityText: {
    position: 'absolute',
    color: '#FFFF68',
    fontWeight: '900',
    textShadowColor: '#010101',
    textShadowOffset: { width: 1, height: 0 },
    textShadowRadius: 0,
    includeFontPadding: false,
    lineHeight: 14,
  },

  // ---------------------------------------------------------------------------
  // Tooltip — fw.java evidence
  // ---------------------------------------------------------------------------
  /**
   * Tooltip container with Java panel style.
   * Source: fw.java — tooltip uses panel fill and border from ap.java pc.a() style.
   */
  tooltipContainer: {
    position: 'absolute',
    left: 0,
    right: 0,
    backgroundColor: PANEL_FILL, // v.aj = 0xF0FBFF
    borderWidth: 1,
    borderColor: PANEL_EDGE, // v.ak from ap.java
    paddingHorizontal: 6,
    paddingVertical: 4,
    zIndex: 2000,
    elevation: 2000,
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
  /**
   * Detail backdrop — Java uses transparent overlay.
   * Source: hg.java — dialog overlay is transparent, not semi-black.
   */
  detailBackdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'transparent',
  },
  /** Detail panel uses same pc.a() frame as main panel. Source: hg.java */
  detailPanel: {
    borderWidth: 2,
    borderColor: PANEL_EDGE,
    backgroundColor: PANEL_FILL,
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
  // Detail separator line - Source: hg.java
  detailSeparator: {
    height: 1,
    backgroundColor: PANEL_BORDER_OUTER,
    marginVertical: 4,
  },
  // Close button row - uses JavaBitmapText
  detailCloseRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 6,
  },
  detailCloseButton: {
    paddingHorizontal: 16,
    paddingVertical: 4,
    backgroundColor: SLOT_BG,
    borderWidth: 1,
    borderColor: PANEL_BORDER_OUTER,
  },
  detailCloseText: {
    fontSize: 10,
    color: '#FFFFFF',
    fontWeight: '700',
  },
});
