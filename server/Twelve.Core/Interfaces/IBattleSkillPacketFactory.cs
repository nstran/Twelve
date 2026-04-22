using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleSkillPacketFactory
    {
        BattleSkillRuntimePacket CreatePacket(BattleSkillPacketSeed seed);
    }
}
