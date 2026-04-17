# Battle System Reconstruction

Tài liệu khôi phục hệ thống chiến đấu (match-3 board + battle scene) từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java) | `mp` | Battle scene singleton. Loads chess pieces, element VFX, casting ball, crystal sheet, star. |
| [mv.java](/d:/Twelve/reference/redecoded/decompiled/mv.java) | `mv` | Board background renderer. Picks `/play/bkboardv` or `/play/bkboardh` based on screen orientation; draws `/play/ground` under. |
| [mx.java](/d:/Twelve/reference/redecoded/decompiled/mx.java) | `mx` | HUD bar renderer — loads `/play/hpbar`, `/play/manabar`, `/play/powerbar`. |
| [mt.java](/d:/Twelve/reference/redecoded/decompiled/mt.java) | `mt` | Battle result splash — picks `/strwin` or `/strlose`. |
| [hs.java](/d:/Twelve/reference/redecoded/decompiled/hs.java) | `hs` | Post-battle stats / level-up screen. Also loads `/strwin` + `/strlose`. |
| [pc.java](/d:/Twelve/reference/redecoded/decompiled/pc.java) | `pc` | Match-3 chess-piece renderer. Loads `/focusmovechess1`, `/crystalblue`, `/hiddendragon`, `/hiddenphoenix`, `/elementsicon`. |
| [lc.java](/d:/Twelve/reference/redecoded/decompiled/lc.java) | `lc` | Aura overlay. Loads `/aura1`, `/aura2`, `/aura3`. |
| [lp.java](/d:/Twelve/reference/redecoded/decompiled/lp.java) | `lp` | Fire-element extra frames — loads `/miniexplosionfire` into `mp.Q`. |
| [da.java](/d:/Twelve/reference/redecoded/decompiled/da.java) | `da` | Casting-ball projectile — loads `/castingball`. |
| [fj.java](/d:/Twelve/reference/redecoded/decompiled/fj.java) | `fj` | Star-icon renderer in rating rows — loads `/staricon`. |
| [com/mg/sq/a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java) | `a` | Root app class — also preloads `/chess0..8` at line 1208 for quick scene switches. |

## Quick Position

The battle scene is the core gameplay loop of L12SQ: a turn-based match-3 grid where matching gems triggers element attacks, rage fills, and HP/MP damage.

Stable rules:

- `Java old client = behavior/spec`
- `client/assets/battle_legacy = working asset input for the new client`
- organize by `runtime role` (board / hud / pieces / crystals / icons / vfx / auras / result / cursor / hidden), NOT by filename prefix
- every asset in this folder is literal-string confirmed against the decompiled source

## Main Working Folder

- [client/assets/battle_legacy](/d:/Twelve/client/assets/battle_legacy)

## Asset Group Contract

### 1. Board background (`mv.java`)

```java
// mv.java line 21-27
if (orientation == VERTICAL) {
    this.a = f.d("/play/bkboardv");
} else {
    this.a = f.d("/play/bkboardh");
}
this.b = f.d("/play/ground");
```

Two orientations of the match-3 board background plus a shared `ground` overlay drawn underneath.

### 2. HUD bars (`mx.java`)

```java
// mx.java line 1568-1580
Image image = f.d("/play/hpbar");
this.D = f.d("/play/manabar");
this.E = f.d("/play/powerbar");
```

Three horizontal bar sprites: HP, Mana (MP), Power (Thanh Nộ / rage).

### 3. Chess pieces (`mp.java`)

```java
// mp.java line 390
for (int n3 = 0; n3 < 9; n3++) {
    this.c[n3] = f.d("/chess" + n3);
}
```

9 gem pieces indexed `chess0`..`chess8`. The mapping from piece index to element / role is server/config driven — do not guess it from filename.

### 4. Crystals + casting (`mp.java`, `pc.java`, `da.java`)

```java
// mp.java
this.b = f.d("/chesscrystal");  // line 393 — crystal overlay on chess
this.h = f.d("/castingball");   // line 382 — projectile sprite

// pc.java
public static Image b = f.d("/crystalblue");  // line 14 — team blue marker
// crystalred.png is paired with crystalblue in pc.java
```

`/castingball` is also re-loaded by `da.java:420` inside the projectile's own init.

### 5. Element icons (`mp.java`, `pc.java`, `fj.java`)

```java
h = f.d("/elementsicon");   // pc.java:68
this.a = f.d("/star");      // mp.java:394
private Image q = f.d("/staricon");  // fj.java:22
```

`elementsicon` is a sheet containing the 3 element types (Hỏa, Lôi, Thủy) used as overlays on the board. `star` + `staricon` are rating / marker sprites.

