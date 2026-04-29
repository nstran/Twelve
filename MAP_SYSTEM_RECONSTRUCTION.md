# MAP_SYSTEM_RECONSTRUCTION.md

## Mục tiêu

Khôi phục hệ thống map/world map/runtime map của Twelve từ Java client cũ sang React Native + .NET 9.

Nguyên tắc:

- Java old client là behavior/spec khi có dữ liệu.
- Không tự ý đổi asset key, tọa độ, cursor, softkey hoặc unlock rule nếu chưa có bằng chứng từ Java/data.
- Logic gameplay quan trọng phải ghi nguồn suy luận trong code/tài liệu.
- World map hiện là catalog legacy; runtime playable đầu tiên là Hoa Lư side-scroll.

---

## 1. Legacy world map theo Java

### 1.1 Source Java chính

Các file Java decompile làm nguồn suy luận chính:

- `oh.java`: màn hình world map/country selection, background `/m/m`, cursor, chọn map, softkey `Vào Thành`.
- `fz.java`: danh sách map/country entries, tọa độ icon/label, trạng thái khóa/mở.
- `fg.java`: renderer icon world map, marker, lock, selected state.
- `hi.java`: image/asset loader/cache.
- `pc.java`: metadata/country catalog provider được world map dùng.
- `og.java`: input/canvas/game loop liên quan màn hình map.
- `ks.java`: command/softkey model.

### 1.2 Asset contract

Asset logical key cần giữ theo Java client:

- `/m/m`
- `/m/arena`
- `/m/room`
- `/m/fsw`
- `/m/lock`
- `/m/lock2`
- `/m/hand`
- `/m/arrow`
- `/roomicon`

Rule:

- Có thể map key legacy sang asset React Native nội bộ, nhưng logical contract không đổi.
- World map dùng hệ tọa độ base `480x480`.
- Nếu asset/Skia không khả dụng, fallback renderer vẫn phải hiển thị bằng React Native primitive, không phụ thuộc Skia.

### 1.3 World map UI rules

- Background world map là `/m/m`, scale theo layout `480x480`.
- Input touch/pan dùng `PanResponder`.
- Pointer phải convert về hệ tọa độ `480x480` trước khi hit-test entry.
- Cursor:
  - `/m/arrow`: selected/hover entry thường.
  - `/m/hand`: entry có runtime target có thể vào.
- Softkey trái theo Java: `Vào Thành`.
- `Vào Thành` chỉ active khi selected entry mở và có runtime target hợp lệ.

### 1.4 Java world map audit đầy đủ

Nguồn Java đã rà trực tiếp cho world map/country selection:

- `reference/redecoded/cfr_fresh/oh.java`
  - Là màn hình world map/country selection.
  - Background logical asset là `/m/m`.
  - Các asset map UI được load trực tiếp trong class:
    - `Image c = f.d("/m/m")`
    - `Image d = f.d("/m/arena")`
    - `Image e = f.d("/m/room")`
    - `Image f = f.d("/m/lock")`
    - `Image g = f.d("/m/lock2")`
  - Softkey/command vào thành:
    - Tạo `new ks("Vào Thành", new ok(this))`.
    - Đây là command trái/primary cho entry đang chọn.
  - World map dùng danh sách entry từ `fz.a` và render thông qua từng `fg`.
  - Selected index/entry được giữ trong state của `oh`; khi đổi selected thì cursor/softkey phụ thuộc entry đó.
  - Cursor dùng asset từ `pc`:
    - `pc.e = /m/hand`.
    - `pc.f = /m/arrow`.
  - Background/country icon/lock/cursor đều là asset logical key, không được đổi tên contract khi port.
  - Map coordinate gốc là coordinate Java client, cần normalize/scale từ base legacy thay vì tự đặt lại.

- `reference/redecoded/cfr_fresh/fz.java`
  - Là catalog/danh sách entry world map phía Java client.
  - Chứa mảng/static entries cho các map/country.
  - Mỗi entry gồm thông tin vị trí icon/label/hit target và trạng thái mở/khóa.
  - Entry được `oh.java` dùng để render, hit-test và quyết định có cho chọn/vào hay không.
  - Khi port sang server catalog, các tọa độ này là source of truth legacy; không được tự điều chỉnh nếu chưa có bằng chứng mới từ Java/data.

- `reference/redecoded/cfr_fresh/fg.java`
  - Là renderer/representation cho từng icon/entry trên world map.
  - Render icon country/map, marker/selection và trạng thái khóa.
  - Dùng lock icon `/m/lock` hoặc `/m/lock2` tùy state.
  - Phân biệt trạng thái selected/hover so với entry thường.
  - Logic render entry phải tách khỏi runtime map side-scroll; world map chỉ là selection/catalog UI.

- `reference/redecoded/cfr_fresh/pc.java`
  - Cung cấp asset dùng chung cho UI map và một số helper render.
  - Asset map/cursor quan trọng:
    - `public static final Image e = f.d("/m/hand")`.
    - `public static final Image f = f.d("/m/arrow")`.
    - `private static Image r = f.d("/roomicon")`.
    - `public static Image h = f.d("/elementsicon")`.
  - `pc.a(Graphics, int, int, byte)` render `/roomicon` theo sprite sheet chia 4 frame ngang:
    - `int n4 = r.getWidth() / 4`.
    - Vẽ frame `by2` tại `(n2,n3)`.
  - `pc.b(Graphics, int, int, int)` render `/elementsicon` theo sprite sheet chia 4 frame ngang:
    - `int n5 = h.getWidth() / 4`.
    - Dùng frame `(n4 / 2)`.
  - `pc.d()` lazy-load image id `30099` qua `pa.a().a(30099, false, false)` và tính sheet `32x32`:
    - `v = u.getHeight() / 32`.
    - `w = u.getWidth() / 32`.
  - `pc.g(Graphics, int tileIndex, int x, int y, int anchor)` render tile/icon `32x32` từ sheet id `30099`:
    - `row = abs(tileIndex) / w`.
    - `col = abs(tileIndex) % w`.
    - Source rect `(col << 5, row << 5, 32, 32)`.
  - Các helper này chứng minh nhiều icon/tile Java dùng grid `32x32`; cần giữ assumption 32 px khi port tile map/movement.

- `reference/redecoded/cfr_fresh/hi.java`
  - Không phải world map catalog provider; class này là fight/effect animation.
  - Load `/m/fsw` và `pc.b` (`/crystalblue`) để render hiệu ứng.
  - Có âm thanh `cp("vs")`.
  - State effect:
    - reset: `g = 15`, `h = 0`, `j = false`, `i = false`, `c = 0`.
    - mỗi tick tăng `c`; chỉ update khi `c % 2 != 0`.
    - `g` giảm 3 cho tới 0.
    - trước pha nổ: `f++` tới max `2`, rồi `j = true`.
    - pha nổ: `h += 5`, `f--` tới 0, sau đó `i = true` và đóng sound.
  - Render:
    - Vẽ `/m/fsw` ở 2 phía quanh tâm, một bên mirror transform `2`.
    - Khi `j = false`, vẽ 1 crystal frame ở tâm.
    - Khi `j = true`, vẽ 8 crystal frame tỏa ra theo các hướng `±h`.
  - Kết luận: `/m/fsw` là asset hiệu ứng/fight transition liên quan map/battle visual, không phải background/runtime navigation.

- `reference/redecoded/cfr_fresh/og.java`
  - Là canvas/game loop/input layer liên quan các màn hình.
  - World map input của Java đi qua key/touch/canvas event rồi gọi vào screen hiện tại.
  - Khi port React Native, `PanResponder`/touch handler phải giữ nguyên ý nghĩa: pointer được convert về tọa độ map legacy trước hit-test, không hit-test trực tiếp theo pixel đã scale.

- `reference/redecoded/cfr_fresh/ks.java`
  - Là command/softkey model.
  - World map dùng `ks("Vào Thành", callback)` cho hành động vào map/city.
  - Khi port RN, label/action contract phải giữ `Vào Thành`; disable/enable dựa theo selected entry mở và có runtime target.

Kết luận world map audit:

- World map Java là catalog/selection screen, không phải runtime side-scroll.
- Runtime Hoa Lư hiện tại là map playable đầu tiên của bản port; không suy ra unlock thêm map chỉ từ icon world map.
- Asset key và tọa độ Java là contract phục dựng.
- Nếu thiếu asset thật, fallback renderer được phép dùng primitive nhưng vẫn phải giữ logical key, tọa độ và state.

