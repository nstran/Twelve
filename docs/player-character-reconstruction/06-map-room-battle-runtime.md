# 06 - Map Room Battle Runtime

Map actor, room/profile runtime, battle actor wrapper và liên hệ với `lh`.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

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

### Map mới trong remake

Không có server/map cũ để khôi phục nguyên trạng. `MapDataStore` hiện chỉ là lớp dữ liệu tile thử nghiệm kiểu cũ, không được dùng làm nguồn truth cho tọa độ side-scroll mới.

Nguồn truth runtime hiện tại là `RuntimeMapCatalog` ở server:

- khai báo map/room mới theo kích thước native, ví dụ Hoa Lu `1536x1024`
- lưu world-state player theo tọa độ native map, không theo pixel đã scale trên màn hình client
- `MapHandler` trả spawn/current position từ catalog/runtime state
- `MoveHandler` clamp tọa độ theo kích thước native của room

Client tự scale tọa độ native sang màn hình theo `mapScale`. Khi tạo map mới, thêm config ở client và thêm room tương ứng vào `RuntimeMapCatalog`, thay vì dựa vào grid/tile cũ.

Quy ước mở rộng nhiều map:

- server: thêm `RuntimeMapRoom` vào `RoomDefinitions`; handler không cần sửa
- client: thêm scene config vào `SIDE_SCROLL_MAP_SCENES`; resolver dùng index theo `mapId:roomId`
- quái: spawn group nên đi theo scene config/map catalog, không hard-code trong màn hình
- DB: world-state luôn lưu `mapId`, `roomId`, `x/y` native để đổi kích thước màn hình không làm lệch vị trí

Luật gameplay hiện tại: map train là PvE instance của chính player, không phải nơi nhiều người chơi đứng cùng nhau. Người chơi chỉ tìm/gặp nhau qua flow Khiêu Chiến/PvP riêng; không tạo player roster/broadcast co-presence trên map train.

### Trạng thái triển khai map runtime

Đã xong:

- player trên map lấy character thật từ DB cho appearance/stat/HUD
- world-state player lưu DB theo `mapId`, `roomId`, `x/y`, `direction`, `actionState`
- `MapHandler` trả map info + vị trí spawn/current từ `RuntimeMapCatalog`
- `MoveHandler` nhận move theo tọa độ native, clamp qua `RuntimeMapRoom.ClampPosition`, lưu DB và echo canonical move ack
- client Hoa Lu scale tọa độ native server sang display pixel, và gửi ngược display pixel về native trước khi move
- client nghe cả `mapInfo` và `playerMapState` để snap về vị trí server duyệt
- `RuntimeMapCatalog` đã có surface metadata native cho Hoa Lu, chuẩn bị cho collision/path nhiều map

Chưa xong:

- server chưa có collision nâng cao theo platform/vertical physics; hiện mới kiểm soát bounds và horizontal surface range
- monster movement/AI vẫn chủ yếu chạy client-side, server mới quản lý roster/encounter
- `actionState` mới lưu/echo, chưa là state machine đầy đủ walk/jump/attack/hit/dead
- Khiêu Chiến/PvP chưa tách thành flow riêng để tìm người chơi và đấu với nhau

## Battle Result / EXP / Gold/KEN Reward

Sau khi battle kết thúc, client không tự cộng thưởng. Client gọi `/battle/result` với `sessionId`, kết quả thắng/thua và HP/MP/Power còn lại. Server claim session một lần, cập nhật DB rồi trả payload để client hiển thị bảng kết quả. HP/MP/Power là runtime resource theo `lh.s/r`, `lh.u/t`, `lh.w/v`.

Quy định remake hiện tại:
- thắng PvE giữ HP còn lại để train attrition có ý nghĩa;
- thua PvE hồi `HP = MaxHp`;
- PvP shadow hồi `HP = MaxHp` sau result;
- MP/Power là tài nguyên tạm trong battle và reset `0` sau khi claim result, tránh trận sau mở vào với nộ/MP đã tích từ trận trước.

Ghi chú 2026-04-25: Java client chỉ chứng minh `lh.H/I` là trục KEN/gold/collection dùng ở battle result (`hs` icon vàng, parser tag `43/99`), không chứng minh đây là tiền nạp. Trong remake, `Quan` được giữ làm paid currency/top-up, vì vậy reward quái thường không được cộng `Quan`; công thức thưởng tiền trận được map sang `Gold*`/`Player.Gold`, còn `Quan*` trong result response để `0`/compat cũ.

Quy định level hiện tại:

- EXP là tổng tích lũy, không reset khi lên cấp
- mốc bắt đầu level `N` = `100 * (N - 1)^2`
- mốc lên level kế tiếp = `100 * N^2`
- ví dụ level 1: `0/100`, level 2: `100/400`, level 3: `400/900`
- mỗi lần lên cấp: `+5` điểm tiềm năng, `+1` điểm kỹ năng

Quy định monster reward hiện tại:

- reward nằm trong `MonsterBattleTemplate`
- factory tính mặc định từ level/threat/skill tier
- EXP cơ bản = `12 + level * 3`, nhân threat: Minor `100%`, Standard `120%`, Elite `150%`, cộng skill bonus
- Gold/KEN cơ bản = `2 + level`, cộng threat: Standard `+4`, Elite `+8`
- `QuanReward` luôn `0` trong PvE quái thường để không phát paid currency
- thua trận: hồi đầy HP, reset MP/Power về `0`, không cộng Gold/Quan, và bị trừ EXP theo defeat penalty của level hiện tại

Đã xong:

- `BattleResultService` claim kết quả và chống claim lại session đã hoàn tất
- `/battle/result` HTTP endpoint
- client gọi result endpoint khi `victory/defeat`
- popup kết quả hiển thị HP còn lại, EXP, Gold/KEN và thưởng nhận được
- App cập nhật HUD/appearance sau result response
- thắng trận có thể rơi item/equipment và lưu thẳng vào aggregate; popup kết quả hiển thị loot text
- sau khi rời battle, App refresh runtime snapshot player để status/inventory/equipment và HP/MP/Power đồng bộ với DB
- nếu thua, nhân vật ngoài map bị khóa input ngắn và nháy opacity trước khi điều khiển lại

Defeat penalty hiện tại:

- HP sau thua được hồi về `MaxHp`
- MP/Power sau thua reset về `0`, không tự full lại ở FE
- EXP bị trừ `max(10, 5% level span hiện tại)` và không tụt xuống dưới `ExpFloor` của level hiện tại
- ví dụ level span = `400 - 100 = 300` thì phạt EXP = `15`

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
- Trong remake hiện tại, API battle result dùng tên `GoldBefore/GoldAfter/GoldGained` cho trục này để tránh nhầm với `Quan` paid currency; client vẫn fallback đọc `quan*` cũ nếu gặp payload cũ.

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

