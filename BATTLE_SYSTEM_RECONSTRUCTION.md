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

## Java Client Coverage / Mức độ đầy đủ hiện tại

Tính riêng Java client đã decompile, không tính phần server Java cũ không có source:

- JAR client đã được bóc tách/decompile gần như đầy đủ:
  - `reference/redecoded/jar-contents.txt`: `2259` entries
  - `.class`: `427`
  - resource/non-class: `1832`
  - `reference/redecoded/decompiled`: `427` file `.java`
  - `reference/redecoded/cfr_fresh`: `427` file `.java`
  - so khớp theo unique class stem: `425 / 425`, không thấy class Java client còn thiếu
- Nhóm class liên quan trực tiếp battle/bàn cờ đã xác nhận có đủ trong cả hai bộ decompile:
  - `mp`, `ms`, `mh`, `nj`, `nd`, `mq`, `mo`, `mt`, `mx`
  - `lg`, `lh`, `nq`, `nl`, `ky`, `pc`
  - `hs`, `oa`, `om`, `mw`, `my`, `mr`, `ne`, `np`, `oz`

Đánh giá mức khôi phục nếu chỉ tính **bàn cờ Java client-side**:

| Hạng mục | Mức đầy đủ | Ghi chú |
|----------|-----------:|---------|
| Java client class decompile coverage | ~100% | Không thấy class client còn nằm riêng trong JAR chưa bóc |
| Board topology/model | 100% | `12x12`, active `8x8`, vùng `2..9` |
| Node definition `nj` | 100% | `id`, `mask`, `type`, `imageIndex` |
| Swap validation | 100% | Bám `mq`/`my` |
| Match scan bằng mask + packed line | 100% | Bám `mq.a()`/`mq.b()` và `mw` |
| Clear thường + special `type 2/type 4` | 100% theo Java client code | Bám `mq`; nhưng gameplay memory xác nhận bản gốc người chơi thấy "ăn xong biến mất", không dùng tạo item special tự nhiên |
| Spawn special `10..15` / `20..25` | Cần khóa lại theo gameplay | Decompile có nhánh `mq`/`mr`, nhưng user memory xác nhận không item nào tạo special; tạm coi là nhánh Java/server/skill/unused hoặc không bật trong runtime gốc |
| Drop/refill receiver/cascade phía client | 95-100% | Core rõ; refill bytes gốc là data packet |
| No-move detection phía client | 100% | Bám `mo.d()` |
| No-move presentation/reset receiver phía client | 95-100% | Client flow rõ; board mới là packet data |
| State machine `mq` phía client | 95-98% | Core battle board rõ; còn vài chi tiết timing/visual nhỏ do obfuscation |
| Skill board mutation/visual dispatch phía client | 90-95% | Client parse/dispatch rõ; payload cụ thể là data packet |
| Actor HUD/result apply phía client | 95-100% | Client nhận/apply `nl[]`, không tự sinh formula |
| Combo popup/visual board runtime | 95-100% | Rõ theo `mq`/`mt`/`nd`/`ne` |

Kết luận coverage:

- Nếu hỏi **logic bàn cờ client Java đã đủ để port chưa**: có, có thể coi là đủ.
- Mức tin cậy thực dụng cho **board core client-side**: khoảng `97-99%`.
- Không chấm `100%` tuyệt đối vì source bị obfuscate/decompile, một số tên semantic và timing visual nhỏ có thể cần đối chiếu bằng gameplay/video.
- Những field như refill queue, board reset bytes, `nq.D`, `nq.F`, `nl[]`, reward list không phải thiếu client; trong Java client chúng là dữ liệu nhận vào rồi apply/render. Công thức sinh dữ liệu đó thuộc server cũ và sẽ được tính/remake sau dựa trên behavior client.

## Core Position

Battle Java cũ không phải pure renderer.

Thực tế nó là hybrid:

- board state, refill bytes, actor result, turn delta từng do packet đưa vào
- nhưng client vẫn tự chạy local swap, scan match, cascade, drop, no-move check, special clear, skill visual dispatch

Vì bản hiện tại không có server, phần từng là authoritative packet phải được thay bằng local authority trong battle controller.

## Java Boundary Chốt

Nếu muốn "đẩy xuống BE" mà vẫn bám đúng Java, boundary hợp lý nên là:

- `Server authority`
  - xác thực cast có hợp lệ hay không
  - skill level thật, mana/cooldown/unlock, không lấy từ client
  - skill packet runtime cho `mq/mt`: `byArray/byArray2/byArray3/byArray4` tương ứng row/col/extra board data/refill-or-effect data tùy skill
  - actor result sau cast/turn: `hp/mp/power`, status timers, thêm lượt, thêm thời gian
  - board refill bytes và board reset/no-move bytes trong bản Java cũ
  - checksum/sync revision (`nq.b`, `nq.i`) để phát hiện lệch board client/server
  - reward/end-state
- `Client authority`
  - input/cursor/menu/tree UI
  - animation/runtime playback của board + actor theo packet/result đã có
  - swap/fall/explode/hint/no-move presentation
  - local scan match/cascade/drop để trình diễn và sync checksum
  - HUD tween/popup từ delta authoritative
- `Transitional hiện tại trong repo`
  - board core vẫn đang local để thay cho packet server Java đã mất
  - skill cast geometry đã đi qua BE endpoint `/battle/skill-cast`
  - nhưng turn result tổng quát vẫn chưa có endpoint authoritative riêng

Hệ quả thực dụng:

- không để client tự suy target list skill nếu skill đó trong Java đi từ packet `nq`
- không để client tự quyết `+ lượt`, `+ time`, damage/heal của skill khi packet server đã biết
- nếu chưa có player skill data thật trên server, chỉ được gửi `debugSkillLevel` tạm thời; không được coi đó là authority
- mọi rule BE mới không thấy trong Java client phải ghi rõ là `remake/reconstruction`, không gắn nhãn Java gốc

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
| `-16777215` | marker | giá trị synthetic Java dùng trong `mq.a(..., -16777215, ...)` để tạo clear/effect entry từ skill, không phải node bàn cờ bình thường |

Lưu ý:

- `mask` mới là thứ quyết định match/collision, không phải tên file ảnh
- `image index` không đồng nghĩa với node id
- nếu bản port đang map thẳng `node id -> asset id` thì rất dễ sai

Nguồn:

- [nj.java](/d:/Twelve/reference/redecoded/decompiled/nj.java:35)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:772)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1340)

### 4. Gameplay memory mapping 8 icon board gốc

Theo xác nhận user ngày `2026-04-26`, board Java cũ gameplay dùng `8` item từ bộ sheet `/chess0..8` nhưng **bỏ `chess7`**. Mapping này là gameplay memory/user evidence, phải được ghi riêng với `nj node id` decompile vì Java renderer dùng `imageIndex` còn logic match dùng `mask`.

| Icon / gameplay memory | Chess sheet / node image liên quan | Behavior đã xác nhận |
|------------------------|------------------------------------|----------------------|
| Kiếm trắng/thường | `chess0` | Base sword; có thể match cùng kiếm lửa do user xác nhận "chỉ cần 1 kiếm lửa với các kiếm trắng cũng nổ hết" |
| Tim hồi máu | `chess1` | Match hồi máu ngay trong trận; công thức hiện xem `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5` |
| Đào | `chess3` | Match hồi nộ/Power ngay trong trận; công thức hiện xem `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5` |
| Kiếm lửa | `chess8` | Item nổ vùng `3x3`; cứ bị tác động là nổ, kể cả match cùng kiếm trắng hoặc skill làm rơi/tác động vào kiếm lửa sau đó; gây sát thương ngay với hệ số user memory `x1.5` |
| Sao xanh | cần đối chiếu image index/node | Match tích EXP tạm nội bộ; không cần hiện counter tạm trong trận, chỉ chốt cộng/hiện ở màn kết quả nếu thắng |
| Vàng | cần đối chiếu image index/node | Tích gold nội bộ trong trận; max `10k` thì quy đổi thành `10k Quan`/tiền nạp sau này; không cần hiện counter tạm, nếu thua mất hết phần gold trận |
| Giọt tím EXP nửa sao | cần đối chiếu image index/node | Match như gem thường, tích EXP tạm nội bộ bằng nửa sao; không tạo special; không cần hiện counter tạm, nếu thua mất hết phần EXP trận |
| Các icon còn lại trong 8 icon | là các sheet còn lại trong `chess0..8` sau khi bỏ `chess7` | Match bình thường theo mask, clear/drop/refill |
| Empty/block | `90`/`99` | Không phải icon gameplay active |

Rule gameplay đi kèm mapping này:

- tập icon gameplay user xác nhận là `chess0..8` nhưng loại `chess7`; tổng cộng `8` item active
- tất cả icon match xong đều biến mất rồi drop/refill
- không một item nào tạo special tự nhiên
- giọt tím EXP nửa sao match bình thường nhưng không tạo special
- tim hồi HP và đào hồi nộ/Power ngay trong trận; công thức hiện dùng rule remake/server-owned trong `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`
- sao xanh/giọt tím EXP và vàng là tích lũy tạm nội bộ trong trận; nếu thắng mới chốt/hiện ở màn kết quả, nếu thua mất hết phần EXP/Gold/Quan kiếm từ board trận đó
- theo user memory, các item vàng/sao/giọt tím không cần hiện counter tạm trong battle HUD; chúng âm thầm cộng vào pending reward và chỉ thể hiện ở màn kết quả nếu thắng
- kiếm lửa `chess8` là tile có tính chất trigger-on-touch: match với kiếm trắng cũng nổ, skill/cascade/refill/tác động sau đó rơi vào nó cũng phải nổ; vùng nổ user memory là `3x3`
- kiếm lửa gây sát thương ngay, với hệ số user memory `x1.5`; công thức base damage/target ownership vẫn là phần server Java cũ hoặc remake hiện tại phải tính sau
- những công thức EXP/Gold/Quan và damage/nổ `3x3` của kiếm lửa là phần server Java cũ hoặc remake hiện tại phải tính sau, không suy bừa từ asset

## Runtime Turn State Machine Java

`mq.b()` là vòng update chính. Các state quan trọng liên quan bàn cờ:

| State | Vai trò đã xác nhận |
|-------|----------------------|
| `0` | idle/chưa vào battle active hoặc chờ init |
| `7` | chuyển vào flow turn mới sau khi nhận `nq` |
| `9` | result/end-state presentation |
| `10` | board reset/no-move animation path |
| `11` | đã nhận skill affect, chuẩn bị xử lý skill |
| `15` | apply turn result / chờ turn tiếp theo |
| `16` | sau result/end-state, gọi cleanup/scene transition |
| `19` | item/effect helper transition |
| `20` | no-move text path, rồi chuyển state `10` |

`nq.c` là loại turn/action packet. Trong `mq.t()`:

| `nq.c` | Flow trong `mq` | Ý nghĩa thực dụng |
|--------|------------------|-------------------|
| `0` | chỉ update revision/end turn nhẹ | sync/update thường |
| `1` | `f(nq.k, nq.j)` | update cursor/actor position/turn owner presentation |
| `2` | `processUpdateMatch` + `nl[]` | update attribute/result không swap |
| `3` | `processSwapChess` | swap board, board/refill buffer, actor result sau swap |
| `4` | `processUseItem` | dùng item, có thể kèm board/result |
| `5` | `processUsingSkill` | skill affect + board mutation + actor result |
| `6` | `processAttack` | attack thường, actor result + animation |
| `8` | battle result cuối | win/lose/draw, exp/gold/reward |

