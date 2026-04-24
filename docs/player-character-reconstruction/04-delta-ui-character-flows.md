# 04 - Delta UI And Character Flows

Delta update packet, profile/status UI, equipment/inventory runtime, skill tree, create character flow.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

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
- `PlayerTrainMapSnapshot` cho actor local trên map train PvE
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

### Trạng thái triển khai runtime status/inventory hiện tại

Đã có bản triển khai thực dụng cho remake:

- status screen dùng runtime snapshot HTTP riêng để lấy inventory/equipment/skill tree hiện tại
- phân điểm tiềm năng cập nhật thẳng player DB rồi recalc derived stats với equip đang mặc
- skill allocation dùng pool `SkillPoints` riêng, giới hạn theo element + level requirement
- equipment có trạng thái `IsEquipped` để tách loadout đang mặc với đồ đang nằm trong túi
- inventory/equipment UI đã bám flow Java `hh` hơn: click ô đồ mở menu dọc kiểu softbar; detail panel chỉ hiển thị thông tin; mặc/tháo là local preview trước, `Cập nhật` mới commit full loadout
- server đã có `/player/runtime/equipment/preview` để clone loadout nháp, chạy `PlayerStatPipeline`, trả stat/sprite preview; `/player/runtime/equipment/loadout` commit danh sách equip key đang mặc
- dùng item hiện chỉ hỗ trợ consumable hồi HP ngoài battle; quantity trừ thật trong `PlayerInventory`
- battle panel tuyệt chiêu chỉ hiện các family code mà player đã học trong aggregate
- các menu `Sửa chữa`, `Nâng cấp`, `Rao bán`, `Vứt bỏ` mới là UI shell để giữ đúng surface Java; nghiệp vụ server tương ứng còn pending

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

