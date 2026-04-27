# CHANGELOG

## 2026-04-27 (AD)

### Tắt lại natural special spawn, giữ special clear cho node có sẵn

**Mục tiêu:**
- Khớp lại `resolveJavaBoardStep()` và `BATTLE_SYSTEM_RECONSTRUCTION.md` với gameplay memory user đã chốt: match `>= 4` chỉ cộng lượt, item match xong biến mất/drop/refill, không để lại special mới.
- Vẫn giữ note nhánh decompile `mq/mr` như tham chiếu/feature flag tương lai nếu có packet log/replay chứng minh mode server cũ từng bật natural special spawn.

**Sửa:**
- Cập nhật `client/src/screens/battle/core/BattleScreen.logic.ts`:
  - không spawn special mới `10..15`/`20..25` sau line match `>= 4`, `>= 5` hoặc cross;
  - vẫn giữ chain clear nếu special node đã tồn tại sẵn trong clear queue từ packet/skill/debug/legacy data:
    - `type 2` (`10..15`) clear 8 ô xung quanh;
    - `type 4` (`20..25`) clear hàng + cột;
  - giữ `bonusTurnCandidate`/extra-turn candidate cho match group `>= 4`, tách khỏi special spawn.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`:
  - sửa coverage và mục special spawn: decompile có nhánh `mq/mr`, nhưng không bật mặc định theo gameplay memory hiện tại;
  - chốt rõ match `>= 4` cộng lượt nhưng không tạo special mới;
  - ghi lại nếu sau này có packet log/replay chứng minh server cũ bật natural spawn thì phải thêm feature flag riêng, không bật mặc định;
  - cập nhật nhật ký chỉnh sửa ngày `2026-04-27`.

**Kiểm tra:**
- Đang chạy `npx tsc -p client/tsconfig.json --noEmit` sau khi cập nhật mục này.

**File đã sửa:**
- `client/src/screens/battle/core/BattleScreen.logic.ts`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-26 (AC)

### Cập nhật battle board theo gameplay memory: icon, +lượt, bỏ natural special mặc định

**Mục tiêu:**
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` theo xác nhận gameplay memory mới nhất về bàn cờ Java gốc.
- Tách rõ nhánh decompile có `mr.x/mr.y` với behavior người chơi nhớ: match xong item biến mất, không tạo special tự nhiên.

**Sửa:**
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`:
  - thêm mapping 8 icon gameplay gốc:
    - kiếm thường/trắng là `chess0`;
    - tim hồi máu là `chess1`;
    - đào là `chess3`, dùng hồi nộ/Power;
    - kiếm lửa là `chess8`, nổ vùng `3x3`, gây sát thương ngay với hệ số user memory `x1.5`;
    - sao xanh tăng EXP nếu thắng trận;
    - vàng tích điểm, max `10k` quy đổi `10k Quan`;
    - giọt tím EXP nửa sao match bình thường, không tạo special;
    - vàng/sao/giọt tím chỉ tích pending reward nội bộ, không cần hiện counter tạm trong battle HUD.
  - sửa kết luận natural special spawn:
    - decompile có nhánh spawn `10..15`/`20..25`;
    - nhưng gameplay memory xác nhận không item nào tạo special;
    - vì vậy không coi natural special spawn là gameplay mặc định.
  - chốt rule phục dựng `+ lượt`:
    - match `>= 4` cộng lượt;
    - cộng theo số group match đủ điều kiện;
    - tách khỏi special spawn.
  - note các công thức cần tính sau: HP từ tim, nộ từ đào, EXP từ sao xanh/giọt tím, gold/Quan từ vàng, base damage/target ownership của kiếm lửa; riêng multiplier kiếm lửa `x1.5` đã xác nhận từ gameplay memory.
- Cập nhật `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`:
  - ghi rõ gameplay memory về resource/reward board item;
  - thêm contract `BattleTemporaryRewards`: EXP/Gold/Quan từ board chỉ commit khi thắng, thua thì discard, không cần hiện counter tạm trong battle HUD;
  - ghi rõ đào trên bàn cờ `chess3` hồi Power/Nộ, không nhầm với item tiêu hao hồi HP;
  - thêm ràng buộc kiếm lửa `chess8` là behavior board riêng, không đồng nghĩa với natural special node `10..15`/`20..25`;
  - ghi rõ kiếm lửa nổ `3x3` gây sát thương với multiplier user memory `x1.5`, còn base damage/target/owner vẫn là rule server/remake cần tính sau.

**Kiểm tra:**
- Không chạy `dotnet build` vì task chỉ sửa tài liệu, không sửa code server.
- Đã kiểm tra nội dung tài liệu qua phản hồi `replace_in_file`.

**File đã sửa:**
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `CHANGELOG.md`

---

## 2026-04-26 (AB)

### Tổng hợp lại logic Java battle bàn cờ

**Mục tiêu:**
- Rà lại logic Java liên quan battle/bàn cờ và cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`.
- Sửa/bỏ các kết luận dễ gây sai lệch, tách rõ logic client Java tự simulate với phần server Java cũ từng gửi qua packet.

**Sửa:**
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`:
  - chuẩn hóa `Java Boundary Chốt`;
  - bổ sung state machine `mq` và mapping `nq.c`;
  - ghi rõ checksum/sync board qua `nq.i` và `oz.a(ms.l)`;
  - bổ sung skill packet board mutation từ `nq.n/r/s/o/q/p` và target arrays;
  - bổ sung runtime cell animation `nd` và combo popup `ne`;
  - note riêng các phần thuộc server Java cũ cần tính sau: `nq.D`, `nq.F`, `nl[]`, refill queue, no-move reset board, reward roll.

**Kiểm tra:**
- Không chạy `dotnet build` vì task chỉ sửa tài liệu, không sửa code server.
- Đã kiểm tra lại nội dung tài liệu sau khi ghi qua phản hồi `replace_in_file`.

**File đã sửa:**
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## [26/04/2026]

### Battle System – Bỏ cơ chế Special Gem (Match 4/5)

**File thay đổi:** `client/src/screens/battle/core/BattleScreen.logic.ts`

**Vấn đề:** Khi ăn ≥4 gem cùng loại, engine tự động:
1. Xóa toàn bộ gem trong match
2. Spawn lại 1 "special gem" (TYPE2: gem 10-15, có crystal overlay; TYPE4: gem 20-25, viền đỏ) tại vị trí giữa
3. Special gem đó khi bị ăn tiếp sẽ nổ 3×3 ring (TYPE2) hoặc cả hàng + cột (TYPE4)

Người dùng nhầm tưởng đây là bug do game Java gốc thực tế **không có** tính năng này.

**Sửa đổi trong `resolveJavaBoardStep`:**
- **Bỏ** `collectSpecialChainKeys()` → không mở rộng vùng xóa qua special gem
- **Bỏ** `resolveSpawnGem()` → không spawn special gem mới sau match 4/5
- `clearedKeys` giờ đúng bằng `triggerKeys` (chỉ những ô match thực sự)
- `spawnedSpecials` luôn trả về `[]`
- `bonusTurnCandidate` giữ nguyên (match 4+ vẫn được thêm lượt)

**Kết quả:** Match 4+ xóa đúng số ô, không còn gem sót lại, không còn hiệu ứng nổ dây chuyền bất ngờ.


## 2026-04-26 (AA)

### Sửa fallback gem resource khiến MP hồi nhầm HP

**Vấn đề:**
- Khi bootstrap payload thiếu `perGemBases`, client fallback sang 3 scalar global `BaseHealPerGem/BaseManaPerGem/BasePowerPerGem`.
- Do một số gem MP/mixed còn có semantic `fx.heal > 0`, scalar global làm gem MP hồi thêm HP rõ rệt.

**Sửa:**
- Sửa `getServerGemResourceBase()` trong `useBattleMatchFlow.ts`:
  - nếu có `perGemBases` thì vẫn dùng server authority per-gem;
  - nếu payload cũ thiếu `perGemBases` thì fallback về `GEM_FX_BASE` per-color, không dùng scalar global cho mọi gem.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md`:
  - ghi rõ nguyên nhân lỗi;
  - chốt policy hiện tại: HP thắng PvE có thể giữ current HP, còn MP/Power là battle resource tạm và reset về `0` sau trận.

