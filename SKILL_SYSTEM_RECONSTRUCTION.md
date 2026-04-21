# Skill System Reconstruction

Tài liệu khôi phục hệ thống kỹ năng (skill) từ Java client cũ.

If you want the deep technical reference, use:

- [reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md](/d:/Twelve/reference/redecoded/SKILL_SYSTEM_RECONSTRUCTION.md)

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java) | `mp` | Battle scene + skill asset manager (singleton) |
| [pa.java](/d:/Twelve/reference/redecoded/decompiled/pa.java) | `pa` | Offline asset loader used by `mp.b(int)` |
| [de.java](/d:/Twelve/reference/redecoded/decompiled/de.java) | `de` | Skill tree UI panel |
| [gg.java](/d:/Twelve/reference/redecoded/decompiled/gg.java) | `gg` | Skill detail / upgrade widget |
| [gu.java](/d:/Twelve/reference/redecoded/decompiled/gu.java) | `gu` | Stat up/down indicator (skill tree) |
| [ln.java](/d:/Twelve/reference/redecoded/decompiled/ln.java) | `ln` | Magic-gate effect (`/magicgate`) |
| [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java) | `lp` | Mini-explosion fire effect loader |
| `is`..`iy`, `iz`, `ja`.. | individual skill-family classes — each calls `mp.a(<family_code>)` |

## Working Folders

- [client/assets/skill](/d:/Twelve/client/assets/skill)
- [reference/review_assets/skill_system_organized](/d:/Twelve/reference/review_assets/skill_system_organized)
- [reference/redecoded/skill_runtime_manifest.csv](/d:/Twelve/reference/redecoded/skill_runtime_manifest.csv)

## Core Architecture

There are three layers. The old client does NOT treat skill icons, shared
named battle effects, and numeric runtime families as one flat asset pool:

1. `00_skill_tree_ui_confirmed` — skill tree board chrome (loaded by `de`/`gg`/`gu`)
2. `01_battle_skill_shared_confirmed` — named shared effects (loaded by `mp` constructor, `ln`, `lp`)
3. `02_elemental_runtime_families` — numeric families (loaded by `mp.a(code)`)

## 1. Skill Asset ID Schema

Every runtime skill asset is loaded via `pa.a(n2, false)` under
`/offline/<id>.png` where `<id>` is a **7-digit** number:

```
<FFFF><NNN>
  │    └─ frame index (001, 002, 003, …)
  └────── family code (1000, 1001, 1003, 2005, 4007, …)
```

Examples from `mp.java`:

| ID      | Family | Frame | Source (mp.java) |
|---------|--------|-------|------------------|
| 1000001 | 1000   | 001   | [mp.java:149](/d:/Twelve/reference/redecoded/decompiled/mp.java:149) |
| 1000002 | 1000   | 002   | [mp.java:152](/d:/Twelve/reference/redecoded/decompiled/mp.java:152) |
| 1003001 | 1003   | 001   | [mp.java:169](/d:/Twelve/reference/redecoded/decompiled/mp.java:169) |
| 2005002 | 2005   | 002   | [mp.java:258](/d:/Twelve/reference/redecoded/decompiled/mp.java:258) |
| 4007001 | 4007   | 001   | [mp.java:350](/d:/Twelve/reference/redecoded/decompiled/mp.java:350) |

Skill runtime asset IDs are always **7 digits**. Any `/offline/` file with
a different digit length belongs to a different system — see Related Docs.

## 2. Runtime Family Loader (`mp.a(int code)`)

`mp.java` line 143-375. The method is a giant switch on family code:

```java
public final void a(int n2) {
    switch (n2) {
        case 1000:
            if (this.k == null) this.k = mp.b(1000001);
            if (this.l == null) this.l = mp.b(1000002);  // 2-frame family
            return;
        case 1001:
            if (this.m == null) this.m = mp.b(1001001);  // 1-frame family
            return;
        case 1003:
            if (this.V == null) this.V = mp.b(1003001);
            if (this.W == null) this.W = mp.b(1003002);
            if (this.n == null) { this.c(); return; }    // 3-frame ultimate
            return;
        // ... etc.
    }
}
```

Asset loader `mp.b(int)` (line 137):

```java
private static Image b(int n2) {
    return pa.a().a(n2, false);   // /offline/<id>.png
}
```

## 3. Confirmed Family Codes

Extracted from `mp.java` switch cases (line 143-365):

### Group 100x — `hoa` / Hỏa (fire) — *likely*

