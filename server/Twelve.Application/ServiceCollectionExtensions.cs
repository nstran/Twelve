using Microsoft.Extensions.DependencyInjection;
using Twelve.Application.Battle;
using Twelve.Application.Handlers;
using Twelve.Application.Monsters;
using Twelve.Application.Npcs;
using Twelve.Application.Players;
using Twelve.Core.Interfaces;
using Twelve.Core.Tlv;

namespace Twelve.Application
{
    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddTwelveApplication(this IServiceCollection services)
        {
            services.AddSingleton<IBattleSessionStore, InMemoryBattleSessionStore>();
            services.AddSingleton<IBattleBoardService, ReconstructedBattleBoardService>();
            services.AddSingleton<IBattleSessionSyncService, BattleSessionSyncService>();
            services.AddSingleton<IBattleResultService, BattleResultService>();
            services.AddSingleton<IBattleTurnEngine, BattleTurnEngine>();
            services.AddSingleton<IBattlePvpActionService, BattlePvpActionService>();
            services.AddSingleton<IBattleEnemyMoveService, BattleEnemyMoveService>();
            services.AddSingleton<IBattleEnemyTurnPlannerService, BattleEnemyTurnPlannerService>();
            services.AddSingleton<IBattleSkillPacketFactory, BattleSkillPacketFactory>();
            services.AddSingleton<IBattleSkillCastPacketService, BattleSkillCastPacketService>();
            services.AddSingleton<IBattleEnemyTurnPacketService, BattleEnemyTurnPacketService>();
            // Monster catalogs now registered in Infrastructure layer (DB-backed)
            services.AddSingleton<IMonsterBattleBootstrapService, MonsterBattleBootstrapService>();
            services.AddSingleton<PlayerCharacterPacketFactory>();
            services.AddSingleton<PlayerContentCatalog>();
            services.AddSingleton<IEquipmentUpgradeService, EquipmentUpgradeService>();
            services.AddSingleton<IEquipmentCombineService, EquipmentCombineService>();
            services.AddSingleton<IPlayerRuntimeService, PlayerRuntimeService>();
            services.AddSingleton<IPvpArenaService, PvpArenaService>();
            services.AddSingleton<IMissionRewardClaimService, MissionRewardClaimService>();

            // ── Đăng ký Handlers ───────────────────────────────────────────────
            services.AddSingleton<AuthHandler>();
            services.AddSingleton<TokenAuthHandler>();
            services.AddSingleton<RegisterHandler>();
            services.AddSingleton<CreateCharacterHandler>();
            services.AddSingleton<AllocateStatHandler>();
            services.AddSingleton<MapHandler>();
            services.AddSingleton<NpcTalkHandler>();
            services.AddSingleton<MissionHandler>();
            services.AddSingleton<MonsterEncounterHandler>();
            services.AddSingleton<EquipmentCommandHandler>();
            services.AddSingleton<MoveHandler>();

            // ── Wiring PacketDispatcher ────────────────────────────────────────
            // CMD numbers (client → server):
            //   1  = Đăng ký (Register)
            //   2  = Đăng nhập (Login)
            //   6  = Tạo nhân vật (CreateCharacter)
            //   11 = Yêu cầu Map Info
            //   16 = NPC talk request (Java ks.a().a(String, boolean))
            //   31/33 = Mission list/detail (Java Mission UI flow)
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
                dispatcher.RegisterHandler((int)CommandCode.NpcTalkRequest, sp.GetRequiredService<NpcTalkHandler>());
                dispatcher.RegisterHandler((int)CommandCode.MissionList, sp.GetRequiredService<MissionHandler>());
                dispatcher.RegisterHandler((int)CommandCode.MissionDetail, sp.GetRequiredService<MissionHandler>());
                dispatcher.RegisterHandler((int)CommandCode.MissionAccept, sp.GetRequiredService<MissionHandler>());
                dispatcher.RegisterHandler((int)CommandCode.MissionCancel, sp.GetRequiredService<MissionHandler>());
                dispatcher.RegisterHandler((int)CommandCode.MonsterBootstrapRequest, sp.GetRequiredService<MonsterEncounterHandler>());
                dispatcher.RegisterHandler((int)CommandCode.EquipmentShopBuy, sp.GetRequiredService<EquipmentCommandHandler>());
                dispatcher.RegisterHandler((int)CommandCode.EquipmentEquipUnequip, sp.GetRequiredService<EquipmentCommandHandler>());
                dispatcher.RegisterHandler((int)CommandCode.EquipmentUpgrade, sp.GetRequiredService<EquipmentCommandHandler>());
                dispatcher.RegisterHandler((int)CommandCode.EquipmentRepairUse, sp.GetRequiredService<EquipmentCommandHandler>());
                dispatcher.RegisterHandler((int)CommandCode.EquipmentCombineForge, sp.GetRequiredService<EquipmentCommandHandler>());
                dispatcher.RegisterHandler(44, sp.GetRequiredService<MoveHandler>());

                return dispatcher;
            });

            return services;
        }
    }
}
