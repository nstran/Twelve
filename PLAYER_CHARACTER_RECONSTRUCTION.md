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

## `lh` Là Truth Payload Của Nhân Vật

Class `lh`:

```java
public final class lh extends ld
```

Nó mang cả:

- identity
- element/class
- gender
- level
- hp/mp/power
- base stats
- stat bonus từ equip
- derived combat stats
- skill points
- prestige / title / rank strings
- equipment
- inventory
- skills
- appearance palettes
- boolean appearance flags
- map icon/timer overlay arrays

### Field map đã chốt được

| Field | Ý nghĩa | Ghi chú |
|------|---------|--------|
| `a` | id/runtime id | kế thừa `ld` |
| `b` | name/account name hiển thị | kế thừa `ld` |
| `c` | display name / alias | hay dùng ở UI |
| `d` | description | kế thừa `ld` |
| `e` | room/status byte | dùng ở room/player list, bị toggle khi `aa` đổi |
| `f` | gender byte | `0/1`, dùng trong `mb` để chọn family `79899/79999` |
| `g` | raw element/class code | Java dùng `1=Hỏa`, `2=Lôi`, `4=Thủy` |
| `G` | level | được render nhiều nơi |
| `H` | current KEN/gold/collection value | tag `43`, dùng trên màn hình kết quả trận với icon vàng |
| `I` | max/cap của thanh KEN/gold/collection | tag `99`, default parser `10000` |
| `J` | current EXP/progression value | tag `42`, dùng cho gauge với `M/N` |
| `K` | free stat points | tag `53` |
| `L` | free skill points | skill tree dùng trực tiếp |
| `M` | EXP floor của level hiện tại | tag `73`, dùng trong `jt` và result screen |
| `N` | EXP ceiling của level hiện tại | tag `74`, dùng trong `jt` và result screen |
| `O` | boolean online/active flag | packet nhẹ có set |
| `P` | status message string | room/player list dùng trực tiếp |
| `Q` | rank title string | auto derive nếu server không gửi |
| `R` | secondary bracket title | hiển thị trong profile card |
| `S` | primary bracket title | hiển thị trong profile card |
| `T` | byte battle side / sub-type | có ở payload battle-prep/lightweight |
| `U` | appearance descriptor 0 | create-char chứng minh đây là phần thay đổi theo tóc/hair family |
| `V` | appearance descriptor 1 | create-char chứng minh liên quan mặt/face family |
| `W` | appearance descriptor 2 | create-char chứng minh là body/skin base palette |
| `X` | wager / stake / room amount long | room/player list dùng trực tiếp |
| `Y` | special actor/monster form id | battle renderer dùng để rẽ nhánh actor không-composite |
| `Z` | appearance toggle | ảnh hưởng slot 0 compositor |
| `aa` | appearance toggle 2 | có delta packet riêng |
| `ab` | `D.Vọng` / prestige / honor | profile UI hiển thị rõ |
| `ac` | `lt[]` map icon/timer overlays | packet tag `158` |
| `ad` | bool cầm vũ khí / equip slot 4 | `b()` set dựa trên equip slot 4 |

### Base stats và bonus stats

Các field đã rõ:

| Field | Meaning |
|------|---------|
| `h` | Cường Lực |
| `i` | Nội Lực |
| `j` | Thân Pháp |
| `k` | Thể Lực |
| `l` | +Cường Lực từ equip/bonus |
| `m` | +Thân Pháp từ equip/bonus |
| `n` | +Nội Lực từ equip/bonus |
| `o` | +Thể Lực từ equip/bonus |
| `p` | +HP flat bonus |
| `q` | HP percent/scaling bonus |

### Resource bars

| Field | Meaning |
|------|---------|
| `s / r` | current HP / max HP |
| `u / t` | current MP / max MP |
| `w / v` | current Power / max Power |

### Derived combat stats

| Field | Meaning |
|------|---------|
| `x / y` | min / max damage |
| `z` | defense |
| `A` | dodge |
| `B` | hit / accuracy internal |
| `C` | crit |

### Nested arrays

| Field | Type | Meaning |
|------|------|---------|
| `D` | `ll[]` | equipment đang mặc |
| `E` | `lv[]` | learned skills |
| `F` | `lm[]` | inventory items |
| `ac` | `lt[]` | map icon/timer overlays |

### Method-level behavior trong `lh`

Các method nhỏ của `lh` rất có giá trị vì chúng cho thấy char aggregate được dùng ra sao:

| Method | Ý nghĩa |
|-------|---------|
| `a()` | deep clone `lh`, clone cả `D/E/F` và `U/V/W` |
| `a(int slot)` | lấy equipment theo slot/type |
| `b()` | lấy equip slot `4`, đồng thời set `ad` |
| `c()` | tính aura/set tier từ equipment ranks |

Điều này xác nhận:

- char Java được clone thường xuyên để preview/compare
- slot logic là core của equipment model
- `ad` không phải flag server tự gửi, mà được suy ra từ loadout
- aura tier cũng là thuộc tính suy ra từ loadout

## Tag Map Quan Trọng Từ `ky.java`

### Identity / basic

| Tag | `lh` field | Meaning |
|-----|-----------|---------|
| `9` | `b` | name |
| `26` | `c` | display name |
| `15` | `g` | raw element/class |
| `16` | `f` | gender |
| `27` | `G` | level |
| `24` | `e` | room/status byte |
| `19` | `T` | subtype/battle side byte |
| `4` | `Y` | alt actor/resource id trong payload nhẹ |

### Bars / combat

| Tag | `lh` field | Meaning |
|-----|-----------|---------|
| `17` | `s` | current HP |
| `47` | `r` | max HP |
| `18` | `u` | current MP |
| `48` | `t` | max MP |
| `45` | `w` | current Power |
| `49` | `v` | max Power |
| `42` | `J` | current EXP/progression |
| `73` | `M` | EXP floor/current level lower bound |
| `74` | `N` | EXP ceiling/current level upper bound |
| `43` | `H` | current KEN/gold/collection value |
| `99` | `I` | KEN/gold/collection cap/threshold |

Không nên gọi `42/73/74/43/99` là combat stat. Bằng chứng:

- `jt.a(lh)` vẽ gauge thứ hai bằng `(J - M) / (N - M)`, tức progress trong level/rank hiện tại
- `com.mg.sq.a.a(lh, lh, ...)` set `hs.k = go.k.J`, `hs.l = go.k.G`, `hs.m = go.k.H` trước khi mở battle result
- `hs` dùng icon `expicon` cho `J/M/N`, icon `gold` cho `H/I`
- `ky.a(ku, byte, int)` đọc battle-result packet: tag `42`, `43`, `110`, list `73/74`, equipment và item reward rồi đẩy vào `nq`; `mq` chuyển các giá trị này sang `hs`

Vì vậy naming C# nên tách:

- `ExperienceValue = J`
- `ExperienceFloor = M`
- `ExperienceCeiling = N`
- `CollectionValue` hoặc `KenProgressValue = H`
- `CollectionCap` hoặc `KenProgressCap = I`

Tên "KEN/gold/collection" còn cần chọn theo UI server mới. Về wire contract thì tag đã chốt.

### Base stats / bonus / progression

| Tag | `lh` field | Meaning |
|-----|-----------|---------|
| `118` | `h` | Cường Lực |
| `119` | `j` | Thân Pháp |
| `120` | `i` | Nội Lực |
| `121` | `k` | Thể Lực |
| `196` | `l` | +Cường Lực |
| `197` | `m` | +Thân Pháp |
| `198` | `n` | +Nội Lực |
| `199` | `o` | +Thể Lực |
| `116` | `p` | +HP flat |
| `115` | `q` | HP scaling percent |
| `53` | `K` | free stat points |
| `76` | `L` | free skill points |
| `160` | `ab` | prestige / honor |
| `165` | `Z` | appearance toggle 1 |
| `166` | `aa` | appearance toggle 2 |
| `209` | `S` | title / bracket string 1 |
| `210` | `R` | title / bracket string 2 |

`Q` không có tag riêng rõ ràng trong các payload đang thấy. Nếu null thì client tự derive:

- `<=100` => `Hào Kiệt`
- `101..200` => `Đại Hiệp`
- `>200` => `Chiến Vương`

### Skills / equipment / inventory / appearance

| Tag | Meaning |
|-----|---------|
| `64` | danh sách skill `lv[]` |
| `83` | danh sách equipment `ll[]` |
| `114` | danh sách inventory `lm[]` |
| `90` | danh sách appearance descriptors `df[]` |
| `158` | danh sách map icon/timer overlays `lt[]` |

## Các Struct Con Của Player

### `lv` = learned skill

Field ổn định:

- `a` = skill id
- `b` = name
- `d` = description
- `e` = mana cost
- `f` = current level
- `g` = max level
- `h[]` = description lines / requirements

Ảnh hưởng tới player:

- skill tree UI `de.java` đọc `lh.E`
- battle bootstrap / cast phải bám `lh.E`, không hardcode tách riêng

### `ll` = equipment

Field quan trọng:

- `b` = market/product numeric id khi item nằm trong market/listing
- `c` = equipment key/string id
- `d` = display name
- `e` = equip slot/type
- `f` = raw element/class requirement hoặc class affinity
- `h` = gender / secondary requirement byte
- `i` = requirement/level gate phụ
- `j` = item level
- `k` = lock/expire/special long, mặc định `-1`
- `l` = market/listing price khi đi qua market payload
- `m` = rank
- `n` = resource id dùng cho appearance
- `o` = chưa thấy parser set ổn định trong char-core
- `p/q` = durability-ish pair
- `r` = `lb` stat modifiers
- `s` = extra state byte
- `t` = tradeable

`lb` trong `ll.r` mang stat modifiers cho player:

- +4 base stats
- +damage
- +defense
- +dodge
- +crit
- +max HP
- bonus phần trăm/hiệu ứng phụ `200..204/221`

Tag parser chuẩn của `ll` nằm ở `ky.a(ku,int,int,boolean)`:

| Tag | `ll`/`lb` field | Meaning đã chốt |
|-----|------------------|-----------------|
| outer `83` | `ll.c` | equipment key/string id |
| `84` | `ll.e` | equip slot/type |
| `4` | `ll.n` | resource id / sprite id |
| `139` | `ll.p` | durability/current use counter |
| `27` | `ll.j` | equipment level |
| `26` | `ll.d` | display name, chỉ có ở full payload |
| `135` | `ll.i` | requirement/gate phụ |
| `15` | `ll.f` | class/element requirement |
| `16` | `ll.h` | gender/sub requirement |
| `138` | `ll.m` | rank/quality |
| `144` | `ll.q` | max durability/counter |
| `117` | `ll.g` | description |
| `156` | `ll.s` | extra state byte |
| `85` | `ll.t` | tradeable flag, `1=true` |
| `190` | `ll.k` | lock/expire/special long |

`boolean bl2` trong parser rất quan trọng:

- `false` = equipment mỏng, đủ cho loadout/render: key, slot, resId, durability, level
- `true` = equipment đầy đủ, dùng trong inventory/market/trade/upgrade: thêm tên, mô tả, rank, trade flag, stat modifiers

### `lb` = equipment stat modifier

`lb` không đứng độc lập trong player payload; nó nằm dưới `ll.r` khi equipment được parse full.

| Field | Tag | Text UI trong `com.mg.sq.a.a(ll)` |
|-------|-----|-----------------------------------|
| `a` | `118` | `cường lực` |
| `b` | `119` | `thân pháp` |
| `c` | `120` | `nội lực` |
| `d` | `121` | `thể lực` |
| `e` | `72` | `sức tấn công` flat |
| `f` | `71` | `phòng thủ` flat |
| `g` | `126` | `% chí mạng` |
| `h` | `124` | `né tránh` |
| `i` | `47` | `sinh lực` flat |
| `j` | `200` | `% hấp thu sát thương` |
| `k` | `201` | `% đánh xuyên giáp` |
| `l` | `202` | `% cản đòn` |
| `m` | `203` | `% hồi sinh` |
| `n` | `204` | `% sức tấn công` |
| `o` | `221` | `% sinh lực` |

Điểm đáng lưu ý:

- `lb.n` không chỉ hiển thị UI; `com.mg.sq.a.a(lh)` dùng nó để cộng thêm `% attack` vào damage derived.
- `lb.j/k/l/m/o` hiện chủ yếu thấy ở text UI/contract equipment. Server vẫn nên lưu nguyên để không mất tính năng late-game.
- `ll.b()` trả true khi `q > 0 && p < q`, tức đồ có durability/counter chưa đầy; packet `48` set `p=q` sau khi dùng item lên equipment.

### `lm` = inventory item

Field quan trọng:

- `a` = item id
- `b` = name
- `c` = display name
- `d` = description
- `e` = type
- `f` = subtype
- `g` = quantity
- `h` = price/value
- `j` = resource id
- `k` = requirement / long value
- `l` = stack capacity
- `m` = tradeable

Tag parser chuẩn của `lm` nằm ở `ky.a(ku,int)`:

| Tag | `lm` field | Meaning |
|-----|------------|---------|
| outer `114` | `lm.a` | item id |
| `26` | `lm.b` | item name |
| `117` | `lm.d` | description |
| `106` | `lm.g` và `lm.i` | quantity/current amount |
| `122` | `lm.e` | type |
| `123` | `lm.f` | subtype/sort group |
| `4` | `lm.j` | resource id |
| `145` | `lm.h` | price/value |
| `82` | `lm.l` | stack capacity |
| `132` | `lm.k` | require KEN / long value |
| `85` | `lm.m` | tradeable flag, `1=true` |

`go.a(lm,int)` cộng dồn quantity nếu item id đã tồn tại, còn `go.b(itemId,qty)` trừ quantity. Đoạn decompile của nhánh `g <= 0` tạo mảng mới nhưng không thấy assignment lại vào `go.m`; khả năng cao là artifact/bug decompile, còn intent runtime là remove stack khi hết số lượng. Vì vậy packet item delta của Java không phải lúc nào cũng gửi lại cả inventory.

### `df` = appearance descriptor

`df` là key để recolor/composite sprite nhân vật.

Field:

- `a` = resource family id
- `c` = category/group
- `d` = source palette
- `e` = target palette
- `f[]` = palette choices

Create-character flow `nw.java` cho thấy:

- `W` = body/skin base
- `V` = face family
- `U` = hair/upper overlay family

Tag parser chuẩn:

| Tag | Meaning |
|-----|---------|
| outer `90` value | `df.a` resource family id |
| `91` | category selector: `0=U`, `1=V`, `2=W` |
| `92` | display/name khi parse catalog |
| `93` + `95` | source palette id + raw RGB int bytes |
| `96` + `98` | target palette id + raw RGB int bytes |

`dg` là palette cụ thể:

- `a` = palette id
- `b` = label/name
- `c[]` = list màu `int`, parse từ byte array theo từng `readInt()`

Đây là kết luận đủ mạnh để build model mới, nhưng chưa nên rename raw asset family cứng vào DB quá sớm.

### `lt` = map icon/timer overlays

`lt` rất nhỏ:

- `a` lấy từ tag `4`
- `b` lấy từ tag `157`

Sau khi truy tiếp `om.java` và `jx.java`, shape này không chỉ là counter ẩn. Map scene biến mỗi entry thành icon countdown:

```java
int iconId = ltArray[n2].a;
Image icon = pa.a().b(iconId, false);
this.ap[n2] = new jx(icon, 1);
this.ap[n2].a(ltArray[n2].b);
this.aq = ltArray[n2].a != 200000 || ltArray[n2].b <= 0L;
```

`jx.a(long)` xác nhận `lt.b` là millisecond countdown:

- chia `b / 1000`
- nếu còn ngày thì render dạng `xNgày`
- nếu dưới ngày thì render `HH:MM:SS`
- mỗi vài tick trừ elapsed time bằng `np.f()`

Kết luận:

- `lt.a` = resource/icon id
- `lt.b` = long countdown milliseconds
- `lh.ac` = các overlay trạng thái/timer gắn với player trên map
- id `200000` có nhánh đặc biệt trong `om`: nếu `a == 200000` và `b > 0` thì flag `aq` bị set false
- repo có asset `client/assets/monster/02_candidate_unknown_ranges/range_200xxx_candidate/200000.png`, kích thước `21x19`, hình ngôi sao xanh/vàng dạng `x2`; có thể là buff nhân đôi, nhưng Java client không có label text đủ chắc để đặt tên nghiệp vụ

Điểm đã chắc từ `ky` và `com.mg.sq.a`:

- full snapshot đọc `lt[]` từ tag group `158`
- delta bit `0x100` cũng đọc cùng shape
- bridge `a(lt[])` set `go.k.ac` rồi nếu đang ở map `om` thì gọi `om.a(lt[])`
- vì vậy `lt[]` thuộc player truth nhưng có tác động trực tiếp tới scene map

## Global Runtime State Quanh Player

`go.java` cho thấy player state thật ở runtime không chỉ nằm trong `go.k`.

| Field | Meaning |
|------|---------|
| `go.k` | current player `lh` |
| `go.l` | equipment/global equip list |
| `go.m` | inventory/global item list |
| `go.n` | inventory capacity |
| `go.o` | inventory extra stat |
| `go.r` | skill tree definitions |
| `go.u` | dropped/effect equipment list on map |
| `go.v` | dropped/effect item list on map |

Điều này rất quan trọng khi port server:

- DB `Players` không đủ, cần bảng/aggregate cho equipment/inventory/skills
- một packet player info đầy đủ phải nuôi được cả `go.k`, `go.l`, `go.m`, `go.r`

Các helper mutate inventory/equipment trong `go`:

| Method | Tác động |
|--------|----------|
| `a()` | reset current player, skill tree, bags |
| `a(ll[], lm[], int, int)` | set equipment bag `go.l`, item bag `go.m`, capacity `n`, extra `o` |
| `a(ll)` | append equipment vào bag |
| `b(ll)` | remove equipment object khỏi bag |
| `a(lm,int)` | cộng item quantity nếu id tồn tại, nếu chưa thì append stack mới |
| `a(lm)` | trừ theo `lm.g` |
| `a(int,int)` | set quantity absolute; `0` nghĩa remove all |
| `b(int,int)` | subtract quantity; nhánh `<=0` có intent remove stack nhưng decompile thiếu assignment `go.m = newArray` |
| `b()` | kiểm tra thùng đồ đầy dựa trên `go.l`, `go.k.D`, `go.m`, capacity `n` |

`go.b()` tính số slot inventory theo cách rất Java:

- equipment trong bag trừ số equipment đang mặc `go.k.D.length`
- item type `7` tính theo quantity `g`
- item khác tính 1 slot

Do đó inventory capacity không thể tính đơn giản bằng số row item/equipment trong DB.

## `com.mg.sq.a` Là Cầu Nối Update Char Toàn Cục

Nếu `lh` là truth payload, thì `com.mg.sq.a` là nơi Java nối packet delta vào runtime.

Vai trò của class này:

- giữ `go.k` đồng bộ với packet mới
- cập nhật map scene nếu đang ở `om`
- cập nhật inventory/equipment screen nếu đang mở
- cập nhật profile/skill UI nếu đang mở
- đổi các boolean runtime như `Z`, `aa`, `e`

Các update bridge quan trọng đã thấy:

| Method bridge | Tác động lên char |
|--------------|-------------------|
| `a(String, byte, byte, df, df, df)` | cập nhật `g/f/U/V/W` |
| `a(String, int...)` 12 args | cập nhật level + base stats + bonus stats + HP bonus |
| `a(String, int...)` 8 args | cập nhật HP/maxHP + gauge block + thresholds |
| `a(String, ll[])` | cập nhật equipment `D[]` |
| `a(lv[])` | cập nhật skill list `E[]` |
| `a(String, int, int, int, String, String, String)` | cập nhật `K/L/ab/S/R/Q` |
| `a(lt[])` | cập nhật `ac[]` |
| `c(boolean)` | cập nhật `Z` |
| `d(boolean)` | cập nhật `aa` và đồng thời chỉnh `e` |
| `U()` | refresh các UI char đang mở |

Điều quan trọng ở đây là Java không update UI từ DB hoặc re-fetch full snapshot.

Nó làm theo flow:

1. packet đến
2. apply delta vào `go.k`
3. gọi các màn hình đang sống để rebuild đúng phần cần rebuild

Đây chính là tinh thần server/client mà bản port nên giữ.

## Appearance / Compositor Pipeline

Pipeline cũ:

