import { Animated } from 'react-native';
import type { MonsterType } from '../../../engine/MonsterSprite';
import type { FXKind } from './BattleScreen.shared';

export interface BattleScreenProps {
  monsterType: MonsterType;
  onVictory: () => void;
  onDefeat: () => void;
  onFlee: () => void;
}

export type BattlePhase = 'idle' | 'busy' | 'over';
export type BattleResult = 'victory' | 'defeat';
export type BattleSide = 'player' | 'enemy';
export type BattleTurn = 'player' | 'monster';
export type BattleCell = [number, number];

export interface ResultArtMeta {
  asset: any;
  frameWidth: number;
  frameHeight: number;
  sheetWidth: number;
  sheetHeight: number;
}

export interface MatchFXItem {
  key: string;
  r: number;
  c: number;
  kind: FXKind;
  source: any;
  size: number;
  startOffsetX: number;
  startOffsetY: number;
  driftX: number;
  driftY: number;
  rotate: string;
  anim: Animated.Value;
  delayMs: number;
  durationMs: number;
}

export interface DamagePopupItem {
  key: string;
  side: BattleSide;
  amount: number;
  anim: Animated.Value;
}

export interface CollectFXItem {
  key: string;
  source: any;
  startX: number;
  startY: number;
  curve1X: number;
  curve1Y: number;
  curve2X: number;
  curve2Y: number;
  endX: number;
  endY: number;
  size: number;
  isCrystal: boolean;
  renderW: number;
  renderH: number;
  cropLeft: number;
  cropWidth: number;
  fadeOutAt: number;
  endScale: number;
  glowScale: number;
  glowOpacity: number;
  delayMs: number;
  durationMs: number;
  anim: Animated.Value;
}

export interface GainPopupItem {
  key: string;
  side: BattleSide;
  text: string;
  anim: Animated.Value;
}
