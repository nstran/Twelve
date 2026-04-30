namespace Twelve.Core.GameLogic
{
    /// <summary>
    /// Tính toán chỉ số di chuyển ngoài map từ Level.
    ///
    /// Nguồn gốc Java:
    ///   kl.java — kl.b(lh):
    ///     this.i = 4 + lh2.G / 10;   // tốc độ di chuyển (int div), cap 9
    ///     if (this.i > 9) this.i = 9;
    ///     this.a = 11 + lh2.G / 10;  // timer animation tấn công, cap 16
    ///     if (this.a > 16) this.a = 16;
    ///
    ///   Java chỉ dùng Level (G) cho speed actor ngoài map; không có bằng chứng
    ///   Thân Pháp ảnh hưởng movement runtime map trong kl/km đã rà.
    ///
    /// Công thức MapMoveSpeed (client px/frame unit, ~60fps reference):
    ///   Java basis : kl.i = 4 + level/10, range [4..9] (int div)
    ///   Dùng trực tiếp kl.i làm px/frame trên runtime map remake.
    ///   Lý do: km.java state 1/5/6 gọi b(kl.b[k] * i, kl.c[k] * i)
    ///   và khi đang nhảy vẫn cộng ngang theo i nếu phím trái/phải đang giữ.
    ///   Không scale xuống vì sẽ làm nhân vật chạy/chuyển hướng trên không quá chậm.
    ///
    /// Công thức LevelAttackTimer (tham chiếu, không dùng cho movement):
    ///   Java basis: kl.a = 11 + level/10, range [11..16] (int div)
    ///   Giữ để tham chiếu khi cần scale attack animation frame timing.
    /// </summary>
    public static class MapMovementCalculator
    {
        private const float BaseMoveSpeed = 4.0f;   // Java kl.i tại level 0..9

        /// <summary>
        /// Tính MapMovementStats từ level hiện tại của player.
        /// Gọi mỗi khi cần build PlayerRuntimeSnapshot (sau phân điểm, equip, lên cấp).
        /// </summary>
        /// <param name="level">Level nhân vật (lh.G)</param>
        public static MapMovementStats Calculate(int level)
        {
            // MoveSpeed: Java kl.i = 4 + level/10 (int div, cap 9).
            // PHẢI giữ integer division giống Java, không nội suy mượt theo level.
            // km.java dùng trực tiếp `i` cho chạy và điều hướng ngang khi nhảy:
            //   state 1: b(kl.b[k] * i, kl.c[k] * i)
            //   state 5/6: nếu trái/phải đang giữ thì k2.a += i * kl.b[4/8]
            var javaSpeed = System.Math.Min(9, 4 + level / 10);
            var moveSpeed = System.MathF.Max(BaseMoveSpeed, javaSpeed);

            // LevelAttackTimer: tham chiếu Java kl.a = 11 + level/10, cap 16
            var attackTimer = System.Math.Min(16, 11 + level / 10);

            return new MapMovementStats(
                MoveSpeed: moveSpeed,
                AttackTimer: attackTimer);
        }
    }

    /// <summary>
    /// Kết quả tính toán chỉ số di chuyển ngoài map.
    /// Nguồn: MapMovementCalculator, bám Java kl.b(lh).
    /// </summary>
    /// <param name="MoveSpeed">
    ///   Tốc độ di chuyển ngang (px/frame tại 60fps reference).
    ///   Range: [4.0 .. 9.0]. Client dùng làm prop `speed` của CharacterController.
    /// </param>
    /// <param name="AttackTimer">
    ///   Timer frame hoạt hình tấn công (tham chiếu Java kl.a).
    ///   Range: [11 .. 16]. Client có thể dùng để scale attack animation timing.
    /// </param>
    public sealed record MapMovementStats(
        float MoveSpeed,
        int AttackTimer
    );
}