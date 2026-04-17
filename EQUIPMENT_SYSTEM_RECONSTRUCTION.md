# Equipment System Reconstruction

Tài liệu khôi phục hệ thống trang bị (equipment) từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [ll.java](file:///d:/Twelve/reference/redecoded/decompiled/ll.java) | `ll` | Equipment data model |
| [lm.java](file:///d:/Twelve/reference/redecoded/decompiled/lm.java) | `lm` | GameItem data model (consumables, NOT equipment) |
| [mb.java](file:///d:/Twelve/reference/redecoded/decompiled/mb.java) | `mb` | Body-part compositor & sprite loader |
| [hg.java](file:///d:/Twelve/reference/redecoded/decompiled/hg.java) | `hg` | Equipment detail dialog UI |
| [fw.java](file:///d:/Twelve/reference/redecoded/decompiled/fw.java) | `fw` | Item/Equipment tooltip renderer |
| [id.java](file:///d:/Twelve/reference/redecoded/decompiled/id.java) | `id` | Upgrade/combine panel |
| [dc.java](file:///d:/Twelve/reference/redecoded/decompiled/dc.java) | `dc` | Item cell renderer (inventory icon) |
| [lh.java](file:///d:/Twelve/reference/redecoded/decompiled/lh.java) | `lh` | Character info (holds equip array `ll[] D`) |
| [ky.java](file:///d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network packet parser (equip parse at line 1269) |

## Working Folders

- [client/assets/equipment_legacy](file:///d:/Twelve/client/assets/equipment_legacy) — trang bị đã tổ chức theo slot type
- [reference/review_assets/verified_semantic/offline_by_id_or_name](file:///d:/Twelve/reference/review_assets/verified_semantic/offline_by_id_or_name) — flat source (1605 PNG)
- [reference/redecoded/character_meta_parsed.csv](file:///d:/Twelve/reference/redecoded/character_meta_parsed.csv) — meta family map

---

## 1. Equipment Data Model (`ll.java`)

```java
// ll.java — Equipment class
public final class ll {
    public String c;   // Unique key (server ID)
    public String d;   // Display name (Vietnamese)
    public byte   e;   // Equipment slot type (0-12)
    public byte   f;   // Element icon ID
    public byte   h;   // Gender: 0=Nam, 1=Nữ, 2=cả hai
    public int    i;   // Required level
    public int    j;   // Enhancement level (+1, +2, ...)
    public long   k;   // Repair cost
    public int    m;   // Rank (rarity)
    public int    n;   // *** Resource ID *** — key field for sprite lookup
    public int    p;   // Current durability
    public int    q;   // Max durability
    public byte   t;   // Tradeable flag (1=yes)
    public lb     r;   // Stats block (attack, defense, etc.)
}
```

### Slot Table (`ll.e`)

Định nghĩa tại `ll.java` static block (line 29-43):

```java
static {
    int[] nArray = new int[13];
    nArray[0] = 1;   // Giáp/Áo (Armor)       → có visual layer
    nArray[1] = 4;   // Vũ Khí (Weapon)        → có visual layer
    nArray[2] = 2;   // Mũ/Nón (Helmet)        → có visual layer
    nArray[3] = 3;   // Giày/Ủng (Boots)       → stored, KHÔNG composited
    nArray[4] = 5;   // Ngựa/Khiên (Mount)     → affects lh.ad facing flag
    nArray[5] = 9;   // Nhẫn (Ring)            → chỉ có stats
    nArray[7] = 6;   // Bùa 1 (Accessory)      → chỉ có stats
    nArray[8] = 10;  // Bùa 2 (Accessory)      → chỉ có stats
    nArray[9..12] = 99; // Misc/Event slots
    a = nArray;
}
```

### Rank → Display Color (`ll.a(int rank)`)

```java
// ll.java line 87-120
switch (rank) {
    case 0:     return bx.d;                              // Thường (white)
    case 1:     return com.mg.sq.a.g;                     // Tốt (green)
    case 2, 3:  return new by(0xB30B0C);                  // Hiếm (dark red)
    case 4,7,8: return new if(new int[]{0x898012,0xFFFF00}); // Huyền Thoại (gold gradient)
}
```

---

## 2. Body Compositor (`mb.java`)

### 2.1 ResId → Band Conversion

```java
// mb.java line 769-776

// Equipment sprite band (round down to nearest 10)
public static int a(ll ll2) {
    int n2 = ll2.n;          // resId
    return n2 - n2 % 10;     // band = 97105 → 97100
}

// Equipment inventory icon ID
public static int a(int n2) {
    return n2 - n2 % 10 + 98;  // icon = 97105 → 97198
}
```

### 2.2 Character Equipment Loading

`mb.a(lh)` tại line 689-716 — đây là hàm cốt lõi quyết định nhân vật trông thế nào:

```java
public static mb[] a(lh lh2) {
    int[] nArray = new int[4];

    // Scan equipped items, store resId band for each slot
    for (int i = 0; i < lh2.D.length; i++) {
        ll equip = lh2.D[i];
        if (equip.e < 4) {                    // CHỈ slot 0,1,2,3
            int resId = equip.n;
            nArray[equip.e] = resId - resId % 10;  // store band
        }
    }

    mb[] result = new mb[4];

    // [1] Hair layer — from appearance, NOT equipment
    result[1] = new mb(lh2.V.a + 99);

    // [0] Armor layer (e=0) — or default body appearance
    int defaultArmor = lh2.U.a + 99;
    int defaultWeapon = (lh2.f == 1) ? 79999 : 79899;  // male/female

    result[0] = (nArray[0] > 0 && !lh2.Z)
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

### 2.3 Render Composition

`mb.a(lh, boolean)` tại line 719-722:

```java
public static mg a(lh lh2, boolean bl2) {
    mb[] mbArray = mb.a(lh2);
    // Order: armor[0], hair[1], helmet[3], weapon[2]
    return mb.a(lh2, mbArray[0], mbArray[1], mbArray[3], mbArray[2], false);
}
```

Tại `mb.a(...)` line 98-138, compositor tạo hình nhân vật theo thứ tự layer:

```
Layer 1: Base body      = mb.a(frame + 99000, lh2.W)    ← luôn hiện
Layer 2: Hair           = mb.a(hair.e + frame, lh2.V)   ← appearance trait
Layer 3: Armor overlay  = mb.a(armor.e + frame, lh2.U)  ← equipment e=0
Layer 4: Helmet overlay = mb.b(helmet.e + frame)         ← equipment e=2
Layer 5: Weapon overlay = mb.b(weapon.e + frame)         ← equipment e=1
```

---

## 3. Equipment Network Parser (`ky.java`)

### 3.1 Parse Equipment from Packet

`ky.a(ku, int, int, boolean)` tại line 1269-1306:

```java
private static ll a(ku ku2, int n2, int n3, boolean bl2) {
    String key = ku2.b(n2);                              // tag: key string
    byte type = ku2.a((short)84, n2, n3, (byte)0);       // tag 84: slot type (e)
    ll equip = new ll(key, type);

    equip.n = ku2.a((short)4, n2, n3, 0);                // tag 4: *** RESOURCE ID ***
    equip.p = ku2.a((short)139, n2, n3, -1);             // tag 139: current durability
    equip.j = ku2.a((short)27, n2, n3, 0);               // tag 27: enhancement level

    if (bl2) {  // full detail mode
        equip.d = ku2.d((short)26, n2, n3);              // tag 26: display name
        equip.i = ku2.a((short)135, n2, n3, -1);         // tag 135: required level
        equip.f = ku2.a((short)15, n2, n3, (byte)7);     // tag 15: element icon
        equip.h = ku2.a((short)16, n2, n3, (byte)2);     // tag 16: gender
        equip.m = ku2.a((short)138, n2, n3, (byte)0);    // tag 138: rank
        equip.q = ku2.a((short)144, n2, n3, 0);          // tag 144: max durability
        equip.g = ku2.d((short)117, n2, n3);             // tag 117: description
        equip.t = ku2.a((short)85, n2, n3, (byte)1);     // tag 85: tradeable
        equip.k = ku2.a((short)190, n2, n3, (byte)-1);   // tag 190: repair cost
        equip.r = parseStats(ku2, n2, n3);                // tags 118-204: stats
    }
    return equip;
}
```

### 3.2 Character Equipment Array

`ky.b(ku, int, int)` tại line 849-857 — load danh sách trang bị đang mặc:

```java
// Parse lh.D — equipped items array
lh2.D = new ll[ku2.a((short)83, n2, n3)];    // tag 83: equip count
for (int i = 0; i < lh2.D.length; i++) {
    lh2.D[i] = ky.a(ku2, cursor, nextCursor, false);  // parse each equip
}
```

### 3.3 Network Commands

| Cmd | Tên | Mô tả |
|-----|-----|-------|
| 51 | processPickupItem | Nhặt item/equip rơi → dispatch to `b.c(ll)` |
| 96 | requestUpgradeEquip | Yêu cầu nâng cấp → `b.a(session, equipKey, msg)` |
| 97 | modifiedUpgradeEquip | Kết quả nâng cấp → `b.d(key,msg,readyStatus,gold)` |
| 99 | requestCombineEquip | Yêu cầu kết hợp → `b.e(session, msg)` |
| 100 | modifiedCombineEquip | Kết quả kết hợp → `b.a(key,msg,status,gold)` |
| 112 | processEquipChange | Mặc/tháo trang bị → `b.a(equipKey, gold)` |

---

## 4. Equipment UI Components

### 4.1 Equipment Detail Dialog (`hg.java`)

```java
// hg.java line 27-75 — constructor
public hg(ll equip) {
    this.k = equip;

    // Load icon: band + 98
    int iconId = mb.a(equip) + 98;          // line 36
    this.l = pa.a().a(iconId, false);        // load icon image

    // Gender restriction check
    if (equip.h != 2 && equip.h != go.k.f)  // line 44
        this.r = true;                        // wrong gender warning

    // Stat lines from com.mg.sq.a
    this.q = com.mg.sq.a.a(equip);           // line 60: stat strings

    // Buttons
    "Bỏ qua" → dismiss (1000)               // line 69
    "Nhặt"   → pick up (2000)               // line 72
}
```

Render tại `hg.c(Graphics)` line 77-137:
- Icon 32×32 → vẽ tại vị trí `(o.a, o.b)`
- Enhancement `+N` → vẽ bằng gradient font nếu `j > 0`
- `"Yêu cầu cấp: X"` → đỏ nếu level thấp hơn yêu cầu
- `"Độ bền: P/Q"` → đỏ nếu dưới 30%
- Gender text: `"Đồ giành cho NAM"`, `"Đồ giành cho NỮ"`, `"Giành cho cả NAM & NỮ"`

### 4.2 Item Cell Renderer (`dc.java`)

```java
// dc.java line 58-87 — render method
public void a(Graphics g, int x, int y) {
    switch (this.j) {  // type
        case 0:  // EQUIPMENT
            g.drawImage(this.l, x, y, 0);                 // equipment icon
            if (equip.p == 0)                              // durability = 0
                g.drawImage(broken_heart, ...);            // broken overlay
            if (equip.m == 4 || equip.m == 7 || equip.m == 8)  // legendary rank
                drawStarEffect(g, ...);                    // animated star effect
            if (equip.j > 0)                               // enhanced
                drawText("+" + equip.j, ...);              // enhancement text

        case 1,2:  // ITEM (lm, not equipment)
            pc.g(g, item.j, x, y, 0);                     // item icon
            drawText("" + item.g, ...);                    // quantity
    }
}
```

### 4.3 Upgrade/Combine Panel (`id.java`)

3 grid panels: inventory (p), materials (q), result (r)
- `"Nâng cấp"` button → `ks.a().b()` → server cmd 96/99
- `"Phí kết hợp: X KEN"` → displayed fee
- Result: `"Nâng cấp thành công"` or `"Nâng cấp thất bại"`

---

## 5. Organized Asset Folder Structure

```
client/assets/equipment_legacy/
│
├── 00_body_base/                    ← 990xx (11 files)
│   └── body_990xx/                  base body, 10 frames + meta
│
├── 01_default_overlays/             ← defaults khi không mặc đồ (32 files)
│   ├── weapon_male_default_799xx/   79900-79909, 79998
│   ├── weapon_female_default_798xx/ 79800-79809, 79898
│   └── helmet_default_899xx/        89900-89909
│
├── 02_armor_e0/                     ← Giáp/Áo — ll.e==0 (270 files)
│   ├── heavy_tier01_500xx/          50000-50009
│   ├── heavy_tier02_501xx/          ...
│   ├── heavy_tier03_502xx/
│   ├── heavy_tier04_503xx/
│   ├── heavy_tier05_504xx/
│   ├── heavy_tier06_505xx/
│   ├── light_tier01_600xx/          60000-60009
│   ├── light_tier02_601xx/          ...
│   ├── ...                          (10 light tiers total)
│   ├── meta_armor_920xx/            meta-backed armor candidates
│   ├── meta_armor_924xx/
│   ├── meta_armor_925xx/
│   ├── meta_armor_980xx/
│   ├── meta_armor_981xx/
│   ├── meta_armor_982xx/
│   └── meta_armor_fullbody_999xx/
│
├── 03_weapon_e1/                    ← Vũ Khí — ll.e==1 (393 files)
│   ├── weapon_01_701xx/             70100-70109, 70198
│   ├── weapon_02_703xx/             ...
│   ├── ...                          (33 weapon sets)
│   ├── meta_weapon_971xx/           meta-backed weapon candidates
│   ├── meta_weapon_974xx/
│   └── meta_weapon_975xx/
│
├── 04_helmet_e2/                    ← Mũ/Nón — ll.e==2 (231 files)
│   ├── helmet_01_800xx/             80000-80009, 80098
│   ├── helmet_02_802xx/             ...
│   ├── ...                          (16 helmet sets)
│   ├── meta_helmet_940xx/           meta-backed helmet candidates
│   ├── meta_helmet_941xx/
│   ├── meta_helmet_942xx/
│   ├── meta_helmet_943xx/
│   └── meta_helmet_983xx/
│
├── 05_boot_e3/                      ← Giày — ll.e==3 (44 files)
│   ├── boot_01_890xx/               NOTE: stored nhưng KHÔNG composited
│   ├── boot_02_891xx/
│   ├── boot_03_892xx/
│   └── boot_04_893xx/
│
├── 06_mount_e4/                     ← Ngựa/Khiên — ll.e==4 (22 files)
│   ├── mount_candidate_904xx/
│   └── mount_candidate_908xx/
│
├── 07_accessory_e5_e7_e8/           ← Nhẫn/Bùa — ll.e==5,7,8 (56 files)
│   ├── aura_candidate_902xx/        visual aura for legendary accessories
│   ├── aura_candidate_903xx/
│   ├── accessory_candidate_120xxx/   9 files: 120198..120998 (end-98 variant band)
│   ├── accessory_candidate_121xxx/  10 files: 121098..121998 (end-98 variant band)
│   ├── accessory_candidate_122xxx/   6 files: 122098..122598 (end-98 variant band)
│   ├── accessory_candidate_128xxx/   4 files: 128098..128398 (end-98 variant band)
│   ├── accessory_candidate_130xxx/   4 files: 130000, 130098, 130100, 130198
│   └── accessory_candidate_140xxx/   1 file:  140098
│                                    The 12xxxx..14xxxx bands were reclaimed
│                                    from monster_legacy after ID cross-check.
│                                    The `98` suffix is the equipment-icon
│                                    convention, not a monster frame index.
│
├── 08_premium_sets/                 ← Full premium sets (110 files)
│   ├── premium_01_952xx/            server-assigned slot type
│   ├── premium_02_954xx/
│   ├── ...                          (10 premium sets)
│   └── premium_10_963xx/
│
├── 09_ui_icons/                     ← UI elements (5 files)
│   ├── broken_heart.png             dc.java — broken equip overlay
│   ├── star.png                     dc.java — legendary rank effect
│   ├── slotlock.png                 id.java — locked slot
│   ├── blacksmith.png               id.java — upgrade NPC
│   └── effblacksmith.png            id.java — upgrade effect
│
└── README.md
```

## NOT in Equipment Folder

| Band | Lý do | Thuộc hệ thống |
|------|-------|----------------|
| `909xx, 910xx, 912xx` | Tóc/ngoại hình | `createcs_legacy` (appearance) |
| `911xx, 913xx` | Hiệu ứng đặc biệt | Không phải trang bị mặc |
| `100xxx` | Thuốc/nguyên liệu | Hệ thống `lm` (GameItem) riêng |
| `110xxx-140xxx` | Map tiles, NPC icons | Map/NPC system |
| `1M/2M/4Mxxxxxxx` | Skill effects | `skill_legacy` |

---

## Port Order

1. **Equipment data model** — class `Equipment` matching `ll.java` fields
2. **Icon resolver** — `resId → band + 98 → equipment_legacy/{folder}/{id}.png`
3. **Equipment detail dialog** — from `hg.java` spec
4. **Item cell renderer** — from `dc.java` spec (rank color, broken overlay, star)
5. **Character compositor** — overlay body layers theo `mb.a(lh)` logic
6. **Inventory grid** — from `id.java` spec
7. **Upgrade panel** — from `id.java` spec (blacksmith UI)
8. **Network protocol** — equip/unequip/upgrade cmds 51, 96-101, 112

## Reference Skills

When implementing the equipment pipeline, consult these project skills
(in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`          | Domain entities `Equipment`, `Stats`, `Inventory` in `Twelve.Core` |
| `binary-protocol/`       | TLV tags 4 / 26 / 27 / 83-85 / 117-144 / 190-204 for equip packets |
| `database-design/`       | Postgres `Inventories` table with `JSONB` stats block, composite index `(PlayerId, IsEquipped)` |
| `game-mechanics/`        | Server-side damage / defense / durability validation |
| `frontend-design/`       | Skia inventory grid, rank color gradient, broken-heart overlay |
| `clean-code/`            | Naming `EquipmentSlot`, `EquipmentRank`, `EquipmentRef` |
| `vulnerability-scanner/` | Never trust client on `equip.p` (durability) or `equip.r` (stats) |
| `api-patterns/`          | REST contract for `/api/inventory`, `/api/upgrade-equip` |

## Next Step

Xây dựng **equipment asset resolver** module:

```javascript
// Pseudocode
function getEquipIcon(resId) {
    const band = resId - (resId % 10);
    const iconId = band + 98;
    return `equipment_legacy/{folder}/${iconId}.png`;
}

function getEquipFrames(resId) {
    const band = resId - (resId % 10);
    return Array.from({length: 10}, (_, i) => `${band + i}.png`);
}
```
