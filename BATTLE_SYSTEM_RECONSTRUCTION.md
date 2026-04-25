# Battle System Reconstruction

Tài liệu khôi phục battle system từ Java client cũ, nhưng viết lại theo hướng dùng được cho bản hiện tại không có server.

Mục tiêu của file này:

- bám behavior Java cũ ở mức runtime thực tế
- tách rõ phần nào Java từng nhận từ server
- định nghĩa phần nào bản hiện tại phải tự tính local
- tránh suy diễn sai từ asset, icon hoặc tên file

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java) | `mp` | battle asset manager: chess sheets, crystal, star, casting, barrier, fire rage, hit FX |
| [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java) | `ms` | battle model: actor arrays, board bytes, refill queue, board buffer swap |
| [mh.java](/d:/Twelve/reference/redecoded/decompiled/mh.java) | `mh` | renderer cho board 8x8 |
| [nj.java](/d:/Twelve/reference/redecoded/decompiled/nj.java) | `nj` | node chess definition: id, mask, type, image index |
| [nd.java](/d:/Twelve/reference/redecoded/decompiled/nd.java) | `nd` | runtime cell animation |
| [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java) | `mq` | battle controller/state machine |
| [mo.java](/d:/Twelve/reference/redecoded/decompiled/mo.java) | `mo` | playing controller, scan nước đi còn hợp lệ |
| [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java) | `mt` | battle scene renderer, projectile/skill dispatch, combo popup, turn text |
| [mx.java](/d:/Twelve/reference/redecoded/decompiled/mx.java) | `mx` | actor + HUD renderer, animate HP/MP/Power |
| [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java) | `lg` | actor battle runtime wrapper, stat/status timers |
| [nq.java](/d:/Twelve/reference/redecoded/decompiled/nq.java) | `nq` | turn model |
| [nl.java](/d:/Twelve/reference/redecoded/decompiled/nl.java) | `nl` | per-actor attribute delta/result |
| [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | packet parser cho battle state/turn/result |
| [pc.java](/d:/Twelve/reference/redecoded/decompiled/pc.java) | `pc` | UI helper: focus, hidden pieces, element icon sheet |
| [hs.java](/d:/Twelve/reference/redecoded/decompiled/hs.java) | `hs` | post-battle result/exp/gold screen |
| [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java) | `oa` | popup nhận item reward |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | chest/equipment reward presentation |

## Core Position

Battle Java cũ không phải pure renderer.

Thực tế nó là hybrid:

- board state, refill bytes, actor result, turn delta từng do packet đưa vào
- nhưng client vẫn tự chạy local swap, scan match, cascade, drop, no-move check, special clear, skill visual dispatch

Vì bản hiện tại không có server, phần từng là authoritative packet phải được thay bằng local authority trong battle controller.

## Java Boundary Chot

Neu muon "day xuong BE" ma van bam dung Java, thi boundary hop ly nen la:

- `Server authority`
  - xac thuc cast co hop le hay khong
  - skill level that, mana/cooldown/unlock, khong lay tu client
  - skill packet runtime cho `mq/mt`: `byArray/byArray2/objectArray/byArray3`
  - actor result sau cast/turn: `hp/mp/power`, status timers, them luot, them thoi gian
  - reward/end-state
- `Client authority`
  - input/cursor/menu/tree UI
  - animation/runtime playback cua board + actor theo packet/result da co
  - swap/fall/explode/hint/no-move presentation
  - HUD tween/popup tu delta authoritative
- `Transitional hien tai trong repo`
  - board core van dang local de thay cho packet server Java da mat
  - skill cast geometry da di qua BE endpoint `/battle/skill-cast`
  - nhung turn result tong quat van chua co endpoint authoritative rieng

He qua thuc dung:

- khong de client tu suy target list skill
- khong de client tu quyet `+ luot`, `+ time`, damage/heal cua skill khi packet server da biet
- neu chua co player skill data that tren server, chi duoc gui `debugSkillLevel` tam thoi; khong duoc coi do la authority

## Board Model

### 1. Kích thước thật

- board render active là `8x8`
- runtime storage là `12x12`
- vùng chơi thật là từ `row 2..9` và `col 2..9`
- viền ngoài là sentinel/block

Nguồn:

- [mh.java](/d:/Twelve/reference/redecoded/decompiled/mh.java:24)
- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:198)

