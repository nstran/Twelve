# L12SQ — Combat Stat Formulas & Display Mapping
> Source: decompiled Java client (jq.java, jr.java, js.java, jp.java, da.java, lh.java, lb.java)
> Last updated: 2026-04-19

---

## 1. lh.java — Character Data Fields

| Field | Meaning |
|-------|---------|
| `lh.h` | Cường Lực (Strength) |
| `lh.i` | Nội Lực (Magic) |
| `lh.j` | Thân Pháp (Agility) |
| `lh.k` | Thể Lực (Vitality) |
| `lh.g` | Character type: `1`=Hỏa/Warrior, `2`=Lôi/Agility, `4`=Thủy/Mage |
| `lh.l`, `lh.n`, `lh.m`, `lh.o` | Equipment bonus: +Strength, +Magic, +Agility, +Vitality |
| `lh.s / lh.r` | curHP / maxHP |
| `lh.u / lh.t` | curMana / maxMana |
| `lh.w / lh.v` | curPower / maxPower |
| `lh.x / lh.y` | minDam / maxDam (set by server) |
| `lh.A` | Dodge Rate |
| `lh.B` | Hit Rate (internal, not displayed directly) |
| `lh.C` | Critical Damage bonus |
| `lh.z` | Defense (base, set by server) |

---

## 2. Display Label Arrays (da.java)

```java
// da.java
String[] U = {"Cường Lực", "Nội Lực", "Thân Pháp", "Thể Lực"};   // base stats
String[] V = {"Tấn Công",  "Chính xác", "Sinh lực"};               // combat col-left
String[] W = {"P.Thủ",     "Né Tránh",  "Chí Mạng"};               // combat col-right
byte[]  aI = {0, 2, 1, 0};                                          // primary-stat highlight per type
```

### Primary stat highlight
`aI[lh.g / 2]` → index into U[] to highlight:

| lh.g | Type | aI[g/2] | Highlighted stat |
|------|------|---------|-----------------|
| 1 | Hỏa (Warrior) | aI[0] = 0 | U[0] = **Cường Lực** |
| 2 | Lôi (Agility) | aI[1] = 2 | U[2] = **Thân Pháp** |
| 4 | Thủy (Mage)   | aI[2] = 1 | U[1] = **Nội Lực** |

---

## 3. jz Interface — Stat Calculator Methods

`jp.a(lh.g)` creates the appropriate calculator (`jq`/`js`/`jr`), then called with:

```java
jz.a(Cường_Lực + equip, Thân_Pháp + equip, Nội_Lực + equip, Thể_Lực + equip)
//     → jp.a                jp.b                jp.c              jp.d
```

**Internal field mapping (jp.java):**

| jp field | Stat |
|----------|------|
| `this.a` (param 1) | Cường Lực |
| `this.b` (param 2) | Thân Pháp |
| `this.c` (param 3) | Nội Lực |
| `this.d` (param 4) | Thể Lực |

**Output method → display variable → label:**

| jz method | da field | Label (da.V / da.W) |
|-----------|----------|---------------------|
| `jz.a()` | `da.ai` | **Sinh lực** (V[2], also = Max HP) |
| `jz.b()` | `da.ak` | **Tấn Công** (V[0]) |
| `jz.f()` | `da.an` | **Chính xác** (V[1]) |
| `jz.d()` | `da.al` | **P.Thủ** (W[0]) |
| `jz.e()` | `da.am` | **Né Tránh** (W[1]) |
| `jz.g()` | `da.ao` | **Chí Mạng %** (W[2]) |
| `jz.c()` | *(unused in display)* | internal hit rate |

---

## 4. Stat Formulas by Character Type

### Type 1 — jq.java (g=1, Hỏa, primary = **Cường Lực**)

| Stat | Formula | Notes |
|------|---------|-------|
| Sinh lực (Max HP) | `Thể_Lực × 6` | `d × 6` |
| Tấn Công | `Cường_Lực` | `a` (raw) |
| Chính xác | `Thân_Pháp × 3` | `b × 3` |
| P.Thủ | `Thân_Pháp / 2` | `b / 2` (int) |
| Né Tránh | `Thân_Pháp × 2` | `b << 1` |
| Chí Mạng | `min(5 + Thân_Pháp / 8, 30)` | capped at 30% |

