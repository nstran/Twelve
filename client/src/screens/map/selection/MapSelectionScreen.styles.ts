import { StyleSheet, Dimensions } from 'react-native';

const { width: SCREEN_WIDTH, height: SCREEN_HEIGHT } = Dimensions.get('window');

// World-map Java asset `/m/m` is square 480x480. Keep the rendered map square;
// non-uniform X/Y scaling makes Java `oh.java` label/lock/hitbox coordinates drift.
export const MAP_WIDTH = 1200;
export const MAP_HEIGHT = 1200;

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#3b2f21', // Dùng màu nâu đất của map thay vì đen nếu có lỡ lộ
  },
  mapViewport: {
    flex: 1,
    overflow: 'hidden',
  },
  panningContainer: {
    position: 'absolute',
    left: 0,
    top: 0,
    width: MAP_WIDTH,
    height: MAP_HEIGHT,
    minWidth: SCREEN_WIDTH,
    minHeight: SCREEN_HEIGHT,
  },
  background: {
    width: MAP_WIDTH,
    height: MAP_HEIGHT,
    resizeMode: 'stretch',
  },
  
  // ── Map Point Styles ──────────────────────────────────────────
  mapPoint: {
    position: 'absolute',
    zIndex: 10,
  },
  iconContainer: {
    width: 44,
    height: 44,
    justifyContent: 'center',
    alignItems: 'center',
  },
  mapIcon: {
    position: 'absolute',
    resizeMode: 'stretch',
  },
  mapLabel: {
    position: 'absolute',
    width: 80,
    color: '#FFF',
    fontSize: 12,
    fontWeight: 'bold',
    textAlign: 'center',
    textShadowColor: 'rgba(0, 0, 0, 1)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
    // Bỏ background xám
    paddingHorizontal: 4,
  },
  mapLabelFocused: {
    color: '#FFD700', // Giữ màu vàng khi chọn
    fontSize: 13,
  },

  // ── Indicators ────────────────────────────────────────────────
  focusIndicator: {
    position: 'absolute',
    top: -15,
    width: 30,
    height: 30,
    zIndex: 20,
  },
  cursorIndicator: {
    position: 'absolute',
    width: 30,
    height: 30,
    resizeMode: 'contain',
    zIndex: 30,
  },
});
