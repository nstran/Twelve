using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterSpawnCatalog : IMonsterSpawnCatalog
    {
        private readonly IReadOnlyDictionary<string, MonsterSpawnTemplate> _templates =
            MonsterCatalogSeed.SpawnTemplates;

        public MonsterSpawnTemplate? GetBySpawnTemplateKey(string spawnTemplateKey) =>
            _templates.TryGetValue(spawnTemplateKey, out var template) ? template : null;

        public IReadOnlyList<MonsterSpawnTemplate> GetAll() => _templates.Values.ToList();
    }
}
