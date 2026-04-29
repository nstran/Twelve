# CHANGELOG

## 2026-04-29

### [DB] Chuẩn hóa SQL theo module (`server/Database/`)
- Gộp 15 file migration gốc thành thư mục: `Accounts/`, `Players/`, `Equipment/`, `WorldMap/`, `Monsters/`.
- Mỗi module: `{module}_schema.sql` (final shape), `{module}_seed.sql` nếu có seed.
- Thứ tự chạy và mô tả: `server/Database/README.md`.

## 2026-04-28

### [MONSTER] Hoàn thiện monster module — DB schema + asset seed + client types
- Tạo migration `Database/Monsters/monsters_schema.sql` với 4 bảng tách biệt theo Java runtime split:
  `Monsters`, `MonsterSpawns`, `MonsterBattles`, `MonsterRosters`.
  Mỗi bảng có JSONB cho `frame_paths`, `skills`, `appearance`.
- Tạo seed `Database/Monsters/monsters_seed.sql` — seed toàn bộ 78 confirmed asset entries
  cho 8 species (1000-1007) từ `client/assets/monster/index.csv`.
  Convention: `MONSTER_{speciesCode}_SLOT_{slot}`.
- Cập nhật `MonsterAssetCatalogEntry` thêm `FramePaths`.
- Cập nhật `MapMonsterRosterEntry` thêm `AssetCatalogId` + `FramePaths`.
- Cập nhật `MonsterBattleInstance` thêm `AssetCatalogId` + `FramePaths`.
- Cập nhật `MonsterCatalogSeed.cs` với toàn bộ 78 asset catalog entries.
- Cập nhật `/map/monster-roster` endpoint trả `assetCatalogId` + `framePaths`.
- Cập nhật `MonsterBattleBootstrapService` truyền asset info vào battle instance.
- Tạo `client/src/types/monster.types.ts` với:
  `MonsterRosterEntry`, `MonsterBattleBootstrap`, `MonsterBattleDelta`,
  `MonsterVisualFamily`, `resolveVisualFamily()`, `resolveIqLabel()`.
- Tài liệu `MONSTER_SYSTEM_RECONSTRUCTION.md` cập nhật backlog.

**Nguyên tắc bảo toàn:**
- Asset / Spawn / Battle / Roster tách biệt — không gộp.
- `monsterKey` là identity gameplay/runtime, khác `asset_id`.
- Map spawn data lightweight giống Java `jo`.
- Battle truth từ battle template / bootstrap giống Java `lh/lv`.
- Client không quyết định monster stats/skills/battle result.

**Files chính:**
- `server/Database/Monsters/monsters_schema.sql`
- `server/Database/Monsters/monsters_seed.sql`
- `server/Twelve.Core/Monsters/MonsterContracts.cs`
- `server/Twelve.Application/Monsters/MonsterCatalogSeed.cs`
- `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
- `server/Twelve.Server/Program.cs`
- `client/src/types/monster.types.ts`
- `MONSTER_SYSTEM_RECONSTRUCTION.md`

### [MAP] Spec map/world runtime
- Rút gọn `MAP_SYSTEM_RECONSTRUCTION.md`, chốt spec cốt lõi world map/runtime map và phần còn thiếu cho map bên ngoài.

### [BATTLE] Gameplay bàn cờ v1
- Xác nhận damage kiếm trắng/đỏ đã scale theo `MinDamage/MaxDamage`; số `15` là kết quả khi `AttackRoll ≈ 15`, không phải hardcode.
- Áp khắc hệ local cho board sword theo vòng server `100/112/92`.
- Sửa cộng lượt: mỗi distinct match group `>=4` cộng `+1`, cascade thật cũng được tính.
- Sau khi tiêu thụ extra turn, nếu còn lượt thì UI hiện lại Còn X lượt; về `0` thì ẩn badge.
- Hoạt ảnh đánh thường phát 4 nhịp rồi mới quay về; damage chỉ apply 1 lần.
- Badge lượt còn lại dùng text trắng để dễ đọc.

**Files chính:**
- `client/src/screens/battle/BattleScreen.tsx`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/hooks/useBattleSwordAttacks.ts`
- `client/src/screens/battle/ui/BattleScreen.panel.tsx`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`

**Kiểm tra:** `npx --prefix client tsc -p client/tsconfig.json --noEmit` — passed.

## 2026-04-27

### [BATTLE] Khóa gameplay bàn cờ v1 theo Java + gameplay memory
- Board active dùng pool `0,1,2,3,4,5,6,8`; bỏ `chess7`.
- `chess0` kiếm trắng và `chess8` kiếm đỏ cùng category kiếm.
- Kiếm đỏ trigger-on-touch, nổ `3x3`, chain sang kiếm đỏ khác và apply item trong vùng.
- Match `>=4` cộng lượt theo số distinct group; không tạo natural special mặc định.
- HP/MP/Nộ/EXP/Gold từ board tách thành pending reward/effect.
- Damage kiếm chuyển từ hardcode sang scale theo `BattleAttackProfile { minDamage, maxDamage }`.
- Sửa result-lock/victory fallback và guard monster turn sau khi enemy HP về `0`.
- Gold/KEN là raw gold; Quan = `floor(rawGold / 10000)`.

**Files chính:**
- `client/src/screens/battle/core/BattleScreen.shared.ts`
- `client/src/screens/battle/core/BattleScreen.logic.ts`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/hooks/useBattleMonsterTurn.ts`
- `client/src/screens/battle/core/BattleScreen.packetResolver.ts`
- `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`

