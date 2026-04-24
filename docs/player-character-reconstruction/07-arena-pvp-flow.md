# 07 - Arena / PvP Flow Reconstruction

Thiết kế thực dụng cho flow `Khiêu Chiến/PvP` khi không có server cũ, bám theo dấu vết Java client đã bóc trong `os/do/ha/ky/ks` và các tài liệu player-map-battle hiện tại.

## Mục Tiêu

- tách PvP khỏi map train PvE instance (đúng hướng hiện tại của project)
- giữ model player truth bám `lh` (không tạo model battle rời hẳn char)
- cho phép tìm đối thủ, gửi/nhận thách đấu, vào battle session 1v1, nhận result/reward
- tương thích dần với command/social surface của Java (menu `Đánh`, `Giao dịch`, `Chat`, `Xem ME`)

## Bằng Chứng Java Cần Bám

- `os.java` + `do.java`: room/player list dùng `status byte`, `prestige`, `status message`, `stake/wager`; menu action phụ thuộc `do.c` và `do.f`
- `ha.java`: preview versus card cần appearance/title/class/level (không chỉ tên)
- `ky/ks`: command family room/social tồn tại riêng, không trộn vào char-core stat/skill/equip commands
- `ms/mx/lg`: battle runtime mutate bars liên tục trên `lh` wrapper, không thay actor object wholesale

## Kiến Trúc Runtime Đề Xuất

Tách 4 lớp service để tránh dồn hết vào `PlayerService`:

1. `PvpLobbyService`
   - online roster cho flow Khiêu Chiến
   - presence/status cho player entry
   - challenge request/accept/decline lifecycle

2. `PvpMatchService`
   - tạo match/session 1v1
   - lock input/roster state khi đang match
   - bootstrap battle payload từ snapshot player thật

3. `PvpRatingService`
   - cập nhật `D.Vọng`/prestige/rank points sau trận
   - rule thắng/thua, anti-farm cơ bản, floor/cap theo config

4. `PvpRewardService`
   - quan/exp/item thưởng PvP (nếu có)
   - tách khỏi reward PvE để dễ cân bằng độc lập

## Data Contract (Server Authority)

### 1) Lobby Player Entry

`PvpLobbyEntry` là bản tương đương practical của `do` + profile-lite:

```csharp
public sealed record PvpLobbyEntry(
    Guid PlayerId,
    string Name,
    int Level,
    int Prestige,
    byte Status,
    string StatusMessage,
    long Stake,
    string? TitlePrimary,
    string? TitleSecondary,
    int ClassCode,
    int AvatarSeed
);
```

Ghi chú:

- `Status`, `StatusMessage`, `Stake` bám chặt các dấu vết đã bóc từ `lh.e`, `lh.P`, `lh.X`
- không cần full `lh` cho mọi roster row; chỉ khi mở `Xem ME`/versus preview mới trả profile snapshot đầy hơn

### 2) Challenge Ticket

```csharp
public sealed record PvpChallengeTicket(
    Guid TicketId,
    Guid ChallengerPlayerId,
    Guid TargetPlayerId,
    long Stake,
    DateTimeOffset CreatedAt,
    DateTimeOffset ExpiresAt,
    string State // Pending | Accepted | Declined | Expired | Cancelled
);
```

Rule tối thiểu:

- mỗi cặp challenger-target chỉ có 1 ticket `Pending`
- auto-expire (ví dụ 20s)
- reject nếu 1 trong 2 đang in battle/loading/disconnected

### 3) PvP Match Session

```csharp
public sealed record PvpMatchSession(
    Guid MatchId,
    Guid LeftPlayerId,
    Guid RightPlayerId,
    long Stake,
    DateTimeOffset StartedAt,
    string State // Bootstrapping | Running | Completed | Cancelled
);
```

## Flow Chuẩn Khiêu Chiến 1v1

1. client vào màn Khiêu Chiến -> gọi `GET /pvp/lobby`
2. chọn target trong list -> `POST /pvp/challenges`
3. target nhận popup thách đấu (timeout + accept/decline)
4. target accept -> server tạo `PvpMatchSession`
5. cả 2 client nhận `matchFound` + battle bootstrap payload
6. battle chạy bằng runtime battle hiện có (reuse battle engine)
7. kết thúc -> gọi `POST /pvp/result` (server claim one-time như `/battle/result`)
8. server cập nhật rating/reward/world-state, trả result payload cho cả 2

## API Blueprint (HTTP Trước, Socket/Event Sau)

### Lobby

- `GET /pvp/lobby`
  - output: danh sách `PvpLobbyEntry`, serverTime, optional season metadata

- `POST /pvp/lobby/status`
  - input: `{ status, statusMessage }`
  - output: ack + row cập nhật

### Challenge

