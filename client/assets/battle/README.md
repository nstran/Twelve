# battle

Legacy battle-scene asset bundle. All assets here are referenced by literal string in the decompiled Java source.

## Layout

| Folder | Purpose | Files | Java ref |
|--------|---------|-------|----------|
| `00_board_background/` | Match-3 board background (vertical / horizontal) + hit ground overlay | 3 | `mv.java:21-27` |
| `01_hud_bars/`         | HP / Mana / Power (Thanh Nộ) bars over the board | 3 | `mx.java:1568-1580` |
| `02_chess_pieces/`     | 9 gem pieces for the match-3 grid (chess0..chess8) | 9 | `mp.java:390`, `com/mg/sq/a.java:1208` |
| `03_crystals_casting/` | Element crystals + casting ball sprite | 4 | `mp.java:382-393`, `pc.java:14-19`, `da.java:420` |
| `04_element_icons/`    | Element overlay icons + star markers | 3 | `mp.java:394`, `pc.java:68`, `fj.java:22` |
| `05_element_vfx/`      | Fire element VFX layers | 3 | `mp.java:380-381`, `lp.java:29` |
| `06_auras/`            | Tinted aura overlays (3 color variants) | 3 | `lc.java:235-280` |
| `07_result_splash/`    | Victory / Defeat splash images | 2 | `mt.java:710-714`, `hs.java:107-110` |
| `08_focus_cursor/`     | Move-chess focus cursor | 1 | `pc.java:13` |
| `09_hidden_pieces/`    | Hidden dragon / phoenix special tiles | 2 | `pc.java:19`, `pc.java:66` |
| `10_hit_effects/`      | Blood throw + zoom-focus on-hit overlays | 2 | `mp.java:395`, `mp.java:412` |
| `11_barrier/`          | Barrier shield sprite drawn over protected targets | 1 | `mp.java:383` |

Total: 36 files. All confirmed — every file name appears as a literal `f.d("/name")` or `f.a("/name")` call in the decompiled source.

## Asset Name Reference

| File | `f.d()` path | Java callsite |
|------|--------------|---------------|
| bkboardv.png | `/play/bkboardv` | mv.java:21 |
| bkboardh.png | `/play/bkboardh` | mv.java:24 |
| ground.png | `/play/ground` | mv.java:27 |
| hpbar.png | `/play/hpbar` | mx.java:1568 |
| manabar.png | `/play/manabar` | mx.java:1579 |
| powerbar.png | `/play/powerbar` | mx.java:1580 |
| chess0-8.png | `/chess<N>` | mp.java:390 loop |
| chesscrystal.png | `/chesscrystal` | mp.java:393 |
| crystalblue.png | `/crystalblue` | pc.java:14 |
| crystalred.png | `/crystalred` | (paired with crystalblue in pc.java) |
| castingball.png | `/castingball` | mp.java:382, da.java:420 |
| elementsicon.png | `/elementsicon` | pc.java:68 |
| star.png | `/star` | mp.java:394 |
| staricon.png | `/staricon` | fj.java:22 |
| firerage.png | `/firerage` | mp.java:380 |
| firerageext.png | `/firerageext` | mp.java:381 |
| miniexplosionfire.png | `/miniexplosionfire` | lp.java:29 |
| aura1.png | `/aura1` | lc.java:235 |
| aura2.png | `/aura2` | lc.java:247, 280 |
| aura3.png | `/aura3` | lc.java:259, 271 |
| strwin.png | `/strwin` | mt.java:710, hs.java:107 |
| strlose.png | `/strlose` | mt.java:714, hs.java:110 |
| focusmovechess1.png | `/focusmovechess1` | pc.java:13 |
| hiddendragon.png | `/hiddendragon` | pc.java:19 |
| hiddenphoenix.png | `/hiddenphoenix` | pc.java:66 |
| bloodthrowaround.png | `/bloodthrowaround` | mp.java:395 |
| zoomfocus.png | `/zoomfocus` | mp.java:412 |
| barrier.png | `/barrier` | mp.java:383 |
