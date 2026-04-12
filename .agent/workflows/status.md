# /status — Project Status

## Steps

1. **Read**: `Twelve.Status.md` for current phase and progress.
2. **Check**: `Twelve.Bugs.md` for open issues.
3. **Scan**: List files in `server/` and `client/src/` to verify actual state.
4. **Report**: Output summary with:
   - Current Phase (1-4)
   - Completed tasks
   - Open bugs/blockers
   - Next priority items

## Components

| Component | Location | Check |
|-----------|----------|-------|
| Backend (.NET 9) | `server/` | `dotnet build` |
| Frontend (React Native) | `client/` | `npx expo check` |
| Database (PostgreSQL) | `server/Database/` | Schema files exist |
| Protocol (TLV) | `Core/Tlv/` | TlvCodec tests pass |
