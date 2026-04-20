using System.Threading.Tasks;
using Dapper;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public class PlayerRepository : IPlayerRepository
    {
        private readonly IDbConnectionFactory _connectionFactory;

        public PlayerRepository(IDbConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<Player?> GetByUsernameAsync(string username)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = "SELECT * FROM Players WHERE Username = @Username";
            return await connection.QueryFirstOrDefaultAsync<Player>(sql, new { Username = username });
        }

        public async Task<int> CreateAsync(Player player)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = @"
                INSERT INTO Players (
                    Username, Level, Gold, Exp, CurrentMap, CurrentRoom,
                    Hp, MaxHp, Mp, MaxMp, Power, MaxPower,
                    CuongLuc, ThanPhap, NoiLuc, TheLuc, FreePoints,
                    Gender, Element, FaceStyle, HairStyle, HairColor, SkinColor,
                    CreatedAt, LastSeenAt
                )
                VALUES (
                    @Username, @Level, @Gold, @Exp, @CurrentMap, @CurrentRoom,
                    @Hp, @MaxHp, @Mp, @MaxMp, @Power, @MaxPower,
                    @CuongLuc, @ThanPhap, @NoiLuc, @TheLuc, @FreePoints,
                    @Gender, @Element, @FaceStyle, @HairStyle, @HairColor, @SkinColor,
                    @CreatedAt, @LastSeenAt
                )
                RETURNING Id";
            return await connection.ExecuteScalarAsync<int>(sql, player);
        }

        public async Task UpdateAsync(Player player)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = @"
                UPDATE Players
                SET Level      = @Level,
                    Gold       = @Gold,
                    Exp        = @Exp,
                    CurrentMap = @CurrentMap,
                    CurrentRoom= @CurrentRoom,
                    Hp = @Hp, MaxHp = @MaxHp,
                    Mp = @Mp, MaxMp = @MaxMp,
                    Power = @Power, MaxPower = @MaxPower,
                    CuongLuc   = @CuongLuc,
                    ThanPhap   = @ThanPhap,
                    NoiLuc     = @NoiLuc,
                    TheLuc     = @TheLuc,
                    FreePoints = @FreePoints,
                    Gender     = @Gender,
                    Element    = @Element,
                    FaceStyle  = @FaceStyle,
                    HairStyle  = @HairStyle,
                    HairColor  = @HairColor,
                    SkinColor  = @SkinColor,
                    LastSeenAt = @LastSeenAt
                WHERE Id = @Id";
            await connection.ExecuteAsync(sql, player);
        }
    }
}
