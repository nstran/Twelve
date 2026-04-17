# Login System Reconstruction

Tài liệu khôi phục hệ thống đăng nhập / màn hình khởi động / màn hình tải từ Java client cũ.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [ob.java](/d:/Twelve/reference/redecoded/decompiled/ob.java) | `ob` | Login scene — loads `/bklogin` as background |
| [nx.java](/d:/Twelve/reference/redecoded/decompiled/nx.java) | `nx` | Download / loading screen — loads bar + fillbar + avatar slot |
| [oc.java](/d:/Twelve/reference/redecoded/decompiled/oc.java) | `oc` | Secondary download handler — also loads `/offline/avatardownloadscreen` |
| [com/mg/sq/a.java](/d:/Twelve/reference/redecoded/decompiled/com/mg/sq/a.java) | `a` | Root app class — loads `/iconbt` (login button icon) at line 2171 |

## Quick Position

The login / boot flow is:

1. Splash (logos) → not string-referenced; likely hard-coded in `MGMIDlet.startApp()` or similar.
2. Download screen (`nx.java`) — shows a progress bar while the jar caches `/offline/` assets.
3. Login screen (`ob.java`) — `/bklogin` background + `/iconbt` button to continue.
4. Post-login → title or world map.

Stable rules:

- `Java old client = behavior/spec`
- `client/assets/login_legacy = working asset input for the new client`
- `/bklogin` is the ONLY background explicitly loaded for login. No separate "welcome" background exists.
- `avatardownloadscreen` is stored under `/offline/` (not root), yet its semantic role is login-adjacent — include it in this bundle anyway.

## Main Working Folder

- [client/assets/login_legacy](/d:/Twelve/client/assets/login_legacy)

## Loader Contract

### Login scene (`ob.java`)

```java
// ob.java line 32
this.b = f.d("/bklogin");
```

After load, the scene also triggers background music:

```java
// ob.java line 292
co.b().a("lv1", -1);
```

### Download screen (`nx.java`)

```java
// nx.java line 78-84
this.d = f.d("/offline/avatardownloadscreen");  // avatar slot
this.k = f.d("/bardownloadscreen");              // bar frame
this.l = f.d("/fillbardownloadscreen");          // bar fill (clipped by progress)
```

### Secondary download (`oc.java`)

```java
// oc.java line 25
oc2.o = f.d("/offline/avatardownloadscreen");
```

### Button icon (`com/mg/sq/a.java`)

```java
// com/mg/sq/a.java line 2171
Image image = f.d("/iconbt");
```

## Confirmed Assets

| File | `f.d()` path | Java callsite |
|------|--------------|---------------|
| `bklogin.png`                  | `/bklogin`                       | ob.java:32 |
| `iconbt.png`                   | `/iconbt`                        | com/mg/sq/a.java:2171 |
| `bardownloadscreen.png`        | `/bardownloadscreen`             | nx.java:81 |
| `fillbardownloadscreen.png`    | `/fillbardownloadscreen`         | nx.java:84 |
| `avatardownloadscreen.png`     | `/offline/avatardownloadscreen`  | nx.java:78 + oc.java:25 |

## Candidate Assets

No literal string reference in the decompiled source. Likely loaded via a dynamic name (`"_mglogo"`, `"_sound1"`, ...) built at runtime, or consumed only at the native / MIDlet bootstrap layer.

| File | Hypothesis |
|------|------------|
| `_mglogo.png`        | Publisher logo on splash |
| `_partnerLogo.png`   | Partner / distributor logo on splash |
| `splash-icon.png`    | Splash center icon |
| `_sound1.png`        | Sound-on toggle icon on intro |
| `_sound2.png`        | Sound-off toggle icon on intro |
| `_blackfont.png`     | Bitmap font — black variant |
| `_fontcap.png`       | Bitmap font — capital letters |

## Expo Manifest Icons (Not In-Game Assets)

These are React Native / Expo launcher icons, NOT J2ME assets. Kept in `05_expo_manifest_icons/` only for folder completeness. They should be wired via `app.json` → `expo.icon`, not via the in-game asset loader.

| File | Purpose |
|------|---------|
| `adaptive-icon.png` | Android adaptive launcher icon |
| `favicon.png`       | Web favicon |
| `icon.png`          | iOS launcher icon |

## Runtime Rules

- The login background `/bklogin` is drawn fullscreen; `iconbt` is drawn centered-bottom as the tap target.
- The download screen composes three layers: `bardownloadscreen` (frame), `fillbardownloadscreen` (clipped fill tied to download progress 0..100%), `avatardownloadscreen` (avatar slot drawn next to the bar).
- Background music `lv1.mid` starts with the login scene and continues into the world map.

## Port Order

1. Port `02_logo_candidate/` as a 3-second splash that transitions to the download screen.
2. Port `01_download_confirmed/` — draw frame + clipped fill based on a 0..1 `progress` value; draw avatar slot to the left.
3. Port `00_login_confirmed/` — draw `bklogin` fullscreen, `iconbt` centered-bottom. Fire a login TLV command (CMD 1) on tap.
4. Start `lv1` music on login scene mount (uses `audio_legacy/00_music_confirmed/lv1.mid`).
5. Promote the 7 candidate assets once a runtime trace reveals where they are used.

## Reference Skills

| Skill | Use When |
|-------|----------|
| `architecture/`     | Domain entity `LoginRequest` / `Session` in `Twelve.Core` |
| `binary-protocol/`  | TLV CMD 1 (Login), response tags for session token |
| `frontend-design/`  | React Native boot flow, Expo splash, progress bar animation |
| `clean-code/`       | Naming `LoginScene`, `DownloadScreen`, `SessionService` |

## Next Practical Step

The next coding step should be a React Native `LoginFlowController` that:

- shows `02_logo_candidate/` for ~3s
- mounts `DownloadScreen` using `01_download_confirmed/` + a real progress value from the asset preloader
- mounts `LoginScene` using `00_login_confirmed/` + plays `audio_legacy/00_music_confirmed/lv1.mid`
- fires TLV CMD 1 on `iconbt` tap
- on server ACK, routes to the next scene (world map or title)