### 2. Không được hiểu `chess0..8` là "9 loại gem logic"

Java chỉ load 9 image sheet:

```java
for (int n3 = 0; n3 < this.c.length; n3++) {
    this.c[n3] = f.d("/chess" + n3);
}
```

Nhưng logic board dùng `nj` node id, không dùng trực tiếp chỉ số ảnh.

`nj` chứa:

- `d` = node id
- `e` = mask dùng để match
- `f` = node type
- `g` = image index đưa vào cell renderer

Nguồn:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:387)
- [nj.java](/d:/Twelve/reference/redecoded/decompiled/nj.java:24)
- [mh.java](/d:/Twelve/reference/redecoded/decompiled/mh.java:75)

### 3. Node families đã xác nhận

| Node id | Type | Ý nghĩa thực dụng |
|---------|------|-------------------|
| `0..5` | `1` | base match pieces |
| `10..15` | `2` | special pieces clear vùng lân cận |
| `20..25` | `4` | special pieces clear hàng + cột |
| `70` | `1` | special node riêng, mask `64`, image index `6` |
| `90` | `1` | empty |
| `99` | `1` | blocked/sentinel |

Lưu ý:

- `mask` mới là thứ quyết định match/collision, không phải tên file ảnh
- `image index` không đồng nghĩa với node id
- nếu bản port đang map thẳng `node id -> asset id` thì rất dễ sai

Nguồn:

- [nj.java](/d:/Twelve/reference/redecoded/decompiled/nj.java:35)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:772)

## Local Board Simulation

### 1. Swap -> Match -> Clear -> Drop -> Cascade là local flow

Battle controller Java chạy vòng đời board theo state machine:

1. vào turn mới
2. thực hiện swap
3. scan match ngang/dọc
4. clear node thường/special
5. drop toàn bộ cột
6. refill ô trống
7. scan cascade tiếp
8. nếu hết thì chờ turn result tiếp theo

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:281)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:564)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:822)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:890)

### 2. Match detection thật dùng mask

Java không so id tuyệt đối; nó so `mask`.

- ngang: `mq.a(nj[][], row, col)`
- dọc: `mq.b(nj[][], row, col)`

Nếu `count >= 3` thì tạo `mw` clear node.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:909)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:927)

### 2.1. Packed line format trong Java

`mq.a()` và `mq.b()` trả `int` packed chứ không trả object:

- byte cao `>> 16 & 0xFF` = số ô kéo về phía trái hoặc phía trên
- byte giữa `>> 8 & 0xFF` = số ô kéo về phía phải hoặc phía dưới
- byte thấp `& 0xFF` = tổng độ dài line

Khi `mq` biến packed value thành `mw`:

- `b,c,d` = line ngang: `row`, `startCol`, `length`
- `e,f,g` = line dọc: `startRow`, `col`, `length`
- `h,i` = ô trung tâm Java chọn để spawn special nếu cần

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:106)
- [mw.java](/d:/Twelve/reference/redecoded/decompiled/mw.java:4)

### 2.2. Pseudo-code scan line đúng kiểu Java

Scan ngang:

```text
scanHorizontal(board, row, col):
  total = 1
  left = 1
  mask = board[row][col].mask

  while (mask & board[row][col - left].mask) != 0:
    left++
    total++

  packed = ((left - 1) & 0xFF) << 16

  right = 1
  while (mask & board[row][col + right].mask) != 0:
    right++
    total++

  packed |= ((right - 1) & 0xFF) << 8
  packed |= total & 0xFF
  return packed
```

Scan dọc tương tự, chỉ đổi `row +/- offset`.

Chi tiết quan trọng:

- Java dùng `mask AND`, không dùng `nodeId ==`
- empty `90` có `mask = 0` nên line dừng ngay
- sentinel `99` không match với base piece nên cũng chặn line
- packed result luôn chứa cả `left/up`, `right/down`, `total`

Nếu muốn giống Java, đừng rút gọn thành `count contiguous same color` kiểu thông thường; phải giữ packed format này vì các bước sau dùng lại nó.

### 3. Special clear

Khi clear queue chạy:

- `type 2` clear 8 ô xung quanh
- `type 4` clear toàn hàng và toàn cột

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:772)

