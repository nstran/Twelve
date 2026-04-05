using System;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using Dapper;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Npgsql;

namespace Twelve.Infrastructure.Data
{
    /// <summary>
    /// Chạy toàn bộ schema SQL khi server khởi động.
    /// Bước 1: Tạo database nếu chưa tồn tại (kết nối qua "postgres" DB).
    /// Bước 2: Chạy toàn bộ file *.sql nhúng trong assembly (thư mục server/Database/).
    /// Thứ tự chạy: sắp xếp theo tên file (01_, 02_, 03_, ...).
    /// An toàn chạy lại bất kỳ lúc nào — tất cả SQL đều dùng IF NOT EXISTS / ADD COLUMN IF NOT EXISTS.
    /// </summary>
    public class DatabaseMigrator
    {
        private readonly IDbConnectionFactory      _db;
        private readonly IConfiguration            _config;
        private readonly ILogger<DatabaseMigrator> _logger;

        public DatabaseMigrator(
            IDbConnectionFactory db,
            IConfiguration config,
            ILogger<DatabaseMigrator> logger)
        {
            _db     = db;
            _config = config;
            _logger = logger;
        }

        public async Task MigrateAsync()
        {
            // ── Bước 1: Đảm bảo database tồn tại ─────────────────────────────
            await EnsureDatabaseExistsAsync();

            // ── Bước 2: Chạy các file .sql nhúng trong assembly ───────────────
            _logger.LogInformation("[DB] Chạy schema migration từ embedded SQL files...");

            var assembly     = typeof(DatabaseMigrator).Assembly;
            var allResources = assembly.GetManifestResourceNames();

            _logger.LogInformation("[DB] Toàn bộ embedded resources ({Count}):", allResources.Length);
            foreach (var r in allResources)
                _logger.LogInformation("[DB]   • {Resource}", r);

            var sqlResources = allResources
                .Where(n => n.EndsWith(".sql", StringComparison.OrdinalIgnoreCase))
                .OrderBy(n => n)   // 01_ trước 02_ trước 03_ ...
                .ToList();

            if (sqlResources.Count == 0)
            {
                _logger.LogWarning("[DB] ⚠ Không tìm thấy file .sql nào. Kiểm tra <EmbeddedResource> trong Twelve.Infrastructure.csproj và rebuild.");
                return;
            }

            _logger.LogInformation("[DB] Tìm thấy {Count} file SQL sẽ chạy:", sqlResources.Count);
            foreach (var r in sqlResources)
                _logger.LogInformation("[DB]   → {Resource}", r);

            using var conn = _db.CreateConnection();

            foreach (var resourceName in sqlResources)
            {
                await using var stream = assembly.GetManifestResourceStream(resourceName)
                    ?? throw new InvalidOperationException($"Không đọc được resource: {resourceName}");
                using var reader = new StreamReader(stream);
                var sql = await reader.ReadToEndAsync();

                try
                {
                    await conn.ExecuteAsync(sql);
                    _logger.LogInformation("[DB] ✓ {Resource}", resourceName);
                }
                catch (Exception ex)
                {
                    _logger.LogError(ex, "[DB] ✗ {Resource}: {Message}", resourceName, ex.Message);
                    throw; // Schema lỗi → server không nên tiếp tục
                }
            }

            _logger.LogInformation("[DB] Migration hoàn tất.");
        }

        // ─────────────────────────────────────────────────────────────────────
        // Kết nối DB "postgres" (luôn tồn tại), tạo DB game nếu thiếu.
        // CREATE DATABASE không thể chạy trong transaction → dùng NpgsqlCommand.
        // ─────────────────────────────────────────────────────────────────────
        private async Task EnsureDatabaseExistsAsync()
        {
            var connStr = _config.GetConnectionString("Default")
                ?? throw new InvalidOperationException("Connection string 'Default' not found.");

            var builder  = new NpgsqlConnectionStringBuilder(connStr);
            var targetDb = builder.Database ?? "Twelve";
            builder.Database = "postgres";

            _logger.LogInformation("[DB] Kiểm tra database '{Db}'...", targetDb);

            await using var adminConn = new NpgsqlConnection(builder.ConnectionString);
            await adminConn.OpenAsync();

            var exists = await adminConn.ExecuteScalarAsync<bool>(
                "SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname = @name)",
                new { name = targetDb });

            if (!exists)
            {
                await using var cmd = adminConn.CreateCommand();
                cmd.CommandText = $"CREATE DATABASE \"{targetDb}\"";
                await cmd.ExecuteNonQueryAsync();
                _logger.LogInformation("[DB] ✓ Đã tạo database '{Db}'", targetDb);
            }
            else
            {
                _logger.LogInformation("[DB] ✓ Database '{Db}' đã tồn tại", targetDb);
            }
        }
    }
}
