# CHANGELOG

CHANGELOG đã được rút gọn để chỉ giữ các mốc quan trọng theo module. Chi tiết đầy đủ nằm trong tài liệu reconstruction tương ứng và lịch sử git.

## 2026-05-04

### [EQUIPMENT] Priority Java-alignment fixes: slot mapping, stat model, inventory capacity, repair gate

- Sửa `PlayerEquipmentSlot.Ring` từ raw `3` (sai) sang `5` theo Java `ll.e` evidence.
- Mở rộng `PlayerStatModifier` đủ 15 field `lb.java` (`a..o`); chỉ aggregate 10 field có status evidence, 5 special stats (`j/k/l/m/o`) lưu/sum cho round-trip/display.
- `EquipmentStatModifierParser` parse thêm 5 special stat tags (`200/201/202/203/221`).
- `PlayerContentCatalog.SumModifiers` và `BuildEquipmentRawJson` serialize đủ 15 field.
- `CanRepair` thêm gate `RepairCost > 0` theo Java `ll.c()`.
- `IsInventoryFullForNewEquipment` bám Java `go.b()`: chỉ đếm equipment bag, item stack theo quantity khi `StackCap > 1`.
- Files: `PlayerAggregate.cs`, `PlayerStatPipeline.cs`, `EquipmentStatModifierParser.cs`, `PlayerContentCatalog.cs`, `PlayerRuntimeService.cs`.

### [MAP] Shared side-scroll map shell

- Common hóa map side-scroll để tránh copy [`HoaLuMapScreen`](client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx) cho nhiều bản đồ:
  - tạo [`SideScrollMapScreen`](client/src/screens/map/shared/SideScrollMapScreen.tsx) làm shell runtime dùng chung;
  - [`HoaLuMapScreen`](client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx) giờ chỉ là wrapper export lại common screen;
  - tách mission/NPC/PVP popup sang `client/src/screens/map/shared/components/*`;
  - tách render/runtime actor sang `client/src/screens/map/shared/components/MapActorField.tsx` và `client/src/screens/map/shared/runtime/MapActorRuntime.ts`;
  - tách socket/mission listeners, PVP state/actions và monster loop sang `client/src/screens/map/shared/hooks/*` theo hướng copy/move logic, không đổi hành vi runtime;
  - thu gọn [`SideScrollMapScreen.styles.ts`](client/src/screens/map/shared/SideScrollMapScreen.styles.ts) chỉ còn shell/debug/background style;
  - bổ sung `decorObjects` trong scene config để map riêng định nghĩa cây/cỏ/gò đất theo layer mà shared screen chỉ render theo data.
- Boundary:
  - Khung cảnh, đất/cỏ, bố cục, surface/collision, decor objects, monster spawn và NPC roster khác nhau đi qua scene config/DB roster, không copy nguyên màn hình cho từng map.
  - Nếu map sau có UI skin/layout khác đã có Java/data evidence, thêm theme/config override rõ ràng thay vì fork `SideScrollMapScreen`.

### [NPC/MISSION] Mission dialog polish and Hoa Lu E2E checklist

- Nâng cấp Mission UI trên map Hoa Lư:
  - menu `Nhiệm Vụ` mở dialog list/detail riêng, request mission list/detail từ server;
  - detail panel hiển thị mô tả, mục tiêu, reward lines, và nút nhận nhiệm vụ theo trạng thái server;
  - dialog được chặn input map/softkey giống các popup lớn khác để tránh overlap thao tác.
- Thêm checklist manual end-to-end ở `plans/hoa-lu-mission-end-to-end-checklist.md` cho 3 nhiệm vụ Hoa Lư: nhận, kill/talk progress, complete, claim reward, chống double-claim, reconnect persistence.
- Boundary:
  - UI là remake polish gần flow Java `hr`/`hb`, chưa clone pixel-perfect asset `questnotifyicon`.
  - Checklist ghi rõ mission catalog/reward/claim policy hiện là remake seed data.

### [NPC/MISSION] Client mission toast queue and battle progress UI

- Hoàn thiện phần RN hiển thị notify nhiệm vụ trên map Hoa Lư:
  - `MapMission.reducer` thêm toast queue riêng cho task notify, mission notify/update và battle progress;
  - `HoaLuMapScreen` render stack toast nhiệm vụ trên map, không trộn với NPC talk dialog;
  - `BattleResultRewardResponse.missionUpdates` được giữ ở `App` khi rời battle rồi consume trên map để hiện tiến độ `KillMonster` sau khi claim kết quả trận.
