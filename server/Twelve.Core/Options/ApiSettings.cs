namespace Twelve.Core.Options
{
    public class ApiSettings
    {
        // Notification
        public string FirebaseServerKey { get; set; } = string.Empty;
        
        // Maps/Location
        public string GoogleMapsApiKey { get; set; } = string.Empty;

        // Custom External APIs
        public string DiscordWebhookUrl { get; set; } = string.Empty;
        public string TelegramBotToken { get; set; } = string.Empty;
        public string TelegramChatId { get; set; } = string.Empty;
    }
}
