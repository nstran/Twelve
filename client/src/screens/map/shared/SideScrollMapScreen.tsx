import React, { useRef, useState, useCallback, useEffect, useMemo, useReducer } from 'react';
import {
  Animated, View, Image, ScrollView, Text,
  Dimensions, Platform,
} from 'react-native';
import {
  MonsterType,
  monsterDisplaySize,
} from '../../../engine/MonsterSprite';
import {
  JavaCompatibleCharacterController,
  buildSurfaceJavaGrid,
  type CharacterControllerRef,
  type MonsterTarget,
  getSurfaceStartY,
} from '../../../engine/character';
import { CharacterRenderer, measureCharacterRenderer } from '../../character';
import {
  BattleIntroScreen,
  type MonsterSharedSheetFamily,
} from '../../battle';
import { MapHUD } from '../../../components/game/MapHUD/MapHUD';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu } from '../../../components/controls/PopupMenu/PopupMenu';
import { TouchGamepad } from '../../../components/controls/TouchGamepad';
import { clearSession } from '../../../storage/SessionStorage';
import {
  SocketClient,
  type MapInfo,
  type MapNpcRosterRecord,
  type PlayerMapState,
} from '../../../network/SocketClient';
import { resolveSideScrollMapSceneConfig } from '../core';
import { createMapGameMenuItems } from '../core';
import {
  EMPTY_MAP_MISSION_STATE,
  MapCharacterDialogs,
  reduceMapMissionState,
  type CharacterStatKey,
  type MapCharacterDialogKind,
} from '../core';
import type {
  MapMonsterRosterEntry,
  ResolveMapMonsterRoster,
} from '../core';
import type { CharacterAppearance } from '../../character/shared';
import type {
  BattleResultRewardResponse,
  MonsterBattleBootstrapResponse,
  ResolvePvpBattleBootstrap,
  ResolvePvpChallengeApi,
  ResolvePvpOpponents,
  ResolveMonsterBattleBootstrap,
} from '../../battle';
import { styles } from './SideScrollMapScreen.styles';
import { MissionDialog } from './components/MissionDialog';
import { PvpDialog } from './components/PvpDialog';
import { PvpIncomingPrompt } from './components/PvpIncomingPrompt';
import { NpcTalkDialog, type NpcTalkDialogState } from './components/NpcTalkDialog';
import { MissionToastStack } from './components/MissionToastStack';
import { MonsterField, NpcField } from './components/MapActorField';
import {
  buildInitialVisuals,
  buildNpcRuntimes,
  reconcileMonsterRuntimes,
  type MonsterRuntime,
  type MonsterVisual,
} from './runtime/MapActorRuntime';
import { useMapSocketRuntime } from './hooks/useMapSocketRuntime';
import { useMapPvpRuntime } from './hooks/useMapPvpRuntime';
import { useMapMonsterLoop } from './hooks/useMapMonsterLoop';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');
const ASSET_SOFTKEY_MENU = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');

const SOFTKEY_BAR_HEIGHT = 26;
const LAYER_GROUND = 1;
const LAYER_CHARACTER = 3;

const DECOR_LAYER_ORDER = ['background', 'behindActors', 'frontDecor'] as const;

const ENGAGE_TRIGGER_DELAY_MS = 120;
const PLAYER_ALERT_MEMORY_MS = 220;
const BATTLE_RECOVERY_MS = 3000;
const FRAME_TICKS = 4;
const MONSTER_TICK_MS = 50;
const MAP_DEBUG_OVERLAY_ENABLED = false;
const MAP_DEBUG_OVERLAY_MIN_MS = 120;

const getLoopNowMs = (): number => (
  typeof performance !== 'undefined' && typeof performance.now === 'function'
    ? performance.now()
    : Date.now()
);

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
  pendingBattleResult?: BattleResultRewardResponse | null;
  onConsumeBattleResult?: () => void;
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
  onUpgradeEquipment?: (equipKey: string, materialItemIds: number[]) => Promise<string | null>;
}

