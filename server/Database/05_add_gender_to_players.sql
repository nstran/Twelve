-- ── Thêm cột Gender vào bảng Players ─────────────────────────────────────────
-- 0 = Nam, 1 = Nữ
-- DEFAULT 0 để không break các row cũ chưa có giá trị.

ALTER TABLE Players ADD COLUMN IF NOT EXISTS Gender INT NOT NULL DEFAULT 0;