### [PLAYER_CHARACTER] Dọn runtime/schema nhân vật
- Xóa `AppearanceJson` duplicate khỏi runtime/repository/schema; thêm migration drop cột.
- Xóa derived stats cache khỏi DB, chuyển sang `PlayerStatPipeline.Calculate()` on-the-fly.
- Giữ các field bonus stat/equipment pipeline cần thiết.

**Kiểm tra:** `dotnet build Twelve.sln` — passed.

## 2026-04-26

### [BATTLE] Resource/damage chuyển sang server authority
- Battle bootstrap trả base resource/damage từ server.
- `BattleTurnEngine` dùng stat server-owned: `MinDamage/MaxDamage`, `Defense`, `HitRate`, `DodgeRate`, `CriticalDamage`.
- Áp khắc hệ battle v1 `112%/100%/92%`.
- Battle result reset MP/Power sau trận; HP PvE thắng có thể giữ attrition.
- Ghi nhận mapping 8 icon board, tắt natural special mặc định, pending EXP/Gold chỉ chốt khi thắng.
- Fallback gem resource theo semantic per-color để MP/Âm Dương không hồi nhầm HP.

**Kiểm tra:** `dotnet build Twelve.sln`, `npx tsc -p client/tsconfig.json --noEmit` — passed.

### [PLAYER_CHARACTER] MP/Power là tài nguyên tạm battle
- Nhân vật mới seed `Mp = 0`, `Power = 0`.
- `/battle/result` reset MP/Power về `0`.
- HP sau thắng PvE có thể giữ current HP.

## 2026-04-25

### [PLAYER_STATS] Chuẩn hóa công thức 6 chỉ số theo Java
- Port công thức status theo `jq/js/jr`.
- Tách `MinDamage` / `MaxDamage`.
- Status `Tấn Công` hiển thị `runtime.minDamage` như Java cũ.
- Bỏ off-element soft cap khỏi tầng status/derived stat.

**Kiểm tra:** `dotnet build Twelve.sln`, `npx tsc -p client/tsconfig.json --noEmit` — passed.

### [MAP] World-map catalog và UI selection
- Chuyển catalog 17 địa danh world-map sang server theo Java `og/oh`.
- Client fetch `/map/world-catalog`, fallback local.
- Sửa pan/cursor/HUD world-map, chỉ Hoa Lư mở runtime thật.
- Bỏ Skia fallback map gây CanvasKit crash.

### [UI] Font/theme/menu/loading/debug cleanup
- Tạo `GameFonts`/`GameTheme`.
- Áp style chung cho Login/Register/PopupMenu/Softkey/Calendar/HUD.
- Bỏ global loading modal khỏi gameplay hoặc polling nền.
- Xóa console runtime trong `client/src`.

### [PVP/BATTLE] Đồng bộ board PvP
- Seed board deterministic từ `sessionId`.
- Active player không bị polling snapshot ghi đè local cascade.
- Session-sync bump `TurnSeq` khi board/bars/turn thay đổi.
- Disable AI/monster hook trong PvP thật.

### [PLAYER_CHARACTER] Tạo nhân vật và progression v1
- Base stats nhân vật mới `(10,10,10,10)`.
- Initial free points = `0`; level-up cộng `+5` điểm.
- EXP curve/spec Level 250 ghi trong tài liệu player-character.
- Map movement speed bám Java level formula.

## Ghi chú

CHANGELOG đã được rút gọn để giữ các mốc chính. Chi tiết triển khai nằm trong tài liệu reconstruction tương ứng và lịch sử git.
