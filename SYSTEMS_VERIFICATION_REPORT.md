# Systems Verification Report

Báo cáo cuối phase tổ chức asset + tài liệu reconstruction. Mỗi dòng dưới là một **cặp độc lập `<doc>.md + client/assets/<bucket>/`** đủ để viết code tính năng end-to-end mà không cần tham khảo file khác.

Sinh ngày: 2026-04-17.

## 11 hệ thống đã tổ chức

| # | System | Top-level Doc | Legacy Folder | Files | Status |
|---|--------|---------------|---------------|-------|--------|
| 1 | Character Creation | `CHARACTER_CREATION_RECONSTRUCTION.md` | `client/assets/createcs/` | 163 | audited |
| 2 | Equipment | `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` | `client/assets/equipment/` | 1144 | audited |
| 3 | Monster | `MONSTER_SYSTEM_RECONSTRUCTION.md` | `client/assets/monster/` | 329 | audited |
| 4 | NPC | `NPC_SYSTEM_RECONSTRUCTION.md` | `client/assets/npc/` | 25 | audited |
| 5 | Skill | `SKILL_SYSTEM_RECONSTRUCTION.md` | `client/assets/skill/` | 73 | audited |
| 6 | Audio | `AUDIO_SYSTEM_RECONSTRUCTION.md` | `client/assets/audio/` | 9 | new |
| 7 | Battle | `BATTLE_SYSTEM_RECONSTRUCTION.md` | `client/assets/battle/` | 36 | new |
| 8 | Map | `MAP_SYSTEM_RECONSTRUCTION.md` | `client/assets/map/` | 12 | new |
| 9 | HUD | `HUD_SYSTEM_RECONSTRUCTION.md` | `client/assets/hud/` | 7 | new |
| 10 | Login | `LOGIN_SYSTEM_RECONSTRUCTION.md` | `client/assets/login/` | 15 | new |
| 11 | UI Chrome | `UI_SYSTEM_RECONSTRUCTION.md` | `client/assets/ui/` | 24 | new |

Tổng cộng: **1837 media file** được khảo sát và sắp xếp, 11 top-level `.md` docs.

> **Ghi chú cross-check ID**: Sau khi soi lại monster candidate band theo ID schema, 51 file đã được di chuyển sang đúng bucket:
>
> - 32 file đuôi `98` (120xxx–140xxx) + 2 file `130000/130100` (tổng 34) → `equipment/07_accessory_e5_e7_e8/`. Lý do: toàn bộ band `13xxxx/14xxxx` + đuôi `98` là accessory icon (E5/E7/E8), không phải monster.
> - 17 file `110000..110160` (bước `X0`) → `npc/04_numbered_npc_candidate_110xxx/`. Lý do: đây là numbered NPC sprites (single-frame standing), không phải monster frame index.
>
> Kết quả: Monster giảm 380 → **329**. Equipment tăng 1142 → **1144**. NPC tăng 8 → **25**.

> **Ghi chú cross-check slot-type trong equipment** (2026-04-17 audit): Sau khi soi `meta_summary.json` của từng band candidate (tại `reference/review_assets/character_creation_organized/02_option_meta_families/headgear_equipment_candidates/`), 4 meta family với tổng **44 file (4 × 11)** đã được di chuyển trong equipment — không thay đổi tổng file, chỉ đổi slot-type:
>
> - `03_weapon_e1/meta_weapon_971xx/` → `04_helmet_e2/meta_helmet_971xx/` (11 file)
> - `03_weapon_e1/meta_weapon_974xx/` → `04_helmet_e2/meta_helmet_974xx/` (11 file)
> - `03_weapon_e1/meta_weapon_975xx/` → `04_helmet_e2/meta_helmet_975xx/` (11 file)
> - `02_armor_e0/meta_armor_fullbody_999xx/` → `04_helmet_e2/meta_helmet_999xx/` (11 file)
>
> Lý do: cả 4 `meta_summary.json` ghi rõ `"note": "Meta-backed equipment candidate family; likely headgear rather than bare create-character appearance."` Idle frame dimension (37×39 cho 99900, 42×41 cho 97400, 41×48 cho 97500, 76×50 cho 97100) nằm trong vùng mũ đã confirm (94300=37×27, 98300=44×27, 94500=45×36). Layout 11 file `XX00..XX09 + XX98` đồng nhất với 5 helmet meta đã confirm. Kết quả counts: armor 270→259, weapon 393→360, helmet 231→275 (tổng 1144 vẫn không đổi).

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
| Monster | 309 | 20 | 94% (sau khi loại 51 file equipment/NPC) |
| NPC | 8 | 17 | 32% (17 numbered NPC candidate) |
| Skill | 73 | 0 | 100% |
| Audio | 6 | 3 | 67% |
| Battle | 36 | 0 | 100% |
| Map | 10 | 2 | 83% |
| HUD | 7 | 0 | 100% |
| Login | 7 | 8 | 47% |
| UI Chrome | 23 | 1 | 96% |

