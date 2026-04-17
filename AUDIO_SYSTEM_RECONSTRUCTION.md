# Audio System Reconstruction

Tài liệu khôi phục hệ thống âm thanh (music + sfx) từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [co.java](/d:/Twelve/reference/redecoded/decompiled/co.java) | `co` | Background music player — singleton `co.b()`, loads `/audio/<name>.mid` |
| [cp.java](/d:/Twelve/reference/redecoded/decompiled/cp.java) | `cp` | Sound effect player — instance `new cp("name")`, loads `/audio/<name>.amr` |
| [cs.java](/d:/Twelve/reference/redecoded/decompiled/cs.java) | `cs` | Audio error flag holder (`cs.a(boolean)`) |
| [v.java](/d:/Twelve/reference/redecoded/decompiled/v.java) | `v` | Global audio config: `v.N` (sound enabled), `v.O` (sfx enabled), `v.Q` (volume level), `v.R` (tempo), `v.S` (sfx allowed) |

## Quick Position

The legacy audio system is tiny — 2 Java classes, 9 media files, 2 formats.

Stable rules:

- `Java old client = behavior/spec`
- `client/assets/audio_legacy = working audio input for the new client`
- Music = `.mid`, played via `co.b().a(name, loopCount)`. `loopCount = -1` = infinite loop, positive = play N times.
- Sfx = `.amr`, played via `new cp(name)`. Always plays once.
- Volume level is clamped via `javax.microedition.media.control.VolumeControl` to `v.Q`.

## Main Working Folder

- [client/assets/audio_legacy](/d:/Twelve/client/assets/audio_legacy)

## Music Runtime Contract (`co.java`)

Singleton with state-held player:

```java
// co.java
public final class co implements PlayerListener, r {
    private static co a;
    private Player b;
    private String c = "";      // current track name
    private int    g;           // current loop count

    public static co b() {      // singleton accessor
        if (a == null) a = new co();
        return a;
    }

    // Start / switch background music.
    public final void a(String string, int n2) { ... }

    // Stop current music.
    public final void c() { ... }

    // Destroy player.
    public final void d() { ... }

    // Resume / restart from current state.
    public final void a() { ... }
}
```

The inner loader at [co.java:63-86](/d:/Twelve/reference/redecoded/decompiled/co.java:63):

```java
this.b = Manager.createPlayer(
    "".getClass().getResourceAsStream("/audio/" + name + ".mid"),
    "audio/midi");
this.b.setLoopCount(loopCount);
```

Volume is pulled from `v.Q` and mute toggles with `!v.N`.

## Sfx Runtime Contract (`cp.java`)

Instance-based. Each Sfx owner creates its own `cp`:

```java
// cp.java
public final class cp implements PlayerListener {
    private Player a;
    private String c = "";

    public cp() {}
    public cp(String string) { this.a(string); }

    private void a(String string) {
        if (!v.O || !v.S) return;
        this.a = Manager.createPlayer(
            "".getClass().getResourceAsStream("/audio/" + string + ".amr"),
            "audio/amr");
        this.a.setLoopCount(1);  // sfx always plays once
        ...
    }

    public final void a() { ... }  // stop / deallocate
}
```

## Confirmed Tracks

All 6 files below are referenced by literal string in the decompiled source.

| Name  | Kind  | Java Callsite | Use |
|-------|-------|---------------|-----|
| `battle`       | music (.mid) | [com/mg/sq/a.java:102](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:102) — `co.b().a("battle", -1)` | In-battle background, infinite loop |
| `title`        | music (.mid) | [com/mg/sq/a.java:108](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:108) — `co.b().a("title", 1)`  | Title / splash screen, plays once |
| `lv1`          | music (.mid) | [ob.java:292](/d:/Twelve/reference/redecoded/decompiled/ob.java:292), [nw.java:294](/d:/Twelve/reference/redecoded/decompiled/nw.java:294) — `co.b().a("lv1", -1)` | Default / world / create-character background |
| `useitem`      | sfx (.amr)   | [com/mg/sq/a.java:985](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java:985) — `new cp("useitem")` | Consumable-item activation sound |
| `vs`           | sfx (.amr)   | [hi.java:27](/d:/Twelve/reference/redecoded/decompiled/hi.java:27) — `new cp("vs")` | Versus splash / battle-start bump |
| `lvu`          | sfx (.amr)   | [hs.java:104](/d:/Twelve/reference/redecoded/decompiled/hs.java:104) — `new cp("lvu")` | Level-up notification |

## Candidate Tracks

Present in the jar, but no literal string reference found in the decompiled source. Most likely called via a variable name (scene-pick string), so the callsite is visible but the string isn't.

| Name            | Kind         | Likely Use |
|-----------------|--------------|------------|
| `charcreation`  | music (.mid) | Character-creation scene BGM |
| `worldmap`      | music (.mid) | World-map / zone-select BGM |
| `attack`        | sfx (.amr)   | Generic attack hit sfx |

Do not rename these to final labels until a runtime trace confirms who plays them.

## Volume / Mute State

Held on static config class `v.java`:

| Field | Purpose |
|-------|---------|
| `v.N` | Global sound enabled flag — gates music start |
| `v.O` | Sfx enabled flag — gates `cp.a(name)` |
| `v.S` | Sfx per-scene permission flag |
| `v.Q` | Current volume level (0..100 clamp) |
| `v.R` | Last-read tempo from `TempoControl` |

`co.g()` re-applies `v.Q` / `v.N` to the live player.

## Port Order

1. Port the singleton `co` → React Native equivalent: one background `Audio.Sound` instance, with `playBackgroundAsync(name, loopCount)` and `stopBackgroundAsync()`.
2. Port the per-call `cp` → a `playSfxAsync(name)` helper; each call creates a throwaway `Audio.Sound` since sfx are short.
3. Wire the three confirmed music names (`battle`, `title`, `lv1`) into their scene start hooks.
4. Wire the three confirmed sfx names (`useitem`, `vs`, `lvu`) at the exact callsite equivalents.
5. Promote the three candidate tracks once the scene that plays them is identified during playthrough testing.

## Reference Skills

When implementing the audio pipeline, consult these project skills (in `.agent/skills/`):

| Skill | Use When |
|-------|----------|
| `architecture/`     | Domain entity `AudioTrack` in `Twelve.Core`, scene-to-track bindings |
| `frontend-design/`  | React Native `expo-av` / `react-native-sound` bridge, volume slider |
| `clean-code/`       | Naming `BackgroundMusicService`, `SoundEffectService` |
| `game-mechanics/`   | Battle-start `vs` sfx, level-up `lvu` sfx, item-use `useitem` sfx |

## Next Practical Step

The next coding step should be a single `AudioService` in the React Native client that:

- exposes `playBgm(name, loop)`, `stopBgm()`, `playSfx(name)`
- reads audio config flags from a Zustand store mirroring `v.N / v.O / v.S / v.Q`
- loads files from `audio_legacy/00_music_confirmed/` and `audio_legacy/01_sfx_confirmed/` first
- falls back gracefully if a candidate track is requested and not yet confirmed