**Căn cứ:**
- Java client xác nhận runtime bar `lh.s/r`, `lh.u/t`, `lh.w/v` và packet result `nl.b/c/d`, nhưng không có server formula cũ cho resource gain.
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`: resource gain là rule remake server-owned; fallback FE chỉ để tương thích payload cũ.

**Kiểm tra:**
- Đang chờ chạy `npx tsc -p client/tsconfig.json --noEmit`.
- Đang chờ chạy `dotnet build Twelve.sln`.

**File đã sửa:**
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-26 (Z)

### Create character không khởi tạo MP full

**Vấn đề:**
- Nhân vật mới tạo đang được `CreateCharacterHandler` set `player.Mp = player.MaxMp`, làm battle bootstrap trận đầu bằng MP full.
- Policy mới đã chốt MP/Power là battle resource tạm: `/battle/result` reset `Player.Mp = 0`, `Player.Power = 0`; `PlayerBattleStateFactory` đọc current MP/Power từ DB để mở trận mới.
- Vì vậy dữ liệu `mp = maxmp` trong DB khiến nhịp MP/Nộ sai dù công thức match gem đã chuyển sang server authority.

**Sửa:**
- Sửa `CreateCharacterHandler`:
  - vẫn chạy `PlayerStatPipeline.RecalculateAndApply(player)` để tính `MaxHP/MaxMP/MaxPower` và derived stats;
  - set `player.Hp = player.MaxHp`;
  - set `player.Mp = 0`;
  - set `player.Power = 0`;
  - thêm comment nguồn từ `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`.
- Cập nhật `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md` để ghi rõ nhân vật mới không seed `CurrentMP = MaxMP`.
- Ghi chú vận hành: dữ liệu dev/test cũ có thể cần reset current battle resource bằng `UPDATE players SET mp = 0, power = 0;`.

**Căn cứ:**
- Java client chứng minh `lh.u/t`, `lh.w/v` là current/max bars nhưng không có server Java cũ cho policy carry-over resource.
- Spec hiện tại xem MP/Power là tài nguyên tạm trong battle, còn HP sau thắng PvE giữ lại để train attrition có ý nghĩa.

**Kiểm tra:**
- Đang chờ chạy `npx tsc -p client/tsconfig.json --noEmit`.
- Đang chờ chạy `dotnet build Twelve.sln`.

**File đã sửa:**
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `CHANGELOG.md`

---

## 2026-04-26 (Y)

### Battle result reset MP/Power sau trận

**Vấn đề:**
- Sau khi chuyển resource battle sang server authority, `/battle/result` đang persist MP/Power còn lại vào DB, làm trận sau bootstrap bằng MP/Nộ đã tích từ trận trước.
- Rule gameplay cần phân biệt rõ:
  - HP sống sót sau thắng PvE được giữ để train attrition có ý nghĩa;
  - MP/Power là tài nguyên tạm trong battle, không carry sang trận sau.

**Sửa:**
- Sửa `BattleResultService`:
  - thắng PvE giữ `CurrentHp` đã clamp từ session result;
  - thua PvE hồi `HP = MaxHp`;
  - PvP shadow hồi `HP = MaxHp`;
  - mọi result reset `Player.Mp = 0`, `Player.Power = 0` sau claim;
  - level-up vẫn chạy `PlayerStatPipeline.RecalculateAndApply`, nhưng sau đó MP/Power vẫn reset `0`.
- Rà lại battle board:
  - `BattleScreen.logic.ts` đã khôi phục natural special spawn theo `mq.java:691-699`: line `>=4` sinh `10..15`, cross hoặc line `>=5` sinh `20..25`;
  - `useBattleMatchFlow.ts` chỉ áp server-provided base/percent cho HP/MP/Power gain, không hardcode công thức stat ở FE.
- Cập nhật `docs/player-character-reconstruction/06-map-room-battle-runtime.md` để chốt HP/MP/Power result policy mới.

**Căn cứ:**
- Java client chứng minh `lh.s/r`, `lh.u/t`, `lh.w/v` là current/max bars, nhưng không có server formula cũ cho carry-over resource sau battle.
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5` quy định HP/MP/Power gain là remake server-owned rule; MP/Power được xem là tài nguyên tạm battle trong policy hiện tại.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → lần đầu fail do DLL bị lock bởi `Twelve.Server (PID 18476)`; đã `taskkill /F /PID 18476` rồi build lại thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Application/Battle/BattleResultService.cs`
- `docs/player-character-reconstruction/06-map-room-battle-runtime.md`
- `CHANGELOG.md`

---

## 2026-04-26 (X)

### Battle resource/damage chuyển sang server authority

**Vấn đề:**
- Battle FE còn tự hardcode một phần công thức HP/MP/Power gain và damage/resource, trong khi spec hiện tại yêu cầu áp dụng từ server để tránh lệch logic.
- PvE monster bootstrap chưa phát đủ metadata resource gain server-owned cho client battle runtime.

**Sửa:**
- Mở rộng battle/session contract để trả `HealGainPercent`, `ManaGainPercent`, `PowerGainPercent`.
- Sửa server battle bootstrap:
  - player battle state lấy stat/resource từ `PlayerStatPipeline`/Java-faithful status hiện có;
  - monster battle state tính resource gain trên server theo rule remake có ghi nguồn từ `08-level-stat-exp-and-element-balance.md §5`;
  - snapshot bootstrap trả resource gain percent cho cả player và enemy.
- Sửa client battle:
  - thêm type contract cho resource gain percent;
  - hydrate battle combatant từ server snapshot thay vì tự suy diễn bằng công thức FE;
  - gem HP/MP/Power gain dùng percent server trả về, chỉ fallback `100` cho compatibility snapshot cũ.
- Bổ sung `BattleGemResourceConfig` trong monster/PvP battle bootstrap để base HP/MP/Nộ từ match gem cũng chuyển sang server authority:
  - `BaseHealPerGem = 18`
  - `BaseManaPerGem = 5`
  - `BasePowerPerGem = 5`
- Giữ `GEM_FX_BASE` ở FE là bảng semantic/compat cho loại gem nào sinh HP/MP/Power; trị số base ưu tiên lấy từ bootstrap server, fallback FE chỉ để tương thích payload cũ.
- Sửa PvP enemy shadow trong `PvpArenaService` để `MonsterBattleInstance` expose đúng `HealGainPercent/ManaGainPercent/PowerGainPercent` từ `BattleSessionCombatantState`; không để client fallback `100%` làm lệch nhịp resource giữa hai phía PvP.
- Sửa `BattleTurnEngine` để damage/skill/power gain lấy stat server-owned từ `BattleSessionCombatantState`:
  - damage dùng `MinDamage/MaxDamage`, `Defense`, `HitRate`, `DodgeRate`, `CriticalDamage`;
  - bỏ cộng thêm stat/level hardcode FE-like trong turn engine để tránh double-count với `PlayerStatPipeline`;
  - Power gain của người đánh/người bị đánh scale qua `PowerGainPercent` server bootstrap.
  - áp dụng khắc hệ battle v1 ở server bằng `ElementCode`: Cường Lực-like `0` > Thân Pháp-like `1` > Nội Lực-like `2` > Cường Lực-like `0`, với `112%/100%/92%`.
- Sửa player battle snapshot không còn reset MP/Power về `0`; session lấy `Player.Mp` và `Player.Power` đã clamp theo max để đúng payload Java `lh.u/t`, `lh.w/v`.
- Sửa `/battle/result` để persist MP/Power còn lại từ session result đã clamp, kể cả PvE/PvP shadow; trận sau bootstrap từ server state thật thay vì FE/full default.
- Hậu kiểm monster battle rule:
  - `MonsterBattleRuleFactory` không còn gán nhầm multiplier cũ `criticalDamage` vào `CriticalRate`; `CriticalRate` dùng crit chance Java-like `min(5 + agility / 8, 30)` cộng bonus role/threat có cap 30.
  - `QuanReward` PvE giữ `0`; reward tiền train dùng `GoldReward`.
- Hạ in-battle resource scale §5 từ cap 180%/3% mỗi điểm xuống cap 140%/1% mỗi điểm để tránh bùng nổ HP/MP/Power ở PvP/PvE mid-late game.
- Chốt `BaseManaPerGem = 5` cho cả PvE/PvP ở bootstrap server để FE không còn tự hardcode nhịp MP; giá trị này là rule remake có kiểm soát nhằm giữ MP pacing gần Java-feel và tránh spam skill khi mọi gem MP đi qua cùng scalar server.

**Căn cứ:**
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`: Java client xác nhận HP/MP/Power bar (`lh.s/r`, `lh.u/t`, `lh.w/v`) nhưng không có server formula cũ; remake v1 dùng integer math và TotalStat cho resource, đồng thời quy định khắc hệ battle v1 `112%/100%/92%`.
- `PLAYER_CHARACTER_RECONSTRUCTION.md`: status/derived stat gửi client phải bám Java `jp/jq/js/jr`, không áp soft-cap ở tầng hiển thị nhân vật.
- `docs/combat-formulas.md` và `BATTLE_SYSTEM_RECONSTRUCTION.md`: Java client chỉ render delta/kết quả server gửi, không chứa công thức damage cuối; server remake phải là authority cho damage/resource.
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5`: base match gem resource cũng là rule remake có kiểm soát, phải phát từ server bootstrap PvE/PvP để FE không hardcode công thức HP/MP/Nộ.
- `docs/player-character-reconstruction/06-map-room-battle-runtime.md`: battle result claim nhận HP/MP/Power runtime theo `lh.s/r`, `lh.u/t`, `lh.w/v`; server phải đồng bộ DB để runtime snapshot sau trận không tự phục hồi tài nguyên sai logic.

**Kiểm tra:**
- `dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `server/Twelve.Core/Battle/BattleSessionContracts.cs`
- `server/Twelve.Core/Monsters/MonsterContracts.cs`
- `server/Twelve.Application/Battle/PlayerBattleStateFactory.cs`
- `server/Twelve.Application/Battle/BattleTurnEngine.cs`
- `server/Twelve.Application/Battle/BattleResultService.cs`
- `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
- `server/Twelve.Application/Players/PvpArenaService.cs`
- `client/src/screens/battle/core/BattleScreen.shared.ts`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/core/BattleScreen.types.ts`
- `client/src/screens/battle/BattleScreen.tsx`
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `docs/player-character-reconstruction/06-map-room-battle-runtime.md`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-26 (W)

