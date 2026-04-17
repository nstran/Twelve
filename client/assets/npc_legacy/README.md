# NPC System Asset Set

This folder is organized around the legacy Java NPC / actor runtime.

The key rule is:

- separate `named NPCs with dedicated sprites` from `shared actor spritesheets`
- keep `interactive map objects` (gates, portals) separate from talking NPCs
- do NOT organize NPCs by `/offline/<id>` numeric ranges — the legacy client does not store NPC sprites there
- NPC identity, dialog, position and sprite-type come from the server via TLV tags on top of a very small pool of shared spritesheets

Folder layout:

- `00_ui_confirmed`
  - dialog chrome loaded directly from `/dialog/*` by the legacy client
- `01_named_npc_confirmed`
  - NPCs that have a dedicated sprite sheet and a dedicated Java handler class
- `02_shared_actor_sheets`
  - shared spritesheets used by the generic `jo` actor type indexed by `jo.c >> 1`
- `03_interactive_map_objects`
  - animated map props that participate in NPC-like interaction (talk / enter) but are not talking NPCs
- `04_numbered_npc_candidate_110xxx`
  - numbered NPC sprite IDs in the `110000..110160` band, `X0` step (17 files).
    These came from `/offline/<id>.png`. Role: NPC (not monster, not equipment).
    Kept as candidate until the server catalog confirms per-ID identity.

Detailed technical reference:

- [NPC_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/NPC_SYSTEM_RECONSTRUCTION.md)

## Why this structure is different from character / skill

The legacy Java client does NOT build NPCs by browsing `/offline/<id>.mg` the way it does for character parts (`79xxx` - `99xxx`) or skills (`1xxx`, `2xxx`, `4xxx`).

Instead, the runtime NPC pipeline is:

1. Server sends an NPC list via TLV packet, populating `jo` records
   (tags `9`, `26`, `27`, `15`, `129`, `106`, `107`).
2. Client picks one of three shared root spritesheets based on `jo.c >> 1`:
   - `0` → `/monster`
   - `1` → `/zap`
   - default → `/ice`
3. Each shared spritesheet is a `1 row × 6 frame` walk-cycle strip.
4. A few NPCs (currently only the blacksmith) are hard-coded with a dedicated
   Java class and a dedicated sprite at the jar root.

Because of this, the stable organizational unit for NPCs is the
**role of the asset inside the runtime** (dialog UI / named NPC / shared sheet / map prop),
not a numeric family.

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

- start from `00_ui_confirmed` and render an NPC dialog frame
- wire `01_named_npc_confirmed/blacksmith` first — it has the richest handler (`le.java`, `hc.java`)
- then implement the generic `jo` actor using `02_shared_actor_sheets`
- only after NPC talk / enter flow is parity-correct should `03_interactive_map_objects` be promoted into full gameplay use
