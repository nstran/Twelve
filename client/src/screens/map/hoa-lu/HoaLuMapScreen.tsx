import React, { useRef, useState, useCallback, useEffect, useMemo } from 'react';
import {
  Animated, View, Image, ScrollView,
  StyleSheet, Dimensions,
} from 'react-native';
import {
  MonsterSprite, MonsterType,
  WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize,
} from '../../../engine/MonsterSprite';
import {
  CharacterController,
  characterDisplaySize,
  type MonsterTarget,
} from '../../../engine/character';
import { HOA_LU_MAP_ASSETS } from './assets';
import { BattleIntroScreen } from '../../battle';
import { MapHUD } from '../../../components/MapHUD';
import { SoftkeyBar } from '../../../components/SoftkeyBar';
import { PopupMenu, MenuItem } from '../../../components/PopupMenu';
import { clearSession } from '../../../storage/SessionStorage';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

// ── Map / Background ────────────────────────────────────────────────────────
const MAP_NATIVE_W  = 1536;
const MAP_NATIVE_H  = 1024;
const MAP_SCALE     = SCREEN_H / MAP_NATIVE_H;
const MAP_W         = Math.round(MAP_NATIVE_W * MAP_SCALE);
const MAP_H         = SCREEN_H;

// ── Stone ground tile ───────────────────────────────────────────────────────
const TILE_W     = 96;
const TILE_H     = 32;
const NUM_TILES  = Math.ceil(MAP_W / (TILE_W - 1)) + 2;

// ── Platform ────────────────────────────────────────────────────────────────
const GROUND_ROWS  = 1;
const PLATFORM_TOP = Math.round(SCREEN_H * 0.72);

// ── Player ──────────────────────────────────────────────────────────────────
const CHAR_SCALE  = 0.7;
const CHAR_SPEED  = 5;
const CHAR_SIZE   = characterDisplaySize(CHAR_SCALE);
const CHAR_INIT_X = Math.round(MAP_W * 0.08);

// ── Monster dữ liệu tĩnh (loại + patrol range) ────────────────────────────
interface MonsterDef {
  id: number;
  type: MonsterType;
  minX: number;   // pixel trái của vùng tuần tra (center)
  maxX: number;   // pixel phải
  startX: number;
  speed: number;  // px/tick (1 tick = 50ms)
}

const MONSTER_DEFS: MonsterDef[] = [
  { id: 1, type: 'fire', startX: MAP_W * 0.28, minX: MAP_W * 0.18, maxX: MAP_W * 0.42, speed: 2.2 },
  { id: 2, type: 'ice',  startX: MAP_W * 0.55, minX: MAP_W * 0.44, maxX: MAP_W * 0.70, speed: 2.6 },
  { id: 3, type: 'zap',  startX: MAP_W * 0.82, minX: MAP_W * 0.68, maxX: MAP_W * 0.94, speed: 3.0 },
];

/**
 * Runtime monster data (mutable, NEVER replaced).
 * Position is driven by Animated.Value → native-thread translateX, no React
 * render is needed for movement. Visual-only fields (frameIndex, direction,
 * attacking) are mirrored into a React state array so the sprite can flip /
 * cycle frames, but those fields change at ~5Hz instead of 20Hz.
 */
interface MonsterRuntime {
  id: number;
  type: MonsterType;
  def: MonsterDef;
  x: number;              // center X (mutable)
  direction: 1 | -1;
  tickCount: number;
  attacking: boolean;
  frameIndex: number;
  xAnim: Animated.Value;  // drives translateX on native side
  size: ReturnType<typeof monsterDisplaySize>;
  topY: number;           // precomputed (constant)
}

/** React-state slice — only re-rendered when it actually changes. */
interface MonsterVisual {
  id: number;
  frameIndex: number;
  direction: 1 | -1;
  attacking: boolean;
}

// Khoảng cách tính là "va chạm" (px từ center-to-center)
const COLLISION_DIST = 52;
// Mỗi bao nhiêu tick thì đổi frame (tick=50ms, FRAME_TICKS=4 → 80ms/frame ≈ 12fps anim)
const FRAME_TICKS = 4;
const MONSTER_TICK_MS = 50; // 20 logic ticks/sec

