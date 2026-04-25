import { Platform } from 'react-native';

/**
 * Central font contract for the Java-like UI.
 *
 * The original client assets include bitmap font atlases:
 * - assets/login/04_font_candidate/_blackfont.png
 * - assets/login/04_font_candidate/_fontcap.png
 * - assets/ui/04_tabs_and_numbers/tinynumber.png
 *
 * React Native cannot use those PNG atlases through `fontFamily` directly.
 * Until a dedicated JavaBitmapText atlas renderer is implemented, these
 * families keep the current UI consistent with the old Java look by using
 * heavier serif/monospace fallbacks instead of modern app fonts.
 */
const webUiFont = 'Arial, Tahoma, Verdana, sans-serif';
const webNumberFont = 'Arial, Tahoma, Verdana, sans-serif';

export const GameFonts = {
  ui: Platform.OS === 'web' ? webUiFont : Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  uiBold: Platform.OS === 'web' ? webUiFont : Platform.OS === 'ios' ? 'System' : 'sans-serif-condensed',
  dialog: Platform.OS === 'web' ? webUiFont : Platform.OS === 'ios' ? 'System' : 'sans-serif-medium',
  number: Platform.OS === 'web' ? webNumberFont : Platform.OS === 'ios' ? 'Menlo' : 'monospace',
  softkey: Platform.OS === 'web' ? webUiFont : Platform.OS === 'ios' ? 'System' : 'sans-serif-condensed',
} as const;
