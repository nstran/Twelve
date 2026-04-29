-- Module: Equipment — catalog + test player equipment seed (merged 09 INSERT + 10).
-- Run after `Equipment/equipment_schema.sql` and `Players/players_schema.sql` (see Database/README.md).

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

DELETE FROM PlayerEquipment
WHERE EquipKey LIKE 'starter_water_talisman-test-%'
   OR EquipKey LIKE 'zap_hunter_boots-test-%'
   OR EquipmentCatalogId IN (
        SELECT Id FROM EquipmentCatalog WHERE TemplateKey IN ('starter_water_talisman', 'zap_hunter_boots')
   );

WITH starter_templates AS (
    SELECT
        p.Id AS PlayerId,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 'starter_zap_blade'
            WHEN 2 THEN 'starter_water_blade'
            ELSE 'starter_fire_blade'
        END AS TemplateKey,
        TRUE AS PreferEquipped
    FROM Players p
),
bag_templates AS (
    SELECT
        p.Id AS PlayerId,
        c.TemplateKey,
        FALSE AS PreferEquipped
    FROM Players p
    CROSS JOIN EquipmentCatalog c
    WHERE c.TemplateKey IN ('fire_guard_vest', 'water_guard_cloak', 'zap_hunter_helm')
),
all_templates AS (
    SELECT * FROM starter_templates
    UNION ALL
    SELECT * FROM bag_templates
),
resolved_templates AS (
    SELECT
        t.PlayerId,
        t.PreferEquipped,
        c.Id AS EquipmentCatalogId,
        c.TemplateKey,
        c.DisplayName,
        c.Summary,
        c.Slot,
        c.ResourceId,
        c.Level,
        c.RequiredLevel,
        c.IconKind,
        c.Rank,
        c.ElementIcon,
        c.Gender,
        c.Durability,
        c.MaxDurability,
        c.Tradeable,
        c.RepairCost,
        c.ModifierJson
    FROM all_templates t
    JOIN EquipmentCatalog c ON c.TemplateKey = t.TemplateKey
    WHERE c.IsEnabled = TRUE
)
INSERT INTO PlayerEquipment (
    PlayerId,
    EquipKey,
    EquipmentCatalogId,
    Slot,
    ResourceId,
    Level,
    IsEquipped,
    RawJson
)
SELECT
    t.PlayerId,
    CONCAT(t.TemplateKey, '-test-', t.PlayerId) AS EquipKey,
    t.EquipmentCatalogId,
    t.Slot,
    t.ResourceId,
    t.Level,
    CASE
        WHEN t.PreferEquipped
             AND NOT EXISTS (
                 SELECT 1
                 FROM PlayerEquipment existing
                 WHERE existing.PlayerId = t.PlayerId
                   AND existing.Slot = t.Slot
                   AND existing.IsEquipped = TRUE
             )
        THEN TRUE
        ELSE FALSE
    END AS IsEquipped,
    jsonb_build_object(
        'templateKey', t.TemplateKey,
        'displayName', t.DisplayName,
        'summary', t.Summary,
        'slot', t.Slot,
        'resourceId', t.ResourceId,
        'level', t.Level,
        'requiredLevel', t.RequiredLevel,
        'iconKind', t.IconKind,
        'rank', t.Rank,
        'elementIcon', t.ElementIcon,
        'gender', t.Gender,
        'durability', t.Durability,
        'maxDurability', t.MaxDurability,
        'tradeable', t.Tradeable,
        'repairCost', t.RepairCost,
        'legacyTags', jsonb_build_object(
            'key', 'string',
            'slot', 84,
            'resourceId', 4,
            'enhancementLevel', 27,
            'requiredLevel', 135,
            'rank', 138,
            'durability', 139,
            'maxDurability', 144,
            'tradeable', 85,
            'repairCost', 190
        ),
        'modifier', t.ModifierJson
    ) AS RawJson
FROM resolved_templates t
ON CONFLICT (PlayerId, EquipKey) DO UPDATE
SET EquipmentCatalogId = EXCLUDED.EquipmentCatalogId,
    Slot = EXCLUDED.Slot,
    ResourceId = EXCLUDED.ResourceId,
    Level = EXCLUDED.Level,
    RawJson = EXCLUDED.RawJson,
    UpdatedAt = CURRENT_TIMESTAMP;
