using System.Collections.Generic;
using System.Threading.Tasks;
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

                var actorPayload = BuildSceneActors(session.Username ?? "Player", DefaultMapId, DefaultRoomId);
                await session.SendPacketAsync(TlvCodec.BuildPacket(43, actorPayload));
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

        private byte[] BuildSceneActors(string username, string mapId, int roomId)
        {
            var room = ResolveRoom(mapId, roomId);
            var roster = _mapMonsterRosterService.GetActiveRoster(mapId, roomId);
            var monsterActors = new List<(string Id, string Label, int Kind, int X, int Y)>();

            foreach (var encounter in roster)
            {
                var spawnTemplate = _monsterSpawnCatalog.GetBySpawnTemplateKey(encounter.SpawnTemplateKey);
                if (spawnTemplate is null)
                {
                    continue;
                }

                var (x, y) = ToWorldPosition(encounter.SpawnCellRow, encounter.SpawnCellCol, room.TileSize);
                monsterActors.Add((encounter.MonsterKey, spawnTemplate.DisplayName, spawnTemplate.VisualTypeByte, x, y));
            }

            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, mapId));
            tags.AddRange(TlvCodec.MakeTag(40, (byte)(1 + monsterActors.Count)));

            // 1. Player
            tags.AddRange(TlvCodec.MakeTag(9, username));
            tags.AddRange(TlvCodec.MakeTag(26, username));
            tags.AddRange(TlvCodec.MakeTag(27, 100)); // Warrior
            tags.AddRange(TlvCodec.MakeTag(102, room.CenterX));
            tags.AddRange(TlvCodec.MakeTag(103, room.CenterY));

            foreach (var actor in monsterActors)
            {
                tags.AddRange(TlvCodec.MakeTag(9, actor.Id));
                tags.AddRange(TlvCodec.MakeTag(26, actor.Label));
                tags.AddRange(TlvCodec.MakeTag(27, actor.Kind));
                tags.AddRange(TlvCodec.MakeTag(102, actor.X));
                tags.AddRange(TlvCodec.MakeTag(103, actor.Y));
            }

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