function buildMonsterRuntimes(): MonsterRuntime[] {
  return MONSTER_DEFS.map(def => {
    const size = monsterDisplaySize(def.type);
    const leftX = def.startX - size.w / 2;
    return {
      id: def.id,
      type: def.type,
      def,
      x: def.startX,
      direction: 1,
      tickCount: 0,
      attacking: false,
      frameIndex: WALK_FRAMES[0],
      xAnim: new Animated.Value(leftX),
      size,
      topY: PLATFORM_TOP - size.h + size.groundOffset,
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
            zIndex: 8,
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
  onBack:    () => void;
  onLogout:  () => void;
  onBattle?: (monsterType: MonsterType) => void;
}

interface EncounterPreviewState {
  monsterType: MonsterType;
  playerLeft: number;
  monsterLeft: number;
}

// ═══════════════════════════════════════════════════════════════════════════
export const HoaLuMapScreen: React.FC<Props> = ({ onBack, onLogout, onBattle }) => {
  const scrollRef = useRef<ScrollView>(null);
  const battleTriggered = useRef(false);
  const charLeftRef = useRef(CHAR_INIT_X);
  const cameraXRef = useRef(0);
  // Camera scroll is coalesced to 1 scrollTo per vsync via rAF, so 60Hz
  // onMove callbacks from the character controller don't hammer the JS
  // thread with redundant ScrollView updates.
  const pendingScrollXRef = useRef<number | null>(null);
  const scrollRafRef = useRef<number | null>(null);
  const [encounterPreview, setEncounterPreview] = useState<EncounterPreviewState | null>(null);

  // ── Monster runtime (stable identity, mutated in place) ─────────────────
  // Created once; the rAF loop mutates fields directly and drives position
  // via Animated.Value (native). This avoids 20Hz React re-renders and
  // keeps the `monsters` prop identity stable for CharacterController.
  const monsterRuntimesRef = useRef<MonsterRuntime[]>([]);
  if (monsterRuntimesRef.current.length === 0) {
    monsterRuntimesRef.current = buildMonsterRuntimes();
  }
  const monsterRuntimes = monsterRuntimesRef.current;

  /**
   * Stable MonsterTarget[] — same array identity across renders, contents
   * are mutated in place by the game loop. CharacterController reads
   * positions via its internal ref each rAF tick → always fresh.
   */
  const monsterTargetsRef = useRef<MonsterTarget[]>([]);
  if (monsterTargetsRef.current.length === 0) {
    monsterTargetsRef.current = monsterRuntimes.map(m => ({
      id: String(m.id),
      x: m.x - m.size.w / 2,
      y: m.topY,
      width: m.size.w,
      height: m.size.h,
    }));
  }

  // React state — only for frame/direction flips (changes ~5Hz, not 20Hz).
  const [monsterVisuals, setMonsterVisuals] = useState<MonsterVisual[]>(
    () => buildInitialVisuals(monsterRuntimes),
  );

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

  const scrollToCharacter = useCallback((charLeft: number) => {
    const maxScrollX = Math.max(0, MAP_W - SCREEN_W);
    const camTarget = charLeft + CHAR_SIZE.w / 2 - SCREEN_W / 2;
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
  }, []);

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

  const isEncounterActive = encounterPreview !== null;

  const startEncounter = useCallback((snap: { type: MonsterType; x: number }) => {
    if (battleTriggered.current || isEncounterActive) return;

    const { w: monsterW } = monsterDisplaySize(snap.type);

    battleTriggered.current = true;
    setEncounterPreview({
      monsterType: snap.type,
      playerLeft: charLeftRef.current - cameraXRef.current,
      monsterLeft: snap.x - monsterW / 2 - cameraXRef.current,
    });
  }, [isEncounterActive]);

  const confirmEncounter = useCallback(() => {
    if (!encounterPreview || !onBattle) return;
    onBattle(encounterPreview.monsterType);
  }, [encounterPreview, onBattle]);

  const cancelEncounter = useCallback(() => {
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
        const playerCenter = charLeftRef.current + CHAR_SIZE.w / 2;

        for (let i = 0; i < monsterRuntimes.length; i++) {
          const m = monsterRuntimes[i];

          // 1. Move
          let newX  = m.x + m.def.speed * m.direction;
          let newDir: 1 | -1 = m.direction;
          if (newX >= m.def.maxX) { newX = m.def.maxX; newDir = -1; }
          if (newX <= m.def.minX) { newX = m.def.minX; newDir =  1; }

          // 2. Collision
          const attacking = Math.abs(newX - playerCenter) < COLLISION_DIST;
          if (attacking && !battleTriggered.current) {
            const snap = { type: m.type, x: newX };
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
  }, [isEncounterActive, monsterRuntimes, startEncounter]);

  useEffect(() => {
    scrollToCharacter(CHAR_INIT_X);
  }, [scrollToCharacter]);

  // ── Render: stone platform ──────────────────────────────────────────────
  const renderGround = () => {
    const tiles = [];
    for (let row = 0; row < GROUND_ROWS; row++) {
      for (let col = 0; col < NUM_TILES; col++) {
        tiles.push(
          <Image
            key={`t-${row}-${col}`}
            source={HOA_LU_MAP_ASSETS.stone}
            style={{
              position: 'absolute',
              left:   col * (TILE_W - 1),
              top:    PLATFORM_TOP + row * TILE_H,
              width:  TILE_W + 1,
              height: TILE_H,
              zIndex: NUM_TILES - col,
            }}
            resizeMode="cover"
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
        hp={800}
        maxHp={1000}
        expPercent={45}
        zoneName="Khu 1"
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
              initialX={CHAR_INIT_X}
              groundY={PLATFORM_TOP}
              controlMode="tap-to-move"
              speed={CHAR_SPEED}
              scale={CHAR_SCALE}
              monsters={monsterTargets}
              minX={0}
              maxX={MAP_W}
              containerWidth={MAP_W}
              containerHeight={MAP_H}
              disabled={menuVisible}
              onMove={(x) => {
                charLeftRef.current = x;
                scrollToCharacter(x);
              }}
              onAttackMonster={(monsterId) => {
                if (battleTriggered.current) return;

                const targetMonster = monsterRuntimes.find((m) => String(m.id) === monsterId);
                if (!targetMonster) return;

                const snap = { type: targetMonster.type, x: targetMonster.x };
                setTimeout(() => startEncounter(snap), 180);
              }}
            />
          )}
        </View>
      </ScrollView>

      {encounterPreview && (
        <BattleIntroScreen
          monsterType={encounterPreview.monsterType}
          playerLeft={encounterPreview.playerLeft}
          monsterLeft={encounterPreview.monsterLeft}
          groundY={PLATFORM_TOP}
          playerScale={CHAR_SCALE}
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
        leftLabel={menuVisible ? 'Chọn' : 'Menu'}
        centerLabel={isEncounterActive ? 'Vào ngay' : undefined}
        rightLabel={isEncounterActive ? 'Hủy' : menuVisible ? 'Đóng' : 'Lùi'}
        onLeftPress={() => {
          if (isEncounterActive) return;
          setMenuVisible(prev => !prev);
        }}
        onRightPress={() => {
          if (isEncounterActive) {
            cancelEncounter();
            return;
          }
          if (menuVisible) {
            setMenuVisible(false);
            return;
          }
          onBack();
        }}
        onCenterPress={() => {
          if (isEncounterActive) {
            confirmEncounter();
            return;
          }
          if (onBattle) {
            const previewMonster =
              monsterRuntimes.find((m) => m.type === 'fire') ?? monsterRuntimes[0];
            if (previewMonster) {
              startEncounter({ type: previewMonster.type, x: previewMonster.x });
            }
          }
        }}
      />

    </View>
  );
};

// ── Styles ───────────────────────────────────────────────────────────────────
const styles = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#000' },

  scroll: { flex: 1 },
  bg: { position: 'absolute', top: 0, left: 0, width: MAP_W, height: MAP_H },
});
