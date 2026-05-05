/**
 * Inventory layout constants extracted from original decompiled source.
 *
 * Source: reference/redecoded/decompiled/hh.java (screen layout)
 * Source: reference/redecoded/decompiled/fg.java (grid cell sizing)
 * Source: reference/redecoded/decompiled/dc.java (cell renderer)
 *
 * Portrait mode: logical width 240, height 320 - softkeyHeight.
 * Landscape mode: logical width 320, height deviceHeight - softkeyHeight.
 *
 * Grid from fg.java:
 *   cellWidth = 32, cellHeight = 32, spacing = 2, paddingY = 6, paddingX = 6
 *   columns = floor(gridRectWidth / (cellWidth + spacing))
 */

import { Dimensions } from 'react-native';

// ---------------------------------------------------------------------------
// Rect helper
// ---------------------------------------------------------------------------

export interface LayoutRect {
  x: number;
  y: number;
  w: number;
  h: number;
}

// ---------------------------------------------------------------------------
// Original logical coordinates — hh.java constructor
// ---------------------------------------------------------------------------

/** Portrait layout (B() == false, logical width 240). Source: hh.java */
const PORTRAIT = {
  logicalWidth: 240,
  /** Default logical height = 320 - softkeyHeight. We use 288 as a safe default (softkey ~32). */
  logicalHeight: 288,
  avatar: { x: 95, y: 21, w: 54, h: 60 } as LayoutRect,
  /** 6 equipped slots, indexed 0–5. Source: hh.java slot rects. */
  slots: [
    { x: 60, y: 18, w: 32, h: 32 },
    { x: 60, y: 53, w: 32, h: 32 },
    { x: 153, y: 18, w: 32, h: 32 },
    { x: 153, y: 53, w: 32, h: 32 },
    { x: 189, y: 18, w: 32, h: 32 },
    { x: 189, y: 53, w: 32, h: 32 },
  ] as LayoutRect[],
  /** Bag grid container rect. Height is dynamic: panelHeight - 96. */
  bag: { x: 9, y: 88, w: 220, h: 192 } as LayoutRect,
  /** Player name position. Source: hh.java cu(22,6). */
  namePos: { x: 22, y: 6 },
  /** Element icon position. Source: hh.java cu(6,4). */
  elementPos: { x: 6, y: 4 },
  /** Capacity text position: x = bag.x, y = bag.y - 16. */
  capacityPos: { x: 9, y: 72 },
};

/** Landscape / wide layout (B() == true, logical width 320). Source: hh.java */
const LANDSCAPE = {
  logicalWidth: 320,
  logicalHeight: 288,
  avatar: { x: 13, y: 24, w: 54, h: 60 } as LayoutRect,
  slots: [
    { x: 6, y: 90, w: 32, h: 32 },
    { x: 6, y: 126, w: 32, h: 32 },
    { x: 42, y: 90, w: 32, h: 32 },
    { x: 42, y: 126, w: 32, h: 32 },
    { x: 6, y: 162, w: 32, h: 32 },
    { x: 42, y: 162, w: 32, h: 32 },
  ] as LayoutRect[],
  bag: { x: 79, y: 23, w: 226, h: 255 } as LayoutRect,
  namePos: { x: 22, y: 6 },
  elementPos: { x: 6, y: 4 },
  capacityPos: { x: 79, y: 7 },
};

export const INVENTORY_LAYOUT = {
  portrait: PORTRAIT,
  landscape: LANDSCAPE,
} as const;

// ---------------------------------------------------------------------------
// Grid constants — fg.java fields
// ---------------------------------------------------------------------------

/** Cell width in logical px. Source: fg.java field o = 32. */
export const GRID_CELL_W = 32;
/** Cell height in logical px. Source: fg.java field p = 32. */
export const GRID_CELL_H = 32;
/** Spacing between cells in logical px. Source: fg.java field n = 2. */
export const GRID_SPACING = 2;
/** Vertical padding inside grid container. Source: fg.java field l = 6. */
export const GRID_PADDING_Y = 6;

