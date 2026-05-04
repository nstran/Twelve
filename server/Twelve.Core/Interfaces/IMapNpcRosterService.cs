using System.Collections.Generic;
using Twelve.Core.Npcs;

namespace Twelve.Core.Interfaces
{
    public interface IMapNpcRosterService
    {
        IReadOnlyList<MapNpcRosterEntry> GetActiveRoster(string mapId, int roomId);
    }
}