Điểm rất quan trọng:

- `mq` luôn queue `nq` vào `kr l`; chỉ xử lý packet tiếp khi HUD actor animation (`mx`) đã xong.
- `nq.b` là revision/turn sequence; `mq.m` cập nhật theo packet.
- `nq.i` là checksum server; sau local board resolution, Java tính checksum client bằng `oz.a(ms.l)` và nếu lệch thì clear queue + yêu cầu sync lại.
- `nq.g` là danh sách byte buffer board/refill phụ; `mq.b(byte[])` đẩy vào `ms.a(byte[])`.
- `nq.h` được gán vào `ms.o`; đây là buffer board reset/no-move hoặc board snapshot dùng bởi `ms.f()`.
- `nq.D` và `nq.F` là delta server gửi, không có formula sinh ra trong client.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:184)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:217)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:238)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1738)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1806)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1822)

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
- sort/dedupe trong `mq.a(a, boolean)` không chỉ để đẹp: nếu 2 line trùng span, Java giữ line có spawn center phù hợp hơn (`h`/`i`) rồi bỏ line còn lại
- merge ngang/dọc chỉ xảy ra khi cùng `mask` và vertical column thật sự nằm trong horizontal span, horizontal row thật sự nằm trong vertical span

### 3.1. Tạo special piece: decompile có nhánh, gameplay gốc cần ưu tiên "không tạo special"

Flow trong Java client code có nhánh xử lý:

1. gom toàn bộ line ngang và dọc vào `mw[]`
2. sort + dedupe từng nhóm line
3. merge line ngang/dọc giao nhau cùng `mask`
4. clear board
5. sau clear mới spawn special mới vào board model

Tuy nhiên, theo gameplay memory do user xác nhận ngày `2026-04-26`:

- "Không một item nào được tạo special"
- tất cả item/gem sau khi match đều biến mất
- giọt tím EXP nửa sao match như bình thường nhưng cũng không tạo special
- board Java cũ chỉ có đúng 8 icon gameplay đã thấy, không có icon khác

Kết luận reconstruction hiện tại:

- không được coi natural match 4/5 tự tạo special là rule gameplay gốc chắc chắn
- nhánh `mr.x/mr.y` trong decompile phải được note là logic Java client tồn tại nhưng có thể là:
  - nhánh không bật trong runtime/server mode gốc
  - nhánh phục vụ skill/packet/variant khác
  - hoặc artifact còn lại nhưng không phải behavior game người chơi nhớ
- khi port bản hiện tại, nếu muốn bám gameplay cũ theo user memory thì match thường chỉ clear rồi drop/refill, không spawn item special tự nhiên

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:609)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1632)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1672)

Rule nâng cấp theo nhánh code decompile, chỉ để tham khảo/đối chiếu:

- nếu `horizontal >= 5`
- hoặc `vertical >= 5`
- hoặc `horizontal >= 3 && vertical >= 3`
  - code có thể spawn `mr.y[baseId]` = `20..25`
  - node này là `type 4`

- nếu `horizontal >= 4`
- hoặc `vertical >= 4`
  - code có thể spawn `mr.x[baseId]` = `10..15`
  - node này là `type 2`

Giới hạn trong code:

- chỉ base node có `mask < 64` mới được nâng cấp
- special đang có sẵn không nâng cấp tiếp trong logic này

Nhưng rule gameplay đã chốt theo user memory:

- match `>= 4` cộng lượt theo số group match, không tạo item special tự nhiên
- mọi item sau match biến mất rồi board drop/refill
- không dùng node `10..15`/`20..25` như natural special spawn trong gameplay local mặc định cho tới khi có packet log/video chứng minh ngược lại

Nguồn:

- [mr.java](/d:/Twelve/reference/redecoded/decompiled/mr.java:4)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:691)

### 3.2. Vị trí spawn special trong nhánh code decompile

Đây là nhánh chỉ giữ để đối chiếu Java code, không còn là contract gameplay mặc định sau khi user xác nhận không item nào tạo special.

Nếu nhánh decompile này được bật, Java không spawn “ở ô vừa swap tới” theo kiểu tùy ý:

- line ngang > 3, không có giao dọc:
  - spawn tại `startCol + ((len - 1) >> 1)`
- line dọc > 3, không có giao ngang:
  - spawn tại `startRow + ((len - 1) >> 1)`
- T/L/cross:
  - spawn đúng tại ô giao giữa line ngang và line dọc

Hệ quả nếu bật nhánh này:

- line 4 ngang spawn ở ô thứ `2` tính từ đầu line
- line 4 dọc cũng vậy
- T/L spawn ở giao điểm, không ở đầu line

Contract gameplay hiện tại:

- match dài chỉ ảnh hưởng clear/drop/refill và `+ lượt`
- không để lại special tại tâm line/giao điểm

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1632)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1672)
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

### 4.1. Rule gameplay đã xác nhận cho `+ lượt`

Theo user memory ngày `2026-04-26`:

- cứ match `>= 4` thì được `+ lượt`
- nếu một nước đi tạo nhiều group match `>= 4` thì cộng theo số group đó
- ví dụ đồng thời ăn 2 group đủ điều kiện thì `+2 lượt`
- rule này là gameplay memory để phục dựng local/server mới; Java client vẫn chỉ thấy `nq.F` là delta server gửi, không có formula sinh `F`

Kết luận implementation:

- local/remake có thể tính `extraTurns = count(matchGroups where length >= 4 hoặc merged group đủ điều kiện)`
- vẫn phải ghi rõ đây là rule phục dựng từ gameplay memory, không phải formula đọc trực tiếp từ client Java
- không gắn rule này với natural special spawn; match `>= 4` cộng lượt nhưng item vẫn biến mất theo memory mới nhất

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
- `my.e[0..3]` giữ lần lượt packed line ngang/dọc của ô thứ nhất và ngang/dọc của ô thứ hai; controller dùng lại để seed clear queue thay vì scan lại tùy ý

## Skill Packet và Board Mutation Java

Skill trong Java không phải chỉ là animation. `nq.c == 5` đi qua flow:

1. `mq.t()` nhận `nq`
2. gọi `a(nq, true)` để xử lý owner/turn UI
3. nạp các buffer board/refill từ `nq.g`
4. gán board reset/snapshot từ `nq.h`
5. gọi `mq.a(ownerSide, skillId, skillLevelPlusOne, byArray, byArray2, byArray3, byArray4)`
6. `mq` mutate board/actor status theo skill id
7. `mt.a(lv, ...)` chạy projectile/impact/visual
8. sau delay, `mq` quay về state result để apply `nl[]`

Mapping tham số trong decompile:

- `nq.n` = skill id
- `nq.r` = skill level/index; `mq` dùng `I = n4 + 1`
- `nq.s`, `nq.o`, `nq.q`, `nq.p` = các byte arrays truyền vào skill processor
- `byArray`/`byArray2` thường là row/col target cell
- `byArray3` có skill dùng như danh sách row/line hoặc tham số phụ
- `byArray4` truyền tiếp xuống renderer/effect

Skill board mutation đã thấy trực tiếp trong `mq`:

| Skill id | Board/status behavior trong `mq` | Note |
|----------|----------------------------------|------|
| `1000` | clear các cell `byArray/byArray2` bằng marker `-16777215` | board clear/effect từ server target list |
| `1001` | set cell thành node `10`, push vào `a.p[]` | fire-sword mark; dễ đụng với natural special node id `10` nên renderer phải phân biệt bằng trigger context |
| `1002` | gọi actor owner `.a(I)` | status/helper timer, không clear board trực tiếp |
| `1003`, `1005` | không clear board trong `mq`, chỉ visual/result | server/renderer xử lý |
| `1006`, `1007` | clear target cells từ packet | target list server |
| `1008` | clear target cells theo nhóm, delay giảm dần `16,14,...` | effect nhiều đợt |
| `2000`, `2003`, `2007`, `2008` | clear target cells từ packet | target list server |
| `2001` | actor owner `.c(I)` | status/helper timer |
| `2002` | actor owner `.d(I)` | status/helper timer |
| `2004` | actor đối thủ `.e(I)` rồi fall-through `2005` | status/helper timer; không break trước `2005` là behavior Java |
| `2006` | sort `byArray3`, clear toàn hàng theo 1-2 row packet gửi, hướng delay phụ thuộc side | line clear do server chọn row |
| `4000`, `4006`, `4007`, `4008` | clear target cells từ packet | target list server |
| `4001`, `4004`, `4005` | không clear board trong `mq` | visual/result |
| `4002` | actor đối thủ `.b(I)` | status/helper timer |

Kết luận boundary:

- Với skill, client Java không tự tìm target list từ hình học asset. Target row/col/line phần lớn đã nằm trong packet.
- Port hiện tại nếu để FE tự đoán target list skill sẽ dễ sai; BE nên trả target arrays giống shape `nq`.
- Các actor status timer do skill set nằm ở `lg`, nhưng tên gameplay chính xác của từng `.a/.b/.c/.d/.e(I)` cần map thêm từ skill data/visual, không nên đoán tên.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1311)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1323)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1336)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1556)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:989)

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
`ne` tạo vòng 20 sprite sao bay ra, radius tăng từ `3` tới `35 + (combo - 1) * 5`; text chỉ hiện sau khi radius >= `7`.

Nguồn:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:394)
- [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java:844)
- [ne.java](/d:/Twelve/reference/redecoded/decompiled/ne.java:23)
- [ne.java](/d:/Twelve/reference/redecoded/decompiled/ne.java:40)

Kết luận:

- `HP`, `MP`, `Nộ` là stat runtime thật
- `Sao` trong battle là combo FX/popup
- không được lưu `star` như resource battle nếu Java không dùng kiểu đó

## Chess Cell Animation Runtime

`nd` là runtime cell animation, không phải nơi quyết định logic match.

Các mode/state animation thấy trong `nd.k()`:

| `nd.e` | Ý nghĩa thực dụng |
|--------|-------------------|
| `0` | inactive/hidden sau explode |
| `1` | idle chess frame |
| `2` | explode/clear animation, chạy tới frame cuối rồi về `0` |
| `3` | falling/drop animation, tới đích rồi về `1` |
| `4` | swap/move animation, tới đích rồi về `1` |
| `5` | highlight/focus pulse có `t = 28` giảm dần |
| `6` | delayed transition: sau delay set position/image rồi vào mode `1` hoặc `2` |

Cell sprite lấy từ `Image[] B`, mỗi sheet chia 7 frame ngang:

```java
this.o = this.b.getWidth() / 7;
this.p = this.b.getHeight();
```

Kết luận port:

- animation mode không được dùng làm truth logic board
- logic board phải cập nhật `nj`/node trước, animation chỉ playback
- visual special `10..15`/`20..25` vẫn lấy image index từ `nj.g`, không được map sang hidden dragon/phoenix ornament

Nguồn:

- [nd.java](/d:/Twelve/reference/redecoded/decompiled/nd.java:28)
- [nd.java](/d:/Twelve/reference/redecoded/decompiled/nd.java:96)
- [nd.java](/d:/Twelve/reference/redecoded/decompiled/nd.java:132)

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

## Server Java Cũ: Những điểm phải note lại để tính sau

