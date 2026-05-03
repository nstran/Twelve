-- Module: Monsters — seed data (moved from legacy 15_seed_monster_data.sql).
-- Convention: MONSTER_{speciesCode}_SLOT_{slot}
-- DisplayName: Vietnamese, ưu tiên từ thuần Việt; bám sprite (không gọi “kê” khi không phải gà).
-- FramePaths stores Java numeric resource IDs (e.g. "100001"), not file paths.
-- Client resolves: assets/monster/{code}.png
-- Source: MONSTER_SYSTEM_RECONSTRUCTION.md + client/assets/monster/

-- ═══════════════════════════════════════════════════════════════════════════
-- Monsters — confirmed species families 1000-1007
-- ═══════════════════════════════════════════════════════════════════════════

INSERT INTO Monsters (AssetCatalogId, DisplayName, SpeciesCode, Slot, SharedSheetFamily, FramePaths, IsCandidate)
VALUES
  ('MONSTER_1000_SLOT_0', 'Gà Điên', 1000, 0, 0, '["100001", "100003", "100004", "100005"]'::jsonb, FALSE), -- Hỏa 1 (Hoa Lư)
  ('MONSTER_1000_SLOT_1', 'Heo Mọi', 1000, 1, 0, '["100011", "100013", "100014", "100015"]'::jsonb, FALSE), -- Lôi 7 (Hoa Lư)
  ('MONSTER_1000_SLOT_2', 'Sơn tặc', 1000, 2, 0, '["100021", "100023", "100024", "100025"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_3', 'Tướng cướp', 1000, 3, 0, '["100031", "100033", "100034", "100035"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_4', 'Sói Trắng Vằn Đỏ', 1000, 4, 0, '["100041", "100043", "100044", "100045"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_5', 'Cóc Độc', 1000, 5, 0, '["100051", "100053", "100054", "100055"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_6', 'Cua Càng', 1000, 6, 0, '["100061", "100063", "100064", "100065"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_7', 'Tên Cướp Khăn Vàng', 1000, 7, 0, '["100071", "100073", "100074", "100075"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_8', 'Chim Điên', 1000, 8, 0, '["100081", "100083", "100085", "100086"]'::jsonb, FALSE),
  ('MONSTER_1000_SLOT_9', 'Khỉ Lửa', 1000, 9, 0, '["100091", "100093", "100095", "100096"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_0', 'Cá Sấu', 1001, 0, 0, '["100101", "100103", "100105", "100106"]'::jsonb, FALSE), -- Thủy 31 (Đỗ Động)
  ('MONSTER_1001_SLOT_1', 'Cóc Tía', 1001, 1, 0, '["100111", "100113", "100114", "100115"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_2', 'Chó Điên', 1001, 2, 0, '["100121", "100123", "100124", "100125"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_3', 'Cóc Bùn Vàng', 1001, 3, 0, '["100131", "100133", "100135", "100136"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_4', 'Heo Lửa', 1001, 4, 0, '["100141", "100143", "100144", "100145"]'::jsonb, FALSE), -- Hỏa
  ('MONSTER_1001_SLOT_5', 'Khỉ Đầu Đàn', 1001, 5, 0, '["100151", "100153", "100155", "100156"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_6', 'Heo Đầu Đàn', 1001, 6, 0, '["100161", "100163", "100164", "100165"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_7', 'Voi Mập', 1001, 7, 0, '["100171", "100173", "100175", "100176"]'::jsonb, FALSE), -- Thủy 56 (Đường Lâm)
  ('MONSTER_1001_SLOT_8', 'Hổ Tinh', 1001, 8, 0, '["100181", "100183", "100185", "100186"]'::jsonb, FALSE),
  ('MONSTER_1001_SLOT_9', 'Ếch Độc Đầm', 1001, 9, 0, '["100191", "100193", "100194", "100195"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_0', 'Chim Kê', 1002, 0, 2, '["100201", "100203", "100204", "100205"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_1', 'Gà Rừng Lông Lửa', 1002, 1, 2, '["100211", "100213", "100214", "100215"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_2', 'Ngưu Ma Thống Lĩnh', 1002, 2, 2, '["100221", "100223", "100225", "100226"]'::jsonb, FALSE), -- Hỏa 150 (Luyện Ngục 4)
  ('MONSTER_1002_SLOT_3', 'Gà Đuôi Dài', 1002, 3, 2, '["100231", "100233", "100234", "100235"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_4', 'Hoa Hồng Gai', 1002, 4, 2, '["100241", "100243", "100245", "100246"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_5', 'Ác Ma Địa Ngục', 1002, 5, 2, '["100251", "100253", "100254", "100255"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_6', 'Gà Núi Kiêu', 1002, 6, 2, '["100261", "100263", "100264", "100265"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_7', 'Bạch Cốt Ma Vương', 1002, 7, 2, '["100271", "100273", "100274", "100275"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_8', 'Rắn Hổ Mang', 1002, 8, 2, '["100281", "100283", "100284", "100285"]'::jsonb, FALSE),
  ('MONSTER_1002_SLOT_9', 'Gà Trắng Đuôi Xanh', 1002, 9, 2, '["100291", "100293", "100294", "100295"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_0', 'Chim Điên', 1003, 0, 1, '["100303", "100305", "100306"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_1', 'Quái Điểu', 1003, 1, 1, '["100313", "100315", "100316"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_2', 'Chim Mỏ Cong', 1003, 2, 1, '["100323", "100325", "100326"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_3', 'Chim Lông Hồng', 1003, 3, 1, '["100331", "100333", "100335", "100336"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_4', 'Chim Lông Bạc', 1003, 4, 1, '["100341", "100343", "100345", "100346"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_5', 'Hồ Ly Tinh', 1003, 5, 1, '["100351", "100353", "100354", "100355"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_6', 'Chim Hai Đuôi', 1003, 6, 1, '["100361", "100363", "100364", "100365"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_7', 'Chim Đầu Vàng', 1003, 7, 1, '["100371", "100373", "100374", "100375"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_8', 'Chim Đuôi Dài', 1003, 8, 1, '["100381", "100383", "100384", "100385"]'::jsonb, FALSE),
  ('MONSTER_1003_SLOT_9', 'Chim Tía Sét', 1003, 9, 1, '["100391", "100393", "100394", "100395"]'::jsonb, FALSE),
  ('MONSTER_1004_SLOT_0', 'Tiểu Yêu Dê', 1004, 0, 0, '["100401", "100403", "100404", "100405"]'::jsonb, FALSE), -- Lôi 58 (Đường Lâm)
  ('MONSTER_1004_SLOT_1', 'Dê Mặt Tím', 1004, 1, 0, '["100411", "100413", "100414", "100415"]'::jsonb, FALSE),
  ('MONSTER_1004_SLOT_2', 'Dê Da Xanh Đao Xanh', 1004, 2, 0, '["100421", "100423", "100424", "100425"]'::jsonb, FALSE),
  ('MONSTER_1004_SLOT_3', 'Tiểu Mã Diện', 1004, 3, 0, '["100431", "100433", "100435", "100436"]'::jsonb, FALSE), -- Hỏa 125 (Tam Đái)
  ('MONSTER_1004_SLOT_4', 'Báo Đốm', 1004, 4, 0, '["100441", "100443", "100444", "100445"]'::jsonb, FALSE), -- Lôi 51 (Đường Lâm)
  ('MONSTER_1004_SLOT_5', 'Dê Râu Xanh', 1004, 5, 0, '["100451", "100453", "100454", "100455"]'::jsonb, FALSE),
  ('MONSTER_1004_SLOT_6', 'Báo Hồng', 1004, 6, 0, '["100461", "100463", "100464", "100465"]'::jsonb, FALSE), -- Lôi 83 (Tiên Du)
  ('MONSTER_1006_SLOT_4', 'Ác Bá Đầu Trọc', 1006, 4, 0, '["100641", "100643", "100644", "100645"]'::jsonb, FALSE), -- Thủy 89 (Tiên Du)
  ('MONSTER_1004_SLOT_7', 'Hỏa Hồng Trư', 1004, 7, 0, '["100471", "100473", "100475", "100476"]'::jsonb, FALSE), -- Hỏa 110 (Luyên Ngục 3)
  ('MONSTER_1004_SLOT_8', 'Thỏ Chiến', 1004, 8, 0, '["100481", "100483", "100484", "100485"]'::jsonb, FALSE),
  ('MONSTER_1004_SLOT_9', 'Thỏ Tinh', 1004, 9, 0, '["100491", "100493", "100494", "100495"]'::jsonb, FALSE), -- Thủy 52 (Phù Liệt)
  ('MONSTER_1005_SLOT_1', 'Tê Giác Tinh', 1005, 1, 0, '["100511", "100513", "100515", "100516"]'::jsonb, FALSE), -- Thủy 185 (Tam Đái)
  ('MONSTER_1005_SLOT_2', 'Rắn Hổ Đất', 1005, 2, 0, '["100521", "100523", "100524", "100525"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_3', 'Thỏ Mũ Đỏ', 1005, 3, 0, '["100531", "100533", "100534", "100535"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_4', 'Thỏ Đao Lớn', 1005, 4, 0, '["100541", "100543", "100544", "100545"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_5', 'Thỏ Mặt Giận', 1005, 5, 0, '["100551", "100553", "100554", "100555"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_6', 'Hổ Vằn', 1005, 6, 0, '["100561", "100563", "100564", "100565"]'::jsonb, FALSE), -- Hỏa 53 (Tam Đái/ Phù Liệt)
  ('MONSTER_1005_SLOT_7', 'Thỏ Lính Vàng', 1005, 7, 0, '["100571", "100573", "100574", "100575"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_8', 'Báo Tím', 1005, 8, 0, '["100581", "100583", "100584", "100585"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_0', 'Báo Đen', 1006, 0, 0, '["100601", "100603", "100604", "100605"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_1', 'Voi Chiến', 1006, 1, 0, '["100611", "100613", "100615", "100616"]'::jsonb, FALSE), -- Hỏa 70 (Tế Giang)
  ('MONSTER_1006_SLOT_2', 'Báo Rình Rậm', 1006, 2, 0, '["100621", "100623", "100624", "100625"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_3', 'Ác Ma Nhất Cấp', 1006, 3, 0, '["100631", "100633", "100634", "100635"]'::jsonb, FALSE), -- Lôi 90 (Luyên Ngục 3)
  ('MONSTER_1006_SLOT_4', 'Ác Ma Nhị Cấp', 1006, 4, 0, '["100641", "100643", "100644", "100645"]'::jsonb, FALSE), -- Thủy 100 (Luyên Ngục 3)
  ('MONSTER_1006_SLOT_5', 'Hỏa Linh Sát Thủ', 1006, 5, 0, '["100651", "100653", "100654", "100655"]'::jsonb, FALSE), -- Hỏa 205 (Phong Châu)
  ('MONSTER_1006_SLOT_6', 'Báo Mun Nanh', 1006, 6, 0, '["100661", "100663", "100664", "100665"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_7', 'Mèo Săn Sương', 1006, 7, 0, '["100671", "100673", "100674", "100675"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_8', 'Mèo Mun Mắt Sáng', 1006, 8, 0, '["100681", "100683", "100684", "100685"]'::jsonb, FALSE),
  ('MONSTER_1006_SLOT_9', 'Báo Đen Đêm', 1006, 9, 0, '["100691", "100693", "100694", "100695"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_0', 'Người Cầm Thương Đỏ', 1007, 0, 0, '["100701", "100703", "100704", "100705"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_1', 'Cô Gái Đao Đỏ', 1007, 1, 0, '["100711", "100713", "100714", "100715"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_2', 'Lính Cầm Giáo', 1007, 2, 0, '["100721", "100723", "100724", "100725"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_3', 'Gái Mũ Xanh', 1007, 3, 0, '["100731", "100733", "100734", "100735"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_4', 'Cô Vung Đao', 1007, 4, 0, '["100741", "100743", "100744", "100745"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_5', 'Gã Áo Đỏ', 1007, 5, 0, '["100751", "100753", "100754", "100755"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_6', 'Lính Giáp Đen', 1007, 6, 0, '["100761", "100763", "100764", "100765"]'::jsonb, FALSE),
  ('MONSTER_1007_SLOT_7', 'Cô Vệ Hồng', 1007, 7, 0, '["100771", "100773", "100774", "100775"]'::jsonb, FALSE),
  ('MONSTER_1005_SLOT_1', 'Tê Giác Yêu Vương', 1005, 1, 0, '["100511", "100513", "100515", "100516"]'::jsonb, FALSE), -- Thủy 220 (Phong Châu)
  ('MONSTER_1007_SLOT_8', 'Tóc Cam Đao', 1007, 8, 0, '["100781", "100783", "100784", "100785"]'::jsonb, FALSE)
