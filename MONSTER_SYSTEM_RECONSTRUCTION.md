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

## Current Project Catalog Direction

Phần triển khai hiện tại trong repo đã chốt hướng authority như sau:

- `client` không còn là nơi quyết định roster monster cho map
- `server` là nơi giữ `monster catalog + map roster`
- `client map scene` chỉ render runtime từ roster server trả về

### Current Server Files

- [MonsterCatalogSeed.cs](/e:/Twelve/server/Twelve.Application/Monsters/MonsterCatalogSeed.cs)
- [InMemoryMonsterAssetCatalog.cs](/e:/Twelve/server/Twelve.Application/Monsters/InMemoryMonsterAssetCatalog.cs)
- [InMemoryMonsterSpawnCatalog.cs](/e:/Twelve/server/Twelve.Application/Monsters/InMemoryMonsterSpawnCatalog.cs)
- [InMemoryMonsterBattleCatalog.cs](/e:/Twelve/server/Twelve.Application/Monsters/InMemoryMonsterBattleCatalog.cs)
- [InMemoryMapMonsterRosterService.cs](/e:/Twelve/server/Twelve.Application/Monsters/InMemoryMapMonsterRosterService.cs)
- [Program.cs](/e:/Twelve/server/Twelve.Server/Program.cs) — exposes `/map/monster-roster`

### Shape We Are Preserving

The remake now separates 4 authoring tables even before DB exists:

1. `asset catalog`
2. `spawn template catalog`
3. `battle template catalog`
4. `map room roster`

This matches the Java runtime split much better than a single flat
`monster-by-map` table.

### Important Rule

If a new monster is added, do **not** hard-code it into a map screen.

Instead:

1. add / reuse an asset catalog entry
2. add a spawn template
3. add a battle template
4. add map roster rows for the target `mapId + roomId`

### Planned DB Migration

Current repo state is still `in-memory seed catalog`, not database authority.

This is intentional.

The next migration should move the **same shapes** into DB tables, not invent a
new contract. In other words:

- `MonsterCatalogSeed.cs` is a temporary seed source
- later it should become repository / DB-backed loaders
- HTTP contract and client roster consumption should remain stable

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

The biggest remaining placeholders are no longer the basic monster roster
bridge itself; they are the deeper runtime authority layers.

Still transitional / not final:

- board match and cascade truth is still partially hybrid client/server
- monster AI pressure is reconstruction logic, not recovered Java server truth
- map placement on remake maps still uses server-authored spawn zones/profiles
  rather than original Java server cells

Current repo direction is already much closer to the recovered Java shape than
the original placeholder actor-scaffold phase.

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

Status legend used below:

- `[Done]` implemented and already active in the repo
- `[In Progress]` direction is active, but not yet fully authoritative
- `[Pending]` still a real next step
- `[Inference]` reconstruction rule, not something proven from Java server code

### [Done] Phase 1. Replace placeholder map monster sending

Replace the monster-related part of `MapHandler` with:

- map layout from map store
- separate monster roster from `MapMonsterRosterService`
- stable `monsterKey`

Current outcome:

- monster map flow now uses lightweight roster packets with stable `monsterKey`
- roster semantics now preserve `group/count` style map authority better than
  the original placeholder actor scaffold

### [Done] Phase 2. Add server monster catalogs

Introduce:

- `MonsterAssetCatalog`
- `MonsterSpawnCatalog`
- `MonsterBattleCatalog`
- `MapMonsterRosterService`

Current outcome:

- the repo now has `MonsterAssetCatalog`, `MonsterSpawnCatalog`,
  `MonsterBattleCatalog`, `MapMonsterRosterService`, and
  `MonsterBattleBootstrapService`
- the catalog source is still in-memory seed data by design, pending future DB
  storage

### [In Progress] Phase 3. Split battle authority from packet reconstruction

Refactor current battle packet logic into:

- `BattleTurnEngine`
- `BattlePacketAssembler`

The current `BattleSkillPacketFactory` can remain the packet half.
The current `BattleSkillCastPacketService` should shrink until it becomes an orchestrator
or disappear into the new engine.

Current note:

- `BattleTurnEngine` now exists and already owns part of cast/turn logic
- packet assembly is much closer to adapter-only than before
- full board/match/cascade authority is still not fully server-owned yet

### [In Progress] Phase 4. Replace debug-only skill level sourcing

Remove gameplay dependence on `DebugSkillLevel`.

Skill level must come from:

- player battle state
- monster battle template
- or server-side character progression

Current note:

- monster skill level already resolves from monster battle template/rule logic
- player-side progression is still transitional

### [In Progress] Phase 5. Add content and AI

Only after the authority split is clean should the project add:

- per-map monster rosters
- monster stat content
- monster skill loadouts
- AI profiles
- reward tables

Current note:

- monster stat content, skill loadouts, and AI profiles are now present through
  a reconstruction rule engine
- this area is active but still not final because the content truth is
  server-authored reconstruction rather than recovered Java server data

### Implementation Snapshot in the Current Repo

The repo now has a substantial end-to-end monster slice implemented.

Important framing:

- the project does **not** have the original Java server
- the current target is therefore not `canon data parity`
- the target is `server-authored reconstruction that preserves Java client
  boundaries, flow, and runtime feel`

Completed infrastructure:

- `MonsterAssetCatalog`
- `MonsterSpawnCatalog`
- `MonsterBattleCatalog`
- `MapMonsterRosterService`
- `MonsterBattleBootstrapService`
- `MonsterBattleRuleFactory`

Completed flow:

1. Hoa Lư map monsters now resolve through stable `monsterKey` values.
2. Map monster roster is now transmitted as a lightweight socket packet with
   Java-like `group/count` semantics instead of only a direct actor scaffold.
3. Client encounter preview / battle entry resolve through `monsterKey`, and
   the server resolves:
   `monsterKey -> MapMonsterEncounter -> MonsterSpawnTemplate -> MonsterBattleTemplate`
4. Encounter preview renders monster name / display level / IQ from bootstrap
   data instead of client-side monster tables.
5. Battle screen uses bootstrap/session enemy state for HP / MP / Power instead
   of a client-only fallback table.
6. Monster map runtime keeps lightweight roster authority on the server while
   the client expands local live actors from roster/group data.
7. Current monster battle templates are no longer hand-tuned one by one; they
   are derived from a rule engine using:
   `element + level + combat role + threat tier + skill tier`

Current scaffold content in code:

- `HOA_LU_FIRE_001`
- `HOA_LU_ICE_001`
- `HOA_LU_ZAP_001`

Important constraint:

- these Hoa Lư monsters are still reconstruction scaffolds aligned with the
  repo's current `fire / ice / zap` visual families
- they are not claimed as legacy-canon Java content
- their stats / skill loadouts are `server-authored reconstruction`, not
  recovered Java server truth

Current practical fidelity estimate:

- if judged against the correct remake target
  `reconstructed logic that stays Java-faithful in system behavior`
- the current monster system is approximately `95-96%` complete/faithful for
  the client-visible monster slice, and about `95%` if the deeper authority
  layers are counted as part of the same feature
- strongest areas:
  - map -> encounter -> battle flow
  - monster identity / key / preview / bootstrap contracts
  - roster/group/count split between map and battle
  - stat progression and skill assignment rule engine
  - map runtime behavior now stays closer to Java feel:
    patrol-only roaming, encounter by direct player attack or collision, and
    a lighter map preview / HUD presentation
- still not final:
  - board/match authority is still hybrid client/server
  - AI pressure is reconstruction, not canon server behavior
  - map placement uses remake spawn zones rather than original Java server cells

What still remains after this implementation slice:

- continue moving board/match resolution toward stronger server authority
- make monster AI depend more deeply on authoritative combat stats and battle state
- extend the rule system from current tiers into:
  `species profile + zone scaling + rare override`
- keep current rule logic ready for a future DB, where the database stores data
  inputs while the rule engine remains the gameplay authority

### Current Development Note

The current preferred direction is:

- keep pushing the parts that are directly traceable from the Java client first
- only infer new server authority where the remake genuinely needs a source of truth

Practical reading:

- `client-visible fidelity` has priority over inventing deeper server gameplay
- map monster feel, encounter flow, battle presentation timing, HUD behavior,
  and preview behavior should be tightened first because they have stronger
  evidence from the old Java client