### Cân bằng lại công thức HP/MP/Nộ từ match gem — level 1 quá nhanh

**Vấn đề:**
- Level 1 ăn 3 viên MP đã thấy thanh MP tăng nhanh, trong khi Java cũ cần nhiều viên hơn mới thấy rõ tác dụng.
- Quái vật ăn Trái Đào/viên đào hồi HP cực nhanh do Cường Lực scale `3%/point`, cap `180%` và giá trị base heal viên đào `28` quá lớn.
- Power/nộ tích nhanh do scale `3%/point` khiến rage burst x2 xuất hiện sớm hơn dự tính.

**Nguyên nhân:**
- `GEM_FX_BASE` cũ: heal `28`, mana `15`, pow `5` — quá cao cho balance level 1.
- `HEAL_GAIN_PERCENT_PER_STRENGTH/MANA_GAIN_PERCENT_PER_MAGIC/POWER_GAIN_PERCENT_PER_STRENGTH = 3%/point`, cap `180%` — Cường Lực/Nội Lực 10 điểm (level 1) đã cho `100%`, nhưng quái có stat cao hơn player thì heal/mp gain càng lớn.

**Sửa:**
- Sửa `client/src/screens/battle/core/BattleScreen.shared.ts`:
  - `GEM_FX_BASE` viên đào (cat 1): `heal 28 → 12`, `pow 2 → 1`.
  - `GEM_FX_BASE` viên đào/mana mixed (cat 2): `heal 5 → 2`, `mana 15 → 8`, `pow 3 → 1`.
  - `GEM_FX_BASE` mixed (cat 4): `heal 10 → 4`, `mana 5 → 3`, `pow 3 → 1`.
  - `GEM_FX_BASE` mana (cat 5): `mana 8 → 5`, `pow 4 → 2`.
  - `GEM_FX_BASE` sword (cat 0/8): `pow 5 → 3`.
  - `GEM_FX_BASE` gold (cat 6): `pow 2 → 1`.
  - `HEAL_GAIN_PERCENT_PER_STRENGTH`: `3 → 1`.
  - `POWER_GAIN_PERCENT_PER_STRENGTH`: `3 → 1`.
  - `MANA_GAIN_PERCENT_PER_MAGIC`: `3 → 1`.
  - `MIN_RESOURCE_GAIN_PERCENT`: `80 → 90`.
  - `MAX_RESOURCE_GAIN_PERCENT`: `180 → 140`.
  - Thêm comment ghi rõ đây là giá trị remake có kiểm soát, chưa có server Java source cụ thể.

**Căn cứ:**
- Java client xác nhận các bar `lh.s/r` (HP), `lh.u/t` (MP), `lh.w/v` (Power) nhưng không chứa bảng resource gain theo gem của server cũ.
- Remake cần pacing gần Java cũ: ăn 3 viên MP nhỏ chỉ được ~1/4 thanh MP ở level 1, không phải hơn.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `client/src/screens/battle/core/BattleScreen.shared.ts`
- `CHANGELOG.md`

---

## 2026-04-25 (V)

### Khôi phục natural special spawn match 4/5 theo Java

**Sửa:**
- Sửa `client/src/screens/battle/core/BattleScreen.logic.ts`:
  - khôi phục `resolveSpawnGem` theo `mq.java:691-699`;
  - cross hoặc line `>= 5` spawn `20..25` (`type 4`, clear hàng + cột);
  - line `>= 4` spawn `10..15` (`type 2`, clear 8 ô lân cận);
  - node mask `64`/`70` không nâng cấp special tự nhiên.
