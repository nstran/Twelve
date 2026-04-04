using System.Threading.Tasks;
using System.Collections.Generic;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Maps;

namespace Twelve.Application.Handlers
{
    public class MapHandler : IPacketHandler
    {
        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 11)
            {
                string mapName = request.GetStringTag(20) ?? "Hoa Lu";
                int roomId = 1; // Default to 1 for now

                System.Console.WriteLine($"[MapHandler] Request Map Info: {mapName}");

                if (mapName == "M99")
                {
                    await SendWorldMapHotspots(session);
                }
                else
                {
                    await SendMapInfo(session, mapName, roomId);
                }
            }
            else if (request.Command == 13)
            {
                // CMD 13 = Select Map
                string targetMap = request.GetStringTag(20) ?? "Hoa Lu";
                int roomId = 1; // From tag 21 if present

                System.Console.WriteLine($"[MapHandler] Selection Request: {targetMap}");

                // Send Cmd 13 Ack
                var payload = new List<byte>();
                payload.AddRange(TlvCodec.MakeTag(20, targetMap));
                payload.AddRange(TlvCodec.MakeTag(21, roomId));
                payload.AddRange(TlvCodec.MakeTag(22, (byte)0)); // Type 0

                byte[] packet = TlvCodec.BuildPacket(13, payload.ToArray(), 3);
                await session.SendPacketAsync(packet);

                System.Console.WriteLine($"[MapHandler] Sent CMD 13 Ack for {targetMap}");
            }
            else if (request.Command == 29)
            {
                // CMD 29 = Map Join Request
                string username = session.Username ?? "Guest";
                string mapName = request.GetStringTag(20) ?? "Hoa Lu";
                int roomId = 1;

                System.Console.WriteLine($"[MapHandler] Join Request: {username} -> {mapName}");

                // Send Cmd 29 Ack
                var payload = new List<byte>();
                payload.AddRange(TlvCodec.MakeTag(9, username));
                payload.AddRange(TlvCodec.MakeTag(20, mapName));
                payload.AddRange(TlvCodec.MakeTag(21, roomId));

                byte[] packet = TlvCodec.BuildPacket(29, payload.ToArray(), 3);
                await session.SendPacketAsync(packet);

                System.Console.WriteLine($"[MapHandler] Sent CMD 29 Ack for {username}");
            }
        }

        private async Task SendWorldMapHotspots(GameSession session)
        {
            var payload = new List<byte>();
            payload.AddRange(TlvCodec.MakeTag(12, (byte)0)); 
            payload.AddRange(TlvCodec.MakeTag(20, "M99"));
            
            // Append entry for Hoa Lu on World Map
            payload.AddRange(TlvCodec.MakeTag(21, 0)); // ID 0
            payload.AddRange(TlvCodec.MakeTag(26, "Hoa Lu"));
            payload.AddRange(TlvCodec.MakeTag(22, (byte)0)); // MarkerType 0
            payload.AddRange(TlvCodec.MakeTag(102, 120)); // CenterX
            payload.AddRange(TlvCodec.MakeTag(103, 220)); // CenterY
            payload.AddRange(TlvCodec.MakeTag(104, 64)); // Width
            payload.AddRange(TlvCodec.MakeTag(105, 40)); // Height
            payload.AddRange(TlvCodec.MakeTag(101, (byte)1)); // Enabled 1
            payload.AddRange(TlvCodec.MakeTag(4, 0)); // IconId 0

            byte[] packet = TlvCodec.BuildPacket(11, payload.ToArray(), 9); 
            await session.SendPacketAsync(packet);
        }

        private async Task SendMapInfo(GameSession session, string mapName, int roomId)
        {
            if (!MapDataStore.Maps.TryGetValue(mapName, out var rooms) || rooms.Count < roomId)
            {
                System.Console.WriteLine($"[MapHandler] Error: Map {mapName} or Room {roomId} not found.");
                return;
            }

            var room = rooms[roomId - 1];
            int size = room.Width * room.Height;
            byte[] emptyLayer = new byte[size];

            var payload = new List<byte>();
            payload.AddRange(TlvCodec.MakeTag(12, (byte)1)); // Type: Room Info
            payload.AddRange(TlvCodec.MakeTag(20, mapName));
            payload.AddRange(TlvCodec.MakeTag(26, room.Label));
            payload.AddRange(TlvCodec.MakeTag(41, 5120)); // Flags
            payload.AddRange(TlvCodec.MakeTag(56, room.Width));
            payload.AddRange(TlvCodec.MakeTag(57, room.Height));
            payload.AddRange(TlvCodec.MakeTag(58, room.TileSize));
            payload.AddRange(TlvCodec.MakeTag(59, room.TileSize));
            payload.AddRange(TlvCodec.MakeTag(55, emptyLayer)); // Ground
            payload.AddRange(TlvCodec.MakeTag(54, emptyLayer)); // Decoration
            payload.AddRange(TlvCodec.MakeTag(61, room.LogicLayer)); // Logic
            payload.AddRange(TlvCodec.MakeTag(60, room.TilesetId));
            payload.AddRange(TlvCodec.MakeTag(63, room.BackgroundId));
            payload.AddRange(TlvCodec.MakeTag(29, 0)); // Overlay

            // Entry data
            payload.AddRange(TlvCodec.MakeTag(21, room.Id));
            payload.AddRange(TlvCodec.MakeTag(26, room.Label));
            payload.AddRange(TlvCodec.MakeTag(22, (byte)1)); // MarkerType 1
            payload.AddRange(TlvCodec.MakeTag(102, room.CenterX));
            payload.AddRange(TlvCodec.MakeTag(103, room.CenterY));
            payload.AddRange(TlvCodec.MakeTag(104, room.EntryWidth));
            payload.AddRange(TlvCodec.MakeTag(105, room.EntryHeight));
            payload.AddRange(TlvCodec.MakeTag(101, (byte)1)); // Enabled 1
            payload.AddRange(TlvCodec.MakeTag(4, 0)); // IconId 0
            
            payload.AddRange(TlvCodec.MakeTag(6, 0)); // Padding
            payload.AddRange(TlvCodec.MakeTag(6, 0)); // Padding

            byte[] packet = TlvCodec.BuildPacket(11, payload.ToArray(), 25); 
            await session.SendPacketAsync(packet);
        }
    }
}
