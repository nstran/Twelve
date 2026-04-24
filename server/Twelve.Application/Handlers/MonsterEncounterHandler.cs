using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Twelve.Application.Monsters;
using Twelve.Core;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public sealed class MonsterEncounterHandler : IPacketHandler
    {
        private const int TagMapId = 20;
        private const int TagRoomId = 30;
        private const int TagMonsterKey = 9;
        private const int TagInitialTurnSide = 40;

        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            Converters = { new JsonStringEnumConverter() }
        };

        private readonly IMonsterBattleBootstrapService _monsterBattleBootstrapService;
        private readonly IMapMonsterRosterService _mapMonsterRosterService;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;

        public MonsterEncounterHandler(
            IMonsterBattleBootstrapService monsterBattleBootstrapService,
            IMapMonsterRosterService mapMonsterRosterService,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IPlayerAggregateRepository playerAggregateRepository)
        {
            _monsterBattleBootstrapService = monsterBattleBootstrapService;
            _mapMonsterRosterService = mapMonsterRosterService;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _playerAggregateRepository = playerAggregateRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command != (int)CommandCode.MonsterBootstrapRequest)
            {
                return;
            }

            var mapId = request.GetStringTag(TagMapId);
            var roomId = request.GetIntTag(TagRoomId);
            var monsterKey = request.GetStringTag(TagMonsterKey);
            if (string.IsNullOrWhiteSpace(mapId) ||
                string.IsNullOrWhiteSpace(monsterKey) ||
                !roomId.HasValue)
            {
                await SendBootstrapResponseAsync(session, ok: false, data: null, error: "invalid_request");
                return;
            }

            if (!session.IsAuthenticated || string.IsNullOrWhiteSpace(session.Username))
            {
                await SendBootstrapResponseAsync(session, ok: false, data: null, error: "unauthenticated");
                return;
            }

            var playerAggregate = await _playerAggregateRepository.GetByUsernameAsync(session.Username);
            if (playerAggregate is null)
            {
                await SendBootstrapResponseAsync(session, ok: false, data: null, error: "character_not_found");
                return;
            }

            var bootstrapRequest = new MonsterBattleBootstrapRequest(
                MapId: mapId,
                RoomId: roomId.Value,
                MonsterKey: monsterKey,
                InitialTurnSide: request.GetByteTag(TagInitialTurnSide) == 1
                    ? BattleSide.Enemy
                    : BattleSide.Player);

            var response = _monsterBattleBootstrapService.Bootstrap(bootstrapRequest, playerAggregate);
            if (response is null)
            {
                await SendBootstrapResponseAsync(session, ok: false, data: null, error: "not_found");
                return;
            }

            await SendBootstrapResponseAsync(session, ok: true, data: response, error: null);

            var removedEncounter = _mapMonsterRosterService.DeactivateEncounter(
                bootstrapRequest.MapId,
                bootstrapRequest.RoomId,
                bootstrapRequest.MonsterKey);
            if (removedEncounter is null)
            {
                return;
            }

            var spawnTemplates = _monsterSpawnCatalog
                .GetAll()
                .ToDictionary(template => template.SpawnTemplateKey, template => template, System.StringComparer.OrdinalIgnoreCase);
            var removePayload = MonsterRuntimePacketFactory.BuildRuntimePacket(
                bootstrapRequest.MapId,
                bootstrapRequest.RoomId,
                mode: 1,
                encounters: new[] { removedEncounter },
                spawnTemplates: spawnTemplates);
            await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.MapMonsterRoster, removePayload));
        }

        private static Task SendBootstrapResponseAsync(
            GameSession session,
            bool ok,
            Core.Monsters.MonsterBattleBootstrapResponse? data,
            string? error)
        {
            var payload = JsonSerializer.SerializeToUtf8Bytes(
                new MonsterBootstrapSocketEnvelope(ok, data, error),
                JsonOptions);
            return session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.MonsterBootstrapResponse, payload));
        }

        private sealed record MonsterBootstrapSocketEnvelope(
            bool Ok,
            Core.Monsters.MonsterBattleBootstrapResponse? Data,
            string? Error
        );
    }
}
