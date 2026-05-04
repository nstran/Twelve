import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  monsterContainer: {
    position: 'absolute',
    left: 0,
    overflow: 'visible',
    alignItems: 'center',
  },
  monsterLabel: {
    maxWidth: 120,
    color: '#FFFFFF',
    fontSize: 12,
    fontWeight: '700',
    lineHeight: 14,
    marginBottom: 2,
    textAlign: 'center',
    textShadowColor: '#000000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
  npcContainer: {
    position: 'absolute',
    zIndex: 2,
    overflow: 'visible',
    alignItems: 'center',
  },
  npcLabel: {
    maxWidth: 120,
    fontSize: 12,
    fontWeight: '700',
    lineHeight: 14,
    marginBottom: 2,
    textAlign: 'center',
    textShadowColor: '#000000',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 1,
  },
});