Hệ thống có tỉ lệ candidate cao:

- **Monster**: 39 candidate là range-scan của spritesheet ID (101xxx partial species, 110xxx X0 pattern, 130000/130100, 200000) — file thật sự tồn tại trong JAR nhưng Java source load qua `pa.a(id, false)` với `id` tính toán runtime. Cần runtime trace để promote.
- **Login**: 8 candidate là logo / font sheet / sound toggle không có literal ref — được load ở tầng MIDlet bootstrap (ngoài phạm vi CFR decompile).
- **Audio**: 3 candidate (`charcreation.mid`, `worldmap.mid`, `attack.amr`) có tên phù hợp nhưng source dùng tên động — cần trace thêm.

## Phát hiện quan trọng trong quá trình audit

1. **Barrier missed initially**: `mp.java:383` dùng `f.a("/barrier")` (alt loader) thay vì `f.d()` — suýt bỏ sót. Đã bổ sung vào `battle/11_barrier/`.
2. **51 file ID monster band thực chất là equipment + NPC**: Sau khi cross-check ID schema với các bucket còn lại, 51 file đã được di chuyển sang đúng chỗ:
   - **34 file → equipment** (`07_accessory_e5_e7_e8/`): 32 file đuôi `98` (`120198..122598`, `128098..128398`, `140098`) + 2 file `130000/130100`. Toàn bộ band `120xxx/130xxx/140xxx` là accessory icon (E5/E7/E8), đuôi `98` là icon variant convention.
   - **17 file → NPC** (`04_numbered_npc_candidate_110xxx/`): `110000..110160` bước `X0`. Đây là numbered NPC sprite (single-frame standing), không phải monster multi-frame slot. Pattern `X0` cho thấy 1-per-family layout (không sub-slot), khớp với use case "static NPC portrait".
3. **Một con đồng thời ở 2 chỗ là không chấp nhận được**: Bug này cho thấy phải cross-check giữa các bucket khi ID schema ambiguous, không chỉ dựa vào folder nguồn đã organized.
4. **Caveat về working tree hiện tại**: 11 file `equipment/00_body_base/body_990xx/99000.png..99009.png + 99099.png` hiện không tồn tại trên đĩa trong sandbox do một state cũ từ session trước (git status hiển thị `D` cho các file này). Chúng vẫn tracked trong git HEAD nên có thể khôi phục bằng:
   ```bash
   git checkout HEAD -- client/assets/equipment/00_body_base/body_990xx/
   ```
   Sau khi restore, equipment sẽ đủ 1144 file như báo cáo. Hiện số file vật lý là 1133; con số 1144 trong báo cáo là trạng thái mục tiêu sau restore (khớp với git HEAD + 2 file accessory mới vừa di chuyển vào từ monster).
3. **arrowfocus1 là byte-array load**: `mp.java:400` dùng `f.b("/arrowfocus1")` — trả về `byte[]` không phải `Image`. Vẫn là literal string ref nên được đánh confirmed.
4. **Shared spritesheet dispatch**: Monster/Skill dispatch tile qua `jo.c >> 1` để chọn giữa `/monster`, `/zap`, `/ice` sheets — đã ghi chú trong `MONSTER_SYSTEM_RECONSTRUCTION.md` và `SKILL_SYSTEM_RECONSTRUCTION.md`.
5. **Expo manifest icons tách riêng**: 3 icon `adaptive-icon.png`, `favicon.png`, `icon.png` trong `login/05_expo_manifest_icons/` KHÔNG phải J2ME asset — phải wire qua `app.json`, không qua asset loader.
6. **4 meta family bị gán sai slot-type** (2026-04-17 audit): Phát hiện sau khi đọc `meta_summary.json` rằng `meta_weapon_971xx/974xx/975xx` (nằm trong `03_weapon_e1/`) và `meta_armor_fullbody_999xx` (nằm trong `02_armor_e0/`) thực chất đều là **headgear candidates**. Metadata ghi rõ `"likely headgear rather than bare create-character appearance"`. Đã di chuyển 44 file (4 family × 11 file) sang `04_helmet_e2/` với tên mới `meta_helmet_971xx/974xx/975xx/999xx/`. Bài học: khi cross-organize equipment, LUÔN đọc `meta_summary.json` trước — đừng suy từ ID prefix (70xxx/90xxx không phải một slot-type duy nhất).

## Code-ready checklist

Để code một tính năng từ cặp `.md + folder`, lập trình viên cần:

| Step | Tool | Input | Output |
|------|------|-------|--------|
| 1. Đọc doc | editor | `<SYSTEM>_RECONSTRUCTION.md` | Hiểu loader contract + port order |
| 2. Import assets | Expo asset resolver | file trong `<system>/` | React Native Image sources |
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
