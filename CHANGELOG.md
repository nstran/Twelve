# CHANGELOG

## 2026-05-03

### [EQUIPMENT] Rà Java client và cập nhật plan khôi phục trang bị

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` dựa trên audit Java client các lớp `ll/lb/ky/mb/hg/fw/dc/id/gp/cz/go/com.mg.sq.a`.
- Bổ sung full field model `ll` gồm các field trước đó còn thiếu (`b/l/o/s/u`) và mapping packet tags cho equipment full/minimal parse.
- Bổ sung `lb` 15 stat fields, mapping tag `118-221`, rule cộng stat nhân vật và lưu ý decompile bug tag `118`.
- Ghi lại inventory/equip rules từ `go.java` và `cz.java`: capacity default `50`, full-check item type `7`, one item per slot `ll.e`, boots `e=3` không rebuild visual.
- Bổ sung comparator `gp.java`, equipment icon/frame convention từ `mb.java`, detail/tooltip/cell renderer/upgrade panel flow từ `hg/fw/dc/id`.
- Rà bổ sung `hh/hl/ia/lq/ks`: màn hình hành trang, equip/unequip sync key array, menu mặc/sửa/nâng cấp/rao bán/vứt bỏ, repair bằng búa, shop mặc thử/giỏ hàng/mua và network sender payloads.
- Rà tiếp `da/ho` sau search toàn cục: status panel chỉ cộng stat trang bị còn trong `go.l` và `p != 0`; combine panel có flow riêng với sender action `0/1`, final arrays và kết quả `"Kết hợp thành công/thất bại"`.
- Rà bổ sung exact callback/result packet shape trong `ky.java` cho command `96/97/99/100/112`: request-upgrade/combine response, modified-upgrade/combine result, equip-change response và các tag chính `83/186/187/188/114/106/132/157/175/1`.
- Rà bổ sung usage stat đặc biệt `lb.j..o`: `lb.n` (`AttackPercent`) chắc chắn cộng theo `baseAttack * percent / 100`; `lb.o` (`HpPercent`) và `lb.j/k/l/m` hiện chỉ chắc chắn parse/display, chưa tự ý gán công thức combat/status.
- Search phụ `hn/hq` xác nhận các màn market/trade có thể wrap `ll` qua `lq` nhưng không đổi core model/rule equipment.
- Rà bổ sung `gx.java`: dialog confirm giỏ hàng mua nhiều equipment dùng `lq.e -> ll`, icon `mb.a(ll)+98`, stat strings `com.mg.sq.a.a(ll)`, rank color `ll.a(rank)` và tổng tiền từ `lq.d`.
- Rà bổ sung `of.java`: panel giao dịch add/remove/reclaim equipment bằng `dc`, icon convention cũ, key `ll.c`, log cập nhật/lấy lại và danh sách kiểm tra giao dịch; mapping sender trade `ks.k/l/m/n` giữ riêng khỏi equip/upgrade/combine.
- Cập nhật coverage estimate trong tài liệu: khoảng `99.5%` phần equipment client-side core đã gom; phần còn lại chủ yếu là combat usage của stat đặc biệt, asset/meta đối chiếu bằng server dump, UI thương mại phụ ngoài core và byte-perfect serializer test với client thật.
- Dọn phần thừa không phải bằng chứng Java source: bỏ bảng `.agent/skills/` và đánh dấu `110xxx-140xxx` là mixed/needs re-audit thay vì exclude cứng.

### [EQUIPMENT] Bổ sung quyết định server-side từ gameplay memory

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` thêm section server-side reconstruction do không có Java server gốc; nguồn là gameplay memory/user-provided evidence ngày `2026-05-03` kết hợp Java client equipment parser/UI đã audit.
- Chốt vòng đời equipment:
  - Quái rơi trang bị ra đất dạng hộp.
  - Khi nhặt mở dialog `Nhặt/Bỏ qua` kiểu `hg.java`.
  - Shop/nhiệm vụ/event có thể cấp trang bị.
  - Drop phân cấp theo level/map/quái.
  - Equipment instance roll chỉ số random trong range từ template.
- Chốt validation mặc đồ ở server:
  - Sai giới tính không mặc được.
  - Chưa đủ level không mặc được.
  - Không có giới hạn class/phái/hệ ngoài level + gender theo evidence hiện tại.
  - Giao dịch phải tháo đồ trước, không trade trực tiếp đồ đang mặc.
- Chốt durability/repair:
  - Thắng trận trừ `1` độ bền trên đồ đang mặc.
  - Thua trận trừ `3` độ bền.
  - Độ bền về `0` chỉ broken, không mất đồ.
  - Broken equipment không cộng stat/effect.
  - Durability/current durability và max durability được lưu DB riêng theo từng equipment instance, không chỉ là default/template chung.
  - Mỗi món đồ có thể có `MaxDurability` riêng; nếu template/server generation không chỉ định thì fallback default ban đầu là `30`.
  - Upgrade/enhancement cũng tăng độ bền tối đa của chính equipment instance đó; công thức tăng cụ thể sẽ cấu hình theo tier upgrade khi implement.
  - Equipment durability `p == 0` vẫn mặc được và vẫn nằm ở slot trang bị, nhưng toàn bộ stat/effect không còn tác dụng cho status/combat.
  - Repair chỉ dùng item búa sửa đồ: mỗi lần dùng `1` búa, hồi đầy durability về `MaxDurability`, không mất Quan/KEN.
  - Có đồ không thể sửa nên cần field `IsRepairable`.
