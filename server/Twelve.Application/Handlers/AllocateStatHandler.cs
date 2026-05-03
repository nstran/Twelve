using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Twelve.Core;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 50 — Phân điểm tiềm năng (AllocateStatRequest)
    //
    //  Tags nhận vào:
    //    50 (StatChoice) = int  →  0=CuongLuc, 1=ThanPhap, 2=NoiLuc, 3=TheLuc
    //
    //  Ref: combat-formulas.md § 11 — Stat Point Distribution
    //  Flow:
    //    1. Validate session + FreePoints > 0 + StatChoice in [0,3]
    //    2. player.{ChosenStat} += 1; player.FreePoints -= 1;
    //    3. RecalculateAndApply(player)  → MaxHp updated
    //    4. Save to DB
    //    5. Return CMD 180 với toàn bộ base stats + combat stats
    // ═══════════════════════════════════════════════════════════════════════════
    public class AllocateStatHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly ILogger<AllocateStatHandler> _logger;

        public AllocateStatHandler(
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            ILogger<AllocateStatHandler> logger)
        {
            _playerRepository = playerRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _logger = logger;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            _logger.LogInformation("[AllocateStat] HandleAsync for '{Username}'", session.Username);

            // ── Guard: phải đăng nhập ─────────────────────────────────────────
            if (!session.IsAuthenticated || string.IsNullOrEmpty(session.Username))
            {
                _logger.LogWarning("[AllocateStat] Session not authenticated");
                await SendError(session, "Chua dang nhap.");
                return;
            }

            // ── Lấy player từ DB ──────────────────────────────────────────────
            var player = await _playerRepository.GetByUsernameAsync(session.Username);
            if (player == null || player.Element == null)
            {
                _logger.LogWarning("[AllocateStat] Player not found or not initialized for '{Username}'", session.Username);
                await SendError(session, "Nhan vat chua duoc khoi tao.");
                return;
            }
            var aggregate = await _playerAggregateRepository.GetByUsernameAsync(session.Username);

            // ── Validate FreePoints ───────────────────────────────────────────
            if (player.FreePoints <= 0)
            {
                _logger.LogWarning("[AllocateStat] No FreePoints left for '{Username}'", session.Username);
                await SendError(session, "Khong con diem tiem nang.");
                return;
            }

            // ── Đọc lựa chọn stat (Tag 50) ───────────────────────────────────
            int? statChoice = request.GetIntTag((int)TagCode.StatChoice);
            if (statChoice == null || statChoice < 0 || statChoice > 3)
            {
                _logger.LogWarning("[AllocateStat] Invalid StatChoice={V} for '{Username}'", statChoice, session.Username);
                await SendError(session, "Chi so khong hop le.");
                return;
            }

            // ── Phân điểm: tăng stat được chọn, trừ FreePoints ──────────────
            // Ref: combat-formulas.md § 11 — "player.{ChosenStat} += 1; FreePoints -= 1;"
            switch (statChoice)
            {
                case 0: player.CuongLuc++; break;   // Cường Lực
                case 1: player.ThanPhap++; break;   // Thân Pháp
                case 2: player.NoiLuc++;   break;   // Nội Lực
                case 3: player.TheLuc++;   break;   // Thể Lực
            }
            player.FreePoints--;

            _logger.LogInformation("[AllocateStat] '{Username}' +1 to stat {Stat} → CL={CL} TP={TP} NL={NL} TL={TL} Free={F}",
                session.Username, statChoice,
                player.CuongLuc, player.ThanPhap, player.NoiLuc, player.TheLuc, player.FreePoints);

            // ── Tính lại combat stats + cập nhật MaxHp ───────────────────────
            // Java evidence/remake policy: ll.p/tag139 durability <= 0 means broken equipment remains wearable,
            // but does not contribute stats/effects; ll.e=8 wing participates like other enabled equipment slots.
            var equipmentModifiers = aggregate?.Equipment
                .Where(entry => entry.IsEquipped && entry.Durability > 0)
                .Select(entry => EquipmentStatModifierParser.Parse(entry.RawJson));
            var combat = PlayerStatPipeline.RecalculateAndApply(player, equipmentModifiers);

            _logger.LogInformation("[AllocateStat] Combat recalc → MaxHp={Hp} TanCong={TC} ChinhXac={CX} PThu={PT} NeTranh={NT} ChiMang={CM}%",
                combat.MaxHp, combat.MinDamage, combat.Hit, combat.Defense, combat.Dodge, combat.Crit);

            // ── Lưu DB ────────────────────────────────────────────────────────
            try
            {
                await _playerRepository.UpdateAsync(player);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "[AllocateStat] DB update failed for '{Username}'", session.Username);
                await SendError(session, "Loi luu du lieu. Vui long thu lai.");
                return;
            }

            // ── Gửi response CMD 180 ──────────────────────────────────────────
            // Tags: base stats + FreePoints + combat stats
            var tags = new List<byte[]>
            {
                TlvCodec.MakeTag((int)TagCode.CuongLuc,  player.CuongLuc),
                TlvCodec.MakeTag((int)TagCode.ThanPhap,  player.ThanPhap),
                TlvCodec.MakeTag((int)TagCode.NoiLuc,    player.NoiLuc),
                TlvCodec.MakeTag((int)TagCode.TheLuc,    player.TheLuc),
                TlvCodec.MakeTag((int)TagCode.FreePoints, player.FreePoints),
                TlvCodec.MakeTag((int)TagCode.MaxHp,     combat.MaxHp),
                TlvCodec.MakeTag((int)TagCode.TanCong,   combat.MinDamage),
                TlvCodec.MakeTag((int)TagCode.ChinhXac,  combat.Hit),
                TlvCodec.MakeTag((int)TagCode.PThu,      combat.Defense),
                TlvCodec.MakeTag((int)TagCode.NeTranh,   combat.Dodge),
                TlvCodec.MakeTag((int)TagCode.ChiMang,   combat.Crit),
            };

            byte[] payload = tags.SelectMany(t => t).ToArray();
            byte[] packet  = TlvCodec.BuildPacket(CommandCode.AllocateStatResponse, payload, subCount: tags.Count);
            await session.SendPacketAsync(packet);

            _logger.LogInformation("[AllocateStat] ✓ Response sent to '{Username}'", session.Username);
        }

        private Task SendError(GameSession session, string message)
        {
            var packet = TlvCodec.BuildSingleTagPacket(
                CommandCode.AllocateStatResponse,
                TagCode.Message,
                message);
            return session.SendPacketAsync(packet);
        }
    }
}
