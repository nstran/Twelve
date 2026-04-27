# 01 - Implementation Plan CSharp

Stat calculator, C# gap analysis, schema/service blueprint, port order và trạng thái chốt cuối.

Nguồn tách từ PLAYER_CHARACTER_RECONSTRUCTION.md, giữ nguyên nội dung phân tích gốc theo nhóm chủ đề.

## Stat Calculator Và Derived Combat

`jp/jq/js/jr` là calculator theo raw class code:

- `1` = Hỏa
- `2` = Lôi
- `4` = Thủy

`com.mg.sq.a.a(lh)`:

1. lấy base stats + bonus stats
2. cộng modifier từ equipment `ll.r`
3. gọi calculator theo hệ
4. ghi lại:
   - `r` max HP
   - `A` dodge
   - `C` crit
   - `z` defense
   - `B` hit
   - `x/y` damage range

Điểm cần nhớ:

- Java raw element code là `1/2/4`, không phải `0/1/2`
- nếu C# muốn bám sát packet cũ thì phải có map rõ giữa storage enum và wire enum

`com.mg.sq.a.a(lh)` không chỉ gọi calculator từ base stats. Nó làm pipeline:

1. lấy base + bonus hiện có: `h+l`, `j+m`, `i+n`, `k+o`
2. tạo calculator theo `jp.a(lh.g)`
3. duyệt từng `ll` trong `D`
4. cộng stat modifier `lb.a/b/c/d` vào 4 stat nền
5. cộng flat attack `lb.e`
6. cộng `% attack` từ `lb.n` theo `jz2.c() * lb.n / 100`
7. cộng crit `lb.g`, defense `lb.f`, dodge `lb.h`, max HP `lb.i`
8. chạy calculator lại với stat sau equipment
9. ghi derived vào `lh.r/A/C/z/B/x/y`

Mapping derived cuối:

| `lh` field | Meaning | Nguồn |
|------------|---------|-------|
| `r` | max HP | `jz2.a() + lb.i` |
| `A` | dodge | `jz2.e() + lb.h` |
| `C` | crit | `jz2.g() + lb.g` |
| `z` | defense | `jz2.d() + lb.f` |
| `B` | hit/accuracy | `jz2.f()` |
| `x` | damage chính/status attack | `jz2.b() + flat attack + percent attack` |
| `y` | damage trần/battle range upper | `jz2.c() + flat attack + percent attack` |

Ghi chú UI status Java cũ: màn hình nhân vật chỉ có **một ô `Tấn Công`**, không hiển thị dạng range. Ảnh tham chiếu nhân vật level 170 (`CườngLực 510`, `NộiLực 110`, `ThânPháp 107`, `ThểLực 209`) cho thấy `Tấn Công = 524`, khớp với Hỏa dùng `lh.x = CườngLực + flatAttack/status attack bonus`, còn `lh.y` chỉ nên giữ cho battle damage upper bound. Client remake vì vậy phải hiển thị `runtime.minDamage` ở status, không hiển thị `runtime.maxDamage`.

C# `StatCalculator` hiện mới tương đương calculator nền `jq/js/jr`; chưa có lớp tổng hợp `lh + ll.r + bonus stats` như bridge Java.

## Phần C# Hiện Tại Đã Có

Đã có:

- account register/login/token
- tạo nhân vật cơ bản
- free stat points cơ bản
- công thức stat nền tối thiểu
- map bootstrap rất thô
- move echo rất thô
- monster roster/map encounter
- monster battle bootstrap

Tương ứng các file:

- `server/Twelve.Application/Handlers/AuthHandler.cs`
- `server/Twelve.Application/Handlers/TokenAuthHandler.cs`
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `server/Twelve.Application/Handlers/AllocateStatHandler.cs`
- `server/Twelve.Application/Handlers/MapHandler.cs`
- `server/Twelve.Application/Handlers/MoveHandler.cs`
- `server/Twelve.Application/Handlers/MonsterEncounterHandler.cs`

## Gap Analysis: C# So Với Java

### 1. `Player.cs` mới chỉ phản ánh một phần nhỏ của `lh`

Hiện có:

- level
- gold / exp
- map / room
- hp/mp/power
- 4 base stats
- free points
- gender / element / face / hair / skin

Cần kiểm tra lại naming `gold/exp` hiện tại với Java:

