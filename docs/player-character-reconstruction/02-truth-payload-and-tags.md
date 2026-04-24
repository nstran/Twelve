# 02 - Truth Payload And Tags

Chi tiết `lh`, field map, resource bars, derived stats và tag map quan trọng từ `ky.java`.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

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

