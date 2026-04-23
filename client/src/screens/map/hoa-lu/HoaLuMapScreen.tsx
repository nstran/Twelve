import React, { useRef, useState, useCallback, useEffect, useMemo } from 'react';
import {
  Animated, View, Image, ScrollView,
  StyleSheet, Dimensions, Platform,
} from 'react-native';
import {
  MonsterSprite, MonsterType,
  WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize, monsterPlacementMetrics,
} from '../../../engine/MonsterSprite';
import {
  CharacterController,
  type CharacterControllerRef,
  type GroundSurface,
  type MonsterTarget,
  getSurfaceStartY,
} from '../../../engine/character';
import { CharacterRenderer, measureCharacterRenderer } from '../../character';
import { HOA_LU_MAP_ASSETS } from './assets';
import { buildHoaLuSurfaces } from './hoaLu.navigation';
import { BattleIntroScreen } from '../../battle';
import type { MonsterSharedSheetFamily } from '../../battle';
import { MapHUD } from '../../../components/game/MapHUD/MapHUD';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu, MenuItem } from '../../../components/controls/PopupMenu/PopupMenu';
import { TouchGamepad } from '../../../components/controls/TouchGamepad';
import { clearSession } from '../../../storage/SessionStorage';
import type {
  MapMonsterRosterEntry,
  ResolveMapMonsterRoster,
} from '../core';
import type { CharacterAppearance } from '../../character/shared';
import type {
  MonsterBattleBootstrapResponse,
  ResolveMonsterBattleBootstrap,
} from '../../battle';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');
const ASSET_SOFTKEY_MENU = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_sharpest_1.png');
const ASSET_SOFTKEY_OK = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_ok.png');
const ASSET_SOFTKEY_CANCEL = require('../../../../assets/ui/11_softkey_icons_confirmed/icon_cancel.png');

// ── Map / Background ────────────────────────────────────────────────────────
const MAP_NATIVE_W  = 1536;
const MAP_NATIVE_H  = 1024;
const MAP_SCALE     = SCREEN_H / MAP_NATIVE_H;
const MAP_W         = Math.round(MAP_NATIVE_W * MAP_SCALE);
const MAP_H         = SCREEN_H;
const MAP_MIN_X     = 0;
const MAP_MAX_X     = MAP_W;
const SOFTKEY_BAR_HEIGHT = 26;
const LAYER_BG = 0;
const LAYER_GROUND = 1;
const LAYER_MONSTER = 2;
const LAYER_CHARACTER = 3;

// ── Ground tile strip ────────────────────────────────────────────────────────
const GROUND_TILE_W       = 124;
const GROUND_TILE_H       = 102;
const GROUND_TILE_OVERLAP = 20;
const GROUND_TILE_STEP    = GROUND_TILE_W - GROUND_TILE_OVERLAP;
// Mở rộng ground 1 tile sang TRÁI để nhân vật không nhìn thấy edge khi đứng đầu map
const GROUND_TILE_LEFT_OFFSET = GROUND_TILE_STEP;
const NUM_GROUND_TILES    = Math.ceil((MAP_W + GROUND_TILE_LEFT_OFFSET) / GROUND_TILE_STEP) + 2;
// Tỉ lệ từ đỉnh tile xuống điểm tiếp xúc mặt đất (grass surface).
// 0.16 × 102px ≈ 16px từ đỉnh tile = vị trí visual grass trong tile image.
// Đây là giá trị đã được calibrate để nhân vật đứng đúng trên cỏ.
const GROUND_TILE_CONTACT_RATIO = 0.16;
const GROUND_TILE_CONTACT_OFFSET = Math.round(GROUND_TILE_H * GROUND_TILE_CONTACT_RATIO);
// Hạ ground line thêm vài px để character/monster "ăn" xuống thảm cỏ hơn,
// tránh cảm giác đang lơ lửng trên mép cỏ.
const GROUND_TILE_CONTACT_VISUAL_DROP = 4;

// ── Platform ─────────────────────────────────────────────────────────────────
const GROUND_ROWS  = 1;
// GROUND_SINK: đẩy toàn bộ tile strip xuống. Giảm = đất cao hơn, trông đẹp hơn về mặt visual.
const GROUND_SINK  = 18;
const GROUND_STRIP_TOP = SCREEN_H - SOFTKEY_BAR_HEIGHT - (GROUND_ROWS * GROUND_TILE_H) + GROUND_SINK;

