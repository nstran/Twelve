/**
 * JavaFontMetrics - Maps Java bitmap font sizes to React Native system fonts
 * 
 * Evidence: bx.java, ca.java (bitmap font class)
 * 
 * Java uses bitmap fonts loaded from /_fontcap via ca class:
 * - bx.c: Small font (used for UI labels, item names, stats)
 * - bx.d: Large font (used for titles, names, important values)
 * - bx.a, bx.b, bx.e: Other font variants
 * 
 * Font methods:
 * - d.a() = font height in pixels (bitmap)
 * - d.a(String) = string width in pixels
 * - d.a(char) = character width
 * - d.a(Graphics, String, x, y, anchor) = draw text
 * - d.b() = secondary height method
 * 
 * In J2ME, bitmap fonts typically have specific pixel heights:
 * - Small font (bx.c): ~9-12px height
 * - Large font (bx.d): ~12-16px height
 * 
 * RN system fonts are in pt/dp, need scaling factor based on screen density.
 * Typical mapping:
 * - Java 9px → RN 9-10pt (small labels)
 * - Java 12px → RN 12-13pt (normal text)
 * - Java 16px → RN 16-18pt (titles/headers)
 * - Java 20px → RN 20-22pt (large titles)
 */

import { PixelRatio, Platform } from 'react-native';

/**
 * Java bitmap font height constants (from bx.java evidence)
 * These are approximate pixel heights for the bitmap fonts
 */
export const JAVA_FONT_SMALL_HEIGHT = 9;   // bx.c height (small labels)
export const JAVA_FONT_MEDIUM_HEIGHT = 12; // bx.d height (normal text)
export const JAVA_FONT_LARGE_HEIGHT = 16;  // Large title font
export const JAVA_FONT_XLARGE_HEIGHT = 20; // Extra large

/**
 * Scale factor to convert Java pixel sizes to RN pt sizes
 * Java canvas is typically 176-240px wide, fonts are sized in pixels
 * RN uses pt which is ~1.5x pixel density on modern screens
 */
const JAVA_TO_RN_SCALE = 1.0; // Keep same size, RN handles density

/**
 * Maps Java bitmap font height (px) to RN font size (pt)
 * @param javaFontHeight - Java bitmap font pixel height
 * @returns RN font size in points
 */
export function mapJavaFontToRN(javaFontHeight: number): number {
  // Direct mapping with small adjustment for readability
  return Math.round(javaFontHeight * JAVA_TO_RN_SCALE);
}

/**
 * Gets font size for PvP arena row labels
 * Based on ew.java usage - primary label uses bx.d, secondary uses bx.c
 */
export function getArenaPrimaryFontSize(): number {
  // Java: "Cấp X  --  Danh vọng Y" uses bx.d for width calc
  // bx.d.a() returns ~12px height in Java
  return mapJavaFontToRN(JAVA_FONT_MEDIUM_HEIGHT);
}

export function getArenaSecondaryFontSize(): number {
  // Secondary meta text uses bx.c (smaller)
  return mapJavaFontToRN(JAVA_FONT_SMALL_HEIGHT);
}

/**
 * Gets font size for PvP dialog titles
 * Based on os.java header usage
 */
export function getDialogTitleFontSize(): number {
  return mapJavaFontToRN(JAVA_FONT_LARGE_HEIGHT);
}

/**
 * Gets font size for Battle Intro card names
 * Based on ha.java name display
 */
export function getBattleIntroNameFontSize(): number {
  return mapJavaFontToRN(JAVA_FONT_LARGE_HEIGHT);
}

export function getBattleIntroLevelFontSize(): number {
  return mapJavaFontToRN(JAVA_FONT_MEDIUM_HEIGHT);
}

/**
 * Gets font size for incoming challenge popup
 * Based on bs.java text usage
 */
export function getPopupFontSize(): number {
  return mapJavaFontToRN(JAVA_FONT_SMALL_HEIGHT);
}

export function getPopupTitleFontSize(): number {
  return mapJavaFontToRN(JAVA_FONT_MEDIUM_HEIGHT);
}

/**
 * Font weight mapping from Java to RN
 * Java bitmap fonts are essentially "regular" weight
 * Bold text uses different draw method or shadow
 */
export type FontWeight = '400' | '500' | '600' | '700' | '800' | '900';

export function getJavaFontWeight(isBold: boolean = false): FontWeight {
  return isBold ? '700' : '500';
}

/**
 * Text alignment mapping
 * Java anchor values:
 * - 0 = LEFT
 * - 1 = HCENTER
 * - 2 = RIGHT
 * - 3 = BASELINE (with vertical offset)
 */
export type TextAlignment = 'left' | 'center' | 'right';

export function mapJavaAnchor(anchor: number): TextAlignment {
  switch (anchor) {
    case 0: return 'left';
    case 1: return 'center';
    case 2: return 'right';
    default: return 'left';
  }
}

/**
 * Line height multiplier for Java text rendering
 * Java uses d2.a() height + spacing for line height
 * RN lineHeight should be ~1.2-1.4x fontSize for readability
 */
export function getLineHeightMultiplier(): number {
  return 1.3;
}

/**
 * Consistent font family for PvP UI
 * Using system font with fallbacks to match Java bitmap feel
 */
export const PVP_FONT_FAMILY = Platform.select({
  ios: 'System',
  android: 'Roboto',
  default: 'System',
});

/**
 * Font style presets for PvP components
 */
export const PvpFontStyles = {
  // Arena row - primary name
  arenaPrimary: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getArenaPrimaryFontSize(),
    fontWeight: '700' as FontWeight,
    lineHeight: getArenaPrimaryFontSize() * getLineHeightMultiplier(),
  },
  // Arena row - secondary meta
  arenaSecondary: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getArenaSecondaryFontSize(),
    fontWeight: '500' as FontWeight,
    lineHeight: getArenaSecondaryFontSize() * getLineHeightMultiplier(),
  },
  // Dialog title
  dialogTitle: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getDialogTitleFontSize(),
    fontWeight: '800' as FontWeight,
  },
  // Dialog body text
  dialogBody: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getArenaSecondaryFontSize(),
    fontWeight: '500' as FontWeight,
  },
  // Button text
  buttonText: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getArenaSecondaryFontSize(),
    fontWeight: '700' as FontWeight,
  },
  // Battle intro name
  battleName: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getBattleIntroNameFontSize(),
    fontWeight: '800' as FontWeight,
  },
  // Battle intro level
  battleLevel: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getBattleIntroLevelFontSize(),
    fontWeight: '600' as FontWeight,
  },
  // Input field text
  inputText: {
    fontFamily: PVP_FONT_FAMILY,
    fontSize: getArenaSecondaryFontSize(),
    fontWeight: '500' as FontWeight,
  },
};