- Java `J/M/N` là trục EXP/progression: current, floor, ceiling.
- Java `H/I` là trục KEN/gold/collection: current, cap.
- Nếu `Player.Gold` hiện chỉ là tiền ví tuyệt đối thì không nên map thẳng vào `lh.H` nếu server mới còn có wallet riêng.

Thiếu hẳn:

- bonus stats `l..q`
- derived combat stats `x..C`
- free skill points `L`
- prestige/honor `ab`
- title strings `Q/R/S`
- appearance descriptors `U/V/W`
- equipment `ll[]`
- inventory `lm[]`
- learned skills `lv[]`
- map icon/timer overlays `lt[]`
- appearance toggles `Z/aa`
- special actor form `Y`

### 2. Session model hiện quá mỏng

`GameSession.cs` chỉ giữ:

- `Username`
- `IsAuthenticated`

Thiếu:

- current player id
- current map/room actor state
- reconnect state
- active battle session id
- selected target / map interaction context

### 3. DB schema chưa có aggregate cho player thật

Các migration hiện có mới thêm:

- traits
- gender
- 4 base stats
- power

Nhưng chưa có bảng/JSON cột cho:

- equipment loadout
- item inventory
- learned skills
- titles / prestige
- appearance descriptors `U/V/W`
- skill point `L`
- map icon/timer overlays `lt[]`
- map actor state x/y/direction/action

### 4. `CreateCharacterHandler` mới tạo trait-level data, chưa tạo Java-faithful char payload

Thiếu:

- raw element wire code `1/2/4`
- appearance descriptor generation `U/V/W`
- default equipment để compositor dựng đúng sprite
- skill list khởi tạo
- skill points
- titles/prestige init

### 5. `AllocateStatHandler` mới xử lý stat point, chưa cover skill point / derived truth đầy đủ

Thiếu:

- update derived stats như Java full path
- sync damage/defense/dodge/crit vào payload battle/map/profile
- delta packet model giống `ky.b`

### 6. `MapHandler` hiện chưa trả player truth đúng kiểu Java

Hiện làm:

- trả map info cơ bản
- trả roster monster

Thiếu:

- current player snapshot đầy đủ
- room player list
- map object / chest / interactable state
- join/leave/update player broadcasts
- player delta update packets
- map-specific counter load từ `lh.ac`

### 7. `MoveHandler` chưa có world actor state

Hiện chỉ:

- đọc tag `102/103`
- log ra console
- echo packet lại

Thiếu:

- validate tile / collision
- update actor direction/state
- broadcast sang người chơi khác
- sync exact world position
- persist hoặc cache world state

### 8. Battle bootstrap player đang là placeholder

`MonsterBattleBootstrapService` đang dựng player mặc định fake:

- display name `Player`
- fixed HP/MP/Power
- skill list không bám `lh.E`

Trong Java cũ:

- battle player phải đi từ player thật `lh`
- ngoại hình, stat, power, skills, equip đều đi theo char thật

### 9. Element mapping giữa Java và C# chưa canonical

Java:

- `1=Hỏa`
- `2=Lôi`
- `4=Thủy`

C# hiện tại:

- `0=Hỏa`
- `1=Lôi`
- `2=Thủy`

Điều này không sai nếu coi là storage enum nội bộ.
Nhưng phải có một lớp mapping tường minh khi encode/decode packet, nếu không rất dễ lệch về sau.

### 10. Có comment/schema cũ không còn chuẩn với reconstruction hiện tại

Cần thống nhất sớm để tránh lưu data sai nghĩa.

## Khối Chưa Làm Nhưng Bắt Buộc Nếu Muốn Bám Java Sâu

1. Full player aggregate model tương đương `lh + ll[] + lm[] + lv[] + lt[] + df*3`
2. Wire mapping giữa storage enum và raw Java tags
3. Appearance descriptor builder và body compositor data contract
4. Equipment persistence + stat modifier pipeline
5. Inventory persistence + stack/trade flags
6. Learned skill persistence + skill point `L`
7. Player delta packet system theo bitmask
8. Room player roster + broadcast movement/state
9. Map actor state machine cho player
10. Battle bootstrap từ player thật thay vì placeholder
11. Prestige/title/rank system `Q/R/S/ab`
12. Extra counter/timer model `lt[]`

