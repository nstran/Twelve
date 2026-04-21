# hud

Legacy HUD / info-panel asset bundle. Hosts the small stat icons + stat-editor widgets that appear across battle, world map, and character screens.

## Layout

| Folder | Purpose | Files | Confidence |
|--------|---------|-------|------------|
| `00_stat_icons/`      | Gold / Heart (HP) / EXP icons shown in stat rows | 3 | confirmed |
| `01_button_markers/`  | Button-increase `+` marker used next to stat rows | 1 | confirmed |
| `02_gauge_and_chest/` | EXP gauge bar + item-chest button | 2 | confirmed |
| `03_hidden_marker/`   | Hidden-objective marker drawn over blocked content | 1 | confirmed |

Total: 7 files. All are literal-string confirmed.

## Asset paths

| File | `f.d()` path | Java callsite |
|------|--------------|---------------|
| 00_stat_icons/gold.png       | `/info/gold`       | of.java:96, da.java:274, hs.java:82 |
| 00_stat_icons/heart.png      | `/info/heart`      | da.java:273, hs.java:83 |
| 00_stat_icons/expicon.png    | `/info/expicon`    | da.java:272, hs.java:84 |
| 01_button_markers/btinscrease.png | `/info/btinscrease` | da.java:305, gf.java:13 |
| 02_gauge_and_chest/gauge.png      | `/info/gauge`       | jt.java:24 |
| 02_gauge_and_chest/itemchest.png  | `/info/itemchest`   | om.java:326 |
| 03_hidden_marker/hidenobj.png     | `/info/hidenobj`    | hh.java:14 |

## Notes

- `/info/skilltree`, `/info/increase`, `/info/decrease` are NOT here — they live in `skill/00_skill_tree_ui_confirmed/` because their callsites are in the skill-tree panel (`de.java`, `gu.java`, `gg.java`), not the generic HUD.
- `heart` = HP icon, not actual heart rate. Named after the sprite shape.
- `gauge` is a horizontal EXP bar; `expicon` is the small badge next to it.
