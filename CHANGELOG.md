# CHANGELOG

CHANGELOG đã được rút gọn để chỉ giữ các mốc quan trọng theo module. Chi tiết đầy đủ nằm trong tài liệu reconstruction tương ứng và lịch sử git.

## 2026-05-03

### [EQUIPMENT] Inventory item icon asset wiring for equipment materials

- Cập nhật UI inventory item theo asset đã cắt từ spritesheet:
  - chuẩn hóa tên file trong `client/assets/equipment/items/` theo icon đã xác nhận: HP, MP, trứng đà điểu, búa sửa đồ, Kim Thạch, Huyết Thạch, Bùa 1/2/3;
  - giữ icon chưa xác minh gameplay dưới dạng `pending_*.png`, không suy diễn tên domain.
- Cập nhật `client/src/screens/map/core/MapCharacterDialogs.tsx`:
  - thêm resolver icon item theo `CharacterInventoryItem.iconKind`;
  - map `potion_red`, `potion_blue`, `peach`, `hammer`, `kim_thach`, `huyet_thach`, `charm_1`, `charm_2`, `charm_3`.
- Giữ boundary:
  - repair hammer `30099` là policy/user confirmation đã chốt ngày `2026-05-03`;
  - Kim Thạch/Huyết Thạch/Bùa mới wire icon/material identity UI, chưa consume/roll upgrade thật vì thiếu danh sách đá/bùa/tỉ lệ Java gốc.

### [EQUIPMENT] Runtime API/client wiring + upgrade skeleton gate

- Tiếp tục Phase equipment server authority theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật runtime API/server:
  - thêm contract `PlayerUpgradeEquipmentRuntimeRequest`;
  - expose `UpgradeEquipment(...)` qua `IPlayerRuntimeService`;
  - thêm endpoint `POST /player/runtime/equipment/upgrade`;
  - endpoint hiện là skeleton an toàn: validate ownership/item tồn tại, reject nếu equipment đang mặc, không mutate item/material/durability khi chưa có danh sách đá/bùa gốc.
- Cập nhật equipment view:
  - `PlayerEquipmentItemView` thêm `CanUpgrade`, `UpgradeStatus`;
  - `CanUpgrade=false` khi item đang mặc; roll upgrade thật vẫn pending.
- Cập nhật client:
  - TypeScript model/API đồng bộ `canUpgrade`, `upgradeStatus`, `upgradeEquipment(...)`;
  - UI inventory bỏ chặn mặc đồ hỏng, đúng policy broken item vẫn mặc được;
  - repair hammer UI đổi sang itemId `30099`;
  - upgrade/rao bán/drop equipment bị disable khi item đang mặc.
- Giữ boundary:
  - Java evidence: `ll.e`, `ll.n`, `ll.p/tag 139`, `ll.q/tag 144`, `da.java p != 0`;
  - remake policy: upgrade/trade/rao bán/drop yêu cầu tháo đồ trước; broken item vẫn mặc được nhưng không cộng stat/effect;
  - chưa consume đá/bùa, chưa roll success/fail/destroy, chưa mở combat formula cho special stats pending.

### [EQUIPMENT] Repair flow API hardening + equipment state view

- Tiếp tục Phase equipment server authority theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật runtime contract/view:
  - `PlayerEquipmentItemView` expose `IsBroken`, `ContributesStats`, `CanRepair`.
  - TypeScript `CharacterEquipmentItem` đồng bộ các field state mới.
- Cập nhật repair server flow:
  - chỉ nhận repair hammer `30099`;
  - yêu cầu inventory còn quantity `> 0`;
  - consume đúng `1` hammer;
  - restore `Durability = MaxDurability`;
  - không trừ Quan.
- Cập nhật equipment state boundary:
  - broken item vẫn mặc được nhưng `ContributesStats=false`;
  - `CanRepair` chỉ là state view từ durability hiện tại, chưa thêm template policy `IsRepairable` cho Luyện Ngục vì thiếu template/item evidence rõ.
- Giữ boundary:
  - Java evidence: `ll.p/tag 139`, `ll.q/tag 144`, `da.java p != 0`;
  - remake policy: user chốt ngày `2026-05-03` repair bằng 1 búa `30099`, hồi full durability, không mất Quan;
  - chưa mở combat formula cho special stats pending.

### [EQUIPMENT/BATTLE] Battle-end durability loss authority

