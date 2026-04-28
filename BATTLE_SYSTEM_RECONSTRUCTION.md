# Battle System Reconstruction

Tài liệu này khóa logic battle/bàn cờ v1 cho project Twelve (.NET 9 + React Native) khi không có server Java cũ. Mục tiêu là bám Java client decompile ở phần client-side, đồng thời ghi rõ các phần là reconstruction/remake từ gameplay memory.

## 1. Nguồn tham chiếu chính

| Java file | Vai trò |
|---|---|
| `mp.java` | Battle asset manager: chess sheets, effect, HUD asset |
| `ms.java` | Battle model: board bytes, refill queue, buffer board |
| `mh.java` | Renderer board `8x8` |
| `nj.java` | Node chess definition: `id`, `mask`, `type`, `imageIndex` |
| `nd.java` | Runtime cell animation |
| `mq.java` | Battle controller/state machine, match/clear/drop/cascade |
| `mo.java` | Playing controller, scan nước đi hợp lệ |
| `mt.java` | Battle scene renderer, projectile/skill dispatch, turn text |
| `mx.java` | Actor + HUD renderer, animate HP/MP/Power |
| `lg.java`, `lh.java` | Actor runtime wrapper + stat/resource fields |
| `nq.java`, `nl.java`, `ky.java` | Packet turn/result model/parser |
| `hs.java`, `oa.java`, `om.java` | Result/reward presentation |

Java client coverage hiện tại: decompile đủ nhóm class battle/bàn cờ, board core client-side tin cậy khoảng `97-99%`. Các phần như refill packet, `nq.D`, `nq.F`, `nl[]`, reward roll là dữ liệu server Java cũ gửi vào; client chỉ apply/render.

## 2. Boundary Java vs remake

### Java client có thể port gần nguyên
- Board active `8x8`, storage Java là `12x12`, vùng chơi `row/col 2..9`.
- Node logic dùng `nj.mask`, không dùng trực tiếp tên ảnh `chessX`.
- Validate swap bằng swap thử + scan line ngang/dọc packed.
- Match scan dùng phép `mask AND`.
- Clear thường, clear special có sẵn `type 2/type 4`.
- Drop bottom-up theo cột, refill từ queue.
- Cascade scan dựa trên ô thay đổi.
- No-move detection brute-force swap kề nhau.
- HUD tween HP/MP/Power và text `"Còn X lượt"`.

### Server Java cũ / remake hiện tại
- `nq.D`: delta thời gian turn.
- `nq.F`: delta lượt còn lại.
- `nl[]`: HP/MP/Power/damage/result cuối cùng.
- Refill queue/no-move board reset bytes.
- Damage final, khắc hệ, reward roll, EXP/Gold từ board.

Quy tắc: logic nào không có server Java source hoặc packet log phải ghi là `reconstruction/remake`, không gắn nhãn Java gốc.

## 3. Board model và node

- Render active: `8x8`.
- Java storage: `12x12`, vùng thật `2..9`.
- Empty: `90`.
- Sentinel/block: `99`.
- Base node Java: `0..5`, `type = 1`.
- Special có sẵn:
  - `10..15`, `type = 2`: clear 8 ô xung quanh.
  - `20..25`, `type = 4`: clear hàng + cột.
- Node `70`: node riêng mask `64`, image index `6`.

Không map `node id -> asset id` một cách mù quáng. Logic match phải theo `mask`.

## 4. Mapping 8 icon gameplay v1

Theo gameplay memory đã chốt, board dùng `chess0..8` nhưng bỏ `chess7`.

| Icon | Chess | Behavior v1 |
|---|---:|---|
| Kiếm trắng | `chess0` | Sword damage, match chung với kiếm đỏ |
| Tim | `chess1` | Hồi HP ngay trong trận |
| Âm Dương / MP | `chess2` | Hồi MP/Mana |
| Đào | `chess3` | Hồi Nộ/Power |
| Nước / giọt tím | `chess4` | EXP bằng nửa sao |
| Sao xanh | `chess5` | EXP tạm |
| Vàng | `chess6` | Gold/KEN tạm |
| Kiếm đỏ / kiếm lửa | `chess8` | Trigger-on-touch, nổ `3x3`, damage x1.5 kiếm trắng |

Rule đi kèm:
- `chess8` cùng category/mask kiếm với `chess0`.
- Kiếm đỏ bị match, bị nổ lan, hoặc bị skill/clear tác động đều nổ `3x3`.
- Kiếm đỏ có thể chain sang kiếm đỏ khác.
- Base icon match xong biến mất rồi drop/refill.
- Natural special spawn `10..15`/`20..25` **không bật mặc định** theo gameplay memory hiện tại. Nếu sau này packet log/replay chứng minh server cũ bật mode này thì thêm feature flag riêng.

## 5. Match, clear, drop, cascade

### Validate swap
1. Swap thử 2 ô.
2. Scan ngang/dọc cho cả 2 endpoint.
3. Hợp lệ nếu có line length `>= 3`.
4. Swap back.
5. Trả packed line result để seed clear queue.

