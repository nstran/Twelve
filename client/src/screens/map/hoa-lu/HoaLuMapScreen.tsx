import React, { useRef, useState, useCallback, useEffect, useMemo } from 'react';
import {
  Animated, View, Image, ScrollView, Text,
  StyleSheet, Dimensions, Platform,
  ActivityIndicator, Pressable, TextInput, TouchableOpacity,
} from 'react-native';
import {
  MonsterSprite, MonsterType,
  WALK_FRAMES, monsterDisplaySize, monsterPlacementMetrics,
} from '../../../engine/MonsterSprite';
import {
  JavaCompatibleCharacterController,
  buildFlatGroundJavaGrid,
  type CharacterControllerRef,
  type GroundSurface,
  type MonsterTarget,
  getSurfaceStartY,
} from '../../../engine/character';
import { CharacterRenderer, measureCharacterRenderer } from '../../character';
import {
  BattleIntroScreen,
  type MonsterSharedSheetFamily,
  resolveMonsterTypeFromVisuals,
} from '../../battle';
import { MapHUD } from '../../../components/game/MapHUD/MapHUD';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu } from '../../../components/controls/PopupMenu/PopupMenu';
import { TouchGamepad } from '../../../components/controls/TouchGamepad';
import { CornerFrame } from '../../../components/ui/CornerFrame/CornerFrame';
import { clearSession } from '../../../storage/SessionStorage';
import {
  SocketClient,
  type MapInfo,
  type MapMonsterRosterPacket,
  type PlayerMapState,
} from '../../../network/SocketClient';
import { resolveSideScrollMapSceneConfig } from '../core';
import { createMapGameMenuItems } from '../core';
import {
  MapCharacterDialogs,
  type CharacterStatKey,
  type MapCharacterDialogKind,
} from '../core';
import type {
  MapMonsterRosterEntry,
  ResolveMapMonsterRoster,
} from '../core';
import { applyMapMonsterRuntimePacket } from '../core';
import type { CharacterAppearance } from '../../character/shared';
import type {
  MonsterBattleBootstrapResponse,
  PvpOpponentEntry,
  PvpChallengeTicket,
  ResolvePvpBattleBootstrap,
  ResolvePvpChallengeApi,
  ResolvePvpOpponents,
  ResolveMonsterBattleBootstrap,
} from '../../battle';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');
const ASSET_SOFTKEY_MENU = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');

const SOFTKEY_BAR_HEIGHT = 26;
const LAYER_BG = 0;
const LAYER_GROUND = 1;
const LAYER_MONSTER = 2;
const LAYER_CHARACTER = 3;

/**
 * Runtime monster data (mutable, NEVER replaced).
 * Position is driven by Animated.Value → native-thread translateX, no React
 * render is needed for movement. Visual-only fields (frameIndex, direction,
 * attacking) are mirrored into a React state array so the sprite can flip /
 * cycle frames, but those fields change at ~5Hz instead of 20Hz.
 */
interface MonsterRuntime {
  id: string;
  type: MonsterType;
  roster: MapMonsterRosterEntry;
  surfaceId: string;
  groundY: number;
  minX: number;
  maxX: number;
  x: number;              // center X (mutable)
  direction: 1 | -1;
  tickCount: number;
  attacking: boolean;
  worldState: 'patrol' | 'alert' | 'engaging';
  aggroTicks: number;
  engageQueued: boolean;
  frameIndex: number;
  xAnim: Animated.Value;  // drives translateX on native side
  size: ReturnType<typeof monsterDisplaySize>;
  topY: number;
}

/** React-state slice — only re-rendered when it actually changes. */
interface MonsterVisual {
  id: string;
  frameIndex: number;
  direction: 1 | -1;
  attacking: boolean;
  worldState: 'patrol' | 'alert' | 'engaging';
}

const ENGAGE_TRIGGER_DELAY_MS = 120;
const PLAYER_ALERT_MEMORY_MS = 220;
// Recovery/invincible window sau khi rời battle: Java server không có đặc tả
// map-side này trong phần đã rà, nên client Hoa Lư tự phát triển để tránh
// monster overlap retrigger ngay tại vị trí vừa trở lại map.
const BATTLE_RECOVERY_MS = 3000;
// Mỗi bao nhiêu tick thì đổi frame (tick=50ms, FRAME_TICKS=4 → 80ms/frame ≈ 12fps anim)
const FRAME_TICKS = 4;
const MONSTER_TICK_MS = 50; // 20 logic ticks/sec

const getLoopNowMs = (): number => (
  typeof performance !== 'undefined' && typeof performance.now === 'function'
    ? performance.now()
    : Date.now()
);

function createMonsterRuntime(entry: MapMonsterRosterEntry, surfaces: GroundSurface[]): MonsterRuntime {
  const surface = surfaces.find((candidate) => candidate.id === entry.surfaceId);
  if (!surface) {
    throw new Error(`Surface '${entry.surfaceId}' not found in Hoa Lu navigation data.`);
  }

  const span = Math.max(0, surface.x2 - surface.x1);
  const minX = surface.x1 + Math.max(0, Math.min(1, entry.patrolStartRatio)) * span;
  const maxX = surface.x1 + Math.max(0, Math.min(1, entry.patrolEndRatio)) * span;
  const startX = minX + Math.max(0, maxX - minX) * Math.max(0, Math.min(1, entry.spawnRatio));
  const type = resolveMonsterTypeFromVisuals(entry.visualTypeByte, entry.sharedSheetFamily);
  const size = monsterDisplaySize(type);
  const placement = monsterPlacementMetrics(type);
  const leftX = startX - size.w / 2;
  const surfaceGroundY = getSurfaceStartY(surface);

  return {
    id: entry.monsterKey,
    type,
    roster: entry,
    surfaceId: surface.id,
    groundY: surfaceGroundY,
    minX,
    maxX,
    x: startX,
    direction: (entry.spawnInstanceIndex & 1) === 0 ? 1 : -1,
    tickCount: 0,
    attacking: false,
    worldState: 'patrol',
    aggroTicks: 0,
    engageQueued: false,
    frameIndex: WALK_FRAMES[0],
    xAnim: new Animated.Value(leftX),
    size,
    topY: surfaceGroundY - size.h + placement.groundOffset,
  };
}

function reconcileMonsterRuntimes(
  previousRuntimes: MonsterRuntime[],
  previousTargets: MonsterTarget[],
  roster: MapMonsterRosterEntry[],
  surfaces: GroundSurface[],
): { runtimes: MonsterRuntime[]; targets: MonsterTarget[] } {
  const previousById = new Map(previousRuntimes.map((runtime) => [runtime.id, runtime]));
  const previousTargetsById = new Map(previousTargets.map((target) => [target.id, target]));

  const runtimes = roster.map((entry) => {
    const existing = previousById.get(entry.monsterKey);
    if (!existing) {
      return createMonsterRuntime(entry, surfaces);
    }

    const surface = surfaces.find((candidate) => candidate.id === entry.surfaceId);
    if (!surface) {
      throw new Error(`Surface '${entry.surfaceId}' not found in Hoa Lu navigation data.`);
    }

    const span = Math.max(0, surface.x2 - surface.x1);
    const minX = surface.x1 + Math.max(0, Math.min(1, entry.patrolStartRatio)) * span;
    const maxX = surface.x1 + Math.max(0, Math.min(1, entry.patrolEndRatio)) * span;
    const type = resolveMonsterTypeFromVisuals(entry.visualTypeByte, entry.sharedSheetFamily);
    const size = monsterDisplaySize(type);
    const placement = monsterPlacementMetrics(type);
    const surfaceGroundY = getSurfaceStartY(surface);
    const clampedX = Math.max(minX, Math.min(maxX, existing.x));
    const nextTopY = surfaceGroundY - size.h + placement.groundOffset;
    const nextLeftX = clampedX - size.w / 2;

    existing.type = type;
    existing.roster = entry;
    existing.surfaceId = surface.id;
    existing.groundY = surfaceGroundY;
    existing.minX = minX;
    existing.maxX = maxX;
    existing.x = clampedX;
    existing.size = size;
    existing.topY = nextTopY;
    existing.xAnim.setValue(nextLeftX);

    return existing;
  });

  const targets = runtimes.map((runtime) => {
    const existing = previousTargetsById.get(runtime.id);
    const leftX = runtime.x - runtime.size.w / 2;
    if (existing) {
      existing.x = leftX;
      existing.y = runtime.topY;
      existing.width = runtime.size.w;
      existing.height = runtime.size.h;
      return existing;
    }

    return {
      id: runtime.id,
      x: leftX,
      y: runtime.topY,
      width: runtime.size.w,
      height: runtime.size.h,
    };
  });

  return { runtimes, targets };
}

function hasMonsterCollision(
  playerLeft: number,
  playerWidth: number,
  playerFootY: number,
  monsterCenterX: number,
  monsterGroundY: number,
  monsterWidth: number,
  monsterHeight: number,
): boolean {
  const playerRight = playerLeft + playerWidth;
  const monsterLeft = monsterCenterX - monsterWidth / 2;
  const monsterRight = monsterLeft + monsterWidth;
  const horizontalInset = Math.max(6, Math.min(18, Math.floor(Math.min(playerWidth, monsterWidth) * 0.22)));
  const horizontalOverlap = playerRight >= monsterLeft + horizontalInset &&
    playerLeft <= monsterRight - horizontalInset;

  if (!horizontalOverlap) {
    return false;
  }

  // Java map actors collide through runtime hitboxes (`kl.t`) rather than
  // a pure X-line trigger. This vertical clearance lets a player jump over a
  // monster without starting battle while still triggering when feet overlap
  // the monster body near ground level.
  const monsterTop = monsterGroundY - monsterHeight;
  const verticalInset = Math.max(4, Math.min(14, Math.floor(monsterHeight * 0.18)));
  return playerFootY >= monsterTop + verticalInset;
}

function buildInitialVisuals(runtimes: MonsterRuntime[]): MonsterVisual[] {
  return runtimes.map(m => ({
    id: m.id,
    frameIndex: m.frameIndex,
    direction: m.direction,
    attacking: m.attacking,
    worldState: m.worldState,
  }));
}

/**
 * Memoized monster renderer — driven by a **stable** runtimes array
 * (identity never changes) + a React state array for visual changes.
 * Position updates via Animated.Value don't cause this component to
 * re-render at all; only frame/direction flips do.
 */
