import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  container: {
    paddingVertical: 12,
    paddingHorizontal: 25,
    minWidth: 280,
    maxWidth: 400,
    alignItems: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#000',
    textAlign: 'center',
    letterSpacing: 0.5,
    marginBottom: 8,
  },
  message: {
    fontSize: 16,
    color: '#000',
    textAlign: 'center',
    lineHeight: 22,
    width: '100%',
    marginBottom: 15,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 30,
    width: '100%',
  },
  btnWrapper: {
    width: 90,
    height: 32,
    justifyContent: 'center',
    alignItems: 'center',
  },
  btnBackground: {
    flexDirection: 'row',
    ...StyleSheet.absoluteFillObject,
  },
  btnPart: {
    width: 45,
    height: 32,
  },
  btnPartRotated: {
    transform: [{ scaleX: -1 }],
  },
  btnTextOverlay: {
    ...StyleSheet.absoluteFillObject,
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 10,
    paddingBottom: 2,
  },
  btnText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
    textShadowColor: 'rgba(0, 0, 0, 0.75)',
    textShadowOffset: { width: 0, height: 1 },
    textShadowRadius: 1,
  },
});
