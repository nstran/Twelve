# Game Database Design (Postgres + Dapper)

## 🏗️ Schema Design

- **Primary Models**: 
  - `Players`: ID, Username, PasswordHash, Level, EXP, Gold, Ken, ClassId.
  - `Inventories`: PlayerId, ItemId, Quantity, IsEquipped.
  - `Maps`: MapId, Name, JsonData (Tiles, Collision).
- **Integrity**: Standard PostgreSQL constraints (FKs, Unique Index on Username).
- **Performance**: Use JSONB columns for flexible data (e.g., Quest progress) to avoid excessive table joins.

## ⚡ Performance (Dapper)

- **Raw SQL**: Write optimized SQL queries. Avoid complex ORM abstractions for high-frequency game actions.
- **Connection Pooling**: Always use `NpgsqlConnection` with pooling enabled.
- **Async**: Use `QueryAsync` and `ExecuteAsync` to prevent blocking the thread pool.
- **Indexing**: Composite indexes on `(PlayerId, IsEquipped)` for fast gear lookups.

## 🔄 Migrations & Seeding

- **FluentMigrator**: Use a lightweight migration tool (FluentMigrator) for version control.
- **Seeding**: Initial data for Maps, Items, and NPC stats should be seeded via SQL scripts or JSON imports.