- Boundary:
  - UI toast là remake polish dựa trên flow Java `nu`/`hr`/`hb`, chưa phải bản clone pixel-perfect của popup Java gốc.
  - Dữ liệu objective/reward vẫn đến từ server/DB seed, client không hardcode monster id, NPC id hoặc số lượng.

### [NPC/MISSION] Mission progress notification/update packets

- Thêm flow notify/update nhiệm vụ khi objective thay đổi:
  - `MissionProgressUpdate` trả mission/task vừa thay đổi sau khi cộng progress;
  - `MissionPacketFactory` build command `34` task notify, command `38` mission update, command `35` mission complete/reward notify;
  - `NpcTalkHandler` gửi notify ngay khi `TalkNpc` update progress;
  - battle result response mang kèm mission updates cho `KillMonster` để RN có dữ liệu render sau khi claim kết quả trận.
- Boundary:
  - Server đã phát/đính kèm dữ liệu notify theo parser RN hiện có; UI popup/queue kiểu Java `hr`/`hb` vẫn là bước client polish tiếp theo.
  - Nội dung text notify là remake policy, còn command/tag shape bám parser Java-client đã audit.

### [NPC/MISSION] Mission reward claim/grant

- Thêm claim/grant reward nhiệm vụ theo DB seed:
  - `MissionRewardClaimService` đọc `MissionRewards` qua mission detail và grant `Exp`, `Item`, `Equipment` theo kiểu reward;
  - `Exp` dùng progression hiện có để cộng EXP/level và tính lại chỉ số khi cần;
  - `Item` merge stack trong `PlayerInventory` theo raw item id;
  - `Equipment` chỉ nhận equipment template key hợp lệ, không dùng raw item id.
- `MissionHandler` dùng remake policy tạm: nếu mission đã `Completed`, gửi accept lần nữa sẽ claim reward; `PlayerMissionStateRepository` chuyển trạng thái sang `RewardClaimed` để chặn double-claim.
- Sửa seed Hoa Lư: raw id `30094` là `Trứng gà` trong `ItemCatalog`, nên reward tuần tra đổi từ `Equipment 30094` sang `Item 30094`.
- Boundary:
  - Cơ chế claim bằng accept-lại là remake policy vì chưa có Java server command claim reward chính xác.
  - Phần thưởng vẫn đổi được bằng seed DB; không hardcode mission key/phần thưởng trong handler.

### [NPC/MISSION] Data-driven mission objective progress

- Thêm progress objective dùng chung cho các dạng mission lặp lại nhiều trong game:
  - `PlayerMissionStateRepository.AddProgressAsync(username, objectiveType, targetKey, amount)` update theo `ObjectiveType + TargetKey`, clamp theo `RequiredAmount`;
  - mission tự chuyển `Completed` khi mọi objective đã hoàn thành;
  - `KillMonster` progress từ `BattleResultService` dùng cả `SpawnTemplateKey` và runtime `MonsterKey`, nên seed sau này có thể target quái theo template hoặc instance key;
  - `TalkNpc` progress từ `NpcTalkHandler` dùng NPC id làm `TargetKey`.
- Boundary:
  - Reward claim/grant đã được bổ sung ở bước sau bằng remake policy riêng.
  - Công thức hoàn thành dựa trên DB objective seed, không hardcode monster id, NPC id hoặc số lượng.

### [NPC/MISSION] Player mission accept/cancel state

- Thêm state nhiệm vụ tối thiểu theo player:
  - `PlayerMissions` lưu trạng thái `Accepted`, `Completed`, `RewardClaimed`, `Canceled` theo `PlayerId + MissionCatalogId`;
  - `PlayerMissionObjectives` khởi tạo progress từng objective khi accept mission;
  - `PlayerMissionStateRepository` xử lý accept/cancel bằng DB, không hardcode mission key hoặc phần thưởng;
  - `MissionHandler` trả mission list/detail kèm trạng thái hiện tại của player.
- Boundary:
  - Chưa tự hoàn thành objective, chưa grant EXP/item/equipment, chưa claim reward.
  - Đây là remake policy tối thiểu cho mission state để tiếp tục nối `KillMonster`/`TalkNpc` sau khi chốt progress policy.

### [NPC/MISSION] DB-backed NPC talk and mission read flow

