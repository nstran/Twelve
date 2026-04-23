using System;
using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMapMonsterRosterService : IMapMonsterRosterService
    {
        private readonly IMonsterSpawnCatalog _spawnCatalog;
        private readonly IReadOnlyDictionary<string, IReadOnlyList<MapMonsterSpawnGroup>> _spawnGroups =
            MonsterCatalogSeed.MapSpawnGroups;

        public InMemoryMapMonsterRosterService(IMonsterSpawnCatalog spawnCatalog)
        {
            _spawnCatalog = spawnCatalog;
        }

        public IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId)
        {
            var groups = GetActiveSpawnGroups(mapId, roomId);
            if (groups.Count == 0)
            {
                return [];
            }

            var encounters = new List<MapMonsterEncounter>();
            foreach (var group in groups)
            {
                if (!group.IsActive)
                {
                    continue;
                }

                var spawnTemplate = _spawnCatalog.GetBySpawnTemplateKey(group.SpawnTemplateKey);
                if (spawnTemplate is null || spawnTemplate.SpawnCount <= 0)
                {
                    continue;
                }

                for (var i = 0; i < spawnTemplate.SpawnCount; i++)
                {
                    encounters.Add(new MapMonsterEncounter(
                        MonsterKey: BuildMonsterKey(group.SpawnGroupKey, i + 1),
                        SpawnGroupKey: group.SpawnGroupKey,
                        SpawnInstanceIndex: i,
                        MapId: group.MapId,
                        RoomId: group.RoomId,
                        SpawnTemplateKey: group.SpawnTemplateKey,
                        SpawnCellRow: group.SpawnCellRow,
                        SpawnCellCol: group.SpawnCellCol,
                        SurfaceId: group.SurfaceId,
                        PatrolStartRatio: group.PatrolStartRatio,
                        PatrolEndRatio: group.PatrolEndRatio,
                        SpawnRatio: ResolveSpawnRatio(group, i, spawnTemplate.SpawnCount),
                        MoveSpeed: group.MoveSpeed,
                        IsActive: true));
                }
            }

            return encounters;
        }

        public IReadOnlyList<MapMonsterSpawnGroup> GetActiveSpawnGroups(string mapId, int roomId)
        {
            if (!_spawnGroups.TryGetValue(MonsterCatalogSeed.ToRosterKey(mapId, roomId), out var groups))
            {
                return [];
            }

            var activeGroups = new List<MapMonsterSpawnGroup>(groups.Count);
            foreach (var group in groups)
            {
                if (group.IsActive)
                {
                    activeGroups.Add(group);
                }
            }

            return activeGroups;
        }

        public MapMonsterEncounter? FindEncounter(string mapId, int roomId, string monsterKey)
        {
            foreach (var encounter in GetActiveRoster(mapId, roomId))
            {
                if (string.Equals(encounter.MonsterKey, monsterKey, StringComparison.OrdinalIgnoreCase))
                {
                    return encounter;
                }
            }

            return null;
        }

        private static string BuildMonsterKey(string spawnGroupKey, int instanceNumber) =>
            $"{spawnGroupKey}:{instanceNumber:000}".ToUpperInvariant();

        private static float ResolveSpawnRatio(
            MapMonsterSpawnGroup group,
            int spawnInstanceIndex,
            int spawnCount)
        {
            var start = Clamp01(group.SpawnStartRatio);
            var end = Clamp01(group.SpawnEndRatio);

            if (end < start)
            {
                (start, end) = (end, start);
            }

            if (spawnCount <= 1)
            {
                return start + ((end - start) * 0.5f);
            }

            var step = (end - start) / Math.Max(1, spawnCount - 1);
            var centeredOffset = step * 0.18f;
            var ratio = start + (step * spawnInstanceIndex);

            if ((spawnInstanceIndex & 1) == 1)
            {
                ratio -= centeredOffset;
            }
            else
            {
                ratio += centeredOffset;
            }

            return Clamp01(ratio);
        }

        private static float Clamp01(float value)
        {
            if (value < 0f)
            {
                return 0f;
            }

            if (value > 1f)
            {
                return 1f;
            }

            return value;
        }
    }
}
