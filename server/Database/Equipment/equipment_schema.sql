-- Module: Equipment — item definitions (merged 09 CREATE + 08 columns on PlayerEquipment in Players module).
-- Surrogate Id (BIGINT) is the primary key; TemplateKey remains the stable business identifier (unique).

CREATE TABLE IF NOT EXISTS EquipmentCatalog (
    Id BIGSERIAL PRIMARY KEY,
    TemplateKey TEXT NOT NULL UNIQUE,
    DisplayName TEXT NOT NULL,
    Summary TEXT NOT NULL DEFAULT '',
    Slot INT NOT NULL,
    ResourceId INT NOT NULL,
    Level INT NOT NULL DEFAULT 0,
    RequiredLevel INT NOT NULL DEFAULT 1,
    IconKind TEXT NOT NULL DEFAULT 'equipment',
    Rank INT NOT NULL DEFAULT 0,
    ElementIcon INT NOT NULL DEFAULT 7,
    Gender INT NOT NULL DEFAULT 2,
    Durability INT NOT NULL DEFAULT 30,
    MaxDurability INT NOT NULL DEFAULT 30,
    Tradeable BOOLEAN NOT NULL DEFAULT TRUE,
    RepairCost BIGINT NOT NULL DEFAULT -1,
    ModifierJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    IsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_equipment_catalog_resource_id ON EquipmentCatalog(ResourceId);
CREATE INDEX IF NOT EXISTS idx_equipment_catalog_slot ON EquipmentCatalog(Slot);

-- Java evidence / Remake policy boundary:
-- - ItemId is raw gameplay id used by server logic.
-- - ResourceId/IconId are nullable because Java asset/resource ids for non-equipment items are still pending.
-- - Kind/EvidenceStatus use raw enum values from Twelve.Core.Players.PlayerItemKind/PlayerItemEvidenceStatus.
CREATE TABLE IF NOT EXISTS ItemCatalog (
    ItemId INT PRIMARY KEY,
    DisplayName TEXT NOT NULL,
    Description TEXT NOT NULL DEFAULT '',
    StackCap INT NOT NULL DEFAULT 99,
    IsUsable BOOLEAN NOT NULL DEFAULT FALSE,
    HealAmount INT NOT NULL DEFAULT 0,
    ManaAmount INT NOT NULL DEFAULT 0,
    RestoreKind TEXT NOT NULL DEFAULT 'none',
    IconKind TEXT NOT NULL DEFAULT 'item',
    Kind INT NOT NULL DEFAULT 1,
    EvidenceStatus INT NOT NULL DEFAULT 0,
    ResourceId INT NULL,
    IconId INT NULL,
    IsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_item_catalog_kind ON ItemCatalog(Kind);
CREATE INDEX IF NOT EXISTS idx_item_catalog_resource_id ON ItemCatalog(ResourceId);
