# Monster System Reconstruction

Tài liệu khôi phục hệ thống quái vật (monster) từ Java client cũ.

If you want the deep technical reference, use:

- [client/assets/monster/README.md](/d:/Twelve/client/assets/monster/README.md)

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [pa.java](/d:/Twelve/reference/redecoded/decompiled/pa.java) | `pa` | Offline loader — `pa.a(id, false)` resolves `/offline/<id>.png` |
| [ki.java](/d:/Twelve/reference/redecoded/decompiled/ki.java) | `ki` | Actor renderer — 1 row × 6 frame walk strip |
| [jo.java](/d:/Twelve/reference/redecoded/decompiled/jo.java) | `jo` | Actor data record (`.c` type byte drives sheet choice) |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | Map dispatcher — decides which sheet a `jo` uses |
| [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java) | `ky` | Network decoder — parses spawn lists into `jo[]` |
| [ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java) | `ha` | Encounter / versus preview giữa player và monster, đọc level + IQ từ `jo` |
| [ks.java](/d:/Twelve/reference/redecoded/decompiled/ks.java) | `ks` | Socket writer — gửi request đánh monster bằng `jo.a` |
| [oa.java](/d:/Twelve/reference/redecoded/decompiled/oa.java) | `oa` | Platform / map screen bridge — chuyển callback monster từ app layer xuống `om` |
| [com.mg.sq.a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java) | `com.mg.sq.a` | App-level dispatcher triển khai `kq`, chuyển packet monster vào screen hiện tại |
| [kq.java](/d:/Twelve/reference/redecoded/decompiled/kq.java) | `kq` | Callback interface có 3 hook monster `a/b/c(jo[], mapId)` |
| [kd.java](/d:/Twelve/reference/redecoded/decompiled/kd.java) | `kd` | Map actor bucket + depth sorting cho player, monster, map objects |
| [kj.java](/d:/Twelve/reference/redecoded/decompiled/kj.java) | `kj` | Monster movement stepper / roaming logic trên map |
| [kl.java](/d:/Twelve/reference/redecoded/decompiled/kl.java) | `kl` | Player world actor, được monster dùng để check chase / collide |
| [pa.java](/d:/Twelve/reference/redecoded/decompiled/pa.java) | `pa` | Data cacher / loader, cho thấy map resource flow đợi tải monster xong |
| [lh.java](/d:/Twelve/reference/redecoded/decompiled/lh.java) | `lh` | Fighter battle payload — chứa element, HP/MP, stats, equipment, skills |
| [lv.java](/d:/Twelve/reference/redecoded/decompiled/lv.java) | `lv` | Skill entry trong battle payload của fighter |
| [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java) | `lg` | Battle runtime wrapper — current HP/MP/Power, status timers, skill lookup |
| [mx.java](/d:/Twelve/reference/redecoded/decompiled/mx.java) | `mx` | Battle actor + HUD renderer — dựng monster actor, animate HP/MP/Power bars |
| [mb.java](/d:/Twelve/reference/redecoded/decompiled/mb.java) | `mb` | Battle body-part compositor — ghép part sheets thành animation state |
| [ni.java](/d:/Twelve/reference/redecoded/decompiled/ni.java) | `ni` | Battle actor state machine cho fighter dạng composite |
| [nq.java](/d:/Twelve/reference/redecoded/decompiled/nq.java) | `nq` | Turn / result packet model chứa actor deltas và runtime payload |
| [nl.java](/d:/Twelve/reference/redecoded/decompiled/nl.java) | `nl` | Per-fighter attribute delta: damage, hp, mana, power |
| [df.java](/d:/Twelve/reference/redecoded/decompiled/df.java) | `df` | Appearance layer descriptor với palette remap data |

The monster system does not have a single dedicated Java class.

It is split across 4 layers:

1. `asset bank` — numeric `/offline/<id>` files used as reconstruction input
2. `map spawn runtime` — `jo[]` parsed by `ky.java`
3. `map / encounter visual runtime` — `om.java`, `ki.java`, `ha.java`
4. `battle truth` — `lh` / `lv` fighter payloads sent by the server

## Quick Position

Monster sprites are addressed by 6-digit numeric IDs under `/offline/<id>.png`.

The stable rules are:

- `Java old client = behavior/spec`
- `client/assets/monster = working asset input for the new client`
- monster identity in live play is server-driven, NOT inferred from jar filenames
- map spawn runtime and battle runtime are 2 different data layers
- organize numeric art by `family code + slot`, but expose gameplay by explicit catalog IDs
- keep a stable monster key so a map encounter can be turned into the correct battle monster later

## Java Runtime Split

This is the most important thing to preserve if the remake wants to feel
like the old client.

### 1. Map spawn layer

The map receives `jo[]` records from the network decoder. Those records carry
only lightweight encounter data such as:

- runtime key
- display name
- type byte for sheet / icon selection
- display level
- IQ / AI label value
- spawn count
- name color mode

They do **not** carry HP/MP/skills.

### 2. Map render layer

The traced Java runtime in [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:491)
does **not** render map monsters directly from `/offline/<id>.png`.

Instead it dispatches through 3 shared sheets:

- `/monster`
- `/zap`
- `/ice`

selected by `jo.c >> 1`.

This means the old client has a split between:

- `numeric offline asset bank` for reconstruction / catalog work
- `shared live actor sheets` for the active map runtime we can actually trace

### 3. Encounter preview layer

Before battle starts, [ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java)
builds a versus-style preview dialog using the `jo` record already attached to
the live `ki` monster actor.

That preview reads:

- icon / element marker from `jo.c`
- level from `jo.d`
- IQ value from `jo.e`
- display name from `jo.b`

### 4. Battle truth layer

Once the player commits to battle, the client only sends the monster key
(`jo.a`) plus initiative/context flags. The actual battle fighter data comes
back as a full `lh` payload with HP/MP/stats/skills.

So for the remake:

- `map monster` is a lightweight encounter projection
- `battle monster` is a fully resolved fighter template
- they must be linked by key / catalog ID, not by guessed sprite name

## Main Working Folders

- [client/assets/monster](/d:/Twelve/client/assets/monster)
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

## Shared Actor Sheet Runtime

For the traced live map renderer in [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:491),
the raw `jo.c` byte is reduced to a sheet family by shifting right 1 bit:

| Rule | Sheet |
|------|-------|
| `jo.c >> 1 == 0` | `/monster` |
| `jo.c >> 1 == 1` | `/zap` |
| otherwise | `/ice` |

So the new system should preserve 2 values separately:

- raw encounter byte (`jo.c`) for legacy-like behavior / icon mapping
- resolved visual family for picking the actual sprite sheet

## Monster Asset Preload in Map Flow

The old client does not treat map monster art as an isolated feature.

Map entry is part of a broader resource pipeline, and the data cacher shows
that the client may finish downloading monster-related resources before it
continues map loading.

Evidence:

- [pa.java:697](/d:/Twelve/reference/redecoded/decompiled/pa.java:697)

This matters for the remake because monster-map flow should not be modeled as:

- load map tilemap
- then lazily invent monster visuals later

The Java spirit is closer to:

1. resource layer prepares what the map scene needs
2. map scene initializes spawn cells and live actor lists
3. spawn packets populate those cells into runtime actors

## Map Spawn Record (`jo`)