- Tiếp tục Phase equipment server authority theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật `server/Twelve.Application/Battle/BattleResultService.cs`:
  - thắng trận: equipment đang mặc mất `1` durability;
  - thua trận/đầu hàng: equipment đang mặc mất `3` durability;
  - chỉ giảm item `IsEquipped && Durability > 0`, clamp durability về `0`;
  - đồ hỏng vẫn giữ equipped state nhưng stat pipeline recalc để bỏ qua aggregate theo broken-stat gate;
  - victory clamp lại HP theo `MaxHp` sau recalc để tránh HP vượt max khi equipment vừa hỏng.
- Giữ boundary:
  - Java evidence: `ll.p/tag 139`, `ll.q/tag 144`, `da.java` chỉ cộng stat khi `p != 0`;
  - remake policy: user chốt ngày `2026-05-03` thắng trừ `1`, thua trừ `3`;
  - chưa áp durability loss riêng cho `PvpShadow`; chưa mở combat formula cho special stats pending.

### [EQUIPMENT] Equip/unequip authority cleanup + packet stat recalculation gate

- Tiếp tục backend equipment Phase tiếp theo theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật domain/runtime:
  - `PlayerEquipmentSlot` giữ raw value gameplay đang chốt: `Armor=0`, `Weapon=1`, `Helmet=2`, `Ring=3`, `Wing=8`.
  - `PlayerEquipmentLocation` đặt tên trạng thái `Inventory/Equipped` nhưng không đổi boundary lưu `IsEquipped`.
  - Equip validation chỉ mở 5 slot gameplay Phase hiện tại theo `ll.e`.
- Cập nhật rule server authority:
  - Đồ hỏng `Durability <= 0` vẫn mặc được, không còn bị reject khi equip.
  - Clone/preview/commit loadout giữ nguyên durability instance.
  - Packet handler phân điểm tiềm năng cũng lọc equipment `IsEquipped && Durability > 0` khi recalc stat, tránh bypass broken-stat gate.
  - Wing `e=8` tham gia aggregate như equipment hợp lệ khi còn durability.
- Không mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`; các stat này vẫn pending combat evidence.

### [EQUIPMENT] Durability instance + repair hammer 30099 + broken stat gate

- Tiếp tục backend equipment Phase 1 theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật durability thành dữ liệu instance lưu DB/entity riêng:
  - `server/Database/Players/players_schema.sql`
  - `server/Twelve.Core/Entities/PlayerAggregate.cs`
  - `server/Twelve.Infrastructure/Repositories/PlayerAggregateRepository.cs`
- Cập nhật `PlayerContentCatalog.cs`:
  - equipment mới/starter khởi tạo `Durability = MaxDurability`;
  - `ToEquipmentView` đọc durability từ instance;
  - `RestoreDurability` hồi full durability trên entity;
  - `GetEquippedModifiers` bỏ qua equipment `Durability <= 0`, giữ đúng policy đồ hỏng vẫn mặc nhưng không cộng stat/effect;
  - repair hammer dùng đúng itemId `30099`.
- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` với Java evidence (`ll.p/tag 139`, `ll.q/tag 144`, `da.java p != 0`) và remake policy tương ứng.
- `dotnet build Twelve.sln --no-restore` pass: `0 Warning(s), 0 Error(s)`.

### [EQUIPMENT] Chốt gameplay policy cho implementation tiếp theo

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` sau khi user chốt các quyết định gameplay cốt lõi:
  - Cánh `e=8` có cộng stat và có slot UI riêng trong remake.
  - Equipment hỏng `p == 0` vẫn mặc được nhưng không cộng stat/effect.
  - Repair dùng 1 búa `30099`, hồi full durability, không mất Quan.
  - Đồ không sửa được trước mắt chỉ áp dụng nhóm Luyện Ngục khi template/item được đánh dấu rõ.
  - Upgrade yêu cầu tháo đồ khỏi người; đồ hỏng vẫn nâng cấp được và durability giữ nguyên.
  - Chưa bật roll upgrade thật cho đến khi có danh sách đá/bùa gốc.
  - Upgrade destroy outcome xóa/mất hẳn equipment instance.
  - Trade/rao bán yêu cầu tháo đồ trước.
  - Shop/drop roll stat random trong range template.
- Ghi rõ các điểm trên là `Remake policy / user confirmation`, không phải Java server evidence.
- Chỉ sửa tài liệu `.md`, không sửa code server/client nên không chạy build.

### [EQUIPMENT] Thêm code-readiness gate trước implementation tiếp theo

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` sau khi rà lại toàn bộ `Pending/Unverified`.
- Kết luận tài liệu đã đủ để code **Phase 1 equipment foundation** theo Java evidence + remake policy đã tách.
- Ghi rõ phần chưa được code như Java gốc:
  - combat special stats `DamageAbsorb/Pierce/Block/Revive/HpPercent`;
  - wing/mount runtime effect;
  - original upgrade material catalog;
  - market tax;
  - serializer byte-perfect nâng cao.