## Data Model Đề Xuất Cho Server Mới

Không nên cố nhét hết vào `Players` table phẳng.

Nên tách:

1. `players`
   - identity, level, raw class, storage element, bars, prestige, titles
2. `player_appearance`
   - create-char inputs
   - raw descriptors `U/V/W`
   - toggles `Z/aa`
3. `player_stats`
   - base stats, bonus stats, derived stats
4. `player_equipment`
   - slot/type/key/resId/affixes
5. `player_inventory`
   - items stack/trade/cap/value
6. `player_skills`
   - learned skills + current level + mana cost snapshot nếu cần
7. `player_counters`
   - `lt[]` raw entries
8. `player_world_state`
   - map/room/x/y/direction/action/lastSeen

Nếu muốn đi nhanh hơn, có thể dùng một aggregate JSON trung gian, nhưng field names vẫn nên mirror `lh` rõ ràng.

### Schema tối thiểu để port đúng `lh`

Nếu giữ PostgreSQL và muốn đi nhanh nhưng vẫn rõ ownership, nên thêm các bảng/cột sau thay vì mở rộng `Players` vô hạn:

```sql
ALTER TABLE Players
    ADD COLUMN IF NOT EXISTS RawElementCode INT NOT NULL DEFAULT 1,
    ADD COLUMN IF NOT EXISTS SkillPoints INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS Honor INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS TitleMain TEXT,
    ADD COLUMN IF NOT EXISTS TitleSub TEXT,
    ADD COLUMN IF NOT EXISTS TitleRank TEXT,
    ADD COLUMN IF NOT EXISTS ExpFloor BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS ExpCeiling BIGINT NOT NULL DEFAULT 100,
    ADD COLUMN IF NOT EXISTS KenProgress BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS KenProgressCap BIGINT NOT NULL DEFAULT 10000,
    ADD COLUMN IF NOT EXISTS AppearanceHidden0 BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS AppearanceHidden1 BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS SpecialActorForm INT NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS PlayerEquipment (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    EquipKey TEXT NOT NULL,
    Slot INT NOT NULL,
    ResourceId INT NOT NULL,
    Level INT NOT NULL DEFAULT 0,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, EquipKey)
);

CREATE TABLE IF NOT EXISTS PlayerInventory (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    ItemId INT NOT NULL,
    Quantity INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, ItemId)
);

CREATE TABLE IF NOT EXISTS PlayerSkills (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    SkillId INT NOT NULL,
    Level INT NOT NULL,
    RawJson JSONB NOT NULL DEFAULT '{}'::jsonb,
    PRIMARY KEY (PlayerId, SkillId)
);

CREATE TABLE IF NOT EXISTS PlayerMapOverlays (
    PlayerId INT NOT NULL REFERENCES Players(Id) ON DELETE CASCADE,
    IconId INT NOT NULL,
    EndsAt TIMESTAMP WITH TIME ZONE NULL,
    DurationMs BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (PlayerId, IconId)
);
```

Lý do dùng `RawJson` ở giai đoạn đầu:

- `ll/lm/lv` có nhiều field phụ đã parse được nhưng chưa cần normalize hết ngay.
- Server vẫn có thể encode Java-faithful packet từ raw aggregate.
- Khi gameplay ổn định có thể tách tiếp affix, market, durability, requirement thành bảng riêng.

### DTO aggregate nên có trong C#

Đừng mở rộng class `Player` hiện tại thành 100 field ngay. Nên thêm model riêng:

```csharp
public sealed class PlayerAggregate
{
    public PlayerCore Core { get; init; }
    public PlayerAppearance Appearance { get; init; }
    public PlayerStats Stats { get; init; }
    public IReadOnlyList<PlayerEquipmentEntry> Equipment { get; init; }
    public IReadOnlyList<PlayerItemStack> Inventory { get; init; }
    public IReadOnlyList<PlayerSkillEntry> Skills { get; init; }
    public IReadOnlyList<PlayerMapOverlay> MapOverlays { get; init; }
}
```

Service boundary khuyến nghị:

- `PlayerAggregateRepository`: load/save full aggregate.
- `PlayerPacketFactory`: encode full snapshot `lh`.
- `PlayerDeltaPacketFactory`: encode bitmask tag `23`.
- `PlayerStatPipeline`: port `com.mg.sq.a.a(lh)`.
- `InventoryService`: mutate `lm[]`, stack/capacity/trade validation.
- `EquipmentService`: mutate `ll[]`, loadout, derived stat refresh.
- `RecipeService`: xử lý `id/ho` (`Nâng cấp`/`Kết hợp`).
- `TradeService`: xử lý command family `55`.

### Wire compatibility cần sửa trong C# hiện tại

C# hiện tại dùng protocol tiện triển khai nhưng chưa phải Java-faithful:

| Việc hiện tại | Java-faithful cần có |
|---------------|----------------------|
| create character `CMD 6`, tag `20-25` | create character `CMD 8`, tag `16`, `15`, repeated `90/96` |
| stat allocation `CMD 50`, một `StatChoice` | stat allocation `CMD 10`, batch tags `118/119/120/121` |
| response combat tags custom `130-135` | dùng tag Java gốc: HP `17/47`, stat `118-121`, derived theo snapshot/delta |
| storage element `0/1/2` | raw wire element `1/2/4` qua mapper tường minh |
| `Gold`/`Exp` phẳng | tách wallet khỏi `J/M/N` EXP gauge và `H/I` KEN-progress gauge |

Nếu muốn giữ client mới song song, có thể hỗ trợ hai protocol mode. Nhưng tầng domain vẫn nên canonical theo Java `lh`, rồi adapter encode ra Java hoặc client-new shape.

## Port Order Khuyến Nghị

1. Thêm `PlayerAggregate` và repository load/save aggregate.
2. Tách storage enum `0/1/2` khỏi raw Java wire enum `1/2/4`.
3. Port appearance descriptor model `U/V/W`, `Z/aa`, và create-char adapter `CMD 8`.
4. Port equipment/inventory/skill persistence.
5. Port `PlayerStatPipeline` gồm base + bonus + `ll.r` modifiers.
6. Dựng full snapshot packet tương đương `ky.a(ku)` đọc vào `lh`.
7. Dựng delta packet tag `23` theo bitmask.
8. Sửa stat allocation thành batch `CMD 10`, không phải single-stat custom `CMD 50`.
9. Nối map player actor state và `lt[]` overlay countdown.
10. Dùng player thật để bootstrap battle thay vì placeholder.
11. Thêm recipe/trade services cho `id/ho/of`.
12. Chỉ sau đó mới tối ưu renderer/client pixel-perfect.

## Trạng Thái Chốt Cuối

Với phạm vi player/character, reconstruction từ Java client đã đạt **100% thực dụng**:

- `lh` field map và wire tag map.
- nested structs `ll/lb/lm/lv/df/dg/lt`.
- full snapshot và delta bitmask.
- create/stat/skill command flow.
- equipment/inventory/use/sell/toggle/capacity.
- upgrade/combine/trade contract.
- map actor, battle actor, result screen dependency.
- C# gap và blueprint schema/service.

Các pending còn lại không phải thiếu phân tích Java nữa, mà là phần server mới phải tự định nghĩa rule vì Java client không mang đủ bằng chứng để đặt tên nghiệp vụ tuyệt đối:

- catalog tên nghiệp vụ cho từng `lt.a` icon id.
- giá trị curve chính xác cho title/rank/prestige nếu không nằm trong client.
- một số social/system command nếu remake toàn bộ Ola/social layer.

Nguyên tắc khi implement: không chờ dữ liệu ngoài repo. Lưu raw id/tag đúng theo Java, dựng rule từ parser/encoder/UI hiện có, rồi đặt tên business theo mức chắc chắn của bằng chứng. Mọi logic mới phải ghi rõ nguồn suy luận trong MD hoặc comment cạnh service.

## Stat tiềm năng, level và movement ngoài map

### Kết luận bám Java hiện tại

Nguồn trực tiếp từ Java client:

- `kl.java — kl.b(lh)`:
  - `this.i = 4 + lh.G / 10`, cap `9`: tốc độ actor ngoài map theo **level**, dùng integer division giống Java.
  - `this.a = 11 + lh.G / 10`, cap `16`: timer/tham số animation tấn công, giữ làm tham chiếu.