- Chốt economy/trade:
  - Shop equipment dùng đơn vị remake `Quan` thay cho KEN gốc.
  - Equipment không bán lại NPC.
  - Có market/rao bán và thuế, công thức pending.
- Ghi rõ pending:
  - Combine pending.
  - Original Java server item IDs/names cho đá nâng cấp, đá may mắn và đồ bảo hộ vẫn chưa biết; hiện dùng taxonomy remake trong tài liệu.
  - Tradeable visual, template dump, item sample, asset band `12xxxx-14xxxx`, premium/event set sẽ chờ dữ liệu/ảnh người dùng cung cấp thêm.
- Cập nhật `.clinerules` thêm rule cấm báo Task Completed khi chưa thật sự sửa file/cập nhật tài liệu/kiểm tra kết quả.

### [EQUIPMENT] Chốt upgrade hard-mode và công thức cường hóa

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` section `13.5` với policy nâng cấp remake đã được user duyệt:
  - Max enhancement `+15`.
  - Success roll dùng basis point `1..10000`.
  - Tỉ lệ hard-mode: `+1 90%`, `+2 80%`, `+3 70%`, `+4 60%`, `+5 45%`, `+6 35%`, `+7 25%`, `+8 18%`, `+9 12%`, `+10 5%`, `+11 4%`, `+12 3%`, `+13 2%`, `+14 1.5%`, `+15 1%`.
  - Cap sau luck/event: `+1..+4 95%`, `+5..+9 50%`, `+10..+12 10%`, `+13..+15 5%`.
  - Fail policy: tier thấp mất phí/nguyên liệu, tier giữa tụt cấp, tier cao có thể vỡ/mất trang bị nếu không có bảo hộ.
  - Destroy chance khi đã fail từ `+10..+15`: `10%`, `15%`, `20%`, `28%`, `35%`, `45%`.
  - Protection items: bùa chống tụt, bùa chống vỡ, bảo hộ hoàn hảo.
  - Luck items: `+1%`, `+2%`, `+3%`, `+5%` trước khi clamp bởi cap.
- Ghi rõ trạng thái nguyên liệu:
  - Java client chỉ chứng minh upgrade payload có equipment keys, item IDs, item quantities và fee.
  - Chưa có server gốc/template dump nên **chưa biết ID/tên nguyên liệu original**.
  - Tạm dùng taxonomy remake: `UpgradeStoneBasic`, `UpgradeStoneIntermediate`, `UpgradeStoneAdvanced`, `UpgradeStoneRefined`, `UpgradeStoneDivine`.
  - Khi có item dump/screenshot thật thì alias hoặc thay thế taxonomy này bằng ID gốc.
- Chốt công thức phí remake: `feeQuan = baseByRank * targetLevel^2`.
- Chốt công thức tăng stat: `enhancedFlatStat = baseFlatStat + floor(baseFlatStat * bonusPercent[level] / 100)`.
- Policy an toàn ban đầu: chỉ tăng flat stats; percent/special stats giữ nguyên tới khi phục dựng combat/stat đầy đủ hơn.

### [EQUIPMENT] Chốt policy pre-code cho repair, upgrade, trade, shop và combine

- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` sau vòng xác nhận gameplay cuối trước khi code:
  - Búa sửa đồ chỉ có 1 loại, icon `client/assets/equipment/09_ui_icons/30099.png`, mỗi lần repair dùng `1` búa và hồi đầy durability.
  - Equipment mới rơi/mua/được cấp luôn full durability; `CurrentDurability/MaxDurability` lưu riêng theo từng instance.
  - Một số item Luyện Ngục không sửa được nên model cần `IsRepairable` và optional `RepairBlockReason`.
  - Thêm `IsUpgradeable`; đồ broken vẫn có thể upgrade nếu field này cho phép, nhưng đồ đang mặc phải tháo ra túi trước khi upgrade.
  - Upgrade thành công giữ nguyên current durability, chỉ tăng max durability nếu config tier có; upgrade fail không trừ durability.
  - Phase đầu upgrade chỉ consume material/optional item, chưa consume Quan fee; fee formula giữ dạng reserved/off-by-config.
  - Upgrade destroy xóa hẳn equipment instance và báo mất đồ; bùa chống vỡ giữ đồ nhưng vẫn có thể tụt cấp; bảo hộ hoàn hảo giữ nguyên cấp và không vỡ khi fail.
  - Shop equipment vẫn roll random stat trong template/shop offer range.
  - Equipment default tradeable `t=1`; trade vẫn yêu cầu tháo đồ trước.
  - Gender mapping giữ đúng Java `0=Nam`, `1=Nữ`, `2=Cả hai`.
  - Slot cánh/event khi thiếu data chỉ lưu DB + inventory icon, chưa cộng stat/chưa render.
  - DB/server design tách `EquipmentTemplates` và `PlayerEquipment`; inventory default capacity `50`.
  - Server key dùng sortable unique string/ULID-style hoặc tương đương.
  - Drop pool ngoài equipment có thể gồm HP/MP item, trứng và material; trứng có thể mở/đập ra equipment theo config.
  - Combine không làm placeholder; sẽ implement bằng `CombineRecipes` server config/table với input filters, material, output template/pool, success rate và failure policy.

### [EQUIPMENT] Gộp lại cây asset equipment theo folder slot lớn

