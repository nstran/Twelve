# Player Character Reconstruction - Index

Tổng quan, nguyên tắc reconstruction khi không có server cũ, trạng thái chốt và source files.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

## File Map

| File | Nội dung |
|------|----------|
| [01-implementation-plan-csharp.md](01-implementation-plan-csharp.md) | stat calculator, C# gaps, schema/service blueprint, port order |
| [02-truth-payload-and-tags.md](02-truth-payload-and-tags.md) | `lh`, field map, bars, derived stats, tag map từ `ky.java` |
| [03-structs-global-appearance.md](03-structs-global-appearance.md) | `ll/lb/lm/lv/df/dg/lt`, `go`, `com.mg.sq.a`, appearance/compositor |
| [04-delta-ui-character-flows.md](04-delta-ui-character-flows.md) | delta packet, profile/status UI, inventory/equipment runtime, skill tree, create flow |
| [05-protocol-trade-upgrade-market.md](05-protocol-trade-upgrade-market.md) | outbound/inbound protocol, trade, nâng cấp, kết hợp, market/sale |
| [06-map-room-battle-runtime.md](06-map-room-battle-runtime.md) | map actor, room/profile runtime, battle actor/HUD/result dependencies |
| [07-arena-pvp-flow.md](07-arena-pvp-flow.md) | kiến trúc Khiêu Chiến/PvP: lobby, challenge, match session, result/rating/reward |
| [08-level-stat-exp-and-element-balance.md](08-level-stat-exp-and-element-balance.md) | spec cân bằng level 250, điểm tiềm năng, EXP curve, stat, movement, resource và khắc hệ |

# Player / Character Reconstruction

Tài liệu này gom toàn bộ dấu vết Java hiện có cho `player`, `char`, `nhân vật`
thành một mô hình reconstruction dùng để port server/client mới sát runtime cũ.

Mục tiêu:

- xem Java cũ như spec
- tách rõ `account/session`, `char truth payload`, `map actor`, `battle actor`
- note luôn phần C# hiện tại đã làm gì và còn thiếu gì

## Nguyên Tắc Reconstruction Khi Không Có Server

Dự án này **không có server cũ**, nên tuyệt đối không giả định rằng sẽ có packet live
hoặc DB production để đối chiếu. Mọi logic server-side phải được suy ra từ các dấu vết
client Java:

- parser inbound `ky.java`: client kỳ vọng nhận field/tag nào, theo shape nào
- encoder outbound `ks.java` + envelope `kw.java`: client từng gửi command/tag nào
- state bridge `go.java` và `com.mg.sq.a`: packet làm thay đổi truth state ra sao
- UI flow `nw/da/de/hh/id/ho/of`: client validate, preview, commit logic nào
- renderer/map/battle `mb/kl/om/lg/ms/mx`: field nào thật sự ảnh hưởng runtime
- asset offline trong jar/client: chỉ dùng để suy luận bổ trợ, không dùng để bịa tên nghiệp vụ

Vì vậy các chỗ ghi "chưa chốt" trong tài liệu này nghĩa là:

- Java client không chứa đủ bằng chứng để đặt tên nghiệp vụ chắc chắn
- nhưng wire shape, state mutation, và rule cần implement vẫn phải được suy luận từ Java
- khi port server mới, ưu tiên lưu raw id/tag trước, đặt tên business sau khi có thêm bằng chứng từ gameplay/quay màn hình

## Tiến Độ Bóc Tách Hiện Tại

Ước lượng sau lượt đào sâu hiện tại: **100% trong phạm vi player/character server contract suy luận từ Java client**.

Đây không có nghĩa là đã biết mọi tên nghiệp vụ tuyệt đối của server cũ. Nó nghĩa là toàn bộ phần có thể suy ra từ Java client cho `player/char` đã được bóc thành contract, state mutation, packet shape, và blueprint implement.

