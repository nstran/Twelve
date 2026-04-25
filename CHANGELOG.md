# CHANGELOG

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