- Gộp cấu trúc asset `client/assets/equipment/` từ các folder audit sâu (`01_default_overlays/`, `02_armor_e0/`, `03_weapon_e1/`, `04_helmet_e2/`, `07_accessory_e5_e7_e8/`, `08_premium_sets/`, `09_ui_icons/`) sang folder slot lớn:
  - `default/`, `armor/`, `weapon/`, `helmet/`, `accessory/`, `premium/`, `ui/`.
- Tạo/cập nhật manifest:
  - `client/assets/equipment/index.csv`
  - `client/assets/equipment/equipment_manifest.json`
- Cập nhật `client/assets/equipment/README.md` với layout mới, convention `band + 98`, rule resolver và số lượng asset sau gộp.
- Cập nhật `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` section `9` để tài liệu reconstruction khớp với cây asset hiện tại.
- Số lượng PNG sau gộp: `974`:
  - `default`: `32`
  - `armor`: `363`
  - `weapon`: `220`
  - `helmet`: `187`
  - `accessory`: `56`
  - `premium`: `110`
  - `ui`: `6`
- Giữ rule quan trọng: folder vật lý chỉ là asset lookup hint; gameplay slot authoritative vẫn là `ll.e` từ packet/model Java.

## 2026-04-30

### [MAP] Tăng reachability jump Hoa Lư theo policy reconstructed

