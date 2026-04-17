# Equipment Legacy Asset Set

Chỉ chứa **trang bị nhân vật mặc được** — được xác nhận từ code Java runtime.

Reference: [EQUIPMENT_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/EQUIPMENT_SYSTEM_RECONSTRUCTION.md)
Source: [offline_by_id_or_name](/d:/Twelve/reference/review_assets/verified_semantic/offline_by_id_or_name)

## Folder Layout (theo ll.e slot type)

```
equipment_legacy/
  01_default_overlays/  <- mb.java:712-715 — defaults khi khong mac do
  02_armor_e0/          <- visual audit + icon check: armor/body overlays 701xx-783xx
  03_weapon_e1/         <- visual audit + icon check: weapon overlays 800xx-832xx, 890xx-893xx
  04_helmet_e2/         <- headgear/head-overlay candidates 500xx-609xx, 904xx, 908xx
                           + meta headgear/visual candidate bands 920/924/925/940/941/942/943/971/974/975/980/981/982/983/999
  05_boot_e3/           <- hien tai chua co band boot confirmed sau khi audit icon
  06_mount_e4/          <- hien tai chua co band mount confirmed sau khi audit icon
  07_accessory_e5_e7_e8/ <- ll.e==5,7,8 — Nhan/Bua (no visual)
  08_premium_sets/      <- Full premium sets (server-assigned type)
  09_ui_icons/          <- broken_heart, star, slotlock, blacksmith
```

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
- `{band}99` — `.meta` descriptor (when present)

## NOT Included (not equipment)

- `990xx` — Base body compositor layer, khong luu trong `equipment_legacy`
- `909xx, 910xx, 912xx` — Hair/appearance → `createcs_legacy`
- `911xx, 913xx` — Special effects → not wearable
- `100xxx` — Consumable items → separate `lm` system
- `110xxx-140xxx` — Map/NPC → map system
- `1M/2M/4Mxxxxxxx` — Skill effects → `skill_legacy`

## Audit Notes

- `701xx-783xx` da duoc chuyen tu `03_weapon_e1` sang `02_armor_e0` vi icon `xx98` va frame `xx00` deu cho thay day la outfit/body overlay.
- `800xx-832xx` va `890xx-893xx` da duoc chuyen vao `03_weapon_e1` vi icon `xx98` la kiem/gay/vu khi.
- `500xx-609xx`, `904xx`, `908xx` da duoc dua vao `04_helmet_e2` duoi nhan `headgear_candidate_*` vi frame runtime la head overlay, khong phai body armor.
