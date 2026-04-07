import React, { useRef, useState, useCallback } from 'react';
import {
  View,
  Image,
  ScrollView,
  Animated,
  StyleSheet,
  Text,
  TouchableOpacity,
  Dimensions,
  Pressable,
} from 'react-native';

const { width: SCREEN_W, height: SCREEN_H } = Dimensions.get('window');

// ── Map / Background ────────────────────────────────────────────────────────
// bgmap_hoa_lu.png is 1536×1024 — scale proportionally to fill screen height
const MAP_NATIVE_W = 1536;
const MAP_NATIVE_H = 1024;
const MAP_SCALE    = SCREEN_H / MAP_NATIVE_H;   // e.g. 844/1024 ≈ 0.824
const MAP_W        = Math.round(MAP_NATIVE_W * MAP_SCALE); // rendered pixel width of map
const MAP_H        = SCREEN_H;

// ── Stone ground tile ───────────────────────────────────────────────────────
// stone.png is 96×32 — dùng kích thước gốc (không scale) để tile rõ nét và liền nhau
const TILE_W = 96;  // native width — no rounding gap between tiles
const TILE_H = 32;  // native height
const NUM_TILES = Math.ceil(MAP_W / (TILE_W - 1)) + 2; // đủ tile kể cả khi step = TILE_W-1

// ── Platform position ───────────────────────────────────────────────────────
// 1 dải đất duy nhất, lơ lửng ở ~72% chiều cao màn hình
const GROUND_ROWS  = 1;                              // CHỈ 1 hàng — dải đất mỏng lơ lửng
const PLATFORM_TOP = Math.round(SCREEN_H * 0.72);   // Y của mặt trên dải đất

// ── Player character ────────────────────────────────────────────────────────
// Nguoi.png is 435×923 — display at 44px wide, maintain aspect ratio
const CHAR_W     = 44;
const CHAR_H     = Math.round(CHAR_W * (923 / 435)); // ≈ 93 px tall
const CHAR_INIT_X = Math.round(MAP_W * 0.08);         // start near left

interface Props {
  onBack: () => void;
}