- Thêm read flow NPC/Mission từ DB:
  - `INpcMissionCatalog` + `DbNpcMissionCatalog` đọc `NpcMissionLinks`, `MissionCatalog`, `MissionObjectives`, `MissionRewards`;
  - `NpcTalkHandler` không hardcode `tutorial_npc` nữa mà resolve NPC talk context từ DB và trả kèm mission summary gắn với NPC;
  - `MissionHandler` xử lý mission list/detail theo command Java-evidence `31`/`33`, còn `32` accept và `41` cancel mới được wire nhưng chưa thay đổi state;
  - `MissionPacketFactory` đóng gói list/detail theo parser hiện có của RN client.
- Boundary:
  - Chỉ đọc/hiển thị mission từ DB; chưa làm per-player mission state, progress, reward claim hoặc grant EXP/item/equipment.
  - Reward/mission có thể đổi bằng seed DB vì handler không hardcode mission key/phần thưởng.

### [NPC/MISSION] DB-backed NPC roster runtime

- Thay runtime NPC roster từ static hardcode sang DB-backed service:
  - thêm `server/Twelve.Infrastructure/Repositories/DbMapNpcRosterService.cs` đọc `NpcMapRosters` join `NpcCatalog`;
  - DI chuyển `IMapNpcRosterService` từ `StaticMapNpcRosterService` sang `DbMapNpcRosterService`;
  - packet output vẫn giữ contract `MapNpcRosterEntry` theo Java `jo` fields, còn dữ liệu map/tọa độ/tên override nằm trong DB seed.
- Boundary:
  - Chỉ đổi nguồn dữ liệu NPC roster runtime; chưa implement mission accept/progress/reward claim.
  - Mục tiêu là để sau này đổi NPC/nhiệm vụ/phần thưởng bằng seed/catalog thay vì sửa hardcode handler.

### [NPC/MISSION] Hoa Lư starter mission seed

- Triển khai plan Hoa Lư đã được user duyệt vào seed DB:
  - `npc_110020` / Trưởng làng Gia Viễn giao `hoa_lu_ga_dien_quay_pha_001` và `hoa_lu_bao_tin_cho_linh_002`;
  - `npc_110110` / Lính Hoa Lư giao `hoa_lu_tuan_tra_cung_linh_003`;
  - objective dùng `KillMonster` với `MONSTER_1000_SLOT_0` / Gà Điên và `TalkNpc` với `npc_110110`;
  - reward chỉ dùng scope đã chốt: `Exp`, `Item`, `Equipment`.
- Sửa schema `MissionRewards` để dùng unique index expression riêng cho `COALESCE(RewardKey, '')`, giúp `ON CONFLICT` trong seed khớp PostgreSQL đúng hơn.
- Boundary:
  - Đây là remake policy đã được user duyệt, không ghi nhận là Java server mission catalog evidence.

### [NPC/MISSION] NPC database seed foundation

- Thêm schema/seed DB tối giản cho NPC:
  - `server/Database/Npcs/npcs_schema.sql` tạo `NpcCatalog`, `NpcMapRosters`, `MissionCatalog`, `MissionObjectives`, `MissionRewards`, `NpcMissionLinks`;
  - `server/Database/Npcs/npcs_seed.sql` seed đủ `110000..110160` với tên user vừa cập nhật;
  - embed migration qua `DB.09_npcs_schema.sql` và `DB.10_npcs_seed.sql` trong `server/Twelve.Infrastructure/Twelve.Infrastructure.csproj`.
- Field bám Java evidence:
  - `NpcKey` / `DisplayName` / `VisualTypeByte` / `DisplayLevel` / `TileX` / `TileY` / `NameColorMode` tương ứng `jo.a` đến `jo.g`.
- Điều chỉnh theo review:
  - bỏ các field/bảng chưa được runtime DB dùng như `InteractionMode`, `DialogKey`, `IsVerifiedJava`, `NpcDialogLines`, `MissionGroupKey`, `ShopKey`, `Notes`;
  - hỗ trợ NPC xuất hiện nhiều map qua nhiều dòng `NpcMapRosters`;
  - hỗ trợ một NPC chứa nhiều nhiệm vụ qua `NpcMissionLinks`;
  - seed `npc_110110` vào mọi map đang bật trong `WorldMapCatalog`, với `DisplayNameOverride = 'Lính ' || WorldMapCatalog.DisplayName` để tên lính đi theo từng map;
  - thêm `MissionObjectives` và `MissionRewards`; reward hiện chỉ cho phép `Exp`, `Item`, `Equipment` theo chốt scope hiện tại.
- Boundary:
  - Mission seed hiện là placeholder remake để chứng minh quan hệ DB, không ghi nhận là Java mission evidence.

