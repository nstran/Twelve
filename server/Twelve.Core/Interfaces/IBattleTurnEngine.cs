using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleTurnEngine
    {
        BattleSkillPacketSeed? ResolvePlayerCast(BattleSkillCastRequest request);
        BattleSkillPacketSeed? ResolveEnemyTurn(BattleEnemyTurnRequest request);
    }
}