Các phần sau liên quan server Java cũ hoặc packet authoritative, client chỉ apply/render nên chưa thể chốt formula 100%:

1. `nq.D` thêm/giảm thời gian turn
   - Client chỉ cộng vào `ms.g`, rồi set `np.a = seconds * 1000`.
   - Không thấy formula sinh `D` trong client.
2. `nq.F` thêm/giảm lượt còn lại
   - Client chỉ cộng vào biến lượt `B` nếu mode không phải `oq.o == 1`.
   - Không thấy formula sinh `F` trong client.
   - Gameplay memory đã xác nhận rule phục dựng: match `>= 4` cộng lượt theo số group match đủ điều kiện.
3. `nl[]` actor result
   - Client nhận `hp/mp/power/damage/flag`, update HUD và popup.
   - Final damage/heal/resource gain formula nằm server cũ.
4. Skill target arrays
   - Client dùng row/col/line arrays từ `nq`, không tự suy toàn bộ.
   - BE hiện tại cần sinh packet shape tương đương nếu muốn bám Java.
5. Refill queue và no-move board reset
   - `ms.m[ms.n++]` lấy từ byte buffer packet.
   - `ms.o` dùng cho board reset/snapshot; client không tự generate như server cũ.
6. Checksum/sync
   - Server gửi checksum `nq.i`; client tính `oz.a(ms.l)`.
   - Nếu lệch thì clear queue và request sync.
7. Reward roll
   - Client chỉ nhận `ll[]`, `lm[]`, exp/gold/result flags rồi present.
8. EXP từ sao xanh và giọt tím EXP nửa sao
   - User xác nhận match sao xanh tăng EXP cho nhân vật nếu thắng trận.
   - Giọt tím EXP nửa sao match như bình thường, không tạo special.
   - User xác nhận vàng/sao/giọt tím chỉ âm thầm cộng pending reward, không cần hiện counter tạm trong trận; chỉ chốt/hiện ở màn kết quả nếu thắng.
   - Công thức cộng EXP và điều kiện ghi EXP cuối trận cần tính sau.
9. Gold từ icon vàng
   - User xác nhận vàng tích điểm vào player, max `10k` thì quy đổi `10k Quan`.
   - Công thức tích gold/quy đổi Quan cần tính sau, không suy từ reward item icon.

Những điểm này cần server source, packet log, hoặc replay/video đủ dày để suy ngược. Khi chưa có, mọi logic thay thế phải ghi là reconstruction/remake.

Nguồn:

- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:223)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:232)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:245)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1251)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1311)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1570)
- [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java:1584)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1832)

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
2. match ngang/dọc, clear/drop/refill; natural special spawn tắt theo gameplay memory hiện tại
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
- không coi natural special spawn là gameplay mặc định: user memory xác nhận không item nào tạo special; nhánh spawn tâm line/giao điểm chỉ giữ như note decompile để đối chiếu
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
  - hiện đang có nhánh spawn special `10..15` và `20..25`, nhưng cần chỉnh/tắt nếu bám gameplay memory mới nhất: match xong item biến mất, không tạo special tự nhiên
  - clear chain cho special `type 2` và `type 4` chỉ nên giữ cho packet/skill/variant nếu thật sự có node đó
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

Code client hiện tại có `bonusTurnCandidate` theo heuristic:

- line `>= 4`
- hoặc line merge có trục `>= 4`

Sau xác nhận user ngày `2026-04-26`, rule phục dựng nên đổi thành:

- match `>= 4` thì cộng lượt
- cộng theo số group match đủ điều kiện trong cùng nước đi/cascade tùy contract turn local
- không tạo special tự nhiên kèm theo việc cộng lượt

Lý do vẫn phải ghi boundary:

- Java client chỉ parse `nq.F`
- formula server cũ sinh `nq.F` không nằm trong client
- rule `match >= 4 => + lượt theo group` là gameplay memory/user-confirmed, không phải dòng code client Java sinh trực tiếp

## 100% Boundary

Có 2 mức “giống Java”:

### 1. Có thể làm gần như 100% từ client Java

- board topology
- validate swap
- match scan theo mask
- merge line ngang/dọc
- special clear
- special clear rule nếu packet/skill thật sự sinh node `type 2/type 4`
- nhánh special spawn/position có trong decompile nhưng không còn coi là gameplay mặc định nếu bám memory "không item nào tạo special"
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

1. định nghĩa enum/const cho toàn bộ `nj node ids` và mapping 8 icon gameplay (`chess0` kiếm trắng, `chess1` tim, `chess3` đào, `chess8` kiếm lửa; bộ `/chess0..8` bỏ `chess7`)
2. port nguyên match scan theo `mask`
3. chỉnh local board contract: match thường clear/drop/refill, không tạo special tự nhiên; match `>= 4` cộng lượt theo số group
4. giữ clear behavior `type 2/type 4` cho skill/packet/variant nếu phát hiện node đó thật sự xuất hiện
5. tách `timeLeft`, `remainingTurns`, `hasValidMove`
6. chuẩn hóa local result model cho `hp/mp/power`, EXP từ sao xanh/giọt tím, gold/Quan từ vàng
7. chỉ sau đó mới bind animation `mt/mx/mp`

Nếu làm ngược lại, bản battle sẽ nhìn giống Java nhưng logic sẽ lệch ở những chỗ quan trọng nhất.

## Nhật ký chỉnh sửa

