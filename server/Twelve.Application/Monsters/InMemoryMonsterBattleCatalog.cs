using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterBattleCatalog : IMonsterBattleCatalog
    {
        private readonly IReadOnlyDictionary<string, MonsterBattleTemplate> _templates =
            MonsterCatalogSeed.BattleTemplates;

        public MonsterBattleTemplate? GetByBattleTemplateId(string battleTemplateId) =>
            _templates.TryGetValue(battleTemplateId, out var template) ? template : null;
    }
}
