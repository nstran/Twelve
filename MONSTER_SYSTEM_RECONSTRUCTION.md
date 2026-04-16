# Monster System Reconstruction

This is the top-level entrypoint for the legacy monster restoration work.

If you want the deep technical reference, use:

- [reference/review_assets/monster_organized/README.md](/d:/Twelve/reference/review_assets/monster_organized/README.md)

## Quick Position

The legacy monster system is the only actor family in the jar where sprites
are addressed by 6-digit numeric IDs under `/offline/<id>.png`. It sits
between the character pipeline (which uses `79xxx`..`99xxx` parts) and the
NPC pipeline (which uses 3 shared spritesheets by `jo.c >> 1`).

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/monster_legacy = working asset input for the new client`
- monster identity (species id + map placement) is server-driven via the
  map-state packet, NOT driven by jar filenames
- organize by `numeric family` (species code + slot), NOT by guessed names

## Main Working Folders

- [client/assets/monster_legacy](/d:/Twelve/client/assets/monster_legacy)
- [reference/review_assets/monster_organized](/d:/Twelve/reference/review_assets/monster_organized)

## ID Schema

Every confirmed 6-digit PNG under `/offline/` follows the shape `AAAABC`:

| Position | Meaning |
|----------|---------|
| `AAAA`   | Species family code. `1000`..`1007` is confirmed. |
| `B`      | Slot index within the species (`0`..`9`). |
| `C`      | Frame / variant index inside the slot. |

Example: `100351.png` → species `1003`, slot `5`, frame `1`.

This schema is inferred from the `/offline/XX099.meta` marker convention
already used for characters and skills (one meta per 100-id block), and
confirmed by the per-slot frame bundling visible in the folder listing.

## Confirmed Species Families

Eight families have full or near-full 10-slot coverage. Names / roles are
intentionally left blank — they are server-catalog data.

| Species | Slot coverage | File count |
|---------|---------------|------------|
| 1000    | slot 0..9     | 40 |
| 1001    | slot 0..9     | 40 |
| 1002    | slot 0..9     | 40 |
| 1003    | slot 0..9     | 37 |
| 1004    | slot 0..9     | 40 |
| 1005    | slot 0..8     | 36 |
| 1006    | slot 0..9     | 40 |
| 1007    | slot 0..8     | 36 |

Total confirmed: `309 frames across 8 species`.

## Candidate Unknown Ranges

Everything else in the 6-digit numeric space that is not `100X` is kept as a
candidate until the server catalog confirms its role.

| Bucket | Files | Pattern / Heuristic |
|--------|-------|---------------------|
| `range_101xxx_partial_species`         | 19 | Same `100XYZ` shape, 9th family with partial slots |
| `range_110xxx_pattern_X0`              | 17 | All ids end in `0` (`110000`, `110010`, `110020`, ...) |
| `range_12xxxx_end98_meta_adjacent`     | 25 | All ids end in `98`. Sits next to `XX099.meta`. |
| `range_128xxx_end98_meta_adjacent`     |  4 | Same end-`98` pattern, different band. |
| `range_130xxx_candidate`               |  4 | Tiny 130xxx band, unknown role. |
| `range_140xxx_candidate`               |  1 | Single id `140098`. |
| `range_200xxx_candidate`               |  1 | Single id `200000`. |

Total candidate: `71 frames across 7 buckets`.

Grand total reconciled against the jar: `309 + 71 = 380 files`.

## Relationship with the NPC Pipeline

The monster pipeline and the NPC pipeline share the same rendering class
(`ki.java`) but NOT the same asset surface:

| System  | Asset surface |
|---------|---------------|
| Monster | Per-species 6-digit PNGs under `/offline/` |
| NPC     | 3 shared root sheets (`/monster`, `/zap`, `/ice`) + `/blacksmith` |

Note that the root-level `/monster` sheet loaded by `om.java` is the
shared NPC `jo.c >> 1 == 0` surface, not a per-species monster frame. See:

- [NPC_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/NPC_SYSTEM_RECONSTRUCTION.md)
- [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:84)

## What We Intentionally Do NOT Store

- monster names
- map / zone mappings
- HP / attack / defense
- level / tier labels
- drop tables

All of these live on the server. Guessing them now just creates rework once
the server catalog is re-authored.

## Regeneration

The folder is fully reproducible by running:

```bash
python3 /sessions/magical-happy-bohr/tools/organize_monsters.py
```

The script reads from `canonical_from_jar_png/offline/`, classifies every
6-digit PNG by the schema above, and rewrites `monster_organized/` +
`index.csv` deterministically.

## Port Order

1. Read in all 8 confirmed species as a dictionary of
   `{species_code: {slot: [frame_path, ...]}}`.
2. Render one species at its first slot as a smoke test through the generic
   `ki.java`-equivalent walk-cycle renderer.
3. Wire the server map-state packet so that a monster spawn at `(x, y)`
   with species `AAAA` resolves to the correct `species_AAAA/slot_<dir>/`
   folder.
4. Promote candidate ranges one bucket at a time, starting with
   `range_101xxx_partial_species` (same schema, just incomplete).
5. Only then decide whether end-98 and pattern-X0 ranges belong in the
   monster pipeline at all — they may be map markers, not monsters.

## Reference Skills

When implementing the monster pipeline, consult these project skills
(in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`          | Domain entity `Monster` in `Twelve.Core`, Clean Architecture layering |
| `clean-code/`            | Naming `MonsterFrameRef`, `MonsterSpecies`, file organization |
| `binary-protocol/`       | Monster spawn / state TLV tags when server sends the map catalog |
| `database-design/`       | Postgres schema for `Monsters`, JSONB for `SlotFrames` map, Dapper queries |
| `game-mechanics/`        | Battle rules, server-side validation of monster HP / damage |
| `frontend-design/`       | React Native + Skia walk-cycle renderer, 60 FPS sprite batching |
| `vulnerability-scanner/` | OWASP check after exposing any monster-related REST / TLV endpoint |
| `api-patterns/`          | Contract shape for any monster-catalog REST endpoint |

Note: there are no monster **names** in any skill. Names are
server-catalog data and must NOT be invented at the client / asset layer.

## Next Practical Step

The next coding step should be a monster asset loader that:

- reads `reference/review_assets/monster_organized/index.csv`,
- builds an in-memory map `species_code -> slot -> [frame_path]`,
- exposes a `GetFrames(species, slot)` API for the renderer,
- and returns `null` for any id that is still in
  `02_candidate_unknown_ranges`, to force those through an explicit
  promotion step rather than silently rendering them.