- Thêm 5 câu hỏi cần user chốt trước khi implementation lớn: phạm vi server/client, upgrade, combine, seed drop/shop, và rule safety cho `e=8`.
- Chỉ sửa tài liệu `.md`, không sửa code server/client nên không chạy build.

### [EQUIPMENT/BATTLE] Khóa boundary equipment special stats

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` và `BATTLE_SYSTEM_RECONSTRUCTION.md` sau audit bổ sung Java client.
- Xác nhận `lb.j/k/l/m/o` có parser/UI label evidence:
  - `200` hấp thu sát thương;
  - `201` đánh xuyên giáp;
  - `202` cản đòn;
  - `203` hồi sinh;
  - `221` sinh lực %.
- Chỉ `lb.n` / tag `204` hiện có evidence aggregation rõ trong status (`baseAttack * percent / 100`).
- Chuyển combat formula cho `DamageAbsorb`, `ArmorPierce`, `Block`, `Revive`, `HpPercent` sang `Pending/Unverified`; không tự chèn vào battle final damage order nếu chưa có Java/server evidence hoặc remake policy được chốt riêng.
- Chỉ sửa tài liệu `.md`, không sửa code server/client nên không chạy build.

### [EQUIPMENT] Hoàn tất audit/plan phục dựng hệ thống trang bị trước khi code

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` thành spec chính cho hệ thống trang bị.
- Đã gom evidence Java client core:
  - Data/protocol: `ll`, `lb`, `ky`, `ks`.
  - Inventory/equip: `go`, `gp`, `hh`, `cz`, `dc`, `fw`, `hg`.
  - Visual/compositor: `mb`.
  - Shop/repair/forge/trade: `ia`, `gx`, `lq`, `hl`, `id`, `ho`, `of`.
  - Status/stat display: `com.mg.sq.a`, `da`.
- Chốt các rule quan trọng:
  - `ll.e` là slot authoritative; `ll.n` là resource ID authoritative.
  - Icon equipment: `band = resId - resId % 10`, `iconId = band + 98`.
  - Stats block `lb` đủ 15 fields; `AttackPercent` cộng theo `baseAttack * percent / 100`.
  - Equipment durability `p == 0` không cộng stat/effect.
  - Sai giới tính/chưa đủ level không mặc được.
  - Equipment đang mặc không dùng để trade/upgrade/combine trực tiếp.
- Chốt hướng server remake:
  - Tách `EquipmentTemplates` và `PlayerEquipment`.
  - Equipment instance có key riêng, rolled stats, durability riêng, enhancement riêng.
  - Repair dùng 1 loại búa, icon `30099`, consume 1 búa để hồi đầy durability.
  - Upgrade max `+15`, hard-mode rate từ `+10..+15` là `5% → 1%`, fail có thể tụt cấp hoặc destroy.
  - Combine phải làm bằng `CombineRecipes` config/table, không placeholder.
- Dọn tài liệu:
  - `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` giữ spec/evidence.
  - `CHANGELOG.md` chỉ giữ summary quan trọng.

### [EQUIPMENT] Backend equipment foundation Phase 1

- Thêm foundation server cho equipment bám Java client `ll/lb/ky`:
  - `PlayerEquipmentDefinition.cs` định nghĩa `EquipmentSlotIds` chỉ gồm 5 tên gameplay rõ ràng: `Armor`, `Weapon`, `Helmet`, `Ring`, `Wing`; không khai báo các hằng số slot thừa/`Reserved`/`Unused` trong code phase đầu.
  - `PlayerRuntimeContracts.cs` trả thêm equipment view metadata cho client.
  - `PlayerContentCatalog.cs` và `EquipmentCatalogRepository.cs` load catalog equipment từ database.
- Thêm schema/seed nền:
  - `server/Database/Equipment/equipment_schema.sql`
  - `server/Database/Equipment/equipment_seed.sql`
