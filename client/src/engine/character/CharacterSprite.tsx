import React from 'react';
import { View } from 'react-native';
import type { CharacterSpriteProps } from './character.types';
import {
  CONTENT_WIDTH,
  CONTENT_HEIGHT,
  DEFAULT_SCALE,
} from './character.constants';

const CharacterSpriteImpl: React.FC<CharacterSpriteProps> = ({
  frameIndex,
  facing,
  scale = DEFAULT_SCALE,
  placementPreset = 'default',
}) => {
  const displayW = CONTENT_WIDTH * scale;
  const displayH = CONTENT_HEIGHT * scale;
  const isLeft = facing === 'left';
  const activeFrame = frameIndex % 4;
  const isAttackWindup = activeFrame === 2;
  const isAttackSlash = activeFrame === 3;
  const isRunning = activeFrame === 1;
  const placementShiftY = placementPreset === 'battle' ? 1.5 * scale : 0;
  const swordRotation = isAttackWindup ? '-34deg' : isAttackSlash ? '28deg' : isRunning ? '10deg' : '-6deg';
  const weaponOffsetX = isAttackSlash ? 12 * scale : isAttackWindup ? -7 * scale : 0;
  const armOffsetY = isAttackWindup ? -4 * scale : isAttackSlash ? 3 * scale : 0;
  const legLift = isRunning ? 4 * scale : 0;

  return (
    <View
      style={{
        width: displayW,
        height: displayH,
        transform: [
          { translateY: placementShiftY },
          ...(isLeft ? [{ scaleX: -1 as const }] : []),
        ],
      }}
    >
      <View
        style={{
          position: 'absolute',
          left: 18 * scale,
          top: 8 * scale,
          width: 54 * scale,
          height: 32 * scale,
          borderRadius: 16 * scale,
          backgroundColor: '#151515',
          opacity: 0.18,
        }}
      >
        <View
          style={{
            flex: 1,
          }}
        />
      </View>

      <View
        style={{
          position: 'absolute',
          left: 28 * scale,
          top: 2 * scale,
          width: 20 * scale,
          height: 20 * scale,
          borderRadius: 10 * scale,
          backgroundColor: '#f1c27d',
          borderWidth: scale,
          borderColor: '#5a2d11',
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 25 * scale,
          top: 8 * scale,
          width: 26 * scale,
          height: 10 * scale,
          borderRadius: 6 * scale,
          backgroundColor: '#a72828',
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 24 * scale,
          top: 20 * scale,
          width: 28 * scale,
          height: 30 * scale,
          borderRadius: 7 * scale,
          backgroundColor: '#1f53a7',
          borderWidth: scale,
          borderColor: '#0c2553',
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 16 * scale,
          top: (24 * scale) + armOffsetY,
          width: 12 * scale,
          height: 7 * scale,
          borderRadius: 4 * scale,
          backgroundColor: '#f1c27d',
          transform: [{ rotate: '-12deg' }],
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: (45 * scale) + weaponOffsetX,
          top: (22 * scale) + armOffsetY,
          width: 13 * scale,
          height: 7 * scale,
          borderRadius: 4 * scale,
          backgroundColor: '#f1c27d',
          transform: [{ rotate: isAttackWindup ? '-24deg' : '18deg' }],
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 50 * scale + weaponOffsetX,
          top: 10 * scale + armOffsetY,
          width: 5 * scale,
          height: 30 * scale,
          borderRadius: 2 * scale,
          backgroundColor: '#d8dce3',
          borderWidth: Math.max(1, 0.5 * scale),
          borderColor: '#7d8796',
          transform: [{ rotate: swordRotation }],
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 47 * scale + weaponOffsetX,
          top: 26 * scale + armOffsetY,
          width: 10 * scale,
          height: 4 * scale,
          borderRadius: 2 * scale,
          backgroundColor: '#d7a53a',
          transform: [{ rotate: swordRotation }],
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 28 * scale,
          top: 48 * scale,
          width: 8 * scale,
          height: 22 * scale,
          borderRadius: 3 * scale,
          backgroundColor: '#623612',
          transform: [{ translateY: legLift }],
        }}
      />
      <View
        style={{
          position: 'absolute',
          left: 40 * scale,
          top: 48 * scale,
          width: 8 * scale,
          height: 22 * scale,
          borderRadius: 3 * scale,
          backgroundColor: '#623612',
          transform: [{ translateY: -legLift }],
        }}
      />
    </View>
  );
};

export const CharacterSprite = React.memo(CharacterSpriteImpl);

export function characterDisplaySize(scale: number = DEFAULT_SCALE) {
  return {
    w: CONTENT_WIDTH * scale,
    h: CONTENT_HEIGHT * scale,
  };
}
