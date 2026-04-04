using Twelve.Server;
using Twelve.Server.Middleware;
using Twelve.Core.Options;
using Twelve.Application;
using Twelve.Infrastructure;
using dotenv.net;

DotEnv.Load(options: new DotEnvOptions(probeForEnv: true, probeLevelsToSearch: 4));

var builder = WebApplication.CreateBuilder(args);

// Elite Configuration Binding
builder.Services.Configure<GameSettings>(builder.Configuration.GetSection("GameSettings"));
builder.Services.Configure<PaymentSettings>(builder.Configuration.GetSection("PaymentSettings"));
builder.Services.Configure<ApiSettings>(builder.Configuration.GetSection("ApiSettings"));

// Add services to the container.
builder.Services.AddTwelveApplication();
builder.Services.AddTwelveInfrastructure(); // Register DB and Repositories
builder.Services.AddHostedService<TcpServerService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
app.UseWebSockets();
app.UseMiddleware<WebSocketGameMiddleware>();

app.Run();
