-- Migration 007: Remove cached derived stats columns from Players table.
-- These columns were previously used to cache computed stats on the Player row,
-- but now derived stats (MinDamage, MaxDamage, Defense, Dodge, Hit, Crit, MaxPower)
-- are computed on-the-fly by PlayerStatPipeline.Calculate() and never stored in DB.
--
-- Applied: 2026-04-27

ALTER TABLE Players
    DROP COLUMN IF EXISTS DerivedMinDamage,
    DROP COLUMN IF EXISTS DerivedMaxDamage,
    DROP COLUMN IF EXISTS DerivedDefense,
    DROP COLUMN IF EXISTS DerivedDodge,
    DROP COLUMN IF EXISTS DerivedHit,
    DROP COLUMN IF EXISTS DerivedCrit,
    DROP COLUMN IF EXISTS MaxPower;