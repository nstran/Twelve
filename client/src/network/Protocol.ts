/**
 * ── Giao thức Loạn 12 Sứ Quân 2026 ───────────────────────────────────────────
 * Bản sớ định danh các lệnh CMD và Tag để code chuyên nghiệp và an toàn.
 */

export enum Command {
    // ── Auth & Account ───────────────────────────────────────────────
    LOGIN_REQUEST       = 2,
    TOKEN_LOGIN_REQUEST = 3,    // Client gửi token để auto-login
    LOGIN_SUCCESS       = 4,    // Payload: Token(2) + ExpiresAt(3)
    LOGIN_FAILED        = 0,

    REGISTER_REQUEST    = 1,
    REGISTER_RESPONSE   = 131,

    // ── Character Creation ───────────────────────────────────────────
    CHARACTER_REQUIRED  = 5,    // Server yêu cầu tạo nhân vật
    CREATE_CHAR_REQUEST = 6,    // Client gửi các lựa chọn nhân vật
    CHARACTER_INFO      = 7,    // Server trả diện mạo nhân vật sau login
    CREATE_CHAR_RESPONSE= 136,  // Phản hồi kết quả tạo nhân vật

    // ── Game Commands ─────────────────────────────────────────────
    ENTER_GAME          = 10,
    PLAYER_INFO         = 11,
    NPC_TALK_REQUEST    = 16,
    MISSION_LIST        = 31,
    MISSION_ACCEPT_ACK  = 32,
    MISSION_DETAIL      = 33,
    MISSION_TASK_NOTIFY = 34,
    MISSION_NOTIFY      = 35,
    MISSION_UPDATE      = 38,
    MISSION_CANCEL_ACK  = 41,
    MAP_MONSTER_ROSTER  = 43,
    MAP_NPC_ROSTER_REMAKE = 45,
    NPC_TALK_RESPONSE_REMAKE = 46,
    // Remake-only RN battle bootstrap. Migrated away from 96/97 so Java equipment
    // commands can keep their original IDs.
    MONSTER_BOOTSTRAP_REQUEST = 240,
    MONSTER_BOOTSTRAP_RESPONSE = 241,
    MAP_LOAD            = 20,
    MOVE                = 44
}

export enum Tag {
    MESSAGE         = 1,
    TOKEN           = 2,    // Session token (UUID string)
    EXPIRES_AT      = 3,    // Unix timestamp seconds (8 bytes)
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
    SKIN_COLOR      = 24,
    GENDER_STYLE    = 25,   // 0=Nam, 1=Nữ (diện mạo nhân vật)

    // Java lh character truth tags
    RAW_ELEMENT     = 15,
    RAW_GENDER      = 16,
    CURRENT_HP      = 17,
    CURRENT_MP      = 18,
    LEVEL           = 27,
    EXP_VALUE       = 42,
    QUAN_PROGRESS   = 43,
    CURRENT_POWER   = 45,
    MAX_HP          = 47,
    MAX_MP          = 48,
    MAX_POWER       = 49,
    FREE_POINTS     = 53,
    EXP_FLOOR       = 73,
    EXP_CEILING     = 74,
    SKILL_POINTS    = 76,
    QUAN_CAP        = 99,
    CUONG_LUC       = 118,
    THAN_PHAP       = 119,
    NOI_LUC         = 120,
    THE_LUC         = 121,
    ATTACK          = 131,
    ACCURACY        = 132,
    DEFENSE         = 133,
    DODGE           = 134,
    CRIT            = 135,
    HONOR           = 160,
    TITLE_PRIMARY   = 209,
    TITLE_SECONDARY = 210,
    WALLET_QUAN     = 211
}
