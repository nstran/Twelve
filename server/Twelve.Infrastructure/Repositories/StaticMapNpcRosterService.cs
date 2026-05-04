using System;
using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Npcs;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class StaticMapNpcRosterService : IMapNpcRosterService
    {
        private static readonly IReadOnlyList<MapNpcRosterEntry> HoaLuRoomOneRoster = new[]
        {
            // Remake policy 2026-05-04: first safe NPC roster seed for Java client command 43.
            new MapNpcRosterEntry(
                NpcId: "tutorial_npc",
                DisplayName: "Huong dan",
                VisualTypeByte: 4,
                DisplayLevel: 0,
                TileX: 6,
                TileY: 23,
                NameColorMode: 2),
        };

        public IReadOnlyList<MapNpcRosterEntry> GetActiveRoster(string mapId, int roomId)
        {
            if (string.Equals(mapId, RuntimeMapCatalog.DefaultMapId, StringComparison.OrdinalIgnoreCase) && roomId == RuntimeMapCatalog.DefaultRoomId)
            {
                return HoaLuRoomOneRoster;
            }

            return Array.Empty<MapNpcRosterEntry>();
        }
    }
}