interface MonsterFieldProps {
  runtimes: MonsterRuntime[];
  visuals: MonsterVisual[];
}

const MonsterField = React.memo<MonsterFieldProps>(({ runtimes, visuals }) => (
  <>
    {runtimes.map((m, i) => {
      const vis = visuals[i] ?? {
        frameIndex: m.frameIndex,
        direction: m.direction,
        attacking: m.attacking,
        worldState: m.worldState,
      };
      const spriteScale = vis.worldState === 'engaging'
        ? 1.06
        : vis.worldState === 'alert'
          ? 1.03
          : 1;
      return (
        <Animated.View
          key={m.id}
          style={{
            position: 'absolute',
            left: 0,
            top: m.topY - 16,
            width: Math.max(m.size.w, 72),
            height: m.size.h + 16,
            zIndex: LAYER_MONSTER,
            transform: [{ translateX: m.xAnim }, { scale: spriteScale }],
            overflow: 'visible',
            alignItems: 'center',
          }}
          pointerEvents="none"
        >
          <Text
            numberOfLines={1}
            style={{
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
            }}
          >
            {m.roster.displayName}
          </Text>
          <MonsterSprite
            type={m.type}
            frameIndex={vis.frameIndex}
            facingRight={vis.direction === 1}
          />
        </Animated.View>
      );
    })}
  </>
));
MonsterField.displayName = 'MonsterField';

// ── Props ────────────────────────────────────────────────────────────────────
interface Props {
  mapId: string;
  roomId: number;
  roomLabel?: string;
  appearance: CharacterAppearance;
  defeatBlinkToken?: number;
  onBack:    () => void;
  onLogout:  () => void;
  onBattle?: (
    monsterType: MonsterType,
    initialTurn: 'player' | 'monster',
    monsterBootstrap: MonsterBattleBootstrapResponse,
  ) => void;
  resolveMonsterRoster?: ResolveMapMonsterRoster;
  resolveMonsterBootstrap?: ResolveMonsterBattleBootstrap;
  resolvePvpOpponents?: ResolvePvpOpponents;
  resolvePvpBootstrap?: ResolvePvpBattleBootstrap;
  resolvePvpChallengeApi?: ResolvePvpChallengeApi;
  onAllocateStat?: (stat: CharacterStatKey) => Promise<string | null>;
  onAllocateSkill?: (familyCode: number) => Promise<string | null>;
  onToggleEquipment?: (equipKey: string, equip: boolean) => Promise<string | null>;
  onPreviewEquipmentLoadout?: (equipKeys: string[]) => Promise<CharacterAppearance | null>;
  onCommitEquipmentLoadout?: (equipKeys: string[]) => Promise<string | null>;
  onUseItem?: (itemId: number) => Promise<string | null>;
  onDiscardEquipment?: (equipKey: string) => Promise<string | null>;
  onDiscardItem?: (itemId: number, quantity: number) => Promise<string | null>;
  onRepairEquipment?: (equipKey: string) => Promise<string | null>;
}

interface EncounterPreviewState {
  monsterType: MonsterType;
  monsterKey: string;
  displayName: string;
  displayLevel: number;
  iqValue: number;
  visualTypeByte: number;
  sharedSheetFamily: MonsterSharedSheetFamily;
  nameColorMode: number;
  monsterFrameIndex: number;
  monsterFacingRight: boolean;
  monsterWorldState: 'patrol' | 'alert' | 'engaging';
  playerLeft: number;
  monsterLeft: number;
  groundY: number;
  initialTurn: 'player' | 'monster';
  bootstrapStatus: 'loading' | 'ready' | 'error';
  monsterBootstrap: MonsterBattleBootstrapResponse | null;
}

type PvpDialogMode = 'arena' | 'challenge';
type PvpDialogStatus = 'idle' | 'loading' | 'ready' | 'error' | 'starting';

interface PvpIncomingPromptState {
  ticket: PvpChallengeTicket;
  status: 'pending' | 'starting';
}

interface PvpStartOptions {
  stake: number;
  allowSpectators: boolean;
  oneWay: boolean;
  disableSpecialSkills: boolean;
}

interface PvpDialogProps {
  mode: PvpDialogMode;
  opponents: PvpOpponentEntry[];
  status: PvpDialogStatus;
  error: string | null;
  selectedTarget: string;
  stakeThousands: string;
  allowSpectators: boolean;
  oneWay: boolean;
  disableSpecialSkills: boolean;
  onClose: () => void;
  onRefresh: () => void;
  onSelectTarget: (target: string) => void;
  onStakeThousandsChange: (value: string) => void;
  onAllowSpectatorsChange: (value: boolean) => void;
  onOneWayChange: (value: boolean) => void;
  onDisableSpecialSkillsChange: (value: boolean) => void;
  onStart: (target: string, options: PvpStartOptions) => void;
}

interface PvpIncomingPromptProps {
  prompt: PvpIncomingPromptState;
  onAccept: (ticket: PvpChallengeTicket) => void;
  onDecline: (ticket: PvpChallengeTicket) => void;
}

const parseStakeThousands = (value: string): number => {
  const normalized = value.replace(/[^\d]/g, '');
  if (!normalized) {
    return 0;
  }

  return Math.max(0, Number(normalized)) * 1000;
};

const formatPvpStake = (stake: number): string =>
  `${Math.max(0, Math.floor(stake / 1000)).toLocaleString('vi-VN')}.000 KEN`;

const formatPvpHonorLine = (honor: number): string => `Cấp -- Danh vọng ${Math.max(0, honor)}`;

const PvpCheckbox: React.FC<{
  label: string;
  checked: boolean;
  onChange: (value: boolean) => void;
}> = ({ label, checked, onChange }) => (
  <TouchableOpacity
    activeOpacity={0.85}
    style={styles.pvpCheckRow}
    onPress={() => onChange(!checked)}
  >
    <View style={[styles.pvpCheckBox, checked && styles.pvpCheckBoxActive]}>
      {checked ? <Text style={styles.pvpCheckMark}>✓</Text> : null}
    </View>
    <Text style={styles.pvpCheckLabel}>{label}</Text>
  </TouchableOpacity>
);

const PvpDialog: React.FC<PvpDialogProps> = ({
  mode,
  opponents,
  status,
  error,
  selectedTarget,
  stakeThousands,
  allowSpectators,
  oneWay,
  disableSpecialSkills,
  onClose,
  onRefresh,
  onSelectTarget,
  onStakeThousandsChange,
  onAllowSpectatorsChange,
  onOneWayChange,
  onDisableSpecialSkillsChange,
  onStart,
}) => {
  const isBusy = status === 'loading' || status === 'starting';
  const trimmedTarget = selectedTarget.trim();
  const exactOpponent = opponents.find(
    (opponent) => opponent.username.toLowerCase() === trimmedTarget.toLowerCase(),
  );
  const selectedOpponent = mode === 'arena'
    ? exactOpponent ?? opponents[0] ?? null
    : exactOpponent ?? null;
  const arenaFocusCard = mode === 'arena' ? selectedOpponent : null;

  return (
    <View style={styles.pvpOverlay}>
      <Pressable style={styles.pvpBackdrop} onPress={isBusy ? undefined : onClose} />
      <CornerFrame style={styles.pvpFrame} contentStyle={styles.pvpContent}>
        <View style={styles.pvpHeader}>
          <Text style={styles.pvpTitle}>{mode === 'arena' ? 'Lôi Đài' : 'Khiêu Chiến'}</Text>
          <TouchableOpacity activeOpacity={0.85} onPress={onRefresh} disabled={isBusy}>
            <Text style={[styles.pvpHeaderAction, isBusy && styles.pvpActionDisabled]}>Cập nhật</Text>
          </TouchableOpacity>
        </View>

        {mode === 'challenge' ? (
          <View style={styles.pvpChallengeForm}>
            <Text style={styles.pvpLabel}>Nhập nick</Text>
            <TextInput
              value={selectedTarget}
              onChangeText={onSelectTarget}
              editable={!isBusy}
              autoCapitalize="none"
              autoCorrect={false}
              style={styles.pvpInput}
              placeholderTextColor="#9b7b51"
              placeholder="Tên nhân vật"
            />
            <Text style={styles.pvpLabel}>Đặt Cược</Text>
            <View style={styles.pvpStakeRow}>
              <TextInput
                value={stakeThousands}
                onChangeText={onStakeThousandsChange}
                editable={!isBusy}
                keyboardType="number-pad"
                style={[styles.pvpInput, styles.pvpStakeInput]}
                placeholderTextColor="#9b7b51"
                placeholder="0"
              />
              <Text style={styles.pvpStakeUnit}>.000 KEN</Text>
            </View>
            <View style={styles.pvpCheckGrid}>
              <PvpCheckbox label="Cho xem" checked={allowSpectators} onChange={onAllowSpectatorsChange} />
              <PvpCheckbox label="1 chiều" checked={oneWay} onChange={onOneWayChange} />
              <PvpCheckbox
                label="Không chơi Tuyệt Chiêu"
                checked={disableSpecialSkills}
                onChange={onDisableSpecialSkillsChange}
              />
            </View>
          </View>
        ) : null}

        {mode === 'arena' ? (
          <View style={styles.pvpArenaBoard}>
            <ScrollView style={styles.pvpList} contentContainerStyle={styles.pvpListContent}>
              {opponents.map((opponent) => {
                const selected = opponent.username.toLowerCase() === trimmedTarget.toLowerCase();
                return (
                  <TouchableOpacity
                    key={opponent.username}
                    activeOpacity={0.86}
                    style={[styles.pvpLegacyRow, selected && styles.pvpLegacyRowActive]}
                    onPress={() => onSelectTarget(opponent.username)}
                    disabled={isBusy}
                  >
                    <Text style={styles.pvpLegacyBadge}>{opponent.currentHp > 0 ? '><' : '[]'}</Text>
                    <View style={styles.pvpLegacyTextWrap}>
                      <Text style={styles.pvpLegacyName} numberOfLines={1}>{opponent.username}</Text>
                      <Text style={styles.pvpLegacyMeta} numberOfLines={1}>{formatPvpHonorLine(opponent.honor)}</Text>
                    </View>
                    <Text style={styles.pvpLegacyStake}>{Math.max(0, opponent.level)}</Text>
                  </TouchableOpacity>
                );
              })}
            </ScrollView>

            {opponents.length === 0 ? (
              <View style={styles.pvpArenaEmptyPanel}>
                <Text style={styles.pvpArenaEmptyTitle}>Bảng Lôi Đài đang trống</Text>
                <Text style={styles.pvpArenaEmptyMeta}>Bấm Cập nhật để lấy danh sách đối thủ</Text>
              </View>
            ) : null}

            {arenaFocusCard ? (
              <View style={styles.pvpLegacyPreviewCard}>
                <View style={styles.pvpLegacyPreviewAvatar}>
                  <CharacterRenderer appearance={arenaFocusCard.appearance} scale={0.88} anchorToBody facing="right" />
                </View>
                <View style={styles.pvpLegacyPreviewInfo}>
                  <Text style={styles.pvpLegacyPreviewName} numberOfLines={1}>{arenaFocusCard.username}</Text>
                  <Text style={styles.pvpLegacyPreviewMeta} numberOfLines={1}>Cấp: {arenaFocusCard.level}</Text>
                  <Text style={styles.pvpLegacyPreviewMeta} numberOfLines={1}>{arenaFocusCard.statusMessage || 'Hào Kiệt'}</Text>
                </View>
              </View>
            ) : null}
          </View>
        ) : selectedOpponent ? (
          <View style={styles.pvpPreviewRow}>
            <View style={styles.pvpPreviewAvatar}>
              <CharacterRenderer appearance={selectedOpponent.appearance} scale={0.9} anchorToBody facing="right" />
            </View>
            <View style={styles.pvpOpponentInfo}>
              <Text style={styles.pvpOpponentName} numberOfLines={1}>{selectedOpponent.username}</Text>
              <Text style={styles.pvpOpponentMeta} numberOfLines={1}>
                Cấp {selectedOpponent.level}  |  Danh vọng {selectedOpponent.honor}
              </Text>
              <Text style={styles.pvpOpponentMeta} numberOfLines={1}>
                Sinh lực {selectedOpponent.currentHp}/{selectedOpponent.maxHp}
              </Text>
            </View>
          </View>
        ) : null}

        {status === 'loading' ? (
          <View style={styles.pvpStatusRow}>
            <ActivityIndicator color="#f2d383" />
            <Text style={styles.pvpStatusText}>Đang tải...</Text>
          </View>
        ) : null}
        {error ? <Text style={styles.pvpErrorText}>{error}</Text> : null}
        {status !== 'loading' && opponents.length === 0 ? (
          <Text style={styles.pvpEmptyText}>Chưa có đối thủ</Text>
        ) : null}

        <View style={styles.pvpFooter}>
          <TouchableOpacity activeOpacity={0.85} style={styles.pvpButton} onPress={onClose} disabled={isBusy}>
            <Text style={styles.pvpButtonText}>Hủy</Text>
          </TouchableOpacity>
          <TouchableOpacity
            activeOpacity={0.85}
            style={[styles.pvpButton, styles.pvpButtonPrimary, isBusy && styles.pvpButtonDisabled]}
            disabled={isBusy}
            onPress={() => onStart(trimmedTarget, {
              stake: parseStakeThousands(stakeThousands),
              allowSpectators,
              oneWay,
              disableSpecialSkills,
            })}
          >
            <Text style={styles.pvpButtonPrimaryText}>{mode === 'arena' ? 'Đánh!' : 'Gửi'}</Text>
          </TouchableOpacity>
        </View>
      </CornerFrame>
    </View>
  );
};

