using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public static class MonsterSpawnDisplayResolver
    {
        /// <summary>
        /// Prefer <see cref="MonsterAssetCatalogEntry.DisplayName"/> for roster packets when the spawn links an asset.
        /// </summary>
        public static Dictionary<string, MonsterSpawnTemplate> EnrichTemplatesForEncounters(
            IReadOnlyList<MapMonsterEncounter> encounters,
            IMonsterSpawnCatalog spawnCatalog,
            IMonsterAssetCatalog assetCatalog)
        {
            var enriched = spawnCatalog.GetAll()
                .ToDictionary(t => t.SpawnTemplateKey, t => t, StringComparer.OrdinalIgnoreCase);

            foreach (var enc in encounters)
            {
                if (!enriched.TryGetValue(enc.SpawnTemplateKey, out var st))
                    continue;
                if (string.IsNullOrWhiteSpace(st.AssetCatalogId))
                    continue;
                var asset = assetCatalog.GetById(st.AssetCatalogId);
                if (asset is null || string.IsNullOrWhiteSpace(asset.DisplayName))
                    continue;
                enriched[enc.SpawnTemplateKey] = st with { DisplayName = asset.DisplayName };
            }

            return enriched;
        }
    }
}
