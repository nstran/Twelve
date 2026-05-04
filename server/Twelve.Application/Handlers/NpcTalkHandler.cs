using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public sealed class NpcTalkHandler : IPacketHandler
    {
        private const int TagNpcId = 9;
        private const int TagContinue = 40;

        private readonly IMapNpcRosterService _mapNpcRosterService;

        public NpcTalkHandler(IMapNpcRosterService mapNpcRosterService)
        {
            _mapNpcRosterService = mapNpcRosterService;
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
                return SendResponseAsync(session, ok: false, npcId: null, message: null, error: "invalid_request");
            }

            if (!session.IsAuthenticated)
            {
                return SendResponseAsync(session, ok: false, npcId: npcId, message: null, error: "unauthenticated");
            }

            // Java evidence: om.java calls ks.a().a(ki2.f.a, bl2), where ki2.f is the focused jo NPC record.
            var isKnownNpc = _mapNpcRosterService
                .GetActiveRoster("Hoa Lu", 1)
                .Any(npc => npc.NpcId == npcId);
            if (!isKnownNpc)
            {
                return SendResponseAsync(session, ok: false, npcId: npcId, message: null, error: "not_found");
            }

            var isContinue = request.GetByteTag(TagContinue) == 1;
            var message = isContinue
                ? "Huong dan: Hay tiep tuc kham pha Hoa Lu."
                : "Huong dan: Chao mung den Hoa Lu.";

            return SendResponseAsync(session, ok: true, npcId: npcId, message: message, error: null);
        }

        private static Task SendResponseAsync(
            GameSession session,
            bool ok,
            string? npcId,
            string? message,
            string? error)
        {
            var payload = JsonSerializer.SerializeToUtf8Bytes(new NpcTalkSocketEnvelope(ok, npcId, message, error));
            return session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.NpcTalkResponseRemake, payload));
        }

        private sealed record NpcTalkSocketEnvelope(
            bool Ok,
            string? NpcId,
            string? Message,
            string? Error
        );
    }
}
