import React from 'react';
import { Animated, Text, View } from 'react-native';
import { MonsterSprite, monsterDisplaySize, monsterPlacementMetrics } from '../../../../engine/MonsterSprite';
import type { MonsterRuntime, MonsterVisual, NpcRuntime } from '../runtime/MapActorRuntime';
import { resolveNpcNameColor } from '../runtime/MapActorRuntime';
import { styles } from './MapActorField.styles';

const LAYER_MONSTER = 2;

interface MonsterFieldProps {
  runtimes: MonsterRuntime[];
  visuals: MonsterVisual[];
}

interface NpcFieldProps {
  npcs: NpcRuntime[];
}

export const MonsterField = React.memo<MonsterFieldProps>(({ runtimes, visuals }) => (
  <>
    {runtimes.map((m, i) => {
      const vis = visuals[i] ?? {
        frameIndex: m.frameIndex,
        direction: m.direction,
        attacking: m.attacking,
        worldState: m.worldState,
      };
      let spriteScale = 1;
      if (vis.worldState === 'engaging') {
        spriteScale = 1.06;
      } else if (vis.worldState === 'alert') {
        spriteScale = 1.03;
      }

      return (
        <Animated.View
          key={m.id}
          style={[
            styles.monsterContainer,
            {
              top: m.topY - 16,
              width: Math.max(m.size.w, 72),
              height: m.size.h + 16,
              zIndex: LAYER_MONSTER,
              transform: [{ translateX: m.xAnim }, { scale: spriteScale }],
            },
          ]}
          pointerEvents="none"
        >
          <Text numberOfLines={1} style={styles.monsterLabel}>
            {m.roster.displayName}
          </Text>
          <MonsterSprite
            type={m.type}
            frameIndex={vis.frameIndex}
            facingRight={vis.direction === 1}
          />
        </Animated.View>
      );
    })}
  </>
));
MonsterField.displayName = 'MonsterField';

export const NpcField = React.memo<NpcFieldProps>(({ npcs }) => (
  <>
    {npcs.map((npc) => {
      const size = monsterDisplaySize(npc.type);
      const metrics = monsterPlacementMetrics(npc.type);
      const left = Math.round(npc.x - size.w / 2);
      const top = Math.round(npc.y - size.h + metrics.groundOffset);

      return (
        <View
          key={npc.id}
          pointerEvents="none"
          style={[
            styles.npcContainer,
            {
              left,
              top,
              width: Math.max(size.w, 72),
              height: size.h + 18,
            },
          ]}
        >
          <Text style={[styles.npcLabel, { color: resolveNpcNameColor(npc.nameColorMode) }]} numberOfLines={1}>
            {npc.displayName}
          </Text>
          <MonsterSprite type={npc.type} frameIndex={0} facingRight />
        </View>
      );
    })}
  </>
));
NpcField.displayName = 'NpcField';
