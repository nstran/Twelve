-- Migration 013: Remove duplicate appearance snapshot column from Players table.
-- Appearance source-of-truth is now the scalar Java-compatible fields:
-- Gender, Element, RawElementCode, FaceStyle, HairStyle, HairColor, SkinColor,
-- AppearanceHidden0, AppearanceHidden1, SpecialActorForm.
--
-- Applied: 2026-04-27

ALTER TABLE Players
    DROP COLUMN IF EXISTS AppearanceJson;