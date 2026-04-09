import { StyleSheet, Dimensions } from 'react-native';

export const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

// ── Background image (bkboardv.png = 240 × 320 native) ───────────────────────
export const BG_NATIVE_W = 240;
export const BG_NATIVE_H = 320;
export const BG_W        = SCREEN_W - 16;                        // 8px padding mỗi bên
export const BG_H        = Math.round(BG_W * BG_NATIVE_H / BG_NATIVE_W);
export const BOARD_SCALE = BG_W / BG_NATIVE_W;

// ── Bàn cờ (grid 8 × 8, vùng grid native ≈ 232 × 232 px trong ảnh) ──────────
export const BOARD_COLS = 8;
export const BOARD_ROWS = 8;

// GEM_SIZE: chia đều vùng 232 native px, floor để board không tràn ra ngoài
const GRID_NATIVE    = 228;
const GRID_PX        = BOARD_SCALE * GRID_NATIVE;
export const GEM_SIZE          = Math.floor(GRID_PX / BOARD_COLS);
const BOARD_CENTER_OFFSET = Math.round((BG_W - GEM_SIZE * BOARD_COLS) / 2);

// Fine-tune lưới gem bên trong khung bkboardv theo trục ngang.
// Âm = dịch sang trái, dương = dịch sang phải.
const BOARD_LEFT_SHIFT_NATIVE = -4;
// Căn giữa board trong vùng grid của ảnh nền rồi fine-tune ngang.
export const BOARD_LEFT_OFFSET = BOARD_CENTER_OFFSET
  + Math.round(BOARD_LEFT_SHIFT_NATIVE * BOARD_SCALE);
const BOARD_TOP_SHIFT_NATIVE = 11;
export const BOARD_TOP_OFFSET  = BOARD_CENTER_OFFSET + Math.round(BOARD_TOP_SHIFT_NATIVE * BOARD_SCALE);

export const BOARD_POS_LEFT = Math.round((SCREEN_W - BG_W) / 2) + BOARD_LEFT_OFFSET;
export const BOARD_POS_TOP  = BOARD_TOP_OFFSET;   // background bắt đầu y=0, board ngay trong đó

// ── Styles ────────────────────────────────────────────────────────────────────
export const s = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#060612' },

  gemBoard: { position: 'absolute', zIndex: 10 },

  logBar: {
    position: 'absolute', alignSelf: 'center',
    flexDirection: 'row', alignItems: 'center', justifyContent: 'center',
    gap: 6, zIndex: 10,
    backgroundColor: 'rgba(0,0,0,0.38)', borderRadius: 4,
    paddingHorizontal: 10, height: 28,
  },
  logTxt: {
    color: '#FFD700', fontSize: 10, fontWeight: 'bold',
    flex: 1, textAlign: 'center', lineHeight: 13,
    textShadowColor: '#000', textShadowOffset: { width: 1, height: 1 }, textShadowRadius: 2,
  },
  comboTxt: {
    color: '#ff4b4b', fontSize: 14, fontWeight: 'bold',
    textShadowColor: '#000', textShadowOffset: { width: 1, height: 1 }, textShadowRadius: 2,
  },

  ground: {
    position: 'absolute', left: 0, right: 0, height: 20, width: '100%', zIndex: 7,
  },

  charsRow: {
    position: 'absolute', left: 20, right: 20, height: 80,
    flexDirection: 'row', alignItems: 'flex-end', zIndex: 8,
  },
  playerSprite: { width: 64, height: 70 },

  btnRow: {
    position: 'absolute', left: 0, right: 0, height: 44,
    flexDirection: 'row', justifyContent: 'space-around',
    alignItems: 'center', paddingHorizontal: 12, zIndex: 15,
  },
  btnFlee: {
    paddingHorizontal: 16, paddingVertical: 8,
    backgroundColor: 'rgba(100,20,20,0.92)', borderRadius: 8,
    borderWidth: 1, borderColor: '#b00',
  },
  btnSkill: {
    paddingHorizontal: 16, paddingVertical: 8,
    backgroundColor: 'rgba(20,40,120,0.92)', borderRadius: 8,
    borderWidth: 1, borderColor: '#44f',
  },
  btnOff: { opacity: 0.45 },
  btnTxt: { color: '#fff', fontSize: 12, fontWeight: 'bold' },

  // AI panel
  aiRow: {
    position: 'absolute', left: 0, right: 0, height: 36,
    flexDirection: 'row', alignItems: 'center', justifyContent: 'center',
    gap: 4, zIndex: 15, paddingHorizontal: 8,
  },
  aiManualBtn: {
    paddingHorizontal: 10, paddingVertical: 4,
    backgroundColor: 'rgba(40,40,60,0.9)', borderRadius: 6,
    borderWidth: 1, borderColor: '#555',
  },
  aiManualBtnActive: {
    backgroundColor: 'rgba(60,60,120,0.95)', borderColor: '#88f',
  },
  aiChip: {
    width: 32, height: 32, borderRadius: 16,
    alignItems: 'center', justifyContent: 'center',
    backgroundColor: 'rgba(30,30,50,0.9)',
    borderWidth: 1, borderColor: '#444',
  },
  aiChipActive: {
    backgroundColor: 'rgba(255,180,0,0.25)',
    borderColor: '#FFD700', borderWidth: 2,
  },
  aiChipTxt:  { fontSize: 16 },
  aiSmallTxt: { color: '#aaa', fontSize: 9 },
  aiLabelRow: {
    position: 'absolute', left: 0, right: 0, height: 18,
    alignItems: 'center', justifyContent: 'center', zIndex: 14,
  },
  aiLabelTxt: {
    color: '#FFD700', fontSize: 9, fontWeight: 'bold',
    textShadowColor: '#000', textShadowOffset: { width: 1, height: 1 }, textShadowRadius: 2,
  },

  overlay: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: 'rgba(2,4,14,0.6)',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 100,
  },
  resultBannerStage: {
    position: 'absolute',
    left: 0,
    right: 0,
    alignItems: 'center',
    zIndex: 101,
  },
});
