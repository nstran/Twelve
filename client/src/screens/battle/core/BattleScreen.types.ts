import { Animated } from 'react-native';
import type { MonsterType } from '../../../engine/MonsterSprite';
import type { CharacterAppearance } from '../../character/shared';
import type { FXKind } from './BattleScreen.shared';
import type { SkillFamilyCode } from './BattleScreen.skills';

export type BattleTurn = 'player' | 'monster';

export interface BattleScreenProps {
  monsterType: MonsterType;
  appearance: CharacterAppearance;
  initialTurn?: BattleTurn;
  onVictory: () => void;
  onDefeat: () => void;
  onFlee: () => void;
}

export type BattlePhase = 'idle' | 'busy' | 'over';
export type BattleResult = 'victory' | 'defeat';
export type BattleSide = 'player' | 'enemy';
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

export interface SkillTargetPoint {
  x: number;
  y: number;
  row: number;
  col: number;
}

export interface ActiveBattleSkillCast {
  key: string;
  familyCode: SkillFamilyCode;
  startedAt: number;
  sourceX: number;
  sourceY: number;
  targetX: number;
  targetY: number;
  boardPoints: SkillTargetPoint[];
  effectPoints: SkillTargetPoint[];
  durationMs: number;
}