- `javaMapMovement` thêm `RECONSTRUCTED_JUMP_HEIGHT_MULTIPLIER = 1.25` để tăng initial jump/cap trên nền công thức Java `kl.a = min(16, 11 + level / 10)`.
- `createJavaMapActorRuntime(...)` và `JavaCompatibleCharacterController` đồng bộ cùng multiplier khi khởi tạo runtime và khi level đổi, tránh quay về cap Java thấp giữa phiên.
- Giữ nguyên state/tick Java-compatible: rising `t.b -= s; s--`, falling `t.b += s; s = min(a, s + 2)`; chỉ tuning reachability cho topology Hoa Lư hiện chưa recover exact `kf.d`/platform data gốc.
- Ghi chú rõ đây là Java-inspired/reconstructed policy và cập nhật `.clinerules`, `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Khôi phục jump/fall runtime Java thật

- `JavaCompatibleCharacterController` bỏ tuning `DEFAULT_JUMP_IMPULSE_MULTIPLIER = 1.15` và `AIRBORNE_VERTICAL_DELTA_RATIO = 0.82` khỏi physics runtime.
- Jump rising/falling trở lại đúng công thức Java `km.java`: `t.b -= s; s--` và `t.b += s; s = min(a, s + 2)`.
- Smoothness tuning nếu có chỉ được visual-only; runtime `kl.t.b`, velocity `s/a`, collision grid/surface adapter và monster trigger vẫn là source of truth.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Air-control X interpolation an toàn Hoa Lư

- `JavaCompatibleCharacterController` thêm `USE_AIRBORNE_X_VISUAL_INTERPOLATION = true` để chỉ làm mượt X visual khi actor đang `JumpRising`/`Falling`, không bật lại interpolation toàn cục từng gây jitter.
- Input trái/phải, tap-to-move và move-to-monster lúc airborne chỉ cập nhật hướng/bitmask Java `k=4/8`; horizontal runtime X vẫn cộng trong fixed tick Java, còn render X blend tới runtime X mới nhất.
- Giữ source of truth ở runtime hitbox `kl.t`, collision grid/surface adapter, monster runtime box và battle trigger; thay đổi chỉ là Java-inspired/reconstructed render policy.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Sửa mất sprite khi air-control nhảy Hoa Lư

- `JavaCompatibleCharacterController` sửa airborne visual interpolation để không nội suy X khi player nhảy/rơi kết hợp trái/phải; X visual bám runtime hiện tại, tránh sprite biến mất rồi xuất hiện lại ở vị trí đáp.
- Giảm `DEFAULT_JUMP_IMPULSE_MULTIPLIER` từ `1.35` xuống `1.15` và thêm `AIRBORNE_VERTICAL_DELTA_RATIO = 0.82` để nhảy/rơi bớt nhanh trong scene Hoa Lư hiện tại.
- Thay đổi vẫn là Java-inspired/reconstructed render/scale policy; runtime hitbox `kl.t`, state machine, collision grid, monster trigger và attack range không đổi.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Airborne Y visual interpolation Hoa Lư

- `JavaCompatibleCharacterController` thêm `USE_AIRBORNE_Y_VISUAL_INTERPOLATION = true` để chỉ nội suy visual offset Y khi actor đang `JumpRising`/`Falling`.
- Giữ `USE_INTERPOLATED_JAVA_VISUAL_COMMIT = false`, nên X và ground running vẫn commit trực tiếp theo Java fixed-step/J2ME actor loop.
- Collision, attack range, monster trigger vẫn đọc runtime `kl.t`; thay đổi chỉ tác động lớp render `posYAnim` giữa các tick `JAVA_MAP_TICK_MS`.
- Không nội suy X để tránh tái phát jitter/snap camera từng gặp ở rollback interpolation trước.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Căn visual chân nhân vật Hoa Lư

- `HoaLuMapScreen` tăng riêng `spriteFootSink` visual thêm `Math.round(2 * sceneConfig.playerScale)` để chân render chạm mép cỏ/ground strip tự nhiên hơn.
- Thay đổi chỉ tác động `playerSpriteSize.groundOffset` của lớp render sprite; không đổi `groundTop`, `charFootYRef`, runtime `kl.t`, collision grid, monster trigger hoặc battle collision.
- Ghi chú trong code/tài liệu đây là Java-inspired/reconstructed visual anchoring policy do chưa recover exact Java render anchor.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Rollback visual interpolation gây jitter Hoa Lư

- `JavaCompatibleCharacterController` tắt lại `USE_INTERPOLATED_JAVA_VISUAL_COMMIT` sau khi test thực tế báo sprite còn rung/giật.
- Visual position quay về commit trực tiếp theo runtime actor sau mỗi Java fixed-step, gần Java J2ME actor loop hơn: update runtime rồi draw ngay, không tween giữa tick.
- Giữ `DEBUG_JAVA_MOVEMENT_JITTER = false`; không bật log/overlay trong runtime thường.
- Physics/collision/monster trigger không đổi, vẫn đọc runtime `kl.t`, `JAVA_MAP_TICK_MS` và collision grid/surface adapter.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Visual interpolation-only theo Java fixed-step cho Hoa Lư

- `JavaCompatibleCharacterController` chuyển từ discrete-only commit sang `USE_INTERPOLATED_JAVA_VISUAL_COMMIT = true`.
- Thêm frame nội suy visual-only giữa vị trí sprite hiện tại và vị trí runtime mới nhất sau Java fixed-step 40ms.
- Không dùng lại `Animated.timing` restart mỗi tick; rAF chỉ cập nhật `posAnim`/`posYAnim` cho lớp render.
- Spawn/stop/landing/reset vẫn hard commit để endpoint không bị trễ.
- Physics/collision/monster trigger vẫn đọc runtime `kl.t` và collision grid hiện có; interpolation không feed back vào gameplay.
- Debug jitter/overlay vẫn tắt trong runtime thường.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Ổn định visual commit sau debug jitter Hoa Lư

- `JavaCompatibleCharacterController` bật lại discrete visual commit theo Java actor loop (`USE_DISCRETE_JAVA_VISUAL_COMMIT = true`) để sprite bám runtime `kl.t` ngay sau fixed-step, tránh restart/cancel native animation liên tục gây rung/rubber-band.
- Giữ `DEBUG_JAVA_MOVEMENT_JITTER = false` để không spam console trong runtime thường.
- Rà `HoaLuMapScreen`: debug overlay `MAP_DEBUG_OVERLAY_ENABLED` đang tắt nên không còn cập nhật state overlay theo `onMove` khi test movement thường.
- Không đổi physics/collision gameplay; thay đổi chỉ ổn định lớp render React Native theo Java-inspired/reconstructed policy.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Thêm debug movement jitter Hoa Lư

- `JavaCompatibleCharacterController` thêm log tạm `[JavaMoveDebug]` để chẩn đoán rung/giật khi người chơi chạy trong Hoa Lư.
- Log tick runtime gồm fixed-step count, accumulator, state `j`, runtime rect `kl.t`, visual X/Y, footY, groundY và collision grid state.
- Log riêng ground snap và `positionRevision` reset để phân biệt jitter do grid/surface Y, render tick, hoặc parent/server echo.
- `HoaLuMapScreen` thêm overlay tạm `JavaMoveDebug overlay` hiển thị trực tiếp trên màn hình để test web khi Console không hiện log app.
- Overlay cập nhật theo `onMove`, `onMoveEnd`, gamepad/key start/stop và hiển thị X, footY, facing, hướng input, cameraX, số monster runtime.
- Không đổi physics/collision gameplay; chỉ thêm instrumentation phục vụ kiểm tra runtime parity.
- Kiểm tra: `npx tsc --noEmit` và `npm exec -- tsc --noEmit` trong `client` hiện fail do môi trường npx/npm exec gọi nhầm package placeholder `tsc`; dùng trực tiếp local TypeScript binary `node client/node_modules/typescript/bin/tsc -p client/tsconfig.json --noEmit` — passed.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Ổn định monster runtime/trigger Hoa Lư

- Rà và hoàn thiện đồng bộ `monsterTargets` trong `HoaLuMapScreen` với `collisionWidth`, `collisionHeight`, `groundY` theo runtime monster hiện tại.
- Auto encounter/tap/attack range dùng monster runtime collision box tách khỏi visual sprite, AABB anchored bottom-center theo Java-inspired/reconstructed policy.
- Player trigger dùng runtime-like body width gần Java `kl.t.c`, không dùng full sprite padding; vertical gate theo vùng body quanh ground line để giảm kéo battle sai khi nhảy qua/đứng lệch tầng.
- Ghi rõ chưa Java-perfect vì chưa recover exact monster hitbox/map actor data gốc.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Hoa Lư dùng surface-based Java collision grid

- `HoaLuMapScreen` dùng `buildSurfaceJavaGrid(mapWidth, mapHeight, sceneSurfaces)` cho `JavaCompatibleCharacterController` thay vì flat one-row ground grid/fallback surface.
- Collision support giờ phản ánh toàn bộ `GroundSurface` đã author cho ground/platform, giảm snap/fall/trigger lệch ở vùng platform giữa map khi runtime actor hitbox Java `kl.t` đang chạy.
- Ghi chú rõ đây vẫn là adapter Java-inspired/reconstructed theo mô hình `kf.d`; chưa Java-perfect vì chưa recover exact collision matrix gốc Hoa Lư.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Discrete visual commit theo Java actor loop cho Hoa Lư

- `JavaCompatibleCharacterController` thêm `USE_DISCRETE_JAVA_VISUAL_COMMIT = true` để commit sprite position trực tiếp sau mỗi Java fixed-step, không restart `Animated.timing` 40ms liên tục.
- Giữ physics/collision theo runtime `kl.t` và `JAVA_MAP_TICK_MS`; thay đổi chỉ ở lớp render để sprite không bị trễ/rubber-band so với runtime hitbox.
- Nguồn suy luận: Java J2ME actor loop update runtime rồi draw ngay trong tick/canvas; đây là Java-inspired/reconstructed render policy, không đổi công thức movement/collision.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Tuning visual smoothing Java-compatible cho Hoa Lư

- `JavaCompatibleCharacterController` buộc visual smoothing dùng đúng `JAVA_MAP_TICK_MS` thay vì thời lượng rời rạc và chỉ retarget animation khi có Java fixed-step mới.
- Hard commit vị trí ở spawn/stop/landing endpoints để tránh `Animated.timing` bị restart theo rAF và gây micro-stutter khi chạy/nhảy.
- Giảm landing hold từ `150ms` xuống `90ms`; thêm `Easing.linear` cho `posAnim`/`posYAnim`; chỉ nội suy lớp render, không đổi physics fixed-step Java.
- Rà lại `HoaLuMapScreen`: camera scroll đã coalesce bằng rAF, socket move đã throttle `750ms`/`24px` và force khi `onMoveEnd`, không gửi theo từng frame.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Runtime monster collision box Java-inspired cho Hoa Lư

- `MonsterTarget` mở rộng `collisionWidth`, `collisionHeight`, `groundY` để controller dùng runtime collision box tách khỏi visual sprite.
- `HoaLuMapScreen` truyền collision box/ground line của monster runtime vào target array và đổi auto encounter sang AABB anchored bottom-center.
- Player trigger dùng runtime-like body width gần Java `kl.t.c`, không dùng full sprite width.
- `JavaCompatibleCharacterController` bỏ `MONSTER_TOUCH_HITBOX_RATIO`; tap/attack monster dùng `getMonsterRuntimeBox(...)`, pad touch 4px và vertical gate theo ground line.
- Player runtime X/center trong controller được căn theo hitbox Java `kl.t` rộng 17px; visual sprite chỉ là lớp render quanh runtime center để tránh dùng full sprite padding cho move-to/tap/attack range.
- `monsterCollisionSize(type)` dùng ratio theo loại quái và visible body height; policy ghi rõ Java-inspired/reconstructed vì chưa recover exact monster hitbox gốc.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Thu nhỏ collision block monster ngoài map

- Thêm `monsterCollisionSize(type)` để tách collision/encounter hitbox khỏi kích thước sprite hiển thị.
- `HoaLuMapScreen` dùng collision size thu nhỏ cho monster và inset thân player khi xét auto encounter, tránh bị kéo battle khi chưa chạm hình quái.
- Nguồn suy luận: Java map actor dùng runtime hitbox `kl.t`, không dùng full sprite rectangle; phần monster collision là adapter remake tạm vì asset RN có alpha padding.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Tăng nhảy, thu nhỏ tap hitbox monster, xóa controller cũ

- `JavaCompatibleCharacterController` tăng initial jump impulse bằng multiplier tạm `1.35` trên nền công thức Java `kl.a = min(16, 11 + level / 10)`.
- Giữ nguyên state/tick Java-compatible: jump `j=5`, falling `j=6`, vertical step vẫn theo `km.java`.
- Thu nhỏ vùng tap monster còn 58% vùng giữa sprite để tránh chạm viền/khoảng trống vẫn bị chọn monster.
- Xóa `client/src/engine/character/CharacterController.tsx`; runtime map chỉ còn dùng controller Java-compatible qua export hiện tại.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.

### [MAP] Sửa snap X khi air-control trên không

- `JavaCompatibleCharacterController` giữ đúng state Java `j=5/6` khi player đang nhảy/rơi nhưng bấm/trỏ trái-phải.
- `startMove`, tap-to-move và move-to-monster trong lúc airborne chỉ cập nhật hướng `k=4/8`, cộng X tức thời và commit visual position; không ép về `Running`.
- Mục tiêu: sửa lỗi nhân vật nhảy lên rồi điều hướng ngang nhưng X bị kéo/snap về vị trí đứng ban đầu.
- Nguồn suy luận: `km.java` state `5/6` xử lý vertical rồi gọi helper air-control; Java cộng dồn `t.a` từng tick, không có interpolation về điểm nhảy cũ.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.
- Kiểm tra: `node client/node_modules/typescript/bin/tsc -p client/tsconfig.json --noEmit` — passed.

### [MAP] Sửa trigger monster khi nhảy qua

- `HoaLuMapScreen` đổi encounter collision từ trigger theo trục X sang kiểm hitbox X + foot Y.
- Lưu `charFootYRef` từ controller Java-compatible; nếu player nhảy cao hơn thân monster thì không kích battle dù X overlap.
- Khi player ở gần mặt đất/chân còn chạm vùng thân monster, encounter vẫn kích bình thường.
- Nguồn suy luận: Java map actor dùng runtime hitbox `kl.t`, không phải line trigger X-only.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md`.
- Kiểm tra: `client\node_modules\.bin\tsc.cmd --noEmit -p client\tsconfig.json` — passed.

