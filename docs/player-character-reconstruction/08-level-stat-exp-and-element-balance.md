# Level / Stat / EXP / Khắc Hệ Balance Spec

Tài liệu này là **spec cân bằng gameplay mới** cho player/character trong server remake khi không còn server Java cũ.

> **Quyết định 2026-04-25 — Java status là nguồn truth tuyệt đối:** các chỉ số status/derived stat gửi client (`lh.r/x/y/z/A/B/C`) phải port đúng Java `jp/jq/js/jr` và bridge `com.mg.sq.a.a(lh)`, không áp rule remake/off-element soft cap tại tầng hiển thị nhân vật. Mọi rule cân bằng hybrid/soft-cap chỉ được phép áp ở tầng battle/skill/resource riêng khi có ghi chú rõ là remake.

Mức chắc chắn:
- **Java evidence chắc chắn**:
  - `lh.h/i/j/k` là 4 chỉ số nền.
  - `lh.u/t` là MP hiện tại/tối đa.
  - `lh.w/v` là Power/nộ hiện tại/tối đa.
  - Battle HUD `mx` vẽ HP/MP/Power.
  - `lh` là truth payload nhân vật.
- **Không có evidence server cũ đầy đủ**:
  - Công thức EXP chính xác.
  - Công thức mỗi level tăng bao nhiêu điểm tiềm năng.
  - Công thức HP/MP/Power gain khi match gem.
  - Công thức Thân Pháp ảnh hưởng movement ngoài map.
  - Công thức khắc hệ server-side.
- Vì vậy tài liệu này chốt **rule remake có kiểm soát**, ưu tiên:
  - Java-like integer math.
  - Dễ cân bằng đến level 250.
  - Không phá map/collision.
  - Không làm PvP vỡ vì né/chí mạng/resource quá cao.
  - Cân bằng được 3 hướng build: Cường Lực, Nội Lực, Thân Pháp.

---

## 1. Chốt Level Cap và Điểm Tiềm Năng

```text
MaxLevel = 250
PotentialPerLevel = 5
InitialFreePoints = 0
BaseStrength = 10
BaseAgility = 10
BaseMagic = 10
BaseVitality = 10
```

Tổng điểm tiềm năng nhận từ level:

```text
InitialFreePointsAtLv1 = 0
TotalPotentialAtLv250 = (250 - 1) * 5 = 1245
```

Level 1 mới tạo nhân vật **không có điểm tiềm năng dư**. Điểm tiềm năng chỉ bắt đầu cộng khi lên cấp: mỗi level-up `+5`.

Không có bonus theo mốc level.

Lý do:
- Đơn giản, giống tinh thần game Java RPG cũ.
- Không làm level cao quá dư điểm.
- 1245 điểm đủ tạo build khác biệt.
- Không cộng sẵn 5 điểm ở level 1; nếu không, tổng điểm thực tế sẽ thành 1250 và màn nhân vật mới hiển thị sai `Điểm = 5`.
- Dễ cân bằng PvE/PvP.
- Dễ kiểm tra và rollback nếu cần.

---

## 2. Mapping 4 Chỉ Số Gốc

```text
Cường Lực = Strength
Thân Pháp = Agility
Nội Lực = Magic
Thể Lực = Vitality
```

Nguyên tắc tính modifier:

```text
effectiveStat = max(0, stat - 10)
```

Baseline `10` là mốc không cộng/không trừ modifier đặc biệt. Điều này giúp level thấp ổn định và tránh stat dưới baseline gây hiệu ứng âm ngoài ý muốn.

### 2.1 Chọn Hệ Nhân Vật và Phân Điểm Khác Hệ

Nhân vật có **hệ chính** khi tạo/chọn nhân vật:

```text
MainElement = Strength | Agility | Magic
```

Mapping:

```text
MainElement Strength = hệ Cường Lực
MainElement Agility = hệ Thân Pháp
MainElement Magic = hệ Nội Lực
```

Nguyên tắc quan trọng:
- Người chơi **nên tăng điểm theo hệ chính** để đạt hiệu quả cao nhất.
- Nhưng server **không nên cấm tuyệt đối** việc tăng điểm khác hệ.
- Tăng điểm khác hệ vẫn có tác dụng, nhưng chỉ nên là **hybrid/support**, không được mạnh hơn build đúng hệ.
- `Thể Lực` là stat chung, hệ nào tăng cũng hợp lý.

Lý do không cấm cộng khác hệ:
- Game RPG cũ thường cho người chơi tự do build.
- Có người muốn Cường Lực nhưng cộng thêm Nội Lực để đủ MP dùng skill.
- Có người muốn Nội Lực nhưng cộng thêm Thể Lực để sống lâu hơn.
- Có người muốn Thân Pháp nhưng cộng thêm Cường Lực để damage nền ổn hơn.
- Nếu cấm cứng sẽ làm gameplay nghèo và khó cân bằng trang bị/skill về sau.

Nhưng để không phá cân bằng, dùng **MainElement Affinity**.

#### 2.1.1 MainElement Affinity

Stat đúng hệ được hưởng 100% hiệu quả. Stat khác hệ hưởng hiệu quả thấp hơn khi tính các chỉ số tấn công/chuyên môn.

```text
SameElementStatEfficiency = 100%
OffElementStatEfficiencyBeforeSoftCap = 70%
OffElementStatEfficiencyAfterSoftCap = 35%
OffElementSoftCap = 120
VitalityEfficiency = 100% cho mọi hệ
```

Công thức v1.1 khuyến nghị dùng **off-element offense soft cap** để 3 hệ luôn song hành, không bị loãng game ở late game:

```text
ResolveOffElementOffenseStat(totalStat):
  firstPart = min(totalStat, 120)
  overflow = max(0, totalStat - 120)

  return floor(firstPart * 70 / 100)
       + floor(overflow * 35 / 100)
```

Sau đó:

```text
EffectiveStrengthForDamage =
  MainElement == Strength ? Strength : ResolveOffElementOffenseStat(Strength)

EffectiveAgilityForOffense =
  MainElement == Agility ? Agility : ResolveOffElementOffenseStat(Agility)

EffectiveMagicForSkill =
  MainElement == Magic ? Magic : ResolveOffElementOffenseStat(Magic)

EffectiveVitalityForHP = Vitality
```

Áp dụng:
- Damage vật lý chính dùng `EffectiveStrengthForDamage`.
- Hit/Crit/Dodge chính dùng `EffectiveAgilityForOffense`.
- MP/skill damage chính dùng `EffectiveMagicForSkill`.
- HP luôn dùng `Vitality` 100%.