// ── Player ────────────────────────────────────────────────────────────────────
const CHAR_SCALE  = 1.6;   // tăng size nhân vật
const CHAR_SPEED  = 1.35;
const CHAR_INIT_X = Math.round(MAP_W * 0.08);
// CreateCharacterScreen đặt canvas bottom 5px DƯỚI stone surface (tại scale 2.2).
// → Chân nhân vật cách canvas bottom: 5 / 2.2 ≈ 2.27 native px.
// → Tại CHAR_SCALE: transparent_display = round(5 * CHAR_SCALE / 2.2) = 4px.
// → Phải bù thêm 4px vào groundOffset để body bottom + 4 = GROUND_MAIN_Y → chân chạm cỏ.
const CREATE_CHARACTER_DEFAULT_SCALE = 2.2;
const SPRITE_FOOT_SINK = Math.round(5 * CHAR_SCALE / CREATE_CHARACTER_DEFAULT_SCALE); // = 4
const HOA_LU_SURFACES_BASE: GroundSurface[] = buildHoaLuSurfaces(MAP_SCALE);
// GROUND_MAIN_Y = vị trí mặt đất vật lý (y tính từ đỉnh màn hình xuống chỗ chân nhân vật/quái đứng)
const GROUND_MAIN_Y = GROUND_STRIP_TOP + GROUND_TILE_CONTACT_OFFSET + GROUND_TILE_CONTACT_VISUAL_DROP;
const HOA_LU_SURFACES: GroundSurface[] = HOA_LU_SURFACES_BASE.map((surface) => (
  surface.id === 'ground_main'
    ? { ...surface, x1: -GROUND_TILE_LEFT_OFFSET, y: GROUND_MAIN_Y }
    : surface
));
const GROUND_MAIN_SURFACE = HOA_LU_SURFACES.find((surface) => surface.id === 'ground_main')
  ?? { id: 'ground_main', x1: -GROUND_TILE_LEFT_OFFSET, x2: MAP_W, y: GROUND_MAIN_Y, kind: 'ground' as const };
const GROUND_TOP = getSurfaceStartY(GROUND_MAIN_SURFACE);

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
}

// Khoảng cách tính là "va chạm" (px từ center-to-center)
const COLLISION_DIST = 52;
// Mỗi bao nhiêu tick thì đổi frame (tick=50ms, FRAME_TICKS=4 → 80ms/frame ≈ 12fps anim)
const FRAME_TICKS = 4;
const MONSTER_TICK_MS = 50; // 20 logic ticks/sec

function mapSharedSheetFamilyToMonsterType(sharedSheetFamily: MonsterSharedSheetFamily): MonsterType {
  switch (sharedSheetFamily) {
    case 'Ice':
      return 'ice';
    case 'Zap':
      return 'zap';
    case 'Monster':
    default:
      return 'fire';
  }
}

function buildMonsterRuntimes(roster: MapMonsterRosterEntry[]): MonsterRuntime[] {
  return roster.map((entry) => {
    const surface = HOA_LU_SURFACES.find((candidate) => candidate.id === entry.surfaceId);
    if (!surface) {
      throw new Error(`Surface '${entry.surfaceId}' not found in Hoa Lu navigation data.`);
    }

    const span = Math.max(0, surface.x2 - surface.x1);
    const minX = surface.x1 + Math.max(0, Math.min(1, entry.patrolStartRatio)) * span;
    const maxX = surface.x1 + Math.max(0, Math.min(1, entry.patrolEndRatio)) * span;
    const startX = minX + Math.max(0, maxX - minX) * Math.max(0, Math.min(1, entry.spawnRatio));
    const type = mapSharedSheetFamilyToMonsterType(entry.sharedSheetFamily);
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
      direction: 1,
      tickCount: 0,
      attacking: false,
      frameIndex: WALK_FRAMES[0],
      xAnim: new Animated.Value(leftX),
      size,
      topY: surfaceGroundY - size.h + placement.groundOffset,
    };
  });
}

