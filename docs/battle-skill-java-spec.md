# Battle Skill Java Spec

Nguon goc tai lieu nay la ma Java goc trong:

- `reference/redecoded/decompiled/mt.java`
- `reference/redecoded/decompiled/mq.java`
- `reference/redecoded/decompiled/mp.java`
- `reference/redecoded/decompiled/is..iy`, `jg..jl`, `iz..jf`
- `reference/redecoded/skill_runtime_manifest.csv`

Muc tieu cua file nay la ghi lai phan client-side renderer cua tuyet chieu trong battle. File nay KHONG tu dat ten skill player-facing. Java client chi xac nhan `family code`, asset runtime, timeline animation va mot so board-side visual mutations; name/description cuoi cung van phu thuoc skill catalog tu server.

## Quy uoc chung

- `family code` la truth de chon animation.
- `mt.a(lv, byArray, byArray2, objectArray, byArray3, side)` la ham dispatch animation skill tong.
- `mq` xu ly visual mutation tren board truoc khi `mt` spawn animation.
- `byArray/byArray2` = danh sach o bi tac dong truc tiep tren board.
- `objectArray/byArray3` = danh sach o target dung de dat impact/projectile theo tung family.
- `side` chi ben cast. Trong `mt`, `nArray` la khung actor bi dinh skill; `object2` la khung actor dang cast.
- Client Java KHONG tu tinh damage, radius, so o pha huy. Server gui ket qua, client chi render.

## Shared Runtime

`mp` preload cac asset chung:

- `/skillupdownstat` -> `mp.P`
- `/firerage` -> `mp.N`
- `/firerageext` -> `mp.O`
- `/castingball` -> `mp.h`
- `/barrier` -> `mp.j`
- `/miniexplosionfire` -> `mp.Q/R/S` qua `lp`
- `/magicgate` -> `ln`
- `/zap`, `/ice` la asset ho tro battle scene, khong phai family runtime rieng

## Board-Side Truth Tu `mq.java`

Nhung phan duoi day la phan Java client co xac nhan board visual, nhung target list van do server gui:

| Family | Board-side visual trong `mq` |
| --- | --- |
| `1000` | clear cac o trong `byArray/byArray2` |
| `1001` | danh dau/gan state dac biet len tung o trong `byArray/byArray2`, dong thoi dua vao mang `p[]` |
| `1002` | apply helper image `I` len actor/board object cua ben cast |
| `1003` | khong clear o; chi la cast helper |
| `1005` | khong clear o; chi la cast helper |
| `1006` | clear cac o trong `byArray/byArray2` |
| `1007` | clear cac o trong `byArray/byArray2` |
| `1008` | clear theo tung dot delay giam dan; chia `byArray.length / 8` o moi dot |
| `2000` | clear cac o trong `byArray/byArray2` |
| `2001` | apply helper `c(I)` len actor/board object cua ben cast |
| `2002` | apply helper `d(I)` len actor/board object cua ben cast |
| `2003` | clear cac o trong `byArray/byArray2` |
| `2004` | apply helper `e(I)` roi tat animation hit (`bl2=false`) |
| `2005` | chi cast helper, khong clear o |
| `2006` | clear theo cot `byArray3`; co sort cot truoc, roi clear tu tren xuong hoac duoi len tuy side |
| `2007` | clear cac o trong `byArray/byArray2` |
| `2008` | clear cac o trong `byArray/byArray2` |
| `4000` | clear cac o trong `byArray/byArray2` |
| `4001` | khong clear o |
| `4002` | apply helper `b(I)` len actor/board object cua ben cast |
| `4004` | chi cast helper |
| `4005` | chi cast helper |
| `4006` | clear cac o trong `byArray/byArray2` |
| `4007` | clear cac o trong `byArray/byArray2` |
| `4008` | clear cac o trong `byArray/byArray2` |

## Family Runtime Rules

### Hỏa group `100x`

