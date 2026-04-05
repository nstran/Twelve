using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using Npgsql;

// Twelve.Tools - Công cụ quản trị dự án Loạn 12 Sứ Quân 🐲
// Đã được quy hoạch lại để nằm gọn trong thư mục server/

var builder = new ConfigurationBuilder()
    .SetBasePath(Directory.GetCurrentDirectory())
    .AddJsonFile("appsettings.json", optional: true)
    // Đường dẫn tương đối từ server/Twelve.Tools đến server/Twelve.Server
    .AddJsonFile(Path.Combine("..", "Twelve.Server", "appsettings.json"), optional: true)
    .AddEnvironmentVariables();

var config = builder.Build();

if (args.Length == 0)
{
    PrintHelp();
    return;
}

string command = args[0].ToLower();

switch (command)
{
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
    Console.WriteLine("Twelve Tools v1.1 🛡️ Dragon-Power");
    Console.WriteLine("Su dung: dotnet run -- [command] [args]");
    Console.WriteLine("\nCac lenh ho tro:");
    Console.WriteLine("  init-db [sqlPath]  - Khoi tao DB tu script SQL (Account/Player).");
    Console.WriteLine("  fix-png           - Khoi phuc anh PNG hang loat tu file .mg.");
}

async Task RunInitDb(string[] subArgs)
{
    string? connectionString = config.GetConnectionString("Default");
    // Mac dinh trỏ vào server/Database/01_accounts.sql
    string? sqlPathConfig = config["Database:InitSqlPath"] ?? Path.Combine("..", "Database", "01_accounts.sql");
    string sqlPath = subArgs.Length > 0 ? subArgs[0] : sqlPathConfig;

    if (string.IsNullOrEmpty(connectionString))
    {
        Console.WriteLine("CRITICAL: Khong tim thay connection string 'Default'.");
        return;
    }

    try
    {
        string fullSqlPath = Path.GetFullPath(sqlPath);
        if (!File.Exists(fullSqlPath))
        {
            Console.WriteLine($"Error: Khong tim thay script tai {fullSqlPath}");
            return;
        }

        Console.WriteLine($"🔥 Dang thuc thi script tai {fullSqlPath}...");
        string sql = await File.ReadAllTextAsync(fullSqlPath);
        
        using var conn = new NpgsqlConnection(connectionString);
        await conn.OpenAsync();
        
        using var cmd = new NpgsqlCommand(sql, conn);
        await cmd.ExecuteNonQueryAsync();
        
        Console.WriteLine("✅ THANH CONG: Da khoi tao Database. San sang cho tran danh! 🛡️");
    }
    catch (Exception ex)
    {
        Console.WriteLine("DATABASE ERROR: " + ex.Message);
    }
}

async Task RunFixPng(string[] subArgs)
{
    // Mac dinh trỏ vào reference/L12SQ/...
    string sourceDir = config["Assets:SourceMgDir"] ?? Path.Combine("..", "..", "reference", "L12SQ", "client-base", "extracted");
    string outputDir = config["Assets:OutputPngDir"] ?? Path.Combine("..", "..", "client", "assets", "original");

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
        Console.WriteLine($"🚀 Found {mgFiles.Length} files. Restoring...");

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
