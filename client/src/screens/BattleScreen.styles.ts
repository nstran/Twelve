import { StyleSheet, Dimensions } from "react-native";

export const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get("window");

// ── Tỷ lệ chuẩn dựa trên ảnh gốc 240x320 ──────────────────────────────────────
export const BG_NATIVE_W = 240;
export const BG_NATIVE_H = 320;
export const BG_W = SCREEN_W - 20; // Padding 10px mỗi bên
export const BG_H = (BG_W / BG_NATIVE_W) * BG_NATIVE_H;
export const BOARD_SCALE = BG_W / BG_NATIVE_W;

// ── Tọa độ bàn cờ ─────────────────────────────────────────────────────────────
export const BOARD_COLS = 8;
export const BOARD_ROWS = 8;
// Tỉ lệ vàng: ảnh 240 có board 232px, lề 4px. Vậy mỗi ô = 29px chuẩn.
export const GEM_SIZE = 29 * BOARD_SCALE;
export const BOARD_LEFT_OFFSET = 4.5 * BOARD_SCALE;
export const BOARD_TOP_OFFSET  = 4.5 * BOARD_SCALE;

export const BOARD_POS_LEFT = (SCREEN_W - BG_W) / 2 + BOARD_LEFT_OFFSET;
export const BOARD_POS_TOP  = 10 + BOARD_TOP_OFFSET;

// ── Styles cho các quân cờ ───────────────────────────────────────────────────
export const gc = StyleSheet.create({
  wrap: {
    position: "absolute",
    alignItems: "center",
    justifyContent: "center",
  },
  sel: {
    zIndex: 10,
    borderWidth: 2,
    borderColor: "#00FFFF",
    borderRadius: 1,
  },
});

// ── Styles chính cho màn hình Battle ─────────────────────────────────────────
export const s = StyleSheet.create({
  root: { flex: 1, backgroundColor: "#060612" },

  statusWrap: {
    position: "absolute",
    left: 6,
    right: 6,
    zIndex: 20,
    gap: 1,
  },
  barRow: { flexDirection: "row", alignItems: "center", height: 15, gap: 4 },
  barTag: {
    color: "#FFD700",
    fontSize: 9,
    fontWeight: "bold",
    width: 30,
    textShadowColor: "#000",
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },
  barTrack: {
    flex: 1,
    height: 9,
    backgroundColor: "rgba(0,0,0,0.75)",
    borderRadius: 1,
    borderWidth: 1,
    borderColor: "#333",
    overflow: "hidden",
  },
  barFill: { height: "100%", borderRadius: 0 },
  barGloss: {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    height: "40%",
    backgroundColor: "rgba(255,255,255,0.15)",
  },
  barNum: {
    color: "#fff",
    fontSize: 10,
    fontWeight: "bold",
    width: 55,
    textAlign: "right",
    fontFamily: "Roboto",
    textShadowColor: "#000",
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },

  gemBoard: { position: "absolute", zIndex: 10 },

  logBar: {
    position: "absolute",
    alignSelf: "center",
    height: 32,
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    gap: 6,
    zIndex: 10,
    backgroundColor: "rgba(0,0,0,0.3)",
    borderRadius: 4,
    paddingHorizontal: 10,
  },
  logTxt: {
    color: "#FFD700",
    fontSize: 10,
    fontWeight: "bold",
    flex: 1,
    textAlign: "center",
    textShadowColor: "#000",
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
    lineHeight: 12,
  },
  comboTxt: {
    color: "#ff4b4b",
    fontSize: 14,
    fontWeight: "bold",
    textShadowColor: "#000",
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },

  ground: {
    position: "absolute",
    left: 0,
    right: 0,
    height: 20,
    width: "100%",
    zIndex: 7,
  },

  charsRow: {
    position: "absolute",
    left: 20,
    right: 20,
    height: 80,
    flexDirection: "row",
    alignItems: "flex-end",
    zIndex: 8,
  },
  playerSprite: { width: 64, height: 70 },

  btnRow: {
    position: "absolute",
    left: 0,
    right: 0,
    height: 44,
    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
    paddingHorizontal: 12,
    zIndex: 15,
  },
  btnFlee: {
    paddingHorizontal: 18,
    paddingVertical: 8,
    backgroundColor: "rgba(100,20,20,0.92)",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: "#b00",
  },
  btnSkill: {
    paddingHorizontal: 18,
    paddingVertical: 8,
    backgroundColor: "rgba(20,40,120,0.92)",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: "#44f",
  },
  btnOff: { opacity: 0.45 },
  btnTxt: { color: "#fff", fontSize: 12, fontWeight: "bold" },

  overlay: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: "rgba(0,0,0,0.78)",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 100,
  },
  overlayTitle: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#FFD700",
    textShadowColor: "#000",
    textShadowOffset: { width: 2, height: 2 },
    textShadowRadius: 6,
    marginBottom: 10,
  },
  overlaySub: { fontSize: 14, color: "#ccc", marginBottom: 28 },
  overlayBtn: {
    paddingHorizontal: 36,
    paddingVertical: 14,
    backgroundColor: "#FFD700",
    borderRadius: 10,
  },
  overlayBtnTxt: { color: "#000", fontSize: 17, fontWeight: "bold" },
});
