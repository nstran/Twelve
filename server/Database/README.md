# Database modules — run order for a fresh PostgreSQL database

`Twelve.Server` khởi động sẽ gọi `DatabaseMigrator` và chạy lần lượt các file SQL đã nhúng trong `Twelve.Infrastructure` (tên `DB.01_` … `DB.08_`, cùng thứ tự như dưới). Chỉ cần Postgres chạy và connection string `Default` đúng; không bắt buộc chạy tay từng file nếu bạn dùng server mặc định.

Tên file theo **module** (tránh mọi thư mục đều `01_schema.sql`).

Execute SQL in this order:

1. `Accounts/accounts_schema.sql`
2. `Equipment/equipment_schema.sql` (before Players — `PlayerEquipment` FKs `EquipmentCatalog`)
3. `Players/players_schema.sql`
4. `Equipment/equipment_seed.sql`
5. `WorldMap/worldmap_schema.sql`
6. `WorldMap/worldmap_seed.sql`
7. `Monsters/monsters_schema.sql`
8. `Monsters/monsters_seed.sql`

Legacy numbered files at `Database/` root were consolidated into these modules (2026-04).