| Cụm | Trạng thái | Ghi chú |
|-----|------------|---------|
| `lh` truth payload | Chốt | field/tag/method core đã chốt; `H/I/J/M/N` là progress/currency axis |
| struct con `ll/lb/lm/lv/df/dg/lt` | Chốt | tag parser đã bóc; `lt` là icon countdown overlay, raw id giữ nguyên nếu chưa đủ label |
| inbound parser `ky` | Chốt | full/delta/trade/upgrade/market/battle-result đã bóc |
| outbound `ks/kw` | Chốt | command char-core đã map; command social phụ đã gom theo family |
| global state `go` + bridge `com.mg.sq.a` | Chốt | mutate player/inventory/refresh/result-screen/map-overlay chain đã rõ |
| appearance/compositor `mb/lc/nr` | Chốt contract | đủ để thiết kế data contract; pixel-perfect renderer là việc client |
| map actor `kl/kd/jt/om` | Chốt contract | init/update/gauge/sort/`lt` overlay đã rõ; collision chi tiết thuộc map system |
| battle actor `lg/ms/mx/ni` | Chốt contract | bootstrap/render/HUD/result sync đã rõ; turn detail thuộc battle system |
| create/stat/skill UI `nw/da/de/ib` | Chốt | command, raw element, pending stat/skill batch đã rõ |
| inventory/equipment `hh` | Chốt | equip/use/sell/toggle/repair/stack/capacity flow đã rõ |
| upgrade/combine/trade `id/ho/of` | Chốt | `id=Nâng cấp`, `ho=Kết hợp`, data contract/capacity/finalize flow rõ |
| room/social/profile `os/do/ha/dd/cz/gy` | Chốt phần chạm player | model, display dependencies, room action menu và command phụ đã gom |
| C# gap/data model | Chốt blueprint | schema/service/wire compatibility đủ để bắt đầu implement |

### Sổ Pending / New Logic Vì Không Có Server

Các mục dưới đây **không được coi là phần chưa bóc Java**. Đây là những điểm server cũ từng quyết định nhưng repo hiện chỉ còn client, nên server mới phải lưu raw id/tag và dựng logic mới dựa trên dấu vết client.

| Mục | Bằng chứng Java/client đã có | Cách implement server mới |
|-----|------------------------------|---------------------------|
| tên nghiệp vụ từng `lt.a` icon | `ky` đọc `158 -> 4/157`, `om` dựng `jx`, asset offline có icon | lưu `IconId`, `DurationMs`, render countdown; đặt tên display theo asset nếu đủ chắc, không chặn gameplay |
| `lt.a=200000` | icon `x2`, `om` có nhánh đặc biệt khi countdown > 0 | model thành `MapOverlay` raw id `200000`; có thể đặt label tạm `DoubleBuffCandidate`, rule cụ thể cấu hình được |
| title/rank/prestige curve | `Q/R/S/ab`, default title theo level, profile/result đọc trực tiếp | tạo `TitleService` mới: default theo level như Java, còn curve prestige cấu hình server-side |
| giá/phí nâng cấp/kết hợp | `id/ho` chỉ cho biết packet phí tag `132`, ready status, snapshot sau xử lý | tạo `RecipeRuleCatalog` mới; giữ packet shape `96-101`, validate ownership/capacity |
| reward battle EXP/KEN/item/equip | `ky/mq/hs` cho biết tag `42/43/73/74/83/114` và result animation | tạo `BattleRewardService` mới; output đúng tags, formula có thể chỉnh bằng config |
| market economy | `ky`/`ks` cho biết list/sell/buy tags, price tag `157/132` | tạo `MarketService` mới, enforce ownership, price, capacity; giữ raw item/equip snapshot |
| social/Ola commands ngoài char | command family đã gom nhưng không thuộc player aggregate | để pending module `SocialService`; chỉ stub response an toàn nếu chưa remake social layer |
| pixel-perfect animation | renderer Java chỉ cần nếu làm client clone sát | không ảnh hưởng server; client xử lý sau bằng asset/runtime frame data |

Nguyên tắc: **không chờ server cũ**. Cái gì Java client chứng minh được thì port đúng; cái gì Java client không chứng minh được thì lưu raw id/tag, thêm cấu hình server mới, và note mức chắc chắn.

## Source Files Cần Bám

Java reference chính:

- `reference/redecoded/decompiled/ky.java`
- `reference/redecoded/decompiled/lh.java`
- `reference/redecoded/decompiled/ld.java`
- `reference/redecoded/decompiled/ll.java`
- `reference/redecoded/decompiled/lm.java`
- `reference/redecoded/decompiled/lv.java`
- `reference/redecoded/decompiled/lt.java`
- `reference/redecoded/decompiled/df.java`
- `reference/redecoded/decompiled/mb.java`
- `reference/redecoded/decompiled/kl.java`
- `reference/redecoded/decompiled/kd.java`
- `reference/redecoded/decompiled/om.java`
- `reference/redecoded/decompiled/jt.java`
- `reference/redecoded/decompiled/ni.java`
- `reference/redecoded/decompiled/ms.java`
- `reference/redecoded/decompiled/mx.java`
- `reference/redecoded/decompiled/com/mg/sq/a.java`
- `reference/redecoded/decompiled/go.java`
- `reference/redecoded/decompiled/ks.java`
- `reference/redecoded/decompiled/kw.java`
- `reference/redecoded/decompiled/nw.java`
- `reference/redecoded/decompiled/gy.java`
- `reference/redecoded/decompiled/dd.java`
- `reference/redecoded/decompiled/cz.java`
- `reference/redecoded/decompiled/da.java`
- `reference/redecoded/decompiled/de.java`
- `reference/redecoded/decompiled/ib.java`
- `reference/redecoded/decompiled/hh.java`
- `reference/redecoded/decompiled/id.java`
- `reference/redecoded/decompiled/ho.java`
- `reference/redecoded/decompiled/of.java`
- `reference/redecoded/decompiled/os.java`
- `reference/redecoded/decompiled/ha.java`
- `reference/redecoded/decompiled/jp.java`
- `reference/redecoded/decompiled/jq.java`
- `reference/redecoded/decompiled/js.java`
- `reference/redecoded/decompiled/jr.java`

