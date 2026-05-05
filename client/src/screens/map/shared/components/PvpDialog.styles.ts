import { Dimensions, StyleSheet } from 'react-native';
import { PvpFontStyles } from '../JavaFontMetrics';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

export const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 100,
    justifyContent: 'center',
    alignItems: 'center',
  },
  backdrop: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.55)',
  },
  frame: {
    width: Math.min(SCREEN_W * 0.94, 448),
    maxHeight: Math.min(SCREEN_H * 0.58, 380),
  },
  separator: {
    position: 'absolute',
    left: 0,
    right: 0,
    height: 1,
    backgroundColor: '#20a5de',
    zIndex: 3,
  },
  separatorArena: {
    top: 5, // Java evidence: os.java:116 with n = k(0, 5, v.t, 1) for arena mode.
  },
  separatorChallenge: {
    top: 73, // Java evidence: os.java:116 with n = k(0, 73, v.t, 1) for challenge mode.
  },
  hiddenDragon: {
    position: 'absolute',
    right: 0,
    bottom: 17, // Java evidence: os.java:110 uses y = height - ba.a; ba.a default softkey height is 17.
    opacity: 0.92,
    zIndex: 1,
  },
  timerText: {
    position: 'absolute',
    right: 5,
    bottom: 35, // Java evidence: os.java:120 and ew.java:82 draw at v.u - 35.
    color: '#313338',
    ...PvpFontStyles.arenaSecondary,
    fontWeight: '800',
    zIndex: 4,
  },
  content: {
    padding: 8,
    backgroundColor: '#f2fbff',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 6,
  },
  title: {
    color: '#8a5700',
    ...PvpFontStyles.dialogTitle,
  },
  headerAction: {
    color: '#313338',
    fontSize: 14,
    fontWeight: '700',
  },
  actionDisabled: {
    opacity: 0.45,
  },
  arenaBoard: {
    position: 'relative',
    marginTop: 2,
    borderWidth: 1,
    borderColor: '#96add3',
    backgroundColor: '#f0fbff',
  },
  list: {
    maxHeight: 220,
    width: '100%',
  },
  listContent: {
    gap: 0,
    paddingBottom: 2,
  },
  legacyRow: {
    minHeight: 32,
    borderBottomWidth: 1,
    borderBottomColor: '#b9c7df',
    backgroundColor: '#f0fbff',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 2,  // Java: n2 + 2
    paddingVertical: 3,    // Java: n3 + 3 (normal)
  },
  legacyRowActive: {
    minHeight: 42,
    backgroundColor: '#6ef0ef',  // Java: 7267055 = 0x6EF0EF
    borderTopWidth: 2,
    borderTopColor: '#20a5de',
    borderBottomColor: '#20a5de',
    paddingVertical: 7,  // Java: n3 + 7 (selected)
  },
  legacyBadge: {
    width: 23,  // Java: text starts at n2 + 25, so badge = 25 - 2 (padding) = 23
    alignItems: 'center',
    justifyContent: 'center',
  },
  legacyBadgeActive: {
    // No additional styles needed for View container when selected
  },
  legacyTextWrap: {
    flex: 1,
    minWidth: 0,
    marginLeft: 0,  // Java: text at n2 + 25, badge handles the 23px, padding adds 2px = 25px total
  },
  legacyTextWrapActive: {
    marginLeft: 0,  // Java: same 25px offset for selected
  },
  legacyName: {
    color: '#1d2f59',
    ...PvpFontStyles.arenaPrimary,
  },
  legacyMeta: {
    color: '#2b5ec4',
    ...PvpFontStyles.arenaSecondary,
    marginTop: 13,  // Java: n3 + 13 (13px below primary text)
  },
  legacyStake: {
    width: 34,
    color: '#486ea8',
    fontSize: 11,
    fontWeight: '700',
    textAlign: 'right',
  },
  legacyPreviewCard: {
    position: 'absolute',
    right: 12,
    top: 74,
    width: Math.min(SCREEN_W * 0.54, 212),
    minHeight: 74,
    borderWidth: 2,
    borderColor: '#20a5de',
    backgroundColor: '#f0fbff',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 6,
  },
  arenaEmptyPanel: {
    minHeight: 72,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#f0fbff',
    borderTopWidth: 1,
    borderTopColor: '#b9c7df',
    paddingHorizontal: 12,
  },
  arenaEmptyTitle: {
    color: '#1f2f4d',
    ...PvpFontStyles.arenaPrimary,
    textAlign: 'center',
  },
  arenaEmptyMeta: {
    color: '#496ca5',
    ...PvpFontStyles.arenaSecondary,
    marginTop: 4,
    textAlign: 'center',
  },
  legacyPreviewAvatar: {
    width: 62,
    height: 70,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
    marginRight: 8,
  },
  legacyPreviewInfo: {
    flex: 1,
    minWidth: 0,
  },
  legacyPreviewName: {
    color: '#1f2f4d',
    ...PvpFontStyles.arenaPrimary,
  },
  legacyPreviewMeta: {
    color: '#2c3a52',
    ...PvpFontStyles.arenaSecondary,
    fontWeight: '700',
    marginTop: 2,
  },
  previewRow: {
    minHeight: 74,
    borderWidth: 1,
    borderColor: '#6f5535',
    backgroundColor: '#f2e0b8',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    marginTop: 8,
  },
  previewAvatar: {
    width: 54,
    height: 68,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
    marginRight: 10,
  },
  opponentInfo: {
    flex: 1,
    minWidth: 0,
  },
  opponentName: {
    color: '#2f1d12',
    fontSize: 14,
    fontWeight: '800',
  },
  opponentMeta: {
    color: '#5d4327',
    fontSize: 11,
    marginTop: 2,
  },
  challengeForm: {
    gap: 6,
  },
  label: {
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '700',
  },
  input: {
    minHeight: 34,
    borderWidth: 1,
    borderColor: '#7e5d37',
    backgroundColor: '#f8e8be',
    color: '#2f1d12',
    paddingHorizontal: 10,
    fontSize: 14,
  },
  stakeRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  stakeInput: {
    flex: 1,
  },
  stakeUnit: {
    width: 86,
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '800',
    textAlign: 'right',
  },
  checkGrid: {
    marginTop: 4,
    gap: 6,
  },
  checkRow: {
    flexDirection: 'row',
    alignItems: 'center',
    minHeight: 24,
  },
  checkBox: {
    width: 18,
    height: 18,
    borderWidth: 1,
    borderColor: '#d8a95d',
    backgroundColor: '#1d1712',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 8,
  },
  checkBoxActive: {
    backgroundColor: '#a8642b',
  },
  checkMark: {
    color: '#fff4c9',
    fontSize: 12,
    fontWeight: '900',
    lineHeight: 14,
  },
  checkLabel: {
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '700',
  },
  statusRow: {
    marginTop: 10,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
  },
  statusText: {
    color: '#f8e8be',
    fontSize: 12,
  },
  errorText: {
    color: '#ff9f9f',
    fontSize: 12,
    textAlign: 'center',
    marginTop: 8,
  },
  emptyText: {
    color: '#33486c',
    fontSize: 12,
    textAlign: 'center',
    marginTop: 8,
  },
  footer: {
    marginTop: 8,
    flexDirection: 'row',
    justifyContent: 'flex-end',
    gap: 8,
  },
  button: {
    minWidth: 82,
    minHeight: 30,
    borderWidth: 1,
    borderColor: '#c88b2f',
    backgroundColor: '#fff1cd',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 12,
  },
  buttonPrimary: {
    backgroundColor: '#b26a2f',
  },
  buttonDisabled: {
    opacity: 0.55,
  },
  buttonText: {
    color: '#5a3a0f',
    fontSize: 14,
    fontWeight: '800',
  },
  buttonPrimaryText: {
    color: '#fff4c9',
    fontSize: 14,
    fontWeight: '900',
  },
});
