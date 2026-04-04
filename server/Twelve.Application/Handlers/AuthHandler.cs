using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Handlers
{
    public class AuthHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;

        public AuthHandler(IPlayerRepository playerRepository)
        {
            _playerRepository = playerRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 4) // Login Request
            {
                string username = request.GetStringTag(9) ?? "Guest";
                
                // Try to load player from DB
                var player = await _playerRepository.GetByUsernameAsync(username);
                
                if (player == null)
                {
                    // Create new player if not exists (Auto-registration)
                    player = new Player { Username = username };
                    player.Id = await _playerRepository.CreateAsync(player);
                    System.Console.WriteLine($"[AuthHandler] Created new player: {username}");
                }
                else
                {
                    System.Console.WriteLine($"[AuthHandler] Player logged in: {username} (Level {player.Level})");
                }

                session.Username = username;
                session.IsAuthenticated = true;

                // Respond with CMD 1 (Empty Success)
                byte[] cmd1 = TlvCodec.BuildPacket(1, System.Array.Empty<byte>());
                await session.SendPacketAsync(cmd1);

                // Respond with CMD 2 (Host Info)
                var payload = new System.Collections.Generic.List<byte>();
                payload.AddRange(TlvCodec.MakeTag(2, "127.0.0.1"));
                payload.AddRange(TlvCodec.MakeTag(3, "2026"));
                byte[] cmd2 = TlvCodec.BuildPacket(2, payload.ToArray(), 2);
                await session.SendPacketAsync(cmd2);
            }
        }
    }
}