### 2026-04-26 — Cập nhật gameplay memory icon board, +lượt và bỏ natural special mặc định

  - File tài liệu đã sửa:
    - `BATTLE_SYSTEM_RECONSTRUCTION.md`
  - Nội dung:
    - Ghi nhận xác nhận gameplay memory:
      - board gameplay dùng `8` item từ `/chess0..8` nhưng bỏ `chess7`;
      - `chess0` là kiếm trắng/thường;
      - tim hồi máu là `chess1` và hồi ngay trong trận;
      - đào là `chess3` và hồi nộ/Power ngay trong trận;
      - kiếm lửa là `chess8`, nổ vùng `3x3`, có tính chất cứ bị tác động là nổ, gây sát thương ngay với hệ số user memory `x1.5`;
      - chỉ cần `1` kiếm lửa nằm trong match với các kiếm trắng thì toàn bộ cụm kiếm đó kích hoạt nổ;
      - skill/cascade/refill/tác động sau đó rơi vào kiếm lửa cũng phải kích hoạt nổ;
      - sao xanh/giọt tím EXP và vàng là tích lũy tạm nội bộ trong trận, không cần hiện counter tạm, chỉ chốt/hiện ở màn kết quả nếu thắng; thua trận mất hết EXP/Gold/Quan kiếm được từ board trận đó;
      - giọt tím EXP nửa sao match như bình thường nhưng không tạo special.
    - Sửa lại kết luận natural special spawn: decompile có nhánh `mr.x/mr.y` spawn `10..15`/`20..25`, nhưng user memory xác nhận "không một item nào được tạo special"; vì vậy không coi natural special spawn là gameplay mặc định.
    - Chốt rule phục dựng `+ lượt`: match `>= 4` cộng lượt theo số group match đủ điều kiện, tách khỏi special spawn.
    - Liên kết HP từ tim và nộ từ đào với công thức remake/server-owned trong `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`.
    - Note các phần cần tính sau từ server/remake: công thức EXP từ sao xanh/giọt tím, công thức gold/Quan, base damage/target ownership của kiếm lửa `3x3`; riêng hệ số sát thương user memory đã xác nhận là `x1.5`.
  - Nguồn:
    - User gameplay memory trả lời trực tiếp ngày `2026-04-26`.
    - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5` cho công thức HP/MP/Power match gem hiện tại.
    - Java client vẫn chỉ parse/apply `nq.F`, `nl[]`, reward/EXP/gold result; không có source server formula.

### 2026-04-26 — Bổ sung coverage Java client battle/bàn cờ

- File tài liệu đã sửa:
  - `BATTLE_SYSTEM_RECONSTRUCTION.md`
- Nội dung:
  - Bổ sung section `Java Client Coverage / Mức độ đầy đủ hiện tại`.
  - Ghi rõ kết quả kiểm tra JAR client:
    - `2259` entries;
    - `427` `.class`;
    - `1832` resource/non-class;
    - `427` file `.java` trong `decompiled`;
    - `427` file `.java` trong `cfr_fresh`;
    - unique class stem match `425 / 425`.
  - Xác nhận nhóm class battle/bàn cờ chính đều tồn tại ở cả `decompiled` và `cfr_fresh`.
  - Chốt mức khôi phục nếu chỉ tính bàn cờ Java client-side là khoảng `97-99%`; phần còn lại chủ yếu là tên semantic/timing visual nhỏ do obfuscation, không phải thiếu core logic.
  - Ghi rõ các field packet như refill queue, board reset bytes, `nq.D`, `nq.F`, `nl[]`, reward list là data client nhận/apply, không phải phần client tự sinh formula.
- Nguồn kiểm tra:
  - `reference/redecoded/jar-contents.txt`
  - `reference/redecoded/decompiled`
  - `reference/redecoded/cfr_fresh`

### 2026-04-26 — Tổng hợp lại boundary và logic Java battle bàn cờ

- File tài liệu đã sửa:
  - `BATTLE_SYSTEM_RECONSTRUCTION.md`
- Nội dung:
  - Chuẩn hóa lại `Java Boundary Chốt`, tách rõ phần client Java tự simulate board với phần server Java cũ từng gửi qua `nq`.
  - Bổ sung state machine `mq`: các state runtime quan trọng (`7`, `10`, `11`, `15`, `20`) và mapping `nq.c` (`swap`, `skill`, `attack`, `result`, `use item`).
  - Bổ sung note checksum/sync: client Java tính checksum board sau local cascade và so với `nq.i`; lệch thì clear queue/request sync.
  - Bổ sung skill packet board mutation: `nq.n/r/s/o/q/p`, target arrays row/col, các skill id `1000..4008` đã thấy trong `mq`, và cảnh báo target list thuộc packet/server.
  - Bổ sung `nd` cell animation runtime và `ne` combo-star popup để tránh nhầm visual asset với logic board.
  - Bổ sung danh sách các điểm chắc chắn thuộc server Java cũ cần tính sau: `nq.D`, `nq.F`, `nl[]`, refill queue, no-move reset board, reward roll.
- Nguồn suy luận:
  - `reference/redecoded/decompiled/mq.java`
  - `reference/redecoded/decompiled/mr.java`
  - `reference/redecoded/decompiled/mw.java`
  - `reference/redecoded/decompiled/my.java`
  - `reference/redecoded/decompiled/nq.java`
  - `reference/redecoded/decompiled/nl.java`
  - `reference/redecoded/decompiled/nd.java`
  - `reference/redecoded/decompiled/ne.java`
  - `reference/redecoded/decompiled/np.java`

### 2026-04-26 — Sửa fallback gem resource khiến MP hồi nhầm HP

- File code đã sửa:
  - `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- Nội dung:
  - Sửa `getServerGemResourceBase()` để khi payload bootstrap cũ/chưa đủ `perGemBases`, client fallback về bảng semantic per-color `GEM_FX_BASE` thay vì dùng 3 scalar global `BaseHealPerGem/BaseManaPerGem/BasePowerPerGem` cho mọi gem.
  - Nguyên nhân lỗi "ăn MP lại hồi cả HP": một số gem MP/mixed có `fx.heal > 0` ở bảng semantic cũ; fallback scalar global đã biến phần heal nhỏ đó thành `BaseHealPerGem`, làm gem MP cũng hồi HP rõ rệt nếu bootstrap chưa có `perGemBases`.
  - Policy lưu DB được giữ: HP sau thắng PvE có thể giữ current HP để tạo attrition; MP/Power là tài nguyên tạm trong battle, `/battle/result` reset về `0`, nhân vật mới cũng seed `Mp = 0`, `Power = 0`. Vì mỗi trận reset MP/Power nên không nên dùng DB như nguồn carry-over MP battle; DB chỉ giữ trạng thái ngoài trận/bootstrap an toàn.