### 3.0. Resolve queue đúng thứ tự Java

Pseudo-flow của `mq.a(nj[][])`:

```text
resolveMatches(board, clearQueue):
  X = []

  if mixingEnabled:
    split clearQueue thành:
      horizontalOrCross
      verticalOnly
      singleCell

    sort/dedupe horizontalOrCross theo độ dài ngang
    sort/dedupe verticalOnly theo độ dài dọc

    nếu một bên rỗng:
      đẩy toàn bộ line vào X
    ngược lại:
      merge line ngang/dọc nếu:
        - cùng mask
        - thật sự giao nhau
      line merge sẽ giữ:
        - cả span ngang
        - cả span dọc
        - intersection làm spawn point

  clear base lines trong clearQueue cũ
  clear chain reaction của special cũ nằm trong clearQueue
  spawn special mới từ X
  cập nhật combo counter theo baseId
```

Điểm cần giữ:

- Java clear line cũ trước, rồi mới spawn special mới
- special mới spawn ra không được tự clear ngay trong cùng pass này
- `X` là tập line sau khi merge/dedupe, không phải raw line ban đầu

### 3.1. Tạo special piece đúng rule Java

Flow thật:

1. gom toàn bộ line ngang và dọc vào `mw[]`
2. sort + dedupe từng nhóm line
3. merge line ngang/dọc giao nhau cùng `mask`
4. clear board
5. sau clear mới spawn special mới vào board model

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:609)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1632)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1672)

Rule nâng cấp:

- nếu `horizontal >= 5`
- hoặc `vertical >= 5`
- hoặc `horizontal >= 3 && vertical >= 3`
  - spawn `mr.y[baseId]` = `20..25`
  - đây là special `type 4`

- nếu `horizontal >= 4`
- hoặc `vertical >= 4`
  - spawn `mr.x[baseId]` = `10..15`
  - đây là special `type 2`

Giới hạn:

- chỉ base node có `mask < 64` mới được nâng cấp
- special đang có sẵn không nâng cấp tiếp trong logic này

Nguồn:

- [mr.java](/d:/Twelve/reference/redecoded/decompiled/mr.java:4)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:691)

### 3.2. Vị trí spawn special

Đây là chỗ rất dễ port sai.

Java không spawn “ở ô vừa swap tới” theo kiểu tùy ý.

Quy tắc thật:

- line ngang > 3, không có giao dọc:
  - spawn tại `startCol + ((len - 1) >> 1)`
- line dọc > 3, không có giao ngang:
  - spawn tại `startRow + ((len - 1) >> 1)`
- T/L/cross:
  - spawn đúng tại ô giao giữa line ngang và line dọc

Hệ quả:

- line 4 ngang spawn ở ô thứ `2` tính từ đầu line
- line 4 dọc cũng vậy
- T/L spawn ở giao điểm, không ở đầu line

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1691)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1727)

### 4. Refill trong Java cũ từng lấy từ byte queue

`ms` dùng `o/m/r/s/u` làm buffer board/refill bytes do packet cấp.

Nguồn:

- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:87)
- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:151)
- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:198)

Cho bản hiện tại không có server:

- battle controller phải tự sinh refill node
- phải tránh sinh board vô nghiệm liên tục
- phải tránh loop cascade vô hạn
- nên dùng RNG deterministic theo `battle_seed + turn_index + cascade_index`

### 4.1. Drop đúng kiểu Java

Drop chạy theo từng cột:

- con trỏ đích mỗi cột bắt đầu ở row `9`
- quét từ dưới lên `9 -> 2`
- node có `mask != 0` được kéo xuống vị trí thấp nhất còn trống
- node rỗng là `90`

Sau khi kéo hết node cũ xuống:

- mọi ô còn lại phía trên được refill
- Java lấy node mới từ `ms.m[ms.n++]`

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:822)
- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:151)

### 4.2. Cascade scan sau drop

Java không scan lại toàn board mù quáng sau mỗi drop.

Nó scan lại chỉ trên danh sách ô vừa thay đổi trong `a.p[]`:

- ô vừa rơi xuống
- ô vừa refill

Rồi check line ngang/dọc từ các ô đó.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:890)

### 4.3. Pseudo-code drop/refill gần nguyên bản Java