## 2026-04-29

### [DB] Chuẩn hóa SQL theo module (`server/Database/`)
- Gộp 15 file migration gốc thành thư mục: `Accounts/`, `Players/`, `Equipment/`, `WorldMap/`, `Monsters/`.
- Mỗi module: `{module}_schema.sql` (final shape), `{module}_seed.sql` nếu có seed.
- Thứ tự chạy và mô tả: `server/Database/README.md`.

### [BATTLE] Đầu hàng resolve như thua trận
- Menu `Đầu hàng` trong `BattleScreen` không gọi callback flee để thoát trực tiếp nữa.
- Client khóa trận bằng `result = defeat`, chạy defeat/recovery sequence và để `/battle/result` nhận HP/MP/Power hiện tại.
- Mục tiêu: server áp dụng penalty như thua trận, giữ HP đã mất thay vì hồi/reset sai trạng thái sau khi quay lại map.
- Cập nhật `BATTLE_SYSTEM_RECONSTRUCTION.md` với rule reconstruction/remake cho flee/defeat.

### [MAP] Sửa tốc độ chạy, air-control khi nhảy và monster respawn
- `MapMovementCalculator` dùng trực tiếp speed Java `kl.i = min(9, 4 + level / 10)` thay vì scale xuống `1.0..2.8`.
- `CharacterController` giữ điều hướng ngang khi đang nhảy theo Java `km.java` state `5/6`.
- Khi thả trái/phải trên không, đồng bộ target nhảy về X hiện tại để tránh snap/rơi về vị trí đứng cũ.
- Scale movement ngang theo display `scale` cho run/tap/air-control/fall target timing, đồng bộ với jump impulse đã scale để sửa cảm giác chạy chậm và nhảy ngang không qua được platform.
- Battle monster lưu `MapId`/`RoomId` trong `BattleSessionState` để claim result biết đúng encounter runtime.
- `IMapMonsterRosterService`/`DbMapMonsterRosterService` hỗ trợ deactivate encounter theo hạn; quái thường respawn sau 3 phút, boss-like sau 1 ngày.
- Sau mọi kết quả battle monster, client kích hoạt recovery 3 giây: nhân vật nhấp nháy/vô địch, dừng input/movement và chặn monster overlap retrigger ngay khi quay lại map.
- Recovery sau battle không ẩn monster field; quái vẫn hiển thị/patrol/animate, chỉ bị chặn collision retrigger trong 3 giây.
- Cập nhật `MAP_SYSTEM_RECONSTRUCTION.md` với nguồn suy luận và nhật ký chỉnh sửa.
- Rà lại Java movement để hướng tới mục tiêu giống 100%:
  - `kl.java`: actor runtime, speed `i`, jump/fall cap `a`, hitbox `t`, state `j`, direction bitmask `k`.
  - `km.java`: input flags, key mapping, state machine movement, jump/fall air-control.
  - `kh.java`: collision flag helpers `a/b/c/d/l/m/n`, camera/bounds clamp.
  - `kf.java`: collision tile matrix `d`, tile size `32x32`.
