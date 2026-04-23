using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMonsterBattleCatalog
    {
        MonsterBattleTemplate? GetByBattleTemplateId(string battleTemplateId);
    }
}
