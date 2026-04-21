# Skill System Asset Set

This folder is organized around the legacy Java skill runtime.

The key rule is:

- separate skill-tree UI from battle runtime effects
- keep shared named assets separate from numeric family sheets
- group runtime assets by `family code` such as `1000`, `2005`, or `4007`
- do not rename numeric families into final player-facing skill names unless the old Java runtime or server catalog proves that mapping

Folder layout:

- `00_skill_tree_ui_confirmed`
- `01_battle_skill_shared_confirmed`
- `02_elemental_runtime_families`

Detailed technical reference:

- [SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md)
