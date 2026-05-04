-- Module: NPCs and Missions -- seed confirmed numbered 110xxx NPC sprites.
-- Source: NPC_SYSTEM_RECONSTRUCTION.md + user-provided NPC names.
-- Boundary: mission content/map ownership is remake seed data until original Java server/catalog evidence is provided.

INSERT INTO NpcCatalog (
    NpcKey,
    DisplayName,
    SpriteAssetId,
    SpritePath,
    VisualTypeByte,
    DisplayLevel,
    NameColorMode
)
VALUES
    ('npc_110000', 'Cu Tí', 110000, 'assets/npc/110000.png', 4, 0, 2),
    ('npc_110010', 'Bé Lan', 110010, 'assets/npc/110010.png', 4, 0, 2),
    ('npc_110020', 'Trưởng làng Gia Viễn', 110020, 'assets/npc/110020.png', 4, 0, 2),
    ('npc_110030', 'Dì Năm', 110030, 'assets/npc/110030.png', 4, 0, 2),
    ('npc_110040', 'Thúy Lan', 110040, 'assets/npc/110040.png', 4, 0, 2),
    ('npc_110050', 'Tiểu Đồng', 110050, 'assets/npc/110050.png', 4, 0, 2),
    ('npc_110060', 'Triệu Thương Gia', 110060, 'assets/npc/110060.png', 4, 0, 2),
    ('npc_110070', 'Thôn nữ', 110070, 'assets/npc/110070.png', 4, 0, 2),
    ('npc_110080', 'Hùng Hưng', 110080, 'assets/npc/110080.png', 4, 0, 2),
    ('npc_110090', 'Tướng Quân Hoa Lư', 110090, 'assets/npc/110090.png', 4, 0, 2),
    ('npc_110100', 'Phạm Hạc', 110100, 'assets/npc/110100.png', 4, 0, 2),
    ('npc_110110', 'Lính Hoa Lư', 110110, 'assets/npc/110110.png', 4, 0, 2),
    ('npc_110120', 'Pháp sư Huyền Quang', 110120, 'assets/npc/110120.png', 4, 0, 2),
    ('npc_110130', 'Chú Tiểu', 110130, 'assets/npc/110130.png', 4, 0, 2),
    ('npc_110140', 'Sư Vạn Hạnh', 110140, 'assets/npc/110140.png', 4, 0, 2),
    ('npc_110150', 'Đinh trang chủ', 110150, 'assets/npc/110150.png', 4, 0, 2),
    ('npc_110160', 'Tướng Quân Phù Liệt', 110160, 'assets/npc/110160.png', 4, 0, 2)
ON CONFLICT (NpcKey) DO UPDATE SET
    DisplayName = EXCLUDED.DisplayName,
    SpriteAssetId = EXCLUDED.SpriteAssetId,
    SpritePath = EXCLUDED.SpritePath,
    VisualTypeByte = EXCLUDED.VisualTypeByte,
    DisplayLevel = EXCLUDED.DisplayLevel,
    NameColorMode = EXCLUDED.NameColorMode;

INSERT INTO NpcMapRosters (
    RosterKey,
    MapId,
    RoomId,
    NpcCatalogId,
    DisplayNameOverride,
    TileX,
    TileY,
    SortOrder,
    RosterMode,
    IsActive
)
SELECT
    'all_maps_guard_' || LOWER(REPLACE(m.RuntimeMapId, ' ', '_')),
    m.RuntimeMapId,
    1,
    n.Id,
    'Lính ' || m.DisplayName,
    6,
    23,
    0,
    3,
    TRUE
FROM WorldMapCatalog m
JOIN NpcCatalog n ON n.NpcKey = 'npc_110110'
WHERE m.IsEnabled = TRUE
ON CONFLICT (RosterKey) DO UPDATE SET
    MapId = EXCLUDED.MapId,
    RoomId = EXCLUDED.RoomId,
    NpcCatalogId = EXCLUDED.NpcCatalogId,
    DisplayNameOverride = EXCLUDED.DisplayNameOverride,
    TileX = EXCLUDED.TileX,
    TileY = EXCLUDED.TileY,
    SortOrder = EXCLUDED.SortOrder,
    RosterMode = EXCLUDED.RosterMode,
    IsActive = EXCLUDED.IsActive;

INSERT INTO NpcMapRosters (
    RosterKey,
    MapId,
    RoomId,
    NpcCatalogId,
    DisplayNameOverride,
    TileX,
    TileY,
    SortOrder,
    RosterMode,
    IsActive
)
SELECT
    'hoa_lu_room_1_' || n.NpcKey,
    'Hoa Lu',
    1,
    n.Id,
    NULL,
    8 + row_number() OVER (ORDER BY n.SpriteAssetId),
    23,
    row_number() OVER (ORDER BY n.SpriteAssetId),
    3,
    TRUE
