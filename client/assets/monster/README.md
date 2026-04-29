# Monster Asset Set

All monster PNG files are stored **flat** in this directory, named by their
6-digit Java numeric resource ID (e.g. `100001.png`).

This mirrors how Java's `om` class loads monster sprites via integer resource IDs.
The server stores only the numeric code in `frame_paths`; the client constructs
the full path: `assets/monster/{code}.png`.

## ID Schema

Every confirmed monster asset is a 6-digit PNG named `AAAABC.png` where:

| Position | Meaning |
|----------|---------|
| `AAAA`   | Species family code (`1000`..`1007`) |
| `B`      | Slot index within the species (0..9) |
| `C`      | Frame / variant index inside the slot |

Example: `100351.png` -> species `1003`, slot `5`, frame `1`.

## Species Families

| Code | Slots | Sheet Family |
|------|-------|-------------|
| 1000 | 0-9   | Monster     |
| 1001 | 0-9   | Monster     |
| 1002 | 0-9   | Ice         |
| 1003 | 0-9   | Zap         |
| 1004 | 0-9   | Monster     |
| 1005 | 0-8   | Monster     |
| 1006 | 0-9   | Monster     |
| 1007 | 0-8   | Monster     |

## What we intentionally do NOT store here

- monster names (e.g. "slime", "goblin")
- map / zone mappings (e.g. "forest 1", "boss room")
- HP / attack stats
- level / tier labels

All of the above live on the server side (see `MONSTER_SYSTEM_RECONSTRUCTION.md`).

## Index

`index.csv` lists every PNG with:

| Column     | Meaning |
|------------|---------|
| file       | flat path under `client/assets/monster/` |
| source     | original path under `canonical_from_jar_png/offline/` |
| target     | same as file |
| confidence | `confirmed_monster_family` or `candidate_unknown` |
| note       | schema / pattern rationale |
