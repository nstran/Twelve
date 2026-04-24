using Twelve.Core.Entities;

namespace Twelve.Core.GameLogic
{
    /// <summary>
    /// Tính combat stats từ 4 base stats theo công thức Java jq/js/jr.
    /// Ref: combat-formulas.md § 4
    ///
    /// All integer division — truncate (not round), matching Java behavior.
    /// Element 0 = Hỏa (jq), 1 = Lôi (js), 2 = Thủy (jr)
    /// </summary>
    public static class StatCalculator
    {
        private const int ChiMangCap = 30; // max crit % từ jq/js/jr source

        /// <summary>
        /// Compute combat stats from player's current base stats + element.
        /// Does NOT mutate the player object.
        /// </summary>
        public static CombatStats Calculate(Player player)
        {
            return Calculate(
                player.Element ?? ElementMapper.StorageHoa,
                player.CuongLuc,
                player.ThanPhap,
                player.NoiLuc,
                player.TheLuc);
        }

        public static CombatStats Calculate(int storageElement, int str, int agi, int mag, int vit)
        {
            return storageElement switch
            {
                0 => CalculateHoa(str, agi, mag, vit),
                1 => CalculateLoi(str, agi, mag, vit),
                2 => CalculateThuy(str, agi, mag, vit),
                _  => CalculateHoa(str, agi, mag, vit) // fallback
            };
        }

        /// <summary>
        /// Compute combat stats then update player.MaxHp (and clamp player.Hp).
        /// Call this after any base stat change.
        /// </summary>
        public static CombatStats RecalculateAndApply(Player player)
        {
            var stats = Calculate(player);
            player.MaxHp = stats.MaxHp;
            if (player.Hp > player.MaxHp)
                player.Hp = player.MaxHp;
            return stats;
        }

        // ── jq.java — Hỏa (g=1, primary = Cường Lực) ──────────────────────
        // MaxHp    = vit × 6
        // TanCong  = str                              (raw)
        // ChinhXac = agi × 3
        // PThu     = agi / 2                          (int div)
        // NeTranh  = agi × 2                          (agi << 1)
        // ChiMang  = min(5 + agi / 8, 30)             (int div, capped)
        private static CombatStats CalculateHoa(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:    vit * 6,
                TanCong:  str,
                ChinhXac: agi * 3,
                PThu:     agi / 2,
                NeTranh:  agi * 2,
                ChiMang:  System.Math.Min(5 + agi / 8, ChiMangCap)
            );

        // ── js.java — Lôi (g=2, primary = Thân Pháp) ──────────────────────
        // MaxHp    = vit × 4                          (vit << 2)
        // TanCong  = (agi × 80 + str × 16) / 100
        // ChinhXac = agi × 3
        // PThu     = agi / 2                          (int div)
        // NeTranh  = agi × 15 / 10                    (int div)
        // ChiMang  = min(5 + agi / 8, 30)
        private static CombatStats CalculateLoi(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:    vit * 4,
                TanCong:  (agi * 80 + str * 16) / 100,
                ChinhXac: agi * 3,
                PThu:     agi / 2,
                NeTranh:  agi * 15 / 10,
                ChiMang:  System.Math.Min(5 + agi / 8, ChiMangCap)
            );

        // ── jr.java — Thủy (g=4, primary = Nội Lực) ───────────────────────
        // MaxHp    = vit × 5
        // TanCong  = mag × 130 / 100                  (= mag × 1.3, int div)
        // ChinhXac = agi × 2                          (agi << 1)
        // PThu     = agi / 2                          (int div)
        // NeTranh  = agi × 3
        // ChiMang  = min(5 + agi / 8, 30)
        private static CombatStats CalculateThuy(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:    vit * 5,
                TanCong:  mag * 130 / 100,
                ChinhXac: agi * 2,
                PThu:     agi / 2,
                NeTranh:  agi * 3,
                ChiMang:  System.Math.Min(5 + agi / 8, ChiMangCap)
            );
    }
}
