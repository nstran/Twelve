@echo off
REM 🐲 Thirteen Project - Server Runner Shortcut (BATCH)
REM Khởi động Server Loạn 12 Sứ Quân từ Windows CMD

echo ==================================================
echo   ⚔️  KHƠI CHAY SERVER: LOAN 12 SÚ QUÂN (2026)
echo ==================================================

cd /d %~dp0Twelve.Server
echo 🚀 Dang bien dich va chay Server .NET 9...
dotnet run --project Twelve.Server.csproj

pause