### [NPC/MISSION] Numbered NPC asset layout correction

- Di chuyển các sprite NPC numbered `110000.png..110160.png` ra trực tiếp dưới `client/assets/npc/` theo xác nhận của user.
- Cập nhật `client/assets/npc/README.md`, `client/assets/npc/index.csv`, và `NPC_SYSTEM_RECONSTRUCTION.md` để ghi rõ:
  - `110xxx` là bộ NPC sprite thật;
  - các subfolder còn lại là UI chrome, shared actor sheets, blacksmith visual evidence, hoặc map prop, không phải bộ numbered NPC chính.
- Boundary:
  - Chưa gán map/name/mission role cho từng `110xxx` vì vẫn cần server catalog/packet/video evidence.

### [NPC/MISSION] Mission parser foundation

- Thêm nền parser/state cho Mission theo Java client evidence:
  - command `31` mission list, `33` mission detail, `34` task notification, `35` mission notification, `38` mission update;
  - outbound request `31`, `33`, `32`, `41` dùng tag `77` đúng evidence `ks`;
  - thêm `MapMission.types.ts` và `MapMission.reducer.ts` để giữ Mission state/queue riêng khỏi NPC talk dialog.
- Boundary:
  - Chưa dựng UI mission đầy đủ kiểu `hr.java`.
  - Chưa suy quest giver, reward flow, mission ownership, accept/cancel server policy hoặc completion policy.
  - Không thay đổi Monster flow.

### [NPC/MISSION] NPC talk request v1

- Implement bước NPC talk nhỏ, tách khỏi Mission ownership:
  - client gửi `NPC_TALK_REQUEST = 16` với NPC id tag `9` và continue flag tag `40`, bám evidence `ks.a().a(String, boolean)`;
  - server thêm `NpcTalkHandler` validate `tutorial_npc` và trả response tạm trên command `46`;
  - RN map hiển thị dialog nhỏ từ response và center softkey chuyển giữa `Noi chuyen` / `Tiep tuc`.
- Boundary:
  - Command `16` là Java client evidence cho request; command `46` và nội dung lời thoại là remake policy tạm.
  - Chưa gán quest giver, mission ownership, reward flow, shop role hoặc portal role.
  - Không thay đổi Monster roster/encounter flow.

### [NPC/MISSION] Server NPC roster v1

- Implement server remake policy đầu tiên cho NPC roster theo Java client command shape `43`:
  - thêm contract `MapNpcRosterEntry`, `NpcRosterMode`, `NpcSharedSheetFamily`;
  - thêm `IMapNpcRosterService` và `StaticMapNpcRosterService` seed NPC `tutorial_npc` cho map `Hoa Lu`, room `1`;
  - thêm `NpcRosterPacketFactory` serialize packet shape Java: tag `20`, tag `40`, repeated tag `9`, nested tags `9`, `26`, `27`, `15`, `129`, `106`, `107`;
  - khôi phục/preserve `MapHandler` gửi Monster roster hiện tại trên command `43`;
  - thêm command tạm `45` cho NPC roster trên RN để không phá Monster roster/map encounter đang dùng `43`.
- Boundary:
  - Đây là remake policy dựa trên Java client packet shape, không phải Java server evidence.
  - Chưa implement NPC interaction/dialog/mission ownership.
  - Java evidence vẫn là NPC raw command `43`; command `45` chỉ là remake transport tạm cho RN trong lúc Monster roster hiện tại còn giữ `43`.
  - Client đã parse/render NPC roster riêng, không đưa NPC vào Monster collision/encounter.

### [NPC/MISSION] Java evidence audit expansion

- Cập nhật `NPC_SYSTEM_RECONSTRUCTION.md` chỉ trong phạm vi NPC & Mission:
  - bổ sung evidence cho mission records `ns`, mission tasks `nt`, queue `nu`;
  - bổ sung command/packet evidence cho mission commands `31`, `33`, `34`, `35`, `38` và NPC actor roster command `43`;
  - bổ sung asset inventory từ JAR cho `blacksmith`, `effblacksmith`, `monster`, `zap`, `ice`, `magicgate`, `gate`, `questnotifyicon`, `dialog/corner`, và candidate `offline/110xxx`;
  - bổ sung deep-audit evidence cho mission screen `hr`, completed mission popup `hb`, outbound mission request methods trong `ks`, focused NPC `ki` interaction path trong `om`, NPC interaction preview trong `ha`, và first-time mission tutorial hint trong `om`.
