-- Migration 10: Seed test equipment for existing players.
-- Purpose: players created before the character aggregate seed may have an empty
-- PlayerEquipment table, making the equipment/inventory dialogs hard to test.

DELETE FROM PlayerEquipment
WHERE EquipKey LIKE 'starter_water_talisman-test-%'
   OR EquipKey LIKE 'zap_hunter_boots-test-%'
   OR TemplateKey IN ('starter_water_talisman', 'zap_hunter_boots');

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
    TemplateKey,
    Slot,
    ResourceId,
    Level,
    IsEquipped,
    RawJson
)
SELECT
    t.PlayerId,
    CONCAT(t.TemplateKey, '-test-', t.PlayerId) AS EquipKey,
    t.TemplateKey,
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
SET TemplateKey = EXCLUDED.TemplateKey,
    Slot = EXCLUDED.Slot,
    ResourceId = EXCLUDED.ResourceId,
    Level = EXCLUDED.Level,
    RawJson = EXCLUDED.RawJson,
    UpdatedAt = CURRENT_TIMESTAMP;