| Family | Frames | Java class | Source |
|--------|--------|-----------|--------|
| 1000   | 2      | `is`      | [is.java:40](/d:/Twelve/reference/redecoded/decompiled/is.java:40) |
| 1001   | 1      | `it`      | [it.java:27](/d:/Twelve/reference/redecoded/decompiled/it.java:27) |
| 1002   | 0 (empty stub) | — | [mp.java:164](/d:/Twelve/reference/redecoded/decompiled/mp.java:164) |
| 1003   | 2 + helper | `io` | [io.java:41](/d:/Twelve/reference/redecoded/decompiled/io.java:41) |
| 1004   | 1      | `iu`      | [iu.java:38](/d:/Twelve/reference/redecoded/decompiled/iu.java:38) |
| 1005   | 1      | `iv`      | [iv.java:46](/d:/Twelve/reference/redecoded/decompiled/iv.java:46) |
| 1006   | 2      | `iw`      | [iw.java:29](/d:/Twelve/reference/redecoded/decompiled/iw.java:29) |
| 1007   | 1      | `ix`      | [ix.java:59](/d:/Twelve/reference/redecoded/decompiled/ix.java:59) |
| 1008   | 1      | `iy`      | [iy.java:93](/d:/Twelve/reference/redecoded/decompiled/iy.java:93) |

### Group 200x — `loi` / Lôi (thunder) — *likely*

| Family | Frames | Notes |
|--------|--------|-------|
| 2000   | 1 + 2-array | asset `this.u = 2000001`, `this.v = [2000002, 2000003]` |
| 2001   | 0 (empty) | — |
| 2002   | 0 (empty) | — |
| 2003   | 2      | `this.w = 2003001`, `this.x = 2003002` |
| 2004   | 1      | `this.y = 2004001` |
| 2005   | 2 + helper | `this.X = 2005001`, `this.Y = 2005002`, `this.d()` loads more (ultimate) |
| 2006   | 1      | `this.A = 2006001` |
| 2007   | 1      | `this.B = 2007001` |
| 2008   | 1      | `this.C = 2008001` |

### Group 400x — `thuy` / Thủy (water) — *likely*

| Family | Frames | Notes |
|--------|--------|-------|
| 4000   | 2      | `this.D = 4000001`, `this.E = 4000002` |
| 4001   | 1      | `this.F = 4001001` |
| 4002   | 1      | `this.G = 4002001` |
| 4003   | 1      | `this.H = 4003001` |
| 4004   | 2 + helper | `this.Z = 4004001`, `this.aa = 4004002`, `this.e()` loads more (ultimate) |
| 4005   | 1      | `this.J = 4005001` |
| 4006   | 2      | `this.K = 4006001`, `this.L = 4006002` |
| 4007   | 1      | `this.M = 4007001` |
| 4008   | alias  | reuses 4000's frames (line 355-361) |

**Important**: The element labels (`hoa`/`loi`/`thuy`) are **conservative
guesses** based on match-3 mechanic (Hỏa / Lôi / Thủy in `game-mechanics`
skill). The **code ranges themselves are confirmed** from Java, but the
player-facing element names must come from the server skill catalog.

## 4. Confirmed Named Shared Effects

Loaded directly by string path (not numeric ID) — these are safe to treat
as "named" because the Java literal is the source of truth:

| String path | Load site | Java field |
|-------------|-----------|------------|
| `/skillupdownstat` | [mp.java:379](/d:/Twelve/reference/redecoded/decompiled/mp.java:379) | `mp.P` |
| `/firerage`        | [mp.java:380](/d:/Twelve/reference/redecoded/decompiled/mp.java:380) | `mp.N` |
| `/firerageext`     | [mp.java:381](/d:/Twelve/reference/redecoded/decompiled/mp.java:381) | `mp.O` |
| `/castingball`     | [mp.java:382](/d:/Twelve/reference/redecoded/decompiled/mp.java:382) | `mp.h` |
| `/barrier`         | [mp.java:383](/d:/Twelve/reference/redecoded/decompiled/mp.java:383) | `mp.j` |
| `/chess0`..`/chess8` | [mp.java:390](/d:/Twelve/reference/redecoded/decompiled/mp.java:390) | `mp.c[0..8]` — match-3 gem sprites |
| `/chesscrystal`    | [mp.java:393](/d:/Twelve/reference/redecoded/decompiled/mp.java:393) | `mp.b` |
| `/star`            | [mp.java:394](/d:/Twelve/reference/redecoded/decompiled/mp.java:394) | `mp.a` |
| `/bloodthrowaround`| [mp.java:395](/d:/Twelve/reference/redecoded/decompiled/mp.java:395) | `mp.f` |
| `/miniexplosionfire` | [lp.java:29](/d:/Twelve/reference/redecoded/decompiled/lp.java:29) | `mp.Q` (injected) |
| `/magicgate`       | [ln.java:44](/d:/Twelve/reference/redecoded/decompiled/ln.java:44) | `ln`-owned (4-frame anim) |

