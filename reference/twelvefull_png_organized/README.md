# Twelve Full PNG Organized

This folder is a second-pass organization of image assets decoded from `reference/twelvefull.jar`.

Verdict:
- The named asset groups are in good shape: `monster`, `skill_effects`, `board_chess`, `ui_hud`, `fonts_logos`, `items_objects`, `social_emotes`.
- The old `character_npc` label was too confident. It is now renamed to `character_resources`.
- Numeric `offline/*.mg` assets are cache resources by ID, not original semantic folders.

Confidence guide:
- `high`: directly supported by jar path or explicit asset name usage in decompiled client code.
- `medium`: strongly suggested by create-character composition logic, but not perfectly named in the original client.
- `low`: numeric resource cache where the exact role is not provable from filename alone.

Character-related groups:
- `character_resources/body_990xx_confirmed`
  Meaning: confirmed body-part base sheets used by the body compositor.
- `character_resources/skin_899xx_likely`
  Meaning: likely skin-color related resources used by create-character composition.
- `character_resources/appearance_group_798xx_uncertain`
  Meaning: appearance resources used by the create-character flow, exact semantic label still uncertain.
- `character_resources/appearance_group_799xx_uncertain`
  Meaning: appearance resources used by the create-character flow, exact semantic label still uncertain.
- `character_resources/create_screen`
  Meaning: background and stone assets for the create-character UI.

Important limitation:
- `offline_unclassified` is not a failure of organization alone. Those files are bundled by numeric cache ID in the original client.
- To split more of that folder into `npc`, `map object`, `equipment`, or `effect`, we need packet captures, more runtime reconstruction, or visual review of contact sheets.

Inventory:
- See `index.csv` for the current path, group, confidence, and rationale for every PNG.
