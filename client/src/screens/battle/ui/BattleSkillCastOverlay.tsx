import React from 'react';
import { Image, Image as RNImage, View } from 'react-native';
import {
  BATTLE_SKILLS,
  type ActiveBattleSkillCast,
  type BattleSkillDefinition,
  type ScreenPoint,
  type SkillFamilyCode,
  type SkillTargetPoint,
} from '../core';

const ASSET_BLOOD_THROW = require('../../../../assets/battle/10_hit_effects/bloodthrowaround.png');
const ASSET_MAGIC_GATE = require('../../../../assets/skill/01_battle_skill_shared_confirmed/named_effects/magicgate.png');
const ASSET_MINI_EXPLOSION = require('../../../../assets/skill/01_battle_skill_shared_confirmed/named_effects/miniexplosionfire.png');
const ASSET_SKILLUPDOWNSTAT = require('../../../../assets/skill/01_battle_skill_shared_confirmed/named_effects/skillupdownstat.png');
const ASSET_ZAP = require('../../../../assets/skill/01_battle_skill_shared_confirmed/battle_scene_support/zap.png');
const ASSET_ICE = require('../../../../assets/skill/01_battle_skill_shared_confirmed/battle_scene_support/ice.png');
const ASSET_IN_OVERLAY = require('../../../../assets/skill/02_elemental_runtime_families/group_100x_hoa_fire_likely/family_1003_io_elementVariant-0/runtime_png/1003003.png');

const FRAME_TICK_MS = 40;
const JAVA_SKILL_DELAY_TICK_MS = 40;
const HELPER_BADGE_FRAME_COUNT = 4;

type VolleyRenderConfig = {
  includeActorTarget: boolean;
  includeCellTargets: boolean;
  twinImpact: boolean;
  startDx: number;
  startDy: number;
  volleyDelayMs: number;
  flightPath?: 'straight' | 'fireball_drop';
  cellStartMode?: 'parallel' | 'after_actor_impact';
};

type HelperRenderConfig = {
  frameIndex: number;
  useSourcePoint: boolean;
  includeInOverlay: boolean;
};

const VOLLEY_CONFIG: Partial<Record<SkillFamilyCode, VolleyRenderConfig>> = {
  1000: {
    includeActorTarget: true,
    includeCellTargets: true,
    twinImpact: false,
    startDx: -180,
    startDy: -180,
    volleyDelayMs: 72,
    flightPath: 'fireball_drop',
    cellStartMode: 'after_actor_impact',
  },
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

const HELPER_RENDER_CONFIG: Partial<Record<SkillFamilyCode, HelperRenderConfig>> = {
  1002: { frameIndex: 1, useSourcePoint: true, includeInOverlay: true },
  2001: { frameIndex: 0, useSourcePoint: true, includeInOverlay: true },
  2002: { frameIndex: 2, useSourcePoint: true, includeInOverlay: true },
  4002: { frameIndex: 3, useSourcePoint: false, includeInOverlay: false },
};

const clamp01 = (value: number) => Math.max(0, Math.min(1, value));
const lerp = (a: number, b: number, t: number) => a + (b - a) * t;
const easeOutQuad = (t: number) => 1 - (1 - t) * (1 - t);
const easeInOutQuad = (t: number) => (t < 0.5 ? 2 * t * t : 1 - ((-2 * t + 2) ** 2) / 2);
const smoothStep = (t: number) => t * t * (3 - 2 * t);

const runtimePrimary = (skill: BattleSkillDefinition) => skill.runtimeFrames[0] ?? skill.icon;
const runtimeSecondary = (skill: BattleSkillDefinition) => skill.runtimeFrames[1] ?? skill.runtimeFrames[0] ?? skill.icon;
const runtimeTertiary = (skill: BattleSkillDefinition) => skill.runtimeFrames[2] ?? skill.runtimeFrames[1] ?? skill.runtimeFrames[0] ?? skill.icon;
const volleyStartDxForCast = (cast: ActiveBattleSkillCast, config: VolleyRenderConfig) =>
  cast.casterSide === 'player' ? config.startDx : -config.startDx;

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
    sheetHeight: height,
  };
};

