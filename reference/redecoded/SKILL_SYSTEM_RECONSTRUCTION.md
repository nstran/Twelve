# Skill System Reconstruction

This document defines the restoration model for the legacy skill system.

The goal is to preserve the old Java runtime architecture, separate skill-tree UI from battle effects,
and organize assets by stable runtime families instead of guessed visual names.

## Scope

This document covers:

- skill-tree UI assets
- skill icon loading
- battle skill runtime asset families
- shared named battle skill assets
- organized working folders for the new client

This document does not cover:

- full server skill balancing
- final player-facing skill naming
- map or monster assets outside skill support

## Source Of Truth

Primary evidence:

- [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java:28)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:2041)
- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:143)
- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:377)
- [io.java](/d:/Twelve/reference/redecoded/decompiled/io.java:22)
- [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java:24)
- [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java:43)
- [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:84)
- [skill_runtime_manifest.csv](/d:/Twelve/reference/redecoded/skill_runtime_manifest.csv)

Operational rule:

- `Java old client = behavior/spec`
- `skill_system_organized` and `skill` = implementation input
- `TypeScript/React Native` = production runtime we will ship

## What The Old Client Actually Does

The old client has two separate skill-related asset flows.

### 1. Skill tree and skill icon flow

The skill tree screen loads:

- `/info/skilltree`
- `/info/increase`
- `/info/decrease`

Reference:

- [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java:28)
- [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java:65)

It then loads each skill icon from the cache using:

- `go.r[n].a * 1000`

Reference:

- [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java:67)
- [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java:69)

Skill definitions themselves are parsed from server data into `lw[]` and `lx[]`.

Reference:

- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:2041)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:2049)
- [ky.java](/d:/Twelve/reference/redecoded/decompiled/ky.java:2057)

This means:

- the skill tree is not driven by arbitrary filenames
- each skill family has an icon id tied to `skillId * 1000`
- the stable organizational unit is the numeric family code

### 2. Battle skill runtime flow

Battle skill runtime sheets are loaded lazily through `mp.a(code)`.

Reference:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:143)

This loader maps skill family codes to numeric cached PNG ids such as:

- `1000 -> 1000001, 1000002`
- `1003 -> 1003001, 1003002, 1003003`
- `2007 -> 2007001`
- `4006 -> 4006001, 4006002`

Reference:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:147)
- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:167)
- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:218)
- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:287)

The loader also initializes shared named assets used by multiple battle effects:

- `/skillupdownstat`
- `/firerage`
- `/firerageext`
- `/castingball`
- `/barrier`

Reference:

- [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:377)

## Shared Battle Skill Support

Two more named assets matter for battle scene support:

- `/zap`
- `/ice`

They are loaded by the map/battle scene runtime, not by the per-skill loader.

Reference:

- [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java:84)

Another named battle helper is:

- `/magicgate`

Reference:

- [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java:43)

The explosion helper used by multiple skills is:

- `/miniexplosionfire`

It is loaded in base form and recolored variants.

Reference:

- [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java:24)
- [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java:34)
- [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java:44)

## Organized Asset Layout

The canonical working sets are:

- [reference/review_assets/skill_system_organized](/d:/Twelve/reference/review_assets/skill_system_organized)
- [client/assets/skill](/d:/Twelve/client/assets/skill)

Both folders share the same layout:

- `00_skill_tree_ui_confirmed`
- `01_battle_skill_shared_confirmed`
- `02_elemental_runtime_families`

### `00_skill_tree_ui_confirmed`

Contains only confirmed skill tree UI assets:

- `skilltree.png`
- `increase.png`
- `decrease.png`

These come directly from `/info/*`.

### `01_battle_skill_shared_confirmed`

Contains named assets used across multiple skill effects or battle runtime contexts.

Subgroups:

- `named_effects`
- `battle_scene_support`

`named_effects` contains:

- `barrier.png`
- `castingball.png`
- `firerage.png`
- `firerageext.png`
- `magicgate.png`
- `miniexplosionfire.png`
- `skillupdownstat.png`

`battle_scene_support` contains:

- `zap.png`
- `ice.png`

### `02_elemental_runtime_families`

Contains numeric runtime families grouped by code range.

Current grouping:

- `group_100x_hoa_fire_likely`
- `group_200x_loi_thunder_likely`
- `group_400x_thuy_water_likely`

This naming is intentionally conservative.

The code ranges are real.
The element labels are strong in-context interpretations based on the legacy game structure,
but we still keep `likely` in the folder names because live-server naming is not fully preserved locally.

## Family Model

Each family folder contains:

- `skill_icon/<code>000.png`
- `runtime_png/*.png`
- `family_summary.json`

This is the key architectural decision.

We do not organize the legacy skill system by guessed names like:

- `fire_ball`
- `lightning_arrow`
- `water_wave`

We organize by:

- `family_1000`
- `family_2005`
- `family_4007`

because that is how the old client actually resolves skill assets.

## Confirmed Runtime Families

The following families have direct runtime evidence in Java:

### 100x group

- `1000 -> is`
- `1001 -> it`
- `1003 -> io(elementVariant=0)`
- `1004 -> iu`
- `1005 -> iv`
- `1006 -> iw`
- `1007 -> ix`
- `1008 -> iy`

### 200x group

- `2000 -> jg`
- `2003 -> jh`
- `2004 -> ji`
- `2005 -> io(elementVariant=1)`
- `2006 -> jj`
- `2007 -> jk`
- `2008 -> jl`

### 400x group

- `4000 -> iz`
- `4001 -> ja`
- `4002 -> jb`
- `4003 -> jc`
- `4004 -> io(elementVariant=2)`
- `4005 -> jd`
- `4006 -> je`
- `4007 -> jf`

Reference:

- [skill_runtime_manifest.csv](/d:/Twelve/reference/redecoded/skill_runtime_manifest.csv)

## Special Cases

These cases must stay explicit in later code:

- `1002`
  - icon exists
  - no dedicated runtime class found in the local Java jar
- `2001`
  - icon exists
  - no dedicated runtime class found in the local Java jar
- `2002`
  - icon exists
  - no dedicated runtime class found in the local Java jar
- `4008`
  - icon exists
  - `mp` falls back to the `4000` projectile pair runtime sheets
  - [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java:355)

These should not be silently merged away.

## Variant Branches

The `io` effect class is a shared branch for three family codes:

- `1003`
- `2005`
- `4004`

Reference:

- [io.java](/d:/Twelve/reference/redecoded/decompiled/io.java:39)

That means these three families are structurally related,
but they still need separate family folders because they load different numeric runtime ids and different recolor paths.

## Confidence Rules

Use these exact terms:

- `confirmed_runtime`
  - the family or asset is loaded directly by the old Java runtime
- `icon_only_or_unresolved`
  - the icon exists, but no dedicated local runtime path has been proven
- `icon_only_with_loader_alias`
  - the icon exists, and the local runtime falls back to another family loader

## Implementation Rules For The New Client

When porting to React Native:

- start from numeric family codes
- keep skill tree icons separate from battle runtime sheets
- load shared named assets separately from numeric families
- do not hardcode final player-facing names too early
- keep `family_summary.json` and manifest data close to the renderer

Minimum implementation model:

1. load skill tree board assets from `00_skill_tree_ui_confirmed`
2. load skill icons by `skillId * 1000`
3. load battle runtime family sheets by family code
4. add shared named dependencies such as `miniexplosionfire` or `magicgate`
5. only then add final semantic labels or UI naming

## Recommended Port Order

1. Build a simple skill tree view from `skilltree.png`, `increase.png`, and `decrease.png`.
2. Build a skill icon resolver using `skillId * 1000`.
3. Build a battle skill asset resolver around `family_code`.
4. Implement one family from each main group: `1000`, `2000`, `4000`.
5. Implement the shared branch `1003/2005/4004`.
6. Implement the larger one-sheet effects like `1008`, `2007`, and `4007`.

## What Must Not Be Done

Do not:

- reorganize skill runtime sheets by eye only
- merge icons and runtime sheets into one flat folder
- rename families into final player-facing names without server proof
- discard icon-only families because they may still matter to UI and progression

## Immediate Next Deliverables

The next practical coding steps are:

1. build a skill asset resolver in the client around `client/assets/skill`
2. implement a preview/debug view for skill families by code
3. verify one representative effect from each code group
4. then connect skill runtime to the current battle system
