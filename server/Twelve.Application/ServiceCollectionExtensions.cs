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
            services.AddSingleton<RegisterHandler>(); // NEW
            services.AddSingleton<MapHandler>();
            services.AddSingleton<MoveHandler>();

            // Register PacketDispatcher as a singleton using a factory to resolve handlers
            services.AddSingleton<PacketDispatcher>(sp => 
            {
                var dispatcher = new PacketDispatcher();
                
                // Resolve handlers from DI container
                var authHandler = sp.GetRequiredService<AuthHandler>();
                var registerHandler = sp.GetRequiredService<RegisterHandler>(); // NEW
                var mapHandler = sp.GetRequiredService<MapHandler>();
                var moveHandler = sp.GetRequiredService<MoveHandler>();

                dispatcher.RegisterHandler(4, authHandler);   // Login
                dispatcher.RegisterHandler(131, registerHandler); // Register
                dispatcher.RegisterHandler(11, mapHandler);   // Map Info
                dispatcher.RegisterHandler(13, mapHandler);   // Map Select
                dispatcher.RegisterHandler(29, mapHandler);   // Map Join
                dispatcher.RegisterHandler(44, moveHandler);  // Move (Action)

                return dispatcher;
            });
            
            return services;
        }
    }
}