### Type 2 — js.java (g=2, Lôi, primary = **Thân Pháp**)

| Stat | Formula | Notes |
|------|---------|-------|
| Sinh lực (Max HP) | `Thể_Lực × 4` | `d << 2` |
| Tấn Công | `(Thân_Pháp × 80 + Cường_Lực × 16) / 100` | `(b×80 + a×16) / 100` |
| Chính xác | `Thân_Pháp × 3` | `b × 3` |
| P.Thủ | `Thân_Pháp / 2` | `b / 2` (int) |
| Né Tránh | `Thân_Pháp × 3 / 2` | `b × 15 / 10` |
| Chí Mạng | `min(5 + Thân_Pháp / 8, 30)` | capped at 30% |

### Type 4 — jr.java (g=4, Thủy, primary = **Nội Lực**)

| Stat | Formula | Notes |
|------|---------|-------|
| Sinh lực (Max HP) | `Thể_Lực × 5` | `d × 5` |
| Tấn Công | `Nội_Lực × 13 / 10` | `c × 130 / 100` (= ×1.3) |
| Chính xác | `Thân_Pháp × 2` | `b << 1` |
| P.Thủ | `Thân_Pháp / 2` | `b / 2` (int) |
| Né Tránh | `Thân_Pháp × 3` | `b × 3` |
| Chí Mạng | `min(5 + Thân_Pháp / 8, 30)` | capped at 30% |

> **Lưu ý**: P.Thủ, Né Tránh, và Chí Mạng đều phụ thuộc **Thân Pháp** cho cả 3 loại nhân vật.

---

## 5. Equipment Bonus (lb.java fields applied in da.java)

```java
// Equipment effects on final combat stats:
as  += lb.a;                        // +Cường Lực (input to jz)
au  += lb.b;                        // +Thân Pháp (input to jz)
at  += lb.c;                        // +Nội Lực (input to jz)
av  += lb.d;                        // +Thể Lực (input to jz)
aw  += lb.e;                        // +Tấn Công (added AFTER jz output)
aw  += jz.c() * lb.n / 100;        // +Chính xác bonus % (hit rate)
ay  += lb.g;                        // +Chí Mạng %
az  += lb.f;                        // +P.Thủ
ax  += lb.h;                        // +Né Tránh
aA  += lb.i;                        // +Sinh lực (Max HP)
```

Final display values (da.x()):
```java
da.ai = jz.a() + aA + lh.p      // Sinh lực = base MaxHP + equip + level bonus
da.aj = lh.s + (ai - lh.r) * lh.q / 100 / 100  // curHP (scaled with level)
da.ak = jz.b() + aw             // Tấn Công = base attack + equip
da.an = jz.f()                  // Chính xác = base (no equip add shown here)
da.al = jz.d() + az             // P.Thủ = base defense + equip
da.am = jz.e() + ax             // Né Tránh = base dodge + equip
da.ao = jz.g() + ay             // Chí Mạng % = base crit + equip
```

---

## 6. HP Bar Calculation

```java
maxHP = jz.a() + equipBonus(aA) + lh.p          // = da.ai
curHP = lh.s + (maxHP - lh.r) * lh.q / 100 / 100  // = da.aj
HPbarFill = curHP * barPixelWidth / maxHP
```

---

## 7. Elemental Counter System (hệ khắc)

**Kết luận từ phân tích Java client:** Công thức hệ khắc **KHÔNG có trong client code**.

Toàn bộ tính toán battle outcome (bao gồm elemental advantage) xử lý **server-side**. Client chỉ nhận kết quả damage từ server qua TLV packet rồi render animation.

Từ field `lh.g`: giá trị `1`=Hỏa, `2`=Lôi, `4`=Thủy — chỉ dùng để:
1. Chọn stat calculator (`jp.a(lh.g)` → jq/js/jr)
2. Highlight primary stat trong UI (`aI[lh.g/2]`)

**Chuỗi khắc (suy luận từ tên game / thể loại):**
```
Hỏa (Fire) → khắc → Lôi (Thunder)
Lôi (Thunder) → khắc → Thủy (Water)
Thủy (Water) → khắc → Hỏa (Fire)
```