1. server gửi `lh.U`, `lh.V`, `lh.W`, `lh.D`, `lh.f`, `lh.Z`, `lh.aa`
2. `mb.a(lh)` quyết định các body-part metadata family
3. `mb.a(...)` ghép nhiều lớp sprite thành `mg/md`
4. `lc.a(lh)` thêm aura/set-effect theo quality equipment
5. `nr.a(lh)` thêm weapon overlay nếu equip slot `4` tồn tại

Các rule đã thấy:

- body base dùng `99000 + frameGroup`
- default family `79899/79999/89999` phụ thuộc gender và equip slot
- equip slot `0/1/2` có thể override compositor family qua `ll.n`
- `Z` có thể ngăn override slot 0
- `ad` được set theo việc có equip slot `4`

Kết luận:

- ngoại hình nhân vật không thể chỉ lưu `face/hair/color/skin`
- cần lưu raw appearance descriptors hoặc model đủ để tái dựng `U/V/W`
- equip đổi là appearance đổi ngay

Chi tiết `mb.a(lh)` chọn 4 metadata layer:

| Layer index | Nguồn |
|-------------|-------|
| `0` | hair/upper metadata: nếu equip slot `0` có `n` và `Z=false` thì dùng `ll.n - ll.n % 10 + 99`, ngược lại `U.a + 99` |
| `1` | face metadata: luôn `V.a + 99` |
| `2` | body/clothes lower layer: nếu equip slot `1` có `n` thì dùng `ll.n - ll.n % 10 + 99`, ngược lại gender default `79899/79999` |
| `3` | armor/outer layer: nếu equip slot `2` có `n` thì dùng `ll.n - ll.n % 10 + 99`, ngược lại `89999` |

Khi render frame, `mb` ghép theo thứ tự:

1. body base image `99000 + frameGroup`, recolor bằng `W`
2. face image, recolor bằng `V` rồi `W`
3. hair/upper image, recolor bằng `U`
4. layer outer/body phụ không recolor
5. layer clothes/armor phụ không recolor

Cache key của composite gồm frame group, target palettes `W/V/U`, và metadata resource id của các layer. Nếu đổi màu hoặc đổi equip, cache key đổi và sprite được rebuild.

## Aura, Weapon, Và Body Effect Cũng Là Một Phần Của Char

Hai method nhỏ trong `lh.java` rất đáng chú ý:

- `b()`:
  - tìm equipment slot `4`
  - set `ad = true/false`
  - `nr.a(lh)` dùng chính item đó để dựng weapon overlay
- `c()`:
  - duyệt equipment
  - nếu đủ điều kiện rank/set thì trả về tier `1..4`
  - `lc.a(lh)` dựa vào kết quả này để dựng aura

Kết luận:

- aura không phải effect tách rời character
- weapon overlay cũng không tách
- chỉ cần `lh.D` đổi là:
  - sprite base có thể đổi
  - weapon overlay có thể đổi
  - aura tier có thể đổi

Server mới nếu chỉ lưu “chỉ số” mà không lưu loadout đúng thì sẽ không thể dựng char sát Java.

## Map Player Runtime

`kl.java` là actor player ngoài map.

Nó giữ:

- animation state
- rect/collision `t`
- current composed sprite `d`
- idle/walk/attack/hit/dead variants
- blink/visibility/invulnerable state
- offsets do weapon/aura

Map init trong `om.java`:

1. tạo `this.l = new kl()`
2. `this.l.a(go.k)` để nạp full `lh`
3. add player vào `kd` depth bucket
4. tạo `jt(go.k)` cho gauge
5. load `go.k.ac` vào map scene

Map update trong `om.a(lh, boolean)`:

- gauge `jt` update ngay
- nếu `boolean == true` thì actor `kl` rebuild stat + appearance

`kd.java` cho thấy player được sort cùng monster/object theo:

- đáy sprite `o()+q()`
- class priority `l()`

Kết luận cho remake:

- cần player world-state riêng, không chỉ DB fields
- map player phải có `x/y/direction/action/visibility/aura/weapon overlay`
- khi stat/equip/appearance đổi phải có packet delta và actor rebuild

### `kl` actor state chi tiết

`kl.a(lh)` rebuild toàn bộ actor visual:

- gọi `mb.a(lh)` để lấy 4 metadata layer body
- gọi `lc.a(lh)` để tạo aura theo `lh.c()`
- gọi `nr.a(lh)` để tạo weapon overlay từ equip slot `4`
- dựng các animation:
  - `e` idle/stand
  - `z` walk
  - `f` hit/attack transition
  - `A` weapon/attack variant
  - `h` death/down variant
  - `g` special/action animation
- set collision/render rect dựa trên composed sprite và `k t`
- gọi `b(lh)` để scale một số speed/range theo level

`kl.b(lh)` dùng level trực tiếp:

- `i = 4 + G / 10`, cap `9`
- `a = 11 + G / 10`, cap `16`

Điều này có nghĩa level không chỉ là text UI; nó ảnh hưởng runtime actor ngoài map.

### `jt` map gauge

`jt` không dùng MP/Power trực tiếp. Nó vẽ 2 bar:

- bar trên = `s / r` HP
- bar dưới = `(J - M) / (N - M)`

Vì vậy bộ `J/M/N/H/I` không nên bị bỏ qua:

- `J/M/N` là gauge EXP/progression thật ngoài map.
- `H/I` là trục KEN/gold/collection dùng ở battle result, không phải combat stat.

### `kd` depth bucket

`kd` giữ actor player riêng trong `f`, nhưng khi render nó đưa player vào list `g` chung với actor/object đang visible. Sort theo:

- `o() + q()` tức đáy sprite
- nếu bằng đáy thì `l()` làm priority phụ

Port mới cần giữ sorting theo chân nhân vật, không sort theo center hoặc y raw, nếu muốn map render giống Java.

## Room / Player List Runtime

`os.java` và `do.java` cho thấy Java có thêm một lớp “char ngoài map” dùng cho room/player list.

`do` model:

| Field | Meaning |
|------|---------|
| `a` | player name |
| `b` | level |
| `c` | status byte |
| `d` | prestige / honor |
| `e` | status message |
| `f` | wager / bet / room amount |

`os.a(byte)` build `do` cho chính player từ:

- `go.k.b` -> name
- `go.k.ab` -> honor
- `go.k.G` -> level
- `go.k.e` -> status byte
- `go.k.X` -> stake long
- `go.k.P` -> status message

Nghĩa là player truth trong Java không chỉ phục vụ map và battle.
Nó còn phục vụ social/room list runtime.

Đây là lý do `lh.e`, `lh.P`, `lh.X` không nên bị bỏ qua.

`os.java` còn cho thấy action menu phụ thuộc trực tiếp vào `do.c` và `do.f`:

- status byte quyết định menu hiện `Đánh`, `Giao dịch`, `Chat`, `Xem ME`
- nếu `do.f > 0` thì có nhánh wager/bet để mở đánh
- khi `go.k.aa=true`, `os` ép `go.k.e=2`; khi tắt và `e==2` thì trả về `0`

`ha.java` là versus/preview screen:

- nếu nhận `lh` thì hiển thị composite sprite từ `mb.a(lh,false)`
- đọc `lh.g`, `lh.G`, `lh.Q`, `lh.c`
- nếu preview monster/special actor thì dùng label IQ riêng

Vì vậy room/social preview cũng cần snapshot đủ appearance/title/class, không chỉ player name.

## Battle Player Runtime

Battle không dùng trực tiếp `kl`.

Pipeline:

1. `lh` -> `lg` runtime state
2. `lg` -> `ni` actor composite
3. `ms` giữ `lg[][]` cho 2 phe
4. `mx` render actor, HP/MP/Power, selector, status text

`lh` dùng trong battle để cấp:

- HP / MP / Power
- level
- element/class
- appearance
- learned skills
- equipment-driven stat modifiers
- special actor id `Y` cho form khác compositor thường

Kết luận:

- battle player state phải bootstrap từ player thật
- monster battle bootstrap hiện tại đang dùng player giả là chưa đúng hướng

### `ms` battle state wrapper

`ms` nhận hai mảng `lh[]` theo hai phe và wrap thành `lg[][]`:

- `q[0]` = phe player/local side
- `q[1]` = phe đối phương
- mỗi `lh` được giữ nguyên bên trong `new lg(lh)`

Khi server gửi update battle mới, `ms.a(byte[], byte[], byte[], lh[], lh[])` không thay object actor hoàn toàn. Nó copy lại các resource bar vào `lh` đang nằm trong `lg`:

- `r/s` = max/current HP
- `t/u` = max/current MP
- `v/w` = max/current Power

Điều này cho thấy battle sync của Java ưu tiên mutate state hiện có để animation/HUD giữ liên tục.

### `mx` battle renderer/HUD

`mx` build actor render từ `lg.a()` tức từ `lh`:

- phe 0 luôn dùng `mx.b(lh, side)`
- phe 1 nếu `lg.b()` và `lh.Y > 0` thì dùng branch special actor `a(lh, side)`, ngược lại dùng composite player thường
- HP/MP/Power bars đọc qua `lg.m/l`, `lg.n/o`, `lg.q/r`
- name labels đọc từ `lg.j()` tức `lh.b`

`mx.b(lh,int)` dựng đủ composite trong battle:

1. `mb.a(lh)` lấy metadata body parts
2. `lc.a(lh)` lấy aura
3. `nr.a(lh)` lấy weapon overlay
4. dựng nhiều animation `mg/mc`
5. trả `ni` battle actor composite

Kết luận: battle actor cũng phụ thuộc `D`, `U/V/W`, `Z`, `f`, `Y`, `O`, không thể dựng từ stats đơn thuần.

### `ni` battle actor state machine

`ni` là renderer/action state của một fighter composite:

- giữ nhiều sprite animation: idle, walk/advance, attack, hit/miss, skill/special, death
- giữ `nr` weapon overlay và đổi mode overlay theo action
- text feedback có các trạng thái như `Xí Hụt`, `Đỡ đòn`
- `a(int state)` đổi animation mà vẫn giữ position/direction hiện tại

Server không cần port renderer này vào backend, nhưng client mới cần hiểu rằng battle packet phải đủ dữ liệu để dựng lại cùng state machine.

## `lg` Là Wrapper Battle-State Sát `lh`