- `POST /pvp/challenges`
  - input: `{ targetPlayerId, stake }`
  - output: `ticketId`, `expiresAt`

- `POST /pvp/challenges/{ticketId}/accept`
- `POST /pvp/challenges/{ticketId}/decline`
- `POST /pvp/challenges/{ticketId}/cancel`

Trạng thái implement hiện tại (C# runtime):

- đã có endpoint HTTP tương ứng với flow send/accept/decline/cancel
- ticket lưu in-memory với TTL 20s, state: `Pending | Accepted | Declined | Cancelled | Expired`
- chặn duplicate ticket pending cho cùng cặp người chơi (cả chiều thuận/ngược)

### Match & Result

- `GET /pvp/matches/{matchId}`
  - output: lightweight match state để reconnect/resume UI

- `POST /pvp/result`
  - input: `{ matchId, winnerPlayerId, loserPlayerId, hpLeft }`
  - output:
    - rating delta hai bên
    - exp/quan/item reward (nếu config bật)
    - updated runtime player snapshot cho local client

## Bootstrap Payload Cho PvP Battle

Không dùng player giả. Lấy snapshot tương tự battle player runtime đã chốt:

- identity: name, level, class/element, title/prestige
- bars: HP/MP/Power current/max theo session bootstrap rule
- skills: learned skill levels từ aggregate thật
- appearance: descriptor + equip overlays để dựng compositor
- equipment-derived stats: attack/defense/crit/dodge/hit và modifier cần cho battle formula

Tránh sai lệch:

- không derive từ vài cột DB rời
- luôn chạy qua cùng `PlayerStatPipeline` đang dùng cho runtime snapshot/equip preview

## State Machine Đề Xuất

### Player PvP Presence

- `Offline`
- `Idle`
- `ViewingProfile`
- `ChallengePendingOut`
- `ChallengePendingIn`
- `MatchBootstrapping`
- `InMatch`
- `PostMatchCooldown`

### Match

- `PendingAccept`
- `Bootstrapping`
- `Running`
- `ResultPendingClaim`
- `Completed`
- `Cancelled`

## Chống Lỗi Và Anti-Abuse Tối Thiểu

- idempotent result claim: mỗi `matchId` chỉ finalize 1 lần
- optimistic lock hoặc transaction cho stake/rating update
- timeout auto-cancel nếu 1 bên không vào battle sau khi accept
- anti-spam challenge: rate limit theo player + target
- anti-farm cơ bản:
  - giảm mạnh reward nếu gặp lại cùng đối thủ quá nhiều trong cửa sổ ngắn
  - giới hạn chênh lệch level/rating có thể nhận thưởng full

## Mapping Với Contract Java Cũ

- menu `Đánh` trong room list -> mapped thành action tạo challenge ticket
- `do.c` status byte -> map vào `PvpLobbyEntry.Status`
- `do.f` wager/stake -> map vào `PvpLobbyEntry.Stake` + `PvpChallengeTicket.Stake`
- `Xem ME` -> endpoint profile snapshot (reuse contract profile/status screen)
- `Giao dịch`/`Chat` để ở service khác; PvP chỉ consume presence/status chung

Lưu ý status practical hiện tại:

- status lobby đang suy từ HP thật của player: `HP > 0 => 0 (San sang)`, `HP = 0 => 1 (Hoi phuc)`
- đã sửa clamp để không còn tình trạng `HP=0` nhưng vẫn bị hiển thị `San sang`

## Kế Hoạch Port Theo Bước (Practical)

1. phase 1 - lobby read-only
   - có danh sách online player + status + preview card
2. phase 2 - challenge lifecycle
   - send/accept/decline/cancel + timeout
3. phase 3 - match bootstrap 1v1
   - tạo battle session PvP và vào trận ổn định
4. phase 4 - result + rating
   - finalize one-time, cập nhật D.Vọng/rank/reward
5. phase 5 - polish compatibility
   - wager options, reconnect resume, anti-farm, log/telemetry

## Tích Hợp Với Tiến Độ Hiện Tại Của Repo

- map train vẫn giữ PvE instance riêng như hiện tại
- PvP đi qua flow/lobby riêng, không cần broadcast co-presence trên map train
- battle result pattern có thể tái dùng từ `/battle/result` (claim session 1 lần)
- reward/rating tách config để không phá cân bằng PvE đã có

## Definition Of Done Cho Bản PvP Đầu Tiên

- người chơi thấy lobby có danh sách đối thủ online
- gửi thách đấu và bên kia nhận được popup realtime/polling
- accept vào trận 1v1 và battle bootstrap đúng stats/skill/equip thật
- kết thúc trận trả result đúng một lần, cập nhật rating + reward
- client refresh lại runtime player snapshot, UI status/inventory/equip không lệch
