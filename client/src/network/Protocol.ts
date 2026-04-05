/**
 * ── Giao thức Loạn 12 Sứ Quân 2024 ───────────────────────────────────────────
 * Bản sớ định danh các lệnh CMD và Tag để code chuyên nghiệp và an toàn.
 */

export enum Command {
    // ── Auth & Account ───────────────────────────────────────────────
    LOGIN_REQUEST       = 2,
    LOGIN_SUCCESS       = 4,
    LOGIN_FAILED        = 0,
    
    REGISTER_REQUEST    = 1,
    REGISTER_RESPONSE   = 131,
    
    // ── Character Creation ───────────────────────────────────────────
    CHARACTER_REQUIRED  = 5,    // Server yêu cầu tạo nhân vật
    CREATE_CHAR_REQUEST = 6,    // Client gửi các lựa chọn nhân vật
    CREATE_CHAR_RESPONSE= 136,  // Phản hồi kết quả tạo nhân vật
    
    // ── Game Commands ─────────────────────────────────────────────
    ENTER_GAME          = 10,
    PLAYER_INFO         = 11,
    MAP_LOAD            = 20,
    MOVE                = 44
}

export enum Tag {
    MESSAGE         = 1,
    USERNAME        = 9,
    PASSWORD        = 10,
    FULL_NAME       = 11,
    DATE_OF_BIRTH   = 12,
    PHONE           = 13,
    GENDER          = 14,
    
    // Character Traits (CMD 6)
    ELEMENT         = 20,
    FACE            = 21,
    HAIR_STYLE      = 22,
    HAIR_COLOR      = 23,
    SKIN_COLOR      = 24
}