- Rà `Quan`/`Gold(KEN)` PvE reward: không phát hiện thêm chỗ cần sửa ngoài trạng thái Gold/KEN đã tách trước đó.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` thêm nhật ký và boundary: `nq.D`/`nq.F` vẫn là packet/server result, local `bonusTurnCandidate` chỉ là heuristic cho đến khi có packet log/server source.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → lần đầu fail do DLL bị lock bởi `Twelve.Server (PID 11464)`; đã `taskkill /F /PID 11464` rồi build lại thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `client/src/screens/battle/core/BattleScreen.logic.ts`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (U)

### Battle result tách Gold/KEN khỏi Quan paid currency

**Sửa:**
- Sửa `/battle/result` contract:
  - thêm `GoldBefore/GoldAfter/GoldGained` làm reward tiền trận thường;
  - giữ `QuanBefore/QuanAfter/QuanGained = 0` để tương thích nhưng không phát Quan từ quái PvE.
- Sửa `BattleResultService` cộng reward thắng vào `Player.Gold`, không còn dùng tên `Quan` cho reward quái thường.
- Sửa `MonsterBattleTemplate`/`MonsterBattleRuleFactory`:
  - công thức cũ `2 + level + threatBonus` được map sang `GoldReward`;
  - `QuanReward` luôn `0` cho monster battle thường.
- Sửa client battle result:
  - type response dùng `gold*`;
  - popup kết quả hiển thị thanh icon vàng từ `gold*`, fallback `quan*` cũ nếu gặp payload cũ.
- Cập nhật `PLAYER_CHARACTER_RECONSTRUCTION.md` và `docs/player-character-reconstruction/06-map-room-battle-runtime.md` để ghi rõ `lh.H/I` là KEN/gold/collection theo Java, còn `Quan` remake là paid currency.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → lần đầu fail do thừa dấu `)` ở `MonsterBattleRuleFactory.cs`; đã sửa và build lại thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Core/Battle/BattleSessionContracts.cs`
- `server/Twelve.Application/Battle/BattleResultService.cs`
- `server/Twelve.Core/Monsters/MonsterContracts.cs`
- `server/Twelve.Application/Monsters/MonsterBattleRuleFactory.cs`
- `client/src/screens/battle/core/BattleScreen.types.ts`
- `client/src/screens/battle/BattleScreen.tsx`
- `client/src/screens/battle/ui/BattleScreen.overlays.tsx`
- `docs/player-character-reconstruction/06-map-room-battle-runtime.md`
- `PLAYER_CHARACTER_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (T)

### Sửa special board visual và bỏ loading global khi gameplay

**Sửa:**
- Sửa `client/src/screens/battle/ui/BattleScreen.components.tsx`:
  - tách rõ board special node `10` khỏi skill fire-sword mark `1001`;
  - cell `10` chỉ chạy fire-sword animation khi có `fireSwordMarkTrigger`/`fireSwordBaseGemType` từ skill packet;
  - board special `10..15` trở lại đúng `type 2` clear vùng lân cận, không bị render/persist như kiếm đỏ;
  - tiếp tục không dùng `hiddendragon/hiddenphoenix` làm overlay board cho `20..25`, vì đó là UI ornament từ `pc.java`, không phải `nj.g` của cell.
- Sửa `client/App.tsx`:
  - bỏ global fetch loading modal để API battle/map/PvP không còn hiện `Vui lòng chờ...` liên tục trong gameplay.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` thêm nhật ký, nguồn suy luận `nj.java`/`mq.java`/`mp.java`/`mh.java`/`pc.java`.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `client/src/screens/battle/ui/BattleScreen.components.tsx`
- `client/App.tsx`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (S)

### Battle board enemy move scoring bám packed line Java

**Sửa:**
- Sửa `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`:
  - thêm comment nguồn suy luận từ `reference/redecoded/cfr_fresh/mq.java` và `mr.java`;
  - giữ validate swap theo rule Java: board playable `8x8`, swap hợp lệ khi endpoint tạo line ngang/dọc `>= 3`;
  - thêm `JavaMatchInfo` mô phỏng packed line gồm left/up, right/down, total length;
  - enemy move scoring ưu tiên match dài, cross match, special node `10..15` và `20..25` thay vì chỉ đếm số ô match.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` thêm nhật ký chỉnh sửa.

**Kiểm tra:**
- `dotnet build Twelve.sln` → lần đầu build có warning DLL lock bởi `Twelve.Server (17904)` nhưng vẫn succeeded; PID đã thoát trước khi kill; build lại thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (R)

### Battle damage dùng stat nhân vật thật từ pipeline Java

**Sửa:**
- Sửa `server/Twelve.Application/Battle/BattleTurnEngine.cs`:
  - damage battle dùng `MinDamage/MaxDamage`, `HitRate`, `DodgeRate`, `Defense`, `CriticalDamage` từ session combatant state đã build từ `PlayerStatPipeline`;
  - hit/miss dùng `HitRate - DodgeRate`, giúp Thân Pháp ảnh hưởng rõ qua chính xác/né tránh;
  - crit dùng `lh.C`/Chí Mạng %, defense dùng `lh.z`, damage range dùng `lh.x/lh.y`;
  - player skill level ưu tiên skill đã học thật, không mặc định debug level 12 khi đã có skill list;
  - mana cost ưu tiên session skill, Power gain giảm độ phình để giữ vai trò thanh nộ tích dần.
- Cập nhật `docs/player-character-reconstruction/01-implementation-plan-csharp.md` thêm nhật ký và nguồn suy luận.

**Kiểm tra:**
- `dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Application/Battle/BattleTurnEngine.cs`
- `docs/player-character-reconstruction/01-implementation-plan-csharp.md`
- `CHANGELOG.md`

---

## 2026-04-25 (Q)

### Fix LoadingDialog nháy liên tục do polling nền

**Sửa:**
- Sửa `client/App.tsx`:
  - global fetch loading chỉ track các API cần người chơi chờ;
  - bỏ qua các polling nền: `/pvp/challenges/inbox`, `/battle/session-snapshot`, `/battle/session-sync`, `/pvp/opponents`;
  - hỗ trợ header `X-Twelve-Silent-Loading: true` để API nền sau này tự opt-out khỏi global loading.
- Mục tiêu: khi vào Hoa Lư, request polling `inbox` vẫn chạy để nhận PVP challenge nhưng không bật modal `Vui lòng chờ...` liên tục.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/App.tsx`
- `CHANGELOG.md`

---

## 2026-04-25 (P)

### Fix màn đen CanvasKit khi vào fallback map

**Sửa:**
- Đối chiếu lại Java `oh.java`/`og.java` cho flow chọn thành:
  - `oh.c(int,int)` hit test theo mảng `i[]`, set `go.x`;
  - chọn lại cùng địa danh gọi `og.a(this.s)`;
  - `og.f()` gửi `ks.a().b("M99", go.x)`, tức vào thành dùng index catalog.
- Sửa `client/src/engine/MapRenderer.tsx`:
  - bỏ import/usage `@shopify/react-native-skia`;
  - thay Canvas/Rect/Group/Circle bằng React Native `View`/`Text` absolute layout.
