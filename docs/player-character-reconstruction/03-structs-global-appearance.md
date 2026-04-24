# 03 - Structs Global State And Appearance

Các struct con `lv/ll/lb/lm/df/lt`, global runtime state, bridge update và appearance/compositor pipeline.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

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

