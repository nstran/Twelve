# NPC System Reconstruction

Tài liệu khôi phục hệ thống NPC từ Java client cũ.

If you want the deep technical reference, use:

- [client/assets/npc/README.md](/d:/Twelve/client/assets/npc/README.md)

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java) | `jo` | NPC / actor data record (7 fields) |
| [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java) | `ki` | Generic actor renderer — 1 row × 6 frame walk strip |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | Map controller — shared-sheet dispatch via `jo.c >> 1` |
| [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network decoder — parses NPC spawn list from TLV packet |
| [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java) | `le` | Blacksmith NPC visual actor (dedicated sprite/effect only in this document) |
| [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java) | `ln` | Magic-gate prop — 4-frame teleport animation |

| [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java) | `hr` | Mission list/detail/accept/cancel UI component `241204` |
| [hb.java](/d:/Twelve/reference/redecoded/decompiled/hb.java) | `hb` | Completed mission reward popup |

## Quick Position

The legacy NPC system must be rebuilt from the `jo` actor model and the shared spritesheet architecture, not from guessed numeric IDs in `/offline/`.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/npc = working asset input for the new client`
- NPC identity is server-driven via TLV tags, NOT asset-file-driven
- organize by `runtime role` (ui / named / shared sheet / map prop), NOT by `/offline/<id>`

## Main Working Folders

- [client/assets/npc](/d:/Twelve/client/assets/npc)
- [reference/review_assets/npc_organized](/d:/Twelve/reference/review_assets/npc_organized) — raw organized source

## Confirmed Runtime Pieces

These are safe to build around first:

- `/blacksmith` and `/effblacksmith`
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:13)
  - [le.java](/d:/Twelve/reference/redecoded/decompiled/le.java:14)
  - Scope note: this document records only the NPC visual actor/effect evidence.
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
aim at an NPC or prop. Later handlers prove the same ids route to map interaction
logic, including talk/action state transitions in [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:853):

| Label          | Command ID |
|----------------|------------|
| Vào            | 101        |
| Nói Chuyện     | 107        |
| Tiếp tục       | 118        |
| Nhặt           | 110        |

"Nói Chuyện" (107) is the canonical talk action.

NPC interaction evidence discovered in the deep audit:

- When player overlaps/selects a `ki` actor, [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:660) scans `this.I` for `ki` actors and stores the focused index in `this.U`.
- Actor facing is flipped against the player before interaction using `ki.b(2)` / `ki.b(3)` in [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:670).
- Interaction preview/dialog can clone an NPC actor with `ki.b()` before opening [ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java:82) / [ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java:85).
- Starting interaction with the focused `ki` sends `ks.a().a(ki2.f.a, bl2)` from [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:814). This proves `jo.a` is the outbound actor id for that interaction.

## Candidate Classes

These are valid interactive map actors and should stay available, but do not
yet deserve a "full NPC" label:

- `ln.java` — `/magicgate` — 4-frame teleport gate
- `gate.png` — generic gate asset

Do not rename these to final labels like `quest_giver` or `warp_portal` until
the server NPC/Mission packet catalog confirms behavior.

## Numbered NPC Band — `110xxx`

The actual numbered NPC sprites are the 17 files in the `110000..110160` band
on an `X0` stride. Per user correction, these are kept directly at
`client/assets/npc/` rather than inside a candidate subfolder.

- Bucket: `client/assets/npc/110000.png` through `client/assets/npc/110160.png` — 17 files.
- Pattern: `110000, 110010, 110020, ..., 110160` (step 10).
- Confidence: `confirmed_npc_sprite` — user confirmed these are NPC sprites, not monster/equipment.
- Boundary: exact map placement, name, mission role, shop role, or portal role still requires server catalog / packet / video evidence.

Do NOT assign names / roles to these IDs until the server NPC catalog or another
evidence source proves the mapping. The `X0` stride suggests a 1-per-family
layout (no sub-indexing) which matches the static numbered NPC sprite use case.

## Folder Reading Order

Use this order when working:

1. [110000.png..110160.png](/d:/Twelve/client/assets/npc) — numbered NPC sprites.
2. [00_ui_confirmed](/d:/Twelve/client/assets/npc/00_ui_confirmed)
3. [01_named_npc_confirmed](/d:/Twelve/client/assets/npc/01_named_npc_confirmed)
4. [02_shared_actor_sheets](/d:/Twelve/client/assets/npc/02_shared_actor_sheets)
5. [03_interactive_map_objects](/d:/Twelve/client/assets/npc/03_interactive_map_objects)

## Port Order

1. Rebuild NPC / Mission dialog chrome from `dialog/corner.png`.
2. Port the blacksmith visual actor first only as NPC sprite/effect evidence.
3. Port the generic `jo` actor using `monster.png`, `zap.png`, `ice.png` as the three shared spritesheets, indexed by `jo.c >> 1`.
4. Verify the four map actor verbs (Vào, Nói Chuyện, Tiếp tục, Nhặt) route through the NPC/Mission TLV dispatcher.
5. Add `magicgate` and `gate` only as NPC/Mission-adjacent map props once talking NPCs are stable.

## Scope Guard

This document is only for NPC & Mission reconstruction.

Only keep evidence that belongs directly to NPC/Mission flow.

Allowed content is limited to:

- NPC visual actor evidence;
- NPC/Mission TLV tags and command routing;
- Mission list/detail/notification UI;
- map props that Java places in the same actor/interaction layer (`gate`, `magicgate`).

## Mission / Quest Evidence

This section is limited to NPC + Mission reconstruction.

### Mission data classes

Java evidence:

- `ns.java` is the mission / quest record:
  - `a`: quest id.
  - `b`: title / display text.
  - `c`: extra text / description slot.
  - `d`: long numeric value, likely time / expiry / progress anchor, not proven.
  - `e`: boolean status flag populated from TLV tag `100` in some packets.
  - `f`: task array (`nt[]`).
  - `g`: string array, used by one quest notification packet.
  - Evidence: [ns.java](/d:/Twelve/reference/redecoded/decompiled/ns.java:4)
- `nt.java` is a mission task / objective record:
  - constructor receives raw int task value plus strings;
  - field `b` stores visible task text;
  - field `a` stores quest id / owner id in observed constructors.
  - Evidence: [nt.java](/d:/Twelve/reference/redecoded/decompiled/nt.java:4)
- `nu.java` is the mission notification queue with three independent queues:
  - `a(ns)`: add completed/new quest notification.
  - `a(nt)`: add task notification.
  - `b(ns)`: add quest update notification.
  - `a()`, `b()`, `c()` pop one pending quest/task/update respectively.
  - Evidence: [nu.java](/d:/Twelve/reference/redecoded/decompiled/nu.java:4)

Remake policy:

- Use names like `MissionRecord`, `MissionTask`, and `MissionNotifierQueue` in new code.
- Keep raw ids/tags exactly as Java evidence. Do not infer mission category,
  reward type, NPC owner, or completion condition from decompiled field names
  alone.

### Mission packet decoding

Java evidence from `ky.java`:

| Java handler | Triggered by | Tags / fields | Meaning proven |
|--------------|--------------|---------------|----------------|
| `g(ku)` | command `31` | repeated tag `77`, nested tag `26`, tag `100` | mission list summary into `ns[]` |
| `n(ku)` | command `33` | tag `77`, `26`, `79`, `132`, `100`, repeated task tag `80` with text tag `81` | single mission detail with task list |
| `o(ku)` | command `38` | tag `77`, repeated task tag `80` with text tag `81` | mission update queued via `nu.b(ns)` |
| inline case `34` | command `34` | tag `80`, `81`, `77`, `149` | task notification queued via `nu.a(nt)` and message shown |
| `p(ku)` | command `35` | tag `77`, `26`, repeated tag `1`, tag `149` | mission notification queued via `nu.a(ns)` and message shown |

Evidence lines:

- Command dispatch: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:432)
- Task notification case `34`: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:448)
- Mission list parser `g(ku)`: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1622)
- Mission detail parser `n(ku)`: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1970)
- Mission update parser `o(ku)`: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1997)
- Mission notification parser `p(ku)`: [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:2020)

Important boundaries:

- Tags `77`, `80`, `81`, `79`, `100`, `132`, `149`, `26`, and `1` are proven
  for Mission packet parsing in the client.
- The exact server command names are not present in Java source. Only command
  numbers and handler behavior are proven.
- Reward structure is not proven by these snippets. Do not add reward fields to
  Mission DTOs until more Java evidence is found.

### Mission UI routing

Java evidence:

- `kq.java` exposes mission callbacks `a(ns[])`, `a(ns, boolean)`, `A()`, and
  `B()` in the main network/UI bridge interface.
  - Evidence: [kq.java](/d:/Twelve/reference/redecoded/decompiled/kq.java:35)
- `com/mg/sq/a.java` forwards `a(ns[])` to `oa.a(ns[])`, calls `v()`, and
  forwards `A()` / `B()` to `oa.g()` / `oa.t()`.
  - Evidence: [com/mg/sq/a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:733)
  - Evidence: [com/mg/sq/a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:739)
  - Evidence: [com/mg/sq/a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:746)
- `oa.a(ns[])` routes mission lists into UI component id `241204` when present.
  - Evidence: [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java:322)
- `oa.a(ns, boolean)` routes one mission into the same UI component id `241204`
  and sets mode `2` when boolean is true, otherwise mode `1`.
  - Evidence: [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java:345)
- `oa.u()` periodically checks `nu` queues and opens a completed quest dialog
  (`hb`) with a `Đóng` button when `nu.a()` returns a quest.
  - Evidence: [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java:487)

- `hr.java` is the mission screen itself and registers component id `241204`.
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:35)
- Mission list mode (`r = 0`) renders title `Nhiệm Vụ`, empty text `Chưa có nhiệm vụ mới.`, and one row per `ns.b`.
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:75)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:117)
- Mission detail/update modes (`r = 1` / `r = 2`) render `ns.b`, description `ns.c`, task texts `nt.b`, and optional price `ns.d` as `KEN`.
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:136)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:159)
- `hr.java` menu actions prove mission verbs and outbound calls:
  - `Chi Tiết` / command `1113` calls `ks.a().o(this.k[this.p].a)`.
  - `Nhận` / command `1112` calls `ks.a().m(this.q.a)` after optional `KEN` confirm.
  - `Hủy nhiệm vụ` / command `1115` confirms then calls `ks.a().n(this.q.a)`.
  - `Danh Sách Nhiệm Vụ` / command `1116` calls `ks.a().o()` if list is not loaded.
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:183)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:224)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:235)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:293)
  - Evidence: [hr.java](/d:/Twelve/reference/redecoded/decompiled/hr.java:334)
- `hb.java` is the completed mission popup. It renders `Đã hoàn thành nhiệm vụ: ` + `ns.b`, reward heading `Bạn nhận được:`, and reward lines from `ns.g`.
  - Evidence: [hb.java](/d:/Twelve/reference/redecoded/decompiled/hb.java:23)
  - Evidence: [hb.java](/d:/Twelve/reference/redecoded/decompiled/hb.java:41)
- `om.java` contains a first-time tutorial hint pointing the user to `Menu > Nhiệm vụ` / `Nhiệm Vụ`.
  - Evidence: [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:1236)

Remake policy:

- New client should keep a mission notification queue separate from normal NPC
  dialog state.
- Completed mission popup is Java evidence; exact visual skin can be rebuilt
  from existing dialog/chrome assets.
- Mission list/detail UI should not be mixed with generic NPC talk until server
  proves a mission belongs to a specific NPC.

### Mission UI assets

Java evidence:

- `fc.java` loads `/questnotifyicon` and corner assets `/corner/4`, `/corner/5`.
  - Evidence: [fc.java](/d:/Twelve/reference/redecoded/decompiled/fc.java:38)
  - Evidence: [fc.java](/d:/Twelve/reference/redecoded/decompiled/fc.java:41)
- JAR contains `questnotifyicon.mg`.
  - Evidence: [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:2226)
- JAR contains `dialog/corner.mg`, used as dialog chrome source in `pc.java`.
  - Evidence: [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:138)
  - Evidence: [pc.java](/d:/Twelve/reference/redecoded/decompiled/pc.java:17)

## NPC / Mission Command Map

Java evidence:

| Command | Evidence | Client behavior |
|---------|----------|-----------------|
| `31` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:428) | decode mission list via `g(ku)` |
| `32` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:432) | bridge calls `kq.A()`; request uses tag `77` |
| `33` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:440) | decode mission detail via `n(ku)`; request uses tag `77` |
| `34` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:448) | task notification + message tag `149` |
| `35` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:454) | mission notification via `p(ku)` |
| `38` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:444) | mission update via `o(ku)` |
| `41` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:436) | bridge calls `kq.B()`; request uses tag `77` |
| `43` | [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:242) | decode NPC actor roster via `f(ku)` |

Outbound request evidence:

- Command `31` is sent by `ks.o()` with no mission id; UI uses it to request the mission list.
  - Evidence: [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java:1377)
- Command `32` is sent by `ks.m(String)` with `kw2.K = questId`; UI uses it for `Nhận`.
  - Evidence: [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java:1383)
- Command `41` is sent by `ks.n(String)` with `kw2.K = questId`; UI uses it for `Hủy nhiệm vụ`.
  - Evidence: [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java:1390)
- Command `33` is sent by `ks.o(String)` with `kw2.K = questId`; UI uses it for `Chi Tiết`.
  - Evidence: [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java:1397)
- Commands `32`, `41`, and `33` serialize tag `77` from `kw2.K` in the packet builder.
  - Evidence: [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java:245)

Boundary:

- Commands `32` and `41` are mission-related because their bridge methods route
  through mission UI flow, but their final labels are not proven from Java text.
  Keep names pending until more client behavior is reconstructed.

## NPC / Mission Asset Inventory From JAR

Confirmed named assets:

| Asset | Evidence | Runtime role |
|-------|----------|--------------|
| `blacksmith.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:58) | dedicated blacksmith sprite |
| `effblacksmith.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:163) | blacksmith effect sprite |
| `monster.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:426) | shared NPC/actor sheet type `0` |
| `zap.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:2250) | shared NPC/actor sheet type `1` |
| `ice.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:280) | shared NPC/actor fallback/default sheet |
| `magicgate.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:409) | animated map gate prop |
| `gate.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:221) | generic gate prop |
| `questnotifyicon.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:2226) | mission notification icon |
| `dialog/corner.mg` | [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:138) | dialog chrome |

Numbered NPC band:

- JAR contains `offline/110000.mg` through `offline/110160.mg` on a step-10
  stride.
  - Evidence: [jar-contents.txt](/d:/Twelve/reference/redecoded/jar-contents.txt:825)
- User correction: these are NPC sprites and are now stored directly under `client/assets/npc/`.
- No decompiled Java literal load path proves map placement, names, roles, or missions for them yet.

## Server Remake Policy — NPC Roster v1

Java evidence:

- Raw NPC roster command is `43` and parser is [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1587).
- Roster mode uses tag `40`; Java dispatch observes modes `0`, `1`, and `3`.
- Each NPC record uses tag `9` and fields `jo.a`, `jo.b`, `jo.c`, `jo.d`, `jo.e`, `jo.f`, `jo.g` from tags documented above.

DB seed policy confirmed 2026-05-04:

- `NpcCatalog` stores only fields needed by the current NPC roster runtime:
  - `NpcKey` maps to server-side NPC id / Java `jo.a` policy.
  - `DisplayName` maps to Java `jo.b`.
  - `SpriteAssetId` / `SpritePath` store the confirmed numbered `110xxx` NPC sprite.
  - `VisualTypeByte` maps to Java `jo.c` for shared-sheet fallback.
  - `DisplayLevel` maps to Java `jo.d`.
  - `NameColorMode` maps to Java `jo.g`.
- `NpcMapRosters` stores per-map placement fields:
  - `MapId`, `RoomId`, `DisplayNameOverride`, `TileX`, `TileY`, `RosterMode`, `IsActive`.
  - `TileX` maps to Java `jo.e`; `TileY` maps to Java `jo.f`.
  - One NPC can appear in many maps by having multiple roster rows.
- `MissionCatalog` stores minimal mission seed data: `MissionKey`, `Title`, `Description`, `RewardText`.
- `MissionObjectives` stores mission goals: `ObjectiveType`, `TargetKey`, `RequiredAmount`, `SortOrder`.
- `MissionRewards` stores only approved reward types for now: `Exp`, `Item`, `Equipment`.
- `NpcMissionLinks` is a many-to-many link so one NPC can contain many missions and a mission can be moved/reused without changing NPC identity.
- Seed files are [server/Database/Npcs/npcs_schema.sql](server/Database/Npcs/npcs_schema.sql) and [server/Database/Npcs/npcs_seed.sql](server/Database/Npcs/npcs_seed.sql).
- Migration embeds these as `DB.09_npcs_schema.sql` and `DB.10_npcs_seed.sql` from [server/Twelve.Infrastructure/Twelve.Infrastructure.csproj](server/Twelve.Infrastructure/Twelve.Infrastructure.csproj).
- Hoa Lư starter mission seed approved by user on 2026-05-04:
  - `npc_110020` / Trưởng làng Gia Viễn owns `hoa_lu_ga_dien_quay_pha_001` and `hoa_lu_bao_tin_cho_linh_002`.
  - `npc_110110` / Lính Hoa Lư owns `hoa_lu_tuan_tra_cung_linh_003`.
  - Mission objectives currently use `KillMonster` against `MONSTER_1000_SLOT_0` / Gà Điên and `TalkNpc` against `npc_110110`.
  - Mission rewards currently use only approved reward types: `Exp`, `Item`, `Equipment`.

Remake policy confirmed 2026-05-04:

- First server roster target: map `Hoa Lu`, room `1`.
- First NPC seed: `tutorial_npc`.
- Display name policy: `Huong dan` in packet payload for ASCII-safe server source; UI localization can render Vietnamese later.
- Visual type byte: `4`; Java computes `4 >> 1 = 2`, so the client uses `/ice` fallback/default sheet.
- Spawn mode: tag `40 = 3`.
- Name color mode: `2`, matching Java gray/dark tint handling in `ki`.
- Coordinates: tile `x = 6`, tile `y = 23`, placed near existing Hoa Lu spawn (`123,738`) using the current 32px tile grid.
- Interaction v1 is now implemented as a separate talk request step; do not mix it with Mission ownership yet.
- Safety correction: current React Native remake already uses raw command `43` for the Monster roster/map encounter flow.
- Remake transport policy: keep Monster roster on `43`; send NPC roster through temporary command `45` on RN only. Java evidence remains raw command `43`, and command ownership can be migrated later when Monster flow is moved safely.

Implemented server files:

- [server/Twelve.Core/Npcs/NpcContracts.cs](server/Twelve.Core/Npcs/NpcContracts.cs)
- [server/Twelve.Core/Interfaces/IMapNpcRosterService.cs](server/Twelve.Core/Interfaces/IMapNpcRosterService.cs)
- [server/Twelve.Application/Npcs/NpcRosterPacketFactory.cs](server/Twelve.Application/Npcs/NpcRosterPacketFactory.cs)
- [server/Twelve.Infrastructure/Repositories/StaticMapNpcRosterService.cs](server/Twelve.Infrastructure/Repositories/StaticMapNpcRosterService.cs) — old v1 fallback/static seed, no longer wired in DI.
- [server/Twelve.Infrastructure/Repositories/DbMapNpcRosterService.cs](server/Twelve.Infrastructure/Repositories/DbMapNpcRosterService.cs) — DB-backed runtime roster, reads `NpcMapRosters` + `NpcCatalog` and emits Java `jo`-shaped NPC records by map/room.
- [server/Twelve.Application/Handlers/MapHandler.cs](server/Twelve.Application/Handlers/MapHandler.cs) — preserves Monster roster flow on `43` and sends NPC roster on temporary command `45`.
- [server/Twelve.Core/Tlv/CommandCodes.cs](server/Twelve.Core/Tlv/CommandCodes.cs) — keeps `MapMonsterRoster = 43`, adds `MapNpcRosterRemake = 45`, uses Java evidence command `16` for NPC talk request, and command `46` as RN-safe talk response transport.
- [server/Twelve.Core/Npcs/NpcMissionContracts.cs](server/Twelve.Core/Npcs/NpcMissionContracts.cs) — DB mission/talk contracts for NPC-linked mission summaries, details, objectives, and rewards.
- [server/Twelve.Core/Interfaces/INpcMissionCatalog.cs](server/Twelve.Core/Interfaces/INpcMissionCatalog.cs) — read-only mission catalog boundary so future seed reward changes stay data-driven.
- [server/Twelve.Infrastructure/Repositories/DbNpcMissionCatalog.cs](server/Twelve.Infrastructure/Repositories/DbNpcMissionCatalog.cs) — reads `NpcMissionLinks`, `MissionCatalog`, `MissionObjectives`, and `MissionRewards` from DB.
- [server/Twelve.Application/Npcs/MissionPacketFactory.cs](server/Twelve.Application/Npcs/MissionPacketFactory.cs) — emits mission list/detail packets using the already-audited Java Mission command/tag shape.
- [server/Twelve.Application/Handlers/NpcTalkHandler.cs](server/Twelve.Application/Handlers/NpcTalkHandler.cs) — resolves NPC talk context from DB mission links instead of hardcoded `tutorial_npc` content.
- [server/Twelve.Application/Handlers/MissionHandler.cs](server/Twelve.Application/Handlers/MissionHandler.cs) — handles mission list/detail/accept/cancel and remake-policy reward claim when a completed mission is accepted again.
- [server/Twelve.Application/Npcs/MissionRewardClaimService.cs](server/Twelve.Application/Npcs/MissionRewardClaimService.cs) — grants data-driven mission rewards from `MissionRewards` into EXP/item/equipment persistence.
- [server/Twelve.Core/Interfaces/IMissionRewardClaimService.cs](server/Twelve.Core/Interfaces/IMissionRewardClaimService.cs) — application boundary for mission reward claim logic.
- [client/src/network/Protocol.ts](client/src/network/Protocol.ts)
- [client/src/network/SocketClient.ts](client/src/network/SocketClient.ts)
- [client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx](client/src/screens/map/hoa-lu/HoaLuMapScreen.tsx)
- [server/Twelve.Infrastructure/ServiceCollectionExtensions.cs](server/Twelve.Infrastructure/ServiceCollectionExtensions.cs)

Boundary:

- Runtime NPC roster is now DB-backed, so future NPC placement/name/mission ownership changes should be made through seed/catalog data instead of hardcoded service rows.
- This is remake policy based on Java client packet shape, not recovered Java server data.
- NPC talk request command `16` is Java client evidence from `ks.a().a(String, boolean)`; response command `46` remains remake transport policy for RN.
- Mission list/detail use Java-evidence commands/tags already audited from client, but the current mission rows/rewards are approved remake seed data.
- Mission ownership is DB-backed through `NpcMissionLinks`.
- Mission accept/cancel state is now persisted per player in `PlayerMissions`; per-objective progress rows are initialized in `PlayerMissionObjectives` on accept.
- Objective progress is data-driven by `MissionObjectives.ObjectiveType`, `TargetKey`, and `RequiredAmount`.
- `KillMonster` progress accepts both `SpawnTemplateKey` (shared monster target such as `MONSTER_1000_SLOT_0`) and runtime `MonsterKey` so future mission seeds can choose either target style.
- `TalkNpc` progress uses the talked NPC id (`jo.a` / `NpcKey`) as `TargetKey`.
- Reward claim/grant is implemented as explicit remake policy: accepting a `Completed` mission again claims rewards once, then status stays `RewardClaimed`.
- Reward grants are data-driven from `MissionRewards`: `Exp` updates player EXP/level, `Item` merges inventory stacks by raw item id, and `Equipment` expects an equipment `TemplateKey`.
- Seed correction: reward key `30094` is confirmed in `ItemCatalog` as `Trứng gà`, so the Hoa Lư patrol seed now stores it as `Item`, not `Equipment`.
- Mission progress now returns changed objective data so handlers can emit Java-client mission notification/update packets (`34`, `35`, `38`) instead of silently updating DB state.
- Existing Monster roster behavior must not be removed as part of NPC work; command ownership must be decided before wiring NPC roster into live map packets.

## Next Practical Step

The next coding step should be deeper client-side Mission screen polish, while keeping these current boundaries clear:

- Java client proves mission list/detail/accept/cancel commands, but original Java server reward catalog is still missing.
- Reward claim command is remake policy for now: repeated accept on a completed mission means claim reward.
- Server emits mission progress/complete/reward notifications, and RN now renders a lightweight map toast queue for these updates.
- RN now has a playable mission list/detail/accept dialog on the Hoa Lu map; presentation remains remake polish based on Java `hr`/`hb` flow, not pixel-perfect Java UI.
- Future `Equipment` rewards must use equipment template keys from `EquipmentCatalog.TemplateKey`, not raw item ids from `ItemCatalog`.
- Manual end-to-end verification lives in `plans/features/hoa-lu-mission-end-to-end-checklist.md`.

In parallel, continue with a client-side NPC + Mission evidence port that:

- implements a `jo`-shaped NPC actor contract from command `43` / TLV tags;
- extends Mission UI from the now-added Mission DTO/parser foundation for `ns` / `nt` and commands `31` / `33` / `34` / `35` / `38`;
- keeps NPC talk/dialog separate from Mission list/detail/notification state;
- draws `jo` actors through the three shared sheets selected by `jo.c >> 1`;
- supports mission notification icon/dialog using `questnotifyicon` and dialog corner assets;
- does not assign mission ownership, rewards, quest giver role, or portal role until packet/catalog evidence proves it.

That is the point where candidate families (gates, portals, future named NPCs)
can start being promoted into final semantic roles.

## Nhật ký chỉnh sửa

### 2026-05-04

- Audit scope: NPC & Mission only.
- Added Java evidence for Mission classes `ns`, `nt`, notifier queue `nu`, mission packet handlers in `ky`, bridge routes in `kq` / `com.mg.sq.a` / `oa`, and mission UI asset `questnotifyicon`.
- Added JAR asset inventory for NPC/Mission assets: `blacksmith`, `effblacksmith`, `monster`, `zap`, `ice`, `magicgate`, `gate`, `questnotifyicon`, `dialog/corner`, and `offline/110xxx` numbered NPC band.
- Tightened scope guard so this document stays only NPC & Mission.
- Deep-audit additions: mission screen `hr`, completed mission popup `hb`, outbound mission request methods in `ks`, focused NPC `ki` interaction path in `om`, `ha` NPC interaction preview evidence, and first-time mission tutorial hint in `om`.
- Implemented server remake policy v1 for NPC roster data/factory: Java command shape `43`, map `Hoa Lu` room `1`, seed NPC `tutorial_npc`, mode `3`, type `4` (`/ice`), tile `(6,23)`, flag `2`, with interaction intentionally deferred.
- Corrected server wiring after review: restored the existing Monster roster send in `MapHandler` and moved live NPC roster transport to temporary RN command `45` because the current RN client still treats command `43` as Monster roster.
- Added client NPC roster parse/render: RN listens for command `45`, parses Java `jo` tags, stores NPC roster separately from Monster roster, and renders `tutorial_npc` as an `/ice` shared-sheet actor without Monster collision/encounter behavior.
- Added NPC talk v1: RN sends command `16` with NPC id tag `9` and continue flag tag `40`, server validates `tutorial_npc`, and RN displays the temporary response from command `46`. Java evidence is only the request shape; response/content remain remake policy.
- Added Mission parser foundation on RN from Java client evidence: commands `31`, `33`, `34`, `35`, `38` now decode into `MissionRecord` / `MissionTaskRecord` equivalents of Java `ns` / `nt`; outbound mission list/detail/accept/cancel requests use commands `31`, `33`, `32`, `41` with tag `77` where Java `ks` proves it.
- Added separate map mission state reducer/queue so Mission notifications stay isolated from NPC talk dialog; no quest giver, reward, ownership, or completion policy has been inferred yet.
- Corrected NPC asset organization per user evidence: moved numbered NPC sprites `110000.png..110160.png` directly under `client/assets/npc/`; remaining NPC subfolders are UI/shared-sheet/prop evidence, not the numbered NPC set.
- Added NPC DB schema/seed files with `NpcCatalog`, `NpcMapRosters`, and `NpcDialogLines`; seeded all `110000..110160` sprites with placeholder names so the original NPC names/roles can be filled later without changing schema.
- Trimmed NPC DB fields to the currently needed roster/dialog fields, added `MissionCatalog` plus `NpcMissionLinks` for NPCs containing multiple missions, and seeded `Lính Hoa Lư` (`npc_110110`) into every enabled world map via `WorldMapCatalog`.
- Added `NpcMapRosters.DisplayNameOverride` so shared NPCs can have map-specific names, e.g. `Lính Hoa Lư`, `Lính Kỷ Bố`, without duplicating the base sprite/NPC catalog row.
- Removed unused DB fields/tables from the current seed scope: `InteractionMode`, `DialogKey`, `IsVerifiedJava`, and `NpcDialogLines` were dropped because no DB-backed runtime currently reads them.
- Added minimal mission objective/reward tables: `MissionObjectives` plus `MissionRewards`; reward type is constrained to `Exp`, `Item`, and `Equipment` only so future reward categories can be added deliberately later.
- Implemented the approved Hoa Lư starter mission seed as remake policy: Trưởng làng Gia Viễn gives the first Gà Điên kill mission and a talk-to-guard mission; Lính Hoa Lư gives a follow-up Gà Điên patrol mission. Java server mission catalog evidence is still missing, so these rows remain clearly marked as approved remake seed data.
- Replaced the runtime NPC roster wiring with `DbMapNpcRosterService`, which reads `NpcMapRosters` joined with `NpcCatalog` and emits the existing Java `jo`-shaped `MapNpcRosterEntry` packet data by map/room. This keeps future NPC placement/name changes in DB seed data instead of hardcoded runtime code.
- Added DB-backed NPC Mission read flow: `DbNpcMissionCatalog` loads NPC talk contexts, mission summaries, objectives, and rewards from DB; `NpcTalkHandler` now returns linked missions for the talked NPC; `MissionHandler` serves mission list/detail packets using Java-evidence mission commands while leaving objective progress/reward claim unimplemented.
- Added minimal per-player mission persistence: `PlayerMissions` stores Accepted/Completed/RewardClaimed/Canceled state, `PlayerMissionObjectives` stores objective progress rows, and `PlayerMissionStateRepository` wires accept/cancel without hardcoding mission keys or rewards.
- Added data-driven mission progress updates: `PlayerMissionStateRepository.AddProgressAsync(username, objectiveType, targetKey, amount)` updates accepted objectives by `ObjectiveType + TargetKey`, clamps by `RequiredAmount`, and marks the mission completed when all objectives are complete. `BattleResultService` reports `KillMonster` progress using both monster spawn template key and runtime monster key; `NpcTalkHandler` reports `TalkNpc` progress using NPC id.
- Added mission reward claim/grant remake policy: `MissionRewardClaimService` grants `Exp`, `Item`, and `Equipment` rewards from DB seed data, `MissionHandler` treats accept on a completed mission as claim, and `PlayerMissionStateRepository` transitions completed missions to `RewardClaimed` to prevent double-claim.
- Corrected the Hoa Lư patrol reward seed: raw id `30094` is `Trứng gà` in `ItemCatalog`, so the reward remains data-driven as `Item 30094 x1`; `Equipment` rewards are reserved for equipment template keys.
- Added mission notification/update packets for progress flow: `MissionProgressUpdate` carries changed objective status, `MissionPacketFactory` builds command `34` task notifications, command `38` mission updates, and command `35` complete/reward notifications. `NpcTalkHandler` sends these immediately for `TalkNpc`; battle result responses now include mission updates for `KillMonster` so RN can render progress after battle claim.
- Added RN mission toast queue polish: `MapMission.reducer` now builds task/mission/progress toasts, `HoaLuMapScreen` renders a stacked mission notification panel separate from NPC talk, and `App` carries pending battle result mission updates back to the map after leaving battle. This UI is remake polish; objective/reward data remains server/DB-driven.
- Added playable Hoa Lư Mission dialog polish: the map menu opens a list/detail panel, requests mission list/detail from the server, renders objectives/rewards, and sends accept by mission key without hardcoding client mission data. Added `plans/features/hoa-lu-mission-end-to-end-checklist.md` for receive/progress/complete/claim/double-claim/reconnect manual QA.
