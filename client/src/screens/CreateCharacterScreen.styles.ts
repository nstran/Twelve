import { StyleSheet, Dimensions, Platform } from 'react-native';

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

  // ── Selection Panel (Hệ, Tóc, Da...) ───────────────────────────────
  selectionPanel: {
    marginHorizontal: 10,
    marginBottom: 60, // Chừa chỗ cho Softbar
    backgroundColor: '#FEF8E6', // Creamy beige from Image 1
    borderRadius: 12,
    borderWidth: 2,
    borderColor: '#8B4513', // SaddleBrown
    padding: 12,
    elevation: 5,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
  },
  selectionRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: 'rgba(139, 69, 19, 0.2)', // Light brown divider
  },
  label: {
    color: '#5D4037', // Dark brown for text
    fontSize: 14,
    fontWeight: '900', // Sắc nét (Sharp)
    textTransform: 'uppercase',
  },
  selector: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  valueText: {
    color: '#212121',
    fontSize: 15,
    fontWeight: 'bold',
    minWidth: 90,
    textAlign: 'center',
    textTransform: 'uppercase',
  },
  arrow: {
    width: 28,
    height: 28,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#8B4513',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#5D4037',
  },
  arrowText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: 'bold',
  },

  // ── Bottom Softkeys (Sẽ dùng chung component) ─────────────────────
  softKeyBarContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
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
