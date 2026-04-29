-- Module: World map catalog and per-player unlock state (schema only).
-- Ref: MAP_SYSTEM_RECONSTRUCTION.md. Requires Players table.

CREATE TABLE IF NOT EXISTS WorldMapCatalog (
    Id BIGSERIAL PRIMARY KEY,
    Code TEXT NOT NULL UNIQUE,
    MapIndex INT NOT NULL UNIQUE,
    DisplayName TEXT NOT NULL,
    RuntimeMapId TEXT NOT NULL,
    LabelX INT NOT NULL,
    LabelY INT NOT NULL,
    LockX INT NOT NULL,
    LockY INT NOT NULL,
    HitX INT NOT NULL,
    HitY INT NOT NULL,
    HitWidth INT NOT NULL,
    HitHeight INT NOT NULL,
    IsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_world_map_catalog_code ON WorldMapCatalog(Code);
CREATE INDEX IF NOT EXISTS idx_world_map_catalog_runtime_map_id ON WorldMapCatalog(RuntimeMapId);

CREATE TABLE IF NOT EXISTS PlayerWorldMapUnlocks (
    PlayerId BIGINT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    WorldMapId BIGINT NOT NULL REFERENCES WorldMapCatalog(Id) ON DELETE CASCADE,
    IsUnlocked BOOLEAN NOT NULL DEFAULT TRUE,
    UnlockedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UnlockReason TEXT NOT NULL DEFAULT 'system',
    PRIMARY KEY (PlayerId, WorldMapId)
);

CREATE INDEX IF NOT EXISTS idx_player_world_map_unlocks_player_id ON PlayerWorldMapUnlocks(PlayerId);
CREATE INDEX IF NOT EXISTS idx_player_world_map_unlocks_world_map_id ON PlayerWorldMapUnlocks(WorldMapId);