- Mục tiêu: fallback `MainScreen`/legacy map không còn crash web runtime với `CanvasKit is not defined` / `WebGLRenderer`.
- Hoa Lư side-scroll runtime thật không đổi.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → lần đầu bị lock bởi `Twelve.Server (PID 17216)` / Visual Studio; đã `taskkill /F /PID 17216` rồi build lại thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `client/src/engine/MapRenderer.tsx`
- `MAP_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (O)

### Sửa pan/cursor/HUD cho world-map selection

**Sửa:**
- Bỏ nested `ScrollView` ở màn chọn bản đồ, chuyển sang `PanResponder` để kéo world-map theo cả 2 trục bằng chuột/touch.
- Bỏ default focus vào `Hoa Lư`; chỉ focus khi con trỏ đang nằm trong hitbox địa danh.
- Cursor đúng theo Java:
  - ngoài hitbox địa danh: `/m/arrow` màu vàng;
  - trong hitbox địa danh: `/m/hand`.
- Sửa lệch lock/label/hitbox sau khi chuyển catalog sang server:
  - tọa độ vẫn bám `oh.java`;
  - client scale theo asset `/m/m` extract thực tế 480x480 thay vì giả định 512x512.
- HUD khi vào map dùng tên địa danh (`Hoa Lư`, `Kỷ Bố`, ...) thay vì `"Khu 1"`.
- Giữ world-map catalog static trong server code, chưa thêm DB:
  - lý do: đây là Java truth từ `og.java`/`oh.java`, ít thay đổi;
  - DB chỉ nên dùng sau này cho unlock/progression từng player hoặc runtime config động.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.
- `dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `client/src/screens/map/selection/MapSelectionScreen.tsx`
- `client/src/screens/map/selection/MapSelectionScreen.styles.ts`
- `client/src/screens/map/selection/assets.ts`
- `client/src/data/MapData.ts`
- `client/App.tsx`
- `server/Twelve.Core/Maps/RuntimeMapCatalog.cs`
- `MAP_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (N)

### Chuyển world-map catalog sang BE theo Java `og/oh`

**Sửa:**
- Thêm catalog 17 địa danh world-map ở server theo Java client:
  - `og.java`: thứ tự/tên địa danh.
  - `oh.java`: tọa độ label, lock icon, hitbox chọn map.
  - `og.f()`/`M99 + go.x`: giữ index làm định danh flow vào thành.
- Thêm API `GET /map/world-catalog` để BE là nguồn truth cho map id/name/unlock/scene kind.
- Client map selection fetch catalog từ BE, fallback mirror server khi offline/dev.
- Render marker/label/lock theo tọa độ Java gốc 512x512 và scale theo nền world map hiện tại.
- Chỉ `Hoa Lư` mở và đi vào side-scroll runtime thật; các địa danh còn lại giữ khóa/legacy cho tới khi có runtime tương ứng.

**Kiểm tra:**
- `dotnet build Twelve.sln` → thành công.
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `server/Twelve.Core/Maps/RuntimeMapCatalog.cs`
- `server/Twelve.Server/Program.cs`
- `client/src/data/MapData.ts`
- `client/src/screens/map/selection/MapSelectionScreen.tsx`
- `client/src/screens/map/selection/MapSelectionScreen.styles.ts`
- `client/App.tsx`
- `MAP_SYSTEM_RECONSTRUCTION.md`
- `CHANGELOG.md`

---

## 2026-04-25 (M)

### Chốt hiển thị `Tấn Công` status theo ảnh Java cũ

**Sửa:**
- Xác nhận màn status Java cũ chỉ hiển thị một số `Tấn Công`, không phải dạng range.
- Giữ server/domain có `MinDamage` và `MaxDamage` để battle dùng damage range.
- Sửa client status để ô `Tấn Công` hiển thị `runtime.minDamage` (`lh.x`) thay vì `runtime.maxDamage` (`lh.y`).
- Bổ sung ghi chú reconstruction: `lh.x` là damage chính/status attack, `lh.y` là damage trần cho battle range.

**Kiểm tra:**
- `npx tsc -p client\tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/screens/character/status/CharacterStatus.api.ts`
- `docs/player-character-reconstruction/01-implementation-plan-csharp.md`
- `CHANGELOG.md`

---

## 2026-04-25 (L)

### Chuẩn hóa công thức 6 chỉ số nhân vật theo Java `jq/js/jr`

**Sửa:**
- Đọc lại Java client để chốt công thức status gốc:
  - `jq.java` = Hỏa/Cường Lực.
  - `js.java` = Lôi/Thân Pháp.
  - `jr.java` = Thủy/Nội Lực.
  - `com.mg.sq.a.a(lh)` map `jz.a/b/c/d/e/f/g` vào `lh.r/x/y/z/A/B/C`.
  - `da.java` xác nhận label UI: Tấn Công, Chính xác, Sinh lực, P.Thủ, Né Tránh, Chí Mạng.
- `CombatStats` tách `MinDamage` / `MaxDamage` thay vì một `TanCong`.
- `StatCalculator` port đúng công thức Java:
  - Hỏa: HP `TheLuc * 6`, damage `CuongLuc .. CuongLuc * 120 / 100`, dodge `ThanPhap * 2`, hit `ThanPhap * 3`.
  - Lôi: HP `TheLuc * 4`, damage `(ThanPhap * 80 + CuongLuc * 16) / 100 .. ThanPhap + CuongLuc / 5`, dodge `ThanPhap * 15 / 10`, hit `ThanPhap * 3`.
  - Thủy: HP `TheLuc * 5`, damage `NoiLuc * 130 / 100 .. NoiLuc * 150 / 100`, dodge `ThanPhap * 3`, hit `ThanPhap * 2`.
- `PlayerStatPipeline` bỏ off-element soft cap khỏi tầng status/derived stat; status dùng tổng stat thật theo Java bridge.
- Giữ ghi chú rõ: mọi balance soft-cap/hybrid nếu cần chỉ áp ở battle/skill/resource, không làm sai số status Java.
- Cập nhật `08-level-stat-exp-and-element-balance.md`:
  - Chốt “Java status là nguồn truth tuyệt đối”.
  - Ghi rõ Thủy né cao hơn Lôi là đúng Java, không sửa theo cảm tính balance.

**Kiểm tra:**
- Lần đầu `dotnet build Twelve.sln` bị DLL lock bởi `Twelve.Server (PID 1912)`.
- Đã chạy `taskkill /F /PID 1912 && dotnet build Twelve.sln` → thành công, 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Core/Entities/CombatStats.cs`
- `server/Twelve.Core/GameLogic/StatCalculator.cs`
- `server/Twelve.Core/GameLogic/PlayerStatPipeline.cs`
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `CHANGELOG.md`

---

## 2026-04-25 (K)

### Đồng bộ nút tick menu và mũi tên Calendar

**Sửa:**
- Bổ sung cơ chế `selectSignal` cho `PopupMenu`: khi menu đang mở, softkey trái/tick sẽ chọn item đang focus ở cấp menu sâu nhất, bám hành vi phím mềm Java cũ.
- Nối cơ chế tick chọn menu vào:
  - màn status nhân vật;
  - màn map Hòa Lư.
- Thay mũi tên text `<` / `>` trong `CalendarPicker` bằng asset `arrowfocus1.png`, cùng hướng xoay giống selector Giới Tính/Khuôn Mặt/Kiểu Tóc ở màn tạo nhân vật.

**Kiểm tra:**
- `npx --prefix client tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/components/controls/PopupMenu/PopupMenu.tsx`
- `client/src/screens/character/status/CharacterStatusScreen.tsx`
- `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`
- `client/src/components/controls/CalendarPicker/CalendarPicker.tsx`
- `client/src/components/controls/CalendarPicker/CalendarPicker.styles.ts`

---

## 2026-04-25 (J)

### Xóa toàn bộ console runtime trong client/src

**Sửa:**
- Xóa toàn bộ `console.log`, `console.warn`, `console.error`, `console.debug`, `console.info` trong `client/src` để không còn spam console khi chạy web/dev.
- Các nhóm đã dọn:
  - `SocketClient`: log kết nối WebSocket, packet/CMD, login/register/create character response.
  - `SessionStorage` native/web: log save/load/clear session, lastScreen, lỗi storage.
  - `RegisterScreen`: log flow đăng ký và validation.
  - `LoginScreen`: đã dọn ở mục trước.
  - `MapSelectionScreen`: log map bị khóa.
  - `MainScreen`: log move ack.
  - `usePaletteSwappedImage`: warning palette swap fallback.
- Các lỗi runtime không cần hiển thị console sẽ fallback im lặng; UI vẫn dùng banner/flow hiện có để báo lỗi nghiệp vụ cho người chơi.