- `lh.G`: level.
- `lh.h/i/j/k`: 4 stat nền; trong tài liệu server mới đang map lần lượt thành `CuongLuc/NoiLuc/ThanPhap/TheLuc` theo UI.
- Java client chưa tìm thấy công thức level-up tự cộng `CuongLuc/NoiLuc/ThanPhap`; các stat này đang được xử lý như điểm tiềm năng do server cấp và người chơi phân bổ. Vì vậy server remake **không tự cộng 3 stat này khi level up**, mà cộng `freePoints` rồi để flow phân điểm cập nhật aggregate/derived stat.

### Rule đã chốt cho server remake

Vì map train hiện tại là map mới, không có map gốc để mirror platform/collision tuyệt đối, movement dùng 2 tầng:

1. **Tầng Java-faithful bắt buộc**:
   - Level tăng tốc theo `kl.i = min(9, 4 + level / 10)`.
   - Phải giữ integer division: level `1..9` chưa tăng bậc, `10..19` tăng 1 bậc, ...
2. **Tầng thiết kế remake có ghi nguồn suy luận**:
   - `ThanPhap` ảnh hưởng tốc độ ngang và lực nhảy vì nghĩa nghiệp vụ là thân pháp/agility.
   - Hệ số cố tình nhỏ và có cap để không phá map/collision.

Công thức đang implement trong `server/Twelve.Core/GameLogic/MapMovementCalculator.cs`:

```csharp
javaSpeed = Math.Min(9, 4 + level / 10);
moveSpeed = min(2.8, 1.0 + (javaSpeed - 4) * 0.30 + thanPhap * 0.008);

jumpSpeed = min(12.0, 6.4 + max(0, thanPhap - 10) * 0.06);
attackTimer = min(16, 11 + level / 10);
```

Client nhận qua `PlayerRuntimeSnapshot.mapMoveSpeed/mapJumpSpeed`, merge vào `CharacterAppearance.mapMovement`, rồi `HoaLuMapScreen` truyền xuống `CharacterController`:

- `speed={appearance.mapMovement?.moveSpeed ?? sceneConfig.playerSpeed}`
- `jumpSpeed={appearance.mapMovement?.jumpSpeed}`

### Nhật ký chỉnh sửa 2026-04-25

- Thêm `server/Twelve.Core/GameLogic/MapMovementCalculator.cs` để tập trung hóa công thức movement ngoài map, có comment nguồn `kl.java — kl.b(lh)`.
- Cập nhật `server/Twelve.Core/Players/PlayerRuntimeContracts.cs` thêm `MapMoveSpeed`, `MapJumpSpeed`.
- Cập nhật `server/Twelve.Application/Players/PlayerRuntimeService.cs` để build snapshot từ `MapMovementCalculator`.
- Cập nhật client:
  - `client/src/screens/character/status/CharacterStatus.api.ts`
  - `client/src/screens/character/shared/characterAppearance.ts`
  - `client/src/engine/character/character.types.ts`
  - `client/src/engine/character/CharacterController.tsx`
  - `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`
- Bổ sung resource battle theo hướng bám Java nhất có thể từ evidence hiện có:
  - Nguồn Java chắc chắn: `lh.h/i/j/k` là stat nền, `lh.u/t` MP, `lh.w/v` Power; battle HUD `mx` vẽ HP/MP/Power.
  - Client Java không chứa công thức server cũ chính xác cho lượng ăn đào/nộ/MP, nên remake ghi rõ đây là rule suy luận có kiểm soát.
  - `client/src/screens/battle/core/BattleScreen.shared.ts`: thêm `scalePowerGainByStrength()` để Cường Lực tăng tốc độ nhận nộ/Power, tương tự rule đào/HP hiện có; Nội Lực vẫn tăng MP qua `scaleManaGainByMagic()`.
  - `client/src/screens/battle/hooks/useBattleMatchFlow.ts`: áp dụng scale Power theo Cường Lực khi match gem/resource trong trận.
- Thân Pháp tiếp tục đi qua pipeline derived stat Java đã phục dựng: chính xác, né tránh, chí mạng; ngoài map còn ảnh hưởng move/jump theo rule remake có cap.
- Chốt lại hiển thị `Tấn Công` trên status theo ảnh Java cũ:
  - Java status là một số đơn, không phải range.
  - `lh.x`/`runtime.minDamage` là số hiển thị ở ô `Tấn Công`.
  - `lh.y`/`runtime.maxDamage` chỉ giữ làm damage trần cho battle range hoặc công thức skill nếu cần.
  - Cập nhật `client/src/screens/character/status/CharacterStatus.api.ts` để merge `combat.attack = runtime.minDamage`.