The old client's monster spawn record is:

```java
public final class jo {
    public String a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g = 0;
}
```

Parsed in [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1587).

### Field Mapping

| Field | Java evidence | Meaning | Confidence |
|-------|---------------|---------|------------|
| `jo.a` | [ky.java:1596](/d:/Twelve/reference/redecoded/decompiled/ky.java:1596), [om.java:814](/d:/Twelve/reference/redecoded/decompiled/om.java:814), [ks.java:1080](/d:/Twelve/reference/redecoded/decompiled/ks.java:1080) | runtime monster key sent back when requesting battle | high |
| `jo.b` | [ky.java:1597](/d:/Twelve/reference/redecoded/decompiled/ky.java:1597), [ha.java:82](/d:/Twelve/reference/redecoded/decompiled/ha.java:82) | display name in map / encounter UI | high |
| `jo.c` | [ky.java:1599](/d:/Twelve/reference/redecoded/decompiled/ky.java:1599), [om.java:491](/d:/Twelve/reference/redecoded/decompiled/om.java:491), [ha.java:82](/d:/Twelve/reference/redecoded/decompiled/ha.java:82) | raw type byte used for icon + visual family dispatch | high |
| `jo.d` | [ky.java:1598](/d:/Twelve/reference/redecoded/decompiled/ky.java:1598), [ha.java:260](/d:/Twelve/reference/redecoded/decompiled/ha.java:260) | display level | high |
| `jo.e` | [ky.java:1600](/d:/Twelve/reference/redecoded/decompiled/ky.java:1600), [ha.java:83](/d:/Twelve/reference/redecoded/decompiled/ha.java:83) | IQ / encounter AI label value | high |
| `jo.f` | [ky.java:1601](/d:/Twelve/reference/redecoded/decompiled/ky.java:1601), [om.java:375](/d:/Twelve/reference/redecoded/decompiled/om.java:375) | number of instances to spawn from this record | high |
| `jo.g` | [ky.java:1602](/d:/Twelve/reference/redecoded/decompiled/ky.java:1602), [ki.java:76](/d:/Twelve/reference/redecoded/decompiled/ki.java:76) | explicit name color mode / rarity tint override | medium-high |

### IQ Buckets from `ha.java`

[ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java:83) maps `jo.e` into labels:

| Condition | Label |
|-----------|-------|
| `< 3` | `Siêu gà` |
| `< 7` | `Bờm` |
| `< 10` | `Ma lanh` |
| `== 11` | `Tốc chiến` |
| otherwise | `Tuyệt đỉnh` |

This bucket logic is client-visible and should be preserved if the new project
wants the same preview feel.

## Map Spawn Placement in Old Java

This is another critical legacy behavior.

The old tiled map does **not** receive exact `(x, y)` coordinates for each
monster from the `jo` packet.

Instead:

1. The map logic layer marks eligible monster cells with value `2`
2. [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:262) collects those cells into `this.n`
3. For each `jo`, the client spawns `jo.f` copies locally
4. Placement is chosen from available spawn cells with local randomness
5. The client avoids placing monsters directly on the player's current row path

Evidence:

- spawn marker extraction: [om.java:262](/d:/Twelve/reference/redecoded/decompiled/om.java:262)
- player-row exclusion list: [om.java:360](/d:/Twelve/reference/redecoded/decompiled/om.java:360)
- multiplicity loop `jo.f`: [om.java:375](/d:/Twelve/reference/redecoded/decompiled/om.java:375)
- random cell choice: [om.java:389](/d:/Twelve/reference/redecoded/decompiled/om.java:389)

Implication for the remake:

- old Java map spawn data was closer to `encounter groups` than authored fixed coordinates
- if the new side-scrolling maps want to feel like Java, they should still keep
  a separation between:
  - `spawn template`
  - `spawn zone / placement rule`
  - `battle template`

## Packet Bridge: How Monster Updates Reach the Map

This is the full non-battle callback chain visible in the Java client.

### 1. Packet decode

`ky.f(ku)` parses:

- `mapId` from tag `20`
- mode byte from tag `40`
- `jo[]` monster records from repeated tag `9`

Evidence:

- [ky.java:1587](/d:/Twelve/reference/redecoded/decompiled/ky.java:1587)

### 2. App-level dispatch

After parsing, `ky` routes by mode byte:

- `mode 0` -> `kq.b(jo[], mapId)`
- `mode 1` -> `kq.c(jo[], mapId)`
- `mode 3` -> `kq.a(jo[], mapId)`

Evidence:

- [ky.java:1606](/d:/Twelve/reference/redecoded/decompiled/ky.java:1606)
- [kq.java:43](/d:/Twelve/reference/redecoded/decompiled/kq.java:43)

### 3. Current-screen bridge

`com.mg.sq.a` implements those callbacks.

Observed behavior:

- `a(jo[], mapId)` forwards to the current `oa`
- `b(jo[], mapId)` also forwards to the current `oa`
- `c(jo[], mapId)` resolves the active map screen and removes matching monsters

Evidence:

- [com.mg.sq.a.java:872](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:872)
- [com.mg.sq.a.java:878](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:878)
- [com.mg.sq.a.java:884](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:884)

### 4. Platform screen handoff

`oa.a(jo[], mapId)` checks active map id and calls:

- `((om)ol.p).a(joArray, false)`

Evidence:

- [oa.java:538](/d:/Twelve/reference/redecoded/decompiled/oa.java:538)

### Practical reading

What we can safely say from the client:

- `mode 1` definitely reaches the remove path
- `mode 0` and `mode 3` both converge into the add/spawn path in the traced client

What we should **not** overclaim:

- the exact server-side semantic difference between `mode 0` and `mode 3`

For the remake, it is fine to model these as:

- `spawnOrRefresh`
- `remove`
- optional `fullResync`

as long as the final visible behavior matches.

## Map Scene Initialization Relevant to Monsters

When a map scene (`om`) is created, it immediately initializes monster-specific
runtime dependencies.

Confirmed setup:

- load live actor sheets `/monster`, `/zap`, `/ice`
- build tile collision / logic arrays
- collect map cells whose logic value is `2` into the monster spawn pool `this.n`
- create `kd` render bucket
- create `kj` monster updater
- create player actor `kl`

Evidence:

- sheets loaded in constructor: [om.java:62](/d:/Twelve/reference/redecoded/decompiled/om.java:62)
- spawn marker extraction in `x()`: [om.java:257](/d:/Twelve/reference/redecoded/decompiled/om.java:257)

This means monster-map flow in Java is not bolted on after the fact.
It is part of map boot itself.

## Live Monster Actor Lifecycle on the Map

### 1. Spawn path

`om.a(jo[], boolean)` is the main spawn routine.

It:

- computes a row-based exclusion list from the player's current path
- consumes random cells from `this.n`
- creates one `ki` per `jo.f`
- adds each `ki` both to:
  - `this.I` = logical live monster list
  - `this.H.b` = render bucket list

Evidence:

- [om.java:353](/d:/Twelve/reference/redecoded/decompiled/om.java:353)
- [om.java:507](/d:/Twelve/reference/redecoded/decompiled/om.java:507)

### 2. Remove path

`om.a(jo[])` removes monsters by matching `jo.a`.

It removes from:

- `this.I`
- `kd.b`