**Kiểm tra:**
- Rà `console.(log|warn|error|debug|info)` trong `client/src` → không còn kết quả.
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/network/SocketClient.ts`
- `client/src/screens/auth/register/RegisterScreen.tsx`
- `client/src/storage/SessionStorage.web.ts`
- `client/src/storage/SessionStorage.ts`
- `client/src/screens/map/selection/MapSelectionScreen.tsx`
- `client/src/screens/main/home/MainScreen.tsx`
- `client/src/screens/character/create/usePaletteSwappedImage.ts`

---

## 2026-04-25 (I)

### Xóa console debug khỏi màn đăng nhập

**Sửa:**
- Xóa toàn bộ `console.log` debug trong `client/src/screens/auth/login/LoginScreen.tsx`:
  - log mount/unmount auth listener;
  - log `authSuccess`;
  - log `authFailed`;
  - log `characterRequired`;
  - log `handleLogin`;
  - log gửi CMD login.
- Giữ nguyên flow xử lý login, banner lỗi, loading state và emit `authSuccessWithUser`.

**Kiểm tra:**
- Rà `console.(log|warn|error|debug|info)` trong `client/src/screens/auth/login` → không còn kết quả.
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/screens/auth/login/LoginScreen.tsx`

---

## 2026-04-25 (H)

### Áp font/theme cho PopupMenu và các text hệ thống còn hardcode

**Sửa:**
- Kiểm tra menu trong ảnh đăng nhập:
  - `PopupMenu` đã dùng `GameTextStyles.menuText` / `menuTextSelected` cho label.
  - Bổ sung style riêng cho mũi tên submenu `>` để không còn inline hardcode font weight/color.
- Sửa các điểm còn hardcode `fontFamily` ngoài theme:
  - `client/src/components/controls/CalendarPicker/CalendarPicker.styles.ts`
    - Bỏ import `Platform`.
    - Áp `GameTextStyles.dialogText` cho group label, year/month button text, arrow icon, day text.
  - `client/src/components/game/MapHUD/MapHUD.tsx`
    - Bỏ font monospace hardcode theo `Platform`.
    - Áp `GameTextStyles.numberSmall` cho tên khu vực/map HUD.
- Chạy rà soát `fontFamily:` trong `client/src`: hiện chỉ còn nằm trong `client/src/theme/GameTheme.ts`, tức font contract tập trung một chỗ.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/components/controls/PopupMenu/PopupMenu.styles.ts`
- `client/src/components/controls/PopupMenu/PopupMenu.tsx`
- `client/src/components/controls/CalendarPicker/CalendarPicker.styles.ts`
- `client/src/components/game/MapHUD/MapHUD.tsx`

---

## 2026-04-25 (G)

### Áp font family/theme cho màn đăng ký

**Sửa:**
- Kiểm tra màn đăng nhập:
  - `client/src/screens/auth/login/LoginScreen.styles.ts` đã dùng `GameTextStyles.dialogText` cho input và checkbox.
- Sửa màn đăng ký:
  - `client/src/screens/auth/register/RegisterScreen.styles.ts`
  - Import `GameTextStyles` từ `client/src/theme/GameTheme`.
  - Áp `GameTextStyles.dialogText` cho header, label, input, radio text.
  - Áp `GameTextStyles.uiLabelStrong` cho banner thông báo.
- Mục tiêu là đồng bộ font Java-like fallback từ `GameFonts.dialog` / `GameFonts.ui` thay vì hardcode trực tiếp `serif` / `sans-serif` rải rác trong màn đăng ký.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/screens/auth/register/RegisterScreen.styles.ts`

---

## 2026-04-25 (F)

### Hiển thị dialog “Vui lòng chờ...” cho mọi HTTP API call

**Sửa:**
- Tìm thấy dialog chờ hiện có tại:
  - `client/src/components/dialogs/LoadingDialog/LoadingDialog.tsx`
- Tích hợp dialog chờ cấp app trong `client/App.tsx`.
- Bọc `globalThis.fetch` ở tầng root để mọi request HTTP tới API server `http://localhost:5102` tự động:
  - tăng counter khi bắt đầu request;
  - giảm counter trong `finally`;
  - hiện `<LoadingDialog message="Vui lòng chờ..." />` khi còn ít nhất 1 request đang chạy.
- Cách này phủ được các API hiện đang dùng `fetch`:
  - `/player/runtime...`
  - `/battle/...`
  - `/pvp/...`
  - `/map/monster-roster...`
- Không ảnh hưởng WebSocket packet game realtime vì WebSocket không đi qua `fetch`.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/App.tsx`

---

## 2026-04-25 (E)

### Sửa điểm tiềm năng ban đầu và áp dụng font/theme từ Login

**Sửa:**
- Sửa nhân vật level 1 mới tạo không còn có sẵn `5` điểm tiềm năng:
  - `CreateCharacterHandler.cs`: `player.FreePoints = 0`.
  - `Player.cs`: default `FreePoints = 0`.
- Giữ rule level-up: `PlayerLevelProgression` vẫn cộng `+5` điểm tiềm năng mỗi level.
- Cập nhật spec `08-level-stat-exp-and-element-balance.md`:
  - `InitialFreePoints = 0`.
  - `InitialFreePointsAtLv1 = 0`.
  - Tổng điểm tới level 250 vẫn là `(250 - 1) * 5 = 1245`.
- Áp dụng `GameTextStyles.dialogText` vào màn đăng nhập để font/style dùng chung bắt đầu từ Login, không chỉ các màn sau.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit && dotnet build Twelve.sln` → thành công, 0 Error.

**File đã sửa:**
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `server/Twelve.Core/Entities/Player.cs`
- `client/src/screens/auth/login/LoginScreen.styles.ts`
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`

---

## 2026-04-25 (D)

### Chuẩn hóa font/style toàn hệ thống — GameTheme

**Mục tiêu:**
- Không hardcode font rải rác trong từng file style.
- Tạo contract style/font dùng chung toàn client, các màn riêng chỉ override layout/màu khi cần.
- Ghi nhận font atlas Java cũ hiện có:
  - `client/assets/login/04_font_candidate/_blackfont.png`
  - `client/assets/login/04_font_candidate/_fontcap.png`
  - `client/assets/ui/04_tabs_and_numbers/tinynumber.png`

**Sửa:**
- Tạo `client/src/theme/GameFonts.ts`
  - Chứa font fallback Java-like cho RN Text.
  - Ghi chú rõ bitmap PNG atlas chưa thể dùng bằng `fontFamily` trực tiếp, cần renderer riêng về sau.
- Tạo `client/src/theme/GameTheme.ts`
  - Chứa `GameColors`.
  - Chứa `GameTextStyles` dùng chung: `uiLabel`, `uiLabelStrong`, `uiValue`, `menuText`, `softkeyText`, `numberTiny`, `numberSmall`, `numberValue`, `dialogText`.
- Refactor các style đang sửa sang dùng theme chung:
  - `SoftkeyBar.styles.ts`
  - `PopupMenu.styles.ts`
  - `CreateCharacterScreen.styles.ts`
  - `CharacterStatusScreen.styles.ts`
- Áp dụng hướng số bitmap/tinynumber ở mức fallback hiện tại qua `GameTextStyles.number*`; renderer crop atlas thật sẽ làm sau nếu cần pixel-perfect Java.

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/theme/GameFonts.ts`
- `client/src/theme/GameTheme.ts`
- `client/src/components/controls/SoftkeyBar/SoftkeyBar.styles.ts`
- `client/src/components/controls/PopupMenu/PopupMenu.styles.ts`
- `client/src/screens/character/create/CreateCharacterScreen.styles.ts`
- `client/src/screens/character/status/CharacterStatusScreen.styles.ts`

---

## 2026-04-25 (C)

### Sửa focus mặc định màn tạo nhân vật — CreateCharacterScreen

