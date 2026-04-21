# Character Creation Asset Set

This folder is organized around the legacy Java compositor architecture.

Detailed implementation reference:

- [CHARACTER_CREATION_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/CHARACTER_CREATION_RECONSTRUCTION.md)

The key rule is:

- keep `confirmed runtime pieces` separate from default create-character option sets
- split visible face/hair options by `gender` first, then by `set/band`
- do not leave duplicate files in the old parent folder after reorganizing

## Folder layout

- `00_ui_confirmed`
  - direct create-character UI assets from `/createcs/*`
- `01_core_compositor`
  - assets directly proven to participate in the legacy create-character compositor
- `02_eye`
  - face/eye option families grouped by `male|female/set_xxx`
- `03_hair`
  - hair option families grouped by `male|female/set_xxx`

## Why this structure is better

The legacy Java client does not build create-character by browsing random PNG files.
It builds from:

- metadata families like `79899`, `79999`, `89999`
- base image sheets such as `99000 + frameGroup`
- additional option families resolved through server-provided IDs and color offsets

For the current React Native port, the practical unit is:

- gender bucket
- style/set band such as `500xx`, `600xx`
- color/tone offset inside that set

## Confidence levels

- `confirmed_runtime`
  - directly instantiated or referenced by the legacy Java create-character runtime
- `meta_group_candidate`
  - valid legacy `.meta` family with coherent sprite sheets, but semantic role is still not fully proven

## Important limitation

This is the strongest organization possible from the local legacy client and jar alone.
It is stronger than raw visual clustering, but it is still not identical to the original live server catalog.

## Coding order

Use this asset set in the following order:

1. `00_ui_confirmed`
2. `01_core_compositor`
3. `02_eye`
4. `03_hair`

Headgear and wearable meta families were reclassified into `equipment/04_helmet_e2`.
`02_eye` and `03_hair` are the current working default-option buckets for the recreated create-character screen.
