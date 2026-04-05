import { StyleSheet, Dimensions, Platform } from 'react-native';

// Chúng ta sẽ dùng các hàm tính toán linh hoạt thay vì hằng số tĩnh
export const getScreenSize = (width: number, height: number) => Math.min(width, height);

export const getStyles = (width: number, height: number) => {
  const SCREEN_SIZE = getScreenSize(width, height);
  
  return StyleSheet.create({
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
    inputBox: {
      position: 'absolute',
      width: '36.5%',
      height: '2.4%', // Sweet Spot v2.3
      justifyContent: 'center',
    },
    transparentInput: {
      width: '100%',
      height: '100%',
      color: '#2a1a05',
      fontSize: SCREEN_SIZE * 0.035,
      fontWeight: 'bold',
      paddingHorizontal: 8,
      paddingTop: 0,
      borderWidth: 0,
      outlineStyle: 'none',
      caretColor: 'transparent',
      fontFamily: Platform.OS === 'ios' ? 'Hoefler Text' : 'serif',
    } as any,
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
      fontSize: SCREEN_SIZE * 0.035,
      fontWeight: 'bold',
      marginTop: Platform.OS === 'web' ? -2 : 0,
    },
    phantomButton: {
      position: 'absolute',
      top: '78.3%',
      left: '36.5%',
      width: '27.5%',
      height: '6.5%',
      backgroundColor: 'transparent',
      justifyContent: 'center',
      alignItems: 'center',
    },
  });
};
