using System;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;
using Twelve.Core.GameLogic;
using Twelve.Application.Players;

namespace Twelve.Application.Handlers
{
    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 6 — Tạo nhân vật (CreateCharacterRequest)
    //  Tags nhận vào:
    //    20 = Element     (int)
    //    21 = FaceStyle   (int)
    //    22 = HairStyle   (int)
    //    23 = HairColor   (int)
    //    24 = SkinColor   (int)
    //    25 = GenderStyle (int, 0=Nam 1=Nữ)
    // ═══════════════════════════════════════════════════════════════════════════
    public class CreateCharacterHandler : IPacketHandler
    {
        private readonly IPlayerRepository _playerRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly PlayerCharacterPacketFactory _characterPacketFactory;
        private readonly PlayerContentCatalog _contentCatalog;
        private readonly ILogger<CreateCharacterHandler> _logger;

        public CreateCharacterHandler(
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            PlayerCharacterPacketFactory characterPacketFactory,
            PlayerContentCatalog contentCatalog,
            ILogger<CreateCharacterHandler> logger)
        {
            _playerRepository = playerRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _characterPacketFactory = characterPacketFactory;
            _contentCatalog = contentCatalog;
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
            bool isNewRecord = (existing == null);

            // Nếu đã có nhân vật VÀ nhân vật đã được khởi tạo đầy đủ (Element != null) thì báo lỗi
            if (existing != null && existing.Element != null)
            {
                _logger.LogWarning("[CreateChar] Player already exists and is fully initialized for '{Username}'", session.Username);
                await session.SendPacketAsync(TlvCodec.BuildSingleTagPacket(
                    CommandCode.CreateCharacterResponse, 
                    TagCode.Message, 
                    "Nhan vat da ton tai.")
                );
                return;
            }

            // ── Đọc các trường đặc tính (Trait tags) ─────────────────────────
            int gender    = request.GetIntTag((int)TagCode.GenderStyle) ?? 0;
            int element   = request.GetIntTag((int)TagCode.Element)     ?? 0;
            if (!ElementMapper.IsValidStorageCode(element))
            {
                element = ElementMapper.StorageHoa;
            }
            int faceStyle = request.GetIntTag((int)TagCode.Face)        ?? 0;
            int hairStyle = request.GetIntTag((int)TagCode.HairStyle)   ?? 0;
            int hairColor = request.GetIntTag((int)TagCode.HairColor)   ?? 0;
            int skinColor = request.GetIntTag((int)TagCode.SkinColor)   ?? 0;

            _logger.LogInformation("[CreateChar] Choices: Gender={G}, Element={E}, Face={F}, Hair={H}, Color={C}, Skin={S}",
                gender, element, faceStyle, hairStyle, hairColor, skinColor);

            // ── Khởi tạo hoặc Cập nhật nhân vật trong Database ──────────────────────────
            var player = isNewRecord ? new Player { Username = session.Username } : existing!;
            player.Gender    = gender;
            player.Element   = element;
            player.RawElementCode = ElementMapper.ToRawJavaCode(element);
            player.FaceStyle = faceStyle;
            player.HairStyle = hairStyle;
            player.HairColor = hairColor;
            player.SkinColor = skinColor;
            
            player.Level       = 1;
            player.Gold        = 0;
            player.Exp         = 0;
            player.ExpFloor    = 0;
            player.ExpCeiling  = 100;
            player.QuanProgress = 0;
            player.QuanProgressCap = 10000;
            player.CurrentMap  = "M1"; // Bản đồ tân thủ
            player.CurrentRoom = 1;

            // ── Base stats theo element (combat-formulas.md § 10) ─────────────
            // Java type mapping: 0=Hỏa(jq), 1=Lôi(js), 2=Thủy(jr)
            // Primary stat boost +5, Nội/Cường "dump stat" giữ mức 5
            (player.CuongLuc, player.ThanPhap, player.NoiLuc, player.TheLuc) = element switch
            {
                0 => (15, 10,  5, 10),  // Hỏa  — primary Cường Lực
                1 => ( 5, 15,  5, 10),  // Lôi  — primary Thân Pháp
                2 => ( 5, 10, 15, 10),  // Thủy — primary Nội Lực
                _ => (10, 10, 10, 10),  // fallback cân bằng
            };
            player.FreePoints = 5;
            player.SkillPoints = 0;
            player.Honor = 0;
            player.TitleMain = ResolveDefaultTitle(player.Level);
            player.TitleSub = string.Empty;
            player.TitleRank = string.Empty;
            player.BonusCuongLuc = 0;
            player.BonusThanPhap = 0;
            player.BonusNoiLuc = 0;
            player.BonusTheLuc = 0;

            // ── HP/Mana tính từ công thức Java (combat-formulas.md § 4) ──────
            // Sinh Lực (MaxHP) = TheLuc × hệ số theo type (6/4/5)
            int hpMultiplier = element switch { 0 => 6, 1 => 4, 2 => 5, _ => 5 };
            player.MaxHp  = player.TheLuc * hpMultiplier;
            player.Hp     = player.MaxHp;
            // Mana / Power: server quyết định sau; khởi tạo = 0
            player.Mp     = 0;
            player.MaxMp  = 0;
            player.Power  = 0;
            player.MaxPower = 0;
            PlayerStatPipeline.RecalculateAndApply(player);
            player.Hp = player.MaxHp;

            // ── Lưu diện mạo ──────────────────────────────────────────────────
            player.AppearanceHidden0 = false;
            player.AppearanceHidden1 = false;
            player.SpecialActorForm = 0;
            player.AppearanceJson = JsonSerializer.Serialize(new
            {
                source = "create-character",
                gender,
                storageElement = element,
                rawElementCode = player.RawElementCode,
                faceStyle,
                hairStyle,
                hairColor,
                skinColor
            });

            try
            {
                if (isNewRecord)
                    player.Id = await _playerRepository.CreateAsync(player);
                else
                    await _playerRepository.UpdateAsync(player);

                await _playerAggregateRepository.InitializeForCharacterAsync(player);
                await _playerAggregateRepository.SaveCollectionsAsync(
                    player.Id,
                    _contentCatalog.CreateStarterEquipment(player),
                    _contentCatalog.CreateStarterInventory(player),
                    _contentCatalog.CreateStarterSkills(player));
                var aggregate = await _playerAggregateRepository.GetByUsernameAsync(session.Username);

                _logger.LogInformation("[CreateChar] ✓ Success: Player {Mode} for '{Username}'", 
                    isNewRecord ? "Created" : "Updated", session.Username);

                // CMD_CREATE_CHAR_RESPONSE: Trả về thành công
                await session.SendPacketAsync(TlvCodec.BuildSingleTagPacket(
                    CommandCode.CreateCharacterResponse, 
                    TagCode.Message, 
                    "Khoi tao nhan vat thanh cong!")
                );

                if (aggregate is not null)
                {
                    await session.SendPacketAsync(_characterPacketFactory.CreateCharacterInfoPacket(aggregate));
                }
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

        private static string ResolveDefaultTitle(int level) =>
            level switch
            {
                >= 20 => "Cao thu",
                >= 10 => "Hao kiet",
                _ => "Tan thu"
            };
    }
}
