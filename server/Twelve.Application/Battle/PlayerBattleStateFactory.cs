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
            var maxPower = Math.Max(0, player.MaxPower);
            var minDamage = Math.Max(0, stats.MinDamage);
            var maxDamage = Math.Max(minDamage, stats.MaxDamage);

            return new BattleSessionCombatantState(
                CombatantId: $"player:{player.Id}",
                DisplayName: player.Username,
                Side: side,
                CurrentHp: currentHp,
                MaxHp: maxHp,
                CurrentMp: 0,
                MaxMp: maxMp,
                CurrentPower: 0,
                MaxPower: maxPower,
                Strength: stats.CuongLuc + stats.BonusCuongLuc,
                Agility: stats.ThanPhap + stats.BonusThanPhap,
                Magic: stats.NoiLuc + stats.BonusNoiLuc,
                Vitality: stats.TheLuc + stats.BonusTheLuc,
                MinDamage: minDamage,
                MaxDamage: maxDamage,
                Defense: stats.Defense,
                HitRate: stats.Hit,
                DodgeRate: stats.Dodge,
                CriticalDamage: stats.Crit,
                Skills: CreateSessionSkills(aggregate.Skills),
                Level: player.Level,
                IqValue: iqValue,
                AiProfileId: aiProfileId);
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
                CriticalDamage: 110,
                Skills: [],
                Level: 10,
                IqValue: 0,
                AiProfileId: null);

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
                CriticalDamage: state.CriticalDamage,
                Skills: CreateSkillInstances(state.Skills));

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