ON CONFLICT (AssetCatalogId) DO UPDATE SET
    SpeciesCode = EXCLUDED.SpeciesCode,
    Slot = EXCLUDED.Slot,
    SharedSheetFamily = EXCLUDED.SharedSheetFamily,
    FramePaths = EXCLUDED.FramePaths,
    DisplayName = EXCLUDED.DisplayName,
    IsCandidate = EXCLUDED.IsCandidate;

-- Candidate ranges (101xxx, 200xxx) are NOT seeded here.
-- See MONSTER_SYSTEM_RECONSTRUCTION.md: Candidate Unknown Ranges section.

-- ═══════════════════════════════════════════════════════════════════════════
-- MonsterBattles — combat templates (server truth for battle bootstrap)
-- Hoa Lu room 1: progression along map (near → far on ground_main).
--   StableKey 1 = đầu map (SpawnCol 3), StableKey 2 = giữa (4), StableKey 3 = cuối (5).
--   Cấp độ 1 / 5 / 10 trong khoảng tân thủ ~1–10; map khác seed riêng sau.
-- ═══════════════════════════════════════════════════════════════════════════

INSERT INTO MonsterBattles (
    StableKey, Element, Level, MaxHp, MaxMp, MaxPower,
    Strength, Agility, Magic, Vitality,
    MinDamage, MaxDamage, Defense, HitRate, DodgeRate, CriticalRate,
    Skills, Appearance, AiProfileId, ExpReward, GoldReward, QuanReward
) VALUES
    (1, 0, 1, 44, 10, 100,
     5, 4, 3, 5, 5, 10, 2, 72, 4, 5,
     '[]'::jsonb,
     '{"assetCatalogId":"MONSTER_1000_SLOT_0"}'::jsonb,
     'beast', 6, 3, 0),
    (2, 2, 5, 78, 32, 100,
     5, 7, 10, 8, 12, 18, 5, 78, 5, 7,
     '[{"skillId":4000,"level":1,"manaCost":6}]'::jsonb,
     '{"assetCatalogId":"MONSTER_1002_SLOT_0"}'::jsonb,
     'move_first', 24, 8, 0),
    (3, 1, 10, 108, 48, 100,
     10, 13, 8, 11, 17, 26, 9, 90, 8, 12,
     '[{"skillId":2000,"level":3,"manaCost":10}]'::jsonb,
     '{"assetCatalogId":"MONSTER_1003_SLOT_0"}'::jsonb,
     'tactician', 52, 14, 0)
