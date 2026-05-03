# Equipment System Reconstruction

Tài liệu khôi phục hệ thống trang bị (equipment) từ Java client cũ.

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

// Clone — deep copy tất cả fields (trừ stats block chỉ copy ref)
public ll d() { /* ... copies all fields ... */ }

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
| 4 | 5 | Ngựa/Khiên (Mount) | ❌ affects `lh.ad` flag |
| 5 | 9 | Nhẫn (Ring) | ❌ stats only |
| 6 | 0 | (unused) | — |
| 7 | 6 | Bùa 1 (Accessory) | ❌ stats only |
| 8 | 10 | Bùa 2 (Accessory) | ❌ stats only |
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

---

## 2. Stats Block (`lb.java`)

### 2.1 All 15 Stat Fields

Suy luận từ `lb.toString()` + `com.mg.sq.a.a(ll)` stat string builder:

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

### 4.2 Inventory Operations

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

### 4.3 Inventory Full Check

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

**Lưu ý bug decompile:** Ở line 1287-1288, decompiler tạo 2 `new lb()` nhưng chỉ assign `lb2` vào `equip.r`. Field `a` bị assign cho object bỏ đi. Logic thật cần assign `lb2.a = tag 118`.

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
| 112 | processEquipChange | Mặc/tháo trang bị → `b.a(equipKey, gold)` |

### 7.5 Client Senders (`ks.java`)

Các sender sau là bằng chứng client-side về payload cần server tương thích:

| Flow | Java call | Payload suy luận |
|------|-----------|------------------|
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

### Phase 1 — Data Model & Protocol

- [ ] Tạo backend/domain model `Equipment` bám sát `ll.java` fields:
  - `Key`, `SlotType`, `ResourceId`, `EnhancementLevel`, `Durability`, `MaxDurability`, `Rank`, `RequiredLevel`, `Gender`, `ElementIcon`, `Description`, `Tradeable`, `RepairCost`, `Stats`.
  - Comment nguồn: `ll.java`, `ky.java`.
- [ ] Tạo `EquipmentStats` bám sát `lb.java` 15 fields + tag mapping:
  - `Strength`, `Agility`, `Magic`, `Vitality`, `Attack`, `Defense`, `CriticalRate`, `Dodge`, `Hp`, `DamageAbsorbPercent`, `ArmorPiercePercent`, `BlockPercent`, `RevivePercent`, `AttackPercent`, `HpPercent`.
  - Comment nguồn: `lb.java`, `ky.java`, `com/mg/sq/a.java`.
- [ ] Parser/serializer equipment packet phải phân biệt:
  - Minimal equip (`bl2=false`) cho `lh.D`
  - Full detail (`bl2=true`) cho inspect/detail/drop/upgrade result.