Evidence:

- [om.java:456](/d:/Twelve/reference/redecoded/decompiled/om.java:456)

### 3. Important identity rule

The map runtime treats `jo.a` as the stable live identity for a monster instance
group. Removal is **not** based on coordinates or sprite family.

That same key is later used for battle handoff.

So in the remake, `monsterKey` must stay stable from:

- spawn packet
- live map actor
- encounter preview
- battle request

## `ki` as the Actual Map Monster Actor

`jo` is just data. `ki` is the live map monster.

`om.a(x, y, jo, sharedImage)` constructs each `ki` and sets:

- sprite sheet family from `jo.c >> 1`
- UI anchor offset
- random move step magnitude `d`
- world position
- initial facing / movement direction

Evidence:

- [om.java:485](/d:/Twelve/reference/redecoded/decompiled/om.java:485)

An important detail:

- the first spawned copy of a `jo` record can seed the shared image
- subsequent copies may reuse `ki.g`

This reinforces that a single `jo` record represents an encounter group,
not a single authored actor definition.

## Map Monster Roaming / Facing Logic

`kj.a(ki, kl, logic, kh, enableAggro)` is the core per-frame monster updater.

Recovered behavior:

- each monster advances using `ki.d * ki.a[] / ki.b[]`
- if it hits collision in the map logic grid, it flips direction
- if its rectangle overlaps the player while the player is moving sideways,
  it can switch into aggressive / contact behavior
- if the view/collision helper `kh` rejects movement, it also changes facing

Evidence:

- [kj.java:4](/d:/Twelve/reference/redecoded/decompiled/kj.java:4)

Practical interpretation:

- monsters are not static decorations
- they roam locally on the map
- they have simple autonomous motion and contact reaction

For the remake, even if the pathing is simplified, monsters should still:

- patrol / drift
- flip facing at obstacles
- react when the player enters their contact rectangle

Otherwise the map will feel much less like Java.

## Monster Detection / Encounter Trigger Flow

The `om.n()` update loop shows two distinct encounter triggers.

### 1. Direct player collision with live monster

If the player is in movement state and their small collision box `u`
touches a monster's `ki.e`, `om`:

- starts encounter flow `a(ki, true)`
- remembers selected monster index `U`
- flips both monster and player facing toward each other

Evidence:

- [om.java:664](/d:/Twelve/reference/redecoded/decompiled/om.java:664)

### 2. Monster-driven aggressive contact

Every visible monster is also updated through `kj`.
If a monster reaches state `1`, `om` treats it as engagement-ready and can
start encounter flow with `a(ki, false)`.

Evidence:

- [om.java:690](/d:/Twelve/reference/redecoded/decompiled/om.java:690)

This is important because it means Java map encounters are not purely
"press interact near monster". They can emerge from motion/contact state.

## Encounter Dialog Handoff

`om.a(ki, boolean)` is the final non-battle handoff.

It:

1. freezes map flow into encounter mode
2. stores a world anchor based on the monster rect
3. creates `ha(playerActor, monsterActorClone, ...)`
4. sends the battle request using `ks.a().a(ki.f.a, bl2)`

Evidence:

- [om.java:797](/d:/Twelve/reference/redecoded/decompiled/om.java:797)

Important details:

- encounter dialog uses `ki.b()` clone, not the same live actor object
- Java explicitly caches the monster anchor `E` for transition timing / effects
- once the dialog ends without battle, `om.q()` restores map state

Evidence:

- [ki.java:228](/d:/Twelve/reference/redecoded/decompiled/ki.java:228)
- [om.java:783](/d:/Twelve/reference/redecoded/decompiled/om.java:783)

## Render Stack / Depth Sorting for Monsters

`kd` is the map render bucket that determines draw order.

Recovered behavior:

- player, monsters, and some objects are gathered into `g`
- visible monsters are filtered by viewport
- entries are depth-sorted primarily by bottom edge `o() + q()`
- tie-breaking also considers actor layer `l()`

Evidence:

- [kd.java:52](/d:/Twelve/reference/redecoded/decompiled/kd.java:52)

For the remake this matters a lot visually:

- monsters should not just be drawn in insertion order
- they should be depth-sorted against the player and world props

## Monster Preview Dialog Is Part of Map Flow, Not Battle Flow

`ha` in its map-backed constructor takes:

- player actor `at`
- monster actor `at` which is actually `ki`
- initiative / side flag
- callback owner

It then reads monster-facing map data only:

- `jo.c` for icon
- `jo.e` for IQ bucket
- `jo.b` for display name
- `jo.d` for displayed level

Evidence:

- [ha.java:35](/d:/Twelve/reference/redecoded/decompiled/ha.java:35)

So the correct reconstruction split is:

- `map flow` ends at `ha`
- `battle flow` begins only after the server returns `lh`

Do not collapse these two screens into one concept in the remake.

## Dialog Guard / Callback IDs Around Monster Encounters

The Java client also exposes a small but important bit of encounter runtime
plumbing through numeric dialog ids.

### Confirmed IDs

| Id | Meaning | Evidence |
|----|---------|----------|
| `191919` | active encounter / combat preview dialog `ha` | [ha.java:98](/d:/Twelve/reference/redecoded/decompiled/ha.java:98), [om.java:694](/d:/Twelve/reference/redecoded/decompiled/om.java:694), [oa.java:367](/d:/Twelve/reference/redecoded/decompiled/oa.java:367) |
| `99030` | callback code used when encounter dialog finishes | [ha.java:52](/d:/Twelve/reference/redecoded/decompiled/ha.java:52), [oa.java:268](/d:/Twelve/reference/redecoded/decompiled/oa.java:268) |

### Observed behavior

- `om` refuses to open another encounter while dialog `191919` exists
- `oa` also checks `191919` before constructing a second `ha`
- when callback `99030` is received, `oa` routes control into battle follow-up via `ol.j(2)`

Implication for the remake:

- encounter preview should be treated as a modal guarded state
- duplicate monster engagement while preview is open should be blocked

## Exhaustive Client Touchpoint Inventory

This section is the closest thing to a "done list" for client-side monster tracing.

### Monster-specific map classes

| Class | Role |
|-------|------|
| `jo` | spawn/encounter data record |
| `ki` | live map monster actor |
| `om` | map scene owner for spawn, update, engage, remove |
| `ha` | encounter preview dialog |
| `kj` | per-frame monster roaming/contact updater |
| `kd` | render/depth bucket that carries visible monsters |

### Monster-specific bridge / packet classes

| Class | Role |
|-------|------|
| `ky` | parses monster packet into `jo[]` |
| `kq` | callback contract for monster updates |
| `com.mg.sq.a` | global app dispatcher implementing `kq` |
| `oa` | platform/map screen bridge forwarding to `om` |
| `ks` | sends battle request by monster key |

### Battle monster classes

| Class | Role |
|-------|------|
| `lh` | authoritative fighter payload |
| `lv` | skill entry inside fighter payload |
| `lg` | live runtime HP/MP/Power wrapper |
| `mx` | actor/HUD renderer |
| `mb` | body-part compositor |
| `ni` | battle actor state machine |
| `nq` | turn/result packet model |
| `nl` | per-fighter delta model |
| `df` | appearance / recolor layer descriptor |

### Shared infrastructure that touches monster flow but is not monster-only