---

## 2. Server catalog

### 2.1 Endpoint

Endpoint catalog hiện tại:

```http
GET /map/world-catalog
```

Catalog trả về:

- danh sách world map/country entries;
- tọa độ legacy;
- lock/open state;
- optional runtime target.

### 2.2 `RuntimeMapCatalog.AllWorldMaps`

`RuntimeMapCatalog.AllWorldMaps` là source server hiện tại cho world map catalog.

Rule hiện chốt:

- Hoa Lư là entry mở duy nhất trỏ vào side-scroll runtime thật:
  - `runtimeMapId = "Hoa Lu"`
  - `defaultRoomId = 1`
  - `sceneKind = "sideScroll"`
- Các map còn lại tạm là `legacy` hoặc locked.
- Không mở thêm map nếu chưa có runtime data và unlock/progression rule từ Java/data.

---

## 3. Runtime side-scroll architecture

Runtime map playable được tách 3 lớp:

1. **Art layer**
   - Background, midground, foreground, tile/decoration.
   - Chỉ chịu trách nhiệm render.
2. **Navigation layer**
   - Ground, one-way platform, slope, wall, ceiling, portal.
   - Quyết định collision và movement.
3. **Entity layer**
   - Player, NPC, monster, portal marker, trigger/interactable object.
   - Không hard-code entity vào art layer.

Rule authoring:

- Map/room mới cùng topology nên thêm bằng scene config/data/art.
- Nếu cần sửa engine, phải ghi rõ nguồn suy luận Java hoặc tài liệu liên quan.

### 3.1 `GroundSurface` contract

`GroundSurface` đại diện cho bề mặt đứng/chạy.

Thuộc tính cốt lõi:

- `id`
- `x1`, `y1`, `x2`, `y2`
- `kind`
  - `solid`
  - `oneWay`
  - `slope`

Collision rule:

- Player chỉ đứng trên surface hợp lệ khi chân đi từ trên xuống hoặc đang snap vào mặt đất.
- Surface có thể là ngang hoặc slope.
- Không dùng pixel collision nếu chưa có bằng chứng Java yêu cầu.

### 3.2 Runtime movement rules

- Đứng trên mặt đất khi chân chạm `GroundSurface`.
- Chạy trái/phải theo input.
- Tốc độ chạy runtime lấy từ server `MapMovementCalculator`: bám Java `kl.java` với `kl.i = min(9, 4 + level / 10)` (integer division), dùng trực tiếp làm px/frame reference thay vì scale xuống.
- Client phải nhân movement ngang theo display `scale` giống vertical jump impulse vì Java dùng cùng hệ sprite/map tick; nếu chỉ scale Y mà không scale X thì nhân vật chạy chậm và nhảy ngang không qua được platform.
- Khi đang nhảy, nếu người chơi giữ trái/phải thì vẫn cộng chuyển động ngang mỗi frame theo hướng input. Nguồn Java: `km.java` state `5/6` gọi movement với `i * kl.b[4/8]`; không được snap/interpolate về vị trí nhảy ban đầu khi thả phím.
- Nếu không còn surface dưới chân thì chuyển sang falling.
- Khi falling và giao với surface hợp lệ thì đáp xuống.
- Nếu đi lên chạm ceiling thì va đầu và vận tốc Y bị chặn theo navigation rule.
- Không xuyên wall theo navigation layer.
- Camera follow player trong bounds scene.

### 3.3 `CharacterController` status

Status runtime phản ánh movement/collision thực tế:

- `idle`
- `running`
- `falling`
- `jumping`
- trạng thái va chạm/head-hit nếu engine cần expose cho animation/debug.

Không dùng status chỉ để đổi sprite nếu physics chưa tương ứng.

### 3.4 Java movement runtime audit để hướng tới 100%

Nguồn Java đã rà trực tiếp:

- `reference/redecoded/cfr_fresh/kl.java`
  - Actor/player runtime ngoài map.
  - `t = new k(m, n, E, 32)` là hitbox runtime, mặc định rộng `E = 17`, cao `32`.
  - `u = new k(m, n, 26, 32)` là hitbox phụ/attack hoặc interaction side.
  - `I = new k(t.a, t.b - J, t.c, d.q())` là hitbox/render bounds phụ theo chiều cao animation hiện tại.
  - `G = -t.c`, `H = t.d - e.q()` là offset render sprite so với hitbox.
  - `m/n` không phải luôn là physics origin; sau mỗi tick Java sync:
    - state `2/8/3`: `m = t.a - (o - t.c) / 2`, `n = t.b - (p - t.d)`.
    - state khác: `m = t.a`, `n = t.b - (p - t.d)`.
  - Speed ngang: `i = 4 + lh.G / 10`, cap `9`.
  - Gia tốc/rơi tối đa/jump strength: `a = 11 + lh.G / 10`, cap `16`.
  - `s` là vận tốc Y đang dùng trong jump/fall.
  - `w` cộng dồn quãng đường nhảy lên trong state `5`.
  - `x = 50` là thời gian blink/nhấp nháy ban đầu; mỗi tick `K = !K`, hết thì `K = true`.
  - `y = lh.ad` là cờ actor bay/flying/vertical mode đặc biệt:
    - Nếu `y = true`, idle state có dao động sprite nhẹ theo `H` mỗi 10 tick qua `P = {-1, 1}`.
    - Bounds/collision rơi dùng rule khác với actor không bay.
  - Animation objects:
    - `e`: idle.
    - `z`: run.
    - `f`: attack/interact.
    - `A`: jump rising.
    - `h`: falling/landing.
    - `g`: climb/vertical.
  - State:
    - `j = 0`: idle/stand.
    - `j = 1`: running/move trên ground.
    - `j = 2`: climb/vertical movement theo tile đặc biệt.
    - `j = 3`: transition/drop/exit climb animation.
    - `j = 4`: attack/interact animation.
    - `j = 5`: jump rising.
    - `j = 6`: falling/airborne downward.
    - `j = 7`: landing.
    - `j = 8`: climb/enter vertical tile.
  - Direction/move bit `k` dùng bitmask:
    - `1`: lên/climb up.
    - `2`: xuống/climb down.
    - `4`: trái.
    - `8`: phải.
    - `5 = 4|1`: trái + lên/slope lên.
    - `6 = 4|2`: trái + xuống/slope xuống.
    - `9 = 8|1`: phải + lên/slope lên.
    - `10 = 8|2`: phải + xuống/slope xuống.
  - Vector table:
    - `kl.b[4/5/6] = -1`, `kl.b[8/9/10] = 1` cho X.
    - `kl.c[1/5/9] = -1`, `kl.c[2/6/10] = 1` cho Y.
  - `a(lh)` load animation theo character/equipment qua `mb`/`lc`/`nr`, sau đó set default state `a(0, 8)`.
  - `b(lh)` tính lại movement stats từ level `lh.G`.
  - `a(int state, int dir)` gọi `b(dir)` rồi `a(state)`.
  - `b(int dir)` set hướng animation:
    - nếu `dir & 8 != 0` thì sprite direction `2`.
    - nếu `dir & 4 != 0` thì sprite direction `0`.
    - nếu không giữ hướng cũ.
  - `a(int state)` đổi animation và side effects:
    - state `1`: dùng run animation `z`, giữ frame direction cũ, companion/effect `O.d(1)`.
    - state `6`: dùng falling animation `h`, giữ direction, `d.d(0)`, set `s = 1`.
    - state `7`: dùng `h`, `d.d(2)`, reset frame counter `B = 0`.
    - state `5`: dùng jump animation `A`, giữ direction, `d.d(0)`, set `s = a`, reset `w = 0`.
    - state `0`: dùng idle animation `e`, giữ direction.
    - state `2`: dùng climb animation `g`, `d.d(1)`.
    - state `3`: `d.d(0)`, giữ direction.
    - state `8`: dùng climb animation `g`, `d.d(1)`, giữ direction.
    - state `4`: bật collision/action side, dùng attack animation `f`, `d.a(0)`, set hitbox phụ `u` theo hướng:
      - nếu sprite direction `0`: `u.a = t.a - u.c`.
      - ngược lại: `u.a = t.a + t.c`.
      - `u.b = t.b`.
  - Khi đổi state, Java luôn `d.r()` và nếu frame âm thì tick animation ngay `d.i()`.
  - Helper tọa độ:
    - `b(dx,dy)` cộng trực tiếp vào hitbox `t`.
    - `c(x,y)` set hitbox `t`, sync `m/n`.
    - `g(y)` set Y hitbox.
    - `f(x)` set X hitbox.
    - `n()` trả x render centered: `t.a - (o - t.c) / 2`.
    - `o()` trả `t.b + H`, dùng trong kiểm jump/head tile.
  - Khi đổi state qua `a(int state)`, Java đổi animation sprite nhưng logic vẫn dựa vào `j/k/s/t/s/y`.

