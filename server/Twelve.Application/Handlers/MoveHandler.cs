using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Interfaces;
using Twelve.Core.Entities;

namespace Twelve.Application.Handlers
{
    public class MoveHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;

        public MoveHandler(IPlayerRepository playerRepository)
        {
            _playerRepository = playerRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 44) // Move Request
            {
                int targetX = request.GetIntTag(102) ?? 0;
                int targetY = request.GetIntTag(103) ?? 0;

                // 1. Update In-Session state (In-memory)
                System.Console.WriteLine($"[MoveHandler] User {session.Username} moving to ({targetX}, {targetY})");

                // 2. Persist to PostgreSQL (SHIHEN)
                var player = await _playerRepository.GetByUsernameAsync(session.Username ?? "");
                if (player != null)
                {
                    // Update activity and last position
                    player.LastSeenAt = System.DateTime.UtcNow;
                    await _playerRepository.UpdateAsync(player);
                }

                // 3. Broadcast Echo (Confirm move to user)
                byte[] echo = TlvCodec.BuildPacket(44, request.RawPayload);
                await session.SendPacketAsync(echo);
            }
        }
    }
}