| Class | Role in flow |
|-------|--------------|
| `kh` | camera + viewport + boundary helper used by monster roaming and map rendering |
| `kl` | player world actor that monsters collide / face against |
| `jv` | abstract updater base; `kj` and `km` both derive from it |
| `km` | player movement updater; not monster-specific but participates in encounter contact state |
| `pa` | resource/cache loader that gates map continuation on monster-related assets |

### Notable non-findings from the final sweep

After the last repo-wide scan, no extra hidden monster subsystem was found beyond:

- map spawn / map actor / preview flow
- packet bridge and remove/add callbacks
- battle payload / actor / delta flow

In other words, the client does **not** appear to contain:

- a hidden authoritative per-map monster roster database
- a hidden full monster stat catalog for all maps
- a separate monster AI rules engine outside the traced map/battle runtime

## Remaining Client Unknowns After the Final Sweep

After this pass, the unresolved items are now narrow and mostly semantic:

- exact server-side meaning difference between monster packet modes `0` and `3`
- exact semantic labels for a few `lh` flags like `f`, `Z`, `aa`, `ac`
- exact human-readable names for some `ni` motion states, though runtime role is already mostly recovered

These are no longer "missing systems". They are small interpretation gaps.

So the practical conclusion is:

- monster **client flow** has been recovered very deeply
- the big missing authority remains the **Java server**

## Map Monster Animation Contract

`ki.java` uses 6 horizontal frames but not all frames equally.

The animation tables are hard-coded:

- walk sequence array `i = [0, 0, 0, 4, 4, 4, 5, 5, 5]`
- alt/attack-like sequence `j = [3, 3, 3, 2, 2, 3, 1, 1, 1]`

Evidence:

- frame arrays: [ki.java:47](/d:/Twelve/reference/redecoded/decompiled/ki.java:47)
- sequence switch: [ki.java:132](/d:/Twelve/reference/redecoded/decompiled/ki.java:132)

So the remake should keep:

- `1 row x 6 frames`
- walk frames centered around `0, 4, 5`
- attack/aggressive frames centered around `3, 2, 1`

## Name / Difficulty Tint Logic

The map renderer colors the monster nameplate using:

- explicit override from `jo.g`
- otherwise relative level difference `jo.d - playerLevel`

Evidence: [ki.java:75](/d:/Twelve/reference/redecoded/decompiled/ki.java:75).

Observed rules:

- `jo.g == 1` -> red tint
- `jo.g == 2` -> alternate blue-ish tint
- otherwise:
  - if `monsterLevel - playerLevel >= 5` -> high danger tint
  - if `monsterLevel - playerLevel < -9` -> trivial / grey tint
  - else default neutral tint

This is UI-only data, but preserving it will make the remake look much more
like Java with very little cost.

## Encounter Preview Contract

The pre-battle encounter dialog in [ha.java](/d:/Twelve/reference/redecoded/decompiled/ha.java)
reads directly from the live map monster actor.

Displayed fields:

- monster icon from `jo.c`
- monster name from `jo.b`
- monster level from `jo.d`
- monster IQ label from `jo.e`

What it does **not** know yet:

- HP
- MP
- real battle skill list
- defense / attack stats

This is the correct legacy split to preserve in the new project:

- `encounter preview` should use lightweight spawn data
- `battle scene` should use fully resolved battle data

## Battle Handoff and Authority

The old client flow is:

1. map receives `jo[]`
2. client renders local monster actors
3. player selects / collides with a live monster
4. client sends `jo.a` back to server to request battle
5. server returns full fighter data for the enemy
6. battle UI uses `lh` / `lv` as truth

Important consequence:

- `jo` is **not** a battle stat object
- `jo` is a map encounter object
- `lh` is the battle stat object

## Battle Monster Payload (`lh` / `lv`)

The battle fighter parser in [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:1003)
reads the authoritative monster battle payload into `lh`.

Confirmed fields relevant to monster reconstruction:

| Field | Meaning |
|-------|---------|
| `lh.g` | element / type battle byte |
| `lh.s / lh.r` | current HP / max HP |
| `lh.u / lh.t` | current MP / max MP |
| `lh.h / lh.i / lh.j / lh.k` | base stats |
| `lh.D` | equipped items |
| `lh.E` | battle skills (`lv[]`) |
| `lh.F` | carried item entries |

The embedded `lv[]` entries in this packet carry at least:

- skill id
- skill level
- mana cost

This is the layer where the remake should resolve:

- HP
- MP
- element
- battle skills
- combat stats

not from the map spawn packet.

## Battle Fighter Field Map (`lh`)

`lh` là object gần với "monster truth" nhất mà Java client còn cho thấy.

Nó không chỉ là HP/MP đơn giản. Nó còn mang:

- stat chiến đấu
- visual layer để dựng battle body
- skill list
- equipment / item payload
- level và một số bonus combat fields

### Confirmed Fields Worth Keeping in the Remake

| Field | Java evidence | Meaning | Confidence |
|-------|---------------|---------|------------|
| `lh.a` | [ky.java:1008](/d:/Twelve/reference/redecoded/decompiled/ky.java:1008), [ld.java:4](/d:/Twelve/reference/redecoded/decompiled/ld.java:4) | fighter type / id inherited from `ld` | medium |
| `lh.b` | [ky.java:1009](/d:/Twelve/reference/redecoded/decompiled/ky.java:1009) | battle display name | high |
| `lh.c` | [ky.java:1010](/d:/Twelve/reference/redecoded/decompiled/ky.java:1010) | extra display string / title text from packet | medium-low |
| `lh.g` | [ky.java:1011](/d:/Twelve/reference/redecoded/decompiled/ky.java:1011) | battle element / type byte | high |
| `lh.f` | [ky.java:1012](/d:/Twelve/reference/redecoded/decompiled/ky.java:1012) | fighter-side / style byte used by battle runtime | medium |
| `lh.G` | [ky.java:1013](/d:/Twelve/reference/redecoded/decompiled/ky.java:1013), [ha.java:260](/d:/Twelve/reference/redecoded/decompiled/ha.java:260) | level | high |
| `lh.s / lh.r` | [ky.java:1014](/d:/Twelve/reference/redecoded/decompiled/ky.java:1014), [lh.java:145](/d:/Twelve/reference/redecoded/decompiled/lh.java:145) | current HP / max HP | high |
| `lh.u / lh.t` | [ky.java:1016](/d:/Twelve/reference/redecoded/decompiled/ky.java:1016), [lh.java:146](/d:/Twelve/reference/redecoded/decompiled/lh.java:146) | current MP / max MP | high |
| `lh.w / lh.v` | [ky.java:730](/d:/Twelve/reference/redecoded/decompiled/ky.java:730), [ky.java:815](/d:/Twelve/reference/redecoded/decompiled/ky.java:815), [lh.java:147](/d:/Twelve/reference/redecoded/decompiled/lh.java:147) | current Power / max Power | high |
| `lh.h / lh.j / lh.i / lh.k` | [ky.java:1018](/d:/Twelve/reference/redecoded/decompiled/ky.java:1018), [lh.java:137](/d:/Twelve/reference/redecoded/decompiled/lh.java:137) | strength / agility / magic / vitality | high |
| `lh.l / lh.m / lh.n / lh.o` | [ky.java:1022](/d:/Twelve/reference/redecoded/decompiled/ky.java:1022), [lh.java:141](/d:/Twelve/reference/redecoded/decompiled/lh.java:141) | additive stat bonuses | medium-high |
| `lh.p / lh.q` | [ky.java:1026](/d:/Twelve/reference/redecoded/decompiled/ky.java:1026) | addHealth / health-percent-like bonus fields | medium |
| `lh.x / lh.y` | [lh.java:148](/d:/Twelve/reference/redecoded/decompiled/lh.java:148), [docs/combat-formulas.md](/d:/Twelve/docs/combat-formulas.md:20) | min / max damage range | high |
| `lh.z` | [lh.java:150](/d:/Twelve/reference/redecoded/decompiled/lh.java:150), [docs/combat-formulas.md](/d:/Twelve/docs/combat-formulas.md:24) | defense | high |
| `lh.A / lh.B / lh.C` | [lh.java:149](/d:/Twelve/reference/redecoded/decompiled/lh.java:149), [lh.java:150](/d:/Twelve/reference/redecoded/decompiled/lh.java:150) | dodge rate / hit rate / critical damage | medium-high |
| `lh.D` | [ky.java:1059](/d:/Twelve/reference/redecoded/decompiled/ky.java:1059) | equipped visual / gear entries | high |
| `lh.E` | [ky.java:1045](/d:/Twelve/reference/redecoded/decompiled/ky.java:1045) | battle skill list `lv[]` | high |
| `lh.F` | [ky.java:1067](/d:/Twelve/reference/redecoded/decompiled/ky.java:1067) | carried item entries | high |
| `lh.U / lh.V / lh.W` | [ky.java:1078](/d:/Twelve/reference/redecoded/decompiled/ky.java:1078), [ky.java:1089](/d:/Twelve/reference/redecoded/decompiled/ky.java:1089) | 3 appearance layer descriptors for battle body assembly | high |
| `lh.Z / lh.aa` | [ky.java:1041](/d:/Twelve/reference/redecoded/decompiled/ky.java:1041) | packet booleans affecting runtime behavior / appearance flags | medium |
| `lh.ac` | [ky.java:1098](/d:/Twelve/reference/redecoded/decompiled/ky.java:1098) | extra timed / long-value entries | low-medium |