- Bổ sung roadmap trong `MAP_SYSTEM_RECONSTRUCTION.md`: cần port nguyên state `j/k/s/t` và tile collision grid `kf.d` để đạt Java-perfect; bản hiện tại mới giống Java ở speed + air-control + no-snap X.
- Tổng hợp thêm audit Java map & move chi tiết trong `MAP_SYSTEM_RECONSTRUCTION.md`:
  - World map: `oh/fz/fg/pc/hi/og/ks`, asset contract `/m/m`, `/m/arena`, `/m/room`, `/m/lock`, `/m/lock2`, `/m/hand`, `/m/arrow`, `/roomicon`, softkey `Vào Thành`.
  - Movement: `kl/km` chi tiết hitbox `t/u/I`, state `0..8`, direction bitmask `1/2/4/8/5/6/9/10`, jump/fall/landing/slope/climb/action behavior.
- Port bước đầu Hoa Lư sang Java-compatible tile movement:
  - Thêm `client/src/engine/character/javaMapMovement.ts` với constants/state kiểu Java `kl/km`, hitbox `t`, state `j`, direction `k`, velocity `s`, helper tile flag `kh.a/b/c/d/l/m/n`, wall/ground/jump/fall/landing tick theo grid `32x32`.
  - Thêm `client/src/engine/character/JavaCompatibleCharacterController.tsx` để React Native dùng state machine mới và expose status runtime.
  - `HoaLuScene.ts` sinh collision grid `32x32` từ navigation surface/wall/ceiling hiện có làm adapter tạm cho Hoa Lư.
  - `HoaLuMapScreen.tsx` chuyển player sang controller Java-compatible; monster/render/recovery vẫn giữ logic hiện hành.
  - Bổ sung truyền `playerLevel` trực tiếp vào controller để công thức Java `kl.i/kl.a` lấy đúng level thay vì suy ngược từ speed.
  - Bổ sung truyền `collisionGrid` Hoa Lư vào `JavaCompatibleCharacterController`; controller ưu tiên tile probing `kf.d` cho ground support, landing, wall side-check và ceiling/head-hit trước khi fallback surface.
  - Kiểm tra: `client\node_modules\.bin\tsc.cmd --project client\tsconfig.json --noEmit` — passed.
