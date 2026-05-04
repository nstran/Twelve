import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  stack: {
    position: 'absolute',
    left: 12,
    right: 12,
    bottom: 92,
    zIndex: 94,
    gap: 6,
    alignItems: 'stretch',
  },
  toast: {
    borderWidth: 1,
    borderColor: '#d8a95d',
    backgroundColor: 'rgba(37, 23, 13, 0.9)',
    paddingHorizontal: 10,
    paddingVertical: 7,
  },
  title: {
    color: '#ffd276',
    fontSize: 12,
    fontWeight: '900',
    textAlign: 'center',
  },
  text: {
    color: '#fff2c8',
    fontSize: 12,
    fontWeight: '800',
    textAlign: 'center',
    marginTop: 2,
  },
  line: {
    color: '#f5d89a',
    fontSize: 11,
    fontWeight: '700',
    textAlign: 'center',
    marginTop: 1,
  },
});