const PvpIncomingPrompt: React.FC<PvpIncomingPromptProps> = ({ prompt, onAccept, onDecline }) => {
  const busy = prompt.status === 'starting';
  return (
    <View style={styles.pvpOverlay} pointerEvents="box-none">
      <View style={styles.pvpBackdrop} />
      <CornerFrame style={styles.pvpIncomingFrame} contentStyle={styles.pvpIncomingContent}>
        <Text style={styles.pvpTitle}>Khiêu Chiến</Text>
        <Text style={styles.pvpIncomingText}>
          {prompt.ticket.challengerUsername} muốn thách đấu với bạn.
        </Text>
        <Text style={styles.pvpIncomingMeta}>Cược: {formatPvpStake(prompt.ticket.stake)}</Text>
        <View style={styles.pvpFooter}>
          <TouchableOpacity activeOpacity={0.85} style={styles.pvpButton} onPress={() => onDecline(prompt.ticket)} disabled={busy}>
            <Text style={styles.pvpButtonText}>Từ chối</Text>
          </TouchableOpacity>
          <TouchableOpacity activeOpacity={0.85} style={[styles.pvpButton, styles.pvpButtonPrimary]} onPress={() => onAccept(prompt.ticket)} disabled={busy}>
            <Text style={styles.pvpButtonPrimaryText}>{busy ? 'Đang vào...' : 'Đồng ý'}</Text>
          </TouchableOpacity>
        </View>
      </CornerFrame>
    </View>
  );
};