### What `lv` Confirms for Monster Skills

Each `lv` entry embedded in `lh.E` carries at least:

- `lv.a` = skill id
- `lv.f` = skill level
- `lv.e` = mana cost

Evidence:

- [ky.java:1051](/d:/Twelve/reference/redecoded/decompiled/ky.java:1051)
- [lv.java:4](/d:/Twelve/reference/redecoded/decompiled/lv.java:4)

This is enough to reconstruct monster skill loadouts cleanly in the remake,
even without the old server.

## Battle Runtime Wrapper (`lg`)

Once `lh` has been parsed, Java does not mutate `lh` directly everywhere.
It wraps it inside `lg`.

This wrapper is important because it shows exactly which live battle values
the Java client expects to animate and change over time.

### Confirmed Live Getters / Setters

| `lg` method | Meaning |
|-------------|---------|
| `l()` / `m()` | max HP / current HP |
| `n()` / `o()` | current MP / max MP |
| `q()` / `r()` | current Power / max Power |
| `g(int)` | set current HP |
| `h(int)` | set current MP |
| `j(int)` | set current Power |
| `i(skillId)` | look up a skill entry by id |
| `p()` | check whether Power is full |

Evidence:

- [lg.java:88](/d:/Twelve/reference/redecoded/decompiled/lg.java:88)
- [lg.java:100](/d:/Twelve/reference/redecoded/decompiled/lg.java:100)
- [lg.java:129](/d:/Twelve/reference/redecoded/decompiled/lg.java:129)

`lg` also carries several countdown-style flags (`d/e/f/g/h`) decremented in
`s()`. The exact semantic names are not fully recovered, but they are clearly
battle status timers, so the remake should leave room for per-monster runtime
status durations instead of treating monsters as stateless sprites.

## Battle Actor Appearance Assembly

Map monsters are lightweight sprites. Battle monsters are much richer.

Java battle actors are assembled from multiple visual layers and metadata files,
not from a single flat sprite strip.

### Appearance Layers from `lh`

`ky.java` parses repeated packet entries into `df` objects, then assigns them by slot:

- slot `0` -> `lh.U`
- slot `1` -> `lh.V`
- slot `2` -> `lh.W`

Evidence:

- [ky.java:1068](/d:/Twelve/reference/redecoded/decompiled/ky.java:1068)
- [df.java:4](/d:/Twelve/reference/redecoded/decompiled/df.java:4)

Each `df` contains:

- a base numeric asset id
- a source palette map `d`
- a destination palette map `e`
- an array `f` of palette variants

That means battle monster appearance in Java is not only "which PNG".
It is also "which palette remap over which base body part".

### Body-Part Composition from `mb`

`mb.a(lh)` loads 4 metadata bundles, then the battle renderer composes them
into animation states.

Important evidence:

- metadata bundle resolution: [mb.java:706](/d:/Twelve/reference/redecoded/decompiled/mb.java:706)
- cache key includes palette signatures from `U/V/W`: [mb.java:730](/d:/Twelve/reference/redecoded/decompiled/mb.java:730)
- per-state factory methods: [mb.java:289](/d:/Twelve/reference/redecoded/decompiled/mb.java:289)

Implication:

- two monsters can share the same structural body metadata
- but differ by palette layer or part sheet
- therefore the remake should separate `battle body definition` from `battle stats`

## Battle Actor State Machine (`ni`)

This is the deepest monster-specific battle behavior that Java still exposes.

`mx.java` builds a composite battle actor from 6 motion bundles plus 1 cast bundle:

- `mb.a(...)`
- `mb.b(...)`
- `mb.c(...)`
- `mb.d(...)`
- `mb.e(...)`
- `mb.f(...)`
- `mb.a(..., mp.a().h, ...)` for cast / channel support

Evidence:

- [mx.java:1533](/d:/Twelve/reference/redecoded/decompiled/mx.java:1533)
- [mx.java:1557](/d:/Twelve/reference/redecoded/decompiled/mx.java:1557)

`ni.a(int state)` then switches between those bundles.

### Inferred State Map

The exact Java enum names are lost, but behavior from call sites is clear enough
to recover the runtime roles:

| State | Likely role | Evidence |
|-------|-------------|----------|
| `0` | idle / standing loop | default state in constructor and after most actions |
| `1` | strike / contact animation | reached after forward movement in [ni.java:224](/d:/Twelve/reference/redecoded/decompiled/ni.java:224) |
| `2` | advance toward target | entered from attack start in [ni.java:92](/d:/Twelve/reference/redecoded/decompiled/ni.java:92) |
| `3` | hurt reaction | used by `a(false, false)` in [ni.java:122](/d:/Twelve/reference/redecoded/decompiled/ni.java:122) |
| `4` | down / defeat reaction | entered by `d()` in [ni.java:126](/d:/Twelve/reference/redecoded/decompiled/ni.java:126) |
| `5` | return / retreat | entered after strike finishes in [ni.java:218](/d:/Twelve/reference/redecoded/decompiled/ni.java:218) |
| `6` | cast / channel | entered by `a(boolean)` and `e()` in [ni.java:130](/d:/Twelve/reference/redecoded/decompiled/ni.java:130) |
| `7` | evade / miss / guard reaction | used by miss / block flow in [ni.java:100](/d:/Twelve/reference/redecoded/decompiled/ni.java:100) |
| `8` | alternate hurt alias | maps to same motion as state `3` in [ni.java:164](/d:/Twelve/reference/redecoded/decompiled/ni.java:164) |

