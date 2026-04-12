# Architecture & API Patterns (Lean Game Server)

## Layering (Clean Architecture)

### L12SQ.Core (Domain) — "The Soul"
- **Entities**: Player, Item, Map, Monster, Account
- **Logic**: BattleEngine, LevelingFormula, MovementValidator, MapLogic
- **Interfaces**: IRepository, IGameSession, IPaymentProvider
- **TLV**: TlvCodec, CommandCodes, PacketRequest

### L12SQ.Application — "The Bridge"
- **Handlers**: PacketHandlers (Binary TLV), Hubs (SignalR)
- **Services**: AuthService, PlayerService — orchestration only, no business logic
- **DTOs**: Flat objects for REST/JSON. No deep nesting.
- **Dispatcher**: PacketDispatcher maps CommandId → IPacketHandler

### L12SQ.Infrastructure — "The Plumbing"
- **Data**: Dapper + PostgreSQL (NOT EF Core for game queries)
- **Network**: TCP Listener, WebSocket Bridge
- **External**: File system for map assets, JWT token store

## API Patterns

### REST (JSON) — Modern Client
| Verb | Pattern | Example |
|------|---------|---------|
| GET | `/api/{resource}` | `/api/players/profile`, `/api/players/stats` |
| POST | `/api/{resource}` | `/api/auth/login`, `/api/shop/purchase` |
| PUT | `/api/{resource}/{id}` | `/api/players/avatar` |

### Socket (TLV Binary) — Game Real-time
```csharp
// Every handler follows this signature
Task Handle(GameSession session, PacketRequest request)

// Registry: CommandId → Handler
// Cmd 11 → MapHandler, Cmd 1 → AuthHandler, etc.
```

## Design Patterns

- **Command Pattern**: Cmd ID → IPacketHandler
- **Observer Pattern**: Game events (PlayerJoined, BattleStarted)
- **Singleton**: World/MapStore state
- **Scoped/Transient**: GameSession, per-request services

## Trade-offs (Why We Chose This Way)

| Choice | Why |
|--------|-----|
| Dapper over EF Core | Raw SQL speed for hot-path packet processing |
| Binary TLV over JSON | J2ME compatibility + lower bandwidth |
| Zustand over Redux | Lighter weight for React Native game client |
| Flat DTOs | Performance + simplicity for mobile |