- Giữ các rule đã chốt:
  - Slot gameplay lấy từ 5 mapping đang dùng của `ll.e`; resource/icon lấy từ `ll.n`.
  - Rank không tạo enum/constant `RankN`; giữ raw `ll.m` integer và chỉ switch trực tiếp theo Java `ll.a(rank)` khi cần mapping màu/UI (`0/1/2/3/4/7/8`).
  - Durability `0` là broken, không xóa item.
  - Giữ raw Java fields (`Rank`, `Gender`, `RepairCost`) và bỏ metadata policy chưa đủ evidence khỏi Phase 1: `IsRepairable`, `RepairBlockReason`, `IsUpgradeable`, `InventoryCapacityCost`.
  - Seed chỉ là starter/test tối thiểu, không tự seed đại trà asset khi chưa có dump template gốc.
- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` với trạng thái Phase 1, quyết định 5 nhóm equipment hiện tại và nhật ký code đã sửa.
- `dotnet build Twelve.sln` pass sau cleanup: `0 Warning(s), 0 Error(s)`.

### [EQUIPMENT] Safety cleanup tài liệu phục dựng

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` để tránh hiểu nhầm tài liệu là Java server gốc hoàn chỉnh.
- Thêm rule an toàn:
  - Section client-side Java là evidence mạnh nhất.
  - Section server-side gameplay/remake policy phải được đánh dấu nguồn rõ ràng.
  - Không tự thêm field/policy như `IsRepairable`, `RepairBlockReason`, `IsUpgradeable`, `InventoryCapacityCost` nếu chưa có template/server evidence.
- Bỏ tuyên bố coverage theo phần trăm tuyệt đối; giữ trạng thái là đã gom phần equipment client-side core chính ở mức cao nhưng còn cần server template dump/combat audit/byte-perfect packet test.
- Bổ sung evidence equipment có hệ/nguyên tố:
  - `ll.f` / tag `15` là element icon ID.
  - Screenshot user ngày `2026-05-03` xác nhận icon hệ hiển thị trước tên equipment như `Kim Đao (Luyện Ngục)`.
  - Tạm chốt đây là metadata/UI icon; chưa suy diễn combat effect nếu chưa audit battle source.

### [EQUIPMENT] Gộp cây asset equipment

- Gộp `client/assets/equipment/` về các nhóm lớn:
  - `default`, `armor`, `weapon`, `helmet`, `accessory`, `premium`, `ui`.
- Tạo/cập nhật manifest:
  - `client/assets/equipment/index.csv`
  - `client/assets/equipment/equipment_manifest.json`
- Tổng PNG sau gộp: `974`.
- Rule giữ nguyên: folder vật lý chỉ là lookup hint; gameplay slot vẫn lấy từ `ll.e`.

### [EQUIPMENT] Bổ sung audit slot đặc biệt e=8/e=4

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` với evidence Java client cho slot đặc biệt:
  - `e=8`: `hh.java` không đưa vào 6 ô equipped `F`, menu dùng `"Dùng"`; phase hiện tại map remake `e=8 -> Wing` nhưng render/stat/effect vẫn pending evidence.
  - `e=4`: `lh.b()` set `lh.ad` mount/riding flag; `mb.java` dùng flag này cho pose/render branch, không phải layer armor/weapon/helmet thường.
- Chốt an toàn Phase 1:
  - Không khai báo gameplay slot `Mount`.
  - Không suy diễn `e=4` thành cánh.
  - Cánh chỉ lưu DB/icon/detail trước khi có template/asset/server evidence rõ hơn.

## 2026-04-30

### [MAP] Ổn định movement Hoa Lư theo Java-compatible runtime

- Hoàn thiện nhiều vòng tuning movement/collision/render cho Hoa Lư:
  - fixed Java tick;
  - runtime hitbox kiểu `kl.t`;
  - jump/fall theo công thức Java;
  - air-control khi nhảy/rơi;
  - collision grid/surface adapter;
  - monster runtime collision box;
  - visual commit/interpolation được rollback/tuning để giảm jitter.
- Chốt policy reconstruction:
  - runtime/collision là source of truth;
  - visual smoothing chỉ là lớp render;
  - các tuning chưa có Java source exact phải ghi rõ là reconstructed policy.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

## 2026-04-29

### [MAP] Port bước đầu Hoa Lư sang Java-compatible tile movement

- Thêm nền `javaMapMovement` và `JavaCompatibleCharacterController`.
- Dùng state/field kiểu Java: `j/k/s/t`, speed/jump formula, collision probing theo grid `32x32`.
- `HoaLuMapScreen` chuyển sang controller Java-compatible.
- Bổ sung roadmap author collision map khi không có `kf.d` gốc.
- TypeScript check đã pass ở thời điểm chỉnh sửa.

### [MAP] Sửa battle trigger/monster respawn/recovery

- Battle monster lưu `MapId/RoomId` để claim result đúng encounter runtime.
- Monster roster hỗ trợ deactivate/respawn.
- Sau battle thêm recovery/invincibility ngắn để tránh retrigger ngay khi quay lại map.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [BATTLE] Đầu hàng xử lý như thua trận

- Menu `Đầu hàng` chuyển sang defeat/recovery sequence.
- `/battle/result` nhận trạng thái HP/MP/Power hiện tại để server áp penalty giống thua trận.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`.

