using System.Collections.Generic;
using Twelve.Core.Npcs;

namespace Twelve.Core.Interfaces
{
    public interface INpcMissionCatalog
    {
        NpcTalkContext? GetTalkContext(string mapId, int roomId, string npcId);

        IReadOnlyList<MissionSummary> GetAvailableMissions(string mapId, int roomId, string? npcId = null);

        MissionDetail? GetMissionDetail(string missionKey);
    }
}