interface MapDebugOverlayState {
  seq: number;
  event: string;
  x: number;
  footY: number;
  facing: 'left' | 'right';
  activeMoveDirection: 'left' | 'right' | null;
  monsters: number;
  cameraX: number;
  at: number;
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

// ═══════════════════════════════════════════════════════════════════════════
export const SideScrollMapScreen: React.FC<Props> = ({
  mapId,
  roomId,
  roomLabel,
  appearance,
  defeatBlinkToken = 0,
  onLogout,
  onBattle,
  pendingBattleResult,
  onConsumeBattleResult,
  resolveMonsterRoster,
  resolveMonsterBootstrap,
  resolvePvpOpponents,
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
  onUpgradeEquipment,
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
  /**
   * Java-inspired/reconstructed visual anchoring policy:
   * physics/collision footY stays on `groundTop`, but the recovered RN
   * create-character compositor exposes a few transparent/body pixels below
   * the visible shoes. Sink only the rendered sprite a little deeper into the
   * grass so the feet visually touch the Hoa Lư ground strip; do not change
   * runtime `kl.t` collision or monster trigger footY.
   */
  const spriteFootSink = Math.round(
    sceneConfig.playerFootSinkSourcePx
    * sceneConfig.playerScale
    / sceneConfig.createCharacterDefaultScale,
  ) + Math.round(2 * sceneConfig.playerScale);
  const defaultCharInitX = Math.round(mapWidth * sceneConfig.playerSpawnRatio);
  const [serverPlayerX, setServerPlayerX] = useState<number | null>(null);
  const [serverPlayerFacing, setServerPlayerFacing] = useState<'left' | 'right'>('right');
  const [playerSpawnRevision, setPlayerSpawnRevision] = useState(0);
  const [missionState, dispatchMission] = useReducer(reduceMapMissionState, EMPTY_MAP_MISSION_STATE);
  const [npcTalkDialog, setNpcTalkDialog] = useState<NpcTalkDialogState | null>(null);
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
    () => buildSurfaceJavaGrid(mapWidth, mapHeight, sceneSurfaces),
    [mapHeight, mapWidth, sceneSurfaces],
  );
  const sceneDecorByLayer = useMemo(() => {
    const buckets = {
      background: [],
      behindActors: [],
      frontDecor: [],
    } as Record<typeof DECOR_LAYER_ORDER[number], Array<NonNullable<typeof sceneConfig.decorObjects>[number]>>;

    for (const decor of sceneConfig.decorObjects ?? []) {
      buckets[decor.layer].push(decor);
    }

    return buckets;
  }, [sceneConfig.decorObjects]);

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
  const [mapDebugOverlay, setMapDebugOverlay] = useState<MapDebugOverlayState | null>(null);
  const mapDebugSeqRef = useRef(0);
  const mapDebugLastUpdateAtRef = useRef(0);
  const [activeMoveDirection, setActiveMoveDirection] = useState<'left' | 'right' | null>(null);
  const [monsterRoster, setMonsterRoster] = useState<MapMonsterRosterEntry[]>([]);
  const [npcRoster, setNpcRoster] = useState<MapNpcRosterRecord[]>([]);
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
      const acceptedX = acceptedServerPositionRef.current;
      const serverDeltaFromAccepted = acceptedX === null
        ? Infinity
        : Math.abs(displayPlayerX - acceptedX);
      const serverDeltaFromLocal = Math.abs(displayPlayerX - localX);
      const isInitialSpawnPacket = acceptedX === null;
      const isTeleportReconcile = serverDeltaFromAccepted >= 24 && serverDeltaFromLocal >= 24;

      // Java client keeps the local map actor authoritative during immediate movement/jump
      // and only accepts server position packets as spawn/teleport reconciliation.
      // Source: reverse-engineered map actor behavior from reference/redecoded/decompiled/mh.java
      // setfall path + current RN bug trace where playerMapState echo reset initialX mid-jump.
      //
      // Java-inspired/reconstructed anti-jitter policy:
      // playerMapState is a coarse server echo, not per-frame movement authority.
      // Treat sub-tile/rounding echoes as accepted bookkeeping only; do not bump
      // `positionRevision`, because that remount-snaps the runtime actor/camera.
      if (localPositionDirtyRef.current && playerRecentlyMoved && serverDeltaFromLocal > 1) {
        acceptedServerPositionRef.current = displayPlayerX;
        setServerPlayerFacing(state?.direction === 0 ? 'left' : 'right');
        return;
      }

      acceptedServerPositionRef.current = displayPlayerX;
      localPositionDirtyRef.current = false;
      if (isInitialSpawnPacket || isTeleportReconcile) {
        setPlayerSpawnRevision((revision) => revision + 1);
        setServerPlayerX(displayPlayerX);
      }
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

  useMapSocketRuntime({
    client: socketClientRef.current,
    mapId,
    roomId,
    setMonsterRoster,
    setNpcRoster,
    setNpcTalkDialog,
    dispatchMission,
  });

  const npcRuntimes = useMemo(
    () => buildNpcRuntimes(npcRoster, mapScale, groundTop),
    [groundTop, mapScale, npcRoster],
  );

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
  const [missionDialogVisible, setMissionDialogVisible] = useState(false);
  const {
    activePvpDialog,
    setActivePvpDialog,
    pvpOpponents,
    pvpStatus,
    pvpError,
    pvpTarget,
    setPvpTarget,
    pvpStakeThousands,
    setPvpStakeThousands,
    pvpAllowSpectators,
    setPvpAllowSpectators,
    pvpOneWay,
    setPvpOneWay,
    pvpDisableSpecialSkills,
    setPvpDisableSpecialSkills,
    pvpIncomingPrompt,
    setPvpIncomingPrompt,
    loadPvpOpponents,
    openPvpDialog,
    startPvpBattle,
    acceptPvpChallenge,
    declinePvpChallenge,
  } = useMapPvpRuntime({
    appearance,
    onBattle,
    resolvePvpOpponents,
    resolvePvpChallengeApi,
    setMenuVisible,
    setActiveCharacterDialog,
  });

  const handleLogout = useCallback(async () => {
    await clearSession();
    onLogout();
  }, [onLogout]);

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
      onOpenQuests: () => {
        setMenuVisible(false);
        setMissionDialogVisible(true);
        SocketClient.getInstance().requestMissionList();
      },
    }),
    [handleLogout, openPvpDialog],
  );

  const isEncounterActive = encounterPreview !== null;
  const isCharacterDialogActive = activeCharacterDialog !== null;
  const isPvpDialogActive = activePvpDialog !== null;
  const isPvpPromptActive = pvpIncomingPrompt !== null;
  const isNpcTalkDialogActive = npcTalkDialog !== null;
  const isMissionDialogActive = missionDialogVisible;
  const showTouchGamepad = !menuVisible && !isEncounterActive && !defeatRecoveryActive && !isCharacterDialogActive && !isPvpDialogActive && !isPvpPromptActive && !isNpcTalkDialogActive && !isMissionDialogActive;
  const allowMapPointerInput = Platform.OS !== 'web' && !defeatRecoveryActive && !isCharacterDialogActive && !isPvpDialogActive && !isPvpPromptActive && !isNpcTalkDialogActive && !isMissionDialogActive;
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

  useEffect(() => {
    if (!pendingBattleResult) return;

    if (pendingBattleResult.missionUpdates && pendingBattleResult.missionUpdates.length > 0) {
      dispatchMission({ type: 'battleProgress', updates: pendingBattleResult.missionUpdates });
    }

    onConsumeBattleResult?.();
  }, [onConsumeBattleResult, pendingBattleResult]);

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

  useMapMonsterLoop({
    isEncounterActive,
    monsterRuntimeVersion,
    playerSpriteWidth: playerSpriteSize.w,
    monsterRuntimes,
    monsterTargetsRef,
    playerLastMovedAtRef,
    charLeftRef,
    charFootYRef,
    engagedMonsterIdRef,
    battleTriggered,
    defeatRecoveryActiveRef,
    setMonsterVisuals,
    startEncounter,
    getLoopNowMs,
    playerAlertMemoryMs: PLAYER_ALERT_MEMORY_MS,
    monsterTickMs: MONSTER_TICK_MS,
    frameTicks: FRAME_TICKS,
    engageTriggerDelayMs: ENGAGE_TRIGGER_DELAY_MS,
  });

  useEffect(() => {
    // Java-inspired/reconstructed policy from mh/km map actor ownership:
    // only hard-reset local refs/camera when `positionRevision` says this is a
    // spawn/teleport reconciliation. Server echo updates can change `charInitX`
    // by a few pixels due to native/display rounding; tying this effect to
    // `charInitX` made the camera/player snap while walking, perceived as jitter.
    charLeftRef.current = charInitX;
    charFootYRef.current = groundTop;
    scrollToCharacter(charInitX);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [playerSpawnRevision]);

  useEffect(() => {
    if (showTouchGamepad) return;
    characterControllerRef.current?.stopMove();
    setActiveMoveDirection(null);
  }, [showTouchGamepad]);

  const updateMapDebugOverlay = useCallback((
    event: string,
    x: number = charLeftRef.current,
    facing: 'left' | 'right' = serverPlayerFacing,
    footY: number = charFootYRef.current,
    force = false,
  ) => {
    if (!MAP_DEBUG_OVERLAY_ENABLED) return;

    const now = getLoopNowMs();
    if (!force && now - mapDebugLastUpdateAtRef.current < MAP_DEBUG_OVERLAY_MIN_MS) {
      return;
    }

    mapDebugLastUpdateAtRef.current = now;
    mapDebugSeqRef.current += 1;
    setMapDebugOverlay({
      seq: mapDebugSeqRef.current,
      event,
      x: Math.round(x),
      footY: Math.round(footY),
      facing,
      activeMoveDirection,
      monsters: monsterRuntimesRef.current.length,
      cameraX: Math.round(cameraXRef.current),
      at: Math.round(now),
    });
  }, [activeMoveDirection, serverPlayerFacing]);

  const handleGamepadMoveStart = useCallback((direction: 'left' | 'right') => {
    playerLastMovedAtRef.current = getLoopNowMs();
    setActiveMoveDirection(direction);
    updateMapDebugOverlay(`move-start:${direction}`, charLeftRef.current, direction, charFootYRef.current, true);
    characterControllerRef.current?.startMove(direction);
  }, [updateMapDebugOverlay]);

  const handleGamepadMoveStop = useCallback(() => {
    setActiveMoveDirection(null);
    updateMapDebugOverlay('move-stop', charLeftRef.current, serverPlayerFacing, charFootYRef.current, true);
    characterControllerRef.current?.stopMove();
  }, [serverPlayerFacing, updateMapDebugOverlay]);

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

  const renderDecorLayer = (layer: typeof DECOR_LAYER_ORDER[number]) => (
    sceneDecorByLayer[layer].map((decor) => {
      const decorWidth = Math.round(decor.width * mapScale);
      const decorHeight = Math.round(decor.height * mapScale);
      const decorLeft = Math.round(mapWidth * decor.xRatio - decorWidth / 2);
      let decorTop: number;
      if (typeof decor.yRatio === 'number') {
        decorTop = Math.round(mapHeight * decor.yRatio);
      } else {
        decorTop = Math.round(groundTop - decorHeight - ((decor.groundOffset ?? 0) * mapScale));
      }

      return (
        <Image
          key={decor.key}
          source={decor.asset}
          style={{
            position: 'absolute',
            left: decorLeft,
            top: decorTop,
            width: decorWidth,
            height: decorHeight,
            zIndex: decor.zIndex,
          }}
          resizeMode={decor.resizeMode ?? 'contain'}
        />
      );
    })
  );

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

          {renderDecorLayer('background')}

          {/* Layer 1: Đất */}
          {renderGround()}

          {renderDecorLayer('behindActors')}

          {/* Layer 2: Quái vật + NPC map actors */}
          {!isEncounterActive && (
            <>
              <MonsterField
                runtimes={monsterRuntimes}
                visuals={monsterVisuals}
              />
              <NpcField npcs={npcRuntimes} />
            </>
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
              collisionGrid={javaCollisionGrid}
              // Java-inspired/reconstructed policy from km.java/kf.java:
              // Hoa Lư must feed the controller with a tile collision grid built
              // from every authored GroundSurface. A flat one-row ground grid made
              // the compact runtime rect snap/fall around mid-map platforms, while
              // `undefined` forced a non-Java surface fallback.
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
                updateMapDebugOverlay('onMove', x, facing, footY ?? groundTop);
                scrollToCharacter(x);
                persistPlayerWorldPosition(x, facing, false);
              }}
              onMoveEnd={(x, facing, footY) => {
                localPositionDirtyRef.current = true;
                charLeftRef.current = x;
                charFootYRef.current = footY ?? groundTop;
                updateMapDebugOverlay('onMoveEnd', x, facing, footY ?? groundTop, true);
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
          {renderDecorLayer('frontDecor')}
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

      {MAP_DEBUG_OVERLAY_ENABLED && mapDebugOverlay && (
        <View pointerEvents="none" style={styles.mapDebugOverlay}>
          <Text style={styles.mapDebugTitle}>JavaMoveDebug overlay #{mapDebugOverlay.seq}</Text>
          <Text style={styles.mapDebugText}>
            {`event=${mapDebugOverlay.event} x=${mapDebugOverlay.x} footY=${mapDebugOverlay.footY} facing=${mapDebugOverlay.facing}`}
          </Text>
          <Text style={styles.mapDebugText}>
            {`dir=${mapDebugOverlay.activeMoveDirection ?? '-'} cameraX=${mapDebugOverlay.cameraX} monsters=${mapDebugOverlay.monsters} t=${mapDebugOverlay.at}`}
          </Text>
        </View>
      )}

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
        onUpgradeEquipment={onUpgradeEquipment}
      />

      {missionDialogVisible && (
        <MissionDialog
          missions={missionState.missions}
          activeMission={missionState.activeMission}
          onSelectMission={(questId) => SocketClient.getInstance().requestMissionDetail(questId)}
          onAcceptMission={(questId) => SocketClient.getInstance().requestMissionAccept(questId)}
          onClose={() => setMissionDialogVisible(false)}
        />
      )}

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

      <MissionToastStack toasts={missionState.toasts} />

      {npcTalkDialog && <NpcTalkDialog dialog={npcTalkDialog} />}

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
        centerLabel={isEncounterActive ? 'Vào ngay' : isNpcTalkDialogActive ? 'Tiep tuc' : npcRoster.length > 0 ? 'Noi chuyen' : undefined}
        onLeftPress={() => {
          if (isPvpDialogActive || isPvpPromptActive) return;
          if (isCharacterDialogActive) return;
          if (isMissionDialogActive) return;
          if (isNpcTalkDialogActive) return;
          if (isEncounterActive) return;
          if (menuVisible) {
            setMenuSelectSignal(prev => prev + 1);
            return;
          }
          setMenuVisible(true);
        }}
        onRightPress={menuVisible || isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive || isNpcTalkDialogActive || isMissionDialogActive ? () => {
          if (isNpcTalkDialogActive) {
            setNpcTalkDialog(null);
            return;
          }
          if (isPvpPromptActive) {
            declinePvpChallenge(pvpIncomingPrompt.ticket);
            return;
          }
          if (isPvpDialogActive) {
            setActivePvpDialog(null);
            return;
          }
          if (isMissionDialogActive) {
            setMissionDialogVisible(false);
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
          if (isMissionDialogActive) {
            return;
          }
          if (isCharacterDialogActive) {
            return;
          }
          if (isNpcTalkDialogActive) {
            SocketClient.getInstance().requestNpcTalk(npcTalkDialog.npcId, true);
            return;
          }
          if (isEncounterActive) {
            confirmEncounter();
            return;
          }
          const firstNpc = npcRoster[0];
          if (firstNpc) {
            SocketClient.getInstance().requestNpcTalk(firstNpc.npcId, false);
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
        leftIcon={menuVisible ? ASSET_SOFTKEY_OK : isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive || isNpcTalkDialogActive || isMissionDialogActive ? undefined : ASSET_SOFTKEY_MENU}
        rightIcon={menuVisible || isEncounterActive || isCharacterDialogActive || isPvpDialogActive || isPvpPromptActive || isNpcTalkDialogActive || isMissionDialogActive ? ASSET_SOFTKEY_CANCEL : undefined}
      />

    </View>
  );
};

