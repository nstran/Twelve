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
        MapLoad                 = 20
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
        GenderStyle     = 25    // 0=Nam, 1=Nữ (diện mạo nhân vật)
    }
}
