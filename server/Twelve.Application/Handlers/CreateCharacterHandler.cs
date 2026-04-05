using System;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Handlers
{
    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 6 — Tạo nhân vật (CreateCharacterRequest)
    //  Tags nhận vào:
    //    20 = Element   (int)
    //    21 = FaceStyle (int)
    //    22 = HairStyle (int)
    //    23 = HairColor (int)
    //    24 = SkinColor (int)
    // ═══════════════════════════════════════════════════════════════════════════
    public class CreateCharacterHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;
        private readonly ILogger<CreateCharacterHandler> _logger;

        public CreateCharacterHandler(
            IPlayerRepository playerRepository,
            ILogger<CreateCharacterHandler> logger)
        {
            _playerRepository = playerRepository;
            _logger = logger;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            _logger.LogInformation("[CreateChar] ── HandleAsync fired for '{Username}' ──", session.Username);

            if (!session.IsAuthenticated || string.IsNullOrEmpty(session.Username))
            {
                _logger.LogWarning("[CreateChar] Session not authenticated");
                return;
            }

            // ── Kiểm tra xem nhân vật đã tồn tại chưa ────────────────────────
            var existing = await _playerRepository.GetByUsernameAsync(session.Username);
            if (existing != null)
            {
                _logger.LogWarning("[CreateChar] Player already exists for '{Username}'", session.Username);
                await session.SendPacketAsync(TlvCodec.BuildSingleTagPacket(
                    CommandCode.CreateCharacterResponse, 
                    TagCode.Message, 
                    "Nhan vat da ton tai.")
                );
                return;
            }

            // ── Đọc các trường đặc tính (Trait tags) ─────────────────────────
            int element   = request.GetIntTag((int)TagCode.Element)   ?? 0;
            int faceStyle = request.GetIntTag((int)TagCode.Face)      ?? 0;
            int hairStyle = request.GetIntTag((int)TagCode.HairStyle) ?? 0;
            int hairColor = request.GetIntTag((int)TagCode.HairColor) ?? 0;
            int skinColor = request.GetIntTag((int)TagCode.SkinColor) ?? 0;

            _logger.LogInformation("[CreateChar] Choices: Element={E}, Face={F}, Hair={H}, Color={C}, Skin={S}",
                element, faceStyle, hairStyle, hairColor, skinColor);

            // ── Khởi tạo nhân vật mới trong Database ──────────────────────────
            var newPlayer = new Player
            {
                Username    = session.Username,
                Level       = 1,
                Gold        = 500,  // Tặng chút vàng khởi nghiệp
                Exp         = 0,
                CurrentMap  = "M1", // Bản đồ tân thủ
                CurrentRoom = 1,
                Hp          = 100,
                MaxHp       = 100,
                Mp          = 50,
                MaxMp       = 50,
                
                // Lưu diện mạo
                Element     = element,
                FaceStyle   = faceStyle,
                HairStyle   = hairStyle,
                HairColor   = hairColor,
                SkinColor   = skinColor,
            };

            try
            {
                await _playerRepository.CreateAsync(newPlayer);
                _logger.LogInformation("[CreateChar] ✓ Success: Player created for '{Username}'", session.Username);

                // CMD_CREATE_CHAR_RESPONSE: Trả về thành công
                await session.SendPacketAsync(TlvCodec.BuildSingleTagPacket(
                    CommandCode.CreateCharacterResponse, 
                    TagCode.Message, 
                    "Khoi tao nhan vat thanh cong!")
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "[CreateChar] Error creating player");
                await session.SendPacketAsync(TlvCodec.BuildSingleTagPacket(
                    CommandCode.CreateCharacterResponse, 
                    TagCode.Message, 
                    "Co loi xay ra khi tao nhan vat.")
                );
            }
        }
    }
}
