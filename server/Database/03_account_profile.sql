-- Migration: Thêm thông tin cá nhân vào bảng Accounts
-- Chạy sau 01_accounts.sql

ALTER TABLE Accounts
    ADD COLUMN IF NOT EXISTS FullName    VARCHAR(100) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS DateOfBirth VARCHAR(20)  NOT NULL DEFAULT '',   -- DD-MM-YYYY (hoặc DD - MM - YYYY)
    ADD COLUMN IF NOT EXISTS Phone       VARCHAR(20)  NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS Gender      SMALLINT     NOT NULL DEFAULT 0;    -- 0=Nam, 1=Nữ

-- Đảm bảo cột đủ rộng kể cả khi đã tạo với type hẹp hơn trước đó
ALTER TABLE Accounts ALTER COLUMN DateOfBirth TYPE VARCHAR(20);
ALTER TABLE Accounts ALTER COLUMN Phone       TYPE VARCHAR(20);
ALTER TABLE Accounts ALTER COLUMN FullName    TYPE VARCHAR(100);

COMMENT ON COLUMN Accounts.Gender      IS '0 = Nam, 1 = Nữ';
COMMENT ON COLUMN Accounts.DateOfBirth IS 'Định dạng DD - MM - YYYY';
