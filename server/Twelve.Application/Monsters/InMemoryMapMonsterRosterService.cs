using System;
using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMapMonsterRosterService : IMapMonsterRosterService
    {
        private readonly IReadOnlyDictionary<string, IReadOnlyList<MapMonsterEncounter>> _rosters =
            MonsterCatalogSeed.MapRosters;

        public IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId)
        {
            if (_rosters.TryGetValue(MonsterCatalogSeed.ToRosterKey(mapId, roomId), out var roster))
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
    }
}