- `reference/redecoded/cfr_fresh/km.java`
  - Input/controller + state machine movement.
  - Input flags `boolean[5]`:
    - `a[0]`: lên.
    - `a[1]`: xuống.
    - `a[2]`: trái.
    - `a[3]`: phải.
    - `a[4]`: reserved/extra movement flag.
  - Key mapping:
    - `99/150`: lên pressed.
    - `98/156`: xuống pressed.
    - `97/152`: trái pressed.
    - `96/154`: phải pressed.
    - `95/153`: action/attack, nếu state `0/1` thì `j = 4`.
    - `149`: chéo trái + lên.
    - `151`: chéo phải + lên.
  - Jump rising `j = 5`:
    - `t.b -= s`, `w += s`, `s--`.
    - Khi `s == 0` chuyển `j = 6`.
    - Nếu tile phía trên không còn `kh.a(...)`, chuyển falling.
    - Sau xử lý vertical vẫn gọi helper movement ngang `a(kl,k,kf,kh)`, nên đang nhảy vẫn nhận trái/phải.
  - Falling `j = 6`:
    - `t.b += s`, `s += 2`, cap `s <= kl.a`.
    - Kiểm tile dưới chân/slope để landing `j = 7`.
    - Sau xử lý rơi vẫn gọi helper movement ngang `a(kl,k,kf,kh)`, nên falling vẫn có air-control.
  - Landing `j = 7`:
    - Chờ animation landing xong thì về `j = 0`.
    - Vẫn kiểm input trái/phải để chuyển lại running nếu người chơi giữ phím.
  - Ground idle/running `j = 0/1`:
    - Nếu nhấn trái/phải thì set `j = 1`, set `k = 4/8` hoặc slope-combo.
    - Mỗi tick running cộng `t.a += kl.b[k] * i`, `t.b += kl.c[k] * i`.
    - Nếu không có tile ground dưới chân và không ở trạng thái đặc biệt thì chuyển `j = 6`.
  - Helper air-control:
    - Nếu giữ trái:
      - nếu `k` chưa có bit trái thì `b(4)` để đổi hướng.
      - nếu không bị chặn bởi `km.a(...)` và không bị tile side `kh2.n(...)` chặn thì `t.a += i * kl.b[4]`.
    - Nếu giữ phải:
      - nếu `k` chưa có bit phải thì `b(8)` để đổi hướng.
      - nếu không bị chặn bởi `km.b(...)` và không bị tile side `kh2.m(...)` chặn thì `t.a += i * kl.b[8]`.
    - Nếu đang trên không và nhấn lên/xuống/flag phụ, Java còn kiểm tile vertical `kh.b(...)` ở thân/trên chân để có thể chuyển sang state `8` climb/enter vertical path.
    - Không có interpolation về điểm xuất phát jump; X là tích lũy theo từng tick.
  - Wall helper trong `km`:
    - `km.a(k,kl,kf,kh,boolean)` kiểm trái tại row `(b + d/2) / 32`, col `(a - i) / 32`; nếu `kh2.n(tile)` thì chặn, và nếu `bl2` thì snap `x = (col + 1) << 5`.
    - `km.b(k,kl,kf,kh,boolean)` kiểm phải tại row `(b + d/2) / 32`, col `(a + c + i) / 32`; nếu `kh2.m(tile)` thì chặn, và nếu `bl2` thì snap `x = ((col - 1) << 5) - (hitboxWidth - 32)`.
  - Ground direction resolver `a(k,kf,kh,kl)`:
    - Nếu giữ trái và không flying:
      - thiếu ground ở phải + có slope-left `kh.d` ở trái => `j=1,k=5`.
      - thiếu ground ở trái + có slope-right `kh.l` ở phải => `j=1,k=6`.
      - có slope-right ở phải => `j=1,k=6`.
      - có slope-left ở trái => `j=1,k=5`.
      - mặc định `j=1,k=4`.
    - Nếu giữ phải và không flying:
      - thiếu ground ở phải + có slope-left ở trái => `j=1,k=10`.
      - thiếu ground ở trái + có slope-right ở phải => `j=1,k=9`.
      - có slope-right ở phải => `j=1,k=9`.
      - có slope-left ở trái => `j=1,k=10`.
      - mặc định `j=1,k=8`.
    - Nếu flying thì trái/phải chỉ set `k=4/8`.
  - Chi tiết jump rising `j = 5`:
    - Mỗi tick `t.b -= s`, `w += s`, `s--`.
    - `s == 0` thì chuyển `j = 6`.
    - Kiểm tile tại row `(o() + 20) / 32`, col center `(t.a + t.c/2) / 32`; nếu `!kh.a(tile)` thì chuyển falling.
    - Sau vertical vẫn chạy helper air-control.
  - Chi tiết falling `j = 6`:
    - Mỗi tick `t.b += s`, `s += 2`, cap `s <= a`.
    - `var8 = (t.b + t.d - a) / 32` dùng để biết chân đã đi qua tile row mới chưa.
    - Landing slope-right `kh.l(tileFeetRight)`:
      - nếu `k == 4` thì thêm bit xuống `k|2`.
      - nếu `k == 8` thì thêm bit lên `k|1`.
      - chuyển `j=7`, snap Y theo phần dư X bên phải: `(row << 5) - hitboxHeight + (32 - (x + width) % 32)`.
    - Landing slope-left `kh.d(tileFeetLeft)`:
      - nếu `k == 4` thì thêm bit lên `k|1`.
      - nếu `k == 8` thì giữ/thêm bit phải `k|8`.
      - chuyển `j=7`, snap Y theo `x % 32`.
    - Landing ground thường:
      - kiểm chân trái `(x+5)/32` và chân phải `(x+width-5)/32`.
      - chỉ landing nếu row trước khác row hiện tại (`var8 != rowFeet`).
      - snap Y: `(rowFeet - 1 << 5) - (hitboxHeight - 32)`.
    - Nếu chưa landing thì chạy helper air-control.
  - Chi tiết landing `j = 7`:
    - Nếu animation landing xong `d.j()` thì về `j=0`.
    - Vẫn gọi ground direction resolver để nhận input trái/phải.
    - Nếu đang giữ bit trái/phải và wall helper báo có xử lý thì return sớm.
  - Chi tiết idle/running `j = 0/1`:
    - Từ idle `j=0`, nhấn lên:
      - nếu không flying và tile giữa thân `kh.b(...)` thì căn X vào giữa tile và vào state `8,k=1`.
      - nếu không có vertical tile nhưng tile phía trên `kh.a(...)` thì vào jump `j=5` và clear input lên.
      - nếu flying thì vào `j=1,k=1`.
    - Từ idle `j=0`, nhấn xuống:
      - nếu không flying và tile dưới `kh.b(...)` thì căn X/Y vào tile và vào state `8,k=2`.
      - nếu flying và phía dưới không ground hoặc là vertical tile thì vào `j=1,k=2`; nếu chạm ground thì idle.
    - Khi không flying, idle cũng kiểm mất ground dưới chân dựa theo hướng gần nhất `k=4/8`; nếu mất ground thì vào falling `j=6`.
    - Từ running `j=1`, nhấn lên:
      - nếu tile vertical `kh.b(...)` ở thân thì vào `j=8,k=1`, clear trái/phải.
      - nếu không, kiểm tile phía trên `kh.a(...)` để jump `j=5`, clear lên.
      - nếu flying thì đổi hướng `k=1`.
    - Từ running `j=1`, nhấn xuống:
      - nếu flying và phía dưới không ground hoặc vertical tile thì `j=1,k=2`; nếu chạm ground thì idle.
    - Running xử lý trái/phải với slope transitions rất chi tiết:
      - trái từ `k=4` có thể chuyển sang `5/6` khi gặp slope flag `kh.d/kh.l`.
      - trái từ `k=5/6/9/10` có rule thoát slope để về `k=4/5/6`.
      - phải từ `k=8` có thể chuyển sang `9/10` khi gặp slope flag.
      - phải từ `k=9/10/5/6` có rule thoát slope để về `k=8/9/10`.
    - Nếu không còn input nào trong `boolean[5]`, Java gọi `a(0)`.
    - Nếu vẫn `j=1`, mỗi tick chạy `b(kl.b[k] * i, kl.c[k] * i)`.
    - Sau khi chạy ngang:
      - nếu `k=4`, kiểm wall trái ở chân và snap vào tile boundary nếu cần.
      - nếu `k=8`, kiểm wall phải ở chân và snap vào tile boundary nếu cần.
  - Chi tiết climb/vertical states:
    - `j=8` là enter vertical tile; nếu `k=1` và phía trên không còn vertical tile `kh.b`, tính offset `b/c` cho transition.
    - `j=2` là đang climb:
      - giữ lên: `v=true`; nếu hướng cũ xuống thì đổi `k=1`, gọi animation `g.s()`, di chuyển `dy=i*kl.c[1]`; nếu hết vertical tile hoặc gặp ground/tile điều kiện thì vào `j=3`.
      - giữ xuống: `v=true`; nếu hướng cũ lên thì đổi `k=2`, gọi animation `g.t()`, di chuyển `dy=i*kl.c[2]`; nếu hết vertical tile hoặc gặp ground thì snap Y và vào `j=3`.
      - không giữ lên/xuống: `v=false`.
      - giữ trái khi climb: clear lên/xuống, `x -= 16`, vào falling `j=6,k=4`.
      - giữ phải khi climb: clear lên/xuống, `x += 16`, vào falling `j=6,k=8`.
    - `j=3` là transition thoát climb:
      - nếu `k=1`, `t.b += c * kl.c[k]`.
      - khi animation xong, reset `b/c`, snap Y về tile row, `j=0`, clear input, gọi `v.c()`.
      - nếu sau transition không có ground dưới chân thì vào falling `j=6`.
  - Action `j=4`:
    - Press key `95/153` khi `j=0/1` thì `j=4`.
    - Khi animation action xong `d.j()` thì về `j=0`.

