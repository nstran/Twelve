# Skill System Reconstruction

Tai lieu khoi phuc he thong skill tu Java client cu.
File nay la source of truth duy nhat trong repo cho phan skill reconstruction.

## LUU Y TO NHAT: KHONG CO SOURCE SERVER JAVA GOC

Repo hien tai KHONG CO source code server Java goc cua battle system.

Dieu da chot:

- Chung ta chi co Java client decompiled (`mq`, `mt`, `mx`, `mp`, `de`, `gz`, ...).
- Java client cho thay:
  - runtime render skill
  - board mutation sau khi da co `byArray/byArray2/objectArray/byArray3`
  - metadata skill/tree/UI
- Java client KHONG cho thay:
  - server truth sinh `byArray/byArray2/objectArray/byArray3`
  - battle packet resolution that cho tung family
  - damage/he so thuc chien theo level
  - level scaling runtime packet that

He qua bat buoc:

- Moi logic packet/server viet bang C# hien nay deu la `RECONSTRUCTION`, KHONG duoc xem la Java-canon 1:1.
- Bat ky rule nao trong C# khong co bang chung truc tiep tu Java client hoac packet capture that deu phai:
  - duoc note ro trong file nay
  - san sang sua hoac xoa ngay khi doi chieu thay sai
- Khong duoc "giu tam cho chay duoc" neu no lam lech spec Java lau dai.

Quy tac lam viec bat buoc tu bay gio:

1. `Java-confirmed` thi giu.
2. `Reconstructed` thi phai note ro la reconstructed.
3. Neu reconstruction bi doi chieu thay sai, uu tien xoa/sua ve dung spec thay vi bao ve code cu.
4. Moi cuoc tro chuyen moi deu phai doc muc nay truoc khi sua skill server/client runtime.

Khong suy damage, mana, unlock, level tree, cooldown tu client.
Khong dat ten skill player-facing neu Java khong cho thay.
Khong dung ghi chu fake/local fixture de suy ra battle behavior.

Neu can doi chieu tai lieu goc decompiled, xem:

- [reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md)

## Nguon Java Da Doc Lai

| File | Class | Vai tro |
| --- | --- | --- |
| [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java) | `mp` | battle scene + skill asset manager |
| [pa.java](/d:/Twelve/reference/redecoded/decompiled/pa.java) | `pa` | offline asset loader cho `mp.b(int)` |
| [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java) | `de` | skill tree UI panel |
| [gg.java](/d:/Twelve/reference/redecoded/decompiled/gg.java) | `gg` | skill detail / upgrade widget |
| [gu.java](/d:/Twelve/reference/redecoded/decompiled/gu.java) | `gu` | stat up/down indicator |
| [mq.java](/d:/Twelve/reference/redecoded/decompiled/mq.java) | `mq` | board-side mutation / helper dispatch |
| [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java) | `mt` | projectile path + actor-side runtime dispatch |
| [mx.java](/d:/Twelve/reference/redecoded/decompiled/mx.java) | `mx` | victim hit emitter / hit feedback |
| [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java) | `ln` | magic-gate effect loader |
| [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java) | `lp` | mini explosion fire effect loader |
| [io.java](/d:/Twelve/reference/redecoded/decompiled/io.java) | `io` | shared elemental helper runtime |
| [in.java](/d:/Twelve/reference/redecoded/decompiled/in.java) | `in` | actor overlay dung sheet `1003003` |
| [is.java](/d:/Twelve/reference/redecoded/decompiled/is.java) | `is` | family `1000` |
| [it.java](/d:/Twelve/reference/redecoded/decompiled/it.java) | `it` | family `1001` |
| [iu.java](/d:/Twelve/reference/redecoded/decompiled/iu.java) | `iu` | family `1004` |
| [iv.java](/d:/Twelve/reference/redecoded/decompiled/iv.java) | `iv` | family `1005` |
| [iw.java](/d:/Twelve/reference/redecoded/decompiled/iw.java) | `iw` | family `1006` |
| [ix.java](/d:/Twelve/reference/redecoded/decompiled/ix.java) | `ix` | family `1007` |
| [iy.java](/d:/Twelve/reference/redecoded/decompiled/iy.java) | `iy` | family `1008` |
| [jg.java](/d:/Twelve/reference/redecoded/decompiled/jg.java) | `jg` | family `2000` |
| [jh.java](/d:/Twelve/reference/redecoded/decompiled/jh.java) | `jh` | family `2003` |
| [ji.java](/d:/Twelve/reference/redecoded/decompiled/ji.java) | `ji` | family `2004` |
| [jj.java](/d:/Twelve/reference/redecoded/decompiled/jj.java) | `jj` | family `2006` |
| [jk.java](/d:/Twelve/reference/redecoded/decompiled/jk.java) | `jk` | family `2007` |
| [jl.java](/d:/Twelve/reference/redecoded/decompiled/jl.java) | `jl` | family `2008` |
| [iz.java](/d:/Twelve/reference/redecoded/decompiled/iz.java) | `iz` | family `4000` |
| [ja.java](/d:/Twelve/reference/redecoded/decompiled/ja.java) | `ja` | family `4001` |
| [jb.java](/d:/Twelve/reference/redecoded/decompiled/jb.java) | `jb` | family `4002` |
| [jc.java](/d:/Twelve/reference/redecoded/decompiled/jc.java) | `jc` | family `4003` |
| [jd.java](/d:/Twelve/reference/redecoded/decompiled/jd.java) | `jd` | family `4005` |
| [je.java](/d:/Twelve/reference/redecoded/decompiled/je.java) | `je` | family `4006` |
| [jf.java](/d:/Twelve/reference/redecoded/decompiled/jf.java) | `jf` | family `4007` |

## Working Folders

- [client/assets/skill](/d:/Twelve/client/assets/skill)
- [reference/review_assets/skill_system_organized](/d:/Twelve/reference/review_assets/skill_system_organized)
- [reference/redecoded/skill_runtime_manifest.csv](/d:/Twelve/reference/redecoded/skill_runtime_manifest.csv)

## Core Architecture

Java cu tach he skill thanh 3 lop rieng, khong phai mot pool asset phang:

1. `00_skill_tree_ui_confirmed` - skill tree board chrome, icon khung UI
2. `01_battle_skill_shared_confirmed` - named shared battle effects
3. `02_elemental_runtime_families` - numeric runtime families load qua `mp.a(code)`

Battle runtime lai tach them thanh 3 lop xu ly:

1. `mq.java` xu ly board-side mutation, mark, clear, helper tren ban co
2. `mt.java` spawn projectile / helper / path / aura cua family runtime
3. `mx.java` phat hit emitter o actor bi trung don

Neu chi render projectile tren board ma khong co impact vao actor, hoac nguoc lai, thi chua giong Java.

## Skill Asset ID Schema

Moi runtime skill asset load qua `pa.a(id, false)` duoi duong dan `/offline/<id>.png`,
trong do `id` la so 7 chu so:

```text
<FFFF><NNN>
  |    \- frame index (001, 002, 003, ...)
  \------ family code (1000, 1001, 2005, 4007, ...)
```

Vi du tu `mp.java`:

| ID | Family | Frame | Source |
| --- | --- | --- | --- |
| `1000001` | `1000` | `001` | [mp.java:149](/d:/Twelve/reference/redecoded/decompiled/mp.java:149) |
| `1000002` | `1000` | `002` | [mp.java:152](/d:/Twelve/reference/redecoded/decompiled/mp.java:152) |
| `1003001` | `1003` | `001` | [mp.java:169](/d:/Twelve/reference/redecoded/decompiled/mp.java:169) |
| `2005002` | `2005` | `002` | [mp.java:258](/d:/Twelve/reference/redecoded/decompiled/mp.java:258) |
| `4007001` | `4007` | `001` | [mp.java:350](/d:/Twelve/reference/redecoded/decompiled/mp.java:350) |

