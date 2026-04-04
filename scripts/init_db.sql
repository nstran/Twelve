-- Khởi tạo Database cho Project Twelve (Loạn 12 Sứ Quân)
-- DB Name: SHIHEN

-- Xóa bảng cũ nếu tồn tại (Cẩn trọng!)
-- DROP TABLE IF EXISTS Players;

CREATE TABLE IF NOT EXISTS Players (
    Id SERIAL PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Level INT NOT NULL DEFAULT 1,
    Gold BIGINT NOT NULL DEFAULT 0,
    Exp BIGINT NOT NULL DEFAULT 0,
    
    -- Vị trí nhân vật
    CurrentMap VARCHAR(50) NOT NULL DEFAULT 'M99',
    CurrentRoom INT NOT NULL DEFAULT 1,
    
    -- Chỉ số chiến đấu
    Hp INT NOT NULL DEFAULT 100,
    MaxHp INT NOT NULL DEFAULT 100,
    Mp INT NOT NULL DEFAULT 50,
    MaxMp INT NOT NULL DEFAULT 50,
    
    -- Thời gian
    CreatedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    LastSeenAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index để tìm kiếm nhanh theo Username
CREATE INDEX IF NOT EXISTS idx_players_username ON Players(Username);

-- Chèn dữ liệu mẫu nếu cần
INSERT INTO Players (Username, Level, Gold, Exp)
VALUES ('Admin', 99, 1000000, 0)
ON CONFLICT (Username) DO NOTHING;
