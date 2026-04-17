# NPC System Reconstruction

Tài liệu khôi phục hệ thống NPC từ Java client cũ.

If you want the deep technical reference, use:

- [client/assets/npc_legacy/README.md](/d:/Twelve/client/assets/npc_legacy/README.md)

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java) | `jo` | NPC / actor data record (7 fields) |
| [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java) | `ki` | Generic actor renderer — 1 row × 6 frame walk strip |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | Map controller — shared-sheet dispatch via `jo.c >> 1` |
| [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network decoder — parses NPC spawn list from TLV packet |
| [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java) | `le` | Blacksmith NPC (special: dedicated sprite + handler) |
| [hc.java](/d:/Twelve/reference/redecoded/decompiled/hc.java) | `hc` | Upgrade UI — "Nâng cấp" button driven by blacksmith |
| [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java) | `ln` | Magic-gate prop — 4-frame teleport animation |

## Quick Position

The legacy NPC system must be rebuilt from the `jo` actor model and the shared spritesheet architecture, not from guessed numeric IDs in `/offline/`.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/npc_legacy = working asset input for the new client`
- NPC identity is server-driven via TLV tags, NOT asset-file-driven
- organize by `runtime role` (ui / named / shared sheet / map prop), NOT by `/offline/<id>`

## Main Working Folders

- [client/assets/npc_legacy](/d:/Twelve/client/assets/npc_legacy)
- [reference/review_assets/npc_organized](/d:/Twelve/reference/review_assets/npc_organized) — raw organized source

## Confirmed Runtime Pieces

These are safe to build around first:

- `/blacksmith` and `/effblacksmith`
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:13)
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:14)
  - Upgrade UI driver: [hc.java](/d:/Twelve/reference/redecoded/decompiled/hc.java:37) — label "Nâng cấp"
- `/monster`, `/zap`, `/ice` shared actor sheets (NPC surfaces, root-level)
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:84)
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:85)
  - [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:86)
- generic actor class: [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java:11)
- NPC data record: [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java:4)
- NPC list decoder: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1595)
- Map actor spawn: [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:485)

## NPC Data Contract (`jo`)

The `jo` class is the canonical NPC record shape. Full Java definition:

```java
// jo.java
public final class jo {
    public String a;     // NPC id / name key
    public String b;     // display text (name shown above head / dialog)
    public int    c;     // type byte — jo.c >> 1 picks shared sheet
    public int    d;     // numeric parameter (likely hp / state)
    public int    e;     // tile x
    public int    f;     // tile y
    public int    g = 0; // hostility / direction flag — used by ki for color
}
```

It is populated from the server map-state packet in
[ky.java:1595-1605](/d:/Twelve/reference/redecoded/decompiled/ky.java:1595):

```java
jo jo2 = new jo();
jo2.a = ku2.b(n2);                               // unnamed first tag
jo2.b = ku2.d((short)26, n2, n4);                // tag 26: text
jo2.d = ku2.a((short)27, n2, n4, 0);             // tag 27: numeric
jo2.c = ku2.a((short)15, n2, n4, (byte)0);       // tag 15: type byte
jo2.e = ku2.a((short)129, n2, n4, 0);            // tag 129: x
jo2.f = ku2.a((short)106, n2, n4, 0);            // tag 106: y
jo2.g = ku2.a((short)107, n2, n4, (byte)0);      // tag 107: flag
```

| Field | TLV Tag | Meaning |
|-------|---------|---------|
| jo.a  | (first) | NPC id / name key |
| jo.b  | 26      | Display text (name / dialog label) |
| jo.c  | 15      | Type byte. `jo.c >> 1` picks the shared spritesheet |
| jo.d  | 27      | Numeric parameter (likely hp / state) |
| jo.e  | 129     | Tile x |
| jo.f  | 106     | Tile y |
| jo.g  | 107     | Hostility / direction flag. `ki` uses it for name color: 1=red, 2=dark-gray, default=auto |

The server, not the jar, is the source of truth for which NPCs exist on a map.

### `ki.java` uses `jo.g` for name tint

From [ki.java:74-97](/d:/Twelve/reference/redecoded/decompiled/ki.java:74):

```java
switch (jo2.g) {
    case 1: C = 0xFF0000;   break;  // red — hostile
    case 2: C = 9008914;    break;  // gray
    default:
        // auto-tint based on level delta (d - lh.G)
}
```

## Shared Spritesheet Dispatch

Inside `om.java` around [line 491](/d:/Twelve/reference/redecoded/decompiled/om.java:491),
the sprite source is chosen from `jo.c >> 1`:

```java
switch (((jo)object).c >> 1) {
    case 0:  image = this.r;  n4 = 14; break;  // /monster
    case 1:  image = this.s;  n4 = 5;  break;  // /zap
    default: image = this.t;  n4 = 13;         // /ice
}
object = new ki(image, 1, 6, (jo)object, go.k, (Image)object2);
```

The 3 sheets are loaded once in the map-controller constructor
([om.java:84-86](/d:/Twelve/reference/redecoded/decompiled/om.java:84)):

```java
this.r = f.d("/monster");   // hostile / enemy actors
this.s = f.d("/zap");       // lightning / special
this.t = f.d("/ice");       // passive / ice fallback
```

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

## Numbered NPC Candidate Band — `110xxx`

In addition to the 3 shared actor sheets and the blacksmith, the jar contains
17 numbered NPC sprites in the `110000..110160` band on an `X0` stride. They
are relocated from `monster_legacy/02_candidate_unknown_ranges/` after a
cross-check confirmed the IDs are NPC sprites, not monster frames.

- Bucket: `04_numbered_npc_candidate_110xxx/` — 17 files.
- Pattern: `110000, 110010, 110020, ..., 110160` (step 10).
- Confidence: `candidate` — no literal `f.d("/110000")` string in the
  decompiled source. Likely loaded via `pa.a(id, false)` with the id passed
  from a server-sent TLV tag.
- Hypothesis: each ID is a **single-frame standing sprite** for a numbered
  NPC slot (unlike monsters which come as multi-frame slots under the
  `AAAABC` schema).

Do NOT assign names / roles to these IDs until the server NPC catalog is
reconstructed. The `X0` stride suggests a 1-per-family layout (no slot
sub-indexing) which would match the "static NPC portrait" use case.

## Folder Reading Order

Use this order when working:

1. [00_ui_confirmed](/d:/Twelve/client/assets/npc_legacy/00_ui_confirmed)
2. [01_named_npc_confirmed](/d:/Twelve/client/assets/npc_legacy/01_named_npc_confirmed)
3. [02_shared_actor_sheets](/d:/Twelve/client/assets/npc_legacy/02_shared_actor_sheets)
4. [03_interactive_map_objects](/d:/Twelve/client/assets/npc_legacy/03_interactive_map_objects)
5. [04_numbered_npc_candidate_110xxx](/d:/Twelve/client/assets/npc_legacy/04_numbered_npc_candidate_110xxx)

## Port Order

1. Rebuild NPC dialog chrome from `dialog/corner.png`.
2. Port the blacksmith NPC first — it is the only NPC with a dedicated sprite + dedicated handler.
3. Port the generic `jo` actor using `monster.png`, `zap.png`, `ice.png` as the three shared spritesheets, indexed by `jo.c >> 1`.
4. Verify the four command verbs (Vào, Nói Chuyện, Tiếp tục, Nhặt) route through the TLV command dispatcher.
5. Add `magicgate` and `gate` once talking NPCs are stable.

## Reference Skills

When implementing the NPC pipeline, consult these project skills
(in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`          | Domain entity `Npc` in `Twelve.Core` |
| `binary-protocol/`       | TLV tags 9 / 26 / 27 / 15 / 129 / 106 / 107 for spawn list |
| `database-design/`       | Postgres `NpcCatalog` table (server-authored) |
| `game-mechanics/`        | Dialog flow, "Nói Chuyện" command 107 routing |
| `frontend-design/`       | Skia dialog chrome + tile actor overlay |
| `clean-code/`            | Naming `NpcRecord`, `SharedActorSheet` |

## Next Practical Step

The next coding step should be a client-side NPC renderer that:

- takes a `jo`-shaped record from the server,
- picks one of the three shared sheets by `jo.c >> 1`,
- draws a 6-frame walk strip at the tile position,
- and opens a dialog box on the `Nói Chuyện` (107) command.

That is the point where candidate families (gates, portals, future named NPCs) can start being promoted into final semantic roles.
