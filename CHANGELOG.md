# CHANGELOG

## 2026-04-30

### [MAP] Sửa snap X khi air-control trên không

- `JavaCompatibleCharacterController` giữ đúng state Java `j=5/6` khi player đang nhảy/rơi nhưng bấm/trỏ trái-phải.
- `startMove`, tap-to-move và move-to-monster trong lúc airborne chỉ cập nhật hướng `k=4/8`, cộng X tức thời và commit visual position; không ép về `Running`.
- Mục tiêu: sửa lỗi nhân vật nhảy lên rồi điều hướng ngang nhưng X bị kéo/snap về vị trí đứng ban đầu.
- Nguồn suy luận: `km.java` state `5/6` xử lý vertical rồi gọi helper air-control; Java cộng dồn `t.a` từng tick, không có interpolation về điểm nhảy cũ.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.
- Kiểm tra: `node client/node_modules/typescript/bin/tsc -p client/tsconfig.json --noEmit` — passed.

### [MAP] Sửa trigger monster khi nhảy qua

- `HoaLuMapScreen` đổi encounter collision từ trigger theo trục X sang kiểm hitbox X + foot Y.
- Lưu `charFootYRef` từ controller Java-compatible; nếu player nhảy cao hơn thân monster thì không kích battle dù X overlap.
- Khi player ở gần mặt đất/chân còn chạm vùng thân monster, encounter vẫn kích bình thường.
- Nguồn suy luận: Java map actor dùng runtime hitbox `kl.t`, không phải line trigger X-only.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.
- Kiểm tra: `client\node_modules\.bin\tsc.cmd --noEmit -p client\tsconfig.json` — passed.

## 2026-04-29

### [DB] Chuẩn hóa SQL theo module (`server/Database/`)
- Gộp 15 file migration gốc thành thư mục: `Accounts/`, `Players/`, `Equipment/`, `WorldMap/`, `Monsters/`.
- Mỗi module: `{module}_schema.sql` (final shape), `{module}_seed.sql` nếu có seed.
- Thứ tự chạy và mô tả: `server/Database/README.md`.

### [BATTLE] Đầu hàng resolve như thua trận
- Menu `Đầu hàng` trong `BattleScreen` không gọi callback flee để thoát trực tiếp nữa.
- Client khóa trận bằng `result = defeat`, chạy defeat/recovery sequence và để `/battle/result` nhận HP/MP/Power hiện tại.
- Mục tiêu: server áp dụng penalty như thua trận, giữ HP đã mất thay vì hồi/reset sai trạng thái sau khi quay lại map.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` với rule reconstruction/remake cho flee/defeat.

### [MAP] Sửa tốc độ chạy, air-control khi nhảy và monster respawn
- `MapMovementCalculator` dùng trực tiếp speed Java `kl.i = min(9, 4 + level / 10)` thay vì scale xuống `1.0..2.8`.
- `CharacterController` giữ điều hướng ngang khi đang nhảy theo Java `km.java` state `5/6`.
- Khi thả trái/phải trên không, đồng bộ target nhảy về X hiện tại để tránh snap/rơi về vị trí đứng cũ.
- Scale movement ngang theo display `scale` cho run/tap/air-control/fall target timing, đồng bộ với jump impulse đã scale để sửa cảm giác chạy chậm và nhảy ngang không qua được platform.
- Battle monster lưu `MapId`/`RoomId` trong `BattleSessionState` để claim result biết đúng encounter runtime.
- `IMapMonsterRosterService`/`DbMapMonsterRosterService` hỗ trợ deactivate encounter theo hạn; quái thường respawn sau 3 phút, boss-like sau 1 ngày.
- Sau mọi kết quả battle monster, client kích hoạt recovery 3 giây: nhân vật nhấp nháy/vô địch, dừng input/movement và chặn monster overlap retrigger ngay khi quay lại map.
- Recovery sau battle không ẩn monster field; quái vẫn hiển thị/patrol/animate, chỉ bị chặn collision retrigger trong 3 giây.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md` với nguồn suy luận và nhật ký chỉnh sửa.
- Rà lại Java movement để hướng tới mục tiêu giống 100%:
  - `kl.java`: actor runtime, speed `i`, jump/fall cap `a`, hitbox `t`, state `j`, direction bitmask `k`.
  - `km.java`: input flags, key mapping, state machine movement, jump/fall air-control.
  - `kh.java`: collision flag helpers `a/b/c/d/l/m/n`, camera/bounds clamp.
  - `kf.java`: collision tile matrix `d`, tile size `32x32`.
