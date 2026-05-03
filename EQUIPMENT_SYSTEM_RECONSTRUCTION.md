# Equipment System Reconstruction

Tài liệu khôi phục hệ thống trang bị (equipment) từ Java client cũ.

> **Trạng thái an toàn phục dựng:** Tài liệu này đã gom evidence client-side Java, nhưng **không được coi là server gốc hoàn chỉnh**. Các section `1..12` là phần bám Java client mạnh nhất. Section `13` là quyết định server-side từ gameplay memory/user confirmation vì không có Java server; khi code phải ghi rõ là reconstructed/remake policy và không được coi là công thức gốc nếu chưa có thêm dump/template/packet evidence.
>
> **Quy tắc bắt buộc:** Nếu một rule/field không trace được về Java client (`ll/lb/ky/ks/hh/...`) hoặc evidence người dùng cung cấp, phải đánh dấu `UNVERIFIED`/`PENDING` và hỏi lại trước khi implement. Không tự thêm field/policy như `IsRepairable`, `RepairBlockReason`, `IsUpgradeable`, `InventoryCapacityCost` vào model Java-compatible nếu chưa có template/server evidence.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [ll.java](file:///d:/Twelve/reference/redecoded/decompiled/ll.java) | `ll` | Equipment data model |
| [lb.java](file:///d:/Twelve/reference/redecoded/decompiled/lb.java) | `lb` | Stats block (15 stat fields) |
| [lm.java](file:///d:/Twelve/reference/redecoded/decompiled/lm.java) | `lm` | GameItem data model (consumables, NOT equipment) |
| [mb.java](file:///d:/Twelve/reference/redecoded/decompiled/mb.java) | `mb` | Body-part compositor & sprite loader |
| [hg.java](file:///d:/Twelve/reference/redecoded/decompiled/hg.java) | `hg` | Equipment detail dialog UI |
| [fw.java](file:///d:/Twelve/reference/redecoded/decompiled/fw.java) | `fw` | Item/Equipment tooltip renderer |
| [id.java](file:///d:/Twelve/reference/redecoded/decompiled/id.java) | `id` | Upgrade panel (blacksmith) |
| [ho.java](file:///d:/Twelve/reference/redecoded/decompiled/ho.java) | `ho` | Combine panel variant: material/result grids, check/final combine senders |
| [dc.java](file:///d:/Twelve/reference/redecoded/decompiled/dc.java) | `dc` | Item cell renderer (inventory icon) |
| [hh.java](file:///d:/Twelve/reference/redecoded/decompiled/hh.java) | `hh` | Inventory/equipment screen: bag grid, equip/unequip, sell, discard, repair entry |
| [hl.java](file:///d:/Twelve/reference/redecoded/decompiled/hl.java) | `hl` | Repair dialog using repair-hammer item |
| [ia.java](file:///d:/Twelve/reference/redecoded/decompiled/ia.java) | `ia` | Shop screen: preview equipment, cart, buy response |
| [gx.java](file:///d:/Twelve/reference/redecoded/decompiled/gx.java) | `gx` | Shop/cart confirm dialog for buying multiple equipment products |
| [lq.java](file:///d:/Twelve/reference/redecoded/decompiled/lq.java) | `lq` | Shop product wrapper (`type`, `price`, `item`) |
| [of.java](file:///d:/Twelve/reference/redecoded/decompiled/of.java) | `of` | Trade/transaction panel: received equipment list, remove/reclaim equipment from trade |
| [ks.java](file:///d:/Twelve/reference/redecoded/decompiled/ks.java) | `ks` | Client network sender for equip/sell/upgrade/shop/repair/trade commands |
| [lh.java](file:///d:/Twelve/reference/redecoded/decompiled/lh.java) | `lh` | Character info (holds equip array `ll[] D`) |
| [ky.java](file:///d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network packet parser (equip parse at line 1269) |
| [gp.java](file:///d:/Twelve/reference/redecoded/decompiled/gp.java) | `gp` | Equipment comparator (inventory sorting) |
| [cz.java](file:///d:/Twelve/reference/redecoded/decompiled/cz.java) | `cz` | Character info panel (equip/unequip + stat diff display) |
| [da.java](file:///d:/Twelve/reference/redecoded/decompiled/da.java) | `da` | Character info/status panel: displayed stats include only equipped items that still exist in `go.l` and have durability `p != 0` |
| [go.java](file:///d:/Twelve/reference/redecoded/decompiled/go.java) | `go` | Global inventory state (equip array `ll[] l`, item array `lm[] m`) |
| [com/mg/sq/a.java](file:///d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java) | `com.mg.sq.a` | Stat string builder + character stat aggregation from equipment |

## Working Folders

- [client/assets/equipment](file:///d:/Twelve/client/assets/equipment) — trang bị đã tổ chức theo slot type
- [reference/review_assets/verified_semantic/offline_by_id_or_name](file:///d:/Twelve/reference/review_assets/verified_semantic/offline_by_id_or_name) — flat source (1605 PNG)
- [reference/redecoded/character_meta_parsed.csv](file:///d:/Twelve/reference/redecoded/character_meta_parsed.csv) — meta family map

---

## 1. Equipment Data Model (`ll.java`)

### 1.1 All Fields

```java
public final class ll {
    public static final int[] a;  // Slot sort-priority table (13 entries)
    public int    b;   // Internal sequence ID (used in clone/equals)
    public String c;   // Unique key (server ID string)
    public String d;   // Display name (Vietnamese)
    public byte   e;   // Equipment slot type (0-12)
    public byte   f;   // Element icon ID (tag 15, default 7)
    public String g;   // Description text (tag 117)
    public byte   h;   // Gender: 0=Nam, 1=Nữ, 2=cả hai (tag 16, default 2)
    public int    i;   // Required level (tag 135, default -1)
    public int    j;   // Enhancement level (+1, +2, ...) (tag 27, default 0)
    public long   k;   // Repair cost (tag 190, default -1) — -1 = không sửa được
    public long   l;   // (unknown, copied in clone but not parsed from network)
    public int    m;   // Rank/rarity (tag 138, default 0)
    public int    n;   // *** Resource ID *** — key field for sprite lookup (tag 4)
    public long   o;   // (unknown, not parsed from network)
    public int    p;   // Current durability (tag 139, default -1)
    public int    q;   // Max durability (tag 144, default 0)
    public lb     r;   // Stats block (attack, defense, etc.)
    public byte   s;   // (tag 156, default -1, unknown purpose — parsed in full detail)
    public byte   t;   // Tradeable flag: 1=yes (tag 85, default 1)
    private String u;  // (private, copied in clone, unknown purpose)
}
```

### 1.2 Constructor & Methods

```java
// Constructor — chỉ nhận key + slot type
public ll(String key, byte slotType) {
    this.c = key;
    this.e = slotType;
}

// Tradeable check
public boolean a() { return this.t == 1; }

// Damaged check — true nếu có max durability > 0 VÀ current < max
public boolean b() { return this.q > 0 && this.p < this.q; }

// Repairable check — true nếu repair cost > 0
public boolean c() { return this.k > 0L; }

// Clone — PARTIAL copy (có decompiler bug, xem section 12.1)
// [Java evidence: ll.java line 63-81]
// Fields COPIED: c,e (via constructor), f, h, i, j, k, m, n, p, q, r (ref only), s, t, u
// Fields MISSED (decompiler bug / omission): d (throwaway object bug), g, l, o
public ll d() { /* ... partial copy — see section 12.1 for details ... */ }

// toString — debug format
// "Equip[key=X resid=N ; name=Y; type=E rank=M  \n stats level=J tradeableT"
```

### 1.3 Slot Table (`ll.a[]`)

Mảng static `ll.a` dùng để **sắp xếp** trang bị theo slot priority, KHÔNG phải slot ID:

| Index (e) | Priority (`ll.a[e]`) | Slot Name | Visual Layer |
|-----------|----------------------|-----------|--------------|
| 0 | 1 | Giáp/Áo (Armor) | ✅ body overlay |
| 1 | 4 | Vũ Khí (Weapon) | ✅ weapon overlay |
| 2 | 2 | Mũ/Nón (Helmet) | ✅ head overlay |
| 3 | 3 | Giày/Ủng (Boots) | ❌ stats only |
| 4 | 5 | Ngựa/Khiên (Mount) | ❌ affects `lh.ad` flag; không phải layer equipment thường |
| 5 | 9 | Nhẫn (Ring) | ❌ stats only |
| 6 | 0 | (unused) | — |
| 7 | 6 | Bùa 1 (Accessory) | ❌ stats only |
| 8 | 10 | Bùa 2 / slot đặc biệt | ❌ Java `hh` xử lý đặc biệt: không đưa vào 6 ô `F`, menu dùng `"Dùng"`; remake decision `Wing` xem section 13 |
| 9-12 | 99 | Misc/Event slots | ❌ |

### 1.4 Rank → Display Color (`ll.a(int rank)`)

```java
switch (rank) {
    case 0:     return bx.d;                                    // Thường (white)
    case 1:     return com.mg.sq.a.g;                           // Tốt (green)
    case 2:
    case 3:     return new by(0xB30B0C);                        // Hiếm (dark red #B30B0C)
    case 4:
    case 7:
    case 8:     return new if(new int[]{0x898012, 0xFFFF00});   // Huyền Thoại (gold gradient)
    default:    return bx.d;                                    // fallback white
}
```

### 1.5 Equipment Element / Hệ trang bị (`ll.f`, tag `15`)

Trang bị có **hệ/nguyên tố** hiển thị bằng icon nhỏ đứng trước tên item trong dialog/tooltip. User screenshot ngày `2026-05-03` xác nhận ví dụ `Kim Đao (Luyện Ngục)` có icon **tia sét** trước tên.

Java client evidence:
- `ll.f` là `byte` element icon ID.
- `ky.a(..., bl2=true)` parse `ll.f` từ tag `15`, default `7`.
- `hg.java` render equipment detail có hiển thị element icon từ `equip.f`.
- Element icon chỉ được client chứng minh là metadata/UI icon của equipment; chưa đủ evidence để kết luận nó tự động đổi công thức combat nếu chưa đối chiếu battle source/server policy.

Reconstruction rule:
```text
Equipment.ElementIcon = ll.f / tag 15
DefaultElementIcon = 7
UI must display element icon before equipment name when available.
Do not infer combat element effect from ll.f until battle/equipment interaction is audited.
```

Pending audit:
- Tìm mapping đầy đủ `ll.f -> tên hệ/icon asset` trong Java UI/resource code.
- Đối chiếu battle source xem equipment element có ảnh hưởng damage/khắc hệ không, hay chỉ là icon/thuộc tính hiển thị.
- Nếu server remake quyết định dùng equipment element trong combat, phải ghi rõ là reconstructed policy hoặc link tới Java evidence tương ứng.

---

## 2. Stats Block (`lb.java`)

### 2.1 All 15 Stat Fields

Java evidence verified `2026-05-03` từ `lb.java`, `ky.java` tag parser line `1288-1302`, `lb.toString()` và `com.mg.sq.a.a(ll)` stat string builder. Mapping dưới đây giữ nguyên raw tag/field Java; không đổi tên field decompile thành gameplay nếu chưa có evidence UI/string tương ứng.

| Field | Tag | Stat Name (VN) | Unit | toString label |
|-------|-----|----------------|------|----------------|
| `a` | 118 | Cường lực (Strength) | flat | `strength` |
| `b` | 119 | Thân pháp (Agility) | flat | `agility` |
| `c` | 120 | Nội lực (Magic/Mana) | flat | `magic` |
| `d` | 121 | Thể lực (Vitality) | flat | `vitality` |
| `e` | 72 | Sức tấn công (Attack) | flat | `attack` |
| `f` | 71 | Phòng thủ (Defense) | flat | `defense` |
| `g` | 126 | Chí mạng (Critical Rate) | % | `critDamRate` |
| `h` | 124 | Né tránh (Dodge) | flat | (unnamed in toString) |
| `i` | 47 | Sinh lực (HP bonus) | flat | `HP` |
| `j` | 200 | Hấp thu sát thương (Damage Absorb) | % | — |
| `k` | 201 | Đánh xuyên giáp (Armor Pierce) | % | — |
| `l` | 202 | Cản đòn (Block) | % | — |
| `m` | 203 | Hồi sinh (Revive/Regen) | % | — |
| `n` | 204 | Sức tấn công % (Attack %) | % | — |
| `o` | 221 | Sinh lực % (HP %) | % | — |

### 2.2 Stats Aggregation for Character

#### Java evidence

Từ `com.mg.sq.a.a(lh)` — khi tính tổng stat nhân vật, mỗi equip trong `lh.D`:

```java
for (int i = 0; i < lh2.D.length; i++) {
    lb stats = lh2.D[i].r;
    if (stats != null) {
        strength  += stats.a;
        vitality  += stats.d;
        agility   += stats.b;
        magic     += stats.c;
        attack    += stats.e;
        attack    += baseAttack * stats.n / 100;  // Attack% is multiplicative on base
        critRate  += stats.g;
        defense   += stats.f;
        dodge     += stats.h;
        hpBonus   += stats.i;
    }
}
```

**Quan trọng:** `lb.n` (Attack %) được tính bằng `baseAttack * stats.n / 100` — nhân tỉ lệ, không cộng flat.

#### Pending/Unverified

- Đoạn aggregation trên chỉ chứng minh client cộng các field `a,b,c,d,e,f,g,h,i,n` vào stat/status tổng hợp.
- Chưa có evidence trong đoạn Java đã audit rằng `j/k/l/m/o` được áp dụng vào battle runtime:
  - `j`: hấp thu sát thương %
  - `k`: đánh xuyên giáp %
  - `l`: cản đòn %
  - `m`: hồi sinh %
  - `o`: sinh lực %
- Các field `j/k/l/m/o` có Java evidence về parser + UI label, nhưng **chưa đủ evidence formula gameplay**.

#### Remake policy

- Server/client remake được phép hiển thị `j/k/l/m/o` đúng raw field/tag và label UI.
- Không implement combat effect cho `j/k/l/m/o` như công thức chắc chắn của game gốc nếu chưa có thêm battle/server packet evidence hoặc user confirmation policy riêng.
- Nếu sau này bật combat effect bằng config server mới, phải ghi rõ là `Remake policy`, không ghi như Java gốc.

### 2.2b Status Panel Aggregation (`da.java`)

`da.a(lh)` xác nhận một rule runtime quan trọng khi panel thông tin nhân vật cộng stat từ trang bị:

```java
for (int i = 0; i < this.O.D.length; i++) {
    for (int j = 0; j < go.l.length; j++) {
        if (go.l[j].c.equals(this.O.D[i].c) && go.l[j].p != 0) {
            lb stats = go.l[j].r;
            // cộng strength/vitality/agility/magic/attack/attack%/crit/defense/dodge/HP
        }
    }
}
```

Kết luận phục dựng:
- Trang bị đang mặc (`lh.D`) vẫn được đối chiếu lại với inventory global `go.l` bằng key `ll.c`.
- Nếu durability `p == 0`, stat của trang bị **không được cộng** trong panel status.
- `da.java` cộng `a,b,c,d,e,f,g,h,i,n`; không thấy cộng `j,k,l,m,o` trong status panel.
- Đây là rule client-side UI/status; server combat cũng nên authoritative kiểm tra durability trước khi áp stat, nhưng công thức combat cuối cùng phải đối chiếu thêm battle Java source.

### 2.3 Stat Display Strings

`com.mg.sq.a.a(ll)` trả về `String[10]` cho UI hiển thị. Format:
- Flat stats: `"+ X tên_stat"` hoặc `"- X tên_stat"`
- Percent stats: `"+ X% tên_stat"` hoặc `"- X% tên_stat"`
- Chỉ hiện stat có giá trị != 0

---

## 3. Equipment Sorting (`gp.java`)

Comparator dùng cho sắp xếp inventory (equipment), thứ tự ưu tiên:

```
1. Rank priority:    ll.a[b.m] - ll.a[a.m]     ← rank cao lên trước (descending)
2. Gender:           a.h - b.h                  ← 0=Nam < 1=Nữ < 2=Both
3. Slot type:        a.e - b.e                  ← slot index ascending
4. Enhancement:      b.j - a.j                  ← +cao lên trước (descending)
5. Required level:   b.i - a.i                  ← level cao lên trước (descending)
6. Key string:       a.c.compareTo(b.c)         ← alphabetical fallback
```

---

## 4. Global Inventory State (`go.java`)

### 4.1 Fields

```java
public static lh   k;          // Current player character
public static ll[] l;          // Inventory equipment array (bag, NOT equipped)
public static lm[] m;          // Inventory item/consumable array
public static int  n = 50;     // Max inventory capacity
public static int  o;          // (usage unclear, possibly gold/currency offset)
public static b    p = new gp(0);  // Equipment comparator (gp)
public static b    q = new gq(0);  // Item comparator (gq)
public static ll[] u;          // (secondary equip array — shop/trade context?)
public static lm[] v;          // (secondary item array — shop/trade context?)
public static long s = -1L;    // Player gold (-1 = not loaded)
public static boolean t = true; // (flag, default true)
```

### 4.2 GameItem Data Model (`lm.java`)

`lm` là model item/consumable/material trong inventory (`go.m`). Đây không phải equipment `ll`, nhưng liên quan trực tiếp tới equipment flow vì upgrade/combine/repair/shop dùng item/material và `fw.java`/`dc.java` render chung `ll` + `lm`.

#### Java evidence

`lm.java` extends `ld`. Base `ld` cung cấp các field chung được `lm.toString()` dùng:
- `a`: item id.
- `b`: name.
- `c`: display name.
- `d`: description.

Own fields trong `lm.java`:

| Field | Type | Initial | Evidence từ `toString()` / method | Meaning status |
|-------|------|---------|-----------------------------------|----------------|
| `e` | `byte` | `0` | `type = e`; `go.b()` dùng `item.e == 7` để tính stack by quantity | Java evidence: item type raw value; `7` là stack-count special trong inventory-full check |
| `f` | `int` | `0` | copied in `b()`, không in `toString()` | Pending/Unverified |
| `g` | `int` | `0` | `qty=g` | Java evidence: quantity |
| `h` | `long` | `0` | `$=h` | Java evidence: price/currency-like value; exact economy meaning pending by flow |
| `i` | `int` | `0` | copied in `b()`, không in `toString()` | Pending/Unverified |
| `j` | `int` | `0` | `res id=j`; `dc.java` uses item icon rendering through item res id | Java evidence: item resource/icon id |
| `k` | `long` | `0` | `requireKen=k` | Java evidence: required KEN/cost-like; exact usage pending |
| `l` | `int` | `-1` | `slotCapacity=l`; stack/selection flows use item capacity/quantity limits | Java evidence: slot capacity / stack capacity raw field |
| `m` | `byte` | `1` | `trade=m`; `a()` returns `m == 1` | Java evidence: tradeable flag raw value, `1=true` |

```java
public final boolean a() {
    return this.m == 1;
}

public final String toString() {
    return "Item[id=" + this.a + "; qty=" + this.g + "; $=" + this.h
        + " type = " + this.e + " res id = " + this.j
        + " ] name = " + this.b + " displaynam = " + this.c
        + " requireKen = " + this.k + " des = " + this.d
        + " slotCapacity = " + this.l + " trade " + this.m;
}
```

#### Clone/decompile bug (`lm.b()`)

`lm.b()` có cùng pattern decompiler bug như `ll.d()`:

```java
lm lm2 = new lm(this.a);
new lm(this.a).e = this.e; // THROWAWAY object, lm2.e is not assigned
lm2.d = this.d;
lm2.g = this.g;
lm2.f = this.f;
lm2.h = this.h;
lm2.i = this.i;
lm2.b = this.b;
lm2.c = this.c;
lm2.j = this.j;
lm2.k = this.k;
lm2.l = this.l;
lm2.m = this.m;
return lm2;
```

Kết luận phục dựng:
- Khi clone/copy item trong server/client remake, phải copy `e` vào object clone thật; không tái hiện bug decompiler.
- Không suy diễn `f/i` thành field gameplay khi chưa có usage rõ.
- Giữ raw value `m == 1` là tradeable theo Java method `lm.a()`.

#### Remake policy

- `lm` dùng cho material/consumable/repair hammer trong equipment flows.
- Repair hammer đã được user xác nhận icon/id `30099`, nhưng item catalog gốc đầy đủ vẫn pending.
- Không gộp `ll` và `lm` thành một entity gameplay nếu serializer/protocol vẫn phân biệt equipment và item.

### 4.3 Inventory Operations

```java
// Add equipment to bag
go.a(ll equip) {
    ll[] newArr = new ll[l.length + 1];
    System.arraycopy(l, 0, newArr, 0, l.length);
    newArr[newArr.length - 1] = equip;
    l = newArr;
}

// Remove equipment from bag
go.b(ll equip) {
    ll[] newArr = new ll[l.length - 1];
    // copy all except matching reference
    l = newArr;
}

// Set full inventory from server
go.a(ll[] equips, lm[] items, int capacity, int offset) {
    l = equips; m = items; n = capacity; o = offset;
}
```

### 4.4 Inventory Full Check

```java
// go.b() — check if inventory is full
public static boolean b() {
    int count = l.length;
    count -= go.k.D.length;          // subtract equipped items
    for (lm item : m) {
        if (item.e == 7)             // stackable type 7
            count += item.g;         // add quantity
        else
            count++;                 // non-stackable = 1 slot
    }
    return count >= n;               // full if >= capacity (default 50)
}
```

---

## 5. Equip/Unequip on Character (`cz.java`)

### 5.1 Character Info Panel

`cz` là panel hiển thị nhân vật + 6 stat chính. Labels:

```java
String[][] labels = {
    {"Công", "C.Xác", "S.Lực"},   // Công / Chính xác / Sức lực
    {"P.Thủ", "N.Tránh", "C.Mạng"} // Phòng thủ / Né tránh / Chí mạng
};
```

6 stats hiển thị: `lh.x` (Công), `lh.B` (C.Xác), `lh.r` (S.Lực), `lh.z` (P.Thủ), `lh.A` (N.Tránh), `lh.C` (C.Mạng %).

Khi equip/unequip, panel so sánh stat mới vs stat cơ bản (`this.h`):
- Stat tăng → **green** (`com.mg.sq.a.g`)
- Stat giảm → **red** (`com.mg.sq.a.h`)
- Không đổi → **white** (`bx.d`)

### 5.2 Equip Item (`cz.a(ll)`)

```java
public void a(ll equip) {
    if (equip == null) return;

    ll[] equipped = this.a.D;
    boolean replaced = false;

    // Tìm slot cùng type → thay thế
    for (int i = 0; i < equipped.length; i++) {
        if (equipped[i].e == equip.e) {
            if (equipped[i].b == equip.b) return;  // same item, skip
            this.a.D[i] = equip;   // replace in slot
            replaced = true;
            break;
        }
    }

    // Nếu chưa có slot cùng type → thêm mới vào array
    if (!replaced) {
        ll[] newArr = new ll[this.a.D.length + 1];
        System.arraycopy(this.a.D, 0, newArr, 0, this.a.D.length);
        newArr[this.a.D.length] = equip;
        this.a.D = newArr;
    }

    // Rebuild visual: e != 3 (boots) thì update character sprite
    this.a(this.a, equip.e != 3);
}
```

### 5.3 Unequip Item (`cz.b(ll)`)

```java
public void b(ll equip) {
    if (equip == null) return;

    // Remove from equipped array
    for (int i = 0; i < this.a.D.length; i++) {
        if (this.a.D[i].equals(equip)) {
            // Shrink array by 1, removing index i
            break;
        }
    }

    // Rebuild visual if not boots
    if (equip.e != 3) {
        this.b = mb.a(this.a, false);   // rebuild character sprite
        this.b.a(lc.a(this.a));          // apply color transform
        this.b.a(nr.a(this.a));          // apply animation
        this.b.c(2);                     // set frame/direction
    }

    // Recalculate stats
    this.a = com.mg.sq.a.a(this.a);
}
```

**Key insight:** Boots (e=3) KHÔNG trigger visual rebuild khi equip/unequip.

---

## 6. Body Compositor (`mb.java`)

### 6.1 ResId → Band Conversion

```java
// Equipment sprite band (round down to nearest 10)
public static int a(ll equip) {
    int resId = equip.n;
    return resId - resId % 10;     // band = 97105 → 97100
}

// Equipment inventory icon ID
public static int a(int resId) {
    return resId - resId % 10 + 98;  // icon = 97105 → 97198
}
```

### 6.2 Character Equipment Loading

`mb.a(lh)` — hàm cốt lõi quyết định nhân vật trông thế nào:

```java
public static mb[] a(lh character) {
    int[] nArray = new int[4];

    // Scan equipped items, store resId band for each slot
    for (int i = 0; i < character.D.length; i++) {
        ll equip = character.D[i];
        if (equip.e < 4) {                          // CHỈ slot 0,1,2,3
            nArray[equip.e] = equip.n - equip.n % 10;  // store band
        }
    }

    mb[] result = new mb[4];

    // [1] Hair layer — from appearance, NOT equipment
    result[1] = new mb(character.V.a + 99);

    // [0] Armor layer (e=0) — or default body appearance
    int defaultArmor = character.U.a + 99;
    int defaultWeapon = (character.f == 1) ? 79999 : 79899;  // male/female

    result[0] = (nArray[0] > 0 && !character.Z)
                ? new mb(nArray[0] + 99)     // equipped armor
                : new mb(defaultArmor);       // naked body appearance

    // [2] Weapon layer (e=1) — or default weapon
    result[2] = (nArray[1] > 0)
                ? new mb(nArray[1] + 99)     // equipped weapon
                : new mb(defaultWeapon);      // default weapon

    // [3] Helmet layer (e=2) — or default helmet
    result[3] = (nArray[2] > 0)
                ? new mb(nArray[2] + 99)     // equipped helmet
                : new mb(89999);              // default helmet

    // NOTE: nArray[3] (boots e=3) is NEVER used for visual!
    return result;
}
```

### 6.3 Render Composition Layer Order

```
Layer 1: Base body      = frame + 99000 + body color    ← luôn hiện
Layer 2: Hair           = hair resId + frame + hair color ← appearance trait
Layer 3: Armor overlay  = armor resId + frame + body color ← equipment e=0
Layer 4: Helmet overlay = helmet resId + frame            ← equipment e=2
Layer 5: Weapon overlay = weapon resId + frame            ← equipment e=1
```

Default resource IDs:
- Default weapon male: `79999`, female: `79899`
- Default helmet: `89999`
- `character.Z` flag = true → ẩn armor (hiện body mặc định)

---

## 7. Equipment Network Parser (`ky.java`)

### 7.1 Parse Equipment from Packet

`ky.a(ku, int, int, boolean)` — line 1269-1306:

**Minimal mode** (`bl2 = false`): chỉ parse key, slot, resId, durability, enhancement.
**Full detail mode** (`bl2 = true`): parse tất cả fields + stats block.

| Tag | Field | Type | Default | Description |
|-----|-------|------|---------|-------------|
| — | `c` | String | — | Key string (server ID) |
| 84 | `e` | byte | 0 | Slot type |
| 4 | `n` | int | 0 | Resource ID |
| 139 | `p` | int | -1 | Current durability |
| 27 | `j` | int | 0 | Enhancement level |
| 26 | `d` | String | null | Display name |
| 135 | `i` | int | -1 | Required level |
| 15 | `f` | byte | 7 | Element icon |
| 16 | `h` | byte | 2 | Gender (0=M, 1=F, 2=Both) |
| 138 | `m` | byte | 0 | Rank/rarity |
| 144 | `q` | int | 0 | Max durability |
| 117 | `g` | String | null | Description |
| 156 | `s` | byte | -1 | (unknown) |
| 85 | `t` | byte | 1 | Tradeable (1=yes) |
| 190 | `k` | byte→long | -1 | Repair cost |
| 118-221 | `r` | lb | — | Stats block (see section 2) |

### 7.2 Stats Block Tags (lb)

| Tag | lb field | Stat |
|-----|----------|------|
| 118 | `a` | Cường lực (Strength) |
| 119 | `b` | Thân pháp (Agility) |
| 120 | `c` | Nội lực (Magic) |
| 121 | `d` | Thể lực (Vitality) |
| 72 | `e` | Sức tấn công (Attack) |
| 71 | `f` | Phòng thủ (Defense) |
| 126 | `g` | Chí mạng % (Critical Rate) |
| 124 | `h` | Né tránh (Dodge) |
| 47 | `i` | Sinh lực (HP bonus) |
| 200 | `j` | Hấp thu sát thương % |
| 201 | `k` | Đánh xuyên giáp % |
| 202 | `l` | Cản đòn % |
| 203 | `m` | Hồi sinh % |
| 204 | `n` | Sức tấn công % |
| 221 | `o` | Sinh lực % |

**Java evidence verified `2026-05-03`:** tag mapping `118/119/120/121/72/71/126/124/47/200/201/202/203/204/221` match trực tiếp `ky.java` parser line `1288-1302`.

**Lưu ý bug decompile:** Ở line 1287-1288, decompiler tạo 2 `new lb()` nhưng chỉ assign `lb2` vào `equip.r`. Field `a` bị assign cho object bỏ đi. Logic thật cần assign `lb2.a = tag 118`.

**Lưu ý decompiler alias:** Ở line 1273, code decompile có dạng `v0.n = ku2.a((short)4, n2, n3, 0);` trong khi object parse chính là equipment `ll`. Audit `2026-05-03` đánh dấu đây là **likely decompiler alias (`v0 == object`)**, không đủ evidence để coi là server/gameplay branch riêng. Khi viết parser remake, gán tag `4` trực tiếp vào `Equipment.ResourceId/ll.n`.

### 7.3 Character Equipment Array

```java
// Parse lh.D — equipped items array
lh2.D = new ll[ku2.a((short)83, n2, n3)];    // tag 83: equip count
for (int i = 0; i < lh2.D.length; i++) {
    lh2.D[i] = ky.a(ku2, cursor, nextCursor, false);  // minimal parse
}
```

### 7.4 Network Commands

| Cmd | Tên | Mô tả |
|-----|-----|-------|
| 51 | processPickupItem | Nhặt item/equip rơi → dispatch to `b.c(ll)` |
| 96 | requestUpgradeEquip | Check/yêu cầu nâng cấp. `ks.a().b(sessionKey, action, equipKey)` hoặc final send `ks.a().b(sessionKey, equipKeys[], itemIds[], itemQtys[], fee)` |
| 97 | modifiedUpgradeEquip | Kết quả nâng cấp → `b.d(key, msg, readyStatus, gold)` |
| 99 | requestCombineEquip | Check/yêu cầu kết hợp item/material. `ks.a().b(sessionKey, action, itemId, qty)` / `b.e(session, msg)` |
| 100 | modifiedCombineEquip | Kết quả kết hợp → `b.a(key, msg, status, gold)` |
| 112 | processEquipChange | Mặc/tháo trang bị → `b.a(equipKey, gold)` hoặc gold/count update; đọc tag `175` nhưng client không dùng text này |

#### Cmd `112` detail (`processEquipChange`)

Java evidence từ `ky.java` cmd `112`:

```java
String equipKey = packet.a((short)83);
if (equipKey != null) {
    String ignoredText = packet.a((short)175); // read but not used
    long gold = packet.a((short)157, 0L);
    b.a(equipKey, gold);
    return;
}

int goldAmount = packet.c((short)114, 0);
if (goldAmount > 0) {
    String ignoredText = packet.a((short)175); // read but not used
    int count = packet.c((short)106, 0);
    packet.a((short)157, 0L); // read/discard
    b.g(goldAmount, count);
}
```

Kết luận phục dựng:
- Tag `83`: equipment key string, nhánh equip/unequip sync chính.
- Tag `175`: string được client đọc nhưng không truyền tiếp vào callback; không được suy diễn là message bắt buộc UI nếu chưa có usage khác.
- Tag `157`: long gold/current-money-like value trong nhánh có equip key.
- Tag `114` + `106`: nhánh update gold amount/count.
- Server remake phải authoritative state; client chỉ sync key/gold callback.

### 7.5 Client Senders (`ks.java`)

Các sender sau là bằng chứng client-side về payload cần server tương thích:

| Flow | Java call | Payload suy luận |
|------|-----------|------------------|
| Equip/unequip sender case | `ks.java` switch case `84` | Sends tag `114` from `kw2.M`; audit `2026-05-03` xác nhận đây là nhánh sender equipment/economy-related, nhưng wrapper method cụ thể vẫn cần giữ theo overload Java khi serializer byte-perfect. |
| Equip/unequip from inventory | `ks.a().a(String[] equipKeys)` | Danh sách key trang bị đang mặc sau khi client đổi slot trong `hh`; server chốt thay đổi và trả cmd `112`. |
| Sell equipment | `ks.a().a(equipKey, price)` | Bán 1 trang bị đang chọn; giá nhập từ UI `hu`/sale dialog, key từ `ll.c`. |
| Sell item | `ks.a().a(itemId, quantity, price)` | Bán item consumable/material. |
| Upgrade/check equip | `ks.a().b(sessionKey, action, equipKey)` | Check material/fee cho 1 equipment trong blacksmith panel. |
| Upgrade/check item | `ks.a().b(sessionKey, action, itemId, quantity)` | Check material/fee cho item/material. |
| Upgrade final | `ks.a().b(sessionKey, equipKeys[], itemIds[], itemQtys[], fee)` | Final upgrade/combine request với toàn bộ nguyên liệu đã chọn. |
| Repair equipment | `ks.a().a(hammerItemId, equipKey)` | Sửa trang bị bằng item búa đã chọn trong `hh`/`hl`. |
| Buy shop products | `ks.a().a(int[] productIds)` | Mua 1 hoặc nhiều product trong shop `ia`; nếu nhiều item thì qua confirm cart `gx`. |
| Request shop category page | `ks.a().d(categoryId, alreadyLoadedCount)` | Lazy-load shop section. |
| Trade/transaction accept/reject stages | `ks.k()`, `ks.k(String)`, `ks.l(String)`, `ks.l()`, `ks.m()`, `ks.n()` | Các sender cmd `55/56` với state byte `T` và session/key tùy overload; liên quan panel giao dịch `of`, cần giữ mapping riêng khi phục dựng trade. |

**Rule phục dựng:** Client chỉ gửi key/id/qty/fee để yêu cầu; server phải authoritative cho ownership, durability, stats, fee, gold và kết quả upgrade/repair.

---

## 8. Equipment UI Components

### 8.1 Equipment Detail Dialog (`hg.java`)

```java
public hg(ll equip) {
    this.k = equip;

    // Load icon: band + 98
    int iconId = mb.a(equip) + 98;
    this.l = pa.a().a(iconId, false);

    // Gender restriction check
    if (equip.h != 2 && equip.h != go.k.f)
        this.r = true;   // wrong gender warning

    // Stat lines
    this.q = com.mg.sq.a.a(equip);   // String[] stat lines

    // Buttons
    "Bỏ qua" → dismiss (cmdId 1000)
    "Nhặt"   → pick up (cmdId 2000)
}
```

Render (`hg.c(Graphics)`):
- Icon 32×32 vẽ tại vị trí cố định
- Enhancement `+N` → gradient font nếu `j > 0`
- `"Yêu cầu cấp: X"` → **đỏ** nếu player level < required
- `"Độ bền: P/Q"` → **đỏ** nếu `p < q * 30 / 100` (dưới 30%)
- Gender text: `"Đồ giành cho NAM"`, `"Đồ giành cho NỮ"`, `"Giành cho cả NAM & NỮ"`
- Rank text: `"Đồ thường"`, `"Đồ tốt"`, `"Đồ hiếm"`, `"Huyền thoại"`
- Element icon hiển thị từ `equip.f`

### 8.2 Item/Equipment Tooltip (`fw.java`)

`fw` dùng chung cho cả equipment (`ll`) và item (`lm`):

```java
public fw(Object obj) {
    if (obj instanceof ll) {
        // Equipment tooltip
        ll equip = (ll) obj;
        this.k = equip.d;                        // name
        this.l = ll.a(equip.m);                  // rank color
        this.m = com.mg.sq.a.a(equip);           // stat strings
        if (equip.j > 0) this.n = "+" + equip.j; // enhancement
        if (equip.g != null) this.o = equip.g;   // description
    } else if (obj instanceof lm) {
        // Item tooltip
        lm item = (lm) obj;
        this.k = item.d;
        this.l = bx.d;  // white
        if (item.h != null) this.o = item.h;     // description
    }
}
```

### 8.3 Item Cell Renderer (`dc.java`)

```java
public void a(Graphics g, int x, int y) {
    switch (this.j) {   // type: 0=equipment, 1=item, 2=material
        case 0:  // EQUIPMENT
            g.drawImage(this.l, x, y, 0);                      // icon image
            if (equip.p == 0)                                   // durability = 0
                g.drawImage(broken_heart_icon, ...);            // broken overlay
            if (equip.m == 4 || equip.m == 7 || equip.m == 8)  // legendary ranks
                drawStarEffect(g, x, y);                        // animated star
            if (equip.j > 0)                                    // enhanced
                drawText("+" + equip.j, gradient_color, ...);  // enhancement text

        case 1:  // CONSUMABLE ITEM
        case 2:  // MATERIAL
            pc.g(g, item.j, x, y, 0);          // item icon from sprite sheet
            drawText("" + item.g, ...);          // quantity text
    }
}
```

### 8.4 Inventory/Equipment Screen (`hh.java`)

`hh` là màn hình hành trang chính, vừa hiển thị bag (`go.l/go.m`) vừa giữ 6 ô trang bị đang mặc (`dc[] F = new dc[6]`).

#### 8.4.1 Khởi tạo danh sách trang bị

- Lấy `go.l` làm danh sách equipment trong inventory.
- Nếu `go.l[n]` trùng key với `M.D[]` (đang mặc trên nhân vật):
  - Nếu `e != 8`: đưa vào `F[e] = new dc(icon, equip, 0, theme)`.
  - Nếu `e == 8`: vẫn không đưa vào 6 ô `F` (bùa/slot đặc biệt xử lý riêng).
- Equipment không đang mặc → add vào bag grid `v`.
- Icon luôn load bằng `mb.a(equip) + 98`.

#### 8.4.2 Equip/Unequip flow

Khi chọn equipment trong bag:
- Điều kiện mặc:
  - `equip.i <= character.G` (đủ level).
  - `equip.h == 2 || equip.h == character.f` (đúng giới tính hoặc unisex).
- Nếu đủ cả 2 điều kiện → gọi `a(equip)` để bật softkey/menu mặc.
- Khi xác nhận mặc:
  1. `n2 = equip.e`.
  2. `equip.o = -1L`.
  3. Nếu `F[n2]` đang có đồ cũ, đồ cũ được trả về bag grid.
  4. `F[n2] = selectedCell`.
  5. Gọi `a(M, F)` để rebuild `M.D` từ `F[]`.
  6. Gửi server danh sách key đang mặc bằng `ks.a().a(stringArray)`.
- Khi tháo:
  1. Lấy `dc2 = F[w]`.
  2. `F[w] = null`.
  3. Add `dc2` về bag grid.
  4. Rebuild `M.D` bằng `a(M, F)`.

`a(lh, dc[])` trong `hh` tạo lại `ll[]` chỉ từ các cell `j == 0`; sau đó gọi pipeline visual/stat để nhân vật preview cập nhật.

#### 8.4.3 Context menu / actions

Menu cho equipment (`dc.j == 0`) phụ thuộc trạng thái:
- Nếu đang ở bag và đủ điều kiện mặc: `"Mặc"` / `"Dùng"` với slot đặc biệt `e == 8`.
- `"Nâng cấp"` → mở blacksmith/upgrade flow.
- `"Rao bán"` chỉ hiện nếu `equip.a()` (`t == 1`, tradeable).
- `"C.Tiết"` → mở detail dialog `com.mg.sq.a.a(ll, ...)`.
- `"Vứt bỏ"` → confirm `"Bạn có chắc là muốn vứt bỏ ... này không?"`.
- Nếu equipment damaged + repairable (`b() && c()`): softkey `"Sửa chữa"` mở `hl`.

#### 8.4.4 Render rules

- Vẽ slot backgrounds cho `u[]`, icon slot từ sprite `k`.
- Vẽ các equipped cells `F[n]` tại tọa độ `u[e]`.
- Nếu đang chọn equipment trong bag, highlight ô target slot `u[e]` bằng 3 viền vàng.
- Capacity text hiển thị `v.s()/go.n`.

### 8.5 Repair Dialog (`hl.java`)

`hl` là dialog chọn item búa sửa chữa cho 1 trang bị.

- Chỉ mở nếu `equip.b()` và `equip.c()`:
  - `b()` = `q > 0 && p < q` (đang hỏng/mòn).
  - `c()` = `k > 0` (có repair cost).
- Nếu không còn búa sửa:
  - Hiện text `"Hiện tại bạn không còn cây búa nào để sửa chữa! Bạn có muốn vào cửa hàng mua không?"`.
  - Softkey `"C.Hàng"` mở shop.
- Khi chọn búa:
  - `ks.a().a(hammerItemId, equipKey)`.
  - `P.d(1)` chuyển repair UI sang trạng thái chờ.
- Broken icon trong bag vẫn do `dc.java` vẽ khi `p == 0`.

### 8.6 Shop Equipment Preview/Buy (`ia.java`, `lq.java`)

`lq` là product wrapper:
```java
public final class lq {
    public int a;      // product id
    public String b;   // product/category name?
    public byte c;     // product type
    public int d;      // price
    public Object e;   // ll / lm / lu
    public String f;   // extra text?
    public long g;     // extra numeric?
}
```

Shop `ia` giữ:
- `lq[] y = new lq[4]`: các trang bị đang mặc thử theo slot `0..3`.
- `boolean[] z = new boolean[4]`: slot nào là đồ mới chọn mua.
- `ll[] u = new ll[4]`: đồ thật nhân vật đang mặc ở slot `0..3`.
- `cz s`: panel preview nhân vật.

Menu cho product equipment:
- Nếu đúng giới tính và đang mặc thử item đó: `"Cởi ra"`, `"Mua"`, `"C.Tiết"`.
- Nếu đúng giới tính và chưa mặc thử: `"Mặc thử"`, `"Mua"`, `"C.Tiết"`.
- Nếu sai giới tính: chỉ `"Mua"`, `"C.Tiết"`.

Flow mặc thử:
- `"Mặc thử"`:
  - Nếu slot đã mặc thử item khác, reset highlight item cũ.
  - `y[slot] = product`, `z[slot] = true`.
  - `cz.a(ll)` để preview visual/stat.
- `"Cởi ra"`:
  - `y[slot] = null`, `z[slot] = false`.
  - Nếu slot có đồ thật `u[slot]`, preview lại đồ thật; nếu không thì `cz.b(ll)` tháo khỏi preview.
- `"Giỏ hàng"` / `"Đóng"`:
  - Gom các `y[i] != null && z[i]` vào cart.
  - Nếu cart rỗng: `"Giỏ hàng trống. Bạn chưa chọn món đồ nào"`.
- Nếu có cart: `gx` confirm, `"Mua"` gửi `ks.a().a(productIds[])`.
- `gx` hiển thị tối đa theo mảng `lq[]` cart:
  - Mỗi product equipment lấy `ll ll2 = (ll)lq.e`.
  - Icon = `mb.a(ll2) + 98`.
  - Stat lines = `com.mg.sq.a.a(ll2)`.
  - Tên trang bị dùng màu rank `ll.a(ll2.m)`.
  - Tổng tiền = sum `lq.d`, render `"Tổng tiền: X"`.
  - Phím trái/phải đổi item đang xem; `"Bỏ qua"` cmd `1000`, `"Nhặt"`/buy cmd `2000`.

Buy response:
- Với equipment: clone `((ll)product.e).d()`, set `p = q == 0 ? -1 : q`, set server key từ `stringArray[n]`, rồi `go.a(ll)` add vào inventory.
- Với item: clone `lm`, add quantity theo server result.
- Sau mua thành công single equipment: popup `"Bạn vừa mua thành công! " + equipName`.

### 8.6b Trade/Transaction Equipment Panel (`of.java`)

`of` là panel giao dịch/transaction có thể chứa equipment (`ll`) và item (`lm`) thông qua cell `dc`.

Các đoạn đã xác nhận liên quan equipment:
- Khi nhận/cập nhật 1 equipment từ giao dịch:
  ```java
  public final void a(ll equip) {
      this.e(true);
      this.Z = 125;
      this.w(); // reset reject/accept flags nếu cần
      dc cell = new dc(this.Y.a(mb.a(equip) + 98, true), equip, 0, this.al);
      this.l.a(cell);
      this.ab = 10;
      this.aa = true;
      this.a(this.A + " vừa cập nhật " + equip.d, (byte)0, com.mg.sq.a.g);
  }
  ```
- Khi nhận danh sách kiểm tra giao dịch (`a(ll[] llArray, lm[] lmArray, int ken)`):
  - Clear list hiện tại (`this.l.t()`), reset chat/log.
  - Add từng equipment bằng `new dc(assetLoader.a(mb.a(equip)+98,true), equip, 0, theme)`.
  - Log từng dòng `"- " + equip.d`.
  - Add item theo stack rule giống inventory.
  - Log `"- Số KEN giao dịch: " + formatKen`.
- Khi lấy lại/remove equipment khỏi giao dịch:
  ```java
  public final void a(String equipKey) {
      for each dc in this.l:
          if (dc.j == 0 && ((ll)dc.k).c.equals(equipKey)) {
              log(this.A + " vừa lấy lại " + equip.d);
              this.l.l(index);
              return;
          }
  }
  ```
- Nếu giao dịch bị từ chối/chưa chấp nhận (`w()`), UI log `"----- Không chấp nhận -----"` và reset các flag `N/O/P`.

Rule phục dựng:
- Trade panel chỉ dùng equipment model/icon/cell renderer chung; không tạo slot/equip rule mới.
- Equipment trong trade xác định bằng key `ll.c`.
- Không gộp sender trade `ks.k/l/m/n...` vào equip/unequip/forge sender; đây là flow session riêng của giao dịch.

### 8.7 Upgrade Panel (`id.java`)

3 grid panels:
- `p` (inventory grid) — hiện trang bị/item trong túi đồ, trừ đang mặc
- `q` (materials grid, 5 slots) — nguyên liệu bỏ vào nâng cấp
- `r` (result grid, 1 slot) — kết quả nâng cấp

**Upgrade flow:**

1. Player mở panel → inventory grid load `go.l` (bag equips) + `go.m` (items).
2. Chọn trang bị/item từ `p` → move vào `q`.
3. Client hỏi server check ready/material:
   - Equip: `ks.a().b(sessionKey, byte action, equipKey)`
   - Item: `ks.a().b(sessionKey, byte action, itemId, quantity)`
4. Server trả key/message/status/fee:
   - `N == 0`: chưa đủ nguyên liệu → popup `"Chưa đủ nguyên liệu. Vui lòng thử lại!!!"`
   - `N == 1`: đủ điều kiện → nếu đủ tiền thì hỏi confirm.
5. Bấm `"Nâng cấp"` → send all selected equip keys + item IDs/quantities + fee:
   ```java
   ks.a().b(this.I, equipKeys, itemIds, itemQtys, this.O);
   ```
6. Kết quả trả về `ll[]` + `lm[]` + status byte:
   - `by2 == 1` → `"Nâng cấp thành công"`
   - ngược lại → `"Nâng cấp thất bại"`
7. Client remove nguyên liệu khỏi `go.l/go.m`, add kết quả vào `go.l/go.m`, show ở result grid `r`, button đổi thành `"Tiếp tục"`.

**UI strings:**
- `"Nâng cấp"`
- `"Tiếp tục"`
- `"Phí kết hợp: X KEN"`
- `"Nâng cấp thành công"`
- `"Nâng cấp thất bại"`
- `"Chưa đủ nguyên liệu. Vui lòng thử lại!!!"`
- `"Vượt quá số tiền bạn đang có. Vui lòng thử lại!!!"`
- `"{message}. Bạn có muốn nâng cấp không?"`

**Grid navigation matrix (`E`)**:
```java
E[0] = { 1, -1, 2, -1 };
E[1] = { 2,  0, 2, -1 };
E[2] = { 3,  2, 3,  0 };
E[3] = {-1,  2,-1,  2 };
```

### 8.8 Combine Panel Variant (`ho.java`)

`ho` là biến thể panel kết hợp khác với `id` nhưng dùng cùng pattern: inventory grid + material/result grid + server check + final send.

Cấu trúc UI:
- `p`: inventory grid, chứa equipment không đang mặc + item/material.
- `q`: material/result grid 6 ô.
- `C`: button `"Kết hợp"`.
- Fee render: `"Phí kết hợp: X KEN"`.
- Result string:
  - `by2 == 1` → `"Kết hợp thành công"`
  - khác `1` → `"Kết hợp thất bại"`

Inventory source:
```java
this.w = go.k.a();                         // clone current character
int capacityCount = go.l.length - w.D.length;
...
// Add only equipment not currently equipped into p
dc iconCell = new dc(assetLoader.a(mb.a(equip) + 98, true), equip, 0, theme);
```

Check material/result senders:
```java
// Move selected equip from p -> q
ks.a().a(sessionKey, (byte)0, equipKey);

// Remove selected equip from q -> p
ks.a().a(sessionKey, (byte)1, equipKey);

// Move selected item/material from p -> q with selected quantity
ks.a().a(sessionKey, (byte)0, itemId, quantity);

// Remove item/material from q -> p
ks.a().a(sessionKey, (byte)1, itemId, existingSelectedQty);
```

Final combine send:
```java
ks.a().a(sessionKey, equipKeys[], itemIds[], itemQtys[], fee);
```

Server check response handling:
- `a(String equipKey, String msg, byte ready, long fee)` moves matching equipment `q -> p` after server response.
- `b(String equipKey, String msg, byte ready, long fee)` moves matching equipment `p -> q`.
- Item variants merge/split stack quantities by `lm.l` stack size.
- Nếu `ready == 0` và chưa kết hợp xong: popup `"Chưa đủ nguyên liệu. Vui lòng thử lại!!!"`.
- Nếu `ready == 1`: kiểm tra `go.s` đủ tiền, rồi confirm `"{message}. Bạn có muốn kết hợp không?"`.

Final result response:
```java
public void a(ll[] resultEquips, lm[] resultItems, byte status) {
    // remove selected materials from go.l/go.m
    // clear q
    // add returned result equipment/items into q and global inventory
    this.Q = status == 1 ? "Kết hợp thành công" : "Kết hợp thất bại";
    this.O = true;
}
```

Rule phục dựng:
- `id.java` và `ho.java` không nên gộp mù thành một flow nếu server command/session khác nhau; giữ abstraction chung `ForgePanel` nhưng mapping sender/command phải bám đúng Java source.
- Equipment đang mặc không được đưa vào grid nguyên liệu combine/upgrade trực tiếp; UI loại bằng cách so key với `w.D`.

---

## 9. Organized Asset Folder Structure

Asset equipment đã được gộp lại theo folder slot lớn ngày 2026-05-03 để resolver đơn giản và tránh cây thư mục quá sâu. Các folder audit cũ `01_default_overlays/`, `02_armor_e0/`, `03_weapon_e1/`, `04_helmet_e2/`, `07_accessory_e5_e7_e8/`, `08_premium_sets/`, `09_ui_icons/` đã được copy vào nhóm mới rồi xóa.

```
client/assets/equipment/
│
├── default/       32 PNG  — default overlays khi không mặc đồ (weapon 798/799xx, helmet 899xx)
├── armor/        363 PNG  — `ll.e == 0`, visual armor/body overlays
├── weapon/       220 PNG  — `ll.e == 1`, visual weapon overlays
├── helmet/       187 PNG  — `ll.e == 2`, headgear/head-overlay candidates
├── accessory/     56 PNG  — `ll.e == 5,7,8`, ring/amulet/accessory icons/effects; stats-only trong compositor hiện biết
├── premium/      110 PNG  — premium/full-set bands; server quyết định `ll.e` thật
├── ui/             6 PNG  — broken_heart, star, slotlock, blacksmith, effblacksmith, repair hammer 30099
├── index.csv              — manifest CSV: `file,id,numericId,band,group`
├── equipment_manifest.json — manifest JSON tương đương `index.csv`
└── README.md
```

**Tổng sau gộp:** 974 PNG.

Rule quan trọng:
- Folder vật lý chỉ là lookup hint cho asset resolver; gameplay slot vẫn phải lấy từ `ll.e`.
- `premium/` không tự động có nghĩa là slot riêng; server packet/detail phải cung cấp slot thật.
- `index.csv` / `equipment_manifest.json` dùng để tra `band` và group mà không cần scan thư mục runtime.

## 10. NOT in Equipment Folder / Needs Re-audit

Những band dưới đây **không được coi là equipment chắc chắn** nếu chưa có `ll.n` từ server/meta xác nhận:

| Band | Trạng thái | Lý do |
|------|------------|-------|
| `911xx`, `913xx` | Exclude | Hiệu ứng đặc biệt, không đi qua `mb.a(lh)` equipment compositor |
| `100xxx` | Exclude | Thuốc/nguyên liệu thuộc `lm` GameItem |
| `110xxx-140xxx` | Mixed / cần audit bằng key server | Trước đây có ghi map/NPC; một phần `12xxxx-14xxxx` có thể là icon accessory do suffix `98`; chỉ giữ nếu match equipment icon convention và có bằng chứng |
| `1M/2M/4Mxxxxxxx` | Exclude | Skill/effect assets |

**Rule bảo toàn:** asset resolver không tự suy đoán slot từ folder. Slot gameplay phải lấy từ `ll.e`; folder chỉ là physical path để resolve image.

---

## 11. Port Plan / Implementation Order

### Phase 1 — Code Checklist: Backend Equipment Foundation (KHÔNG đụng gameplay chưa chắc)

Mục tiêu phase 1 là dựng nền dữ liệu/protocol an toàn để các phase sau có thể mặc/tháo, hiển thị icon và cộng stat. Phase này **chưa implement upgrade/combine/drop/shop/repair hoàn chỉnh**, chưa tự seed option đặc biệt chưa có bằng chứng.

#### 11.1 Scope được phép làm trong Phase 1

- [x] **Core enums/value objects**
- `EquipmentSlotIds` chỉ expose đúng **5 nhóm equipment gameplay hiện tại**, đặt tên rõ ràng, không dùng prefix `SlotN` và không khai báo các slot thừa:
  - `Armor`
  - `Weapon`
  - `Helmet`
  - `Ring`
  - `Wing`
- Các raw id khác từ Java `ll.e` vẫn được tài liệu hóa ở bảng evidence `ll.a[]`, nhưng chưa đưa vào code gameplay phase đầu khi chưa có template/server evidence thật.
- Source note: armor/weapon/helmet bám Java visual/compositor; ring/wing là quyết định remake từ gameplay memory/user confirmation ngày `2026-05-03`, raw-id mapping có thể điều chỉnh nếu dump template/server evidence sau này chỉ ra khác.
- Không tạo `EquipmentGender` enum trong code Phase 1; giữ raw numeric `ll.h`: `0=Nam`, `1=Nữ`, `2=Cả hai`.
- Không tạo `EquipmentRankIds`/enum rank trong code vì Java chỉ lưu raw `ll.m` và `ll.a(int rank)` switch trực tiếp theo số (`0/1/2/3/4/7/8`) để quyết định màu/UI; `PlayerEquipmentDefinition.Rank` phải giữ nguyên `int`.
  - Comment nguồn: `ll.java`, `hh.java`, `mb.java`.
  - Implemented Phase 1 in `server/Twelve.Core/Players/PlayerEquipmentDefinition.cs`.

- [x] **Domain model / entity**
  - Tạo backend/domain model cho equipment instance bám `ll.java` fields:
    - `Key`, `SlotType`, `ResourceId`, `EnhancementLevel`, `CurrentDurability`, `MaxDurability`, `Rank`, `RequiredLevel`, `Gender`, `ElementIcon`, `Name`, `Description`, `Tradeable`, `RepairCost`, `Stats`.
  - Không thêm field policy remake (`IsRepairable`, `IsUpgradeable`, `RepairBlockReason`) vào Phase 1 cho tới khi có template evidence hoặc policy server chính thức.
  - Không bỏ các field Java chưa rõ (`b/l/o/s/u`) khỏi tài liệu; khi code phase 1 chỉ map field nào cần lưu/serialize, field unknown ghi comment `Unknown from ll.java`.
- Implemented as immutable records / raw numeric id constants in `PlayerEquipmentDefinition.cs` and exposed through runtime view contracts.

- [x] **Stats model**
  - Tạo `EquipmentStats` bám `lb.java` đủ 15 fields:
    - `Strength`, `Agility`, `Magic`, `Vitality`, `Attack`, `Defense`, `CriticalRate`, `Dodge`, `Hp`, `DamageAbsorbPercent`, `ArmorPiercePercent`, `BlockPercent`, `RevivePercent`, `AttackPercent`, `HpPercent`.
  - Tag mapping phải ghi trong comment/source doc:
    - `118/119/120/121/72/71/126/124/47/200/201/202/203/204/221`.
  - Sửa đúng decompile bug khi parse: tag `118` phải assign vào `lb.a` / `Strength`, không tạo object bỏ đi.
  - Phase 1 code keeps existing `PlayerStatModifier` for runtime bonuses and documents the full `lb.java` stats/tag set for next packet serializer step; special percent stats remain out-of-scope for combat until battle source confirms formulas.

- [x] **Database schema tối thiểu**
  - Tách bảng/template và instance:
    - `EquipmentCatalog`: base template/catalog data hiện có trong project, tương đương template foundation.
    - `PlayerEquipment`: owned equipment instance có key riêng, durability/current stat roll/enhancement/tradeable/repairable qua `RawJson` + catalog fields.
  - Inventory default capacity vẫn `50` theo `go.n`.
  - Broken rule lưu bằng `CurrentDurability == 0`, không xóa item.
  - Không tạo hard-coded template lớn nếu chưa có dump; chỉ schema + seed tối thiểu/test nếu cần.
  - Implemented schema/seed in `server/Database/Equipment/equipment_schema.sql` and `server/Database/Equipment/equipment_seed.sql`.

- [x] **DTO/API contract foundation**
  - Dùng `record` DTO theo rule .NET.
  - DTO phải phân biệt:
    - Minimal equipment view tương đương `ky.a(..., bl2=false)` cho equipped array/list nhanh.
    - Full equipment detail tương đương `ky.a(..., bl2=true)` cho detail/drop/forge result.
  - Trường `Stats` nullable-safe; nếu null thì client/status không cộng.
  - Implemented Phase 1 runtime view extension in `PlayerRuntimeContracts.cs`; explicit minimal/full packet DTO split remains reserved for binary protocol layer.

- [x] **Parser/serializer/service mapping**
  - Mapping packet/tag bám `ky.java`:
    - Minimal: key, tag `84` slot type, tag `4` resource id, tag `139` current durability, tag `27` enhancement.
    - Full: thêm `26/135/15/16/138/144/117/85/190/156` + stats tags.
  - Nếu chưa có binary protocol server hoàn chỉnh, tạo mapper nội bộ + unit test shape trước; chưa giả lập byte-perfect nếu thiếu protocol framework.

- [ ] **Asset resolver constants phía client/shared**
  - Giữ công thức Java:
    - `band = resId - resId % 10`
    - `iconId = band + 98`
    - frames `band + 0..9`
  - Resolver chỉ lookup theo manifest/path hiện có; không quyết định gameplay slot từ folder.
  - Đã có `client/src/screens/character/shared/equipmentAssets.generated.ts` trỏ folder mới, gồm `EQUIPMENT_ICON_ASSETS_BY_BAND` và `EQUIPMENT_LAYER_ASSETS_BY_BAND`; phase 1 dùng lại, không suy slot từ folder.

- [ ] **Verification bắt buộc cho Phase 1**
  - Backend có sửa code server thì chạy `dotnet build` theo workflow hidden/redirect.
  - Client có sửa TypeScript thì chạy local compiler:
    - `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit`
  - Không báo hoàn tất nếu chưa cập nhật tài liệu + changelog.
  - 2026-05-03: `dotnet build Twelve.sln` pass sau cleanup: `0 Warning(s), 0 Error(s)`.

#### 11.2 Out of Scope Phase 1 (để tránh tự bịa logic)

- [ ] Chưa implement công thức combat cho `DamageAbsorb/Pierce/Block/Revive/HpPercent`.
- [ ] Chưa seed đại trà stat đặc biệt chưa thấy trên item thật.
- [ ] Chưa implement upgrade hard-mode/consume material/destroy; chỉ chuẩn bị field `EnhancementLevel`.
- [ ] Chưa implement combine recipe.
- [ ] Chưa implement shop/drop pool thật nếu chưa có template/dump; chỉ chuẩn bị schema.
- [ ] Chưa render slot cánh/event vào UI chính nếu chưa xác định UI gốc.
- [ ] Chưa dùng folder `premium/accessory` để suy slot hoặc cộng stat.

#### 11.3 Phase 1 Acceptance Criteria

- [x] Có model/DTO/schema đủ biểu diễn một equipment Java `ll` + stats `lb`.
- [x] Có mapping minimal/full equipment detail rõ nguồn từ `ky.java`.
- [x] Có rule null/durability/gender/level documented trong code comment nhưng chưa cần endpoint mặc đồ nếu chưa sang Phase 2.
- [x] Không có logic nào tự ý hiện đại hóa khác Java/client evidence.
- [x] Build/check pass với phần code đã sửa.

### Phase 2 — Inventory & Equip Rules

- [ ] Inventory state:
  - `go.l` equivalent = bag equipment.
  - `go.m` equivalent = bag items.
  - `go.k.D` equivalent = equipped array.
  - Capacity default = `50`.
- [ ] Full check bám `go.b()`:
  - Count bag equips excluding equipped.
  - Item type `e == 7` count by quantity.
  - Other items count as 1 slot.
- [ ] Equip/unequip bám `cz.a(ll)` / `cz.b(ll)`:
  - One equipped item per `ll.e`.
  - Replace same slot.
  - If same internal `b` skip.
  - Boots `e == 3` do not rebuild visual.
- [ ] Sorting bám `gp.java`.

### Phase 3 — Stat Pipeline

- [ ] Character stat aggregation bám `com.mg.sq.a.a(lh)` + `da.a(lh)`:
  - Add flat stat fields.
  - Apply `AttackPercent` as `baseAttack * percent / 100`.
  - Ignore equipment stat when durability `p == 0` in status/combat-facing aggregation unless a specific Java path proves otherwise.
  - Do not invent formulas for `DamageAbsorb`, `Pierce`, `Block`, `Revive`, `HpPercent` until battle Java source confirms usage.
- [ ] UI stat lines bám `com.mg.sq.a.a(ll)`:
  - Only show non-zero stats.
  - Preserve Vietnamese labels and percent formatting.

### Phase 4 — Asset Resolver & Compositor

- [ ] Asset resolver:
  ```ts
  function getEquipmentBand(resId: number): number {
    return resId - (resId % 10);
  }

  function getEquipmentIconId(resId: number): number {
    return getEquipmentBand(resId) + 98;
  }

  function getEquipmentFrameIds(resId: number): number[] {
    const band = getEquipmentBand(resId);
    return Array.from({ length: 10 }, (_, i) => band + i);
  }
  ```
- [ ] Character compositor bám `mb.a(lh)`:
  - Armor `e=0`, weapon `e=1`, helmet `e=2`.
  - Boots `e=3` read but not drawn.
  - Hair/body appearance remain from character traits, not equipment.
  - Defaults: male weapon `79999`, female weapon `79899`, helmet `89999`.
- [ ] Do not use accessory/boot/mount assets as body overlay unless Java source or server payload confirms a separate compositor path.

### Phase 5 — UI Reconstruction

- [ ] Inventory screen bám `hh.java`:
  - Bag equipment from `go.l`, equipped cells `F[e]`.
  - Equip/unequip replacement and server sync with equipped key array.
  - Context actions: wear/use, upgrade, repair, sale listing, detail, discard.
  - Target-slot yellow highlight while selecting equipment.
- [ ] Repair dialog bám `hl.java`:
  - Only available when `b() && c()`.
  - Uses hammer item id + equip key.
  - Empty hammer message and shop shortcut.
- [ ] Shop preview/buy bám `ia.java` + `gx.java` + `lq.java`:
  - Product wrapper with `e` as `ll/lm/lu`.
  - Try-on only for correct gender equipment.
  - Cart stores selected new `lq`; `gx` confirms multiple product purchase with icon/stat/total price.
  - Buy response clones `ll`, sets durability full, assigns server key, then adds to `go.l`.
- [ ] Equipment detail dialog bám `hg.java`:
  - Icon = `band + 98`.
  - Required level warning.
  - Durability red below 30%.
  - Gender warning.
  - Rank color from `ll.a(rank)`.
- [ ] Tooltip bám `fw.java`.
- [ ] Inventory cell bám `dc.java`:
  - Broken heart overlay when `p == 0`.
  - Star effect when rank `4/7/8`.
  - Enhancement text when `j > 0`.
- [ ] Upgrade/combine panels bám `id.java` + `ho.java`:
  - Inventory/material/result grids.
  - Fee `KEN`.
  - Ready status.
  - Result messages.
  - Keep `id` upgrade sender mapping and `ho` combine sender mapping separate unless server protocol proves they are identical.
- [ ] Trade/transaction panel bám `of.java`:
  - Equipment list uses `dc` with icon `mb.a(equip)+98`.
  - Received/reclaimed equipment keyed by `ll.c`.
  - Trade command/session sender mapping from `ks.java` must remain separate from equip/unequip and forge.

---

## 12. Confirmed Reconstruction Decisions

- `ll.e` là slot type authoritative.
- `ll.n` là resource ID authoritative cho icon/frame lookup.
- `ll.f` / tag `15` là element icon ID/hệ trang bị dùng cho UI; screenshot user ngày `2026-05-03` xác nhận equipment có icon hệ trước tên item.
- Equipment icon convention: `iconId = band + 98`.
- Equipment visual frame convention: `frameIds = band + 0..9`.
- Only slots `0`, `1`, `2` are actually composited visually; slot `3` is explicitly ignored for drawing.
- Rank color and legendary star conditions must preserve Java exact ranks (`4`, `7`, `8`).
- Stats block has 15 fields; older docs missing `s` tag `156` and `lb.o` tag `221` were incomplete.
- `hh.java` confirms inventory screen has only `dc[] F = new dc[6]` physical equipped cells; slots outside that range (notably `e==8`) must not be blindly rendered into the main 6-slot UI.
- `ia.java` confirms shop try-on only tracks slots `0..3`; it is preview-only until server returns bought equipment key.
- `hl.java` confirms repair is item-driven (hammer item id + equipment key), not a direct gold-only operation, even though equipment has `repairCost`.
- `da.java` confirms equipment with durability `p == 0` is skipped when status panel aggregates equipped stats.
- `ho.java` confirms combine has its own panel/sender flow and should not be treated as fully identical to `id.java` upgrade.
- `ky.java` command `96/97/99/100/112` response handlers confirm the authoritative server callback shape for upgrade/combine/equip-change UI sync:
  - `96` request-upgrade response: session tag `186`, equip key tag `83`, message tag `1`.
  - `97` modified-upgrade result: mode/status tag `187`, equip key tag `83`, optional item id/count tags `114/106`, gold tag `132`, message tag `1`, ready status tag `188`.
  - `99` request-combine response: session tag `186`, message tag `1`.
  - `100` modified-combine result: same result layout as `97`, but dispatches combine callbacks.
  - `112` equip-change response: equip key tag `83`, ignored text tag `175`, gold tag `157`, optional item id/count tags `114/106`.
- `com/mg/sq/a.java` and search cross-check confirm `lb.n` (`AttackPercent`) and `lb.o` (`HpPercent`) are displayed by equipment UI; only `lb.n` is currently proven to affect character stat aggregation in `com.mg.sq.a.a(lh)`. Do not silently apply `lb.o` to HP until battle/status Java evidence confirms the exact formula.
- Special fields `lb.j/k/l/m` (`DamageAbsorb`, `ArmorPierce`, `Block`, `Revive`) are parsed/displayable equipment stats, but current audited client-side status aggregation does not apply them. Their combat effect remains battle-system-dependent.
- `hn.java`/`hq.java` search confirms additional market/trade-like screens can wrap `ll` inside `lq` and display equipment details/actions, but they do not change core `ll`/`lb`/`mb` equipment model rules.
- `gx.java` confirms cart confirm dialog for shop multi-buy uses existing equipment icon/stat/rank rules only; no new model rule.
- `of.java` confirms trade/transaction panel uses `dc` equipment cells and key-based add/remove/reclaim behavior; no new slot/stat/compositor rule.
- The `.agent/skills/` section from older draft was removed because it was not Java source evidence and could mislead reconstruction.

### 12.1 Decompiler Clone Bugs Summary

Audit `2026-05-03` xác nhận pattern decompiler CFR 0.152 tạo throwaway object trong clone methods. Khi remake, phải copy đúng tất cả fields vào object chính; không tái hiện bug.

#### `ll.d()` — Equipment clone (ll.java line 63-81)

```java
ll ll2 = new ll(this.c, this.e);      // ll2 gets c,e via constructor
new ll(this.c, this.e).d = this.d;    // BUG: sets d on THROWAWAY, ll2.d is never set
ll2.f = this.f;
ll2.h = this.h;
ll2.i = this.i;
ll2.j = this.j;
ll2.k = this.k;
ll2.m = this.m;
ll2.n = this.n;
ll2.p = this.p;
ll2.q = this.q;
ll2.r = this.r;    // reference copy only
ll2.s = this.s;
ll2.t = this.t;
ll2.u = this.u;
return ll2;
```

| Field | Copied? | Note |
|-------|---------|------|
| `c`, `e` | ✅ | Via constructor |
| `d` | ❌ | Throwaway object bug |
| `f`, `h`, `i`, `j`, `k`, `m`, `n`, `p`, `q`, `s`, `t`, `u` | ✅ | Direct assignment |
| `r` | ⚠️ | Reference copy (shallow) |
| `b` | ❌ | Not copied (sequence ID) |
| `g` | ❌ | Not copied (description) |
| `l` | ❌ | Not copied (unknown) |
| `o` | ❌ | Not copied (unknown) |

**Remake policy:** Copy ALL fields vào clone thật, bao gồm `d`, `g`, `l`, `o`. Deep-copy `r` (stats) nếu clone cần independent stats.

#### `lm.b()` — GameItem clone (lm.java line 28-43)

```java
lm lm2 = new lm(this.a);              // lm2 gets a via constructor
new lm(this.a).e = this.e;            // BUG: sets e on THROWAWAY, lm2.e is never set
lm2.d = this.d;
lm2.g = this.g;
// ... other fields copied correctly
return lm2;
```

| Field | Copied? | Note |
|-------|---------|------|
| `a` | ✅ | Via constructor |
| `e` | ❌ | Throwaway object bug |
| `d`, `g`, `f`, `h`, `i`, `b`, `c`, `j`, `k`, `l`, `m` | ✅ | Direct assignment |

**Remake policy:** Copy `e` (item type) vào clone thật.

---

## 13. Server-side Reconstruction Decisions from Gameplay Memory

Phần này ghi các quyết định phục dựng server-side do **không có Java server gốc**. Mức bằng chứng là gameplay memory/user-provided evidence ngày `2026-05-03` + Java client behavior đã audit ở các section trên. Khi triển khai code, phải comment rõ các rule này là `Source: gameplay memory 2026-05-03 + Java client equipment parser/UI`.

### 13.1 Equipment acquisition / drop lifecycle

Nguồn trang bị trong game gốc:
- Quái rơi trang bị ra đất dưới dạng **hộp rơi** trên map.
- Shop bán một số trang bị.
- Nhiệm vụ và event có thể thưởng trang bị.
- Upgrade/combine không phải nguồn equipment template mới ở thời điểm hiện tại; phần nâng cấp/kết hợp đang `pending`.

Drop behavior:
- Khi quái rơi equipment, client hiển thị dialog kiểu `hg.java` với nút:
  - `"Nhặt"`
  - `"Bỏ qua"`
- Hình người dùng cung cấp xác nhận hộp rơi trên đất và dialog item drop:
  - Ví dụ: `Kim Đao (Luyện Ngục)`, yêu cầu cấp `1`, độ bền `60/60`, `+9 thân pháp`, `+11 sức tấn công`.
- Drop theo level/map/quái: **có phân cấp**; quái level/map cao hơn rơi equipment tier/resId/rank/stat range cao hơn.
- Stats của equipment rơi: **random trong range nhất định**, không phải mọi instance cùng template đều cố định hoàn toàn.

Reconstruction rule:
```text
EquipmentTemplate = base data: slot, resId, rank, required level, gender, max durability, allowed stat ranges.
EquipmentInstance = generated item: unique key, rolled stats, current durability, enhancement, tradeable flag.
```

### 13.2 Equip validation policy

Server phải authoritative validate khi mặc/tháo:
- Sai giới tính: **không mặc được**. Client có thể tô nền hơi đỏ/warning, nhưng server vẫn phải reject.
- Chưa đủ level: **không mặc được**.
- Không có giới hạn class/phái/hệ ngoài level + gender theo gameplay memory hiện tại.
- One item per slot theo `ll.e` như `cz.java`.
- Khi giao dịch giữa người chơi, đồ phải **tháo ra khỏi người trước**, không trade trực tiếp đồ đang mặc.

Slot notes:
- **Chốt gameplay 2026-05-03:** phase đầu chuẩn hóa hệ trang bị thực tế thành 5 nhóm: **vũ khí, ring/nhẫn, armor/áo giáp, nón/mũ, cánh**.
- Mapping code hiện tại:
  - `e=0` → `Armor`
  - `e=1` → `Weapon`
  - `e=2` → `Helmet`
  - `e=5` → `Ring`
  - `e=8` → `Wing`
- `e=3`, `e=4`, `e=7`, `e=9..12` chỉ còn là Java evidence trong tài liệu; không khai báo hằng số code cho gameplay phase đầu.
- `e=4` trong client table từng ghi mount/shield/ngựa: gameplay memory xác nhận **chưa phát triển item này**.
- `hh.java` main equipped UI chỉ có `dc[] F = new dc[6]`; `e==8` trong Java inventory flow có xử lý đặc biệt (`Dùng`/không đưa vào 6 ô chính), nên cánh phải được xử lý riêng khi dựng UI/server feature, không nhét bừa vào 6 ô chính.

### 13.3 Server stat policy for equipment

Tên stat equipment bám các dòng chỉ số nhân vật/status đã document trong:
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `com/mg/sq/a.java`
- `da.java`

Confirmed equipment stat behavior:
- Equipment cộng vào các chỉ số status/derived stat riêng theo Java client aggregation.
- Equipment broken (`p == 0`) mặc vào **không có tác dụng gì cả**.
- Client `da.java` đã xác nhận status panel bỏ qua stat của equipment có durability `p == 0`.
- Server combat cũng phải bỏ qua toàn bộ stat/effect của equipment broken.

Gameplay memory bổ sung:
- Battle dùng **công thức riêng**, không đồng nhất hoàn toàn với status panel.
- Các stat đặc biệt percent như `DamageAbsorb`, `Pierce`, `Block`, `Revive`, `HpPercent` hiện không xuất hiện/không xác nhận trên item thật theo memory hiện tại; không tự seed các option này cho equipment thường.
- Chỉ seed các dòng stat đã thấy/được xác nhận như stat gốc, attack, defense, HP, dodge, crit nếu có template/range.

### 13.4 Durability / broken / repair

Durability server rule từ gameplay memory:
```text
OnBattleWin:  each equipped repairable/durable equipment loses 1 durability.
OnBattleLose: each equipped repairable/durable equipment loses 3 durability.
Min durability = 0.
If durability reaches 0: item is broken, not destroyed.
Broken equipment remains in inventory/equipped state but gives no stat/effect.
```

Repair:
- Broken/damaged equipment sửa được bằng **búa sửa chữa**.
- **Chốt gameplay 2026-05-03:** chỉ có duy nhất 1 loại búa sửa đồ dùng cho mọi equipment.
  - Asset/icon user xác nhận: `client/assets/equipment/ui/30099.png`.
- Mỗi lần repair consume **1 búa**, hồi đầy `CurrentDurability = MaxDurability`, không dùng KEN/Quan trực tiếp.
- Equipment mới rơi/mua/được cấp luôn khởi tạo full durability: `CurrentDurability = MaxDurability`.
- **Chốt gameplay 2026-05-03:** durability/max durability là dữ liệu lưu DB riêng cho từng equipment instance, không chỉ là default/template chung.
- **Chốt gameplay 2026-05-03:** mỗi món đồ có thể có `MaxDurability` riêng; nếu template/server generation không chỉ định thì dùng default ban đầu `30`.
- **Chốt gameplay 2026-05-03:** upgrade/enhancement cũng tăng độ bền tối đa của chính equipment instance đó; công thức tăng cụ thể sẽ được cấu hình theo tier upgrade khi implement.
- **Chốt gameplay 2026-05-03:** upgrade thành công giữ nguyên `CurrentDurability`; upgrade thất bại không trừ durability.
- **Chốt gameplay 2026-05-03:** equipment có durability `p == 0` vẫn mặc được và vẫn nằm ở slot trang bị, nhưng toàn bộ stat/effect của món đó không còn tác dụng cho status/combat.
- Có các equipment **không thể sửa**, đặc biệt một số item trong map Luyện Ngục, nhưng chưa có template evidence để đánh dấu chính xác ở Phase 1.
- Phase 1 chỉ giữ raw Java-compatible fields:
```text
CurrentDurability        // stored per equipment instance in DB
MaxDurability            // stored per equipment instance in DB
RepairCost               // maps to ll.k; ll.c() true when k > 0
Tradeable                // maps to ll.t
RepairItemId / HammerItemId = 30099
DefaultMaxDurability = 30 // fallback only when generated item/template has no explicit max durability
DurabilityBonusByEnhancementLevel
```

Mapping với Java client:
- `ll.p` = current durability.
- `ll.q` = max durability.
- `ll.k > 0` làm `ll.c()` trả true trong client; Phase 1 chưa thêm field repair policy riêng, vì vậy không được tự suy diễn ngoài `RepairCost/ll.k` nếu chưa có template evidence.
- `hl.java`/`ks.java` repair sender dùng `hammerItemId + equipKey`.

### 13.5 Upgrade / enhancement

Gameplay memory / user decision:
- Enhancement tối đa: `+15`.
- Từ `+5` trở lên tỉ lệ phải thấp rõ rệt.
- Từ `+10` trở lên phải hard, success rate nằm khoảng `5% → 1%`.
- Upgrade fail có thể:
  - Mất phí/nguyên liệu.
  - Tụt cấp.
  - Vỡ/mất trang bị.
- Có vật phẩm bảo hộ.
- User duyệt hướng hard-mode ngày `2026-05-03`.

Source note:
```text
Java client confirms only upgrade UI/payload/result flow:
- id.java: upgrade panel
- ky.java: cmd 96/97 result parser
- ks.java: upgrade sender payload
- ho.java: related forge/combine panel variant

No Java server formula is available.
The policy below is REMAKE BALANCING POLICY from gameplay memory + user approval, not original Java server source.
```

#### 13.5.1 Upgrade target and roll unit

```text
currentLevel = ll.j
targetLevel = currentLevel + 1
MaxEnhancementLevel = 15
```

Use basis points to avoid floating point drift:

```text
10000 bp = 100%
1000 bp  = 10%
100 bp   = 1%
150 bp   = 1.5%

finalSuccessRateBp = baseSuccessRateBp[targetLevel]
                   + luckBonusBp
                   + eventBonusBp

finalSuccessRateBp = min(finalSuccessRateBp, successCapBp[targetLevel])

roll = randomInt(1, 10000)
success if roll <= finalSuccessRateBp
```

#### 13.5.2 Hard-mode success rate table

| Target | Base rate | Base bp |
|---:|---:|---:|
| `+1` | `90%` | `9000` |
| `+2` | `80%` | `8000` |
| `+3` | `70%` | `7000` |
| `+4` | `60%` | `6000` |
| `+5` | `45%` | `4500` |
| `+6` | `35%` | `3500` |
| `+7` | `25%` | `2500` |
| `+8` | `18%` | `1800` |
| `+9` | `12%` | `1200` |
| `+10` | `5%` | `500` |
| `+11` | `4%` | `400` |
| `+12` | `3%` | `300` |
| `+13` | `2%` | `200` |
| `+14` | `1.5%` | `150` |
| `+15` | `1%` | `100` |

Success cap after luck/event bonus:

| Target range | Cap |
|---|---:|
| `+1..+4` | `95%` |
| `+5..+9` | `50%` |
| `+10..+12` | `10%` |
| `+13..+15` | `5%` |

Example:

```text
Upgrade +14 -> +15
base = 1% = 100 bp
large luck stone = +3% = 300 bp
raw = 400 bp = 4%
cap for +13..+15 = 5%
final = 4%
roll 1..10000; success if roll <= 400
```

#### 13.5.3 Upgrade failure policy

**Chốt gameplay 2026-05-03:** phase code đầu tiên chỉ consume material/optional items; chưa consume Quan/fee upgrade. Fee formula ở section `13.5.7` là reserved policy để bật sau bằng config.

| Target | Failure outcome |
|---:|---|
| `+1..+3` | Fail keeps current enhancement; only materials lost. |
| `+4` | Mostly keep current enhancement; small chance downgrade `-1`. |
| `+5..+6` | Downgrade `-1`. |
| `+7..+9` | Downgrade `-1` or `-2`. |
| `+10..+12` | Downgrade `-2` or `-3`; can destroy equipment if no protection. |
| `+13..+15` | Downgrade `-3` to `-5`; high destroy chance if no protection. |

Destroy chance is rolled only after failure and only for target `+10..+15`:

| Target | Destroy chance inside failed attempt |
|---:|---:|
| `+10` | `10%` |
| `+11` | `15%` |
| `+12` | `20%` |
| `+13` | `28%` |
| `+14` | `35%` |
| `+15` | `45%` |

Special hard-mode note:
- For `+15`, a severe failure may downgrade to `+10` or lower if the server wants to preserve `+15` rarity.
- Exact severe-failure distribution can be tuned later, but must remain server-authoritative and logged.

#### 13.5.4 Protection items

Proposed protection items:

| Protection | Use range | Effect |
|---|---|---|
| `DowngradeProtection` / Bùa chống tụt | Best for `+4..+9` | Prevents downgrade on failure. Consumed when it prevents downgrade. |
| `DestroyProtection` / Bùa chống vỡ | `+10..+15` | Prevents equipment destruction. Equipment may still downgrade at high tier. Consumed when it prevents destruction. |
| `PerfectProtection` / Bảo hộ hoàn hảo | Rare/event/premium | On failure, prevents both destruction and downgrade for one attempt. Consumed on failed protected outcome. |

Hard-mode balancing option:
- From `+13` upward, `PerfectProtection` may reduce penalty instead of fully removing it if economy becomes too easy.
- This must be a server config/policy, not client logic.

#### 13.5.5 Luck items

Luck items add basis points before cap:

| Luck item | Bonus |
|---|---:|
| Small luck stone | `+1%` / `+100 bp` |
| Medium luck stone | `+2%` / `+200 bp` |
| Large luck stone | `+3%` / `+300 bp` |
| Ultra luck stone | `+5%` / `+500 bp`, rare/event |

Luck item does not protect from downgrade/destruction unless explicitly defined as a hybrid item.

#### 13.5.6 Upgrade materials

Exact original Java server material IDs are **not known yet**. Java client `id.java`/`ky.java`/`ks.java` proves the upgrade panel consumes item IDs/counts, but not the original material catalog. Therefore the following is a remake material taxonomy until item template evidence is recovered.

Proposed material tiers:

| Target | Material tier |
|---:|---|
| `+1..+3` | `UpgradeStoneBasic` / Đá cường hóa sơ cấp |
| `+4..+6` | `UpgradeStoneIntermediate` / Đá cường hóa trung cấp |
| `+7..+9` | `UpgradeStoneAdvanced` / Đá cường hóa cao cấp |
| `+10..+12` | `UpgradeStoneRefined` / Đá cường hóa tinh luyện |
| `+13..+15` | `UpgradeStoneDivine` / Đá cường hóa thần khí |

Proposed quantity by target:

| Target | Stone count |
|---:|---:|
| `+1` | `1` |
| `+2` | `1` |
| `+3` | `2` |
| `+4` | `2` |
| `+5` | `3` |
| `+6` | `4` |
| `+7` | `5` |
| `+8` | `6` |
| `+9` | `8` |
| `+10` | `10` |
| `+11` | `12` |
| `+12` | `15` |
| `+13` | `18` |
| `+14` | `22` |
| `+15` | `30` |

Material reconstruction status:
- Known from Java client: upgrade request can send equipment keys + item IDs + item quantities + fee.
- Unknown: original item names/IDs for stones/protection/luck.
- Implementation should create stable remake IDs but mark them as `Reconstructed`, then replace/alias them if original item dump is found.

#### 13.5.7 Upgrade fee

Remake uses `Quan` economy even though Java client strings may still mention KEN.

Proposed fee:

```text
feeQuan = baseByRank * targetLevel * targetLevel
```

| Rank | baseByRank |
|---:|---:|
| `0` normal | `5` |
| `1` good | `10` |
| `2` rare | `20` |
| `3` rare/high | `30` |
| `4` legendary | `50` |
| `7` event/legend | `70` |
| `8` premium/legend | `100` |

Example:

```text
Rank 4 upgrade to +10: 50 * 10 * 10 = 5000 Quan
Rank 1 upgrade to +5: 10 * 5 * 5 = 250 Quan
```

#### 13.5.8 Enhancement stat formula

Enhancement stat bonus is computed from the equipment's base rolled stat, not compounded from the previous enhanced value.

```text
enhancedFlatStat = baseFlatStat + floor(baseFlatStat * bonusPercent[enhancementLevel] / 100)
```

Bonus table:

| Level | Total bonus |
|---:|---:|
| `+0` | `0%` |
| `+1` | `3%` |
| `+2` | `6%` |
| `+3` | `10%` |
| `+4` | `15%` |
| `+5` | `21%` |
| `+6` | `28%` |
| `+7` | `36%` |
| `+8` | `45%` |
| `+9` | `55%` |
| `+10` | `66%` |
| `+11` | `78%` |
| `+12` | `91%` |
| `+13` | `105%` |
| `+14` | `120%` |
| `+15` | `140%` |

Examples:

```text
Weapon base Attack +100:
+5  => 100 + floor(100 * 21 / 100) = 121
+10 => 100 + floor(100 * 66 / 100) = 166
+15 => 100 + floor(100 * 140 / 100) = 240

Armor base Defense +80:
+10 => 80 + floor(80 * 66 / 100) = 132
+15 => 80 + floor(80 * 140 / 100) = 192
```

Initial safe stat policy:
- Apply enhancement bonus only to flat stats.
- Do **not** enhance percent/special stats in the first implementation:
  - `lb.j` DamageAbsorb %
  - `lb.k` ArmorPierce %
  - `lb.l` Block %
  - `lb.m` Revive %
  - `lb.n` AttackPercent
  - `lb.o` HpPercent
- Reason: Java client confirms parsing/display for these fields, but combat/status formulas are not fully reconstructed for all of them. Enhancing percent stats too early can break balance.

Flat stats eligible for enhancement:
- `lb.a` Strength
- `lb.b` Agility
- `lb.c` Magic/Internal
- `lb.d` Vitality
- `lb.e` Attack
- `lb.f` Defense
- `lb.g` Critical if treated as flat integer
- `lb.h` Dodge
- `lb.i` HP bonus

#### 13.5.9 Server-side upgrade flow

```text
1. Validate owner, equipment exists, not locked/trading, not equipped, target <= +15.
2. Validate template/instance allows upgrade via server-side policy/config when implemented; Phase 1 has no `IsUpgradeable` field.
3. Validate required materials and optional luck/protection items.
4. Phase-1 economy: consume materials/optional items at attempt start; do not consume Quan fee yet.
5. Roll success using basis points.
6. If success:
   - set ll.j = targetLevel
   - recalculate enhanced stats from base rolled stats
   - increase MaxDurability if configured for target tier
   - keep CurrentDurability unchanged
   - return command 97 success message
7. If fail:
   - durability stays unchanged
   - roll destroy if target >= +10
   - apply protection if present
   - otherwise downgrade/destroy according to failure table
   - if destroyed: delete equipment instance from DB/inventory and notify player "mất đồ"
   - if downgraded: recalculate stats from base rolled stats
   - return command 97 failure message
8. Audit-log every attempt with before/after level, materials, roll, outcome.
```

### 13.6 Combine

**Chốt gameplay 2026-05-03:** combine không làm placeholder/tạm bợ; khi code equipment phải làm hoàn chỉnh bằng recipe/config server-side, dù Java server gốc chưa có.

Client evidence:
- `ho.java` và `id.java` xác nhận UI/payload/result flow.
- `ho.java` có sender action `0/1`, material/result grids, final arrays và result text `"Kết hợp thành công"` / `"Kết hợp thất bại"`.
- Java client không chứng minh công thức server gốc, nên công thức combine phải đánh dấu `Source: gameplay memory 2026-05-03 + reconstructed server policy`.

Initial remake combine policy để implement:
- Combine chạy qua `CombineRecipes` server config/table.
- Recipe gồm:
  - `RecipeId`
  - `InputEquipmentSlotId/RankId/LevelRange/EnhancementRange` optional filters
  - `MaterialItemId + Quantity`
  - `OutputTemplateId` hoặc `OutputPoolId`
  - `SuccessRateBasisPoints`
  - `FailurePolicy`
- Server validate ownership, inventory, material và lock trạng thái; client chỉ gửi selection.
- Equipment đang mặc không được dùng làm nguyên liệu combine, giống upgrade/trade.
- Result có thể là:
  - equipment mới roll stat trong output template/pool range;
  - material/item thưởng;
  - fail mất material và/hoặc mất input theo recipe config.
- Mọi recipe/tỉ lệ phải nằm DB/config để review/tuning, không hardcode trong client.

### 13.7 Shop / economy / trade

Shop:
- Trang bị shop bán bằng KEN trong game gốc, nhưng remake đã đổi đơn vị hiển thị/kinh tế sang **Quan**.
- Khi port UI/document, dùng `Quan` cho đơn vị hiện tại, nhưng note Java client strings cũ có thể còn ghi `KEN`.
- **Chốt gameplay 2026-05-03:** equipment mua từ shop vẫn roll random stat trong range nhất định của template/shop offer, không phải stat cố định tuyệt đối.

Sell to NPC:
- Equipment **không bán lại NPC** theo gameplay memory hiện tại.

Trade:
- **Chốt gameplay 2026-05-03:** tất cả equipment mặc định trade được, gồm đồ rơi/shop/event nếu template không override đặc biệt.
- `ll.t` vẫn giữ để Java-compatible serialization/UI; default `t=1`.
- Người chơi phải tháo equipment trước khi trade.
- Có rao bán/market trang bị.
- Market có thuế/fee, công thức pending.

### 13.8 Slot đặc biệt `e=8` và `e=4`

#### `e=8` — cánh/remake slot đặc biệt

Java client evidence:
- `hh.java` khai báo `dc[] F = new dc[6]` cho 6 ô equipped UI chính.
- Khi build equipped cells từ `lh.D`, equipment `e == 8` không được đưa vào 6 ô `F`.
- Khi chọn equipment trong bag và `ll.e == 8`, menu dùng action `"Dùng"` thay vì flow `"Mặc"` thường.
- `e=8` vẫn đi qua model `ll`, icon resolver, detail/tooltip và packet như equipment nếu server gửi.

Reconstruction decision:
- Theo xác nhận gameplay ngày `2026-05-03`, phase hiện tại map nhóm gameplay thứ 5 là **cánh** và dùng `e=8 -> Wing`.
- Vì Java evidence chỉ chứng minh `e=8` là slot đặc biệt/use-flow, không render cánh vào body compositor nếu chưa có asset/template/server evidence.
- Phase đầu: lưu DB + inventory icon + detail/tooltip; stat/effect/render của cánh để `PENDING` hoặc bật bằng server config khi có evidence.

#### `e=4` — mount/shield/ngựa / `lh.ad` flag

Java client evidence:
- `lh.b()` scan `lh.D`; nếu có equipment `e == 4` thì set `this.ad = true`, ngược lại `this.ad = false`.
- `mb.java` dùng `lh.ad` để đổi nhánh render/pose/facing, không đưa `e=4` vào layer armor/weapon/helmet thường.
- Một số flow loại `e == 4` khỏi điều kiện forge/equip-style giống slot đặc biệt.

Reconstruction decision:
- Theo gameplay memory ngày `2026-05-03`, game hiện tại chưa phát triển mount/shield/ngựa như gameplay equipment phase đầu.
- Không khai báo gameplay slot `Mount` trong code Phase 1; chỉ giữ evidence Java `e=4` trong tài liệu.
- Không suy diễn `e=4` thành cánh; cánh hiện map theo decision remake `e=8`.

### 13.9 Asset/template evidence still pending

Cần chờ người dùng cung cấp thêm:
- Equipment template dump/list: name, resId, slot, level, stats range, rank, gender, max durability, repairable/tradeable.
- Screenshot/video item mẫu.
- Giải thích các asset band `12xxxx-14xxxx`.
- Premium/event set `95xxx/96xxx`: gameplay memory xác nhận là item hiếm/event, sẽ phát triển event sau.
- Một số item đặc biệt trong map Luyện Ngục không sửa được; cần template evidence trước khi thêm cờ policy hoặc đánh dấu chính xác.

Until then:
- Không seed cứng toàn bộ asset band thành equipment.
- Chỉ seed item có bằng chứng từ template/screenshot/memory.
- Event/premium items phải để `Event/Premium pending`, không trộn vào shop/drop thường.

### 13.10 Initial server implementation plan from current evidence

#### Code readiness gate `2026-05-03`

Tài liệu hiện tại **đủ để bắt đầu code Phase 1 equipment foundation**, nhưng chưa đủ để code mọi nhánh equipment nâng cao như gameplay gốc hoàn chỉnh.

##### Java evidence đủ để code ngay

- Raw model `ll`/`lb`/`lm` và tag parser chính:
  - `ll` equipment fields/tag shape.
  - `lb` 15 stat fields/tag shape.
  - `lm` consumable/item model cho hammer/material foundation.
  - `ky.java` parser shape cho equipment/item.
  - `ks.java` sender command chính cho equip/upgrade/shop/repair/combine/trade.
- Core inventory/equip UI logic:
  - `go.l` equipment bag/equipped state.
  - `hh.java`, `cz.java`, `gp.java`, `dc.java`, `fw.java`, `hg.java`.
- Status aggregation:
  - `com/mg/sq/a.java` và `da.java` chứng minh chỉ cộng `lb.a,b,c,d,e,f,g,h,i,n` khi equipment còn durability.
  - `lb.n`/tag `204` áp `baseAttack * percent / 100`.
  - `lb.j/k/l/m/o` chỉ lưu/hiển thị, không apply combat/status trong Phase 1.
- Visual resolver/compositor:
  - icon `band = resId - resId % 10`, icon id `band + 98`.
  - compositor chắc cho armor/weapon/helmet; slot đặc biệt giữ pending.
- Remake policy đã được tách rõ và có thể code dưới feature/config:
  - template/instance split.
  - inventory capacity default `50`.
  - durability loss/repair hammer `30099`.
  - shop buy roll stat from template/shop range.
  - upgrade hard-mode table/config.
  - combine via `CombineRecipes`.

##### Không chặn Phase 1, nhưng không được code như Java gốc

- Combat runtime effects của `DamageAbsorb/Pierce/Block/Revive/HpPercent`.
- Slot `e=4` mount/riding effect ngoài flag render đã thấy.
- Slot `e=8` wing/event stat/render/effect ngoài lưu DB + icon/detail.
- Original material IDs/names for upgrade/luck/protection.
- Market tax formula.
- Full byte-perfect serializer cho mọi optional branch nếu mục tiêu là emulator Java client hoàn chỉnh.

##### Câu hỏi cần user chốt trước khi code Phase 1

1. **Phạm vi Phase 1**: code foundation server trước hay làm cả mobile UI inventory/equipment detail cùng lúc?
2. **Upgrade trong Phase 1**: chỉ tạo schema/config và API skeleton, hay bật luôn roll upgrade hard-mode theo section `13.5`?
3. **Combine trong Phase 1**: tạo bảng `CombineRecipes` + endpoint nhưng chưa seed recipe, hay user sẽ cung cấp recipe seed ban đầu?
4. **Drop/shop seed**: dùng starter/test equipment seed tối thiểu hiện có, hay user cung cấp thêm danh sách template thật trước khi mở drop/shop?
5. **Wing `e=8`**: Phase 1 chỉ lưu/hiển thị icon/detail và không cộng stat/render, đúng như safety gate hiện tại?

Nếu chưa có câu trả lời, safe default để code là:
- server foundation + API core;
- UI detail/inventory tối thiểu nếu cần;
- không bật special combat stats;
- không bật wing stat/render;
- không seed đại trà;
- upgrade/combine chỉ config-driven, không hardcode recipe/material original.

Có thể implement an toàn trước:
- Equipment template + equipment instance model tách chuẩn:
  - `EquipmentTemplates` cho base data/range/config.
  - `PlayerEquipment` cho unique instance key, owner, rolled stats, durability, enhancement, equipped slot/state.
- Inventory capacity default `50`, sau này mở rộng bằng field capacity/server progression.
- Drop generation từ monster/map tier với stat roll trong range; drop rate phải rất thấp.
- Drop pool ngoài equipment có thể gồm HP/MP item, trứng (`egg`) và material khác; trứng có flow mở/đập để có thể ra equipment.
- Pickup dialog/API flow.
- Equip validation: ownership, level, gender mapping Java `0=Nam, 1=Nữ, 2=Cả hai`, one-per-slot, not trading equipped item.
- Slot cánh/event giai đoạn đầu chỉ lưu DB + inventory icon; chưa cộng stat/chưa render lên nhân vật nếu thiếu asset/data.
- Durability loss after battle win/loss.
- Broken equipment disables all equipment stats.
- Repair by single hammer item `30099`, 1 hammer per repair.
- Shop buy with Quan; shop equipment rolls stats from configured range.
- Trade requires unequipped equipment; default tradeable = true.
- Upgrade hard-mode policy support:
  - `MaxEnhancementLevel = 15`.
  - Success roll in basis points.
  - Hard rates from `+10..+15`: `5%, 4%, 3%, 2%, 1.5%, 1%`.
  - Failure can downgrade or destroy equipment from high tiers.
  - Protection/luck/material policy as reconstructed in section `13.5`.
  - Phase-1 upgrade consumes material only; fee formula is reserved/off by config.
  - Upgrade allowed only while equipment is in bag, not equipped.
  - Broken equipment upgrade policy cần chốt bằng server config khi implement upgrade; Phase 1 chưa có `IsUpgradeable`.
  - Destroy outcome deletes equipment instance permanently.
- Combine implemented via server-configured `CombineRecipes`, not placeholder.
- Server key generation: use sortable unique string keys (ULID-style or equivalent) so keys are compact, unique and log/debug friendly without exposing player sequence assumptions.

Chưa implement nếu chưa có thêm evidence:
- Original Java server material IDs/names for upgrade stones, luck items, and protection items. Current section `13.5.6` is remake taxonomy.
- Market tax formula.
- Special stat combat effects beyond currently confirmed status aggregation.
- Slot `e=4` mount/riding effect beyond confirmed `lh.ad` render flag.

#### Gameplay policy chốt thêm với user `2026-05-03`

Các quyết định dưới đây là **Remake policy / user confirmation**, không phải Java server evidence. Khi code phải comment nguồn: `Source: user confirmation 2026-05-03 + Java client equipment evidence`.

| Mục | Quyết định đã chốt | Implementation rule |
|-----|---------------------|---------------------|
| Cánh `e=8` | Có cộng stat và có slot UI riêng | `Wing` được tính như equipment gameplay hợp lệ trong stat pipeline nếu còn durability; UI cần slot riêng, không nhét vào `hh.java` 6 ô Java chính nếu đang dựng UI remake. Render lên nhân vật chỉ làm khi có asset/compositor policy rõ; slot UI riêng không tự đồng nghĩa body compositor Java gốc. |
| Đồ hỏng `p == 0` | Vẫn mặc được, nhưng không cộng bất kỳ stat/effect nào | Giữ equipped state; stat/status/combat-facing aggregation phải bỏ qua toàn bộ stats/effects của item hỏng. |
| Repair hammer `30099` | 1 búa sửa full durability cho mọi đồ sửa được, không mất Quan | Consume exactly 1 hammer item id `30099`; set `CurrentDurability = MaxDurability`; không trừ currency. |
| Đồ không sửa được | Chỉ áp dụng nhóm Luyện Ngục hiện biết | Vì chưa có template dump, không tự gắn cờ đại trà. Khi template/item được đánh dấu Luyện Ngục thì không cho repair; ngoài nhóm này phase đầu theo `RepairCost > 0`/policy config. |
| Cường hóa đồ đang mặc | Phải tháo ra túi mới nâng cấp được | Upgrade validation reject equipment currently equipped. |
| Đồ hỏng nâng cấp | Được nâng cấp; durability giữ nguyên | Upgrade không yêu cầu repair trước; success/fail không tự hồi durability trừ khi có config riêng sau này. |
| Cường hóa phase đầu | Chờ có danh sách đá/bùa gốc rồi mới bật roll thật | Phase hiện tại chỉ chuẩn bị schema/config/API skeleton nếu cần; không dùng material remake tạm để bật roll hard-mode. |
| Upgrade fail tier cao bị vỡ | Vỡ là mất hẳn equipment instance | Destroy outcome deletes equipment instance permanently from ownership/inventory/equipped state. |
| Trade/rao bán | Phải tháo đồ ra mới trade/rao bán được | Trade/market validation reject equipped item. |
| Shop/drop stat roll | Đồ rơi và đồ shop roll stat random trong range template | Template defines stat ranges; instance stores rolled stats. Không dùng stat cố định tuyệt đối nếu template/range cho phép roll. |
| Đập/mở trứng | Trứng mở bằng Quan theo từng loại; không bao giờ mở ra cánh | Đây là `Remake policy / user confirmation 2026-05-03`, không phải Java evidence. Cánh `Wing/e=8` chỉ đi qua flow chế tạo/crafting riêng sau này, không nằm trong reward pool đập trứng. Equipment từ trứng chỉ roll các slot `Armor/Weapon/Helmet/Ring`. |
| Phí mở trứng | Trứng thường `20,000` Quan; trứng đà điểu `30,000` Quan; trứng khủng long `100,000` Quan; trứng rồng `300,000` Quan; trứng phượng `300,000` Quan | Các mức Quan là policy hiện tại và có thể chỉnh bằng config sau. Khi implement không hard-code vào logic rải rác; đưa vào egg config/server authority. |
| Danh sách equip theo từng loại trứng | Sẽ có list equipment mở riêng cho mỗi loại trứng | Pending config/data: mỗi loại trứng cần `AllowedEquipmentTemplateKeys` hoặc reward pool template riêng. Nếu list chưa cấu hình thì API mở trứng phải trả lỗi cấu hình rõ ràng, không fallback sang template ngẫu nhiên sai tier. |

#### Open-egg / đập trứng remake policy `2026-05-03`

##### Java evidence

- Java/client evidence hiện mới đủ cho item/icon identity một phần của trứng trong inventory UI.
- Chưa có Java server evidence cho tỉ lệ drop trứng, phí mở trứng, reward pool, rank roll, hoặc list equipment có thể mở từ từng loại trứng.
- Vì vậy toàn bộ rule bên dưới là **Remake policy / user confirmation**, không được ghi là logic Java gốc.

##### User-confirmed remake policy

| Egg type | Quan cost/open | Reward boundary |
|----------|----------------:|-----------------|
| Trứng thường | `20,000` | Mở ra reward theo list/config riêng của trứng thường. |
| Trứng đà điểu | `30,000` | Mở ra reward theo list/config riêng của trứng đà điểu. |
| Trứng khủng long | `100,000` | Mở ra reward theo list/config riêng của trứng khủng long. |
| Trứng rồng | `300,000` | Mở ra reward theo list/config riêng của trứng rồng. |
| Trứng phượng | `300,000` | Mở ra reward theo list/config riêng của trứng phượng. |

Rules bắt buộc khi implement:
- Consume đúng `1` trứng và trừ đúng Quan cost theo egg config.
- Trứng **không bao giờ** mở ra cánh; `Wing/e=8` là flow chế tạo/crafting riêng sau này.
- Equipment reward từ trứng chỉ được chọn từ slot `Armor`, `Weapon`, `Helmet`, `Ring`.
- Mỗi loại trứng sẽ có list equipment/template mở riêng; cần note/config dạng `AllowedEquipmentTemplateKeys`, `AllowedTemplatePoolId`, hoặc bảng reward pool tương đương.
- Nếu list equipment của loại trứng chưa cấu hình hoặc không có template hợp lệ thì trả lỗi cấu hình rõ ràng; không tự fallback sang đồ random để tránh sai policy.
- Stat roll của equipment mở từ trứng dùng cùng pipeline template/range đã chốt cho shop/drop.
- Special stats `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent` nếu xuất hiện từ template vẫn chỉ hiển thị/aggregate theo rule đã có evidence; không tự bật combat formula.
- Cost/rate/reward pool phải nằm trong config/server authority để sau này chỉnh mà không sửa nhiều code.

##### Pending/Unverified

- ItemId chính thức cho các loại trứng ngoài icon trứng đà điểu.
- Drop rate trứng từ monster/map/event.
- Reward chance cụ thể cho từng loại trứng.
- List equipment/template mở riêng cho từng loại trứng.
- Có/không có pity, bảo hiểm, hoặc event multiplier.

---

## 14. Coverage Estimate / Remaining Audit

### 14.1 Mức độ đã rà soát

Ước lượng hiện tại: **phần equipment client-side core chính đã được gom ở mức cao, nhưng không chốt phần trăm tuyệt đối** vì còn thiếu Java server/template dump và vẫn có các nhánh UI/protocol phụ cần test byte-perfect với client thật. Không dùng con số phần trăm để tuyên bố hoàn chỉnh.

Đã đọc/đối chiếu các nhóm chính:
- Data/protocol: `ll`, `lb`, `ky`, `ks`.
- Inventory/equip: `go`, `gp`, `hh`, `cz`, `dc`, `fw`, `hg`.
- Visual: `mb`.
- Shop/repair/forge/trade: `ia`, `gx`, `lq`, `hl`, `id`, `ho`, `of`.
- Status/stat display: `com/mg/sq/a`, `da`.

Phạm vi đã đủ để lên kế hoạch port core equipment gồm: model, stats, inventory, equip/unequip, icon/frame resolver, character compositor, detail/tooltip/cell UI, repair, shop preview/buy, upgrade/combine UI flow, client sender payloads và response callback shape chính cho `96/97/99/100/112`.

### 14.2 Còn cần audit trước khi code server hoàn chỉnh

Các mục dưới đây không chặn plan core equipment, nhưng cần đối chiếu thêm khi triển khai server/API chính thức để tránh đoán sai những phần nằm ngoài evidence equipment client-core:

- Combat/battle source để xác nhận effect runtime của 5 stat đặc biệt của `lb`: absorb/pierce/block/revive/hpPercent. Hiện chỉ chắc chắn chúng được parse/display; `AttackPercent` đã chắc chắn được cộng theo `baseAttack * percent / 100`. Re-audit `2026-05-03` xác nhận `da.java` và `com/mg/sq/a.java` chỉ cộng `a,b,c,d,e,f,g,h,i,n` vào status/preview, không chứng minh formula battle cho `j,k,l,m,o`.
- Asset/meta đối chiếu `ll.n` thật từ server dump nếu có, đặc biệt các band `12xxxx-14xxxx`, mount/shield/accessory/premium.
- Các màn thương mại phụ `hn.java`/`hq.java` nếu về sau cần phục dựng đầy đủ UI chợ/rao bán ngoài core equipment. Đã search xác nhận có wrap `ll` qua `lq`, chưa thấy thay đổi model/rule core.
- Byte-perfect serializer cho mọi nhánh optional của `96/97/99/100/112` vẫn nên test với client thật; tài liệu hiện đã ghi đủ tag shape chính từ parser Java.

---

## 15. Nhật ký chỉnh sửa

> Section này đã được dọn ngày `2026-05-03` để tránh trùng lặp dài với `CHANGELOG.md`. Chi tiết lịch sử thay đổi đầy đủ xem `CHANGELOG.md` mục `[EQUIPMENT]`. Tài liệu này chỉ giữ lại các mốc ảnh hưởng trực tiếp tới spec phục dựng.

### 2026-05-03 — Java client equipment audit

- Đã gom evidence client-side core từ:
  - Data/protocol: `ll.java`, `lb.java`, `ky.java`, `ks.java`.
  - Inventory/equip: `go.java`, `gp.java`, `hh.java`, `cz.java`, `dc.java`, `fw.java`, `hg.java`.
  - Visual: `mb.java`.
  - Shop/repair/forge/trade: `ia.java`, `gx.java`, `lq.java`, `hl.java`, `id.java`, `ho.java`, `of.java`.
  - Status/stat display: `com/mg/sq/a.java`, `da.java`.
- Bổ sung các điểm Java-critical:
  - Full field model `ll` gồm cả field chưa rõ `b/l/o/s/u`.
  - Full 15-field `lb` stats và tag mapping packet.
  - Parser minimal/full equipment theo `ky.a(..., bl2=false/true)`.
  - Equipment element/icon field `ll.f` từ tag `15`; screenshot user xác nhận icon hệ hiển thị trước tên item.
  - ResId convention `band = resId - resId % 10`, icon `band + 98`.
  - Rule status panel: equipment `p == 0` không cộng stat.
  - `lb.n` (`AttackPercent`) chắc chắn cộng theo `baseAttack * percent / 100`; các stat đặc biệt khác còn pending combat audit.
- Coverage hiện tại: phần equipment client-side core chính đã gom ở mức cao nhưng chưa được tuyên bố tuyệt đối; phần còn thiếu chủ yếu là combat usage của stat đặc biệt, server template dump và byte-perfect serializer test với client thật.

### 2026-05-03 — Server-side reconstruction decisions

- Nguồn: gameplay memory/user confirmation ngày `2026-05-03` + Java client UI/protocol evidence; không phải Java server gốc.
- Chốt model server:
  - Tách `EquipmentTemplates` và `PlayerEquipment`.
  - Equipment instance có key unique string, rolled stats, durability riêng, enhancement riêng.
  - Inventory default capacity `50`.
- Chốt validation:
  - Gender mapping giữ đúng Java `0=Nam`, `1=Nữ`, `2=Cả hai`.
  - Chưa đủ level/sai giới tính không mặc được.
  - Equipment đang mặc không dùng để trade/upgrade/combine.
  - Slot cánh/event khi thiếu data chỉ lưu DB + inventory icon, chưa cộng stat/chưa render.
- Chốt durability/repair:
  - Đồ mới rơi/mua/cấp luôn full durability.
  - Thắng trận trừ `1`, thua trận trừ `3`.
  - Durability `0` chỉ broken, không mất đồ, nhưng không cộng stat/effect.
  - Repair dùng duy nhất búa icon `client/assets/equipment/ui/30099.png`, mỗi lần consume `1` búa và hồi đầy durability.
  - Có đồ không sửa/nâng cấp được trong gameplay memory, nhưng Java client chỉ xác nhận raw fields/method:
    - `ll.c()` repairable khi `k > 0`.
    - upgrade block gốc cần đối chiếu đúng tại luồng sender/UI trước khi thêm cột server riêng.
    - Không thêm `IsRepairable`/`RepairBlockReason`/`IsUpgradeable` khi chưa có template evidence hoặc policy server hoàn chỉnh.
- Chốt economy/trade/shop:
  - Remake dùng đơn vị `Quan`; Java string cũ có thể còn `KEN`.
  - Equipment không bán lại NPC theo memory hiện tại.
  - Equipment mặc định tradeable `ll.t=1`, nhưng phải tháo trước khi trade.
  - Shop equipment roll stat trong range template/shop offer.
- Chốt upgrade:
  - Max `+15`.
  - Success roll dùng basis point `1..10000`.
  - Hard rates từ `+10..+15`: `5%`, `4%`, `3%`, `2%`, `1.5%`, `1%`.
  - Fail có thể tụt cấp hoặc destroy từ high tier; destroy xóa hẳn equipment instance.
  - Có protection/luck items; ID/tên item original chưa biết, dùng taxonomy remake tạm thời.
  - Phase đầu upgrade chỉ consume material/optional item; fee formula reserved/off-by-config.
  - Enhancement stat formula chỉ áp dụng flat stats trong implementation đầu.
- Chốt combine:
  - Không làm placeholder.
  - Implement bằng `CombineRecipes` server config/table với input filters, material, output template/pool, success rate và failure policy.

### 2026-05-03 — Documentation cleanup

- Dọn section nhật ký để bỏ phần lặp lại dài đã có trong `CHANGELOG.md`.
- Giữ lại các mốc quyết định quan trọng và link nguồn bằng tên file Java để không mất evidence phục dựng.
- Không xóa các section kỹ thuật `1..14` vì đây là spec trực tiếp cần cho Phase 1 equipment.

### 2026-05-03 — Chuẩn hóa 5 nhóm equipment gameplay hiện tại

- Cập nhật lại terminology sau user confirmation: gameplay hiện tại chỉ chuẩn hóa 5 nhóm equipment chính **vũ khí, ring/nhẫn, armor/áo giáp, nón/mũ, cánh**.
- File code cập nhật:
  - `server/Twelve.Core/Players/PlayerEquipmentDefinition.cs`
- Thay hướng `SlotNRaw`/`SlotN...` bằng naming rõ đúng 5 nhóm đang dùng:
  - `Armor`
  - `Weapon`
  - `Helmet`
  - `Ring`
  - `Wing`
- Không khai báo hằng số `Reserved`/`Unused` trong code để tránh hiểu nhầm là gameplay có nhiều slot hơn 5 nhóm hiện tại.
- Ghi chú nguồn: armor/weapon/helmet bám Java client; ring/wing là quyết định remake theo gameplay memory/user confirmation ngày `2026-05-03`, có thể chỉnh mapping khi có template/server evidence mới.

### 2026-05-03 — Audit cập nhật tài liệu (re-audit Java source)

- Audit lại các file Java chính: `ll.java`, `lb.java`, `lm.java`, `ky.java`, `ks.java`, `mb.java`.
- Phát hiện và sửa:
  - **Section 1.2 clone mô tả sai**: `ll.d()` KHÔNG deep-copy tất cả fields. Fields `d`, `g`, `l`, `o` bị miss do decompiler throwaway bug và omission. Sửa comment + thêm reference section 12.1.
  - **Section 2.1 stats table**: Thêm verified marker `2026-05-03` và note giữ raw tag/field Java.
  - **Section 4.2 mới**: Thêm `lm.java` (GameItem) full field mapping table, `toString()` analysis, clone bug `lm.b()`, remake policy. Tách evidence/pending/remake policy rõ ràng.
  - **Section 7.2 parser bugs**: Thêm `v0.n` decompiler alias note (likely `v0 == object`). Thêm verified marker cho tag mapping.
  - **Section 7.4 cmd 112**: Bổ sung detail handler với tag `83/175/157/114/106`. Ghi chú tag `175` client đọc nhưng không dùng.
  - **Section 7.5 senders**: Thêm `ks.java` switch case `84` equip sender → tag `114`.
  - **Section 12.1 mới**: Tổng hợp decompiler clone bugs cho cả `ll.d()` và `lm.b()`, gồm field-by-field copy table và remake policy.
- Files `.md` đã sửa:
  - `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`
- Không sửa code server/client → không cần build.

### 2026-05-03 — Equipment special stats battle boundary audit

- Audit bổ sung Java evidence:
  - `lb.java`: xác nhận 15 field raw `a..o`.
  - `ky.java`: xác nhận tag parser `118/119/120/121/72/71/126/124/47/200/201/202/203/204/221`.
  - `com/mg/sq/a.java`: xác nhận UI label cho `j/k/l/m/o` nhưng chỉ cộng status aggregation cho `a,b,c,d,e,f,g,h,i,n`.
  - `da.java`: status panel chỉ cộng equipment đang mặc còn durability (`p != 0`) với các field `a,b,c,d,e,f,g,h,i,n`; không thấy apply `j,k,l,m,o`.
- Kết luận tài liệu:
  - `j/k/l/m/o` giữ raw mapping + UI label là Java evidence.
  - Combat formula của hấp thu sát thương / xuyên giáp / cản đòn / hồi sinh / sinh lực % chuyển rõ về `Pending/Unverified`.
  - Remake không được tự bật combat effect cho các stat đặc biệt này nếu chưa có evidence hoặc policy được chốt riêng.
- Files `.md` đã sửa:
  - `EQUIPMENT_SYSTEM_RECONSTRUCTION.md`
  - `BATTLE_SYSTEM_RECONSTRUCTION.md`
- Không sửa code server/client → không cần build.

### 2026-05-03 — Code readiness gate before next implementation

- Rà lại toàn bộ `Pending/Unverified` hiện còn trong tài liệu.
- Kết luận:
  - Tài liệu đủ để code **Phase 1 equipment foundation** theo Java evidence + remake policy đã tách.
  - Chưa đủ để code combat special stats, wing/mount runtime effect, market tax, hoặc original material catalog như Java gốc.
- Thêm section `13.10 Code readiness gate` gồm:
  - phần evidence đủ để code ngay;
  - phần không chặn Phase 1 nhưng không được code như Java gốc;
  - 5 câu hỏi cần user chốt trước khi code implementation lớn.
- Không sửa code server/client → không cần build.

### 2026-05-03 — Gameplay policy questions resolved for next equipment implementation

- User chốt thêm các quyết định gameplay:
  - Cánh `e=8`: có cộng stat và có slot UI riêng trong remake.
  - Đồ hỏng `p == 0`: vẫn mặc được nhưng không cộng bất kỳ stat/effect nào.
  - Repair: dùng 1 búa `30099`, hồi full durability, không mất Quan.
  - Đồ không sửa được: trước mắt chỉ áp dụng nhóm Luyện Ngục; cần template evidence để đánh dấu chính xác.
  - Upgrade: đồ đang mặc phải tháo ra; đồ hỏng vẫn nâng cấp được và durability giữ nguyên.
  - Upgrade roll thật: chờ danh sách đá/bùa gốc, không bật bằng material remake tạm.
  - Upgrade destroy outcome: mất hẳn equipment instance.
  - Trade/rao bán: phải tháo đồ ra trước.
  - Shop/drop: roll stat random trong range template.
- Đã thêm bảng `Gameplay policy chốt thêm với user 2026-05-03` vào section `13.10`.
- Không sửa code server/client → không cần build.

### 2026-05-03 — Backend equipment foundation Phase 1

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerEquipmentDefinition.cs`
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
  - `server/Twelve.Infrastructure/Repositories/EquipmentCatalogRepository.cs`
  - `server/Database/Equipment/equipment_schema.sql`
  - `server/Database/Equipment/equipment_seed.sql`
- Nội dung logic:
  - Bổ sung foundation catalog/template equipment bám `ll.java`: slot/resource/rank/element/gender/durability/tradeable/repair-cost.
  - Giữ raw Java fields thay vì tạo enum/cột policy quá sớm:
    - `Gender` giữ số `ll.h` (`0=Nam`, `1=Nữ`, `2=Cả hai`), không dùng enum C#.
    - `Rank` giữ số `ll.m`, không invent named rank enum.
    - Bỏ các cột/view metadata chưa đủ evidence: `IsRepairable`, `RepairBlockReason`, `IsUpgradeable`, `InventoryCapacityCost`.
  - Seed chỉ là starter/test tối thiểu; không tạo template đại trà khi chưa có dump item thật.
- `dotnet build Twelve.sln` pass sau cleanup: `0 Warning(s), 0 Error(s)`.

### 2026-05-03 — Durability as first-class DB/entity column + broken-item stat gate

- Code đã sửa:
  - `server/Database/Players/players_schema.sql`: thêm cột `Durability INT NOT NULL DEFAULT 30`, `MaxDurability INT NOT NULL DEFAULT 30` vào `PlayerEquipment`; comment mapping `ll.p/tag 139`, `ll.q/tag 144`.
  - `server/Twelve.Core/Entities/PlayerAggregate.cs`: thêm `Durability`, `MaxDurability` vào `PlayerEquipmentEntry` entity.
  - `server/Twelve.Infrastructure/Repositories/PlayerAggregateRepository.cs`: SELECT/INSERT queries bao gồm `Durability`, `MaxDurability`.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`:
    - `BuildEquipmentEntry`: set `Durability = MaxDurability` khi tạo instance mới (Java evidence: `ll.p = ll.q` at creation).
    - `CreateStarterEquipmentEntry`: propagate durability từ `BuildEquipmentEntry`.
    - `GetEquippedModifiers`: thêm filter `entry.Durability > 0` — broken item vẫn mặc nhưng không cộng stat (Remake policy 2026-05-03, bám `da.java` evidence `p != 0`).
    - `RestoreDurability`: set `Durability = MaxDurability` trên entity thay vì chỉ trong RawJson.
    - `ToEquipmentView`: đọc `Durability`/`MaxDurability` từ entry instance thay vì definition.
    - `ResolveEquipment`: override durability từ entry instance thay vì parse RawJson.
- Java evidence applied:
  - `da.java`: status panel chỉ cộng stat equipment khi `p != 0`.
  - `ll.p` (tag 139) / `ll.q` (tag 144): instance durability columns.
- Remake policy applied:
  - Equipment hỏng `p == 0` vẫn mặc được nhưng bị bỏ qua khi aggregate stat (user chốt 2026-05-03).
  - Repair hammer itemId `30099` (user-confirmed repair hammer asset/id).
  - Wing `e=8` tham gia stat aggregation như equipment khác (user chốt 2026-05-03).
- `dotnet build Twelve.sln` pass: `0 Warning(s), 0 Error(s)`.

### 2026-05-03 — Equip/unequip authority cleanup + packet stat recalculation gate

- Code đã sửa:
  - `server/Twelve.Core/Entities/PlayerAggregate.cs`:
    - thêm enum `PlayerEquipmentSlot` giữ raw value gameplay đang chốt: `Armor=0`, `Weapon=1`, `Helmet=2`, `Ring=3`, `Wing=8`;
    - thêm enum `PlayerEquipmentLocation` để đặt tên domain state `Inventory/Equipped` nhưng vẫn lưu boundary bằng `IsEquipped`;
    - thêm computed `Location` cho `PlayerEquipmentEntry`.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`:
    - equip/preview/commit không còn chặn item hỏng `Durability <= 0`, đúng policy đồ hỏng vẫn mặc được;
    - validation slot chỉ mở 5 slot gameplay Phase hiện tại theo `ll.e`: Armor/Weapon/Helmet/Ring/Wing;
    - clone equipment giữ nguyên `Durability` và `MaxDurability` khi preview/commit loadout.
  - `server/Twelve.Application/Handlers/AllocateStatHandler.cs`:
    - khi xử lý CMD 50 phân điểm tiềm năng, recalculation từ packet handler cũng chỉ lấy equipment `IsEquipped && Durability > 0`, tránh bypass gate trong `PlayerContentCatalog.GetEquippedModifiers`.
- Java evidence applied:
  - `ll.e` là slot authoritative, không suy diễn từ tên field decompile.
  - `da.java`: equipment `p == 0` không cộng stat trong status aggregation.
- Remake policy applied:
  - Wing `e=8` là gameplay slot hợp lệ và tham gia aggregate khi còn durability.
  - Broken equipment vẫn mặc được nhưng không cộng stat/effect.
- Pending/Unverified giữ nguyên:
  - Không mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.
  - Không mở slot `e=4` mount/shield/ngựa trong gameplay Phase hiện tại.

### 2026-05-03 — Battle-end durability loss server authority

- Code đã sửa:
  - `server/Twelve.Application/Battle/BattleResultService.cs`
- Nội dung logic:
  - Áp dụng durability loss server-side khi claim battle result:
    - thắng trận: equipment đang mặc mất `1` durability;
    - thua trận/đầu hàng: equipment đang mặc mất `3` durability.
  - Chỉ giảm equipment `IsEquipped` và `Durability > 0`; durability clamp về `0`, không âm.
  - Equipment hỏng vẫn giữ trạng thái đang mặc; stat pipeline recalc sau khi durability đổi để broken item bị loại khỏi aggregate.
  - Victory vẫn giữ surviving HP nhưng clamp lại theo `MaxHp` sau recalc, tránh trường hợp equipment vừa hỏng làm giảm max HP/derived stat.
- Java evidence applied:
  - `ll.p/tag 139` là current durability; `ll.q/tag 144` là max durability.
  - `da.java`/status aggregation chỉ cộng equipment khi `p != 0`.
- Remake policy applied:
  - User confirmation ngày `2026-05-03`: thắng trừ `1`, thua trừ `3`; broken equipment không mất item và không tự tháo.
- Pending/Unverified giữ nguyên:
  - Chưa áp durability loss riêng cho PvP shadow vì hiện service đang bypass loot/penalty cho `BattleSessionKind.PvpShadow`; cần policy riêng nếu muốn áp dụng PvP thật.
  - Không mở combat formula cho special stats `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.

### 2026-05-03 — Repair flow API hardening + equipment state view

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - `PlayerEquipmentItemView` expose thêm `IsBroken`, `ContributesStats`, `CanRepair`.
    - Giữ durability boundary bằng raw `Durability`/`MaxDurability` tương ứng `ll.p/tag 139` và `ll.q/tag 144`.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - `ToEquipmentView` trả state rõ cho client:
      - `IsBroken` khi `Durability <= 0`;
      - `ContributesStats` chỉ true khi item đang mặc và durability `> 0`;
      - `CanRepair` khi có max durability và current durability thấp hơn max.
    - `IsRepairMaterial` chỉ nhận repair hammer `30099`.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - `RepairEquipment` kiểm tra đúng repair material `30099`, yêu cầu inventory còn quantity `> 0`.
    - Consume đúng `1` hammer và gọi `RestoreDurability` để hồi `Durability = MaxDurability`.
    - Không trừ Quan trong repair flow.
  - `client/src/screens/character/shared/characterAppearance.ts`
    - Đồng bộ TypeScript model với equipment state server trả về.
- Java evidence applied:
  - `ll.p/tag 139` là durability hiện tại; `ll.q/tag 144` là max durability.
  - `da.java` chỉ cộng stat equipment khi `p != 0`.
  - `ll.e` vẫn là slot authoritative; `ll.n` vẫn là resource ID authoritative.
- Remake policy applied:
  - Repair dùng một búa `30099`, consume `1`, hồi full durability, không mất Quan.
  - Broken item vẫn mặc được nhưng `ContributesStats=false` và không cộng stat/effect.
  - Wing `e=8` tiếp tục tham gia aggregate như equipment khác khi durability `> 0`.
- Pending/Unverified giữ nguyên:
  - Chưa thêm `IsRepairable` template policy cho nhóm Luyện Ngục vì cần template/item evidence rõ.
  - Chưa mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.

### 2026-05-03 — Equipment runtime API/client wiring + upgrade skeleton gate

- Code đã sửa:
  - `server/Twelve.Core/Interfaces/IPlayerRuntimeService.cs`
    - expose thêm `UpgradeEquipment(...)` ở service boundary.
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - `PlayerEquipmentItemView` expose thêm `CanUpgrade`, `UpgradeStatus` để client biết trạng thái rule nâng cấp.
    - thêm `PlayerUpgradeEquipmentRuntimeRequest` cho endpoint skeleton.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - `ToEquipmentView` set `CanUpgrade=false` khi item đang mặc; `true` khi item đã tháo.
    - `UpgradeStatus` tách rõ policy remake: phải tháo đồ trước; roll thật vẫn pending danh sách đá/bùa gốc.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - thêm `UpgradeEquipment` skeleton an toàn:
      - kiểm tra player/equipment tồn tại;
      - reject nếu equipment đang mặc;
      - không mutate equipment/material/durability khi chưa có danh sách đá/bùa gốc và tỉ lệ roll Java.
  - `server/Twelve.Server/Program.cs`
    - thêm endpoint `POST /player/runtime/equipment/upgrade`.
  - `client/src/screens/character/shared/characterAppearance.ts`
    - đồng bộ model TypeScript với `canUpgrade`, `upgradeStatus`.
  - `client/src/screens/character/status/CharacterStatus.api.ts`
    - thêm client API `upgradeEquipment(...)`.
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`
    - bỏ chặn equip item hỏng ở UI, đúng policy broken item vẫn mặc được.
    - đồng bộ repair hammer UI itemId từ `5010` sang `30099`.
    - menu upgrade/rao bán/drop disable khi equipment đang mặc, đúng rule tháo đồ trước.
    - map icon inventory item theo `iconKind` cho HP/MP/trứng đà điểu/búa sửa đồ/Kim Thạch/Huyết Thạch/Bùa 1/2/3; item chưa có evidence icon tiếp tục dùng chest placeholder UI hiện có, không suy diễn gameplay.
- Audit/verification:
  - Shop/drop/stat-roll Phase hiện tại vẫn đi qua `PlayerContentCatalog.BuildEquipmentEntry(...)`; instance mới lấy stat từ template/range đã có trong catalog path hiện tại, không thêm fallback random mới khi chưa có template gốc đầy đủ.
- Java evidence applied:
  - `ll.e` là slot authoritative; `ll.n` là resource ID authoritative.
  - `ll.p/tag 139` và `ll.q/tag 144` là durability instance.
  - `da.java` chỉ cộng stat equipment khi `p != 0`.
- Remake policy applied:
  - Equipment hỏng vẫn mặc được nhưng không cộng stat/effect.
  - Repair dùng búa `30099`.
  - Upgrade yêu cầu tháo đồ khỏi người.
  - Đồ hỏng vẫn được phép đi vào upgrade gate khi đã tháo, nhưng roll thật chưa bật.
  - Trade/rao bán/drop equipment yêu cầu tháo đồ trước.
- Pending/Unverified giữ nguyên:
  - Chưa consume đá/bùa, chưa roll success/fail/destroy, chưa mutate enhance level vì thiếu danh sách material/rate Java gốc.
  - Chưa mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.

### 2026-05-03 — Open-egg / đập trứng remake policy

- User chốt policy mở trứng:
  - Trứng thường tốn `20,000` Quan.
  - Trứng đà điểu tốn `30,000` Quan.
  - Trứng khủng long tốn `100,000` Quan.
  - Trứng rồng tốn `300,000` Quan.
  - Trứng phượng tốn `300,000` Quan.
- Boundary quan trọng:
  - Trứng không bao giờ mở ra cánh; `Wing/e=8` chỉ đi qua flow chế tạo/crafting riêng sau này.
  - Equipment reward từ trứng chỉ được roll trong các slot `Armor/Weapon/Helmet/Ring`.
  - Sau này sẽ có list equipment/template mở riêng cho mỗi loại trứng; cần cấu hình rõ trước khi bật API thật.
- Pending:
  - Chưa có Java server evidence cho drop rate/cost/reward pool gốc.
  - ItemId đầy đủ cho từng loại trứng, reward chance cụ thể, list equipment theo từng trứng, pity/event multiplier đều còn chờ config/policy tiếp.
- Chỉ sửa tài liệu `.md`, không sửa code server/client → không chạy build.

### 2026-05-03 — Open-egg backend safety skeleton + capacity gate

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - thêm `PlayerOpenEggRuntimeRequest`.
  - `server/Twelve.Core/Interfaces/IPlayerRuntimeService.cs`
    - expose `OpenEgg(...)`.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - thêm `PlayerEggDefinition`;
    - cấu hình skeleton cho `Trứng đà điểu` itemId `30095`, cost `30,000` Quan;
    - giới hạn reward slots ở `Armor/Weapon/Helmet/Ring`, không include `Wing/e=8`.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - thêm `OpenEgg(...)` safety skeleton;
    - validate egg config, inventory có trứng, đủ Quan, túi đồ chưa đầy;
    - chặn mutate/consume khi chưa có `AllowedEquipmentTemplateKeys` rõ ràng.
  - `server/Twelve.Server/Program.cs`
    - thêm endpoint `POST /player/runtime/item/open-egg`.
- Java evidence applied:
  - `go.n` default inventory capacity `50`;
  - inventory capacity count bám `go.b()` theo equipment bag + equipment đang mặc + item stacks.
- Remake policy applied:
  - Cost mở trứng là policy user chốt ngày `2026-05-03`, không phải Java server evidence.
  - Trứng không mở ra cánh; `Wing/e=8` chỉ đi qua flow crafting/chế tạo riêng sau này.
  - API skeleton không fallback random equipment và không tự chọn template khi chưa có reward pool cấu hình.
- Pending/Unverified giữ nguyên:
  - Chưa có Java server evidence cho reward chance/rate.
  - ItemId đầy đủ cho các loại trứng khác, list equipment theo từng trứng, pity/event multiplier vẫn pending.
  - Chưa consume trứng/Quan và chưa tạo equipment instance cho đến khi reward pool cụ thể được cấu hình.
- Build/check:
  - Chạy `dotnet build Twelve.sln` sau khi sửa code server.

### 2026-05-03 — Inventory item icon asset wiring for equipment materials

- Code/assets đã sửa:
  - `client/assets/equipment/items/`
    - Chuẩn hóa tên file item icon đã cắt từ spritesheet theo thứ tự user xác nhận: `hp.png`, `mp.png`, `repair_hammer.png`, `ostrich_egg.png`, `kim_thach.png`, `huyet_thach.png`, `charm_1.png`, `charm_2.png`, `charm_3.png` và các icon pending còn lại.
    - Giữ các icon chưa xác minh gameplay với tên `pending_*.png`, không đặt tên domain nếu chưa có evidence.
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`
    - Thêm resolver icon inventory item theo `CharacterInventoryItem.iconKind`.
    - Map các `iconKind` đã có đủ evidence/policy hiện tại: `potion_red`, `potion_blue`, `peach`, `hammer`, `kim_thach`, `huyet_thach`, `charm_1`, `charm_2`, `charm_3`.
- Java evidence / Remake policy boundary:
  - Repair hammer `30099` là policy/user confirmation đã chốt ngày `2026-05-03`; UI dùng asset `repair_hammer.png`.
  - Kim Thạch/Huyết Thạch/Bùa chỉ được wire icon UI/material identity; chưa dùng để roll upgrade thật.
- Pending/Unverified giữ nguyên:
  - Chưa có danh sách đá/bùa/tỉ lệ upgrade Java gốc nên không consume material, không roll success/fail/destroy, không mutate enhance level.
  - Các icon `pending_*.png` chưa được suy diễn tên gameplay.
  - Chưa mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.

### 2026-05-03 — Raw item id enum boundary for equipment items

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - thêm enum `PlayerItemId` để giữ raw item ids đã được server-authoritative flow sử dụng.
    - raw values giữ nguyên theo catalog/runtime hiện tại: `OstrichEgg = 30095`, `RepairHammer = 30099`.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - `IsRepairMaterial(...)`, catalog item definitions và open-egg skeleton dùng `PlayerItemId` thay vì số trần ở logic code.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - comment repair flow trỏ tới `PlayerItemId.RepairHammer` và vẫn ghi rõ raw itemId `30099`.
- Boundary:
  - Đây là clean-code/authority cleanup, không đổi gameplay behavior.
  - `30099` vẫn là remake policy/user confirmation, không ghi thành Java server evidence.
  - Giữ raw value trong enum; chỉ cast tại boundary catalog/API/storage.
- Pending/Unverified giữ nguyên:
  - ItemId đầy đủ cho các loại trứng/material khác và danh sách đá/bùa/tỉ lệ Java gốc vẫn pending.

### 2026-05-03 — Minimal item catalog fields for inventory icon/resource authority

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - thêm enum `PlayerItemKind` với raw values nội bộ: `Consumable = 0`, `Material = 1`, `Egg = 2`, `RepairMaterial = 3`.
    - thêm enum `PlayerItemEvidenceStatus`: `PendingUnverified = 0`, `RemakePolicy = 1`, `JavaEvidence = 2`.
    - `PlayerInventoryItemView` expose thêm `Kind`, `EvidenceStatus`, `ResourceId`, `IconId`.
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - mở rộng `PlayerItemDefinition` thành item catalog/template tối thiểu, không thêm field thừa ngoài display, stack/use, kind/evidence, resource/icon id.
    - serialize/resolve `kind`, `evidenceStatus`, `resourceId`, `iconId` trong `RawJson`.
    - `OstrichEgg = 30095` và `RepairHammer = 30099` dùng `Kind` enum tương ứng, giữ `ResourceId/IconId = null` vì icon/resource id Java gốc chưa được chứng minh.
- Boundary:
  - `PlayerItemId` chỉ là raw gameplay id cho server logic; không dùng trực tiếp làm asset id.
  - Với equipment, icon vẫn derive từ `ll.n/resourceId` theo Java evidence `iconId = (resId - resId % 10) + 98`.
  - Với item tiêu hao/material như trứng/búa, catalog giữ `ResourceId/IconId` riêng và có `EvidenceStatus`; chưa tự gán raw item id như `30095/30099` thành icon id.
- Remake policy applied:
  - `30095` là trứng đà điểu cho open-egg skeleton theo catalog/runtime hiện tại.
  - `30099` là repair hammer cho cmd 48 repair flow.
- Pending/Unverified giữ nguyên:
  - Java asset/resource id chính thức của trứng đà điểu, búa sửa đồ và material nâng cấp vẫn pending.
  - Danh sách item/material/trứng đầy đủ và tỉ lệ upgrade/drop Java gốc vẫn pending.

### 2026-05-03 — Minimal DB ItemCatalog for inventory items

- Code/DB đã sửa:
  - `server/Database/Equipment/equipment_schema.sql`
    - thêm bảng `ItemCatalog` tối thiểu cho item inventory/server gameplay.
    - các field giữ ở mức cần thiết: `ItemId`, display/description, stack/use, heal/mana/restore kind, icon kind, `Kind`, `EvidenceStatus`, nullable `ResourceId/IconId`, `IsEnabled`, `UpdatedAt`.
    - thêm index theo `Kind` và `ResourceId`.
  - `server/Database/Equipment/equipment_seed.sql`
    - thay placeholder bằng seed `ItemCatalog` tối thiểu cho item hiện đang dùng trong code: `5001..5007`, `30095..30099`.
    - tên/icon kind bám danh sách asset thật trong `client/assets/items/`: `hp`, `mp`, `huyet_thach`, `kim_thach`, `dragon_claw`, `feather`, `x2_exp`, `chicken_egg`, `dinosaur_egg`, `phoenix_egg`, `dragon_egg`, `repair_hammer`.
    - dùng `ON CONFLICT (ItemId) DO UPDATE` để migrator chạy lại an toàn.
- Boundary:
  - `ItemId` là raw gameplay id cho server logic, không dùng trực tiếp làm asset id.
  - `Kind` giữ raw enum value theo `PlayerItemKind`: `Consumable = 0`, `Material = 1`, `Egg = 2`, `RepairMaterial = 3`.
  - `EvidenceStatus` giữ raw enum value theo `PlayerItemEvidenceStatus`: `PendingUnverified = 0`, `RemakePolicy = 1`, `JavaEvidence = 2`.
  - `ResourceId/IconId` để `NULL` cho item chưa có Java asset/resource evidence; không tự gán raw item id như `30095/30099` thành icon id.
- Remake policy applied:
  - Seed `30095..30098` là các egg catalog shell theo asset hiện có; item id chính thức/reward pool Java gốc của từng trứng vẫn pending trừ id đã được runtime policy dùng.
  - Seed `30099` là repair hammer cho cmd 48 repair flow.
  - Pending/Unverified giữ nguyên:
    - Chưa nối runtime repository đọc `ItemCatalog`; server hiện vẫn dùng in-memory `PlayerContentCatalog` item definitions.
    - Java asset/resource id chính thức của item/material/trứng/búa vẫn pending.
    - Các id `5001..5007`, `30095..30097` là seed catalog tạm theo asset/runtime shell, chưa được ghi là Java server evidence.

### 2026-05-03 — User-facing ItemCatalog descriptions cleanup

- Code/DB đã sửa:
  - `server/Database/Equipment/equipment_seed.sql`
    - sửa cột `Description` của các seed `ItemCatalog` hiện có để dùng mô tả vật phẩm hiển thị cho người chơi thay vì ghi chú kỹ thuật reconstruction.
    - `HP`: mô tả là bình máu hồi HP.
    - `MP`: mô tả là bình năng lượng hồi MP.
    - `Huyết Thạch`, `Kim Thạch`, `Vuốt Rồng`, `Lông Vũ`: mô tả là nguyên liệu dùng cho chế tạo/cường hóa/nâng cấp khi hệ thống tương ứng được mở khóa.
    - `X2 EXP`: mô tả là vật phẩm hỗ trợ tăng tốc độ nhận kinh nghiệm trong thời gian hiệu lực.
    - `Trứng Gà`, `Trứng Khủng Long`, `Trứng Phượng Hoàng`, `Trứng Rồng`: mô tả là trứng dùng cho mở thưởng/triệu hồi khi hệ thống trứng được mở khóa.
    - `Búa Sửa Chữa`: mô tả đúng policy repair hiện tại: dùng 1 búa để phục hồi đầy độ bền cho một trang bị bị hư hỏng.
- Boundary:
  - Nội dung evidence/policy/pending vẫn giữ ở comment tài liệu và các cột `EvidenceStatus`; không đưa trực tiếp vào `Description` vì đây là text hiển thị item.
  - Không đổi raw item id, `Kind`, `EvidenceStatus`, `ResourceId/IconId`.
  - Không đổi gameplay behavior, không thêm công thức combat hoặc upgrade/drop/open-egg mới.
- Pending/Unverified giữ nguyên:
  - Java server evidence chính thức cho item ids/resource ids/reward pool/rate vẫn pending như mục trước.

### 2026-05-03 — Open-egg configured reward pool activation (safe Phase)

- Code đã sửa:
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - In-memory `PlayerItemDefinition` đã khai báo đủ các egg item ids hiện có trong `ItemCatalog` seed: `30094..30098`, giữ raw values qua enum `PlayerItemId`.
    - `GetEggDefinition(...)` cấu hình chi phí mở trứng theo policy user chốt:
      - `ChickenEgg/30094`: `20,000` Quan.
      - `OstrichEgg/30095`: `30,000` Quan.
      - `DinosaurEgg/30096`: `100,000` Quan.
      - `PhoenixEgg/30097`: `300,000` Quan.
      - `DragonEgg/30098`: `300,000` Quan.
    - Reward slots vẫn bị giới hạn ở `Armor/Weapon/Helmet/Ring`; không include `Wing/e=8`.
    - Chỉ `OstrichEgg/30095` có reward pool tạm đã cấu hình rõ bằng template catalog hiện có: `fire_guard_vest`, `zap_hunter_helm`, `water_guard_cloak`.
    - Các trứng còn lại giữ `AllowedEquipmentTemplateKeys = Array.Empty<string>()` để endpoint trả lỗi cấu hình và không mutate.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - `OpenEgg(...)` đã chuyển từ skeleton chặn toàn bộ sang flow mutate an toàn khi reward pool được cấu hình rõ:
      - validate item là egg, inventory có egg, đủ Quan, capacity chưa đầy;
      - validate từng template trong reward pool tồn tại trong `EquipmentCatalog`;
      - validate template slot thuộc danh sách slot được phép của egg;
      - chặn cứng `Wing/e=8` trong reward pool;
      - chọn reward bằng deterministic stable index theo player/egg/inventory/equipment state hiện tại;
      - consume đúng `1` egg, trừ đúng `OpenCostQuan`, tạo equipment instance bằng `BuildEquipmentEntryFromTemplate(...)` và lưu collection.
- Java evidence applied:
  - `go.n` default inventory capacity `50`.
  - Inventory capacity count bám `go.b()` theo equipment bag + equipment đang mặc + item stacks.
  - Equipment instance vẫn dùng `ll.e` slot authoritative và `ll.n` resource ID authoritative từ template.
- Remake policy applied:
  - Open-egg costs là policy user chốt ngày `2026-05-03`, không phải Java server evidence.
  - Trứng không mở ra cánh; `Wing/e=8` chỉ đi qua flow crafting/chế tạo riêng sau này.
  - Reward pool chỉ được bật khi template list cấu hình rõ, không fallback random equipment.
- Pending/Unverified giữ nguyên:
  - Chưa có Java server evidence cho reward chance/rate/pity/event multiplier.
  - Reward pool của `30094`, `30096`, `30097`, `30098` vẫn chưa bật vì chưa có list template cụ thể.
  - Reward pool tạm của `30095` là remake-config Phase hiện tại dựa trên template catalog sẵn có, không ghi là reward pool Java gốc.
  - Chưa mở combat formula cho `DamageAbsorb/ArmorPierce/Block/Revive/HpPercent`.

### 2026-05-03 — Equipment persistence snapshot stat aggregation

- Code đã sửa:
  - `server/Twelve.Infrastructure/Repositories/PlayerAggregateRepository.cs`
    - `BuildStatSnapshot(...)` khi restore `PlayerAggregate` từ database đã dùng `PlayerEquipment.RawJson` của equipment đang mặc để tính `PlayerStatSnapshot`.
    - Chỉ aggregate equipment có `IsEquipped = true` và `Durability > 0`.
    - Parse modifier bằng `EquipmentStatModifierParser.Parse(...)`, giữ raw stat/evidence pipeline hiện có.
- Java evidence applied:
  - `PlayerEquipment.RawJson` chứa stat instance đã được build từ `lb` stat block.
  - `ll.e` là slot authoritative; repository không suy diễn slot gameplay từ tên template.
  - `ll.p` durability là authoritative cho trạng thái hỏng khi tính contribution.
- Remake policy applied:
  - Policy user chốt `2026-05-03`: equipment hỏng vẫn mặc được nhưng không cộng stat/effect.
  - Snapshot restore từ database phải phản ánh đúng stat contribution server-side, không chỉ phụ thuộc runtime recalculate sau equip/repair.
- Boundary:
  - Không thêm formula mới cho `DamageAbsorb/tag 200`, `ArmorPierce/tag 201`, `Block/tag 202`, `Revive/tag 203`, `HpPercent/tag 221`.
  - Không đổi schema `PlayerEquipment`; các field hiện có `EquipKey`, `EquipmentCatalogId`, `Slot`, `ResourceId`, `Level`, `Durability`, `MaxDurability`, `IsEquipped`, `RawJson` đủ cho phase persistence hiện tại.
- Pending/Unverified giữ nguyên:
  - Chưa có test project tự động cho repository/persistence; phase này verify bằng `dotnet build`.
  - Chưa chuyển toàn bộ `SaveCollectionsAsync(...)` sang upsert delta; hiện vẫn dùng delete/reinsert transaction cho aggregate collection.

### 2026-05-03 — Equipment stat aggregation helpers + snapshot equip breakdown + BonusAttackPercent

- Code đã sửa:
  - `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
    - `PlayerEquipmentItemView` expose thêm `BonusAttackPercent` để client/UI thấy rõ stat tag `204`.
    - `PlayerRuntimeSnapshot` expose thêm các field tổng contribution từ equipment đang mặc/non-broken:
      - `EquipCuongLuc`
      - `EquipThanPhap`
      - `EquipNoiLuc`
      - `EquipTheLuc`
      - `EquipFlatAttack`
      - `EquipAttackPercent`
      - `EquipCrit`
      - `EquipDefense`
      - `EquipDodge`
      - `EquipMaxHp`
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - `ToEquipmentView(...)` truyền `BonusAttackPercent` từ `PlayerStatModifier.AttackPercent`.
    - Thêm `ReduceDurability(entry, amount)` làm helper server-side để giảm durability, clamp về `0`, không tự tháo/xóa equipment.
    - Thêm `GetEquippedModifierTotal(equipment)` để aggregate modifier của tất cả equipment đang mặc và chưa hỏng.
    - Thêm `SumModifiers(...)` để cộng đúng 10 field đã có evidence/đang được stat pipeline hỗ trợ.
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
    - `BuildSnapshot(...)` lấy aggregate equipment modifier và trả breakdown `Equip*` trong runtime snapshot.
- Java evidence applied:
  - `ll.p/tag 139` là current durability; equipment `p == 0` không đóng góp stat/effect trong status pipeline đã audit từ `da.java`.
  - `lb.n/tag 204` là attack percent; audit hiện có evidence cộng theo `baseAttack * percent / 100`.
  - Stats block tiếp tục parse từ `lb`/`RawJson`; không suy diễn thêm field decompile mơ hồ.
- Remake policy applied:
  - User confirmation `2026-05-03`: equipment hỏng vẫn mặc được nhưng không cộng stat/effect.
  - User confirmation `2026-05-03`: `Wing/e=8` tham gia aggregate như equipment hợp lệ khác.
  - `ReduceDurability(...)` chỉ là helper server-authority; mức trừ durability thuộc caller/flow combat đã chốt riêng.
- Boundary:
  - Không thêm combat formula cho các special stats chỉ có parser/UI label evidence:
    - `DamageAbsorb/tag 200`
    - `ArmorPierce/tag 201`
    - `Block/tag 202`
    - `Revive/tag 203`
    - `HpPercent/tag 221`
  - Không đổi DB schema.
  - Không bật roll upgrade thật.
  - Không thêm fallback slot/stat/template ngoài evidence/policy đã chốt.
- Verification:
  - `dotnet build Twelve.sln` pass: `0 Warning(s), 0 Error(s)`.

### 2026-05-03 — Client equipment breakdown contract + repair UI status

- Code đã sửa:
  - `client/src/screens/character/shared/characterAppearance.ts`
    - đồng bộ TypeScript contract với runtime snapshot equipment breakdown server-side.
    - `CharacterEquipmentItem` expose `bonusAttackPercent`, `isBroken`, `contributesStats` để UI không tự suy từ field mơ hồ.
    - `CharacterAppearance` expose `equipmentStats` gồm các tổng `Equip*` đã được server tính theo authoritative aggregate.
  - `client/src/screens/character/status/CharacterStatus.api.ts`
    - map `bonusAttackPercent` từ `PlayerEquipmentItemView.BonusAttackPercent`.
    - map `equipmentStats` từ các field `Equip*` trong `PlayerRuntimeSnapshot`.
    - derive `isBroken`/`contributesStats` theo durability view từ server contract để client hiển thị rõ trạng thái, không đổi gameplay authority.
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`
    - equipment detail hiển thị thêm `Tấn Công +x%` cho tag `204`.
    - broken equipment hiển thị `Đã hỏng - không cộng chỉ số` và `Hiệu lực: Không cộng chỉ số/effect`.
    - action menu `Sửa chữa` chỉ bật khi item hỏng và inventory có repair hammer raw itemId `30099`.
- Java evidence applied:
  - `ll.p/tag 139` là current durability; `ll.q/tag 144` là max durability.
  - `lb.n/tag 204` là `AttackPercent`, evidence cộng theo `baseAttack * percent / 100`.
  - Icon equipment vẫn resolve theo `ll.n/resourceId` qua helper hiện có; không suy slot/icon từ tên.
- Remake policy applied:
  - User confirmation `2026-05-03`: equipment hỏng vẫn mặc được nhưng không cộng stat/effect.
  - User confirmation `2026-05-03`: repair dùng 1 búa raw itemId `30099`, hồi full durability, không mất Quan.
  - User confirmation `2026-05-03`: `Wing/e=8` tham gia aggregate server-side như equipment hợp lệ khác; UI contract chỉ nhận breakdown từ snapshot.
- Boundary:
  - Đây là client contract/UI wiring cho server authority đã có; không thêm combat formula mới.
  - Không áp combat effect cho `DamageAbsorb/tag 200`, `ArmorPierce/tag 201`, `Block/tag 202`, `Revive/tag 203`, `HpPercent/tag 221`.
  - Không bật roll upgrade thật, không đổi DB schema, không thêm fallback stat/slot.
- Verification:
  - `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` pass: stdout/stderr rỗng.

### 2026-05-04 — Inventory item icon mapping correction

- Code đã sửa:
  - `server/Twelve.Application/Players/PlayerContentCatalog.cs`
    - Sửa `PlayerItemDefinition` runtime catalog cho các item đang xuất hiện trong inventory/battle loot để `iconKind` khớp asset thật trong `client/assets/items/`.
    - `5002`: đổi sang MP consumable, `iconKind = potion_blue`.
    - `5003`: đổi sang `Huyết thạch`, `iconKind = huyet_thach`.
    - `5004`: đổi sang `Kim thạch`, `iconKind = kim_thach`.
    - `OstrichEgg/30095`: đổi `iconKind` từ `chicken_egg` sang `ostrich_egg`.
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`
    - Bổ sung asset resolver key cho `chicken_egg`, `ostrich_egg`, `dinosaur_egg`, `phoenix_egg`, `dragon_egg`.
    - Bổ sung alias `repair_hammer` để khớp server catalog.
- Java evidence / Remake policy:
  - Raw item ids vẫn giữ nguyên theo enum/runtime hiện có; không đổi gameplay id.
  - Đây là mapping hiển thị theo asset/remake catalog hiện có, không phải Java server evidence mới.
  - Equipment icon formula không đổi: equipment vẫn resolve từ `ll.n/resourceId` theo Java evidence `iconId = (resId - resId % 10) + 98`.
- Boundary:
  - Không đổi DB schema.
  - Không đổi combat formula/stat aggregation/repair logic.
  - Không thêm fallback icon gameplay; client chỉ dùng `itemchest` khi `iconKind` chưa có asset mapping rõ.
- Verification:
  - Pending trong bước sau của task: chạy `dotnet build Twelve.sln` và TypeScript check client.

### 2026-05-04 — Inventory detail panel spacing cleanup

- Code đã sửa:
  - `client/src/screens/map/core/MapCharacterDialogs.tsx`
    - Bỏ emoji/icon prefix trước tên equipment/item trong detail panel để tránh lệch text và giữ tên hiển thị gọn.
    - Giữ resolver icon ở grid/slot; không đổi công thức icon equipment.
  - `client/src/screens/map/core/MapCharacterDialogs.styles.ts`
    - Canh lại panel chi tiết trong inventory: `left = 14`, `width = 312`.
    - Tăng padding ngang header/content lên `14` để nội dung không sát viền.
- Java evidence / Remake policy:
  - Đây chỉ là cleanup presentation React Native.
  - Không thêm Java evidence mới.
  - Không đổi slot gameplay, raw item id, raw equipment resource id hoặc equipment stat policy.
- Boundary:
  - Equipment icon formula vẫn giữ theo Java evidence: `iconId = (resId - resId % 10) + 98`.
  - Repair hammer raw itemId `30099` không đổi.
  - Không đổi server API, DB schema, combat formula, stat aggregation hoặc repair flow.
- Verification:
  - Pending trong bước sau của task: TypeScript check client.
