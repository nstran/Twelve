using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMonsterAssetCatalog
    {
        MonsterAssetCatalogEntry? GetById(string assetCatalogId);
    }
}