```text
dropAllColumns(board):
  changed = []
  destRow[col] = 9 for col 2..9
  delay[col] = 0
  wave = 0

  for col in 2..9:
    movedInThisColumn = false

    for row from 9 downto 2:
      if board[row][col].mask != 0:
        if row != destRow[col]:
          board[destRow[col]][col] = board[row][col]
          board[row][col] = EMPTY
          changed.add(destRow[col], col)
          animateDrop(row, col -> destRow[col], col, delay[col] + wave)
          delay[col]++
          movedInThisColumn = true
        destRow[col]--

    if movedInThisColumn:
      wave++

  wave = 0
  for col in 2..9:
    spawnOffset = 0
    if destRow[col] >= 2:
      for row from destRow[col] downto 2:
        board[row][col] = nextRefillNode()
        changed.add(row, col)
        animateSpawn(spawnOffset, col -> row, col, delay[col] + wave)
        delay[col]++
        spawnOffset--
      wave++

  return changed
```

Điểm dễ sai:

- scan từ dưới lên
- `mask != 0` mới được coi là vật thể rơi
- ô mới spawn dùng vị trí nguồn âm dần `0, -1, -2...`, không spawn cùng một hàng
- cascade tiếp theo chỉ scan `changed`, không scan full board ngay

## Turn, Time, Extra Turn

### 1. `nq` có 2 nhánh độc lập

- `nq.D` = delta thời gian turn
- `nq.F` = delta số lượt còn lại

Nguồn:

- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1832)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1833)

### 2. Timer

Java đổi `nq.D` sang milliseconds bằng:

```java
this.a.g = n2;
np.a = n2 * 1000;
```

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1584)
- [np.java](/d:/Twelve/reference/redecoded/decompiled/np.java:8)

### 3. Lượt còn lại

`nq.F` được cộng dồn vào biến lượt, rồi `mt` hiện text:

```java
"Còn " + n3 + " lượt"
```

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:232)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:503)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:1548)

### 4. Hết nước đi là trạng thái khác

Java có text riêng:

```java
"Hết nước đi!"
```

Nguồn:

- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:1559)
- [mo.java](/d:/Twelve/reference/redecoded/decompiled/mo.java:170)

Kết luận:

- `thêm lượt`
- `thời gian`
- `nước đi còn hợp lệ`

là 3 khái niệm phải tách riêng, không được gộp.

### 5. Validate swap đúng kiểu Java

Java check swap hợp lệ bằng cách:

1. swap thử 2 ô trong board model
2. tính line ngang/dọc cho cả 2 ô
3. nếu ít nhất 1 line có `length >= 3` thì hợp lệ
4. swap board lại như cũ
5. trả `my` chứa:
   - 2 ô swap
   - 4 packed line result của 2 ô

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:981)
- [my.java](/d:/Twelve/reference/redecoded/decompiled/my.java:4)

Đây là rule phải giữ nguyên nếu muốn bàn cờ local giống Java.

Pseudo-code:

```text
validateSwap(r1, c1, r2, c2):
  if board[r1][c1] == board[r2][c2]:
    return null

  swap(board[r1][c1], board[r2][c2])

  h1 = scanHorizontal(board, r1, c1)
  v1 = scanVertical(board, r1, c1)
  h2 = scanHorizontal(board, r2, c2)
  v2 = scanVertical(board, r2, c2)

  valid =
    len(h1) >= 3 or len(v1) >= 3 or
    len(h2) >= 3 or len(v2) >= 3

  swapBack()

  if !valid:
    return null

  return SwapResult {
    a = r1, b = c1,
    c = r2, d = c2,
    e = [h1, v1, h2, v2]
  }
```

Lưu ý:

- Java reject sớm nếu 2 ô đang trỏ cùng `nj` singleton
- không có rule riêng cho swap special ở đây; hợp lệ hay không vẫn dựa trên packed line sau swap

## HP / MP / Nộ / Combo

### 1. Turn result không chỉ có damage

Java parse mỗi actor thành `nl`:

- `e` = damage/result delta
- `b` = HP hiện tại
- `c` = Mana hiện tại
- `d` = Power hiện tại
- `f` = flag byte đi kèm actor result

Nguồn:

- [nl.java](/d:/Twelve/reference/redecoded/decompiled/nl.java:12)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1848)

