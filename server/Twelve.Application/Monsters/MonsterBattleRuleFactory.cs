using System;
using System.Collections.Generic;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    internal enum MonsterCombatRole
    {
        Brute = 0,
        Skirmisher = 1,
        Mystic = 2,
    }

    internal enum MonsterThreatTier
    {
        Minor = 0,
        Standard = 1,
        Elite = 2,
    }

    internal enum MonsterSkillTier
    {
        None = 0,
        Basic = 1,
        Advanced = 2,
    }

    internal sealed record MonsterBattleRuleSpec(
        string BattleTemplateId,
        byte Element,
        int Level,
        MonsterCombatRole Role,
        MonsterThreatTier ThreatTier,
        MonsterSkillTier SkillTier,
        string AssetCatalogId
    );

    internal static class MonsterBattleRuleFactory
    {
        public static MonsterBattleTemplate Build(MonsterBattleRuleSpec spec)
        {
            var stats = ResolveStats(spec);
            var attack = ResolveBaseAttack(spec.Element, stats.Strength, stats.Agility, stats.Magic);
            var defenseBase = stats.Agility / 2;
            var accuracyBase = ResolveAccuracyBase(spec.Element, stats.Agility);
            var dodgeBase = ResolveDodgeBase(spec.Element, stats.Agility);
            var critChance = Math.Min(5 + (stats.Agility / 8), 30);
            var skills = BuildSkills(spec);

            var maxHp = (stats.Vitality * ResolveHpFactor(spec.Element) * 2)
                + ResolveRoleHpBonus(spec.Role)
                + ResolveThreatHpBonus(spec.ThreatTier);
            var maxMp = 12
                + (stats.Magic * 2)
                + spec.Level
                + ResolveSkillMpBonus(spec.SkillTier)
                + ResolveRoleMpBonus(spec.Role);
            var minDamage = Math.Max(1, attack + (spec.Level / 2) + ResolveRoleMinDamageBonus(spec.Role));
            var maxDamage = minDamage
                + 4
                + ResolveThreatDamageVariance(spec.ThreatTier)
                + ResolveRoleDamageVariance(spec.Role);
            var defense = defenseBase
                + ResolveRoleDefenseBonus(spec.Role)
                + ResolveThreatDefenseBonus(spec.ThreatTier);
            var hitRate = Math.Min(
                98,
                70 + (accuracyBase / 2) + ResolveThreatHitBonus(spec.ThreatTier) + ResolveRoleHitBonus(spec.Role));
            var dodgeRate = Math.Max(
                3,
                (dodgeBase / 5) + ResolveThreatDodgeBonus(spec.ThreatTier) + ResolveRoleDodgeBonus(spec.Role));
            var criticalDamage = 105
                + (critChance / 2)
                + ResolveThreatCriticalDamageBonus(spec.ThreatTier)
                + ResolveRoleCriticalDamageBonus(spec.Role);

            return new MonsterBattleTemplate(
                BattleTemplateId: spec.BattleTemplateId,
                Element: spec.Element,
                Level: spec.Level,
                MaxHp: maxHp,
                MaxMp: maxMp,
                MaxPower: 100,
                Strength: stats.Strength,
                Agility: stats.Agility,
                Magic: stats.Magic,
                Vitality: stats.Vitality,
                MinDamage: minDamage,
                MaxDamage: maxDamage,
                Defense: defense,
                HitRate: hitRate,
                DodgeRate: dodgeRate,
                CriticalDamage: criticalDamage,
                Skills: skills,
                Appearance: new MonsterAppearanceTemplate(AssetCatalogId: spec.AssetCatalogId),
                AiProfileId: ResolveAiProfileId(spec));
        }

        private static (int Strength, int Agility, int Magic, int Vitality) ResolveStats(MonsterBattleRuleSpec spec)
        {
            var level = Math.Max(1, spec.Level);
            var strength = spec.Element switch
            {
                0 => 5 + level,
                1 => 4 + ((level * 2) / 3),
                2 => 3 + (level / 2),
                _ => 5 + level,
            };
            var agility = spec.Element switch
            {
                0 => 4 + (level / 2),
                1 => 5 + level,
                2 => 4 + (level / 2),
                _ => 4 + (level / 2),
            };
            var magic = spec.Element switch
            {
                0 => 3 + (level / 3),
                1 => 3 + (level / 3),
                2 => 5 + level,
                _ => 3 + (level / 3),
            };
            var vitality = spec.Element switch
            {
                0 => 5 + (level / 2),
                1 => 4 + (level / 2),
                2 => 5 + ((level * 2) / 3),
                _ => 5 + (level / 2),
            };

            switch (spec.Role)
            {
                case MonsterCombatRole.Brute:
                    strength += 1;
                    vitality += 1;
                    break;
                case MonsterCombatRole.Skirmisher:
                    strength += 1;
                    agility += 1;
                    break;
                case MonsterCombatRole.Mystic:
                    magic += 1;
                    vitality += 1;
                    break;
            }

            switch (spec.ThreatTier)
            {
                case MonsterThreatTier.Standard:
                    vitality += 1;
                    ApplyPrimaryStatBonus(spec.Element, ref strength, ref agility, ref magic, primaryBonus: 1, secondaryBonus: 0);
                    break;
                case MonsterThreatTier.Elite:
                    vitality += 2;
                    ApplyPrimaryStatBonus(spec.Element, ref strength, ref agility, ref magic, primaryBonus: 2, secondaryBonus: 1);
                    break;
            }

            return (strength, agility, magic, vitality);
        }

        private static void ApplyPrimaryStatBonus(
            byte element,
            ref int strength,
            ref int agility,
            ref int magic,
            int primaryBonus,
            int secondaryBonus)
        {
            switch (element)
            {
                case 0:
                    strength += primaryBonus;
                    agility += secondaryBonus;
                    break;
                case 1:
                    agility += primaryBonus;
                    strength += secondaryBonus;
                    break;
                case 2:
                    magic += primaryBonus;
                    agility += secondaryBonus;
                    break;
            }
        }

        private static int ResolveBaseAttack(byte element, int strength, int agility, int magic) =>
            element switch
            {
                0 => strength,
                1 => ((agility * 80) + (strength * 16)) / 100,
                2 => (magic * 130) / 100,
                _ => strength,
            };

        private static int ResolveAccuracyBase(byte element, int agility) =>
            element == 2 ? agility * 2 : agility * 3;

        private static int ResolveDodgeBase(byte element, int agility) =>
            element switch
            {
                0 => agility * 2,
                1 => (agility * 15) / 10,
                2 => agility * 3,
                _ => agility * 2,
            };

        private static int ResolveHpFactor(byte element) =>
            element switch
            {
                0 => 6,
                1 => 4,
                2 => 5,
                _ => 5,
            };

        private static int ResolveRoleHpBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Brute => 0,
                MonsterCombatRole.Skirmisher => 12,
                MonsterCombatRole.Mystic => 8,
                _ => 0,
            };

        private static int ResolveThreatHpBonus(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Standard => 12,
                MonsterThreatTier.Elite => 36,
                _ => 0,
            };

        private static int ResolveRoleMpBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Mystic => 6,
                MonsterCombatRole.Skirmisher => 2,
                _ => 0,
            };

        private static int ResolveSkillMpBonus(MonsterSkillTier tier) =>
            tier switch
            {
                MonsterSkillTier.Basic => 8,
                MonsterSkillTier.Advanced => 16,
                _ => 0,
            };

        private static int ResolveRoleMinDamageBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Brute => 2,
                MonsterCombatRole.Skirmisher => 1,
                MonsterCombatRole.Mystic => 1,
                _ => 0,
            };

        private static int ResolveRoleDamageVariance(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Brute => 2,
                MonsterCombatRole.Skirmisher => 2,
                MonsterCombatRole.Mystic => 1,
                _ => 1,
            };

        private static int ResolveThreatDamageVariance(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Standard => 1,
                MonsterThreatTier.Elite => 3,
                _ => 0,
            };

        private static int ResolveRoleDefenseBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Brute => 1,
                MonsterCombatRole.Mystic => 1,
                _ => 0,
            };

        private static int ResolveThreatDefenseBonus(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Standard => 1,
                MonsterThreatTier.Elite => 2,
                _ => 0,
            };

        private static int ResolveRoleHitBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Skirmisher => 2,
                MonsterCombatRole.Mystic => 1,
                _ => 0,
            };

        private static int ResolveThreatHitBonus(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Standard => 2,
                MonsterThreatTier.Elite => 4,
                _ => 0,
            };

        private static int ResolveRoleDodgeBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Skirmisher => 1,
                _ => 0,
            };

        private static int ResolveThreatDodgeBonus(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Elite => 2,
                MonsterThreatTier.Standard => 1,
                _ => 0,
            };

        private static int ResolveRoleCriticalDamageBonus(MonsterCombatRole role) =>
            role switch
            {
                MonsterCombatRole.Brute => 2,
                MonsterCombatRole.Skirmisher => 3,
                _ => 0,
            };

        private static int ResolveThreatCriticalDamageBonus(MonsterThreatTier tier) =>
            tier switch
            {
                MonsterThreatTier.Standard => 2,
                MonsterThreatTier.Elite => 5,
                _ => 0,
            };

        private static IReadOnlyList<MonsterSkillTemplate> BuildSkills(MonsterBattleRuleSpec spec)
        {
            if (spec.SkillTier == MonsterSkillTier.None)
            {
                return [];
            }

            var skills = new List<MonsterSkillTemplate>(2);
            var primarySkillId = ResolvePrimarySkillId(spec.Element, spec.SkillTier, spec.Level);
            var primarySkillLevel = ResolveSkillLevel(spec.Level, spec.SkillTier);
            skills.Add(new MonsterSkillTemplate(
                SkillId: primarySkillId,
                Level: primarySkillLevel,
                ManaCost: ResolveManaCost(primarySkillLevel, spec.SkillTier)));

            if (spec.SkillTier == MonsterSkillTier.Advanced && spec.Level >= 14)
            {
                var secondarySkillId = ResolveAdvancedSecondarySkillId(spec.Element);
                if (secondarySkillId.HasValue && secondarySkillId.Value != primarySkillId)
                {
                    var secondaryLevel = Math.Max(1, primarySkillLevel - 1);
                    skills.Add(new MonsterSkillTemplate(
                        SkillId: secondarySkillId.Value,
                        Level: secondaryLevel,
                        ManaCost: ResolveManaCost(secondaryLevel, MonsterSkillTier.Basic)));
                }
            }

            return skills;
        }

        private static int ResolvePrimarySkillId(byte element, MonsterSkillTier tier, int level) =>
            (element, tier) switch
            {
                (_, MonsterSkillTier.None) => 0,
                (0, MonsterSkillTier.Basic) => 1000,
                (0, MonsterSkillTier.Advanced) => level >= 10 ? 1006 : 1000,
                (1, MonsterSkillTier.Basic) => 2000,
                (1, MonsterSkillTier.Advanced) => level >= 10 ? 2003 : 2000,
                (2, MonsterSkillTier.Basic) => 4000,
                (2, MonsterSkillTier.Advanced) => level >= 10 ? 4006 : 4000,
                _ => 1000,
            };

        private static int? ResolveAdvancedSecondarySkillId(byte element) =>
            element switch
            {
                0 => 1007,
                1 => 2006,
                2 => 4007,
                _ => null,
            };

        private static int ResolveSkillLevel(int level, MonsterSkillTier tier)
        {
            var baseLevel = 1 + Math.Max(0, (level - 5) / 2);
            if (tier == MonsterSkillTier.Advanced)
            {
                baseLevel += 1;
            }

            return Math.Clamp(baseLevel, 1, 6);
        }

        private static int ResolveManaCost(int skillLevel, MonsterSkillTier tier) =>
            5 + skillLevel + (tier == MonsterSkillTier.Advanced ? 2 : 0);

        private static string ResolveAiProfileId(MonsterBattleRuleSpec spec)
        {
            if (spec.SkillTier == MonsterSkillTier.None || spec.ThreatTier == MonsterThreatTier.Minor)
            {
                return "beast";
            }

            if (spec.ThreatTier == MonsterThreatTier.Elite || spec.Role == MonsterCombatRole.Skirmisher)
            {
                return "tactician";
            }

            if (spec.Role == MonsterCombatRole.Mystic)
            {
                return "move_first";
            }

            return "standard";
        }
    }
}