Skill runtime asset IDs luon la 7 chu so. File `/offline/` co digit length khac thuoc he thong khac.

## Shared Runtime Da Xac Nhan Tu Java

`mp.java` preload cac named asset sau:

| String path | Load site | Java field |
| --- | --- | --- |
| `/skillupdownstat` | [mp.java:379](/d:/Twelve/reference/redecoded/decompiled/mp.java:379) | `mp.P` |
| `/firerage` | [mp.java:380](/d:/Twelve/reference/redecoded/decompiled/mp.java:380) | `mp.N` |
| `/firerageext` | [mp.java:381](/d:/Twelve/reference/redecoded/decompiled/mp.java:381) | `mp.O` |
| `/castingball` | [mp.java:382](/d:/Twelve/reference/redecoded/decompiled/mp.java:382) | `mp.h` |
| `/barrier` | [mp.java:383](/d:/Twelve/reference/redecoded/decompiled/mp.java:383) | `mp.j` |
| `/chess0`..`/chess8` | [mp.java:390](/d:/Twelve/reference/redecoded/decompiled/mp.java:390) | `mp.c[0..8]` |
| `/chesscrystal` | [mp.java:393](/d:/Twelve/reference/redecoded/decompiled/mp.java:393) | `mp.b` |
| `/star` | [mp.java:394](/d:/Twelve/reference/redecoded/decompiled/mp.java:394) | `mp.a` |
| `/bloodthrowaround` | [mp.java:395](/d:/Twelve/reference/redecoded/decompiled/mp.java:395) | `mp.f` |
| `/miniexplosionfire` | [lp.java:29](/d:/Twelve/reference/redecoded/decompiled/lp.java:29) | `mp.Q` |
| `/magicgate` | [ln.java:44](/d:/Twelve/reference/redecoded/decompiled/ln.java:44) | `ln` owned |

Ghi chu quan trong:

- `bloodthrowaround` la shared hit effect cua actor/battle renderer (`mx.java`), khong phai runtime sheet rieng cua tung family.
- `1003/2005/4004` dung them `1003003` da byte-rotate qua `mp.c()/d()/e()`:
  - base `mp.n`
  - rotate `-98` -> `mp.z`
  - rotate `-180` -> `mp.I`
- `/miniexplosionfire` khong load qua `mp.a(code)` ma load trong `lp.java`:
  - variant `0` = goc
  - variant `1` = rotate `-111`
  - variant `2` = rotate `138`
- `4008` khong co class rieng; `mp.a(4008)` reuse `4000001/4000002`.

## Runtime Family Loader Map Tu `mp.a(int code)`

| Family | Java class | Runtime PNG |
| --- | --- | --- |
| `1000` | `is` | `1000001`, `1000002` |
| `1001` | `it` | `1001001` |
| `1002` | none | icon only |
| `1003` | `io(0)` | `1003001`, `1003002`, `1003003` |
| `1004` | `iu` | `1004001` |
| `1005` | `iv` | `1005001` |
| `1006` | `iw` | `1006001`, `1006002` |
| `1007` | `ix` | `1007001` |
| `1008` | `iy` | `1008001` |
| `2000` | `jg` | `2000001`, `2000002`, `2000003` |
| `2001` | none | icon only |
| `2002` | none | icon only |
| `2003` | `jh` | `2003001`, `2003002` |
| `2004` | `ji` | `2004001` |
| `2005` | `io(1)` | `2005001`, `2005002`, rotated `1003003` |
| `2006` | `jj` | `2006001` |
| `2007` | `jk` | `2007001` |
| `2008` | `jl` | `2008001` |
| `4000` | `iz` | `4000001`, `4000002` |
| `4001` | `ja` | `4001001` |
| `4002` | `jb` | `4002001` |
| `4003` | `jc` | `4003001` |
| `4004` | `io(2)` | `4004001`, `4004002`, rotated `1003003` |
| `4005` | `jd` | `4005001` |
| `4006` | `je` | `4006001`, `4006002` |
| `4007` | `jf` | `4007001` |
| `4008` | `iz` reuse | `4000001`, `4000002` |

## Skill Tree UI Chrome

Loaded qua `/info/<name>` string paths:

| String path | Load site |
| --- | --- |
| `/info/skilltree` | [de.java:65](/d:/Twelve/reference/redecoded/decompiled/de.java:65) |
| `/info/increase` | [de.java:28](/d:/Twelve/reference/redecoded/decompiled/de.java:28), [gu.java:18](/d:/Twelve/reference/redecoded/decompiled/gu.java:18) |
| `/info/decrease` | [de.java:29](/d:/Twelve/reference/redecoded/decompiled/de.java:29), [gu.java:19](/d:/Twelve/reference/redecoded/decompiled/gu.java:19) |

## Asset Folder Structure

```text
client/assets/skill/
|
+-- 00_skill_tree_ui_confirmed/
|   \-- skill_tree_board/
|       +-- skilltree.png
|       +-- increase.png
|       \-- decrease.png
|
+-- 01_battle_skill_shared_confirmed/
|   +-- named_effects/
|   |   +-- barrier.png
|   |   +-- castingball.png
|   |   +-- firerage.png
|   |   +-- firerageext.png
|   |   +-- magicgate.png
|   |   +-- miniexplosionfire.png
|   |   \-- skillupdownstat.png
|   \-- battle_scene_support/
|       +-- ice.png
|       \-- zap.png
|
+-- 02_elemental_runtime_families/
|   +-- group_100x/
|   +-- group_200x/
|   \-- group_400x/
|
\-- index.csv
```

## Battle Runtime Quy Uoc Da Xac Nhan

- `mt.a(lv, byArray, byArray2, objectArray, byArray3, side)` la dispatcher battle skill runtime.
- `mq.a(side, family, byArray, byArray2, objectArray, byArray3)` la board-side mutation / helper dispatch truoc khi `mt` render.
- `byArray/byArray2` la danh sach o board bi tac dong truc tiep.
- `objectArray/byArray3` la danh sach toa do board dung cho projectile / impact per-cell trong nhieu family.
- `side = 0/1`; trong `mt`, `nArray` la actor victim, `object2` la actor caster.
- Client Java chi render theo cac mang tren; khong tu tinh ket qua battle.

## Server C# Reconstruction Hien Tai

Muc nay ghi lai logic da dua vao `server/Twelve.Application/Battle/BattleSkillCastPacketService.cs`
de sau nay con tiep tuc doi chieu va xoa/sua neu thay lech Java.

Trang thai:

- Day la `C# reconstruction layer`, KHONG phai port 1:1 tu server Java goc.
- Muc tieu la dua client vao model `packet-driven` thay vi fake tu client cursor.
- Neu packet capture that hoac source server Java xuat hien, muc nay phai duoc doi chieu lai tung dong.

Rule dang co trong C#:

- `1001 / Hoa Kiem Thuat`
  - board mutation = `Mark`
  - `stateId = 10`
  - target list hien tai = `tat ca o cung category voi o da chon`, sap theo khoang cach den o chon
  - C# hien tai da bo qua cac o da la `state 10`, khong mark lai o da thanh `chess8`
  - `USER-CONFIRMED lv12 truth`:
    - lv12 bien `8..10` o thanh kiem do
    - lv12 co `56%` ti le di tiep luot
  - `USER-DERIVED reconstruction formula` hien dang code trong C#:
    - so o mark:
      - lv1 = `3..5`
      - lv12 = `8..10`
      - noi suy bang 5 moc tang deu trong 12 level:
        - `minMarks = 3 + floor((level - 1) * 5 / 11)`
        - `maxMarks = 5 + floor((level - 1) * 5 / 11)`
      - moi lan cast roll ngau nhien trong range tren, roi lay `N` o gan o chon nhat
    - bonus luot:
      - lv1 = `12%`
      - lv12 = `56%`
      - noi suy tuyen tinh:
        - `extraTurn% = 12 + (level - 1) * 4`
      - moi lan cast roll true/false theo % nay
  - duration packet = `10 tick + 4 tick * index cuoi + trailing anim`
  - client runtime note:
    - moi o `state 10` phai chay strip `1001001.png` du 5 frame theo thu tu trai -> phai
    - `chess8` chi duoc hien dong thoi voi frame cuoi cua strip, khong duoc lo ra tu frame dau
    - sau khi frame cuoi ket thuc moi de lai `chess8` tren o
    - khong render `state 10` bang crystal overlay generic
    - sau khi dot mark cuoi cung ket thuc, neu cac o `state 10` tao thanh line hop le thi battle phai auto resolve chain match ngay, khong doi den swap sau
  - danh dau: `RECONSTRUCTED`
  - phan Java-confirmed chi gom:
    - `mq` mark `a(row,col,10)`
    - `mt` start `10 tick`, moi o `+4 tick`
  - phan chua duoc Java-confirmed:
    - cach server chon danh sach o nao de mark
    - cong thuc di tiep luot day du cho moi level trong Java goc
  - ghi chu rat quan trong:
    - cong thuc tren la `USER-DERIVED`, dung de phuc dung battle server khi khong con server Java
    - neu sau nay tim thay dump skill data/payload that khac voi cong thuc nay, phai doi lai theo data that

- `1000 / Hoa cau thuat`
  - board mutation = `Clear`
  - C# hien tai dung reconstruction theo `vung 2x2`
  - so vung dang scale theo `skillLevel`
  - count `1..2` o band level thap, `2..3` o band level cao
  - anchor vung 2x2 dang duoc chon theo reconstruction C#, khong phai Java-confirmed
  - danh dau: `RECONSTRUCTED`
  - phan Java-confirmed chi gom:
    - clear theo `byArray/byArray2`
    - projectile actor hit + volley per-cell theo `objectArray/byArray3`
  - phan chua duoc Java-confirmed:
    - cong thuc server chon chinh xac cac vung 2x2

- `1008 / Hoa phung lieu nguyen`
  - board mutation = clear full cot target
  - actor-side runtime lay cot tu `byArray3[0]`, spawn effect tu row `7` cua cot do
  - danh dau:
    - `column shape` = `JAVA-CONFIRMED`
    - packet target list day du = `RECONSTRUCTED`

- `2006`
  - board mutation = clear theo cot, sort cot tang dan truoc khi dispatch
  - actor-side runtime = side sweep + them 3 lane ngang
  - danh dau:
    - `column sweep shape` = `JAVA-CONFIRMED`
    - packet target list day du = `RECONSTRUCTED`

Sandbox/runtime note:

- Battle sandbox client hien tai van gui `skillLevel` fallback de test packet khi chua noi skill tree/server catalog that.
- Fallback nay chi duoc xem la gia lap de test reconstruction.
- Khi noi xong skill tree / player skill data that:
  - phai thay fallback bang `skill level` that
  - doi chieu lai cac family co scaling nhu `1000`
- User da nhac lai rang co `cong thuc / ty le di tiep luot` rieng o he skill.
  - Hien chua co bang chung Java/client packet nao du de code canon phan nay.
  - Tam thoi KHONG duoc tu y bịa formula.
  - Khi co mo ta level, packet capture, hoac user cung cap cong thuc, phai bo sung lai cho dung.
- Hien tai battle runtime CHI CO them luot theo board rule:
  - line `4+` trong `resolveJavaBoardStep()` => `bonusTurnCandidate = true`
  - day KHONG duoc xem la cong thuc them luot rieng cua skill
  - neu sau nay tim thay cong thuc skill-specific, phai tach rieng khoi board-rule bonus turn
- `UPDATE UI FLOW`:
  - battle Java feel khong dung popup chu kieu `Ban/Quai duoc them luot`
  - khi co extra turn, board phai hien overlay giua ban co theo kieu `Con 1 luot`, `Con 2 luot`, ...
  - chain combo lien tiep phai hien badge giua/duoi ban co theo kieu `x2`, `x3`, ...
  - vi vay client RN phai uu tien `board overlay` cho extra turn va combo chain, khong dung gain-popup text de thong bao 2 viec nay
- Chung cu da chot cho blocker nay:
  - `it.java` chi la runtime sheet/tick cua `1001`, KHONG chua cong thuc them luot
  - `ib.java` chi render text level `lx.e` va mana cost `lx.d / 9`
  - `ky.q(ku)` cho thay `lx.e`/`lx.d` duoc nap tu data packet/resource ngoai code, KHONG hardcode trong repo
  - vi vay cong thuc them luot rieng cua skill neu co hien nam o skill data/server truth dang thieu
- `UPDATE`: user da xac nhan them 1 moc that cho `1001`:
  - `lv12 = bien 8..10 o thanh kiem do`
  - `lv12 = 56% ti le di tiep luot`
  - tu moc nay, C# da noi suy nguoc ra cong thuc reconstruction cho `lv1..11`
- Code hien tai da mo san packet hook cho truong hop server truth tra ve ket qua nay:
  - `BattleSkillRuntimePacket.grantsExtraTurn`
  - `BattleSkillRuntimePacket.extraTurnChancePercent`
  - client se giu luot neu packet server tra `grantsExtraTurn = true`
  - NHUNG server reconstruction hien tai CHUA duoc tu y set 2 field nay cho `1001` khi chua co cong thuc that

## Board Mutation Truth Tu `mq.java`

| Family | Board-side behavior xac nhan trong `mq` |
| --- | --- |
| `1000` | clear tung o trong `byArray/byArray2` ngay lap tuc |
| `1001` | goi `this.a.a(row,col,10)` va push vao `this.a.p[]`; day la mark state dac biet, khong phai clear thuong |
| `1002` | goi helper actor/board object `a(this.I)` tren ben cast; `bl2=false` |
| `1003` | khong clear board; `bl2=false` |
| `1005` | khong clear board; `bl2=false` |
| `1006` | clear tung o trong `byArray/byArray2` |
| `1007` | clear tung o trong `byArray/byArray2` |
| `1008` | clear theo dot: delay bat dau `16`, moi dot clear `byArray.length / 8` o, sau moi dot delay giam `2` |
| `2000` | clear tung o trong `byArray/byArray2` |
| `2001` | goi helper `c(this.I)` tren actor/board object ben cast; `bl2=false` |
| `2002` | goi helper `d(this.I)` tren actor/board object ben cast; `bl2=false` |
| `2003` | clear tung o trong `byArray/byArray2` |
| `2004` | goi helper `e(this.I)` tren actor/board object ben victim, roi roi xuong case `2005`; `bl2=false` |
| `2005` | khong clear board; `bl2=false` |
| `2006` | sort `byArray3` tang dan, roi clear theo cot; neu `side==0` clear row `2..9` voi delay `(-2 or 0) + row`, neu `side==1` clear row `9..2` voi delay `(9 or 11) - row`; `bl2=false` |
| `2007` | clear tung o trong `byArray/byArray2` |
| `2008` | clear tung o trong `byArray/byArray2` |
| `4000` | clear tung o trong `byArray/byArray2` |
| `4001` | khong clear board; `bl2=false` |
| `4002` | goi helper `b(this.I)` tren actor/board object ben victim; `bl2=false` |
| `4004` | khong clear board; `bl2=false` |
| `4005` | khong clear board; `bl2=false` |
| `4006` | clear tung o trong `byArray/byArray2` |
| `4007` | clear tung o trong `byArray/byArray2` |
| `4008` | clear tung o trong `byArray/byArray2` |

## Runtime Dispatch Truth Tu `mt.java`

### Hinh hoc / helper chung

