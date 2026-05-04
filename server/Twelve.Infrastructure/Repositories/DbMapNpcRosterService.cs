using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbMapNpcRosterService : IMapNpcRosterService
    {
        private readonly ConcurrentDictionary<string, IReadOnlyList<MapNpcRosterEntry>> _rosters = new(StringComparer.OrdinalIgnoreCase);

        public DbMapNpcRosterService(IDbConnectionFactory connectionFactory)
        {
            using var conn = connectionFactory.CreateConnection();
            var rows = conn.Query<RosterRow>(
                @"SELECT r.MapId,
                         r.RoomId,
                         c.NpcKey,
                         COALESCE(r.DisplayNameOverride, c.DisplayName) AS DisplayName,
                         c.VisualTypeByte,
                         c.DisplayLevel,
                         r.TileX,
                         r.TileY,
                         c.NameColorMode,
                         r.SortOrder,
                         r.RosterMode,
                         r.IsActive
                  FROM NpcMapRosters r
                  INNER JOIN NpcCatalog c ON c.Id = r.NpcCatalogId
                  WHERE r.IsActive = TRUE
                  ORDER BY r.MapId, r.RoomId, r.SortOrder, c.SpriteAssetId, c.NpcKey");

            var byRosterKey = new Dictionary<string, List<MapNpcRosterEntry>>(StringComparer.OrdinalIgnoreCase);
            foreach (var row in rows)
            {
                if (!row.IsActive)
                    continue;

                // Java evidence: map NPC packet is a per-map jo[] roster. DB rows only decide which jo records are emitted.
                var entry = new MapNpcRosterEntry(
                    NpcId: row.NpcKey,
                    DisplayName: row.DisplayName,
                    VisualTypeByte: (byte)row.VisualTypeByte,
                    DisplayLevel: row.DisplayLevel,
                    TileX: row.TileX,
                    TileY: row.TileY,
                    NameColorMode: (byte)row.NameColorMode);

                var rosterKey = ToRosterKey(row.MapId, row.RoomId);
                if (!byRosterKey.TryGetValue(rosterKey, out var list))
                {
                    list = new List<MapNpcRosterEntry>();
                    byRosterKey[rosterKey] = list;
                }

                list.Add(entry);
            }

            foreach (var (key, roster) in byRosterKey)
            {
                _rosters[key] = roster;
            }
        }

        public IReadOnlyList<MapNpcRosterEntry> GetActiveRoster(string mapId, int roomId)
        {
            if (_rosters.TryGetValue(ToRosterKey(mapId, roomId), out var roster))
                return roster;

            return [];
        }

        private static string ToRosterKey(string mapId, int roomId) => $"{mapId}#{roomId}";

        private sealed class RosterRow
        {
            public string MapId { get; set; } = "";
            public int RoomId { get; set; }
            public string NpcKey { get; set; } = "";
            public string DisplayName { get; set; } = "";
            public int VisualTypeByte { get; set; }
            public int DisplayLevel { get; set; }
            public int TileX { get; set; }
            public int TileY { get; set; }
            public int NameColorMode { get; set; }
            public int SortOrder { get; set; }
            public int RosterMode { get; set; }
            public bool IsActive { get; set; } = true;
        }
    }
}