- Boundary:
  - Không implement code.
  - Không đưa battle/equipment/skill/payment/PvP vào tài liệu này ngoài các cross-reference trực tiếp phục vụ NPC/Mission.
  - Không tự gán quest giver/shop/portal role khi chưa có Java packet/catalog evidence.

### [EQUIPMENT] Inventory detail panel spacing cleanup

- Cập nhật `client/src/screens/map/core/MapCharacterDialogs.tsx`:
  - bỏ emoji/icon prefix trước tên item/equipment trong dialog chi tiết để tên item hiển thị đúng, gọn.
- Cập nhật `client/src/screens/map/core/MapCharacterDialogs.styles.ts`:
  - tăng padding trái/phải phần header/content của panel chi tiết;
  - thu và canh lại panel chi tiết để nội dung không sát viền.
- Boundary:
  - Chỉ chỉnh UI presentation React Native.
  - Không đổi gameplay logic, raw item id, equipment slot, stat aggregation hoặc server API.

### [EQUIPMENT] Inventory item icon mapping correction

- Sửa lỗi nhiều vật phẩm trong túi đồ hiển thị sai thành icon rương do `iconKind` server/client không khớp asset thật.
- Cập nhật `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
  - item `5002` đổi sang MP consumable dùng `iconKind = potion_blue`;
  - item `5003` dùng `Huyết thạch` / `iconKind = huyet_thach`;
  - item `5004` dùng `Kim thạch` / `iconKind = kim_thach`;
  - `OstrichEgg/30095` dùng `iconKind = ostrich_egg` thay vì `chicken_egg`.
- Cập nhật `client/src/screens/map/core/MapCharacterDialogs.tsx`:
  - bổ sung mapping asset cho các egg icon `chicken_egg`, `ostrich_egg`, `dinosaur_egg`, `phoenix_egg`, `dragon_egg`;
  - bổ sung alias `repair_hammer` để khớp `iconKind` server.
- Boundary:
  - Không đổi raw item id.
  - Đây là sửa mapping hiển thị/asset theo catalog remake hiện có, không phải Java server evidence mới.
  - Không đổi equipment icon formula; equipment vẫn resolve từ `ll.n/resourceId` theo Java evidence.

## 2026-05-03

### [EQUIPMENT] ItemCatalog primary key naming cleanup

- Chuẩn hóa primary key của `server/Database/Equipment/equipment_schema.sql`:
  - `ItemCatalog.ItemId INT PRIMARY KEY` đổi thành `ItemCatalog.Id INT PRIMARY KEY`.
  - `Id` vẫn giữ nguyên raw gameplay item id dùng trong server logic, chỉ sửa tên cột DB để thống nhất convention PK là `Id`.
- Cập nhật `server/Database/Equipment/equipment_seed.sql`:
  - `INSERT INTO ItemCatalog (Id, ...)`.
  - `ON CONFLICT (Id) DO UPDATE`.
- Boundary:
  - Không đổi raw item ids (`5001..5007`, `30094..30099`).
  - Không đổi `PlayerInventory.ItemId` vì đây là field stack inventory/link raw item id, không phải primary key bảng `ItemCatalog`.
  - Không đổi gameplay behavior.

### [EQUIPMENT] Client equipment breakdown contract + repair UI status

- Cập nhật client contract/UI để nhận và hiển thị breakdown equipment do server tính:
  - `client/src/screens/character/shared/characterAppearance.ts`: thêm `equipmentStats`, `bonusAttackPercent`, `isBroken`, `contributesStats`.
  - `client/src/screens/character/status/CharacterStatus.api.ts`: map các field `Equip*` từ `PlayerRuntimeSnapshot`, map `BonusAttackPercent`, derive trạng thái broken/contribution từ durability view.
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`: hiển thị `Tấn Công +x%`, trạng thái đồ hỏng/không cộng chỉ số và chỉ bật `Sửa chữa` khi có búa `30099`.
- Boundary:
  - Đây là client wiring cho server authority đã có; không đổi combat formula, không bật special stats pending, không đổi DB schema.
  - Repair hammer `30099` là remake policy/user confirmation `2026-05-03`, không ghi thành Java server evidence.
  - TypeScript check pass bằng `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit`.

### [EQUIPMENT] Equipment stat aggregation helpers + snapshot equip breakdown + BonusAttackPercent

