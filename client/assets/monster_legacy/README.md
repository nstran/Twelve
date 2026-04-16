# Monster Asset Set

This folder is organized around the 6-digit monster / actor IDs that live under
`canonical_from_jar_png/offline/` and cannot be disambiguated by the legacy Java
classes alone.

The key rule is:

- separate `confirmed species families` (full 10-slot layout) from
  `candidate unknown ranges` (partial / pattern-only)
- group confirmed species by their 4-digit family code (`1000`..`1007`)
- inside each species, split by `slot` 0..9 using the `100XYZ` schema
- do NOT invent monster names or map locations — those are server-driven
  and cannot be recovered from the offline jar

Folder layout:

- `01_confirmed_species_families`
  - 8 species (`1000`..`1007`), each with `slot_0`..`slot_9`
- `02_candidate_unknown_ranges`
  - partial / non-standard 6-digit ranges grouped by prefix or end-pattern

Detailed technical reference:

- [MONSTER_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/MONSTER_SYSTEM_RECONSTRUCTION.md)

## ID Schema

Every confirmed monster asset is a 6-digit PNG named `AAAABC.png` where:

| Position | Meaning |
|----------|---------|
| `AAAA`   | Species family code (`1000`..`1007`) |
| `B`      | Slot index within the species (0..9) |
| `C`      | Frame / variant index inside the slot |

Example: `100351.png` → species `1003`, slot `5`, frame `1`.

This schema is inferred from the jar's `/offline/*.meta` neighbour convention
(`XX099.meta` describes the `XX000..XX098` range) and confirmed by the per-slot
frame bundling already visible in the offline folder.

## Why this structure is different from NPC

Monsters differ from NPCs in two important ways:

1. Monsters exist as distinct numeric IDs in `/offline/<id>.png`. NPCs do NOT
   — the legacy client draws NPCs from 3 shared spritesheets + 1 named blacksmith.
2. Monster identity is still server-driven (the map catalog decides which
   species spawns where), but the sprite surface itself is per-ID. NPC sprite
   surfaces are shared by `jo.c >> 1`.

So the monster folder keeps a **numeric family grouping** just like
`character_creation_organized/` and `skill_system_organized/`, while the NPC
folder is keyed by **runtime role**.

## Confidence levels

- `confirmed_monster_family`
  - 4-digit species code is in `{1000..1007}` with full 10-slot coverage
- `candidate_unknown`
  - 6-digit id does not fit the `100X` schema or is only partially populated

## What we intentionally do NOT store here

- monster names (e.g. "slime", "goblin")
- map / zone mappings (e.g. "forest 1", "boss room")
- HP / attack stats
- level / tier labels

All of the above live on the server side. The offline jar does not carry
that metadata, and guessing them here would just create rework once the
server catalog is re-authored.

## Index

`index.csv` lists every copied PNG with:

| Column     | Meaning |
|------------|---------|
| source     | path under `canonical_from_jar_png/offline/` |
| target     | path under `client/assets/monster_legacy/` |
| confidence | `confirmed_monster_family` or `candidate_unknown` |
| note       | schema / pattern rationale for the bucket |

## Regeneration

The folder is fully reproducible by running:

```bash
powershell -File tools/organize_monsters.ps1
```

The script reads from `canonical_from_jar_png/offline/` and rebuilds this
folder + `index.csv` deterministically.
