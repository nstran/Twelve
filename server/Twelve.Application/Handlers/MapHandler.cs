using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Monsters;
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

                var monsterRosterPayload = BuildMonsterRosterPayload(DefaultMapId, DefaultRoomId);
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

        private byte[] BuildMonsterRosterPayload(string mapId, int roomId)
        {
            var groups = _mapMonsterRosterService.GetActiveSpawnGroups(mapId, roomId);
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, mapId));
            tags.AddRange(TlvCodec.MakeTag(40, (byte)0));

            foreach (var group in groups)
            {
                var spawnTemplate = _monsterSpawnCatalog.GetBySpawnTemplateKey(group.SpawnTemplateKey);
                if (spawnTemplate is null)
                {
                    continue;
                }

                var rosterEntryPayload = BuildMonsterRosterEntryPayload(group, spawnTemplate);
                tags.AddRange(TlvCodec.MakeTag(9, rosterEntryPayload));
            }

            return tags.ToArray();
        }

        private static byte[] BuildMonsterRosterEntryPayload(
            MapMonsterSpawnGroup group,
            MonsterSpawnTemplate spawnTemplate)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(9, group.SpawnGroupKey));
            tags.AddRange(TlvCodec.MakeTag(26, spawnTemplate.DisplayName));
            tags.AddRange(TlvCodec.MakeTag(27, spawnTemplate.DisplayLevel));
            tags.AddRange(TlvCodec.MakeTag(15, spawnTemplate.VisualTypeByte));
            tags.AddRange(TlvCodec.MakeTag(129, spawnTemplate.IqValue));
            tags.AddRange(TlvCodec.MakeTag(106, spawnTemplate.SpawnCount));
            tags.AddRange(TlvCodec.MakeTag(107, spawnTemplate.NameColorMode));

            return tags.ToArray();
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