**Hệ số damage khi khắc:** Chưa xác định từ source (server-side). Cần kiểm tra server packet log hoặc `.NET` server implementation. Thông thường trong game cùng thể loại: **×1.3 ~ ×1.5** bonus damage.

---

## 8. React Native → Java Type Mapping

| React `elementIndex` | lh.g | Type | Stat class | Primary |
|---------------------|-------|------|------------|---------|
| 0 | 1 | Hỏa | jq | Cường Lực |
| 1 | 2 | Lôi | js | Thân Pháp |
| 2 | 4 | Thủy | jr | Nội Lực |

```typescript
// CharacterStatusScreen.tsx (current)
const PRIMARY_STAT: Record<number, number> = { 0: 0, 1: 2, 2: 1 };
// 0→row0=Cường Lực ✓, 1→row2=Thân Pháp ✓, 2→row1=Nội Lực ✓
```

---

## 9. Key Observations

1. **Thân Pháp là stat phòng thủ universal** — P.Thủ, Né Tránh, Chí Mạng đều dùng `jp.b` (Thân Pháp) cho cả 3 loại.
2. **Sinh lực = Max HP** (không phải "HP regen"). Giá trị này hiển thị ở ô V[2] và đồng thời làm `maxHP` cho thanh HP bar.
3. **Chính xác** (V[1]) dùng `jz.f()`, **không phải** `jz.c()` — `jz.c()` là "internal hit rate" dùng cho equip bonus tính thôi.
4. **Tất cả giá trị combat stat là integer** (Java int, không float). Division là integer division (truncate).
5. **Server owns truth**: minDam/maxDam (`lh.x/lh.y`) và Defense (`lh.z`) được server gửi qua packet, client không tự tính.

---

## 10. Chỉ Số Khởi Tạo Khi Tạo Nhân Vật (Level 1)

> **Nguồn gốc:** Hoàn toàn server-side — client chỉ gửi `gender + element(1/2/4) + appearance`.
> Client code (`nw.java`) không chứa giá trị mặc định nào. Đây là giá trị **thiết kế cho remake**.

### Base Stats theo Element

| Chỉ số | Hỏa (g=1) | Lôi (g=2) | Thủy (g=4) |
|--------|-----------|-----------|------------|
| Cường Lực | **15** | 5 | 5 |
| Thân Pháp | 10 | **15** | 10 |
| Nội Lực | 5 | 5 | **15** |
| Thể Lực | 10 | 10 | 10 |
| Điểm tự do (K) | 5 | 5 | 5 |

> Primary stat của từng loại được boost +5 so với base = 10.
> Điểm tự do dùng để phân phối ngay lúc tạo nhân vật.

### Combat Stats tính toán tại Level 1 (chưa phân điểm)

| Combat Stat | Hỏa (Warrior) | Lôi (Agility) | Thủy (Mage) |
|-------------|--------------|--------------|-------------|
| **Sinh Lực (MaxHP)** | 10×6 = **60** | 10×4 = **40** | 10×5 = **50** |
| **Tấn Công** | 15 | (15×80+5×16)/100 = **12** | 15×130/100 = **19** |
| **Chính Xác** | 10×3 = **30** | 15×3 = **45** | 10×2 = **20** |
| **P.Thủ** | 10/2 = **5** | 15/2 = **7** | 10/2 = **5** |
| **Né Tránh** | 10×2 = **20** | 15×15/10 = **22** | 10×3 = **30** |
| **Chí Mạng** | min(5+10/8,30) = **6%** | min(5+15/8,30) = **6%** | min(5+10/8,30) = **6%** |

### Mapping C# Server ↔ Java (TLV tags)

| C# field | Java lh field | TLV Tag | Giá trị mặc định |
|----------|--------------|---------|-----------------|
| `CuongLuc` | `lh.h` | Tag 118 | theo type |
| `ThanPhap` | `lh.j` | Tag 119 | theo type |
| `NoiLuc` | `lh.i` | Tag 120 | theo type |
| `TheLuc` | `lh.k` | Tag 121 | 10 |
| `FreePoints` | `lh.K` | Tag 53 | 5 |

---

## 11. Phân Điểm Tiềm Năng — Cơ Chế và Hiệu Quả

### Cơ chế cốt lõi