### 2. `mq` apply result vào actor runtime

`mq.a(owner, nl[])`:

- update actor wrapper `lg`
- sync `hp/mp/power`
- phát bar animation qua `mx`
- phát damage popup nếu HP giảm

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:125)
- [mx.java](/d:/Twelve/reference/redecoded/decompiled/mx.java:2060)

### 2.1. Combo trong Java là combo theo màu trong cùng turn

`mq.A[baseId]` là counter nội bộ theo từng base piece.

Trong cùng một turn:

- nếu cùng màu tiếp tục match ở cascade sau
- counter của đúng màu đó tăng
- từ lần thứ `2` trở lên Java mới hiện popup `xN`

Nó không phải một biến combo global duy nhất cho toàn board.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:667)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:705)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:844)

### 3. `lg` là source runtime cho actor

`lg` giữ:

- `s/r` = current/max HP
- `u/t` = current/max Mana
- `w/v` = current/max Power

Nguồn:

- [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java:89)
- [lh.java](/d:/Twelve/reference/redecoded/decompiled/lh.java:15)

### 4. Combo popup

Combo `"xN"` là visual riêng, dùng star sprite của battle scene, không phải stat resource.

Nguồn:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:394)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:844)
- [ne.java](/d:/Twelve/reference/redecoded/decompiled/ne.java:23)

Kết luận:

- `HP`, `MP`, `Nộ` là stat runtime thật
- `Sao` trong battle là combo FX/popup
- không được lưu `star` như resource battle nếu Java không dùng kiểu đó

## Status Timers

`lg` giữ 5 countdown timer local:

- `d`
- `e`
- `f`
- `g`
- `h`

Mỗi turn đều giảm bằng `lg.s()`.

Nguồn:

- [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java:5)
- [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java:109)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:512)

Ý nghĩa semantic chính xác của từng timer chưa đủ chắc để đặt tên player-facing, nhưng behavior runtime đã rõ:

- hết timer thì controller gỡ overlay/helper tương ứng
- có nhánh riêng để add/remove helper effect qua `mt`

Cho bản hiện tại:

- nên giữ tên trung tính như `statusTimer1..5`
- chỉ đặt tên gameplay khi thật sự map được từng effect

## No-Move Detection

### 1. Java scan brute-force mọi swap kề nhau

Khi tới turn player và board idle, `mo.d()` duyệt toàn bộ board:

- thử swap sang phải
- thử swap xuống dưới

Nếu có move hợp lệ:

- gom vào list
- chọn ngẫu nhiên 1 move để làm hint

Nếu không có move hợp lệ:

- đi vào no-move flow

Nguồn:

- [mo.java](/d:/Twelve/reference/redecoded/decompiled/mo.java:170)

### 2. No-move path trong Java cũ chưa hoàn toàn local

Java client cũ không tự tính đầy đủ board mới sau no-move.

Dấu hiệu:

- `ms.f()` chỉ thay board khi `o != null`
- `o` là byte buffer từng đến từ packet
- state `20` chỉ hiện `"Hết nước đi!"`
- state `10` chủ yếu chạy animation reset board

Nguồn:

- [ms.java](/d:/Twelve/reference/redecoded/decompiled/ms.java:198)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:194)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:459)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:567)

Cho bản hiện tại không có server:

- có thể làm giống Java gần như hoàn toàn ở phần detect no-move
- nhưng phần board mới sau no-move phải tự định nghĩa local

Rule local nên dùng:

- giữ nguyên brute-force scan adjacent swap
- khi không có move:
  - hiện `"Hết nước đi!"`
  - chạy animation reset board
  - reshuffle toàn bộ node có `mask != 0`
  - reject mọi board mới nếu:
    - có match sẵn ngay lúc spawn
    - không còn move hợp lệ

## Skill Trigger và Board Mutation

Skill family mutation đúng phải đi qua `mq` rồi `mt`:

- `mq` xử lý clear/mark/helper trên board
- `mt` xử lý projectile, impact, actor helper, combo text

Nếu chỉ render skill mà không mutate board đúng Java, battle feel sẽ sai.

Nguồn chi tiết:

- [SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/SKILL_SYSTEM_RECONSTRUCTION.md)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1311)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:989)

