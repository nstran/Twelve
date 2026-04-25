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

            // Java-faithful status/derived stats — nguồn:
            // - reference/redecoded/cfr_fresh/com/mg/sq/a.java:1891-1928
            // - reference/redecoded/cfr_fresh/jq.java/js.java/jr.java
            //
            // Không áp off-element soft cap tại tầng status. Java tính lh.r/x/y/z/A/B/C trực tiếp
            // từ tổng stat thật (base + bonus + equipment). Nếu remake cần cân bằng hybrid/off-element,
            // chỉ áp riêng tại tầng battle skill damage, không làm sai số hiển thị nhân vật.
            var baseStats = StatCalculator.Calculate(
                element,
                cuongLuc,
                thanPhap,
                noiLuc,
                theLuc);

            int minPercentAttack = baseStats.MinDamage * totalModifier.AttackPercent / 100;
            int maxPercentAttack = baseStats.MaxDamage * totalModifier.AttackPercent / 100;
            int finalMinAttack = baseStats.MinDamage + totalModifier.FlatAttack + minPercentAttack;
            int finalMaxAttack = baseStats.MaxDamage + totalModifier.FlatAttack + maxPercentAttack;
            int maxHp = baseStats.MaxHp + totalModifier.MaxHp;
            int maxMp = CalculateMaxMp(player.Level, noiLuc);
            int maxPower = 100;

            return new PlayerDerivedStats(
                MaxHp: maxHp,
                MaxMp: maxMp,
                MaxPower: maxPower,
                MinDamage: finalMinAttack,
                MaxDamage: System.Math.Max(finalMinAttack, finalMaxAttack),
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

