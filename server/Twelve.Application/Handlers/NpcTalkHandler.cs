using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Application.Npcs;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public sealed class NpcTalkHandler : IPacketHandler
    {
        private const int TagNpcId = 9;
        private const int TagContinue = 40;

        private readonly INpcMissionCatalog _missionCatalog;
        private readonly IPlayerMissionStateRepository _missionStateRepository;

        public NpcTalkHandler(
            INpcMissionCatalog missionCatalog,
            IPlayerMissionStateRepository missionStateRepository)
        {
            _missionCatalog = missionCatalog;
            _missionStateRepository = missionStateRepository;
        }

        public Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command != (int)CommandCode.NpcTalkRequest)
            {
                return Task.CompletedTask;
            }

            var npcId = request.GetStringTag(TagNpcId);
            if (string.IsNullOrWhiteSpace(npcId))
            {
                return SendResponseAsync(session, ok: false, npcId: null, message: null, missions: [], error: "invalid_request");
            }

            if (!session.IsAuthenticated)
            {
                return SendResponseAsync(session, ok: false, npcId: npcId, message: null, missions: [], error: "unauthenticated");
            }

            // Java evidence: om.java calls ks.a().a(ki2.f.a, bl2), where ki2.f is the focused jo NPC record.
            var context = _missionCatalog.GetTalkContext("Hoa Lu", 1, npcId);
            if (context is null)
            {
                return SendResponseAsync(session, ok: false, npcId: npcId, message: null, missions: [], error: "not_found");
            }

            SendProgressNotifications(session, awaitProgressForTalkNpc(session.Username, npcId));

            var isContinue = request.GetByteTag(TagContinue) == 1;
            var message = BuildTalkMessage(context, isContinue);

            return SendResponseAsync(session, ok: true, npcId: npcId, message: message, missions: context.Missions, error: null);
        }

        private IReadOnlyList<MissionProgressUpdate> awaitProgressForTalkNpc(string? username, string npcId)
        {
            if (string.IsNullOrWhiteSpace(username))
                return [];

            return _missionStateRepository
                .AddProgressAsync(username, "TalkNpc", npcId, 1)
                .GetAwaiter()
                .GetResult();
        }

        private static void SendProgressNotifications(GameSession session, IReadOnlyList<MissionProgressUpdate> updates)
        {
            foreach (var update in updates)
            {
                session.SendPacketAsync(MissionPacketFactory.BuildMissionTaskNotification(update)).GetAwaiter().GetResult();
                session.SendPacketAsync(MissionPacketFactory.BuildMissionUpdate(update)).GetAwaiter().GetResult();
                if (update.MissionCompleted)
                {
                    session.SendPacketAsync(MissionPacketFactory.BuildMissionNotification(update)).GetAwaiter().GetResult();
                }
            }
        }

        private static string BuildTalkMessage(NpcTalkContext context, bool isContinue)
        {
            if (context.Missions.Count == 0)
                return isContinue ? $"{context.DisplayName}: Chua co nhiem vu moi." : $"{context.DisplayName}: Xin chao.";

            var firstMission = context.Missions[0];
            return isContinue
                ? $"{context.DisplayName}: Hay xem nhiem vu {firstMission.Title}."
                : $"{context.DisplayName}: Ta co {context.Missions.Count} nhiem vu cho nguoi.";
        }

        private static Task SendResponseAsync(
            GameSession session,
            bool ok,
            string? npcId,
            string? message,
            IReadOnlyList<MissionSummary> missions,
            string? error)
        {
            var payload = JsonSerializer.SerializeToUtf8Bytes(new NpcTalkSocketEnvelope(ok, npcId, message, missions, error));
            return session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.NpcTalkResponseRemake, payload));
        }

        private sealed record NpcTalkSocketEnvelope(
            bool Ok,
            string? NpcId,
            string? Message,
            IReadOnlyList<MissionSummary> Missions,
            string? Error
        );
    }
}
