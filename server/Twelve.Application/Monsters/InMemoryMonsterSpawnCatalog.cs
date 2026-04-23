using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterSpawnCatalog : IMonsterSpawnCatalog
    {
        private readonly Dictionary<string, MonsterSpawnTemplate> _templates =
            new(StringComparer.OrdinalIgnoreCase)
            {
                // Scaffold content for the current Hoa Lu map prototype. The goal here is
                // to move authority out of client hard-code while keeping the repo's
                // existing fire / ice / zap visual families intact.
                ["hoa_lu_fire_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_fire_basic",
                    DisplayName: "Gà Điên",
                    VisualTypeByte: 0,
                    DisplayLevel: 7,
                    IqValue: 2,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_fire_basic",
                    AssetCatalogId: "monster_species_1000_slot_0"),
                ["hoa_lu_ice_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_ice_basic",
                    DisplayName: "Băng Linh",
                    VisualTypeByte: 2,
                    DisplayLevel: 8,
                    IqValue: 8,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_ice_basic",
                    AssetCatalogId: "monster_species_1002_slot_0"),
                ["hoa_lu_zap_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_zap_basic",
                    DisplayName: "Lôi Thú",
                    VisualTypeByte: 1,
                    DisplayLevel: 9,
                    IqValue: 11,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_zap_basic",
                    AssetCatalogId: "monster_species_1003_slot_0")
            };

        public MonsterSpawnTemplate? GetBySpawnTemplateKey(string spawnTemplateKey) =>
            _templates.TryGetValue(spawnTemplateKey, out var template) ? template : null;

        public IReadOnlyList<MonsterSpawnTemplate> GetAll() => _templates.Values.ToList();
    }
}