ON CONFLICT (StableKey) DO UPDATE SET
    Element = EXCLUDED.Element, Level = EXCLUDED.Level,
    MaxHp = EXCLUDED.MaxHp, MaxMp = EXCLUDED.MaxMp, MaxPower = EXCLUDED.MaxPower,
    Strength = EXCLUDED.Strength, Agility = EXCLUDED.Agility,
    Magic = EXCLUDED.Magic, Vitality = EXCLUDED.Vitality,
    MinDamage = EXCLUDED.MinDamage, MaxDamage = EXCLUDED.MaxDamage,
    Defense = EXCLUDED.Defense, HitRate = EXCLUDED.HitRate,
    DodgeRate = EXCLUDED.DodgeRate, CriticalRate = EXCLUDED.CriticalRate,
    Skills = EXCLUDED.Skills, Appearance = EXCLUDED.Appearance,
    AiProfileId = EXCLUDED.AiProfileId, ExpReward = EXCLUDED.ExpReward,
    GoldReward = EXCLUDED.GoldReward, QuanReward = EXCLUDED.QuanReward;

-- ═══════════════════════════════════════════════════════════════════════════
-- MonsterSpawns — encounter templates (DisplayName follows Monsters when set)
-- ═══════════════════════════════════════════════════════════════════════════

