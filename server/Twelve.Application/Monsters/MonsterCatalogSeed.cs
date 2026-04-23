using System;
using System.Collections.Generic;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    internal static class MonsterCatalogSeed
    {
        // Current phase: server-authoritative file/code seed.
        // Next phase: move the same shapes into DB tables without changing the
        // public contracts consumed by map scenes and battle bootstrap.

        public static IReadOnlyDictionary<string, MonsterAssetCatalogEntry> AssetCatalogEntries { get; } =
            new Dictionary<string, MonsterAssetCatalogEntry>(StringComparer.OrdinalIgnoreCase)
            {
                ["monster_species_1000_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1000_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Monster,
                    SpeciesCode: 1000,
                    Slot: 0),
                ["monster_species_1002_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1002_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Ice,
                    SpeciesCode: 1002,
                    Slot: 0),
                ["monster_species_1003_slot_0"] = new MonsterAssetCatalogEntry(
                    AssetCatalogId: "monster_species_1003_slot_0",
                    SharedSheetFamily: MonsterSharedSheetFamily.Zap,
                    SpeciesCode: 1003,
                    Slot: 0),
            };

        public static IReadOnlyDictionary<string, MonsterSpawnTemplate> SpawnTemplates { get; } =
            new Dictionary<string, MonsterSpawnTemplate>(StringComparer.OrdinalIgnoreCase)
            {
                ["hoa_lu_fire_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_fire_basic",
                    DisplayName: "Gà Điên",
                    VisualTypeByte: 0,
                    DisplayLevel: 7,
                    IqValue: 2,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_fire_basic",
                    AssetCatalogId: "monster_species_1000_slot_0"),
                ["hoa_lu_ice_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_ice_basic",
                    DisplayName: "Băng Linh",
                    VisualTypeByte: 4,
                    DisplayLevel: 8,
                    IqValue: 8,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_ice_basic",
                    AssetCatalogId: "monster_species_1002_slot_0"),
                ["hoa_lu_zap_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_zap_basic",
                    DisplayName: "Lôi Thú",
                    VisualTypeByte: 2,
                    DisplayLevel: 9,
                    IqValue: 11,
                    SpawnCount: 1,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_zap_basic",
                    AssetCatalogId: "monster_species_1003_slot_0"),
            };

        public static IReadOnlyDictionary<string, MonsterBattleTemplate> BattleTemplates { get; } =
            new Dictionary<string, MonsterBattleTemplate>(StringComparer.OrdinalIgnoreCase)
            {
                ["battle_hoa_lu_fire_basic"] = new MonsterBattleTemplate(
                    BattleTemplateId: "battle_hoa_lu_fire_basic",
                    Element: 0,
                    Level: 7,
                    MaxHp: 120,
                    MaxMp: 36,
                    MaxPower: 100,
                    Strength: 12,
                    Agility: 8,
                    Magic: 5,
                    Vitality: 10,
                    MinDamage: 12,
                    MaxDamage: 18,
                    Defense: 4,
                    HitRate: 82,
                    DodgeRate: 4,
                    CriticalDamage: 110,
                    Skills: new[]
                    {
                        new MonsterSkillTemplate(SkillId: 1000, Level: 1, ManaCost: 6)
                    },
                    Appearance: new MonsterAppearanceTemplate(
                        AssetCatalogId: "monster_species_1000_slot_0")),
                ["battle_hoa_lu_ice_basic"] = new MonsterBattleTemplate(
                    BattleTemplateId: "battle_hoa_lu_ice_basic",
                    Element: 2,
                    Level: 8,
                    MaxHp: 150,
                    MaxMp: 48,
                    MaxPower: 100,
                    Strength: 10,
                    Agility: 10,
                    Magic: 12,
                    Vitality: 14,
                    MinDamage: 14,
                    MaxDamage: 20,
                    Defense: 6,
                    HitRate: 85,
                    DodgeRate: 5,
                    CriticalDamage: 115,
                    Skills: new[]
                    {
                        new MonsterSkillTemplate(SkillId: 4000, Level: 2, ManaCost: 8)
                    },
                    Appearance: new MonsterAppearanceTemplate(
                        AssetCatalogId: "monster_species_1002_slot_0")),
                ["battle_hoa_lu_zap_basic"] = new MonsterBattleTemplate(
                    BattleTemplateId: "battle_hoa_lu_zap_basic",
                    Element: 1,
                    Level: 9,
                    MaxHp: 180,
                    MaxMp: 60,
                    MaxPower: 100,
                    Strength: 16,
                    Agility: 14,
                    Magic: 8,
                    Vitality: 16,
                    MinDamage: 18,
                    MaxDamage: 26,
                    Defense: 8,
                    HitRate: 90,
                    DodgeRate: 7,
                    CriticalDamage: 120,
                    Skills: new[]
                    {
                        new MonsterSkillTemplate(SkillId: 2000, Level: 3, ManaCost: 10)
                    },
                    Appearance: new MonsterAppearanceTemplate(
                        AssetCatalogId: "monster_species_1003_slot_0")),
            };

        public static IReadOnlyDictionary<string, IReadOnlyList<MapMonsterEncounter>> MapRosters { get; } =
            new Dictionary<string, IReadOnlyList<MapMonsterEncounter>>(StringComparer.OrdinalIgnoreCase)
            {
                [ToRosterKey("Hoa Lu", 1)] = new[]
                {
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_FIRE_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_fire_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 3,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.25f,
                        PatrolEndRatio: 0.48f,
                        SpawnRatio: 0.40f,
                        MoveSpeed: 2.2f),
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_ICE_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_ice_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 4,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.48f,
                        PatrolEndRatio: 0.72f,
                        SpawnRatio: 0.50f,
                        MoveSpeed: 2.6f),
                    new MapMonsterEncounter(
                        MonsterKey: "HOA_LU_ZAP_001",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_zap_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 5,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.70f,
                        PatrolEndRatio: 0.92f,
                        SpawnRatio: 0.50f,
                        MoveSpeed: 3.0f),
                }
            };

        public static string ToRosterKey(string mapId, int roomId) => $"{mapId}#{roomId}";
    }
}
