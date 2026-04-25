namespace Twelve.Core.Entities
{
    /// <summary>
    /// Combat stats computed from the 4 base stats via jq/js/jr formulas.
    /// Ref: combat-formulas.md § 4
    /// These are NOT stored in DB — computed on demand from base stats.
    /// </summary>
    public record CombatStats(
        int MaxHp,       // Sinh Lực — lh.r = jz.a()
        int MinDamage,   // Tấn Công min — lh.x = jz.b()
        int MaxDamage,   // Tấn Công max — lh.y = jz.c()
        int ChinhXac,    // Chính Xác — lh.B = jz.f()
        int PThu,        // P.Thủ — lh.z = jz.d()
        int NeTranh,     // Né Tránh — lh.A = jz.e()
        int ChiMang      // Chí Mạng % — lh.C = jz.g(), capped at 30
    );
}
