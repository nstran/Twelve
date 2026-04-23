using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleEnemyTurnPacketService
    {
        BattleSkillRuntimePacket? CreatePacket(BattleEnemyTurnRequest request);
    }
}
