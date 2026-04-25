# CHANGELOG

## 2026-04-25

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
