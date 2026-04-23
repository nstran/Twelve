using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleSkillCastPacketService : IBattleSkillCastPacketService
    {
        private readonly IBattleTurnEngine _battleTurnEngine;
        private readonly IBattleSkillPacketFactory _packetFactory;

        public BattleSkillCastPacketService(
            IBattleTurnEngine battleTurnEngine,
            IBattleSkillPacketFactory packetFactory)
        {
            _battleTurnEngine = battleTurnEngine;
            _packetFactory = packetFactory;
        }

        public BattleSkillRuntimePacket? CreatePacket(BattleSkillCastRequest request)
        {
            var seed = _battleTurnEngine.ResolvePlayerCast(request);
            return seed is null ? null : _packetFactory.CreatePacket(seed);
        }
    }
}