Không áp dụng penalty cho tất cả mọi thứ. Một số lợi ích phụ vẫn nên dùng stat thật hoặc penalty nhẹ để hybrid còn có giá trị:
- Cường Lực khác hệ vẫn tăng `HealGain/PowerGain`, nhưng có thể dùng 70% nếu thấy quá mạnh.
- Nội Lực khác hệ vẫn tăng `MaxMP/ManaGain`, nhưng skill damage nên dùng affinity.
- Thân Pháp khác hệ vẫn tăng movement, nhưng combat crit/dodge nên dùng affinity.

#### 2.1.2 Stat từ Trang Bị và Giá Trị Hybrid

Một điểm rất quan trọng theo trải nghiệm game Java cũ: **trang bị tăng Cường Lực/Nội Lực/Thân Pháp phải thật sự có giá trị, kể cả khi không cùng hệ chính**.

Ví dụ người chơi hệ Lôi/hệ skill nhưng mặc đồ tăng `Nội Lực` hoặc `Cường Lực`:
- Tăng `Nội Lực` giúp `MaxMP` cao hơn, hồi MP/item MP tốt hơn, từ đó dễ dùng skill hơn.
- Tăng `Cường Lực` giúp hồi HP/item HP tốt hơn, lên nộ tốt hơn, damage vật lý nền tốt hơn.
- Đây là cái hay của game cũ: người chơi có thể phối đồ để bù điểm yếu, không bị khóa cứng vào một build duy nhất.

Vì vậy cần tách rõ hai khái niệm:

```text
AllocatedStat = điểm người chơi tự cộng
EquipmentStat = điểm từ trang bị/buff/item passive
TotalStat = BaseStat + AllocatedStat + EquipmentStat
```

Rule:
- `TotalStat` luôn được dùng cho resource, item hồi phục và các lợi ích phụ.
- Affinity 70% chỉ dùng để giảm hiệu quả **chuyên môn tấn công chính khác hệ**, không làm trang bị khác hệ trở nên vô dụng.
- Trang bị tăng Nội Lực trên nhân vật không phải hệ Nội Lực vẫn phải làm người chơi cảm nhận được: nhiều MP hơn, ăn item MP hồi nhiều hơn, dùng skill dễ hơn.
- Trang bị tăng Cường Lực trên nhân vật không phải hệ Cường Lực vẫn phải làm người chơi cảm nhận được: hồi HP tốt hơn, item HP tốt hơn, nộ tốt hơn.
- **Riêng sát thương/chuyên môn tấn công thì có giảm nếu stat đó không phải hệ chính**, kể cả stat đến từ cộng điểm hay mặc đồ.
- Nếu muốn cân bằng PvP, giảm ở damage/crit/skill final, không giảm trắng toàn bộ giá trị stat.

Công thức nên dùng:

```text
TotalStrength = BaseStrength + AllocatedStrength + EquipmentStrength + BuffStrength
TotalAgility = BaseAgility + AllocatedAgility + EquipmentAgility + BuffAgility
TotalMagic = BaseMagic + AllocatedMagic + EquipmentMagic + BuffMagic
TotalVitality = BaseVitality + AllocatedVitality + EquipmentVitality + BuffVitality
```

Sau đó:

```text
EffectiveStrengthForDamage =
  MainElement == Strength ? TotalStrength : ResolveOffElementOffenseStat(TotalStrength)

EffectiveAgilityForOffense =
  MainElement == Agility ? TotalAgility : ResolveOffElementOffenseStat(TotalAgility)

EffectiveMagicForSkill =
  MainElement == Magic ? TotalMagic : ResolveOffElementOffenseStat(TotalMagic)
```

Nhưng resource/item nên dùng `TotalStat`:

```text
HPItemScale dùng TotalStrength
MPItemScale dùng TotalMagic
PowerGainScale dùng TotalStrength
ManaGainScale dùng TotalMagic
MovementScale dùng TotalAgility
```

#### 2.1.2.1 Tách Damage và Utility Khi Mặc Đồ Khác Hệ

Đây là chốt cân bằng quan trọng:

```text
Stat khác hệ tăng utility/resource: gần như đầy đủ theo TotalStat.
Stat khác hệ tăng damage/chuyên môn tấn công: bị giảm qua affinity + soft cap.
```

Ví dụ nhân vật hệ Nội Lực mặc đồ tăng Cường Lực:

```text
MainElement = Magic
TotalStrength = 200
```

Khi tính hồi HP, item HP, Power/nộ:

```text
StrengthResourceScale dùng TotalStrength = 200
```

Nhưng khi tính damage vật lý:

```text
EffectiveStrengthForDamage =
  floor(120 * 70 / 100) + floor((200 - 120) * 35 / 100)
= 84 + 28
= 112
```

Nghĩa là:
- Mặc đồ Cường Lực vẫn thấy lợi rõ: ăn `Trái Đào` tốt hơn, hồi HP/nộ tốt hơn.
- Nhưng sát thương vật lý không ăn đủ 200 điểm Cường Lực vì người đó không phải hệ Cường Lực.
- Nhờ vậy hybrid có giá trị nhưng không vượt build đúng hệ.

Ví dụ nhân vật hệ Cường Lực mặc đồ tăng Nội Lực:

```text
MainElement = Strength
TotalMagic = 200
```

Khi tính MP và item MP:

```text
MaxMP/ManaGain/ItemManaScale dùng TotalMagic = 200
```

Nhưng khi tính skill damage Nội Lực chuyên môn:

```text
EffectiveMagicForSkill =
  floor(120 * 70 / 100) + floor((200 - 120) * 35 / 100)
= 84 + 28
= 112
```

Nghĩa là:
- Mặc đồ Nội Lực giúp có nhiều MP và dùng skill dễ hơn.
- Nhưng skill damage không mạnh bằng nhân vật hệ Nội Lực thật.

Bảng rule ngắn:

| Loại hiệu ứng | Dùng stat nào |
|---------------|---------------|
| Damage vật lý từ Cường Lực | `EffectiveStrengthForDamage` |
| Skill damage Nội Lực | `EffectiveMagicForSkill` |
| Hit/Crit/Dodge chuyên môn Thân Pháp | `EffectiveAgilityForOffense` |
| MaxMP | `TotalMagic` |
| Hồi MP / item MP | `TotalMagic` |
| Hồi HP / item HP / Power | `TotalStrength` |
| Movement ngoài map | `TotalAgility` hoặc stat movement đã tổng hợp |
| HP | `TotalVitality` |