### What This Means for the Remake

If the goal is to look very close to Java, a battle monster template should not
only say:

- HP
- MP
- element
- skill ids

It should also reference a `battle actor pack` that contains enough state data
to render at least:

- idle
- advance
- strike
- retreat
- hurt
- cast
- evade / guard
- down / defeat

## Battle HUD Contract for Monsters

Monster and player both use the same HP / MP / Power HUD system in battle.

`mx.c(boolean)` loads:

- `/play/hpbar`
- `/play/manabar`
- `/play/powerbar`

Evidence:

- [mx.java:1568](/d:/Twelve/reference/redecoded/decompiled/mx.java:1568)

Then `mx.a(int hp, int mp, int power, String owner, int tweenFlag)` updates the
bars for the fighter matched by name.

Evidence:

- [mx.java:2060](/d:/Twelve/reference/redecoded/decompiled/mx.java:2060)
- [mi.java:20](/d:/Twelve/reference/redecoded/decompiled/mi.java:20)

Important practical point:

- the Java client clearly expects monsters to have visible HP, MP, and Power
- those three resources are first-class runtime values, not optional extras

## Turn Result / Attribute Delta Pipeline

This is the key piece that explains how monsters change after a skill or turn.

### 1. Packet decode

`ky.b(ku, nq)` parses `nq.f = new nl[...]`.

Each `nl` entry contains at least:

- target fighter name
- `damage`-like value from tag `46`
- resulting HP from tag `17`
- resulting MP from tag `18`
- resulting Power from tag `45`
- extra byte from tag `19`

Evidence:

- [ky.java:1851](/d:/Twelve/reference/redecoded/decompiled/ky.java:1851)
- [nl.java:4](/d:/Twelve/reference/redecoded/decompiled/nl.java:4)

### 2. Runtime application

`mq.a(String owner, nl[] deltas)` walks all fighters, matches each delta by name,
then:

- animates HP / MP / Power bars through `mx`
- writes the new current HP / MP / Power into `lg`
- emits hit feedback if HP dropped and the previous delta contained damage

Evidence:

- [mq.java:125](/d:/Twelve/reference/redecoded/decompiled/mq.java:125)
- [mq.java:157](/d:/Twelve/reference/redecoded/decompiled/mq.java:157)

### 3. Consequence for the remake

To stay Java-like, battle should not jump straight from "cast skill" to
"hard overwrite current stats" with no intermediate model.

It should have a turn result object equivalent to:

```ts
type MonsterBattleDelta = {
  combatantKey: string;
  damage?: number;
  hp?: number;
  mp?: number;
  power?: number;
  timingByte?: number;
};
```

One thing worth improving over the original Java:

- Java matches deltas by fighter name string
- the remake should keep a stable combatant id and only use names for display

## Monster Truth We Still Cannot Recover from the Client Alone

Even after extracting all of the above, the Java client still does **not**
give us certain data for free:

- real map-to-monster roster for each zone if the server was the source
- exact server formulas for damage, hit, dodge, crit, AI decisions
- exact server-selected target arrays and legality checks for all monster skills
- exact server-authored drop tables or reward logic

So the remake goal should be:

- recover every client-visible monster contract from Java
- author the missing server truth explicitly in the new catalogs
- never mix the two layers

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

### Excluded (moved to equipment / npc)

After cross-checking IDs against the other bundles, the following ranges
that were initially staged here were confirmed to belong elsewhere and
have been MOVED out of `monster`:

| Former bucket | Files | Correct bundle | Reason |
|---------------|-------|----------------|--------|
| `range_12xxxx_end98_meta_adjacent` | 25 | `equipment/07_accessory_e5_e7_e8/` | End-`98` = accessory-icon convention |
| `range_128xxx_end98_meta_adjacent` |  4 | `equipment/07_accessory_e5_e7_e8/` | Same end-`98` pattern |
| `range_140xxx_candidate`           |  1 | `equipment/07_accessory_e5_e7_e8/` | `140098` is accessory, not monster |
| `range_130xxx_candidate`           |  4 | `equipment/07_accessory_e5_e7_e8/` | Entire `130xxx` band is accessory |
| `range_110xxx_pattern_X0`          | 17 | `npc/04_numbered_npc_candidate_110xxx/` | Numbered NPC sprites, `X0` step |

Total relocated: **51 files**. MUST NOT be re-copied into `monster/`.
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

### Clarification for the remake

The list above is still correct for the raw asset layer.

However, because the remake no longer has the original Java server, the new
project **must** author a new gameplay catalog.

That new catalog should not be mixed into the raw asset index.

Instead keep 3 separate layers:

1. `monster asset catalog`
2. `monster spawn catalog`
3. `monster battle catalog`

This preserves the old Java separation while still giving the remake enough
authoritative data to run without the original server.

## Recommended New Canonical Model

To stay as close as possible to Java while still being practical without the
old server, use the following split.

### A. Asset catalog

Pure visual data.

```ts
type MonsterAssetCatalogEntry = {
  assetId: string;          // stable internal id in the remake
  speciesCode?: number;     // optional numeric family such as 1003
  slot?: number;            // optional numeric slot
  framePaths: string[];     // 6-frame strip or equivalent source
  sharedSheetFamily?: 'monster' | 'zap' | 'ice';
};
```

### B. Spawn catalog

Legacy-like lightweight encounter data. This is the closest equivalent to `jo`.

```ts
type MonsterSpawnTemplate = {
  monsterKey: string;       // runtime encounter key, equivalent role to jo.a
  displayName: string;      // jo.b
  visualTypeByte: number;   // jo.c
  displayLevel: number;     // jo.d
  iqValue: number;          // jo.e
  spawnCount: number;       // jo.f
  nameColorMode: number;    // jo.g
  battleTemplateId: string; // NEW: explicit link to battle truth
};
```

### C. Battle catalog

Authoritative replacement for the old server-side fighter payload.

```ts
type MonsterBattleTemplate = {
  battleTemplateId: string;
  element: 1 | 2 | 4;       // legacy-like Hỏa / Lôi / Thủy byte
  level: number;
  maxHp: number;
  maxMp: number;
  stats: {
    strength: number;
    agility: number;
    magic: number;
    vitality: number;
    minDamage?: number;
    maxDamage?: number;
    defense?: number;
  };
  skills: Array<{
    skillId: number;
    level: number;
    manaCost: number;
  }>;
};
```

### D. Map placement data

For the new project, placement should be authored explicitly, but the old
Java spirit can still be preserved by keeping placement separate from the
monster template itself.

```ts
type MonsterSpawnPlacement = {
  mapId: string;
  roomId?: string;
  spawnTemplateKey: string;
  zoneId?: string;
  fixedPoints?: Array<{ x: number; y: number }>;
  randomArea?: { x1: number; x2: number; y1: number; y2: number; maxCount: number };
};
```

