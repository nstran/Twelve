import { StyleSheet, Platform } from 'react-native';

export const getScreenSize = (width: number, height: number) => Math.min(width, height);

// ── be.a = 17 px trong J2ME ──────────────────────────────────────────────────
const BOTTOM_BAR_H = 17;

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

    // ── Inputs (phantom overlay trên login.png) ───────────────────────────
    inputBox: {
      position: 'absolute',
      width: '36.5%',
      height: '2.4%',
      justifyContent: 'center',
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
    checkboxArea: {
      position: 'absolute',
      width: '3.5%',
      height: '3.5%',
      backgroundColor: 'transparent',
      justifyContent: 'center',
      alignItems: 'center',
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

    // ══════════════════════════════════════════════════════════════════════
    //  POPUP MENU  (bv.java)
    //  — Góc dưới trái, nhỏ gọn, màu y hệt J2ME
    //  — bg: cream nhạt  |  selected: blue J2ME  |  text: đen thuần
    // ══════════════════════════════════════════════════════════════════════
    menuBackdrop: {
      ...StyleSheet.absoluteFillObject,
      zIndex: 10,
    },

    menuBox: {
      position: 'absolute',
      bottom: BOTTOM_BAR_H,       // ngay trên thanh softkey
      left: 0,                    // sát mép trái (bv.java: x bắt đầu từ 0)
      // width tự fit content — dùng minWidth
      minWidth: '30%',
      maxWidth: '50%',
      backgroundColor: '#eee8c8', // kem nhạt J2ME (gần trắng kem)
      borderWidth: 1,
      borderColor: '#555555',     // border tối như J2ME canvas
      overflow: 'hidden',
      zIndex: 11,
      elevation: 8,
      shadowColor: '#000',
      shadowOffset: { width: 1, height: 2 },
      shadowOpacity: 0.5,
      shadowRadius: 2,
    },

    // mỗi row = j=20 trong bv.java, padding trái = 14 (n4+14)
    menuItem: {
      height: 20,
      paddingHorizontal: 14,
      justifyContent: 'center',
    },
    menuItemSelected: {
      backgroundColor: '#2255bb', // blue J2ME (ak.c().c selection color)
    },
    menuItemText: {
      color: '#000000',           // đen thuần — ca.d font trong J2ME
      fontSize: 11,
      fontFamily: Platform.OS === 'android' ? 'sans-serif' : 'System',
    },
    menuItemTextSelected: {
      color: '#ffffff',           // trắng — ca.c font trong J2ME
      fontWeight: 'bold',
    },

    // ══════════════════════════════════════════════════════════════════════
    //  BOTTOM SOFTKEY BAR  (be.java: height=17, bg #030D66 từ z.class)
    //  s[0]="Chọn"  |  s[1]="▲"  |  s[2]="Thoát"
    // ══════════════════════════════════════════════════════════════════════
    bottomBar: {
      position: 'absolute',
      bottom: 0,
      left: 0,
      right: 0,
      height: BOTTOM_BAR_H,
      backgroundColor: '#030D66', // z.class constant — navy blue J2ME chrome
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'space-between',
      paddingHorizontal: 4,
      zIndex: 20,
    },

    softkey: {
      paddingHorizontal: 6,
      height: '100%',
      justifyContent: 'center',
      alignItems: 'center',
    },
    softkeyText: {
      color: '#ffffff',           // trắng — softkey text J2ME
      fontSize: 9,
      fontFamily: Platform.OS === 'android' ? 'sans-serif' : 'System',
    },

    softkeyCenter: {
      flex: 1,
      height: '100%',
      justifyContent: 'center',
      alignItems: 'center',
    },

    // Center icon row: game skull + arrowfocus indicator
    centerIconRow: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 2,
    },
    // Game skull icon (icon.png 30×32 → scale down to fit bar h=17)
    gameIcon: {
      width: 11,
      height: 12,
    },
    // arrowfocus1/2 (10×7 → fit bar)
    arrowIcon: {
      width: 8,
      height: 6,
    },

    // kept for fallback (unused when images load)
    arrowText: {
      color: '#ffffff',
      fontSize: 10,
      lineHeight: 12,
    },
  });
};
