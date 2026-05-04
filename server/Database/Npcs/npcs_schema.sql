-- Module: NPCs and Missions -- minimal catalog, map roster, and NPC mission links.
-- Source: NPC_SYSTEM_RECONSTRUCTION.md.
-- Java evidence:
--   jo.a = NPC id/key, jo.b = display name, jo.c = visual type byte,
--   jo.d = display level/state-like int, jo.e = tile x, jo.f = tile y,
--   jo.g = name color mode.
-- Remake policy:
--   DB stores only fields required by current NPC roster and mission assignment.

CREATE TABLE IF NOT EXISTS NpcCatalog (
    Id                  BIGSERIAL PRIMARY KEY,
    NpcKey              TEXT NOT NULL UNIQUE,
    DisplayName         TEXT NOT NULL DEFAULT '',
    SpriteAssetId       INT NOT NULL,
    SpritePath          TEXT NOT NULL,
    VisualTypeByte      SMALLINT NOT NULL DEFAULT 4,
    DisplayLevel        INT NOT NULL DEFAULT 0,
    NameColorMode       SMALLINT NOT NULL DEFAULT 2,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_NpcCatalog_SpriteAssetId
    ON NpcCatalog (SpriteAssetId);

CREATE TABLE IF NOT EXISTS NpcMapRosters (
    Id                  BIGSERIAL PRIMARY KEY,
    RosterKey           TEXT NOT NULL UNIQUE,
    MapId               TEXT NOT NULL,
    RoomId              INT NOT NULL DEFAULT 1,
    NpcCatalogId        BIGINT NOT NULL,
    DisplayNameOverride TEXT NULL,
    TileX               INT NOT NULL DEFAULT 0,
    TileY               INT NOT NULL DEFAULT 0,
    SortOrder           INT NOT NULL DEFAULT 0,
    RosterMode          SMALLINT NOT NULL DEFAULT 3,
    IsActive            BOOLEAN NOT NULL DEFAULT TRUE,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_npc_map_roster_catalog
        FOREIGN KEY (NpcCatalogId)
        REFERENCES NpcCatalog (Id)
        ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_NpcMapRosters_Map
    ON NpcMapRosters (MapId, RoomId);

CREATE INDEX IF NOT EXISTS idx_NpcMapRosters_NpcCatalogId
    ON NpcMapRosters (NpcCatalogId);

CREATE TABLE IF NOT EXISTS MissionCatalog (
    Id                  BIGSERIAL PRIMARY KEY,
    MissionKey          TEXT NOT NULL UNIQUE,
    Title               TEXT NOT NULL DEFAULT '',
    Description         TEXT NOT NULL DEFAULT '',
    RewardText          TEXT NOT NULL DEFAULT '',
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS MissionObjectives (
    Id                  BIGSERIAL PRIMARY KEY,
    MissionCatalogId    BIGINT NOT NULL,
    ObjectiveType       TEXT NOT NULL,
    TargetKey           TEXT NOT NULL,
    RequiredAmount      INT NOT NULL DEFAULT 1,
    SortOrder           INT NOT NULL DEFAULT 0,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_mission_objective_mission
        FOREIGN KEY (MissionCatalogId)
        REFERENCES MissionCatalog (Id)
        ON DELETE CASCADE,
    CONSTRAINT uq_mission_objective UNIQUE (MissionCatalogId, ObjectiveType, TargetKey, SortOrder)
);

CREATE INDEX IF NOT EXISTS idx_MissionObjectives_MissionCatalogId
    ON MissionObjectives (MissionCatalogId);

CREATE TABLE IF NOT EXISTS MissionRewards (
    Id                  BIGSERIAL PRIMARY KEY,
    MissionCatalogId    BIGINT NOT NULL,
    RewardType          TEXT NOT NULL,
    RewardKey           TEXT NULL,
    Amount              INT NOT NULL DEFAULT 1,
    SortOrder           INT NOT NULL DEFAULT 0,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_mission_reward_mission
        FOREIGN KEY (MissionCatalogId)
        REFERENCES MissionCatalog (Id)
        ON DELETE CASCADE,
    CONSTRAINT ck_mission_reward_type
        CHECK (RewardType IN ('Exp', 'Item', 'Equipment')),
    CONSTRAINT uq_mission_reward UNIQUE (MissionCatalogId, RewardType, COALESCE(RewardKey, ''), SortOrder)
);

CREATE INDEX IF NOT EXISTS idx_MissionRewards_MissionCatalogId
    ON MissionRewards (MissionCatalogId);

CREATE TABLE IF NOT EXISTS NpcMissionLinks (
    Id                  BIGSERIAL PRIMARY KEY,
    NpcCatalogId        BIGINT NOT NULL,
    MissionCatalogId    BIGINT NOT NULL,
    SortOrder           INT NOT NULL DEFAULT 0,
    IsActive            BOOLEAN NOT NULL DEFAULT TRUE,
    CreatedAt           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_npc_mission_link_npc
        FOREIGN KEY (NpcCatalogId)
        REFERENCES NpcCatalog (Id)
        ON DELETE CASCADE,
    CONSTRAINT fk_npc_mission_link_mission
        FOREIGN KEY (MissionCatalogId)
        REFERENCES MissionCatalog (Id)
        ON DELETE CASCADE,
    CONSTRAINT uq_npc_mission_link UNIQUE (NpcCatalogId, MissionCatalogId)
);

CREATE INDEX IF NOT EXISTS idx_NpcMissionLinks_NpcCatalogId
    ON NpcMissionLinks (NpcCatalogId);

CREATE INDEX IF NOT EXISTS idx_NpcMissionLinks_MissionCatalogId
    ON NpcMissionLinks (MissionCatalogId);
