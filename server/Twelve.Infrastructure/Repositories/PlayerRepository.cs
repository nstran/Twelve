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
                    Username, Level, Gold, Exp, ExpFloor, ExpCeiling, QuanProgress, QuanProgressCap,
                    CurrentMap, CurrentRoom,
                    Hp, MaxHp, Mp, MaxMp, Power, MaxPower,
                    CuongLuc, ThanPhap, NoiLuc, TheLuc, FreePoints,
                    BonusCuongLuc, BonusThanPhap, BonusNoiLuc, BonusTheLuc,
                    SkillPoints, Honor,
                    DerivedMinDamage, DerivedMaxDamage, DerivedDefense, DerivedDodge, DerivedHit, DerivedCrit,
                    Gender, Element, RawElementCode, FaceStyle, HairStyle, HairColor, SkinColor,
                    AppearanceHidden0, AppearanceHidden1, SpecialActorForm, AppearanceJson,
                    TitleMain, TitleSub, TitleRank,
                    CreatedAt, LastSeenAt
                )
                VALUES (
                    @Username, @Level, @Gold, @Exp, @ExpFloor, @ExpCeiling, @QuanProgress, @QuanProgressCap,
                    @CurrentMap, @CurrentRoom,
                    @Hp, @MaxHp, @Mp, @MaxMp, @Power, @MaxPower,
                    @CuongLuc, @ThanPhap, @NoiLuc, @TheLuc, @FreePoints,
                    @BonusCuongLuc, @BonusThanPhap, @BonusNoiLuc, @BonusTheLuc,
                    @SkillPoints, @Honor,
                    @DerivedMinDamage, @DerivedMaxDamage, @DerivedDefense, @DerivedDodge, @DerivedHit, @DerivedCrit,
                    @Gender, @Element, @RawElementCode, @FaceStyle, @HairStyle, @HairColor, @SkinColor,
                    @AppearanceHidden0, @AppearanceHidden1, @SpecialActorForm, CAST(@AppearanceJson AS jsonb),
                    @TitleMain, @TitleSub, @TitleRank,
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
                    ExpFloor   = @ExpFloor,
                    ExpCeiling = @ExpCeiling,
                    QuanProgress = @QuanProgress,
                    QuanProgressCap = @QuanProgressCap,
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
                    BonusCuongLuc = @BonusCuongLuc,
                    BonusThanPhap = @BonusThanPhap,
                    BonusNoiLuc = @BonusNoiLuc,
                    BonusTheLuc = @BonusTheLuc,
                    SkillPoints = @SkillPoints,
                    Honor = @Honor,
                    DerivedMinDamage = @DerivedMinDamage,
                    DerivedMaxDamage = @DerivedMaxDamage,
                    DerivedDefense = @DerivedDefense,
                    DerivedDodge = @DerivedDodge,
                    DerivedHit = @DerivedHit,
                    DerivedCrit = @DerivedCrit,
                    Gender     = @Gender,
                    Element    = @Element,
                    RawElementCode = @RawElementCode,
                    FaceStyle  = @FaceStyle,
                    HairStyle  = @HairStyle,
                    HairColor  = @HairColor,
                    SkinColor  = @SkinColor,
                    AppearanceHidden0 = @AppearanceHidden0,
                    AppearanceHidden1 = @AppearanceHidden1,
                    SpecialActorForm = @SpecialActorForm,
                    AppearanceJson = CAST(@AppearanceJson AS jsonb),
                    TitleMain = @TitleMain,
                    TitleSub = @TitleSub,
                    TitleRank = @TitleRank,
                    LastSeenAt = @LastSeenAt
                WHERE Id = @Id";
            await connection.ExecuteAsync(sql, player);
        }
    }
}