| Family | Java class | Runtime asset | Runtime rule xac nhan tu Java |
| --- | --- | --- | --- |
| `1000` | `is` | `1000001`, `1000002` | projectile 2-state. State 0 bay tu vi tri lech ngang `180px` va lech doc `180px` vao target; state 1 la impact. `mt` spawn 1 qua vao center victim roi spawn tiep mot qua cho moi target cell trong `objectArray/byArray3` voi delay random `10..24`. |
| `1001` | `it` | `1001001` | effect dung tai tung o target. Neu delay > 0 thi giu state 0, het delay moi chuyen state 1 va tu tat. `mt` spawn tren chinh tung cell trong `byArray/byArray2`, stagger +4 tick moi o. |
| `1002` | none | icon only | khong co `im` class. Client chi goi helper board/object `a(I)` trong `mq` va actor helper `this.a(side, 1, true, true)` trong `mt`. |
| `1003` | `io(elementVariant=0)` | `1003001`, `1003002`, `1003003` + `miniexplosionfire` | effect helper 6 mini explosions, delay 10 tick roi moi goi actor helper `a((side+1)%2, 34, 24, true)`. Java load 3 numeric runtime sheet qua `mp.V/W/n`; `io` dung `lp(0)` cho explosion mau Hỏa. |
| `1004` | `iu` | `1004001` + `miniexplosionfire` | state 1 mo sheet chinh, frame sap ket thuc thi spawn 6 mini explosion doi xung quanh target X, sau do state 0 doi den khi toan bo explosion tat roi tu tat. `mt` dat effect tai center-day victim va goi `c(otherSide, 26, 16)`. |
| `1005` | `iv` | `1005001` + `miniexplosionfire` | projectile cung roi co gia toc (`x` tang dan, `j += x`) cho toi khi cham dich; cham dich thi no 6 mini explosion; neu co delay thi dung state 0 truoc. `mt` phong tu center caster sang center victim. |
| `1006` | `iw` | `1006001`, `1006002` | bien the projectile 2-sheet cua `is`, nhung state impact ve doi xung 2 ben tru khi frame index = 3. `mt` spawn center victim truoc, roi them impact theo tung target cell voi delay random `4..10`, kem actor helper `c(otherSide, 36, 26)`. |
| `1007` | `ix` | `1007001` | effect cot/lua cao, anchor o `x - o/2`, `y - p`; co delay ban dau, het delay moi vao state render chinh, chay xong thi tat. `mt` spawn 1 effect goc o center victim, sau do spawn tiep tren tung target cell sau khi offset theo nua o. |
| `1008` | `iy` | `1008001` | effect doi xung trai-phai, state 0 chay frame strip, state 1 roi doc xuong voi toc do tang `k += 2` den dich `n5 - p`. `mt` chi lay 1 cot target tu `byArray3[0]`, spawn tu hang 7 xuong 0, kem actor helper `c(otherSide, 10, 4)`. |

### Lôi group `200x`

| Family | Java class | Runtime asset | Runtime rule xac nhan tu Java |
| --- | --- | --- | --- |
| `2000` | `jg` | `2000001`, `2000002`, `2000003` | chain/path effect. `jg.a(int[], int[])` nhan day diem; render tung node bang sheet chinh va noi node bang 2 segment sheet phu. `mt` build path qua tung cell target roi noi vao center victim. |
| `2001` | none | icon only | khong co `im` class. Chi goi actor helper `a(side, 0, true, true)` va board/object helper `c(I)`. |
| `2002` | none | icon only | khong co `im` class. Chi goi actor helper `a(side, 2, true, true)` va board/object helper `d(I)`. |
| `2003` | `jh` | `2003001`, `2003002` | cung base voi `is`, delay impact = 4. `mt` spawn 1 projectile vao center victim, kem `c(otherSide, 36, 26)`. |
| `2004` | `ji` | `2004001` | beam/stretch effect. Java ve dau-beam, giua-beam lap lai, cuoi-beam; huong trai/phai theo vi tri caster-victim. `mt` spawn tu mep caster sang mot diem truoc victim `+/-60`, sau do goi actor helper `c(otherSide, 20, 10)`; neu `h.a(side,0).i()` thi goi them `a(side, 1, false, false)`. |
| `2005` | `io(elementVariant=1)` | `2005001`, `2005002`, rotated `1003003` + `miniexplosionfire(rotated)` | cung runtime helper voi `1003`, nhung dung elementVariant 1 va explosion mau Lôi (`lp(1)`). |
| `2006` | `jj` | `2006001` | projectile ngang. State 0 bay den dich; state 1 no/hold; state 2 delay countdown truoc khi state 0 bat dau neu `n6 > 0`. `mt` sap xep clear theo cot, spawn 1 projectile goc tu ngoai le vao center victim roi spawn tiep cac projectile ngang tren tung cot target. |
| `2007` | `jk` | `2007001` | 2 layer chong len nhau: layer chinh va `as v` phu cung frame table nhung region khac. Delay > 0 thi giu state 1, het delay moi vao state 0. `mt` spawn tung effect doc tu tren xuong tren moi target cell, sau do them 1 effect o center victim, kem actor helper `c(otherSide, n3, n3-6)`. |
| `2008` | `jl` | `2008001` | particle burst. `jl` tao san 36 sprite con; state 0 kich hoat tat ca particle, state 1 dem 5 tick, state 2 bay tung particle den diem random quanh dich, state 3 la anim ket thuc tung particle. `mt` dat toa do ban dau cua moi particle o tung cell trong `byArray/byArray2`, `s = byArray.length`, roi goi actor helper `a(otherSide, 32, 22, false)`. |

