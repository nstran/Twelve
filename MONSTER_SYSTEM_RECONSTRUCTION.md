# Monster System Reconstruction

Tài liệu khôi phục hệ thống quái vật (monster) từ Java client cũ.

If you want the deep technical reference, use:

- [client/assets/monster_legacy/README.md](/d:/Twelve/client/assets/monster_legacy/README.md)

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [pa.java](/d:/Twelve/reference/redecoded/decompiled/pa.java) | `pa` | Offline loader — `pa.a(id, false)` resolves `/offline/<id>.png` |
| [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java) | `ki` | Actor renderer — 1 row × 6 frame walk strip |
| [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java) | `jo` | Actor data record (`.c` type byte drives sheet choice) |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | Map dispatcher — decides which sheet a `jo` uses |
| [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network decoder — parses spawn lists into `jo[]` |

The monster system does not have a dedicated Java class. Monsters are drawn
through `ki.java`, differentiated by the 6-digit asset ID the server sends.

## Quick Position

Monster sprites are addressed by 6-digit numeric IDs under `/offline/<id>.png`.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/monster_legacy = working asset input for the new client`
- monster identity (species id + map placement) is server-driven via the
  map-state packet, NOT driven by jar filenames
- organize by `numeric family` (species code + slot), NOT by guessed names

## Main Working Folders

- [client/assets/monster_legacy](/d:/Twelve/client/assets/monster_legacy)
- [reference/review_assets/monster_organized](/d:/Twelve/reference/review_assets/monster_organized) — raw organized source

## ID Schema

Every confirmed 6-digit PNG under `/offline/` follows the shape `AAAABC`:

| Position | Meaning |
|----------|---------|
| `AAAA`   | Species family code. `1000`..`1007` is confirmed. |
| `B`      | Slot index within the species (`0`..`9`). |
| `C`      | Frame / variant index inside the slot. |

Example: `100351.png` → species `1003`, slot `5`, frame `1`.

This schema is inferred from the `/offline/XX099.meta` marker convention
(one meta per 100-id block), and confirmed by the per-slot frame bundling
visible in the folder listing.

Monster IDs are always **6 digits**. Any `/offline/` file with a different
digit length belongs to a different system — see Related Docs.

## Rendering Contract

The sprite is drawn through `ki.java` with a hard-coded 6-frame strip:

```java
// ki.java line 58-65
public ki(Image object, int n2, int n3, jo jo2, lh lh2, Image image) {
    int n4 = 6;                              // 6 frames
    n3 = 1;                                  // 1 row (overridden from arg)
    this.k = object;
    this.o = object.getWidth()  / n4;        // frame width  = w / 6
    this.p = object.getHeight() / n3;        // frame height = h / 1
}
```

Every monster PNG — even candidate ones — MUST be laid out as a single
horizontal strip of 6 frames.

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
| `range_200xxx_candidate`               |  1 | Single id `200000`. |

Total candidate: `20 frames across 2 buckets`.

Grand total reconciled against the jar: `309 + 20 = 329 files`.

### Excluded (moved to equipment_legacy / npc_legacy)

After cross-checking IDs against the other bundles, the following ranges
that were initially staged here were confirmed to belong elsewhere and
have been MOVED out of `monster_legacy`:

| Former bucket | Files | Correct bundle | Reason |
|---------------|-------|----------------|--------|
| `range_12xxxx_end98_meta_adjacent` | 25 | `equipment_legacy/07_accessory_e5_e7_e8/` | End-`98` = accessory-icon convention |
| `range_128xxx_end98_meta_adjacent` |  4 | `equipment_legacy/07_accessory_e5_e7_e8/` | Same end-`98` pattern |
| `range_140xxx_candidate`           |  1 | `equipment_legacy/07_accessory_e5_e7_e8/` | `140098` is accessory, not monster |
| `range_130xxx_candidate`           |  4 | `equipment_legacy/07_accessory_e5_e7_e8/` | Entire `130xxx` band is accessory |
| `range_110xxx_pattern_X0`          | 17 | `npc_legacy/04_numbered_npc_candidate_110xxx/` | Numbered NPC sprites, `X0` step |

Total relocated: **51 files**. MUST NOT be re-copied into `monster_legacy/`.
The `98` suffix (for 120xxx-140xxx) is the equipment-icon convention;
the `X0` step (for 110xxx) is the numbered-NPC stride. Neither is a
monster frame index.

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
2. Render one species at its first slot as a smoke test through the
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

- reads `client/assets/monster_legacy/index.csv`,
- builds an in-memory map `species_code -> slot -> [frame_path]`,
- exposes a `GetFrames(species, slot)` API for the renderer,
- and returns `null` for any id that is still in
  `02_candidate_unknown_ranges`, to force those through an explicit
  promotion step rather than silently rendering them.