- `n5` bat dau la `-180`, neu `side==0` thi doi thanh `180`.
- `n6/n7` la nua chieu rong / nua chieu cao cua `this.a.a(0,0)`; duoc cong them vao target cell o nhung family can center cell.
- `mt.a(index, family, startX, startY, endX, endY, delay)` tao/reuse runtime actor `im`, sau do goi `im.a(...)`.
- `this.a(side, x, y, bl)` va `this.c(side, x, y)` la actor helper/hit helper; Java khong dat ten semantic cho id helper.
- `this.d(side)` la pipeline chung cho `1003/2005/4004` -> `io`.

### Group 100x

| Family | Runtime dispatch da xac nhan |
| --- | --- |
| `1000 / is` | spawn 1 projectile vao center victim, them actor helper `a(otherSide, 32, 22, false)`, roi spawn them projectile toi tung cell trong `objectArray/byArray3` voi delay `10..24` |
| `1001 / it` | spawn effect tai tung o trong `byArray/byArray2`; delay bat dau tu `10`, sau moi o tang `4` |
| `1002` | khong tao `im`; chi goi actor helper `a(side, 1, true, true)` |
| `1003 / io(0)` | chi goi `d(side)`; `n3 += 30` |
| `1004 / iu` | spawn tai center-day victim; goi `c(otherSide, 26, 16)` |
| `1005 / iv` | spawn tu center caster -> center victim; goi `c(otherSide, 20, 10)` |
| `1006 / iw` | spawn 1 projectile vao center-day victim, goi `c(otherSide, 36, 26)`, roi spawn them toi tung cell `objectArray/byArray3` voi delay `4..10` |
| `1007 / ix` | spawn 1 effect tai center-day victim, goi `c(otherSide, 30, 11)`, roi spawn them tai tung cell `objectArray/byArray3` voi delay `10..16` |
| `1008 / iy` | goi `c(otherSide, 10, 4)`, lay duy nhat cot `byArray3[0]`, spawn effect tu row `7` cua cot do len diem dich `y=0` |

### Group 200x

| Family | Runtime dispatch da xac nhan |
| --- | --- |
| `2000 / jg` | build path tu tung cell trong `byArray/byArray2`, sau do noi them vao center victim; goi `c(otherSide, n3, n3-6)` voi `n3 = 10 + (pathLength * 5 + 5)` |
| `2001` | khong tao `im`; chi goi actor helper `a(side, 0, true, true)` |
| `2002` | khong tao `im`; chi goi actor helper `a(side, 2, true, true)` |
| `2003 / jh` | spawn 1 projectile vao center victim; goi `c(otherSide, 36, 26)`; `n3 = 14` |
| `2004 / ji` | spawn beam tu mep caster den diem truoc victim `+/-60`; goi `c(otherSide, 20, 10)`; neu `this.h.a(otherSide,0).i()` thi goi them `a(otherSide, 1, false, false)` |
| `2005 / io(1)` | chi goi `d(side)`; `n3 += 30` |
| `2006 / jj` | spawn quet ngang tu ngoai le trai/phai man hinh vao center victim, roi them sweep tren tung cot target va 3 lane ngang bo sung; `n3 = 5` |
| `2007 / jk` | spawn tai tung cell `objectArray/byArray3` voi delay `15..24 + (index<<1)`, sau do spawn them 1 effect tai center-day victim voi delay `n3 - 15`; goi `c(otherSide, n3, n3-6)` |
| `2008 / jl` | spawn 1 actor `jl` tai center victim, set `s = byArray.length`, roi dat tung particle start point tu `byArray/byArray2`; goi `a(otherSide, 32, 22, false)` |

### Group 400x

| Family | Runtime dispatch da xac nhan |
| --- | --- |
| `4000 / iz` | giong `1000`: 1 projectile vao center victim + actor helper `a(otherSide, 32, 22, false)` + volley per-cell `10..24` |
| `4001 / ja` | spawn tai center-day victim; goi `this.b.c(10)`; `n3 = 14` |
| `4002 / jb` | spawn linear shot tu center caster sang center victim; goi `a(otherSide, 1, false, false)`; `n3 = 15` |
| `4003 / jc` | spawn tai center-day victim; goi `this.b.a(otherSide, 10)` va `c(otherSide, 15, 16)`; `n3 = 13` |
| `4004 / io(2)` | chi goi `d(side)`; `n3 += 30` |
| `4005 / jd` | spawn tu center caster -> center victim; goi `c(otherSide, 26, 16)` |
| `4006 / je` | spawn 1 projectile center victim truoc, goi `c(otherSide, 20, 10)`, roi volley per-cell `10..24` |
| `4007 / jf` | spawn tai tung cell `objectArray/byArray3` voi delay `10..24`, roi goi `c(otherSide, n3, n3-6)` |
| `4008 / iz reuse` | giong `4000`, nhung volley per-cell delay `10..29` |

## Victim Hit Emitter Tu `mx.java`

Relevant methods:

- [mx.java:1935](/d:/Twelve/reference/redecoded/decompiled/mx.java:1935)
- [mx.java:1973](/d:/Twelve/reference/redecoded/decompiled/mx.java:1973)

`mx.c(int)` giu va lam muot cuong do hit cho lan phat tiep theo.

`mx.a(side, power, bl2)` ban particle quanh actor bi trung don; neu `bl2` bat thi con day tiep impact vao runtime HUD/state cua victim.

Vi vay khi port skill can du ca:

- visual cell tren board
- burst / shake / hit feedback o actor
- damage popup dung nhip impact

## Verified Runtime Class Notes

### `is`

- Dung `1000001` lam flight sheet, `1000002` lam impact sheet.
- `j = at.a(startX, endX, 10)`, `k = at.a(startY, endY, 10)`.
- State `0`: bay den dich.
- State `1`: impact animation, frame delay `3`, xong thi `r=false`.
- `u` la delay countdown truoc khi ve / update.
- Facing: neu `startX < endX` thi rotate `2`, nguoc lai rotate `0`.

### `iw`

- Ke thua `is`.
- Flight/impact asset doi sang `1006001/1006002`.
- Impact frame count ngan hon (`w = 4`).
- Override render: trong state `1`, neu frame index khac `3` thi ve doi xung 2 ben bang `drawRegion(..., transform 0)` va `drawRegion(..., transform 2)`.

### `iz`

- Ke thua `is`.
- Asset `4000001/4000002`.
- Impact frame count `w = 3`.
- Override `d(1)` de set frame delay impact = `3`.

### `je`

- Ke thua `iz`.
- Asset `4006001/4006002`.

### `it`

- Asset `1001001`.
- Neu `delay <= 0` thi vao state anim chinh ngay.
- Neu `delay > 0` thi giu state idle, countdown het moi chuyen state anim chinh.
- Het state anim chinh thi `r=false`.

### `iu`

- Asset `1004001`.
- Tao san 6 `lp(0)`.
- Neu target nam nua phai man hinh (`n4 >= v.t / 2`) thi:
  - rotate `0`
  - anchor hitbox tai `targetX + 20, targetY`
  - draw origin tai `h - o - 20`
- Neu target nam nua trai man hinh thi doi xung:
  - rotate `2`
  - anchor tai `targetX - 20, targetY`
  - draw origin tai `h + o + 20`
- Khi frame chay den `length - 5` thi spawn 6 mini explosion doi xung quanh truc X.
- Ket thuc khi toan bo `lp[]` tat.

### `iv`

