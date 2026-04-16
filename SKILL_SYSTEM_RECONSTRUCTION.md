# Skill System Reconstruction

This is the top-level entrypoint for the legacy skill restoration work.

If you want the deep technical reference, use:

- [reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md)

## Quick Position

The legacy skill system must be rebuilt from numeric family codes and Java runtime loaders.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/skill_legacy = working asset input for the new client`
- separate `skill tree UI` from `battle runtime effects`
- organize by `family code` such as `1000`, `2005`, or `4007`

## Main Working Folders

- [client/assets/skill_legacy](/d:/Twelve/client/assets/skill_legacy)
- [reference/review_assets/skill_system_organized](/d:/Twelve/reference/review_assets/skill_system_organized)
- [reference/redecoded/skill_runtime_manifest.csv](/d:/Twelve/reference/redecoded/skill_runtime_manifest.csv)

## Core Architecture

There are three layers:

1. `00_skill_tree_ui_confirmed`
2. `01_battle_skill_shared_confirmed`
3. `02_elemental_runtime_families`

This split is intentional.

The old client does not treat skill icons, shared named battle effects, and numeric runtime families as one flat asset pool.

## Confirmed Runtime Areas

Safe starting points:

- skill tree board assets from `/info/skilltree`, `/info/increase`, `/info/decrease`
- numeric family loaders in `mp.a(code)`
- shared named assets like `firerage`, `barrier`, `magicgate`, `miniexplosionfire`

## Family Groups

The organized asset set currently groups runtime families into:

- `group_100x_hoa_fire_likely`
- `group_200x_loi_thunder_likely`
- `group_400x_thuy_water_likely`

The code ranges are real.
The element labels are intentionally conservative and remain marked as `likely`.

## Port Order

1. Build the skill tree UI.
2. Resolve skill icons by `skillId * 1000`.
3. Build the runtime loader by `family_code`.
4. Implement one test family from each major group.
5. Only then promote final player-facing skill names.

## Next Practical Step

The next coding step should be a client-side skill asset resolver and preview screen for `skill_legacy`.
