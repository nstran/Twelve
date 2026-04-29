-- Module: Monsters — catalog tables (moved from legacy 14_monster_catalog.sql).
-- Upgrade: nếu DB cũ còn cột MonsterBattles.BattleTemplateId dạng TEXT, hãy xóa các bảng
-- MonsterRosters / MonsterSpawns / MonsterBattles rồi khởi động lại server để tạo lại schema.
-- Source: MONSTER_SYSTEM_RECONSTRUCTION.md — preserves Java runtime split:
--   asset catalog / spawn template / battle template / map roster
-- PK/FK policy: surrogate BIGINT Id on every table; legacy string keys are UNIQUE for seeds and tools.

-- 1. Monsters — main species/asset catalog (Java om)
CREATE TABLE IF NOT EXISTS Monsters (
    Id                  BIGSERIAL PRIMARY KEY,
    AssetCatalogId      TEXT NOT NULL UNIQUE,
    DisplayName         TEXT NOT NULL DEFAULT '',
    SpeciesCode         INT,
    Slot                INT,
    SharedSheetFamily   SMALLINT NOT NULL DEFAULT 0,
    FramePaths          JSONB NOT NULL DEFAULT '[]'::jsonb,
    IsCandidate         BOOLEAN NOT NULL DEFAULT FALSE,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_Monsters_Species
    ON Monsters (SpeciesCode, Slot);

-- 2. MonsterBattles — combat stats (Java lh/lv). Created BEFORE MonsterSpawns (FK dependency)
-- StableKey: idempotent seed + human-stable slot (not exposed over wire; FK is always Id).
CREATE TABLE IF NOT EXISTS MonsterBattles (
    Id                  BIGSERIAL PRIMARY KEY,
    StableKey           INT NOT NULL UNIQUE,
    Element             SMALLINT NOT NULL DEFAULT 0,
    Level               INT NOT NULL DEFAULT 1,
    MaxHp               INT NOT NULL DEFAULT 100,
    MaxMp               INT NOT NULL DEFAULT 50,
    MaxPower            INT NOT NULL DEFAULT 100,
    Strength            INT NOT NULL DEFAULT 10,
    Agility             INT NOT NULL DEFAULT 10,
    Magic               INT NOT NULL DEFAULT 10,
    Vitality            INT NOT NULL DEFAULT 10,
    MinDamage           INT NOT NULL DEFAULT 1,
    MaxDamage           INT NOT NULL DEFAULT 5,
    Defense             INT NOT NULL DEFAULT 0,
    HitRate             INT NOT NULL DEFAULT 70,
    DodgeRate           INT NOT NULL DEFAULT 5,
    CriticalRate        INT NOT NULL DEFAULT 5,
    Skills              JSONB NOT NULL DEFAULT '[]'::jsonb,
    Appearance          JSONB NOT NULL DEFAULT '{}'::jsonb,
    AiProfileId         TEXT,
    ExpReward           INT NOT NULL DEFAULT 0,
    GoldReward          INT NOT NULL DEFAULT 0,
    QuanReward          INT NOT NULL DEFAULT 0,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3. MonsterSpawns — lightweight encounter config (Java jo)
CREATE TABLE IF NOT EXISTS MonsterSpawns (
    Id                  BIGSERIAL PRIMARY KEY,
    SpawnTemplateKey    TEXT NOT NULL UNIQUE,
    DisplayName         TEXT NOT NULL,
    VisualTypeByte      SMALLINT NOT NULL DEFAULT 0,
    DisplayLevel        INT NOT NULL DEFAULT 1,
    IqValue             INT NOT NULL DEFAULT 5,
    SpawnCount          INT NOT NULL DEFAULT 1,
    NameColorMode       SMALLINT NOT NULL DEFAULT 0,
    BattleTemplateId    BIGINT NOT NULL,
    MonsterId           BIGINT NULL,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_spawn_battle_template
        FOREIGN KEY (BattleTemplateId)
        REFERENCES MonsterBattles (Id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_spawn_monster
        FOREIGN KEY (MonsterId)
        REFERENCES Monsters (Id)
        ON DELETE SET NULL
);

-- 4. MonsterRosters — runtime map placement authority
CREATE TABLE IF NOT EXISTS MonsterRosters (
    Id                  BIGSERIAL PRIMARY KEY,
    SpawnGroupKey       TEXT NOT NULL,
    MapId               TEXT NOT NULL,
    RoomId              INT NOT NULL DEFAULT 1,
    MonsterSpawnId      BIGINT NOT NULL,
    SpawnCellRow        INT NOT NULL DEFAULT 0,
    SpawnCellCol        INT NOT NULL DEFAULT 0,
    SurfaceId           TEXT NOT NULL DEFAULT 'ground_main',
    PatrolStartRatio    REAL NOT NULL DEFAULT 0.0,
    PatrolEndRatio      REAL NOT NULL DEFAULT 1.0,
    SpawnStartRatio     REAL NOT NULL DEFAULT 0.0,
    SpawnEndRatio       REAL NOT NULL DEFAULT 1.0,
    MoveSpeed           REAL NOT NULL DEFAULT 2.0,
    IsActive            BOOLEAN NOT NULL DEFAULT TRUE,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_roster_monster_spawn
        FOREIGN KEY (MonsterSpawnId)
        REFERENCES MonsterSpawns (Id)
        ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_MonsterRosters_Map
    ON MonsterRosters (MapId, RoomId);

CREATE INDEX IF NOT EXISTS idx_MonsterRosters_Group
    ON MonsterRosters (SpawnGroupKey);