## Reward Flow

Battle result packet cuối có thể mang:

- `ll[]` equipment reward
- `lm[]` item reward

Controller lưu chúng vào:

- `go.u` = equipment
- `go.v` = items

Nguồn:

- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1925)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1570)

Presentation:

- item popup `"Bạn nhận được"` dùng `go.v`
- equipment reward bay ra chest effect dùng `go.u`

Nguồn:

- [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java:763)
- [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:325)

Lưu ý cho bản hiện tại:

- reward chỉ phát đúng 1 lần ở end state
- tuyệt đối không map board piece sang reward icon
- nếu đang thấy quá nhiều icon kiếm, hãy kiểm tra chỗ đọc `lm.j` hoặc UI reward list trước, không đổ lỗi cho core board

## Offline Authority Rules

Java cũ từng dựa vào packet cho nhiều kết quả battle. Bản hiện tại không có server nên phải chốt authority local.

Battle controller local phải tự quyết định toàn bộ:

1. swap có hợp lệ hay không
2. match ngang/dọc và special spawn/clear
3. cascade và refill
4. no-move detection và xử lý lại board
5. skill trigger từ pattern / power / state
6. delta `hp/mp/power`
7. delta thời gian turn
8. delta số lượt thêm
9. win/lose/draw
10. reward roll

Quy tắc thực thi:

- renderer không được tự suy logic từ asset
- `star`, `hidden dragon`, `hidden phoenix`, `elementsicon` chỉ là render support
- board model phải dùng node definition, không dùng filename image làm truth
- reward phải tách riêng khỏi board node

## Practical Contract Cho Bản Hiện Tại

Nếu triển khai battle local bây giờ, nên giữ contract sau:

- `BattleBoardState`
  - `cells[8][8]` là `nodeId`, không phải `assetIndex`
- `BattleActorState`
  - `hp`, `maxHp`, `mp`, `maxMp`, `power`, `maxPower`
  - `statusTimer1..5`
- `BattleTurnState`
  - `timeLeftSeconds`
  - `remainingTurns`
  - `hasValidMove`
- `BattleResolution`
  - `attributeResults[]`
  - `boardMutations[]`
  - `comboCount`
  - `rewards`

## Immediate Corrections So Với Bản Cũ

- bỏ kết luận "`client chỉ render`"
- bỏ kết luận "`chess0..8` = 9 opaque gem logic"
- bỏ dòng "`8x8 hoặc 8x9`"; board active thật là `8x8`
- thêm riêng 3 nhánh: `time`, `extra turns`, `no-move`
- coi `star` là combo FX, không phải resource
- tách `board node`, `reward item`, `UI icon` thành 3 lớp khác nhau
- giữ đúng rule spawn special ở tâm line hoặc giao điểm
- giữ combo theo từng màu trong cùng turn, không gộp thành 1 biến global

## Client Port Status

Tính đến bản client hiện tại, phần board core đã được port local vào:

- [BattleScreen.logic.ts](/d:/Twelve/client/src/screens/battle/core/BattleScreen.logic.ts)
- [useBattleMatchFlow.ts](/d:/Twelve/client/src/screens/battle/hooks/useBattleMatchFlow.ts)
- [useBattleBoardAnimations.ts](/d:/Twelve/client/src/screens/battle/hooks/useBattleBoardAnimations.ts)

Đã có trong code:

- `resolveJavaBoardStep()`
  - scan line ngang/dọc bằng packed span
  - dedupe line theo axis
  - merge ngang/dọc theo giao điểm
  - spawn special `10..15` và `20..25`
  - clear chain cho special `type 2` và `type 4`
- `collapseResolvedBoard()`
  - drop bottom-up từng cột
  - refill từ queue RNG local
  - trả `affectedKeys` để cascade scan đúng kiểu Java
- `reshuffleBoard()`
  - local fallback khi hết nước đi
  - giữ multiset node hiện có
  - reject board mới nếu có match sẵn hoặc không còn move

Luồng hook hiện tại:

1. `validateSwap()`
2. swap local
3. `resolveJavaBoardStep()`
4. render explode trên `triggerKeys` / `clearedKeys`
5. `collapseResolvedBoard()`
6. cascade tiếp chỉ trên `affectedKeys`
7. nếu không còn move thì `reshuffleBoard()`

