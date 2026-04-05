import { StyleSheet, Platform } from 'react-native';

export const getScreenSize = (width: number, height: number) => Math.min(width, height);

// ── be.a = 28 px trong thiết kế mới (Skia) ───────────────────────────────────
const BOTTOM_BAR_H = 26; // Match softkey bar height

export const getStyles = (width: number, height: number) => {
  const SCREEN_SIZE = getScreenSize(width, height);
  const FONT_SIZE   = SCREEN_SIZE * 0.035;

  return StyleSheet.create({

    // ── Container ─────────────────────────────────────────────────────────
    container: {
      flex: 1,
      backgroundColor: '#000',
      justifyContent: 'center',
      alignItems: 'center',
    },
    fullBg: {
      ...StyleSheet.absoluteFillObject,
      backgroundColor: '#000',
    },
    imageBg: {
      width: SCREEN_SIZE,
      height: SCREEN_SIZE,
      justifyContent: 'center',
    },
    contentOverlay: {
      width: '100%',
      height: '100%',
    },

    // ── Inputs (phantom overlay trên login.png) ──────────────
    inputBoxNick: {
      position: 'absolute',
      width: '36.5%',
      height: '2.4%',
      justifyContent: 'center',
      top: '60.3%',
      left: '39.8%',
    },
    inputBoxPass: {
      position: 'absolute',
      width: '36.5%',
      height: '2.4%',
      justifyContent: 'center',
      top: '66.3%',
      left: '39.8%',
    },

    transparentInput: {
      width: '100%',
      height: '100%',
      color: '#2a1a05',
      fontSize: FONT_SIZE,
      fontWeight: 'bold',
      paddingHorizontal: 8,
      paddingTop: 0,
      borderWidth: 0,
      outlineStyle: 'none',
      caretColor: 'transparent',
      fontFamily: Platform.OS === 'ios' ? 'Hoefler Text' : 'serif',
    } as any,

    // ── Checkboxes ────────────────────────────────────────────────────────
    checkboxArea1: {
      position: 'absolute',
      width: '3.5%',
      height: '3.5%',
      backgroundColor: 'transparent',
      justifyContent: 'center',
      alignItems: 'center',
      top: '70.8%',
      left: '39.5%',
    },
    checkboxArea2: {
      position: 'absolute',
      width: '3.5%',
      height: '3.5%',
      backgroundColor: 'transparent',
      justifyContent: 'center',
      alignItems: 'center',
      top: '76.1%',
      left: '39.5%',
    },
    tickText: {
      color: '#2a1a05',
      fontSize: FONT_SIZE,
      fontWeight: 'bold',
      marginTop: Platform.OS === 'web' ? -2 : 0,
    },

    // ── Loading ───────────────────────────────────────────────────────────
    loadingOverlay: {
      ...StyleSheet.absoluteFillObject,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: 'rgba(0,0,0,0.25)',
      zIndex: 5,
    },

    // Popup Menu J2ME
    menuBackdrop: {
      ...StyleSheet.absoluteFillObject,
      zIndex: 10,
    },
    menuBox: {
      position: 'absolute',
      bottom: BOTTOM_BAR_H,
      left: 0,
      minWidth: '30%',
      maxWidth: '50%',
      backgroundColor: '#eee8c8',
      borderWidth: 1,
      borderColor: '#555555',
      overflow: 'hidden',
      zIndex: 11,
      elevation: 8,
      shadowColor: '#000',
      shadowOffset: { width: 1, height: 2 },
      shadowOpacity: 0.5,
      shadowRadius: 2,
    },
    menuItem: {
      height: 20,
      paddingHorizontal: 14,
      justifyContent: 'center',
    },
    menuItemSelected: {
      backgroundColor: '#2255bb',
    },
    menuItemText: {
      color: '#000000',
      fontSize: 11,
      fontFamily: Platform.OS === 'android' ? 'sans-serif' : 'System',
    },
    menuItemTextSelected: {
      color: '#ffffff',
      fontWeight: 'bold',
    },

    arrowText: {
      color: '#ffffff',
      fontSize: 10,
      lineHeight: 12,
    },
  });
};
