# 🐲 Thirteen Project - Server Runner
# Tập lệnh khởi động Server Loạn 12 Sứ Quân

$ProjectDir = Join-Path $PSScriptRoot "Twelve.Server"
$ProjectPath = Join-Path $ProjectDir "Twelve.Server.csproj"

Write-Host "--------------------------------------------------" -ForegroundColor Cyan
Write-Host "⚔️  KHỞI CHẠY SERVER: LOẠN 12 SỨ QUÂN (2026)"
Write-Host "--------------------------------------------------" -ForegroundColor Cyan
Write-Host "📂 Dự án chính: $ProjectPath" -ForegroundColor Gray

if (-not (Test-Path $ProjectPath)) {
    Write-Error "❌ Không tìm thấy file project .csproj tại $ProjectPath"
    exit 1
}

Write-Host "🚀 Đang kiểm tra mã nguồn và biên dịch..." -ForegroundColor Magenta
dotnet build $ProjectDir --configuration Debug

if ($LASTEXITCODE -ne 0) {
    Write-Error "❌ Lỗi khi biên dịch Server. Vui lòng kiểm tra lại mã nguồn."
    exit 1
}

Write-Host "✅ Biên dịch thành công. Đang khởi động Server..." -ForegroundColor Green
Write-Host "📡 WebSocket Server: localhost:5102/game" -ForegroundColor Cyan
Write-Host "--------------------------------------------------" -ForegroundColor DarkGray

dotnet run --project $ProjectPath --no-build