- Nguồn suy luận:
  - Java client xác nhận `lh.s/r`, `lh.u/t`, `lh.w/v` và `nl.b/c/d` là current/max/delta runtime, nhưng không có server formula cũ cho bảng resource gain.
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`: HP/MP/Power gain là rule remake có kiểm soát, server-owned; fallback FE chỉ để tương thích payload cũ, không được làm đổi semantic per-color.

### 2026-04-26 — Battle gem base resource chuyển sang server bootstrap

- File code đã sửa:
  - `server/Twelve.Core/Monsters/MonsterContracts.cs`
  - `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
  - `client/src/screens/battle/core/BattleScreen.types.ts`
  - `client/src/screens/battle/BattleScreen.tsx`
  - `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- Nội dung:
  - Bổ sung `BattleGemResourceConfig` vào monster battle bootstrap để server phát base HP/MP/Nộ cho match gem:
    - `BaseHealPerGem = 18`
    - `BaseManaPerGem = 5`
    - `BasePowerPerGem = 5`
  - Client `useBattleMatchFlow.applyGemFx` không còn lấy trị số base resource trực tiếp từ hardcode FE nếu bootstrap có config; FE chỉ dùng `GEM_FX_BASE` để giữ semantic board Java-like: gem family nào có thể sinh HP/MP/Power.
  - Công thức runtime giữ integer math: `floor(BaseValue * MatchedGemCount / 3)`, sau đó scale bằng `HealGainPercent/ManaGainPercent/PowerGainPercent` do server trả.
  - Fallback sang `GEM_FX_BASE` chỉ để tương thích payload cũ, không phải source of truth mới.
- Nguồn suy luận:
  - Java client xác nhận board/HUD/resource bar nhưng không có server formula cũ cho resource gain.
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`: resource gain là rule remake có kiểm soát và phải thuộc server authority.

### 2026-04-26 — Battle resource scale chuyển sang server authority

