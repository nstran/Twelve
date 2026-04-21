import { StyleSheet, Dimensions } from 'react-native';

export const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

// ── Background image (bkboardv.png = 240 × 320 native) ───────────────────────
export const BG_NATIVE_W = 240;
export const BG_NATIVE_H = 320;
export const BG_W        = SCREEN_W;
export const BG_H        = BG_W * BG_NATIVE_H / BG_NATIVE_W;
export const BOARD_SCALE = BG_W / BG_NATIVE_W;

// ── Bàn cờ (grid 8 × 8, vùng grid native ≈ 232 × 232 px trong ảnh) ──────────
export const BOARD_COLS = 8;
export const BOARD_ROWS = 8;
const BOARD_NATIVE_LEFT = 8;
const BOARD_NATIVE_TOP = 4;

// Java client dùng board 224x224 với cell 28x28, rồi scale toàn bộ scene.
// Không floor/round từng cell; nếu làm vậy thì mỗi ô nhỏ đi một chút và cả
// board sẽ drift dần về trên-trái so với background gốc.
const GRID_NATIVE    = 224;
const GRID_PX        = BOARD_SCALE * GRID_NATIVE;
export const GEM_SIZE          = GRID_PX / BOARD_COLS;
export const BOARD_LEFT_OFFSET = BOARD_NATIVE_LEFT * BOARD_SCALE;
export const BOARD_TOP_OFFSET  = BOARD_NATIVE_TOP * BOARD_SCALE;

export const BOARD_POS_LEFT = (SCREEN_W - BG_W) / 2 + BOARD_LEFT_OFFSET;
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
    position: 'absolute', height: 20, zIndex: 7,
  },

  charsRow: {
    position: 'absolute', height: 80,
    flexDirection: 'row', alignItems: 'flex-end', zIndex: 8,
  },
  playerSprite: { width: 64, height: 70 },
  fleeIconBtn: {
    position: 'absolute',
    left: 4,
    bottom: 5,
    width: 26,
    height: 24,
    zIndex: 20,
    alignItems: 'center',
    justifyContent: 'center',
  },
  fleeIconBtnDisabled: {
    opacity: 0.45,
  },
  fleeIconImg: {
    width: 26,
    height: 24,
  },
  fleeTextBtn: {
    color: '#ffffff',
    fontSize: 10,
    fontWeight: 'bold',
    textTransform: 'uppercase',
    textShadowColor: 'rgba(0,0,0,0.75)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },

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

  resultBannerLayer: {
    ...StyleSheet.absoluteFillObject,
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
