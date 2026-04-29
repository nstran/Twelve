-- Module: Accounts — login credentials and profile (merged 01_accounts + 03_account_profile).
-- PK/FK policy: surrogate keys use BIGINT (BIGSERIAL) for long-term scale and C# long alignment.

CREATE TABLE IF NOT EXISTS Accounts (
    Id           BIGSERIAL PRIMARY KEY,
    Username     VARCHAR(50) UNIQUE NOT NULL,
    PasswordHash TEXT NOT NULL,
    Salt         TEXT NOT NULL,

    FullName     VARCHAR(100) NOT NULL DEFAULT '',
    DateOfBirth  VARCHAR(20)  NOT NULL DEFAULT '',
    Phone        VARCHAR(20)  NOT NULL DEFAULT '',
    Gender       SMALLINT     NOT NULL DEFAULT 0,

    CreatedAt    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LastLoginAt  TIMESTAMP WITH TIME ZONE NULL
);

COMMENT ON COLUMN Accounts.Gender      IS '0 = Nam, 1 = Nữ';
COMMENT ON COLUMN Accounts.DateOfBirth IS 'Định dạng DD - MM - YYYY';
