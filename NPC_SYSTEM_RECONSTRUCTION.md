# NPC System Reconstruction

This is the top-level entrypoint for the legacy NPC restoration work.

If you want the deep technical reference, use:

- [reference/review_assets/npc_organized/README.md](/d:/Twelve/reference/review_assets/npc_organized/README.md)

## Quick Position

The legacy NPC system must be rebuilt from the `jo` actor model and the shared spritesheet architecture, not from guessed numeric IDs in `/offline/`.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/npc_legacy = working asset input for the new client`
- NPC identity is server-driven via TLV tags, NOT asset-file-driven
- organize by `runtime role` (ui / named / shared sheet / map prop), NOT by `/offline/<id>`

## Main Working Folders

- [client/assets/npc_legacy](/d:/Twelve/client/assets/npc_legacy)
- [reference/review_assets/npc_organized](/d:/Twelve/reference/review_assets/npc_organized)

## Confirmed Runtime Pieces

These are safe to build around first:

- `/blacksmith` and `/effblacksmith`
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:13)
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:14)
  - Upgrade UI driver: [hc.java](/d:/Twelve/reference/redecoded/decompiled/hc.java:37) — label "Nâng cấp"
- `/monster`, `/zap`, `/ice` shared actor sheets
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:84)
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:85)
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:86)
- generic actor class: [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java:11)
- NPC data record: [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java:4)
- NPC list decoder: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1595)
- Map actor spawn: [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:485)

## NPC Data Contract (`jo`)

The `jo` class is the canonical NPC record shape.
It is populated from the server map-state packet in `ky.java`:

| Field | TLV Tag | Meaning (proposed) |
|-------|---------|--------------------|
| jo.a  | 9       | NPC id / name key |
| jo.b  | 26      | Dialog / speech text |
| jo.c  | 15      | Type byte. `jo.c >> 1` picks the shared spritesheet |
| jo.d  | 27      | Numeric parameter (likely hp / state) |
| jo.e  | 129     | Numeric parameter (likely tile x) |
| jo.f  | 106     | Numeric parameter (likely tile y) |
| jo.g  | 107     | Direction / facing flag |

The server, not the jar, is the source of truth for which NPCs exist on a map.

## Shared Spritesheet Dispatch

Inside `om.a(int, int, jo, Image)`, the sprite source is chosen from `jo.c >> 1`:

| jo.c >> 1 | Image loaded | Vertical offset |
|-----------|--------------|-----------------|
| 0         | `/monster`   | 14              |
| 1         | `/zap`       | 5               |
| default   | `/ice`       | 13              |

Each sheet is treated as `1 row × 6 frame` walk strip by `ki.java`.
`ki.k` holds the sheet, `ki.o = width / 6` is the frame width.

## Menu Commands on Map

From `om.java` (around line 150-160) — these are the verbs the player can
aim at an NPC or prop:

| Label          | Command ID |
|----------------|------------|
| Vào            | 101        |
| Nói Chuyện     | 107        |
| Tiếp tục       | 118        |
| Nhặt           | 110        |

"Nói Chuyện" (107) is the canonical talk action.

## Candidate Classes

These are valid interactive map actors and should stay available, but do not
yet deserve a "full NPC" label:

- `ln.java` — `/magicgate` — 4-frame teleport gate
- `gate.png` — generic gate asset

Do not rename these to final labels like `quest_giver`, `warp_portal`, or
`shop_entry` until the server packet catalog confirms behavior.

## Folder Reading Order

Use this order when working:

1. [00_ui_confirmed](/d:/Twelve/reference/review_assets/npc_organized/00_ui_confirmed)
2. [01_named_npc_confirmed](/d:/Twelve/reference/review_assets/npc_organized/01_named_npc_confirmed)
3. [02_shared_actor_sheets](/d:/Twelve/reference/review_assets/npc_organized/02_shared_actor_sheets)
4. [03_interactive_map_objects](/d:/Twelve/reference/review_assets/npc_organized/03_interactive_map_objects)

## Port Order

1. Rebuild NPC dialog chrome from `dialog/corner.png`.
2. Port the blacksmith NPC first — it is the only NPC with a dedicated sprite + dedicated handler.
3. Port the generic `jo` actor using `monster.png`, `zap.png`, `ice.png` as the three shared spritesheets, indexed by `jo.c >> 1`.
4. Verify the four command verbs (Vào, Nói Chuyện, Tiếp tục, Nhặt) route through the TLV command dispatcher.
5. Add `magicgate` and `gate` once talking NPCs are stable.

## Next Practical Step

The next coding step should be a client-side NPC renderer that:

- takes a `jo`-shaped record from the server,
- picks one of the three shared sheets by `jo.c >> 1`,
- draws a 6-frame walk strip at the tile position,
- and opens a dialog box on the `Nói Chuyện` (107) command.

That is the point where candidate families (gates, portals, future named NPCs) can start being promoted into final semantic roles.
