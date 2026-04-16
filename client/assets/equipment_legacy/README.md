# Equipment Legacy Asset Set

Chỉ chứa **trang bị nhân vật mặc được** — được xác nhận từ code Java runtime.

Reference: [EQUIPMENT_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/EQUIPMENT_SYSTEM_RECONSTRUCTION.md)
Source: [offline_by_id_or_name](/d:/Twelve/reference/review_assets/verified_semantic/offline_by_id_or_name)

## Folder Layout (theo ll.e slot type)

```
equipment_legacy/
  00_body_base/         <- mb.java:108 — base body (99000+frame)
  01_default_overlays/  <- mb.java:712-715 — defaults khi khong mac do
  02_armor_e0/          <- ll.e==0 — Giap/Ao, nArray[0], composited
  03_weapon_e1/         <- ll.e==1 — Vu Khi, nArray[1], composited
  04_helmet_e2/         <- ll.e==2 — Mu/Non, nArray[2], composited
  05_boot_e3/           <- ll.e==3 — Giay (stored, NOT composited)
  06_mount_e4/          <- ll.e==4 — Ngua/Khien (candidate)
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

- `909xx, 910xx, 912xx` — Hair/appearance → `createcs_legacy`
- `911xx, 913xx` — Special effects → not wearable
- `100xxx` — Consumable items → separate `lm` system
- `110xxx-140xxx` — Map/NPC → map system
- `1M/2M/4Mxxxxxxx` — Skill effects → `skill_legacy`
