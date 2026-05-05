using Twelve.Server;
using Twelve.Server.Middleware;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;
using Twelve.Core.Monsters;
using Twelve.Core.Options;
using Twelve.Core.Players;
using Twelve.Application;
using Twelve.Application.Monsters;
using Twelve.Infrastructure;
using Twelve.Infrastructure.Data;
using dotenv.net;
using Microsoft.AspNetCore.Http.Json;
using System.Text.Json.Serialization;
using Microsoft.Net.Http.Headers;

DotEnv.Load(options: new DotEnvOptions(probeForEnv: true, probeLevelsToSearch: 4));

var builder = WebApplication.CreateBuilder(args);

// ── Configuration ─────────────────────────────────────────────────────────
builder.Services.Configure<GameSettings>(builder.Configuration.GetSection("GameSettings"));
builder.Services.Configure<PaymentSettings>(builder.Configuration.GetSection("PaymentSettings"));
builder.Services.Configure<ApiSettings>(builder.Configuration.GetSection("ApiSettings"));
builder.Services.Configure<JsonOptions>(options =>
{
    options.SerializerOptions.Converters.Add(new JsonStringEnumConverter());
});
builder.Services.AddCors(options =>
{
    options.AddPolicy("DevClientCors", policy =>
    {
        policy
            .SetIsOriginAllowed(origin =>
            {
                if (!Uri.TryCreate(origin, UriKind.Absolute, out var uri))
                {
                    return false;
                }

                if (!string.Equals(uri.Host, "localhost", StringComparison.OrdinalIgnoreCase) &&
                    !string.Equals(uri.Host, "127.0.0.1", StringComparison.OrdinalIgnoreCase))
                {
                    return false;
                }

                return uri.Scheme is "http" or "https";
            })
            .AllowAnyHeader()
            .AllowAnyMethod()
            .WithExposedHeaders(HeaderNames.ContentType);
    });
});

// ── Services ──────────────────────────────────────────────────────────────
builder.Services.AddTwelveApplication();
builder.Services.AddTwelveInfrastructure();
builder.Services.AddHostedService<TcpServerService>();

var app = builder.Build();

// ── Auto Migration: chạy toàn bộ schema SQL khi khởi động ─────────────────
// An toàn chạy nhiều lần nhờ IF NOT EXISTS — không cần lo về bảng/cột đã có.
await app.Services.GetRequiredService<DatabaseMigrator>().MigrateAsync();

// ── HTTP Pipeline ─────────────────────────────────────────────────────────
app.UseCors("DevClientCors");
app.UseWebSockets();
app.UseMiddleware<WebSocketGameMiddleware>();

app.MapPost("/debug/battle/skill-packet", (BattleSkillPacketSeed seed, IBattleSkillPacketFactory factory) =>
{
    var packet = factory.CreatePacket(seed);
    return Results.Ok(packet);
});

app.MapPost("/battle/skill-cast", (BattleSkillCastRequest request, IBattleSkillCastPacketService service) =>
{
    var packet = service.CreatePacket(request);
    return packet is null ? Results.NoContent() : Results.Ok(packet);
});

app.MapPost("/battle/enemy-move", (BattleEnemyMoveRequest request, IBattleEnemyMoveService service) =>
{
    var response = service.CreateMove(request);
    return response is null ? Results.NoContent() : Results.Ok(response);
});

app.MapPost("/battle/enemy-turn-plan", (BattleEnemyTurnPlanRequest request, IBattleEnemyTurnPlannerService service) =>
{
    var response = service.CreatePlan(request);
    return response is null ? Results.NoContent() : Results.Ok(response);
});

app.MapPost("/battle/session-sync", (BattleSessionSyncRequest request, IBattleSessionSyncService service) =>
{
    var synced = service.Sync(request);
    return synced ? Results.Ok() : Results.NotFound();
});

