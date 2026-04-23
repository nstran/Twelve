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
                    SpawnCount: 2,
                    NameColorMode: 1,
                    BattleTemplateId: "battle_hoa_lu_fire_basic",
                    AssetCatalogId: "monster_species_1000_slot_0"),
                ["hoa_lu_ice_basic"] = new MonsterSpawnTemplate(
                    SpawnTemplateKey: "hoa_lu_ice_basic",
                    DisplayName: "Băng Linh",
                    VisualTypeByte: 4,
                    DisplayLevel: 8,
                    IqValue: 8,
                    SpawnCount: 2,
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
                ["battle_hoa_lu_fire_basic"] = MonsterBattleRuleFactory.Build(new MonsterBattleRuleSpec(
                    BattleTemplateId: "battle_hoa_lu_fire_basic",
                    Element: 0,
                    Level: 7,
                    Role: MonsterCombatRole.Brute,
                    ThreatTier: MonsterThreatTier.Minor,
                    SkillTier: MonsterSkillTier.None,
                    AssetCatalogId: "monster_species_1000_slot_0")),
                ["battle_hoa_lu_ice_basic"] = MonsterBattleRuleFactory.Build(new MonsterBattleRuleSpec(
                    BattleTemplateId: "battle_hoa_lu_ice_basic",
                    Element: 2,
                    Level: 8,
                    Role: MonsterCombatRole.Mystic,
                    ThreatTier: MonsterThreatTier.Standard,
                    SkillTier: MonsterSkillTier.Basic,
                    AssetCatalogId: "monster_species_1002_slot_0")),
                ["battle_hoa_lu_zap_basic"] = MonsterBattleRuleFactory.Build(new MonsterBattleRuleSpec(
                    BattleTemplateId: "battle_hoa_lu_zap_basic",
                    Element: 1,
                    Level: 9,
                    Role: MonsterCombatRole.Skirmisher,
                    ThreatTier: MonsterThreatTier.Elite,
                    SkillTier: MonsterSkillTier.Advanced,
                    AssetCatalogId: "monster_species_1003_slot_0")),
            };

        public static IReadOnlyDictionary<string, IReadOnlyList<MapMonsterSpawnGroup>> MapSpawnGroups { get; } =
            new Dictionary<string, IReadOnlyList<MapMonsterSpawnGroup>>(StringComparer.OrdinalIgnoreCase)
            {
                [ToRosterKey("Hoa Lu", 1)] = new[]
                {
                    new MapMonsterSpawnGroup(
                        SpawnGroupKey: "hoa_lu_fire_group_a",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_fire_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 3,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.25f,
                        PatrolEndRatio: 0.48f,
                        SpawnStartRatio: 0.32f,
                        SpawnEndRatio: 0.46f,
                        MoveSpeed: 2.2f),
                    new MapMonsterSpawnGroup(
                        SpawnGroupKey: "hoa_lu_ice_group_a",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_ice_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 4,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.48f,
                        PatrolEndRatio: 0.72f,
                        SpawnStartRatio: 0.50f,
                        SpawnEndRatio: 0.66f,
                        MoveSpeed: 2.6f),
                    new MapMonsterSpawnGroup(
                        SpawnGroupKey: "hoa_lu_zap_group_a",
                        MapId: "Hoa Lu",
                        RoomId: 1,
                        SpawnTemplateKey: "hoa_lu_zap_basic",
                        SpawnCellRow: 5,
                        SpawnCellCol: 5,
                        SurfaceId: "ground_main",
                        PatrolStartRatio: 0.70f,
                        PatrolEndRatio: 0.92f,
                        SpawnStartRatio: 0.76f,
                        SpawnEndRatio: 0.88f,
                        MoveSpeed: 3.0f),
                }
            };

        public static string ToRosterKey(string mapId, int roomId) => $"{mapId}#{roomId}";
    }
}