`lg.java` không tạo một data model mới độc lập.
Nó chỉ wrap quanh `lh` và thêm vài timer/status counters.

Những gì `lg` lấy trực tiếp từ `lh`:

- online/active flag `O`
- level `G`
- side/type byte `T`
- HP `s/r`
- MP `u/t`
- Power `w/v`
- skills `E[]`

Các gì `lg` thêm:

- timer/status counter `d/e/f/g/h`
- vài runtime flag nhỏ cho turn engine/render

Kết luận:

- battle runtime của Java vẫn bám chặt player truth payload
- không có chuyện battle player là model khác hẳn rồi chỉ copy vài stat

## Delta Update Packet Của Player

`ky.b(ku)` parse packet update nhân vật theo bitmask tag `23`.

Các bit quan trọng:

| Bit | Nội dung |
|-----|----------|
| `1` | appearance + class/gender + `df` triplet |
| `2` | level + base stats + bonus stats + HP bonus |
| `4` | HP/maxHP + EXP/KEN progress values |
| `8` | free stat points + free skill points + prestige + title strings |
| `0x10` | booleans `Z/aa` |
| `0x20` | skill levels list |
| `0x40` | equipment list |
| `0x100` | map icon/timer overlays `lt[]` |

Sau khi xử lý, client gọi `U()` để refresh UI/map/profile liên quan.

Đây là một chốt quan trọng cho server mới:

- không nên chỉ trả full snapshot mọi lúc
- Java cũ có delta model rõ ràng cho player

Chi tiết tag theo từng bit:

| Bit | Tags đọc | Bridge gọi vào `com.mg.sq.a` |
|-----|----------|-------------------------------|
| `1` | `9`, `15`, `16`, `90/91/93/95/96/98` | `a(name, class, gender, U, V, W)` |
| `2` | `27`, `118`, `119`, `120`, `121`, `196`, `197`, `198`, `199`, `116`, `115` | `a(name, level, base/bonus stats, hp bonus)` |
| `4` | `17`, `47`, `42`, `73`, `74`, `43`, `99` | `a(name, hp/maxHp, J, M/N, H/I)` |
| `8` | `53`, `76`, `160`, `27`, `209`, `210` | `a(name, K, L, honor, S, R, Q)` |
| `0x10` | `165`, `166` | `c(Z)`, `d(aa)` |
| `0x20` | `64`, nested `67` | `a(lv[])`, chỉ level skill trong delta |
| `0x40` | `83[]` | `a(name, ll[])` |
| `0x100` | `158[]` chứa `4`, `157` | `a(lt[])` |

`ky` có nhiều parser `lh` khác nhau, không chỉ một full reader:

| Parser | Ngữ cảnh | Độ đầy đủ |
|--------|----------|-----------|
| `a(ku)` | full fighter/current char info | đầy đủ nhất: base stat, bonus stat, bars, skill level/mana, equipment, inventory, appearance, toggles, counters |
| `a(ku,int,int)` | profile/light payload | có identity, bars, skill id mỏng, equipment mỏng, appearance; không có inventory |
| `b(ku,int,int)` | battle prepare full-ish | có `O`, `Y`, `T`, bars, skill đủ tên/mô tả/mana, equipment, inventory, appearance |
| `c(ku,int,int)` | battle prepare compact | có bars, skill id mỏng, equipment, inventory item id/qty/resId mỏng, appearance |

Vì vậy server mới nên tách packet builder theo use-case:

- `FullPlayerSnapshot` cho login/profile/char status
- `PlayerMapSnapshot` cho map/room actor
- `PlayerBattleSnapshot` cho battle bootstrap
- `PlayerDelta` cho các thay đổi nhỏ

## Profile / Status UI Đào Sâu

### `dd` = room/profile mini card

Hiển thị trực tiếp từ `lh`:

- sprite composite
- raw class icon từ `g`
- level `G`
- `D.Vọng` từ `ab`
- title strings `Q`, `S`, `R`

### `cz` = equipment compare / stat preview card

Flow:

1. clone `lh`
2. chạy lại `com.mg.sq.a.a(lh)` để recalc full derived stats
3. so sánh với bản trước

Nó dùng trực tiếp:

- `x` attack
- `B` accuracy/hit
- `r` max HP
- `z` defense
- `A` dodge
- `C` crit

Điều này chứng minh:

- derived stats phải tồn tại như một phần của char truth
- equip preview không thể làm nếu server/model chỉ lưu 4 stat gốc

### `da` = full character status screen

`da` là một mỏ vàng vì nó kéo gần như toàn bộ char core vào một nơi:

- base stats `h/i/j/k`
- equip bonus `l/m/n/o`
- free stat points `K`
- title strings `Q/R/S`
- level `G`
- prestige `ab`
- HP block `s/r/p/q`
- các gauge phụ `J/M/N/H/I`
- derived stats qua `jp/jq/js/jr`
- full composite sprite với aura + weapon

Nó còn chứng minh:

- character screen có ít nhất 3 thanh/gauge khác nhau
- `K` là điểm cộng stat riêng
- derived stat được recompute từ `lh` + equip effects, không đọc DB trực tiếp

Flow cộng stat trong `da`:

- UI có 8 nút, 4 nút tăng và 4 nút giảm preview.
- `A` là free stat points còn lại trên preview, lấy từ `lh.K`.
- `y[]` là điểm đã cộng tạm nhưng chưa commit.
- `z[]` là stat preview sau khi cộng.
- thứ tự commit sang `ks.a(int,int,int,int)` là:
  - `y[0]` -> tag `118` Cường Lực
  - `y[2]` -> tag `119` Thân Pháp
  - `y[1]` -> tag `120` Nội Lực
  - `y[3]` -> tag `121` Thể Lực
- nếu rời màn hình khi `y[]` còn pending, UI hỏi có cập nhật không.

Nghĩa là command `10` không gửi “stat choice + 1”. Nó gửi batch delta 4 stat. Server mới có thể hỗ trợ single increment nội bộ, nhưng nếu muốn tương thích Java thì phải accept batch delta.

### `ib` + `de` = skill tree UI gắn chặt với char

`de.a(lh)` đọc:

- `L` làm tổng skill points
- `E[]` làm current skill levels
- `G` để check level requirement

`de` giữ cả:

- current allocated points preview
- points còn lại chưa commit
- dependency giữa các node skill

`ib` là shell UI quanh `de`, thêm:

- mô tả skill
- preview tăng/giảm level
- packet update khi commit

Flow skill commit:

- `de.B` là skill points còn lại trong preview, init từ `lh.L`.
- `de.r[]` là level skill sau preview.
- `de.s[]` là số point cộng tạm theo từng skill node.
- `de.i(skillIndex)` check điều kiện:
  - còn points
  - chưa max level
  - đủ cost `go.r[index].c[level].c`
  - đủ level nhân vật `go.r[index].c[level].b`
  - dependency skill đã học qua `go.r[index].d`
- `ib.v()` gom:
  - `int[] skillIds = go.r[n].a`
  - `int[] levels = de.x()`
  - gửi `ks.a(skillIds, levels)` command `27`

Command `27` vì vậy là batch commit skill levels, không phải một request “learn skill X” đơn lẻ.

Điều quan trọng:

- player có hai pool point riêng:
  - `K` = stat points
  - `L` = skill points
- server mới hiện chưa có skill-point model đúng nghĩa cho player

## Equipment / Inventory Runtime Đào Sâu

### `hh` = inventory/equipment screen

`hh` clone `go.k`:

- `this.M = go.k.a()`

Sau đó:

- dựng 6 slot equip `F[]`
- dựng inventory grid từ `go.m`
- dùng `go.l` làm master equipment list
- khi equip/unequip sẽ rebuild `lh.D`
- rồi gọi lại compositor `mb + lc + nr`

Đây là evidence mạnh rằng:

- `go.l` và `go.m` là runtime sources riêng nhưng vẫn phải hòa vào `lh`
- equipment/inventory screen không hoạt động trên “metadata nhẹ”
- nó cần char clone thật để preview

### Stack rules của inventory item

`hh` cho thấy:

- nếu `lm.l > 0` thì item có stack cap
- item cùng id có thể tách thành nhiều ô
- quantity sync về sau có thể add/remove partial stack

Nghĩa là inventory server cần:

- quantity
- stack cap
- item type
- resource id
- trade flags

### Equipment preview và char rebuild

Khi equip/unequip:

1. build lại `lh.D`
2. clear sprite cũ
3. gọi `mb.a(lh, false)`
4. attach `lc.a(lh)` aura
5. attach `nr.a(lh)` weapon

Đây là core loop rất quan trọng của character system.

### `hh` command surface chi tiết

Các action chính trong `hh`:

| UI action | Command/helper | Ghi chú |
|-----------|----------------|--------|
| commit loadout | `ks.a().c(stringArray)` -> cmd `37`, `89=0` | gửi toàn bộ key equipment đang mặc |
| bỏ/vứt equipment | `ks.a().a(stringArray)` -> cmd `37`, `89=2` | xóa equipment theo key |
| item lên equipment | `ks.a().a(itemId, equipKey)` -> cmd `48` | dùng búa/sửa/áp vật phẩm vào đồ |
| dùng item | `ks.a().f(itemId)` -> cmd `51` | có thể consume item và sinh output |
| bỏ item quantity | `ks.a().f(itemId, qty)` -> cmd `83` | quantity-only delta |
| action item đặc biệt | `ks.a().e(itemId)` -> cmd `84` | item one-click/special |
| mua thêm ngăn | `ks.a().u()` -> cmd `86` | inventory capacity |
| rao bán equip | `ks.a().a(equipKey, price)` -> cmd `112` | price gửi tag `132` |
| rao bán item | `ks.a().a(itemId, qty, price)` -> cmd `112` | stack sale |
| ẩn/hiện nón | `ks.a().b(boolean)` -> cmd `7`, mode `0` | update `Z` |

Menu equip trong `hh` cho thấy equip slot và bag không chỉ là UI:

- chuyển đồ từ bag vào slot là local preview trước
- chỉ khi bấm `Cập nhật` mới gửi command `37`
- nếu rời màn hình khi loadout preview khác thật, UI hỏi commit
- `go.l` bị mutate sau ack server, không nên mutate vĩnh viễn chỉ bằng thao tác preview

Stack item trong `hh`:

- nếu `lm.l == 1`, mỗi quantity có thể thành một entry thao tác riêng
- nếu `lm.l > 1`, UI tách stack theo cap
- nếu `lm.l <= 0` hoặc `Integer.MAX_VALUE`, giữ như một stack lớn

Server mới cần lưu cả `quantity` và `stackCap`; client mới có thể render khác, nhưng protocol Java cũ nghĩ theo stack splitting này.

## Skill Tree Và Skill Progression Đào Sâu

`de.java` cho thấy skill tree không phải chỉ là list skill level đơn giản.

Nó còn cần:

- dependency giữa node qua `lw.d`
- requirement theo level player
- cost per level qua `lw.c[level].c`
- mô tả kết quả sau khi nâng qua `lw.c[level].e`

Từ góc nhìn player data, cần ít nhất:

1. current learned skills `E[]`
2. free skill points `L`
3. current player level `G`
4. skill tree definition catalog `go.r`

Server port nếu bỏ `L` hoặc không tách learned skill ra khỏi battle skill list thì sau này skill tree sẽ lệch Java rất nhanh.

## Social / Interaction Surface Của Char

`os.java` chứng minh thông tin char còn nuôi:

- chat target
- duel/challenge availability
- trade availability
- watch battle availability
- room status icon

Một player entry trong list không chỉ cần tên và level.
Nó còn cần:

- status byte `e`
- prestige `ab`
- status message `P`
- wager/stake `X`
- đôi khi cả full `lh` để mở “Xem ME”

## Refresh Chain Khi Char Đổi

Java cũ có chain refresh khá rõ:

1. packet delta đến `ky`
2. bridge trong `com.mg.sq.a` mutate `go.k`
3. nếu đang ở map:
   - `om.a(go.k, true/false)`
4. nếu đang mở inventory/equipment:
   - `hh.w()` hoặc update item/equip runtime
5. nếu đang mở skill tree:
   - `ib.a(go.k)`
6. nếu có screen khác giữ card/profile:
   - `U()` dispatch refresh

Đây là insight quan trọng cho kiến trúc mới:

- player aggregate phải là source trung tâm
- mỗi feature chỉ subscribe và rebuild phần nó cần

## Những Điểm Char-Core Đã Chắc Hơn Sau Khi Đào Sâu

1. `lh.e` là status/state byte, không phải trait ngoại hình.
2. `lh.P` là status message cho room/player list.
3. `lh.X` là long value kiểu stake/bet/amount trong room/social context.
4. `K` và `L` là hai hệ point riêng, không được trộn.
5. aura/weapon là hệ quả trực tiếp từ equipment loadout của char.
6. inventory/equipment screen chạy trên char clone thật, không phải metadata rời.
7. `com.mg.sq.a` là tầng apply-delta trung tâm cho toàn bộ character runtime.

## Create Character Flow

`nw.java` chứng minh client create-char chỉ chọn:

- giới tính
- hệ
- khuôn mặt
- kiểu tóc
- màu tóc
- màu da

Client không tự sinh stat truth.

Server phải quyết định:

- raw `lh.g` element/class code
- stat khởi tạo
- free stat point
- skill point
- appearance descriptors `U/V/W`

Chi tiết flow `nw`:

1. UI có 6 selector: giới tính, hệ, khuôn mặt, kiểu tóc, màu tóc, màu da.
2. hệ UI là index `0/1/2`, nhưng trước khi gửi đổi sang raw Java code bằng mảng `{1,2,4}`.
3. `p[gender][hairIndex]` là source cho `U`.
4. `q[gender][faceIndex]` là source cho `V`.
5. `r[gender]` là source cho `W`.
6. đổi màu tóc set `U.e = selected dg`.
7. đổi màu da set `W.e = selected dg`.
8. preview tạo `lh` tạm, set `W/U`, rồi gọi `mb.a(...)`.

Outbound `ks.a(int gender, int rawElement, df U, df V, df W)` gửi command `8`:

| Tag | Value |
|-----|-------|
| `16` | gender |
| `15` | raw element/class `1/2/4` |
| repeated `90` | `df.a` của từng descriptor |
| repeated `96` | target palette id `df.e.a` |

Điểm quan trọng: client create-char không gửi face/hair/skin index thô. Nó gửi raw descriptor family + palette id. Server mới nếu chỉ lưu index UI thì vẫn cần map ngược ra `df` tương đương để snapshot sau login dựng đúng sprite.

## Outbound Character Protocol Từ `ks.java`

`ks` là outbound transport manager.

`kw` là envelope command phía client:

- `kw.a` = command id
- các field còn lại là payload tạm theo từng command family
- `ks.a(kw)` convert `kw` sang `kx` TLV packet bằng tag Java cũ

`kw` không có semantic cố định theo field name. Cùng một field được reuse giữa nhiều command:

| `kw` field | Vai trò trong char-core |
|------------|-------------------------|
| `p/q` | gender/raw element trong create-char |
| `O` | `df[]` create-char descriptors |
| `E/F/G/H` | stat delta batch cho command `10` |
| `I/J` | skill id + level arrays cho command `27` |
| `Q` | equipment key trong nhiều command |
| `R` | equipment key array |
| `M` | item id |
| `g` | item quantity hoặc generic count |
| `S` | mode của command `37` |
| `T` | sub-mode của command families `7`, `55`, `56`, `129` |
| `C` | session/context string cho recipe upgrade/combine |
| `A` | recipe source side byte `187` |
| `Y/h` | item id array + quantity array trong final recipe request |
| `ah` | KEN/price/fee long |
| `aa` | market context string |
| `ag` | trade session/queue token |

Không nên port `kw` thành DTO typed duy nhất. Nên tạo DTO theo command family rồi encode ra tag shape tương ứng.

Điểm rất quan trọng cho server port:

- `ky.java` cho thấy inbound của player đã là TLV/tag-based
- `ks.java` cho thấy outbound của char cũng TLV/tag-based tương ứng
- muốn bám Java sâu thì không chỉ clone data model `lh`, mà còn phải clone shape command family của `ks`

### Command char-core đã chốt được

| Cmd | Helper `ks` | Tags gửi đi | Meaning chốt được | Evidence |
|-----|-------------|-------------|-------------------|----------|
| `8` | `a(int,int,df,df,df)` | `16`, `15`, `90`, `96` | create character với `gender`, raw class code, 3 descriptor `U/V/W` | `nw.java` |
| `10` | `a(int,int,int,int)` | `118`, `119`, `120`, `121` | commit cộng stat point | `da.java` |
| `27` | `a(int[], int[])` | `64`, `67` | commit level của skill list | `ib.java` |
| `37` mode `0` | `c(String[])` | `83`, `89=0` | commit full equipment loadout hiện tại | `hh.C()` và equip flow |
| `37` mode `2` | `a(String[])` | `83`, `89=2` | vứt/bỏ equipment theo key | `hh.java` confirm discard |
| `48` | `a(int, String)` | `114`, `83` | apply item/material lên equipment key | `hh.java` case chọn item + equip |
| `51` | `f(int)` | `114` | dùng/mở inventory item theo item id | `hh.java` |
| `83` | `f(int, int)` | `114`, `106` | bỏ item theo quantity | `hh.java` |
| `84` | `e(int)` | `114` | action một-click cho item đặc biệt | `hh.java` |
| `86` | `u()` | none | mua thêm ngăn chứa / inventory capacity | `hh.java` |
| `96` | `r(String)` | `83` | submit equipment key vào flow nâng cấp | `id.java`, `com.mg.sq.a.h(String)` |
| `112` equip | `a(String, long)` | `83`, `132` | rao bán equipment với giá KEN | `hh.java` |
| `112` item | `a(int, int, long)` | `114`, `106`, `132` | rao bán item stack với giá KEN | `hh.java` |
| `7` mode `0` | `b(boolean)` | `147=0`, `165` | toggle `Z`, UI gọi như ẩn/hiện nón | `hh.java` |
| `7` mode `1` | `c(boolean)` | `147=1`, `166` | toggle `aa` | caller trực tiếp chưa chốt |

### Command phụ chạm player nhưng không phải char-core

Nhóm này không cần port vào `Player` aggregate, nhưng cần dispatcher nhận biết vì UI/player state có thể bị ảnh hưởng:

| Cmd | Tags chính | Vai trò |
|-----|------------|--------|
| `9` | `9`, `91/101` tùy mode | player/social action theo tên target |
| `11/13/29/43` | room/map key string | current location, join/move room hoặc action theo map context |
| `12` | `192`, `1`, `157`, `9`, `40` | room/social invite/message có tiền cược hoặc timestamp |
| `30` | `q` byte | action mode ngắn từ UI |
| `31/32/33/41` | tên/context string | room/social command, không mutate trực tiếp `lh` |
| `42` | string | request/status text |
| `52` | `T` sub-mode | secondary UI command family |
| `55` | `147` sub-mode, `150` session id | trade family, xem bảng dưới |
| `56/57/64/65` | room/social payload | join room/list room/chat-room style packet |
| `113/114/115/116` | market context/category | market list/buy/sell support quanh inventory |
| `127/128/129/130/131/132/133` | misc/social/system tags | packet phụ, không cần nhét vào player model |

Kết luận thiết kế: `PlayerService` chỉ nên xử lý aggregate char/inventory/equipment/stat/skill. Các command trên đi qua room/social/market/trade service riêng, nhưng vẫn dùng chung repository inventory/equipment khi có giao dịch vật phẩm.

### Trade family `55` gắn chặt với inventory/equipment của char

`of.java` là trade screen và dùng một family command riêng `55` với sub-mode trong tag `147`.

| Mode | Helper `ks` | Payload chính | Nghĩa suy ra |
|------|-------------|---------------|--------------|
| `0` | `j(String)` | `9=targetName` | mở/request trade với player target |
| `1` | `a(boolean)` | `150=queueTarget`, `31=bool` | accept/reject invitation hoặc ready-state theo queue trade |
| `2` | `g(int)` | `150=currentTradeId`, `132=ken` | cập nhật lượng KEN offer |
| `3` | `e(int,int)` | `150=currentTradeId`, `114=itemId`, `106=qty` | add/remove item stack vào khung trade |
| `4` | `k(String)` / `l(String)` | `150=currentTradeId`, `83=equipKey`, `106=1/0` | add/remove equipment vào khung trade |
| `5` | `l()` | `150=currentTradeId` | leave/close trade session |
| `6` | `m()` | `150=currentTradeId`, `41=j` | confirm/lock trade state |
| `7` | `n()` | `150=currentTradeId` | cancel/unready current trade state |