- `reference/redecoded/cfr_fresh/kh.java`
  - Camera/map viewport + tile flag helpers.
  - Collision flag helpers:
    - `kh.a(byte)`: `(tile & 8) != 0`.
    - `kh.b(int)`: `(tile & 0x20) != 0`.
    - `kh.c(int)`: `(tile & 0x10) != 0` ground/standable check chính.
    - `kh.d(int)`: `(tile & 0x40) != 0 && (tile & 2) != 0` slope/left-side special.
    - `kh.l(int)`: `(tile & 0x40) != 0 && (tile & 1) != 0` slope/right-side special.
    - `kh.m(int)`: right wall passability: `(tile & 1) == 0 && !kh.o(tile)`.
    - `kh.n(int)`: left wall passability: `(tile & 2) == 0 && !kh.o(tile)`.
  - Bounds clamp:
    - Clamp X/Y actor hoặc hitbox trong map bounds.
    - Nếu actor không phải flying (`!k.y`) và rơi khỏi bounds dưới thì clamp về đáy.

- `reference/redecoded/cfr_fresh/kf.java`
  - Tile layer runtime.
  - Tile size Java cố định `32x32`.
  - `c` là visual tile matrix.
  - `d` là collision/navigation flag matrix.
  - `b(row, col)` trả collision flag `d[row][col]`, out-of-range hoặc thiếu data trả `0`.

Kết luận audit:

- Movement ngoài map của Java client là client-side state machine theo tile grid `32x32`, không phải server authoritative từng frame trong phần decompile hiện có.
- React Native đã có bước port đầu tiên sang Java-compatible movement cho Hoa Lư:
  - `client/src/engine/character/javaMapMovement.ts` giữ state/hitbox kiểu Java `j/k/s/t`, speed `i`, jump cap `a`, tile flag helpers `kh.a/b/c/d/l/m/n`, helper tile probing theo pixel, wall trái/phải, ground support, ceiling/head-hit và landing theo grid `32x32`.
  - `client/src/engine/character/JavaCompatibleCharacterController.tsx` chạy tick movement theo input flags kiểu `km.java`, expose status `idle/running/jumping/falling/landing/action/climbing/climbTransition`, ưu tiên `collisionGrid` cho support/landing/wall/ceiling trước khi fallback surface.
  - `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx` dùng controller mới cho player runtime, truyền `playerLevel` để tính trực tiếp `kl.i/kl.a` và truyền `javaCollisionGrid` vào controller.
  - `client/src/screens/map/hoa-lu/HoaLuScene.ts` sinh collision grid `32x32` từ `GroundSurface`/wall/ceiling hiện có để làm adapter tạm cho Hoa Lư.
- Chưa thể gọi là 100% Java-perfect vì grid Hoa Lư hiện là adapter sinh từ surface remake, chưa phải `kf.d` gốc recover từ Java/data; slope/climb/action mới có khung state/helper, chưa đủ mọi nhánh chi tiết nếu chưa có tile flag tương ứng.
- Để đạt mục tiêu “giống 100%”, cần recover hoặc author `kf.d` thật cho từng room, sau đó thay adapter `GroundSurface` bằng collision tile matrix authoritative.

### 3.5 Movement reconstruction status

- [x] Port speed ngoài map theo Java `kl.java`: `kl.i = 4 + lh.G / 10`, cap `9`.
- [x] Port jump/fall acceleration constant theo Java `kl.java`: `kl.a = 11 + lh.G / 10`, cap `16` vào tài liệu/spec; cần đảm bảo code runtime dùng đúng khi chuyển sang Java-compatible engine.
- [x] Client `CharacterController` giữ air-control khi nhảy theo Java `km.java` state `5/6`: đang ở trên không vẫn nhận trái/phải và khóa interpolation cũ bằng `airControlLocked` để tránh snap/rơi về vị trí cũ sau khi thả phím.
- [x] Client `CharacterController` scale movement ngang theo `scale` cho run/tap/air-control/fall target timing, đồng bộ với jump impulse đã scale.
- [x] Port bước đầu state machine `km.java` cho `j/k/s/t` vào `javaMapMovement.ts`/`JavaCompatibleCharacterController.tsx`; đã dùng cho Hoa Lư thay vì chỉ status `idle/running/jumping/falling`.
- [x] Port collision helper Java tile flag matrix contract `kf.d` và helper `kh.a/b/c/d/l/m/n` vào client movement engine.
- [x] Tích hợp `collisionGrid` vào `JavaCompatibleCharacterController` để ground support, landing, wall side-check và ceiling/head-hit đọc tile grid trước khi fallback `GroundSurface`.
- [x] Chuẩn hóa Hoa Lư navigation thành adapter grid `32x32` sinh từ `GroundSurface`/wall/ceiling hiện có và truyền grid này vào controller runtime.
- [x] Sửa Java-compatible movement sang fixed Java tick `JAVA_MAP_TICK_MS = 40`, bỏ ảnh hưởng `MOVE_TICK_MS = 16`/60fps của controller RN cũ để tránh chạy/rơi nhanh hơn Java.
- [x] Không export `CharacterController` cũ qua `client/src/engine/character/index.ts`; runtime map dùng `JavaCompatibleCharacterController` để tránh dùng lại interpolation/timing cũ.
- [x] Sửa air-control trong `JavaCompatibleCharacterController`: khi đang `jumping/falling`, lệnh trái/phải/tap-to-move chỉ cập nhật input flags/bitmask Java `k=4/8` và không ép state về `running`, để tick `km.java` state `5/6` tiếp tục cộng X theo từng Java tick.
- [ ] Recover/author `kf.d` gốc cho Hoa Lư thay vì adapter từ surface remake.
- [ ] Hoàn thiện toàn bộ slope/diagonal bitmask `5/6/9/10` theo mọi nhánh chi tiết của `km.java` khi có tile slope thật.
- [ ] Hoàn thiện climb/vertical tile state `2/3/8` khi map data có tile flag `0x20` hoặc vertical path thật.
- [ ] Wall/portal transition runtime đầy đủ theo data Java/server.

---

## 4. Hoa Lư runtime map

### 4.1 Files chính

- `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`
- `client/src/screens/map/hoa-lu/HoaLuScene.ts`
- `client/src/screens/map/hoa-lu/HoaLuTileMap.tsx`
- `client/src/screens/map/core/*`
- `client/src/engine/character/*`
  - `javaMapMovement.ts`: Java-compatible runtime constants/state/tile flag helper/state tick.
  - `JavaCompatibleCharacterController.tsx`: React Native controller wrapper dùng state `j/k/s/t`.
