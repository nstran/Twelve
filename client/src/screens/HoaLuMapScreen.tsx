import React, { useRef, useState, useCallback, useEffect } from 'react';
import {
  View, Image, ScrollView, Animated,
  StyleSheet, Text, TouchableOpacity, Dimensions, Pressable,
} from 'react-native';
import {
  MonsterSprite, MonsterType,
  WALK_FRAMES, ATTACK_FRAMES, monsterDisplaySize,
} from '../engine/MonsterSprite';

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
const CHAR_W      = 72;
const CHAR_H      = Math.round(CHAR_W * (1077 / 990)); // Full.png ≈ 78px
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
  onBack: () => void;
}

// ═══════════════════════════════════════════════════════════════════════════
export const HoaLuMapScreen: React.FC<Props> = ({ onBack }) => {
  const scrollRef = useRef<ScrollView>(null);

  // Vị trí nhân vật (Animated cho mượt, ref cho game logic)
  const charLeft    = useRef(new Animated.Value(CHAR_INIT_X)).current;
  const charLeftVal = useRef(CHAR_INIT_X);
  const [facingRight, setFacingRight] = useState(true);

  // Danh sách quái (state → trigger re-render mỗi tick)
  const [monsters, setMonsters] = useState<MonsterState[]>(buildInitialMonsters);

  // ── Game loop: quái di chuyển 20fps ─────────────────────────────────────
  useEffect(() => {
    const TICK_MS = 50; // 20fps

    const loop = setInterval(() => {
      const playerCenter = charLeftVal.current + CHAR_W / 2;

      setMonsters(prev => prev.map(m => {
        // Di chuyển
        let newX  = m.x + m.speed * m.direction;
        let newDir = m.direction;
        if (newX >= m.maxX) { newX = m.maxX; newDir = -1; }
        if (newX <= m.minX) { newX = m.minX; newDir =  1; }

        // Kiểm tra va chạm với nhân vật
        const attacking = Math.abs(newX - playerCenter) < COLLISION_DIST;

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
  }, []); // chạy 1 lần khi mount

  // ── Di chuyển nhân vật ───────────────────────────────────────────────────
  const moveTo = useCallback((mapX: number) => {
    const targetLeft = Math.max(0, Math.min(mapX - CHAR_W / 2, MAP_W - CHAR_W));
    setFacingRight(targetLeft >= charLeftVal.current);
    const distance = Math.abs(targetLeft - charLeftVal.current);
    charLeftVal.current = targetLeft;
    Animated.timing(charLeft, {
      toValue:        targetLeft,
      duration:       Math.max(150, Math.min(distance * 1.8, 1200)),
      useNativeDriver: true,
    }).start(() => {
      const camTarget = targetLeft + CHAR_W / 2 - SCREEN_W / 2;
      scrollRef.current?.scrollTo({ x: Math.max(0, camTarget), animated: true });
    });
  }, [charLeft]);

  // ── Render: stone platform ──────────────────────────────────────────────
  const renderGround = () => {
    const tiles = [];
    for (let row = 0; row < GROUND_ROWS; row++) {
      for (let col = 0; col < NUM_TILES; col++) {
        tiles.push(
          <Image
            key={`t-${row}-${col}`}
            source={require('../../assets/createcs/stone.png')}
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

      {/* HUD top */}
      <View style={styles.hudTop} pointerEvents="none">
        <View style={styles.hpBlock}>
          <Text style={styles.charName}>Hoa Lư</Text>
          <View style={styles.hpBg}>
            <View style={[styles.hpFill, { width: '80%' }]} />
          </View>
        </View>
        <Text style={styles.zoneName}>Hoa Lư 1</Text>
      </View>

      {/* Scrollable map */}
      <ScrollView
        ref={scrollRef}
        horizontal
        showsHorizontalScrollIndicator={false}
        scrollEventThrottle={16}
        style={styles.scroll}
        contentContainerStyle={{ width: MAP_W, height: MAP_H }}
        keyboardShouldPersistTaps="handled"
      >
        <Pressable
          style={{ width: MAP_W, height: MAP_H }}
          onPress={e => moveTo(e.nativeEvent.locationX)}
        >
          {/* Layer 0: Background */}
          <Image
            source={require('../../assets/m/bgmap_hoa_lu.png')}
            style={styles.bg}
            resizeMode="stretch"
          />

          {/* Layer 1: Đất */}
          {renderGround()}

          {/* Layer 2: Quái vật */}
          {renderMonsters()}

          {/* Layer 3: Nhân vật */}
          <Animated.View
            style={[
              styles.charContainer,
              { top: PLATFORM_TOP - CHAR_H, transform: [{ translateX: charLeft }] },
            ]}
          >
            <Image
              source={require('../../assets/character/Full.png')}
              style={[styles.charImg, !facingRight && { transform: [{ scaleX: -1 }] }]}
              resizeMode="contain"
            />
          </Animated.View>

        </Pressable>
      </ScrollView>

      {/* HUD bottom */}
      <View style={styles.hudBottom}>
        <TouchableOpacity style={styles.btn} onPress={onBack}>
          <Text style={styles.btnTxt}>◀ Quay lại</Text>
        </TouchableOpacity>
        <TouchableOpacity style={[styles.btn, styles.btnEnter]}>
          <Text style={styles.btnTxt}>⚔ Vào Trận</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.btn}>
          <Text style={styles.btnTxt}>☰ Menu</Text>
        </TouchableOpacity>
      </View>

    </View>
  );
};

// ── Styles ───────────────────────────────────────────────────────────────────
const styles = StyleSheet.create({
  root: { flex: 1, backgroundColor: '#000' },

  hudTop: {
    position: 'absolute', top: 0, left: 0, right: 0, zIndex: 20,
    flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between',
    paddingHorizontal: 12, paddingVertical: 6,
    backgroundColor: 'rgba(0,0,0,0.50)',
  },
  hpBlock:  { flex: 1, marginRight: 16 },
  charName: { color: '#FFD700', fontSize: 11, fontWeight: 'bold', marginBottom: 2, letterSpacing: 1 },
  hpBg:     { height: 7, backgroundColor: '#3a0000', borderRadius: 4, borderWidth: 1, borderColor: '#800' },
  hpFill:   { height: '100%', backgroundColor: '#e74c3c', borderRadius: 4 },
  zoneName: { color: '#fff', fontSize: 11, fontWeight: '600', letterSpacing: 1 },

  scroll: { flex: 1 },
  bg: { position: 'absolute', top: 0, left: 0, width: MAP_W, height: MAP_H },

  charContainer: { position: 'absolute', width: CHAR_W, height: CHAR_H },
  charImg:        { width: CHAR_W, height: CHAR_H },

  hudBottom: {
    position: 'absolute', bottom: 0, left: 0, right: 0, zIndex: 20,
    flexDirection: 'row', justifyContent: 'space-around', alignItems: 'center',
    paddingHorizontal: 8, paddingVertical: 8,
    backgroundColor: 'rgba(0,0,0,0.65)',
    borderTopWidth: 1, borderTopColor: '#333',
  },
  btn: {
    paddingHorizontal: 18, paddingVertical: 8,
    backgroundColor: 'rgba(40,40,60,0.9)',
    borderRadius: 6, borderWidth: 1, borderColor: '#555',
  },
  btnEnter: { backgroundColor: 'rgba(120,20,20,0.95)', borderColor: '#a00' },
  btnTxt:   { color: '#fff', fontSize: 13, fontWeight: 'bold' },
});
