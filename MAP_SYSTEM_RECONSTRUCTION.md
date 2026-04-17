# Map System Reconstruction

Tài liệu khôi phục hệ thống bản đồ thế giới (world map / zone-select) từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [oh.java](/d:/Twelve/reference/redecoded/decompiled/oh.java) | `oh` | Map scene controller — loads `/m/m` (main sheet) and `/m/lock` (lock overlay) |
| [fz.java](/d:/Twelve/reference/redecoded/decompiled/fz.java) | `fz` | Map location sub-renderer — loads `/m/lock`, `/m/arena`, `/m/room` |
| [fg.java](/d:/Twelve/reference/redecoded/decompiled/fg.java) | `fg` | Secondary map overlay — loads `/m/lock2` |
| [hi.java](/d:/Twelve/reference/redecoded/decompiled/hi.java) | `hi` | Versus / battle-start splash — loads `/m/fsw` |
| [pc.java](/d:/Twelve/reference/redecoded/decompiled/pc.java) | `pc` | Shared UI sprites — loads `/m/hand`, `/m/arrow`, `/roomicon` |

## Quick Position

The world map lets the player pick a scene (arena, room, fsw, etc.) to enter. It sits between login and battle in the scene graph.

Stable rules:

- `Java old client = behavior/spec`
- `client/assets/map_legacy = working asset input for the new client`
- the main map tileset is `/m/m`. POIs are loaded individually and drawn at map coordinates.
- `/roomicon` is shared with the battle/room entry flow and lives at root (`f.d("/roomicon")`, not `/m/roomicon`).
- 9 literal-string-confirmed assets. 3 candidate assets (background painting + labels).

## Main Working Folder

- [client/assets/map_legacy](/d:/Twelve/client/assets/map_legacy)

## Map Scene Loader Contract

### Main sheet (`oh.java`)

```java
// oh.java line 53-55
this.l = f.d("/m/m");       // main overworld sheet
this.r = f.d("/m/lock");    // locked location overlay
```

### Sub-renderer (`fz.java`)

```java
// fz.java line 24-30
j = f.d("/m/lock");   // duplicate load (per-instance cache)
l = f.d("/m/arena");
k = f.d("/m/room");
```

### Secondary overlay (`fg.java`)

```java
// fg.java line 41-42
this.k  = f.d("/slotlock");
this.C  = f.d("/m/lock2");
```

### FSW splash (`hi.java`)

```java
// hi.java line 13
private Image a = f.d("/m/fsw");
```

### Shared UI cursors (`pc.java`)

```java
// pc.java line 20-21, 67
public static final Image e = f.d("/m/hand");
public static final Image f = f.d("/m/arrow");
r = f.d("/roomicon");
```

## Asset Roles

| Asset | Role |
|-------|------|
| `/m/m`            | Main overworld sheet. Probably the tiled ground layer drawn first. |
| `/m/arena`        | POI marker for the arena (combat location). |
| `/m/room`         | POI marker for a generic room / chamber. |
| `/m/fsw`          | POI marker loaded by the versus splash — likely the "Forest / FSW" zone. |
| `/m/lock`         | Overlay drawn on a locked POI. Primary variant. |
| `/m/lock2`        | Overlay drawn on a locked POI. Secondary variant (e.g., a higher lock tier). |
| `/m/hand`         | Cursor drawn on the currently-focused POI. |
| `/m/arrow`        | Arrow hint pointing to the next available POI. |
| `/roomicon`       | Room icon used as a badge in overlays and lists. |

## Candidate Assets

These are present in the jar cache but no literal string reference was found in the decompiled source. They likely reach the runtime through a dynamic callsite.

| Asset | Hypothesis |
|-------|------------|
| `bgmap_hoa_lu.png`  | 2.3 MB painted background of Hoa Lư capital. Likely drawn as the bottom layer before `/m/m` tiles. |
| `focusname.png`     | Name plate drawn near the focused POI. |
| `l.png`             | Tiny `l` glyph — possibly a level-indicator or legend marker. |

Do NOT promote these until a runtime trace or a decompiled dynamic-string callsite confirms their role.

## Port Order

1. Render `04_map_background_candidate/bgmap_hoa_lu.png` full-screen as the base layer (if the new client is oriented to use it).
2. Render `00_map_tileset/m.png` on top as the overworld sheet.
3. Draw POI markers from `01_location_markers/` at server-provided coordinates.
4. Draw `03_locked_markers/lock.png` or `lock2.png` over a POI when the server flags the zone as locked.
5. Add the focus cursor: `02_cursor_pointer/hand.png` on the selected POI, `arrow.png` as a "go here next" hint.
6. Wire tap → zone-select TLV command; on server ACK, transition to the battle / room scene.
7. Promote the three candidates (`bgmap_hoa_lu`, `focusname`, `l`) once their dynamic callsites are identified.

## Reference Skills

When implementing the map pipeline, consult these project skills (in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`     | Domain entity `WorldZone`, `MapLocation` in `Twelve.Core` |
| `binary-protocol/`  | TLV CMD 11 (MapInfo) + CMD 13 (SelectMap) + tags 55/56/57/60/61 |
| `database-design/`  | Postgres `MapCatalog`, zone unlocks per player |
| `game-mechanics/`   | Zone gating rules (lock / lock2 tiers) |
| `frontend-design/`  | Skia map renderer, touch pick-handling, 60 FPS overlay draws |
| `clean-code/`       | Naming `WorldMapRenderer`, `LocationMarker`, `ZoneGateService` |

## Next Practical Step

The next coding step should be a React Native + Skia `WorldMapScene` that:

- loads all 12 assets from `map_legacy/`
- draws the base map layer (candidate `bgmap_hoa_lu` OR confirmed `m/m`)
- places POI markers at server-supplied `(x, y)` coordinates from CMD 11 (MapInfo)
- overlays `lock` / `lock2` per-POI when the zone is locked
- handles touch to pick a POI and fires CMD 13 (SelectMap)
- on successful server ACK, transitions to the appropriate scene (battle / room / fsw)
