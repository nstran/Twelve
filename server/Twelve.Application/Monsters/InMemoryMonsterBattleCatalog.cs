using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class InMemoryMonsterBattleCatalog : IMonsterBattleCatalog
    {
        private readonly Dictionary<string, MonsterBattleTemplate> _templates =
            new(System.StringComparer.OrdinalIgnoreCase)
            {
                // Scaffold battle templates aligned with the current local client visuals.
                // HP bands intentionally match the previous client placeholders so the new
                // authority path does not cause a gameplay jump during integration.
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
                        AssetCatalogId: "monster_species_1003_slot_0"))
            };

        public MonsterBattleTemplate? GetByBattleTemplateId(string battleTemplateId) =>
            _templates.TryGetValue(battleTemplateId, out var template) ? template : null;
    }
}
