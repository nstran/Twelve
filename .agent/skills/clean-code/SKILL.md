# Clean Code Standards (Project Twelve)

## Naming Conventions

| Language | Style | Example |
|----------|-------|---------|
| C# methods/classes | PascalCase | `HandleMapInfo`, `GameSession` |
| C# private fields | _camelCase | `_mapStore`, `_logger` |
| JS/TS functions | camelCase | `handleSwipe`, `useGameSocket` |
| Constants | UPPER_SNAKE | `CMD_MAP_INFO = 11` |
| Tag IDs | Named constant | `TAG_GROUND_LAYER = 55` (never magic numbers) |

## Code Structure Rules

1. **Guard clauses first** — early return for invalid state
2. **Async/await mandatory** — all socket, network, DB operations
3. **Domain in Core only** — business logic never leaks to Infrastructure
4. **No boilerplate** — if it doesn't add value, remove it
5. **Flat DTOs** — avoid deep nesting for game performance

## File Organization

```
Core/
  Entities/       → Player, Map, Monster (pure domain)
  GameLogic/      → BattleEngine, MapLogic (rules)
  Tlv/            → TlvCodec, CommandCodes (protocol)
  Interfaces/     → IRepository contracts
Application/
  Handlers/       → One handler per CommandId
  Services/       → Orchestration only
Infrastructure/
  Data/           → Dapper repositories
  Repositories/   → SQL implementations
```

## Verification

- `dotnet build` — must pass, zero warnings preferred
- `dotnet test` — all unit tests green
- `npx expo check` — frontend health check
- Protocol: verify against J2ME decompiled source
