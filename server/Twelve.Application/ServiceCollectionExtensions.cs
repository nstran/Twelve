using Microsoft.Extensions.DependencyInjection;
using Twelve.Application.Handlers;

namespace Twelve.Application
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddTwelveApplication(this IServiceCollection services)
        {
            // Register Handlers as services to support DI
            services.AddSingleton<AuthHandler>();
            services.AddSingleton<MapHandler>();

            // Register PacketDispatcher as a singleton using a factory to resolve handlers
            services.AddSingleton<PacketDispatcher>(sp => 
            {
                var dispatcher = new PacketDispatcher();
                
                // Resolve handlers from DI container (supports PlayerRepository injection)
                var authHandler = sp.GetRequiredService<AuthHandler>();
                var mapHandler = sp.GetRequiredService<MapHandler>();

                dispatcher.RegisterHandler(4, authHandler); // Login
                dispatcher.RegisterHandler(11, mapHandler); // Map Info
                dispatcher.RegisterHandler(13, mapHandler); // Map Select
                dispatcher.RegisterHandler(29, mapHandler); // Map Join

                return dispatcher;
            });
            
            return services;
        }
    }
}