export const HoaLuMapScreen: React.FC<Props> = ({ onBack }) => {
  const scrollRef = useRef<ScrollView>(null);

  // charLeft = left edge of character in MAP coordinates
  const charLeft = useRef(new Animated.Value(CHAR_INIT_X)).current;
  const charLeftVal = useRef(CHAR_INIT_X);

  const [facingRight, setFacingRight] = useState(true);

  // Move character to MAP-space X coordinate (center of character)
  const moveTo = useCallback((mapX: number) => {
    const targetLeft = Math.max(0, Math.min(mapX - CHAR_W / 2, MAP_W - CHAR_W));

    const direction = targetLeft >= charLeftVal.current;
    setFacingRight(direction);

    const distance = Math.abs(targetLeft - charLeftVal.current);
    const duration = Math.max(150, Math.min(distance * 1.8, 1200));

    charLeftVal.current = targetLeft;

    Animated.timing(charLeft, {
      toValue: targetLeft,
      duration,
      useNativeDriver: true,
    }).start(() => {
      // After moving, scroll camera to keep character in center of viewport
      const camTarget = targetLeft + CHAR_W / 2 - SCREEN_W / 2;
      scrollRef.current?.scrollTo({ x: Math.max(0, camTarget), animated: true });
    });
  }, [charLeft]);

  // locationX inside the ScrollView's content View is already in MAP coordinates
  const handleMapPress = useCallback((mapX: number) => {
    moveTo(mapX);
  }, [moveTo]);

  // Stone tile images (1 row = grass surface, remaining rows = depth fill)
  const renderStonePlatform = () => {
    const rows = [];
    for (let row = 0; row < GROUND_ROWS; row++) {
      for (let col = 0; col < NUM_TILES; col++) {
        rows.push(
          <Image
            key={`tile-r${row}-c${col}`}
            source={require('../../assets/createcs/stone.png')}
            style={{
              position: 'absolute',
              // Mỗi tile sau lùi vào 1px so với tile trước → overlap, không còn khe
              left: col * (TILE_W - 15),
              top: PLATFORM_TOP + row * TILE_H,
              // Mở rộng thêm 1px để chắc chắn che hết phần tiếp giáp
              width: TILE_W + 1,
              height: TILE_H,
              // Tile trước (col nhỏ) có zIndex cao hơn → phủ lên tile sau
              zIndex: NUM_TILES - col,
            }}
            resizeMode="cover"
          />
        );
      }
    }
    return rows;
  };

  return (
    <View style={styles.root}>

      {/* ── HUD: top bar ─────────────────────────────────────────────── */}
      <View style={styles.hudTop} pointerEvents="none">
        <View style={styles.hpBlock}>
          <Text style={styles.charName}>Hoa Lư</Text>
          <View style={styles.hpBg}>
            <View style={[styles.hpFill, { width: '80%' }]} />
          </View>
        </View>
        <Text style={styles.zoneName}>Hoa Lư 1</Text>
      </View>

      {/* ── Scrollable map ────────────────────────────────────────────── */}
      <ScrollView
        ref={scrollRef}
        horizontal
        showsHorizontalScrollIndicator={false}
        scrollEventThrottle={16}
        style={styles.scroll}
        contentContainerStyle={{ width: MAP_W, height: MAP_H }}
        // Allow taps to pass through to the Pressable below
        keyboardShouldPersistTaps="handled"
      >
        <Pressable
          style={{ width: MAP_W, height: MAP_H }}
          onPress={(e) => handleMapPress(e.nativeEvent.locationX)}
        >
          {/* Background */}
          <Image
            source={require('../../assets/m/bgmap_hoa_lu.png')}
            style={styles.bg}
            resizeMode="stretch"
          />

          {/* Ground: stone tiles tiled horizontally */}
          {renderStonePlatform()}

          {/* Player character */}
          <Animated.View
            style={[
              styles.charContainer,
              {
                top: PLATFORM_TOP - CHAR_H,
                transform: [{ translateX: charLeft }],
              },
            ]}
          >
            <Image
              source={require('../../assets/character/Nguoi.png')}
              style={[
                styles.charImg,
                // flip sprite when walking left
                !facingRight && { transform: [{ scaleX: -1 }] },
              ]}
              resizeMode="contain"
            />
          </Animated.View>

        </Pressable>
      </ScrollView>

      {/* ── HUD: bottom softkey bar ───────────────────────────────────── */}
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

const styles = StyleSheet.create({
  root: {
    flex: 1,
    backgroundColor: '#000',
  },

  // ── HUD top ──────────────────────────────────────────────────────────────
  hudTop: {
    position: 'absolute',
    top: 0, left: 0, right: 0,
    zIndex: 20,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 12,
    paddingTop: 6,
    paddingBottom: 6,
    backgroundColor: 'rgba(0,0,0,0.50)',
  },
  hpBlock: { flex: 1, marginRight: 16 },
  charName: {
    color: '#FFD700',
    fontSize: 11,
    fontWeight: 'bold',
    marginBottom: 2,
    letterSpacing: 1,
  },
  hpBg: {
    height: 7,
    backgroundColor: '#3a0000',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#800',
  },
  hpFill: {
    height: '100%',
    backgroundColor: '#e74c3c',
    borderRadius: 4,
  },
  zoneName: {
    color: '#ffffff',
    fontSize: 11,
    fontWeight: '600',
    letterSpacing: 1,
  },

  // ── Map scroll ────────────────────────────────────────────────────────────
  scroll: {
    flex: 1,
  },
  bg: {
    position: 'absolute',
    top: 0, left: 0,
    width: MAP_W,
    height: MAP_H,
  },

  // ── Character ─────────────────────────────────────────────────────────────
  charContainer: {
    position: 'absolute',
    width: CHAR_W,
    height: CHAR_H,
  },
  charImg: {
    width: CHAR_W,
    height: CHAR_H,
  },

  // ── HUD bottom ────────────────────────────────────────────────────────────
  hudBottom: {
    position: 'absolute',
    bottom: 0, left: 0, right: 0,
    zIndex: 20,
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 8,
    backgroundColor: 'rgba(0,0,0,0.65)',
    borderTopWidth: 1,
    borderTopColor: '#333',
  },
  btn: {
    paddingHorizontal: 18,
    paddingVertical: 8,
    backgroundColor: 'rgba(40,40,60,0.9)',
    borderRadius: 6,
    borderWidth: 1,
    borderColor: '#555',
  },
  btnEnter: {
    backgroundColor: 'rgba(120,20,20,0.95)',
    borderColor: '#a00',
  },
  btnTxt: {
    color: '#fff',
    fontSize: 13,
    fontWeight: 'bold',
  },
});
