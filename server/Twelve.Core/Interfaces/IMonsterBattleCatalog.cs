using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMonsterBattleCatalog
    {
        MonsterBattleTemplate? GetById(long id);
    }
}