### 6. Element VFX (`mp.java`, `lp.java`)

```java
this.N = f.d("/firerage");            // mp.java:380
this.O = f.d("/firerageext");         // mp.java:381
mp2.Q = f.d("/miniexplosionfire");    // lp.java:29
```

Fire element currently has the only dedicated VFX. `firerageext` is the second half of the fire rage animation. `miniexplosionfire` is the match-triggered explosion. Lôi and Thủy VFX must be server-driven (or reused from other sheets) — do not synthesize here.

### 7. Auras (`lc.java`)

```java
s = f.d("/aura1");   // lc.java:235
u = f.d("/aura2");   // lc.java:247, 280
v = f.d("/aura3");   // lc.java:259, 271
```

Three tinted aura overlays drawn under chess pieces or character portraits during special states.

### 8. Result splash (`mt.java`, `hs.java`)

```java
image = f.d("/strwin");   // mt.java:710, hs.java:107
image = f.d("/strlose");  // mt.java:714, hs.java:110
```

Shown once the battle outcome is resolved server-side.

### 9. Focus cursor (`pc.java`)

```java
private static final Image i = f.d("/focusmovechess1");  // pc.java:13
```

The move-chess focus / selection cursor drawn on the highlighted grid cell.

### 10. Hidden special pieces (`pc.java`)

```java
public static Image d = f.d("/hiddendragon");   // pc.java:19
q = f.d("/hiddenphoenix");                      // pc.java:66
```

Special "hidden" match tiles that reveal dragon / phoenix on certain match patterns.

## Runtime Rules From Code

- Orientation of the board is set by a controller flag checked by `mv.java:21-24`. The new client decides once per session and caches the chosen bkboard.
- HP / Mana / Power bars are 1-row sprite sheets; filling is done by sub-rect draw (clip X axis, not alpha blend).
- Chess grid is 8×8 or 8×9 per `CLAUDE.md`. Piece count = 9 (`chess0..chess8`), so at least one piece index is reserved (likely the special crystal slot).
- Server validates every battle result — client never decides `strwin` vs `strlose` on its own.

## Server Authority Rules

Per project rule (CLAUDE.md):

- Server validates ALL battle outcomes
- Client never trusts its own HP, Mana, Power, or match calculation
- Match-3 trigger → TLV event → server returns authoritative state → client renders

The asset bundle here is purely renderer-side. No damage formula is stored in assets.

## Port Order

1. Render `00_board_background/` with orientation switch, draw `ground.png` below.
2. Render `01_hud_bars/` as sub-rect bars. Wire them to Zustand state keyed to HP / MP / Rage.
3. Render `02_chess_pieces/` on an 8×8 grid. Treat all 9 piece IDs as opaque gems — do NOT map to element names yet.
4. Add `08_focus_cursor/` over the selected cell.
5. Wire match-triggered effects: `05_element_vfx/miniexplosionfire` on match, `firerage` + `firerageext` on match-4/5 fire.
6. Add `06_auras/` as an overlay layer, triggered by a boolean state flag per piece.
7. Add `03_crystals_casting/castingball.png` as a projectile fired from source cell to target.
8. Add `04_element_icons/` as an overlay sheet for element-typed pieces.
9. Add `09_hidden_pieces/` behind matched special tiles.
10. Add `07_result_splash/` on server-sent battle end.

## Reference Skills

When implementing the battle pipeline, consult these project skills (in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`          | Domain entity `BattleState`, `ChessPiece`, `ElementType` in `Twelve.Core` |
| `binary-protocol/`       | TLV tags for match events, damage result, rage trigger |
| `database-design/`       | Postgres `BattleLogs`, `BattleResults`, JSONB for turn-by-turn replay |
| `game-mechanics/`        | Match-3 rules, elemental combo table, rage / thanh nộ formula |
| `frontend-design/`       | Skia renderer for 8×8 grid, sprite batching, 60 FPS target |
| `clean-code/`            | Naming `Match3Board`, `BattleHud`, `ResultSplash` |
| `vulnerability-scanner/` | Server-side validation audit of every battle TLV handler |
| `api-patterns/`          | Battle-state query contract shape |

## Next Practical Step

The next coding step should be a React Native + Skia `BattleScene` that:

- loads all 33 assets from `battle_legacy/` via an in-memory registry
- draws the board background according to a single `orientation` Zustand atom
- renders an 8×8 grid of `chess0..chess8` pieces with focus cursor overlay
- shows HP / MP / Power bars fed by server-broadcast state
- reacts to TLV match events by animating `castingball` + the appropriate element VFX layer
- closes with `strwin` / `strlose` splash on server-sent battle-end packet
