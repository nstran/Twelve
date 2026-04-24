-- Migration 09: Seed test equipment for existing players.
-- Purpose: players created before the character aggregate seed may have an empty
-- PlayerEquipment table, making the equipment/inventory dialogs hard to test.

WITH starter_templates AS (
    SELECT
        p.Id AS PlayerId,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 'starter_zap_blade'
            WHEN 2 THEN 'starter_water_talisman'
            ELSE 'starter_fire_blade'
        END AS TemplateKey,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 'Lôi Kiếm Tập Sự'
            WHEN 2 THEN 'Thủy Phù Tập Sự'
            ELSE 'Hỏa Kiếm Tập Sự'
        END AS DisplayName,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 'Vũ khí nhập môn cho hệ Lôi.'
            WHEN 2 THEN 'Phù nhập môn cho hệ Thủy.'
            ELSE 'Vũ khí nhập môn cho hệ Hỏa.'
        END AS Summary,
        CASE COALESCE(p.Element, 0)
            WHEN 2 THEN 5
            ELSE 4
        END AS Slot,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 80200
            WHEN 2 THEN 120100
            ELSE 80000
        END AS ResourceId,
        1 AS Level,
        1 AS RequiredLevel,
        CASE COALESCE(p.Element, 0)
            WHEN 2 THEN 'talisman'
            ELSE 'weapon'
        END AS IconKind,
        0 AS BonusCuongLuc,
        CASE COALESCE(p.Element, 0) WHEN 1 THEN 1 ELSE 0 END AS BonusThanPhap,
        CASE COALESCE(p.Element, 0) WHEN 2 THEN 1 ELSE 0 END AS BonusNoiLuc,
        0 AS BonusTheLuc,
        CASE COALESCE(p.Element, 0)
            WHEN 1 THEN 3
            WHEN 2 THEN 2
            ELSE 4
        END AS BonusAttack,
        0 AS BonusDefense,
        CASE COALESCE(p.Element, 0) WHEN 1 THEN 2 ELSE 0 END AS BonusDodge,
        CASE COALESCE(p.Element, 0) WHEN 0 THEN 1 ELSE 0 END AS BonusCrit,
        CASE COALESCE(p.Element, 0) WHEN 2 THEN 12 ELSE 0 END AS BonusMaxHp
    FROM Players p
),
bag_templates AS (
    SELECT
        p.Id AS PlayerId,
        v.TemplateKey,
        v.DisplayName,
        v.Summary,
        v.Slot,
        v.ResourceId,
        v.Level,
        v.RequiredLevel,
        v.IconKind,
        v.BonusCuongLuc,
        v.BonusThanPhap,
        v.BonusNoiLuc,
        v.BonusTheLuc,
        v.BonusAttack,
        v.BonusDefense,
        v.BonusDodge,
        v.BonusCrit,
        v.BonusMaxHp
    FROM Players p
    CROSS JOIN (
        VALUES
            ('fire_guard_vest', 'Giáp Hỏa Vệ', 'Tăng công và thủ khi train map đầu.', 2, 70100, 1, 6, 'armor', 0, 0, 0, 0, 3, 2, 0, 0, 10),
            ('water_guard_cloak', 'Băng Bào Hộ Thể', 'Áo choàng tăng HP và né tránh.', 2, 70300, 1, 6, 'armor', 0, 0, 1, 0, 0, 0, 2, 0, 18),
            ('zap_hunter_boots', 'Ngoa Lôi Săn', 'Giày tăng thân pháp và chính diện.', 1, 120200, 1, 8, 'boots', 0, 2, 0, 0, 2, 0, 3, 0, 0)
    ) AS v(
        TemplateKey,
        DisplayName,
        Summary,
        Slot,
        ResourceId,
        Level,
        RequiredLevel,
        IconKind,
        BonusCuongLuc,
        BonusThanPhap,
        BonusNoiLuc,
        BonusTheLuc,
        BonusAttack,
        BonusDefense,
        BonusDodge,
        BonusCrit,
        BonusMaxHp
    )
),
all_templates AS (
    SELECT *, TRUE AS PreferEquipped FROM starter_templates
    UNION ALL
    SELECT *, FALSE AS PreferEquipped FROM bag_templates
)
INSERT INTO PlayerEquipment (
    PlayerId,
    EquipKey,
    Slot,
    ResourceId,
    Level,
    IsEquipped,
    RawJson
)
SELECT
    t.PlayerId,
    CONCAT(t.TemplateKey, '-test-', t.PlayerId) AS EquipKey,
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
        'modifier', jsonb_build_object(
            'cuongLuc', t.BonusCuongLuc,
            'thanPhap', t.BonusThanPhap,
            'noiLuc', t.BonusNoiLuc,
            'theLuc', t.BonusTheLuc,
            'attack', t.BonusAttack,
            'attackPercent', 0,
            'crit', t.BonusCrit,
            'defense', t.BonusDefense,
            'dodge', t.BonusDodge,
            'maxHp', t.BonusMaxHp
        )
    ) AS RawJson
FROM all_templates t
ON CONFLICT (PlayerId, EquipKey) DO UPDATE
SET Slot = EXCLUDED.Slot,
    ResourceId = EXCLUDED.ResourceId,
    Level = EXCLUDED.Level,
    RawJson = EXCLUDED.RawJson,
    UpdatedAt = CURRENT_TIMESTAMP;
