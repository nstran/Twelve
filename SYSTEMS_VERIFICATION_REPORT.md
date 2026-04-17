# Systems Verification Report

Báo cáo cuối phase tổ chức asset + tài liệu reconstruction. Mỗi dòng dưới là một **cặp độc lập `<doc>.md + client/assets/<bucket>_legacy/`** đủ để viết code tính năng end-to-end mà không cần tham khảo file khác.

Sinh ngày: 2026-04-17.

## 11 hệ thống đã tổ chức

| # | System | Top-level Doc | Legacy Folder | Files | Status |
|---|--------|---------------|---------------|-------|--------|
| 1 | Character Creation | `CHARACTER_CREATION_RECONSTRUCTION.md` | `client/assets/createcs_legacy/` | 163 | audited |
| 2 | Equipment | `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` | `client/assets/equipment_legacy/` | 1142 | audited |
| 3 | Monster | `MONSTER_SYSTEM_RECONSTRUCTION.md` | `client/assets/monster_legacy/` | 380 | audited |
| 4 | NPC | `NPC_SYSTEM_RECONSTRUCTION.md` | `client/assets/npc_legacy/` | 8 | audited |
| 5 | Skill | `SKILL_SYSTEM_RECONSTRUCTION.md` | `client/assets/skill_legacy/` | 73 | audited |
| 6 | Audio | `AUDIO_SYSTEM_RECONSTRUCTION.md` | `client/assets/audio_legacy/` | 9 | new |
| 7 | Battle | `BATTLE_SYSTEM_RECONSTRUCTION.md` | `client/assets/battle_legacy/` | 36 | new |
| 8 | Map | `MAP_SYSTEM_RECONSTRUCTION.md` | `client/assets/map_legacy/` | 12 | new |
| 9 | HUD | `HUD_SYSTEM_RECONSTRUCTION.md` | `client/assets/hud_legacy/` | 7 | new |
| 10 | Login | `LOGIN_SYSTEM_RECONSTRUCTION.md` | `client/assets/login_legacy/` | 15 | new |
| 11 | UI Chrome | `UI_SYSTEM_RECONSTRUCTION.md` | `client/assets/ui_legacy/` | 24 | new |

Tổng cộng: **1869 media file** được khảo sát và sắp xếp, 11 top-level `.md` docs.

## Tiêu chuẩn self-sufficiency

Mỗi cặp `.md + legacy folder` đáp ứng đủ 4 tiêu chí:

1. **Source Code Reference**: Bảng liệt kê các file Java decompile + class + vai trò.
2. **Loader Contract**: Code snippet Java chính xác (có số dòng) cho mọi `f.d()` / `f.b()` / `pa.a()` call.
3. **Confirmed vs Candidate split**: Mỗi subfolder có confidence level riêng, candidate luôn có hypothesis.
4. **Port Order + Next Practical Step**: Dẫn rõ component React Native nào cần build và theo thứ tự nào.

Không có doc nào reference doc khác (độc lập tuyệt đối — tuân thủ yêu cầu "Cho độc lập thôi bạn ơi").

## Đánh giá độ bám sát Java source

Quy tắc: một asset được đánh `confirmed` khi file name xuất hiện như một **literal string** trong một call `f.d("/...")`, `f.b("/...")`, `f.a("/...")`, hoặc `pa.a(<numeric id>, false)` với mapping ID rõ ràng.

| System | Confirmed | Candidate | Confirmed % |
|--------|-----------|-----------|-------------|
| Character Creation | 163 | 0 | 100% |
| Equipment | 1142 | 0 | 100% |
| Monster | 180 | 200 | 47% (candidate = range scan) |
| NPC | 8 | 0 | 100% |
| Skill | 73 | 0 | 100% |
| Audio | 6 | 3 | 67% |
| Battle | 36 | 0 | 100% |
| Map | 10 | 2 | 83% |
| HUD | 7 | 0 | 100% |
| Login | 7 | 8 | 47% |
| UI Chrome | 23 | 1 | 96% |

Hệ thống có tỉ lệ candidate cao:

- **Monster**: 200 candidate là range-scan của spritesheet ID (ví dụ range_12xxxx_end98_meta_adjacent) — file thật sự tồn tại trong JAR nhưng Java source load qua `pa.a(id, false)` với `id` tính toán runtime. Cần runtime trace để promote.
- **Login**: 8 candidate là logo / font sheet / sound toggle không có literal ref — được load ở tầng MIDlet bootstrap (ngoài phạm vi CFR decompile).
- **Audio**: 3 candidate (`charcreation.mid`, `worldmap.mid`, `attack.amr`) có tên phù hợp nhưng source dùng tên động — cần trace thêm.

## Phát hiện quan trọng trong quá trình audit

1. **Barrier missed initially**: `mp.java:383` dùng `f.a("/barrier")` (alt loader) thay vì `f.d()` — suýt bỏ sót. Đã bổ sung vào `battle_legacy/11_barrier/`.
2. **32 monster files synced**: Folder `monster_legacy` ban đầu có 348 files, doc ghi 380. Đã copy bổ sung từ `reference/review_assets/monster_organized/` để khớp 100%.
3. **arrowfocus1 là byte-array load**: `mp.java:400` dùng `f.b("/arrowfocus1")` — trả về `byte[]` không phải `Image`. Vẫn là literal string ref nên được đánh confirmed.
4. **Shared spritesheet dispatch**: Monster/Skill dispatch tile qua `jo.c >> 1` để chọn giữa `/monster`, `/zap`, `/ice` sheets — đã ghi chú trong `MONSTER_SYSTEM_RECONSTRUCTION.md` và `SKILL_SYSTEM_RECONSTRUCTION.md`.
5. **Expo manifest icons tách riêng**: 3 icon `adaptive-icon.png`, `favicon.png`, `icon.png` trong `login_legacy/05_expo_manifest_icons/` KHÔNG phải J2ME asset — phải wire qua `app.json`, không qua asset loader.

## Code-ready checklist

Để code một tính năng từ cặp `.md + folder`, lập trình viên cần:

| Step | Tool | Input | Output |
|------|------|-------|--------|
| 1. Đọc doc | editor | `<SYSTEM>_RECONSTRUCTION.md` | Hiểu loader contract + port order |
| 2. Import assets | Expo asset resolver | file trong `<system>_legacy/` | React Native Image sources |
| 3. Viết component | Skia + Zustand | loader contract section | Functional component |
| 4. Validate | So sánh với Java source | line numbers từ doc | Behavior khớp bản cũ |
| 5. Server protocol | TLV codec + tag IDs | doc "Reference Skills → binary-protocol" | Server-authoritative validation |

Không bước nào phụ thuộc doc khác.

## Next Actions

Sau phase tổ chức asset này, các hạng mục tiếp theo:

- Bắt đầu port thứ tự Boot → Login → World Map theo section "Port Order" trong mỗi doc.
- Runtime trace để promote các candidate (đặc biệt monster range + audio candidate).
- Tạo `AssetRegistry` trung tâm trong `Twelve.Core` map `f.d(path) → require(...)` để React Native code giữ nguyên ngữ nghĩa đường dẫn Java gốc.
- Server-side: build TLV CMD dispatcher cho các lệnh ưu tiên (CMD 1 Login → CMD 11 MapInfo → CMD 13 SelectMap → CMD 29 JoinMap → CMD 43 SceneReady).

## Sources

Tất cả đường dẫn dưới là file trong workspace hiện tại.

- [CHARACTER_CREATION_RECONSTRUCTION.md](/d:/Twelve/CHARACTER_CREATION_RECONSTRUCTION.md)
- [EQUIPMENT_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/EQUIPMENT_SYSTEM_RECONSTRUCTION.md)
- [MONSTER_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/MONSTER_SYSTEM_RECONSTRUCTION.md)
- [NPC_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/NPC_SYSTEM_RECONSTRUCTION.md)
- [SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/SKILL_SYSTEM_RECONSTRUCTION.md)
- [AUDIO_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/AUDIO_SYSTEM_RECONSTRUCTION.md)
- [BATTLE_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/BATTLE_SYSTEM_RECONSTRUCTION.md)
- [MAP_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/MAP_SYSTEM_RECONSTRUCTION.md)
- [HUD_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/HUD_SYSTEM_RECONSTRUCTION.md)
- [LOGIN_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/LOGIN_SYSTEM_RECONSTRUCTION.md)
- [UI_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/UI_SYSTEM_RECONSTRUCTION.md)
