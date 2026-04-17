# audio_legacy

Legacy audio bundle extracted from the J2ME jar `/audio/` resource folder.

Two media kinds:

- `.mid` — background music, played by `co.java` (singleton `co.b()`).
- `.amr` — sound effects, played by `cp.java` (instance `new cp(name)`).

Both are loaded via `Manager.createPlayer(getResourceAsStream("/audio/" + name + ".ext"), ...)`.

## Layout

| Folder | Type | Java call shape | Files |
|--------|------|-----------------|-------|
| `00_music_confirmed/`  | `.mid` | `co.b().a("<name>", loopCount)` | 3 |
| `01_sfx_confirmed/`    | `.amr` | `new cp("<name>")`              | 3 |
| `02_music_candidate/`  | `.mid` | — (no literal string ref found) | 2 |
| `03_sfx_candidate/`    | `.amr` | — (no literal string ref found) | 1 |

Total: 9 files (5 music + 4 sfx).

## Confidence

**Confirmed** = the asset name appears as a Java string literal passed to `co.b().a(...)` or `new cp(...)`.
**Candidate** = the file is shipped in the jar but the callsite uses a variable name (dynamic string), so the literal reference is not visible in the decompiled source.

See `index.csv` for per-file confidence + the exact callsite reference.