FROM NpcCatalog n
WHERE n.NpcKey <> 'npc_110110'
ON CONFLICT (RosterKey) DO UPDATE SET
    MapId = EXCLUDED.MapId,
    RoomId = EXCLUDED.RoomId,
    NpcCatalogId = EXCLUDED.NpcCatalogId,
    DisplayNameOverride = EXCLUDED.DisplayNameOverride,
    TileX = EXCLUDED.TileX,
    TileY = EXCLUDED.TileY,
    SortOrder = EXCLUDED.SortOrder,
    RosterMode = EXCLUDED.RosterMode,
    IsActive = EXCLUDED.IsActive;

UPDATE NpcMapRosters r
SET
    TileX = 10,
    TileY = 23,
    SortOrder = 1
FROM NpcCatalog n
WHERE r.NpcCatalogId = n.Id
  AND r.RosterKey = 'hoa_lu_room_1_npc_110020'
  AND n.NpcKey = 'npc_110020';

-- Remake policy approved by user: Hoa Lu starter missions until Java server mission catalog is found.
INSERT INTO MissionCatalog (MissionKey, Title, Description, RewardText)
VALUES
    ('hoa_lu_ga_dien_quay_pha_001', 'Gà Điên Quấy Phá', 'Gần làng xuất hiện nhiều Gà Điên làm dân làng hoảng sợ. Hãy đánh bại chúng để giúp Trưởng làng ổn định lại Hoa Lư.', 'EXP x80; Item 5001 x2'),
    ('hoa_lu_bao_tin_cho_linh_002', 'Báo Tin Cho Lính Hoa Lư', 'Sau khi xử lý Gà Điên, hãy báo lại cho Lính Hoa Lư để khu vực quanh làng được canh phòng cẩn thận hơn.', 'EXP x50'),
    ('hoa_lu_tuan_tra_cung_linh_003', 'Tuần Tra Cùng Lính Hoa Lư', 'Lính Hoa Lư nhờ bạn tiếp tục tuần tra và đánh bại thêm Gà Điên quanh khu vực làng.', 'EXP x120; Item 5001 x3; Item 30094 x1')
ON CONFLICT (MissionKey) DO UPDATE SET
    Title = EXCLUDED.Title,
    Description = EXCLUDED.Description,
    RewardText = EXCLUDED.RewardText;

INSERT INTO MissionObjectives (MissionCatalogId, ObjectiveType, TargetKey, RequiredAmount, SortOrder)
SELECT m.Id, v.ObjectiveType, v.TargetKey, v.RequiredAmount, v.SortOrder
FROM MissionCatalog m
JOIN (VALUES
    ('hoa_lu_ga_dien_quay_pha_001', 'KillMonster', 'MONSTER_1000_SLOT_0', 3, 0),
    ('hoa_lu_bao_tin_cho_linh_002', 'TalkNpc', 'npc_110110', 1, 0),
    ('hoa_lu_tuan_tra_cung_linh_003', 'KillMonster', 'MONSTER_1000_SLOT_0', 5, 0)
) AS v(MissionKey, ObjectiveType, TargetKey, RequiredAmount, SortOrder)
    ON v.MissionKey = m.MissionKey
ON CONFLICT (MissionCatalogId, ObjectiveType, TargetKey, SortOrder) DO UPDATE SET
    RequiredAmount = EXCLUDED.RequiredAmount;

INSERT INTO MissionRewards (MissionCatalogId, RewardType, RewardKey, Amount, SortOrder)
SELECT m.Id, v.RewardType, v.RewardKey, v.Amount, v.SortOrder
FROM MissionCatalog m
JOIN (VALUES
    ('hoa_lu_ga_dien_quay_pha_001', 'Exp', NULL, 80, 0),
    ('hoa_lu_ga_dien_quay_pha_001', 'Item', '5001', 2, 1),
    ('hoa_lu_bao_tin_cho_linh_002', 'Exp', NULL, 50, 0),
    ('hoa_lu_tuan_tra_cung_linh_003', 'Exp', NULL, 120, 0),
    ('hoa_lu_tuan_tra_cung_linh_003', 'Item', '5001', 3, 1),
    ('hoa_lu_tuan_tra_cung_linh_003', 'Item', '30094', 1, 2)
) AS v(MissionKey, RewardType, RewardKey, Amount, SortOrder)
    ON v.MissionKey = m.MissionKey
ON CONFLICT (MissionCatalogId, RewardType, COALESCE(RewardKey, ''), SortOrder) DO UPDATE SET
    Amount = EXCLUDED.Amount;

INSERT INTO NpcMissionLinks (NpcCatalogId, MissionCatalogId, SortOrder, IsActive)
SELECT n.Id, m.Id, v.SortOrder, TRUE
FROM NpcCatalog n
JOIN (VALUES
    ('npc_110020', 'hoa_lu_ga_dien_quay_pha_001', 0),
    ('npc_110020', 'hoa_lu_bao_tin_cho_linh_002', 1),
    ('npc_110110', 'hoa_lu_tuan_tra_cung_linh_003', 0)
) AS v(NpcKey, MissionKey, SortOrder)
    ON v.NpcKey = n.NpcKey
JOIN MissionCatalog m ON m.MissionKey = v.MissionKey
ON CONFLICT (NpcCatalogId, MissionCatalogId) DO UPDATE SET
    SortOrder = EXCLUDED.SortOrder,
    IsActive = EXCLUDED.IsActive;
