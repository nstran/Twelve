using Twelve.Server;
using Twelve.Server.Middleware;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Core.Options;
using Twelve.Application;
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

app.Run();