Điểm cần nhớ:

- trade của Java dùng trực tiếp `ll.c` và `lm.a/g`
- server không thể xử lý trade nếu không có inventory/equipment aggregate thật
- state trade không chỉ là money, mà là danh sách equip key + item stack + ready state của hai phía

Chi tiết thêm từ `of.java`:

- trước khi confirm mode `6`, UI tự kiểm tra sức chứa hành trang sau giao dịch:
  - bắt đầu từ `go.l.length - go.k.D.length`
  - cộng item trong `go.m`
  - cộng đồ/item đang nhận từ đối phương
  - trừ đồ/item đang đưa đi
  - so với `go.n` hoặc capacity screen `G.x()`
- add equipment vào trade chỉ cho item `ll.a() == true` tức tradeable.
- add item stack dùng dialog quantity, sau đó gửi mode `3` với `itemId + qty`.
- khi finalize thành công, `of` tự mutate local bags:
  - nhận từ đối phương thì `go.a(ll)` hoặc `go.a(lm, qty)`
  - đồ/item mình đưa đi thì `go.b(ll)` hoặc `go.a(lm)` để trừ stack

Vì vậy trade server không chỉ cần validate ownership; nó còn phải validate capacity hai bên trước finalize, nếu không client có thể reject/hiện lỗi lệch.

### Hai family xử lý đồ nâng cấp / chế tác vẫn là char-core

Hai màn hình `id.java` và `ho.java` dùng hai command family song song:

- `id.java` dùng `97/98`
- `ho.java` dùng `100/101`

Shape packet của cả hai family gần như giống nhau:

- target/context string qua tag `186`
- equipment keys qua tag `83`
- item stacks qua cặp `114/106`
- tiền/phí qua tag `132`

Điểm đã chắc:

- `id.java` là flow nâng cấp vì phản hồi dùng thẳng text `Nâng cấp thành công/thất bại`
- server trả lại danh sách `ll[]` và `lm[]` mới sau khi xử lý
- cả hai flow đều mutate thẳng inventory/equipment thật của player, không phải metadata phụ

`ho.java` đã chốt là flow `Kết hợp`: constructor tạo nút `Kết hợp`, kết quả `101` hiển thị `Kết hợp thành công/thất bại`, còn command mở/mutate là `99/100`.

Chi tiết giống nhau giữa `id` và `ho`:

- Cả hai dựng source list từ `go.l` và `go.m`, nhưng loại bỏ equipment đang mặc trong `go.k.D`.
- Cả hai tách item stack theo `lm.l` giống `hh`.
- Recipe slot chứa `dc`:
  - `j==0` = equipment
  - `j==1/2` = item stack
  - `j==3` = UI placeholder/khác
- Add/remove recipe không tự commit local ngay; nó gửi command mutate lên server (`97` hoặc `100`).
- Khi có kết quả cuối (`98` hoặc `101`), UI:
  - trả nguyên liệu đang nằm trong recipe về `go` nếu cần
  - add equipment/item snapshot server trả về
  - hiện success/fail theo byte `189`

Khác biệt chính:

| Screen | Open cmd | Mutate cmd | Final cmd | Text result |
|--------|----------|------------|-----------|-------------|
| `id` | `96` | `97` | `98` | `Nâng cấp thành công/thất bại` |
| `ho` | `99` | `100` | `101` | `Kết hợp thành công/thất bại` |

Server mới nên coi đây là hai recipe engines cùng shape packet nhưng khác rule business.

## UI -> Command -> Server Reaction Kỳ Vọng

### `nw` create-char

Client gửi command `8`.

Server Java-faithful phải trả ít nhất:

- full snapshot đủ để dựng `go.k`
- equipment mặc định nếu sprite gốc cần
- inventory/skill init nếu scene sau login cần dùng ngay

### `da` cộng stat

Client gửi command `10`.

Server không chỉ trừ `K` và tăng `h/i/j/k`.
Nó còn phải phát delta làm client refresh:

- base stats
- free stat points `K`
- derived stats/bars nếu công thức đổi

### `ib/de` cộng skill

Client gửi command `27`.

Server phải trả lại:

- `E[]` mới
- `L` mới
- bất kỳ stat/battle-skill side effect nào nếu skill passive có tác dụng ngay

### `hh` equip, item use, market, appearance toggle

`hh` là màn hình cho thấy rõ nhất player aggregate phải sống thật.

Các command nó bắn gồm:

- `37` commit loadout
- `48` item lên equip
- `51/83/84` dùng hoặc bỏ item
- `112` rao bán
- `7` toggle appearance
- `86` mua thêm ngăn chứa

Server phải phản hồi bằng state thật chứ không phải ack rỗng:

- `ll[]` mới
- `lm[]` mới
- `Z/aa` mới
- inventory capacity mới nếu mua slot
- stat/appearance delta nếu loadout đổi

### `id` nâng cấp equipment

Flow `id` cho thấy server sau nâng cấp phải trả:

- equipment list mới
- item/material list mới
- success byte riêng

Client method nhận là `id.a(ll[] llArray, lm[] lmArray, byte by2)`.

Nghĩa là server mới sau này không nên chỉ trả một item upgraded duy nhất.
Java cũ nghĩ theo snapshot cục bộ của cả bag/equip liên quan.

### `of` trade

Trade flow kỳ vọng nhiều delta nhỏ:

- add/remove equip
- add/remove item qty
- đổi KEN offer
- ready/unready
- finalize hoặc cancel

Nếu server mới không giữ một trade aggregate riêng nhưng bám `ll/lm` thật của mỗi player thì flow này sẽ gãy rất nhanh.

## Inbound Response Contract Từ `ky.java`

Phần outbound của `ks` chỉ mới cho thấy client muốn gì.
Phần `ky` mới cho thấy server Java cũ thực sự trả gì về để mutate char runtime.

Điểm quan trọng:

- nhiều flow không trả một `ok/fail` đơn giản
- server thường trả kèm item/equip snapshot hoặc partial delta ngay trong cùng packet
- nhiều UI của char sống nhờ đúng shape response này

### `55` = trade state machine inbound

`ky.s(ku)` parse command `55` theo sub-mode tag `147`.

| Mode | Tags chính | Callback bridge | Nghĩa chốt được |
|------|------------|-----------------|-----------------|
| `0` | `9`, `150` | `b.k(name)` | incoming trade invite; `150` là trade queue/session token được nhét vào `ks.a().g` |
| `1` | `9`, `150`, `31` | `b.m(name)` hoặc `b.n(name)` | accept/reject invite; nếu accept thì set `ks.a().f = tradeSessionId`, reset `ks.j` |
| `2` | `9`, `41`, `132` | `b.l((int)ken)` | đối phương đổi KEN offer |
| `3` | `9`, `41`, `114/106` + item payload | `b.a(lm, qty)` hoặc `b.a(lm)` | đối phương thêm hoặc rút item stack khỏi khung trade |
| `4` | `9`, `41`, `83/106` + equip payload | `b.b(ll)` hoặc `b.j(key)` | đối phương thêm hoặc rút equipment khỏi khung trade |
| `5` | `9` | `b.i(name)` | hủy trade / trade bị đóng; UI `of.c(name)` hiện message hủy |
| `6` | `132`, `83[]`, `114[]` | `b.a(ll[], lm[], ken)` | server gửi snapshot kiểm tra cuối trước khi lock/finalize |
| `7` | none rõ ràng thêm | `b.P()` | screen-level reset/clear pending confirm của trade |
| `8` | none rõ ràng thêm | `b.O()` | clear loading/progress trong trade flow |
| `9` | `9` | `b.l(name)` | finalize thành công; UI hiện `Giao dịch thành công!` |

Các điểm chốt thêm:

- tag `41` trong trade là counter/step index dùng cho `ks.j`
- trade snapshot mode `6` mang đủ `ll[]`, `lm[]`, `KEN`
- UI `of.java` ghi log/chat summary trực tiếp từ snapshot mode `6`, không tự recompute từ local state

### `96/97/98` = flow nâng cấp equipment

Family này map sang `id.java`.

#### `96` mở flow

`case 96` đọc:

- `186` = session/context string
- `83` = equipment key
- `1` = message

Rồi gọi `b.a(session, equipKey, message)` -> `com.mg.sq.a.a(...)` -> mở màn hình `id`.

Nghĩa là:

- server chủ động trả context string cho flow nâng cấp
- màn hình nâng cấp không tự sinh context từ client

#### `97` mutate recipe / selection

`case 97` đọc:

- `187` = source side byte
- `83` hoặc `114/106` = equip key hoặc item+qty vừa mutate
- `132` = fee hiện tại
- `1` = status/message text
- `188` = ready-status byte

Rule callback:

- có `83` và `187==0` -> `b.d(key, msg, ready, fee)` -> move equip từ bag sang slot recipe
- có `83` và `187!=0` -> `b.c(key, msg, ready, fee)` -> move equip ngược lại
- có `114` và `187==0` -> `b.b(msg, ready, fee)` -> add item stack vào recipe
- có `114` và `187!=0` -> `b.b(itemId, qty, msg, ready, fee)` -> remove item stack khỏi recipe

Điểm chốt được từ `id.java`:

- `ready-status` tag `188`:
  - `0` = chưa đủ nguyên liệu
  - `1` = đủ điều kiện, có thể hiện hộp thoại xác nhận nâng cấp
- `message` tag `1` là text business server muốn hiện ngay trên UI
- `fee` tag `132` là phí đang phải trả để nâng cấp

#### `98` trả kết quả cuối

`ky.B(ku)` parse command `98`:

- `186` = session/context string
- `189` = success byte
- full `83[]` = equipment snapshot sau xử lý
- full `114[]` = item snapshot sau xử lý

Rồi gọi `b.b(session, ll[], lm[], successByte)`.

Điều này xác nhận:

- kết quả nâng cấp không trả chỉ một item upgraded
- server trả snapshot bag/equip liên quan sau xử lý
- tag `189` là result byte:
  - `1` = `Nâng cấp thành công`
  - khác `1` = fail

### `99/100/101` = flow kết hợp / chế tác song song với nâng cấp

