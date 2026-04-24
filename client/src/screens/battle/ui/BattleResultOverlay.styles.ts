import { StyleSheet } from 'react-native';

export const resultStyles = StyleSheet.create({
  splashClip: {
    overflow: 'hidden',
  },
  resultCard: {
    width: 212,
    minHeight: 214,
    backgroundColor: '#E7F4FF',
    borderWidth: 2,
    borderColor: '#4895FF',
    padding: 6,
    overflow: 'hidden',
    borderRadius: 8,
  },
  levelText: {
    fontSize: 12,
    fontWeight: '700',
    color: '#202020',
    marginBottom: 5,
  },
  sectionTitle: {
    fontSize: 12,
    fontWeight: '700',
    color: '#333',
  },
  collectionTitle: {
    marginTop: 10,
    marginBottom: 4,
  },
  rewardTitle: {
    marginTop: 8,
  },
  lootTitle: {
    marginTop: 8,
  },
  levelUpText: {
    fontSize: 11,
    color: '#D05500',
    fontWeight: '700',
    marginTop: 2,
  },
  resultArt: {
    position: 'absolute',
    right: 10,
    bottom: 20,
    overflow: 'hidden',
    opacity: 0.72,
  },
  closeText: {
    position: 'absolute',
    bottom: 2,
    left: 0,
    right: 0,
    textAlign: 'center',
    color: '#516070',
    fontSize: 11,
  },
  bar: {
    height: 16,
    marginBottom: 5,
    borderWidth: 1,
    borderColor: '#94A8B8',
    backgroundColor: '#FFF8E8',
    overflow: 'hidden',
    borderRadius: 5,
  },
  barFill: {
    height: '100%',
    borderRadius: 4,
  },
  barLabel: {
    position: 'absolute',
    left: 0,
    right: 0,
    top: 1,
    textAlign: 'center',
    fontSize: 10,
    fontWeight: '700',
    color: '#6E1818',
  },
  barRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  barTrack: {
    flex: 1,
  },
  barIconFrame: {
    width: 16,
    height: 16,
    overflow: 'hidden',
    alignItems: 'flex-start',
    justifyContent: 'flex-start',
  },
  staticIcon: {
    width: 14,
    height: 14,
  },
  rewardLine: {
    flexDirection: 'row',
    alignItems: 'center',
    height: 16,
    marginBottom: 1,
  },
  rewardIconWrap: {
    width: 14,
    height: 14,
    marginRight: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  rewardLabel: {
    fontSize: 12,
    color: '#222',
    fontWeight: '600',
  },
  lootText: {
    fontSize: 10,
    color: '#30435f',
    lineHeight: 13,
  },
});
