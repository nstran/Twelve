# Character Creation Asset Set

This folder is organized around the legacy Java compositor architecture.

Detailed implementation reference:

- [CHARACTER_CREATION_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/CHARACTER_CREATION_RECONSTRUCTION.md)

The key rule is:

- organize by `meta family` when possible
- keep `confirmed runtime pieces` separate from `meta-backed candidate families`
- keep only bare create-character appearance candidates here

## Folder layout

- `00_ui_confirmed`
  - direct create-character UI assets from `/createcs/*`
- `01_core_compositor`
  - assets directly proven to participate in the legacy create-character compositor
- `02_option_meta_families`
  - candidate appearance families grouped by legacy `.meta` units

## Why this structure is better

The legacy Java client does not build create-character by browsing random PNG files.
It builds from:

- metadata families like `79899`, `79999`, `89999`
- base image sheets such as `99000 + frameGroup`
- additional option families resolved through server-provided IDs

That means the most stable organizational unit is the `.meta` family, not a guessed visual label.

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
3. `02_option_meta_families`

Headgear and wearable meta families were reclassified into `equipment_legacy/04_helmet_e2`.
Do not treat the remaining `candidate` groups as final semantic labels yet.
