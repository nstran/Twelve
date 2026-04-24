using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Dapper;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Players;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class EquipmentCatalogRepository : IEquipmentCatalogRepository
    {
        private readonly IDbConnectionFactory _connectionFactory;

        public EquipmentCatalogRepository(IDbConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<IReadOnlyList<PlayerEquipmentDefinition>> GetAllAsync()
        {
            using var connection = _connectionFactory.CreateConnection();
            var rows = await connection.QueryAsync<EquipmentCatalogRow>(
                @"SELECT TemplateKey,
                         DisplayName,
                         Summary,
                         Slot,
                         ResourceId,
                         Level,
                         RequiredLevel,
                         IconKind,
                         Rank,
                         ElementIcon,
                         Gender,
                         Durability,
                         MaxDurability,
                         Tradeable,
                         RepairCost,
                         IsEnabled,
                         ModifierJson::text AS ModifierJson
                  FROM EquipmentCatalog
                  WHERE IsEnabled = TRUE
                  ORDER BY Slot, ResourceId, TemplateKey");

            return rows
                .Select(row => new PlayerEquipmentDefinition(
                    TemplateKey: row.TemplateKey,
                    DisplayName: row.DisplayName,
                    Summary: row.Summary,
                    Slot: row.Slot,
                    ResourceId: row.ResourceId,
                    Level: row.Level,
                    RequiredLevel: row.RequiredLevel,
                    IconKind: row.IconKind,
                    Rank: row.Rank,
                    ElementIcon: row.ElementIcon,
                    Gender: row.Gender,
                    Durability: row.Durability,
                    MaxDurability: row.MaxDurability,
                    Tradeable: row.Tradeable,
                    RepairCost: row.RepairCost,
                    IsEnabled: row.IsEnabled,
                    Modifier: EquipmentStatModifierParser.Parse(row.ModifierJson)))
                .ToArray();
        }

        private sealed class EquipmentCatalogRow
        {
            public string TemplateKey { get; init; } = string.Empty;
            public string DisplayName { get; init; } = string.Empty;
            public string Summary { get; init; } = string.Empty;
            public int Slot { get; init; }
            public int ResourceId { get; init; }
            public int Level { get; init; }
            public int RequiredLevel { get; init; }
            public string IconKind { get; init; } = "equipment";
            public int Rank { get; init; }
            public int ElementIcon { get; init; }
            public int Gender { get; init; }
            public int Durability { get; init; }
            public int MaxDurability { get; init; }
            public bool Tradeable { get; init; }
            public long RepairCost { get; init; }
            public bool IsEnabled { get; init; }
            public string ModifierJson { get; init; } = "{}";
        }
    }
}