## 5. Skill Tree UI Chrome

Loaded via `/info/<name>` string paths:

| String path | Load site |
|-------------|-----------|
| `/info/skilltree` | [de.java:65](/d:/Twelve/reference/redecoded/decompiled/de.java:65) |
| `/info/increase`  | [de.java:28](/d:/Twelve/reference/redecoded/decompiled/de.java:28), [gu.java:18](/d:/Twelve/reference/redecoded/decompiled/gu.java:18) |
| `/info/decrease`  | [de.java:29](/d:/Twelve/reference/redecoded/decompiled/de.java:29), [gu.java:19](/d:/Twelve/reference/redecoded/decompiled/gu.java:19) |

## 6. Asset Folder Structure

```
client/assets/skill/
│
├── 00_skill_tree_ui_confirmed/
│   └── skill_tree_board/
│       ├── skilltree.png
│       ├── increase.png
│       └── decrease.png
│
├── 01_battle_skill_shared_confirmed/
│   ├── named_effects/
│   │   ├── barrier.png
│   │   ├── castingball.png
│   │   ├── firerage.png
│   │   ├── firerageext.png
│   │   ├── magicgate.png
│   │   ├── miniexplosionfire.png
│   │   └── skillupdownstat.png
│   └── battle_scene_support/
│       ├── ice.png
│       └── zap.png
│
├── 02_elemental_runtime_families/
│   ├── group_100x_hoa_fire_likely/
│   │   ├── family_1000_is/      (1000001, 1000002)
│   │   ├── family_1001_it/      (1001001)
│   │   ├── family_1002_no_dedicated_class/
│   │   ├── family_1003_io_elementVariant-0/
│   │   ├── family_1004_iu/
│   │   ├── family_1005_iv/
│   │   ├── family_1006_iw/
│   │   ├── family_1007_ix/
│   │   └── family_1008_iy/
│   ├── group_200x_loi_thunder_likely/
│   └── group_400x_thuy_water_likely/
│
├── README.md
└── index.csv
```

## 7. What This Doc Intentionally Does NOT Store

- player-facing skill names (e.g. "Hỏa Cầu", "Lôi Chưởng") — server catalog
- skill MP / rage cost — server formula
- skill damage multiplier — server formula
- skill level tree — server catalog
- cooldown timers — server state

The offline jar carries **only the sprite + effect assets**, plus the hard
mapping `family_code → asset IDs`. Everything mechanical is server-driven.

## Port Order

1. Build the **skill tree UI** chrome from `/info/skilltree`, `/info/increase`, `/info/decrease`.
2. Build the **asset resolver** that maps `family_code → [asset_id…]` (mirror of `mp.a(code)` switch).
3. Build the **named shared effect preloader** that loads the 11 `/x` string paths at battle start.
4. Implement **one test family per group** (e.g. 1000 fire, 2000 lightning, 4000 water) end-to-end.
5. Only **after** the server skill catalog is authored, promote family codes to player-facing names.

## Reference Skills

When implementing the skill pipeline, consult these project skills
(in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`          | Domain entity `Skill` in `Twelve.Core` |
| `binary-protocol/`       | TLV tags for skill-slot / skill-level packets |
| `database-design/`       | Postgres `PlayerSkills` table, JSONB for learned tree |
| `game-mechanics/`        | Match-3 → rage → skill trigger pipeline |
| `frontend-design/`       | Skia-based skill animation player |
| `clean-code/`            | Naming `SkillFamily`, `SkillAssetRef` |
| `vulnerability-scanner/` | Never trust client for skill damage — OWASP-ish check |

## Next Practical Step

Build a **client-side skill asset resolver** that reads
`client/assets/skill/index.csv`, exposes a
`GetFrames(familyCode) → [assetId…]` API, and refuses to serve any
family code not present in the confirmed table above. This forces any
new family (from future server catalog) through an explicit promotion
step.
