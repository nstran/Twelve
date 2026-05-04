using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Application.Monsters;
using Twelve.Application.Npcs;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public class MapHandler : IPacketHandler
    {
        private const int TagMapId = 20;
        private const int TagRoomId = 30;
        private const int TagPlayerX = 102;
        private const int TagPlayerY = 103;
        private const int TagPlayerDirection = 104;
        private const int TagPlayerActionState = 105;

        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly IMapMonsterRosterService _mapMonsterRosterService;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;
        private readonly IMonsterAssetCatalog _monsterAssetCatalog;
        private readonly IMapNpcRosterService _mapNpcRosterService;

        public MapHandler(
            IPlayerAggregateRepository playerAggregateRepository,
            IMapMonsterRosterService mapMonsterRosterService,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IMonsterAssetCatalog monsterAssetCatalog,
            IMapNpcRosterService mapNpcRosterService)
        {
            _playerAggregateRepository = playerAggregateRepository;
            _mapMonsterRosterService = mapMonsterRosterService;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _monsterAssetCatalog = monsterAssetCatalog;
            _mapNpcRosterService = mapNpcRosterService;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 11) // Map Info 
            {
                var requestedMapId = request.GetStringTag(TagMapId);
                var requestedRoomId = request.GetIntTag(TagRoomId);
                var playerAggregate = session.IsAuthenticated && !string.IsNullOrWhiteSpace(session.Username)
                    ? await _playerAggregateRepository.GetByUsernameAsync(session.Username)
                    : null;

                var mapId = !string.IsNullOrWhiteSpace(requestedMapId)
                    ? requestedMapId
                    : playerAggregate?.WorldState.MapId ?? RuntimeMapCatalog.DefaultMapId;
                var roomId = requestedRoomId ?? playerAggregate?.WorldState.RoomId ?? RuntimeMapCatalog.DefaultRoomId;
                var room = RuntimeMapCatalog.ResolveRoom(mapId, roomId);

                var mapPayload = BuildMapPayload(room.MapId, room.RoomId, playerAggregate);
                await session.SendPacketAsync(TlvCodec.BuildPacket(11, mapPayload));

                var monsterRosterPayload = BuildMonsterRosterPayload(room.MapId, room.RoomId, mode: 3);
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.MapMonsterRoster, monsterRosterPayload));

                var npcRosterPayload = BuildNpcRosterPayload(room.MapId, room.RoomId, NpcRosterMode.Spawn);
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.MapNpcRosterRemake, npcRosterPayload));
            }
            else if (request.Command == 29) // Map Join
            {
                await session.SendPacketAsync(TlvCodec.BuildPacket(29, request.RawPayload));
            }
        }

        private static byte[] BuildMapPayload(
            string mapId,
            int roomId,
            Twelve.Core.Entities.PlayerAggregate? playerAggregate)
        {
            var room = RuntimeMapCatalog.ResolveRoom(mapId, roomId);
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(20, room.MapId));
            tags.AddRange(TlvCodec.MakeTag(30, room.RoomId));
            tags.AddRange(TlvCodec.MakeTag(56, room.NativeWidth));
            tags.AddRange(TlvCodec.MakeTag(57, room.NativeHeight));
            tags.AddRange(TlvCodec.MakeTag(58, room.TileSize));
            tags.AddRange(TlvCodec.MakeTag(59, room.TileSize));
            tags.AddRange(TlvCodec.MakeTag(55, room.LogicLayer));
            tags.AddRange(TlvCodec.MakeTag(61, room.LogicLayer));

            if (playerAggregate is not null)
            {
                var worldState = playerAggregate.WorldState;
                var isSameRoom = string.Equals(worldState.MapId, room.MapId, System.StringComparison.OrdinalIgnoreCase) &&
                    worldState.RoomId == room.RoomId;
                var position = isSameRoom && worldState.X > 0
                    ? room.ClampPosition(worldState.X, worldState.Y)
                    : room.ClampPosition(room.SpawnX, room.SpawnY);
                var direction = isSameRoom
                    ? NormalizeDirection(worldState.Direction)
                    : 1;
                var actionState = isSameRoom
                    ? System.Math.Max(0, worldState.ActionState)
                    : 0;

                tags.AddRange(TlvCodec.MakeTag(TagPlayerX, position.X));
                tags.AddRange(TlvCodec.MakeTag(TagPlayerY, position.Y));
                tags.AddRange(TlvCodec.MakeTag(TagPlayerDirection, direction));
                tags.AddRange(TlvCodec.MakeTag(TagPlayerActionState, actionState));
            }

            return tags.ToArray();
        }

        private byte[] BuildMonsterRosterPayload(string mapId, int roomId, byte mode)
        {
            var activeRoster = _mapMonsterRosterService.GetActiveRoster(mapId, roomId);
            var spawnTemplates = MonsterSpawnDisplayResolver.EnrichTemplatesForEncounters(
                activeRoster,
                _monsterSpawnCatalog,
                _monsterAssetCatalog);

            return MonsterRuntimePacketFactory.BuildRuntimePacket(
                mapId,
                roomId,
                mode,
                activeRoster,
                spawnTemplates);
        }

        private byte[] BuildNpcRosterPayload(string mapId, int roomId, NpcRosterMode mode)
        {
            var roster = _mapNpcRosterService.GetActiveRoster(mapId, roomId);
            return NpcRosterPacketFactory.BuildRosterPacket(mapId, mode, roster);
        }

        private static int NormalizeDirection(int value) =>
            value <= 0 ? 0 : 1;

    }
}
