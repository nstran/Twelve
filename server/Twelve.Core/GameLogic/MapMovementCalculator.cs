namespace Twelve.Core.GameLogic
{
    /// <summary>
    /// Tính toán chỉ số di chuyển ngoài map từ Level và Thân Pháp.
    ///
    /// Nguồn gốc Java:
    ///   kl.java — kl.b(lh):
    ///     this.i = 4 + lh2.G / 10;   // tốc độ di chuyển (int div), cap 9
    ///     if (this.i > 9) this.i = 9;
    ///     this.a = 11 + lh2.G / 10;  // timer animation tấn công, cap 16
    ///     if (this.a > 16) this.a = 16;
    ///
    ///   Java chỉ dùng Level (G) cho speed actor ngoài map.
    ///   Map train trong remake là map mới (không có map gốc để mirror), nên
    ///   Thân Pháp được thêm vào như một phần thiết kế: stat "Thân Pháp" (Agility)
    ///   mang nghĩa "thân pháp" = body technique → hợp lý ảnh hưởng tốc độ/nhảy.
    ///
    /// Công thức MapMoveSpeed (client px/frame unit, ~60fps reference):
    ///   Java basis : kl.i = 4 + level/10, range [4..9] (int div)
    ///   Scale sang client: (kl.i - 4) * 0.30 + 1.0
    ///     → level 0   = 1.0, level 10 = 1.3, level 50 = cap ≈ 2.5
    ///   ThanPhap bonus: + thanPhap * 0.008
    ///     → ThanPhap 10 = +0.08, ThanPhap 50 = +0.40
    ///   Total cap: 2.8 (tương đương kl.i cap = 9 cộng ThanPhap bonus tối đa)
    ///
    /// Công thức MapJumpSpeed (initial upward speed, client px/frame unit):
    ///   Base = 6.4 (JUMP_INITIAL_SPEED từ CharacterController.tsx, Java-faithful)
    ///   ThanPhap bonus: + max(0, thanPhap - 10) * 0.06
    ///     → ThanPhap 10 = +0.0, ThanPhap 50 = +2.4, ThanPhap 100 = +5.4
    ///   Cap: 12.0
    ///
    /// Công thức LevelAttackTimer (tham chiếu, không dùng cho movement):
    ///   Java basis: kl.a = 11 + level/10, range [11..16] (int div)
    ///   Giữ để tham chiếu khi cần scale attack animation frame timing.
    /// </summary>
    public static class MapMovementCalculator
    {
        // Client reference constants (phải khớp CharacterController.tsx)
        private const float BaseJumpSpeed = 6.4f;   // JUMP_INITIAL_SPEED
        private const float MaxJumpSpeed  = 12.0f;

        private const float BaseMoveSpeed = 1.0f;   // điểm khởi đầu level 0
        private const float MaxMoveSpeed  = 2.8f;

        // Hệ số scale từ Java J2ME unit (kl.i range 4→9) sang client px/frame
        // (9 - 4) Java units → (2.5 - 1.0) client units → 0.30 per Java unit
        private const float JavaSpeedUnitFactor = 0.30f;
        private const float AgiSpeedFactor      = 0.008f; // bonus ThanPhap per điểm

        // ThanPhap bonus jump: +0.06 per điểm vượt 10
        private const float AgiJumpFactor  = 0.06f;
        private const int   AgiJumpBaseline = 10;       // ThanPhap mặc định lv1

        /// <summary>
        /// Tính MapMovementStats từ level và Thân Pháp hiện tại của player.
        /// Gọi mỗi khi cần build PlayerRuntimeSnapshot (sau phân điểm, equip, lên cấp).
        /// </summary>
        /// <param name="level">Level nhân vật (lh.G)</param>
        /// <param name="thanPhap">Thân Pháp (lh.j) sau khi cộng bonus equip</param>
        public static MapMovementStats Calculate(int level, int thanPhap)
        {
            // MoveSpeed: Java kl.i = 4 + level/10 (int div, cap 9)
            // PHẢI giữ integer division giống Java, không nội suy mượt theo level.
            var javaSpeed = System.Math.Min(9, 4 + level / 10);
            var moveSpeed = System.MathF.Min(MaxMoveSpeed,
                BaseMoveSpeed + (javaSpeed - 4) * JavaSpeedUnitFactor + thanPhap * AgiSpeedFactor);
            moveSpeed = System.MathF.Max(BaseMoveSpeed, moveSpeed);

            // JumpSpeed: base 6.4 + ThanPhap bonus per điểm vượt baseline 10
            var agiBonus = System.MathF.Max(0f, thanPhap - AgiJumpBaseline) * AgiJumpFactor;
            var jumpSpeed = System.MathF.Min(MaxJumpSpeed, BaseJumpSpeed + agiBonus);

            // LevelAttackTimer: tham chiếu Java kl.a = 11 + level/10, cap 16
            var attackTimer = System.Math.Min(16, 11 + level / 10);

            return new MapMovementStats(
                MoveSpeed:   moveSpeed,
                JumpSpeed:   jumpSpeed,
                AttackTimer: attackTimer);
        }

        /// <summary>
        /// Convenience: tính từ level và (ThanPhap base + BonusThanPhap từ equip).
        /// </summary>
        public static MapMovementStats Calculate(int level, int thanPhapBase, int bonusThanPhap)
            => Calculate(level, thanPhapBase + bonusThanPhap);
    }

    /// <summary>
    /// Kết quả tính toán chỉ số di chuyển ngoài map.
    /// Nguồn: MapMovementCalculator, bám Java kl.b(lh).
    /// </summary>
    /// <param name="MoveSpeed">
    ///   Tốc độ di chuyển ngang (px/frame tại 60fps reference).
    ///   Range: [1.0 .. 2.8]. Client dùng làm prop `speed` của CharacterController.
    /// </param>
    /// <param name="JumpSpeed">
    ///   Tốc độ bật nhảy ban đầu (px/frame, hướng lên âm).
    ///   Range: [6.4 .. 12.0]. Client dùng làm prop `jumpSpeed` của CharacterController.
    /// </param>
    /// <param name="AttackTimer">
    ///   Timer frame hoạt hình tấn công (tham chiếu Java kl.a).
    ///   Range: [11 .. 16]. Client có thể dùng để scale attack animation timing.
    /// </param>
    public sealed record MapMovementStats(
        float MoveSpeed,
        float JumpSpeed,
        int   AttackTimer
    );
}