function buildInitialVisuals(runtimes: MonsterRuntime[]): MonsterVisual[] {
  return runtimes.map(m => ({
    id: m.id,
    frameIndex: m.frameIndex,
    direction: m.direction,
    attacking: m.attacking,
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
      };
      return (
        <Animated.View
          key={m.id}
          style={{
            position: 'absolute',
            left: 0,
            top: m.topY,
            width: m.size.w,
            height: m.size.h,
            zIndex: LAYER_MONSTER,
            transform: [{ translateX: m.xAnim }],
          }}
        >
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
  onBack:    () => void;
  onLogout:  () => void;
  onBattle?: (
    monsterType: MonsterType,
    initialTurn: 'player' | 'monster',
    monsterBootstrap: MonsterBattleBootstrapResponse,
  ) => void;
  resolveMonsterRoster?: ResolveMapMonsterRoster;
  resolveMonsterBootstrap?: ResolveMonsterBattleBootstrap;
}

interface EncounterPreviewState {
  monsterType: MonsterType;
  monsterKey: string;
  playerLeft: number;
  monsterLeft: number;
  groundY: number;
  initialTurn: 'player' | 'monster';
  bootstrapStatus: 'loading' | 'ready' | 'error';
  monsterBootstrap: MonsterBattleBootstrapResponse | null;
}

// ═══════════════════════════════════════════════════════════════════════════
export const HoaLuMapScreen: React.FC<Props> = ({
  mapId,
  roomId,
  roomLabel = 'Khu 1',
  appearance,
  onBack,
  onLogout,
  onBattle,
  resolveMonsterRoster,
  resolveMonsterBootstrap,
}) => {
  const scrollRef = useRef<ScrollView>(null);
  const characterControllerRef = useRef<CharacterControllerRef>(null);
  const battleTriggered = useRef(false);
  const encounterRequestVersionRef = useRef(0);
  const charLeftRef = useRef(CHAR_INIT_X);
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

  // ── Monster runtime (stable identity, mutated in place) ─────────────────
  const monsterRuntimesRef = useRef<MonsterRuntime[]>([]);
  const monsterTargetsRef = useRef<MonsterTarget[]>([]);
  const [monsterVisuals, setMonsterVisuals] = useState<MonsterVisual[]>([]);
  const monsterRuntimes = monsterRuntimesRef.current;

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
    const nextRuntimes = buildMonsterRuntimes(monsterRoster);
    monsterRuntimesRef.current = nextRuntimes;
    monsterTargetsRef.current = nextRuntimes.map((runtime) => ({
      id: runtime.id,
      x: runtime.x - runtime.size.w / 2,
      y: runtime.topY,
      width: runtime.size.w,
      height: runtime.size.h,
    }));
    setMonsterVisuals(buildInitialVisuals(nextRuntimes));
    battleTriggered.current = false;
    setMonsterRuntimeVersion((version) => version + 1);
  }, [monsterRoster]);

  // ── Menu state ──────────────────────────────────────────────────────────
  const [menuVisible, setMenuVisible] = useState(false);
  const [menuSelectedIndex, setMenuSelectedIndex] = useState(0);

  const handleLogout = async () => {
    await clearSession();
    onLogout();
  };

  // ── Build game menu structure (standardized) ─────────────────────────────
  const menuItems: MenuItem[] = useMemo(() => [
    {
      id: 'loi-dai',
      label: 'Lôi Đài',
      onPress: () => { /* TODO: Navigate to Loi Dai */ },
    },
    {
      id: 'khieu-chien',
      label: 'Khiêu Chiến',
      onPress: () => { /* TODO: PvP challenge */ },
    },
    {
      id: 'nhan-vat',
      label: 'Nhân Vật',
      children: [
        { id: 'thong-tin', label: 'Thông tin', onPress: () => {} },
        { id: 'tuyet-chieu', label: 'Tuyệt Chiêu', onPress: () => {} },
        { id: 'ruong-do', label: 'Rương Đồ', onPress: () => {} },
        { id: 'che-tao', label: 'Chế tạo', onPress: () => {} },
        { id: 'xep-hang', label: 'Xếp hạng', onPress: () => {} },
      ],
    },
    {
      id: 'mua-ban',
      label: 'Mua bán',
      children: [
        { id: 'cua-hang', label: 'Cửa hàng', children: [
          { id: 'cua-hang-vu-khi', label: 'Vũ khí', onPress: () => {} },
          { id: 'cua-hang-giap', label: 'Giáp', onPress: () => {} },
          { id: 'cua-hang-tieu-hao', label: 'Tiêu hao', onPress: () => {} },
        ]},
        { id: 'cho-troi', label: 'Chợ trời', onPress: () => {} },
        { id: 'giao-dich', label: 'Giao dịch', onPress: () => {} },
      ],
    },
    {
      id: 'nhiem-vu',
      label: 'Nhiệm Vụ',
      onPress: () => { /* TODO: Quest screen */ },
    },
    {
      id: 'ho-tro',
      label: 'Hỗ trợ',
      children: [
        { id: 'gioi-thieu', label: 'Giới thiệu', onPress: () => {} },
        { id: 'ho-tro-sub', label: 'Hỗ trợ', onPress: () => {} },
        { id: 'doi-sdt', label: 'Đổi SĐT', onPress: () => {} },
        { id: 'cai-dat', label: 'Cài đặt', onPress: () => {} },
      ],
    },
    {
      id: 'dang-xuat',
      label: 'Đăng Xuất',
      onPress: handleLogout,
    },
  ], [handleLogout]);

  const isEncounterActive = encounterPreview !== null;
  const showTouchGamepad = !menuVisible && !isEncounterActive;
  const allowMapPointerInput = Platform.OS !== 'web';
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
      const measured = measureCharacterRenderer(appearance, CHAR_SCALE, true);
      return { ...measured, groundOffset: measured.groundOffset + SPRITE_FOOT_SINK };
    },
    [appearance],
  );
  const hudHp = appearance.hp?.cur ?? 800;
  const hudMaxHp = appearance.hp?.max ?? 1000;
  const hudExpPercent = appearance.exp?.cur ?? 45;

  const scrollToCharacter = useCallback((charLeft: number) => {
    const maxScrollX = Math.max(0, MAP_W - SCREEN_W);
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
  }, [playerSpriteSize.w]);

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
    x: number;
    groundY: number;
    initialTurn: 'player' | 'monster';
  }) => {
    if (battleTriggered.current || isEncounterActive) return;

    const { w: monsterW } = monsterDisplaySize(snap.type);
    const requestVersion = encounterRequestVersionRef.current + 1;
    encounterRequestVersionRef.current = requestVersion;

    battleTriggered.current = true;
    setEncounterPreview({
      monsterType: snap.type,
      monsterKey: snap.monsterKey,
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
        const playerCenter = charLeftRef.current + playerSpriteSize.w / 2;

        for (let i = 0; i < monsterRuntimes.length; i++) {
          const m = monsterRuntimes[i];

          // 1. Move
          let newX  = m.x + m.roster.moveSpeed * m.direction;
          let newDir: 1 | -1 = m.direction;
          if (newX >= m.maxX) { newX = m.maxX; newDir = -1; }
          if (newX <= m.minX) { newX = m.minX; newDir =  1; }

          // 2. Collision
          const attacking = Math.abs(newX - playerCenter) < COLLISION_DIST;
          if (attacking && !battleTriggered.current) {
            const snap = {
              type: m.type,
              monsterKey: m.roster.monsterKey,
              x: newX,
              groundY: m.groundY,
              initialTurn: 'monster' as const,
            };
            setTimeout(() => startEncounter(snap), 120);
          }

          // 3. Advance frame
          const newTick   = m.tickCount + 1;
          const frames    = attacking ? ATTACK_FRAMES : WALK_FRAMES;
          const stepIdx   = Math.floor(newTick / FRAME_TICKS) % frames.length;
          const nextFrame = frames[stepIdx];

          // 4. Detect visual diff BEFORE mutating
          if (
            m.frameIndex !== nextFrame ||
            m.direction !== newDir ||
            m.attacking !== attacking
          ) {
            visualsDirty = true;
          }

          // 5. Commit to runtime (mutate in place)
          m.x          = newX;
          m.direction  = newDir;
          m.attacking  = attacking;
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
    scrollToCharacter(CHAR_INIT_X);
  }, [scrollToCharacter]);

  useEffect(() => {
    if (showTouchGamepad) return;
    characterControllerRef.current?.stopMove();
    setActiveMoveDirection(null);
  }, [showTouchGamepad]);

  const handleGamepadMoveStart = useCallback((direction: 'left' | 'right') => {
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
    for (let row = 0; row < GROUND_ROWS; row++) {
      for (let col = 0; col < NUM_GROUND_TILES; col++) {
        const tileLeft = col * GROUND_TILE_STEP - GROUND_TILE_LEFT_OFFSET;
        // Edge tiles chỉ dùng cho tile đầu/cuối thực sự ngoài phạm vi map
        const isFirst = tileLeft <= -GROUND_TILE_LEFT_OFFSET;
        const isLast  = col === NUM_GROUND_TILES - 1;
        const source  = isFirst
          ? HOA_LU_MAP_ASSETS.groundLeft
          : isLast
            ? HOA_LU_MAP_ASSETS.groundRight
            : HOA_LU_MAP_ASSETS.groundCenter;

        tiles.push(
          <Image
            key={`t-${row}-${col}`}
            source={source}
            style={{
              position: 'absolute',
              left:   tileLeft,
              top:    GROUND_STRIP_TOP + row * GROUND_TILE_H,
              width:  GROUND_TILE_W,
              height: GROUND_TILE_H,
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
        zoneName={roomLabel}
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
        contentContainerStyle={{ width: MAP_W, height: MAP_H }}
        keyboardShouldPersistTaps="handled"
      >
        <View style={{ width: MAP_W, height: MAP_H }}>
          {/* Layer 0: Background */}
          <Image
            source={HOA_LU_MAP_ASSETS.background}
            style={styles.bg}
            resizeMode="stretch"
          />

          {/* Layer 1: Đất */}
          {renderGround()}

          {/* Layer 2: Quái vật */}
          {!isEncounterActive && (
            <MonsterField runtimes={monsterRuntimes} visuals={monsterVisuals} />
          )}

          {/* Layer 3: Nhân vật */}
          {!isEncounterActive && (
            <CharacterController
              ref={characterControllerRef}
              initialX={CHAR_INIT_X}
              groundY={GROUND_TOP}
              controlMode="tap-to-move"
              speed={CHAR_SPEED}
              scale={CHAR_SCALE}
              spriteSize={playerSpriteSize}
              renderSprite={({ action, actionFrameIndex, facing, scale, poseFamilySlot, poseFrameIndex }) => (
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
              )}
              monsters={monsterTargets}
              surfaces={HOA_LU_SURFACES}
              // Cho phép đi sát 2 đầu map nhưng vẫn bị clamp trong biên map,
              // nên ở điểm đầu/cuối sẽ không bị hụt support rồi rơi xuống.
              minX={MAP_MIN_X}
              maxX={MAP_MAX_X}
              containerWidth={MAP_W}
              containerHeight={MAP_H}
              zIndex={LAYER_CHARACTER}
              allowPointerInput={allowMapPointerInput}
              disabled={menuVisible}
              onMove={(x) => {
                charLeftRef.current = x;
                scrollToCharacter(x);
              }}
              onAttackMonster={(monsterId) => {
                if (battleTriggered.current) return;

                const targetMonster = monsterRuntimes.find((m) => m.id === monsterId);
                if (!targetMonster) return;

                const snap = {
                  type: targetMonster.type,
                  monsterKey: targetMonster.roster.monsterKey,
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

      {encounterPreview && (
        <BattleIntroScreen
          monsterType={encounterPreview.monsterType}
          monsterBootstrap={encounterPreview.monsterBootstrap}
          bootstrapStatus={encounterPreview.bootstrapStatus}
          playerLeft={encounterPreview.playerLeft}
          monsterLeft={encounterPreview.monsterLeft}
          groundY={encounterPreview.groundY}
          playerScale={CHAR_SCALE}
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
        onSelect={(item: MenuItem) => { /* handle specific items if needed */ }}
        onClose={() => setMenuVisible(false)}
        bottomOffset={27} 
      />

      {/* ─── SoftkeyBar (bottom bar) - using icons like login screen ─── */}
      <SoftkeyBar
        width={SCREEN_W}
        centerLabel={isEncounterActive ? 'Vào ngay' : undefined}
        onLeftPress={() => {
          if (isEncounterActive) return;
          setMenuVisible(prev => !prev);
        }}
        onRightPress={menuVisible || isEncounterActive ? () => {
          if (isEncounterActive) {
            cancelEncounter();
            return;
          }
          if (menuVisible) {
            setMenuVisible(false);
          }
        } : undefined}
        onCenterPress={() => {
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
                x: previewMonster.x,
                groundY: previewMonster.groundY,
                initialTurn: 'player',
              });
            }
          }
        }}
        leftIcon={menuVisible ? ASSET_SOFTKEY_OK : isEncounterActive ? undefined : ASSET_SOFTKEY_MENU}
        rightIcon={menuVisible || isEncounterActive ? ASSET_SOFTKEY_CANCEL : undefined}
      />

    </View>
  );
};

// ── Styles ───────────────────────────────────────────────────────────────────
const styles = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#000' },

  scroll: { flex: 1 },
  bg: { position: 'absolute', top: 0, left: 0, width: MAP_W, height: MAP_H, zIndex: LAYER_BG },
});
