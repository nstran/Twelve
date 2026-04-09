import { StyleSheet, Dimensions } from 'react-native';

const { width: SCREEN_WIDTH, height: SCREEN_HEIGHT } = Dimensions.get('window');

// Tăng kích thước để cho phép cuộn thoải mái
export const MAP_WIDTH = 1300; 
export const MAP_HEIGHT = 1200;

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#3b2f21', // Dùng màu nâu đất của map thay vì đen nếu có lỡ lộ
  },
  panningContainer: {
    minWidth: SCREEN_WIDTH,
    minHeight: SCREEN_HEIGHT,
  },
  background: {
    width: MAP_WIDTH,
    height: MAP_HEIGHT,
    resizeMode: 'cover',
  },
  
  // ── Map Point Styles ──────────────────────────────────────────
  mapPoint: {
    position: 'absolute',
    alignItems: 'center',
    justifyContent: 'center',
    width: 80, // Hitbox size
    zIndex: 10,
  },
  iconContainer: {
    width: 44,
    height: 44,
    justifyContent: 'center',
    alignItems: 'center',
  },
  mapIcon: {
    width: 36,
    height: 36,
    resizeMode: 'contain',
  },
  mapLabel: {
    marginTop: 2,
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
});
