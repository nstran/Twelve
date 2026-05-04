using Microsoft.Extensions.DependencyInjection;
using Twelve.Core.Interfaces;
using Twelve.Infrastructure.Data;
using Twelve.Infrastructure.Repositories;

namespace Twelve.Infrastructure
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddTwelveInfrastructure(this IServiceCollection services)
        {
            services.AddSingleton<IDbConnectionFactory, DbConnectionFactory>();
            services.AddSingleton<IPlayerRepository, PlayerRepository>();
            services.AddSingleton<IPlayerAggregateRepository, PlayerAggregateRepository>();
            services.AddSingleton<IEquipmentCatalogRepository, EquipmentCatalogRepository>();
            services.AddSingleton<IAccountRepository, AccountRepository>();
            services.AddSingleton<IPasswordHasher, PasswordHasher>();
            services.AddSingleton<ISessionTokenStore, JwtSessionTokenStore>();

            // Monster catalogs — DB-backed, cached at startup
            services.AddSingleton<IMonsterAssetCatalog, DbMonsterAssetCatalog>();
            services.AddSingleton<IMonsterSpawnCatalog, DbMonsterSpawnCatalog>();
            services.AddSingleton<IMonsterBattleCatalog, DbMonsterBattleCatalog>();
            services.AddSingleton<IMapMonsterRosterService, DbMapMonsterRosterService>();

            services.AddSingleton<IMapNpcRosterService, StaticMapNpcRosterService>();

            // Migration tự động khi khởi động
            services.AddSingleton<DatabaseMigrator>();

            return services;
        }
    }
}
