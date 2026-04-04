# Game API & Socket Patterns

## 📡 REST API (Management)

- **Auth**: `/api/auth/login`, `/api/auth/register` (JWT based).
- **Profile**: `/api/players/profile`, `/api/players/stats`.
- **Shop**: `/api/shop/items`, `/api/shop/purchase`.
- **Verbs**:
  - `GET`: Fetch data (e.g., `GetPlayerStats`).
  - `POST`: Actions (e.g., `Register`, `PurchaseItem`).
  - `PUT`: Updates (e.g., `UpdateAvatar`).

## 🔌 Socket Handlers (Real-time)

- **Registry**: Map Command IDs to Handlers (e.g., `Cmd 11 -> MapHandler.Handle`).
- **Context**: Every packet is processed in the context of a `GameSession`.
- **Structure**: 
  - `Handle(GameSession session, PacketRequest request)`
  - Use `BinaryWriter` to build response packets (Cmd + Tags).

## 📦 Data Transfer

- **JSON**: Used for REST API and modern React Native client communication.
- **TLV (Binary)**: Used for J2ME legacy client communication.
- **DTOs**: Keep them flat and simple. Avoid deep nesting for game performance.

## 🔐 Security & Anti-Cheat

- **Validation**: All packet data must be validated (e.g., coordinates within map bounds).
- **Stateless (mostly)**: Keep the session state minimal; pull player data from DB/Redis when needed.
- **Rate Limiting**: Protect socket endpoints from spam/packet flooding.
