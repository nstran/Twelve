using System;
using System.Collections.Generic;
using Twelve.Core.Battle;
using Twelve.Core.Entities;
using Twelve.Core.Monsters;

namespace Twelve.Application.Battle
{
    public static class PlayerBattleStateFactory
    {
        public static BattleSessionCombatantState Create(
            PlayerAggregate aggregate,
            BattleSide side,
            int iqValue = 0,
            string? aiProfileId = null)
        {
            var player = aggregate.Core;
            var stats = aggregate.Stats;
            var maxHp = Math.Max(1, player.MaxHp);
            var currentHp = Math.Clamp(player.Hp <= 0 ? maxHp : player.Hp, 1, maxHp);
            var maxMp = Math.Max(0, player.MaxMp);
            var currentMp = Math.Clamp(player.Mp, 0, maxMp);
            const int maxPower = 100;
            var currentPower = Math.Clamp(player.Power, 0, maxPower);
            var minDamage = Math.Max(0, stats.MinDamage);
            var maxDamage = Math.Max(minDamage, stats.MaxDamage);
            var totalStrength = stats.CuongLuc + stats.BonusCuongLuc;
            var totalMagic = stats.NoiLuc + stats.BonusNoiLuc;

            return new BattleSessionCombatantState(
                CombatantId: $"player:{player.Id}",
                DisplayName: player.Username,
                Side: side,
                CurrentHp: currentHp,
                MaxHp: maxHp,
                // Source: Java lh.u/t and lh.w/v are authoritative current/max MP and Power bars.
                // Battle session must consume server PlayerStatPipeline/runtime values, not FE hardcoded zeros.
                CurrentMp: currentMp,
                MaxMp: maxMp,
                CurrentPower: currentPower,
                MaxPower: maxPower,
                Strength: totalStrength,
                Agility: stats.ThanPhap + stats.BonusThanPhap,
                Magic: totalMagic,
                Vitality: stats.TheLuc + stats.BonusTheLuc,
                MinDamage: minDamage,
                MaxDamage: maxDamage,
                Defense: stats.Defense,
                HitRate: stats.Hit,
                DodgeRate: stats.Dodge,
                ElementCode: Math.Max(0, player.Element ?? 0),
                // stats.Crit = Java lh.C = Chí Mạng % = crit RATE, not multiplier (see BattleSessionContracts).
                CriticalRate: stats.Crit,
                Skills: CreateSessionSkills(aggregate.Skills),
                Level: player.Level,
                IqValue: iqValue,
                AiProfileId: aiProfileId,
                HealGainPercent: ComputeStrengthResourceGainPercent(totalStrength),
                ManaGainPercent: ComputeMagicResourceGainPercent(totalMagic),
                PowerGainPercent: ComputeStrengthResourceGainPercent(totalStrength));
        }

        public static BattleSessionCombatantState CreateDefault() =>
            new(
                CombatantId: "player:self",
                DisplayName: "Player",
                Side: BattleSide.Player,
                CurrentHp: 100,
                MaxHp: 100,
                CurrentMp: 30,
                MaxMp: 100,
                CurrentPower: 40,
                MaxPower: 100,
                Strength: 12,
                Agility: 10,
                Magic: 8,
                Vitality: 10,
                MinDamage: 10,
                MaxDamage: 16,
                Defense: 5,
                HitRate: 85,
                DodgeRate: 5,
                ElementCode: 0,
                CriticalRate: 5,
                Skills: [],
                Level: 10,
                IqValue: 0,
                AiProfileId: null,
                HealGainPercent: ComputeStrengthResourceGainPercent(12),
                ManaGainPercent: ComputeMagicResourceGainPercent(8),
                PowerGainPercent: ComputeStrengthResourceGainPercent(12));

        public static BattleCombatantSnapshot CreateSnapshot(BattleSessionCombatantState state) =>
            new(
                CombatantId: state.CombatantId,
                DisplayName: state.DisplayName,
                Level: state.Level,
                CurrentHp: state.CurrentHp,
                MaxHp: state.MaxHp,
                CurrentMp: state.CurrentMp,
                MaxMp: state.MaxMp,
                CurrentPower: state.CurrentPower,
                MaxPower: state.MaxPower,
                Strength: state.Strength,
                Agility: state.Agility,
                Magic: state.Magic,
                Vitality: state.Vitality,
                MinDamage: state.MinDamage,
                MaxDamage: state.MaxDamage,
                Defense: state.Defense,
                HitRate: state.HitRate,
                DodgeRate: state.DodgeRate,
                CriticalRate: state.CriticalRate,
                Skills: CreateSkillInstances(state.Skills),
                HealGainPercent: state.HealGainPercent,
                ManaGainPercent: state.ManaGainPercent,
                PowerGainPercent: state.PowerGainPercent);

        public static IReadOnlyList<MonsterSkillInstance> CreateSkillInstances(
            IReadOnlyList<BattleSessionSkillInstance> skills)
        {
            if (skills.Count == 0)
            {
                return [];
            }

            var instances = new List<MonsterSkillInstance>(skills.Count);
            foreach (var skill in skills)
            {
                instances.Add(new MonsterSkillInstance(
                    SkillId: skill.SkillId,
                    Level: skill.Level,
                    ManaCost: skill.ManaCost));
            }

            return instances;
        }

        // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
        // Java client proves HP/MP/Power bars (`lh.u/t`, `lh.w/v`, HUD `mx`) but not the old server resource formula.
        // Remake rule v1 keeps Java-like integer math and moves resource scaling to server authority instead of FE.
        // Balance (W) reduced scale 3→1 %/point and cap 180→140 to prevent gem resource pacing too fast at level 1.
        private static int ComputeStrengthResourceGainPercent(int totalStrength) =>
            Math.Clamp(100 + ((totalStrength - 10) * 1), 80, 140);

        private static int ComputeMagicResourceGainPercent(int totalMagic) =>
            Math.Clamp(100 + ((totalMagic - 10) * 1), 80, 140);

        private static IReadOnlyList<BattleSessionSkillInstance> CreateSessionSkills(
            IReadOnlyList<PlayerSkillEntry> skills)
        {
            if (skills.Count == 0)
            {
                return [];
            }

            var instances = new List<BattleSessionSkillInstance>(skills.Count);
            foreach (var skill in skills)
            {
                instances.Add(new BattleSessionSkillInstance(
                    SkillId: skill.SkillId,
                    Level: Math.Max(1, skill.Level),
                    ManaCost: 0));
            }

            return instances;
        }
    }
}
