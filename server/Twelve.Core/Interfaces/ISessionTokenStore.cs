using System;
using System.Threading.Tasks;

namespace Twelve.Core.Interfaces
{
    /// <summary>
    /// Lưu trữ session token sau khi đăng nhập thành công.
    /// Token hết hạn sau 24 giờ.
    /// </summary>
    public interface ISessionTokenStore
    {
        /// <summary>Tạo token mới cho username, lưu vào store, trả về token.</summary>
        Task<string> CreateTokenAsync(string username);

        /// <summary>Validate token. Trả về username nếu hợp lệ, null nếu hết hạn/không tồn tại.</summary>
        Task<string?> ValidateTokenAsync(string token);

        /// <summary>Xoá token (khi logout hoặc đăng nhập lại từ thiết bị khác).</summary>
        Task RevokeTokenAsync(string token);
    }
}
