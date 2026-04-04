using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Interfaces;
using Twelve.Core.GameLogic;

namespace Twelve.Application.Handlers
{
    public class MapHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;

        public MapHandler(IPlayerRepository playerRepository)
        {
            _playerRepository = playerRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 11) // Map Info 
            {
                var mapPayload = BuildMapPayload();
                await session.SendPacketAsync(TlvCodec.BuildPacket(11, mapPayload));

                var actorPayload = BuildSceneActors(session.Username ?? "Player");
                await session.SendPacketAsync(TlvCodec.BuildPacket(43, actorPayload));
            }
            else if (request.Command == 29) // Map Join
            {
                await session.SendPacketAsync(TlvCodec.BuildPacket(29, request.RawPayload));
            }
        }

        private byte[] BuildMapPayload()
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, "Hoa Lu"));
            tags.AddRange(TlvCodec.MakeTag(56, MapLogic.Width));
            tags.AddRange(TlvCodec.MakeTag(57, MapLogic.Height));
            tags.AddRange(TlvCodec.MakeTag(58, MapLogic.TileSize));
            tags.AddRange(TlvCodec.MakeTag(59, MapLogic.TileSize));
            
            // Build Logic Layer (0-4 are wall, 5-7 are ground)
            byte[] logic = new byte[MapLogic.Width * MapLogic.Height];
            for (int y = 0; y < MapLogic.Height; y++)
            {
                for (int x = 0; x < MapLogic.Width; x++)
                {
                    if (y >= 5 && y <= 7) logic[y * MapLogic.Width + x] = 32; // Walkable
                    else logic[y * MapLogic.Width + x] = 0; // Wall
                }
            }
            tags.AddRange(TlvCodec.MakeTag(55, logic)); // Ground Layer
            tags.AddRange(TlvCodec.MakeTag(61, logic)); // Trigger/Logic Layer
            
            return tags.ToArray();
        }

        private byte[] BuildSceneActors(string username)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, "Hoa Lu"));
            tags.AddRange(TlvCodec.MakeTag(40, (byte)2)); // 2 Actors: Player + Boss

            // 1. Player
            tags.AddRange(TlvCodec.MakeTag(9, username));
            tags.AddRange(TlvCodec.MakeTag(26, username));
            tags.AddRange(TlvCodec.MakeTag(27, 100)); // Warrior
            tags.AddRange(TlvCodec.MakeTag(102, 120)); // X
            tags.AddRange(TlvCodec.MakeTag(103, 160)); // Y (Row 5 starting)

            // 2. [NEW] Boss Monster: "Manh Ho"
            tags.AddRange(TlvCodec.MakeTag(9, "BOSS_001"));
            tags.AddRange(TlvCodec.MakeTag(26, "Manh Ho"));
            tags.AddRange(TlvCodec.MakeTag(27, 200)); // Monster Kind
            tags.AddRange(TlvCodec.MakeTag(102, 280)); // X
            tags.AddRange(TlvCodec.MakeTag(103, 192)); // Y (Row 6)
            
            return tags.ToArray();
        }
    }
}
