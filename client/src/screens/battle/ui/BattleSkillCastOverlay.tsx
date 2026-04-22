import React from 'react';
import { Image, Image as RNImage, View } from 'react-native';
import {
  BATTLE_SKILLS,
  type ActiveBattleSkillCast,
  type BattleSkillDefinition,
  type SkillFamilyCode,
} from '../core';

const ASSET_BLOOD_THROW = require('../../../../assets/battle/10_hit_effects/bloodthrowaround.png');

const FRAME_TICK_MS = 40;

type VolleyRenderConfig = {
  includeActorTarget: boolean;
  includeCellTargets: boolean;
  twinImpact: boolean;
  startDx: number;
  startDy: number;
  volleyDelayMs: number;
};

const VOLLEY_CONFIG: Partial<Record<SkillFamilyCode, VolleyRenderConfig>> = {
  1000: { includeActorTarget: true, includeCellTargets: true, twinImpact: false, startDx: -180, startDy: -180, volleyDelayMs: 72 },
  1006: { includeActorTarget: true, includeCellTargets: true, twinImpact: true, startDx: -180, startDy: -180, volleyDelayMs: 52 },
  2003: { includeActorTarget: true, includeCellTargets: false, twinImpact: false, startDx: -180, startDy: -180, volleyDelayMs: 0 },
  4000: { includeActorTarget: true, includeCellTargets: true, twinImpact: false, startDx: -180, startDy: -180, volleyDelayMs: 72 },
  4006: { includeActorTarget: true, includeCellTargets: true, twinImpact: false, startDx: 0, startDy: -180, volleyDelayMs: 80 },
  4008: { includeActorTarget: true, includeCellTargets: true, twinImpact: false, startDx: -180, startDy: -180, volleyDelayMs: 96 },
};

const IMPACT_FRAME_COUNTS: Partial<Record<SkillFamilyCode, number>> = {
  1000: 5,
  1006: 4,
  2003: 4,
  4000: 3,
  4006: 3,
  4008: 3,
};

const clamp01 = (value: number) => Math.max(0, Math.min(1, value));
const lerp = (a: number, b: number, t: number) => a + (b - a) * t;
const easeOutQuad = (t: number) => 1 - (1 - t) * (1 - t);

const runtimePrimary = (skill: BattleSkillDefinition) => skill.runtimeFrames[0] ?? skill.icon;
const runtimeSecondary = (skill: BattleSkillDefinition) => skill.runtimeFrames[1] ?? skill.runtimeFrames[0] ?? skill.icon;

const sheetMetrics = (source: any, frameCount: number) => {
  const safeFrameCount = Math.max(1, frameCount);
  let meta: { width: number; height: number } | null = null;

  try {
    meta = RNImage.resolveAssetSource(source as any) ?? null;
  } catch {
    meta = null;
  }

  const width = meta?.width ?? 64 * safeFrameCount;
  const height = meta?.height ?? 64;

  return {
    frameWidth: width / safeFrameCount,
    frameHeight: height,
    sheetWidth: width,
  };
};

const intrinsicSize = (source: any) => {
  const metrics = sheetMetrics(source, 1);
  return { width: metrics.frameWidth, height: metrics.frameHeight };
};

const frameAt = (t: number, frameCount: number) =>
  Math.max(0, Math.min(frameCount - 1, Math.floor(clamp01(t) * frameCount)));

const Sprite: React.FC<{
  source: any;
  left: number;
  top: number;
  width: number;
  height: number;
  opacity?: number;
  flipX?: boolean;
}> = ({ source, left, top, width, height, opacity = 1, flipX = false }) => (
  <Image
    source={source}
    resizeMode="stretch"
    style={{
      position: 'absolute',
      left,
      top,
      width,
      height,
      opacity,
      transform: [{ scaleX: flipX ? -1 : 1 }],
    }}
  />
);

const SheetFrame: React.FC<{
  source: any;
  frameCount: number;
  frameIndex: number;
  x: number;
  y: number;
  opacity?: number;
  flipX?: boolean;
}> = ({ source, frameCount, frameIndex, x, y, opacity = 1, flipX = false }) => {
  const metrics = sheetMetrics(source, frameCount);

  return (
    <View
      style={{
        position: 'absolute',
        left: x - metrics.frameWidth / 2,
        top: y - metrics.frameHeight / 2,
        width: metrics.frameWidth,
        height: metrics.frameHeight,
        overflow: 'hidden',
        opacity,
        transform: [{ scaleX: flipX ? -1 : 1 }],
      }}
    >
      <Image
        source={source}
        resizeMode="stretch"
        style={{
          position: 'absolute',
          left: -metrics.frameWidth * frameIndex,
          top: 0,
          width: metrics.sheetWidth,
          height: metrics.frameHeight,
        }}
      />
    </View>
  );
};