### Packed line Java
- Byte cao: số ô kéo trái/trên.
- Byte giữa: số ô kéo phải/dưới.
- Byte thấp: tổng length.
- So khớp bằng `(maskA & maskB) != 0`.

### Clear
- Base match line clear các ô match.
- Special có sẵn trong clear queue:
  - `type 2`: clear 8 ô quanh.
  - `type 4`: clear hàng + cột.
- Kiếm đỏ trong clear queue: nổ `3x3`, apply item trong vùng, chain kiếm đỏ khác.
- Dùng `resolvedKeys` để mỗi cell chỉ apply effect một lần trong cùng chain.

### Drop/refill
- Quét từng cột từ dưới lên.
- Node có `mask != 0` rơi xuống vị trí thấp nhất còn trống.
- Ô trống phía trên refill từ RNG/queue.
- Cascade sau drop chỉ scan lại vùng bị ảnh hưởng.

## 6. Công thức board item v1

Các công thức dưới đây là reconstruction/remake đã chốt, không phải server Java gốc.

### HP từ tim `chess1`

```text
HpScale = clamp(90, 140, 100 + (TotalStrength - 10))
HealGainBase = max(3, floor(MaxHp * 6 / 100))
HealGain = floor(HealGainBase * gemCount / 3 * HpScale / 100)
currentHp = min(MaxHp, currentHp + HealGain)
```

### MP từ Âm/Dương `chess2`

```text
ManaScale = clamp(70, 160, 100 + (TotalMagic - 10))
ManaGain = floor(MaxMp * 625 * gemCount * ManaScale / (10000 * 3 * 100))
if gemCount > 0 and currentMana < MaxMp:
  ManaGain = max(1, ManaGain)
currentMana = min(MaxMp, currentMana + ManaGain)
```

Mốc chuẩn: `TotalMagic = 10`, match 3 = `1/16 MaxMp = 6.25%`.

### Nộ/Power từ đào `chess3`

```text
PowerScale = clamp(80, 150, 100 + (TotalStrength - 10))
PowerGain = floor(MaxPower * gemCount * PowerScale / (19 * 100))
currentPower = min(MaxPower, currentPower + PowerGain)
```

Mốc chuẩn: `TotalStrength = 10`, 1 đào ≈ `1/19` thanh nộ.

### EXP từ sao/nước

Dùng accumulator `x2` để giữ nửa EXP:

```text
BoardExpUnit2 += blueStarCount * 2
BoardExpUnit2 += waterCount
FinalBoardExp = floor(BoardExpUnit2 / 2)
```

- Sao xanh `chess5`: `1` EXP.
- Nước/giọt tím `chess4`: `0.5` EXP.
- Chỉ chốt EXP nếu thắng; thua xóa pending board EXP.

### Gold/KEN từ vàng `chess6`

Dùng accumulator `x10`:

```text
GoldUnit10 += goldIconCount * 2
FinalBoardGold = floor(GoldUnit10 / 10)
```

- 1 icon vàng = `0.2` raw gold/KEN.
- Không scale theo level.
- Chỉ chốt nếu thắng.
- Quan hiển thị theo mốc:

```text
WalletQuan = floor(totalRawGold / 10000)
GoldProgress = totalRawGold % 10000
```

Chưa đủ `10000` raw gold/KEN thì hiển thị `0 Quan`.

## 7. Damage kiếm trắng / kiếm đỏ

Nguồn damage board sword là Tấn Công đã tính từ server/bootstrap:

```text
AttackRoll = random(Actor.MinDamage, Actor.MaxDamage)
```

Client local hiện dùng average deterministic để preview/flow:

```text
AttackRoll = floor((MinDamage + MaxDamage) / 2)
```

Không nhân thêm `TotalStrength` ở board vì `MinDamage/MaxDamage` đã được `PlayerStatPipeline` tính theo hệ/stat/trang bị.

### Raw damage

```text
SwordDamageRaw = floor(AttackRoll * 35 * whiteSwordCount / 100)
FireSwordDamageRaw = floor(AttackRoll * 35 * redSwordCount * 150 / 10000)
RawBoardDamage = SwordDamageRaw + FireSwordDamageRaw
```

Diễn giải:
- 1 kiếm trắng = `35% AttackRoll`.
- 1 kiếm đỏ = `52.5% AttackRoll` (`x1.5` kiếm trắng).
- Match 3 kiếm trắng với `AttackRoll ≈ 15` ra khoảng `15` damage là đúng công thức, không phải hardcode.

### Final damage order v1

```text
damage = RawBoardDamage
damage = ApplyDefense(damage, targetDefense)
damage = isCritical ? floor(damage * CriticalDamagePercent / 100) : damage
damage = floor(damage * ElementPercentAfterResist / 100)
damage = floor(damage * ModePercent / 100)
damage = max(1, damage)
```

Hiện client board local đã truyền `elementDamagePercent` cùng vòng khắc hệ server `100/112/92`, bám `BattleTurnEngine.ResolveElementDamagePercent()`.

