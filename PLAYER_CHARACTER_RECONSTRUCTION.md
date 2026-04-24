# Player / Character Reconstruction

Tài liệu chi tiết đã được tách vào folder [`docs/player-character-reconstruction`](docs/player-character-reconstruction/README.md) để dễ tra cứu và triển khai.

Trạng thái: **100% thực dụng trong phạm vi player/character server contract suy luận từ Java client**.

## Bộ Tài Liệu

| File | Nội dung |
|------|----------|
| [`README.md`](docs/player-character-reconstruction/README.md) | Tổng quan, nguyên tắc khi không có server cũ, source files, class map |
| [`01-implementation-plan-csharp.md`](docs/player-character-reconstruction/01-implementation-plan-csharp.md) | stat calculator, C# gaps, schema/service blueprint, port order |
| [`02-truth-payload-and-tags.md`](docs/player-character-reconstruction/02-truth-payload-and-tags.md) | `lh`, field map, bars, derived stats, tag map từ `ky.java` |
| [`03-structs-global-appearance.md`](docs/player-character-reconstruction/03-structs-global-appearance.md) | `ll/lb/lm/lv/df/dg/lt`, `go`, `com.mg.sq.a`, appearance/compositor |
| [`04-delta-ui-character-flows.md`](docs/player-character-reconstruction/04-delta-ui-character-flows.md) | delta packet, profile/status UI, inventory/equipment runtime, skill tree, create flow |
| [`05-protocol-trade-upgrade-market.md`](docs/player-character-reconstruction/05-protocol-trade-upgrade-market.md) | outbound/inbound protocol, trade, nâng cấp, kết hợp, market/sale |
| [`06-map-room-battle-runtime.md`](docs/player-character-reconstruction/06-map-room-battle-runtime.md) | map actor, room/profile runtime, battle actor/HUD/result dependencies |

## Nguyên Tắc Chính

Không có server cũ, nên mọi logic server-side phải suy luận từ Java client: parser `ky`, encoder `ks/kw`, state bridge `go`/`com.mg.sq.a`, UI flows, map/battle runtime và asset offline. Phần nào Java client không đủ bằng chứng để đặt tên nghiệp vụ thì lưu raw id/tag, cấu hình rule server mới, và ghi rõ nguồn suy luận.

Riêng map trong remake là map mới, không có map cũ để bám theo. Runtime map server dùng `RuntimeMapCatalog` với kích thước native/tọa độ native; `MapDataStore` tile cũ chỉ còn là tham chiếu thử nghiệm, không phải nguồn truth cho player movement side-scroll.

Tiến độ map player hiện tại: player trên map đã dùng character thật và world-state DB; move được server clamp/echo theo tọa độ native map; client đã snap theo move ack canonical. Map train là PvE của chính player để đánh quái kiếm EXP, không có co-presence người chơi trên map. Người chơi chỉ gặp nhau qua flow Khiêu Chiến/PvP riêng. Phần chưa hoàn tất là collision platform nâng cao, server-authoritative monster AI và flow Khiêu Chiến/PvP.

Tiến độ reward hiện tại: battle result đã có `/battle/result`; server claim session một lần, cộng EXP/Quan khi thắng, lưu HP/MP/Power còn lại, áp dụng level curve `100 * (level - 1)^2`, và client hiển thị bảng kết quả HP/EXP/Quan sau trận.
