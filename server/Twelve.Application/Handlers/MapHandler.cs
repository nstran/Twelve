using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using Twelve.Application.Monsters;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public class MapHandler : IPacketHandler
    {
        private const string DefaultMapId = "Hoa Lu";
        private const int DefaultRoomId = 1;

        private readonly IMapMonsterRosterService _mapMonsterRosterService;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;

        public MapHandler(
            IPlayerRepository playerRepository,
            IMapMonsterRosterService mapMonsterRosterService,
            IMonsterSpawnCatalog monsterSpawnCatalog)
        {
            _ = playerRepository;
            _mapMonsterRosterService = mapMonsterRosterService;
            _monsterSpawnCatalog = monsterSpawnCatalog;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 11) // Map Info 
            {
                var mapPayload = BuildMapPayload(DefaultMapId, DefaultRoomId);
                await session.SendPacketAsync(TlvCodec.BuildPacket(11, mapPayload));

                var monsterRosterPayload = BuildMonsterRosterPayload(DefaultMapId, DefaultRoomId, mode: 3);
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.MapMonsterRoster, monsterRosterPayload));
            }
            else if (request.Command == 29) // Map Join
            {
                await session.SendPacketAsync(TlvCodec.BuildPacket(29, request.RawPayload));
            }
        }

        private static byte[] BuildMapPayload(string mapId, int roomId)
        {
            var room = ResolveRoom(mapId, roomId);
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, mapId));
            tags.AddRange(TlvCodec.MakeTag(56, room.Width));
            tags.AddRange(TlvCodec.MakeTag(57, room.Height));
            tags.AddRange(TlvCodec.MakeTag(58, room.TileSize));
            tags.AddRange(TlvCodec.MakeTag(59, room.TileSize));
            tags.AddRange(TlvCodec.MakeTag(55, room.LogicLayer));
            tags.AddRange(TlvCodec.MakeTag(61, room.LogicLayer));

            return tags.ToArray();
        }

        private byte[] BuildMonsterRosterPayload(string mapId, int roomId, byte mode)
        {
            var activeRoster = _mapMonsterRosterService.GetActiveRoster(mapId, roomId);
            var spawnTemplates = _monsterSpawnCatalog
                .GetAll()
                .ToDictionary(template => template.SpawnTemplateKey, template => template, System.StringComparer.OrdinalIgnoreCase);

            return MonsterRuntimePacketFactory.BuildRuntimePacket(
                mapId,
                roomId,
                mode,
                activeRoster,
                spawnTemplates);
        }

        private static MapRoom ResolveRoom(string mapId, int roomId)
        {
            if (MapDataStore.TryGetRoom(mapId, roomId, out var room))
            {
                return room;
            }

            return new MapRoom();
        }

        private static (int X, int Y) ToWorldPosition(int row, int col, int tileSize)
        {
            var x = (col * tileSize) + (tileSize / 2);
            var y = (row * tileSize) + (tileSize / 2);
            return (x, y);
        }
    }
}
