using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbMapMonsterRosterService : IMapMonsterRosterService
    {
        private readonly ConcurrentDictionary<string, IReadOnlyList<MapMonsterSpawnGroup>> _spawnGroups = new(StringComparer.OrdinalIgnoreCase);
        private readonly ConcurrentDictionary<string, IReadOnlyList<MapMonsterEncounter>> _baseRosters = new(StringComparer.OrdinalIgnoreCase);
        private readonly ConcurrentDictionary<string, ConcurrentDictionary<string, DateTime>> _inactiveEncounterKeys = new(StringComparer.OrdinalIgnoreCase);

        public DbMapMonsterRosterService(IDbConnectionFactory connectionFactory, IMonsterSpawnCatalog spawnCatalog)
        {
            using var conn = connectionFactory.CreateConnection();
            var rows = conn.Query<RosterRow>(
                @"SELECT r.SpawnGroupKey, r.MapId, r.RoomId, s.SpawnTemplateKey,
                         r.SpawnCellRow, r.SpawnCellCol, r.SurfaceId,
                         r.PatrolStartRatio, r.PatrolEndRatio,
                         r.SpawnStartRatio, r.SpawnEndRatio, r.MoveSpeed, r.IsActive
                  FROM MonsterRosters r
                  INNER JOIN MonsterSpawns s ON s.Id = r.MonsterSpawnId
                  ORDER BY r.MapId, r.RoomId, r.SpawnGroupKey");

            var groupsByRosterKey = new Dictionary<string, List<MapMonsterSpawnGroup>>(StringComparer.OrdinalIgnoreCase);

            foreach (var row in rows)
            {
                var group = new MapMonsterSpawnGroup(
                    SpawnGroupKey: row.SpawnGroupKey,
                    MapId: row.MapId,
                    RoomId: row.RoomId,
                    SpawnTemplateKey: row.SpawnTemplateKey,
                    SpawnCellRow: row.SpawnCellRow,
                    SpawnCellCol: row.SpawnCellCol,
                    SurfaceId: row.SurfaceId ?? "ground_main",
                    PatrolStartRatio: row.PatrolStartRatio,
                    PatrolEndRatio: row.PatrolEndRatio,
                    SpawnStartRatio: row.SpawnStartRatio,
                    SpawnEndRatio: row.SpawnEndRatio,
                    MoveSpeed: row.MoveSpeed,
                    IsActive: row.IsActive);

                var rosterKey = ToRosterKey(row.MapId, row.RoomId);
                if (!groupsByRosterKey.TryGetValue(rosterKey, out var list))
                {
                    list = new List<MapMonsterSpawnGroup>();
                    groupsByRosterKey[rosterKey] = list;
                }
                list.Add(group);
            }

            foreach (var (key, groups) in groupsByRosterKey)
            {
                _spawnGroups[key] = groups;
                _baseRosters[key] = BuildBaseRoster(groups, spawnCatalog);
            }
        }

        public IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId)
        {
            var rosterKey = ToRosterKey(mapId, roomId);
            if (!_baseRosters.TryGetValue(rosterKey, out var baseRoster))
                return [];

            if (!_inactiveEncounterKeys.TryGetValue(rosterKey, out var inactiveKeys) || inactiveKeys.Count == 0)
                return baseRoster;

            var nowUtc = DateTime.UtcNow;
            var activeRoster = new List<MapMonsterEncounter>(baseRoster.Count);
            foreach (var encounter in baseRoster)
            {
                if (inactiveKeys.TryGetValue(encounter.MonsterKey, out var inactiveUntilUtc))
                {
                    if (inactiveUntilUtc > nowUtc)
                        continue;

                    inactiveKeys.TryRemove(encounter.MonsterKey, out _);
                }

                activeRoster.Add(encounter);
            }
            return activeRoster;
        }

        public IReadOnlyList<MapMonsterSpawnGroup> GetActiveSpawnGroups(string mapId, int roomId)
        {
            if (!_spawnGroups.TryGetValue(ToRosterKey(mapId, roomId), out var groups))
                return [];

            var active = new List<MapMonsterSpawnGroup>(groups.Count);
            foreach (var group in groups)
            {
                if (group.IsActive)
                    active.Add(group);
            }
            return active;
        }

        public MapMonsterEncounter? FindEncounter(string mapId, int roomId, string monsterKey)
        {
            foreach (var encounter in GetActiveRoster(mapId, roomId))
            {
                if (string.Equals(encounter.MonsterKey, monsterKey, StringComparison.OrdinalIgnoreCase))
                    return encounter;
            }
            return null;
        }

        public MapMonsterEncounter? DeactivateEncounter(string mapId, int roomId, string monsterKey) =>
            DeactivateEncounterUntil(mapId, roomId, monsterKey, DateTime.MaxValue);

        public MapMonsterEncounter? DeactivateEncounterUntil(string mapId, int roomId, string monsterKey, DateTime inactiveUntilUtc)
        {
            var rosterKey = ToRosterKey(mapId, roomId);
            if (!_baseRosters.TryGetValue(rosterKey, out var baseRoster))
                return null;

            MapMonsterEncounter? encounter = null;
            foreach (var candidate in baseRoster)
            {
                if (string.Equals(candidate.MonsterKey, monsterKey, StringComparison.OrdinalIgnoreCase))
                {
                    encounter = candidate;
                    break;
                }
            }

            if (encounter is null)
                return null;

            var inactiveKeys = _inactiveEncounterKeys.GetOrAdd(
                rosterKey,
                _ => new ConcurrentDictionary<string, DateTime>(StringComparer.OrdinalIgnoreCase));
            inactiveKeys[encounter.MonsterKey] = inactiveUntilUtc;
            return encounter;
        }

        private static string ToRosterKey(string mapId, int roomId) => $"{mapId}#{roomId}";

        private static IReadOnlyList<MapMonsterEncounter> BuildBaseRoster(
            IReadOnlyList<MapMonsterSpawnGroup> groups,
            IMonsterSpawnCatalog spawnCatalog)
        {
            var encounters = new List<MapMonsterEncounter>();

            foreach (var group in groups)
            {
                if (!group.IsActive)
                    continue;

                var spawnTemplate = spawnCatalog.GetBySpawnTemplateKey(group.SpawnTemplateKey);
                if (spawnTemplate is null || spawnTemplate.SpawnCount <= 0)
                    continue;

                for (var i = 0; i < spawnTemplate.SpawnCount; i++)
                {
                    encounters.Add(new MapMonsterEncounter(
                        MonsterKey: $"{group.SpawnGroupKey}:{i + 1:000}".ToUpperInvariant(),
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

        private static float ResolveSpawnRatio(MapMonsterSpawnGroup group, int spawnInstanceIndex, int spawnCount)
        {
            var start = Clamp01(group.SpawnStartRatio);
            var end = Clamp01(group.SpawnEndRatio);
            if (end < start) (start, end) = (end, start);

            if (spawnCount <= 1)
                return start + ((end - start) * 0.5f);

            var step = (end - start) / Math.Max(1, spawnCount - 1);
            var centeredOffset = step * 0.18f;
            var ratio = start + (step * spawnInstanceIndex);

            if ((spawnInstanceIndex & 1) == 1)
                ratio -= centeredOffset;
            else
                ratio += centeredOffset;

            return Clamp01(ratio);
        }

        private static float Clamp01(float value) => value < 0f ? 0f : value > 1f ? 1f : value;

        private sealed class RosterRow
        {
            public string SpawnGroupKey { get; set; } = "";
            public string MapId { get; set; } = "";
            public int RoomId { get; set; }
            public string SpawnTemplateKey { get; set; } = "";
            public int SpawnCellRow { get; set; }
            public int SpawnCellCol { get; set; }
            public string? SurfaceId { get; set; }
            public float PatrolStartRatio { get; set; }
            public float PatrolEndRatio { get; set; }
            public float SpawnStartRatio { get; set; }
            public float SpawnEndRatio { get; set; }
            public float MoveSpeed { get; set; }
            public bool IsActive { get; set; } = true;
        }
    }
}
