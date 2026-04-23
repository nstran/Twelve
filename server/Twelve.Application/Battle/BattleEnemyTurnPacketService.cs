using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleEnemyTurnPacketService : IBattleEnemyTurnPacketService
    {
        private readonly IBattleTurnEngine _battleTurnEngine;
        private readonly IBattleSkillPacketFactory _packetFactory;

        public BattleEnemyTurnPacketService(
            IBattleTurnEngine battleTurnEngine,
            IBattleSkillPacketFactory packetFactory)
        {
            _battleTurnEngine = battleTurnEngine;
            _packetFactory = packetFactory;
        }

        public BattleSkillRuntimePacket? CreatePacket(BattleEnemyTurnRequest request)
        {
            var seed = _battleTurnEngine.ResolveEnemyTurn(request);
            return seed is null ? null : _packetFactory.CreatePacket(seed);
        }
    }
}
