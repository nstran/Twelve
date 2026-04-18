import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  container: {
    position: 'relative',
    overflow: 'visible',
    width: '100%',
    height: '100%',
  },
  patternContainer: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 1,
    overflow: 'hidden',
    alignItems: 'flex-end',
  },
  patternBackground: {
    width: '50%',
    height: '100%',
    opacity: 1,
  },
  frameFill: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 2,
  },
  content: {
    flex: 1,
    position: 'relative',
    zIndex: 5,
    overflow: 'hidden',
  },
  frameLines: {
    ...StyleSheet.absoluteFillObject,
    zIndex: 10,
  },
  cornerSlot: {
    position: 'absolute',
    zIndex: 11,
    overflow: 'visible',
  },
  cornerTopLeft: {
    top: 0,
    left: 0,
  },
  cornerTopRight: {
    top: 0,
    right: 0,
  },
  cornerBottomLeft: {
    bottom: 0,
    left: 0,
  },
  cornerBottomRight: {
    bottom: 0,
    right: 0,
  },
  cornerImage: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
  cornerMirrorX: {
    transform: [{ scaleX: -1 }],
  },
  cornerMirrorY: {
    transform: [{ scaleY: -1 }],
  },
  cornerMirrorXY: {
    transform: [{ scaleX: -1 }, { scaleY: -1 }],
  },
  lineHorizontal: {
    position: 'absolute',
    height: 1,
    zIndex: 2,
  },
  lineVertical: {
    position: 'absolute',
    width: 1,
    zIndex: 2,
  },
  lineOuterTop: {
    top: 0,
    left: 1,
    right: 1,
  },
  lineOuterBottom: {
    bottom: 0,
    left: 1,
    right: 1,
  },
  lineOuterLeft: {
    left: 0,
    top: 1,
    bottom: 1,
  },
  lineOuterRight: {
    right: 0,
    top: 1,
    bottom: 1,
  },
  topFill: {
    position: 'absolute',
    top: 1,
    left: 1,
    right: 1,
    height: 3,
    zIndex: 1,
  },
  leftFill: {
    position: 'absolute',
    top: 1,
    bottom: 1,
    left: 1,
    width: 2,
    zIndex: 1,
  },
  rightFill: {
    position: 'absolute',
    top: 1,
    bottom: 1,
    right: 1,
    width: 2,
    zIndex: 1,
  },
  lineInnerTop: {
    top: 1,
    left: 1,
    right: 1,
  },
  lineInnerBottom: {
    bottom: 1,
    left: 1,
    right: 1,
  },
  lineInnerLeft: {
    left: 1,
    top: 1,
    bottom: 1,
  },
  lineInnerRight: {
    right: 1.5,
    top: 1,
    bottom: 1,
  },
  lineAccentTop: {
    top: 3.5,
    left: 3,
    right: 3,
  },
  lineAccentTopBlue: {
    top: 2.5,
    left: 1,
    right: 1,
    height: 1.5,
  },
  lineAccentBottom: {
    bottom: 3.5,
    left: 3,
    right: 3,
  },
  lineAccentBottomBlue: {
    bottom: 1.5,
    left: 1,
    right: 1,
    height: 1.5,
  },
  lineAccentLeft: {
    left: 2,
    top: 2,
    bottom: 2,
  },
  lineAccentRight: {
    right: 2,
    top: 2,
    bottom: 2,
  },
});
