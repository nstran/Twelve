using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleSkillCastPacketService
    {
        BattleSkillRuntimePacket? CreatePacket(BattleSkillCastRequest request);
    }
}
