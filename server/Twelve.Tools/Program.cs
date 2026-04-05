using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using Npgsql;

// Twelve.Tools v1.6 🐲 - Chế độ "Setup Tận Răng" (Case-Insensitive Master)
// Công cụ quản trị dự án Loạn 12 Sứ Quân

// Tự động tìm kiếm file cấu hình appsettings.json
var currentDir = Directory.GetCurrentDirectory();
var builder = new ConfigurationBuilder().SetBasePath(currentDir);

string[] possiblePaths = {
    Path.Combine(currentDir, "appsettings.json"),
    Path.Combine(currentDir, "server", "Twelve.Server", "appsettings.json"),
    Path.Combine(currentDir, "..", "Twelve.Server", "appsettings.json")
};

bool foundConfig = false;
foreach (var path in possiblePaths)
{
    if (File.Exists(path))
    {
        builder.AddJsonFile(path);
        foundConfig = true;
        break;
    }
}

builder.AddEnvironmentVariables();
var config = builder.Build();

if (args.Length == 0)
{
    PrintHelp();
    return;
}

string command = args[0].ToLower();

switch (command)
{
    case "setup":
        await RunSetup();
        break;
    case "init-db":
        await RunInitDb(args.Skip(1).ToArray());
        break;
    case "fix-png":
        await RunFixPng(args.Skip(1).ToArray());
        break;
    default:
        Console.WriteLine($"Unknown command: {command}");
        PrintHelp();
        break;
}

void PrintHelp()
{
    Console.WriteLine("Twelve Tools v1.6 🛡️ Dragon-Power");
    Console.WriteLine("Su dung: dotnet run -- [command] [args]");
    if (!foundConfig) Console.WriteLine("⚠️ CANH BAO: Khong tim thay file appsettings.json!");
    Console.WriteLine("\nCac lenh ho tro:");
    Console.WriteLine("  setup             - Tu dong tao Database Twelve va nap toan bo Schema.");
    Console.WriteLine("  init-db [sqlPath]  - Khoi tao DB tu script SQL (Account/Player).");
    Console.WriteLine("  fix-png           - Khoi phuc anh PNG hang loat tu file .mg.");
}

async Task RunSetup()
{
    Console.WriteLine("🐉 BAT DAU QUTRINH THIET LAP CHIEN TRUONG (AUTO-SETUP)...");
    
    string? connectionString = config.GetConnectionString("Default");
    if (string.IsNullOrEmpty(connectionString))
    {
        Console.WriteLine("CRITICAL: Khong tim thay connection string 'Default'!");
        return;
    }

    var csb = new NpgsqlConnectionStringBuilder(connectionString);
    string targetDb = csb.Database ?? "Twelve";
    
    // Step 1: Tao Database thong qua system DB 'postgres'
    Console.WriteLine($"Step 1: Kiem tra va tao Database '{targetDb}'...");
    
    var systemCsb = new NpgsqlConnectionStringBuilder(connectionString) { Database = "postgres" };
    string realDbName = targetDb;

    try 
    {
        using var conn = new NpgsqlConnection(systemCsb.ConnectionString);
        await conn.OpenAsync();
        
        using var checkCmd = new NpgsqlCommand($"SELECT datname FROM pg_database WHERE LOWER(datname) = LOWER('{targetDb}')", conn);
        var result = await checkCmd.ExecuteScalarAsync();
        
        if (result == null)
        {
            Console.WriteLine($"   -> Dang tao database '{targetDb}'...");
            using var createCmd = new NpgsqlCommand($"CREATE DATABASE \"{targetDb}\"", conn);
            await createCmd.ExecuteNonQueryAsync();
            Console.WriteLine("   -> Da tao Database thanh cong!");
            
            NpgsqlConnection.ClearAllPools();
            await Task.Delay(2000); 
            realDbName = targetDb;
        }
        else
        {
            realDbName = result.ToString() ?? targetDb;
            Console.WriteLine($"   -> Database '{realDbName}' da ton tai. Sang buoc tiep theo.");
        }
    }
    catch (PostgresException ex) when (ex.SqlState == "42P04")
    {
        Console.WriteLine($"   -> Database '{targetDb}' da ton tai (Exception handled).");
    }
    catch (Exception ex)
    {
        Console.WriteLine("FATAL ERROR (Step 1): Khong the ket noi den Postgres system DB.");
        Console.WriteLine("Error: " + ex.Message);
        return;
    }

    // Luôn sử dụng Connection String với tên Database chính xác từ Postgres
    var finalCsb = new NpgsqlConnectionStringBuilder(connectionString) { Database = realDbName };
    string finalConnString = finalCsb.ConnectionString;

    // Step 2: Nap Schema
    Console.WriteLine("\nStep 2: Nap toan bo Schema vao database...");
    
    string dbDir = Directory.Exists(Path.Combine(currentDir, "server", "Database")) 
        ? Path.Combine(currentDir, "server", "Database")
        : Path.Combine(currentDir, "..", "Database");

    string[] schemaFiles = { "01_accounts.sql", "02_players.sql" };
    
    foreach (var file in schemaFiles)
    {
        string fullPath = Path.GetFullPath(Path.Combine(dbDir, file));
        if (File.Exists(fullPath))
        {
            Console.WriteLine($"   -> Dang thuc thi {file}...");
            await RunInitDbInternal(fullPath, finalConnString);
        }
    }

    Console.WriteLine("\n✅ HOAN TAT: Chien truong Twelve da san sang de vung guom! 🛡️🐲");
}

