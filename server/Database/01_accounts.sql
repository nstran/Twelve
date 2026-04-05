-- Twelve Project: Accounts & Authentication Schema
-- This Table stores login credentials separately from Player character data.

CREATE TABLE IF NOT EXISTS Accounts (
    Id SERIAL PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    PasswordHash TEXT NOT NULL,
    Salt TEXT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LastLoginAt TIMESTAMP
);

-- Link Players to Accounts
-- If the Players table already exists without AccountId, we will add it later.
-- ALter TABLE Players ADD COLUMN AccountId INTEGER REFERENCES Accounts(Id);
