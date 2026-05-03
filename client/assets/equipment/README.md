# Equipment Legacy Asset Set

Chỉ chứa **asset liên quan hệ thống trang bị** đã được gom lại theo nhóm slot lớn để resolver dễ dùng. Logic slot gameplay vẫn phải lấy từ packet/model `ll.e`, không suy đoán ngược từ folder.

Reference: [EQUIPMENT_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/EQUIPMENT_SYSTEM_RECONSTRUCTION.md)
Source: [offline_by_id_or_name](/d:/Twelve/reference/review_assets/verified_semantic/offline_by_id_or_name)

## Folder Layout hiện tại

```
equipment/
  default/      <- default overlays khi không mặc đồ: weapon 798/799xx, helmet 899xx
  armor/        <- ll.e==0 visual armor/body overlays
  weapon/       <- ll.e==1 visual weapon overlays
  helmet/       <- ll.e==2 headgear/head-overlay candidates
  accessory/    <- ll.e==5,7,8 ring/amulet/accessory icons/effects; stats-only trong compositor hiện biết
  premium/      <- premium/full-set visual bands, server quyết định ll.e thật
  ui/           <- icon UI forge/equipment: broken_heart, star, slotlock, blacksmith, effblacksmith
  index.csv     <- manifest dạng CSV: file,id,numericId,band,group
  equipment_manifest.json <- manifest dạng JSON tương đương index.csv
```

Số lượng sau gom ngày 2026-05-03:

| Folder | PNG |
|--------|-----|
| `default/` | 32 |
| `armor/` | 363 |
| `weapon/` | 220 |
| `helmet/` | 187 |
| `accessory/` | 56 |
| `premium/` | 110 |
| `ui/` | 5 |
| **Tổng** | **974** |

Các folder audit cũ `01_default_overlays/`, `02_armor_e0/`, `03_weapon_e1/`, `04_helmet_e2/`, `07_accessory_e5_e7_e8/`, `08_premium_sets/`, `09_ui_icons/` đã được gộp/xóa để tránh cây thư mục quá sâu. Nếu cần truy vết band cũ, dùng `index.csv`/`equipment_manifest.json` và tài liệu reconstruction.

## Compositor Render Order (mb.java line 98-138)

```
Layer 1: Base body      = 99000 + frame
Layer 2: Hair           = appearance (NOT equipment)
Layer 3: Armor overlay  = ll.e==0 resId band + frame
Layer 4: Helmet overlay = ll.e==2 resId band + frame
Layer 5: Weapon overlay = ll.e==1 resId band + frame
```

## Per-band File Convention

- `{band}00` through `{band}09` — 10 animation frames
- `{band}98` — inventory icon (32x32), from `mb.a(int)`: `resId - resId%10 + 98`
- `{band}99` — `.meta` descriptor/icon family marker when present

## Resolver Rule

- Equipment band: `resId - (resId % 10)`
- Equipment icon: `band + 98`
- Equipment frames: `band + 0..9`
- Physical folder is only an asset lookup hint. Gameplay slot, equip/unequip legality, stats, durability, gender and rank must come from server/domain model reconstructed from `ll.java`, `ky.java`, `cz.java`, `go.java`.

## NOT Included / Needs Separate System

- `990xx` — Base body compositor layer, không lưu trong `equipment`
- `909xx, 910xx, 912xx` — Hair/appearance → character creation/appearance system
- `911xx, 913xx` — Special effects → not wearable
- `100xxx` — Consumable items → separate `lm` system
- `110xxx-140xxx` — Mixed legacy range; chỉ giữ trong equipment nếu match equipment icon convention/server evidence
- `1M/2M/4Mxxxxxxx` — Skill effects → `skill`

## Audit Notes

- `701xx-783xx` nằm trong `armor/` vì icon `xx98` và frame `xx00` cho thấy outfit/body overlay.
- `800xx-832xx` và `890xx-893xx` nằm trong `weapon/` vì icon `xx98` là kiếm/gậy/vũ khí.
- `500xx-609xx`, `904xx`, `908xx` và các meta-backed headgear candidates nằm trong `helmet/` khi có bằng chứng head overlay.
- Repair hammer `30099` đã được user xác nhận là item repair dùng cho mọi equipment, nhưng asset inventory đã tách khỏi domain equipment và nằm ở `client/assets/items/repair_hammer.png`.
- Kim Thạch/Huyết Thạch/Bùa/trứng/HP/MP là item/material/consumable, không lưu trong `client/assets/equipment/`. Asset inventory tương ứng nằm ở `client/assets/items/`.
- Tỉ lệ drop trứng từ monster và tỉ lệ/cost mở trứng là **Remake policy pending** nếu chưa có Java server evidence hoặc bảng policy do user chốt. Không tự suy ra từ icon.