C# hiện tại cần đối chiếu:

- `server/Twelve.Core/Entities/Player.cs`
- `server/Twelve.Infrastructure/Repositories/PlayerRepository.cs`
- `server/Twelve.Application/Handlers/AuthHandler.cs`
- `server/Twelve.Application/Handlers/TokenAuthHandler.cs`
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `server/Twelve.Application/Handlers/AllocateStatHandler.cs`
- `server/Twelve.Application/Handlers/MapHandler.cs`
- `server/Twelve.Application/Handlers/MoveHandler.cs`
- `server/Twelve.Application/Handlers/MonsterEncounterHandler.cs`
- `server/Twelve.Core/GameSession.cs`
- `server/Twelve.Core/Tlv/CommandCodes.cs`
- `server/Database/02_players.sql`
- `server/Database/04_add_player_traits.sql`
- `server/Database/05_add_gender_to_players.sql`
- `server/Database/06_add_base_stats.sql`

## Mô Hình Tổng Quát

Java cũ không có một class `Player` duy nhất bao trọn mọi thứ.

Nó tách ra thành các lớp sau:

1. `session/account`
   - login, token, reconnect, socket state
2. `character truth payload`
   - class `lh`
   - đây là dữ liệu server gửi xuống và client giữ làm nguồn truth
3. `map actor`
   - class `kl`
   - render + animation + movement state ngoài map
4. `battle runtime actor`
   - `lg` là state runtime
   - `ni` là actor battle composite
   - `mx` là HUD + renderer battle
5. `appearance/compositor`
   - `df`, `dg`, `mb`, `lc`, `nr`
6. `inventory/equipment/skills`
   - `lm`, `ll`, `lv`, `lt`
7. `global current-player state`
   - `go.k` = current player `lh`
   - `go.l` = equipment bag/global list
   - `go.m` = item inventory/global list
   - `go.r` = skill tree definitions

Kết luận quan trọng:

- `lh` mới là data contract thật của nhân vật
- `Player.cs` hiện tại chỉ là một mảnh nhỏ của `lh`
- map player và battle player không được dựng trực tiếp từ DB row đơn giản
- appearance, equipment, skills, titles, prestige, cooldowns đều là một phần của player truth

## Class Map Theo Vai Trò

| Class | Vai trò | Ghi chú |
|------|---------|--------|
| `ld` | base identity record | `a=id`, `b/c/d=name/display/desc` |
| `lh` | full fighter / character payload | class quan trọng nhất |
| `ll` | equipment entry | equip slot, rank, resId, affix stats |
| `lm` | inventory item entry | quantity, type, price, stack cap |
| `lv` | learned skill entry | id, level, mana cost, description |
| `lt` | map icon/timer overlay entry | tag `158/157`, icon id + countdown/value |
| `df` | appearance palette descriptor | dùng cho recolor body/face/hair |
| `mb` | body-part metadata + compositor | ghép sprite nhân vật |
| `kl` | map player actor | outside battle |
| `kd` | actor bucket + depth sorting | player/monster/object cùng map |
| `jt` | map HP/Power gauge | dùng `lh` để fill gauge |
| `ni` | battle actor state machine | inside battle |
| `ms` | battle board state wrapper | giữ `lg[][]` theo 2 phe |
| `mx` | battle HUD/renderer | dựng actor, HP/MP/Power bar |
| `go` | global runtime state | current player + bags + skill tree |
| `nw` | create-character UI | chứng minh role của appearance fields |
| `de` | skill tree UI | đọc skill levels + skill points từ `lh` |
| `ha` | encounter/versus preview | hiển thị cấp, hệ, title, IQ |


