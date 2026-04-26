# [+] Thirteen Project - Server Runner
# Khoi dong Server Loan 12 Su Quan

$ProjectDir = Join-Path $PSScriptRoot "Twelve.Server"
$ProjectPath = Join-Path $ProjectDir "Twelve.Server.csproj"

Write-Host "--------------------------------------------------" -ForegroundColor Cyan
Write-Host "[+] KHOI CHAY SERVER: LOAN 12 SU QUAN (2026)"
Write-Host "--------------------------------------------------" -ForegroundColor Cyan
Write-Host "[i] Du an chinh: $ProjectPath" -ForegroundColor Gray

if (-not (Test-Path $ProjectPath)) {
    Write-Error "[!] Khong tim thay file project .csproj tai $ProjectPath"
    exit 1
}

Write-Host "[...] Dang kiem tra ma nguon va bien dich..." -ForegroundColor Magenta
dotnet build $ProjectDir --configuration Debug

if ($LASTEXITCODE -ne 0) {
    Write-Error "[!] Loi khi bien dich Server. Vui long kiem tra lai ma nguon."
    exit 1
}

Write-Host "[OK] Bien dich thanh cong. Dang khoi dong Server..." -ForegroundColor Green
Write-Host "[i] WebSocket Server: localhost:5102/game" -ForegroundColor Cyan
Write-Host "--------------------------------------------------" -ForegroundColor DarkGray

dotnet run --project $ProjectPath --no-build