Lý do:
- Giữ đúng cảm giác game cũ: mặc đồ tăng stat là thấy mạnh lên ngay.
- Khuyến khích phối đồ: hệ skill có thể mặc đồ Nội Lực để dùng skill nhiều hơn; hệ vật lý có thể mặc thêm Nội Lực nếu muốn linh hoạt.
- Không phá cân bằng vì damage/skill/offense chính vẫn đi qua affinity và cap.


#### 2.1.3 Ví dụ Build

Ví dụ nhân vật hệ Cường Lực:

```text
MainElement = Strength
Strength = 300
Agility = 150
Magic = 80
Vitality = 180
```

Tính hiệu quả:

```text
EffectiveStrengthForDamage = 300
EffectiveAgilityForOffense = floor(150 * 70 / 100) = 105
EffectiveMagicForSkill = floor(80 * 70 / 100) = 56
EffectiveVitalityForHP = 180
```

Kết quả:
- Damage vật lý rất tốt.
- Có chút crit/dodge từ Thân Pháp nhưng không bằng hệ Thân Pháp thật.
- Có chút MP/skill utility nhưng không bằng hệ Nội Lực.
- HP vẫn ổn nhờ Thể Lực.

Ví dụ nhân vật hệ Nội Lực cố cộng Cường Lực rất nhiều:

```text
MainElement = Magic
Strength = 300
Magic = 250
```

Khi tính damage vật lý:

```text
EffectiveStrengthForDamage = floor(300 * 70 / 100) = 210
```

Người chơi vẫn mạnh hơn người không cộng Cường Lực, nhưng không thể vượt hệ Cường Lực cùng lượng điểm.

#### 2.1.4 Có Nên Cho Đổi Hệ Không?

V1 không nên cho đổi hệ miễn phí.

Nếu sau này có đổi hệ:
- Chỉ cho qua item đặc biệt hoặc reset nhân vật.
- Khi đổi hệ, giữ điểm đã cộng nhưng đổi `MainElement`, các affinity sẽ tự tính lại.
- Cần reset skill tree hoặc tính phí cao để tránh abuse PvP.

---

## 3. EXP Curve Cho Max Level 250

Curve cũ đang dùng trong một số tài liệu/code:

```text
ExpFloor(level) = 100 * (level - 1)^2
```

Với max level 250:

```text
ExpFloor(250) = 100 * 249^2 = 6,200,100
```

Con số này quá thấp nếu game cần cày cuốc lâu.

### 3.1 Curve Khuyến Nghị v1

```text
n = level - 1

ExpFloor(level) =
  80 * n^2
+  8 * n^3
+  max(0, level - 100)^3 * 12
```

Đặc điểm:
- Early game vẫn lên nhanh.
- Mid game bắt đầu cần train.
- Sau level 100 tốc độ lên cấp chậm rõ rệt.
- Level 250 khoảng 262 triệu EXP tổng, hợp lý hơn cho game cày cuốc.
- Không cần bonus điểm tiềm năng.

Ví dụ xấp xỉ:

| Level | ExpFloor |
|-------|----------|
| 1 | 0 |
| 10 | 11,664 |
| 30 | 262,624 |
| 50 | 1,133,944 |
| 100 | 8,436,744 |
| 150 | 36,004,544 |
| 200 | 107,056,344 |
| 250 | 261,992,144 |

### 3.2 Biến Thể Hardcore Nếu Cần

Nếu sau test thấy lên level vẫn nhanh, chỉ tăng late-game term:

```text
ExpFloor(level) =
  80 * n^2
+  8 * n^3
+  max(0, level - 100)^3 * 15
```

Không nên tăng early term quá mạnh vì sẽ làm người chơi mới nản.

---

## 4. Derived Status Stat Java-faithful

Tất cả công thức core status dùng integer truncation/floor kiểu Java:

```text
int result = numerator / denominator;
```

Không dùng float phức tạp trong stat core nếu không cần.

Nguồn Java đã đối chiếu:
- `reference/redecoded/cfr_fresh/jp.java`: chọn calculator theo raw element.
- `reference/redecoded/cfr_fresh/jq.java`: hệ Hỏa / Cường Lực.
- `reference/redecoded/cfr_fresh/js.java`: hệ Lôi / Thân Pháp.
- `reference/redecoded/cfr_fresh/jr.java`: hệ Thủy / Nội Lực.
- `reference/redecoded/cfr_fresh/com/mg/sq/a.java:1891-1928`: bridge tính `lh.r/x/y/z/A/B/C`.
- `reference/redecoded/cfr_fresh/da.java:52-55,206-215`: UI status label mapping.

Mapping Java:
- `lh.r` = Sinh lực / MaxHP = `jz.a()`
- `lh.x` = Tấn Công min = `jz.b()`
- `lh.y` = Tấn Công max = `jz.c()`
- `lh.z` = P.Thủ = `jz.d()`
- `lh.A` = Né Tránh = `jz.e()`
- `lh.B` = Chính xác = `jz.f()`
- `lh.C` = Chí Mạng % = `jz.g()`

### 4.1 Tổng stat trước khi tính Java calculator

Java bridge `com.mg.sq.a.a(lh)` tính lại derived stat theo thứ tự:

```text
TotalStrength = lh.h + lh.l + equipmentStrength
TotalMagic    = lh.j + lh.m + equipmentMagic
TotalAgility  = lh.i + lh.n + equipmentAgility
TotalVitality = lh.k + lh.o + equipmentVitality

jz.a(TotalStrength, TotalMagic, TotalAgility, TotalVitality)
```

Trong code C# hiện tại mapping tương ứng:
- `CuongLuc` = Java arg `a` / Strength.
- `NoiLuc` = Java arg `b` / Magic.
- `ThanPhap` = Java arg `c` / Agility.
- `TheLuc` = Java arg `d` / Vitality.

### 4.2 Hỏa / Cường Lực — `jq.java`

```text
MaxHP     = TheLuc * 6
MinDamage = CuongLuc
MaxDamage = CuongLuc * 120 / 100
Defense   = ThanPhap / 2
Dodge     = ThanPhap * 2
Hit       = ThanPhap * 3
Crit      = min(5 + ThanPhap / 8, 30)
```

### 4.3 Lôi / Thân Pháp — `js.java`

```text
MaxHP     = TheLuc * 4
MinDamage = (ThanPhap * 80 + CuongLuc * 16) / 100
MaxDamage = ThanPhap + CuongLuc / 5
Defense   = ThanPhap / 2
Dodge     = ThanPhap * 15 / 10
Hit       = ThanPhap * 3
Crit      = min(5 + ThanPhap / 8, 30)
```

