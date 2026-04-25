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
| [`07-arena-pvp-flow.md`](docs/player-character-reconstruction/07-arena-pvp-flow.md) | kiến trúc Khiêu Chiến/PvP: lobby, challenge, match session, result/rating/reward |
| [`08-level-stat-exp-and-element-balance.md`](docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md) | spec cân bằng level 250, điểm tiềm năng, EXP curve, stat, movement, resource và khắc hệ |

## Nguyên Tắc Chính

Không có server cũ, nên mọi logic server-side phải suy luận từ Java client: parser `ky`, encoder `ks/kw`, state bridge `go`/`com.mg.sq.a`, UI flows, map/battle runtime và asset offline. Phần nào Java client không đủ bằng chứng để đặt tên nghiệp vụ thì lưu raw id/tag, cấu hình rule server mới, và ghi rõ nguồn suy luận.

Riêng map trong remake là map mới, không có map cũ để bám theo. Runtime map server dùng `RuntimeMapCatalog` với kích thước native/tọa độ native; `MapDataStore` tile cũ chỉ còn là tham chiếu thử nghiệm, không phải nguồn truth cho player movement side-scroll.

Tiến độ map player hiện tại: player trên map đã dùng character thật và world-state DB; move được server clamp/echo theo tọa độ native map; client đã snap theo move ack canonical. Map train là PvE của chính player để đánh quái kiếm EXP, không có co-presence người chơi trên map. Người chơi chỉ gặp nhau qua flow Khiêu Chiến/PvP riêng. Phần chưa hoàn tất là collision platform nâng cao, server-authoritative monster AI và flow Khiêu Chiến/PvP.

Tiến độ reward hiện tại: battle result đã có `/battle/result`; server claim session một lần, cộng EXP/Quan khi thắng; khi thua hồi HP về đầy và trừ EXP theo defeat penalty của level hiện tại; không persist MP/Power/nộ vì reset theo từng trận; hiện code còn áp dụng level curve cũ `100 * (level - 1)^2`, nhưng spec cân bằng mới đã chốt trong `08-level-stat-exp-and-element-balance.md`: max level 250, không bonus điểm tiềm năng, EXP curve cày cuốc dài hơn và có khắc hệ; reward thắng đã có thêm drop item/equipment và lưu thẳng vào `PlayerInventory` / `PlayerEquipment`; client hiển thị bảng kết quả HP/EXP/Quan kèm loot text sau trận, rồi refresh runtime player đầy đủ.

Tiến độ character runtime hiện tại: status screen chỉ giữ vai trò hiển thị thông tin nhân vật kiểu Java cũ. Các action character nằm trong menu map dùng chung `Nhân Vật`: phân điểm tiềm năng, nâng skill theo hệ, mặc/tháo trang bị, dùng item hồi HP ngoài battle. Battle bootstrap giờ lấy skill đã học thật từ aggregate để khóa panel tuyệt chiêu theo player state hiện tại.

Tiến độ inventory/equipment hiện tại: màn túi đồ/trang bị đã chuyển sang flow gần Java `hh` hơn. Click ô đồ mở menu dọc kiểu softbar với các mục `Sửa chữa`, `Trang bị/Tháo`, `Chi Tiết`, `Nâng cấp`, `Rao bán`, `Vứt bỏ`; panel dưới chỉ hiển thị thông tin chi tiết. Mặc/tháo là preview local trước, sprite và stat preview được rebuild theo loadout nháp qua `/player/runtime/equipment/preview`; chỉ khi chọn `Cập nhật` mới commit full loadout qua `/player/runtime/equipment/loadout`. Các mục sửa chữa/nâng cấp/rao bán/vứt bỏ mới có UI shell, chưa có nghiệp vụ server đầy đủ.