- new server logic should be added conservatively, mainly for:
  - battle authority
  - AI
  - rewards
  - board/cascade truth

This means the preferred near-term order is:

1. polish `map patrol / contact / encounter feel`
2. polish `battle presentation timing` per skill family / impact family
3. polish `encounter confirm / cancel / restore map state`
4. only then continue deeper server-side inference where authority is required

Non-goal for now:

- do not invent large new gameplay systems on the server just to "fill in" what
  is missing if that behavior is not supported by client-visible Java evidence

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

## Deep Java Trace Addendum — Monster Runtime Details

Phần này bổ sung các chi tiết đọc trực tiếp từ Java decompile trong lượt rà soát
`jo.java`, `ki.java`, `kj.java`, `om.java`, `ha.java`, và `ky.java`.

### `kj.java` — Monster Roaming Algorithm

`kj` là updater rất nhỏ nhưng là nơi thể hiện rõ nhất "cảm giác" monster trên
map Java cũ. Logic quan trọng nằm trong
[kj.java:6](/d:/Twelve/reference/redecoded/decompiled/kj.java:6).

Một frame update cho monster làm các bước sau:

1. gọi `ki.i()` để tick animation/state hiện tại
2. nếu monster đang ở state `0`, xử lý roaming/contact
3. nếu được bật aggro (`bl2`) và player đang ở state đi ngang (`kl.j == 1 || kl.j == 0`),
   kiểm tra hitbox monster `ki.e` với player rect `kl.t`
4. nếu chạm player và player còn `m()`, monster chuyển sang state `1`
5. nếu không chạm player, monster tự trôi theo hướng hiện tại
6. kiểm tra collision tile để đổi hướng
7. kiểm tra viewport/boundary helper `kh.a(ki)` để đổi hướng khi ra khỏi vùng hợp lệ

Pseudo-code faithful:

```java
ki.i();

if (ki.a() == 0) {
    if (player != null && enableAggro && ki.m()
        && (player.j == 1 || player.j == 0)
        && player.t.a(ki.e)
        && player.m()) {
        int facing = ki.e.a > player.t.a ? 2 : 3;
        ki.a(1, facing);
        player.b(facing == 2 ? 8 : 4);
        return;
    }

    ki.b(ki.d * ki.a[ki.c], ki.d * ki.b[ki.c]);

    int row = (ki.o() + ki.q() - 5) / 32;
    int col = (ki.n() + (ki.c == 2 ? 0 : ki.e.c)) / 32;

    if (logic[row][col] != false) {
        ki.a(0, ki.c == 2 ? 3 : 2);
    }
}

if (viewport.a(ki)) {
    int next = ki.c == 2 ? 3 : ki.c == 3 ? 2 : ki.c == 0 ? 1 : 0;
    ki.a(0, next);
}
```

Important reconstruction notes:

- `ki.d` là tốc độ bước ngẫu nhiên được set khi spawn trong `om` (`cv.a(2, 4)`).
- `ki.c` là hướng/facing hiện tại.
- `ki.a[]` và `ki.b[]` là vector di chuyển theo hướng.
- state `0` là roaming bình thường.
- state `1` là contact/aggressive/engagement-ready.
- hướng `2 <-> 3` dùng nhiều cho trái/phải khi chạm tile.
- nếu boundary helper chặn movement thì flip theo bảng:
  - `2 -> 3`
  - `3 -> 2`
  - `0 -> 1`
  - còn lại `-> 0`

Comment nguồn gốc khi port logic:

```csharp
// Reconstructed from Java client kj.java:6-50.
// Monster roaming is local playback: tick animation, move by facing vector,
// flip on tile/boundary collision, and enter state 1 on player contact.
```

### `om.java` — Spawn Construction Details

`om.a(int x, int y, jo data, Image sharedImage)` là factory tạo `ki` map monster.
Nguồn: [om.java:485](/d:/Twelve/reference/redecoded/decompiled/om.java:485).

Sheet selection:

| Java rule | Sheet field | Legacy resource | Anchor offset |
|-----------|-------------|-----------------|---------------|
| `jo.c >> 1 == 0` | `this.r` | `/monster` | `14` |
| `jo.c >> 1 == 1` | `this.s` | `/zap` | `5` |
| otherwise | `this.t` | `/ice` | `13` |

Construction sequence:

```java
switch (jo.c >> 1) {
    case 0: image = this.r; n4 = 14; break;
    case 1: image = this.s; n4 = 5; break;
    default: image = this.t; n4 = 13;
}

ki monster = new ki(image, 1, 6, jo, go.k, sharedImage);
monster.c(n4);
monster.a(monster.n(), monster.o(), monster.p() - 12, 20);
monster.d = cv.a(2, 4);
monster.c(x - (monster.p() - 32), y - (monster.q() - 32));
monster.a(0, cv.a(2, 3));
this.H.b.a(monster);
```

Reconstruction implications:

- map monster visuals are resolved from the raw type byte, not from battle stats
- the Java constructor arguments are misleading (`1, 6`) because `ki` itself
  hard-codes 6 frames and 1 row
- every live monster starts in state `0`
- initial facing is random in `[2, 3]`
- local roam speed is random in `[2, 4]`
- collision rect/name anchor is adjusted immediately after construction
- render bucket insertion happens inside the factory, not only after spawn loop

### `om.java` — Remove Contract

`om.a(jo[])` removes by stable key only.
Nguồn: [om.java:456](/d:/Twelve/reference/redecoded/decompiled/om.java:456).

Removal sequence:

1. loop input `jo[]`
2. find live `ki` in `this.I` where `ki.f.a.equals(jo.a)`
3. remove from `this.I`
4. find matching `ki` in render bucket `this.H.b`
5. remove first matching render actor

This proves removal is not coordinate-based, not name-based, and not sprite-based.
The only safe key is `jo.a`.

### `om.java` — Encounter Trigger Details

There are two visible trigger paths in `om.n()`.

#### Player-driven collision

Nguồn: [om.java:660](/d:/Twelve/reference/redecoded/decompiled/om.java:660).

Condition:

- player `l.m()` is true
- target actor `ki.m()` is true
- player small collision box `l.u` overlaps monster rect `ki.e`

Effect:

```java
this.a(monster, true);
this.U = monsterIndex;

if (monster.e.a < player.t.a) {
    monster.b(3);
    player.b(4);
} else {
    monster.b(2);
    player.b(8);
}
```

`bl2 == true` is sent into battle request and preview constructor, so this flag
must be preserved as an initiative/context flag.

#### Monster-driven aggressive contact

Nguồn: [om.java:690](/d:/Twelve/reference/redecoded/decompiled/om.java:690).

Every visible monster except the currently selected one is updated by `kj`.
If after update `ki.a() == 1` and dialog `191919` is not already open, `om`
starts encounter with:

```java
this.U = monsterIndex;
this.a(monster, false);
this.am.c(null);
```

This makes monster engagement a modal state guarded by dialog id `191919`.

### `om.java` — Battle Handoff Details

Nguồn: [om.java:797](/d:/Twelve/reference/redecoded/decompiled/om.java:797).

The handoff does more than send a socket request:

```java
this.y();
if (!bl2) {
    ag.a().b(10);
}
ag.b().l();
this.N = true;
this.Y = 10;
this.r();
this.e(false);

E = new cu(ki.e.a + ki.e.c / 2, ki.e.b + ki.e.d);
this.q = new ha(this.l.e.u(), ki.b(), false, bl2, 99030, this.X);
this.q.a(this.am);

ks.a().a(ki.f.a, bl2);
```

Important reconstruction points:

- map is frozen with `N = true`
- `Y = 10` creates a short transition/countdown window
- player state is cached through `r()`
- encounter anchor `E` is bottom-center of monster collision rect
- preview receives `ki.b()` clone, not original live actor
- socket request sends `ki.f.a` (`jo.a`) and initiative/context flag `bl2`
- `jo.d` and `jo.e` are read but ignored at send site; they are preview-only here

### `ha.java` — Encounter Preview Contract

`ha(at player, at monster, ...)` is the map-backed constructor.
Nguồn: [ha.java:47](/d:/Twelve/reference/redecoded/decompiled/ha.java:47).

When `this.y == false`, the right/left actor is a `ki` map monster and the
constructor reads:

| Java read | Meaning |
|-----------|---------|
| `((ki)at3).f.c` | raw visual/icon type byte (`jo.c`) |
| `((ki)at3).f.e` | IQ numeric value (`jo.e`) |
| `((ki)at3).f.b` | display name (`jo.b`) |

Exact IQ bucket logic:

```java
this.G = this.N < 3 ? "Siêu gà"
       : (this.N < 7 ? "Bờm"
       : (this.N < 10 ? "Ma lanh"
       : (this.N == 11 ? "Tốc chiến" : "Tuyệt đỉnh")));
```

Render-time fields for map monster preview:

```java
this.a(graphics, panel, this.q, ki.f.d, this.I, side, "IQ: " + this.G);
```

So the displayed fields are:

- icon/element marker = `jo.c`
- level = `jo.d`
- name = `jo.b`
- secondary line = `"IQ: " + bucket(jo.e)`

Special tint:

```java
int bg = v.aj;
if (this.N == 11) {
    bg = 0xFDBDBD;
}
```

`jo.e == 11` therefore visually marks `"Tốc chiến"` encounters with a distinct
pink/red dialog background.

Dialog/callback constants:

| Id | Source | Meaning |
|----|--------|---------|
| `191919` | `ha.b(191919)` | modal encounter/combat preview dialog id |
| `99030` | `this.M = 99030` | callback id sent when preview completes |

Port rule:

```csharp
// Reconstructed from Java client ha.java:82-98 and ha.java:221-260.
// Encounter preview uses lightweight jo fields only. Do not show battle HP/MP/skills here.
```

### `ky.java` — Authoritative Battle Fighter Packet Tags

The parser `ky.a(ku)` reads a full `lh` fighter payload.
Nguồn: [ky.java:1003](/d:/Twelve/reference/redecoded/decompiled/ky.java:1003).

Observed tag map:

| `lh` field | Packet tag | Java line | Meaning |
|------------|------------|-----------|---------|
| constructor byte / `lh.a` | `15` nested/default | `1008-1009` | fighter type/id inherited from `ld` |
| `lh.b` | `9` | `1010` | battle display name |
| `lh.c` | `26` | `1011` | extra display/title string |
| `lh.g` | `15` | `1012` | battle element/type byte |
| `lh.f` | `16` | `1013` | fighter style/side byte |
| `lh.G` | `27` | `1014` | level |
| `lh.s` | `17` | `1015` | current HP |
| `lh.r` | `47` | `1016` | max HP |
| `lh.u` | `18` | `1017` | current MP |
| `lh.t` | `48` | `1018` | max MP |
| `lh.h` | `118` | `1019` | strength-like base stat |
| `lh.j` | `119` | `1020` | agility-like base stat |
| `lh.i` | `120` | `1021` | magic-like base stat |
| `lh.k` | `121` | `1022` | vitality-like base stat |
| `lh.l` | `196` | `1023` | additive stat bonus 1 |
| `lh.m` | `197` | `1024` | additive stat bonus 2 |
| `lh.n` | `198` | `1025` | additive stat bonus 3 |
| `lh.o` | `199` | `1026` | additive stat bonus 4 |
| `lh.p` | `116` | `1027` | addHealth |
| `lh.q` | `115` | `1028` | health-percent-like field |
| `lh.J` | `42` | `1030` | extra battle numeric |
| `lh.H` | `43` | `1031` | extra battle numeric |
| `lh.I` | `99` | `1032` | extra battle numeric, default `10000` |
| `lh.K` | `53` | `1033` | extra battle numeric |
| `lh.L` | `76` | `1034` | extra battle numeric |
| `lh.M` | `73` | `1035` | extra battle numeric |
| `lh.N` | `74` | `1036` | extra battle numeric |
| `lh.S` | `209` | `1037` | extra string |
| `lh.R` | `210` | `1038` | extra string |
| `lh.ab` | `160` | `1042` | extra battle numeric |
| `lh.Z` | `165` | `1043` | boolean flag |
| `lh.aa` | `166` | `1044` | boolean flag |
| `lh.E` | repeated `64` | `1045-1060` | skill entries |
| `lh.D` | repeated `83` | `1061-1069` | equipped item/visual entries |
| `lh.F` | parser `k(ku)` | `1070` | carried item entries |
| `lh.U/V/W` | repeated `90` + slot `91` | `1071-1100` | appearance layer descriptors |

`lh.Q` title fallback:

```java
lh.Q = lh.G > 100 && lh.G <= 200 ? "Đại Hiệp"
     : (lh.G > 200 ? "Chiến Vương" : "Hào Kiệt");
```

This appears to be generic fighter-rank text, not monster-specific species data.

### `ky.java` — `lv` Skill Entry Tags

Inside the repeated tag `64` block:

```java
lh.E[n] = new lv(ku.a(blockStart, -1));         // skill id
lh.E[n].f = ku.a((short)67, blockStart, next, -1); // skill level
lh.E[n].e = ku.a((short)68, blockStart, next, -1); // mana cost
ku.a((short)89, blockStart, next, (byte)-1);       // extra ignored byte
```

Reconstruction rule:

- monster skill id, level, and mana cost belong to `lh.E`
- they are **not** available in the map `jo` packet
- the replacement server must resolve them at battle bootstrap time

### `ky.java` — Appearance Layer Tags

Inside repeated tag `90`:

```java
int baseId = ku.a(blockStart, 0);
int slot = ku.a((short)91, blockStart, next, (byte)0);

df layer = new df(baseId);

int sourcePaletteId = ku.a((short)93, blockStart, next, 0);
byte[] sourcePaletteBytes = ku.c((short)95, blockStart, next);
layer.d = new dg(sourcePaletteId, sourcePaletteBytes);
layer.f = new dg[]{layer.d};

int destinationPaletteId = ku.a((short)96, blockStart, next, 0);
byte[] destinationPaletteBytes = ku.c((short)98, blockStart, next);
layer.e = new dg(destinationPaletteId, destinationPaletteBytes);

switch (slot) {
    case 0: lh.U = layer; break;
    case 1: lh.V = layer; break;
    case 2: lh.W = layer; break;
}
```

This proves battle monster appearance is packet-driven body composition with
palette remapping. It must not be collapsed into the map sprite sheet system.

### Reconstruction Summary From This Trace

The deep trace reinforces the same hard boundary:

```text
jo packet
  -> lightweight map encounter record
  -> local ki actor
  -> ha preview using jo fields
  -> ks sends jo.a
  -> server returns lh/lv/df battle truth
  -> battle actor assembled from lh appearance and runtime stats
```

Do not let any of these layers leak into each other:

- `/offline/<id>.png` is asset evidence, not stat evidence
- `jo.c` is preview/icon/sheet evidence, not battle element authority
- `jo.d` is display level for preview, not necessarily the final `lh.G`
- `jo.e` is IQ label value for preview/contact feel, not full AI behavior
- `lh.E` is battle skill authority
- `lh.U/V/W` are battle body appearance authority
- `nq/nl` turn result packets are runtime delta authority

## Battle Monster Java Classes — Deep Field Mapping

Phần này bổ sung lớp **battle monster** sau khi rà soát sâu các Java class:
`lh.java`, `lg.java`, `lv.java`, `nl.java`, `nq.java`, `df.java`,
`ni.java`, và `mb.java`.

Mục tiêu của section này là chốt rõ:

- field nào trong battle payload là stat thật
- field nào là runtime value
- field nào là skill / turn delta / appearance
- battle monster khác map monster ở đâu
- khi port sang .NET 9 phải giữ boundary nào

### `lh.java` — Authoritative Fighter Payload

`lh` là object gần nhất với "battle monster truth" mà Java client còn giữ.
Nó kế thừa từ `ld`, nên các field base như `a`, `b`, `c`, `d` đến từ class
cha, còn phần stat/skill/appearance nằm trong `lh`.

Evidence quan trọng nhất là `lh.toString()` tại
[lh.java:135](/d:/Twelve/reference/redecoded/decompiled/lh.java:135).

#### Confirmed Combat Stat Fields from `toString()`