- File code đã sửa:
  - `server/Twelve.Core/Battle/BattleSessionContracts.cs`
  - `server/Twelve.Core/Monsters/MonsterContracts.cs`
  - `server/Twelve.Application/Battle/PlayerBattleStateFactory.cs`
  - `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
  - `client/src/screens/battle/core/BattleScreen.shared.ts`
  - `client/src/screens/battle/core/BattleScreen.types.ts`
  - `client/src/screens/battle/BattleScreen.tsx`
- Nội dung:
  - Mở rộng combatant snapshot trả `HealGainPercent`, `ManaGainPercent`, `PowerGainPercent` để server là nguồn truth cho scale HP/MP/Nộ từ match gem.
  - Player combat state lấy stat/status từ `PlayerStatPipeline`, giữ status Java-faithful `jp/jq/js/jr` đã port trước đó.
  - Monster combat state tính resource percent ở server theo TotalStrength/TotalMagic của monster.
  - Client bỏ tự suy diễn scale resource theo stat cục bộ; `applyGemFx` dùng percent server trả về, fallback `100%` chỉ để tương thích snapshot cũ.
  - `GEM_FX_BASE` ở FE chỉ còn là base effect/visual pacing của từng loại gem, không còn là nơi quyết định stat scaling.
  - `BattleTurnEngine` tiêu thụ trực tiếp stat server-owned trong `BattleSessionCombatantState`: `MinDamage/MaxDamage`, `Defense`, `HitRate`, `DodgeRate`, `CriticalDamage`, `PowerGainPercent`.
  - Bỏ nhánh cộng thêm `ResolveAttackStat()`/level/stat hardcode trong damage turn engine để không double-count với `PlayerStatPipeline`/status Java-faithful.
  - Power gain từ skill và từ nhận damage được scale bằng `PowerGainPercent` do server bootstrap, không để FE tự quyết.
  - Bổ sung `ElementCode` vào battle session combatant để damage skill/monster turn áp dụng khắc hệ v1 ở server: Cường Lực-like `0` > Thân Pháp-like `1` > Nội Lực-like `2` > Cường Lực-like `0`, với `112%/100%/92%`.
  - Player lấy element từ `Player.Element`; monster lấy từ `MonsterBattleTemplate.Element`.
- Nguồn suy luận:
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`: Java client chỉ xác nhận HP/MP/Power bar, công thức gain là rule remake có kiểm soát.
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §8`: khắc hệ battle là rule remake có kiểm soát vì không có server Java mẫu/final damage packet formula.
  - `PLAYER_CHARACTER_RECONSTRUCTION.md`: status hiển thị phải bám Java, battle/resource là tầng riêng được phép có remake rule khi ghi rõ nguồn.
  - `docs/combat-formulas.md`: Java client chỉ áp/render delta authoritative, không chứa final server damage formula.
  - Không có server Java mẫu, nên mọi resource/damage gain mới được comment là reconstruction/remake, không gắn nhãn Java gốc.

### 2026-04-26 — Cân bằng lại HP/MP/Nộ từ match gem level thấp

- File code đã sửa:
  - `client/src/screens/battle/core/BattleScreen.shared.ts`
- Nội dung:
  - Giảm base resource gain của `GEM_FX_BASE` để level 1 không hồi MP/HP/nộ quá nhanh:
    - viên đào `heal 28 -> 12`;
    - viên MP chính `mana 15 -> 8`;
    - các viên mixed giảm heal/mana/power tương ứng;
    - Power/nộ trên sword/gold/resource giảm để thanh nộ tích dần hơn.
  - Giảm scaling theo stat resource:
    - Cường Lực tăng hồi HP/nộ từ `3%/point` xuống `1%/point`;
    - Nội Lực tăng hồi MP từ `3%/point` xuống `1%/point`;
    - cap resource từ `80..180%` thành `90..140%`.
  - Lý do: Java client xác nhận bar `lh.s/r`, `lh.u/t`, `lh.w/v` và battle HUD `mx`, nhưng không có công thức server cũ chính xác cho bảng ăn gem. Remake phải ưu tiên pacing quan sát từ Java cũ: level 1 ăn 3 viên MP chỉ nên tăng khoảng một phần nhỏ thanh MP, quái ăn đào không được hồi quá nhanh chỉ vì stat Cường Lực.
- Nguồn suy luận:
  - `docs/player-character-reconstruction/02-truth-payload-and-tags.md`: mapping HP/MP/Power `lh`.
  - `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`: resource là tầng remake có cap, không phải công thức Java status.
  - Phản hồi test gameplay level 1: MP/HP/nộ tăng quá nhanh so với Java cũ.

### 2026-04-25 — Khôi phục natural special spawn match 4/5 theo Java

- File code đã sửa:
  - `client/src/screens/battle/core/BattleScreen.logic.ts`
- Nội dung:
  - Rà lại `mq.java`, `mr.java`, `nj.java` để xác minh natural match dài thật sự spawn special node:
    - cross hoặc line `>= 5` spawn `mr.y[baseId]` = `20..25` (`type 4`, clear hàng + cột);
    - line `>= 4` spawn `mr.x[baseId]` = `10..15` (`type 2`, clear 8 ô lân cận);
    - chỉ node có `mask < 64` mới được nâng cấp, nên node `70`/gold mask `64` không spawn special tự nhiên.
  - Sửa `resolveSpawnGem` trong client để không còn disable natural special spawn. Special mới vẫn spawn sau khi clear line cũ, bám flow `mq.a(nj[][])`.
  - Giữ ghi chú boundary: `nq.D` thêm thời gian và `nq.F` thêm lượt là kết quả packet/turn-result server Java, không chứng minh được 100% từ client rằng natural match 4/5 tự sinh thêm time/turn. Remake hiện tại vẫn giữ heuristic `bonusTurnCandidate` cho match `>= 4` như contract local cũ cho đến khi có packet log/server source.
- Nguồn suy luận:
  - `reference/redecoded/decompiled/mq.java:691-699`
  - `reference/redecoded/decompiled/mr.java:4-6`
  - `reference/redecoded/decompiled/nj.java:41-62`

### 2026-04-25 — Sửa visual special board và bỏ global API loading battle

- File code đã sửa:
  - `client/src/screens/battle/ui/BattleScreen.components.tsx`
  - `client/App.tsx`
- Nội dung:
  - Rà lại rule `nj`: node `10..15` là `type 2` clear 8 ô lân cận; node `20..25` là `type 4` clear hàng + cột. Lỗi nhìn giống "ăn item này lúc nổ hàng, lúc nổ kiếm đỏ" đến từ renderer đang coi mọi node id `10` là fire-sword mark skill `1001`, trong khi `10` còn là special board type 2 của kiếm.
  - Sửa renderer để chỉ chạy animation fire-sword mark khi cell `10` thật sự có `fireSwordMarkTrigger`/`fireSwordBaseGemType` từ skill packet; còn board special `10` tự nhiên sẽ render như `type 2` đúng logic Java, không bị biến thành kiếm đỏ persisted.
  - Giữ `type 4` đúng theo nhật ký trước: không dùng `hiddendragon/hiddenphoenix` làm overlay board vì đó là UI ornament/hidden-piece asset, không phải `nj.g` của node bàn cờ. Node `20..25` vẫn nổ hàng + cột theo logic, nhưng visual chỉ là base chess frame, tránh background/ornament lỗi.
  - Bỏ global fetch loading modal trong `App.tsx`; các API battle/map/PvP vẫn chạy bình thường nhưng không hiện `Vui lòng chờ...` liên tục trong gameplay. Loading chỉ còn ở màn/flow cục bộ nào tự bật, ví dụ tạo nhân vật.
- Nguồn suy luận:
  - `nj.java`: tách `node id`, `type`, `mask`, `image index`.
  - `mq.java`: clear special dựa vào `type 2` và `type 4`, không dựa vào ảnh.
  - `mp.java`/`mh.java`: renderer board lấy chess sheet theo image index; `pc.java` hidden pieces không phải asset render cell chính.

### 2026-04-25 — Bổ sung scoring nước đi board theo match quality Java

- File code đã sửa:
  - `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`
- Nội dung:
  - Bổ sung comment truy xuất nguồn gốc trực tiếp từ `reference/redecoded/cfr_fresh/mq.java` và `reference/redecoded/cfr_fresh/mr.java`.
  - Giữ rule Java: board playable là `8x8` tương ứng vùng `2..9` trong ma trận padded `12x12`; swap chỉ hợp lệ khi một trong hai endpoint tạo line ngang/dọc `>= 3`.
  - Thêm `JavaMatchInfo` để tái hiện packed line của Java: left/up, right/down và total length.
  - Enemy move scoring giờ ưu tiên match dài, cross match, line special `10..15`, cross/strong special `20..25`, thay vì chỉ đếm số ô match thô. Đây chưa phải full cascade authoritative, nhưng bám sát hơn logic `mq` khi chọn/đánh giá nước đi hợp lệ.

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
