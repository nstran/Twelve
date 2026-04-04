using System.Threading.Tasks;

namespace Twelve.Core.Interfaces
{
    public interface IPaymentProvider
    {
        string ProviderName { get; }
        
        /// <summary>
        /// Tạo URL thanh toán để gửi cho người chơi (VNPay/Momo)
        /// </summary>
        Task<string> CreatePaymentUrlAsync(long amount, string orderInfo, string returnUrl);
        
        /// <summary>
        /// Xác thực kết quả thanh toán từ cổng (Verification)
        /// </summary>
        Task<bool> VerifyPaymentAsync(System.Collections.Generic.IDictionary<string, string> responseParams);
        
        /// <summary>
        /// Kiểm tra trạng thái giao dịch
        /// </summary>
        Task<string> GetPaymentStatusAsync(string transactionId);
    }
}