const estimateFrameCount = (source: any) => {
  const metrics = sheetMetrics(source, 1);
  const ratio = metrics.sheetWidth / Math.max(1, metrics.sheetHeight);
  if (ratio < 1.6) return 1;
  return Math.max(1, Math.min(12, Math.round(ratio)));
};

const intrinsicSize = (source: any, frameCount?: number) => {
  const metrics = sheetMetrics(source, frameCount ?? estimateFrameCount(source));
  return { width: metrics.frameWidth, height: metrics.frameHeight };
};

const frameAt = (t: number, frameCount: number) =>
  Math.max(0, Math.min(frameCount - 1, Math.floor(clamp01(t) * frameCount)));

const sourcePointOf = (cast: ActiveBattleSkillCast): ScreenPoint => ({ x: cast.sourceX, y: cast.sourceY });

const sortTargetsByRow = (targets: SkillTargetPoint[]) =>
  [...targets].sort((left, right) => left.row - right.row);

const deterministicOrbitPoint = (
  center: ScreenPoint,
  index: number,
  total: number,
  radiusX: number,
  radiusY: number,
): ScreenPoint => {
  const safeTotal = Math.max(1, total);
  const angle = (-Math.PI / 2) + (Math.PI * 2 * index) / safeTotal;
  return {
    x: center.x + Math.cos(angle) * radiusX,
    y: center.y + Math.sin(angle) * radiusY,
  };
};

const arcPoint = (start: ScreenPoint, end: ScreenPoint, t: number, lift: number): ScreenPoint => {
  const controlY = Math.min(start.y, end.y) - lift;
  const invT = 1 - t;
  return {
    x: lerp(start.x, end.x, t),
    y: invT * invT * start.y + 2 * invT * t * controlY + t * t * end.y,
  };
};

const quadraticPoint = (
  start: ScreenPoint,
  control: ScreenPoint,
  end: ScreenPoint,
  t: number,
): ScreenPoint => {
  const invT = 1 - t;
  return {
    x: invT * invT * start.x + 2 * invT * t * control.x + t * t * end.x,
    y: invT * invT * start.y + 2 * invT * t * control.y + t * t * end.y,
  };
};

const volleyProjectilePoint = (
  cast: ActiveBattleSkillCast,
  config: VolleyRenderConfig,
  point: ScreenPoint,
  flightT: number,
): ScreenPoint => {
  const startDx = volleyStartDxForCast(cast, config);
  const start = {
    x: point.x + startDx,
    y: point.y + config.startDy,
  };

  if (config.flightPath === 'fireball_drop') {
    const easedT = smoothStep(flightT);
    return quadraticPoint(
      start,
      {
        x: lerp(start.x, point.x, 0.26),
        y: lerp(start.y, point.y, 0.12),
      },
      point,
      easedT,
    );
  }

  return {
    x: lerp(start.x, point.x, flightT),
    y: lerp(start.y, point.y, easeOutQuad(flightT)),
  };
};