- Bổ sung roadmap trong `MAP_SYSTEM_RECONSTRUCTION.md`: cần port nguyên state `j/k/s/t` và tile collision grid `kf.d` để đạt Java-perfect; bản hiện tại mới giống Java ở speed + air-control + no-snap X.
- Tổng hợp thêm audit Java map & move chi tiết trong `MAP_SYSTEM_RECONSTRUCTION.md`:
  - World map: `oh/fz/fg/pc/hi/og/ks`, asset contract `/m/m`, `/m/arena`, `/m/room`, `/m/lock`, `/m/lock2`, `/m/hand`, `/m/arrow`, `/roomicon`, softkey `Vào Thành`.
  - Movement: `kl/km` chi tiết hitbox `t/u/I`, state `0..8`, direction bitmask `1/2/4/8/5/6/9/10`, jump/fall/landing/slope/climb/action behavior.
- Port bước đầu Hoa Lư sang Java-compatible tile movement:
  - Thêm `client/src/engine/character/javaMapMovement.ts` với constants/state kiểu Java `kl/km`, hitbox `t`, state `j`, direction `k`, velocity `s`, helper tile flag `kh.a/b/c/d/l/m/n`, wall/ground/jump/fall/landing tick theo grid `32x32`.
  - Thêm `client/src/engine/character/JavaCompatibleCharacterController.tsx` để React Native dùng state machine mới và expose status runtime.
  - `HoaLuScene.ts` sinh collision grid `32x32` từ navigation surface/wall/ceiling hiện có làm adapter tạm cho Hoa Lư.
  - `HoaLuMapScreen.tsx` chuyển player sang controller Java-compatible; monster/render/recovery vẫn giữ logic hiện hành.
  - Bổ sung truyền `playerLevel` trực tiếp vào controller để công thức Java `kl.i/kl.a` lấy đúng level thay vì suy ngược từ speed.
  - Bổ sung truyền `collisionGrid` Hoa Lư vào `JavaCompatibleCharacterController`; controller ưu tiên tile probing `kf.d` cho ground support, landing, wall side-check và ceiling/head-hit trước khi fallback surface.
  - Kiểm tra: `client\node_modules\.bin\tsc.cmd --project client\tsconfig.json --noEmit` — passed.
- Sửa Java-compatible movement dùng fixed Java tick:
  - Thêm `JAVA_MAP_TICK_MS = 40` và `JAVA_MAP_MAX_STEPS_PER_FRAME = 3` trong `javaMapMovement.ts`.
  - `JavaCompatibleCharacterController.tsx` bỏ phụ thuộc `MOVE_TICK_MS = 16`/60fps của controller RN cũ; rAF chỉ còn làm scheduler, physics chạy fixed-step accumulator 40ms.
  - Mỗi Java physics step dùng công thức tick từ `km.java`: chạy ngang `x += kl.i`, jump `y -= s; s--`, falling `y += s; s += 2` cap `kl.a`.
  - Không export `CharacterController` cũ qua `client/src/engine/character/index.ts` để runtime map không vô tình dùng lại interpolation/timing cũ.
  - Mục tiêu: sửa lỗi chạy/rơi nhanh hơn Java khoảng `40 / 16 = 2.5` lần do áp công thức Java px/tick vào loop 16ms.
- Sửa air-control trong `JavaCompatibleCharacterController`:
  - khi đang `jumping/falling`, lệnh trái/phải cập nhật input flags và direction bitmask Java `k=4/8` nhưng không ép state về `running`;
  - tap-to-move/move-to-monster chỉ bật `running` khi player đang `idle/running/landing`, còn trên không giữ state `j=5/6` để helper air-control cộng X theo tick như `km.java`;
  - kiểm tra lại `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` — passed.
- Bổ sung plan chi tiết trong `MAP_SYSTEM_RECONSTRUCTION.md` cho hướng tự author collision map khi không có `kf.d` gốc:
  - chốt source chính tương lai là authored Java-compatible collision grid `32x32`, không suy physics trực tiếp từ art/background;
  - ghi rõ flag contract `0/8/16/32/65/66`, symbol text map `.`, `G`, `V`, `R`, `L`, `H`, `>`, `<`, `B`;
  - đề xuất files `javaCollisionMap.ts`, `HoaLuCollisionMap.ts`, `CollisionDebugOverlay.tsx`;
  - mô tả workflow vẽ Hoa Lư theo từng bước: ground, platform, wall, slope, climb, portal;
  - yêu cầu debug overlay hiển thị grid, hitbox, sample points, state `j/k/s/t`;
  - bổ sung milestone triển khai collision authoring foundation, debug overlay, topology Hoa Lư, slope/climb, portal/runtime room.

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