- Cập nhật `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`:
  - `PlayerEquipmentItemView`: thêm field `BonusAttackPercent` (tag `204` evidence).
  - `PlayerRuntimeSnapshot`: thêm 10 field `Equip*` tổng hợp stat contribution từ tất cả equipment đang mặc non-broken, để client hiển thị breakdown equipment riêng biệt với base/level stat.
- Cập nhật `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
  - `ToEquipmentView`: truyền `BonusAttackPercent` từ modifier.
  - Thêm `ReduceDurability(entry, amount)`: helper giảm durability clamped to 0, trả entry mới.
  - Thêm `GetEquippedModifierTotal(equipment)`: aggregate tất cả equipped non-broken modifier thành 1 `PlayerStatModifier`.
  - Thêm `SumModifiers(...)`: private accumulator cho 10 stat fields.
- Cập nhật `server/Twelve.Application/Players/PlayerRuntimeService.cs`:
  - `BuildSnapshot(...)`: gọi `GetEquippedModifierTotal` và truyền 10 field `Equip*` vào snapshot.
- Java evidence applied:
  - `AttackPercent/tag 204` evidence cộng theo `baseAttack * percent / 100` (đã audit `lb.java`).
  - Broken equipment `p == 0` vẫn mặc nhưng bị bỏ qua khi aggregate (bám `da.java` evidence).
- Remake policy applied:
  - Wing `e=8` tham gia aggregate như các slot khác (user chốt `2026-05-03`).
  - `ReduceDurability` là server authority helper cho combat durability loss; caller quyết định amount.
- Boundary:
  - Không đổi combat damage formula; 5 special stats (`DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`) vẫn chỉ parse/display, chưa áp combat effect.
  - Không đổi DB schema.
  - `dotnet build Twelve.sln` pass: `0 Warning(s), 0 Error(s)`.

### [EQUIPMENT] Equipment persistence snapshot stat aggregation

- Cập nhật `server/Twelve.Infrastructure/Repositories/PlayerAggregateRepository.cs`.
- `BuildStatSnapshot(...)` khi restore `PlayerAggregate` từ DB giờ aggregate stat từ `PlayerEquipment.RawJson` của equipment đang mặc.
- Gate server authority:
  - chỉ cộng equipment có `IsEquipped = true`;
  - chỉ cộng khi `Durability > 0`, đúng policy đồ hỏng vẫn mặc được nhưng không có stat/effect;
  - wing `e=8` không bị loại riêng, vì slot đang mặc hợp lệ sẽ đi qua cùng pipeline equipment.
- Boundary:
  - không đổi schema persistence hiện tại;
  - không thêm formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`;
  - verification bằng `dotnet build Twelve.sln`.

### [EQUIPMENT] Open-egg configured reward pool activation (safe Phase)

