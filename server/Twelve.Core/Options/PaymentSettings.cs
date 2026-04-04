namespace Twelve.Core.Options
{
    public class PaymentSettings
    {
        public bool Enabled { get; set; } = false;
        
        // VNPay
        public string VnPayTmnCode { get; set; } = string.Empty;
        public string VnPayHashSecret { get; set; } = string.Empty;
        public string VnPayUrl { get; set; } = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

        // Momo
        public string MomoPartnerCode { get; set; } = string.Empty;
        public string MomoAccessKey { get; set; } = string.Empty;
        public string MomoSecretKey { get; set; } = string.Empty;
        public string MomoPartnerName { get; set; } = "Twelve Game";
    }
}