### 4.4 Thủy / Nội Lực — `jr.java`

```text
MaxHP     = TheLuc * 5
MinDamage = NoiLuc * 130 / 100
MaxDamage = NoiLuc * 150 / 100
Defense   = ThanPhap / 2
Dodge     = ThanPhap * 3
Hit       = ThanPhap * 2
Crit      = min(5 + ThanPhap / 8, 30)
```

Ghi chú quan trọng:
- Thủy có `Dodge = ThanPhap * 3`, cao hơn Hỏa/Lôi. Đây là hành vi Java gốc, không sửa bằng cảm tính balance.
- Lôi có `Hit = ThanPhap * 3` và damage ăn trực tiếp Thân Pháp; Dodge chỉ `ThanPhap * 1.5`.
- Hỏa dùng Cường Lực làm damage chính, HP cao nhất theo Thể Lực.
- Không áp off-element soft cap vào các công thức trên.

### 4.5 Trang bị cộng derived stat trực tiếp

Theo `com.mg.sq.a.a(lh)`:
- Trang bị cộng stat gốc trước, rồi chạy lại `jz`.
- Trang bị cộng damage flat (`lb.e`) và damage percent theo max damage (`lb.n`) cộng vào cả `lh.x/lh.y`.
- Trang bị cộng crit/defense/dodge/maxHP trực tiếp vào `lh.C/lh.z/lh.A/lh.r`.
- `lh.B` / Hit trong Java bridge hiện chỉ lấy `jz.f()`, chưa thấy cộng hit equipment ở đoạn này.

### 4.6 MP

```text
MaxMP = 40 + level * 6 + TotalMagic * 8 + EquipmentMP
```

Vai trò:
- `Nội Lực` là nguồn MP chính.
- Hệ Nội Lực mạnh nhờ dùng skill ổn định hơn.
- Hệ khác vẫn có MP từ level/equipment nhưng không thể spam skill nếu không đầu tư.
- Nếu nhân vật khác hệ cố cộng hoặc mặc đồ tăng Nội Lực thì vẫn được MP tốt hơn baseline.
- `MaxMP` dùng `TotalMagic` chứ không dùng affinity 70%, để đúng cảm giác game cũ: mặc đồ Nội Lực giúp nhiều MP hơn và dễ dùng skill hơn.
- Affinity 70% chỉ nên áp dụng vào `skill damage/effect chuyên môn`, không triệt tiêu giá trị MP/resource.

### 4.7 Damage / skill remake ngoài status

```text
MinDamage = 5 + level * 2 + EffectiveStrengthForDamage * 2 + EquipmentMinDamage
MaxDamage = 10 + level * 2 + EffectiveStrengthForDamage * 3 + EquipmentMaxDamage
```

Vai trò:
- `Cường Lực` là damage vật lý ổn định.
- Mạnh early/mid.
- Không phụ thuộc crit quá nhiều.
- Không cần MP nhiều để có hiệu quả.
- Nếu nhân vật khác hệ cố cộng Cường Lực thì damage vẫn tăng, nhưng chỉ tính 70% hiệu quả chuyên môn để không vượt hệ Cường Lực thật.

### 4.8 Chính Xác trong battle

```text
HitRate = clamp(60, 95, 75 + EffectiveAgilityForOffense / 5 + EquipmentHit)
```

Vai trò:
- `Thân Pháp` giúp đánh trúng ổn định hơn.
- Cap 95% để không bao giờ tuyệt đối 100%.
- Nếu nhân vật khác hệ cố cộng Thân Pháp thì vẫn đánh trúng tốt hơn, nhưng không đạt hiệu quả tối đa như hệ Thân Pháp thật.

### 4.9 Né Tránh trong battle

```text
DodgeRate = clamp(0, 35, 3 + EffectiveAgilityForOffense / 9 + EquipmentDodge)
```

Vai trò:
- `Thân Pháp` có khả năng né.
- Cap 35% để tránh PvP bị “không đánh trúng được”.

### 4.10 Chí Mạng trong battle

```text
CriticalRate = clamp(0, 40, 3 + EffectiveAgilityForOffense / 12 + EquipmentCriticalRate)
```

Vai trò:
- `Thân Pháp` là hướng build burst theo xác suất.
- Cap 40% để không biến mọi đòn thành chí mạng.

### 4.11 Sát Thương Chí Mạng

```text
CriticalDamage = clamp(150, 250, 150 + EffectiveAgilityForOffense / 10 + EquipmentCriticalDamage)
```

Ý nghĩa:
- 150 nghĩa là 150% damage thường.
- Cap 250% để tránh one-shot quá thường xuyên.

---

## 5. Resource Gain Trong Battle

Nguồn Java:
- Client có HP/MP/Power bar.
- `lh.u/t` là MP.
- `lh.w/v` là Power/nộ.
- Không có công thức server cũ chính xác cho resource gain.

Rule remake v1:

### 5.1 Cường Lực tăng hồi HP và nộ/Power

```text
StrengthPercent = clamp(80, 180, 100 + (TotalStrength - 10) * 3)

HealGain = floor(BaseHealGain * StrengthPercent / 100)
PowerGain = floor(BasePowerGain * StrengthPercent / 100)
```

Lý do:
- Cường Lực không chỉ là damage, còn làm người chơi “đánh khỏe, lên nộ nhanh”.
- Cap 180% để level 250 không làm nộ tăng vô hạn.
- 3%/point vừa đủ rõ ở early game, không quá nổ ở late game.

### 5.2 Nội Lực tăng hồi MP

```text
MagicPercent = clamp(80, 180, 100 + (TotalMagic - 10) * 3)

ManaGain = floor(BaseManaGain * MagicPercent / 100)
```

Lý do:
- Nội Lực chuyên về MP và skill.
- Không cho MP gain vượt quá 180% để tránh spam skill vô hạn.
- Dùng cùng tốc độ 3%/point với Cường Lực để cân bằng giữa hệ vật lý và hệ kỹ năng.

### 5.3 Item Hồi Phục như Trái Đào

Các item hồi phục như `Trái Đào` vẫn nên được hưởng lợi từ chỉ số tương ứng, để người chơi cảm nhận rõ build của mình.

Nguyên tắc:
- Item hồi HP chịu ảnh hưởng bởi hướng Cường Lực/resource hoặc có thể thêm Thể Lực nếu cần.
- Item hồi MP chịu ảnh hưởng bởi Nội Lực/resource.
- Item hồi Power/nộ chịu ảnh hưởng bởi Cường Lực/resource.
- Không để item hồi theo phần trăm quá lớn vượt cap, tránh PvP/PvE bị kéo dài vô hạn.