- Kích hoạt flow đập trứng an toàn cho loại trứng đã có reward pool rõ ràng.
- Cập nhật `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
  - khai báo đủ item definitions cho các egg raw ids `30094..30098` qua `PlayerItemId`;
  - cấu hình cost theo policy user chốt: `30094=20,000`, `30095=30,000`, `30096=100,000`, `30097=300,000`, `30098=300,000` Quan;
  - giữ reward slots ở `Armor/Weapon/Helmet/Ring`, không include `Wing/e=8`;
  - chỉ bật reward pool tạm cho `OstrichEgg/30095` bằng các template hiện có: `fire_guard_vest`, `zap_hunter_helm`, `water_guard_cloak`;
  - các egg còn lại vẫn trả lỗi cấu hình vì chưa có list template cụ thể.
- Cập nhật `server/Twelve.Application/Players/PlayerRuntimeService.cs`:
  - `OpenEgg(...)` validate egg/inventory/Quan/capacity/template/slot;
  - chặn cứng `Wing/e=8` trong reward pool;
  - consume đúng `1` egg, trừ đúng `OpenCostQuan`, tạo equipment instance bằng template catalog và lưu server-side.
- Boundary:
  - cost/reward pool hiện tại là `Remake policy`, không phải Java server evidence;
  - không fallback random equipment khi pool chưa cấu hình;
  - reward rate/chance/pity/event multiplier và pool của các egg khác vẫn pending;
  - không mở combat formula cho special stats pending.

### [EQUIPMENT] User-facing ItemCatalog descriptions cleanup

- Cập nhật `server/Database/Equipment/equipment_seed.sql` để mô tả item là text hiển thị cho người chơi, không còn ghi chú kỹ thuật reconstruction trong cột `Description`.
- Các mô tả đã chuyển sang dạng gameplay/user-facing:
  - `HP`/`MP`: mô tả hồi HP/MP.
  - `Huyết Thạch`, `Kim Thạch`, `Vuốt Rồng`, `Lông Vũ`: mô tả là nguyên liệu cho chế tạo/cường hóa/nâng cấp khi hệ thống tương ứng mở khóa.
  - `X2 EXP`: mô tả vật phẩm hỗ trợ tăng kinh nghiệm.
  - Các loại trứng: mô tả dùng cho mở thưởng/triệu hồi khi hệ thống trứng được mở khóa.
  - `Búa Sửa Chữa`: mô tả dùng 1 búa để hồi đầy độ bền trang bị hư hỏng.
- Boundary:
  - Evidence/policy/pending vẫn nằm ở tài liệu/comment và cột `EvidenceStatus`, không đưa vào text mô tả item.
  - Không đổi raw item id, `Kind`, `EvidenceStatus`, `ResourceId/IconId`.
  - Không đổi gameplay behavior.

### [EQUIPMENT] Minimal DB ItemCatalog for inventory items

- Thêm DB item catalog tối thiểu theo yêu cầu tạo database cho items.
- Cập nhật `server/Database/Equipment/equipment_schema.sql`:
  - thêm bảng `ItemCatalog`;
  - giữ các trường cần thiết: `Id`, `DisplayName`, `Description`, stack/use/heal/mana/restore/icon kind, `Kind`, `EvidenceStatus`, nullable `ResourceId/IconId`, `IsEnabled`, `UpdatedAt`;
  - thêm index theo `Kind` và `ResourceId`.
- Cập nhật `server/Database/Equipment/equipment_seed.sql`:
  - thay placeholder bằng seed tối thiểu cho item hiện đang dùng trong runtime/catalog shell: `5001..5007`, `30095..30099`;
  - tên/icon kind bám asset thật trong `client/assets/items/` (`hp`, `mp`, `huyet_thach`, `kim_thach`, `dragon_claw`, `feather`, `x2_exp`, các egg icon và `repair_hammer`) thay vì đặt tên giả;
  - dùng `ON CONFLICT (Id) DO UPDATE` để migrator chạy lại an toàn.
- Boundary:
  - `Id` là raw gameplay item id, không dùng trực tiếp làm asset id;
  - `Kind`/`EvidenceStatus` giữ raw enum values theo `PlayerItemKind` và `PlayerItemEvidenceStatus`;
  - `ResourceId/IconId` để `NULL` khi Java asset/resource evidence chưa chắc chắn;
  - các id seed tạm ngoài `30095/30099` chưa được ghi là Java server evidence, chỉ là catalog shell theo asset/runtime hiện có.

### [EQUIPMENT] Minimal item catalog fields for inventory icon/resource authority

- Thêm item catalog/template tối thiểu cho inventory item để tách raw gameplay id khỏi asset resource/icon id.
- Cập nhật `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`:
  - thêm enum `PlayerItemKind`;
  - thêm enum `PlayerItemEvidenceStatus`;
  - `PlayerInventoryItemView` expose `Kind`, `EvidenceStatus`, `ResourceId`, `IconId`.
- Cập nhật `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
  - mở rộng `PlayerItemDefinition` với kind/evidence/resource/icon id;
  - serialize/resolve các field mới trong `RawJson`;
  - `OstrichEgg = 30095` và `RepairHammer = 30099` giữ `ResourceId/IconId = null` vì chưa có Java asset/resource evidence chính thức.
- Boundary:
  - `PlayerItemId` chỉ là raw gameplay id cho server logic, không dùng trực tiếp làm asset id;
  - equipment icon vẫn derive từ `ll.n/resourceId` theo Java evidence `iconId = (resId - resId % 10) + 98`;
  - item tiêu hao/material dùng catalog riêng và `EvidenceStatus`, không tự gán raw item id như `30095/30099` thành icon id.

### [EQUIPMENT] Raw item id enum boundary for equipment items

- Tiếp tục cleanup server authority cho equipment item ids.
- Cập nhật `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`:
  - thêm enum `PlayerItemId`;
  - giữ raw values theo catalog/runtime hiện tại: `OstrichEgg = 30095`, `RepairHammer = 30099`.
