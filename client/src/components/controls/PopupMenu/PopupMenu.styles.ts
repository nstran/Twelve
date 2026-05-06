import { StyleSheet } from 'react-native';
import { GameTextStyles } from '../../../theme/GameTheme';

/**
 * Java popup menu frame colors from ap.java evidence:
 * - v.aj: fill color (0xF0FBFF)
 * - v.al: outer border color
 * - v.ak: inner border color
 * - Corner pieces from /_corner.png
 */
export const JAVA_MENU_FILL = '#F0FBFF';
export const JAVA_MENU_OUTER_BORDER = '#A8D8FF'; // v.al approximation
export const JAVA_MENU_INNER_BORDER = '#DEFFFF';  // v.ak approximation

export const styles = StyleSheet.create({
  menuBackdrop: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1000,
    elevation: 1000,
  },

  // Khung viền ngoài
  menuBox: {
    position: 'absolute',
    left: 4,
    minWidth: 120,
    backgroundColor: '#ffffff',
    borderWidth: 1,
    borderColor: '#2255bb',
    padding: 2, // Khe hở giữa 2 border
    zIndex: 1001,
    elevation: 1001,
  },

  // Khung viền trong
  menuInnerBox: {
    borderWidth: 1,
    borderColor: '#2255bb',
    borderRadius: 5,
    paddingVertical: 4,
    overflow: 'hidden',
  },

  menuItem: {
    height: 30,
    justifyContent: 'center',
    position: 'relative',
    marginVertical: 1,
  },
  
  menuItemSelectedBg: {
    ...StyleSheet.absoluteFillObject,
    marginHorizontal: 3, 
    borderRadius: 4,
    backgroundColor: '#4488ff', 
    overflow: 'hidden',
  },

  menuSelectedBaseImage: {
    ...StyleSheet.absoluteFillObject,
    width: '120%', 
    left: -8,      
    height: '100%',
  },

  menuOrnateClip: {
    position: 'absolute',
    width: 12, 
    height: '100%',
    top: 0,
    overflow: 'hidden',
    zIndex: 3, 
  },
  menuOrnateImage: {
    width: 32, 
    height: '100%',
  },

  menuItemContent: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingRight: 10,
  },

  menuItemText: {
    ...GameTextStyles.menuText,
    textAlign: 'center',
    paddingLeft: 0,
    zIndex: 4, 
    flex: 1,
  },
  menuItemTextSelected: {
    ...GameTextStyles.menuTextSelected,
  },
  menuArrowText: {
    ...GameTextStyles.menuText,
    color: '#666666',
    fontWeight: 'bold',
    textAlign: 'right',
  },
  menuArrowTextSelected: {
    ...GameTextStyles.menuTextSelected,
    color: '#ffffff',
    fontWeight: 'bold',
    textAlign: 'right',
  },

  /** Java bs popup renderer parity. Source: bs.java:96-119 width=max(item.p(),50)+42, item height j=20, text x+14. */
  javaMenuBox: {
    position: 'absolute',
    left: 4,
    minWidth: 92,
    backgroundColor: '#F0FBFF',
    borderWidth: 1,
    borderColor: '#0056E6',
    padding: 4,
    zIndex: 1001,
    elevation: 1001,
  },
  javaMenuInnerBox: {
    borderWidth: 1,
    borderColor: '#DEFFFF',
    paddingTop: 6,
    paddingBottom: 4,
    paddingHorizontal: 0,
    overflow: 'hidden',
  },
  javaMenuItem: {
    height: 20,
    justifyContent: 'center',
    position: 'relative',
  },
  javaMenuItemSelectedBg: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: '#0385FF',
  },
  javaMenuItemContent: {
    position: 'relative',
    height: 20,
  },
  javaMenuItemText: {
    ...GameTextStyles.menuText,
    color: '#010101',
    fontWeight: '800',
    textAlign: 'left',
    zIndex: 4,
    flex: 1,
  },
});
