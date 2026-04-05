using Microsoft.Extensions.DependencyInjection;
using Twelve.Application.Handlers;

namespace Twelve.Application
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddTwelveApplication(this IServiceCollection services)
        {
            // ── Đăng ký Handlers ───────────────────────────────────────────────
            services.AddSingleton<AuthHandler>();
            services.AddSingleton<RegisterHandler>();
            services.AddSingleton<MapHandler>();
            services.AddSingleton<MoveHandler>();

            // ── Wiring PacketDispatcher ────────────────────────────────────────
            // CMD numbers (client → server):
            //   1  = Đăng ký (Register)
            //   2  = Đăng nhập (Login)
            //   11 = Yêu cầu Map Info
            //   44 = Di chuyển (Move)
            services.AddSingleton<PacketDispatcher>(sp =>
            {
                var dispatcher = new PacketDispatcher();

                dispatcher.RegisterHandler(1,  sp.GetRequiredService<RegisterHandler>()); // CMD 1 = Register
                dispatcher.RegisterHandler(2,  sp.GetRequiredService<AuthHandler>());     // CMD 2 = Login
                dispatcher.RegisterHandler(11, sp.GetRequiredService<MapHandler>());      // CMD 11 = Map Info
                dispatcher.RegisterHandler(13, sp.GetRequiredService<MapHandler>());      // CMD 13 = Map Select
                dispatcher.RegisterHandler(29, sp.GetRequiredService<MapHandler>());      // CMD 29 = Map Join
                dispatcher.RegisterHandler(44, sp.GetRequiredService<MoveHandler>());     // CMD 44 = Move

                return dispatcher;
            });

            return services;
        }
    }
}