Công thức khuyến nghị v1 cho item hồi HP:

```text
ItemHealPercent =
  MainElement == Strength
    ? clamp(80, 180, 100 + (TotalStrength - 10) * 3)
    : clamp(80, 150, 100 + (TotalStrength - 10) * 2)

HealFromItem = floor(ItemBaseHeal * ItemHealPercent / 100)
HealFromItem = min(HealFromItem, MissingHP)
```

Ý nghĩa:
- Nhân vật hệ Cường Lực ăn `Trái Đào` hồi nhiều nhất vì đúng hướng build.
- Nhân vật khác hệ cố cộng Cường Lực vẫn hồi nhiều hơn người không cộng, nhưng cap thấp hơn và scale thấp hơn để không vượt hệ Cường Lực thật.
- `MissingHP` clamp để không hồi vượt quá `MaxHP`.

Ví dụ:

```text
Trái Đào base heal = 500
Nhân vật Cường Lực, Strength = 100

ItemHealPercent = 100 + (100 - 10) * 3 = 370 -> clamp 180
HealFromItem = 500 * 180 / 100 = 900
```

Nhân vật Nội Lực cố cộng Cường Lực:

```text
Trái Đào base heal = 500
MainElement = Magic
Strength = 100

ItemHealPercent = 100 + (100 - 10) * 2 = 280 -> clamp 150
HealFromItem = 500 * 150 / 100 = 750
```

Như vậy:
- Có cộng Cường Lực thì ăn item hồi HP vẫn tốt hơn.
- Đúng hệ Cường Lực thì lợi hơn rõ.
- Khác hệ không bị vô dụng, nhưng không phá vai trò hệ chính.

Công thức item hồi MP:

```text
ItemManaPercent =
  MainElement == Magic
    ? clamp(80, 180, 100 + (TotalMagic - 10) * 3)
    : clamp(80, 150, 100 + (TotalMagic - 10) * 2)

ManaFromItem = floor(ItemBaseMana * ItemManaPercent / 100)
ManaFromItem = min(ManaFromItem, MissingMP)
```

Ghi chú quan trọng:
- Đây là rule remake vì chưa có công thức server Java cũ cho item heal.
- Nếu sau này decompile/client data chỉ ra `Trái Đào` là hồi fixed amount tuyệt đối, có thể chuyển item thường thành fixed và chỉ để item cao cấp/buff chịu stat scaling.
- V1 nên cho scaling có cap vì hợp lý với gameplay stat/build và giúp Cường Lực/Nội Lực có bản sắc rõ hơn ngoài damage.

---

## 6. Movement Ngoài Map Theo Thân Pháp

Nguồn Java:
- Java cũ có map actor `kl/kd/jt/om`, nhưng map remake là map mới.
- Không có map cũ để đối chiếu movement/collision.
- Vì vậy movement theo Thân Pháp là rule remake có cap.

Rule v1:

```text
AgiOver = max(0, Agility - 10)

MoveMultiplier = clamp(1.00, 1.30, 1.00 + AgiOver / 1200)
JumpMultiplier = clamp(1.00, 1.20, 1.00 + AgiOver / 1700)
```

Ý nghĩa:
- Thân Pháp cao cảm nhận rõ nhân vật nhanh/nhẹ hơn.
- Tối đa +30% tốc chạy.
- Tối đa +20% lực nhảy/tốc nhảy.
- Không giảm gravity ở v1 để tránh phá collision/platform.

Không khuyến nghị vượt cap này trước khi có full collision/platform test.

---

## 7. Cân Bằng 3 Hướng Build

Mục tiêu thiết kế quan trọng nhất: **game phải có build đa dạng, mỗi hệ đều có lý do để chơi, không hệ nào quá nổi trội và không hệ nào quá yếu**.

Nguyên tắc v1:
- Không có hệ “đúng tuyệt đối”.
- Không có stat “vứt đi”.
- Trang bị sai hệ vẫn có giá trị, nhưng không thay thế hoàn toàn build đúng hệ.
- Build thuần hệ phải mạnh nhất ở chuyên môn của nó.
- Build hybrid phải linh hoạt hơn, nhưng sát thương/chuyên môn không được vượt build thuần.
- Mỗi hệ phải có ít nhất một điểm mạnh rõ ràng, một điểm yếu rõ ràng và một cách khắc phục qua trang bị/stat phụ.
- PvE và PvP đều phải có đất cho cả 3 hệ.

Bảng mục tiêu cân bằng:

| Hệ/build | Mạnh nhất ở | Yếu ở | Có thể bù bằng |
|----------|-------------|-------|----------------|
| Cường Lực | Damage vật lý, hồi HP, Power/nộ | MP, skill uptime, né/crit | Mặc/cộng Nội Lực hoặc Thân Pháp |
| Nội Lực | MP, skill, late-game burst/effect | Máu, damage thường, phụ thuộc MP | Mặc/cộng Thể Lực hoặc Cường Lực |
| Thân Pháp | Hit, dodge, crit, movement | Damage nền, MP, dễ bị burst nếu thiếu máu | Mặc/cộng Cường Lực/Nội Lực/Thể Lực |
| Hybrid | Linh hoạt, bù điểm yếu, dễ thích nghi | Không đạt đỉnh chuyên môn | Trang bị tốt/skill phù hợp |

Rule cân bằng chốt:
- Damage/offense đúng hệ: `100%`.
- Damage/offense khác hệ: `70%` trước soft cap `120`, sau đó chỉ còn `35%`.
- Utility/resource khác hệ: dùng `TotalStat`, có cap riêng.
- Khắc hệ chỉ nên `112%/100%/92%`, không quá lớn.
- Dodge/crit/resource đều phải có cap để tránh một hệ thống trị PvP.

Các nút chỉnh nếu test thấy lệch:
- Nếu hybrid quá mạnh: giảm `OffElementSoftCap` từ `120` xuống `100`, hoặc giảm `OffElementStatEfficiencyAfterSoftCap` từ `35%` xuống `30%`.
- Nếu hybrid quá yếu: tăng `OffElementSoftCap` từ `120` lên `140`, hoặc tăng `OffElementStatEfficiencyAfterSoftCap` từ `35%` lên `40%`.
- Nếu Cường Lực quá mạnh: giảm damage scale hoặc PowerGain cap.
- Nếu Nội Lực quá mạnh: tăng MP cost skill, giảm skill percent hoặc giảm ManaGain cap.
- Nếu Thân Pháp quá mạnh: giảm dodge/crit cap trước, không nên giảm movement quá sớm.
- Nếu một hệ quá yếu trong PvE: chỉnh monster element distribution/map train, không buff stat vội.
- Nếu một hệ quá yếu trong PvP: xem lại skill tree/trang bị/resistance trước khi đổi core stat formula.