V1:
- Board item match/nổ luôn apply effect, không roll miss.
- Crit có thể roll một lần trên tổng raw damage.
- Không cộng Nộ từ damage kiếm; Nộ đến từ đào/skill/effect server sau này.

## 8. Extra turn / lượt còn lại

Java client chỉ nhận `nq.F` từ server rồi render:

```text
"Còn " + n + " lượt"
```

Rule remake/gameplay memory v1:

```text
extraTurns = count(distinctMatchGroups where uniqueCellCount >= 4)
remainingTurns += extraTurns
```

- Match-4/match-5/L/T/cross đủ `>=4` unique cells: `+1 lượt` mỗi group.
- Nếu một resolve có nhiều group đủ điều kiện: cộng theo số group.
- Cascade sau drop/refill nếu sinh match group `>=4` thật thì cũng cộng lượt.
- Kiếm đỏ nổ lan không tự cộng lượt chỉ vì vùng nổ lớn; chỉ cộng nếu match scan tạo group `>=4`.
- Skill clear không tự cộng lượt; nếu sau skill cascade sinh match group `>=4` thì cascade đó mới cộng.

UI:
- Khi đang có `extraTurns >= 2`, sau mỗi lần tiêu thụ 1 lượt vẫn hiện lại `"Còn X lượt"` với số còn lại.
- Khi giảm về `0` thì không hiện badge còn lượt.

## 9. Pending reward tách khỏi reward packet

Board pending không phải item/equipment reward cuối trận:

- `pendingBoardExp`: sao xanh + nước/giọt tím.
- `pendingBoardGold`: raw gold/KEN.
- `pendingBoardQuan`: chỉ là giá trị hiển thị suy ra từ raw gold/KEN.
- `pendingBoardDamage`: damage kiếm/kiếm đỏ trong trận.

Rule:
- Thắng mới chốt EXP/Gold/Quan từ board.
- Thua xóa toàn bộ pending board reward.
- Không hiển thị counter tạm trong battle HUD nếu muốn bám memory Java.
- `lm[]`/`ll[]` reward item/equipment cuối trận là luồng riêng.

## 10. Skill và board mutation

Skill Java đi qua packet `nq.c == 5`:
- `nq.n`: skill id.
- `nq.r`: skill level/index.
- `nq.s/o/q/p`: target arrays/extra data.
- Client Java mutate board theo arrays server gửi rồi playback animation.

Kết luận:
- FE không tự đoán target list skill từ asset.
- BE nên trả target rows/cols/cells rõ ràng.
- Skill clear item board phải apply effect item bị clear; nếu clear trúng kiếm đỏ thì kiếm đỏ nổ `3x3` và chain.

## 11. Result lock / victory boundary

Khi đã pending victory hoặc `phase=over`:
- Cascade còn lại vẫn được resolve board/clear/drop/refill và phát collect/explosion FX.
- Sword/fire-sword damage không được kích thêm attack/trừ HP sau result lock.
- Monster turn phải abort nếu `pendingVictoryRef` hoặc enemy HP `<= 0`.

Mục tiêu: bàn cờ vẫn “ăn item” cho hết chain đang phát sinh, nhưng không có actor đánh thêm sau khi kết quả đã khóa.

## 12. Client/server port status

Các file chính:
- `client/src/screens/battle/core/BattleScreen.shared.ts`
- `client/src/screens/battle/core/BattleScreen.logic.ts`
- `client/src/screens/battle/hooks/useBattleMatchFlow.ts`
- `client/src/screens/battle/hooks/useBattleSwordAttacks.ts`
- `client/src/screens/battle/BattleScreen.tsx`
- `server/Twelve.Application/Battle/ReconstructedBattleBoardService.cs`
- `server/Twelve.Application/Battle/BattleTurnEngine.cs`

Đã có:
- Pool active `0,1,2,3,4,5,6,8`.
- `chess0/chess8` cùng category kiếm.
- Match scan packed/mask.
- Không natural spawn special mặc định.
- Special clear nếu node có sẵn.
- Kiếm đỏ nổ `3x3`, chain, apply item trong vùng.
- Sword damage scale theo `MinDamage/MaxDamage`; không còn hardcode `15`.
- Element percent local cho sword board.
- Extra turn theo số group `>=4`, cascade cũng tính.
- Badge `"Còn X lượt"` cập nhật sau khi tiêu thụ extra turn.
- Hoạt ảnh đánh thường 4 nhịp rồi mới quay về; damage chỉ apply 1 lần.

## 13. Checklist còn lại

- [ ] Đồng bộ hoàn toàn final damage order board với docs combat/server khi có authoritative endpoint.
- [ ] Nếu có packet log/replay, đối chiếu lại `nq.D`, `nq.F`, refill/no-move reset.
- [ ] Nếu chứng minh Java server bật natural special spawn ở mode nào đó, thêm feature flag riêng.
- [ ] Chuẩn hóa accumulator fixed-point cho MP/Power/EXP/Gold nếu cần cảm giác dài hạn chính xác hơn.
- [ ] Tách rõ API result để server chốt pending board reward khi thắng.