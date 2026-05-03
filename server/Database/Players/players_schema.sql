-- Module: Players — character aggregate + related child tables.
-- Merged: 02_players, 04_add_player_traits, 05_add_gender, 06_add_base_stats,
--         07_player_character_aggregate (without removed derived cols), 08_equip_loadout.
-- PK/FK policy: Players.Id and all PlayerId FK columns are BIGINT. Equipment row links
-- via EquipmentCatalogId (see equipment_schema.sql).
-- Excluded (migration 12/13): DerivedMinDamage, DerivedMaxDamage, DerivedDefense,
--         DerivedDodge, DerivedHit, DerivedCrit, MaxPower, AppearanceJson.

CREATE TABLE IF NOT EXISTS Players (
    Id BIGSERIAL PRIMARY KEY,
    Username    VARCHAR(50) NOT NULL UNIQUE,
    Level       INT NOT NULL DEFAULT 1,
    Gold        BIGINT NOT NULL DEFAULT 0,
    Exp         BIGINT NOT NULL DEFAULT 0,

    CurrentMap  VARCHAR(50) NULL,
    CurrentRoom INT NULL,

    Hp     INT NOT NULL DEFAULT 100,
    MaxHp  INT NOT NULL DEFAULT 100,
    Mp     INT NOT NULL DEFAULT 50,
    MaxMp  INT NOT NULL DEFAULT 50,

    CreatedAt  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LastSeenAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    Element     INT,
    FaceStyle   INT,
    HairStyle   INT,
    HairColor   INT,
    SkinColor   INT,

    Gender      INT NOT NULL DEFAULT 0,

    CuongLuc   INT NOT NULL DEFAULT 10,
    ThanPhap   INT NOT NULL DEFAULT 10,
    NoiLuc     INT NOT NULL DEFAULT 10,
    TheLuc     INT NOT NULL DEFAULT 10,
    FreePoints INT NOT NULL DEFAULT 5,
    Power      INT NOT NULL DEFAULT 0,

    RawElementCode     INT NOT NULL DEFAULT 1,
    SkillPoints        INT NOT NULL DEFAULT 0,
    Honor              INT NOT NULL DEFAULT 0,
    TitleMain          TEXT,
    TitleSub           TEXT,
    TitleRank          TEXT,
    ExpFloor           BIGINT NOT NULL DEFAULT 0,
    ExpCeiling         BIGINT NOT NULL DEFAULT 100,
    QuanProgress       BIGINT NOT NULL DEFAULT 0,
    QuanProgressCap    BIGINT NOT NULL DEFAULT 10000,
    BonusCuongLuc      INT NOT NULL DEFAULT 0,
    BonusThanPhap      INT NOT NULL DEFAULT 0,
    BonusNoiLuc        INT NOT NULL DEFAULT 0,
    BonusTheLuc        INT NOT NULL DEFAULT 0,
    AppearanceHidden0  BOOLEAN NOT NULL DEFAULT FALSE,
    AppearanceHidden1  BOOLEAN NOT NULL DEFAULT FALSE,
    SpecialActorForm   INT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_players_username ON Players(Username);

CREATE TABLE IF NOT EXISTS PlayerEquipment (
    PlayerId BIGINT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    EquipKey      TEXT NOT NULL,
    EquipmentCatalogId BIGINT NOT NULL REFERENCES EquipmentCatalog(Id) ON DELETE RESTRICT,
    Slot       INT NOT NULL,
    ResourceId INT NOT NULL,
    Level      INT NOT NULL DEFAULT 0,          -- Enhancement level (maps to ll.j, tag 27)
    Durability    INT NOT NULL DEFAULT 30,       -- Current durability (maps to ll.p, tag 139)
    MaxDurability INT NOT NULL DEFAULT 30,       -- Max durability (maps to ll.q, tag 144)
    IsEquipped BOOLEAN NOT NULL DEFAULT FALSE,
    RawJson    JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, EquipKey)
);

CREATE INDEX IF NOT EXISTS idx_player_equipment_player_id ON PlayerEquipment(PlayerId);
CREATE INDEX IF NOT EXISTS idx_player_equipment_catalog_id ON PlayerEquipment(EquipmentCatalogId);

CREATE TABLE IF NOT EXISTS PlayerInventory (
    PlayerId BIGINT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    ItemId INT NOT NULL,
    Quantity INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, ItemId)
);

CREATE INDEX IF NOT EXISTS idx_player_inventory_player_id ON PlayerInventory(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerSkills (
    PlayerId BIGINT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    SkillId INT NOT NULL,
    Level INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, SkillId)
);

CREATE INDEX IF NOT EXISTS idx_player_skills_player_id ON PlayerSkills(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerMapOverlays (
    PlayerId BIGINT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    IconId INT NOT NULL,
    EndsAt TIMESTAMP WITH TIME ZONE NULL,
    DurationMs BIGINT NOT NULL DEFAULT 0,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, IconId)
);

CREATE INDEX IF NOT EXISTS idx_player_map_overlays_player_id ON PlayerMapOverlays(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerWorldState (
    PlayerId BIGINT PRIMARY KEY REFERENCES Players(Id) ON DELETE CASCADE,
    MapId VARCHAR(50) NOT NULL DEFAULT 'M1',
    RoomId INT NOT NULL DEFAULT 1,
    X INT NOT NULL DEFAULT 0,
    Y INT NOT NULL DEFAULT 0,
    Direction INT NOT NULL DEFAULT 0,
    ActionState INT NOT NULL DEFAULT 0,
    ActiveBattleSessionId TEXT NULL,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Backfill RawElementCode when Element is set (same as old migration 07).
UPDATE Players
SET RawElementCode = CASE Element
    WHEN 0 THEN 1
    WHEN 1 THEN 2
    WHEN 2 THEN 4
    ELSE 1
END
WHERE Element IS NOT NULL
  AND RawElementCode = 1;