const renderVolleyFamily = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const config = VOLLEY_CONFIG[cast.familyCode];
  const impactFrameCount = IMPACT_FRAME_COUNTS[cast.familyCode];
  if (!config || !impactFrameCount) return null;
  const projectileSource = runtimePrimary(skill);
  const impactSource = runtimeSecondary(skill);
  const projectileSize = intrinsicSize(projectileSource);
  const renderSingleVolley = (key: string, point: { x: number; y: number }, localElapsedMs: number) => {
    const flightT = clamp01(localElapsedMs / 520);
    if (flightT <= 0 || flightT >= 1.2) return null;

    const startX = point.x + config.startDx;
    const startY = point.y + config.startDy;
    const flipX = startX < point.x;
    const projectileX = lerp(startX, point.x, flightT);
    const projectileY = lerp(startY, point.y, easeOutQuad(flightT));
    const impactT = clamp01((localElapsedMs - 380) / 220);
    const impactFrameIndex = frameAt(impactT, impactFrameCount);

    return (
      <React.Fragment key={key}>
        {flightT < 0.82 && (
          <Sprite
            source={projectileSource}
            left={projectileX - projectileSize.width / 2}
            top={projectileY - projectileSize.height}
            width={projectileSize.width}
            height={projectileSize.height}
            flipX={flipX}
          />
        )}
        {impactT > 0 && (
          config.twinImpact ? (
            <>
              <SheetFrame
                source={impactSource}
                frameCount={impactFrameCount}
                frameIndex={impactFrameIndex}
                x={point.x - 16}
                y={point.y}
                opacity={0.94 - impactT * 0.48}
              />
              <SheetFrame
                source={impactSource}
                frameCount={impactFrameCount}
                frameIndex={impactFrameIndex}
                x={point.x + 16}
                y={point.y}
                opacity={0.94 - impactT * 0.48}
                flipX
              />
            </>
          ) : (
            <SheetFrame
              source={impactSource}
              frameCount={impactFrameCount}
              frameIndex={impactFrameIndex}
              x={point.x}
              y={point.y}
              opacity={0.96 - impactT * 0.5}
              flipX={flipX}
            />
          )
        )}
      </React.Fragment>
    );
  };

  const layers: React.ReactNode[] = [];

  if (config.includeActorTarget && cast.actorTarget) {
    const actorVolley = renderSingleVolley(
      `${cast.key}-volley-actor`,
      cast.actorTarget,
      elapsedMs,
    );
    if (actorVolley) layers.push(actorVolley);
  }

  if (config.includeCellTargets) {
    cast.cellTargets.forEach((point, index) => {
      const localElapsedMs = elapsedMs - config.volleyDelayMs * (index + 1);
      const cellVolley = renderSingleVolley(
        `${cast.key}-volley-cell-${index}-${point.row}-${point.col}`,
        point,
        localElapsedMs,
      );
      if (cellVolley) layers.push(cellVolley);
    });
  }

  return layers;
};

const renderMonsterBurst = (cast: ActiveBattleSkillCast, elapsedMs: number) => {
  if (!VOLLEY_CONFIG[cast.familyCode]) return null;
  if (!cast.actorTarget) return null;
  const impactT = clamp01((elapsedMs - 380) / 220);
  if (impactT <= 0 || impactT >= 1) return null;

  const size = 58 + impactT * 18;

  return (
    <Image
      source={ASSET_BLOOD_THROW}
      resizeMode="contain"
      style={{
        position: 'absolute',
        left: cast.actorTarget.x - size / 2,
        top: cast.actorTarget.y - size / 2,
        width: size,
        height: size,
        opacity: 0.86 - impactT * 0.6,
      }}
    />
  );
};

export const BattleSkillCastOverlay: React.FC<{
  casts: ActiveBattleSkillCast[];
}> = ({ casts }) => {
  const [, forceTick] = React.useState(0);

  React.useEffect(() => {
    if (casts.length === 0) return undefined;
    const timer = setInterval(() => forceTick(tick => tick + 1), FRAME_TICK_MS);
    return () => clearInterval(timer);
  }, [casts.length]);

  const now = Date.now();

  return (
    <>
      {casts.map(cast => {
        const skill = BATTLE_SKILLS[cast.familyCode];
        const elapsedMs = now - cast.startedAt;

        return (
          <View
            key={cast.key}
            pointerEvents="none"
            style={{ position: 'absolute', left: 0, top: 0, right: 0, bottom: 0 }}
          >
            {renderVolleyFamily(cast, skill, elapsedMs)}
            {renderMonsterBurst(cast, elapsedMs)}
          </View>
        );
      })}
    </>
  );
};
