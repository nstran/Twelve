-- ── Thêm các cột cho đặc tính nhân vật ───────────────────────────────────────
-- Sử dụng ADD COLUMN IF NOT EXISTS để đảm bảo an toàn khi chạy lại nhiều lần.

ALTER TABLE Players ADD COLUMN IF NOT EXISTS Element INT;
ALTER TABLE Players ADD COLUMN IF NOT EXISTS FaceStyle INT;
ALTER TABLE Players ADD COLUMN IF NOT EXISTS HairStyle INT;
ALTER TABLE Players ADD COLUMN IF NOT EXISTS HairColor INT;
ALTER TABLE Players ADD COLUMN IF NOT EXISTS SkinColor INT;

-- Giải thích các giá trị mặc định:
-- Element: 0=Kim, 1=Mộc, 2=Thủy, 3=Hỏa, 4=Thổ
