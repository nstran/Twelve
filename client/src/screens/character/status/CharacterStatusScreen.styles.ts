import { StyleSheet, Dimensions } from 'react-native';

const { width } = Dimensions.get('window');

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
    justifyContent: 'center', // Center the content (outerBox)
    alignItems: 'center',
  },
  bgWrapper: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  },
  background: {
    width: '100%',
    height: '100%',
    resizeMode: 'cover',
  },
  // ── Khung viền ngoài (Trắng, Viền Xanh đôi) ─────────────────────────
  outerBox: {
    width: width * 0.9,
    backgroundColor: '#ffffff',
    borderWidth: 2,
    borderColor: '#2255bb',
    padding: 4,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 5,
  },
  innerBox: {
    borderWidth: 1,
    borderColor: '#2255bb',
    borderRadius: 6,
    padding: 8,
  },
  
  // ── Header (Avatar & Top Stats) ────────────────────────────────────
  headerRow: {
    flexDirection: 'row',
    marginBottom: 8,
  },
  avatarContainer: {
    width: 80,
    height: 100,
    borderWidth: 1,
    borderColor: '#2255bb',
    backgroundColor: '#f0f8ff',
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 10,
  },
  avatarLayerBody: {
    width: 60,
    height: 60,
    resizeMode: 'contain',
  },
  avatarLayerFace: {
    width: 30,
    height: 18,
    resizeMode: 'contain',
    position: 'absolute',
    top: 19,
  },
  avatarLayerHair: {
    width: 54,
    height: 54,
    resizeMode: 'contain',
    position: 'absolute',
    top: -2,
  },
  topStats: {
    flex: 1,
    gap: 4,
  },
  nameRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 2,
  },
  usernameText: {
    fontSize: 18,
    fontWeight: '900',
    color: '#000',
  },
  levelText: {
    fontSize: 14,
    color: '#444',
  },
  statValueRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    height: 20,
  },
  statLabel: {
    fontSize: 13,
    color: '#333',
    flex: 1,
  },
  statValueBox: {
    flex: 1.5,
    height: '100%',
    backgroundColor: '#e6f0ff', // Light blue box
    borderWidth: 1,
    borderColor: '#99bbff',
    borderRadius: 2,
    justifyContent: 'center',
    alignItems: 'center',
  },
  statValueText: {
    fontSize: 12,
    fontWeight: 'bold',
    color: '#003366',
  },
  titleText: {
    color: '#dd2222', // Red for "Đại Hiệp"
  },
  kenLabel: {
    fontSize: 13,
    fontWeight: 'bold',
    color: '#000',
    marginTop: 4,
  },

  // ── Thanh ngang ngăn cách ───────────────────────────────────────────
  divider: {
    height: 1,
    backgroundColor: '#2255bb',
    marginVertical: 6,
    opacity: 0.3,
  },

  // ── Progress Bars ──────────────────────────────────────────────────
  barsContainer: {
    gap: 6,
    marginBottom: 8,
  },
  barWrapper: {
    height: 18,
    backgroundColor: '#fff8e1', // Light yellow background
    borderWidth: 1,
    borderColor: '#8B4513',
    borderRadius: 2,
    overflow: 'hidden',
    position: 'relative',
    justifyContent: 'center',
  },
  barFill: {
    height: '100%',
    position: 'absolute',
    left: 0,
    top: 0,
  },
  barText: {
    fontSize: 11,
    fontWeight: 'bold',
    color: '#000',
    textAlign: 'center',
    zIndex: 2,
    textShadowColor: 'rgba(255,255,255,0.5)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  barIcon: {
    position: 'absolute',
    left: -10,
    zIndex: 3,
  },

  // ── Attributes (Cường lực, Nội lực...) ─────────────────────────────
  attrContainer: {
    gap: 4,
    marginBottom: 8,
  },
  attrRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    height: 22,
  },
  attrLabel: {
    fontSize: 14,
    fontWeight: '700',
    color: '#333',
    flex: 1.2,
  },
  attrValueBox: {
    flex: 2,
    height: '100%',
    backgroundColor: '#d9eaff',
    borderWidth: 1,
    borderColor: '#2255bb',
    borderRadius: 4,
    justifyContent: 'center',
    alignItems: 'center',
  },
  attrValueText: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#2255bb',
  },
  hpLabel: {
    color: '#dd2222', // Red for "Thân Pháp"
  },

  // ── Points Section ────────────────────────────────────────────────
  pointsRow: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    marginVertical: 4,
  },
  pointsLabel: {
    fontSize: 16,
    fontWeight: '900',
    marginRight: 10,
    color: '#000',
  },
  pointsValueBox: {
    width: 100,
    height: 26,
    backgroundColor: '#e6cc99', // Tan color like "Điểm"
    borderWidth: 1,
    borderColor: '#8B4513',
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
  },

  // ── Combat Stats Grid ──────────────────────────────────────────────
  combatStatsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    gap: 4,
  },
  combatStatItem: {
    width: '48%',
    flexDirection: 'row',
    alignItems: 'center',
    height: 24,
  },
  combatStatLabel: {
    fontSize: 13,
    color: '#333',
    flex: 1.2,
  },
  combatStatBox: {
    flex: 1,
    height: '100%',
    backgroundColor: '#d9eaff',
    borderWidth: 1,
    borderColor: '#2255bb',
    borderRadius: 2,
    justifyContent: 'center',
    alignItems: 'center',
  },
  combatStatValue: {
    fontSize: 12,
    fontWeight: 'bold',
    color: '#2255bb',
  },

  // ── Softkey Bar Container ─────────────────────────────────────────
  softKeyBarContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 40,
    backgroundColor: 'transparent', // Make it transparent to show background
  },
});