| Field | Meaning | Java evidence |
|-------|---------|---------------|
| `lh.h` | Strength | `"Streng = " + this.h` |
| `lh.k` | Vitality | `"vitalit = " + this.k` |
| `lh.j` | Agility | `"agility = " + this.j` |
| `lh.i` | Magic | `"magic = " + this.i` |
| `lh.l` | additive Strength | `"addStreng = " + this.l` |
| `lh.o` | additive Vitality | `"addvitalit = " + this.o` |
| `lh.m` | additive Agility | `"addagility = " + this.m` |
| `lh.n` | additive Magic | intended by field group; decompile print line appears typoed |
| `lh.s / lh.r` | current HP / max HP | `"HP: " + this.s + " / " + this.r` |
| `lh.u / lh.t` | current MP / max MP | `"Mana: " + this.u + " / " + this.t` |
| `lh.w / lh.v` | current Power / max Power | `"Power: " + this.w + " / " + this.v` |
| `lh.x / lh.y` | min damage / max damage | `"MinDam: " + this.x + " / " + this.y` |
| `lh.A` | dodge rate | `"Dodgerate: " + this.A` |
| `lh.B` | hit rate | `"hitrate: " + this.B` |
| `lh.z` | defense | `"Defende: " + this.z` |
| `lh.C` | critical damage | `"criticaldamge " + this.C` |
| `lh.g` | element / hệ | `"hệ: " + this.g` |

Important correction:

- earlier reconstruction guessed several names correctly from usage
- `lh.toString()` now confirms the stat names directly
- `lh.h/j/i/k` order should be documented as:
  - `h = Strength`
  - `j = Agility`
  - `i = Magic`
  - `k = Vitality`

Port comment:

```csharp
// Reconstructed from Java client lh.java:135-152.
// lh is the battle fighter payload. It carries authoritative combat stats:
// STR=h, AGI=j, MAG=i, VIT=k, HP=s/r, MP=u/t, Power=w/v,
// damage=x/y, defense=z, dodge=A, hit=B, critDamage=C, element=g.
```

#### `lh.a()` Clone Behavior

`lh.a()` creates a deep-ish clone used by battle runtime.

Important copied groups:

- identity/display: `a`, `b`, `c`, `d`
- battle state: `f`, `g`, `G`
- resources: `s/r`, `u/t`, `w/v`
- combat stats: `h/i/j/k`, `x/y`, `z`, `A/B/C`
- equipment: `D[]` cloned via `ll.d()`
- items: `F[]` cloned via `lm.b()`
- skills: `E[]` cloned into new `lv`
- appearance: `U/V/W` cloned through `df.a()`
- flags: `O`, `Z`

Reconstruction implication:

- catalog templates should be immutable
- battle session instances should be cloned from templates
- runtime HP/MP/Power/status changes must not mutate the template object

### `lh.D`, `lh.E`, `lh.F`, `lh.U/V/W`

`lh` carries several arrays that matter for monsters in battle.

| Field | Type | Meaning |
|-------|------|---------|
| `lh.D` | `ll[]` | equipment / equipped visual-stat entries |
| `lh.E` | `lv[]` | battle skill list |
| `lh.F` | `lm[]` | carried item entries |
| `lh.U` | `df` | appearance layer slot 0 |
| `lh.V` | `df` | appearance layer slot 1 |
| `lh.W` | `df` | appearance layer slot 2 |
| `lh.ac` | `lt[]` | extra timed/long-value entries parsed from packet |

The old client does not treat battle monster visuals as a single sprite.
It uses the `lh` payload to build a composited actor.

### `lv.java` — Skill Entry

`lv` extends `ld`, so skill id/name/description are inherited from base class.

Evidence: [lv.java:15](/d:/Twelve/reference/redecoded/decompiled/lv.java:15).

| Field | Meaning | Java evidence |
|-------|---------|---------------|
| `lv.a` | skill id, inherited from `ld` | `"Skill " + ((ld)object).a` |
| `lv.b` | skill name, inherited from `ld` | `": " + this.b` |
| `lv.d` | skill description, inherited from `ld` | printed on next line |
| `lv.f` | skill level | `"Level: " + this.f` |
| `lv.e` | mana cost | `"Mana: " + this.e` |
| `lv.g` | max level | `"Max Level: " + this.g` |
| `lv.h` | extra strings / description lines | iterated if non-null |

Packet parsing in `ky.java` confirms battle skills are embedded inside `lh.E`:

- repeated tag `64` = skill entry
- base/default int = skill id
- tag `67` = skill level
- tag `68` = mana cost
- tag `89` = extra ignored byte in this decode path

Reconstruction rule:

```csharp
// Reconstructed from Java client ky.java skill block and lv.java:15-29.
// Monster skills are battle payload data (lh.E), not map spawn data (jo).
```

### `lg.java` — Runtime Battle Wrapper

`lg` wraps `lh` and exposes mutable live battle values.

Evidence: [lg.java](/d:/Twelve/reference/redecoded/decompiled/lg.java).

#### Resource Getters / Setters

| Method | Meaning |
|--------|---------|
| `a()` | returns wrapped `lh` |
| `b()` | returns `lh.O` |
| `c()` | returns level `lh.G` |
| `d()` | returns `lh.T` |
| `j()` | returns display name `lh.b` |
| `l()` | max HP `lh.r` |
| `m()` | current HP `lh.s` |
| `g(int)` | set current HP `lh.s` |
| `n()` | current MP `lh.u` |
| `o()` | max MP `lh.t` |
| `h(int)` | set current MP `lh.u` |
| `q()` | current Power `lh.w` |
| `r()` | max Power `lh.v` |
| `j(int)` | set current Power `lh.w` |
| `p()` | Power full check `lh.w >= lh.v` |
| `i(skillId)` | lookup `lv` by skill id in `lh.E` |

#### Status Timer Slots

`lg` has 5 countdown-style integer timers.

| Timer field | Check method | Setter | Tick behavior |
|-------------|--------------|--------|---------------|
| `lg.d` | `e()` | `a(int)` | decremented in `s()` |
| `lg.e` | `g()` | `c(int)` | decremented in `s()` |
| `lg.f` | `h()` | `d(int)` | decremented in `s()` |
| `lg.g` | `f()` | `b(int)` | decremented in `s()` |
| `lg.h` | `i()` | `e(int)` | decremented in `s()` |

The exact human-readable status names are not recovered from client alone.
But the structure is clear:

- battle fighter can have multiple active timers
- each turn/frame tick decrements them
- monsters must not be modeled as stateless HP bags

Recommended remake model:

```csharp
public sealed record MonsterRuntimeStatus(
    string StatusId,
    int RemainingTicks
);
```

Do not hard-code only one status slot; Java had at least five timer slots.

### `nl.java` — Per-Fighter Attribute Delta

`nl` is the object applied after a battle turn/skill to update fighter runtime
attributes.

Evidence: [nl.java:21](/d:/Twelve/reference/redecoded/decompiled/nl.java:21).

Constructor:

```java
public nl(String string, int n2, int n3, int n4, int n5, int n6) {
    this.a = string;
    this.e = n2;
    this.b = n3;
    this.c = n4;
    this.d = n5;
    this.f = n6;
}
```

`toString()` confirms names:

| Field | Meaning | Java evidence |
|-------|---------|---------------|
| `nl.a` | fighter name / owner key in Java client | `"PlayerAttribute " + this.a` |
| `nl.e` | damage value | `"dam  " + this.e` |
| `nl.f` | life / extra value | `"life" + this.f` |
| `nl.b` | resulting HP | `"hp = " + this.b` |
| `nl.c` | resulting MP | `"mana = " + this.c` |
| `nl.d` | resulting Power | `"power = " + this.d` |

Constructor order should be read as:

```text
nl(name, damage, hp, mana, power, lifeOrExtra)
```

Important Java limitation:

- the old client matches deltas by fighter name string
- the remake should use stable combatant ids internally
- display names should not be the authority key

Recommended C# shape:

```csharp
public sealed record BattleActorDeltaResult(
    string CombatantId,
    string DisplayName,
    int? Damage,
    int? CurrentHp,
    int? CurrentMp,
    int? CurrentPower,
    int? LifeOrExtra
);
```

### `nq.java` — Turn Result Model

`nq` is a broad turn/result model used by battle flow.

Evidence: [nq.java](/d:/Twelve/reference/redecoded/decompiled/nq.java).

`toString()`:

```java
return "TurnModel[type=" + this.c + ",rev=" + this.b + "]";
```

Confirmed fields:

| Field | Type | Likely meaning |
|-------|------|----------------|
| `nq.a` | `String` | owner/caster/display name |
| `nq.b` | `int` | revision / sequence |
| `nq.c` | `byte` | turn type |
| `nq.d` | `boolean` | flag |
| `nq.e` | `int` | extra int |
| `nq.f` | `nl[]` | actor attribute deltas |
| `nq.g` | `byte[][]` | board / visual byte matrix |
| `nq.h` | `byte[]` | extra byte payload |
| `nq.i` | `long` | timing / id value |
| `nq.j/k/l/m` | `int` | coordinate / index style values |
| `nq.o/p/q/s` | `byte[]` | additional byte arrays |
| `nq.y/z` | `int[]` | coordinate / index arrays |
| `nq.A` | `ll[]` | equipment result entries |
| `nq.B` | `lm[]` | item result entries |
| `nq.D/F` | `byte` | extra type flags |
| `nq.E` | `boolean` | extra flag |

Reconstruction rule:

- `nq` is not a monster template
- it is a per-action/per-turn result packet model
- monster battle runtime changes should flow through `nq.f -> nl[]`

### `df.java` — Appearance Layer Descriptor

`df` describes one appearance layer and its palette mapping.

Evidence: [df.java](/d:/Twelve/reference/redecoded/decompiled/df.java).

| Field | Type | Meaning |
|-------|------|---------|
| `df.a` | `int` | base numeric asset id |
| `df.b` | `String` | display/name string |
| `df.c` | `int` | extra numeric value |
| `df.d` | `dg` | source palette |
| `df.e` | `dg` | destination palette |
| `df.f` | `dg[]` | palette variants |

Clone method `df.a()` deep-clones palettes:

- copies `a`, `b`, `c`
- clones `d`
- clones `e`
- clones each entry in `f`

This proves battle appearance is not just "choose PNG".
It includes palette remap data.

### `mb.java` — Battle Body-Part Compositor

`mb.a(lh)` constructs body metadata bundles from the battle fighter payload.

Evidence: [mb.java:689](/d:/Twelve/reference/redecoded/decompiled/mb.java:689).

#### Metadata Bundle Construction

`mb.a(lh)` creates 4 `mb` metadata entries:

| Index | Java source | Meaning |
|-------|-------------|---------|
| `mbArray[0]` | equipment slot 0 or `lh.U.a + 99` | main body/head layer |
| `mbArray[1]` | `lh.V.a + 99` | secondary body layer |
| `mbArray[2]` | equipment slot 1 or default `79899/79999` | weapon / hand layer |
| `mbArray[3]` | equipment slot 2 or default `89999` | accessory / extra layer |

Default weapon-like metadata id depends on `lh.f`:

```java
int n2 = lh2.f == 1 ? 79999 : 79899;
```

Equipment metadata source:

```java
if (equipment.e < 4) {
    nArray[equipment.e] = equipment.n - equipment.n % 10;
}
```

So equipment with `e < 4` can override the body-part metadata bundle.

#### Cache Key Includes Palettes

`mb.a(lh, mb2, mb3, mb4, mb5, int)` builds a cache key from:

- animation/state id
- layer metadata ids
- `lh.W.e.c`
- `lh.V.e.c`
- `lh.U.e.c`

Evidence: [mb.java:735](/d:/Twelve/reference/redecoded/decompiled/mb.java:735).

Implication:

- actor appearance cache must include palette destination signatures
- otherwise different palette variants can incorrectly reuse the same body cache

#### Equipment Visual Tier Helper in `lh.c()`

`lh.c()` computes a visual/equipment tier-like rank.

Evidence: [lh.java:196](/d:/Twelve/reference/redecoded/decompiled/lh.java:196).

Rules:

- count equipped entries where `ll.j >= 7`
- exclude slots `e == 8` and `e == 4`
- if at least 4 qualifying pieces:
  - any piece `< 9` -> rank `1`
  - any piece `< 11` -> rank `2`
  - any piece `< 13` -> rank `3`
  - if 4+ pieces `>= 13` -> rank `4`
- otherwise rank `0`

This is not monster-only, but battle monsters use the same fighter/appearance
system, so the remake should keep this generic at fighter level.

### `ni.java` — Battle Actor State Machine

`ni` is the battle actor runtime state machine for composited fighters.

Evidence: [ni.java](/d:/Twelve/reference/redecoded/decompiled/ni.java).

Constructor receives:

```java
ni(int side, mg idle, mg strike, mg moveA, mg moveB, mg hurt, mg down, mc cast, nr effect)
```

The decompiled field assignments are:

| Constructor arg | Field | Role |
|-----------------|-------|------|
| `mg2` | `this.a` | idle |
| `mg3` | `this.b` | strike/contact |
| `mg4` | `this.c` | move/evade bundle A |
| `mg5` | `this.e` | hurt |
| `mg6` | `this.d` | move/evade bundle B |
| `mg7` | `this.f` | down/defeat |
| `mc2` | `this.F` | cast/channel |
| `nr2` | `this.O` | attached effect/controller |

#### State Map from `a(int state)`

Evidence: [ni.java:158](/d:/Twelve/reference/redecoded/decompiled/ni.java:158).

| State | Selected bundle | Role |
|-------|-----------------|------|
| `0` | `this.a` | idle / standing |
| `1` | `this.b` | strike / contact hit |
| `2` | `this.c` or `this.d` | advance toward target |
| `3` | `this.e` | hurt reaction |
| `4` | `this.f` | down / defeat |
| `5` | `this.c` or `this.d` | retreat / return home |
| `6` | `this.F` | cast / channel |
| `7` | `this.c` or `this.d` | evade / miss / guard motion |
| `8` | `this.e` | alternate hurt alias |

`state 8` maps to the same bundle as `state 3`.

#### Vietnamese Battle Feedback Text

Evidence:

- [ni.java:119](/d:/Twelve/reference/redecoded/decompiled/ni.java:119)
- [ni.java:131](/d:/Twelve/reference/redecoded/decompiled/ni.java:131)

| Text | Meaning |
|------|---------|
| `"Xí Hụt"` | miss |
| `"Đỡ đòn"` | guard / block |

This text is client-visible and should be preserved if the remake wants Java-like
battle feedback.

#### Main State Transitions in `ni.i()`

Evidence: [ni.java:233](/d:/Twelve/reference/redecoded/decompiled/ni.java:233).

| Current state | Transition behavior |
|---------------|---------------------|
| `1` strike | near end of animation triggers target reaction, then switches to `5` return |
| `2` advance | moves to target position, then switches to `1` strike |
| `5` return | moves back to original position, then switches to `0` idle |
| `3` hurt | animation done, then switches to `0` idle |
| `6` cast | cast animation done, then switches to `0` idle |
| `7` evade/miss | moves out/back, then switches to `0` idle |

Monster implication:

- battle monster should not be animated by just toggling idle/hurt
- normal attack should be modeled as:
  - advance
  - strike
  - apply target reaction around strike frame
  - retreat
  - idle

Port comment:

```csharp
// Reconstructed from Java client ni.java.
// Battle actors use stateful motion bundles: idle(0), strike(1), advance(2),
// hurt(3), down(4), retreat(5), cast(6), evade/guard(7), hurtAlias(8).
```

### Battle Monster Layer Boundary — Final Confirmed Flow

After reading the battle classes, the full monster flow is now:

```text
Map packet ky.f()
  -> jo[] lightweight encounter records
  -> om expands each jo.f into local ki actors
  -> kj locally roams/contact-updates ki
  -> ha preview reads jo.b / jo.c / jo.d / jo.e
  -> ks sends jo.a + initiative/context flag
  -> server returns lh fighter payload
  -> lg wraps lh as mutable runtime fighter
  -> mb + df + equipment compose battle actor body
  -> ni animates battle actor states
  -> nq turn result carries nl[] actor deltas
  -> lg current HP/MP/Power are updated from nl
```

Strictly separate meanings:

| Layer | Java object | Contains | Must not contain |
|-------|-------------|----------|------------------|
| map spawn | `jo` | key, name, visual type, display level, IQ, count, tint | HP, MP, skills, damage |
| map actor | `ki` | local sprite, rect, facing, movement state | battle stats |
| preview | `ha` | display fields from `jo` | true battle payload |
| battle payload | `lh` | stats, resources, skills, equipment, appearance | map placement |
| skill entry | `lv` | skill id, level, mana cost | map spawn count |
| runtime wrapper | `lg` | mutable HP/MP/Power/status timers | catalog template mutation |
| actor delta | `nl` | damage and resulting resources | skill definitions |
| turn result | `nq` | board/turn/delta packet model | permanent monster catalog |
| appearance | `df`/`mb` | body metadata + palette remap | gameplay roster authority |
| animation | `ni` | visual state machine | damage formula authority |

### What Has Now Been Exhausted from Java Client

After this deeper pass, the client-side monster surface is effectively exhausted
for both map and battle.

Covered map layer:

- `jo` spawn data
- `ki` live actor / 6-frame map sheet
- `kj` roaming/contact logic
- `om` spawn/remove/engage/handoff
- `ha` encounter preview
- `ky.f()` monster packet decode
- `kq` / `oa` / `com.mg.sq.a` callback bridge
- `ks` battle request by monster key

Covered battle layer:

- `lh` fighter payload and confirmed combat stat names
- `lv` skill entries
- `lg` live runtime wrapper and status timers
- `nl` actor deltas
- `nq` turn result model
- `df` appearance layer descriptor
- `mb` body-part compositor and palette-aware cache
- `ni` battle actor state machine
- `mx` battle actor/HUD integration already referenced by packet/runtime flow

The remaining unknowns are no longer "where is monster logic in the Java
client?". They are server-authority gaps:

- exact original Java server monster roster per map
- exact original Java server damage formulas
- exact monster AI target choice
- exact drop/reward tables
- exact semantic names for some generic fighter flags

These cannot be recovered from the client alone without original server source
or packet captures.

## Server Monster Formula Reconstruction

Phần này là **server-authority reconstruction spec** cho monster trong remake.

Nguồn căn cứ:

- Java client đã xác nhận boundary và payload:
  - map encounter = `jo`
  - battle fighter = `lh` / `lv`
  - turn delta = `nq` / `nl`
- Không có Java server cũ, nên các công thức dưới đây là **remake server-owned logic**
  được thiết kế để:
  - bám đúng boundary Java client
  - dùng lại stat/element framework đã phục dựng cho player
  - cân bằng PvE/PvP
  - không để client quyết định kết quả battle
- Element / stat calculator tham chiếu:
  - [docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md](/d:/Twelve/docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md)

### Reconstruction Authority Level

| Mảng | Nguồn truth |
|------|-------------|
| `jo` map spawn shape | Java client evidence |
| `lh/lv` battle payload shape | Java client evidence |
| HP/MP/Power fields | Java client evidence |
| stat names STR/MAG/AGI/VIT | Java client evidence từ `lh.toString()` |
| monster stat scaling | remake server-owned reconstruction |
| monster skill AI | remake server-owned reconstruction + user gameplay direction |
| monster damage formula | remake server-owned, dùng player stat/element framework |
| monster board/resource rule | remake server-owned, nhưng phải áp chung player/monster |

Important rule:

```text
Client chỉ render/playback.
Server mới phải quyết định:
- monster stat
- monster skill chọn
- target chọn
- board action / gem result
- resource gain
- final damage
- actor deltas
```

### Monster Uses the Same 4 Core Stats as Player

Monster dùng chung stat framework với player:

| Vietnamese | Server name | Java `lh` field |
|------------|-------------|-----------------|
| Cường Lực | Strength | `lh.h` |
| Nội Lực | Magic | `lh.i` |
| Thân Pháp | Agility | `lh.j` |
| Thể Lực | Vitality | `lh.k` |

Port comment:

```csharp
// Monster server stat reconstruction.
// Java client confirms lh.h/i/j/k are STR/MAG/AGI/VIT-like fighter stats.
// Because Java server is missing, monster stat values are generated by the
// remake server from level + element + role + threat tier, then sent as lh-like
// battle payload fields.
```

### Monster Element / Main Build Mapping

Monster hệ nào thì dùng cùng calculator hướng đó.

| Monster element | Build archetype | Java/player calculator mirror | Gameplay role |
|-----------------|-----------------|-------------------------------|---------------|
| Hỏa / Cường Lực | Strength-like | `jq.java` | HP cao, damage vật lý ổn định |
| Lôi / Thân Pháp | Agility-like | `js.java` | hit/crit/dodge, tốc độ/áp lực |
| Thủy / Nội Lực | Magic-like | `jr.java` | MP/skill mạnh, burst/effect |
| Neutral | neutral override | server-owned | early map / filler / non-counter monster |

Rule:

- Monster không có "điểm tiềm năng tự cộng" như player.
- Monster battle template chứa stat đã sinh sẵn.
- Stat sinh từ:
  - `level`
  - `element`
  - `combatRole`
  - `threatTier`
  - optional species/profile override

### Monster Level Scaling

Monster level không bám động theo level player một cách trực tiếp.
Monster level thuộc map/zone roster.

Recommended model:

```csharp
public sealed record MonsterLevelProfile(
    int ZoneBaseLevel,
    int MinLevel,
    int MaxLevel,
    int LevelVariance,
    MonsterThreatTier ThreatTier
);
```

Rules:

| Monster type | Level rule |
|--------------|------------|
| Normal | quanh level map/zone |
| Elite | zone level + 2..5 |
| Boss | zone level + 5..12 hoặc fixed authored |
| Event/Rare | explicit authored override |
| Early training | có thể thấp hơn player để dễ tiếp cận |

Không nên auto-scale toàn bộ monster theo player vì:

- Java map spawn là server-driven roster, không phải client-generated scaling.
- Map train cần bản sắc level/difficulty riêng.
- Player mạnh hơn vẫn phải có cảm giác quay lại map cũ dễ hơn.
- Boss/elite mới cần authored override.

### Threat Tier

Threat tier là hệ số khó, không phải element.

```csharp
public enum MonsterThreatTier
{
    Normal = 0,
    Tough = 1,
    Elite = 2,
    Boss = 3
}
```

Recommended multipliers:

| Tier | HP | Damage | Defense | Hit/Dodge/Crit | MP |
|------|----|--------|---------|----------------|----|
| Normal | 100% | 100% | 100% | 100% | 100% |
| Tough | 125% | 110% | 110% | 105% | 110% |
| Elite | 160% | 125% | 125% | 110% | 125% |
| Boss | 240% | 145% | 150% | 115% | 150% |

Integer application:

```text
FinalValue = floor(BaseValue * TierPercent / 100)
```

Boss nên được cân bằng thêm bằng skill/AI/board behavior, không chỉ nhân số stat
quá lớn.

### Monster Combat Roles

Server monster battle catalog nên có role để sinh stat khác nhau.

```csharp
public enum MonsterCombatRole
{
    Balanced,
    Bruiser,
    Assassin,
    Caster,
    Tank
}
```

#### Role Stat Weight

Base stat budget lấy từ level, sau đó phân phối theo role.

Recommended base budget:

```text
BaseStat = 10
StatBudget = level * 5
```

Role allocation percent:

| Role | STR | MAG | AGI | VIT | Meaning |
|------|-----|-----|-----|-----|---------|
| Balanced | 25% | 25% | 25% | 25% | trung bình |
| Bruiser | 40% | 10% | 20% | 30% | sát thương vật lý + trâu |
| Assassin | 20% | 10% | 45% | 25% | hit/crit/dodge |
| Caster | 10% | 45% | 20% | 25% | MP/skill |
| Tank | 20% | 10% | 15% | 55% | HP/def cao |

Formula:

```text
Strength = 10 + floor(StatBudget * Role.StrPercent / 100)
Magic    = 10 + floor(StatBudget * Role.MagPercent / 100)
Agility  = 10 + floor(StatBudget * Role.AgiPercent / 100)
Vitality = 10 + floor(StatBudget * Role.VitPercent / 100)
```

Then apply element nudge.

### Element Stat Nudge

Element nudges keep each element recognizable even across role differences.

| Element | STR | MAG | AGI | VIT |
|---------|-----|-----|-----|-----|
| Hỏa / Strength-like | +12% | -5% | 0% | +8% |
| Lôi / Agility-like | 0% | -5% | +15% | 0% |
| Thủy / Magic-like | -5% | +15% | 0% | -5% |
| Neutral | 0% | 0% | 0% | 0% |

Formula:

```text
StatAfterElement = max(1, floor(StatBeforeElement * (100 + ElementNudgePercent) / 100))
```

Design notes:

- Hỏa có HP/tank feel tốt hơn.
- Lôi có accuracy/crit/dodge feel tốt hơn.
- Thủy có MP/skill uptime tốt hơn.
- Neutral dùng cho early/filler nếu không muốn ép khắc hệ.

### Derived Battle Stats for Monster

After generating STR/MAG/AGI/VIT, use the same derived status framework already
documented for player.

Java-faithful calculators:

```text
Hỏa / Cường Lực — jq:
MaxHP     = Vitality * 6
MinDamage = Strength
MaxDamage = Strength * 120 / 100
Defense   = Agility / 2
Dodge     = Agility * 2
Hit       = Agility * 3
Crit      = min(5 + Agility / 8, 30)

Lôi / Thân Pháp — js:
MaxHP     = Vitality * 4
MinDamage = (Agility * 80 + Strength * 16) / 100
MaxDamage = Agility + Strength / 5
Defense   = Agility / 2
Dodge     = Agility * 15 / 10
Hit       = Agility * 3
Crit      = min(5 + Agility / 8, 30)

Thủy / Nội Lực — jr:
MaxHP     = Vitality * 5
MinDamage = Magic * 130 / 100
MaxDamage = Magic * 150 / 100
Defense   = Agility / 2
Dodge     = Agility * 3
Hit       = Agility * 2
Crit      = min(5 + Agility / 8, 30)
```

Monster MP:

```text
MaxMP = 40 + level * 6 + Magic * 8
```

Monster Power:

```text
MaxPower = 100
CurrentPowerAtBattleStart = 0
```

Recommended battle start resources:

```text
CurrentHP = MaxHP
CurrentMP = floor(MaxMP * InitialMpPercent / 100)
CurrentPower = 0
```

By default:

| IQ bucket | Initial MP |
|-----------|------------|
| Siêu gà | 20% |
| Bờm | 35% |
| Ma lanh | 50% |
| Tốc chiến | 75% |
| Tuyệt đỉnh | 65% |

Reason:

- Tốc chiến should be able to pressure early.
- Tuyệt đỉnh is smarter but not always full-spam from turn 1.
- MP still has to be earned/managed through board rules.

### Monster Damage Formula

Client Java only receives final deltas; formula is server-owned in remake.

Use the same final order recommended by player balance:

```text
RawDamage
-> Defense reduction
-> Skill multiplier
-> Critical multiplier
-> Element multiplier
-> PvE/PvP mode multiplier
-> Clamp min damage
```

Recommended integer formula:

```text
AttackRoll = RandomInt(MinDamage, MaxDamage)

AfterDefense = max(1, AttackRoll - floor(TargetDefense * DefensePiercePercent / 100))

AfterSkill = floor(AfterDefense * SkillPercent / 100)

AfterCrit =
  IsCritical
    ? floor(AfterSkill * CriticalDamagePercent / 100)
    : AfterSkill

AfterElement = floor(AfterCrit * ElementPercent / 100)

FinalDamage = max(1, floor(AfterElement * ModePercent / 100))
```

Defaults:

```text
DefensePiercePercent = 100
BasicAttackSkillPercent = 100
CriticalDamagePercent = 200 for monster/player critical hits per user confirmation
PvE ModePercent = 100
PvP ModePercent = 90 if PvP damage needs dampening
```

Critical:

```text
IsCritical = RandomPercent() < CriticalRate
CriticalDamage = x2
```

User-confirmed rule:

- Crit = `x2`.

### Element Interaction

Use the player balance document's 3-way circle.

```text
Cường Lực / Hỏa khắc Thân Pháp / Lôi
Thân Pháp / Lôi khắc Nội Lực / Thủy
Nội Lực / Thủy khắc Cường Lực / Hỏa
```

Damage percent:

```text
Advantage = 112
Neutral = 100
Disadvantage = 92
```

Function:

```csharp
public static int ResolveElementDamagePercent(Element attacker, Element defender)
{
    if (attacker == Element.Neutral || defender == Element.Neutral)
    {
        return 100;
    }

    if ((attacker == Element.Strength && defender == Element.Agility) ||
        (attacker == Element.Agility && defender == Element.Magic) ||
        (attacker == Element.Magic && defender == Element.Strength))
    {
        return 112;
    }

    if ((defender == Element.Strength && attacker == Element.Agility) ||
        (defender == Element.Agility && attacker == Element.Magic) ||
        (defender == Element.Magic && attacker == Element.Strength))
    {
        return 92;
    }

    return 100;
}
```

Apply to both directions:

- player -> monster
- monster -> player
- PvE and PvP

### Board Rule Is Shared by Player and Monster

User-confirmed rule:

```text
Cả monster và player đều phải tuân thủ quy tắc bàn cờ.
```

Meaning:

- Monster does not get free MP/Power out of nowhere.
- Monster skill cast must check real current MP.
- If monster lacks MP, it should rely on:
  - basic attack
  - board item / gem action
  - resource gain from board
- Player and monster must use the same server-side board resolution pipeline.

Important server boundary:

```text
BattleTurnEngine owns board action truth.
Monster AI chooses intended action.
Board/match resolver decides actual resource/damage effects.
Packet assembler only serializes result.
```

### Monster Skill Model

User-corrected rule:

```text
"Skill 1" của monster chính là skill đầu tiên của hệ như character.
```

So:

- Skill 1 is **not** a free basic attack.
- Skill 1 is the first elemental/class skill in the monster's skill list.
- Skill 1 has MP cost from `lv.e`.
- Monster must have enough MP to use it.

Recommended contract:

```csharp
public sealed record MonsterSkillTemplate(
    int SkillId,
    int SkillLevel,
    int ManaCost,
    int SkillPercent,
    MonsterSkillKind Kind,
    int Weight = 100
);
```

Basic attack is separate from skill list:

```csharp
public sealed record MonsterBasicAttackTemplate(
    int SkillPercent = 100,
    int ManaCost = 0
);
```

Rules:

1. If selected skill has enough MP:
   - cast skill
   - subtract MP
   - resolve board/target/damage through server battle engine
2. If not enough MP:
   - do not cast that skill
   - choose lower MP skill if IQ allows
   - otherwise use board action/basic attack
3. Monster should not skip turn just because MP is low unless a specific status
   effect prevents action.

### Multi-Skill Selection

User direction:

```text
Skill tiếp theo thì nên random nhưng ưu tiên skill tốn ít MP.
```

Recommended selection algorithm:

```text
AvailableSkills = skills where ManaCost <= CurrentMP

if AvailableSkills empty:
    return BasicAttackOrBoardResourceAction

Sort/weight skills by:
- lower ManaCost gets higher baseline weight
- IQ unlocks higher-cost or situational skills
- role can bias skill kind
- threat tier can increase chance to use stronger skill
```

Low MP priority weight:

```text
MpWeight = max(10, 100 - ManaCost)
```

If two skills are usable, lower MP skill naturally appears more often.

Optional adjusted weight:

```text
SkillWeight =
  BaseWeight
+ LowMpPreference
+ IqSkillBonus
+ RoleKindBonus
- OverkillWastePenalty
```

Where:

```text
LowMpPreference = max(0, 80 - ManaCost)
```

Design:

- Monster does not always use strongest skill.
- Monster remains sustainable.
- High IQ can still pick stronger skill when it matters.

### IQ Buckets and AI Behavior

Java client-visible IQ labels from `ha.java` remain unchanged.

| IQ condition | Label | Server AI behavior |
|--------------|-------|--------------------|
| `< 3` | `Siêu gà` | poor AI; mostly basic/board, random target, low skill use |
| `< 7` | `Bờm` | basic AI; can use skill if MP enough, still random-heavy |
| `< 10` | `Ma lanh` | smart AI; prefers useful skill, target low HP/weakness |
| `== 11` | `Tốc chiến` | aggressive opener; uses skill whenever MP allows, fast pressure |
| otherwise | `Tuyệt đỉnh` | best AI; optimized skill/target/resource decisions |

Recommended numeric AI profile:

| Label | Skill attempt | Target quality | Skill quality | MP conservation |
|-------|---------------|----------------|---------------|-----------------|
| Siêu gà | 25% | random | lowest/simple | none |
| Bờm | 45% | mostly random | low MP skill | low |
| Ma lanh | 65% | low HP / element advantage | weighted best | medium |
| Tốc chiến | 90% | fastest kill pressure | first usable/offensive | low; spam if MP |
| Tuyệt đỉnh | 80% | best expected result | best expected value | high |

Interpretation:

- `Skill attempt` is checked only when at least one skill is usable.
- If no skill is usable, monster must use board/basic/resource action.
- `Tốc chiến` is allowed to spam skill if MP exists.
- `Tuyệt đỉnh` is smarter than Tốc chiến, but not necessarily more reckless;
  it may conserve MP if a cheap action can kill or if no good target exists.

### Monster AI Action Selection Pseudo-Code

```csharp
MonsterAction DecideMonsterAction(MonsterBattleState self, BattleState battle)
{
    var profile = ResolveIqProfile(self.IqValue);

    var boardOpportunity = AnalyzeBoard(battle.Board, self);
    var usableSkills = self.Skills
        .Where(skill => skill.ManaCost <= self.CurrentMp)
        .ToArray();

    if (usableSkills.Length == 0)
    {
        return DecideNoMpAction(self, battle, boardOpportunity, profile);
    }

    if (!RollPercent(profile.SkillAttemptPercent))
    {
        return DecideBoardOrBasicAction(self, battle, boardOpportunity, profile);
    }

    var target = SelectTarget(self, battle, profile);
    var skill = SelectSkill(self, target, usableSkills, profile);

    return MonsterAction.CastSkill(skill.SkillId, target.CombatantId);
}
```

No MP behavior:

```csharp
MonsterAction DecideNoMpAction(...)
{
    if (boardOpportunity.CanGainMana && profile.CanRecognizeManaOpportunity)
    {
        return MonsterAction.MatchBoard(boardOpportunity.BestManaMove);
    }

    if (boardOpportunity.CanGainPowerOrHeal && profile.CanUseResourceOpportunity)
    {
        return MonsterAction.MatchBoard(boardOpportunity.BestResourceMove);
    }

    return MonsterAction.BasicAttack(SelectTarget(...));
}
```

Important:

- Monster with no MP does **not** magically cast.
- Monster should try to recover MP from board if smart enough.
- Low IQ may miss obvious MP board opportunities.

### Target Selection by IQ

Target logic must be server-owned.

| IQ label | Target behavior |
|----------|-----------------|
| Siêu gà | random valid target |
| Bờm | random 70%, lowest HP 30% |
| Ma lanh | lowest HP, element advantage, avoid overkill waste |
| Tốc chiến | lowest effective HP / fastest kill |
| Tuyệt đỉnh | best expected value: kill > advantage > threat > low HP |

For current 1v1 PvE, target selection is trivial.
But keeping this model matters for future party/PvP/multi-monster battle.

### Skill Selection by IQ

| IQ label | Skill selection |
|----------|-----------------|
| Siêu gà | first usable or cheapest |
| Bờm | weighted random, cheap skills favored |
| Ma lanh | weighted random among useful skills |
| Tốc chiến | offensive skill whenever MP allows; cheap skill spam allowed |
| Tuyệt đỉnh | expected value: kill skill, advantage skill, conserve if wasteful |

Recommended weighting for skill list:

```text
Weight(skill) =
  skill.BaseWeight
+ max(0, 80 - skill.ManaCost)        // cheaper skill preference
+ IqOffenseBonus
+ RoleKindBonus
+ KillOpportunityBonus
+ ElementAdvantageBonus
- WastePenalty
```

Example constants:

```text
KillOpportunityBonus = 100
ElementAdvantageBonus = 30
WastePenalty = 40 if skill damage greatly exceeds remaining HP and cheaper skill can kill
```

### Board-Aware MP Rule

Because player and monster share the board, MP is not just a cooldown.

Server must consider:

```text
CanCastSkill = CurrentMP >= SkillManaCost
```

If false:

```text
try board MP gain -> try basic attack -> end with legal fallback
```

Monster cannot ignore MP check.

### Recommended C# Contracts

```csharp
public enum MonsterElement
{
    Neutral = 0,
    Strength = 1,
    Agility = 2,
    Magic = 4
}

public enum MonsterCombatRole
{
    Balanced,
    Bruiser,
    Assassin,
    Caster,
    Tank
}

public enum MonsterThreatTier
{
    Normal,
    Tough,
    Elite,
    Boss
}

public sealed record MonsterStatProfile(
    int Level,
    MonsterElement Element,
    MonsterCombatRole Role,
    MonsterThreatTier ThreatTier,
    int IqValue
);

public sealed record GeneratedMonsterStats(
    int Strength,
    int Magic,
    int Agility,
    int Vitality,
    int MaxHp,
    int MaxMp,
    int MaxPower,
    int MinDamage,
    int MaxDamage,
    int Defense,
    int HitRate,
    int DodgeRate,
    int CriticalRate,
    int CriticalDamage
);
```

### Monster Stat Generation Pseudo-Code

```csharp
GeneratedMonsterStats GenerateMonsterStats(MonsterStatProfile profile)
{
    var budget = profile.Level * 5;
    var role = ResolveRoleWeights(profile.Role);

    var strength = 10 + budget * role.StrengthPercent / 100;
    var magic = 10 + budget * role.MagicPercent / 100;
    var agility = 10 + budget * role.AgilityPercent / 100;
    var vitality = 10 + budget * role.VitalityPercent / 100;

    (strength, magic, agility, vitality) =
        ApplyElementNudge(profile.Element, strength, magic, agility, vitality);

    var derived = CalculateJavaFaithfulDerivedStats(
        profile.Element,
        strength,
        magic,
        agility,
        vitality);

    derived = ApplyThreatTier(profile.ThreatTier, derived);

    return derived with
    {
        CriticalDamage = 200,
        MaxPower = 100
    };
}
```

Note:

- `CriticalDamage = 200` follows user confirmation: crit is x2.
- If code already uses `lh.C` as percent-style crit damage, store `200`.
- If UI expects crit rate in `lh.C`, keep naming consistent with current code;
  Java docs currently map `lh.C` to critical damage/critical display depending
  context, so server contract should name fields explicitly.

### End-to-End Monster Turn Flow

```text
1. Battle session has monster state:
   HP/MP/Power/stats/skills/IQ/element/role

2. Monster AI reads:
   - current MP
   - usable skills
   - board opportunities
   - target state

3. AI chooses intended action:
   - cast skill if MP enough and IQ decides
   - match board/resource if useful or no MP
   - basic attack fallback

4. BattleTurnEngine validates:
   - action legal
   - skill exists
   - MP sufficient
   - board move valid

5. BattleTurnEngine resolves:
   - board clear / resource gain
   - damage/heal/status
   - HP/MP/Power updates

6. BattlePacketAssembler emits:
   - Java-like/current RN packet
   - actor deltas equivalent to nq/nl
```

### Non-Negotiable Server Rules

1. Monster `Skill 1` is the first elemental/class skill, not basic attack.
2. Skill cast must check MP.
3. Monster without MP must use legal board/basic fallback.
4. Player and monster both obey board resource rules.
5. IQ affects skill frequency, target quality, skill choice, and board/resource awareness.
6. Tốc chiến can spam skill when MP allows.
7. Tuyệt đỉnh should optimize, not blindly spam.
8. Damage/element/resource result must be calculated on server.
9. Client only renders preview/animation/deltas.
10. Do not infer stat/damage from `/offline/<id>.png`.

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

## Current Follow-Up Backlog

Use this instead of the older "next practical step" section.

### [Done]

- `MonsterAssetCatalog`
- `MonsterSpawnCatalog`
- `MonsterBattleCatalog`
- `MapMonsterRosterService`
- `MonsterBattleBootstrapService`
- socket roster / bootstrap flow by `monsterKey`
- rule-driven monster stat progression + skill assignment

### [In Progress]

- `BattleTurnEngine` as true server gameplay authority
- packet layer shrinking toward pure adapter role
- monster AI pressure using stronger derived combat-stat logic

### [Pending]

1. move more board/match/cascade truth into the server
2. extend monster rule engine into:
   `species profile + zone scaling + rare override`
3. prepare DB migration so the database stores authoring inputs while keeping
   rule logic in code/service form
4. later connect the same pattern to player progression once monster side is
   considered stable
