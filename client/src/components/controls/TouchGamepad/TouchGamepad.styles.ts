import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  shell: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 34,
    zIndex: 140,
    alignItems: 'center',
    paddingHorizontal: 14,
  },
  dock: {
    alignItems: 'center',
  },
  cluster: {
    borderRadius: 22,
    borderWidth: 1,
    borderColor: 'rgba(238, 214, 143, 0.5)',
    backgroundColor: 'rgba(11, 21, 33, 0.62)',
    paddingHorizontal: 8,
    paddingVertical: 8,
    shadowColor: '#000000',
    shadowOpacity: 0.34,
    shadowRadius: 10,
    shadowOffset: { width: 0, height: 5 },
    elevation: 10,
  },
  dpadCluster: {
    width: 168,
  },
  dpadTopRow: {
    alignItems: 'center',
    marginBottom: 4,
  },
  dpadMiddleRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 4,
  },
  dpadBottomRow: {
    alignItems: 'center',
  },
  button: {
    borderRadius: 15,
    borderWidth: 1,
    borderColor: 'rgba(255, 233, 173, 0.48)',
    backgroundColor: 'rgba(34, 52, 78, 0.92)',
    alignItems: 'center',
    justifyContent: 'center',
    overflow: 'hidden',
  },
  buttonPressed: {
    backgroundColor: 'rgba(63, 99, 148, 0.98)',
    borderColor: 'rgba(255, 241, 197, 0.85)',
  },
  buttonDisabled: {
    opacity: 0.3,
  },
  dpadButton: {
    width: 44,
    height: 44,
  },
  centerButton: {
    width: 52,
    height: 52,
    borderRadius: 26,
    backgroundColor: 'rgba(50, 76, 114, 0.98)',
    borderColor: 'rgba(255, 241, 197, 0.7)',
  },
  arrowIcon: {
    width: 20,
    height: 20,
    tintColor: '#ffffff',
  },
  arrowIconPressed: {
    tintColor: '#fff4c4',
  },
  centerDot: {
    width: 12,
    height: 12,
    borderRadius: 6,
    backgroundColor: '#ffffff',
    shadowColor: '#ffffff',
    shadowOpacity: 0.3,
    shadowRadius: 6,
    shadowOffset: { width: 0, height: 0 },
  },
  centerDotPressed: {
    backgroundColor: '#fff4c4',
  },
});