app.MapPost("/battle/pvp-action", (BattlePvpActionRequest request, IBattlePvpActionService service) =>
{
    var response = service.Submit(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/battle/session-snapshot", (string sessionId, IBattleSessionStore store) =>
{
    var session = store.Get(sessionId);
    if (session is null)
    {
        return Results.NotFound();
    }

    return Results.Ok(new BattleSessionSnapshotResponse(
        SessionId: session.SessionId,
        ActiveTurn: session.ActiveTurn,
        Board: session.Board,
        PlayerCurrentHp: session.Player.CurrentHp,
        PlayerCurrentMp: session.Player.CurrentMp,
        PlayerCurrentPower: session.Player.CurrentPower,
        EnemyCurrentHp: session.Enemy.CurrentHp,
        EnemyCurrentMp: session.Enemy.CurrentMp,
        EnemyCurrentPower: session.Enemy.CurrentPower,
        IsCompleted: session.IsCompleted,
        Kind: session.Kind,
        TurnSeq: session.TurnSeq,
        LastPvpAction: session.LastPvpAction));
});

app.MapPost("/battle/result", (BattleResultClaimRequest request, IBattleResultService service) =>
{
    var response = service.Claim(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/battle/enemy-turn", (BattleEnemyTurnRequest request, IBattleEnemyTurnPacketService service) =>
{
    var packet = service.CreatePacket(request);
    return packet is null ? Results.NoContent() : Results.Ok(packet);
});

app.MapPost("/battle/monster-bootstrap", (MonsterBattleBootstrapRequest request, IMonsterBattleBootstrapService service) =>
{
    var response = service.Bootstrap(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/pvp/opponents", async (string username, IPvpArenaService service) =>
{
    var response = await service.ListOpponentsAsync(new PvpOpponentListRequest(username));
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/arena/enter", async (PvpArenaPresenceRequest request, IPvpArenaService service) =>
{
    var response = await service.EnterArenaAsync(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/arena/leave", async (PvpArenaPresenceRequest request, IPvpArenaService service) =>
{
    var response = await service.LeaveArenaAsync(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/bootstrap", async (PvpBattleBootstrapRequest request, IPvpArenaService service) =>
{
    var response = await service.BootstrapAsync(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges", async (PvpChallengeCreateRequest request, IPvpArenaService service) =>
{
    var response = await service.CreateChallengeAsync(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/pvp/challenges", async (string username, IPvpArenaService service) =>
{
    var response = await service.ListChallengesAsync(username);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges/inbox", async (PvpChallengeActionRequest request, IPvpArenaService service) =>
{
    var response = await service.ListChallengesAsync(request.Username);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/pvp/challenges/{ticketId}", async (string ticketId, string username, IPvpArenaService service) =>
{
    var response = await service.GetChallengeStatusAsync(ticketId, username);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges/{ticketId}/status", async (string ticketId, PvpChallengeStatusRequest request, IPvpArenaService service) =>
{
    var response = await service.GetChallengeStatusAsync(ticketId, request.Username);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges/{ticketId}/accept", async (string ticketId, PvpChallengeActionRequest request, IPvpArenaService service) =>
{
    var response = await service.AcceptChallengeAsync(ticketId, request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges/{ticketId}/decline", async (string ticketId, PvpChallengeActionRequest request, IPvpArenaService service) =>
{
    var response = await service.DeclineChallengeAsync(ticketId, request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/pvp/challenges/{ticketId}/cancel", async (string ticketId, PvpChallengeActionRequest request, IPvpArenaService service) =>
{
    var response = await service.CancelChallengeAsync(ticketId, request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/map/world-catalog", () => Results.Ok(RuntimeMapCatalog.AllWorldMaps));

app.MapGet("/map/monster-roster", (
    string mapId,
    int roomId,
    IMapMonsterRosterService rosterService,
    IMonsterSpawnCatalog spawnCatalog,
    IMonsterAssetCatalog assetCatalog) =>
{
    var encounters = rosterService.GetActiveRoster(mapId, roomId);
    var spawnByKey = MonsterSpawnDisplayResolver.EnrichTemplatesForEncounters(
        encounters,
        spawnCatalog,
        assetCatalog);
    var responseEntries = new List<MapMonsterRosterEntry>(encounters.Count);

    foreach (var encounter in encounters)
    {
        if (!spawnByKey.TryGetValue(encounter.SpawnTemplateKey, out var spawnTemplate))
        {
            continue;
        }

        var assetEntry = !string.IsNullOrWhiteSpace(spawnTemplate.AssetCatalogId)
            ? assetCatalog.GetById(spawnTemplate.AssetCatalogId)
            : null;
        var sharedSheetFamily = ResolveSharedSheetFamily(spawnTemplate, assetCatalog);
        responseEntries.Add(new MapMonsterRosterEntry(
            MonsterKey: encounter.MonsterKey,
            SpawnGroupKey: encounter.SpawnGroupKey,
            SpawnInstanceIndex: encounter.SpawnInstanceIndex,
            SpawnTemplateKey: encounter.SpawnTemplateKey,
            DisplayName: spawnTemplate.DisplayName,
            VisualTypeByte: spawnTemplate.VisualTypeByte,
            DisplayLevel: spawnTemplate.DisplayLevel,
            IqValue: spawnTemplate.IqValue,
            NameColorMode: spawnTemplate.NameColorMode,
            SharedSheetFamily: sharedSheetFamily,
            SurfaceId: encounter.SurfaceId,
            PatrolStartRatio: encounter.PatrolStartRatio,
            PatrolEndRatio: encounter.PatrolEndRatio,
            SpawnRatio: encounter.SpawnRatio,
            MoveSpeed: encounter.MoveSpeed,
            AssetCatalogId: spawnTemplate.AssetCatalogId,
            FramePaths: assetEntry?.FramePaths));
    }

    return Results.Ok(new MapMonsterRosterResponse(
        MapId: mapId,
        RoomId: roomId,
        Encounters: responseEntries));
});

app.MapGet("/player/runtime", (string username, IPlayerRuntimeService service) =>
{
    var response = service.GetSnapshot(new PlayerRuntimeRequest(username));
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/stat", (PlayerAllocateStatRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.AllocateStat(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/skill", (PlayerAllocateSkillRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.AllocateSkill(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/equipment", (PlayerEquipmentRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.UpdateEquipment(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/equipment/preview", (PlayerEquipmentLoadoutRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.PreviewEquipmentLoadout(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/equipment/loadout", (PlayerEquipmentLoadoutRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.CommitEquipmentLoadout(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/item-use", (PlayerUseItemRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.UseItem(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

// cmd 37 mode 2 — vứt bỏ equipment (chỉ khi không đang mặc)
app.MapPost("/player/runtime/equipment/discard", (PlayerDiscardEquipmentRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.DiscardEquipment(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

// cmd 83 — bỏ item theo quantity
app.MapPost("/player/runtime/item/discard", (PlayerDiscardItemRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.DiscardItem(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

// cmd 48 — sửa chữa equipment bằng vật phẩm (ll.p = ll.q)
app.MapPost("/player/runtime/equipment/repair", (PlayerRepairEquipmentRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.RepairEquipment(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

// Remake policy 2026-05-03 — upgrade requires unequipped item;
// real roll remains disabled until Java stone/charm list is verified.
app.MapPost("/player/runtime/equipment/upgrade", (PlayerUpgradeEquipmentRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.UpgradeEquipment(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapGet("/player/runtime/shop", (string username, string? shopKey, IPlayerRuntimeService service) =>
{
    var response = service.GetShop(new PlayerShopRuntimeRequest(username, shopKey ?? "equipment"));
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.MapPost("/player/runtime/shop/buy", (PlayerShopBuyRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.BuyShopOffer(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

// Remake policy 2026-05-03 — đập/mở trứng requires explicit egg config and reward pool;
// no fallback random equipment and no Wing/e=8 rewards.
app.MapPost("/player/runtime/item/open-egg", (PlayerOpenEggRuntimeRequest request, IPlayerRuntimeService service) =>
{
    var response = service.OpenEgg(request);
    return response is null ? Results.NotFound() : Results.Ok(response);
});

app.Run();

static MonsterSharedSheetFamily ResolveSharedSheetFamily(
    MonsterSpawnTemplate spawnTemplate,
    IMonsterAssetCatalog assetCatalog)
{
    if (!string.IsNullOrWhiteSpace(spawnTemplate.AssetCatalogId))
    {
        var assetEntry = assetCatalog.GetById(spawnTemplate.AssetCatalogId);
        if (assetEntry is not null)
        {
            return assetEntry.SharedSheetFamily;
        }
    }

    return (spawnTemplate.VisualTypeByte >> 1) switch
    {
        0 => MonsterSharedSheetFamily.Monster,
        1 => MonsterSharedSheetFamily.Zap,
        _ => MonsterSharedSheetFamily.Ice,
    };
}
