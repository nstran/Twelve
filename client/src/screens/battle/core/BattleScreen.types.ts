import { Animated } from 'react-native';
import type { MonsterType } from '../../../engine/MonsterSprite';
import type { CharacterAppearance } from '../../character/shared';
import type { Board, FXKind } from './BattleScreen.shared';
import type { SkillFamilyCode } from './BattleScreen.skills';

export type BattleTurn = 'player' | 'monster';

export interface BattleScreenProps {
  monsterType: MonsterType;
  appearance: CharacterAppearance;
  initialTurn?: BattleTurn;
  onVictory: () => void;
  onDefeat: () => void;
  onFlee: () => void;
  resolveSkillPacket?: ResolveBattleSkillPacket;
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

export interface ScreenPoint {
  x: number;
  y: number;
}

export type BattleSkillRuntimeSource = 'server_packet';
export type BattleSkillActorAnchor = 'center' | 'bottom';
export type BattleSkillBoardMutationKind = 'clear' | 'mark' | 'helper' | 'none';
export type BattleSkillLevelSource = 'server_authority' | 'client_debug_request' | 'server_fallback';

export interface BattleSkillActorPacketTarget {
  side: BattleSide;
  anchor: BattleSkillActorAnchor;
}

export interface BattleSkillBoardMutation {
  kind: BattleSkillBoardMutationKind;
  cells: BattleCell[];
  stateId?: number | null;
}

export interface BattleSkillPacketImpact {
  hitsActor: boolean;
  damage: number | null;
  hitShakePx?: number | null;
}

export interface BattleSkillActorDelta {
  side: BattleSide;
  hpDelta: number;
  manaDelta: number;
  powerDelta: number;
}

export interface BattleSkillTurnDelta {
  remainingTurnsDelta: number;
  timeLeftSecondsDelta: number;
}

export interface BattleSkillRuntimePacket {
  // Java packet arrays are normalized to 0..7 board coordinates before they reach
  // this client contract. Server adapters should convert Java row 2..9 -> client 0..7.
  castId: string;
  familyCode: SkillFamilyCode;
  runtimeSource: 'server_packet';
  casterSide: BattleSide;
  actorTarget: BattleSkillActorPacketTarget | null;
  boardMutation: BattleSkillBoardMutation;
  cellTargets: BattleCell[];
  impact: BattleSkillPacketImpact;
  actorDeltas?: BattleSkillActorDelta[] | null;
  turnDelta?: BattleSkillTurnDelta | null;
  skillLevelSource: BattleSkillLevelSource;
  grantsExtraTurn?: boolean | null;
  extraTurnChancePercent?: number | null;
  impactDelayMs?: number | null;
  durationMs?: number | null;
}

export interface BattleSkillPacketRequest {
  familyCode: SkillFamilyCode;
  casterSide: BattleSide;
  board: Board;
  selectedCell: BattleCell;
  debugSkillLevel?: number | null;
}

export type ResolveBattleSkillPacket =
  (request: BattleSkillPacketRequest) =>
    BattleSkillRuntimePacket | Promise<BattleSkillRuntimePacket | null> | null;

export interface BattleSkillRuntimePayload {
  actorTarget: ScreenPoint | null;
  boardClearTargets: SkillTargetPoint[];
  cellTargets: SkillTargetPoint[];
  runtimeSource: BattleSkillRuntimeSource;
  hitsActor: boolean;
}

export interface ActiveBattleSkillCast {
  key: string;
  familyCode: SkillFamilyCode;
  startedAt: number;
  casterSide: BattleSide;
  sourceX: number;
  sourceY: number;
  actorTarget: BattleSkillRuntimePayload['actorTarget'];
  boardClearTargets: BattleSkillRuntimePayload['boardClearTargets'];
  cellTargets: BattleSkillRuntimePayload['cellTargets'];
  runtimeSource: BattleSkillRuntimeSource;
  hitsActor: boolean;
  impactDelayMs: number;
  boardMutationDelayMs: number;
  durationMs: number;
}
