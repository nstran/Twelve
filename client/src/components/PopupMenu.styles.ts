import { StyleSheet, Platform } from 'react-native';

export const styles = StyleSheet.create({
  menuBackdrop: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1000,
    elevation: 1000,
  },

  // Khung viền ngoài
  menuBox: {
    position: 'absolute',
    bottom: 26, // BOTTOM_BAR_H
    left: 4,
    minWidth: 120, // Enough for "Đăng xuất"
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

  menuItemText: {
    color: '#000000',
    fontSize: 14,
    fontWeight: 'bold',
    textAlign: 'left',
    paddingLeft: 15,
    fontFamily: Platform.OS === 'android' ? 'sans-serif-medium' : 'System',
    zIndex: 4, 
  },
  menuItemTextSelected: {
    color: '#ffffff',
    textShadowColor: 'rgba(0,0,0,0.5)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
});