- Sửa Java-compatible movement dùng fixed Java tick:
  - Thêm `JAVA_MAP_TICK_MS = 40` và `JAVA_MAP_MAX_STEPS_PER_FRAME = 3` trong `javaMapMovement.ts`.
  - `JavaCompatibleCharacterController.tsx` bỏ phụ thuộc `MOVE_TICK_MS = 16`/60fps của controller RN cũ; rAF chỉ còn làm scheduler, physics chạy fixed-step accumulator 40ms.
  - Mỗi Java physics step dùng công thức tick từ `km.java`: chạy ngang `x += kl.i`, jump `y -= s; s--`, falling `y += s; s += 2` cap `kl.a`.
  - Không export `CharacterController` cũ qua `client/src/engine/character/index.ts` để runtime map không vô tình dùng lại interpolation/timing cũ.
  - Mục tiêu: sửa lỗi chạy/rơi nhanh hơn Java khoảng `40 / 16 = 2.5` lần do áp công thức Java px/tick vào loop 16ms.
- Sửa air-control trong `JavaCompatibleCharacterController`:
  - khi đang `jumping/falling`, lệnh trái/phải cập nhật input flags và direction bitmask Java `k=4/8` nhưng không ép state về `running`;
  - tap-to-move/move-to-monster chỉ bật `running` khi player đang `idle/running/landing`, còn trên không giữ state `j=5/6` để helper air-control cộng X theo tick như `km.java`;
  - kiểm tra lại `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` — passed.
- Bổ sung plan chi tiết trong `MAP_SYSTEM_RECONSTRUCTION.md` cho hướng tự author collision map khi không có `kf.d` gốc:
  - chốt source chính tương lai là authored Java-compatible collision grid `32x32`, không suy physics trực tiếp từ art/background;
  - ghi rõ flag contract `0/8/16/32/65/66`, symbol text map `.`, `G`, `V`, `R`, `L`, `H`, `>`, `<`, `B`;
  - đề xuất files `javaCollisionMap.ts`, `HoaLuCollisionMap.ts`, `CollisionDebugOverlay.tsx`;
  - mô tả workflow vẽ Hoa Lư theo từng bước: ground, platform, wall, slope, climb, portal;
  - yêu cầu debug overlay hiển thị grid, hitbox, sample points, state `j/k/s/t`;
  - bổ sung milestone triển khai collision authoring foundation, debug overlay, topology Hoa Lư, slope/climb, portal/runtime room.

## 2026-04-28

### [MONSTER] Hoàn thiện monster module — DB schema + asset seed + client types
- Tạo migration `Database/Monsters/monsters_schema.sql` với 4 bảng tách biệt theo Java runtime split:
  `Monsters`, `MonsterSpawns`, `MonsterBattles`, `MonsterRosters`.
  Mỗi bảng có JSONB cho `frame_paths`, `skills`, `appearance`.
- Tạo seed `Database/Monsters/monsters_seed.sql` — seed toàn bộ 78 confirmed asset entries
  cho 8 species (1000-1007) từ `client/assets/monster/index.csv`.
  Convention: `MONSTER_{speciesCode}_SLOT_{slot}`.
- Cập nhật `MonsterAssetCatalogEntry` thêm `FramePaths`.
- Cập nhật `MapMonsterRosterEntry` thêm `AssetCatalogId` + `FramePaths`.
- Cập nhật `MonsterBattleInstance` thêm `AssetCatalogId` + `FramePaths`.
- Cập nhật `MonsterCatalogSeed.cs` với toàn bộ 78 asset catalog entries.
- Cập nhật `/map/monster-roster` endpoint trả `assetCatalogId` + `framePaths`.
- Cập nhật `MonsterBattleBootstrapService` truyền asset info vào battle instance.
- Tạo `client/src/types/monster.types.ts` với:
  `MonsterRosterEntry`, `MonsterBattleBootstrap`, `MonsterBattleDelta`,
  `MonsterVisualFamily`, `resolveVisualFamily()`, `resolveIqLabel()`.
- Tài liệu `MONSTER_SYSTEM_RECONSTRUCTION.md` cập nhật backlog.

**Nguyên tắc bảo toàn:**
- Asset / Spawn / Battle / Roster tách biệt — không gộp.
- `monsterKey` là identity gameplay/runtime, khác `asset_id`.
- Map spawn data lightweight giống Java `jo`.
- Battle truth từ battle template / bootstrap giống Java `lh/lv`.
- Client không quyết định monster stats/skills/battle result.

**Files chính:**
- `server/Database/Monsters/monsters_schema.sql`
- `server/Database/Monsters/monsters_seed.sql`
- `server/Twelve.Core/Monsters/MonsterContracts.cs`
- `server/Twelve.Application/Monsters/MonsterCatalogSeed.cs`
- `server/Twelve.Application/Monsters/MonsterBattleBootstrapService.cs`
- `server/Twelve.Server/Program.cs`
- `client/src/types/monster.types.ts`
- `MONSTER_SYSTEM_RECONSTRUCTION.md`

### [MAP] Spec map/world runtime
- Rút gọn `MAP_SYSTEM_RECONSTRUCTION.md`, chốt spec cốt lõi world map/runtime map và phần còn thiếu cho map bên ngoài.

### [BATTLE] Gameplay bàn cờ v1
- Xác nhận damage kiếm trắng/đỏ đã scale theo `MinDamage/MaxDamage`; số `15` là kết quả khi `AttackRoll ≈ 15`, không phải hardcode.
- Áp khắc hệ local cho board sword theo vòng server `100/112/92`.
- Sửa cộng lượt: mỗi distinct match group `>=4` cộng `+1`, cascade thật cũng được tính.
- Sau khi tiêu thụ extra turn, nếu còn lượt thì UI hiện lại Còn X lượt; về `0` thì ẩn badge.
- Hoạt ảnh đánh thường phát 4 nhịp rồi mới quay về; damage chỉ apply 1 lần.
- Badge lượt còn lại dùng text trắng để dễ đọc.

