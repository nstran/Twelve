/**
 * PvP UI Responsive Scaling Constants
 * 
 * Based on Java client evidence from `os.java`, `ew.java`, `ha.java`, `bs.java`.
 * Java client was designed for J2ME devices with small fixed resolutions (e.g., 176x220).
 * 
 * This module provides scaling utilities to maintain Java pixel-perfect proportions
 * while adapting to modern iPhone and Android screen sizes.
 */

import { Dimensions } from 'react-native';

/**
 * Java J2ME base dimensions (reference from `v.java` and typical J2ME screens)
 * These are the "logical canvas" dimensions that Java UI was designed for.
 */
export const JAVA_BASE_WIDTH = 176;
export const JAVA_BASE_HEIGHT = 220;

/**
 * Java-derived PvP UI constants (from evidence)
 */
export const JAVA_PVP_CONSTANTS = {
  // From `ew.java` - lobby row dimensions
  ROW_HEIGHT_NORMAL: 32,
  ROW_HEIGHT_SELECTED: 42,
  ROW_PADDING_VERTICAL_NORMAL: 3,
  ROW_PADDING_VERTICAL_SELECTED: 7,
  ROW_TEXT_LEFT_MARGIN: 25,
  ROW_TEXT_LINE_SPACING: 13,
  
  // From `bs.java` - popup menu dimensions
  POPUP_ROW_HEIGHT: 20,
  POPUP_PADDING: 1,
  POPUP_MIN_WIDTH: 50,
  
  // From `ha.java` - battle intro versus card
  VERSUS_CARD_WIDTH: 200,
  VERSUS_CARD_HEIGHT: 68,
  VERSUS_AVATAR_WIDTH: 58,
  VERSUS_AVATAR_HEIGHT: 60,
  VERSUS_PADDING: 4,
  VERSUS_ICON_SIZE: 20,
  
  // From `os.java` - lobby preview card
  PREVIEW_CARD_MIN_HEIGHT: 74,
  PREVIEW_AVATAR_WIDTH: 62,
  PREVIEW_AVATAR_HEIGHT: 70,
} as const;

/**
 * Calculate responsive scale factor for PvP UI
 * 
 * Strategy:
 * 1. Calculate scale based on screen width relative to Java base width
 * 2. Clamp scale to prevent too small (< 1.5x) or too large (> 4x) scaling
 * 3. Consider screen height to avoid vertical overflow
 * 
 * @returns Scale factor to apply to Java base dimensions
 */
export function calculatePvpScaleFactor(): number {
  const { width: screenWidth, height: screenHeight } = Dimensions.get('window');
  
  // Base scale from width
  const widthScale = screenWidth / JAVA_BASE_WIDTH;
  
  // Base scale from height
  const heightScale = screenHeight / JAVA_BASE_HEIGHT;
  
  // Use the smaller scale to ensure content fits
  const baseScale = Math.min(widthScale, heightScale);
  
  // Clamp scale factor
  // Min 1.5x: Prevent UI from being too small on small screens
  // Max 4.0x: Prevent UI from being too large on tablets
  const clampedScale = Math.max(1.5, Math.min(4.0, baseScale));
  
  return clampedScale;
}

/**
 * Scale a Java dimension to current screen size
 * 
 * @param javaDimension - Original Java pixel dimension
 * @param scaleFactor - Scale factor (from calculatePvpScaleFactor)
 * @returns Scaled dimension for current screen
 */
export function scalePvpDimension(javaDimension: number, scaleFactor: number): number {
  return Math.round(javaDimension * scaleFactor);
}

/**
 * Get scaled PvP UI constants for current screen
 * 
 * @returns Object with all PvP constants scaled to current screen
 */
export function getScaledPvpConstants() {
  const scale = calculatePvpScaleFactor();
  
  return {
    scale,
    rowHeightNormal: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_HEIGHT_NORMAL, scale),
    rowHeightSelected: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_HEIGHT_SELECTED, scale),
    rowPaddingVerticalNormal: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_PADDING_VERTICAL_NORMAL, scale),
    rowPaddingVerticalSelected: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_PADDING_VERTICAL_SELECTED, scale),
    rowTextLeftMargin: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_TEXT_LEFT_MARGIN, scale),
    rowTextLineSpacing: scalePvpDimension(JAVA_PVP_CONSTANTS.ROW_TEXT_LINE_SPACING, scale),
    
    popupRowHeight: scalePvpDimension(JAVA_PVP_CONSTANTS.POPUP_ROW_HEIGHT, scale),
    popupPadding: scalePvpDimension(JAVA_PVP_CONSTANTS.POPUP_PADDING, scale),
    popupMinWidth: scalePvpDimension(JAVA_PVP_CONSTANTS.POPUP_MIN_WIDTH, scale),
    
    versusCardWidth: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_CARD_WIDTH, scale),
    versusCardHeight: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_CARD_HEIGHT, scale),
    versusAvatarWidth: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_AVATAR_WIDTH, scale),
    versusAvatarHeight: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_AVATAR_HEIGHT, scale),
    versusPadding: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_PADDING, scale),
    versusIconSize: scalePvpDimension(JAVA_PVP_CONSTANTS.VERSUS_ICON_SIZE, scale),
    
    previewCardMinHeight: scalePvpDimension(JAVA_PVP_CONSTANTS.PREVIEW_CARD_MIN_HEIGHT, scale),
    previewAvatarWidth: scalePvpDimension(JAVA_PVP_CONSTANTS.PREVIEW_AVATAR_WIDTH, scale),
    previewAvatarHeight: scalePvpDimension(JAVA_PVP_CONSTANTS.PREVIEW_AVATAR_HEIGHT, scale),
  };
}

/**
 * Common screen sizes for testing (reference)
 */
export const COMMON_SCREEN_SIZES = {
  // iPhone
  iPhoneSE: { width: 375, height: 667, scale: 2 },
  iPhone12: { width: 390, height: 844, scale: 3 },
  iPhone14ProMax: { width: 430, height: 932, scale: 3 },
  
  // Android
  androidSmall: { width: 360, height: 640, scale: 2 },
  androidMedium: { width: 412, height: 915, scale: 2.625 },
  androidLarge: { width: 480, height: 1024, scale: 3 },
} as const;