- [ ] Sửa đúng decompile bug: assign tag `118` vào `lb2.a`, không tạo object bỏ đi.

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
EquipmentInstance = generated item: unique key, rolled stats, current durability, enhancement, repairable flag, tradeable flag.
```

### 13.2 Equip validation policy

Server phải authoritative validate khi mặc/tháo:
- Sai giới tính: **không mặc được**. Client có thể tô nền hơi đỏ/warning, nhưng server vẫn phải reject.
- Chưa đủ level: **không mặc được**.
- Không có giới hạn class/phái/hệ ngoài level + gender theo gameplay memory hiện tại.
- One item per slot theo `ll.e` như `cz.java`.
- Khi giao dịch giữa người chơi, đồ phải **tháo ra khỏi người trước**, không trade trực tiếp đồ đang mặc.

Slot notes:
- `e=4` trong client table từng ghi mount/shield/ngựa: gameplay memory xác nhận **chưa phát triển item này**. Giữ slot trong model/protocol nhưng không implement gameplay effect cho tới khi có dữ liệu.
- `e=9..12`: gameplay memory xác nhận hiện chỉ cần note cho **cánh**. Chưa implement full event/cosmetic behavior nếu chưa có item data/screenshot.
- `hh.java` main equipped UI chỉ có `dc[] F = new dc[6]`, nên slot đặc biệt/cánh không được nhét bừa vào 6 ô chính nếu chưa xác định UI gốc.

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
  - Asset/icon user xác nhận: `client/assets/equipment/09_ui_icons/30099.png`.
- Mỗi lần repair consume **1 búa**, hồi đầy `CurrentDurability = MaxDurability`, không dùng KEN/Quan trực tiếp.
- Equipment mới rơi/mua/được cấp luôn khởi tạo full durability: `CurrentDurability = MaxDurability`.
- **Chốt gameplay 2026-05-03:** durability/max durability là dữ liệu lưu DB riêng cho từng equipment instance, không chỉ là default/template chung.
- **Chốt gameplay 2026-05-03:** mỗi món đồ có thể có `MaxDurability` riêng; nếu template/server generation không chỉ định thì dùng default ban đầu `30`.
- **Chốt gameplay 2026-05-03:** upgrade/enhancement cũng tăng độ bền tối đa của chính equipment instance đó; công thức tăng cụ thể sẽ được cấu hình theo tier upgrade khi implement.
- **Chốt gameplay 2026-05-03:** upgrade thành công giữ nguyên `CurrentDurability`; upgrade thất bại không trừ durability.
- **Chốt gameplay 2026-05-03:** equipment có durability `p == 0` vẫn mặc được và vẫn nằm ở slot trang bị, nhưng toàn bộ stat/effect của món đó không còn tác dụng cho status/combat.
- Có các equipment **không thể sửa**, đặc biệt một số item trong map Luyện Ngục. Cần thêm field server-side rõ ràng:
  - `IsRepairable` — có được dùng búa sửa hay không.
  - `RepairBlockReason` optional để trace item đặc biệt.
- Đề xuất model:
```text
CurrentDurability        // stored per equipment instance in DB
MaxDurability            // stored per equipment instance in DB
IsRepairable
RepairBlockReason
IsUpgradeable
RepairItemId / HammerItemId = 30099
DefaultMaxDurability = 30 // fallback only when generated item/template has no explicit max durability
DurabilityBonusByEnhancementLevel
```

Mapping với Java client:
- `ll.p` = current durability.
- `ll.q` = max durability.
- `ll.k > 0` làm `ll.c()` trả true trong client, nhưng server remake dùng `IsRepairable` làm source of truth; khi serialize về client có thể map `IsRepairable=false` thành `ll.k=-1`.
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
2. Validate template/instance allows upgrade via server field IsUpgradeable.
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
  - `InputEquipmentSlot/Rank/LevelRange/EnhancementRange` optional filters
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

### 13.8 Asset/template evidence still pending

Cần chờ người dùng cung cấp thêm:
- Equipment template dump/list: name, resId, slot, level, stats range, rank, gender, max durability, repairable/tradeable.
- Screenshot/video item mẫu.
- Giải thích các asset band `12xxxx-14xxxx`.
- Premium/event set `95xxx/96xxx`: gameplay memory xác nhận là item hiếm/event, sẽ phát triển event sau.
- Một số item đặc biệt trong map Luyện Ngục không sửa được; cần template evidence để đánh dấu chính xác `IsRepairable=false`.

Until then:
- Không seed cứng toàn bộ asset band thành equipment.
- Chỉ seed item có bằng chứng từ template/screenshot/memory.
- Event/premium items phải để `Event/Premium pending`, không trộn vào shop/drop thường.

### 13.9 Initial server implementation plan from current evidence

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
  - Broken equipment can still be upgraded if `IsUpgradeable=true`.
  - Destroy outcome deletes equipment instance permanently.
- Combine implemented via server-configured `CombineRecipes`, not placeholder.
- Server key generation: use sortable unique string keys (ULID-style or equivalent) so keys are compact, unique and log/debug friendly without exposing player sequence assumptions.

Chưa implement nếu chưa có thêm evidence:
- Original Java server material IDs/names for upgrade stones, luck items, and protection items. Current section `13.5.6` is remake taxonomy.
- Market tax formula.
- Special stat combat effects beyond currently confirmed status aggregation.
- Slot `e=4` effect.
- Cánh/event slot behavior beyond storing/displaying when data arrives.

---

## 14. Coverage Estimate / Remaining Audit

### 14.1 Mức độ đã rà soát

Ước lượng hiện tại: **~99.5% phần equipment client-side core đã được gom vào tài liệu**.

Đã đọc/đối chiếu các nhóm chính:
- Data/protocol: `ll`, `lb`, `ky`, `ks`.
- Inventory/equip: `go`, `gp`, `hh`, `cz`, `dc`, `fw`, `hg`.
- Visual: `mb`.
- Shop/repair/forge/trade: `ia`, `gx`, `lq`, `hl`, `id`, `ho`, `of`.
- Status/stat display: `com/mg/sq/a`, `da`.

Phạm vi đã đủ để lên kế hoạch port core equipment gồm: model, stats, inventory, equip/unequip, icon/frame resolver, character compositor, detail/tooltip/cell UI, repair, shop preview/buy, upgrade/combine UI flow, client sender payloads và response callback shape chính cho `96/97/99/100/112`.

### 14.2 Còn cần audit trước khi code server hoàn chỉnh

Các mục dưới đây không chặn plan core equipment, nhưng cần đối chiếu thêm khi triển khai server/API chính thức để tránh đoán sai những phần nằm ngoài evidence equipment client-core:

- Combat/battle source để xác nhận effect runtime của 5 stat đặc biệt của `lb`: absorb/pierce/block/revive/hpPercent. Hiện chỉ chắc chắn chúng được parse/display; `AttackPercent` đã chắc chắn được cộng theo `baseAttack * percent / 100`.
- Asset/meta đối chiếu `ll.n` thật từ server dump nếu có, đặc biệt các band `12xxxx-14xxxx`, mount/shield/accessory/premium.
- Các màn thương mại phụ `hn.java`/`hq.java` nếu về sau cần phục dựng đầy đủ UI chợ/rao bán ngoài core equipment. Đã search xác nhận có wrap `ll` qua `lq`, chưa thấy thay đổi model/rule core.
- Byte-perfect serializer cho mọi nhánh optional của `96/97/99/100/112` vẫn nên test với client thật; tài liệu hiện đã ghi đủ tag shape chính từ parser Java.

---

## 15. Nhật ký chỉnh sửa

### 2026-05-03

- Rà Java client equipment-related classes:
  - `ll.java`, `lb.java`, `ky.java`, `mb.java`, `hg.java`, `fw.java`, `dc.java`, `id.java`, `gp.java`, `cz.java`, `go.java`, `com/mg/sq/a.java`.
- Rà bổ sung vòng inventory/shop/repair/network sender:
  - `hh.java`, `hl.java`, `ia.java`, `lq.java`, `ks.java`.
- Rà bổ sung status/combine sau search toàn cục:
  - `da.java`, `ho.java`.
- Bổ sung các phần còn thiếu:
  - Full `ll` fields (`b/l/o/s/u` included).
  - Full 15-field `lb` stats + network tag mapping, including tag `221`.
  - Equipment sort comparator `gp.java`.
  - Global inventory/capacity logic `go.java`.
  - Equip/unequip replacement and visual rebuild rule `cz.java`.
  - Inventory equipment screen flow từ `hh.java`: equipped cell `F[]`, actions mặc/tháo/sửa/nâng cấp/rao bán/vứt bỏ, target-slot highlight.
  - Repair dialog `hl.java`: búa sửa chữa, empty hammer warning, sender `ks.a().a(hammerItemId, equipKey)`.
  - Shop preview/buy flow từ `ia.java`/`lq.java`: product wrapper, mặc thử, giỏ hàng, clone equipment và assign server key khi mua thành công.
  - Network sender payloads từ `ks.java`.
  - Upgrade panel flow `id.java`.
  - Status panel rule từ `da.java`: equipment `p == 0` không cộng stat.
  - Combine panel variant `ho.java`: sender action `0/1`, final combine arrays, result success/fail string.
- Rà bổ sung exact callback/result packet shape trong `ky.java` cho command `96/97/99/100/112`:
  - Tách request response (`96`, `99`) khỏi modified result (`97`, `100`) và equip-change (`112`).
  - Ghi rõ tags `83`, `186`, `187`, `188`, `114`, `106`, `132`, `157`, `175`, `1`.
- Rà bổ sung usage stat đặc biệt `lb.j..o`:
  - `lb.n` (`AttackPercent`) chắc chắn ảnh hưởng aggregation theo `baseAttack * percent / 100`.
  - `lb.o` (`HpPercent`) và `lb.j/k/l/m` hiện chắc chắn parse/display nhưng chưa có công thức combat/status từ phần equipment core.
- Rà search phụ `hn.java`/`hq.java` quanh market/trade wrapper `lq` chứa `ll`; xác nhận không đổi core model/rule.
- Rà bổ sung `gx.java`:
  - Confirm cart multi-buy equipment dialog dùng `lq.e` → `ll`, icon `mb.a(ll)+98`, stat lines `com.mg.sq.a.a(ll)`, rank color `ll.a(rank)`, tổng tiền từ `lq.d`.
- Rà bổ sung `of.java`:
  - Confirm trade/transaction panel add/remove equipment bằng `dc`, icon convention cũ, key `ll.c`, log cập nhật/lấy lại và danh sách kiểm tra giao dịch.
- Rà bổ sung `ks.java` trade/session sender names:
  - `k()`, `k(String)`, `l(String)`, `l()`, `m()`, `n()` dùng cmd `55/56` với state byte/session; giữ riêng khỏi equip/upgrade/combine.
- Nâng coverage estimate từ ~99% lên ~99.5% cho client-side equipment core.
- Gỡ phần thừa/không phải Java source evidence:
  - Removed old `.agent/skills/` reference table.
  - Reworded asset exclusions so `12xxxx-14xxxx` are marked mixed/need audit rather than blindly excluded.

### 2026-05-03 — Server-side gameplay memory decisions

- Bổ sung section server-side reconstruction do không có Java server gốc, nguồn từ gameplay memory/user-provided evidence ngày `2026-05-03`.
- Chốt vòng đời equipment:
  - Quái rơi hộp trên đất, mở dialog `Nhặt/Bỏ qua`.
  - Shop/nhiệm vụ/event có thể cấp equipment.
  - Drop phân cấp theo level/map/quái.
  - Stats equipment instance random trong range từ template.
- Chốt equip validation server-side:
  - Sai giới tính không mặc được.
  - Chưa đủ level không mặc được.
  - Không giới hạn class/phái/hệ ngoài level + gender ở evidence hiện tại.
  - Trade phải tháo đồ trước.
  - Slot `e=4` chưa phát triển; slot `9..12` note cho cánh/event.
- Chốt durability/repair:
  - Thắng trận trừ `1` durability.
  - Thua trận trừ `3` durability.
  - Durability về `0` chỉ broken, không mất đồ.
  - Broken equipment không có tác dụng stat/effect.
  - Repair dùng duy nhất `1` búa mỗi lần.
  - Có đồ không thể sửa, cần field `IsRepairable`.
- Chốt economy/equipment shop:
  - Shop bán bằng KEN gốc nhưng remake dùng đơn vị `Quan`.
  - Equipment không bán lại NPC.
  - Có market/rao bán và thuế, công thức pending.
- Chốt pending:
  - Combine pending.
  - Tradeable visual, template dump, item sample, `12xxxx-14xxxx`, premium/event set sẽ chờ người dùng cung cấp thêm.

### 2026-05-03 — Final pre-code gameplay decisions from user confirmation

- Chốt 20 policy trước khi code:
  - Búa sửa đồ chỉ có 1 loại, icon `client/assets/equipment/09_ui_icons/30099.png`.
  - Một số item Luyện Ngục không sửa được; thêm field `IsRepairable`.
  - Đồ mới rơi/mua luôn full durability.
  - Upgrade thành công giữ nguyên current durability; chỉ tăng max durability nếu config có.
  - Upgrade fail không trừ durability.
  - Đồ broken vẫn nâng cấp được nếu `IsUpgradeable=true`; thêm field `IsUpgradeable`.
  - Đồ đang mặc không được upgrade trực tiếp, phải tháo ra túi.
  - Shop equipment roll stat random trong range.
  - Server sinh key dạng sortable unique string/ULID-style hoặc tương đương.
  - Tất cả equipment mặc định trade được.
  - Gender mapping giữ đúng Java `0=Nam`, `1=Nữ`, `2=Cả hai`.
  - Slot cánh/event khi thiếu data chỉ lưu DB + inventory icon, chưa cộng stat/chưa render.
  - Upgrade destroy xóa hẳn equipment instance và báo mất đồ.
  - Bùa chống vỡ giữ đồ nhưng vẫn có thể tụt cấp.
  - Bảo hộ hoàn hảo fail thì giữ nguyên cấp và không vỡ.
  - Phase đầu upgrade chỉ consume material, fee để sau.
  - Server tạo trước template/stat range/drop thấp để user review; drop pool có thể gồm HP/MP/egg, đập trứng có thể ra equipment.
  - DB tách chuẩn `EquipmentTemplates` và `PlayerEquipment`.
  - Inventory default capacity `50`, sau này mở rộng.
  - Combine phải làm hoàn chỉnh bằng recipe/config server-side, không placeholder.
- Các quyết định này là `Source: user-provided gameplay confirmation 2026-05-03 + Java client equipment UI/protocol audit`.

### 2026-05-03 — Upgrade hard-mode policy

- User xác nhận upgrade:
  - Max `+15`.
  - Từ `+5` trở lên tỉ lệ phải thấp.
  - Từ `+10` trở lên phải hard, khoảng `5% → 1%`.
  - Fail có thể tụt cấp, vỡ/mất đồ.
  - Có đồ bảo hộ.
- Bổ sung upgrade policy remake vào section `13.5`:
  - Success roll dùng basis point `1..10000`.
  - Base success rates: `+1 90%`, `+2 80%`, `+3 70%`, `+4 60%`, `+5 45%`, `+6 35%`, `+7 25%`, `+8 18%`, `+9 12%`, `+10 5%`, `+11 4%`, `+12 3%`, `+13 2%`, `+14 1.5%`, `+15 1%`.
  - Cap sau luck/event: `+1..+4 95%`, `+5..+9 50%`, `+10..+12 10%`, `+13..+15 5%`.
  - Failure table: early fail mất phí/nguyên liệu, mid tier tụt cấp, high tier có destroy chance nếu không bảo hộ.
  - Destroy chance inside failed attempt for `+10..+15`: `10%`, `15%`, `20%`, `28%`, `35%`, `45%`.
  - Protection items: chống tụt, chống vỡ, bảo hộ hoàn hảo.
  - Luck items: `+1%`, `+2%`, `+3%`, `+5%` before cap.
  - Materials currently reconstructed as remake taxonomy because original Java server item IDs are unknown: basic/intermediate/advanced/refined/divine upgrade stones.
  - Fee formula: `feeQuan = baseByRank * targetLevel^2`.
  - Enhancement stat formula: `enhancedFlatStat = baseFlatStat + floor(baseFlatStat * bonusPercent[level] / 100)`.
  - Initial safe policy only enhances flat stats; percent/special stats stay unchanged until combat formulas are fully reconstructed.
  - Tradeable visual, template dump, item sample, `12xxxx-14xxxx`, premium/event set sẽ chờ người dùng cung cấp thêm.