// ═══════════════════════════════════════════════════════════════════════════
export const HoaLuMapScreen: React.FC<Props> = ({
  mapId,
  roomId,
  roomLabel,
  appearance,
  defeatBlinkToken = 0,
  onBack,
  onLogout,
  onBattle,
  resolveMonsterRoster,
  resolveMonsterBootstrap,
  resolvePvpOpponents,
  resolvePvpBootstrap,
  resolvePvpChallengeApi,
  onAllocateStat,
  onAllocateSkill,
  onToggleEquipment,
  onPreviewEquipmentLoadout,
  onCommitEquipmentLoadout,
  onUseItem,
  onDiscardEquipment,
  onDiscardItem,
  onRepairEquipment,
}) => {
  const sceneConfig = useMemo(
    () => resolveSideScrollMapSceneConfig(mapId, roomId),
    [mapId, roomId],
  );

  if (!sceneConfig) {
    throw new Error(`Missing side-scroll scene config for map '${mapId}' room '${roomId}'.`);
  }

  const activeRoomLabel = roomLabel ?? sceneConfig.roomLabel;
  const mapScale = SCREEN_H / sceneConfig.nativeHeight;
  const mapWidth = Math.round(sceneConfig.nativeWidth * mapScale);
  const mapHeight = SCREEN_H;
  const mapMinX = 0;
  const mapMaxX = mapWidth;
  const groundTileStep = sceneConfig.groundTileWidth - sceneConfig.groundTileOverlap;
  const groundTileLeftOffset = groundTileStep;
  const numGroundTiles = Math.ceil((mapWidth + groundTileLeftOffset) / groundTileStep) + 2;
  const groundTileContactOffset = Math.round(sceneConfig.groundTileHeight * sceneConfig.groundContactRatio);
  const groundStripTop = SCREEN_H
    - SOFTKEY_BAR_HEIGHT
    - (sceneConfig.groundRows * sceneConfig.groundTileHeight)
    + sceneConfig.groundSink;
  const spriteFootSink = Math.round(
    sceneConfig.playerFootSinkSourcePx
    * sceneConfig.playerScale
    / sceneConfig.createCharacterDefaultScale,
  );
  const defaultCharInitX = Math.round(mapWidth * sceneConfig.playerSpawnRatio);
  const [serverPlayerX, setServerPlayerX] = useState<number | null>(null);
  const [serverPlayerFacing, setServerPlayerFacing] = useState<'left' | 'right'>('right');
  const [playerSpawnRevision, setPlayerSpawnRevision] = useState(0);
  const charInitX = Math.round(Math.max(mapMinX, Math.min(mapMaxX, serverPlayerX ?? defaultCharInitX)));
  const surfacesBase = useMemo(
    () => sceneConfig.buildSurfaces(mapScale),
    [mapScale, sceneConfig],
  );
  const primaryGroundY = groundStripTop + groundTileContactOffset + sceneConfig.groundContactVisualDrop;
  const sceneSurfaces = useMemo(() => (
    surfacesBase.map((surface) => (
      surface.id === sceneConfig.primaryGroundSurfaceId
        ? { ...surface, x1: -groundTileLeftOffset, y: primaryGroundY }
        : surface
    ))
  ), [groundTileLeftOffset, primaryGroundY, sceneConfig.primaryGroundSurfaceId, surfacesBase]);
  const groundMainSurface = sceneSurfaces.find((surface) => surface.id === sceneConfig.primaryGroundSurfaceId)
    ?? {
      id: sceneConfig.primaryGroundSurfaceId,
      x1: -groundTileLeftOffset,
      x2: mapWidth,
      y: primaryGroundY,
      kind: 'ground' as const,
    };
  const groundTop = getSurfaceStartY(groundMainSurface);
  const javaCollisionGrid = useMemo(
    () => buildFlatGroundJavaGrid(mapWidth, mapHeight, groundTop),
    [groundTop, mapHeight, mapWidth],
  );

  const scrollRef = useRef<ScrollView>(null);
  const socketClientRef = useRef(SocketClient.getInstance());
  const characterControllerRef = useRef<CharacterControllerRef>(null);
  const defeatBlinkAnim = useRef(new Animated.Value(1)).current;
  const defeatBlinkLoopRef = useRef<Animated.CompositeAnimation | null>(null);
  const defeatBlinkTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const battleTriggered = useRef(false);
  const engagedMonsterIdRef = useRef<string | null>(null);
  const encounterRequestVersionRef = useRef(0);
  const charLeftRef = useRef(charInitX);
  const charFootYRef = useRef(groundTop);
  const playerLastMovedAtRef = useRef(0);
  const localPositionDirtyRef = useRef(false);
  const acceptedServerPositionRef = useRef<number | null>(serverPlayerX);
  const lastMovePersistRef = useRef({ x: charInitX, at: 0 });
  const cameraXRef = useRef(0);
  // Camera scroll is coalesced to 1 scrollTo per vsync via rAF, so 60Hz
  // onMove callbacks from the character controller don't hammer the JS
  // thread with redundant ScrollView updates.
  const pendingScrollXRef = useRef<number | null>(null);
  const scrollRafRef = useRef<number | null>(null);
  const [encounterPreview, setEncounterPreview] = useState<EncounterPreviewState | null>(null);
  const [activeMoveDirection, setActiveMoveDirection] = useState<'left' | 'right' | null>(null);
  const [monsterRoster, setMonsterRoster] = useState<MapMonsterRosterEntry[]>([]);
  const [monsterRuntimeVersion, setMonsterRuntimeVersion] = useState(0);
  const [defeatRecoveryActive, setDefeatRecoveryActive] = useState(false);
  const defeatRecoveryActiveRef = useRef(false);

  // ── Monster runtime (stable identity, mutated in place) ─────────────────
  const monsterRuntimesRef = useRef<MonsterRuntime[]>([]);
  const monsterTargetsRef = useRef<MonsterTarget[]>([]);
  const [monsterVisuals, setMonsterVisuals] = useState<MonsterVisual[]>([]);
  const monsterRuntimes = monsterRuntimesRef.current;

  useEffect(() => {
    const client = socketClientRef.current;

    const applyServerPlayerState = (state: PlayerMapState | undefined) => {
      const playerX = state?.x;
      if (typeof playerX !== 'number' || playerX <= 0) {
        setServerPlayerX(null);
        setServerPlayerFacing('right');
        return;
      }

      const displayPlayerX = Math.max(mapMinX, Math.min(mapMaxX, Math.round(playerX * mapScale)));
      const localX = charLeftRef.current;
      const playerRecentlyMoved = getLoopNowMs() - playerLastMovedAtRef.current <= PLAYER_ALERT_MEMORY_MS;
      const serverPositionChanged = acceptedServerPositionRef.current === null
        || Math.abs(displayPlayerX - acceptedServerPositionRef.current) >= 1;

      // Java client keeps the local map actor authoritative during immediate movement/jump
      // and only accepts server position packets as spawn/teleport reconciliation.
      // Source: reverse-engineered map actor behavior from reference/redecoded/decompiled/mh.java
      // setfall path + current RN bug trace where playerMapState echo reset initialX mid-jump.
      if (localPositionDirtyRef.current && playerRecentlyMoved && Math.abs(displayPlayerX - localX) > 1) {
        setServerPlayerFacing(state?.direction === 0 ? 'left' : 'right');
        return;
      }

      acceptedServerPositionRef.current = displayPlayerX;
      localPositionDirtyRef.current = false;
      if (serverPositionChanged) {
        setPlayerSpawnRevision((revision) => revision + 1);
      }
      setServerPlayerX(displayPlayerX);
      setServerPlayerFacing(state?.direction === 0 ? 'left' : 'right');
    };

    const handleMapInfo = (info: MapInfo) => {
      if (info.name !== mapId || info.roomId !== roomId) {
        return;
      }

      applyServerPlayerState(info.playerWorldState
        ? {
            mapId: info.name,
            roomId: info.roomId,
            ...info.playerWorldState,
          }
        : undefined);
    };

    const handlePlayerMapState = (state: PlayerMapState) => {
      if (state.mapId !== mapId || state.roomId !== roomId) {
        return;
      }

      applyServerPlayerState(state);
    };

    client.on('mapInfo', handleMapInfo);
    client.on('playerMapState', handlePlayerMapState);
    return () => {
      client.off('mapInfo', handleMapInfo);
      client.off('playerMapState', handlePlayerMapState);
    };
  }, [mapId, mapMaxX, mapMinX, mapScale, roomId]);

  useEffect(() => {
    let cancelled = false;

    const resolver = resolveMonsterRoster;
    if (!resolver) {
      setMonsterRoster([]);
      return () => {
        cancelled = true;
      };
    }

    void Promise.resolve(resolver({ mapId, roomId }))
      .then((response) => {
        if (cancelled) return;
        setMonsterRoster(response?.encounters ?? []);
      })
      .catch(() => {
        if (cancelled) return;
        setMonsterRoster([]);
      });

    return () => {
      cancelled = true;
    };
  }, [mapId, roomId, resolveMonsterRoster]);

  useEffect(() => {
    const client = socketClientRef.current;
    const request = { mapId, roomId };

    const handleRosterPacket = (packet: MapMonsterRosterPacket) => {
      if (packet.mapId !== mapId || packet.roomId !== roomId) {
        return;
      }

      setMonsterRoster((current) => applyMapMonsterRuntimePacket(current, request, packet));
    };

    client.on('mapMonsterRoster', handleRosterPacket);
    return () => {
      client.off('mapMonsterRoster', handleRosterPacket);
    };
  }, [mapId, roomId]);

  useEffect(() => {
    const reconciled = reconcileMonsterRuntimes(
      monsterRuntimesRef.current,
      monsterTargetsRef.current,
      monsterRoster,
      sceneSurfaces,
    );
    const nextRuntimes = reconciled.runtimes;
    monsterRuntimesRef.current = nextRuntimes;
    monsterTargetsRef.current = reconciled.targets;
    setMonsterVisuals(buildInitialVisuals(nextRuntimes));
    engagedMonsterIdRef.current = encounterPreview?.monsterKey ?? null;
    battleTriggered.current = false;
    setMonsterRuntimeVersion((version) => version + 1);
  }, [encounterPreview?.monsterKey, monsterRoster, sceneSurfaces]);

  // ── Menu state ──────────────────────────────────────────────────────────
  const [menuVisible, setMenuVisible] = useState(false);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);
  const [menuSelectSignal, setMenuSelectSignal] = useState(0);
  const [activeCharacterDialog, setActiveCharacterDialog] = useState<MapCharacterDialogKind | null>(null);
  const [activePvpDialog, setActivePvpDialog] = useState<PvpDialogMode | null>(null);
  const [pvpOpponents, setPvpOpponents] = useState<PvpOpponentEntry[]>([]);
  const [pvpStatus, setPvpStatus] = useState<PvpDialogStatus>('idle');
  const [pvpError, setPvpError] = useState<string | null>(null);
  const [pvpTarget, setPvpTarget] = useState('');
  const [pvpStakeThousands, setPvpStakeThousands] = useState('0');
  const [pvpAllowSpectators, setPvpAllowSpectators] = useState(true);
  const [pvpOneWay, setPvpOneWay] = useState(false);
  const [pvpDisableSpecialSkills, setPvpDisableSpecialSkills] = useState(false);
  const [pvpIncomingPrompt, setPvpIncomingPrompt] = useState<PvpIncomingPromptState | null>(null);
  const [pvpPendingTicketId, setPvpPendingTicketId] = useState<string | null>(null);

  const handleLogout = useCallback(async () => {
    await clearSession();
    onLogout();
  }, [onLogout]);

  const loadPvpOpponents = useCallback((mode: PvpDialogMode = activePvpDialog ?? 'challenge') => {
    const username = appearance.username?.trim();
    if (!username) {
      setPvpOpponents([]);
      setPvpStatus('error');
      setPvpError('Thiếu tên nhân vật');
      return;
    }

    if (!resolvePvpOpponents) {
      setPvpOpponents([]);
      setPvpStatus('error');
      setPvpError('Chưa có resolver Lôi Đài');
      return;
    }

    setPvpStatus('loading');
    setPvpError(null);
    void Promise.resolve(resolvePvpOpponents({ username, registerPresence: mode === 'arena' }))
      .then((response) => {
        const opponents = response?.opponents ?? [];
        setPvpOpponents(opponents);
        setPvpStatus('ready');
        setPvpTarget((current) => current.trim() || (opponents[0]?.username ?? ''));
      })
      .catch(() => {
        setPvpOpponents([]);
        setPvpStatus('error');
        setPvpError('Không tải được danh sách');
      });
  }, [activePvpDialog, appearance.username, resolvePvpOpponents]);

  const openPvpDialog = useCallback((mode: PvpDialogMode, target = '') => {
    setMenuVisible(false);
    setActiveCharacterDialog(null);
    setActivePvpDialog(mode);
    setPvpError(null);
    if (mode === 'challenge') {
      setPvpTarget(target);
    } else if (target) {
      setPvpTarget(target);
    }
    loadPvpOpponents(mode);
  }, [loadPvpOpponents]);

  const startPvpBattle = useCallback((target: string, options: PvpStartOptions) => {
    const username = appearance.username?.trim();
    const targetUsername = target.trim();
    if (!username) {
      setPvpStatus('error');
      setPvpError('Thiếu tên nhân vật');
      return;
    }
    if (!targetUsername) {
      setPvpStatus('error');
      setPvpError('Chưa nhập nick');
      return;
    }
    if (targetUsername.toLowerCase() === username.toLowerCase()) {
      setPvpStatus('error');
      setPvpError('Không thể khiêu chiến chính mình');
      return;
    }
    if (!resolvePvpChallengeApi || !onBattle) {
      setPvpStatus('error');
      setPvpError('Chưa có resolver khiêu chiến');
      return;
    }

    const sameRoomCandidate = pvpOpponents.find(
      (opponent) => opponent.username.toLowerCase() === targetUsername.toLowerCase(),
    );
    if (!sameRoomCandidate) {
      setPvpStatus('error');
      setPvpError('Đối thủ không ở cùng khu/phòng hoặc đang bận');
      return;
    }

    setPvpStatus('starting');
    setPvpError(null);
    void Promise.resolve(resolvePvpChallengeApi.create({
      username,
      targetUsername,
      stake: options.stake,
    }))
      .then((ticket) => {
        if (!ticket) {
          setPvpStatus('error');
          setPvpError('Không gửi được lời khiêu chiến');
          return;
        }

        setPvpPendingTicketId(ticket.ticketId);
        setPvpStatus('ready');
        setPvpError('Đã gửi lời mời, đang chờ đối thủ đồng ý...');
      })
      .catch(() => {
        setPvpStatus('error');
        setPvpError('Không gửi được lời khiêu chiến');
      });
  }, [appearance.username, onBattle, pvpOpponents, resolvePvpChallengeApi]);

  const handlePvpAcceptedBootstrap = useCallback((bootstrap: MonsterBattleBootstrapResponse) => {
    if (!onBattle) return;
    setActivePvpDialog(null);
    setPvpIncomingPrompt(null);
    setPvpPendingTicketId(null);
    setPvpStatus('idle');
    setPvpError(null);
    onBattle('fire', bootstrap.initialTurnSide === 'enemy' ? 'monster' : 'player', bootstrap);
  }, [onBattle]);

  const acceptPvpChallenge = useCallback((ticket: PvpChallengeTicket) => {
    const username = appearance.username?.trim();
    if (!username || !resolvePvpChallengeApi) return;
    setPvpIncomingPrompt({ ticket, status: 'starting' });
    void resolvePvpChallengeApi.accept({ ticketId: ticket.ticketId, username })
      .then((response) => {
        if (response?.bootstrap) {
          handlePvpAcceptedBootstrap(response.bootstrap);
          return;
        }
        setPvpIncomingPrompt(null);
      })
      .catch(() => setPvpIncomingPrompt(null));
  }, [appearance.username, handlePvpAcceptedBootstrap, resolvePvpChallengeApi]);

  const declinePvpChallenge = useCallback((ticket: PvpChallengeTicket) => {
    const username = appearance.username?.trim();
    setPvpIncomingPrompt(null);
    if (!username || !resolvePvpChallengeApi) return;
    void resolvePvpChallengeApi.decline({ ticketId: ticket.ticketId, username });
  }, [appearance.username, resolvePvpChallengeApi]);

  useEffect(() => {
    const username = appearance.username?.trim();
    if (!username || !resolvePvpChallengeApi || !onBattle) return;
    let cancelled = false;
    const poll = () => {
      void resolvePvpChallengeApi.list(username).then((inbox) => {
        if (cancelled) return;
        const incoming = inbox?.incoming.find(ticket => ticket.state.toLowerCase() === 'pending') ?? null;
        if (incoming && !pvpIncomingPrompt) {
          setPvpIncomingPrompt({ ticket: incoming, status: 'pending' });
        }
      });
      if (pvpPendingTicketId) {
        void resolvePvpChallengeApi.status(pvpPendingTicketId, username).then((status) => {
          if (cancelled || !status) return;
          if (status.bootstrap) {
            handlePvpAcceptedBootstrap(status.bootstrap);
          } else if (!status.ticket.state.toLowerCase().includes('pending')) {
            setPvpPendingTicketId(null);
            setPvpError(status.ticket.state === 'Declined' ? 'Đối thủ đã từ chối' : 'Lời mời đã hết hiệu lực');
          }
        });
      }
    };
    poll();
    const timer = setInterval(poll, 1200);
    return () => {
      cancelled = true;
      clearInterval(timer);
    };
  }, [appearance.username, handlePvpAcceptedBootstrap, onBattle, pvpIncomingPrompt, pvpPendingTicketId, resolvePvpChallengeApi]);

  const menuItems = useMemo(
    () => createMapGameMenuItems({
      onLogout: handleLogout,
      onOpenArena: () => openPvpDialog('arena'),
      onOpenChallenge: () => openPvpDialog('challenge'),
      onOpenCharacterInfo: () => setActiveCharacterDialog('info'),
      onOpenPotential: () => setActiveCharacterDialog('potential'),
      onOpenSkills: () => setActiveCharacterDialog('skills'),
      onOpenEquipment: () => setActiveCharacterDialog('equipment'),
      onOpenInventory: () => setActiveCharacterDialog('inventory'),
    }),
    [handleLogout, openPvpDialog],
  );

  const isEncounterActive = encounterPreview !== null;
  const isCharacterDialogActive = activeCharacterDialog !== null;
  const isPvpDialogActive = activePvpDialog !== null;
  const isPvpPromptActive = pvpIncomingPrompt !== null;
  const showTouchGamepad = !menuVisible && !isEncounterActive && !defeatRecoveryActive && !isCharacterDialogActive && !isPvpDialogActive && !isPvpPromptActive;
  const allowMapPointerInput = Platform.OS !== 'web' && !defeatRecoveryActive && !isCharacterDialogActive && !isPvpDialogActive && !isPvpPromptActive;
  const playerSpriteSize = useMemo(
    () => {
      // anchorToBody=true: groundOffset = maxBelowBody * CHAR_SCALE
      // Đảm bảo body bottom (y=53 trong sprite) chạm đúng GROUND_MAIN_Y bất kể overlay.
      //
      // + SPRITE_FOOT_SINK = 4px: bù phần canvas kéo dài dưới chân (transparent).
      // Lý do: CreateCharacterScreen đặt canvas_bottom 5px dưới stone_surface tại scale 2.2
      //   → transparent_at_bottom = 5/2.2 * CHAR_SCALE ≈ 4px
      //   → charTop giảm thêm 4px → canvas_bottom = groundY+4 → feet = groundY ✓
      //
      // Chứng minh tổng hợp:
      //   charTop = groundY - (maxAbove+53+maxBelow)*scale + maxBelow*scale + SINK
      //           = groundY - (maxAbove+53)*scale + SINK
      //   feet_y  = charTop + (maxAbove+53)*scale - transparent_display
      //           = groundY + SINK - SINK = groundY ✓
      const measured = measureCharacterRenderer(appearance, sceneConfig.playerScale, true);
      return { ...measured, groundOffset: measured.groundOffset + spriteFootSink };
    },
    [appearance, sceneConfig.playerScale, spriteFootSink],
  );
  const hudHp = appearance.hp?.cur ?? 800;
  const hudMaxHp = appearance.hp?.max ?? 1000;
  const hudExpPercent = appearance.exp?.cur ?? 45;
  const playerLevel = appearance.level ?? 1;

  useEffect(() => {
    if (defeatBlinkToken <= 0) {
      return;
    }

    defeatRecoveryActiveRef.current = true;
    setDefeatRecoveryActive(true);
    setMenuVisible(false);
    setActiveMoveDirection(null);
    encounterRequestVersionRef.current += 1;
    engagedMonsterIdRef.current = null;
    battleTriggered.current = false;
    setEncounterPreview(null);
    for (const monster of monsterRuntimesRef.current) {
      monster.engageQueued = false;
      monster.worldState = 'patrol';
    }
    characterControllerRef.current?.stopMove();
    defeatBlinkLoopRef.current?.stop();
    if (defeatBlinkTimerRef.current) {
      clearTimeout(defeatBlinkTimerRef.current);
      defeatBlinkTimerRef.current = null;
    }

    defeatBlinkAnim.setValue(1);
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(defeatBlinkAnim, {
          toValue: 0.18,
          duration: 110,
          useNativeDriver: true,
        }),
        Animated.timing(defeatBlinkAnim, {
          toValue: 1,
          duration: 110,
          useNativeDriver: true,
        }),
      ]),
    );
    defeatBlinkLoopRef.current = loop;
    loop.start();

    defeatBlinkTimerRef.current = setTimeout(() => {
      defeatBlinkLoopRef.current?.stop();
      defeatBlinkLoopRef.current = null;
      defeatBlinkAnim.setValue(1);
      defeatRecoveryActiveRef.current = false;
      setDefeatRecoveryActive(false);
      defeatBlinkTimerRef.current = null;
    }, BATTLE_RECOVERY_MS);

    return () => {
      defeatBlinkLoopRef.current?.stop();
      defeatBlinkLoopRef.current = null;
      if (defeatBlinkTimerRef.current) {
        clearTimeout(defeatBlinkTimerRef.current);
        defeatBlinkTimerRef.current = null;
      }
      defeatBlinkAnim.setValue(1);
      defeatRecoveryActiveRef.current = false;
      setDefeatRecoveryActive(false);
    };
  }, [defeatBlinkAnim, defeatBlinkToken]);

  const scrollToCharacter = useCallback((charLeft: number) => {
    const maxScrollX = Math.max(0, mapWidth - SCREEN_W);
    const camTarget = charLeft + playerSpriteSize.w / 2 - SCREEN_W / 2;
    const nextScrollX = Math.max(0, Math.min(maxScrollX, camTarget));

    // Skip no-op updates (character hugging a map boundary).
    if (nextScrollX === cameraXRef.current && pendingScrollXRef.current === null) {
      return;
    }

    pendingScrollXRef.current = nextScrollX;

    if (scrollRafRef.current !== null) return;
    scrollRafRef.current = requestAnimationFrame(() => {
      scrollRafRef.current = null;
      const target = pendingScrollXRef.current;
      pendingScrollXRef.current = null;
      if (target === null) return;
      cameraXRef.current = target;
      scrollRef.current?.scrollTo({ x: target, animated: false });
    });
  }, [mapWidth, playerSpriteSize.w]);

  const persistPlayerWorldPosition = useCallback((
    x: number,
    facing: 'left' | 'right',
    force = false,
  ) => {
    const now = getLoopNowMs();
    const last = lastMovePersistRef.current;
    if (!force && now - last.at < 750 && Math.abs(x - last.x) < 24) {
      return;
    }

    const displayX = Math.max(mapMinX, Math.min(mapMaxX, Math.round(x)));
    const nativeX = Math.round(displayX / mapScale);
    const nativeY = Math.round(groundTop / mapScale);
    lastMovePersistRef.current = { x: displayX, at: now };
    // Mark the outgoing local position as accepted before the socket echo arrives.
    // Without this, the echoed playerMapState can look like a new spawn revision
    // and remount/reset the Java-compatible controller while the actor is jumping.
    // Source: Java map actor is locally authoritative during km.java state 5/6;
    // server packets are spawn/teleport reconciliation, not per-frame physics authority.
    acceptedServerPositionRef.current = displayX;
    socketClientRef.current.move(
      nativeX,
      nativeY,
      mapId,
      roomId,
      facing,
      0,
    );
  }, [groundTop, mapId, mapMaxX, mapMinX, mapScale, roomId]);

  // Cancel any pending scroll rAF on unmount to avoid leaks.
  useEffect(() => () => {
    if (scrollRafRef.current !== null) {
      cancelAnimationFrame(scrollRafRef.current);
      scrollRafRef.current = null;
    }
  }, []);

  // Stable array identity — CharacterController's useEffect for this prop
  // fires only on mount; contents are mutated live by the game loop.
  const monsterTargets = monsterTargetsRef.current;

  const startEncounter = useCallback((snap: {
    type: MonsterType;
    monsterKey: string;
    displayName: string;
    displayLevel: number;
    iqValue: number;
    visualTypeByte: number;
    sharedSheetFamily: MonsterSharedSheetFamily;
    nameColorMode: number;
    monsterFrameIndex: number;
    monsterFacingRight: boolean;
    monsterWorldState: 'patrol' | 'alert' | 'engaging';
    x: number;
    groundY: number;
    initialTurn: 'player' | 'monster';
  }) => {
    if (defeatRecoveryActiveRef.current || battleTriggered.current || isEncounterActive) return;

    const { w: monsterW } = monsterDisplaySize(snap.type);
    const requestVersion = encounterRequestVersionRef.current + 1;
    encounterRequestVersionRef.current = requestVersion;

    characterControllerRef.current?.stopMove();
    characterControllerRef.current?.face(snap.x >= charLeftRef.current ? 'right' : 'left');
    setActiveMoveDirection(null);
    engagedMonsterIdRef.current = snap.monsterKey;
    battleTriggered.current = true;
    setEncounterPreview({
      monsterType: snap.type,
      monsterKey: snap.monsterKey,
      displayName: snap.displayName,
      displayLevel: snap.displayLevel,
      iqValue: snap.iqValue,
      visualTypeByte: snap.visualTypeByte,
      sharedSheetFamily: snap.sharedSheetFamily,
      nameColorMode: snap.nameColorMode,
      monsterFrameIndex: snap.monsterFrameIndex,
      monsterFacingRight: snap.monsterFacingRight,
      monsterWorldState: snap.monsterWorldState,
      playerLeft: charLeftRef.current - cameraXRef.current,
      monsterLeft: snap.x - monsterW / 2 - cameraXRef.current,
      groundY: snap.groundY,
      initialTurn: snap.initialTurn,
      bootstrapStatus: 'loading',
      monsterBootstrap: null,
    });

    const bootstrapResolver = resolveMonsterBootstrap;
    if (!bootstrapResolver) {
      setEncounterPreview((current) => {
        if (!current || encounterRequestVersionRef.current !== requestVersion) {
          return current;
        }

        return {
          ...current,
          bootstrapStatus: 'error',
        };
      });
      return;
    }

    void Promise.resolve(bootstrapResolver({
      mapId,
      roomId,
      monsterKey: snap.monsterKey,
      initialTurnSide: snap.initialTurn === 'monster' ? 'enemy' : 'player',
    }))
      .then((monsterBootstrap) => {
        setEncounterPreview((current) => {
          if (!current || encounterRequestVersionRef.current !== requestVersion) {
            return current;
          }

          return {
            ...current,
            bootstrapStatus: monsterBootstrap ? 'ready' : 'error',
            monsterBootstrap,
          };
        });
      })
      .catch(() => {
        setEncounterPreview((current) => {
          if (!current || encounterRequestVersionRef.current !== requestVersion) {
            return current;
          }

          return {
            ...current,
            bootstrapStatus: 'error',
          };
        });
      });
  }, [isEncounterActive, mapId, resolveMonsterBootstrap, roomId]);

  const confirmEncounter = useCallback(() => {
    if (!encounterPreview || !encounterPreview.monsterBootstrap || !onBattle) return;
    onBattle(
      encounterPreview.monsterType,
      encounterPreview.initialTurn,
      encounterPreview.monsterBootstrap,
    );
  }, [encounterPreview, onBattle]);

  const cancelEncounter = useCallback(() => {
    encounterRequestVersionRef.current += 1;
    engagedMonsterIdRef.current = null;
    setEncounterPreview(null);
    battleTriggered.current = false;
  }, []);

  // ── Game loop: rAF + accumulator (logic 20Hz, vsync-aligned) ────────────
  // Mutates monster runtimes in place. Position goes through Animated.Value
  // (native thread), so 20Hz logic no longer causes 20Hz React reconciles.
  // A setState is only dispatched when a visual field (frame/direction/
  // attacking) actually flips — typically every ~200ms.
  useEffect(() => {
    if (isEncounterActive) return;

    let rafId: number | null = null;
    let lastTime = 0;
    let accumulator = 0;
    let cancelled = false;

    const step = (now: number) => {
      if (cancelled) return;
      if (lastTime === 0) lastTime = now;
      const dt = Math.min(now - lastTime, 200); // clamp catch-up bursts
      lastTime = now;
      accumulator += dt;

      let visualsDirty = false;

      while (accumulator >= MONSTER_TICK_MS) {
        accumulator -= MONSTER_TICK_MS;
        const playerRecentlyMoved = getLoopNowMs() - playerLastMovedAtRef.current <= PLAYER_ALERT_MEMORY_MS;
        const playerLeft = charLeftRef.current;
        const lockedMonsterId = engagedMonsterIdRef.current;

        for (let i = 0; i < monsterRuntimes.length; i++) {
          const m = monsterRuntimes[i];
          const canOwnEncounter = !defeatRecoveryActiveRef.current && (lockedMonsterId === null || lockedMonsterId === m.id);
          const collidesWithPlayer = canOwnEncounter &&
            playerRecentlyMoved &&
            hasMonsterCollision(playerLeft, playerSpriteSize.w, charFootYRef.current, m.x, m.groundY, m.size.w, m.size.h);
          const nextWorldState: MonsterRuntime['worldState'] = collidesWithPlayer ? 'engaging' : 'patrol';

          // 1. Move
          let newX = m.x + m.roster.moveSpeed * m.direction;
          let newDir: 1 | -1 = m.direction;
          if (newX >= m.maxX) { newX = m.maxX; newDir = -1; }
          if (newX <= m.minX) { newX = m.minX; newDir = 1; }

          const collisionTriggeredEncounter = collidesWithPlayer;
          if (collisionTriggeredEncounter && !battleTriggered.current && engagedMonsterIdRef.current === null) {
            engagedMonsterIdRef.current = m.id;
          }

          if (
            collisionTriggeredEncounter &&
            !battleTriggered.current &&
            engagedMonsterIdRef.current === m.id &&
            !m.engageQueued
          ) {
            m.engageQueued = true;
            const snap = {
              type: m.type,
              monsterKey: m.roster.monsterKey,
              displayName: m.roster.displayName,
              displayLevel: m.roster.displayLevel,
              iqValue: m.roster.iqValue,
              visualTypeByte: m.roster.visualTypeByte,
              sharedSheetFamily: m.roster.sharedSheetFamily,
              nameColorMode: m.roster.nameColorMode,
              monsterFrameIndex: m.frameIndex,
              monsterFacingRight: newDir === 1,
              monsterWorldState: nextWorldState,
              x: newX,
              groundY: m.groundY,
              initialTurn: 'monster' as const,
            };
            setTimeout(() => {
              if (
                defeatRecoveryActiveRef.current ||
                battleTriggered.current ||
                !m.engageQueued ||
                engagedMonsterIdRef.current !== m.id
              ) {
                return;
              }

              startEncounter(snap);
            }, ENGAGE_TRIGGER_DELAY_MS);
          } else if (!collisionTriggeredEncounter) {
            m.engageQueued = false;
          }

          // 3. Advance frame
          const newTick   = m.tickCount + 1;
          const frames    = WALK_FRAMES;
          const stepIdx   = Math.floor(newTick / FRAME_TICKS) % frames.length;
          const nextFrame = frames[stepIdx];

          // 4. Detect visual diff BEFORE mutating
          if (
            m.frameIndex !== nextFrame ||
            m.direction !== newDir ||
            m.attacking !== false ||
            m.worldState !== nextWorldState
          ) {
            visualsDirty = true;
          }

          // 5. Commit to runtime (mutate in place)
          m.x          = newX;
          m.direction  = newDir;
          m.attacking  = false;
          m.worldState = nextWorldState;
          m.aggroTicks = 0;
          m.tickCount  = newTick;
          m.frameIndex = nextFrame;

          // 6. Drive visuals natively (no React render needed)
          const leftX = newX - m.size.w / 2;
          m.xAnim.setValue(leftX);

          // 7. Mutate the stable target array read by CharacterController
          const target = monsterTargetsRef.current[i];
          target.x = leftX;
          target.y = m.topY;
        }
      }

      if (visualsDirty) {
        // Rebuild the small visuals array (N=3). This triggers ONE
        // re-render of MonsterField only — wrapper chain above is memoized.
        setMonsterVisuals(monsterRuntimes.map(m => ({
          id: m.id,
          frameIndex: m.frameIndex,
          direction: m.direction,
          attacking: m.attacking,
          worldState: m.worldState,
        })));
      }

      rafId = requestAnimationFrame(step);
    };

    rafId = requestAnimationFrame(step);

    return () => {
      cancelled = true;
      if (rafId !== null) cancelAnimationFrame(rafId);
    };
  }, [isEncounterActive, monsterRuntimeVersion, playerSpriteSize.w, startEncounter]);

  useEffect(() => {
    charLeftRef.current = charInitX;
    charFootYRef.current = groundTop;
    scrollToCharacter(charInitX);
  }, [charInitX, playerSpawnRevision, scrollToCharacter]);

  useEffect(() => {
    if (showTouchGamepad) return;
    characterControllerRef.current?.stopMove();
    setActiveMoveDirection(null);
  }, [showTouchGamepad]);

  const handleGamepadMoveStart = useCallback((direction: 'left' | 'right') => {
    playerLastMovedAtRef.current = getLoopNowMs();
    setActiveMoveDirection(direction);
    characterControllerRef.current?.startMove(direction);
  }, []);

  const handleGamepadMoveStop = useCallback(() => {
    setActiveMoveDirection(null);
    characterControllerRef.current?.stopMove();
  }, []);

  const handleGamepadJump = useCallback(() => {
    const jumpDirection = activeMoveDirection ?? 'up';
    characterControllerRef.current?.jump(jumpDirection);
  }, [activeMoveDirection]);

  const handleGamepadAttack = useCallback(() => {
    characterControllerRef.current?.attack();
  }, []);

  const handleGamepadDown = useCallback(() => {
    // Reserved for ladder / contextual down actions.
  }, []);

  useEffect(() => {
    if (Platform.OS !== 'web' || !showTouchGamepad || typeof window === 'undefined') {
      return;
    }

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.repeat) return;

      if (event.key === 'ArrowLeft') {
        event.preventDefault();
        handleGamepadMoveStart('left');
        return;
      }

      if (event.key === 'ArrowRight') {
        event.preventDefault();
        handleGamepadMoveStart('right');
        return;
      }

      if (event.key === 'ArrowUp' || event.key === ' ' || event.code === 'Space') {
        event.preventDefault();
        handleGamepadJump();
        return;
      }

      if (event.key.toLowerCase() === 'a' || event.key === 'Enter') {
        event.preventDefault();
        handleGamepadAttack();
      }
    };

    const handleKeyUp = (event: KeyboardEvent) => {
      if (event.key === 'ArrowLeft' || event.key === 'ArrowRight') {
        event.preventDefault();
        handleGamepadMoveStop();
      }
    };

    window.addEventListener('keydown', handleKeyDown);
    window.addEventListener('keyup', handleKeyUp);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
      window.removeEventListener('keyup', handleKeyUp);
    };
  }, [
    handleGamepadAttack,
    handleGamepadJump,
    handleGamepadMoveStart,
    handleGamepadMoveStop,
    showTouchGamepad,
  ]);

  // ── Render: stone platform ───────────────────────────────────────────────
  // Tile strip bắt đầu từ -GROUND_TILE_LEFT_OFFSET (1 tile sang TRÁI khỏi x=0)
  // → nhân vật ở gần đầu map không nhìn thấy edge tile → không cảm giác "rớt khỏi map"
  const renderGround = () => {
    const tiles = [];
    for (let row = 0; row < sceneConfig.groundRows; row++) {
      for (let col = 0; col < numGroundTiles; col++) {
        const tileLeft = col * groundTileStep - groundTileLeftOffset;
        // Edge tiles chỉ dùng cho tile đầu/cuối thực sự ngoài phạm vi map
        const isFirst = tileLeft <= -groundTileLeftOffset;
        const isLast  = col === numGroundTiles - 1;
        const source  = isFirst
          ? sceneConfig.assets.groundLeft
          : isLast
            ? sceneConfig.assets.groundRight
            : sceneConfig.assets.groundCenter;

        tiles.push(
          <Image
            key={`t-${row}-${col}`}
            source={source}
            style={{
              position: 'absolute',
              left:   tileLeft,
              top:    groundStripTop + row * sceneConfig.groundTileHeight,
              width:  sceneConfig.groundTileWidth,
              height: sceneConfig.groundTileHeight,
              zIndex: LAYER_GROUND,
            }}
            resizeMode="stretch"
          />
        );
      }
    }
    return tiles;
  };

  // ═══════════════════════════════════════════════════════════════════════
  return (
    <View style={styles.root}>

      {/* ─── MapHUD: HP / EXP bars + Zone name (top-left) ─── */}
      <MapHUD
        hp={hudHp}
        maxHp={hudMaxHp}
        expPercent={hudExpPercent}
        zoneName={activeRoomLabel}
        width={SCREEN_W}
      />

      {/* ─── Scrollable map ─── */}
      <ScrollView
        ref={scrollRef}
        horizontal
        scrollEnabled={false}
        showsHorizontalScrollIndicator={false}
        scrollEventThrottle={16}
        style={styles.scroll}
        contentContainerStyle={{ width: mapWidth, height: mapHeight }}
        keyboardShouldPersistTaps="handled"
      >
        <View style={{ width: mapWidth, height: mapHeight }}>
          {/* Layer 0: Background */}
          <Image
            source={sceneConfig.assets.background}
            style={[styles.bg, { width: mapWidth, height: mapHeight }]}
            resizeMode="stretch"
          />

          {/* Layer 1: Đất */}
          {renderGround()}

          {/* Layer 2: Quái vật */}
          {!isEncounterActive && (
            <MonsterField
              runtimes={monsterRuntimes}
              visuals={monsterVisuals}
            />
          )}

          {/* Layer 3: Nhân vật */}
          {!isEncounterActive && (
            <JavaCompatibleCharacterController
              ref={characterControllerRef}
              initialX={charInitX}
              positionRevision={playerSpawnRevision}
              initialFacing={serverPlayerFacing}
              groundY={groundTop}
              controlMode="tap-to-move"
              speed={appearance.mapMovement?.moveSpeed ?? sceneConfig.playerSpeed}
              jumpSpeed={appearance.mapMovement?.jumpSpeed}
              scale={sceneConfig.playerScale}
              spriteSize={playerSpriteSize}
              renderSprite={({ action, actionFrameIndex, facing, scale, poseFamilySlot, poseFrameIndex }) => (
                <Animated.View style={{ opacity: defeatRecoveryActive ? defeatBlinkAnim : 1 }}>
                  <CharacterRenderer
                    appearance={appearance}
                    scale={scale}
                    anchorToBody
                    action={action}
                    actionFrameIndex={actionFrameIndex}
                    facing={facing}
                    poseFamilySlotOverride={poseFamilySlot}
                    poseFrameIndexOverride={poseFrameIndex}
                  />
                </Animated.View>
              )}
              level={playerLevel}
              monsters={monsterTargets}
              surfaces={sceneSurfaces}
              collisionGrid={undefined}
              // Hoa Lư hiện có nhiều platform authored bằng GroundSurface.
              // Không truyền flat-ground grid tạm vào Java controller vì grid này
              // chỉ có mặt đất chính, làm jump/fall bỏ qua platform và snap về nền.
              // Nguồn Java: km.java/kf.java dùng collision grid đầy đủ; khi chưa có
              // kf.d thật thì Surface adapter đáng tin hơn flat grid một hàng.
              // Cho phép đi sát 2 đầu map nhưng vẫn bị clamp trong biên map,
              // nên ở điểm đầu/cuối sẽ không bị hụt support rồi rơi xuống.
              minX={mapMinX}
              maxX={mapMaxX}
              containerWidth={mapWidth}
              containerHeight={mapHeight}
              zIndex={LAYER_CHARACTER}
              allowPointerInput={allowMapPointerInput}
              disabled={menuVisible || defeatRecoveryActive || isCharacterDialogActive}
              onMove={(x, facing, footY) => {
                playerLastMovedAtRef.current = getLoopNowMs();
                localPositionDirtyRef.current = true;
                charLeftRef.current = x;
                charFootYRef.current = footY ?? groundTop;
                scrollToCharacter(x);
                persistPlayerWorldPosition(x, facing, false);
              }}
              onMoveEnd={(x, facing, footY) => {
                localPositionDirtyRef.current = true;
                charLeftRef.current = x;
                charFootYRef.current = footY ?? groundTop;
                scrollToCharacter(x);
                persistPlayerWorldPosition(x, facing, true);
              }}
              onAttackMonster={(monsterId) => {
                if (defeatRecoveryActive || battleTriggered.current) return;

                const targetMonster = monsterRuntimes.find((m) => m.id === monsterId);
                if (!targetMonster) return;
                engagedMonsterIdRef.current = monsterId;
                targetMonster.engageQueued = true;

                const snap = {
                  type: targetMonster.type,
                  monsterKey: targetMonster.roster.monsterKey,
                  displayName: targetMonster.roster.displayName,
                  displayLevel: targetMonster.roster.displayLevel,
                  iqValue: targetMonster.roster.iqValue,
                  visualTypeByte: targetMonster.roster.visualTypeByte,
                  sharedSheetFamily: targetMonster.roster.sharedSheetFamily,
                  nameColorMode: targetMonster.roster.nameColorMode,
                  monsterFrameIndex: targetMonster.frameIndex,
                  monsterFacingRight: targetMonster.direction === 1,
                  monsterWorldState: targetMonster.worldState,
                  x: targetMonster.x,
                  groundY: targetMonster.groundY,
                  initialTurn: 'player' as const,
                };
                setTimeout(() => startEncounter(snap), 180);
              }}
            />
          )}
        </View>
      </ScrollView>

      <TouchGamepad
        visible={showTouchGamepad}
        onMoveStart={handleGamepadMoveStart}
        onMoveStop={handleGamepadMoveStop}
        onAttack={handleGamepadAttack}
        onUpPress={handleGamepadJump}
        onDownPress={handleGamepadDown}
      />

      <MapCharacterDialogs
        activeDialog={activeCharacterDialog}
        appearance={appearance}
        onClose={() => setActiveCharacterDialog(null)}
        onAllocateStat={onAllocateStat}
        onAllocateSkill={onAllocateSkill}
        onToggleEquipment={onToggleEquipment}
        onPreviewEquipmentLoadout={onPreviewEquipmentLoadout}
        onCommitEquipmentLoadout={onCommitEquipmentLoadout}
        onUseItem={onUseItem}
        onDiscardEquipment={onDiscardEquipment}
        onDiscardItem={onDiscardItem}
        onRepairEquipment={onRepairEquipment}
      />

      {activePvpDialog && (
        <PvpDialog
          mode={activePvpDialog}
          opponents={pvpOpponents}
          status={pvpStatus}
          error={pvpError}
          selectedTarget={pvpTarget}
          stakeThousands={pvpStakeThousands}
          allowSpectators={pvpAllowSpectators}
          oneWay={pvpOneWay}
          disableSpecialSkills={pvpDisableSpecialSkills}
          onClose={() => setActivePvpDialog(null)}
          onRefresh={() => loadPvpOpponents(activePvpDialog ?? 'challenge')}
          onSelectTarget={setPvpTarget}
          onStakeThousandsChange={setPvpStakeThousands}
          onAllowSpectatorsChange={setPvpAllowSpectators}
          onOneWayChange={setPvpOneWay}
          onDisableSpecialSkillsChange={setPvpDisableSpecialSkills}
          onStart={startPvpBattle}
        />
      )}

      {pvpIncomingPrompt && (
        <PvpIncomingPrompt
          prompt={pvpIncomingPrompt}
          onAccept={acceptPvpChallenge}
          onDecline={declinePvpChallenge}
        />
      )}

      {encounterPreview && (
        <BattleIntroScreen
          monsterType={encounterPreview.monsterType}
          monsterBootstrap={encounterPreview.monsterBootstrap}
          bootstrapStatus={encounterPreview.bootstrapStatus}
          encounterDisplayName={encounterPreview.displayName}
          encounterDisplayLevel={encounterPreview.displayLevel}
          encounterIqValue={encounterPreview.iqValue}
          encounterVisualTypeByte={encounterPreview.visualTypeByte}
          encounterSharedSheetFamily={encounterPreview.sharedSheetFamily}
          encounterNameColorMode={encounterPreview.nameColorMode}
          monsterPreviewFrameIndex={encounterPreview.monsterFrameIndex}
          monsterPreviewFacingRight={encounterPreview.monsterFacingRight}
          monsterPreviewWorldState={encounterPreview.monsterWorldState}
          playerLeft={encounterPreview.playerLeft}
          monsterLeft={encounterPreview.monsterLeft}
          groundY={encounterPreview.groundY}
          playerScale={sceneConfig.playerScale}
          appearance={appearance}
          onConfirm={confirmEncounter}
        />
      )}

      {/* ─── Unified PopupMenu usage ─── */}
      <PopupMenu
        visible={menuVisible}
        items={menuItems}
        selectedIndex={menuSelectedIndex}
        onIndexChange={setMenuSelectedIndex}
        onSelect={() => {}}
        onClose={() => setMenuVisible(false)}
        bottomOffset={27}
        selectSignal={menuSelectSignal}
      />

      {/* ─── SoftkeyBar (bottom bar) - using icons like login screen ─── */}
      <SoftkeyBar
        width={SCREEN_W}
        centerLabel={isEncounterActive ? 'Vào ngay' : undefined}
        onLeftPress={() => {
          if (isPvpDialogActive || isPvpPromptActive) return;
          if (isCharacterDialogActive) return;
          if (isEncounterActive) return;
          if (menuVisible) {
            setMenuSelectSignal(prev => prev + 1);
            return;
          }
          setMenuVisible(true);
        }}
        onRightPress={menuVisible || isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive ? () => {
          if (isPvpPromptActive) {
            declinePvpChallenge(pvpIncomingPrompt.ticket);
            return;
          }
          if (isPvpDialogActive) {
            setActivePvpDialog(null);
            return;
          }
          if (isCharacterDialogActive) {
            setActiveCharacterDialog(null);
            return;
          }
          if (isEncounterActive) {
            cancelEncounter();
            return;
          }
          if (menuVisible) {
            setMenuVisible(false);
          }
        } : undefined}
        onCenterPress={() => {
          if (isPvpDialogActive || isPvpPromptActive) {
            return;
          }
          if (isCharacterDialogActive) {
            return;
          }
          if (isEncounterActive) {
            confirmEncounter();
            return;
          }
          if (onBattle) {
            const previewMonster =
              monsterRuntimes.find((m) => m.type === 'fire') ?? monsterRuntimes[0];
            if (previewMonster) {
              startEncounter({
                type: previewMonster.type,
                monsterKey: previewMonster.roster.monsterKey,
                displayName: previewMonster.roster.displayName,
                displayLevel: previewMonster.roster.displayLevel,
                iqValue: previewMonster.roster.iqValue,
                visualTypeByte: previewMonster.roster.visualTypeByte,
                sharedSheetFamily: previewMonster.roster.sharedSheetFamily,
                nameColorMode: previewMonster.roster.nameColorMode,
                monsterFrameIndex: previewMonster.frameIndex,
                monsterFacingRight: previewMonster.direction === 1,
                monsterWorldState: previewMonster.worldState,
                x: previewMonster.x,
                groundY: previewMonster.groundY,
                initialTurn: 'player',
              });
            }
          }
        }}
        leftIcon={menuVisible ? ASSET_SOFTKEY_OK : isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive ? undefined : ASSET_SOFTKEY_MENU}
        rightIcon={menuVisible || isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive ? ASSET_SOFTKEY_CANCEL : undefined}
      />

    </View>
  );
};