**Lỗi:**
- Khi mở màn tạo nhân vật, ô đang được chọn mặc định là `Khuôn Mặt`.
- Theo thứ tự UI Java-like và ảnh test, focus đầu tiên phải nằm ở `Giới Tính`.

**Sửa:**
- Đổi state khởi tạo:
  - Từ `useState<SelectorKey>('face')`
  - Thành `useState<SelectorKey>('gender')`

**Kiểm tra:**
- `npx tsc -p client/tsconfig.json --noEmit` → thành công.

**File đã sửa:**
- `client/src/screens/character/create/CreateCharacterScreen.tsx`

---

## 2026-04-25 (B)

### Balance v1.1 — Tạo nhân vật + Stat Pipeline + Off-Element Soft Cap + MaxMP/MaxPower

**Mục tiêu:**
- Áp dụng balance spec (08-level-stat-exp-and-element-balance.md) vào code server thực tế.
- Phần tạo nhân vật là nền sống còn của game: công bằng, không phạt chỉ số gốc từ level 1.
- PlayerStatPipeline là nguồn truth duy nhất cho MaxHP/MaxMP/MaxPower/derived stats.

**Sửa:**

#### CreateCharacterHandler.cs
- Bỏ stat khởi tạo cũ theo element (Hỏa 15/10/5/10, Lôi 5/15/5/10, Thủy 5/10/15/10).
- Chuyển sang base stats công bằng `(10, 10, 10, 10)` mọi hệ.
- Lý do: không có bằng chứng server Java cũ cho việc trừ dump-stat khi tạo nhân vật; stat gốc khởi tạo công bằng để người chơi định hướng build từ các lần lên cấp.
- Bỏ `hpMultiplier` cũ tính MaxHp tay; dùng `PlayerStatPipeline.RecalculateAndApply(player)` là nguồn truth duy nhất.
- Set `player.Mp = player.MaxMp` sau pipeline để MP ban đầu đúng với MaxMp được tính.
- `Element` vẫn được set chuẩn để xác định MainElement/affinity/skill tree/khắc hệ.

#### PlayerStatPipeline.cs
- Thêm **off-element offense soft cap** (Balance v1.1):
  - Đúng hệ: TotalStat 100%.
  - Sai hệ trước mốc 120: 70%.
  - Sai hệ sau mốc 120: 35%.
  - Áp dụng cho `CuongLuc`, `ThanPhap`, `NoiLuc` khi truyền vào StatCalculator.
  - `TheLuc` và resource/utility không áp dụng soft cap.
- Thêm `CalculateMaxMp(level, totalNoiLuc)`:
  - `MaxMp = 40 + level * 6 + NoiLucTotal * 8`
  - Dùng TotalNoiLuc (không qua soft cap) vì MaxMP là utility, mặc đồ/cộng Nội Lực luôn có tác dụng.
- Thêm `MaxPower = 100` (fixed, reset theo trận).
- Mở rộng `PlayerDerivedStats` record thêm `MaxMp`, `MaxPower`.
- Mở rộng `Apply()` set `player.MaxMp`, `player.MaxPower`, clamp `player.Mp`, `player.Power`.

**Build:** `dotnet build Twelve.sln` → 0 Warning, 0 Error.

**File đã sửa:**
- `server/Twelve.Application/Handlers/CreateCharacterHandler.cs`
- `server/Twelve.Core/GameLogic/PlayerStatPipeline.cs`

**Trạng thái tiếp theo (bắt đầu chat mới):**
- `AllocateStatHandler.cs` cần kiểm tra còn dùng `StatCalculator.RecalculateAndApply` cũ không → nếu có phải chuyển sang `PlayerStatPipeline.RecalculateAndApply`.
- `BattleResultService.cs` có dùng `PlayerStatPipeline.RecalculateAndApply` cần kiểm tra.
- Client `PlayerRuntimeSnapshot` cần nhận MaxMp/MaxPower đúng từ server để hiển thị bar.
- Kiểm tra combat formula trong `StatCalculator.cs` (jq/js/jr) xem MaxHp có cần điều chỉnh theo level hay không (hiện tại chỉ `vit * factor`, chưa có `level * 18`).

---

## 2026-04-25

### Ghi spec cân bằng Level 250 / Stat / EXP / Khắc Hệ — Player/Character

**Mục tiêu:**
- Tách riêng tài liệu cực quan trọng cho công thức cân bằng khi không có server Java cũ.
- Chốt không có điểm tiềm năng bonus; mỗi level chỉ cộng `+5` điểm.
- Đưa EXP curve mới để game cày cuốc lâu hơn tới max level 250.
- Thiết kế cân bằng 3 hướng build `Cường Lực / Nội Lực / Thân Pháp` và vòng khắc hệ.

**Sửa:**
- Tạo `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`.
- Cập nhật index `docs/player-character-reconstruction/README.md`.
- Cập nhật `PLAYER_CHARACTER_RECONSTRUCTION.md` để trỏ tới spec mới và ghi rõ code hiện vẫn còn curve EXP cũ trước khi port công thức mới.
- Chốt khắc hệ v1: `Cường Lực > Thân Pháp > Nội Lực > Cường Lực`, multiplier 112%/100%/92%, resistance cap 20%.
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
- Ghi rõ khắc hệ áp dụng cho cả PvE/train monster, không chỉ PvP; player đánh monster và monster đánh player đều dùng chung công thức khắc hệ để battle server thống nhất.

**File đã sửa:**
- `docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md`
- `docs/player-character-reconstruction/README.md`
- `PLAYER_CHARACTER_RECONSTRUCTION.md`
- `CHANGELOG.md`

### Bổ sung rule Cường Lực/Nội Lực cho tài nguyên battle — Player/Character + Battle
- Làm rõ `Cường Lực / Nội Lực / Thân Pháp` không tự tăng trực tiếp theo level nếu chưa có bằng chứng Java; level chỉ cấp điểm tiềm năng để người chơi phân bổ.
- Bám Java cho tốc độ actor ngoài map: `kl.java — kl.b(lh)` dùng `lh.G` level để tính `i = 4 + G / 10`, cap `9`.
- Thêm tầng remake có ghi nguồn suy luận: `Thân Pháp` ảnh hưởng tốc độ ngang và lực nhảy với hệ số nhỏ/cap để không phá collision map mới.

**Sửa:**
- Thêm `MapMovementCalculator` trên server, giữ integer division đúng Java cho speed theo level.
- Runtime snapshot trả thêm `mapMoveSpeed`, `mapJumpSpeed`.
- Client merge movement stats vào `CharacterAppearance.mapMovement`.
- `HoaLuMapScreen` truyền speed/jumpSpeed theo runtime player thật xuống `CharacterController`.
- `CharacterController` hỗ trợ `jumpSpeed` động thay vì cố định toàn map.
- Cập nhật tài liệu `docs/player-character-reconstruction/01-implementation-plan-csharp.md` với nguồn Java, công thức và nhật ký chỉnh sửa.

**File đã sửa:**
- `server/Twelve.Core/GameLogic/MapMovementCalculator.cs`
- `server/Twelve.Core/Players/PlayerRuntimeContracts.cs`
- `server/Twelve.Application/Players/PlayerRuntimeService.cs`
- `client/src/screens/character/status/CharacterStatus.api.ts`
- `client/src/screens/character/shared/characterAppearance.ts`
- `client/src/engine/character/character.types.ts`
- `client/src/engine/character/CharacterController.tsx`
- `client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx`
- `docs/player-character-reconstruction/01-implementation-plan-csharp.md`

### Sửa lỗi seed PRNG refill/cascade PvP không đồng nhất — `BattleScreen.tsx`

