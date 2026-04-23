using System.Collections.Generic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterAssetCatalog : IMonsterAssetCatalog
    {
        private readonly IReadOnlyDictionary<string, MonsterAssetCatalogEntry> _entries =
            MonsterCatalogSeed.AssetCatalogEntries;

        public MonsterAssetCatalogEntry? GetById(string assetCatalogId) =>
            _entries.TryGetValue(assetCatalogId, out var entry) ? entry : null;
    }
}
