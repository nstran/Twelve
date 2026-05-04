import { useEffect } from 'react';
import type React from 'react';
import { WALK_FRAMES, type MonsterType } from '../../../../engine/MonsterSprite';
import type { MonsterTarget } from '../../../../engine/character';
import type { MonsterSharedSheetFamily } from '../../../battle';
import {
  hasMonsterCollision,
  type MonsterRuntime,
  type MonsterVisual,
} from '../runtime/MapActorRuntime';

interface MonsterEncounterSnapshot {
  type: MonsterType;
  monsterKey: string;
  displayName: string;
  displayLevel: number;
  iqValue: number;
  visualTypeByte: number;
  sharedSheetFamily: MonsterSharedSheetFamily;
  nameColorMode: number;
  monsterFrameIndex: number;
  monsterFacingRight: boolean;
  monsterWorldState: 'patrol' | 'alert' | 'engaging';
  x: number;
  groundY: number;
  initialTurn: 'player' | 'monster';
}

interface UseMapMonsterLoopArgs {
  isEncounterActive: boolean;
  monsterRuntimeVersion: number;
  playerSpriteWidth: number;
  monsterRuntimes: MonsterRuntime[];
  monsterTargetsRef: React.MutableRefObject<MonsterTarget[]>;
  playerLastMovedAtRef: React.MutableRefObject<number>;
  charLeftRef: React.MutableRefObject<number>;
  charFootYRef: React.MutableRefObject<number>;
  engagedMonsterIdRef: React.MutableRefObject<string | null>;
  battleTriggered: React.MutableRefObject<boolean>;
  defeatRecoveryActiveRef: React.MutableRefObject<boolean>;
  setMonsterVisuals: React.Dispatch<React.SetStateAction<MonsterVisual[]>>;
  startEncounter: (snap: MonsterEncounterSnapshot) => void;
  getLoopNowMs: () => number;
  playerAlertMemoryMs: number;
  monsterTickMs: number;
  frameTicks: number;
  engageTriggerDelayMs: number;
}

export const useMapMonsterLoop = ({
  isEncounterActive,
  monsterRuntimeVersion,
  playerSpriteWidth,
  monsterRuntimes,
  monsterTargetsRef,
  playerLastMovedAtRef,
  charLeftRef,
  charFootYRef,
  engagedMonsterIdRef,
  battleTriggered,
  defeatRecoveryActiveRef,
  setMonsterVisuals,
  startEncounter,
  getLoopNowMs,
  playerAlertMemoryMs,
  monsterTickMs,
  frameTicks,
  engageTriggerDelayMs,
}: UseMapMonsterLoopArgs): void => {
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

      while (accumulator >= monsterTickMs) {
        accumulator -= monsterTickMs;
        const playerRecentlyMoved = getLoopNowMs() - playerLastMovedAtRef.current <= playerAlertMemoryMs;
        const playerLeft = charLeftRef.current;
        const lockedMonsterId = engagedMonsterIdRef.current;

        for (let i = 0; i < monsterRuntimes.length; i++) {
          const m = monsterRuntimes[i];
          const canOwnEncounter = !defeatRecoveryActiveRef.current && (lockedMonsterId === null || lockedMonsterId === m.id);
          const collidesWithPlayer = canOwnEncounter &&
            playerRecentlyMoved &&
            hasMonsterCollision(playerLeft, playerSpriteWidth, charFootYRef.current, m.x, m.groundY, m.collisionSize.w, m.collisionSize.h);
          const nextWorldState: MonsterRuntime['worldState'] = collidesWithPlayer ? 'engaging' : 'patrol';

          // 1. Move
          let newX = m.x + m.roster.moveSpeed * m.direction;
          let newDir: 1 | -1 = m.direction;
          if (newX >= m.maxX) { newX = m.maxX; newDir = -1; }
          if (newX <= m.minX) { newX = m.minX; newDir = 1; }

          const collisionTriggeredEncounter = collidesWithPlayer;
          if (collisionTriggeredEncounter && !battleTriggered.current && engagedMonsterIdRef.current === null) {
            engagedMonsterIdRef.current = m.id;
          }

          if (
            collisionTriggeredEncounter &&
            !battleTriggered.current &&
            engagedMonsterIdRef.current === m.id &&
            !m.engageQueued
          ) {
            m.engageQueued = true;
            const snap = {
              type: m.type,
              monsterKey: m.roster.monsterKey,
              displayName: m.roster.displayName,
              displayLevel: m.roster.displayLevel,
              iqValue: m.roster.iqValue,
              visualTypeByte: m.roster.visualTypeByte,
              sharedSheetFamily: m.roster.sharedSheetFamily,
              nameColorMode: m.roster.nameColorMode,
              monsterFrameIndex: m.frameIndex,
              monsterFacingRight: newDir === 1,
              monsterWorldState: nextWorldState,
              x: newX,
              groundY: m.groundY,
              initialTurn: 'monster' as const,
            };
            setTimeout(() => {
              if (
                defeatRecoveryActiveRef.current ||
                battleTriggered.current ||
                !m.engageQueued ||
                engagedMonsterIdRef.current !== m.id
              ) {
                return;
              }

              startEncounter(snap);
            }, engageTriggerDelayMs);
          } else if (!collisionTriggeredEncounter) {
            m.engageQueued = false;
          }

          // 3. Advance frame
          const newTick   = m.tickCount + 1;
          const frames    = WALK_FRAMES;
          const stepIdx   = Math.floor(newTick / frameTicks) % frames.length;
          const nextFrame = frames[stepIdx];

          // 4. Detect visual diff BEFORE mutating
          if (
            m.frameIndex !== nextFrame ||
            m.direction !== newDir ||
            m.attacking !== false ||
            m.worldState !== nextWorldState
          ) {
            visualsDirty = true;
          }

          // 5. Commit to runtime (mutate in place)
          m.x          = newX;
          m.direction  = newDir;
          m.attacking  = false;
          m.worldState = nextWorldState;
          m.aggroTicks = 0;
          m.tickCount  = newTick;
          m.frameIndex = nextFrame;

          // 6. Drive visuals natively (no React render needed)
          const leftX = newX - m.size.w / 2;
          m.xAnim.setValue(leftX);

          // 7. Mutate the stable target array read by CharacterController
          const target = monsterTargetsRef.current[i];
          target.x = leftX;
          target.y = m.topY;
          target.width = m.size.w;
          target.height = m.size.h;
          target.collisionWidth = m.collisionSize.w;
          target.collisionHeight = m.collisionSize.h;
          target.groundY = m.groundY;
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
          worldState: m.worldState,
        })));
      }

      rafId = requestAnimationFrame(step);
    };

    rafId = requestAnimationFrame(step);

    return () => {
      cancelled = true;
      if (rafId !== null) cancelAnimationFrame(rafId);
    };
  }, [isEncounterActive, monsterRuntimeVersion, playerSpriteWidth, startEncounter]);
};
