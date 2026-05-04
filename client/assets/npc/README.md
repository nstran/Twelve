# NPC System Asset Set

This folder keeps the actual numbered NPC sprites directly at `client/assets/npc`.

The key rule is:

- `110000.png` through `110160.png` are the real numbered NPC sprites from `/offline/<id>`
- keep UI chrome, shared actor sheets, and interactive map objects in subfolders because they are not the numbered talking NPC sprites
- NPC identity, dialog, position and sprite id still come from server/catalog evidence; do not assign map roles without proof

Folder layout:

- `110000.png` .. `110160.png`
  - numbered NPC sprite IDs in the `110000..110160` band, `X0` step (17 files).
  - These came from `/offline/<id>.png`. Role: NPC (not monster, not equipment).
- `00_ui_confirmed`
  - dialog chrome loaded directly from `/dialog/*` by the legacy client
- `01_named_npc_confirmed`
  - dedicated blacksmith visual/effect evidence, not part of the numbered `110xxx` NPC set
- `02_shared_actor_sheets`
  - shared actor spritesheets used by the generic `jo` actor type indexed by `jo.c >> 1`, not numbered NPC sprites
- `03_interactive_map_objects`
  - animated map props that participate in NPC-like interaction (talk / enter) but are not talking NPC sprites

Detailed technical reference:

- [NPC_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/NPC_SYSTEM_RECONSTRUCTION.md)

## Runtime notes

The generic `jo` actor pipeline proven in Java is still:

1. Server sends an NPC list via TLV packet, populating `jo` records
   (tags `9`, `26`, `27`, `15`, `129`, `106`, `107`).
2. Client picks one of three shared root spritesheets based on `jo.c >> 1`:
   - `0` → `/monster`
   - `1` → `/zap`
   - default → `/ice`
3. Each shared spritesheet is a `1 row × 6 frame` walk-cycle strip.
4. A few NPCs (currently only the blacksmith) are hard-coded with a dedicated
   Java class and a dedicated sprite at the jar root.

The numbered `110xxx` sprites are real NPC sprite assets, but their exact map/name/mission roles still require server catalog, packet dump, or other evidence.

## Confidence levels

- `confirmed_runtime`
  - directly loaded by a legacy Java class with `f.d("/...")`
- `interactive_prop`
  - animated map object wired into the same actor pipeline as NPCs

## Important limitation

The server was the source of truth for which `jo.c` values exist and what
they looked like on screen. The three shared sheets (`monster`, `zap`, `ice`)
are the only NPC sprite surfaces visible inside the jar, so this is as
complete as the offline client allows.

## Implementation note

When coding the new NPC flow:

- use root `110xxx.png` files for numbered NPC sprite rendering once the server/catalog sends a matching sprite id
- keep `00_ui_confirmed` for dialog chrome
- keep `02_shared_actor_sheets` for Java generic `jo.c >> 1` actor rendering
- only after NPC talk / enter flow is parity-correct should `03_interactive_map_objects` be promoted into full gameplay use
