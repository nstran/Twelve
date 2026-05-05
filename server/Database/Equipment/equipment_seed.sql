-- Module: Equipment — seed data (embedded as DB.04_equipment_seed.sql).
-- Java evidence / Remake policy boundary:
-- - Id is the raw gameplay item id used by server logic.
-- - ResourceId/IconId are NULL when Java asset/resource evidence is pending.
-- - Kind raw values follow PlayerItemKind: Consumable=0, Material=1, Egg=2, RepairMaterial=3.
-- - EvidenceStatus raw values follow PlayerItemEvidenceStatus: PendingUnverified=0, RemakePolicy=1, JavaEvidence=2.

INSERT INTO ItemCatalog (
    Id,
    DisplayName,
    Description,
    StackCap,
    IsUsable,
    HealAmount,
    ManaAmount,
    RestoreKind,
    IconKind,
    Kind,
    EvidenceStatus,
    ResourceId,
    IconId
)
VALUES
    (5001, 'HP', 'Bình máu dùng để hồi phục HP cho nhân vật.', 20, TRUE, 35, 0, 'hp', 'hp', 0, 0, NULL, NULL),
    (5002, 'MP', 'Bình năng lượng dùng để hồi phục MP cho nhân vật.', 20, TRUE, 0, 35, 'mp', 'mp', 0, 0, NULL, NULL),
    (5003, 'Huyết thạch', 'Nguyên liệu chính nâng cấp trang bị.', 99, FALSE, 0, 0, 'none', 'huyet_thach', 1, 1, NULL, NULL),
    (5004, 'Kim thạch', 'Nguyên liệu chính nâng cấp trang bị.', 99, FALSE, 0, 0, 'none', 'kim_thach', 1, 1, NULL, NULL),
    (5008, 'Bùa may mắn I', 'Bùa hỗ trợ nâng cấp, cộng 5% tỉ lệ thành công.', 99, FALSE, 0, 0, 'none', 'charm_1', 1, 1, NULL, NULL),
    (5009, 'Bùa may mắn II', 'Bùa hỗ trợ nâng cấp, cộng 10% tỉ lệ thành công.', 99, FALSE, 0, 0, 'none', 'charm_2', 1, 1, NULL, NULL),
    (5010, 'Bùa may mắn III', 'Bùa hỗ trợ nâng cấp, cộng 15% tỉ lệ thành công.', 99, FALSE, 0, 0, 'none', 'charm_3', 1, 1, NULL, NULL),
    (5005, 'Vuốt rồng', 'Nguyên liệu quý lấy từ rồng, dùng chế tạo cánh quỷ.', 99, FALSE, 0, 0, 'none', 'dragon_claw', 1, 0, NULL, NULL),
    (5006, 'Lông vũ', 'Nguyên liệu lông vũ dùng chế cánh tiên.', 99, FALSE, 0, 0, 'none', 'feather', 1, 0, NULL, NULL),
    (5007, 'X2 EXP', 'Vật phẩm hỗ trợ tăng tốc độ nhận kinh nghiệm trong thời gian hiệu lực.', 20, FALSE, 0, 0, 'item', 'x2_exp', 0, 0, NULL, NULL),
    (30094, 'Trứng gà', 'Trứng Đập ra có cơ hội nhận được vật phẩm quý hiếm cấp thấp.', 20, FALSE, 0, 0, 'item', 'chicken_egg', 2, 0, NULL, NULL),
    (30095, 'Trứng đà điểu', 'Trứng Đập ra có cơ hội nhận được vật phẩm quý hiếm cấp thấp.', 20, FALSE, 0, 0, 'item', 'chicken_egg', 2, 0, NULL, NULL),
    (30096, 'Trứng khủng long', 'Trứng khủng long Đập ra có cơ hội nhận được vật phẩm quý hiếm cấp cao.', 20, FALSE, 0, 0, 'item', 'dinosaur_egg', 2, 0, NULL, NULL),
    (30097, 'Trứng phượng hoàng', 'Trứng phượng hoàng Đập ra có cơ hội nhận được vật phẩm quý hiếm cấp cao.', 20, FALSE, 0, 0, 'item', 'phoenix_egg', 2, 0, NULL, NULL),
    (30098, 'Trứng rồng', 'Trứng rồng Đập ra có cơ hội nhận được vật phẩm quý hiếm cấp cao.', 20, FALSE, 0, 0, 'item', 'dragon_egg', 2, 0, NULL, NULL),
    (30099, 'Búa sửa chữa', 'Dụng cụ sửa trang bị; dùng 1 búa để phục hồi độ bền cho một trang bị bị hư hỏng.', 20, FALSE, 0, 0, 'item', 'repair_hammer', 3, 1, NULL, NULL)
ON CONFLICT (Id) DO UPDATE SET
    DisplayName = EXCLUDED.DisplayName,
    Description = EXCLUDED.Description,
    StackCap = EXCLUDED.StackCap,
    IsUsable = EXCLUDED.IsUsable,
    HealAmount = EXCLUDED.HealAmount,
    ManaAmount = EXCLUDED.ManaAmount,
    RestoreKind = EXCLUDED.RestoreKind,
    IconKind = EXCLUDED.IconKind,
    Kind = EXCLUDED.Kind,
    EvidenceStatus = EXCLUDED.EvidenceStatus,
    ResourceId = EXCLUDED.ResourceId,
    IconId = EXCLUDED.IconId,
    IsEnabled = TRUE,
    UpdatedAt = CURRENT_TIMESTAMP;