- Cập nhật `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
  - `IsRepairMaterial(...)`, item catalog và open-egg skeleton dùng `PlayerItemId` thay vì số trần trong logic code.
- Cập nhật `server/Twelve.Application/Players/PlayerRuntimeService.cs`:
  - comment repair flow trỏ tới `PlayerItemId.RepairHammer` và vẫn ghi rõ raw itemId `30099`.
- Boundary:
  - không đổi gameplay behavior;
  - `30099` vẫn là remake policy/user confirmation ngày `2026-05-03`, không ghi thành Java server evidence;
  - enum giữ raw value, chỉ cast tại boundary catalog/API/storage.

### [EQUIPMENT] Open-egg backend safety skeleton + capacity gate

- Tiếp tục Phase equipment server authority theo `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật runtime API/server:
  - thêm `PlayerOpenEggRuntimeRequest`;
  - expose `OpenEgg(...)` qua `IPlayerRuntimeService`;
  - thêm endpoint `POST /player/runtime/item/open-egg`.
- Cập nhật catalog/service:
  - thêm `PlayerEggDefinition`;
  - cấu hình skeleton cho trứng đà điểu itemId `30095`, cost `30,000` Quan;
  - validate egg config, có trứng trong inventory, đủ Quan, capacity chưa đầy;
  - nếu chưa có reward pool/template rõ thì trả lỗi cấu hình và không mutate inventory/currency/equipment.
- Giữ boundary:
  - Java evidence: `go.n = 50`; inventory full check bám `go.b()` theo equipment + item stacks;
  - remake policy: trứng không mở ra `Wing/e=8`, reward chỉ nằm trong `Armor/Weapon/Helmet/Ring`;
  - pending: itemId các loại trứng khác, reward pool/rate/pity/event multiplier, và tạo equipment instance sau khi có config rõ.

### [EQUIPMENT] Open-egg / đập trứng remake policy

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` với policy user chốt cho flow mở trứng:
  - Trứng thường tốn `20,000` Quan.
  - Trứng đà điểu tốn `30,000` Quan.
  - Trứng khủng long tốn `100,000` Quan.
  - Trứng rồng tốn `300,000` Quan.
  - Trứng phượng tốn `300,000` Quan.
- Boundary quan trọng:
  - Trứng không bao giờ mở ra cánh; `Wing/e=8` chỉ đi qua flow chế tạo/crafting riêng sau này.
  - Equipment reward từ trứng chỉ được roll trong slot `Armor/Weapon/Helmet/Ring`.
  - Sau này sẽ có list equipment/template mở riêng cho mỗi loại trứng; API thật phải đọc config/list rõ ràng, không fallback random.
- Giữ trạng thái pending:
  - chưa có Java server evidence cho drop rate/cost/reward pool gốc;
  - itemId đầy đủ cho từng loại trứng, reward chance, list equipment từng trứng, pity/event multiplier còn chờ policy/config tiếp.
- Chỉ sửa tài liệu `.md`, không sửa code server/client nên không chạy build/check.

### [EQUIPMENT] Inventory item icon asset wiring for equipment materials

- Cập nhật UI inventory item theo asset đã cắt từ spritesheet:
  - item/material/consumable đã tách khỏi domain equipment sang `client/assets/items/`;
  - chuẩn hóa tên file đã xác nhận: HP, MP, trứng đà điểu, búa sửa đồ, Kim Thạch, Huyết Thạch, Bùa 1/2/3;
  - giữ icon chưa xác minh gameplay dưới dạng `pending_*.png`, không suy diễn tên domain.
- Cập nhật `client/src/screens/map/core/MapCharacterDialogs.tsx`:
  - resolver `CharacterInventoryItem.iconKind` đọc từ `client/assets/items/`;
  - map `potion_red`, `potion_blue`, `peach`, `hammer`, `kim_thach`, `huyet_thach`, `charm_1`, `charm_2`, `charm_3`.
- Cập nhật `client/assets/equipment/README.md`:
  - loại `items/` khỏi layout equipment;
  - ghi rõ repair hammer `30099` là item repair nằm ở `client/assets/items/repair_hammer.png`;
  - tỉ lệ drop trứng từ monster và reward pool gốc vẫn là `Remake policy pending`, không suy ra từ icon; cost mở trứng đã được user chốt riêng trong mục `[EQUIPMENT] Open-egg / đập trứng remake policy`.
- Giữ boundary:
  - repair hammer `30099` là policy/user confirmation đã chốt ngày `2026-05-03`;
  - Kim Thạch/Huyết Thạch/Bùa/trứng mới wire icon/material identity UI, chưa consume/roll upgrade/drop/open thật vì thiếu danh sách đá/bùa/tỉ lệ Java gốc và list equipment/reward pool cụ thể cho từng loại trứng.

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