### 7.1 Cường Lực Build

Vai trò:
- Damage vật lý ổn định.
- Hồi HP từ gem tốt hơn.
- Lên nộ/Power nhanh hơn.
- Dễ chơi, mạnh early/mid.

Điểm yếu:
- MP thấp nếu không đầu tư Nội Lực.
- Né/chí mạng không cao nếu bỏ Thân Pháp.
- Phụ thuộc đánh thường/nộ nhiều hơn skill.

### 7.2 Nội Lực Build

Vai trò:
- MP cao.
- Hồi MP nhanh.
- Skill damage/effect mạnh.
- Late game tốt khi skill level cao.

Điểm yếu:
- Early game có thể yếu hơn nếu thiếu skill.
- Máu thấp nếu bỏ Thể Lực.
- Damage thường thấp hơn Cường Lực.

### 7.3 Thân Pháp Build

Vai trò:
- Chính xác cao.
- Né tránh.
- Chí mạng.
- Di chuyển map nhanh/nhảy tốt hơn.
- Lối chơi nhanh, thiên burst/xác suất.

Điểm yếu:
- Damage nền thấp hơn Cường Lực.
- MP thấp hơn Nội Lực.
- Né/chí mạng bị cap để tránh vỡ PvP.

### 7.4 Thể Lực

Vai trò:
- Sinh tồn chung cho mọi hệ.
- Không phải một hệ riêng, nhưng là stat bắt buộc nếu muốn PvP/PvE bền.
- Giúp chống burst từ khắc hệ/crit/skill.

---

## 8. Khắc Hệ

Khắc hệ là cơ chế rất quan trọng để 3 hệ không bị một hệ thống trị tuyệt đối.

Mục tiêu:
- Có ưu/nhược rõ ràng.
- Không tạo one-shot quá mạnh.
- Không làm người chơi cảm thấy build bị vô dụng.
- PvP vẫn có đất cho kỹ năng, trang bị, level, stat allocation.
- PvE có thể dùng khắc hệ để tạo monster/boss đa dạng.

### 8.1 Mô Hình 3 Hệ Vòng Tròn

Đề xuất dùng vòng tròn đơn giản:

```text
Cường Lực khắc Thân Pháp
Thân Pháp khắc Nội Lực
Nội Lực khắc Cường Lực
```

Giải thích gameplay:
- **Cường Lực > Thân Pháp**: đòn vật lý ổn định, chịu được né/crit tốt hơn, ép sát build nhanh nhẹn.
- **Thân Pháp > Nội Lực**: nhanh, né, crit, gây áp lực lên hệ cần MP/skill.
- **Nội Lực > Cường Lực**: skill/pháp thuật xuyên qua lối chơi vật lý chậm, dùng MP để tạo burst/control.

Nếu sau này lore game có hệ cụ thể khác như Kim/Mộc/Thủy/Hỏa/Thổ thì đổi vòng khắc theo lore; còn hiện tại dùng 3 hướng build để cân bằng player.

### 8.2 Không Dùng Multiplier Quá Lớn

Không nên dùng kiểu:

```text
Khắc hệ: damage x1.5
Bị khắc: damage x0.5
```

Vì sẽ làm PvP mất cân bằng, đặc biệt ở level 250.

Khuyến nghị v1:

```text
AdvantageDamageMultiplier = 1.12
DisadvantageDamageMultiplier = 0.92
NeutralDamageMultiplier = 1.00
```

Nếu cần integer Java-like:

```text
AdvantageDamagePercent = 112
DisadvantageDamagePercent = 92
NeutralDamagePercent = 100

FinalDamage = BaseDamage * ElementPercent / 100
```

Lý do:
- Ưu thế đủ cảm nhận được.
- Không làm người bị khắc thua chắc.
- Trang bị, level, stat và kỹ năng vẫn quan trọng.

### 8.3 Bonus Khắc Hệ Theo Level Không Nên Scale Vô Hạn

Không để khắc hệ tăng theo stat/level trực tiếp ở v1.

Sai lầm cần tránh:

```text
ElementBonus = 100 + Level / 2
```

Ở level 250 sẽ thành bonus quá cao.

Nếu muốn late game có chiều sâu, dùng equipment/resistance riêng, không đưa vào base formula.

### 8.4 Element Resistance

Để chống bị khắc quá nặng, nên có resistance từ trang bị/buff.

```text
ElementResistPercent = clamp(0, 20, EquipmentElementResist + BuffElementResist)

EffectiveElementPercent =
  ElementDamagePercent - ElementResistPercent
```

Ví dụ:
- Nội Lực đánh Cường Lực: 112%.
- Cường Lực có 8% resist Nội Lực.
- Damage còn: `112 - 8 = 104%`.

Cap resistance 20% ở v1 để không vô hiệu hóa hoàn toàn khắc hệ.

### 8.5 Công Thức Damage Cuối Có Khắc Hệ

Thứ tự khuyến nghị:

```text
RawDamage
-> Defense reduction
-> Skill multiplier
-> Critical multiplier
-> Element multiplier
-> PvP/PvE global modifier
-> Clamp min damage
```

Công thức integer:

```text
damage = RawDamage;
damage = ApplyDefense(damage, targetDefense);
damage = damage * SkillPercent / 100;
damage = isCritical ? damage * CriticalDamagePercent / 100 : damage;
damage = damage * ElementPercentAfterResist / 100;
damage = damage * ModePercent / 100;
damage = max(1, damage);
```

Ghi chú:
- Có thể đặt element trước critical cũng được, nhưng phải thống nhất.
- Khuyến nghị để element sau critical để khắc hệ tác động lên tổng sát thương cuối và dễ cảm nhận.
- PvP có thể có `ModePercent = 85` hoặc `90` nếu damage quá cao.

### 8.6 Khắc Hệ Không Chỉ Là Damage

Để 3 hệ cân bằng hơn, khắc hệ có thể tác động nhẹ đến phụ trợ, nhưng không nên làm ở v1 nếu chưa test.

Có thể cân nhắc v2:

```text
Khi khắc hệ:
- +5 HitRate tạm thời
- hoặc +5 PowerGainPercent
- hoặc giảm 5% MP cost
```

