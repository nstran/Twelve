using System;
using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterAssetCatalog : IMonsterAssetCatalog
    {
        private readonly Dictionary<string, MonsterAssetCatalogEntry> _entries =
            new(StringComparer.OrdinalIgnoreCase)
            {
                // Scaffold content aligned with the current local Hoa Lu monster visuals.
                // These entries preserve the Java-like shared sheet split without claiming
                // that the names/stats are legacy-canon yet.
                ["monster_species_1000_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1000_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Monster,
                    SpeciesCode: 1000,
                    Slot: 0),
                ["monster_species_1002_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1002_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Ice,
                    SpeciesCode: 1002,
                    Slot: 0),
                ["monster_species_1003_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1003_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Zap,
                    SpeciesCode: 1003,
                    Slot: 0)
            };

        public MonsterAssetCatalogEntry? GetById(string assetCatalogId) =>
            _entries.TryGetValue(assetCatalogId, out var entry) ? entry : null;
    }
}
