# HUD System Reconstruction

Tài liệu khôi phục hệ thống chỉ số / HUD nhỏ (gold, heart, exp, gauge, chest) từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [of.java](/d:/Twelve/reference/redecoded/decompiled/of.java) | `of` | Stats row in inventory / player panel — loads `/info/gold` |
| [da.java](/d:/Twelve/reference/redecoded/decompiled/da.java) | `da` | Character panel — loads gold, heart, expicon, btinscrease |
| [hs.java](/d:/Twelve/reference/redecoded/decompiled/hs.java) | `hs` | Post-battle stats — loads gold, heart, expicon, strwin/strlose |
| [gf.java](/d:/Twelve/reference/redecoded/decompiled/gf.java) | `gf` | Stat-increase control — loads `/info/btinscrease` |
| [jt.java](/d:/Twelve/reference/redecoded/decompiled/jt.java) | `jt` | EXP gauge renderer — loads `/info/gauge` |
| [om.java](/d:/Twelve/reference/redecoded/decompiled/om.java) | `om` | Map scene — loads `/info/itemchest` |
| [hh.java](/d:/Twelve/reference/redecoded/decompiled/hh.java) | `hh` | Hidden-objective overlay — loads `/info/hidenobj` |

## Quick Position

The HUD bundle is the set of small icons that appear next to stat values across scenes (battle, world map, character panel, post-battle).

Stable rules:

- `Java old client = behavior/spec`
- `client/assets/hud = working asset input for the new client`
- HUD assets are scene-agnostic — the SAME `/info/gold` sprite is drawn in character panel, post-battle screen, and shop.
- Skill-tree UI (`/info/skilltree`, `/info/increase`, `/info/decrease`) is NOT here — it lives under `skill/` because the callsite is the skill-tree panel, not the generic HUD.

## Main Working Folder

- [client/assets/hud](/d:/Twelve/client/assets/hud)

## Asset Contract

### Stat icons (`da.java`, `hs.java`, `of.java`)

```java
// da.java line 272-274
this.w = f.d("/info/expicon");   // exp badge
this.x = f.d("/info/heart");     // HP / heart
this.v = f.d("/info/gold");      // gold / KEN

// hs.java line 82-84 — same 3 icons, different field names
this.I = f.d("/info/gold");
this.K = f.d("/info/heart");
this.J = f.d("/info/expicon");

// of.java line 96 — gold-only usage in inventory rows
this.r = f.d("/info/gold");
```

### Button-increase marker (`da.java`, `gf.java`)

```java
// da.java line 305
Image image = f.d("/info/btinscrease");

// gf.java line 13
private Image j = f.d("/info/btinscrease");
```

Drawn next to a stat row where the player can spend points to raise the value.

### EXP gauge + chest (`jt.java`, `om.java`)

```java
// jt.java line 24
this.a = f.d("/info/gauge");      // horizontal exp bar

// om.java line 326 — used in map scene, for inventory-chest button
Image image3 = f.d("/info/itemchest");
```

### Hidden-object marker (`hh.java`)

```java
// hh.java line 14
private Image k = f.d("/info/hidenobj");
```

Drawn over a locked / unavailable content slot.

## Runtime Rules

- Icons are drawn at fixed pixel size next to a numeric label (`gold`: gold amount, `heart`: current HP, `expicon`: EXP amount).
- `gauge` is a horizontal bar — the sub-rect clip is computed as `width * currentExp / expToNextLevel`.
- `btinscrease` is conditionally drawn only when the player has unspent stat points — the condition comes from a server-broadcast "stat points available" counter.
- `itemchest` opens the inventory when tapped — ties into the equipment system via CMD 29 / 43.
- `hidenobj` is a pure overlay; no interactivity.

## Port Order

1. Render `00_stat_icons/` next to their numeric labels in the character panel and post-battle screen.
2. Render `02_gauge_and_chest/gauge.png` as a sub-rect clipped EXP bar. Wire it to server EXP delta events.
3. Render `01_button_markers/btinscrease.png` only when server says `statPointsAvailable > 0`.
4. Render `02_gauge_and_chest/itemchest.png` on the map HUD; wire tap → open inventory.
5. Render `03_hidden_marker/hidenobj.png` over server-flagged blocked content.

## Reference Skills

| Skill | Use When |
|-------|----------|
| `architecture/`     | Domain entity `PlayerStats` in `Twelve.Core` |
| `binary-protocol/`  | TLV tags for stat delta packets (exp, gold, hp) |
| `frontend-design/`  | Sub-rect clip animation for the gauge, 60 FPS label updates |
| `game-mechanics/`   | EXP-to-next-level curve; stat-point accrual rules |
| `clean-code/`       | Naming `HudOverlay`, `StatRow`, `ExpGauge` |

## Next Practical Step

The next coding step should be a React Native + Skia `HudLayer` component that:

- sits above the scene renderer (`BattleScene`, `MapScene`, `CharacterScene`)
- exposes props `{ gold, hp, maxHp, exp, expToNext, statPoints, chestAvailable }`
- draws the 3 stat icons + numeric labels, the gauge, the chest button, and optionally the `+` button per row
- subscribes to a Zustand `playerStatsStore` that is mutated only by server-broadcast TLV packets