Nhưng v1 chỉ nên dùng damage percent để dễ kiểm soát.

### 8.7 PvE / Train Monster Element

Khắc hệ **bắt buộc áp dụng cho cả PvE train monster**, không chỉ PvP.

Monster/boss nên có hệ để khắc chế player:

```text
MonsterElement = StrengthLike | AgilityLike | MagicLike | Neutral
```

Rule:
- Quái train thường có hệ rõ theo vùng/map/spawn group, nhưng không để toàn bộ game chỉ ưu ái một build.
- Quái thường có thể có một phần `Neutral` để người chơi mọi hệ vẫn train được nếu chưa đủ trang bị.
- Boss nên có hệ rõ để người chơi chuẩn bị trang bị/resist và chọn skill/build phù hợp.
- Không để toàn bộ map chỉ có một hệ nếu muốn mọi build đều train được.
- Damage player đánh monster và monster đánh player đều đi qua cùng `ResolveElementPercent()` để server battle thống nhất PvE/PvP.
- Reward/EXP quái không nên tự tăng chỉ vì người chơi bị khắc hệ; độ khó do khắc hệ được bù bằng lựa chọn map/quái/trang bị, không phải bằng công thức EXP riêng ở v1.

Khuyến nghị phân bổ hệ trong map train:
- Map early game: nhiều `Neutral`, ít monster khắc cứng người chơi.
- Map mid game: mỗi map có 1 hệ chủ đạo + 1 hệ phụ.
- Map late game/boss: hệ rõ ràng, yêu cầu chuẩn bị resist hoặc chọn target hợp lý.
- Không tạo vùng train bắt buộc toàn monster khắc một hệ duy nhất, vì sẽ làm hệ đó bị thiệt tiến độ cày cấp.

Ví dụ:
```text
Hoa Lư early:
- 50% Neutral
- 25% StrengthLike
- 25% AgilityLike/MagicLike luân phiên theo spawn

Map mid Cường Lực:
- 60% StrengthLike
- 25% AgilityLike
- 15% Neutral

Boss Nội Lực:
- 100% MagicLike
- Có thể có minion Neutral/AgilityLike để tránh một màu
```

### 8.8 PvP Matchup Cân Bằng

Với vòng khắc:

```text
Cường Lực > Thân Pháp > Nội Lực > Cường Lực
```

Mỗi hệ có lợi một kèo và bất lợi một kèo.

Nhưng vì multiplier chỉ 112%/92%, kết quả PvP vẫn phụ thuộc:
- Level.
- Trang bị.
- Skill level.
- Cách phân Thể Lực.
- Cách dùng nộ/skill.
- Board/match puzzle.

Đây là mức hợp lý cho game có PvP.

---

## 9. Công Thức Tổng Hợp Khuyến Nghị v1

```text
MaxLevel = 250
PotentialPerLevel = 5
InitialFreePoints = 0
NoBonusPotential = true

ExpFloor(level):
n = level - 1
80 * n^2 + 8 * n^3 + max(0, level - 100)^3 * 12

MaxHP:
100 + level * 18 + Vitality * 14 + EquipmentHP

MaxMP:
40 + level * 6 + TotalMagic * 8 + EquipmentMP

MinDamage:
5 + level * 2 + EffectiveStrengthForDamage * 2 + EquipmentMinDamage

MaxDamage:
10 + level * 2 + EffectiveStrengthForDamage * 3 + EquipmentMaxDamage

HitRate:
clamp(60, 95, 75 + EffectiveAgilityForOffense / 5 + EquipmentHit)

DodgeRate:
clamp(0, 35, 3 + EffectiveAgilityForOffense / 9 + EquipmentDodge)

CriticalRate:
clamp(0, 40, 3 + EffectiveAgilityForOffense / 12 + EquipmentCriticalRate)

CriticalDamage:
clamp(150, 250, 150 + EffectiveAgilityForOffense / 10 + EquipmentCriticalDamage)

Strength resource:
clamp(80, 180, 100 + (TotalStrength - 10) * 3)

Magic resource:
clamp(80, 180, 100 + (TotalMagic - 10) * 3)

Item HP heal:
MainElement == Strength
  ? clamp(80, 180, 100 + (TotalStrength - 10) * 3)
  : clamp(80, 150, 100 + (TotalStrength - 10) * 2)

Item MP heal:
MainElement == Magic
  ? clamp(80, 180, 100 + (TotalMagic - 10) * 3)
  : clamp(80, 150, 100 + (TotalMagic - 10) * 2)

MoveMultiplier:
clamp(1.00, 1.30, 1.00 + max(0, Agility - 10) / 1200)

JumpMultiplier:
clamp(1.00, 1.20, 1.00 + max(0, Agility - 10) / 1700)

Element:
Advantage = 112%
Neutral = 100%
Disadvantage = 92%
ElementResistanceCap = 20%
```

---

## 10. Nhật Ký Chỉnh Sửa

### 2026-04-26 — Battle resource scale chuyển sang server authority

- Áp dụng rule resource §5 vào battle runtime từ server thay vì để FE tự hardcode theo stat.
- Mở rộng battle/session snapshot thêm:
  - `HealGainPercent`
  - `ManaGainPercent`
  - `PowerGainPercent`
- Player battle state lấy chỉ số từ `PlayerStatPipeline`/status Java-faithful hiện có; không áp soft-cap vào tầng status hiển thị.
- Monster battle state tính resource gain percent trên server bằng integer math theo rule remake §5:
  - Cường Lực scale HP/Power.
  - Nội Lực scale MP.
- Client battle chỉ dùng percent server trả về để scale `GEM_FX_BASE`; fallback `100%` chỉ để tương thích payload cũ.
- File code đã sửa:
  - `server/Twelve.Core/Battle/BattleSessionContracts.cs`
  - `server/Twelve.Core/Monsters/MonsterContracts.cs`
  - `server/Twelve.Application/Battle/PlayerBattleStateFactory.cs`
  - `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
  - `client/src/screens/battle/core/BattleScreen.shared.ts`
  - `client/src/screens/battle/core/BattleScreen.types.ts`
  - `client/src/screens/battle/BattleScreen.tsx`

### 2026-04-26 — Battle damage/resource server authority

- Áp dụng boundary battle mới: FE chỉ hydrate/render/tween HP/MP/Power, còn scale resource và damage turn result lấy từ server snapshot/session state.
- File code đã sửa:
  - `server/Twelve.Core/Battle/BattleSessionContracts.cs`
  - `server/Twelve.Core/Monsters/MonsterContracts.cs`
  - `server/Twelve.Application/Battle/PlayerBattleStateFactory.cs`
  - `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
  - `server/Twelve.Application/Battle/BattleTurnEngine.cs`
  - `client/src/screens/battle/core/BattleScreen.shared.ts`
  - `client/src/screens/battle/core/BattleScreen.types.ts`
  - `client/src/screens/battle/BattleScreen.tsx`
