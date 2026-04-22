using Microsoft.Extensions.DependencyInjection;
using Twelve.Application.Battle;
using Twelve.Application.Handlers;
using Twelve.Core.Interfaces;
using Twelve.Core.Tlv;

namespace Twelve.Application
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddTwelveApplication(this IServiceCollection services)
        {
            services.AddSingleton<IBattleSkillPacketFactory, BattleSkillPacketFactory>();
            services.AddSingleton<IBattleSkillCastPacketService, BattleSkillCastPacketService>();

            // ── Đăng ký Handlers ───────────────────────────────────────────────
            services.AddSingleton<AuthHandler>();
            services.AddSingleton<TokenAuthHandler>();
            services.AddSingleton<RegisterHandler>();
            services.AddSingleton<CreateCharacterHandler>();
            services.AddSingleton<AllocateStatHandler>();
            services.AddSingleton<MapHandler>();
            services.AddSingleton<MoveHandler>();

            // ── Wiring PacketDispatcher ────────────────────────────────────────
            // CMD numbers (client → server):
            //   1  = Đăng ký (Register)
            //   2  = Đăng nhập (Login)
            //   6  = Tạo nhân vật (CreateCharacter)
            //   11 = Yêu cầu Map Info
            //   44 = Di chuyển (Move)
            //   50 = Phân điểm tiềm năng (AllocateStat)
            services.AddSingleton<PacketDispatcher>(sp =>
            {
                var dispatcher = new PacketDispatcher();

                dispatcher.RegisterHandler((int)CommandCode.RegisterRequest,        sp.GetRequiredService<RegisterHandler>());
                dispatcher.RegisterHandler((int)CommandCode.LoginRequest,           sp.GetRequiredService<AuthHandler>());
                dispatcher.RegisterHandler((int)CommandCode.TokenLoginRequest,      sp.GetRequiredService<TokenAuthHandler>());
                dispatcher.RegisterHandler((int)CommandCode.CreateCharacterRequest, sp.GetRequiredService<CreateCharacterHandler>());
                dispatcher.RegisterHandler((int)CommandCode.AllocateStatRequest,    sp.GetRequiredService<AllocateStatHandler>());
                dispatcher.RegisterHandler(11, sp.GetRequiredService<MapHandler>()); // To be refactored soon
                dispatcher.RegisterHandler(13, sp.GetRequiredService<MapHandler>());
                dispatcher.RegisterHandler(29, sp.GetRequiredService<MapHandler>());
                dispatcher.RegisterHandler(44, sp.GetRequiredService<MoveHandler>());

                return dispatcher;
            });

            return services;
        }
    }
}
