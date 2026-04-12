import React, { useRef, useState, useCallback, useEffect, useMemo } from 'react';
import {
  View, Image, ScrollView,
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

// ── Monster state (runtime) ─────────────────────────────────────────────────
interface MonsterState extends MonsterDef {
  x: number;          // center X hiện tại
  direction: 1 | -1;  // 1=phải, -1=trái
  frameIndex: number; // 0-5
  attacking: boolean;
  tickCount: number;  // đếm tick để cycle frame
}

// Khoảng cách tính là "va chạm" (px từ center-to-center)
const COLLISION_DIST = 52;
// Mỗi bao nhiêu tick thì đổi frame (tick=50ms, FRAME_TICKS=4 → 80ms/frame ≈ 12fps anim)
const FRAME_TICKS = 4;

// ── Build initial state ──────────────────────────────────────────────────────
function buildInitialMonsters(): MonsterState[] {
  return MONSTER_DEFS.map(def => ({
    ...def,
    x: def.startX,
    direction: 1,
    frameIndex: WALK_FRAMES[0],
    attacking: false,
    tickCount: 0,
  }));
}

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
  const [encounterPreview, setEncounterPreview] = useState<EncounterPreviewState | null>(null);

  // Danh sách quái (state → trigger re-render mỗi tick)
  const [monsters, setMonsters] = useState<MonsterState[]>(buildInitialMonsters);

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
    cameraXRef.current = nextScrollX;
    scrollRef.current?.scrollTo({ x: nextScrollX, animated: false });
  }, []);

  const monsterTargets = useMemo<MonsterTarget[]>(() => (
    monsters.map((monster) => {
      const { w, h, groundOffset } = monsterDisplaySize(monster.type);
      return {
        id: String(monster.id),
        x: monster.x - w / 2,
        y: PLATFORM_TOP - h + groundOffset,
        width: w,
        height: h,
      };
    })
  ), [monsters]);

  const isEncounterActive = encounterPreview !== null;

  const startEncounter = useCallback((monster: MonsterState) => {
    if (battleTriggered.current || isEncounterActive) return;

    const { w: monsterW } = monsterDisplaySize(monster.type);

    battleTriggered.current = true;
    setEncounterPreview({
      monsterType: monster.type,
      playerLeft: charLeftRef.current - cameraXRef.current,
      monsterLeft: monster.x - monsterW / 2 - cameraXRef.current,
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

  // ── Game loop: quái di chuyển 20fps ─────────────────────────────────────
  useEffect(() => {
    const TICK_MS = 50; // 20fps

    const loop = setInterval(() => {
      if (isEncounterActive) return;

      const playerCenter = charLeftRef.current + CHAR_SIZE.w / 2;

      setMonsters(prev => prev.map(m => {
        // Di chuyển
        let newX  = m.x + m.speed * m.direction;
        let newDir = m.direction;
        if (newX >= m.maxX) { newX = m.maxX; newDir = -1; }
        if (newX <= m.minX) { newX = m.minX; newDir =  1; }

        // Kiểm tra va chạm với nhân vật
        const attacking = Math.abs(newX - playerCenter) < COLLISION_DIST;

        // Khi quái va chạm → vào màn hình trận đấu (chỉ trigger 1 lần)
        if (attacking && !battleTriggered.current) {
          setTimeout(() => startEncounter(m), 120);
        }

        // Cycle frame
        const newTick = m.tickCount + 1;
        const frames  = attacking ? ATTACK_FRAMES : WALK_FRAMES;
        const step    = Math.floor(newTick / FRAME_TICKS) % frames.length;

        return {
          ...m,
          x:          newX,
          direction:  newDir,
          attacking,
          tickCount:  newTick,
          frameIndex: frames[step],
        };
      }));
    }, TICK_MS);

    return () => clearInterval(loop);
  }, [isEncounterActive, startEncounter]);

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

  // ── Render: monsters ────────────────────────────────────────────────────
  const renderMonsters = () =>
    monsters.map(m => {
      const { w: mW, h: mH, groundOffset } = monsterDisplaySize(m.type);
      return (
        <View
          key={m.id}
          style={{
            position: 'absolute',
            left:   m.x - mW / 2,
            // groundOffset bù phần trong suốt dưới sprite → chân chạm đúng mặt đất
            top:    PLATFORM_TOP - mH + groundOffset,
            width:  mW,
            height: mH,
            zIndex: 8,
          }}
        >
          <MonsterSprite
            type={m.type}
            frameIndex={m.frameIndex}
            facingRight={m.direction === 1}
          />
        </View>
      );
    });

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
          {!isEncounterActive && renderMonsters()}

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

                const targetMonster = monsters.find((monster) => String(monster.id) === monsterId);
                if (!targetMonster) return;

                setTimeout(() => startEncounter(targetMonster), 180);
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
        leftIcon={
          menuVisible
            ? require('../../../../assets/ui/icons/icon_ok.png')
            : require('../../../../assets/ui/icons/icon_sharpest_1.png')
        }
        rightIcon={
          isEncounterActive
            ? undefined
            : menuVisible
            ? require('../../../../assets/ui/icons/icon_cancel.png')
            : require('../../../../assets/ui/icons/icon_fixed_2.png')
        }
        centerLabel={isEncounterActive ? 'Vào ngay' : undefined}
        rightLabel={isEncounterActive ? 'Hủy' : undefined}
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
            const previewMonster = monsters.find((monster) => monster.type === 'fire') ?? monsters[0];
            if (previewMonster) {
              startEncounter(previewMonster);
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
