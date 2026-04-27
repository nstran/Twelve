-- Migration 07: Player/character aggregate storage.
-- Ref: docs/player-character-reconstruction/01-implementation-plan-csharp.md
-- Purpose: keep Java lh-compatible raw fields without flattening every ll/lm/lv/lt
-- detail into Players.

ALTER TABLE Players
    ADD COLUMN IF NOT EXISTS RawElementCode INT NOT NULL DEFAULT 1,      -- Java wire: 1=Hoa, 2=Loi, 4=Thuy
    ADD COLUMN IF NOT EXISTS SkillPoints INT NOT NULL DEFAULT 0,         -- lh.L
    ADD COLUMN IF NOT EXISTS Honor INT NOT NULL DEFAULT 0,               -- lh.ab / prestige-honor axis
    ADD COLUMN IF NOT EXISTS TitleMain TEXT,
    ADD COLUMN IF NOT EXISTS TitleSub TEXT,
    ADD COLUMN IF NOT EXISTS TitleRank TEXT,
    ADD COLUMN IF NOT EXISTS ExpFloor BIGINT NOT NULL DEFAULT 0,         -- lh.M
    ADD COLUMN IF NOT EXISTS ExpCeiling BIGINT NOT NULL DEFAULT 100,     -- lh.N
    ADD COLUMN IF NOT EXISTS QuanProgress BIGINT NOT NULL DEFAULT 0,     -- lh.H
    ADD COLUMN IF NOT EXISTS QuanProgressCap BIGINT NOT NULL DEFAULT 10000, -- lh.I
    ADD COLUMN IF NOT EXISTS BonusCuongLuc INT NOT NULL DEFAULT 0,       -- lh.l
    ADD COLUMN IF NOT EXISTS BonusThanPhap INT NOT NULL DEFAULT 0,       -- lh.m
    ADD COLUMN IF NOT EXISTS BonusNoiLuc INT NOT NULL DEFAULT 0,         -- lh.n
    ADD COLUMN IF NOT EXISTS BonusTheLuc INT NOT NULL DEFAULT 0,         -- lh.o
    ADD COLUMN IF NOT EXISTS DerivedMinDamage INT NOT NULL DEFAULT 0,    -- lh.x
    ADD COLUMN IF NOT EXISTS DerivedMaxDamage INT NOT NULL DEFAULT 0,    -- lh.y
    ADD COLUMN IF NOT EXISTS DerivedDefense INT NOT NULL DEFAULT 0,      -- lh.z
    ADD COLUMN IF NOT EXISTS DerivedDodge INT NOT NULL DEFAULT 0,        -- lh.A
    ADD COLUMN IF NOT EXISTS DerivedHit INT NOT NULL DEFAULT 0,          -- lh.B
    ADD COLUMN IF NOT EXISTS DerivedCrit INT NOT NULL DEFAULT 0,         -- lh.C
    ADD COLUMN IF NOT EXISTS AppearanceHidden0 BOOLEAN NOT NULL DEFAULT FALSE, -- lh.Z
    ADD COLUMN IF NOT EXISTS AppearanceHidden1 BOOLEAN NOT NULL DEFAULT FALSE, -- lh.aa
    ADD COLUMN IF NOT EXISTS SpecialActorForm INT NOT NULL DEFAULT 0;    -- lh.Y

UPDATE Players
SET RawElementCode = CASE Element
    WHEN 0 THEN 1
    WHEN 1 THEN 2
    WHEN 2 THEN 4
    ELSE 1
END
WHERE Element IS NOT NULL
  AND RawElementCode = 1;

CREATE TABLE IF NOT EXISTS PlayerEquipment (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    EquipKey TEXT NOT NULL,
    Slot INT NOT NULL,
    ResourceId INT NOT NULL,
    Level INT NOT NULL DEFAULT 0,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, EquipKey)
);

CREATE INDEX IF NOT EXISTS idx_player_equipment_player_id ON PlayerEquipment(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerInventory (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    ItemId INT NOT NULL,
    Quantity INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, ItemId)
);

CREATE INDEX IF NOT EXISTS idx_player_inventory_player_id ON PlayerInventory(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerSkills (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    SkillId INT NOT NULL,
    Level INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, SkillId)
);

CREATE INDEX IF NOT EXISTS idx_player_skills_player_id ON PlayerSkills(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerMapOverlays (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    IconId INT NOT NULL,
    EndsAt TIMESTAMP WITH TIME ZONE NULL,
    DurationMs BIGINT NOT NULL DEFAULT 0,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PlayerId, IconId)
);

CREATE INDEX IF NOT EXISTS idx_player_map_overlays_player_id ON PlayerMapOverlays(PlayerId);

CREATE TABLE IF NOT EXISTS PlayerWorldState (
    PlayerId INT PRIMARY KEY REFERENCES Players(Id) ON DELETE CASCADE,
    MapId VARCHAR(50) NOT NULL DEFAULT 'M1',
    RoomId INT NOT NULL DEFAULT 1,
    X INT NOT NULL DEFAULT 0,
    Y INT NOT NULL DEFAULT 0,
    Direction INT NOT NULL DEFAULT 0,
    ActionState INT NOT NULL DEFAULT 0,
    ActiveBattleSessionId TEXT NULL,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
