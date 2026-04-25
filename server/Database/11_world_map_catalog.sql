-- Migration 11: World map catalog and per-player unlock state.
-- Ref: MAP_SYSTEM_RECONSTRUCTION.md, RuntimeMapCatalog.cs, Java client og.java/oh.java.
-- Java client provides fixed city index order, label coordinates, lock coordinates and hit rectangles.
-- Server/DB owns unlock state so client only renders server truth.

CREATE TABLE IF NOT EXISTS WorldMapCatalog (
    Id SERIAL PRIMARY KEY,
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
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    WorldMapId INT NOT NULL REFERENCES WorldMapCatalog(Id) ON DELETE CASCADE,
    IsUnlocked BOOLEAN NOT NULL DEFAULT TRUE,
    UnlockedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UnlockReason TEXT NOT NULL DEFAULT 'system',
    PRIMARY KEY (PlayerId, WorldMapId)
);

CREATE INDEX IF NOT EXISTS idx_player_world_map_unlocks_player_id ON PlayerWorldMapUnlocks(PlayerId);
CREATE INDEX IF NOT EXISTS idx_player_world_map_unlocks_world_map_id ON PlayerWorldMapUnlocks(WorldMapId);

INSERT INTO WorldMapCatalog (
    Code,
    MapIndex,
    DisplayName,
    RuntimeMapId,
    LabelX,
    LabelY,
    LockX,
    LockY,
    HitX,
    HitY,
    HitWidth,
    HitHeight,
    IsEnabled
)
VALUES
    ('hoalu', 0, 'Hoa Lư', 'Hoa Lu', 160, 385, 168, 355, 146, 342, 61, 48, TRUE),
    ('kybo', 1, 'Kỷ Bố', 'Ky Bo', 403, 370, 410, 340, 381, 319, 62, 48, TRUE),
    ('binhkieu', 2, 'Bình Kiều', 'Binh Kieu', 24, 432, 32, 402, 9, 386, 64, 48, TRUE),
    ('dangchau', 3, 'Đằng Châu', 'Dang Chau', 425, 290, 433, 260, 407, 245, 65, 47, TRUE),
    ('dodonggiang', 4, 'Đỗ Động Giang', 'Do Dong Giang', 256, 310, 262, 280, 236, 271, 64, 44, TRUE),
    ('tegiang', 5, 'Tế Giang', 'Te Giang', 314, 270, 322, 240, 300, 227, 63, 44, TRUE),
    ('sieuloai', 6, 'Siêu Loại', 'Sieu Loai', 348, 184, 354, 154, 332, 142, 62, 48, TRUE),
    ('tayphuliet', 7, 'Tây Phù Liệt', 'Tay Phu Liet', 188, 216, 196, 186, 173, 169, 64, 48, TRUE),
    ('duonglam', 8, 'Đường Lâm', 'Duong Lam', 82, 225, 90, 195, 68, 185, 62, 48, TRUE),
    ('coloa', 9, 'Cổ Loa', 'Co Loa', 258, 169, 266, 139, 242, 128, 62, 48, TRUE),
    ('tiendu', 10, 'Tiên Du', 'Tien Du', 385, 110, 393, 80, 369, 70, 64, 44, TRUE),
    ('tamdai', 11, 'Tam Đái', 'Tam Dai', 238, 100, 246, 70, 229, 74, 63, 45, TRUE),
    ('phongchau', 12, 'Phong Châu', 'Phong Chau', 98, 115, 106, 85, 84, 73, 63, 47, TRUE),
    ('hoiho', 13, 'Hồi Hồ', 'Hoi Ho', 32, 88, 40, 58, 18, 38, 62, 48, TRUE),
    ('luyennguc', 14, 'Luyện Ngục', 'Luyen Nguc', 43, 315, 51, 285, 34, 271, 52, 46, TRUE),
    ('thienmon', 15, 'Thiên Môn', 'Thien Mon', 415, 165, 424, 135, 391, 131, 66, 44, TRUE),
    ('mauson', 16, 'Mẫu Sơn', 'Mau Son', 175, 55, 183, 25, 157, 27, 71, 26, TRUE)
ON CONFLICT (Code) DO UPDATE
SET MapIndex = EXCLUDED.MapIndex,
    DisplayName = EXCLUDED.DisplayName,
    RuntimeMapId = EXCLUDED.RuntimeMapId,
    LabelX = EXCLUDED.LabelX,
    LabelY = EXCLUDED.LabelY,
    LockX = EXCLUDED.LockX,
    LockY = EXCLUDED.LockY,
    HitX = EXCLUDED.HitX,
    HitY = EXCLUDED.HitY,
    HitWidth = EXCLUDED.HitWidth,
    HitHeight = EXCLUDED.HitHeight,
    IsEnabled = EXCLUDED.IsEnabled,
    UpdatedAt = CURRENT_TIMESTAMP;

-- Hoa Lư is the initial unlocked city for every existing player.
INSERT INTO PlayerWorldMapUnlocks (
    PlayerId,
    WorldMapId,
    IsUnlocked,
    UnlockReason
)
SELECT
    p.Id,
    w.Id,
    TRUE,
    'initial_hoa_lu'
FROM Players p
JOIN WorldMapCatalog w ON w.Code = 'hoalu'
ON CONFLICT (PlayerId, WorldMapId) DO UPDATE
SET IsUnlocked = TRUE,
    UnlockReason = EXCLUDED.UnlockReason;
