using Twelve.Core.Entities;
using Twelve.Core.Monsters;

namespace Twelve.Core.Interfaces
{
    public interface IMonsterBattleBootstrapService
    {
        MonsterBattleBootstrapResponse? Bootstrap(
            MonsterBattleBootstrapRequest request,
            PlayerAggregate? playerAggregate = null);
    }
}
