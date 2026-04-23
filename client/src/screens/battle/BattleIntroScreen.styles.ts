import { Animated, StyleSheet } from 'react-native';

type PlayerSize = { h: number; groundOffset?: number };
type MonsterSize = { h: number };
type MonsterPlacement = { groundOffset: number };

export const styles = StyleSheet.create({
  root: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  },
  stage: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 10,
    paddingTop: 46,
    paddingBottom: 44,
  },
  cardsLayer: {
    zIndex: 4,
  },
  cardOuter: {
    borderWidth: 2,
    borderColor: '#5aa8ff',
    backgroundColor: '#edf7ff',
    borderRadius: 6,
    padding: 2,
    shadowColor: '#4d88d9',
    shadowOpacity: 0.35,
    shadowRadius: 4,
  },
  cardTop: {
    marginBottom: 12,
  },
  cardBottom: {
    marginTop: 12,
  },
  cardInner: {
    minHeight: 118,
    borderWidth: 1,
    borderColor: '#c9e3ff',
    backgroundColor: 'rgba(247, 252, 255, 0.96)',
    borderRadius: 4,
    overflow: 'hidden',
    justifyContent: 'center',
  },
  hiddenDragon: {
    position: 'absolute',
    width: 107,
    height: 78,
    opacity: 0.22,
    top: 24,
  },
  hiddenDragonRight: {
    right: 16,
  },
  hiddenDragonLeft: {
    left: 16,
  },
  cardContent: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 10,
    paddingVertical: 8,
  },
  cardContentLeft: {
    justifyContent: 'flex-start',
  },
  cardContentRight: {
    flexDirection: 'row-reverse',
    justifyContent: 'space-between',
  },
  actorSlot: {
    width: 96,
    alignItems: 'center',
    justifyContent: 'center',
  },
  textSlot: {
    flex: 1,
    marginLeft: 4,
  },
  textSlotRight: {
    marginLeft: 4,
    marginRight: 0,
    alignItems: 'flex-end',
  },
  nameRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6,
  },
  nameRowRight: {
    justifyContent: 'flex-end',
  },
  badgeText: {
    fontSize: 18,
    marginRight: 6,
  },
  nameText: {
    flexShrink: 1,
    color: '#111',
    fontSize: 20,
    fontWeight: '800',
  },
  levelText: {
    color: '#1f1f1f',
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 4,
  },
  levelTextRight: {
    textAlign: 'right',
  },
  noteText: {
    color: '#242424',
    fontSize: 16,
    fontWeight: '500',
  },
  noteTextRight: {
    textAlign: 'right',
  },
  versusRow: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  versusIcon: {
    color: '#f1c54d',
    fontSize: 36,
    textShadowColor: 'rgba(120, 60, 0, 0.35)',
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 3,
  },
  playerSpriteWrap: {
    width: 82,
    height: 74,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  monsterSpriteWrap: {
    width: 82,
    height: 74,
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  previewPlayer: {
    position: 'absolute',
    zIndex: 1,
  },
  previewMonster: {
    position: 'absolute',
    zIndex: 0,
  },
  hitFlash: {
    position: 'absolute',
    left: -8,
    top: -8,
    right: -8,
    bottom: -8,
    backgroundColor: 'rgba(255,255,255,0.92)',
    borderRadius: 999,
  },
});

export const getBattleIntroCardOuterStyle = (isLeft: boolean, accentColor: string) => ([
  styles.cardOuter,
  isLeft ? styles.cardTop : styles.cardBottom,
  { borderColor: accentColor },
]);

export const getBattleIntroCardInnerStyle = (accentColor: string) => ([
  styles.cardInner,
  { borderColor: accentColor === '#5aa8ff' ? '#c9e3ff' : accentColor },
]);

export const getBattleIntroCardsLayerStyle = (
  cardsAnim: Animated.Value,
) => ([
  styles.cardsLayer,
  {
    opacity: cardsAnim,
    transform: [
      {
        translateY: cardsAnim.interpolate({
          inputRange: [0, 1],
          outputRange: [-18, 0],
        }),
      },
    ],
  },
]);

export const getBattleIntroPreviewPlayerStyle = (
  playerLeft: number,
  groundY: number,
  playerSize: PlayerSize,
  playerLungeAnim: Animated.Value,
) => ([
  styles.previewPlayer,
  {
    left: playerLeft,
    top: groundY - playerSize.h + (playerSize.groundOffset ?? 0),
    transform: [{ translateX: playerLungeAnim }],
  },
]);

export const getBattleIntroPreviewMonsterStyle = (
  monsterLeft: number,
  groundY: number,
  monsterSize: MonsterSize,
  monsterPlacement: MonsterPlacement,
  monsterShakeAnim: Animated.Value,
  previewMonsterScale: number,
) => ([
  styles.previewMonster,
  {
    left: monsterLeft,
    top: groundY - monsterSize.h + monsterPlacement.groundOffset,
    transform: [{ translateX: monsterShakeAnim }, { scale: previewMonsterScale }],
  },
]);

export const getBattleIntroHitFlashStyle = (hitFlashAnim: Animated.Value) => ([
  styles.hitFlash,
  { opacity: hitFlashAnim },
]);
