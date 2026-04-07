/**
 * MonsterSprite.tsx
 * Hiển thị sprite animation cho 1 con quái từ sprite sheet 6 frame.
 *
 * Layout sprite sheet (6 frame, trái → phải):
 *   [0: walk_A] [1: atk_1] [2: atk_2] [3: atk_3] [4: walk_B] [5: walk_C]
 *
 * Walk animation  : frame 0 → 4 → 5 (lặp)
 * Attack animation: frame 1 → 2 → 3 (lặp)
 */

import React from 'react';
import { View, Image } from 'react-native';

// ── Kiểu quái ─────────────────────────────────────────────────────────────
export type MonsterType = 'fire' | 'ice' | 'zap';

// ── Thông số sprite sheet gốc (pixel) ─────────────────────────────────────
interface SpriteSpec {
  totalW: number;  // chiều rộng toàn bộ sheet
  frameW: number;  // chiều rộng 1 frame = totalW / 6
  frameH: number;  // chiều cao sheet (= chiều cao 1 frame)
  // px bù xuống dưới để chân sprite khớp đúng mặt đất
  // (sprite có vùng trong suốt phía dưới khác nhau mỗi loại)
  groundOffset: number;
  source: ReturnType<typeof require>;
}

const SPECS: Record<MonsterType, SpriteSpec> = {
  fire: {
    totalW: 246, frameW: 41, frameH: 55,
    groundOffset: 12,  // fireball có khoảng trống đáy ~12px
    source: require('../../assets/monsters/fire.png'),
  },
  ice: {
    totalW: 216, frameW: 36, frameH: 52,
    groundOffset: 10,
    source: require('../../assets/monsters/ice.png'),
  },
  zap: {
    totalW: 222, frameW: 37, frameH: 37,
    groundOffset: 6,
    source: require('../../assets/monsters/zap.png'),
  },
};

// ── Frame sequence ─────────────────────────────────────────────────────────
export const WALK_FRAMES   = [0, 4, 5] as const; // di chuyển bình thường
export const ATTACK_FRAMES = [1, 2, 3] as const; // tấn công / va chạm

// Scale hiển thị — nhân để quái to vừa nhìn
const DISPLAY_SCALE = 1.8;

// ── Props ──────────────────────────────────────────────────────────────────
interface Props {
  type: MonsterType;
  /** 0–5: index frame cần hiện */
  frameIndex: number;
  /** true = nhìn sang phải, false = lật trái */
  facingRight: boolean;
}

// ── Component ──────────────────────────────────────────────────────────────
export const MonsterSprite: React.FC<Props> = ({ type, frameIndex, facingRight }) => {
  const spec   = SPECS[type];
  const dispW  = Math.round(spec.frameW  * DISPLAY_SCALE); // chiều rộng 1 frame hiển thị
  const dispH  = Math.round(spec.frameH  * DISPLAY_SCALE); // chiều cao hiển thị
  const sheetW = Math.round(spec.totalW  * DISPLAY_SCALE); // chiều rộng toàn sheet hiển thị

  // Dịch sheet sang trái để cửa sổ "nhìn" đúng frame
  const offsetX = -(frameIndex * dispW);

  return (
    <View
      style={{
        width:    dispW,
        height:   dispH,
        overflow: 'hidden',
        // Sprite gốc mặc định nhìn TRÁI → flip khi đi sang PHẢI
        transform: facingRight ? [{ scaleX: -1 }] : undefined,
      }}
    >
      <Image
        source={spec.source}
        style={{
          width:  sheetW,
          height: dispH,
          transform: [{ translateX: offsetX }],
        }}
        resizeMode="stretch"
      />
    </View>
  );
};

// ── Helper: kích thước + ground offset (dùng để căn tọa độ bên ngoài) ─────
export function monsterDisplaySize(type: MonsterType) {
  const s = SPECS[type];
  return {
    w:            Math.round(s.frameW * DISPLAY_SCALE),
    h:            Math.round(s.frameH * DISPLAY_SCALE),
    // Bù thêm vào top để chân quái chạm đúng mặt đất
    groundOffset: Math.round(s.groundOffset * DISPLAY_SCALE),
  };
}