const Sprite: React.FC<{
  source: any;
  left: number;
  top: number;
  width: number;
  height: number;
  opacity?: number;
  flipX?: boolean;
  rotation?: string;
  scale?: number;
}> = ({ source, left, top, width, height, opacity = 1, flipX = false, rotation = '0deg', scale = 1 }) => (
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
      transform: [{ scaleX: flipX ? -1 : 1 }, { rotate: rotation }, { scale }],
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
  rotation?: string;
  scale?: number;
}> = ({ source, frameCount, frameIndex, x, y, opacity = 1, flipX = false, rotation = '0deg', scale = 1 }) => {
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
        transform: [{ scaleX: flipX ? -1 : 1 }, { rotate: rotation }, { scale }],
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

const renderBurstStamp = (
  key: string,
  source: any,
  point: ScreenPoint,
  elapsedMs: number,
  startMs: number,
  durationMs: number,
  options?: { scale?: number; opacityFrom?: number; opacityTo?: number; rotation?: string; flipX?: boolean },
) => {
  const localElapsedMs = elapsedMs - startMs;
  if (localElapsedMs < 0 || localElapsedMs > durationMs) return null;
  const frameCount = estimateFrameCount(source);
  const t = clamp01(localElapsedMs / durationMs);
  return (
    <SheetFrame
      key={key}
      source={source}
      frameCount={frameCount}
      frameIndex={frameAt(t, frameCount)}
      x={point.x}
      y={point.y}
      opacity={lerp(options?.opacityFrom ?? 0.96, options?.opacityTo ?? 0.15, t)}
      scale={lerp(options?.scale ?? 1, (options?.scale ?? 1) * 1.12, t)}
      rotation={options?.rotation}
      flipX={options?.flipX}
    />
  );
};

const renderActorBurst = (
  key: string,
  point: ScreenPoint | null,
  elapsedMs: number,
  startMs: number,
  durationMs: number,
  source: any = ASSET_BLOOD_THROW,
  baseSize = 58,
) => {
  if (!point) return null;
  const localElapsedMs = elapsedMs - startMs;
  if (localElapsedMs < 0 || localElapsedMs > durationMs) return null;
  const t = clamp01(localElapsedMs / durationMs);
  const size = baseSize + t * 20;

  return (
    <Image
      key={key}
      source={source}
      resizeMode="contain"
      style={{
        position: 'absolute',
        left: point.x - size / 2,
        top: point.y - size / 2,
        width: size,
        height: size,
        opacity: 0.9 - t * 0.6,
      }}
    />
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
  const projectileSize = intrinsicSize(projectileSource, estimateFrameCount(projectileSource));
  const flightDurationMs = Math.max(1, cast.impactDelayMs);
  const impactDurationMs = Math.max(
    JAVA_SKILL_DELAY_TICK_MS,
    impactFrameCount * 3 * JAVA_SKILL_DELAY_TICK_MS,
  );
  const renderSingleVolley = (key: string, point: ScreenPoint, localElapsedMs: number) => {
    if (localElapsedMs < 0 || localElapsedMs > flightDurationMs + impactDurationMs) return null;
    const flightT = clamp01(localElapsedMs / flightDurationMs);
    const startDx = volleyStartDxForCast(cast, config);
    const startX = point.x + startDx;
    const flipX = startX < point.x;
    const projectilePoint = volleyProjectilePoint(cast, config, point, flightT);
    const impactT = clamp01((localElapsedMs - flightDurationMs) / impactDurationMs);
    const impactFrameIndex = frameAt(impactT, impactFrameCount);

    return (
      <React.Fragment key={key}>
        {localElapsedMs < flightDurationMs && (
          <Sprite
            source={projectileSource}
            left={projectilePoint.x - projectileSize.width / 2}
            top={projectilePoint.y - projectileSize.height}
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
    const cellStartOffsetMs = config.cellStartMode === 'after_actor_impact' ? flightDurationMs : config.volleyDelayMs;
    cast.cellTargets.forEach((point, index) => {
      const localElapsedMs = elapsedMs - cellStartOffsetMs - config.volleyDelayMs * index;
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

const renderTileBurstPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const burstSource = runtimePrimary(skill);
  const burstDurationMs = Math.max(
    3 * JAVA_SKILL_DELAY_TICK_MS,
    estimateFrameCount(burstSource) * 3 * JAVA_SKILL_DELAY_TICK_MS,
  );

  return cast.cellTargets.map((point, index) =>
    renderBurstStamp(
      `${cast.key}-tile-burst-${index}-${point.row}-${point.col}`,
      burstSource,
      point,
      elapsedMs,
      10 * JAVA_SKILL_DELAY_TICK_MS + index * 4 * JAVA_SKILL_DELAY_TICK_MS,
      burstDurationMs,
    ),
  );
};

const renderHelperPattern = (cast: ActiveBattleSkillCast, elapsedMs: number) => {
  const config = HELPER_RENDER_CONFIG[cast.familyCode];
  if (!config) return null;
  const target = config.useSourcePoint ? sourcePointOf(cast) : cast.actorTarget;
  if (!target) return null;
  const localElapsedMs = elapsedMs - 120;
  if (localElapsedMs < 0 || localElapsedMs > 520) return null;
  const t = clamp01(localElapsedMs / 520);
  const badgePoint = { x: target.x, y: target.y - 46 + t * -6 };
  const badge = (
    <SheetFrame
      key={`${cast.key}-helper-badge`}
      source={ASSET_SKILLUPDOWNSTAT}
      frameCount={HELPER_BADGE_FRAME_COUNT}
      frameIndex={config.frameIndex}
      x={badgePoint.x}
      y={badgePoint.y}
      opacity={0.92 - t * 0.4}
      scale={0.9 + t * 0.1}
    />
  );
  const overlay = config.includeInOverlay
    ? renderBurstStamp(
      `${cast.key}-helper-overlay`,
      ASSET_IN_OVERLAY,
      { x: target.x, y: target.y - 2 },
      elapsedMs,
      160,
      480,
      { opacityFrom: 0.82, opacityTo: 0.2 },
    )
    : null;

  return (
    <>
      {overlay}
      {badge}
    </>
  );
};

const renderElementHelperPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const point = sourcePointOf(cast);
  return (
    <>
      {renderBurstStamp(`${cast.key}-element-a`, runtimePrimary(skill), { x: point.x + 6, y: point.y - 12 }, elapsedMs, 40, 360)}
      {renderBurstStamp(`${cast.key}-element-b`, runtimeSecondary(skill), { x: point.x - 4, y: point.y - 18 }, elapsedMs, 180, 360)}
      {renderBurstStamp(`${cast.key}-element-c`, runtimeTertiary(skill), { x: point.x, y: point.y - 6 }, elapsedMs, 320, 420, { opacityFrom: 0.9, opacityTo: 0.12 })}
    </>
  );
};

const renderFlareGatePattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const point = cast.actorTarget;
  if (!point) return null;
  const bursts = Array.from({ length: 6 }, (_, index) => {
    const angle = (Math.PI * 2 * index) / 6;
    return renderBurstStamp(
      `${cast.key}-flare-burst-${index}`,
      ASSET_MINI_EXPLOSION,
      { x: point.x + Math.cos(angle) * 26, y: point.y + Math.sin(angle) * 10 },
      elapsedMs,
      860,
      220,
      { opacityFrom: 0.88, opacityTo: 0.05 },
    );
  });

  return (
    <>
      {renderBurstStamp(`${cast.key}-magic-gate`, ASSET_MAGIC_GATE, { x: point.x, y: point.y + 6 }, elapsedMs, 160, 620, { scale: 0.92 })}
      {renderBurstStamp(`${cast.key}-flare-core`, runtimePrimary(skill), { x: point.x, y: point.y }, elapsedMs, 240, 620)}
      {bursts}
      {renderActorBurst(`${cast.key}-flare-hit`, point, elapsedMs, 520, 240)}
    </>
  );
};

const renderLobImpactPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  if (!cast.actorTarget) return null;
  const start = sourcePointOf(cast);
  const end = cast.actorTarget;
  const localElapsedMs = elapsedMs - 80;
  const flightMs = 520;
  const t = clamp01(localElapsedMs / flightMs);
  const inFlight = localElapsedMs >= 0 && localElapsedMs <= flightMs;
  const point = arcPoint(start, end, easeOutQuad(t), 96);
  const projectileSource = runtimePrimary(skill);
  const projectileFrames = estimateFrameCount(projectileSource);
  const layers: React.ReactNode[] = [];

  if (inFlight) {
    layers.push(
      <SheetFrame
        key={`${cast.key}-lob-flight`}
        source={projectileSource}
        frameCount={projectileFrames}
        frameIndex={frameAt(t, projectileFrames)}
        x={point.x}
        y={point.y}
        flipX={start.x > end.x}
      />,
    );
  }

  layers.push(renderBurstStamp(`${cast.key}-lob-burst-a`, ASSET_MINI_EXPLOSION, end, elapsedMs, 620, 200, { opacityFrom: 0.92, opacityTo: 0.05 }));
  layers.push(renderActorBurst(`${cast.key}-lob-hit-a`, end, elapsedMs, 620, 220, ASSET_BLOOD_THROW, 54));
  layers.push(renderBurstStamp(`${cast.key}-lob-burst-b`, ASSET_MINI_EXPLOSION, { x: end.x, y: end.y - 4 }, elapsedMs, 620 + 4 * JAVA_SKILL_DELAY_TICK_MS, 180, { opacityFrom: 0.82, opacityTo: 0.04 }));
  layers.push(renderActorBurst(`${cast.key}-lob-hit-b`, end, elapsedMs, 620 + 4 * JAVA_SKILL_DELAY_TICK_MS, 200, ASSET_BLOOD_THROW, 50));

  return layers;
};

const renderPillarPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const layers: React.ReactNode[] = cast.cellTargets.map((point, index) =>
    renderBurstStamp(
      `${cast.key}-pillar-${index}-${point.row}-${point.col}`,
      runtimePrimary(skill),
      point,
      elapsedMs,
      10 * JAVA_SKILL_DELAY_TICK_MS + index * 2 * JAVA_SKILL_DELAY_TICK_MS,
      360,
      { opacityFrom: 0.96, opacityTo: 0.1 },
    ),
  );

  if (cast.actorTarget) {
    layers.push(renderActorBurst(`${cast.key}-pillar-hit`, cast.actorTarget, elapsedMs, 620, 220));
  }

  return layers;
};

const renderDescendingColumnPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const anchor = cast.cellTargets[0];
  if (!anchor) return null;
  const flightDurationMs = 16 * JAVA_SKILL_DELAY_TICK_MS;
  const t = clamp01(elapsedMs / flightDurationMs);
  const currentY = lerp(anchor.y, 0, easeInOutQuad(t));
  const orderedTargets = sortTargetsByRow(cast.boardClearTargets);
  const stageSize = Math.max(1, Math.floor(Math.max(1, orderedTargets.length) / 8));
  const layers: React.ReactNode[] = [
    renderBurstStamp(
      `${cast.key}-column-flight`,
      runtimePrimary(skill),
      { x: anchor.x, y: currentY },
      elapsedMs,
      0,
      flightDurationMs,
      { opacityFrom: 0.92, opacityTo: 0.18 },
    ),
  ];

  orderedTargets.forEach((point, index) => {
    const stageIndex = Math.floor(index / stageSize);
    const startTick = Math.max(2, 16 - stageIndex * 2);
    layers.push(
      renderBurstStamp(
        `${cast.key}-column-hit-${index}-${point.row}-${point.col}`,
        runtimePrimary(skill),
        point,
        elapsedMs,
        startTick * JAVA_SKILL_DELAY_TICK_MS,
        180,
        { opacityFrom: 0.82, opacityTo: 0.08 },
      ),
    );
  });

  return layers;
};

const renderChainPathPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const layers: React.ReactNode[] = cast.cellTargets.map((point, index) =>
    renderBurstStamp(
      `${cast.key}-chain-node-${index}-${point.row}-${point.col}`,
      index % 2 === 0 ? runtimePrimary(skill) : runtimeSecondary(skill),
      point,
      elapsedMs,
      140 + index * 5 * JAVA_SKILL_DELAY_TICK_MS,
      220,
      { opacityFrom: 0.92, opacityTo: 0.14 },
    ),
  );

  if (cast.actorTarget) {
    layers.push(renderBurstStamp(`${cast.key}-chain-impact`, runtimeTertiary(skill), cast.actorTarget, elapsedMs, 640, 260, { opacityFrom: 0.9, opacityTo: 0.12 }));
    layers.push(renderActorBurst(`${cast.key}-chain-hit`, cast.actorTarget, elapsedMs, 720, 200, ASSET_ZAP, 64));
  }

  return layers;
};

const renderBeamPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  if (!cast.actorTarget) return null;
  const start = sourcePointOf(cast);
  const end = { x: cast.actorTarget.x + (start.x < cast.actorTarget.x ? -60 : 60), y: cast.actorTarget.y };
  const localElapsedMs = elapsedMs - 80;
  if (localElapsedMs < 0 || localElapsedMs > 420) return null;
  const t = clamp01(localElapsedMs / 420);
  const beamLength = Math.max(1, Math.abs(end.x - start.x));
  const beamLeft = Math.min(start.x, end.x);
  const beamTop = lerp(start.y, end.y, 0.55);

  return (
    <>
      <View
        style={{
          position: 'absolute',
          left: beamLeft,
          top: beamTop - 3,
          width: beamLength * clamp01(t * 1.15),
          height: 6,
          backgroundColor: '#ffe58d',
          opacity: 0.15 + t * 0.55,
          borderRadius: 3,
        }}
      />
      {renderBurstStamp(`${cast.key}-beam-core`, runtimePrimary(skill), { x: end.x, y: end.y }, elapsedMs, 160, 360, { opacityFrom: 0.92, opacityTo: 0.1 })}
      {renderActorBurst(`${cast.key}-beam-hit`, cast.actorTarget, elapsedMs, 420, 220, ASSET_ZAP, 58)}
    </>
  );
};

const renderSideWavePattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const uniqueCols = Array.from(new Set(cast.boardClearTargets.map(point => point.col))).sort((left, right) => left - right);
  const layers: React.ReactNode[] = [];

  uniqueCols.forEach((col, colIndex) => {
    const columnPoints = sortTargetsByRow(cast.boardClearTargets.filter(point => point.col === col));
    const baseTick = colIndex === 1 ? 0 : -2;

    columnPoints.forEach(point => {
      layers.push(
        renderBurstStamp(
          `${cast.key}-wave-${col}-${point.row}`,
          runtimePrimary(skill),
          point,
          elapsedMs,
          (baseTick + point.row) * JAVA_SKILL_DELAY_TICK_MS,
          180,
          { opacityFrom: 0.84, opacityTo: 0.06 },
        ),
      );
    });
  });

  if (cast.actorTarget) {
    const laneStartMs = 6 * JAVA_SKILL_DELAY_TICK_MS;
    const laneProgress = clamp01((elapsedMs - laneStartMs) / 220);
    const laneYs = [cast.actorTarget.y - 36, cast.actorTarget.y - 16, cast.actorTarget.y + 4];

    laneYs.forEach((laneY, laneIndex) => {
      layers.push(
        <View
          key={`${cast.key}-wave-lane-${laneIndex}`}
          style={{
            position: 'absolute',
            left: 0,
            top: laneY - 3,
            width: cast.actorTarget!.x * laneProgress,
            height: 6,
            backgroundColor: '#ffe58d',
            opacity: 0.08 + laneProgress * 0.28,
            borderRadius: 3,
          }}
        />,
      );
    });

  }

  return layers;
};

const renderDualLayerPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const layers: React.ReactNode[] = [];

  cast.cellTargets.forEach((point, index) => {
    const startMs = 15 * JAVA_SKILL_DELAY_TICK_MS + index * 2 * JAVA_SKILL_DELAY_TICK_MS;
    layers.push(renderBurstStamp(`${cast.key}-dual-a-${index}`, runtimePrimary(skill), { x: point.x - 8, y: point.y }, elapsedMs, startMs, 240, { opacityFrom: 0.92, opacityTo: 0.08 }));
    layers.push(renderBurstStamp(`${cast.key}-dual-b-${index}`, runtimePrimary(skill), { x: point.x + 8, y: point.y }, elapsedMs, startMs + 60, 220, { opacityFrom: 0.7, opacityTo: 0.04, flipX: true }));
  });

  if (cast.actorTarget) {
    layers.push(renderBurstStamp(`${cast.key}-dual-impact`, runtimeSecondary(skill), cast.actorTarget, elapsedMs, 920, 220, { opacityFrom: 0.86, opacityTo: 0.08 }));
    layers.push(renderActorBurst(`${cast.key}-dual-hit`, cast.actorTarget, elapsedMs, 980, 220, ASSET_ZAP, 56));
  }

  return layers;
};

const renderParticleBurstPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  if (!cast.actorTarget) return null;
  const particleSource = runtimePrimary(skill);
  const particleFrames = estimateFrameCount(particleSource);
  const layers: React.ReactNode[] = cast.cellTargets.map((point, index) => {
    const randomOrbitPoint = deterministicOrbitPoint(cast.actorTarget!, index, cast.cellTargets.length, 30 + (index % 3) * 12, 18 + (index % 2) * 10);
    const localElapsedMs = elapsedMs;
    if (localElapsedMs < 0 || localElapsedMs > 720) return null;
    let particlePoint: ScreenPoint = point;
    let t = 0;

    if (localElapsedMs <= 280) {
      t = clamp01(localElapsedMs / 280);
      particlePoint = {
        x: lerp(point.x, randomOrbitPoint.x, easeOutQuad(t)),
        y: lerp(point.y, randomOrbitPoint.y, easeOutQuad(t)),
      };
    } else if (localElapsedMs <= 480) {
      t = clamp01((localElapsedMs - 280) / 200);
      particlePoint = randomOrbitPoint;
    } else {
      t = clamp01((localElapsedMs - 480) / 240);
      particlePoint = {
        x: lerp(randomOrbitPoint.x, cast.actorTarget!.x, easeOutQuad(t)),
        y: lerp(randomOrbitPoint.y, cast.actorTarget!.y, easeOutQuad(t)),
      };
    }
    const opacity =
      localElapsedMs <= 280
        ? 0.94 - t * 0.18
        : localElapsedMs <= 480
          ? 0.76
          : 0.76 - t * 0.36;

    return (
      <SheetFrame
        key={`${cast.key}-particle-${index}`}
        source={particleSource}
        frameCount={particleFrames}
        frameIndex={frameAt(t, particleFrames)}
        x={particlePoint.x}
        y={particlePoint.y}
        opacity={opacity}
      />
    );
  });
  return layers;
};

const renderAnchorBurstPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const point = cast.actorTarget;
  if (!point) return null;
  return (
    <>
      {renderBurstStamp(
        `${cast.key}-anchor-core`,
        runtimePrimary(skill),
        point,
        elapsedMs,
        0,
        13 * 33,
        { opacityFrom: 0.94, opacityTo: 0.12 },
      )}
    </>
  );
};

const renderLinearShotPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  if (!cast.actorTarget) return null;
  const start = sourcePointOf(cast);
  const end = cast.actorTarget;
  const localElapsedMs = elapsedMs - 80;
  if (localElapsedMs < 0 || localElapsedMs > 360) return null;
  const t = clamp01(localElapsedMs / 360);
  const point = {
    x: lerp(start.x, end.x, easeOutQuad(t)),
    y: lerp(start.y, end.y, easeOutQuad(t)),
  };
  const source = runtimePrimary(skill);
  const frameCount = estimateFrameCount(source);

  return (
    <>
      <View
        style={{
          position: 'absolute',
          left: Math.min(start.x, point.x),
          top: point.y - 1,
          width: Math.max(1, Math.abs(point.x - start.x)),
          height: 3,
          backgroundColor: '#cfefff',
          opacity: 0.2 + t * 0.45,
          borderRadius: 2,
        }}
      />
      <SheetFrame
        source={source}
        frameCount={frameCount}
        frameIndex={frameAt(t, frameCount)}
        x={point.x}
        y={point.y}
        opacity={0.94 - t * 0.34}
      />
      <SheetFrame
        source={ASSET_SKILLUPDOWNSTAT}
        frameCount={HELPER_BADGE_FRAME_COUNT}
        frameIndex={3}
        x={end.x}
        y={end.y - 44}
        opacity={clamp01((elapsedMs - 340) / 180)}
      />
    </>
  );
};

const renderStaticAuraPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  const point = cast.actorTarget;
  if (!point) return null;
  return (
    <>
      {renderBurstStamp(`${cast.key}-aura-core`, runtimePrimary(skill), point, elapsedMs, 40, 380, { opacityFrom: 0.88, opacityTo: 0.1 })}
      {renderActorBurst(`${cast.key}-aura-ice`, point, elapsedMs, 120, 260, ASSET_ICE, 72)}
      {renderActorBurst(`${cast.key}-aura-hit`, point, elapsedMs, 340, 220, ASSET_BLOOD_THROW, 52)}
    </>
  );
};

const renderSkillPattern = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  // Pattern implementations stay here for Java cross-checking, but they only run
  // once `BattleScreen` passes packet-driven targets into the overlay.
  switch (skill.pattern) {
    case 'projectile_pair':
      return renderVolleyFamily(cast, skill, elapsedMs);
    case 'tile_burst':
      return renderTileBurstPattern(cast, skill, elapsedMs);
    case 'board_helper':
      return renderHelperPattern(cast, elapsedMs);
    case 'element_helper':
      return renderElementHelperPattern(cast, skill, elapsedMs);
    case 'flare_gate':
      return renderFlareGatePattern(cast, skill, elapsedMs);
    case 'lob_impact':
      return renderLobImpactPattern(cast, skill, elapsedMs);
    case 'pillar':
      return renderPillarPattern(cast, skill, elapsedMs);
    case 'descending_column':
      return renderDescendingColumnPattern(cast, skill, elapsedMs);
    case 'chain_path':
      return renderChainPathPattern(cast, skill, elapsedMs);
    case 'beam':
      return renderBeamPattern(cast, skill, elapsedMs);
    case 'side_wave':
      return renderSideWavePattern(cast, skill, elapsedMs);
    case 'dual_layer':
      return renderDualLayerPattern(cast, skill, elapsedMs);
    case 'particle_burst':
      return renderParticleBurstPattern(cast, skill, elapsedMs);
    case 'anchor_burst':
      return renderAnchorBurstPattern(cast, skill, elapsedMs);
    case 'linear_shot':
      return renderLinearShotPattern(cast, skill, elapsedMs);
    case 'static_aura':
      return renderStaticAuraPattern(cast, skill, elapsedMs);
    default:
      return null;
  }
};

