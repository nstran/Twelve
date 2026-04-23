import { Animated } from 'react-native';
import type { MonsterType } from '../../../engine/MonsterSprite';
import type { CharacterAppearance } from '../../character/shared';
import type { Board, FXKind } from './BattleScreen.shared';
import type { SkillFamilyCode } from './BattleScreen.skills';

export type BattleTurn = 'player' | 'monster';

export interface BattleScreenProps {
  monsterType: MonsterType;
  monsterBootstrap: MonsterBattleBootstrapResponse;
  appearance: CharacterAppearance;
  initialTurn?: BattleTurn;
  onVictory: () => void;
  onDefeat: () => void;
  onFlee: () => void;
  resolveSkillPacket?: ResolveBattleSkillPacket;
  resolveEnemyMove?: ResolveEnemyBattleMove;
  resolveEnemyTurn?: ResolveEnemyBattleTurn;
  resolveEnemyTurnPlan?: ResolveEnemyBattleTurnPlan;
  resolveBattleSessionSync?: ResolveBattleSessionSync;
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
  sessionId: string;
  familyCode: SkillFamilyCode;
  casterSide: BattleSide;
  board: Board;
  selectedCell: BattleCell;
  debugSkillLevel?: number | null;
}

export type ResolveBattleSkillPacket =
  (request: BattleSkillPacketRequest) =>
    BattleSkillRuntimePacket | Promise<BattleSkillRuntimePacket | null> | null;

export interface BattleEnemyTurnRequest {
  sessionId: string;
  board: Board;
}

export type ResolveEnemyBattleTurn =
  (request: BattleEnemyTurnRequest) =>
    BattleSkillRuntimePacket | Promise<BattleSkillRuntimePacket | null> | null;

export interface BattleEnemyMoveRequest {
  sessionId: string;
  board: Board;
}

export interface BattleEnemyMoveResponse {
  move: {
    fromRow: number;
    fromCol: number;
    toRow: number;
    toCol: number;
  };
}

export type ResolveEnemyBattleMove =
  (request: BattleEnemyMoveRequest) =>
    BattleEnemyMoveResponse | Promise<BattleEnemyMoveResponse | null> | null;

export interface BattleEnemyTurnPlanRequest {
  sessionId: string;
  board: Board;
}

export interface BattleEnemyTurnPlanResponse {
  action: 'move' | 'skill' | 'pass';
  move?: {
    fromRow: number;
    fromCol: number;
    toRow: number;
    toCol: number;
  } | null;
  skillPacket?: BattleSkillRuntimePacket | null;
}

export type ResolveEnemyBattleTurnPlan =
  (request: BattleEnemyTurnPlanRequest) =>
    BattleEnemyTurnPlanResponse | Promise<BattleEnemyTurnPlanResponse | null> | null;

export interface BattleSessionSyncRequest {
  sessionId: string;
  board: Board;
  activeTurn: BattleSide;
  playerCurrentHp: number;
  playerCurrentMp: number;
  playerCurrentPower: number;
  enemyCurrentHp: number;
  enemyCurrentMp: number;
  enemyCurrentPower: number;
}

export type ResolveBattleSessionSync =
  (request: BattleSessionSyncRequest) =>
    void | Promise<void>;

export type MonsterSharedSheetFamily = 'Monster' | 'Zap' | 'Ice';

export interface MonsterBattleSkillInstanceDto {
  skillId: number;
  level: number;
  manaCost: number;
}

export interface MonsterBattleAppearanceDto {
  assetCatalogId?: string | null;
  baseBodyId?: number | null;
  weaponBodyId?: number | null;
  hairBodyId?: number | null;
}

export interface BattleCombatantSnapshotDto {
  combatantId: string;
  displayName: string;
  level: number;
  currentHp: number;
  maxHp: number;
  currentMp: number;
  maxMp: number;
  currentPower: number;
  maxPower: number;
  strength: number;
  agility: number;
  magic: number;
  vitality: number;
  minDamage: number;
  maxDamage: number;
  defense: number;
  hitRate: number;
  dodgeRate: number;
  criticalDamage: number;
  skills: MonsterBattleSkillInstanceDto[];
}

export interface MonsterBattleInstanceDto {
  combatantId: string;
  monsterKey: string;
  battleTemplateId: string;
  displayName: string;
  element: number;
  level: number;
  currentHp: number;
  maxHp: number;
  currentMp: number;
  maxMp: number;
  currentPower: number;
  maxPower: number;
  strength: number;
  agility: number;
  magic: number;
  vitality: number;
  minDamage: number;
  maxDamage: number;
  defense: number;
  hitRate: number;
  dodgeRate: number;
  criticalDamage: number;
  skills: MonsterBattleSkillInstanceDto[];
  appearance: MonsterBattleAppearanceDto;
}

export interface MonsterBattleBootstrapRequest {
  mapId: string;
  roomId: number;
  monsterKey: string;
  initialTurnSide: BattleSide;
}

export interface MonsterBattleBootstrapResponse {
  sessionId: string;
  monsterKey: string;
  spawnTemplateKey: string;
  battleTemplateId: string;
  visualTypeByte: number;
  displayLevel: number;
  iqValue: number;
  nameColorMode: number;
  initialTurnSide: BattleSide;
  sharedSheetFamily?: MonsterSharedSheetFamily | null;
  initialBoard: Board;
  player: BattleCombatantSnapshotDto;
  enemy: MonsterBattleInstanceDto;
}

export type ResolveMonsterBattleBootstrap =
  (request: MonsterBattleBootstrapRequest) =>
    MonsterBattleBootstrapResponse | Promise<MonsterBattleBootstrapResponse | null> | null;

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