## Server Reconstruction Strategy

This is the most important architectural section for the remake.

The goal is **not** to pretend we can restore the original Java server 1:1.
The goal is to rebuild a new authoritative server whose boundaries match what
the Java client clearly expected.

### Core Position

The traced Java client proves a stable 3-stage monster authority chain:

1. `map encounter truth` -> `jo`
2. `battle fighter truth` -> `lh` / `lv`
3. `turn result truth` -> `nq` / `nl`

So the replacement server should expose the same logical chain:

1. lightweight spawn records for the map
2. a resolved battle enemy definition for battle entry
3. authoritative turn deltas after each skill / turn

Any server design that collapses all 3 layers into one flat monster object
will drift away from Java behavior very quickly.

### What the Current Repo Already Gets Right

The current codebase already contains a very important idea:

- `BattleSkillPacketFactory` explicitly says it should only convert Java-shaped
  battle arrays **after** the server already knows the true targets and outcome
  ([BattleSkillPacketFactory.cs](/d:/Twelve/server/Twelve.Application/Battle/BattleSkillPacketFactory.cs:8))
- `BattleSkillCastPacketService` explicitly says it is reconstructing missing
  server logic from the selected cell only because the Java server source is gone
  ([BattleSkillCastPacketService.cs](/d:/Twelve/server/Twelve.Application/Battle/BattleSkillCastPacketService.cs:7))

That means the repo is already leaning toward the correct end-state:

- domain authority first
- packet adapter second

This document formally recommends preserving that direction.

### What the Current Repo Still Does as a Placeholder

`MapHandler` currently sends a placeholder scene actor payload with one player
and one boss-like actor, including direct coordinates.

Evidence:

- [MapHandler.cs](/d:/Twelve/server/Twelve.Application/Handlers/MapHandler.cs:21)

This is useful as a sandbox, but it does **not** match the recovered Java
monster-map flow yet, because the Java client monster flow expects:

- map logic layer with spawn markers
- separate monster roster packet decoded into `jo[]`
- stable `monsterKey`
- encounter-to-battle handoff by key

So `MapHandler` should be treated as temporary scaffolding, not as the final
shape of the monster server.

### Target Server Boundary

The replacement server should be split into 4 authorities.

| Authority | Java mirror | What it owns |
|-----------|-------------|--------------|
| `Map roster authority` | `jo` | which monster encounters exist on a map/zone right now |
| `Battle bootstrap authority` | `lh` / `lv` | which concrete enemy fighter enters battle |
| `Battle turn authority` | `nq` / `nl` | which cells/targets/results happen after a cast |
| `Reward/content authority` | post-battle server truth | exp, gold, drops, quest progress |

This split is the cleanest way to stay close to Java while still building a new server.

## Recommended Server Modules

### 1. `MonsterAssetCatalog`

Purely visual and shared by map + battle.

Responsibilities:

- map internal monster asset ids to numeric family/slot/frame source
- resolve shared map sheet family `/monster` / `/zap` / `/ice`
- link to battle body/appearance packs where needed

This module must remain non-authoritative for gameplay.

### 2. `MonsterSpawnCatalog`

Java-like map encounter authority.

Responsibilities:

- store the server-authored equivalent of `jo`
- define:
  - `monsterKey`
  - display name
  - visual type byte
  - display level
  - IQ value
  - spawn count
  - name color mode
  - `battleTemplateId`

This is what the map layer should query.

### 3. `MapMonsterRosterService`

Runtime map authority.

Responsibilities:

- given `mapId`, `roomId`, and maybe `channelId`, produce the active monster roster
- apply spawn placement rules
- keep the roster separate from tiles/background/player actors
- return/update/remove monsters by `monsterKey`

This service is the correct long-term replacement for the current placeholder
monster part inside `MapHandler`.

### 4. `MonsterBattleCatalog`

Battle-entry authority.

Responsibilities:

- resolve `battleTemplateId` into the equivalent of `lh` + `lv`
- define:
  - element
  - level
  - max HP / MP / Power
  - strength / agility / magic / vitality
  - damage range / defense / accuracy-related values
  - skill list with level + mana cost
  - appearance/body descriptors used by battle actor assembly

This is the correct replacement for the missing original Java server monster stat catalog.

### 5. `MonsterBattleFactory`

Battle bootstrap authority.

Responsibilities:

- take `monsterKey`
- resolve spawn template
- resolve battle template
- produce a concrete enemy battle instance for one battle session

This is where the server should finally decide:

- exact current HP/MP/Power at battle start
- battle-only modifiers
- random seed if any battle variation is introduced

### 6. `BattleTurnEngine`

This is the real missing heart of the Java server.

Responsibilities:

- validate the cast
- resolve target arrays
- apply damage/heal/status/resource changes
- produce authoritative actor deltas
- produce authoritative turn deltas
- then call a packet adapter that turns those results into client packets

This engine should own the true gameplay result.
It should **not** live inside the packet factory itself.

### 7. `BattlePacketAssembler`

Pure translation layer.

Responsibilities:

- convert server domain results into:
  - current RN client packet shape
  - optional future Java-like packet shape for diagnostics

The current `BattleSkillPacketFactory` is already close to this role and should
stay a pure adapter.

## Recommended C# Canonical Contracts

Below is the most logical server-side split for the current repo.

### A. Spawn template

```csharp
public sealed record MonsterSpawnTemplate(
    string MonsterKey,
    string DisplayName,
    byte VisualTypeByte,
    int DisplayLevel,
    int IqValue,
    int SpawnCount,
    byte NameColorMode,
    string BattleTemplateId,
    string? AssetCatalogId = null
);
```

### B. Map roster entry

```csharp
public sealed record MapMonsterEncounter(
    string MonsterKey,
    string MapId,
    int RoomId,
    string SpawnTemplateKey,
    int SpawnCellRow,
    int SpawnCellCol,
    bool IsActive = true
);
```

Important note:

- this roster entry is runtime state
- it is **not** the same thing as the spawn template

### C. Battle template

```csharp
public sealed record MonsterBattleTemplate(
    string BattleTemplateId,
    byte Element,
    int Level,
    int MaxHp,
    int MaxMp,
    int MaxPower,
    int Strength,
    int Agility,
    int Magic,
    int Vitality,
    int MinDamage,
    int MaxDamage,
    int Defense,
    int HitRate,
    int DodgeRate,
    int CriticalDamage,
    IReadOnlyList<MonsterSkillTemplate> Skills,
    MonsterAppearanceTemplate Appearance,
    string? AiProfileId = null
);
```

### D. Battle instance

```csharp
public sealed record MonsterBattleInstance(
    string CombatantId,
    string MonsterKey,
    string BattleTemplateId,
    string DisplayName,
    byte Element,
    int Level,
    int CurrentHp,
    int MaxHp,
    int CurrentMp,
    int MaxMp,
    int CurrentPower,
    int MaxPower,
    IReadOnlyList<MonsterSkillInstance> Skills,
    MonsterAppearanceTemplate Appearance
);
```

This is the correct place for battle-local mutable state.
Do **not** mutate the catalog template itself.

### E. Turn result

```csharp
public sealed record BattleTurnResult(
    string CastId,
    int FamilyCode,
    string CasterCombatantId,
    IReadOnlyList<BattleBoardMutationResult> BoardMutations,
    IReadOnlyList<BattleActorDeltaResult> ActorDeltas,
    BattleTurnDeltaResult? TurnDelta,
    BattleVisualPacketSeed VisualSeed
);
```

