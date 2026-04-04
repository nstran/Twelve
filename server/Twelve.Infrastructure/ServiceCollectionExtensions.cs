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
            services.AddScoped<IPlayerRepository, PlayerRepository>();
            
            return services;
        }
    }
}