- Asset `1005001`.
- Region table co 3 frame shape.
- Khoi diem duoc doi `n2 -= 60` truoc khi xet huong.
- Neu bay tu trai sang phai: rotate `0`, anchor dich `h = targetX`.
- Neu bay tu phai sang trai: rotate `2`, anchor dich `h = targetX - 50`.
- Trong state flight: `x` tang dan, `j += x`, roi goi `e(targetX, stepY)` de tao duong cong roi.
- Khi cham dich thi spawn 6 `lp(0)` quanh `v = targetX`.
- Bytecode xac nhan `n6` duoc luu vao `w`, nhung object van luon vao state `1` de bay ngay.
- Sau burst dau tien, object chuyen sang state `0` va countdown `w`.
- Khi `w` ve `0`, object quay lai state `1`.
- Suy luan co kiem soat tu bytecode `iv + as + at`:
  - day khong phai pre-delay thong thuong
  - state `0` dung frame `-1`, nen object bi an trong pha countdown
  - `mt` truyen `n6 = 4` cho ca `1005` va `4005`
  - flow chac nhat hien tai la:
    - lao vao dich
    - no mini-explosion lan 1
    - an khoang 4 tick
    - tai xuat rat ngan tai cung diem trung
    - kich mini-explosion lan 2
  - day la delayed second impact burst, khong phai mot lan bay cong thu hai

### `jd`

- Ke thua `iv`.
- Asset `4005001`.
- Mini explosion dung `lp(2)`.
- Sheet `4005001` la bien the nuoc/bang cua head-lunge family, mau cyan/trang.
- `lp(2)` la `/miniexplosionfire` da rotate `138`, tao splash/burst huong nuoc-bang thay vi lua goc.
- Cung state machine 2-stage nhu `iv`; khac variant mini explosion va region table.

### `ix`

- Asset `1007001`.
- Neo thang tai `x - o/2, y - p`.
- Neu `delay > 0` thi vao state `1`, het delay moi chuyen state `0`.
- State `0` chay strip chinh, xong thi `r=false`.

### `jf`

- Ke thua `ix`.
- Asset `4007001`.
- Region table / frame table khac `ix`.

### `iy`

- Asset `1008001`.
- `a(startX,startY,endX,endY,delay)` khong dung `delay`; no luon vao state `0`.
- Dat vi tri ve tai `startX - o, startY - p`.
- Dat dich Y tai `endY - p`.
- State `0`: chay strip doi xung.
- State `1`: tang `k += 2`, roi dua `y` tien den dich.
- Render doi xung bang 2 nua trai/phai tu cung mot region.

### `jg`

- Asset node chinh `2000001`.
- Asset noi segment: `2000002`, `2000003`.
- `u` = so node hien dang duoc mo.
- Cu 5 tick tang them 1 node.
- `y` toggle `0/1` moi 2 tick de doi segment sprite.
- `a(int[] xs, int[] ys)` nhan path da build san tu `mt`.
- Ve node xong roi noi tung cap node bang segment 28 px.

### `ji`

- Asset `2004001`.
- Khong phai sheet strip thuong; dung region table `u`.
- Mang `t[10]` giu cac moc X cho than beam.
- Frame `0/1`: head.
- Frame `2`: head + than lap lai + tail.
- Huong trai/phai quyet dinh boi `startX/endX`.

### `jj`

- Asset `2006001`.
- Co 3 state:
  - `2` = countdown hold
  - `0` = bay ngang
  - `1` = impact strip
- Toc do ngang = `abs(startX - endX) / 6`, cap tren `30`.
- Trong render, frame nao dang active se doi `o/p` theo region table.

### `jk`

- Asset `2007001`.
- Layer chinh dung region table `t`.
- Layer phu `as v` dung region table `u`.
- 2 layer chay chung frame table `s`.
- Delay > 0 thi vao state `1`, het delay moi chuyen state `0`.
- Trong `k()`, width cua layer chinh duoc sync theo frame dang chay cua layer phu.

### `jl`

- Asset `2008001`.
- Tao san 36 particle `as`.
- Chi dung `s` particle dau.
- State `0`: kich hoat particle, random dich quanh victim, set toc do rieng cho moi particle.
- State `1`: hold `5` tick.
- State `2`: moi particle bay toi dich random.
- State `3`: particle chay strip ket thuc.
- Tat khi tat ca particle xong.

### `ja`

- Asset `4001001`.
- Spawn tai cho, `a(-1)`, `b(true)`.
- Chay 1 strip roi tat.

### `jb`

- Asset `4002001`.
- Neu bay trai->phai thi `startX += 26`, rotate `0`, anchor `10`.
- Neu bay phai->trai thi `startX -= 26`, rotate `2`, anchor `6`.
- State `0`: linear shot.
- State `1`: strip impact.

### `jc`

- Asset `4003001`.
- Huong theo nua man hinh cua target:
  - target nam nua phai => rotate `0`
  - target nam nua trai => rotate `2`
- Spawn tai cho, chay 1 strip roi tat.

### `io`

- Dung chung cho `1003/2005/4004`.
- Tao 6 `lp(variant)` neu chua co.
- Spawn mini explosion doi xung quanh truc X cua victim, delay tung explosion = `25 + cv.a(6)`.
- Sau 10 tick se goi actor helper `a((side + 1) % 2, 34, 24, true)`.
- Family-specific asset preload:
  - variant `0` -> `mp.a(1003)`
  - variant `1` -> `mp.a(2005)`
  - variant `2` -> `mp.a(4004)`

### `lp`

- Variant `0` -> `/miniexplosionfire`
- Variant `1` -> byte-rotate `-111`
- Variant `2` -> byte-rotate `138`
- Delay > 0 thi giu state `0`; het delay moi chuyen state `1`.
- State `1` chay strip 4 frame; xong thi `r=false`.

### `in`

- Day la actor overlay dung sheet `1003003` base / rotate, khong phai family runtime doc lap.
- `n2` trong ctor:
  - `0` -> `mp.n`
  - `1` -> `mp.z`
  - `2` -> `mp.I`

## Cac Diem Xac Nhan La Khong Dung Neu Client Tu Render Kieu Chung

- Khong duoc gom `actor target` va `cell target` vao cung 1 target list cho projectile family.
- Khong duoc dung `bloodthrowaround` lam family effect chung; no la shared hit effect cua actor/battle renderer.
- Khong duoc cho rang moi family deu clear board; nhieu family chi la helper/status branch trong `mq`.
- Khong duoc xem `1002/2001/2002/4008` nhu family thuong:
  - `1002/2001/2002` khong co `im` runtime class
  - `4008` reuse `iz` + asset `4000`
- Khong duoc tu suy board target / actor target tu cursor local; Java render theo arrays da duoc packet dua vao.

## Cac Diem Con Mo Ho, Khong Duoc Doan Them

- `iv.a(...)` / `jd` co nhanh xu ly `n6` ma CFR decompile ra hoi mau thuan voi state machine con lai.
  - Co the no lien quan den hold sau impact, khong phai pre-delay.
  - Chua co runtime capture thi khong suy them.
- Ten semantic that cua actor helper ids:
  - `a(side, 32, 22, false)`
  - `c(side, 26, 16)`
  - `c(side, 20, 10)`
  - `c(side, 36, 26)`
  Java chi cho thay callsite va tham so, khong dat ten gameplay cho chung.
- `1001` board helper `this.a.a(row,col,10)` trong `mq` ro rang la mark state dac biet, nhung ten gameplay chinh xac cua state `10` khong nam trong bo file skill runtime nay.

## Doi Chieu Footage Da Chot

Da doi chieu voi cac video local trong `reference/raw/videos/`.

Nhung diem duoi day da duoc xem la da chot:

- Clip `He Hoa Thuy` co popup bi lech voi luc cast.
  - Khong duoc dung popup clip nay de gan art he.
  - Rule dung de port van la:
    - Hoa chi dung runtime art Hoa
    - Thuy chi dung runtime art Thuy/Bang
- `Hoa Kiem Thuat` trong clip Android la skill bien nhieu o thanh special tile `kiem lua`, khong phai projectile.
  - Day la bang chung footage rat manh cho nhanh `1001` mark/special-tile trong Java.
