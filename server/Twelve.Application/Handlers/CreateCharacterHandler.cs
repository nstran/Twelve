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

            // ── Base stats công bằng cho Balance v1.1 ─────────────────────────
            // Nguồn suy luận:
            // - PLAYER_CHARACTER_RECONSTRUCTION.md / 08-level-stat-exp-and-element-balance.md
            // - Java evidence chỉ xác nhận lh.h/i/j/k là 4 stat gốc; không có bằng chứng server cũ
            //   cho việc trừ dump-stat còn 5 khi tạo nhân vật.
            // Mọi hệ bắt đầu 10/10/10/10.
            // Element chỉ quyết định MainElement/affinity/skill tree/khắc hệ.
            // FreePoints khởi tạo = 0; điểm tiềm năng chỉ cộng khi lên level
            // qua PlayerLevelProgression (+5 mỗi level).
            (player.CuongLuc, player.ThanPhap, player.NoiLuc, player.TheLuc) = (10, 10, 10, 10);
            player.FreePoints = 0;
            player.SkillPoints = 0;
            player.Honor = 0;
            player.TitleMain = ResolveDefaultTitle(player.Level);
            player.TitleSub = string.Empty;
            player.TitleRank = string.Empty;
            player.BonusCuongLuc = 0;
            player.BonusThanPhap = 0;
            player.BonusNoiLuc = 0;
            player.BonusTheLuc = 0;

            // ── Derived stats/resources ───────────────────────────────────────
            // PlayerStatPipeline là nguồn truth duy nhất cho MaxHP/MaxMP/Power/derived stats.
            // Không set HP theo hpMultiplier cũ tại đây để tránh lệch với runtime/equipment preview.
            PlayerStatPipeline.RecalculateAndApply(player);
            player.Hp = player.MaxHp;
            player.Mp = player.MaxMp;
            player.Power = 0;

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
