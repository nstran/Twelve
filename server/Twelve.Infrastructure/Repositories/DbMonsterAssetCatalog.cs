using System;
using System.Collections.Generic;
using System.Text.Json;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbMonsterAssetCatalog : IMonsterAssetCatalog
    {
        private readonly Dictionary<string, MonsterAssetCatalogEntry> _cache = new(StringComparer.OrdinalIgnoreCase);

        public DbMonsterAssetCatalog(IDbConnectionFactory connectionFactory)
        {
            using var conn = connectionFactory.CreateConnection();
            var rows = conn.Query<AssetRow>(
                @"SELECT AssetCatalogId, DisplayName, SpeciesCode, Slot, SharedSheetFamily, FramePaths
                  FROM Monsters
                  WHERE IsCandidate = FALSE");

            foreach (var row in rows)
            {
                var framePaths = ParseJsonStringArray(row.FramePaths);
                var family = (MonsterSharedSheetFamily)(row.SharedSheetFamily ?? 0);

                _cache[row.AssetCatalogId] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: row.AssetCatalogId,
                    DisplayName: row.DisplayName ?? string.Empty,
                    SharedSheetFamily: family,
                    SpeciesCode: row.SpeciesCode,
                    Slot: row.Slot,
                    FramePaths: framePaths);
            }
        }

        public MonsterAssetCatalogEntry? GetById(string assetCatalogId) =>
            _cache.TryGetValue(assetCatalogId, out var entry) ? entry : null;

        private static IReadOnlyList<string> ParseJsonStringArray(string? json)
        {
            if (string.IsNullOrWhiteSpace(json))
                return Array.Empty<string>();

            try
            {
                return JsonSerializer.Deserialize<string[]>(json) ?? Array.Empty<string>();
            }
            catch
            {
                return Array.Empty<string>();
            }
        }

        private sealed class AssetRow
        {
            public string AssetCatalogId { get; set; } = "";
            public string? DisplayName { get; set; }
            public int? SpeciesCode { get; set; }
            public int? Slot { get; set; }
            public int? SharedSheetFamily { get; set; }
            public string? FramePaths { get; set; }
        }
    }
}