// ── Styles ───────────────────────────────────────────────────────────────────
const styles = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#000' },

  scroll: { flex: 1 },
  bg: { position: 'absolute', top: 0, left: 0, zIndex: LAYER_BG },
  pvpOverlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 100,
    justifyContent: 'center',
    alignItems: 'center',
  },
  pvpBackdrop: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.55)',
  },
  pvpFrame: {
    width: Math.min(SCREEN_W * 0.94, 448),
    maxHeight: Math.min(SCREEN_H * 0.58, 380),
  },
  pvpContent: {
    padding: 8,
    backgroundColor: '#dfe9f7',
  },
  pvpIncomingFrame: {
    width: Math.min(SCREEN_W * 0.86, 340),
  },
  pvpIncomingContent: {
    padding: 12,
    backgroundColor: '#fff4d8',
  },
  pvpIncomingText: {
    color: '#2f1d12',
    fontSize: 14,
    fontWeight: '700',
    marginTop: 8,
  },
  pvpIncomingMeta: {
    color: '#7b4c18',
    fontSize: 12,
    fontWeight: '800',
    marginTop: 6,
  },
  pvpHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 6,
  },
  pvpTitle: {
    color: '#8a5700',
    fontSize: 24,
    fontWeight: '800',
  },
  pvpHeaderAction: {
    color: '#313338',
    fontSize: 14,
    fontWeight: '700',
  },
  pvpActionDisabled: {
    opacity: 0.45,
  },
  pvpArenaBoard: {
    position: 'relative',
    marginTop: 2,
    borderWidth: 1,
    borderColor: '#96add3',
    backgroundColor: '#f2f8ff',
  },
  pvpList: {
    maxHeight: 220,
    width: '100%',
  },
  pvpListContent: {
    gap: 0,
    paddingBottom: 2,
  },
  pvpLegacyRow: {
    minHeight: 42,
    borderBottomWidth: 1,
    borderBottomColor: '#b9c7df',
    backgroundColor: '#f7fbff',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 4,
  },
  pvpLegacyRowActive: {
    backgroundColor: '#fff9df',
    borderTopWidth: 2,
    borderTopColor: '#f0b73d',
    borderBottomColor: '#f0b73d',
  },
  pvpLegacyBadge: {
    width: 28,
    color: '#2d64b5',
    fontSize: 10,
    fontWeight: '900',
    textAlign: 'center',
  },
  pvpLegacyTextWrap: {
    flex: 1,
    minWidth: 0,
    marginLeft: 2,
  },
  pvpLegacyName: {
    color: '#1d2f59',
    fontSize: 16,
    fontWeight: '700',
    lineHeight: 18,
  },
  pvpLegacyMeta: {
    color: '#2b5ec4',
    fontSize: 13,
    fontWeight: '500',
    lineHeight: 15,
    marginTop: 1,
  },
  pvpLegacyStake: {
    width: 34,
    color: '#486ea8',
    fontSize: 11,
    fontWeight: '700',
    textAlign: 'right',
  },
  pvpLegacyPreviewCard: {
    position: 'absolute',
    right: 12,
    top: 74,
    width: Math.min(SCREEN_W * 0.54, 212),
    minHeight: 74,
    borderWidth: 2,
    borderColor: '#5ca3ee',
    backgroundColor: '#f6fbff',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 6,
  },
  pvpArenaEmptyPanel: {
    minHeight: 72,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#f6fbff',
    borderTopWidth: 1,
    borderTopColor: '#b9c7df',
    paddingHorizontal: 12,
  },
  pvpArenaEmptyTitle: {
    color: '#1f2f4d',
    fontSize: 14,
    fontWeight: '700',
    textAlign: 'center',
  },
  pvpArenaEmptyMeta: {
    color: '#496ca5',
    fontSize: 12,
    marginTop: 4,
    textAlign: 'center',
  },
  pvpLegacyPreviewAvatar: {
    width: 62,
    height: 70,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
    marginRight: 8,
  },
  pvpLegacyPreviewInfo: {
    flex: 1,
    minWidth: 0,
  },
  pvpLegacyPreviewName: {
    color: '#1f2f4d',
    fontSize: 14,
    fontWeight: '800',
  },
  pvpLegacyPreviewMeta: {
    color: '#2c3a52',
    fontSize: 11,
    fontWeight: '700',
    marginTop: 2,
  },
  pvpOpponentRow: {
    minHeight: 70,
    borderWidth: 1,
    borderColor: '#6f5535',
    backgroundColor: '#f2e0b8',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 6,
  },
  pvpOpponentRowActive: {
    borderColor: '#f1c15a',
    backgroundColor: '#fff0c6',
  },
  pvpOpponentAvatar: {
    width: 42,
    height: 58,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
    marginRight: 8,
  },
  pvpPreviewRow: {
    minHeight: 74,
    borderWidth: 1,
    borderColor: '#6f5535',
    backgroundColor: '#f2e0b8',
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    marginTop: 8,
  },
  pvpPreviewAvatar: {
    width: 54,
    height: 68,
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'flex-end',
    marginRight: 10,
  },
  pvpOpponentInfo: {
    flex: 1,
    minWidth: 0,
  },
  pvpOpponentName: {
    color: '#2f1d12',
    fontSize: 14,
    fontWeight: '800',
  },
  pvpOpponentMeta: {
    color: '#5d4327',
    fontSize: 11,
    marginTop: 2,
  },
  pvpMiniButton: {
    minWidth: 48,
    height: 28,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#7c3f21',
    borderWidth: 1,
    borderColor: '#d8a95d',
  },
  pvpMiniButtonText: {
    color: '#fff2d0',
    fontSize: 11,
    fontWeight: '800',
  },
  pvpChallengeForm: {
    gap: 6,
  },
  pvpLabel: {
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '700',
  },
  pvpInput: {
    minHeight: 34,
    borderWidth: 1,
    borderColor: '#7e5d37',
    backgroundColor: '#f8e8be',
    color: '#2f1d12',
    paddingHorizontal: 10,
    fontSize: 14,
  },
  pvpStakeRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  pvpStakeInput: {
    flex: 1,
  },
  pvpStakeUnit: {
    width: 86,
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '800',
    textAlign: 'right',
  },
  pvpCheckGrid: {
    marginTop: 4,
    gap: 6,
  },
  pvpCheckRow: {
    flexDirection: 'row',
    alignItems: 'center',
    minHeight: 24,
  },
  pvpCheckBox: {
    width: 18,
    height: 18,
    borderWidth: 1,
    borderColor: '#d8a95d',
    backgroundColor: '#1d1712',
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 8,
  },
  pvpCheckBoxActive: {
    backgroundColor: '#a8642b',
  },
  pvpCheckMark: {
    color: '#fff4c9',
    fontSize: 12,
    fontWeight: '900',
    lineHeight: 14,
  },
  pvpCheckLabel: {
    color: '#f8e8be',
    fontSize: 12,
    fontWeight: '700',
  },
  pvpStatusRow: {
    marginTop: 10,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
  },
  pvpStatusText: {
    color: '#f8e8be',
    fontSize: 12,
  },
  pvpErrorText: {
    color: '#ff9f9f',
    fontSize: 12,
    textAlign: 'center',
    marginTop: 8,
  },
  pvpEmptyText: {
    color: '#33486c',
    fontSize: 12,
    textAlign: 'center',
    marginTop: 8,
  },
  pvpFooter: {
    marginTop: 8,
    flexDirection: 'row',
    justifyContent: 'flex-end',
    gap: 8,
  },
  pvpButton: {
    minWidth: 82,
    minHeight: 30,
    borderWidth: 1,
    borderColor: '#c88b2f',
    backgroundColor: '#fff1cd',
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 12,
  },
  pvpButtonPrimary: {
    backgroundColor: '#b26a2f',
  },
  pvpButtonDisabled: {
    opacity: 0.55,
  },
  pvpButtonText: {
    color: '#5a3a0f',
    fontSize: 14,
    fontWeight: '800',
  },
  pvpButtonPrimaryText: {
    color: '#fff4c9',
    fontSize: 14,
    fontWeight: '900',
  },
});
