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
            services.AddSingleton<IAccountRepository, AccountRepository>();
            services.AddSingleton<IPasswordHasher, PasswordHasher>();
            services.AddSingleton<ISessionTokenStore, InMemorySessionTokenStore>();

            // Migration tự động khi khởi động
            services.AddSingleton<DatabaseMigrator>();

            return services;
        }
    }
}
