# map_legacy

Legacy world-map asset bundle. Hosts the sprites for the overworld / zone-select map that sits between login and battle.

## Layout

| Folder | Purpose | Files | Confidence |
|--------|---------|-------|------------|
| `00_map_tileset/`               | Main map sheet (`/m/m`) — background tileset / overworld sheet | 1 | confirmed |
| `01_location_markers/`          | POI markers on the overworld (arena, room, fsw, room icon) | 4 | confirmed |
| `02_cursor_pointer/`            | Hand cursor + arrow used to pick a location | 2 | confirmed |
| `03_locked_markers/`            | Locked-location overlays (primary + alt) | 2 | confirmed |
| `04_map_background_candidate/`  | Large painted map background (Hoa Lư capital). No literal ref found. | 1 | candidate |
| `05_ui_labels_candidate/`       | Focus-name plate + small `l` label. No literal ref found. | 2 | candidate |
| `06_ground_tiles_ai_candidate/` | AI-cropped modular ground tiles used to test a richer Hoa Lư bottom strip | 3 | candidate |

Total: 15 files.

## Confirmed asset paths

| File | `f.d()` path | Java callsite |
|------|--------------|---------------|
| 00_map_tileset/m.png | `/m/m` | oh.java:53 |
| 01_location_markers/arena.png | `/m/arena` | fz.java:27 |
| 01_location_markers/room.png | `/m/room` | fz.java:30 |
| 01_location_markers/fsw.png | `/m/fsw` | hi.java:13 |
| 01_location_markers/roomicon.png | `/roomicon` | pc.java:67 |
| 02_cursor_pointer/hand.png | `/m/hand` | pc.java:20 |
| 02_cursor_pointer/arrow.png | `/m/arrow` | pc.java:21 |
| 03_locked_markers/lock.png | `/m/lock` | oh.java:55, fz.java:24 |
| 03_locked_markers/lock2.png | `/m/lock2` | fg.java:42 |

## Candidate assets

| File | Hypothesis |
|------|------------|
| 04_map_background_candidate/bgmap_hoa_lu.png | Large overworld background of Hoa Lư capital. 2.3 MB, added during reconstruction. Likely the base layer `/m/m` is drawn on top of. |
| 05_ui_labels_candidate/focusname.png | Focused location name plate. |
| 05_ui_labels_candidate/l.png | Tiny "l" glyph — possibly level marker or legend. |
| 06_ground_tiles_ai_candidate/hoa_lu_ground_left.png | AI-cropped left-end ground tile for Hoa Lư terrain strip tests. |
| 06_ground_tiles_ai_candidate/hoa_lu_ground_center.png | AI-cropped repeatable center ground tile for Hoa Lư terrain strip tests. |
| 06_ground_tiles_ai_candidate/hoa_lu_ground_right.png | AI-cropped right-end ground tile for Hoa Lư terrain strip tests. |

Promote to confirmed only after the map controller (`oh.java` / `fz.java`) is fully ported and the dynamic callsite that loads these is identified.
