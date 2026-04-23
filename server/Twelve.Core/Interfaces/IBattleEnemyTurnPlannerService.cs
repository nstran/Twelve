using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleEnemyTurnPlannerService
    {
        BattleEnemyTurnPlanResponse? CreatePlan(BattleEnemyTurnPlanRequest request);
    }
}