INSERT INTO MonsterSpawns (
    SpawnTemplateKey, DisplayName, VisualTypeByte, DisplayLevel,
    IqValue, SpawnCount, NameColorMode, BattleTemplateId, MonsterId
)
SELECT
    v.SpawnTemplateKey,
    COALESCE(NULLIF(trim(m.DisplayName), ''), v.DisplayName) AS DisplayName,
    v.VisualTypeByte,
    v.DisplayLevel,
    v.IqValue,
    v.SpawnCount,
    v.NameColorMode,
    b.Id,
    m.Id
FROM (VALUES
    ('hoa_lu_fire_basic'::text, N'Quái lửa'::text, 0::smallint, 1, 2, 2, 1,
     1::int, 'MONSTER_1000_SLOT_0'::text),
    ('hoa_lu_ice_basic', N'Quái băng', 4::smallint, 5, 5, 2, 1,
     2, 'MONSTER_1002_SLOT_0'),
    ('hoa_lu_zap_basic', N'Quái sét', 2::smallint, 10, 9, 1, 1,
     3, 'MONSTER_1003_SLOT_0')
) AS v(SpawnTemplateKey, DisplayName, VisualTypeByte, DisplayLevel,
      IqValue, SpawnCount, NameColorMode, battle_stable_key, asset_key)
