using System.Collections.Generic;
using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMapMonsterRosterService
    {
        IReadOnlyList<MapMonsterEncounter> GetActiveRoster(string mapId, int roomId);
        IReadOnlyList<MapMonsterSpawnGroup> GetActiveSpawnGroups(string mapId, int roomId);
        MapMonsterEncounter? FindEncounter(string mapId, int roomId, string monsterKey);
        MapMonsterEncounter? DeactivateEncounter(string mapId, int roomId, string monsterKey);
    }
}