- `client/src/engine/MapRenderer.tsx`
- `server/Twelve.Core/Maps/RuntimeMapCatalog.cs`

### 4.2 Authoring rule

Hoa Lư là runtime side-scroll đầu tiên.

Khi thêm map/room cùng loại:

- Tạo scene config/data tương tự Hoa Lư.
- Khai báo art/navigation/entities bằng data.
- Không hard-code NPC/monster/portal vào renderer.
- Logic quan trọng mới phải ghi nguồn suy luận từ Java/client/data.
- Movement/air-control hiện đã ghi nguồn trong `client/src/engine/character/CharacterController.tsx`, `client/src/engine/character/javaMapMovement.ts`, `client/src/engine/character/JavaCompatibleCharacterController.tsx` và `server/Twelve.Core/GameLogic/MapMovementCalculator.cs`.

### 4.3 Scene config direction

Scene config nên tiến tới mô hình:

- `sceneId`
- `runtimeMapId`
- `roomId`
- `kind`
- `bounds`
- `spawnPoints`
- `artLayers`
- `navigation`
  - `groundSurfaces`
  - `walls`
  - `ceilings`
  - `portals`
- `entities`
  - `npcs`
  - `monsters`
  - `interactiveObjects`

### 4.4 Kế hoạch tự author collision map khi không có `kf.d` gốc

Bối cảnh:

- Java client dùng `kf.d` làm collision/navigation matrix thật cho runtime map.
- Hiện chưa recover được `kf.d` gốc của Hoa Lư hoặc các room runtime khác.
- Vì vậy không thể dựa vào “map gốc” để phục dựng collision 1:1.
- Quyết định phục dựng: **tự author/vẽ lại collision map theo format Java-compatible**, không phát minh physics mới.

Nguyên tắc quan trọng:

- Art/background chỉ là lớp nhìn thấy, không quyết định physics.
- Movement phải đọc collision grid `32x32` giống Java.
- Entity/portal/NPC/monster là data riêng, không nhét lẫn vào art layer.
- `GroundSurface` hiện tại chỉ nên xem là fallback/remake helper; source chính tương lai nên là authored Java collision grid.
- Mọi map mới nên có collision data rõ ràng, version control được, debug được.

#### 4.4.1 Source of truth mới cho navigation

Runtime room nên tiến tới mô hình:

```ts
type JavaTileCollisionFlag = number;

type JavaCollisionGrid = {
  tileSize: 32;
  columns: number;
  rows: number;
  flags: JavaTileCollisionFlag[][];
};

type RuntimeRoomNavigation = {
  bounds: {
    width: number;
    height: number;
  };
  collisionGrid: JavaCollisionGrid;
  spawnPoints: SpawnPoint[];
  portals: PortalTrigger[];
};
```

Ý nghĩa:

- `bounds`: kích thước scene runtime.
- `collisionGrid`: ma trận tile flag Java-compatible.
- `spawnPoints`: điểm xuất hiện của player/NPC/monster.
- `portals`: vùng chuyển map/room, để riêng khỏi `kf.d`.

Không nên hard-code collision bằng tọa độ rời rạc trong controller. Controller chỉ được đọc grid và state.

#### 4.4.2 Tile size và quy đổi tọa độ

Java dùng tile cố định:

```text
tileSize = 32
```

Quy đổi:

```text
col = floor(x / 32)
row = floor(y / 32)
```

Ví dụ:

```text
x = 100 => col = 3
y = 260 => row = 8
```

Hitbox player Java mặc định:

```text
width  = 17
height = 32
```

Các điểm sample quan trọng:

```text
feetLeft  = (t.a + 5,       t.b + t.d)
feetRight = (t.a + t.c - 5, t.b + t.d)
head      = (t.a + t.c / 2, t.b)
center    = (t.a + t.c / 2, t.b + t.d / 2)
leftSide  = (t.a - speed,       t.b + t.d / 2)
rightSide = (t.a + t.c + speed, t.b + t.d / 2)
```

Khi tự vẽ grid, phải luôn nhìn map theo lưới `32x32`, không vẽ theo pixel tự do.

#### 4.4.3 Flag contract Java-compatible

Các flag đang biết từ Java:

```ts
export const JAVA_TILE_FLAGS = {
  Empty: 0,

  // kh.a(tile): (tile & 8) != 0
  HeadPass: 0x08,

  // kh.c(tile): (tile & 0x10) != 0
  Ground: 0x10,

  // kh.b(tile): (tile & 0x20) != 0
  Vertical: 0x20,

  // kh.d/l dùng kèm 0x40 và bit 1/2
  Slope: 0x40,

  // kh.l(tile): (tile & 0x40) != 0 && (tile & 1) != 0
  SlopeRight: 0x40 | 0x01,

  // kh.d(tile): (tile & 0x40) != 0 && (tile & 2) != 0
  SlopeLeft: 0x40 | 0x02,

  // wall/passability side bits
  BlockRightRelated: 0x01,
  BlockLeftRelated: 0x02,
} as const;
```

Diễn giải phục dựng:

| Flag | Giá trị | Java helper | Ý nghĩa phục dựng |
|---|---:|---|---|
| Empty | `0` | none | Không có collision |
| HeadPass | `8` | `kh.a` | Tile liên quan kiểm phía trên/jump/head/pass condition |
| Ground | `16` | `kh.c` | Tile đứng được/ground chính |
| Vertical | `32` | `kh.b` | Đường leo/vertical path, dùng state `j=2/8` |
| SlopeRight | `65` | `kh.l` | Slope/right-side special |
| SlopeLeft | `66` | `kh.d` | Slope/left-side special |
| Side bit 1 | `1` | `kh.m` | Bit liên quan chặn/pass bên phải hoặc slope-right |
| Side bit 2 | `2` | `kh.n` | Bit liên quan chặn/pass bên trái hoặc slope-left |

Lưu ý:

- Không đổi ý nghĩa flag tùy tiện sau khi đã author map.
- Nếu phát hiện thêm helper `kh.o` hoặc flag khác từ Java, phải cập nhật bảng này và migration authoring data.
- Portal/trigger không nên encode bằng các flag trên nếu chưa có bằng chứng Java; nên để data riêng.

#### 4.4.4 Text collision map format

Vì chưa có editor, workflow đầu tiên nên dùng text map.

Ví dụ:

```text
........................................
........................................
...............V........................
...............V........................
..........GGGGGGGG......................
............................R...........
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Symbol đề xuất:

| Symbol | Flag | Ý nghĩa |
|---|---:|---|
| `.` | `0` | Empty |
| `G` | `0x10` | Ground |
| `V` | `0x20` | Vertical/climb |
| `R` | `0x40 | 0x01` | SlopeRight |
| `L` | `0x40 | 0x02` | SlopeLeft |
| `H` | `0x08` | HeadPass/jump related |
| `>` | `0x01` | Side bit 1 |
| `<` | `0x02` | Side bit 2 |
| `B` | tùy parser | Block/wall composite nếu cần |

Parser cần validate:

- Mọi row phải cùng độ dài.
- Chỉ nhận symbol đã khai báo.
- `columns * 32` nên khớp hoặc bao phủ `scene.bounds.width`.
- `rows * 32` nên khớp hoặc bao phủ `scene.bounds.height`.
- Nếu bounds không chia hết cho 32, phải ghi rõ padding/crop rule.
- Log lỗi rõ ràng khi symbol lạ hoặc row lệch độ dài.

Ví dụ parser output:

```text
Input row: "G.VRL"
Output:    [16, 0, 32, 65, 66]
```

#### 4.4.5 Files đề xuất

Nên tách rõ engine/parser/data:

```text
client/src/engine/map/javaCollisionMap.ts
```

Chứa:

- constants `JAVA_TILE_FLAGS`;
- type `JavaCollisionGrid`;
- type `JavaCollisionTextSymbol`;
- parser `parseJavaCollisionTextMap(...)`;
- helper `getTileFlagAtPixel(...)`;
- helper `getTileFlagAtCell(...)`;
- validate grid.

```text
client/src/screens/map/hoa-lu/HoaLuCollisionMap.ts
```

Chứa:

- `HOA_LU_COLLISION_TEXT`;
- `HOA_LU_COLLISION_GRID`;
- notes về topology;
- spawn/portal data nếu map-specific.

```text
client/src/screens/map/core/CollisionDebugOverlay.tsx
```

Chứa:

- render grid line;
- render tile color;
- render flag label;
- render player hitbox/sample points nếu truyền vào.

```text
client/src/screens/map/hoa-lu/HoaLuScene.ts
```

Sử dụng:

- import `HOA_LU_COLLISION_GRID`;
- không còn sinh grid chính từ `GroundSurface` khi authored grid đã sẵn sàng;
- giữ adapter cũ như fallback tạm thời nếu cần.

#### 4.4.6 Quy trình tự vẽ Hoa Lư

Không vẽ toàn bộ một lần. Phải chia theo milestone nhỏ để dễ test.

##### Bước 1 — Ground chính

Vẽ nền chính trước:

```text
................................
................................
................................
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Test bắt buộc:

- Spawn player trên ground.
- Player đứng yên không rơi.
- Chạy trái/phải ổn.
- Ra khỏi mép ground thì chuyển falling `j=6`.
- Rơi xuống ground thì landing `j=7`, sau đó idle `j=0`.

##### Bước 2 — Platform

Thêm platform:

```text
................................
..........GGGGGG................
................................
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Test bắt buộc:

- Nhảy lên platform được.
- Rơi từ trên xuống platform thì đáp.
- Đi ngang ra mép platform thì rơi.
- Không snap ngược về điểm nhảy cũ.
- Air-control trái/phải vẫn hoạt động trong state `j=5/6`.

##### Bước 3 — Wall/block

Thêm tường hoặc boundary:

```text
..............B.................
..............B.................
..............B.................
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Test bắt buộc:

- Chạy vào tường không xuyên.
- Player snap đúng cạnh tile, không kẹt.
- Nhảy va cạnh tường không đẩy sai hướng.
- Falling cạnh tường vẫn đáp ground được.

Nếu dùng symbol `B`, cần định nghĩa composite flag rất cẩn thận. Nếu chưa rõ Java wall composite, ưu tiên dùng side bits `>`/`<` hoặc wall adapter riêng và ghi chú remake.

##### Bước 4 — Slope

Slope là phần khó, làm sau ground/platform/wall.

Ví dụ mô tả text:

```text
................R...............
...............RG...............
..............RGG...............
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Test bắt buộc:

- Chạy lên slope không giật.
- Chạy xuống slope không rơi sai.
- Direction bitmask đổi đúng nhóm `5/6/9/10`.
- Landing trên slope snap Y đúng.
- Chuyển từ slope sang ground thường mượt.
- Chuyển từ ground thường sang slope mượt.

Nếu slope bị sai, phải debug bằng `j/k/s`, tile chân trái/phải và công thức snap Y.

##### Bước 5 — Climb/vertical

Thêm vertical path:

```text
...............V................
...............V................
...............V................
GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
```

Test bắt buộc:

- Nhấn lên tại tile `V` vào state `j=8`.
- Sau enter chuyển sang climb `j=2`.
- Giữ lên/xuống di chuyển theo `kl.c[1/2]`.
- Thả phím thì đứng trong climb hoặc giữ state đúng như Java.
- Bấm trái/phải khi climb thì thoát sang falling với offset `±16`.
- Hết vertical path thì vào transition `j=3` hoặc ground/falling đúng rule.

##### Bước 6 — Portal/room transition

Portal để data riêng, không nhét vào collision grid.

Ví dụ:

```ts
export const HOA_LU_PORTALS = [
  {
    id: "hoa-lu-exit",
    x: 928,
    y: 352,
    width: 32,
    height: 64,
    targetMapId: "WorldMap",
    targetRoomId: 0,
    spawnPointId: "from-hoa-lu",
    trigger: "action",
  },
] as const;
```

Test bắt buộc:

- Player overlap portal đúng vùng.
- Nếu Java/data yêu cầu bấm action thì không auto trigger.
- Nếu là portal auto thì chuyển room khi overlap.
- Sau chuyển room spawn đúng point đích.
- Không trigger liên tục khi vừa spawn.

#### 4.4.7 Debug overlay bắt buộc

Tự author collision mà không có debug overlay sẽ rất khó sửa.

Debug overlay cần hiển thị:

- Grid line mỗi `32px`.
- Màu tile theo flag:
  - Empty: trong suốt.
  - Ground: xanh lá.
  - Wall/block: đỏ.
  - Slope: vàng/cam.
  - Vertical: xanh dương.
  - Portal: tím.
  - Spawn: trắng.
- Label flag nhỏ trên tile nếu zoom đủ.
- Hitbox player `t`.
- Hitbox phụ/action `u` khi có.
- Sample points:
  - feet left;
  - feet right;
  - head;
  - center;
  - left side;
  - right side.
- State debug:
  - `j`;
  - `k`;
  - `s`;
  - `t.a/t.b/t.c/t.d`;
  - tile dưới chân trái/phải;
  - tile trước mặt trái/phải;
  - tile trên đầu.

Ví dụ debug HUD:

```text
j=6 falling
k=8 right
s=7
t=(192,288,17,32)
feetL cell=(6,10) flag=16
feetR cell=(6,10) flag=16
head cell=(6,8) flag=0
right cell=(7,9) flag=0
```

Debug overlay phải có thể bật/tắt bằng constant hoặc dev flag, không ảnh hưởng build production.

#### 4.4.8 Unit/integration test cần có

Vì movement core đã tách khỏi React Native, nên nên test parser và movement bằng TypeScript.

Parser tests:

- `"." -> 0`.
- `"G" -> 16`.
- `"V" -> 32`.
- `"R" -> 65`.
- `"L" -> 66`.
- `"G.VRL" -> [16,0,32,65,66]`.
- Row lệch độ dài phải throw.
- Symbol lạ phải throw.

Movement tests tối thiểu:

- Actor đứng trên empty thì chuyển falling `j=6`.
- Actor rơi xuống ground thì landing `j=7`.
- Landing xong về idle `j=0`.
- Giữ trái/phải trên ground thì running `j=1`, `k=4/8`.
- Giữ trái/phải khi jump/fall vẫn đổi X theo air-control.
- Gặp wall trái/phải thì không xuyên và snap boundary.
- Ground kết thúc ở mép thì falling.
- Slope flag `65/66` đổi direction theo nhóm `5/6/9/10`.
- Vertical flag `32` cho vào state `j=8/2`.
- Bounds clamp không cho player ra khỏi scene bất thường.

#### 4.4.9 Milestone triển khai đề xuất

##### Milestone 1 — Collision authoring foundation

Mục tiêu:

- Có parser text map.
- Có constants flag.
- Có Hoa Lư collision text map v1.
- Có validate grid.

Files:

```text
client/src/engine/map/javaCollisionMap.ts
client/src/screens/map/hoa-lu/HoaLuCollisionMap.ts
client/src/screens/map/hoa-lu/HoaLuScene.ts
```

Kết quả:

- Hoa Lư có thể dùng authored grid thay vì adapter sinh từ `GroundSurface`.

##### Milestone 2 — Debug overlay

Mục tiêu:

- Nhìn được grid và hitbox trong game.

Files:

```text
client/src/screens/map/core/CollisionDebugOverlay.tsx
client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx
```

Kết quả:

- Dễ chỉnh từng tile.
- Debug được state `j/k/s/t`.

##### Milestone 3 — Hoa Lư v1 topology

Mục tiêu:

- Vẽ ground chính.
- Vẽ platform chính.
- Vẽ wall/bounds cơ bản.
- Chưa bắt buộc slope/climb nếu chưa cần.

Kết quả:

- Chạy/nhảy/rơi ổn.
- Không xuyên nền/tường.
- Camera follow vẫn đúng bounds.

##### Milestone 4 — Slope/climb

Mục tiêu:

- Thêm slope nếu topology cần.
- Thêm vertical/climb nếu map cần.
- Hoàn thiện nhánh `5/6/9/10` và `2/3/8`.

Kết quả:

- Movement có thể test gần Java hơn ở các tile đặc biệt.

##### Milestone 5 — Portal/runtime room

Mục tiêu:

- Thêm portal data riêng.
- Chuyển room/map.
- Spawn point đích.
- Recovery/debounce portal trigger.

Kết quả:

- Runtime map không chỉ là movement sandbox mà có thể đi qua room/map.

##### Milestone 6 — Documentation + changelog

Mục tiêu:

- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.
- Cập nhật `CHANGELOG.md`.
- Ghi rõ phần nào là Java-sourced, phần nào là authored/remake do thiếu `kf.d` gốc.

#### 4.4.10 Quyết định kỹ thuật chốt

Chốt hướng phát triển:

```text
Không coi GroundSurface là source chính lâu dài.
Source chính cho movement runtime sẽ là authored Java-compatible collision grid.
```

Lý do:

- Gần model Java hơn.
- Kiểm soát từng tile rõ hơn.
- Dễ phục dựng slope/climb/wall theo `km/kh/kf`.
- Dễ debug.
- Dễ mở rộng cho map khác.
- Dễ version control bằng text/JSON.

GroundSurface vẫn có thể tồn tại cho:

- art helper;
- fallback cũ;
- migration tạm;
- map đơn giản chưa author grid.

Nhưng Java-compatible controller nên ưu tiên đọc `collisionGrid`.

---

## 5. Chưa làm cho map bên ngoài/world runtime

Các phần cần làm để map bên ngoài playable:

- DB/server roster:
  - runtime maps;
  - rooms;
  - default spawn;
  - unlock/progression;
  - NPC/monster/portal theo room.
- Portal/room transition:
  - trigger vùng portal;
  - chuyển room/map;
  - spawn point đích;
  - loading/animation nếu có bằng chứng Java.
- NPC:
  - roster data-driven;
  - vị trí;
  - sprite/asset;
  - interaction/dialog/shop/quest theo tài liệu liên quan.
- Monster:
  - spawn point;
  - idle/patrol/chase rule;
  - encounter/battle trigger;
  - [x] respawn rule runtime tạm thời: sau khi kết thúc battle monster, encounter bị ẩn khỏi active roster; quái thường respawn sau 3 phút, boss-like respawn sau 1 ngày.
  - [x] recovery window client: sau khi rời battle về map, nhân vật nhấp nháy/vô địch 3 giây và chặn retrigger monster overlap ngay tại vị trí trả về.
  - [ ] cần bổ sung cờ boss chính thức từ DB/Java data nếu recover được; hiện server tạm phân loại boss theo key/template chứa `boss` hoặc enemy level >= 100.
- Runtime/server sync:
  - player position persistence;
  - map entry/exit;
  - room state.
- Unlock/progression:
  - hiện chỉ Hoa Lư mở;
  - chưa có rule để mở map khác ngoài Hoa Lư.

---

## Nhật ký chỉnh sửa

### 2026-04-29

- Sửa `server/Twelve.Core/GameLogic/MapMovementCalculator.cs`:
  - bỏ scale tốc độ map xuống `1.0..2.8`;
  - dùng trực tiếp công thức Java `kl.i = min(9, 4 + level / 10)` làm tốc độ px/frame reference để nhân vật không chạy quá chậm.
- Sửa `client/src/engine/character/CharacterController.tsx`:
  - giữ air-control trong lúc nhảy theo Java `km.java` state `5/6`;
  - khi giữ/trả trái-phải trên không, khóa `airControlLocked` và ghim `startX/targetX` về vị trí hiện tại để không resume interpolation cũ về điểm đứng ban đầu;
  - scale movement ngang theo display `scale` cho run/tap/air-control/fall target timing để đồng bộ với jump impulse đã scale.
- Lý do: khắc phục lỗi nhân vật di chuyển chậm và nhảy lên rồi bấm trái/phải nhưng không vượt qua được vị trí hiện tại.
- Sửa `server/Twelve.Core/Interfaces/IMapMonsterRosterService.cs` và `server/Twelve.Infrastructure/Repositories/DbMapMonsterRosterService.cs`:
  - thêm `DeactivateEncounterUntil(...)` để ẩn encounter theo thời hạn thay vì xóa vĩnh viễn trong memory roster;
  - `GetActiveRoster(...)` tự đưa monster trở lại khi hết thời gian inactive.
- Sửa `server/Twelve.Core/Battle/BattleSessionContracts.cs` và `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`:
  - lưu `MapId`/`RoomId` vào `BattleSessionState` khi bootstrap battle từ monster map, để claim kết quả biết đúng runtime room chứa encounter.
- Sửa `server/Twelve.Application/Battle/BattleResultService.cs`:
  - khi claim kết quả battle monster, deactivate encounter đã giao chiến dù thắng/thua;
  - quái thường respawn sau 3 phút, boss-like respawn sau 1 ngày.
  - Nguồn suy luận: Java client recover hiện chỉ chứng minh có monster map/encounter; chưa có authoritative old server respawn table, nên đây là remake policy được ghi chú trong code.
- Sửa `client/App.tsx` và `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`:
  - tăng token quay lại map sau mọi kết quả battle monster để kích hoạt recovery;
  - thêm `BATTLE_RECOVERY_MS = 3000`, nhấp nháy nhân vật, dừng input/movement và chặn monster collision retrigger trong thời gian recovery;
  - reset encounter pending/engaged state khi bắt đầu recovery để tránh vừa thoát battle đã bị kéo lại do overlap.

## Nhật ký chỉnh sửa - 2026-04-29 (bổ sung map recovery/render)

- Sửa `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`:
  - đầu hàng/battle return không còn ẩn toàn bộ monster field trong 3 giây recovery; monster vẫn hiển thị, patrol và animate bình thường;
  - recovery chỉ chặn input và collision retrigger bằng `defeatRecoveryActiveRef`, đúng mục tiêu chống kéo lại battle ngay khi player còn overlap;
  - nguồn suy luận: Java server không có đặc tả map-side invincible/recovery trong phần đã rà; đây là remake client policy sau battle return, tách khỏi respawn/deactivate server.

## Nhật ký chỉnh sửa - 2026-04-29 (audit Java movement 100%)

- Rà lại Java decompile:
  - `reference/redecoded/cfr_fresh/kl.java`: actor runtime, speed `i`, gravity cap/jump strength `a`, hitbox `k t`, state `j`, direction bitmask `k`.
  - `reference/redecoded/cfr_fresh/km.java`: controller/input flags, state machine movement, jump/fall air-control, landing, ground running, key mapping.
  - `reference/redecoded/cfr_fresh/kh.java`: tile collision flag helpers và camera/bounds clamp.
  - `reference/redecoded/cfr_fresh/kf.java`: visual/collision tile matrix, tile size `32x32`, `b(row,col)` lấy collision flag.
- Bổ sung mục `3.4 Java movement runtime audit để hướng tới 100%`.
- Chốt lại rằng React Native hiện mới giống Java ở các rule đã port, chưa 100% vì còn thiếu port nguyên `km` state machine và `kf.d` tile collision flags.
- Thêm roadmap cần làm để đạt gần Java-perfect:
  - port state `j/k/s/t`;
  - port tile collision helper `kh.a/b/c/d/l/m/n`;
  - chuẩn hóa Hoa Lư navigation sang grid `32x32` hoặc adapter sinh grid từ scene data;
  - port slope/diagonal/climb state nếu map data có flag tương ứng.

## Nhật ký chỉnh sửa - 2026-04-29 (tổng hợp Java map & move)

- Mở rộng audit world map từ Java:
  - `oh.java`: màn hình chọn map/country, background `/m/m`, asset `/m/arena`, `/m/room`, `/m/lock`, `/m/lock2`, softkey `Vào Thành`.
  - `fz.java`: catalog entry world map/tọa độ/trạng thái mở khóa.
  - `fg.java`: renderer icon/selection/lock trên world map.
  - `pc.java`: cursor `/m/hand`, `/m/arrow`, `/roomicon`, `/elementsicon`, helper render sheet `32x32`.
  - `hi.java`: xác nhận `/m/fsw` là fight/effect visual, không phải navigation/runtime map.
  - `og.java`: canvas/game loop/input layer; pointer/key cần quy đổi về tọa độ legacy trước hit-test.
  - `ks.java`: command/softkey model cho `Vào Thành`.
- Mở rộng audit movement:
  - `kl.java`: bổ sung render offset, animation object theo state, blink/flying flag, state side effects, hitbox phụ `u`, helper tọa độ.
  - `km.java`: bổ sung chi tiết jump/fall landing theo tile, wall helper, ground direction resolver, slope bitmask `5/6/9/10`, climb states `2/3/8`, action state `4`.
- Không sửa code runtime trong task này; chỉ cập nhật tài liệu phục dựng logic Java map/move.

## Nhật ký chỉnh sửa - 2026-04-29 (tích hợp collision grid vào controller)

- Sửa `client/src/engine/character/character.types.ts`:
  - thêm prop `level?: number` để controller nhận level thật của player;
  - thêm prop `collisionGrid?: JavaMapCollisionGrid` để runtime có thể đọc tile matrix Java-compatible.
- Sửa `client/src/engine/character/javaMapMovement.ts`:
  - bổ sung helper tile probing theo pixel/cell từ `kf.d`: `javaTileColAtX`, `javaTileRowAtY`, `javaGetFlagAtPixel`;
  - bổ sung kiểm ground support, landing tile top, wall side-check và ceiling/head-hit dựa trên helper `kh.a/c/m/n`;
  - nguồn suy luận: `reference/redecoded/cfr_fresh/kf.java` và `reference/redecoded/cfr_fresh/kh.java`.
- Sửa `client/src/engine/character/JavaCompatibleCharacterController.tsx`:
  - ưu tiên `collisionGrid` cho ground support, landing, wall side-check và ceiling/head-hit;
  - fallback về `GroundSurface` chỉ khi chưa truyền grid;
  - tính `kl.i/kl.a` từ `level` thật nếu có, chỉ suy từ speed khi thiếu level.
- Sửa `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`:
  - truyền `playerLevel` vào controller;
  - truyền `javaCollisionGrid` Hoa Lư vào controller runtime.
- Lưu ý: grid Hoa Lư hiện vẫn là adapter sinh từ surface/wall/ceiling, chưa phải `kf.d` gốc; cần tiếp tục recover/author grid thật để đạt Java-perfect.

## Nhật ký chỉnh sửa - 2026-04-29 (fixed Java tick movement)

- Sửa `client/src/engine/character/javaMapMovement.ts`:
  - thêm `JAVA_MAP_TICK_MS = 40` và `JAVA_MAP_MAX_STEPS_PER_FRAME = 3`;
  - ghi rõ nguồn suy luận từ `reference/redecoded/cfr_fresh/km.java`: movement Java là step theo game tick, không phải delta-time 16ms/60fps.
- Sửa `client/src/engine/character/JavaCompatibleCharacterController.tsx`:
  - bỏ phụ thuộc `MOVE_TICK_MS = 16` của controller RN cũ;
  - chuyển loop sang rAF scheduler + fixed-step accumulator 40ms;
  - mỗi Java physics step dùng đúng công thức tick Java:
    - chạy ngang: `x += kl.i * direction`;
    - jump rising: `y -= s; s--`;
    - falling: `y += s; s += 2`, cap `s <= kl.a`;
  - cap backlog bằng `JAVA_MAP_MAX_STEPS_PER_FRAME` để tránh teleport khi lag/tab background;
  - landing chuyển từ đếm ms delta-time sang đếm tick Java.
- Sửa `client/src/engine/character/index.ts`:
  - không export `CharacterController` cũ nữa;
  - runtime map chỉ expose `JavaCompatibleCharacterController` để tránh dùng lại movement/interpolation 60fps cũ.
- Kết luận kiểm tra:
  - Hoa Lư không chạy song song `CharacterController` cũ;
  - lỗi chạy/rơi quá nhanh đến từ việc Java-compatible controller dùng nhịp 16ms cũ, khiến công thức Java chạy nhanh khoảng `40 / 16 = 2.5` lần;
  - sau sửa, speed/rơi/nhảy bám Java tick hơn, nhưng vẫn còn phụ thuộc `scale` hiển thị và grid adapter thay vì `kf.d` gốc.

## Nhật ký chỉnh sửa - 2026-04-29 (sửa air-control Java-compatible controller)

- Sửa `client/src/engine/character/JavaCompatibleCharacterController.tsx`:
  - khi player đang ở state airborne `jumping/falling`, `startMove("left"|"right")` cập nhật input flags kiểu `km.java` và gọi `javaSetDirection(...)` để đổi bitmask hướng `k=4/8`;
  - không gọi `javaSetState(...Running...)` khi đang airborne, tránh phá state `j=5/6`;
  - `startTapMove(...)` và move-to-monster chỉ được bật `running` nếu đang `idle/running/landing`, còn khi airborne thì giữ state nhảy/rơi và để air-control cộng X theo tick;
  - nguồn suy luận: `reference/redecoded/cfr_fresh/km.java` state `5/6` luôn gọi helper air-control sau xử lý vertical, không chuyển nhảy/rơi thành running.
- Kiểm tra: `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` — passed.

## Nhật ký chỉnh sửa - 2026-04-29 (plan tự author collision map)

- Bổ sung mục `4.4 Kế hoạch tự author collision map khi không có kf.d gốc`.
- Chốt hướng phát triển:
  - do chưa có collision matrix Java gốc, sẽ tự vẽ collision map theo chuẩn Java-compatible;
  - tile size cố định `32x32`;
  - movement đọc authored grid thay vì tự suy từ art/background;
  - `GroundSurface` chỉ còn là fallback/remake helper, không phải source chính lâu dài.
- Ghi chi tiết:
  - format `JavaCollisionGrid`;
  - flag contract `0/8/16/32/65/66`;
  - text collision map symbols `.`, `G`, `V`, `R`, `L`, `H`, `>`, `<`, `B`;
  - parser/validation rule;
  - files đề xuất `javaCollisionMap.ts`, `HoaLuCollisionMap.ts`, `CollisionDebugOverlay.tsx`;
  - quy trình vẽ Hoa Lư theo từng bước: ground, platform, wall, slope, climb, portal;
  - debug overlay bắt buộc với grid, hitbox, sample points, state `j/k/s/t`;
  - test cases parser và movement;
  - milestone triển khai collision authoring foundation, debug overlay, Hoa Lư topology, slope/climb, portal/runtime room, documentation.

## Nhật ký chỉnh sửa - 2026-04-30 (sửa snap X khi air-control trên không)

- Sửa `client/src/engine/character/JavaCompatibleCharacterController.tsx`:
  - khi player đang airborne (`j=5` jump rising hoặc `j=6` falling), `startMove("left"|"right")` cập nhật ngay `moveDirection`, bit hướng Java `k=4/8`, gọi `applyHorizontalStep(...)` và `commitVisualPosition()` để X dịch ngay trong tick hiện tại;
  - `moveToX(...)` trong lúc airborne không chuyển state về `Running`; thay vào đó giữ nguyên state `j=5/6`, set hướng air-control và commit vị trí hiện tại;
  - `moveToMonster(...)` trong lúc airborne cũng giữ state `j=5/6`, chỉ đổi hướng/tích lũy X giống helper air-control của Java;
  - mục tiêu là loại bỏ hiện tượng nhân vật nhảy lên rồi bấm/trỏ trái-phải nhưng render/target cũ kéo X snap về vị trí đứng ban đầu.
- Nguồn suy luận:
  - `reference/redecoded/cfr_fresh/km.java`: state `5/6` luôn xử lý vertical trước rồi gọi helper movement ngang `a(kl,k,kf,kh)`;
  - Java không có interpolation/target X quay về vị trí bắt đầu nhảy; hitbox `t.a` được cộng dồn theo từng tick.
- Kiểm tra:
  - `node client/node_modules/typescript/bin/tsc -p client/tsconfig.json --noEmit` — passed.

## Nhật ký chỉnh sửa - 2026-04-30 (sửa trigger monster khi nhảy qua)

- Sửa `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`:
  - `hasMonsterCollision(...)` không còn chỉ kiểm overlap trục X; bổ sung `playerFootY`, `monsterGroundY`, `monsterHeight` để kiểm thêm độ cao va chạm;
  - lưu `charFootYRef` từ callback `onMove/onMoveEnd` của `JavaCompatibleCharacterController`, dùng foot Y runtime của hitbox Java (`kl.t.b + kl.t.d`) khi xét encounter;
  - nếu player nhảy cao hơn phần thân monster thì không kích battle dù trục X đang overlap; khi chân player còn trong vùng thân monster gần mặt đất thì vẫn kích encounter bình thường;
  - reset `charFootYRef` về `groundTop` khi respawn/teleport để tránh dùng foot Y cũ.
- Nguồn suy luận:
  - `reference/redecoded/cfr_fresh/kl.java`: actor map dùng runtime hitbox `t = new k(m, n, E, 32)`;
  - trigger map cần bám va chạm hitbox hai chiều thay vì line trigger theo X, để hành vi nhảy qua monster không bị kéo vào battle sai.
- Kiểm tra:
  - `client\node_modules\.bin\tsc.cmd --noEmit -p client\tsconfig.json` — passed.