This is the direct server replacement for the old `nq` / `nl` truth layer.

## Strict Authority Rules

If the replacement server wants to stay close to Java, the following rules must hold.

### Server-authoritative only

- active monster roster per map/channel
- battle entry resolution from `monsterKey`
- skill legality
- target selection
- damage/heal/resource changes
- status durations
- extra-turn / turn-timer changes
- rewards / drops

### Client-authoritative only

- map rendering
- local roaming playback
- encounter preview rendering
- battle animation playback
- HP/MP/Power tweening after authoritative deltas already exist

### Transitional-only and must not become canon

- `DebugSkillLevel` from the current `BattleSkillCastRequest`
  ([BattleSkillCastRequest.cs](/d:/Twelve/server/Twelve.Core/Battle/BattleSkillCastRequest.cs:5))
- family-specific targeting reconstructed only from selected board cell inside
  `BattleSkillCastPacketService`
  ([BattleSkillCastPacketService.cs](/d:/Twelve/server/Twelve.Application/Battle/BattleSkillCastPacketService.cs:28))

These are useful scaffolds, but they must stay labeled as transitional.

## End-to-End Server Flows

### Flow 1. Map load

1. client requests map info
2. server returns map layout / logic layer
3. server returns active monster roster as `jo-like` encounter records
4. client spawns local `ki` actors from that roster

Key rule:

- the map packet must not already contain battle stats

### Flow 2. Encounter start

1. client collides with or engages a map monster
2. client sends `monsterKey`
3. server resolves `monsterKey -> spawn template -> battle template`
4. server creates a battle instance
5. server sends the enemy fighter truth

Key rule:

- battle entry must resolve by key, not by sprite family or display name

### Flow 3. Skill cast

1. client sends cast request
2. server validates against battle session state
3. `BattleTurnEngine` computes the authoritative result
4. `BattlePacketAssembler` converts the result into the runtime packet
5. client plays visuals and applies deltas

Key rule:

- do not let packet-building logic decide gameplay truth

## Migration Plan for the Current Repo

The current repo should evolve in the following order.

### Phase 1. Replace placeholder map monster sending

Replace the monster-related part of `MapHandler` with:

- map layout from map store
- separate monster roster from `MapMonsterRosterService`
- stable `monsterKey`

Do not send hard-coded boss actors as the final design.

### Phase 2. Add server monster catalogs

Introduce:

- `MonsterAssetCatalog`
- `MonsterSpawnCatalog`
- `MonsterBattleCatalog`
- `MapMonsterRosterService`

At this stage the project can already place real monsters on maps and enter battle by key.

### Phase 3. Split battle authority from packet reconstruction

Refactor current battle packet logic into:

- `BattleTurnEngine`
- `BattlePacketAssembler`

The current `BattleSkillPacketFactory` can remain the packet half.
The current `BattleSkillCastPacketService` should shrink until it becomes an orchestrator
or disappear into the new engine.

### Phase 4. Replace debug-only skill level sourcing

Remove gameplay dependence on `DebugSkillLevel`.

Skill level must come from:

- player battle state
- monster battle template
- or server-side character progression

### Phase 5. Add content and AI

Only after the authority split is clean should the project add:

- per-map monster rosters
- monster stat content
- monster skill loadouts
- AI profiles
- reward tables

## Anti-Solutions To Avoid

These would be fast, but they would drift away from Java and create rework.

### 1. One giant `Monster` table for everything

Bad because it merges:

- asset truth
- map encounter truth
- battle truth

Java clearly kept those separate.

### 2. Letting the client decide skill targets or battle result

Bad because the Java client was never authoritative for final outcome.

### 3. Deriving stats from numeric sprite ids

Bad because `/offline/<id>` only tells us asset grouping, not gameplay truth.

### 4. Hard-coding map monsters inside view components long-term

Bad because the Java shape was server-driven roster + client local playback.

## Practical Conclusion

The logically correct solution is:

- keep `BattleSkillPacketFactory` as a pure adapter
- replace placeholder map monster sending with a real spawn roster service
- introduce separate spawn and battle catalogs
- build a true `BattleTurnEngine`
- treat every missing gameplay value as new server-authored truth, not as something hidden in the Java client

That is the closest reconstruction path to the old Java architecture that is
still honest about what we do and do not know.

## Non-Negotiable Reconstruction Rules

If the goal is `>90% giống Java`, the new implementation should keep these
rules:

1. Entering battle from map must be done by monster key, not by sprite name.
2. Map spawn data must stay lightweight; do not stuff HP/MP/skills into it.
3. Battle enemy stats must resolve from a separate authoritative template.
4. Keep the raw `visualTypeByte` and derive visual family from it.
5. Preserve the 6-frame monster strip contract.
6. Preserve level / IQ encounter preview behavior.
7. Preserve name tint / danger tint behavior where possible.
8. Keep asset catalog, spawn catalog, and battle catalog as separate tables.
9. Never guess gameplay values from `/offline/<id>` filenames alone.
10. Numeric family art and battle template must be linked explicitly by catalog.

## Regeneration

The folder is fully reproducible by running:

```bash
python3 /sessions/magical-happy-bohr/tools/organize_monsters.py
```

The script reads from `canonical_from_jar_png/offline/`, classifies every
6-digit PNG by the schema above, and rewrites `monster_organized/` +
`index.csv` deterministically.

## Port Order

1. Read in all confirmed numeric species as a dictionary of
   `{species_code: {slot: [frame_path, ...]}}`.
2. Build a `shared actor family` resolver for `/monster`, `/zap`, `/ice`.
3. Create a `MonsterSpawnTemplate` model mirroring `jo`.
4. Create a `MonsterBattleTemplate` model mirroring the parts of `lh` / `lv`
   the remake needs as local authority.
5. Link spawn template -> battle template through an explicit ID.
6. Keep map placement as a separate data set, not embedded into the monster itself.
7. Promote candidate numeric ranges one bucket at a time, starting with
   `range_101xxx_partial_species`.
8. Only then decide whether end-98 and pattern-X0 ranges belong in the
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

The next coding step should no longer be only an asset loader.

It should be a 3-part monster foundation:

1. `MonsterAssetCatalog`
   - reads `client/assets/monster/index.csv`
   - builds `species_code -> slot -> [frame_path]`
   - exposes shared visual-family lookup

2. `MonsterSpawnCatalog`
   - stores Java-like fields equivalent to `jo`
   - exposes `GetSpawnTemplate(monsterKey)`
   - stays lightweight and map-facing

3. `MonsterBattleCatalog`
   - stores authoritative HP/MP/stats/skills for the remake
   - exposes `GetBattleTemplate(battleTemplateId)`
   - feeds battle scene and AI

The minimal end-to-end contract should be:

```ts
Map encounter -> monsterKey
monsterKey -> MonsterSpawnTemplate
MonsterSpawnTemplate.battleTemplateId -> MonsterBattleTemplate
MonsterSpawnTemplate.visualTypeByte -> shared sheet / icon family
optional speciesCode -> numeric offline art family
```

That is the cleanest way to stay very close to Java while still working
without the original Java server.
