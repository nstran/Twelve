using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleEnemyMoveService
    {
        BattleEnemyMoveResponse? CreateMove(BattleEnemyMoveRequest request);
    }
}
