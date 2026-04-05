import { StyleSheet, Platform } from 'react-native';

export const getStyles = (width: number, height: number) => {
  const FONT_SMALL = 13;
  const FONT_MED   = 15;

  return StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#ffffff',
    },
    
    header: {
      alignItems: 'center',
      paddingVertical: 12,
      borderBottomWidth: 1,
      borderBottomColor: '#dddddd',
    },
    headerText: {
      color: '#000000',
      fontSize: 18,
      fontWeight: 'bold',
      textDecorationLine: 'underline',
      fontFamily: Platform.OS === 'ios' ? 'Hoefler Text' : 'serif',
    },

    scroll: {
      flex: 1,
    },
    scrollContent: {
      paddingHorizontal: 15,
      paddingBottom: 40,
    },

    // ── Banner thông báo (thay Alert.alert) ───────────────────────────────
    bannerError: {
      backgroundColor: '#cc0000',
      borderRadius: 6,
      paddingVertical: 10,
      paddingHorizontal: 14,
      marginTop: 10,
      marginBottom: 4,
    },
    bannerSuccess: {
      backgroundColor: '#1a7a1a',
      borderRadius: 6,
      paddingVertical: 10,
      paddingHorizontal: 14,
      marginTop: 10,
      marginBottom: 4,
    },
    bannerText: {
      color: '#ffffff',
      fontSize: 14,
      fontWeight: 'bold',
      textAlign: 'center',
      fontFamily: Platform.OS === 'ios' ? 'System' : 'sans-serif',
    },

    label: {
      color: '#000000',
      fontSize: FONT_MED,
      fontWeight: 'bold',
      marginTop: 12,
      marginBottom: 4,
      fontFamily: Platform.OS === 'ios' ? 'Hoefler Text' : 'serif',
    },

    // Inputs
    inputBox: {
      height: 32,
      borderWidth: 1,
      borderColor: '#999999',
      borderRadius: 4,
      paddingHorizontal: 10,
      justifyContent: 'center',
      position: 'relative', 
      overflow: 'hidden',
    },
    inputInactive: {
      backgroundColor: '#ede6d3',
    },
    inputActive: {
      backgroundColor: '#7fbffb', 
      borderWidth: 0, 
      borderColor: 'transparent',
      borderRadius: 4,
    },
    
    textInput: {
      color: '#000000',
      fontSize: FONT_SMALL,
      fontWeight: 'bold',
      padding: 0,
      margin: 0,
      width: '100%',
      height: '100%',
      zIndex: 5,
      // Triệt tiêu viền đen (outline) trên Web
      ...Platform.select({
        web: {
          outlineStyle: 'none',
        }
      } as any),
    },

    dobRow: {
      alignItems: 'center',
    },
    dobText: {
      textAlign: 'center',
    },

    genderRow: {
      flexDirection: 'row',
      marginTop: 8,
      gap: 20,
    },
    radioItem: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 8,
    },
    radioBox: {
      width: 18,
      height: 18,
      borderWidth: 1.5,
      borderColor: '#666666',
      backgroundColor: '#ede6d3',
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 2,
    },
    radioCheck: {
      color: '#2a1a05',
      fontSize: 12,
      fontWeight: 'bold',
    },
    radioLabel: {
      color: '#000000',
      fontSize: FONT_MED,
      fontWeight: 'bold',
    },

    loadingOverlay: {
      ...StyleSheet.absoluteFillObject,
      backgroundColor: 'rgba(255,255,255,0.6)',
      justifyContent: 'center',
      alignItems: 'center',
      zIndex: 10,
    },
  });
};