**Files chính:**
- `client/src/screens/battle/BattleScreen.tsx`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/hooks/useBattleSwordAttacks.ts`
- `client/src/screens/battle/ui/BattleScreen.panel.tsx`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`

**Kiểm tra:** `npx --prefix client tsc -p client/tsconfig.json --noEmit` — passed.

## 2026-04-27

### [BATTLE] Khóa gameplay bàn cờ v1 theo Java + gameplay memory
- Board active dùng pool `0,1,2,3,4,5,6,8`; bỏ `chess7`.
- `chess0` kiếm trắng và `chess8` kiếm đỏ cùng category kiếm.
- Kiếm đỏ trigger-on-touch, nổ `3x3`, chain sang kiếm đỏ khác và apply item trong vùng.
- Match `>=4` cộng lượt theo số distinct group; không tạo natural special mặc định.
- HP/MP/Nộ/EXP/Gold từ board tách thành pending reward/effect.
- Damage kiếm chuyển từ hardcode sang scale theo `BattleAttackProfile { minDamage, maxDamage }`.
- Sửa result-lock/victory fallback và guard monster turn sau khi enemy HP về `0`.
- Gold/KEN là raw gold; Quan = `floor(rawGold / 10000)`.

**Files chính:**
- `client/src/screens/battle/core/BattleScreen.shared.ts`
- `client/src/screens/battle/core/BattleScreen.logic.ts`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/hooks/useBattleMonsterTurn.ts`
- `client/src/screens/battle/core/BattleScreen.packetResolver.ts`
- `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`
- `BATTLE_SYSTEM_RECONSTRUCTION.md`

### [PLAYER_CHARACTER] Dọn runtime/schema nhân vật
- Xóa `AppearanceJson` duplicate khỏi runtime/repository/schema; thêm migration drop cột.
- Xóa derived stats cache khỏi DB, chuyển sang `PlayerStatPipeline.Calculate()` on-the-fly.
- Giữ các field bonus stat/equipment pipeline cần thiết.

**Kiểm tra:** `dotnet build Twelve.sln` — passed.

## 2026-04-26

### [BATTLE] Resource/damage chuyển sang server authority
- Battle bootstrap trả base resource/damage từ server.
- `BattleTurnEngine` dùng stat server-owned: `MinDamage/MaxDamage`, `Defense`, `HitRate`, `DodgeRate`, `CriticalDamage`.
- Áp khắc hệ battle v1 `112%/100%/92%`.
- Battle result reset MP/Power sau trận; HP PvE thắng có thể giữ attrition.
- Ghi nhận mapping 8 icon board, tắt natural special mặc định, pending EXP/Gold chỉ chốt khi thắng.
- Fallback gem resource theo semantic per-color để MP/Âm Dương không hồi nhầm HP.

**Kiểm tra:** `dotnet build Twelve.sln`, `npx tsc -p client/tsconfig.json --noEmit` — passed.

### [PLAYER_CHARACTER] MP/Power là tài nguyên tạm battle
- Nhân vật mới seed `Mp = 0`, `Power = 0`.
- `/battle/result` reset MP/Power về `0`.
- HP sau thắng PvE có thể giữ current HP.

## 2026-04-25

### [PLAYER_STATS] Chuẩn hóa công thức 6 chỉ số theo Java
- Port công thức status theo `jq/js/jr`.
- Tách `MinDamage` / `MaxDamage`.
- Status `Tấn Công` hiển thị `runtime.minDamage` như Java cũ.
- Bỏ off-element soft cap khỏi tầng status/derived stat.

**Kiểm tra:** `dotnet build Twelve.sln`, `npx tsc -p client/tsconfig.json --noEmit` — passed.

### [MAP] World-map catalog và UI selection
- Chuyển catalog 17 địa danh world-map sang server theo Java `og/oh`.
- Client fetch `/map/world-catalog`, fallback local.
- Sửa pan/cursor/HUD world-map, chỉ Hoa Lư mở runtime thật.
- Bỏ Skia fallback map gây CanvasKit crash.

### [UI] Font/theme/menu/loading/debug cleanup
- Tạo `GameFonts`/`GameTheme`.
- Áp style chung cho Login/Register/PopupMenu/Softkey/Calendar/HUD.
- Bỏ global loading modal khỏi gameplay hoặc polling nền.
- Xóa console runtime trong `client/src`.

### [PVP/BATTLE] Đồng bộ board PvP
- Seed board deterministic từ `sessionId`.
- Active player không bị polling snapshot ghi đè local cascade.
- Session-sync bump `TurnSeq` khi board/bars/turn thay đổi.
- Disable AI/monster hook trong PvP thật.

### [PLAYER_CHARACTER] Tạo nhân vật và progression v1
- Base stats nhân vật mới `(10,10,10,10)`.
- Initial free points = `0`; level-up cộng `+5` điểm.
- EXP curve/spec Level 250 ghi trong tài liệu player-character.
- Map movement speed bám Java level formula.

## Ghi chú

CHANGELOG đã được rút gọn để giữ các mốc chính. Chi tiết triển khai nằm trong tài liệu reconstruction tương ứng và lịch sử git.
