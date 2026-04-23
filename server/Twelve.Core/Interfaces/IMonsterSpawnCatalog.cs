using System.Collections.Generic;
using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMonsterSpawnCatalog
    {
        MonsterSpawnTemplate? GetBySpawnTemplateKey(string spawnTemplateKey);
        IReadOnlyList<MonsterSpawnTemplate> GetAll();
    }
}