async Task RunInitDbInternal(string fullSqlPath, string connectionString)
{
    try
    {
        string sql = await File.ReadAllTextAsync(fullSqlPath);
        using var conn = new NpgsqlConnection(connectionString);
        await conn.OpenAsync();
        using var cmd = new NpgsqlCommand(sql, conn);
        await cmd.ExecuteNonQueryAsync();
        Console.WriteLine($"      -> Success: {Path.GetFileName(fullSqlPath)}");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"      [X] ERROR ({Path.GetFileName(fullSqlPath)}): {ex.Message}");
    }
}

async Task RunInitDb(string[] subArgs)
{
    string? connectionString = config.GetConnectionString("Default");
    if (string.IsNullOrEmpty(connectionString)) return;

    string dbDir = Directory.Exists(Path.Combine(currentDir, "server", "Database")) 
        ? Path.Combine(currentDir, "server", "Database")
        : Path.Combine(currentDir, "..", "Database");
        
    string defaultSql = Path.Combine(dbDir, "01_accounts.sql");
    string sqlPath = subArgs.Length > 0 ? subArgs[0] : defaultSql;

    await RunInitDbInternal(Path.GetFullPath(sqlPath), connectionString);
}

async Task RunFixPng(string[] subArgs)
{
    string refDir = Directory.Exists(Path.Combine(currentDir, "reference"))
        ? Path.Combine(currentDir, "reference")
        : Path.Combine(currentDir, "..", "..", "reference");
        
    string clientDir = Directory.Exists(Path.Combine(currentDir, "client"))
        ? Path.Combine(currentDir, "client")
        : Path.Combine(currentDir, "..", "..", "client");

    string sourceDir = config["Assets:SourceMgDir"] ?? Path.Combine(refDir, "L12SQ", "client-base", "extracted");
    string outputDir = config["Assets:OutputPngDir"] ?? Path.Combine(clientDir, "assets", "original");

    try 
    {
        var srcInfo = new DirectoryInfo(Path.GetFullPath(sourceDir));
        var outInfo = new DirectoryInfo(Path.GetFullPath(outputDir));

        if (!srcInfo.Exists) {
            Console.WriteLine($"Error: Khong tim thay asset tai {srcInfo.FullName}");
            return;
        }

        if (!outInfo.Exists) outInfo.Create();

        Console.WriteLine($"🔍 Scanning .mg files tai: {srcInfo.FullName}");
        var mgFiles = srcInfo.GetFiles("*.mg");
        int count = 0;
        foreach (var file in mgFiles)
        {
            byte[] data = await File.ReadAllBytesAsync(file.FullName);
            if (data.Length > 4) {
                data[0] = 0x89; data[1] = 0x50; data[2] = 0x4E; data[3] = 0x47;
                string targetName = Path.GetFileNameWithoutExtension(file.Name) + ".png";
                string targetPath = Path.Combine(outInfo.FullName, targetName);
                await File.WriteAllBytesAsync(targetPath, data);
                count++;
            }
        }
        Console.WriteLine($"✅ THANH CONG: Da khoi phuc {count}/{mgFiles.Length} PNG den {outInfo.FullName}");
    }
    catch (Exception ex)
    {
        Console.WriteLine("ASSET ERROR: " + ex.Message);
    }
}