- Nội dung logic:
  - Battle snapshot trả `HealGainPercent`, `ManaGainPercent`, `PowerGainPercent`.
  - Player battle combat state lấy status Java-faithful từ `PlayerStatPipeline`.
  - Monster combat state tự tính resource percent ở server theo rule remake §5 vì không có server Java mẫu.
  - `BattleTurnEngine` dùng `MinDamage/MaxDamage`, `Defense`, `HitRate`, `DodgeRate`, `CriticalDamage`, `PowerGainPercent` từ `BattleSessionCombatantState`; bỏ công thức cộng thêm stat/level hardcode để tránh double-count.
  - Client `GEM_FX_BASE` chỉ còn là base effect/visual pacing, không còn là nguồn quyết định scale theo stat.
- Căn cứ:
  - Java client xác nhận `lh.s/r`, `lh.u/t`, `lh.w/v` và HUD `mx` nhưng chỉ render/apply delta server; không có công thức gain/damage cuối của server cũ trong client.
  - Rule resource §5 là remake có kiểm soát, phải nằm ở server để tránh FE hardcode lệch logic.

### 2026-04-25 — Java-faithful status formula correction

- Đối chiếu lại Java `jq/js/jr` và `com.mg.sq.a.a(lh)` để chốt công thức 6 chỉ số status:
  - `lh.r` Sinh lực.
  - `lh.x/lh.y` Tấn Công min/max.
  - `lh.z` P.Thủ.
  - `lh.A` Né Tránh.
  - `lh.B` Chính xác.
  - `lh.C` Chí Mạng.
- Chốt status/derived stat phải bám Java 100%, không dùng off-element soft cap ở tầng hiển thị nhân vật.
- Ghi rõ Thủy né cao hơn Lôi là đúng Java: Thủy `ThanPhap * 3`, Lôi `ThanPhap * 15 / 10`.
- File code đã sửa:
  - `server/Twelve.Core/Entities/CombatStats.cs`
  - `server/Twelve.Core/GameLogic/StatCalculator.cs`
  - `server/Twelve.Core/GameLogic/PlayerStatPipeline.cs`
- Build xác nhận: `dotnet build Twelve.sln` thành công sau khi kill process khóa DLL `Twelve.Server`.

### 2026-04-25

- Tạo tài liệu riêng cho công thức level/stat/EXP/resource/movement/khắc hệ.
- Chốt không có điểm tiềm năng bonus, level 1 bắt đầu `0` điểm dư, chỉ `+5` mỗi lần lên level.
- Đề xuất EXP curve mới cho max level 250, khoảng 262 triệu EXP tới level tối đa.
- Chốt hướng cân bằng 3 build:
  - Cường Lực: damage vật lý, hồi HP, nộ.
  - Nội Lực: MP, hồi MP, skill.
  - Thân Pháp: hit, dodge, crit, movement.
  - Thể Lực: sinh tồn chung.
- Bổ sung thiết kế khắc hệ 3 vòng:
  - Cường Lực khắc Thân Pháp.
  - Thân Pháp khắc Nội Lực.
  - Nội Lực khắc Cường Lực.
- Chốt multiplier khắc hệ v1:
  - Advantage 112%.
  - Neutral 100%.
  - Disadvantage 92%.
  - Element resistance cap 20%.
- Bổ sung rule chọn hệ chính và cộng điểm khác hệ:
  - Hệ chính dùng stat chuyên môn 100% hiệu quả.
  - Cộng stat khác hệ vẫn hợp lệ nhưng chỉ đóng vai trò hybrid/support.
  - Stat khác hệ dùng soft cap cho damage/skill/offense chuyên môn: 70% trước mốc 120, 35% sau mốc 120.
  - `Thể Lực` luôn 100% cho mọi hệ.
- Bổ sung rule trang bị/stat hybrid theo tinh thần game Java cũ:
  - Trang bị tăng Cường Lực/Nội Lực/Thân Pháp phải có giá trị thật kể cả khi không cùng hệ chính.
  - `TotalStat = Base + Allocated + Equipment + Buff`.
  - Affinity 70% chỉ dùng cho chuyên môn tấn công chính khác hệ.
  - Resource, item hồi phục và lợi ích phụ dùng `TotalStat` để người chơi mặc đồ tăng Nội Lực/Cường Lực vẫn thấy mạnh lên rõ.
  - Ghi rõ sát thương/chuyên môn tấn công từ stat khác hệ vẫn bị giảm qua affinity, kể cả stat đó đến từ cộng điểm hay trang bị.
- Bổ sung rule item hồi phục như `Trái Đào`:
  - Item hồi HP vẫn tăng hiệu quả theo Cường Lực.
  - Đúng hệ Cường Lực scale/cap cao hơn.
  - Khác hệ cố cộng Cường Lực vẫn hồi nhiều hơn nhưng cap thấp hơn để giữ vai trò hybrid/support.
  - Item hồi MP tương tự theo Nội Lực.
- Bổ sung nguyên tắc cân bằng tổng thể để game có build đa dạng:
  - Không hệ nào quá nổi trội, không hệ nào quá yếu.
  - Build thuần mạnh nhất ở chuyên môn.
  - Build hybrid linh hoạt hơn nhưng không vượt build thuần ở sát thương/chuyên môn.
  - Chuyển hướng cân bằng khuyến nghị từ 70% cố định sang off-element offense soft cap để 3 hệ luôn song hành ở late game: 70% trước mốc 120, 35% sau mốc 120.
  - Nếu test lệch, ưu tiên chỉnh soft cap/efficiency/cap/monster distribution/skill cost thay vì phá core formula.
- Bổ sung nguyên tắc quan trọng: khắc hệ áp dụng cho cả PvE/train monster, không chỉ PvP; player đánh monster và monster đánh player đều dùng chung `ResolveElementPercent()` để battle server thống nhất.
- Ghi hướng phân bổ hệ monster theo map train:
  - Early game nhiều Neutral để dễ tiếp cận.
  - Mid game mỗi map có hệ chủ đạo + hệ phụ.
  - Late game/boss có hệ rõ để người chơi chuẩn bị resist/trang bị/build.
