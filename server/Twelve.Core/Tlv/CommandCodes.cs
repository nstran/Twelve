namespace Twelve.Core.Tlv
{
    public enum CommandCode : byte
    {
        // ── Auth & Session ───────────────────────────────────────────
        LoginRequest            = 2,
        TokenLoginRequest       = 3,    // Client gửi token để tự đăng nhập lại
        LoginSuccess            = 4,    // Payload: Tag Token(15) + ExpiresAt(16)
        LoginFailed             = 0,

        RegisterRequest         = 1,
        RegisterResponse        = 131,

        // ── Character Creation & Info ────────────────────────────────
        CharacterRequired       = 5,    // Server yêu cầu tạo nhân vật sau login
        CreateCharacterRequest  = 6,    // Client gửi các lựa chọn nhân vật
        CharacterInfo           = 7,    // Server trả diện mạo nhân vật sau login thành công
        CreateCharacterResponse = 136,  // Phản hồi kết quả tạo nhân vật

        // ── Game Commands ─────────────────────────────────────────────
        EnterGame               = 10,
        PlayerInfo              = 11,
        NpcTalkRequest          = 16,   // Java evidence: ks.a().a(String, boolean) builds kw(16).
        MissionList             = 31,   // Java evidence: mission list request/response.
        MissionAccept           = 32,   // Java evidence: mission accept request with tag 77.
        MissionDetail           = 33,   // Java evidence: mission detail request/response with tag 77.
        MissionTaskNotify       = 34,   // Java evidence: mission task/objective notification.
        MissionNotify           = 35,   // Java evidence: mission/new/complete notification.
        MissionUpdate           = 38,   // Java evidence: mission progress/update notification.
        MissionCancel           = 41,   // Java evidence: mission cancel request with tag 77.
        MapMonsterRoster        = 43,   // Existing RN monster roster packet; keep until roster command migration is decided.
        MapNpcRosterRemake      = 45,   // Temporary RN-safe NPC roster command; Java evidence remains raw command 43.
        NpcTalkResponseRemake   = 46,   // RN-safe ack/dialog envelope for the reconstructed NPC talk request.

        // Java equipment commands — returned to original Java IDs per user decision 2026-05-04.
        // ky.java/ks.java evidence: 96=shop/buy, 97=equip/unequip, 99=upgrade, 100=repair/use, 112=combine/forge.
        EquipmentShopBuy        = 96,
        EquipmentEquipUnequip   = 97,
        EquipmentUpgrade        = 99,
        EquipmentRepairUse      = 100,
        EquipmentCombineForge   = 112,

        // Monster bootstrap — migrated from 96/97 to 240/241 to avoid Java equipment command conflict.
        MonsterBootstrapRequest = 240,
        MonsterBootstrapResponse= 241,
        MapLoad                 = 20,

        // ── Stat Allocation ───────────────────────────────────────────
        // Ref: combat-formulas.md § 11
        AllocateStatRequest     = 50,   // Client → Server: Tag StatChoice (50) = 0/1/2/3
        AllocateStatResponse    = 180   // Server → Client: updated base stats + combat stats
    }

    public enum TagCode : byte
    {
        Message         = 1,
        Token           = 2,    // Session token (UUID string)
        ExpiresAt       = 3,    // Unix timestamp seconds (long, 8 bytes)
        Username        = 9,
        Password        = 10,
        FullName        = 11,
        DateOfBirth     = 12,
        Phone           = 13,
        Gender          = 14,

        // Character Traits (CMD 6)
        Element         = 20,
        Face            = 21,
        HairStyle       = 22,
        HairColor       = 23,
        SkinColor       = 24,
        GenderStyle     = 25,   // 0=Nam, 1=Nữ (diện mạo nhân vật)

        // Java lh identity / bars
        RawElement      = 15,   // lh.g — 1=Hoa, 2=Loi, 4=Thuy
        RawGender       = 16,   // lh.f
        CurrentHp       = 17,   // lh.s
        CurrentMp       = 18,   // lh.u
        Level           = 27,   // lh.G
        ExperienceValue = 42,   // lh.J
        QuanProgress    = 43,   // lh.H
        CurrentPower    = 45,   // lh.w
        JavaMaxHp       = 47,   // lh.r
        MaxMp           = 48,   // lh.t
        MaxPower        = 49,   // lh.v
        ExperienceFloor = 73,   // lh.M
        ExperienceCeiling = 74, // lh.N
        SkillPoints     = 76,   // lh.L
        QuanProgressCap = 99,   // lh.I
        Honor           = 160,  // lh.ab
        TitlePrimary    = 209,  // lh.S
        TitleSecondary  = 210,  // lh.R
        WalletQuan      = 211,  // client-new: separate wallet value

        // Stat Allocation (CMD 50 / 180)
        StatChoice      = 50,   // 0=CuongLuc, 1=ThanPhap, 2=NoiLuc, 3=TheLuc

        // Base stats — mapped từ Java TLV tags (ky.java)
        // Ref: combat-formulas.md § 10 — Mapping C# ↔ Java ↔ TLV
        FreePoints      = 53,   // lh.K  — điểm chưa phân
        CuongLuc        = 118,  // lh.h  — Strength
        ThanPhap        = 119,  // lh.j  — Agility
        NoiLuc          = 120,  // lh.i  — Magic
        TheLuc          = 121,  // lh.k  — Vitality
        BonusCuongLuc   = 196,  // lh.l
        BonusThanPhap   = 197,  // lh.m
        BonusNoiLuc     = 198,  // lh.n
        BonusTheLuc     = 199,  // lh.o

        // Combat stats (response tags — server-computed)
        MaxHp           = 130,
        TanCong         = 131,  // Attack
        ChinhXac        = 132,  // Accuracy
        PThu            = 133,  // Defense
        NeTranh         = 134,  // Dodge
        ChiMang         = 135,  // Crit %

        // Java equipment ll/lb parser tags — ky.java a(ku,int,int,boolean).
        EquipmentArray      = 83,   // repeated nested equipment record
        EquipmentSlot       = 84,   // ll.e byte
        EquipmentKey        = 186,  // equip key/string in command wrappers
        EquipmentToggleMode = 187,  // equip/unequip mode in command wrappers
        ResourceId          = 4,    // ll.n int
        EnhancementLevel    = 27,   // ll.j int
        CurrentDurability   = 139,  // ll.p int
        DisplayName         = 26,   // ll.d string
        RequiredLevel       = 135,  // ll.i int
        ElementIcon         = 15,   // ll.f byte
        EquipmentGender     = 16,   // ll.h byte
        EquipmentRank       = 138,  // ll.m byte
        MaxDurability       = 144,  // ll.q int
        Summary             = 117,  // ll.g string
        UnknownS            = 156,  // ll.s byte, meaning pending/unverified
        Tradeable           = 85,   // ll.t byte
        RepairCost          = 190,  // ll.k byte in Java parser ll.c()
        DamageAbsorbPercent = 200,  // lb.j
        ArmorPiercePercent  = 201,  // lb.k
        BlockPercent        = 202,  // lb.l
        RevivePercent       = 203,  // lb.m
        AttackPercent       = 204,  // lb.n
        HpPercent           = 221   // lb.o
    }
}
