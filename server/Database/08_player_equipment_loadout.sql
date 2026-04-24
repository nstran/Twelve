-- Migration 08: Track equipped-vs-bag state for player equipment.

ALTER TABLE PlayerEquipment
    ADD COLUMN IF NOT EXISTS IsEquipped BOOLEAN NOT NULL DEFAULT FALSE;
