using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;
using Twelve.Core.Players;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    /// <summary>
    /// Handles Java equipment commands 96/97/99/100/112.
    /// <para>
    /// Java evidence: ky.java case 96/97/99/100/112 response parser + ks.java sender payloads.
    /// Each command has a specific TLV tag shape that the Java client expects.
    /// </para>
    /// <para>
    /// Phase 1 scope: cmd 112 (equip/unequip) is fully functional via existing
    /// <see cref="PlayerRuntimeService.UpdateEquipment"/>. Commands 96/97/99/100 return
    /// evidence-correct TLV stub responses with message indicating feature is pending,
    /// so the Java client can parse without crash but the server does not mutate state.
    /// </para>
    /// </summary>
    public sealed class EquipmentCommandHandler : IPacketHandler
    {
        // ── Tag IDs from Java ks.java sender / ky.java parser ──────────────
        // Java evidence: ks.java case 96 sends tag 83 (equipKey).
        // Java evidence: ks.java case 97/100 sends tags 186 (session), 187 (action), 83? (equipKey), 114? (itemId), 106? (count).
        // Java evidence: ks.java case 112 sends tag 83 (equipKey) + 132 (gold), or 114+106+132, or 175 (text).
        private const int TagEquipKey = 83;       // ll.c — equipment unique key (string)
        private const int TagSession = 186;       // session key (string)
        private const int TagAction = 187;        // action/mode byte: 0=upgrade, 1=combine-ish
        private const int TagItemId = 114;        // item id (int)
        private const int TagItemCount = 106;     // item count (int)
        private const int TagGold = 132;          // gold/Quan (long → int for current scope)
        private const int TagMessage = 1;         // message string
        private const int TagReadyStatus = 188;   // ready status byte
        private const int TagIgnoredText = 175;   // text tag read but not used by client in cmd 112
        private const int TagGoldLong = 157;      // gold long in cmd 112 response

        private readonly IPlayerRuntimeService _playerRuntimeService;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;

        public EquipmentCommandHandler(
            IPlayerRuntimeService playerRuntimeService,
            IPlayerAggregateRepository playerAggregateRepository)
        {
            _playerRuntimeService = playerRuntimeService;
            _playerAggregateRepository = playerAggregateRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (!session.IsAuthenticated || string.IsNullOrWhiteSpace(session.Username))
            {
                return;
            }

            var username = session.Username;

            switch (request.Command)
            {
                case (int)CommandCode.EquipmentCombineForge:
                    // Java evidence: cmd 112 is processEquipChange — equip/unequip toggle.
                    // ky.java case 112: reads tag 83 (equipKey). If present → equip/unequip.
                    // Also reads tag 114 (itemId) for item grant/consume callback, but that is
                    // a server→client response path; client sends tag 83 for equip toggle.
                    await HandleEquipUnequip(session, request, username);
                    break;

                case (int)CommandCode.EquipmentShopBuy:
                    // Java evidence: cmd 96 = requestUpgradeEquipment.
                    // Client sends tag 83 (equipKey). Server responds with tags 186+83+1.
                    await HandleUpgradeRequest(session, request, username);
                    break;

                case (int)CommandCode.EquipmentEquipUnequip:
                    // Java evidence: cmd 97 = modifiedUpgradeEquipment result.
                    // Client sends tags 186+187+83?+114?+106?. Server responds with same shape.
                    await HandleUpgradeResult(session, request, username);
                    break;

                case (int)CommandCode.EquipmentUpgrade:
                    // Java evidence: cmd 99 = requestCombineEquipment.
                    // Client sends empty payload. Server responds with tags 186+1.
                    await HandleCombineRequest(session, request);
                    break;

                case (int)CommandCode.EquipmentRepairUse:
                    // Java evidence: cmd 100 = modifiedCombineEquipment result.
                    // Client sends tags 186+187+83?+114?+106?. Server responds with same shape as 97.
                    await HandleCombineResult(session, request);
                    break;

                default:
                    break;
            }
        }

        // ════════════════════════════════════════════════════════════════════
        // CMD 112 — Equip/Unequip (fully functional)
        // ════════════════════════════════════════════════════════════════════

        /// <summary>
        /// Java evidence: ky.java case 112 — processEquipChange.
        /// Client sends tag 83 (equipKey). Server toggles equip state.
        /// Response shape: tag 83 (equipKey) + tag 175 (ignored text) + tag 157 (gold long).
        /// If item grant: tag 114 (itemId) + tag 106 (count).
        /// </summary>
        private async Task HandleEquipUnequip(GameSession session, PacketRequest request, string username)
        {
            var equipKey = request.GetStringTag(TagEquipKey);
            if (string.IsNullOrWhiteSpace(equipKey))
            {
                // No equipKey → might be item grant callback shape; ignore for now.
                return;
            }

            // Determine current equip state to toggle
            var aggregate = await _playerAggregateRepository.GetByUsernameAsync(username);
            if (aggregate is null)
            {
                return;
            }

            var entry = FindEquipmentByKey(aggregate, equipKey);
            if (entry is null)
            {
                // Java evidence: cmd 112 response with equipKey + message.
                await SendEquipChangeResponse(session, equipKey, "Khong tim thay trang bi.", 0);
                return;
            }

            // Toggle: if currently equipped → unequip, otherwise equip
            bool newEquipState = !entry.IsEquipped;

            var result = _playerRuntimeService.UpdateEquipment(
                new PlayerEquipmentRuntimeRequest(username, equipKey, newEquipState));

            if (result is null)
            {
                return;
            }

            // Java evidence: ky.java case 112 response reads:
            //   tag 83 (equipKey) → if not null: read tag 175 (ignored), tag 157 (gold), call b.a(equipKey, gold)
            // Gold = player's current Quan after operation.
            var updatedAggregate = await _playerAggregateRepository.GetByUsernameAsync(username);
            long currentGold = updatedAggregate?.Core.Gold ?? 0;

            await SendEquipChangeResponse(session, equipKey, result.Message ?? "", currentGold);
        }

        /// <summary>
        /// Build cmd 112 response with equipKey path.
        /// Java evidence: ky.java case 112 — tag 83 present path.
        /// </summary>
        private static async Task SendEquipChangeResponse(GameSession session, string equipKey, string message, long gold)
        {
            using var ms = new MemoryStream();
            // Java evidence: ky.java reads tag 83 first, then 175 (ignored text), then 157 (gold long).
            ms.Write(TlvCodec.MakeTag(TagEquipKey, equipKey));
            ms.Write(TlvCodec.MakeTag(TagIgnoredText, message));
            ms.Write(TlvCodec.MakeTag(TagGoldLong, gold));

            var payload = ms.ToArray();
            await session.SendPacketAsync(TlvCodec.BuildPacket(
                (int)CommandCode.EquipmentCombineForge, payload, subCount: 3));
        }

        // ════════════════════════════════════════════════════════════════════
        // CMD 96 — Upgrade Request (stub — pending upgrade policy)
        // ════════════════════════════════════════════════════════════════════

        /// <summary>
        /// Java evidence: ky.java case 96 — processRequestUpgradeEquipment.
        /// Client sends tag 83 (equipKey).
        /// Server responds: tag 186 (session) + tag 83 (equipKey) + tag 1 (message).
        /// </summary>
        private async Task HandleUpgradeRequest(GameSession session, PacketRequest request, string username)
        {
            var equipKey = request.GetStringTag(TagEquipKey);

            // Java evidence: ky.java case 96 response shape: tags 186, 83, 1.
            // This command prepares/acks the upgrade target; the mutation happens in cmd 97.
            using var ms = new MemoryStream();
            ms.Write(TlvCodec.MakeTag(TagSession, username));
            if (!string.IsNullOrWhiteSpace(equipKey))
            {
                ms.Write(TlvCodec.MakeTag(TagEquipKey, equipKey));
            }
            ms.Write(TlvCodec.MakeTag(TagMessage, "Da chon trang bi nang cap."));

            var payload = ms.ToArray();
            await session.SendPacketAsync(TlvCodec.BuildPacket(
                (int)CommandCode.EquipmentShopBuy, payload, subCount: 3));
        }

        // ════════════════════════════════════════════════════════════════════
        // CMD 97 — Upgrade Result (stub — pending upgrade policy)
        // ════════════════════════════════════════════════════════════════════

        /// <summary>
        /// Java evidence: ky.java case 97 — processModifiedUpgradeEquipment.
        /// Client sends tags 186 (session) + 187 (action) + 83? + 114? + 106?.
        /// Server responds: tags 186, 187, 83, 114, 106, 132, 1, 188.
        /// </summary>
        private async Task HandleUpgradeResult(GameSession session, PacketRequest request, string username)
        {
            var sessionKey = request.GetStringTag(TagSession) ?? username;
            var action = request.GetByteTag(TagAction);
            var equipKey = request.GetStringTag(TagEquipKey);
            var itemId = request.GetIntTag(TagItemId) ?? -1;
            var itemCount = request.GetIntTag(TagItemCount) ?? 1;
            var materialItemIds = ReadRepeatedIntTags(request.RawPayload, TagItemId);

            if (string.IsNullOrWhiteSpace(equipKey))
            {
                await SendUpgradeResultResponse(
                    session,
                    sessionKey,
                    action,
                    null,
                    itemId,
                    itemCount,
                    0,
                    "Thieu ma trang bi.",
                    readyStatus: 0);
                return;
            }

            // Remake policy 2026-05-04: user approved Huyet thach/Kim thach/luck charms
            // while Java server material ids/rates remain pending. Java evidence here is TLV shape only.
            var result = _playerRuntimeService.UpgradeEquipment(
                new PlayerUpgradeEquipmentRuntimeRequest(username, equipKey, materialItemIds));

            if (result is null)
            {
                return;
            }

            var updatedAggregate = await _playerAggregateRepository.GetByUsernameAsync(username);
            long currentGold = updatedAggregate?.Core.Gold ?? 0;

            await SendUpgradeResultResponse(
                session,
                sessionKey,
                action,
                equipKey,
                itemId,
                itemCount,
                currentGold,
                result.Message ?? string.Empty,
                readyStatus: 1);
        }

        private static async Task SendUpgradeResultResponse(
            GameSession session,
            string sessionKey,
            byte action,
            string? equipKey,
            int itemId,
            int itemCount,
            long gold,
            string message,
            byte readyStatus)
        {
            using var ms = new MemoryStream();
            ms.Write(TlvCodec.MakeTag(TagSession, sessionKey));
            ms.Write(TlvCodec.MakeTag(TagAction, action));
            if (!string.IsNullOrWhiteSpace(equipKey))
            {
                ms.Write(TlvCodec.MakeTag(TagEquipKey, equipKey));
            }
            if (itemId > 0)
            {
                ms.Write(TlvCodec.MakeTag(TagItemId, itemId));
                ms.Write(TlvCodec.MakeTag(TagItemCount, itemCount));
            }
            ms.Write(TlvCodec.MakeTag(TagGold, gold));
            ms.Write(TlvCodec.MakeTag(TagMessage, message));
            ms.Write(TlvCodec.MakeTag(TagReadyStatus, readyStatus));

            var payload = ms.ToArray();
            await session.SendPacketAsync(TlvCodec.BuildPacket(
                (int)CommandCode.EquipmentEquipUnequip, payload, subCount: 8));
        }

        // ════════════════════════════════════════════════════════════════════
        // CMD 99 — Combine Request (stub — pending combine policy)
        // ════════════════════════════════════════════════════════════════════

        /// <summary>
        /// Java evidence: ky.java case 99 — processRequestCombineEquipment.
        /// Client sends empty payload (ks.java case 99 = break/no tags).
        /// Server responds: tag 186 (session) + tag 1 (message).
        /// </summary>
        private async Task HandleCombineRequest(GameSession session, PacketRequest request)
        {
            // Java evidence: ky.java case 99 response shape: tags 186, 1.
            using var ms = new MemoryStream();
            ms.Write(TlvCodec.MakeTag(TagSession, session.Username ?? ""));
            ms.Write(TlvCodec.MakeTag(TagMessage, "Chuc nang ket hop dang duoc phat trien."));

            var payload = ms.ToArray();
            await session.SendPacketAsync(TlvCodec.BuildPacket(
                (int)CommandCode.EquipmentUpgrade, payload, subCount: 2));
        }

        // ════════════════════════════════════════════════════════════════════
        // CMD 100 — Combine Result (stub — pending combine policy)
        // ════════════════════════════════════════════════════════════════════

        /// <summary>
        /// Java evidence: ky.java case 100 — processModifiedCombineEquipment.
        /// Same tag shape as cmd 97 but dispatches combine callbacks.
        /// </summary>
        private async Task HandleCombineResult(GameSession session, PacketRequest request)
        {
            var sessionKey = request.GetStringTag(TagSession) ?? "";
            var action = request.GetByteTag(TagAction);
            var equipKey = request.GetStringTag(TagEquipKey);

            // Java evidence: ky.java case 100 response shape — same as 97.
            using var ms = new MemoryStream();
            ms.Write(TlvCodec.MakeTag(TagSession, sessionKey));
            ms.Write(TlvCodec.MakeTag(TagAction, action));
            if (!string.IsNullOrWhiteSpace(equipKey))
            {
                ms.Write(TlvCodec.MakeTag(TagEquipKey, equipKey));
            }
            ms.Write(TlvCodec.MakeTag(TagMessage, "Chuc nang ket hop dang duoc phat trien."));
            ms.Write(TlvCodec.MakeTag(TagReadyStatus, (byte)0));

            var payload = ms.ToArray();
            await session.SendPacketAsync(TlvCodec.BuildPacket(
                (int)CommandCode.EquipmentRepairUse, payload, subCount: 5));
        }

        // ════════════════════════════════════════════════════════════════════
        // Helpers
        // ════════════════════════════════════════════════════════════════════

        private static IReadOnlyList<int> ReadRepeatedIntTags(byte[] payload, int tagId)
        {
            var values = new List<int>();
            var pos = 0;
            while (pos <= payload.Length - 5)
            {
                var id = payload[pos];
                var len = (payload[pos + 1] << 24) | (payload[pos + 2] << 16) | (payload[pos + 3] << 8) | payload[pos + 4];
                pos += 5;
                if (len < 0 || pos + len > payload.Length)
                {
                    break;
                }

                if (id == tagId)
                {
                    if (len == 1)
                    {
                        values.Add(payload[pos]);
                    }
                    else if (len == 2)
                    {
                        values.Add((payload[pos] << 8) | payload[pos + 1]);
                    }
                    else if (len == 4)
                    {
                        values.Add((payload[pos] << 24) | (payload[pos + 1] << 16) | (payload[pos + 2] << 8) | payload[pos + 3]);
                    }
                }

                pos += len;
            }
            return values;
        }

        private static PlayerEquipmentEntry? FindEquipmentByKey(PlayerAggregate aggregate, string equipKey)
        {
            foreach (var entry in aggregate.Equipment)
            {
                if (string.Equals(entry.EquipKey, equipKey, System.StringComparison.Ordinal))
                {
                    return entry;
                }
            }
            return null;
        }
    }
}
