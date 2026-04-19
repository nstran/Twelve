import { StyleSheet, Dimensions } from 'react-native';

const { width } = Dimensions.get('window');

const BOX_BG       = '#e8f4ff';
const BOX_BORDER   = '#2255bb';
const SUB_BOX_BG   = '#f0f4ff';
const PRIMARY_BG   = '#fff3cc';
const PRIMARY_BORDER = '#cc9900';
const PRIMARY_TEXT = '#cc6600';
const VALUE_BLUE   = '#1e40af'; // Blue for values in boxes

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
    justifyContent: 'center',
    alignItems: 'center',
  },
  bgWrapper: {
    position: 'absolute', top: 0, left: 0, right: 0, bottom: 0,
  },
  background: {
    width: '100%', height: '100%', resizeMode: 'cover',
  },

  // ── Khung ngoài ─────────────────────────────────────────────────────
  outerBox: {
    width: width * 0.94,
    alignSelf: 'center',
    marginTop: 20,
    height: 'auto', // Override CornerFrame height: 100%
  },
  innerBox: {
    paddingHorizontal: 14,
    paddingVertical: 10,
    gap: 6,
    height: 'auto', // Ensure inner box wraps content
  },

  // ── Header ──────────────────────────────────────────────────────────
  header: {
    gap: 6,
    alignItems: 'stretch',
  },

  headerContent: {
    flexDirection: 'row',
    gap: 12,
    alignItems: 'flex-start',
  },

  avatarBox: {
    width: 80,
    height: 100,
    borderWidth: 1,
    borderColor: BOX_BORDER,
    backgroundColor: '#fff',
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'center', // Center for better scaling/positioning
  },

  infoPanel: {
    flex: 1,
    gap: 3,
  },

  nameRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    marginBottom: 4,
  },
  username: {
    flex: 1,
    fontSize: 14,
    fontWeight: 'bold',
    color: '#333',
  },
  levelText: {
    fontSize: 11,
    fontWeight: 'bold',
    color: '#666',
  },

  infoItem: {
    flexDirection: 'row',
    alignItems: 'center',
    height: 20, 
    gap: 6,
  },
  infoLabel: {
    width: 65,
    fontSize: 11,
    color: '#333',
    textAlign: 'left',
  },
  infoBox: {
    flex: 1,
    maxWidth: 160,
    height: '100%',
    backgroundColor: SUB_BOX_BG,
    borderWidth: 1,
    borderColor: '#99bbee',
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 2,
    borderRadius: 3,
  },
  infoValue: {
    fontSize: 11,
    fontWeight: '700',
    color: VALUE_BLUE,
  },
  infoValueRed: {
    color: '#ef4444',
  },

  // ── Divider ─────────────────────────────────────────────────────────
  dividerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    marginVertical: 4,
    height: 10,
  },
  dividerLine: {
    flex: 1,
    height: 1,
    backgroundColor: '#99bbee',
  },
  dividerSymbol: {
    fontSize: 10,
    color: '#99bbee',
    marginHorizontal: 4,
    fontWeight: 'bold',
  },

  // ── Progress bars ────────────────────────────────────────────────────
  barsSection: {
    gap: 4,
    marginVertical: 4,
  },
  barRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  barWrapper: {
    flex: 1,
    height: 20,
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#666',
    borderRadius: 3,
    overflow: 'hidden',
    position: 'relative',
    justifyContent: 'center',
  },
  barFill: {
    position: 'absolute',
    left: 0, top: 0,
    height: '100%',
    borderRightWidth: 1,
    borderRightColor: 'rgba(0,0,0,0.1)',
  },
  barText: {
    fontSize: 10,
    fontWeight: 'bold',
    color: '#333',
    textAlign: 'center',
    zIndex: 2,
  },

  // ── 4 chỉ số cơ bản ─────────────────────────────────────────────────
  attrSection: {
    gap: 4,
  },
  attrItem: {
    flexDirection: 'row',
    alignItems: 'center',
    height: 20, 
    gap: 10,
  },
  attrLabel: {
    width: 85,
    fontSize: 12,
    fontWeight: 'bold',
    color: '#333',
  },
  attrLabelPrimary: {
    fontWeight: 'bold',
  },
  attrBox: {
    flex: 1,
    maxWidth: 200,
    height: '100%',
    backgroundColor: SUB_BOX_BG,
    borderWidth: 1,
    borderColor: '#99bbee',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 3,
  },
  attrBoxPrimary: {
    backgroundColor: PRIMARY_BG,
    borderColor: PRIMARY_BORDER,
  },
  attrValue: {
    fontSize: 12,
    fontWeight: 'bold',
    color: VALUE_BLUE,
  },
  attrValuePrimary: {
    color: PRIMARY_TEXT,
  },

  // ── Điểm phân bổ ────────────────────────────────────────────────────
  pointsRow: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    gap: 8,
    marginVertical: 4,
  },
  pointsLabel: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#000',
  },
  pointsBox: {
    width: 80,
    height: 20, 
    backgroundColor: '#dcc69d',
    borderWidth: 1,
    borderColor: '#8B4513',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 3,
  },
  pointsValue: {
    fontSize: 12,
    fontWeight: 'bold',
    color: '#5c3600',
  },

  // ── Combat stats ────────────────────────────────────────────────────
  combatGrid: {
    flexDirection: 'row',
    gap: 6,
  },
  combatCol: {
    flex: 1,
    gap: 2,
  },
  combatItem: {
    flexDirection: 'row',
    alignItems: 'center',
    height: 20, 
    gap: 3,
  },
  combatLabel: {
    fontSize: 11,
    color: '#333',
    flex: 1,
  },
  combatBox: {
    flex: 1,
    height: '100%',
    backgroundColor: SUB_BOX_BG,
    borderWidth: 1,
    borderColor: '#99bbee',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 3,
  },
  combatValue: {
    fontSize: 11,
    fontWeight: 'bold',
    color: VALUE_BLUE,
  },

  // ── Softkey bar ──────────────────────────────────────────────────────
  softKeyBar: {
    position: 'absolute',
    bottom: 0, left: 0, right: 0,
    height: 40,
  },
});
