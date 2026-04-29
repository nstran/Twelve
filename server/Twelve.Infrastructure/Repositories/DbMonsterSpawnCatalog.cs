using System;
using System.Collections.Generic;
using System.Linq;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbMonsterSpawnCatalog : IMonsterSpawnCatalog
    {
        private readonly Dictionary<string, MonsterSpawnTemplate> _cache = new(StringComparer.OrdinalIgnoreCase);

        public DbMonsterSpawnCatalog(IDbConnectionFactory connectionFactory)
        {
            using var conn = connectionFactory.CreateConnection();
            var rows = conn.Query<SpawnRow>(
                @"SELECT s.SpawnTemplateKey, s.DisplayName, s.VisualTypeByte,
                         s.DisplayLevel, s.IqValue, s.SpawnCount, s.NameColorMode,
                         s.BattleTemplateId,
                         m.AssetCatalogId AS AssetCatalogId
                  FROM MonsterSpawns s
                  LEFT JOIN Monsters m ON m.Id = s.MonsterId");

            foreach (var row in rows)
            {
                _cache[row.SpawnTemplateKey] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: row.SpawnTemplateKey,
                    DisplayName: row.DisplayName,
                    VisualTypeByte: (byte)(row.VisualTypeByte ?? 0),
                    DisplayLevel: row.DisplayLevel ?? 1,
                    IqValue: row.IqValue ?? 5,
                    SpawnCount: row.SpawnCount ?? 1,
                    NameColorMode: (byte)(row.NameColorMode ?? 0),
                    BattleTemplateId: row.BattleTemplateId,
                    AssetCatalogId: row.AssetCatalogId);
            }
        }

        public MonsterSpawnTemplate? GetBySpawnTemplateKey(string spawnTemplateKey) =>
            _cache.TryGetValue(spawnTemplateKey, out var template) ? template : null;

        public IReadOnlyList<MonsterSpawnTemplate> GetAll() => _cache.Values.ToList();

        private sealed class SpawnRow
        {
            public string SpawnTemplateKey { get; set; } = "";
            public string DisplayName { get; set; } = "";
            public int? VisualTypeByte { get; set; }
            public int? DisplayLevel { get; set; }
            public int? IqValue { get; set; }
            public int? SpawnCount { get; set; }
            public int? NameColorMode { get; set; }
            public long BattleTemplateId { get; set; }
            public string? AssetCatalogId { get; set; }
        }
    }
}