### Thủy group `400x`

| Family | Java class | Runtime asset | Runtime rule xac nhan tu Java |
| --- | --- | --- | --- |
| `4000` | `iz` | `4000001`, `4000002` | bien the `is` cua Thủy, impact duration ngan hon (`w=3`) va state 1 reset tick = 3. `mt` giong `1000`: volley center victim + volley tren tung target cell. |
| `4001` | `ja` | `4001001` | effect neo tai center-day victim. Chi co 1 state anim den cuoi roi tat; `mt` goi them `b.c(10)` tren actor helper. |
| `4002` | `jb` | `4002001` | projectile ngang tu caster den victim, cham dich roi chay state explosion va tat. `mt` spawn tu center caster sang center victim, goi actor helper `a(otherSide, 1, false, false)`. |
| `4003` | `jc` | `4003001` | effect static o center victim, huong trai/phai theo vi tri tren man hinh, anim mot lan roi tat. `mt` goi them `b.a(otherSide, 10)` va `c(otherSide, 15, 16)`. |
| `4004` | `io(elementVariant=2)` | `4004001`, `4004002`, rotated `1003003` + `miniexplosionfire(rotated)` | cung helper nhu `1003/2005`, dung elementVariant 2 va explosion mau Thủy (`lp(2)`). |
| `4005` | `jd` | `4005001` + `miniexplosionfire(rotated)` | bien the `iv` cua Thủy, dung region/frame khac va `lp(2)` cho explosion. `mt` phong tu caster sang victim, kem `c(otherSide, 26, 16)`. |
| `4006` | `je` | `4006001`, `4006002` | bien the `iz`, giong `4000` nhung asset khac. `mt` spawn center victim truoc, roi them volley tren tung target cell. |
| `4007` | `jf` | `4007001` | bien the `ix` cua Thủy, frame/region khac. `mt` spawn tren tung target cell voi delay random `10..24`, roi goi actor helper `c(otherSide, n3, n3-6)`. |
| `4008` | none | icon only, runtime dung lai `4000/4000001/4000002` | Java khong tao class rieng. `mt` route `4008 -> new iz()` va `mp.a(4008)` dung lai asset `4000`. Dispatch giong `4000`, nhung target delay random `10..29` thay vi `10..24`. |

## Base Animation Classes

## Start / End Anchors Tu Java

Day la phan quan trong nhat de port renderer:

| Family / class | Start anchor | End anchor | Ghi chu |
| --- | --- | --- | --- |
| `1000 is`, `2003 jh`, `4000 iz`, `4008 iz(reuse)` | `targetX -/+ 180`, `targetY - 180` | tam victim hoac tam tung target cell | projectile khong bat dau tu caster |
| `1006 iw`, `4006 je` | nhu nhom tren | nhu nhom tren | impact ve doi xung 2 ben |
| `1001 it` | dung ngay tai tung cell `byArray/byArray2` | dung ngay tai tung cell | chi co delay roi no tai cho |
| `1004 iu` | mo tu mot ben cua victim, vi tri quyet dinh boi nua trai/phai man hinh | truot vao quanh truc X cua victim | frame cuoi spawn 6 mini explosion |
| `1005 iv`, `4005 jd` | gan center caster, co offset ngang ban dau (`-60`) | gan victim (`targetX` / `targetX - 50`) | duong bay cung roi, van toc tang dan |
| `1007 ix`, `4007 jf` | neo thang tai chan target (`x - o/2`, `y - p`) | khong co dich den moi | day la cot dung tai cho, khong phai projectile |
| `1008 iy` | neo o cot target dau tien | dich Y len `0 - p` | effect doi xung roi chay doc len tren, khong roi xuong |
| `2000 jg` | diem dau tien trong path target list | diem cuoi la center victim | chain noi cac diem, khong lay caster lam node |
| `2004 ji` | mep caster | diem truoc victim khoang `60px` | beam stretch co dau-than-duoi |
| `2006 jj` | tu ngoai le man hinh / le trai-phai cot target | center victim va tung cot target | quet ngang theo cot da sort |
| `2007 jk` | neo dung tren tung target cell | khong co dich den moi | 2 layer chong len nhau |
| `2008 jl` | tung particle bat dau o tung cell target | diem random quanh victim | sau 5 tick moi bung tiep |
| `4001 ja`, `4003 jc` | neo ngay tai victim | khong co dich den moi | anim tai cho |
| `4002 jb` | center caster co offset ngang `26px` | center victim | projectile ngang don |
| `1003/2005/4004 io` | 6 mini explosion quanh truc X cua victim | khong di chuyen xa | sau 10 tick moi trigger actor helper |

### `is` / `iz` / `jh` / `iw` / `je`

- State 0: bay tu start -> end voi step `at.a(..., 10)`.
- State 1: impact frame sequence, sau do `r=false`.
- `u` la delay truoc khi cho effect ve.
- `iw` override render state impact de ve doi xung 2 ben.

### `it`

- Dung tai cho, delay countdown roi moi vao anim chinh.
- Het state 1 la tat.

### `iu`

- Sheet chinh mo ra ben trai/phai tuy vi tri target nam nua trai hay nua phai man hinh.
- Khi frame den `length-5` thi spawn 6 `lp(0)` quanh cot target.

### `iv` / `jd`

- Projectile roi cong xuong dich bang cach tang van toc `x`.
- Cham dich -> spawn 6 mini explosion.

### `ix` / `jf`

- Effect cot cao neo o chan target.
- Het delay -> state render.
- Khong di chuyen ngang; chi chay frame strip.

### `iy`

- State 0 ve effect doi xung.
- State 1 roi doc xuong toi diem dich.

### `jg`

- Co mang diem `s/t`; Java tu noi cac diem bang 2 sprite segment.
- Toggle `y` 0/1 moi 2 tick de lam chop nhap.

### `ji`

- Ve beam stretch bang region arrays, khong phai sheet strip thuan.
- `t[]` la moc X theo frame de quyet dinh do dai beam.

### `jj`

- State 2 = delay hold.
- State 0 = bay ngang.
- State 1 = no theo strip.

### `jk`

- Hai actor `as` chay song song.
- Frame chinh thay doi width theo frame phu de giu dung region.

### `jl`

- Mua manh/chop 36 particle, chi dung `s` particle dau.
- Particle bay den diem random trong hinh chu nhat quanh dich.

### `io`

- Day la helper chung cho `1003/2005/4004`.
- Tao 6 `lp(elementVariant)` theo cot doi xung quanh mot truc X.
- Sau 10 tick se goi actor helper tren ben doi phuong.

### `lp`

- Dung `/miniexplosionfire`.
- `0` = ban goc.
- `1` = byte-rotate `-111`.
- `2` = byte-rotate `138`.

## Nhung Gi Client Khong Duoc Tu Suy

- Ten skill player-facing cuoi cung.
- Description text cuoi cung.
- Damage that.
- So o bi pha trong mot lan cast.
- Cac mang target `byArray/byArray2/objectArray/byArray3`.
- Logic unlock / level / mana cost / power cost.

Tat ca cac muc tren phai den tu server skill catalog hoac packet battle runtime. Client Java goc chi render theo du lieu da nhan.
