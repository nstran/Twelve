# 05 - Protocol Trade Upgrade Market

Outbound `ks/kw`, command families, trade, nâng cấp, kết hợp, inbound response contract và market/sale contract.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

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