// ---------------------------------------------------------------------------
// Cell renderer constants — dc.java
// ---------------------------------------------------------------------------

/** Ranks that show animated star overlay. Source: dc.java rank check. */
export const STAR_RANKS = [4, 7, 8] as const;

/** Number of star animation frames. Source: dc.java pc.b sprite sheet. */
export const STAR_FRAME_COUNT = 3;

// ---------------------------------------------------------------------------
// Highlight colors — hh.java draw method
// ---------------------------------------------------------------------------

/** 3-rect target slot highlight colors. Source: hh.java draw method. */
export const TARGET_HIGHLIGHT_COLORS = ['#FEFF77', '#FFF930', '#FFFDD3'] as const;

// ---------------------------------------------------------------------------
// Slot names — original Vietnamese labels
// ---------------------------------------------------------------------------

export const EQUIPMENT_SLOT_NAMES: Record<number, string> = {
  0: 'Áo',
  1: 'Vũ khí',
  2: 'Nón',
  3: 'Giày',
  4: 'Ngựa/Khiên',
  5: 'Nhẫn',
  7: 'Bùa',
  8: 'Bùa',
};

// ---------------------------------------------------------------------------
// Rank color mapping — ll.a(rank) from original
// ---------------------------------------------------------------------------

/**
 * Rank-to-color mapping for equipment name display.
 * Source: ll.a(rank) in decompiled source.
 * Ranks: 0=white, 1=green, 2=blue, 3=purple, 4=orange, 5=yellow,
 *        6=cyan, 7=red, 8=gold
 */
export const RANK_COLORS: Record<number, string> = {
  0: '#FFFFFF',
  1: '#00FF00',
  2: '#3399FF',
  3: '#CC66FF',
  4: '#FF9900',
  5: '#FFFF00',
  6: '#00FFFF',
  7: '#FF3333',
  8: '#FFD700',
};

// ---------------------------------------------------------------------------
// Scale helper
// ---------------------------------------------------------------------------

export type InventoryOrientation = 'portrait' | 'landscape';

export interface ScaledLayout {
  orientation: InventoryOrientation;
  scale: number;
  canvasWidth: number;
  canvasHeight: number;
  layout: typeof PORTRAIT;
}

/**
 * Compute the scale factor and effective canvas size for the inventory screen.
 *
 * Uses portrait layout for now (phase one).
 * Landscape constants are ready but not auto-selected yet.
 */
export function computeInventoryScale(
  availableWidth: number,
  availableHeight: number,
  orientation: InventoryOrientation = 'portrait',
): ScaledLayout {
  const layout = orientation === 'landscape' ? LANDSCAPE : PORTRAIT;
  const scale = Math.min(
    availableWidth / layout.logicalWidth,
    availableHeight / layout.logicalHeight,
  );
  return {
    orientation,
    scale,
    canvasWidth: layout.logicalWidth * scale,
    canvasHeight: layout.logicalHeight * scale,
    layout,
  };
}

/**
 * Convert a logical rect to scaled absolute position/size.
 */
export function scaleRect(rect: LayoutRect, scale: number) {
  return {
    left: rect.x * scale,
    top: rect.y * scale,
    width: rect.w * scale,
    height: rect.h * scale,
  };
}

/**
 * Compute grid columns from bag rect width.
 * Source: fg.java d() method: s = gridWidth / (cellWidth + spacing)
 */
export function computeGridColumns(bagWidth: number): number {
  return Math.floor(bagWidth / (GRID_CELL_W + GRID_SPACING));
}

/**
 * Compute grid rows needed for a given item count and column count.
 * Source: fg.java d() method: r = count / s + (count % s > 0 ? 1 : 0) + extra
 */
export function computeGridRows(itemCount: number, columns: number, extraRows: number = 0): number {
  if (columns <= 0) {
    return 0;
  }
  return Math.ceil(itemCount / columns) + extraRows;
}