Family này map sang `ho.java`.

#### `99` mở flow

`case 99` đọc:

- `186` = session/context string
- `1` = message

Rồi gọi `b.e(session, message)` -> mở màn hình `ho`.

#### `100` mutate recipe

Shape gần như y hệt `97`:

- `187` = source side byte
- `83` hoặc `114/106`
- `132` = phí
- `1` = message
- `188` = ready-status

Callback map:

- `a(string, message, ready, fee)` / `b(string, message, ready, fee)` cho equipment
- `a(message, ready, fee)` / `a(itemId, qty, message, ready, fee)` cho item

Điểm chốt từ `ho.java`:

- `ready-status 0` = chưa đủ nguyên liệu
- `ready-status 1` = đủ điều kiện để hiện confirm `Kết hợp`
- `message` và `fee` được render thẳng trên màn hình

#### `101` trả kết quả cuối

`ky.A(ku)` parse:

- `186` = session/context string
- `189` = success byte
- `83[]` + `114[]` = snapshot sau khi kết hợp

Callback:

- `b.a(session, ll[], lm[], successByte)` -> `ho.a(...)`

UI gắn nghĩa trực tiếp:

- `1` = `Kết hợp thành công`
- khác `1` = `Kết hợp thất bại`

### Delta item/equip quan trọng cho char runtime

#### `7` appearance toggle ack

Inbound `case 7`:

- `147=0`, `165` -> `c(boolean)` -> update `go.k.Z`
- `147=1`, `166` -> `d(boolean)` -> update `go.k.aa`

Điểm quan trọng:

- `d(boolean)` không chỉ đổi `aa`
- nếu `aa=true` thì nó còn đẩy `go.k.e = 2`
- nếu tắt `aa` và `e==2` thì trả `e` về `0`

Nghĩa là toggle appearance có side effect lên status byte room/social.

#### `48` item tác động lên equipment

Inbound `case 48` đọc:

- `83` = equip key
- `114` = item id
- `106` = quantity còn lại

`com.mg.sq.a.b(equipKey, itemId, qty)` làm:

- set `ll.p = ll.q` trên equipment tương ứng
- update quantity item trong `go.m`
- refresh `hh` screen

Đây là evidence mạnh rằng packet `48` được dùng cho flow sửa chữa/phục hồi/áp item vào equipment.

#### `51` dùng item có thể spawn nhiều output

Inbound `case 51` có thể đồng thời mang:

- `114` = item vừa bị consume
- `1` = text message
- một object `83` = equipment mới tạo ra / mới nhận được
- thêm `lm[]` = item output mới

Client xử lý theo thứ tự:

1. giảm item đã dùng
2. hiện message nếu có
3. nếu có equip mới thì add vào `go.l`
4. nếu có item output thì add vào `go.m`

Nghĩa là một action dùng item trong Java có thể:

- consume item đầu vào
- sinh equipment
- sinh thêm items khác

#### `83` quantity-only delta

Inbound `case 83` chỉ mang:

- `114` = item id
- `106` = quantity

Client update trực tiếp quantity item.

#### `37` không phải lúc nào cũng là full loadout

Inbound `case 37` nhìn tag `89`:

- `89=0` -> parse full player/equipment update qua path `super.b(ku)`
- `89=1` -> trả list `83[]` key cho map runtime `om`
- `89=2` -> trả list `83[]` key cho inventory/runtime `hh`

Nghĩa là cùng command family `37` nhưng server dùng ít nhất 3 dạng phản hồi khác nhau.

### `112..116` = market/sale contract chạm thẳng inventory char

Phần này không phải combat core, nhưng là char-core vì nó mutate trực tiếp `ll/lm`.

#### `112` rao bán thành công

Server có thể trả:

- `83` + `157` -> equipment vừa được add lên market
- hoặc `114` + `106` -> item stack vừa được add lên market

Client sẽ:

- xóa item/equipment đó khỏi `hh`
- đóng dialog giá bán
- sync lại `go.l/go.m`

#### `114` list market products

Server trả:

- `152` = category id
- `106` = quantity/filter
- nested product list `lq[]`

`lq[]` parse qua `u(ku)`:

- `159` = product type
- `157` = price
- `145` = quantity/amount
- type `0` -> nested `ll`
- type `1` -> nested `lm`
- type `99` -> nested `lu` special record

#### `116` category list

Server trả `lf[]`:

- `152` = category byte
- `26` = category name
- `106` = count/qty

Đây là entrypoint mở market browser.

#### `113` và `115` trả snapshot `ll[]/lm[]`

Hai case này parse bằng `x(ku)` và `z(ku)`:

- cùng shape `175` + `83[]` + `114[]`
- một path đi vào screen `hq` là view `Đang bán`
- một path đi vào screen `hn` là browser/category list có action `Mua`

Tên business cuối cùng của từng market screen còn cần đặt lại đẹp khi implement, nhưng contract dữ liệu thì rõ:

- market response vẫn trả raw equipment/item payload thật
- client không dùng market-specific DTO mỏng

## Stat Calculator Và Derived Combat

`jp/jq/js/jr` là calculator theo raw class code:

- `1` = Hỏa
- `2` = Lôi
- `4` = Thủy

`com.mg.sq.a.a(lh)`:

1. lấy base stats + bonus stats
2. cộng modifier từ equipment `ll.r`
3. gọi calculator theo hệ
4. ghi lại:
   - `r` max HP
   - `A` dodge
   - `C` crit
   - `z` defense
   - `B` hit
   - `x/y` damage range

Điểm cần nhớ:

- Java raw element code là `1/2/4`, không phải `0/1/2`
- nếu C# muốn bám sát packet cũ thì phải có map rõ giữa storage enum và wire enum

`com.mg.sq.a.a(lh)` không chỉ gọi calculator từ base stats. Nó làm pipeline:

1. lấy base + bonus hiện có: `h+l`, `j+m`, `i+n`, `k+o`
2. tạo calculator theo `jp.a(lh.g)`
3. duyệt từng `ll` trong `D`
4. cộng stat modifier `lb.a/b/c/d` vào 4 stat nền
5. cộng flat attack `lb.e`
6. cộng `% attack` từ `lb.n` theo `jz2.c() * lb.n / 100`
7. cộng crit `lb.g`, defense `lb.f`, dodge `lb.h`, max HP `lb.i`
8. chạy calculator lại với stat sau equipment
9. ghi derived vào `lh.r/A/C/z/B/x/y`

Mapping derived cuối:

| `lh` field | Meaning | Nguồn |
|------------|---------|-------|
| `r` | max HP | `jz2.a() + lb.i` |
| `A` | dodge | `jz2.e() + lb.h` |
| `C` | crit | `jz2.g() + lb.g` |
| `z` | defense | `jz2.d() + lb.f` |
| `B` | hit/accuracy | `jz2.f()` |
| `x` | min damage | `jz2.b() + flat attack + percent attack` |
| `y` | max damage | `jz2.c() + flat attack + percent attack` |

C# `StatCalculator` hiện mới tương đương calculator nền `jq/js/jr`; chưa có lớp tổng hợp `lh + ll.r + bonus stats` như bridge Java.

## Phần C# Hiện Tại Đã Có

Đã có:

- account register/login/token
- tạo nhân vật cơ bản
- free stat points cơ bản
- công thức stat nền tối thiểu
- map bootstrap rất thô
- move echo rất thô
- monster roster/map encounter
- monster battle bootstrap

Tương ứng các file:

- `server/Twelve.Application/Handlers/AuthHandler.cs`
- `server/Twelve.Application/Handlers/TokenAuthHandler.cs`
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `server/Twelve.Application/Handlers/AllocateStatHandler.cs`
- `server/Twelve.Application/Handlers/MapHandler.cs`
- `server/Twelve.Application/Handlers/MoveHandler.cs`
- `server/Twelve.Application/Handlers/MonsterEncounterHandler.cs`

## Gap Analysis: C# So Với Java

### 1. `Player.cs` mới chỉ phản ánh một phần nhỏ của `lh`

Hiện có:

- level
- gold / exp
- map / room
- hp/mp/power
- 4 base stats
- free points
- gender / element / face / hair / skin

Cần kiểm tra lại naming `gold/exp` hiện tại với Java:

- Java `J/M/N` là trục EXP/progression: current, floor, ceiling.
- Java `H/I` là trục KEN/gold/collection: current, cap.
- Nếu `Player.Gold` hiện chỉ là tiền ví tuyệt đối thì không nên map thẳng vào `lh.H` nếu server mới còn có wallet riêng.

Thiếu hẳn:

- bonus stats `l..q`
- derived combat stats `x..C`
- free skill points `L`
- prestige/honor `ab`
- title strings `Q/R/S`
- appearance descriptors `U/V/W`
- equipment `ll[]`
- inventory `lm[]`
- learned skills `lv[]`
- map icon/timer overlays `lt[]`
- appearance toggles `Z/aa`
- special actor form `Y`

### 2. Session model hiện quá mỏng

`GameSession.cs` chỉ giữ:

- `Username`
- `IsAuthenticated`

Thiếu:

- current player id
- current map/room actor state
- reconnect state
- active battle session id
- selected target / map interaction context

### 3. DB schema chưa có aggregate cho player thật

Các migration hiện có mới thêm:

- traits
- gender
- 4 base stats
- power

Nhưng chưa có bảng/JSON cột cho:

- equipment loadout
- item inventory
- learned skills
- titles / prestige
- appearance descriptors `U/V/W`
- skill point `L`
- map icon/timer overlays `lt[]`
- map actor state x/y/direction/action

### 4. `CreateCharacterHandler` mới tạo trait-level data, chưa tạo Java-faithful char payload

Thiếu:

- raw element wire code `1/2/4`
- appearance descriptor generation `U/V/W`
- default equipment để compositor dựng đúng sprite
- skill list khởi tạo
- skill points
- titles/prestige init

### 5. `AllocateStatHandler` mới xử lý stat point, chưa cover skill point / derived truth đầy đủ

Thiếu:

- update derived stats như Java full path
- sync damage/defense/dodge/crit vào payload battle/map/profile
- delta packet model giống `ky.b`

### 6. `MapHandler` hiện chưa trả player truth đúng kiểu Java

Hiện làm:

- trả map info cơ bản
- trả roster monster

Thiếu:

- current player snapshot đầy đủ
- room player list
- map object / chest / interactable state
- join/leave/update player broadcasts
- player delta update packets
- map-specific counter load từ `lh.ac`

### 7. `MoveHandler` chưa có world actor state

Hiện chỉ:

- đọc tag `102/103`
- log ra console
- echo packet lại

Thiếu:

- validate tile / collision
- update actor direction/state
- broadcast sang người chơi khác
- sync exact world position
- persist hoặc cache world state

### 8. Battle bootstrap player đang là placeholder

`MonsterBattleBootstrapService` đang dựng player mặc định fake:

- display name `Player`
- fixed HP/MP/Power
- skill list không bám `lh.E`

Trong Java cũ:

- battle player phải đi từ player thật `lh`
- ngoại hình, stat, power, skills, equip đều đi theo char thật

### 9. Element mapping giữa Java và C# chưa canonical

Java:

- `1=Hỏa`
- `2=Lôi`
- `4=Thủy`

C# hiện tại:

- `0=Hỏa`
- `1=Lôi`
- `2=Thủy`

Điều này không sai nếu coi là storage enum nội bộ.
Nhưng phải có một lớp mapping tường minh khi encode/decode packet, nếu không rất dễ lệch về sau.

### 10. Có comment/schema cũ không còn chuẩn với reconstruction hiện tại

Cần thống nhất sớm để tránh lưu data sai nghĩa.

## Khối Chưa Làm Nhưng Bắt Buộc Nếu Muốn Bám Java Sâu

1. Full player aggregate model tương đương `lh + ll[] + lm[] + lv[] + lt[] + df*3`
2. Wire mapping giữa storage enum và raw Java tags
3. Appearance descriptor builder và body compositor data contract
4. Equipment persistence + stat modifier pipeline
5. Inventory persistence + stack/trade flags
6. Learned skill persistence + skill point `L`
7. Player delta packet system theo bitmask
8. Room player roster + broadcast movement/state
9. Map actor state machine cho player
10. Battle bootstrap từ player thật thay vì placeholder
11. Prestige/title/rank system `Q/R/S/ab`
12. Extra counter/timer model `lt[]`

## Data Model Đề Xuất Cho Server Mới

Không nên cố nhét hết vào `Players` table phẳng.

Nên tách:

1. `players`
   - identity, level, raw class, storage element, bars, prestige, titles
2. `player_appearance`
   - create-char inputs
   - raw descriptors `U/V/W`
   - toggles `Z/aa`
3. `player_stats`
   - base stats, bonus stats, derived stats
4. `player_equipment`
   - slot/type/key/resId/affixes
5. `player_inventory`
   - items stack/trade/cap/value
6. `player_skills`
   - learned skills + current level + mana cost snapshot nếu cần
7. `player_counters`
   - `lt[]` raw entries
8. `player_world_state`
   - map/room/x/y/direction/action/lastSeen

Nếu muốn đi nhanh hơn, có thể dùng một aggregate JSON trung gian, nhưng field names vẫn nên mirror `lh` rõ ràng.

### Schema tối thiểu để port đúng `lh`

Nếu giữ PostgreSQL và muốn đi nhanh nhưng vẫn rõ ownership, nên thêm các bảng/cột sau thay vì mở rộng `Players` vô hạn:

```sql
ALTER TABLE Players
    ADD COLUMN IF NOT EXISTS RawElementCode INT NOT NULL DEFAULT 1,
    ADD COLUMN IF NOT EXISTS SkillPoints INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS Honor INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS TitleMain TEXT,
    ADD COLUMN IF NOT EXISTS TitleSub TEXT,
    ADD COLUMN IF NOT EXISTS TitleRank TEXT,
    ADD COLUMN IF NOT EXISTS ExpFloor BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS ExpCeiling BIGINT NOT NULL DEFAULT 100,
    ADD COLUMN IF NOT EXISTS KenProgress BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS KenProgressCap BIGINT NOT NULL DEFAULT 10000,
    ADD COLUMN IF NOT EXISTS AppearanceHidden0 BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS AppearanceHidden1 BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS AppearanceJson JSONB NOT NULL DEFAULT '{}'::jsonb;

CREATE TABLE IF NOT EXISTS PlayerEquipment (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    EquipKey TEXT NOT NULL,
    Slot INT NOT NULL,
    ResourceId INT NOT NULL,
    Level INT NOT NULL DEFAULT 0,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, EquipKey)
);

CREATE TABLE IF NOT EXISTS PlayerInventory (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    ItemId INT NOT NULL,
    Quantity INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, ItemId)
);

CREATE TABLE IF NOT EXISTS PlayerSkills (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    SkillId INT NOT NULL,
    Level INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, SkillId)
);

CREATE TABLE IF NOT EXISTS PlayerMapOverlays (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    IconId INT NOT NULL,
    EndsAt TIMESTAMP WITH TIME ZONE NULL,
    DurationMs BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (PlayerId, IconId)
);
```

Lý do dùng `RawJson` ở giai đoạn đầu:

- `ll/lm/lv` có nhiều field phụ đã parse được nhưng chưa cần normalize hết ngay.
- Server vẫn có thể encode Java-faithful packet từ raw aggregate.
- Khi gameplay ổn định có thể tách tiếp affix, market, durability, requirement thành bảng riêng.

### DTO aggregate nên có trong C#

Đừng mở rộng class `Player` hiện tại thành 100 field ngay. Nên thêm model riêng:

```csharp
public sealed class PlayerAggregate
{
    public PlayerCore Core { get; init; }
    public PlayerAppearance Appearance { get; init; }
    public PlayerStats Stats { get; init; }
    public IReadOnlyList<PlayerEquipmentEntry> Equipment { get; init; }
    public IReadOnlyList<PlayerItemStack> Inventory { get; init; }
    public IReadOnlyList<PlayerSkillEntry> Skills { get; init; }
    public IReadOnlyList<PlayerMapOverlay> MapOverlays { get; init; }
}
```

Service boundary khuyến nghị:

- `PlayerAggregateRepository`: load/save full aggregate.
- `PlayerPacketFactory`: encode full snapshot `lh`.
- `PlayerDeltaPacketFactory`: encode bitmask tag `23`.
- `PlayerStatPipeline`: port `com.mg.sq.a.a(lh)`.
- `InventoryService`: mutate `lm[]`, stack/capacity/trade validation.
- `EquipmentService`: mutate `ll[]`, loadout, derived stat refresh.
- `RecipeService`: xử lý `id/ho` (`Nâng cấp`/`Kết hợp`).
- `TradeService`: xử lý command family `55`.

### Wire compatibility cần sửa trong C# hiện tại

C# hiện tại dùng protocol tiện triển khai nhưng chưa phải Java-faithful:

| Việc hiện tại | Java-faithful cần có |
|---------------|----------------------|
| create character `CMD 6`, tag `20-25` | create character `CMD 8`, tag `16`, `15`, repeated `90/96` |
| stat allocation `CMD 50`, một `StatChoice` | stat allocation `CMD 10`, batch tags `118/119/120/121` |
| response combat tags custom `130-135` | dùng tag Java gốc: HP `17/47`, stat `118-121`, derived theo snapshot/delta |
| storage element `0/1/2` | raw wire element `1/2/4` qua mapper tường minh |
| `Gold`/`Exp` phẳng | tách wallet khỏi `J/M/N` EXP gauge và `H/I` KEN-progress gauge |

Nếu muốn giữ client mới song song, có thể hỗ trợ hai protocol mode. Nhưng tầng domain vẫn nên canonical theo Java `lh`, rồi adapter encode ra Java hoặc client-new shape.

## Port Order Khuyến Nghị

1. Thêm `PlayerAggregate` và repository load/save aggregate.
2. Tách storage enum `0/1/2` khỏi raw Java wire enum `1/2/4`.
3. Port appearance descriptor model `U/V/W`, `Z/aa`, và create-char adapter `CMD 8`.
4. Port equipment/inventory/skill persistence.
5. Port `PlayerStatPipeline` gồm base + bonus + `ll.r` modifiers.
6. Dựng full snapshot packet tương đương `ky.a(ku)` đọc vào `lh`.
7. Dựng delta packet tag `23` theo bitmask.
8. Sửa stat allocation thành batch `CMD 10`, không phải single-stat custom `CMD 50`.
9. Nối map player actor state và `lt[]` overlay countdown.
10. Dùng player thật để bootstrap battle thay vì placeholder.
11. Thêm recipe/trade services cho `id/ho/of`.
12. Chỉ sau đó mới tối ưu renderer/client pixel-perfect.

## Trạng Thái Chốt Cuối

Với phạm vi player/character, reconstruction từ Java client đã đạt **100% thực dụng**:

- `lh` field map và wire tag map.
- nested structs `ll/lb/lm/lv/df/dg/lt`.
- full snapshot và delta bitmask.
- create/stat/skill command flow.
- equipment/inventory/use/sell/toggle/capacity.
- upgrade/combine/trade contract.
- map actor, battle actor, result screen dependency.
- C# gap và blueprint schema/service.

Các pending còn lại không phải thiếu phân tích Java nữa, mà là phần server mới phải tự định nghĩa rule vì Java client không mang đủ bằng chứng để đặt tên nghiệp vụ tuyệt đối:

- catalog tên nghiệp vụ cho từng `lt.a` icon id.
- giá trị curve chính xác cho title/rank/prestige nếu không nằm trong client.
- một số social/system command nếu remake toàn bộ Ola/social layer.

Nguyên tắc khi implement: không chờ dữ liệu ngoài repo. Lưu raw id/tag đúng theo Java, dựng rule từ parser/encoder/UI hiện có, rồi đặt tên business theo mức chắc chắn của bằng chứng. Mọi logic mới phải ghi rõ nguồn suy luận trong MD hoặc comment cạnh service.

## Kết Luận

Nếu mục tiêu là khôi phục sát Java cũ thì:

- đừng xem `monster xong rồi giờ thêm vài field player` là đủ
- `player/char` là một aggregate lớn, và `lh` mới là spec thật
- phần khó nhất không phải mỗi stat, mà là đồng bộ giữa:
  - snapshot truth
  - delta updates
  - appearance compositor
  - map actor
  - battle actor
  - equipment/inventory/skill persistence

File này nên tiếp tục là note trung tâm duy nhất để bám toàn bộ lõi `char/player` trước khi bắt tay vào code.