### [DB] Chuẩn hóa SQL theo module

- Gộp migration/seed về `server/Database/` theo module:
  - `Accounts`
  - `Players`
  - `Equipment`
  - `WorldMap`
  - `Monsters`
- Cập nhật `server/Database/README.md`.

## 2026-04-28

### [MONSTER] Hoàn thiện monster module foundation

- Tạo schema/seed monster:
  - `Monsters`
  - `MonsterSpawns`
  - `MonsterBattles`
  - `MonsterRosters`
- Seed 78 asset entries cho 8 species từ `client/assets/monster/index.csv`.
- API map/battle trả thêm asset/frame info.
- Tạo type client cho monster roster/battle bootstrap/delta.
- Cập nhật `MONSTER_SYSTEM_RECONSTRUCTION.md`.

### [BATTLE] Gameplay bàn cờ v1

- Board active dùng pool đã chốt, bỏ `chess7`.
- Kiếm đỏ trigger/nổ/chain theo policy reconstruction.
- Match `>=4` cộng lượt.
- Damage kiếm scale theo `BattleAttackProfile`.
- Sửa result-lock/victory fallback và guard monster turn sau khi enemy HP về `0`.
- TypeScript check đã pass ở thời điểm chỉnh sửa.

### [MAP] Spec map/world runtime

- Rút gọn `MAP_SYSTEM_RECONSTRUCTION.md`.
- Chốt spec cốt lõi world map/runtime map và phần còn thiếu cho map bên ngoài.

## 2026-04-27

### [BATTLE] Khóa gameplay bàn cờ v1 theo Java + gameplay memory

- Chốt board icon pool, kiếm trắng/đỏ, kiếm đỏ nổ `3x3`, chain và extra-turn.
- Tách pending reward/effect HP/MP/Nộ/EXP/Gold.
- Damage kiếm chuyển từ hardcode sang scale theo profile.
- Server battle board service cập nhật theo policy reconstruction.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`.

### [PLAYER_CHARACTER] Dọn runtime/schema nhân vật

- Xóa duplicate appearance/runtime cache không cần thiết.
- Derived stats chuyển sang tính on-the-fly bằng stat pipeline.
- `dotnet build Twelve.sln` đã pass ở thời điểm chỉnh sửa.

## 2026-04-26

### [BATTLE] Resource/damage chuyển sang server authority

- Battle bootstrap trả base resource/damage từ server.
- `BattleTurnEngine` dùng stat server-owned.
- Áp khắc hệ battle v1 `112%/100%/92%`.
- Battle result reset MP/Power sau trận.
- Build/check đã pass ở thời điểm chỉnh sửa.

### [PLAYER_CHARACTER] MP/Power là tài nguyên tạm battle

- Nhân vật mới seed `Mp = 0`, `Power = 0`.
- `/battle/result` reset MP/Power về `0`.

## 2026-04-25

### [PLAYER_STATS] Chuẩn hóa công thức 6 chỉ số theo Java

- Port công thức status theo Java source `jq/js/jr`.
- Tách `MinDamage` / `MaxDamage`.
- Status `Tấn Công` hiển thị `runtime.minDamage`.
- Build/check đã pass ở thời điểm chỉnh sửa.

### [MAP] World-map catalog và UI selection

- Chuyển catalog 17 địa danh world-map sang server theo Java `og/oh`.
- Client fetch `/map/world-catalog`, fallback local.
- Chỉ Hoa Lư mở runtime thật ở giai đoạn này.

### [UI] Font/theme/menu/loading/debug cleanup

- Tạo `GameFonts`/`GameTheme`.
- Áp style chung cho các màn chính.
- Dọn loading/debug runtime không cần thiết.

### [PVP/BATTLE] Đồng bộ board PvP

- Seed board deterministic từ `sessionId`.
- Active player không bị polling snapshot ghi đè local cascade.
- Tắt AI/monster hook trong PvP thật.

### [PLAYER_CHARACTER] Tạo nhân vật và progression v1

- Base stats nhân vật mới `(10,10,10,10)`.
- Level-up cộng `+5` điểm.
- EXP curve/spec Level 250 ghi trong tài liệu player-character.