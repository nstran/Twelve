using System;
using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMapMonsterRosterService : IMapMonsterRosterService
    {
        private readonly Dictionary<string, IReadOnlyList<MapMonsterEncounter>> _rosters =
            new(StringComparer.OrdinalIgnoreCase)
            {
                [ToKey("Hoa Lu", 1)] = new[]
                {
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_FIRE_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_fire_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 3,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.25f,
                        PatrolEndRatio: 0.48f,
                        SpawnRatio: 0.40f,
                        MoveSpeed: 2.2f),
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_ICE_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_ice_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 4,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.48f,
                        PatrolEndRatio: 0.72f,
                        SpawnRatio: 0.50f,
                        MoveSpeed: 2.6f),
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_ZAP_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_zap_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 5,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.70f,
                        PatrolEndRatio: 0.92f,
                        SpawnRatio: 0.50f,
                        MoveSpeed: 3.0f),
                }
            };

        public IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId)
        {
            if (_rosters.TryGetValue(ToKey(mapId, roomId), out var roster))
            {
                return roster;
            }

            return [];
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

        private static string ToKey(string mapId, int roomId) => $"{mapId}#{roomId}";
    }
}