**Lỗi:**
- Hai client PvP có thể bắt đầu từ cùng board canonical nhưng sau swap/cascade lại sinh refill khác nhau, làm bàn cờ lệch giữa active player và passive observer.
- Passive observer sau đó bị server snapshot kéo giật về board canonical, gây cảm giác PvP không realtime và board bị snap/flicker.

**Nguyên nhân gốc:**
- `boardEngineRef` trước đây khởi tạo bằng `createJavaBoardEngine()` không truyền seed, nên mỗi máy dùng seed local khác nhau (`Date.now()`/`Math.random()`).
- Theo `BATTLE_SYSTEM_RECONSTRUCTION.md`, refill local phải deterministic theo seed chung của trận; nếu không, cùng một swap sẽ rơi/sinh ngọc khác nhau giữa 2 client.

**Sửa:**
- Thêm `deriveBattleSeed(sessionId)` tại `BattleScreen.tsx` để tạo seed deterministic chung từ `monsterBootstrap.sessionId`.
- Khởi tạo lại `boardEngineRef.current = createJavaBoardEngine(battleSeed)` khi reset trận.
- Fallback `makeBoard(...)` cũng dùng cùng `battleSeed`, tránh sinh board local khác nhau nếu bootstrap thiếu board.
- Giảm polling snapshot PvP từ 850ms xuống 150ms để passive observer nhận action nhanh hơn trong kiến trúc HTTP polling hiện tại.

**File đã sửa:**
- `client/src/screens/battle/BattleScreen.tsx`

## 2026-04-25

### Sửa lỗi PvP Battle Desync & Animation Loop — BattleScreen.tsx (Client)

**Lỗi:**
- Hình ảnh/animtion trong trận PvP bị lặp vô tận (sparkle FX loop không dừng).
- Bàn cờ bị lỗi hiển thị "background lạ" trên các ô ngọc sau khi đổi lượt.
- Đối thủ không thấy bàn cờ cập nhật ngay hoặc bị giật lùi trạng thái.

**Nguyên nhân gốc:**
1. **Poll loop overwrite:** Trong PvP, client liên tục poll snapshot ngay cả khi đang là lượt của mình. Khi active player đang chạy local cascade/FX, việc nhận snapshot cũ từ server làm board bị reset về trạng thái trước đó. Vòng lặp reset board → trigger lại hook kiểm tra → cascade chạy lại gây lỗi lặp animation.
2. **Session-sync block sai:** Guard `if (isPvpBattle && turn === 'monster') return;` chặn active player không cho sync state *sau khi* họ vừa cascade xong (lúc này turn vừa chuyển sang 'monster'). Hậu quả là server không bao giờ nhận được board hoàn chỉnh sau cascade, nên passive player cũng chỉ nhận được board cũ.
3. **Persisted overlays:** Khi `applySessionSnapshot` thay thế bàn cờ, các state như `explodeFrames`, `fireSwordMarkBaseGems` không được clear, dẫn đến FX và mark cũ bám lên ngọc mới tạo ra ô "background lạ".

**Sửa:**
1. **Sửa Poll Guard:** Bỏ ngoại lệ PvP. Client chỉ poll snapshot khi `turn === 'monster'` (đang chờ lượt đối thủ). Active player (`turn === 'player'`) tuyệt đối không poll để tránh ghi đè local board.
2. **Sửa Session-sync:** Bỏ guard block turn 'monster'. Active player sau khi cascade xong, `turn` chuyển thành 'monster', sẽ push final board state lên server đúng 1 lần.
3. **Clear visual states & bảo vệ bằng `turnSeq`:** Trong `applySessionSnapshot`, chỉ apply khi `snapshot.turnSeq > local pvpTurnSeq` để bỏ qua snapshot cũ. Khi apply board mới từ server, luôn clear `explodeFrames`, `fireSwordMarkBaseGems`, `fireSwordMarkTriggers`, `selected`, `hintMove` để dọn sạch visual cũ.

**File đã sửa:**
- `client/src/screens/battle/BattleScreen.tsx`

### Đồng bộ bàn cờ khởi đầu (Board Canonicalization) trong PvP — `BattleSessionSyncService.cs`, `PvpArenaService.cs`

**Lỗi:**
- Hai người chơi khi vào trận PvP nhìn thấy 2 bàn cờ (Initial Board) hoàn toàn khác nhau, dẫn đến desync. Cứ bên này swap thì báo Invalid Swap hoặc match không đúng logic bên kia.

**Nguyên nhân gốc:**
1. Khi tạo PvP Shadow Session, `PvpArenaService` gọi `_battleBoardService.CreateInitialBoard()` nhưng gọi 2 lần riêng biệt cho 2 người chơi (Host và Shadow/Target), tạo ra 2 object board độc lập với random seed khác nhau.
2. Mặc dù sau đó đã phát hiện và dùng `sharedBoard`, local client lại không push back state *canonical* sau khi resolve board về server, làm các action polling bị miss match. Server khi nhận `/battle/session-sync` không bump `TurnSeq` nếu board thay đổi do cascade local của người đi trước.

**Sửa:**
1. Server-side authoritative: Cập nhật `BattleSessionSyncService.cs` so sánh content board/bars thay vì rely vào object reference. Nếu có thay đổi (vd: sau khi local resolve board rơi ngọc), tự động bump `TurnSeq`. 
2. Chặn việc passive player drop packet do `turnSeq` cũ khi board thực sự có canonical thay đổi từ active player đẩy lên server.

**File đã sửa:**
- `server/Twelve.Application/Battle/BattleSessionSyncService.cs`

### Ngăn chặn AI tự can thiệp trong trận đấu PvP — `useBattleAI.ts`, `useBattleMonsterTurn.ts`

**Lỗi:**
- Trong trận PvP 2 người chơi thực, khi 1 bên swap xong (lượt chuyển sang 'monster' / chờ đối thủ), các hook điều khiển quái vật và AI cũ lại tự động được kích hoạt, dẫn đến việc AI giành lượt tự đi nước của người chơi còn lại.

**Sửa:**
- Đưa cờ `isPvpBattle` vào `useBattleAI` và `useBattleMonsterTurn`. 
- Bổ sung các guard `if (isPvpBattle) return;` vào đầu các useEffect điều khiển monster turn và AI think-loop.
- Tại `BattleScreen.tsx`, ép cứng cấu hình `aiLevel` thành `null` nếu là trận PvP để đảm bảo AI sẽ không bao giờ được thiết lập thành `linh_canh` hay bất kỳ level nào.

### Sửa lỗi animation observer và visual special piece trong trận PvP

**Lỗi:**
- Người quan sát (passive observer) khi nhận update bảng cờ PvP thấy giật/chậm và bị stuck loop khi có lượt rơi ngọc.
- Khi người chơi tạo ra special piece (sau match 5+ tạo type 4), các mảnh ngọc type 4 bị hiển thị sai asset, trở thành biểu tượng rồng/phượng che lấp bàn cờ gây cảm giác "hiển thị background lạ".

**Sửa:**
- Đổi cách apply snapshot khi nhận board từ đối thủ trong `useBattleMatchFlow.ts` và `BattleScreen.tsx`: dùng hàm `animateValidSwap` (đã được refactor tách ra khỏi UI flow) để tái hiện lại timing animation của một swap thay vì ghi đè đột ngột.
- Xóa bỏ việc áp dụng `hiddendragon` và `hiddenphoenix` sai bản chất cho type 4 trong `BattleScreen.components.tsx` và `BattleScreen.shared.ts`.
- Phục dựng chuẩn Java: node `20..25` (type 4) chỉ hiển thị asset base `chess0..5` kèm theo frame animation từ `nd.java`, thay vì dùng UI ornament tĩnh.
