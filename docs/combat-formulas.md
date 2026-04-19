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
