import { Dimensions, StyleSheet } from 'react-native';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

export const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 98,
    alignItems: 'center',
    justifyContent: 'center',
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
    width: Math.min(SCREEN_W * 0.94, 470),
    maxHeight: Math.min(SCREEN_H * 0.76, 520),
  },
  content: {
    paddingHorizontal: 10,
    paddingVertical: 9,
    backgroundColor: '#fff1c2',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    borderBottomWidth: 1,
    borderBottomColor: '#d09a3d',
    paddingBottom: 6,
    marginBottom: 8,
  },
  title: {
    color: '#7a3b08',
    fontSize: 18,
    fontWeight: '900',
  },
  close: {
    color: '#1d4ed8',
    fontSize: 13,
    fontWeight: '800',
  },
  body: {
    flexDirection: SCREEN_W >= 430 ? 'row' : 'column',
    gap: 8,
  },
  listPanel: {
    width: SCREEN_W >= 430 ? 160 : '100%',
    maxHeight: SCREEN_W >= 430 ? 360 : 150,
    borderWidth: 1,
    borderColor: '#d6a24d',
    backgroundColor: '#fff8dd',
    padding: 6,
  },
  detailPanel: {
    flex: 1,
    minHeight: 220,
    borderWidth: 1,
    borderColor: '#d6a24d',
    backgroundColor: '#fffaf0',
    padding: 8,
  },
  panelTitle: {
    color: '#8a5700',
    fontSize: 12,
    fontWeight: '900',
    marginTop: 6,
    marginBottom: 3,
  },
  listScroll: {
    maxHeight: 304,
  },
  listContent: {
    gap: 5,
  },
  listItem: {
    borderWidth: 1,
    borderColor: '#e2c078',
    backgroundColor: '#fffef7',
    paddingHorizontal: 7,
    paddingVertical: 6,
  },
  listItemActive: {
    borderColor: '#cc7a16',
    backgroundColor: '#ffe3a1',
  },
  listTitle: {
    color: '#3b260f',
    fontSize: 12,
    fontWeight: '800',
  },
  listTitleActive: {
    color: '#8a2f00',
  },
  listMeta: {
    color: '#7b5a2b',
    fontSize: 10,
    fontWeight: '700',
    marginTop: 2,
  },
  detailTitle: {
    color: '#6f2f00',
    fontSize: 16,
    fontWeight: '900',
  },
  detailDesc: {
    color: '#2f1d12',
    fontSize: 12,
    fontWeight: '700',
    lineHeight: 16,
    marginTop: 5,
  },
  taskLine: {
    color: '#1f2937',
    fontSize: 12,
    fontWeight: '700',
    lineHeight: 16,
  },
  rewardLine: {
    color: '#0f7a37',
    fontSize: 12,
    fontWeight: '800',
    lineHeight: 16,
  },
  emptyText: {
    color: '#7b5a2b',
    fontSize: 12,
    fontWeight: '700',
    lineHeight: 16,
  },
  actionButton: {
    alignSelf: 'flex-end',
    marginTop: 10,
    borderWidth: 1,
    borderColor: '#1d4ed8',
    backgroundColor: '#e8f0ff',
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  actionButtonDisabled: {
    opacity: 0.55,
    borderColor: '#9ca3af',
    backgroundColor: '#edf1f6',
  },
  actionButtonText: {
    color: '#1d4ed8',
    fontSize: 13,
    fontWeight: '900',
  },
});