```
Player chọn 1 trong 4 chỉ số → chỉ số đó tăng +1 → toàn bộ combat stats tính lại
```

**Quan trọng:** Element/hệ chỉ quyết định **công thức tính combat stats**, không quyết định player được phân vào stat nào. Player hoàn toàn tự do phân vào bất kỳ chỉ số nào. Nếu phân "sai" (VD: Hỏa đổ điểm vào Nội Lực) thì chỉ số gốc vẫn tăng nhưng không có combat stat nào thay đổi.

**Flow server khi nhận lệnh phân điểm:**
```csharp
// Không check element — cứ tăng stat player chọn, trừ điểm, tính lại
player.{ChosenStat} += 1;
player.FreePoints   -= 1;
RecalculateCombatStats(player);   // áp công thức jq/js/jr theo element
```

**Flow tính lại combat stats (RecalculateCombatStats):**
```csharp
// Xác định công thức theo element (lh.g / 2)
// Element 0 (Hỏa) → jq formulas
// Element 1 (Lôi) → js formulas  
// Element 2 (Thủy) → jr formulas
// Rồi tính MaxHp, TanCong, ChinhXac, PThu, NeTranh, ChiMang từ 4 chỉ số gốc
```

---

### Bảng tra: +1 vào chỉ số X → combat stats thay đổi thế nào?

> Source: `jq.java` / `js.java` / `jr.java`. Tất cả là integer division (truncate, không round).

#### Hỏa — jq formulas

| Phân +1 vào | Tấn Công | Chính Xác | P.Thủ | Né Tránh | Chí Mạng | Sinh Lực (MaxHP) |
|-------------|----------|-----------|-------|----------|----------|-----------------|
| **Cường Lực** | **+1** | — | — | — | — | — |
| **Thân Pháp** | — | **+3** | +1 (mỗi 2đ) | **+2** | +1 (mỗi 8đ) | — |
| **Nội Lực** | — | — | — | — | — | — |
| **Thể Lực** | — | — | — | — | — | **+6** |

#### Lôi — js formulas

| Phân +1 vào | Tấn Công | Chính Xác | P.Thủ | Né Tránh | Chí Mạng | Sinh Lực (MaxHP) |
|-------------|----------|-----------|-------|----------|----------|-----------------|
| **Cường Lực** | +16/100 ≈ **+0** *(cần ~6đ mới +1)* | — | — | — | — | — |
| **Thân Pháp** | **+1** *(80/100≈+1)* | **+3** | +1 (mỗi 2đ) | +1 (mỗi 2đ) | +1 (mỗi 8đ) | — |
| **Nội Lực** | — | — | — | — | — | — |
| **Thể Lực** | — | — | — | — | — | **+4** |

#### Thủy — jr formulas

| Phân +1 vào | Tấn Công | Chính Xác | P.Thủ | Né Tránh | Chí Mạng | Sinh Lực (MaxHP) |
|-------------|----------|-----------|-------|----------|----------|-----------------|
| **Cường Lực** | — | — | — | — | — | — |
| **Thân Pháp** | — | **+2** | +1 (mỗi 2đ) | **+3** | +1 (mỗi 8đ) | — |
| **Nội Lực** | **+1** *(130/100=+1 mỗi điểm)* | — | — | — | — | — |
| **Thể Lực** | — | — | — | — | — | **+5** |

---

### Stats "không hiệu quả" theo hệ (dump stat)

| Hệ | Phân vào stat này = lãng phí |
|----|------------------------------|
| Hỏa | **Nội Lực** |
| Lôi | **Nội Lực**, Cường Lực (rất kém — cần ~6đ = 1 Tấn Công) |
| Thủy | **Cường Lực** |

---

### Ghi chú integer division quan trọng khi implement