JOIN MonsterBattles b ON b.StableKey = v.battle_stable_key
LEFT JOIN Monsters m ON m.AssetCatalogId = v.asset_key
ON CONFLICT (SpawnTemplateKey) DO UPDATE SET
    DisplayName = EXCLUDED.DisplayName,
    VisualTypeByte = EXCLUDED.VisualTypeByte,
    DisplayLevel = EXCLUDED.DisplayLevel,
    IqValue = EXCLUDED.IqValue,
    SpawnCount = EXCLUDED.SpawnCount,
    NameColorMode = EXCLUDED.NameColorMode,
    BattleTemplateId = EXCLUDED.BattleTemplateId,
    MonsterId = EXCLUDED.MonsterId;

-- ═══════════════════════════════════════════════════════════════════════════
-- MonsterRosters — Hoa Lu room 1
-- SpawnCellCol / patrol ratios: cột nhỏ + ratio thấp = gần đầu map (quái yếu);
-- cột lớn + ratio cao = xa hơn (quái mạnh). Khớp StableKey 1→2→3 ở MonsterBattles.
-- ═══════════════════════════════════════════════════════════════════════════

INSERT INTO MonsterRosters (
    SpawnGroupKey, MapId, RoomId, MonsterSpawnId,
    SpawnCellRow, SpawnCellCol, SurfaceId,
    PatrolStartRatio, PatrolEndRatio,
    SpawnStartRatio, SpawnEndRatio, MoveSpeed
)
SELECT
    v.SpawnGroupKey,
    v.MapId,
    v.RoomId,
    s.Id,
    v.SpawnCellRow,
    v.SpawnCellCol,
    v.SurfaceId,
    v.PatrolStartRatio,
    v.PatrolEndRatio,
    v.SpawnStartRatio,
    v.SpawnEndRatio,
    v.MoveSpeed
FROM (VALUES
    ('hoa_lu_fire_group_a', 'Hoa Lu', 1, 'hoa_lu_fire_basic', 5, 3, 'ground_main',
     0.25::real, 0.48::real, 0.32::real, 0.46::real, 2.2::real),
    ('hoa_lu_ice_group_a', 'Hoa Lu', 1, 'hoa_lu_ice_basic', 5, 4, 'ground_main',
     0.48::real, 0.72::real, 0.50::real, 0.66::real, 2.6::real),
    ('hoa_lu_zap_group_a', 'Hoa Lu', 1, 'hoa_lu_zap_basic', 5, 5, 'ground_main',
     0.70::real, 0.92::real, 0.76::real, 0.88::real, 3.0::real)
) AS v(SpawnGroupKey, MapId, RoomId, spawn_key,
       SpawnCellRow, SpawnCellCol, SurfaceId,
       PatrolStartRatio, PatrolEndRatio,
       SpawnStartRatio, SpawnEndRatio, MoveSpeed)
JOIN MonsterSpawns s ON s.SpawnTemplateKey = v.spawn_key
WHERE NOT EXISTS (
    SELECT 1 FROM MonsterRosters r
    WHERE r.SpawnGroupKey = v.SpawnGroupKey
      AND r.MapId = v.MapId
      AND r.RoomId = v.RoomId
);
