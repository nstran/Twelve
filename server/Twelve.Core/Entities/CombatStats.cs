namespace Twelve.Core.Entities
{
    /// <summary>
    /// Combat stats computed from the 4 base stats via jq/js/jr formulas.
    /// Ref: combat-formulas.md § 4
    /// These are NOT stored in DB — computed on demand from base stats.
    /// </summary>
    public record CombatStats(
        int MaxHp,      // Sinh Lực — jz.a()
        int TanCong,    // Tấn Công — jz.b()
        int ChinhXac,   // Chính Xác — jz.f()
        int PThu,       // P.Thủ — jz.d()
        int NeTranh,    // Né Tránh — jz.e()
        int ChiMang     // Chí Mạng % — jz.g(), capped at 30
    );
}
