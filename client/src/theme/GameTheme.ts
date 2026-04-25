import { StyleSheet } from 'react-native';
import { GameFonts } from './GameFonts';

/**
 * Global Java-like UI theme.
 *
 * File style riêng lẻ chỉ nên override khi thật sự cần khác layout/màu.
 * Font atlas PNG gốc Java hiện là asset tham chiếu; RN Text dùng fallback
 * ở đây cho tới khi có JavaBitmapText renderer crop atlas thật.
 */
export const GameColors = {
  textDark: '#111111',
  textBody: '#333333',
  textMuted: '#666666',
  textLight: '#ffffff',
  valueBlue: '#1e40af',
  valueBrown: '#5c3600',
  selectedBlue: '#4488ff',
  panelBorderBlue: '#2255bb',
  subBoxBg: '#f0f4ff',
} as const;

export const GameTextStyles = StyleSheet.create({
  uiLabel: {
    color: GameColors.textDark,
    fontFamily: GameFonts.ui,
    fontSize: 14,
    fontWeight: '700',
  },
  uiLabelStrong: {
    color: GameColors.textDark,
    fontFamily: GameFonts.ui,
    fontSize: 15,
    fontWeight: '900',
  },
  uiValue: {
    color: GameColors.textBody,
    fontFamily: GameFonts.ui,
    fontSize: 13,
    fontWeight: '700',
  },
  uiValueSelected: {
    color: GameColors.textLight,
    fontFamily: GameFonts.ui,
    fontSize: 13,
    fontWeight: '700',
    textShadowColor: 'rgba(0,0,0,0.5)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  menuText: {
    color: '#000000',
    fontFamily: GameFonts.ui,
    fontSize: 14,
    fontWeight: 'bold',
  },
  menuTextSelected: {
    color: GameColors.textLight,
    fontFamily: GameFonts.ui,
    fontSize: 14,
    fontWeight: 'bold',
    textShadowColor: 'rgba(0,0,0,0.5)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  softkeyText: {
    color: GameColors.textLight,
    fontFamily: GameFonts.softkey,
    fontSize: 13,
    fontWeight: 'bold',
    textShadowColor: 'rgba(0,0,0,0.8)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },
  numberTiny: {
    fontFamily: GameFonts.number,
    fontSize: 10,
    fontWeight: 'bold',
  },
  numberSmall: {
    fontFamily: GameFonts.number,
    fontSize: 11,
    fontWeight: 'bold',
  },
  numberValue: {
    color: GameColors.valueBlue,
    fontFamily: GameFonts.number,
    fontSize: 12,
    fontWeight: 'bold',
  },
  dialogText: {
    color: GameColors.textBody,
    fontFamily: GameFonts.dialog,
    fontSize: 14,
    fontWeight: '600',
  },
} as const);