```csharp
// P.Thủ = ThanPhap / 2  (int division)
// ThanPhap 10 → 5,  ThanPhap 11 → 5,  ThanPhap 12 → 6
// → Tăng thực sự mỗi 2 điểm Thân Pháp

// Chí Mạng = min(5 + ThanPhap / 8, 30)
// ThanPhap  8 → 6%,  ThanPhap 16 → 7%,  ThanPhap 200 → 30% (cap)
// → Tăng thực sự mỗi 8 điểm Thân Pháp

// Né Tránh Lôi = ThanPhap * 15 / 10  (int division)
// ThanPhap 15 → 22,  ThanPhap 16 → 24  (tăng 2 mỗi 2 điểm)

// Tấn Công Lôi = (ThanPhap * 80 + CuongLuc * 16) / 100
// +1 ThanPhap: delta = 80/100 → thực tế +1 mỗi điểm (xấp xỉ)
// +1 CuongLuc: delta = 16/100 → thực tế +1 mỗi ~6 điểm

// Tấn Công Thủy = NoiLuc * 130 / 100
// +1 NoiLuc: +1 Tấn Công mỗi điểm (13 → 14 → 15...)
```

---

## 12. Server Implementation — StatCalculator & AllocateStatHandler

> Đây là ánh xạ từ công thức Java sang C# server đã được implement.
> Files: `Twelve.Core/GameLogic/StatCalculator.cs`, `Twelve.Application/Handlers/AllocateStatHandler.cs`

### StatCalculator.cs (Twelve.Core/GameLogic)

```csharp
// Hai method public:

// 1. Tính thuần — KHÔNG mutate player
CombatStats Calculate(Player player)

// 2. Tính rồi áp MaxHp vào player (clamp Hp nếu vượt)
CombatStats RecalculateAndApply(Player player)

// CombatStats record:
record CombatStats(int MaxHp, int TanCong, int ChinhXac, int PThu, int NeTranh, int ChiMang)
```

Gọi `RecalculateAndApply` bất cứ khi nào base stats thay đổi (phân điểm, lên level, equip).

### Packet Protocol — Phân Điểm (CMD 50 / CMD 180)

| Direction | CMD | Mô tả |
|-----------|-----|-------|
| Client → Server | **50** `AllocateStatRequest` | Gửi chỉ số muốn tăng |
| Server → Client | **180** `AllocateStatResponse` | Trả về stats đã cập nhật |

**Request Tags (CMD 50):**

| Tag | Code | Type | Giá trị |
|-----|------|------|---------|
| StatChoice | **50** | int | 0=CuongLuc, 1=ThanPhap, 2=NoiLuc, 3=TheLuc |

**Response Tags (CMD 180):**

| Tag | Code | Nội dung |
|-----|------|---------|
| CuongLuc | 118 | Giá trị mới sau phân điểm |
| ThanPhap | 119 | |
| NoiLuc | 120 | |
| TheLuc | 121 | |
| FreePoints | 53 | Điểm còn lại |
| MaxHp | 130 | Sinh Lực mới (tính lại) |
| TanCong | 131 | Tấn Công mới |
| ChinhXac | 132 | Chính Xác mới |
| PThu | 133 | P.Thủ mới |
| NeTranh | 134 | Né Tránh mới |
| ChiMang | 135 | Chí Mạng % mới |

**Server flow (AllocateStatHandler):**
```
1. Guard: IsAuthenticated + player.Element != null
2. Guard: player.FreePoints > 0
3. Guard: StatChoice in [0, 3]
4. player.{ChosenStat}++; player.FreePoints--;
5. combat = StatCalculator.RecalculateAndApply(player)  → player.MaxHp cập nhật
6. _playerRepository.UpdateAsync(player)                → lưu DB
7. Gửi CMD 180 với 11 tags (base stats + FreePoints + 6 combat stats)
```

**Error response:** Gửi CMD 180 với Tag 1 (Message) chứa chuỗi lỗi.

### TLV Tag Reference — Base Stats

| TagCode | ID | Java field | C# field | Ghi chú |
|---------|----|-----------|----------|---------|
| `CuongLuc` | 118 | `lh.h` | `Player.CuongLuc` | Cường Lực / Strength |
| `ThanPhap` | 119 | `lh.j` | `Player.ThanPhap` | Thân Pháp / Agility |
| `NoiLuc` | 120 | `lh.i` | `Player.NoiLuc` | Nội Lực / Magic |
| `TheLuc` | 121 | `lh.k` | `Player.TheLuc` | Thể Lực / Vitality |
| `FreePoints` | 53 | `lh.K` | `Player.FreePoints` | Điểm chưa phân |
| `StatChoice` | 50 | *(client-side only)* | *(request tag)* | 0-3, chỉ trong CMD 50 |
