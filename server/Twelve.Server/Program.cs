using Twelve.Server;
using Twelve.Server.Middleware;
using Twelve.Core.Options;
using Twelve.Application;
using Twelve.Infrastructure;
using Twelve.Infrastructure.Data;
using dotenv.net;

DotEnv.Load(options: new DotEnvOptions(probeForEnv: true, probeLevelsToSearch: 4));

var builder = WebApplication.CreateBuilder(args);

// ── Configuration ─────────────────────────────────────────────────────────
builder.Services.Configure<GameSettings>(builder.Configuration.GetSection("GameSettings"));
builder.Services.Configure<PaymentSettings>(builder.Configuration.GetSection("PaymentSettings"));
builder.Services.Configure<ApiSettings>(builder.Configuration.GetSection("ApiSettings"));

// ── Services ──────────────────────────────────────────────────────────────
builder.Services.AddTwelveApplication();
builder.Services.AddTwelveInfrastructure();
builder.Services.AddHostedService<TcpServerService>();

var app = builder.Build();

// ── Auto Migration: chạy toàn bộ schema SQL khi khởi động ─────────────────
// An toàn chạy nhiều lần nhờ IF NOT EXISTS — không cần lo về bảng/cột đã có.
await app.Services.GetRequiredService<DatabaseMigrator>().MigrateAsync();

// ── HTTP Pipeline ─────────────────────────────────────────────────────────
app.UseWebSockets();
app.UseMiddleware<WebSocketGameMiddleware>();

app.Run();
