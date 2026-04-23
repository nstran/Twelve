using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMapMonsterRosterService : IMapMonsterRosterService
    {
        public IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId)
        {
            if (!MapDataStore.TryGetRoom(mapId, roomId, out var room))
            {
                return [];
            }

            // Placeholder roster only. Content must later move to a real map roster catalog.
            if (!string.Equals(mapId, "Hoa Lu", System.StringComparison.OrdinalIgnoreCase) || roomId != 1)
            {
                return [];
            }

            var encounters = new List<MapMonsterEncounter>();
            var spawnIndex = 0;

            foreach (var (row, col) in EnumerateSpawnCells(room))
            {
                switch (spawnIndex)
                {
                    case 0:
                        encounters.Add(new MapMonsterEncounter(
                            MonsterKey: "HOA_LU_FIRE_001",
                            MapId: mapId,
                            RoomId: roomId,
                            SpawnTemplateKey: "hoa_lu_fire_basic",
                            SpawnCellRow: row,
                            SpawnCellCol: col));
                        break;
                    case 1:
                        encounters.Add(new MapMonsterEncounter(
                            MonsterKey: "HOA_LU_ICE_001",
                            MapId: mapId,
                            RoomId: roomId,
                            SpawnTemplateKey: "hoa_lu_ice_basic",
                            SpawnCellRow: row,
                            SpawnCellCol: col));
                        break;
                    case 2:
                        encounters.Add(new MapMonsterEncounter(
                            MonsterKey: "HOA_LU_ZAP_001",
                            MapId: mapId,
                            RoomId: roomId,
                            SpawnTemplateKey: "hoa_lu_zap_basic",
                            SpawnCellRow: row,
                            SpawnCellCol: col));
                        break;
                }

                spawnIndex++;
                if (encounters.Count == 3)
                {
                    break;
                }
            }

            return encounters;
        }

        public MapMonsterEncounter? FindEncounter(string mapId, int roomId, string monsterKey)
        {
            foreach (var encounter in GetActiveRoster(mapId, roomId))
            {
                if (string.Equals(encounter.MonsterKey, monsterKey, System.StringComparison.OrdinalIgnoreCase))
                {
                    return encounter;
                }
            }

            return null;
        }

        private static IEnumerable<(int Row, int Col)> EnumerateSpawnCells(MapRoom room)
        {
            for (var row = 0; row < room.Height; row++)
            {
                for (var col = 0; col < room.Width; col++)
                {
                    var index = row * room.Width + col;
                    if (index >= room.LogicLayer.Length)
                    {
                        yield break;
                    }

                    if (room.LogicLayer[index] == 2)
                    {
                        yield return (row, col);
                    }
                }
            }
        }
    }
}
