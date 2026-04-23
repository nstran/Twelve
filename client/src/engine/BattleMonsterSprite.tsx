import React from 'react';
import { Image, View } from 'react-native';
import {
  MonsterSprite,
  monsterDisplaySize as getSharedMonsterDisplaySize,
  monsterPlacementMetrics as getSharedMonsterPlacementMetrics,
  type MonsterType,
} from './MonsterSprite';
import { getBattleMonsterAssetSpec, type BattleMonsterPoseKey } from './BattleMonsterAssetManifest';

export const battleMonsterDisplaySize = (
  monsterType: MonsterType,
  assetCatalogId?: string | null,
) => {
  const spec = getBattleMonsterAssetSpec(assetCatalogId);
  if (!spec) {
    return getSharedMonsterDisplaySize(monsterType);
  }

  return {
    w: Math.round(spec.frameWidth * spec.displayScale),
    h: Math.round(spec.frameHeight * spec.displayScale),
    groundOffset: Math.round(spec.groundOffset * spec.displayScale),
  };
};

export const battleMonsterPlacementMetrics = (
  monsterType: MonsterType,
  assetCatalogId?: string | null,
) => {
  const spec = getBattleMonsterAssetSpec(assetCatalogId);
  if (!spec) {
    return getSharedMonsterPlacementMetrics(monsterType);
  }

  return {
    groundOffset: Math.round((spec.groundOffset + spec.visualFootSink) * spec.displayScale),
  };
};

type BattleMonsterSpriteProps = {
  assetCatalogId?: string | null;
  fallbackType: MonsterType;
  facingRight: boolean;
  frameIndex: number;
  poseKey?: BattleMonsterPoseKey;
};

const getFallbackFrameIndex = (poseKey?: BattleMonsterPoseKey, fallbackFrameIndex = 0) => {
  switch (poseKey) {
    case 'idle_a':
      return 0;
    case 'idle_b':
      return 4;
    case 'idle_c':
      return 5;
    case 'prepare_attack':
      return 2;
    case 'run_attack':
      return 3;
    case 'attack':
      return 1;
    case 'hit':
      return 2;
    default:
      return fallbackFrameIndex;
  }
};

export const BattleMonsterSprite: React.FC<BattleMonsterSpriteProps> = ({
  assetCatalogId,
  fallbackType,
  facingRight,
  frameIndex,
  poseKey,
}) => {
  const spec = getBattleMonsterAssetSpec(assetCatalogId);
  if (!spec) {
    return (
      <MonsterSprite
        type={fallbackType}
        frameIndex={getFallbackFrameIndex(poseKey, frameIndex)}
        facingRight={facingRight}
      />
    );
  }

  const totalFrames = spec.chunks.reduce((sum, chunk) => sum + chunk.frameCount, 0);
  if (totalFrames <= 0) {
    return <MonsterSprite type={fallbackType} frameIndex={frameIndex} facingRight={facingRight} />;
  }

  let activeChunk = spec.chunks[0];
  let remaining = 0;
  const mappedPose = poseKey ? spec.poses?.[poseKey] : null;
  const mappedLegacyFrame = spec.legacyFrameMap?.[frameIndex];

  if (mappedPose) {
    activeChunk = spec.chunks[mappedPose.chunkIndex] ?? spec.chunks[0];
    remaining = Math.min(
      Math.max(mappedPose.frameOffset, 0),
      Math.max(0, activeChunk.frameCount - 1),
    );
  } else if (mappedLegacyFrame) {
    activeChunk = spec.chunks[mappedLegacyFrame.chunkIndex] ?? spec.chunks[0];
    remaining = Math.min(
      Math.max(mappedLegacyFrame.frameOffset, 0),
      Math.max(0, activeChunk.frameCount - 1),
    );
  } else {
    const normalizedFrameIndex = ((frameIndex % totalFrames) + totalFrames) % totalFrames;
    remaining = normalizedFrameIndex;

    for (const chunk of spec.chunks) {
      if (remaining < chunk.frameCount) {
        activeChunk = chunk;
        break;
      }

      remaining -= chunk.frameCount;
    }
  }

  const displayWidth = Math.round(spec.frameWidth * spec.displayScale);
  const displayHeight = Math.round(spec.frameHeight * spec.displayScale);
  const chunkDisplayWidth = displayWidth * activeChunk.frameCount;
  const shouldFlip = facingRight !== spec.baseFacingRight;

  return (
    <View
      style={{
        width: displayWidth,
        height: displayHeight,
        overflow: 'hidden',
        transform: shouldFlip ? [{ scaleX: -1 }] : undefined,
      }}
    >
      <Image
        source={activeChunk.source}
        resizeMode="stretch"
        style={{
          width: chunkDisplayWidth,
          height: displayHeight,
          transform: [{ translateX: -(remaining * displayWidth) }],
        }}
      />
    </View>
  );
};