const renderActorHitOverlay = (
  cast: ActiveBattleSkillCast,
  skill: BattleSkillDefinition,
  elapsedMs: number,
) => {
  if (!cast.actorTarget || !cast.hitsActor) return null;
  if (skill.pattern === 'projectile_pair') {
    const impactStartMs = cast.impactDelayMs;
    const impactT = clamp01((elapsedMs - impactStartMs) / 220);
    if (impactT <= 0 || impactT >= 1) return null;
    return renderActorBurst(`${cast.key}-projectile-hit`, cast.actorTarget, elapsedMs, impactStartMs, 220);
  }
  return null;
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
        if (cast.runtimeSource !== 'server_packet') {
          // Only render packet-driven casts. Local preview payloads were useful
          // while reverse engineering, but they should stay visually disabled
          // until the server provides Java-shaped target arrays.
          return null;
        }

        const skill = BATTLE_SKILLS[cast.familyCode];
        const elapsedMs = now - cast.startedAt;

        return (
          <View
            key={cast.key}
            pointerEvents="none"
            style={{ position: 'absolute', left: 0, top: 0, right: 0, bottom: 0 }}
          >
            {renderSkillPattern(cast, skill, elapsedMs)}
            {renderActorHitOverlay(cast, skill, elapsedMs)}
          </View>
        );
      })}
    </>
  );
};
