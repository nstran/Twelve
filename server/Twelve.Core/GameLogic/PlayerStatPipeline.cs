using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Entities;

namespace Twelve.Core.GameLogic
{
    public sealed record PlayerStatModifier(
        int CuongLuc = 0,
        int ThanPhap = 0,
        int NoiLuc = 0,
        int TheLuc = 0,
        int FlatAttack = 0,
        int AttackPercent = 0,
        int Crit = 0,
        int Defense = 0,
        int Dodge = 0,
        int MaxHp = 0);

    /// <summary>
    /// Ports the Java bridge around com.mg.sq.a.a(lh): base + bonus + equipment
    /// modifiers first, jq/js/jr calculator second, then derived snapshot fields.
    /// </summary>
    public static class PlayerStatPipeline
    {
        public static PlayerDerivedStats Calculate(
            Player player,
            IEnumerable<PlayerStatModifier>? equipmentModifiers = null)
        {
            var totalModifier = SumModifiers(equipmentModifiers);

            int cuongLuc = player.CuongLuc + player.BonusCuongLuc + totalModifier.CuongLuc;
            int thanPhap = player.ThanPhap + player.BonusThanPhap + totalModifier.ThanPhap;
            int noiLuc = player.NoiLuc + player.BonusNoiLuc + totalModifier.NoiLuc;
            int theLuc = player.TheLuc + player.BonusTheLuc + totalModifier.TheLuc;

            var element = player.Element ?? ElementMapper.StorageHoa;

            // Balance v1.1 — nguồn suy luận:
            // docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md
            // Java client chỉ xác nhận lh.h/i/j/k là 4 stat gốc và jq/js/jr tính derived stat theo hệ.
            // Remake thêm off-element offense soft cap để khuyến khích sáng tạo nhưng không làm loãng 3 hệ:
            // - đúng hệ: dùng TotalStat 100%
            // - sai hệ: 70% trước mốc 120, 35% sau mốc 120
            // Resource/utility vẫn dùng TotalStat đầy đủ.
            int effectiveCuongLuc = element == ElementMapper.StorageHoa
                ? cuongLuc
                : ResolveOffElementOffenseStat(cuongLuc);
            int effectiveThanPhap = element == ElementMapper.StorageLoi
                ? thanPhap
                : ResolveOffElementOffenseStat(thanPhap);
            int effectiveNoiLuc = element == ElementMapper.StorageThuy
                ? noiLuc
                : ResolveOffElementOffenseStat(noiLuc);

            var baseStats = StatCalculator.Calculate(
                element,
                effectiveCuongLuc,
                effectiveThanPhap,
                effectiveNoiLuc,
                theLuc);

            int percentAttack = baseStats.TanCong * totalModifier.AttackPercent / 100;
            int finalAttack = baseStats.TanCong + totalModifier.FlatAttack + percentAttack;
            int maxHp = baseStats.MaxHp + totalModifier.MaxHp;
            int maxMp = CalculateMaxMp(player.Level, noiLuc);
            int maxPower = 100;

            return new PlayerDerivedStats(
                MaxHp: maxHp,
                MaxMp: maxMp,
                MaxPower: maxPower,
                MinDamage: finalAttack,
                MaxDamage: finalAttack,
                Defense: baseStats.PThu + totalModifier.Defense,
                Dodge: baseStats.NeTranh + totalModifier.Dodge,
                Hit: baseStats.ChinhXac,
                Crit: baseStats.ChiMang + totalModifier.Crit);
        }

        public static PlayerDerivedStats RecalculateAndApply(
            Player player,
            IEnumerable<PlayerStatModifier>? equipmentModifiers = null)
        {
            var stats = Calculate(player, equipmentModifiers);
            Apply(player, stats);
            return stats;
        }

        public static void Apply(Player player, PlayerDerivedStats stats)
        {
            player.MaxHp = stats.MaxHp;
            if (player.Hp > player.MaxHp)
            {
                player.Hp = player.MaxHp;
            }

            player.MaxMp = stats.MaxMp;
            if (player.Mp > player.MaxMp)
            {
                player.Mp = player.MaxMp;
            }

            player.MaxPower = stats.MaxPower;
            if (player.Power > player.MaxPower)
            {
                player.Power = player.MaxPower;
            }

            player.DerivedMinDamage = stats.MinDamage;
            player.DerivedMaxDamage = stats.MaxDamage;
            player.DerivedDefense = stats.Defense;
            player.DerivedDodge = stats.Dodge;
            player.DerivedHit = stats.Hit;
            player.DerivedCrit = stats.Crit;
        }

        private static int ResolveOffElementOffenseStat(int totalStat)
        {
            const int softCap = 120;
            int firstPart = System.Math.Min(totalStat, softCap);
            int overflow = System.Math.Max(0, totalStat - softCap);

            return (firstPart * 70 / 100) + (overflow * 35 / 100);
        }

        private static int CalculateMaxMp(int level, int totalNoiLuc)
        {
            // Remake resource rule: Nội Lực là nguồn MP chính, nhưng dùng TotalMagic để đồ/stat
            // Nội Lực luôn có giá trị kể cả với hệ khác.
            return 40 + (level * 6) + (totalNoiLuc * 8);
        }

        private static PlayerStatModifier SumModifiers(IEnumerable<PlayerStatModifier>? modifiers)
        {
            if (modifiers is null)
            {
                return new PlayerStatModifier();
            }

            var list = modifiers as IReadOnlyCollection<PlayerStatModifier> ?? modifiers.ToArray();
            return new PlayerStatModifier(
                CuongLuc: list.Sum(m => m.CuongLuc),
                ThanPhap: list.Sum(m => m.ThanPhap),
                NoiLuc: list.Sum(m => m.NoiLuc),
                TheLuc: list.Sum(m => m.TheLuc),
                FlatAttack: list.Sum(m => m.FlatAttack),
                AttackPercent: list.Sum(m => m.AttackPercent),
                Crit: list.Sum(m => m.Crit),
                Defense: list.Sum(m => m.Defense),
                Dodge: list.Sum(m => m.Dodge),
                MaxHp: list.Sum(m => m.MaxHp));
        }
    }

    public sealed record PlayerDerivedStats(
        int MaxHp,
        int MaxMp,
        int MaxPower,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int Dodge,
        int Hit,
        int Crit);
}