### Nhật ký chỉnh sửa 2026-04-27 — Dọn AppearanceJson duplicate

- Loại `AppearanceJson` khỏi runtime C# và schema đề xuất vì dữ liệu diện mạo đã có source-of-truth scalar theo Java-compatible tags:
  - `Gender`, `Element`, `RawElementCode`, `FaceStyle`, `HairStyle`, `HairColor`, `SkinColor`
  - `AppearanceHidden0`/`AppearanceHidden1` (`lh.Z`/`lh.aa`)
  - `SpecialActorForm` (`lh.Y`)
- Cập nhật code:
  - `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
  - `server/Twelve.Core/Entities/Player.cs`
  - `server/Twelve.Core/Entities/PlayerAggregate.cs`
  - `server/Twelve.Infrastructure/Repositories/PlayerRepository.cs`
  - `server/Twelve.Infrastructure/Repositories/PlayerAggregateRepository.cs`
  - `server/Twelve.Application/Players/PlayerRuntimeService.cs`
- Cập nhật migration:
  - `server/Database/07_player_character_aggregate.sql`: không tạo mới `AppearanceJson`.
  - `server/Database/13_remove_appearance_json.sql`: drop cột duplicate khỏi DB hiện có.
- Giữ nguyên `BonusCuongLuc/BonusThanPhap/BonusNoiLuc/BonusTheLuc` vì đây là field Java `lh.l/m/n/o` phục vụ stat bonus/equipment pipeline, không phải dữ liệu duplicate.

### Nhật ký chỉnh sửa 2026-04-25 — Battle damage dùng stat nhân vật thật

- Cập nhật `server/Twelve.Application/Battle/BattleTurnEngine.cs`.
- Battle damage giờ tiêu thụ trực tiếp stat đã phục dựng theo Java `lh`/`jq`/`js`/`jr`:
  - `MinDamage/MaxDamage` lấy từ `lh.x/lh.y` qua `PlayerStatPipeline`.
  - `HitRate` lấy từ `lh.B`.
  - `DodgeRate` lấy từ `lh.A`.
  - `Defense` lấy từ `lh.z`.
  - `CriticalDamage` hiện đang là `lh.C`/`Chí Mạng %`.
- Công thức server-authoritative được ghi comment nguồn ngay trong `CalculateDamage()`:
  - Java client chỉ render kết quả damage/delta server gửi, không chứa công thức damage cuối.
  - Vì vậy remake giữ các stat đầu vào Java-faithful, còn damage resolution là rule server mới có kiểm soát.
- Điều chỉnh hit/miss:
  - `hitChance = clamp(80 + (HitRate - DodgeRate) / 4, 20, 95)`.
  - `Thân Pháp` vì thế ảnh hưởng rõ trong battle qua chính xác/né tránh/chí mạng đúng pipeline Java.
- Điều chỉnh damage:
  - Roll trong range `MinDamage..MaxDamage`.
  - Cộng bonus stat chính theo family skill qua `ResolveAttackStat()`.
  - Trừ `Defense + Vitality / 4`.
  - Áp variance integer `92..108%`, crit `150%`, nộ full power x2.
- Điều chỉnh resource:
  - Player skill level ưu tiên skill đã học thật trong session, không mặc định debug level 12 nếu đã có skill.
  - Mana cost ưu tiên `BattleSessionSkillInstance.ManaCost`; nếu chưa học skill mà player có skill list thì từ chối cast bằng cost không đủ.
  - Power gain giảm bớt độ phình để bám vai trò `lh.w/v` là thanh nộ tích dần, không thay thế sát thương chính.

## Kết Luận

Nếu mục tiêu là khôi phục sát Java cũ thì:

- đừng xem `monster xong rồi giờ thêm vài field player` là đủ
- `player/char` là một aggregate lớn, và `lh` mới là spec thật
- phần khó nhất không phải mỗi stat, mà là đồng bộ giữa:
  - snapshot truth
  - delta updates
  - appearance compositor
  - map actor
  - battle actor
  - equipment/inventory/skill persistence

File này nên tiếp tục là note trung tâm duy nhất để bám toàn bộ lõi `char/player` trước khi bắt tay vào code.
