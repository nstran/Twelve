import { StyleSheet, Dimensions } from 'react-native';

const { width, height } = Dimensions.get('window');

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  background: {
    position: 'absolute',
    width: width,
    height: height,
    resizeMode: 'cover',
  },
  
  // ── Character Preview Area ─────────────────────────────────────────
  previewContainer: {
    flex: 2,
    justifyContent: 'center',
    alignItems: 'center',
    // backgroundColor: 'rgba(255,0,0,0.1)', // Debug
  },
  stonePlatform: {
    width: 200,
    height: 100,
    resizeMode: 'contain',
    position: 'absolute',
    bottom: -40, // Hạ thấp xuống sát mép dưới
  },
  characterStack: {
    width: 250,
    height: 250,
    position: 'absolute',
    bottom: '8%', // Căn để chân đứng trên khối đá dạt xuống mép
    alignItems: 'center',
    justifyContent: 'center',
  },
  bodyLayer: {
    width: 200,
    height: 200,
    resizeMode: 'contain',
    zIndex: 1,
  },
  faceLayer: {
    width: 100,
    height: 60,
    resizeMode: 'contain',
    position: 'absolute',
    top: 65,  // Cân chỉnh để khớp mặt
    zIndex: 2,
  },
  hairLayer: {
    width: 180,
    height: 180,
    resizeMode: 'contain',
    position: 'absolute',
    top: -5,  // Cân chỉnh để tóc trùm lên đầu
    zIndex: 3,
  },
  frontArmLayer: {
    width: 27,    // Kích thước nắm đấm v4 thực tế
    height: 30,
    resizeMode: 'contain',
    position: 'absolute',
    right: 151,    // Tọa độ đã căn chỉnh chuẩn
    bottom: 77,
    zIndex: 5,
  },
  swordLayer: {
    width: 150,
    height: 150,
    resizeMode: 'contain',
    position: 'absolute',
    right: 42,      // Dời vào gần tay hơn sau khi xoay 90 độ
    bottom: -6,    // Hạ thấp chút để cán kiếm khớp tay nằm ngang
    zIndex: 4,
    transform: [{ rotate: '-10deg' }], // Xoay thêm 90 độ so với góc cũ (15 -> 105)
  },

  // ── Info Overlay (Title) ───────────────────────────────────────────
  headerContainer: {
    position: 'absolute',
    top: 40,
    width: '100%',
    alignItems: 'center',
  },
  headerFrame: {
    paddingHorizontal: 24,
    paddingVertical: 8,
    backgroundColor: 'rgba(0,0,0,0.7)',
    borderColor: '#FFD700',
    borderWidth: 1,
    borderRadius: 4,
  },
  headerText: {
    color: '#FFD700',
    fontSize: 18,
    fontWeight: 'bold',
    letterSpacing: 2,
    textShadowColor: 'rgba(0, 0, 0, 0.75)',
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 10,
  },

  // ── Selection Panel (Hệ, Tóc, Da...) ───────────────────────────────
  selectionPanel: {
    flex: 1,
    backgroundColor: 'rgba(255, 248, 220, 0.9)', // Màu Beige đặc trưng J2ME
    borderTopLeftRadius: 16,
    borderTopRightRadius: 16,
    borderWidth: 2,
    borderColor: '#8B4513',
    padding: 16,
  },
  selectionRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 8,
    borderBottomWidth: 1,
    borderBottomColor: 'rgba(139, 69, 19, 0.2)',
  },
  label: {
    color: '#5D4037',
    fontSize: 14,
    fontWeight: 'bold',
  },
  selector: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  valueText: {
    color: '#212121',
    fontSize: 16,
    fontWeight: 'bold',
    minWidth: 80,
    textAlign: 'center',
  },
  arrow: {
    width: 32,
    height: 32,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#8B4513',
    borderRadius: 4,
  },
  arrowText: {
    color: '#FFF',
    fontSize: 18,
    fontWeight: 'bold',
  },

  // ── Bottom Softkeys ───────────────────────────────────────────────
  softKeyBar: {
    height: 50,
    flexDirection: 'row',
    backgroundColor: '#3E2723',
    borderTopWidth: 2,
    borderTopColor: '#5D4037',
  },
  softKey: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  softKeyText: {
    color: '#FFF8DC',
    fontSize: 14,
    fontWeight: 'bold',
    letterSpacing: 1,
  },
  softKeyDivider: {
    width: 2,
    backgroundColor: '#5D4037',
    marginVertical: 10,
  },

  // ── FEMALE STYLE (DÀNH RIÊNG CHO NỮ - VIẾT THÊM Ở CUỐI) ────────────
  frontArmNu: {
    width: 18,
    height: 28,
    resizeMode: 'contain',
    position: 'absolute',
    right: 145,    // Tọa độ Nữ (Chuẩn)
    bottom: 90,   // Tọa độ Nữ (Chuẩn)
    zIndex: 5,
  },
  swordNu: {
    width: 130,
    height: 130,
    resizeMode: 'contain',
    position: 'absolute',
    right: 48,      // Tọa độ Nữ (Chuẩn)
    bottom: 22,     // Tọa độ Nữ (Chuẩn)
    zIndex: 4,
    transform: [{ rotate: '-10deg' }], // Tọa độ Nữ (Chuẩn)
  },
});