- `Bang Tien Thuat` trong footage la skill board-break theo `1 den 2 vung 2x2`.
  - Effect nhin thay la projectile Bang/Thuy lao vao board roi no theo cum.
  - Dieu nay phu hop voi nhom projectile board-hit, khong phai mark skill.
- `Mua Thien Thach` trong footage la nhom nhieu projectile roi lech thoi gian vao nhieu diem board.
  - Actor hit va board hit la hai nhanh rieng.
  - Khong duoc render theo kieu nhieu qua cau chong cung mot o.

Nhung diem nay chi dung de corroborate spec Java.
Source of truth van la ma Java decompiled; footage chi duoc dung de loai bo nhung cach port sai feel.

## Bytecode Verified Notes

Nhung diem duoi day da duoc doi chieu lai bang `javap -c` tren `reference/twelvefull.jar`,
khong chi dua vao output CFR.

### `1005 / 4005` khong phai "khong co code"

- `1005` dispatch vao `new iv()` trong `mt`.
- `4005` dispatch vao `new jd()` trong `mt`.
- `jd` ke thua `iv`, chi doi:
  - asset sheet
  - region table
  - `lp` variant tu `0` sang `2`

### State machine `iv / jd`

Bytecode xac nhan:

- luc khoi tao cast:
  - neu `n6 > 0` thi `w = n6`, goi `d(0)`
  - ngay sau do van goi them `d(1)`
- trong `k()`:
  - state `1` la pha bay / lao den dich
  - khi cham dich:
    - spawn 6 mini explosion doi xung
    - chuyen sang state `0`
  - state `0`:
    - neu `w > 0` thi countdown
    - khi `w == 0` thi quay lai state `1`
    - neu khong con `lp` nao song thi `r = false`

Ket luan ky thuat:

- `n6` cua `1005 / 4005` khong phai delay truoc khi bay.
- No la mot countdown sau impact dau tien.
- Vi object da o tai dich khi quay lai state `1`, kha nang rat cao la effect nay co 2-stage impact.
- Cho den khi co footage dung family nay, phai danh dau behavior nay la `bytecode-verified flow, visual outcome partially inferred`.

### Helper calls trong `mt`

Co 2 nhom hoan toan khac nhau:

- `c(side, power, delay)`:
  - route qua battle hit emitter `mx`
  - gay particle / shake / impact feedback tren actor
  - `delay` duoc dua vao co che lam muot cuong do hit tiep theo
- `a(side, kind, boolA, boolB)`:
  - khong di vao damage burst
  - them actor helper badge `ip` dung sheet `/skillupdownstat`
  - neu `boolB = true` thi goi them overlay `in`

### `/skillupdownstat`

Sheet nay co 4 icon helper nho:

- shield
- sword
- lightning
- skull

Java xac nhan:

- `1002 / 2001 / 2002` dung nhanh helper badge nay, khong co projectile runtime class rieng
- `2004 / 4002` dung helper badge / actor helper, khong phai hit-burst thong thuong

### Helper/status timers tren `lg`

`mq` goi helper families vao `ms.a(side, 0)` -> `lg`.
`lg` giu 5 timer/status rieng:

- `a(int)` -> field `d` -> `lg.e()`
- `c(int)` -> field `e` -> `lg.g()`
- `d(int)` -> field `f` -> `lg.h()`
- `e(int)` -> field `h` -> `lg.i()`
- `b(int)` -> field `g` -> `lg.f()`

Moi tick battle, `mq` goi `lg.s()` de giam ca 5 timer.
Khi mot timer vua het, `mq` bao `mt` spawn helper badge tu `/skillupdownstat`:

- het `lg.e()` -> `mt.a(side, 1, true)` -> badge positive kind `1`
- het `lg.g()` -> `mt.a(side, 0, true)` -> badge positive kind `0`
- het `lg.h()` -> `mt.a(side, 2, true)` -> badge positive kind `2`
- het `lg.i()` -> `mt.a(side, 1, false)` -> badge negative/skull
- het `lg.f()` -> `mt.a(side, 1, false)` -> badge negative/skull

Bytecode `ip.java` xac nhan:

- badge positive dung 3 icon rieng (`kind 0/1/2`)
- badge negative deu dung chung icon skull

Dieu da chot:

- nhom `1002 / 2001 / 2002 / 2004 / 4002` la helper/status families
- chung khong co projectile runtime class rieng nhu nhom `1000 / 1005 / 4000 ...`
- chung set timer/status tren actor wrapper roi de `mq/mt` tu render helper badge va overlay

Image `skillupdownstat.png` xac nhan thu tu icon:

- positive kind `0` = shield
- positive kind `1` = sword
- positive kind `2` = lightning
- negative = skull

`mt.a(side, kind, bl2, bl3)` da ro nghia ky thuat:

- `kind` chon icon badge trong `skillupdownstat`
- `bl2 = true` = lane positive
- `bl2 = false` = lane negative/skull
- `bl3 = true` moi goi them `c(side)` de spawn overlay `in`

`in` chi la actor overlay dung chung sheet `1003003` da rotate theo he.
No khong phai mot status family doc lap.

Map helper/status families da chot:

- `1002`:
  - `mq` goi `lg.a(this.I)`
  - `mt` add helper badge `kind 1 positive` + overlay `in`
  - => helper family dung sword badge
- `2001`:
  - `mq` goi `lg.c(this.I)`
  - `mt` add helper badge `kind 0 positive` + overlay `in`
  - => helper family dung shield badge
- `2002`:
  - `mq` goi `lg.d(this.I)`
  - `mt` add helper badge `kind 2 positive` + overlay `in`
  - => helper family dung lightning badge
- `2004`:
  - `mq` goi `lg.e(this.I)` tren victim
  - `mt` chi add skull badge neu timer nay dang active
  - => debuff family type A
- `4002`:
  - `mq` goi `lg.b(this.I)` tren victim
  - `mt` add skull badge
  - => debuff family type B

Phan chua chot 100%:

- gameplay semantic player-facing cua 3 positive badge:
  - shield / sword / lightning la chac ve hinh anh
  - nhung Java runtime nay khong dat ten canon nhu "tang thu", "tang cong", "tang toc/doi sat thuong"
  - chua tim thay noi nao trong client Java noi ro shield/sword/lightning lam thay doi stat nao
- ten canon cua 2 debuff skull families `2004` va `4002`

## Skill Metadata Client Giu

Java client khong chi giu asset map.
No van giu metadata skill cuc bo trong object `lv`:

- `id/family code` qua `ld.a`
- `name` qua `ld.b`
- `subtitle/type` qua `ld.c`
- `description` qua `ld.d`
- `mana` qua `lv.e`
- `current level` qua `lv.f`
- `max level` qua `lv.g`
- `extra description lines` qua `lv.h[]`

Dieu nay co nghia:

- ten / mo ta / mana / level cua skill co ton tai ben client
- nhung battle resolution van khong duoc client tu tinh
- runtime cast van phai dua vao target arrays va ket qua battle da duoc dua vao

Doc sau hon trong `ky.java` cho thay packet skill metadata do vao `lv` theo cac field:

- `ld.a` = skill id / family code
- `ld.b` = ten skill
- `ld.d` = description chinh
- `lv.f` = current level
- `lv.e` = mana
- `lv.h[]` = cac dong mo ta bo sung

Tuc la:

- ten canon va description that cua skill nam trong packet/catalog
- khong nam trong offline runtime sheets
- khong the suy ra chi bang icon/family class

## Skill Catalog / Tree Metadata

Client con giu 1 lop catalog/tree rieng:

- `lw` = skill node metadata
- `lx` = level metadata cua skill node

`ky.q(...)` parse:

- id skill node
- ten skill node
- `skillrequest`
- cac muc level, request level, point, ...

`lw/lx` cho thay ro:

- `lw.b` = ten node skill tren tree
- `lw.d` = `skillrequest`
- `lx.a` = level moc
- `lx.b` = request level
- `lx.c` = point cost
- `lx.d` = gia tri metadata bo sung theo moc
- `lx.e` = text mo ta theo level

`de.java` load icon tree bang `skillId * 1000`.

Level/dialog flow xac nhan them:

- `gz.java` hien mo ta theo level bang `go.r[n11].c[this.v[n2].f - 1].e`
- `lv.f` = current level cua skill dang so huu
- `lw.c` = danh sach moc level cua node skill
- `lx.e` = chuoi mo ta player-facing cho tung level
- `lx.b` = yeu cau level nhan vat de tang moc tiep
- `lx.c` = so diem skill can de tang moc tiep
- `lx.d` = metadata so hoc bo sung cua level, duoc `mt.java` copy vao `lv.e`

Doc `de/om/gz` cho thay ro:

- tree UI co giu level hien tai cua tung node (`de.r[slot] = lv.f`)
- tree UI ve so level ngay tren icon skill
- tree UI co tinh du dieu kien tang diem dua tren:
  - level hien tai cua node
  - `go.r[slot].c[nextLevel]`
  - level nhan vat `go.k.G`
  - skill point con lai `go.k.L`
- popup/dialog skill doc mo ta dung theo level hien tai, khong phai 1 text co dinh cho moi skill

Vi vay:

- Java client co skill level tree that
- Java client co text mo ta theo tung level
- Java client co unlock rule/co so de hien UI nang skill
- nhung Java client khong tu quyet dinh battle result; phan `byArray/objectArray`, damage thuc chien va runtime cast packet van la du lieu tu server/packet

Ket luan:

- map ten skill that -> family code ve mat du lieu la co kha nang khui tiep tu packet/catalog
- nhung phan battle family runtime van nam o `mp/mq/mt` chu khong nam o skill tree catalog
- ten/description skill trong client duoc nap tu packet parse (`ky`) vao `lv/lw`; khong thay hardcode canon text cho nhom helper/debuff trong offline asset/runtime classes

## Tree UI Boundary

Doc them `de`, `hh`, `id` cho thay:

- `de` chi lo:
  - ve cay skill
  - map icon theo `skillId * 1000`
  - hien level/unlock state
  - move focus tren 9 o
- callback `bq` trong `hh/id` chi dung cho:
  - chon o
  - mo popup / upgrade flow / inventory flow
  - khong goi vao battle runtime `mq/mt`

Ket luan rang:

- tree UI va battle runtime la hai he thong tach biet
- doc sau `de/hh/id` khong mo them semantic battle cho helper/status
- neu can ten canon, mana, description, unlock thi phai di qua packet/catalog
- neu can projectile path, board break, hit feel thi phai di qua `mp/mq/mt`

## Remaining Pending

Sau luot phan tich nay, cac pending chinh con lai khong con nam o runtime flow.
Runtime/board/asset/hit/helper da du de code theo Java.

Pending con lai:

- ten canon that cua tung skill, user se cung cap sau
- ten gameplay canon cua mot so helper/debuff families:
  - nhom sword/shield/lightning/skull
  - Java cho thay icon + timer + dispatch, nhung khong dat ten gameplay minh bach

Pending khong con la blocker de port client runtime.

## Nhung Thu Khong Nam Trong Java Client Nay

- player-facing skill names
- mana / rage cost
- damage multiplier
- unlock table
- cooldown state
- server skill catalog
- packet schema day du cho runtime cast result

Chinh xac hon:

- `skill level tree` co nam trong Java client o muc data/UI:
  - node skill `lw`
  - level entry `lx`
  - skill dang so huu `lv`
  - UI tree/dialog `de/gz`
- nhung Java client khong chua server truth cho battle-effect scaling:
  - so vung 2x2 that su bi pha
  - target arrays cho runtime cast
  - damage/he-so thuc chien theo level

Nhung phan do phai lay tu server hoac tu cac file khac, khong duoc phat sinh tu spec nay.

## Hoa Cau Thuat va Mo Ta Theo Level

Anh popup user cung cap ("Tan cong va gay 168 sat thuong cho doi phuong, dong thoi pha huy tu 2 den 3 vung 2x2 cac quan co") phu hop dung voi pipeline Java nay:

- ten skill lay tu `go.r[n].b`
- mo ta level hien tai lay tu `go.r[n].c[currentLevel - 1].e`

Y nghia reconstruction:

- co the dung text popup de xac nhan y dinh thiet ke cua `1000 / Hoa cau thuat`
- nhung khong duoc dung text popup lam bang chung duy nhat cho packet/runtime shape
- neu popup noi `2 den 3 vung 2x2`, ta duoc phep xem day la muc tieu behavior can dat
- con cach chon chinh xac cac vung 2x2 nao, va khi nao la `2` hay `3` vung, van can doi chieu them voi footage/capture hoac server logic phuc dung

Implementation note hien tai:

- battle runtime da cho phep packet nhan `skillLevel`
- nhung battle sandbox chua noi vao skill tree/DB that
- vi vay implementation hien tai dang dung fallback level trong battle test
- khi co DB/catalog/tree that:
  - phai noi `current skill level` that vao request battle
  - bo fallback sandbox
  - re-tune lai xac suat `1000 / Hoa cau thuat` theo du lieu that neu can
- phan nay de sau, khong coi la blocker cho reconstruction runtime hien tai

## Canonical Names Pending

Ten skill that trong game se duoc bo sung sau tu user / server catalog.

Cho den luc do:

- Khong duoc dung ten player-facing lam source of truth cho runtime.
- Trong code va asset metadata, uu tien dung:
  - `familyCode`
  - ten ky thuat theo pattern nhu `mark_fire_sword`, `water_arrow_cluster`, `meteor_cluster_2x2`
- Khi user cung cap ten that:
  - chi map ten -> family/runtime da co san
  - khong doi nguoc family/runtime de chay theo ten

## User Confirmed Canonical Names

Nhung ten duoi day la ten canon do user cung cap truc tiep, uu tien cao hon placeholder ky thuat.

### He Hoa - theo thu tu 9 o trong cay skill

User xac nhan:

1. `Hoa cau thuat`
2. `Liet Hoa Chi Kiem`
3. `Hoa Kiem Thuat`
4. `Mua thien thach`
5. `Phich lich hoa su`
6. pending
7. pending
8. `Hoa Long than`
9. `Hoa phung lieu nguyen`

Ghi chu:

- Day la mapping theo so thu tu tren cay skill Hoa trong UI, khong phai ket luan moi tu decompile.
- Khi can map vao family runtime `1000..1008`, phai giu quy tac:
  - ten canon di theo thu tu user xac nhan
  - runtime/board behavior van di theo spec Java da doc
- Slot `6` va `7` hien chua co ten canon, tiep tuc de `pending_user_confirmation`.

### He Hoa - map ten canon -> family code

Map nay da duoc tang muc do tin cay boi `de.java`.

Java xac nhan:

- cay skill dung 9 slot co index `0..8`
- icon moi slot load bang `go.r[n].a * 1000`
- trong `de.a(lh)`, client dat level/unlock theo `skillId % 100`
- voi nhom Hoa `1000..1008`, chi so `% 100` map truc tiep vao 9 vi tri tren cay

Vi tri slot trong `de.q`:

- `0` = tren giua
- `1` = giua giua
- `2` = duoi giua
- `3` = tren trai
- `4` = giua trai
- `5` = duoi trai
- `6` = tren phai
- `7` = giua phai
- `8` = duoi phai

Suy ra tu screenshot cay Hoa + ten user da cung cap:

- `1000` -> `Hoa cau thuat`
- `1003` -> `Liet Hoa Chi Kiem`
- `1001` -> `Hoa Kiem Thuat`
- `1006` -> `Mua thien thach`
- `1004` -> `Phich lich hoa su`
- `1002` -> pending
- `1007` -> pending
- `1005` -> `Hoa Long than`
- `1008` -> `Hoa phung lieu nguyen`

Muc do chac chan:

- thu tu slot va vi tri UI: Java-confirmed
- icon tung family Hoa: asset-confirmed
- ten canon theo thu tu 9 o: user-confirmed
- vi vay mapping tren la du manh de dung lam working canonical map cho He Hoa

### He Loi - map chi so cay skill -> family code

Nguon doi chieu:

- [zap_skill.png](/e:/Twelve/reference/raw/images/zap_skill.png)
- [thunder_family_icons_sheet.png](/e:/Twelve/reference/raw/videos/_analysis_frames/thunder_family_icons_sheet.png)
- [de.java](/e:/Twelve/reference/redecoded/decompiled/de.java:31)

Vi tri tren screenshot cay Loi khop dung voi `de.q`, nen co the chot:

- Chieu `1` = slot tren giua = `2000`
- Chieu `2` = slot tren trai = `2003`
- Chieu `3` = slot giua giua = `2001`
- Chieu `4` = slot tren phai = `2006`
- Chieu `5` = slot giua trai = `2004`
- Chieu `6` = slot duoi giua = `2002`
- Chieu `7` = slot giua phai = `2007`
- Chieu `8` = slot duoi trai = `2005`
- Chieu `9` = slot duoi phai = `2008`

Tom tat runtime theo family, de sau nay gan ten canon vao dung o:

- `2000 / jg` = path-chain tu cac o board da chon, noi vao center victim, roi phat actor hit muon
- `2003 / jh` = projectile lao vao victim truoc, sau do mo volley xuong cac o board
- `2001` = helper/status positive voi badge `shield`, khong co projectile family rieng
- `2006 / jj` = side sweep theo cot, clear board theo huong trai-phai hoac phai-trai, co them 3 duong quet ngang
- `2004 / ji` = side-lob vao victim, gay debuff skull-family A, co the them helper badge tren victim neu dieu kien hop le
- `2002` = helper/status positive voi badge `lightning`, khong co projectile family rieng
- `2007 / jk` = nhieu tia danh vao tung cell target, sau do moi co hit vao victim
- `2005 / io(1)` = aura/helper family dung shared `io`, khong clear board
- `2008 / jl` = tao burst trung tam roi moc toi nhieu cell target, co actor helper `a(...32,22...)`

Trang thai hien tai:

- thu tu 9 o + family code: Java-confirmed + screenshot-confirmed
- ten canon cua tung chieu Loi: `pending_user_confirmation`
- co the lap tuc code runtime theo bang tren ma khong can cho ten that

### He Thuy - map chi so cay skill -> family code

Nguon doi chieu:

- [ice_skill.png](/e:/Twelve/reference/raw/images/ice_skill.png)
- [water_family_icons_sheet.png](/e:/Twelve/reference/raw/videos/_analysis_frames/water_family_icons_sheet.png)
- [de.java](/e:/Twelve/reference/redecoded/decompiled/de.java:31)

Vi tri tren screenshot cay Thuy cung khop dung voi `de.q`, nen co the chot:

- Chieu `1` = slot tren giua = `4000`
- Chieu `2` = slot tren trai = `4003`
- Chieu `3` = slot giua giua = `4001`
- Chieu `4` = slot tren phai = `4006`
- Chieu `5` = slot giua trai = `4004`
- Chieu `6` = slot duoi giua = `4002`
- Chieu `7` = slot giua phai = `4007`
- Chieu `8` = slot duoi trai = `4005`
- Chieu `9` = slot duoi phai = `4008`

Tom tat runtime theo family, de sau nay gan ten canon vao dung o:

- `4000 / iz` = volley bang co 1 projectile vao victim + them projectile roi vao cac cell target
- `4003 / jc` = burst/pillar tai chan victim, co hit helper va battle support `ice`
- `4001 / ja` = aura/tinh the dat tai victim, khong clear board
- `4006 / je` = falling ice volley vao victim va nhieu cell target, actor hit tach rieng
- `4004 / io(2)` = aura/helper family dung shared `io`, khong clear board
- `4002 / jb` = debuff skull-family B, bay thang qua victim, khong pha board
- `4007 / jf` = nhieu impact theo tung cell target, sau do phat actor hit muon
- `4005 / jd` = bien the nuoc/bang cua `iv`, bytecode-confirmed la flow 2-stage impact, asset va helper khac `1005`
- `4008 / iz reuse` = reuse asset/runtime cua `4000` nhung delay volley cell dai hon

Trang thai hien tai:

- thu tu 9 o + family code: Java-confirmed + screenshot-confirmed
- ten canon cua tung chieu Thuy: `pending_user_confirmation`
- logic Thuy da du manh de port dung art, projectile path, board break va actor hit ma khong can dat ten that

## To Chuc Folder Skill

Cau truc hien tai la dung huong va khong can trai phang asset ra mot cho.
Nen giu theo 3 lop lon:

1. `00_skill_tree_ui_confirmed`
2. `01_battle_skill_shared_confirmed`
3. `02_elemental_runtime_families`

Quy tac nen giu cho phan runtime families:

- Moi family la 1 folder rieng.
- Ten folder uu tien theo:
  - `family_<code>_<javaClassOrRole>`
  - vi du: `family_1001_it`, `family_4000_iz`, `family_4008_reuse_4000`
- Ben trong moi family folder chi nen co:
  - `runtime_png/`
  - `skill_icon/`
  - `family_summary.json`
- Khong nen chia asset theo ten skill player-facing luc nay, vi sau nay 1 family co the duoc gan ten that muon hon.
- Khong nen gom chung asset theo "skill dep na na nhau", vi runtime Java dispatch theo family code, khong theo cam quan hinh anh.

Dieu toi khuyen nen chinh lai sau, nhung khong can dao lon ngay:

- Giam bot cac nhan `likely` trong ten folder neu da xac nhan he bang chung cu khac.
- Doi `family_4008_no_dedicated_class` thanh 1 ten ro hon vai tro reuse, vi Java route no ve `iz/4000`.
- Them 1 manifest trung tam, vi du `client/assets/skill/families.manifest.json`, de ghi:
  - family code
  - java class
  - group/element
  - runtime asset ids
  - shared helper assets neu co
  - canonical name status: `pending_user_confirmation`

Ket luan thuc dung:

- Khong can trai phang folder.
- Khong can to chuc theo ten skill that luc nay.
- Nen to chuc theo `family code -> runtime asset -> metadata`, vi do la cach Java thuc su van hanh.

## Port Order De Lam Giong Java

1. Build skill tree UI tu `/info/skilltree`, `/info/increase`, `/info/decrease`.
2. Build asset resolver map `family_code -> [asset_id...]` giong `mp.a(code)`.
3. Preload named shared effects o battle start.
4. Tach rieng `actorTarget`, `byArray/byArray2`, `objectArray/byArray3`.
5. Port `mq` va `mt` theo family, khong gom ve mot overlay chung.
6. Dung `bloodthrowaround` nhu shared actor hit effect, khong phai family sheet.
7. Chi khi co skill catalog server moi gan family code voi ten skill ngoai game.

## Reference Skills

| Skill | Use When |
| --- | --- |
| `architecture/` | domain entity `Skill` trong `Twelve.Core` |
| `binary-protocol/` | TLV tags cho skill-slot / skill-level packets |
| `database-design/` | Postgres `PlayerSkills` table |
| `game-mechanics/` | match-3 -> rage -> skill trigger pipeline |
| `frontend-design/` | skill animation player tren client |
| `clean-code/` | naming `SkillFamily`, `SkillAssetRef` |
| `vulnerability-scanner/` | khong tin client cho damage skill |
