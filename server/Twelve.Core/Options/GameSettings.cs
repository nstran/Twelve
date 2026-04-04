namespace Twelve.Core.Options
{
    public class GameSettings
    {
        public string DbConnection { get; set; } = string.Empty;
        public string JwtSecret { get; set; } = "SecretKey";
        public int MaxPlayers { get; set; } = 1000;
        public int GamePort { get; set; } = 2026;
    }
}
