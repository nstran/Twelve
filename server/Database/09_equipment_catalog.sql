-- Migration 09: Equipment catalog.
-- Ref: EQUIPMENT_SYSTEM_RECONSTRUCTION.md and ll.java.
-- Equipment definitions live here; PlayerEquipment stores owned instances only.

CREATE TABLE IF NOT EXISTS EquipmentCatalog (
    TemplateKey TEXT PRIMARY KEY,
    DisplayName TEXT NOT NULL,
    Summary TEXT NOT NULL DEFAULT '',
    Slot INT NOT NULL,                 -- ll.e, tag 84
    ResourceId INT NOT NULL,           -- ll.n, tag 4
    Level INT NOT NULL DEFAULT 0,      -- enhancement seed/default, tag 27
    RequiredLevel INT NOT NULL DEFAULT 1, -- tag 135
    IconKind TEXT NOT NULL DEFAULT 'equipment',
    Rank INT NOT NULL DEFAULT 0,       -- ll.m, tag 138
    ElementIcon INT NOT NULL DEFAULT 7, -- ll.f, tag 15
    Gender INT NOT NULL DEFAULT 2,     -- ll.h, tag 16; 2 = both
    Durability INT NOT NULL DEFAULT 100, -- ll.p, tag 139 default for new instances
    MaxDurability INT NOT NULL DEFAULT 100, -- ll.q, tag 144
    Tradeable BOOLEAN NOT NULL DEFAULT TRUE, -- ll.t, tag 85
    RepairCost BIGINT NOT NULL DEFAULT -1, -- ll.k, tag 190
    ModifierJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    IsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
    UpdatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_equipment_catalog_resource_id ON EquipmentCatalog(ResourceId);
CREATE INDEX IF NOT EXISTS idx_equipment_catalog_slot ON EquipmentCatalog(Slot);

ALTER TABLE PlayerEquipment
    ADD COLUMN IF NOT EXISTS TemplateKey TEXT;

UPDATE PlayerEquipment
SET TemplateKey = RawJson ->> 'templateKey'
WHERE TemplateKey IS NULL
  AND RawJson ? 'templateKey';

INSERT INTO EquipmentCatalog (
    TemplateKey,
    DisplayName,
    Summary,
    Slot,
    ResourceId,
    Level,
    RequiredLevel,
    IconKind,
    Rank,
    ElementIcon,
    Gender,
    Durability,
    MaxDurability,
    Tradeable,
    RepairCost,
    ModifierJson,
    IsEnabled
)
VALUES
    (
        'starter_fire_blade',
        'Hỏa Kiếm Tập Sự',
        'Vũ khí nhập môn cho hệ Hỏa.',
        1,
        80000,
        1,
        1,
        'weapon',
        0,
        0,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"attack":4,"crit":1}'::jsonb,
        TRUE
    ),
    (
        'starter_zap_blade',
        'Lôi Kiếm Tập Sự',
        'Vũ khí nhập môn cho hệ Lôi.',
        1,
        80200,
        1,
        1,
        'weapon',
        0,
        1,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"attack":3,"dodge":2,"thanPhap":1}'::jsonb,
        TRUE
    ),
    (
        'starter_water_blade',
        'Thủy Kiếm Tập Sự',
        'Vũ khí nhập môn cho hệ Thủy.',
        1,
        80400,
        1,
        1,
        'weapon',
        0,
        2,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"attack":2,"maxHp":12,"noiLuc":1}'::jsonb,
        TRUE
    ),
    (
        'fire_guard_vest',
        'Giáp Hỏa Vệ',
        'Tăng công và thủ khi train map đầu.',
        0,
        70100,
        1,
        1,
        'armor',
        0,
        0,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"attack":3,"defense":2,"maxHp":10}'::jsonb,
        TRUE
    ),
    (
        'water_guard_cloak',
        'Băng Bào Hộ Thể',
        'Áo choàng tăng HP và né tránh.',
        0,
        70300,
        1,
        1,
        'armor',
        0,
        2,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"maxHp":18,"dodge":2,"noiLuc":1}'::jsonb,
        TRUE
    ),
    (
        'zap_hunter_helm',
        'Lôi Quan Săn',
        'Nón săn tăng thân pháp và né tránh.',
        2,
        90400,
        1,
        1,
        'helmet',
        0,
        1,
        2,
        100,
        100,
        TRUE,
        -1,
        '{"thanPhap":2,"dodge":3,"attack":2}'::jsonb,
        TRUE
    )
ON CONFLICT (TemplateKey) DO UPDATE
SET DisplayName = EXCLUDED.DisplayName,
    Summary = EXCLUDED.Summary,
    Slot = EXCLUDED.Slot,
    ResourceId = EXCLUDED.ResourceId,
    Level = EXCLUDED.Level,
    RequiredLevel = EXCLUDED.RequiredLevel,
    IconKind = EXCLUDED.IconKind,
    Rank = EXCLUDED.Rank,
    ElementIcon = EXCLUDED.ElementIcon,
    Gender = EXCLUDED.Gender,
    Durability = EXCLUDED.Durability,
    MaxDurability = EXCLUDED.MaxDurability,
    Tradeable = EXCLUDED.Tradeable,
    RepairCost = EXCLUDED.RepairCost,
    ModifierJson = EXCLUDED.ModifierJson,
    IsEnabled = EXCLUDED.IsEnabled,
    UpdatedAt = CURRENT_TIMESTAMP;
