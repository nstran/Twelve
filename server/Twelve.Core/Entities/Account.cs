using System;

namespace Twelve.Core.Entities
{
    public class Account
    {
        public int Id { get; set; }

        // ── Xác thực ─────────────────────────────────────────────────────────
        public string Username     { get; set; } = string.Empty;
        public string PasswordHash { get; set; } = string.Empty;
        public string Salt         { get; set; } = string.Empty;

        // ── Thông tin cá nhân (từ màn đăng ký) ───────────────────────────────
        public string FullName    { get; set; } = string.Empty;   // Họ tên        — tag 11
        public string DateOfBirth { get; set; } = string.Empty;   // DD-MM-YYYY    — tag 12
        public string Phone       { get; set; } = string.Empty;   // Số điện thoại — tag 13
        public short  Gender      { get; set; } = 0;              // 0=Nam, 1=Nữ   — tag 14

        // ── Thời gian ─────────────────────────────────────────────────────────
        public DateTime  CreatedAt   { get; set; } = DateTime.UtcNow;
        public DateTime? LastLoginAt { get; set; }
    }
}
