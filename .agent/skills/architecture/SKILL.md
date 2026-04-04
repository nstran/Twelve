# Architecture (Lean Game Server)

## 🏗️ Layering (Clean Architecture)

- **L12SQ.Core (Domain)**:
  - **Entities**: Player, Item, Map, Monster. 
  - **Logic**: BattleEngine, LevelingFormula, MovementValidator. This is the "Soul" of the game.
  - **Interfaces**: IRepository, IGameSession.
- **L12SQ.Application**:
  - **Handlers**: PacketHandlers (Binary), Hubs (SignalR).
  - **Services**: AuthService, PlayerService. Orchestrates the Domain logic.
  - **DTOs**: Simplified data transfer objects for REST/JSON.
- **L12SQ.Infrastructure**:
  - **Data**: Dapper implementation for PostgreSQL.
  - **Network**: TCP Listener lifecycle management.
  - **External**: File system for map assets.

## 🔄 Patterns

- **Command Pattern**: For processing game packets (Cmd ID -> Handler).
- **Observer Pattern**: For game events (PlayerJoined, BattleStarted).
- **Singleton/Scoped**: Game server state (World) is typically Singleton; Session data is Scoped/Transient.

## ⚖️ Trade-offs

- **Performance vs. Complexity**: Use Dapper for raw SQL speed. Avoid EF Core overhead in the hot-path of packet processing.
- **Binary vs. JSON**: Use Binary (TLV) for the J2ME client; JSON for the modern React Native client API.
