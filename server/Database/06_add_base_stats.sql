-- Migration 06: Thêm 4 chỉ số cơ bản + điểm tiềm năng + Power vào bảng Players
-- Ref: combat-formulas.md § 10 — Initial stats by element
-- Java src: lh.h(CuongLuc), lh.j(ThanPhap), lh.i(NoiLuc), lh.k(TheLuc), lh.K(FreePoints)

ALTER TABLE Players
    -- 4 chỉ số gốc (phân phối điểm tiềm năng)
    ADD COLUMN IF NOT EXISTS CuongLuc   INT NOT NULL DEFAULT 10,  -- Strength  / lh.h / jp.a
    ADD COLUMN IF NOT EXISTS ThanPhap   INT NOT NULL DEFAULT 10,  -- Agility   / lh.j / jp.b
    ADD COLUMN IF NOT EXISTS NoiLuc     INT NOT NULL DEFAULT 10,  -- Magic     / lh.i / jp.c
    ADD COLUMN IF NOT EXISTS TheLuc     INT NOT NULL DEFAULT 10,  -- Vitality  / lh.k / jp.d
    ADD COLUMN IF NOT EXISTS FreePoints INT NOT NULL DEFAULT 5,   -- Điểm chưa phân / lh.K / Tag 53

    -- Power (Thanh Nộ) chưa có trong schema cũ
    ADD COLUMN IF NOT EXISTS Power      INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS MaxPower   INT NOT NULL DEFAULT 0;

-- Cập nhật HP mặc định về 60 (TheLuc=10 × hệ số Hỏa=6)
-- Chỉ áp dụng cho row có MaxHp=100 (row cũ chưa được cập nhật)
UPDATE Players SET MaxHp = 60, Hp = 60 WHERE MaxHp = 100 AND CuongLuc = 10;

-- Comment: Giá trị đúng theo element phải được set lúc CreateCharacter:
-- Hỏa  (element=0): CuongLuc=15, ThanPhap=10, NoiLuc=5,  TheLuc=10 → MaxHp=60
-- Lôi  (element=1): CuongLuc=5,  ThanPhap=15, NoiLuc=5,  TheLuc=10 → MaxHp=40
-- Thủy (element=2): CuongLuc=5,  ThanPhap=10, NoiLuc=15, TheLuc=10 → MaxHp=50