### Lưu ý rất quan trọng về `+ lượt`

Code client hiện tại chỉ có thể làm `bonusTurnCandidate` theo heuristic:

- line `>= 4`
- hoặc line merge có trục `>= 4`

Lý do:

- Java client chỉ parse `nq.F`
- không có công thức authoritative trong client giải thích chính xác khi nào server tăng `nq.F`

Cho nên:

- board algorithm hiện đã bám Java hơn nhiều
- nhưng `+ lượt` mới chỉ là local approximation, chưa thể khẳng định 100% đúng server cũ

Nếu muốn khóa nốt phần này, cần packet log hoặc replay thật để suy ngược rule `nq.F`.

## 100% Boundary

Có 2 mức “giống Java”:

### 1. Có thể làm gần như 100% từ client Java

- board topology
- validate swap
- match scan theo mask
- merge line ngang/dọc
- special clear
- special spawn rule
- special spawn position
- drop logic
- cascade scan
- no-move detection
- HUD apply `hp/mp/power`
- timer UI và `remaining turns`

### 2. Không thể khẳng định 100% chỉ từ client Java

- rule gameplay nào sinh `nq.D` thêm thời gian
- rule gameplay nào sinh `nq.F` thêm lượt
- board bytes server cấp sau no-move / reshuffle
- damage formula cuối cùng
- elemental counter thật
- reward roll table

Nếu muốn đúng tuyệt đối ở các phần này, cần:

- server source
- hoặc packet log thật
- hoặc replay/video đủ dày để suy ngược

## Next Practical Step

Bước hợp lý tiếp theo không phải dựng asset registry nữa, mà là khóa spec logic:

1. định nghĩa enum/const cho toàn bộ `nj node ids`
2. port nguyên match scan theo `mask`
3. port clear behavior cho `type 2` và `type 4`
4. tách `timeLeft`, `remainingTurns`, `hasValidMove`
5. chuẩn hóa local result model cho `hp/mp/power`
6. chỉ sau đó mới bind animation `mt/mx/mp`

Nếu làm ngược lại, bản battle sẽ nhìn giống Java nhưng logic sẽ lệch ở những chỗ quan trọng nhất.

## Nhật ký chỉnh sửa

### 2026-04-25 — Sửa đồng bộ canonical board PvP

- File code đã sửa:
  - `server/Twelve.Application/Battle/BattleSessionSyncService.cs`
- Nội dung:
  - Sửa `/battle/session-sync` cho `PvpShadow` session: khi board/bars/active turn thay đổi sau local Java-like cascade, server bump `TurnSeq` như một phiên bản canonical state.
  - Mirror `TurnSeq` sang linked PvP shadow session để client đối thủ không bỏ qua snapshot board mới vì tưởng là stale packet.
  - Logic này bám theo boundary trong tài liệu: Java cũ từng nhận board/refill/result từ packet server, còn bản hiện tại cho phép client resolve board local rồi sync canonical state lên server vì chưa có authoritative turn-result endpoint đầy đủ.

### 2026-04-25 — Sửa lỗi animation observer và visual special piece

- File code đã sửa:
  - `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
  - `client/src/screens/battle/hooks/useBattleBoardAnimations.ts`
  - `client/src/screens/battle/BattleScreen.tsx`
  - `client/src/screens/battle/ui/BattleScreen.components.tsx`
  - `client/src/screens/battle/core/BattleScreen.shared.ts`
- Nội dung:
  - Sửa lỗi giật/chậm (stuck loop) khi quan sát viên (passive observer) nhận update board PvP. Chuyển `doDirectSwap` sang dùng `animateValidSwap` thay vì set state đột ngột, đảm bảo đồng bộ timing animation.
  - Khớp luồng swap local với server sync: refactor `animateValidSwap` ra khỏi luồng tương tác UI trực tiếp để có thể trigger từ websocket packet.
  - Sửa lỗi visual special item (type 4) sau match 5+: loại bỏ overlay `hiddendragon` và `hiddenphoenix` sai bản chất khỏi board renderer. Phục dựng chuẩn Java: node `20..25` (type 4) chỉ dùng asset base `chess0..5` kèm frame animation từ `nd.java` thay vì dùng UI ornament tĩnh lấy từ `pc.java`.
