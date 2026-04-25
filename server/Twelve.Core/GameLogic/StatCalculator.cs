using Twelve.Core.Entities;

namespace Twelve.Core.GameLogic
{
    /// <summary>
    /// Tính combat stats từ 4 base stats theo công thức Java jq/js/jr.
    /// Ref:
    /// - reference/redecoded/cfr_fresh/jp.java: jp.a(g) chọn jq/js/jr theo raw element 1/2/4.
    /// - reference/redecoded/cfr_fresh/jq.java, js.java, jr.java: jz.a/b/c/d/e/f/g.
    /// - reference/redecoded/cfr_fresh/com/mg/sq/a.java:1891-1928 map jz vào lh.r/x/y/z/A/B/C.
    ///
    /// All integer division — truncate (not round), matching Java behavior.
    /// Storage element 0 = Hỏa (jq/raw g=1), 1 = Lôi (js/raw g=2), 2 = Thủy (jr/raw g=4)
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
        // jz.a() MaxHp      = vit × 6
        // jz.b() MinDamage  = str
        // jz.c() MaxDamage  = str × 120 / 100
        // jz.d() PThu       = agi / 2
        // jz.e() NeTranh    = agi × 2
        // jz.f() ChinhXac   = agi × 3
        // jz.g() ChiMang    = min(5 + agi / 8, 30)
        private static CombatStats CalculateHoa(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:     vit * 6,
                MinDamage: str,
                MaxDamage: str * 120 / 100,
                ChinhXac:  agi * 3,
                PThu:      agi / 2,
                NeTranh:   agi * 2,
                ChiMang:   System.Math.Min(5 + agi / 8, ChiMangCap)
            );

        // ── js.java — Lôi (g=2, primary = Thân Pháp) ──────────────────────
        // jz.a() MaxHp      = vit × 4
        // jz.b() MinDamage  = (agi × 80 + str × 16) / 100
        // jz.c() MaxDamage  = agi + str / 5
        // jz.d() PThu       = agi / 2
        // jz.e() NeTranh    = agi × 15 / 10
        // jz.f() ChinhXac   = agi × 3
        // jz.g() ChiMang    = min(5 + agi / 8, 30)
        private static CombatStats CalculateLoi(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:     vit * 4,
                MinDamage: (agi * 80 + (str << 4)) / 100,
                MaxDamage: agi + str / 5,
                ChinhXac:  agi * 3,
                PThu:      agi / 2,
                NeTranh:   agi * 15 / 10,
                ChiMang:   System.Math.Min(5 + agi / 8, ChiMangCap)
            );

        // ── jr.java — Thủy (g=4, primary = Nội Lực) ───────────────────────
        // jz.a() MaxHp      = vit × 5
        // jz.b() MinDamage  = mag × 130 / 100
        // jz.c() MaxDamage  = mag × 150 / 100
        // jz.d() PThu       = agi / 2
        // jz.e() NeTranh    = agi × 3
        // jz.f() ChinhXac   = agi × 2
        // jz.g() ChiMang    = min(5 + agi / 8, 30)
        private static CombatStats CalculateThuy(int str, int agi, int mag, int vit) =>
            new(
                MaxHp:     vit * 5,
                MinDamage: mag * 130 / 100,
                MaxDamage: mag * 150 / 100,
                ChinhXac:  agi << 1,
                PThu:      agi / 2,
                NeTranh:   agi * 3,
                ChiMang:   System.Math.Min(5 + agi / 8, ChiMangCap)
            );
    }
}
