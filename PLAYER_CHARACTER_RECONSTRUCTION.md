# Player / Character Reconstruction

Tài liệu này gom toàn bộ dấu vết Java hiện có cho `player`, `char`, `nhân vật`
thành một mô hình reconstruction dùng để port server/client mới sát runtime cũ.

Mục tiêu:

- xem Java cũ như spec
- tách rõ `account/session`, `char truth payload`, `map actor`, `battle actor`
- note luôn phần C# hiện tại đã làm gì và còn thiếu gì

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
| `lt` | extra timer/counter entry | tag `158/157`, chưa chốt semantic |
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
- extra timer/counter arrays

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
| `H` | current/related combat stat | xuất hiện cùng HP/MP block, chưa chốt tên cuối |
| `I` | combat threshold / hidden stat | tag `99`, chưa chốt semantic cuối |
| `J` | current power-like value | dùng cho gauge với `M/N` |
| `K` | free stat points | tag `53` |
| `L` | free skill points | skill tree dùng trực tiếp |
| `M` | current gauge floor | dùng trong `jt` |
| `N` | max gauge ceiling | dùng trong `jt` |
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
| `ac` | `lt[]` extra counters | packet tag `158` |
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
| `ac` | `lt[]` | extra counters/timers |

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
| `42` | `J` | gauge current |
| `43` | `H` | related combat stat |
| `99` | `I` | related combat threshold |

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
| `158` | danh sách extra counters `lt[]` |

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

- `c` = equipment key
- `d` = display name
- `e` = equip slot/type
- `j` = item level
- `m` = rank
- `n` = resource id dùng cho appearance
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
- thêm một số bonus tag 200..204/221 chưa nên rename vội

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

Đây là kết luận đủ mạnh để build model mới, nhưng chưa nên rename raw asset family cứng vào DB quá sớm.

### `lt` = extra counters

Hiện mới chốt được:

- `a` lấy từ tag `4`
- `b` lấy từ tag `157`

Client chỉ giữ mảng `lh.ac`, map scene `om` load nó khi vào map.
Semantic cuối còn mở, khả năng là cooldown, quest timer, effect timer, hoặc room-specific counters.

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
| `4` | HP/maxHP + gauge values + combat thresholds |
| `8` | free stat points + free skill points + prestige + title strings |
| `0x10` | booleans `Z/aa` |
| `0x20` | skill levels list |
| `0x40` | equipment list |
| `0x100` | extra counters `lt[]` |

Sau khi xử lý, client gọi `U()` để refresh UI/map/profile liên quan.

Đây là một chốt quan trọng cho server mới:

- không nên chỉ trả full snapshot mọi lúc
- Java cũ có delta model rõ ràng cho player

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

## Outbound Character Protocol Từ `ks.java`

`ks` là outbound transport manager.

`kw` là envelope command phía client:

- `kw.a` = command id
- các field còn lại là payload tạm theo từng command family
- `ks.a(kw)` convert `kw` sang `kx` TLV packet bằng tag Java cũ

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

Phần tên business cuối cùng của `ho.java` còn cần bới thêm packet inbound, nhưng contract dữ liệu thì đã rõ shape.

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

Tên business cuối cùng của hai screen chưa chốt hết, nhưng contract dữ liệu thì rõ:

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
- extra counters `lt[]`
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
- extra counters `lt[]`
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

## Port Order Khuyến Nghị

1. chốt canonical `PlayerAggregate` theo `lh`
2. tách storage enum và raw Java wire enum
3. port equipment/inventory/skill persistence
4. port appearance descriptor model `U/V/W`
5. dựng `PlayerInfo` full snapshot packet
6. dựng delta packet cập nhật player
7. nối map player actor state
8. dùng player thật để bootstrap battle

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
