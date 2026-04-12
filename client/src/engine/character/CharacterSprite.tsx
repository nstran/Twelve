/**
 * CharacterSprite.tsx
 *
 * Pure renderer for the player character sprite.
 * Shows 1 frame from the 4-frame sprite sheet (man.png).
 *
 * Layout: [0: idle] [1: run] [2: attack_windup] [3: attack_slash]
 *
 * Uses the same overflow-clip technique as MonsterSprite:
 * - Container clips to 1 frame width
 * - Image is translated to show the correct frame
 * - scaleX: -1 flips for facing direction
 *
 * The character sprite naturally faces RIGHT.
 * facingLeft applies scaleX: -1 to mirror.
 */

import React from 'react';
import { View, Image } from 'react-native';
import type { CharacterSpriteProps } from './character.types';
import {
  BATTLE_FRAME_OFFSETS,
  DEFAULT_FRAME_OFFSETS,
  FRAME_WIDTH,
  FRAME_HEIGHT,
  SHEET_WIDTH,
  FRAME_BOUNDS,
  CONTENT_LEFT,
  CONTENT_TOP,
  CONTENT_WIDTH,
  CONTENT_HEIGHT,
  DEFAULT_SCALE,
  SPRITE_SOURCE,
} from './character.constants';

export const CharacterSprite: React.FC<CharacterSpriteProps> = ({
  frameIndex,
  facing,
  scale = DEFAULT_SCALE,
  placementPreset = 'default',
}) => {
  const bounds = FRAME_BOUNDS[frameIndex] ?? FRAME_BOUNDS[0];
  const frameOffsets =
    placementPreset === 'battle' ? BATTLE_FRAME_OFFSETS : DEFAULT_FRAME_OFFSETS;
  const placement = frameOffsets[frameIndex] ?? frameOffsets[0];
  const cropW = (bounds.right - bounds.left + 1) * scale;
  const cropH = (bounds.bottom - bounds.top + 1) * scale;
  const cropLeft = ((bounds.left - CONTENT_LEFT) + placement.x) * scale;
  const cropTop = ((bounds.top - CONTENT_TOP) + placement.y) * scale;
  const displayW = CONTENT_WIDTH * scale;
  const displayH = CONTENT_HEIGHT * scale;
  const sheetDisplayW = SHEET_WIDTH * scale;
  const sheetDisplayH = FRAME_HEIGHT * scale;

  const offsetX = -((frameIndex * FRAME_WIDTH) + bounds.left) * scale;
  const offsetY = -(bounds.top * scale);

  const isLeft = facing === 'left';

  return (
    <View
      style={{
        width: displayW,
        height: displayH,
        // Sprite faces right by default → flip when facing left
        transform: isLeft ? [{ scaleX: -1 }] : undefined,
      }}
    >
      <View
        style={{
          position: 'absolute',
          left: cropLeft,
          top: cropTop,
          width: cropW,
          height: cropH,
          overflow: 'hidden',
        }}
      >
        <Image
          source={SPRITE_SOURCE}
          style={{
            width: sheetDisplayW,
            height: sheetDisplayH,
            transform: [
              { translateX: offsetX },
              { translateY: offsetY },
            ],
          }}
          resizeMode="stretch"
        />
      </View>
    </View>
  );
};

// ── Helper: get display size for external layout calculations ─────────────
export function characterDisplaySize(scale: number = DEFAULT_SCALE) {
  return {
    w: CONTENT_WIDTH * scale,
    h: CONTENT_HEIGHT * scale,
  };
}
