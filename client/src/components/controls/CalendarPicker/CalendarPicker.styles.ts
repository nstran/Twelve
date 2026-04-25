import { StyleSheet } from 'react-native';
import { GameTextStyles } from '../../../theme/GameTheme';

export const styles = StyleSheet.create({
  backdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(0,0,0,0.6)',
    zIndex: 200,
    justifyContent: 'center',
    alignItems: 'center',
  },
  
  // Group Box Container (Silk/Beige background)
  groupBox: {
    width: '90%',
    backgroundColor: '#fde9f2', 
    borderWidth: 1,
    borderColor: '#b2b2b2',
    padding: 10,
    borderRadius: 4,
    // Note: groupLabel is now inside the padded box
  },
  groupLabel: {
    ...GameTextStyles.dialogText,
    color: '#000000',
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 10, // Create space from label to year picker
    marginLeft: 2,
  },

  // Year Button Style
  yearBtn: {
    backgroundColor: '#dbceb1', 
    borderWidth: 1,
    borderColor: '#8d826a',
    paddingVertical: 4,
    paddingHorizontal: 25,
    alignSelf: 'center',
    marginBottom: 10,
  },
  yearBtnText: {
    ...GameTextStyles.dialogText,
    color: '#000000',
    fontSize: 16,
    fontWeight: 'bold',
  },

  // Month UI Row
  monthRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 12,
    gap: 8,
  },
  monthBtn: {
    backgroundColor: '#7fbffb', 
    borderWidth: 1,
    borderColor: '#0055cc',
    paddingVertical: 5,
    width: 140,
    alignItems: 'center',
  },
  arrowBtn: {
    width: 28,
    height: 28,
    justifyContent: 'center',
    alignItems: 'center',
  },
  arrowIcon: {
    width: 16,
    height: 16,
    transform: [{ rotate: '90deg' }],
  },
  arrowIconLeft: {
    transform: [{ rotate: '-90deg' }],
  },

  // Grid Header
  gridContainer: {
    borderWidth: 1,
    borderColor: '#8d826a',
    backgroundColor: '#ffffff',
    overflow: 'hidden',
  },
  daysGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  
  // Cell Styles
  dayCell: {
    width: '14.28%', 
    height: 32,
    justifyContent: 'center',
    alignItems: 'center',
    borderRightWidth: 0.5,
    borderBottomWidth: 0.5,
    borderColor: '#cccccc',
  },
  dayText: {
    ...GameTextStyles.dialogText,
    fontSize: 14,
    color: '#000000',
  },
  
  // Color overlays from the image
  satColumn: {
    backgroundColor: '#9ccfff', 
  },
  sunColumn: {
    backgroundColor: '#ff9999', 
  },
  greyRow: {
    backgroundColor: '#e6e6e6', 
  },

  // Year Selection Modal Overlay
  yearModalBackdrop: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(0,0,0,0.8)',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 300,
  },
  yearList: {
    width: 200,
    maxHeight: 300,
    backgroundColor: '#ffffff',
    borderWidth: 2,
    borderColor: '#2a1a05',
    overflow: 'hidden',
  },
  yearListItem: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#eeeeee',
    alignItems: 'center',
  },

  footer: {
    flexDirection: 'row',
    marginTop: 15,
    justifyContent: 'flex-end',
    gap: 15,
  },
  footerBtn: {
    backgroundColor: '#dbceb1',
    paddingVertical: 6,
    paddingHorizontal: 22,
    borderWidth: 1,
    borderColor: '#8d826a',
  },
});
