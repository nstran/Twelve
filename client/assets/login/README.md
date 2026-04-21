# login

Legacy login / boot / download-screen asset bundle. Everything the player sees from app-start up until the first scene (world map or title).

## Layout

| Folder | Purpose | Files | Confidence |
|--------|---------|-------|------------|
| `00_login_confirmed/`           | Main login screen background + login button icon | 2 | confirmed |
| `01_download_confirmed/`        | Progress-bar frame + fill + avatar slot for download screen | 3 | confirmed |
| `02_logo_candidate/`            | Publisher + partner logos, splash icon | 3 | candidate |
| `03_sound_toggle_candidate/`    | Sound-on / sound-off toggle icons shown on intro | 2 | candidate |
| `04_font_candidate/`            | Bitmap font glyph sheets (black + cap) | 2 | candidate |
| `05_expo_manifest_icons/`       | Expo app-manifest icons — NOT in-game assets | 3 | manifest-only |

Total: 15 files.

## Confirmed paths

| File | `f.d()` path | Java callsite |
|------|--------------|---------------|
| 00_login_confirmed/bklogin.png       | `/bklogin`                  | ob.java:32 |
| 00_login_confirmed/iconbt.png        | `/iconbt`                   | com/mg/sq/a.java:2171 |
| 01_download_confirmed/bardownloadscreen.png     | `/bardownloadscreen`     | nx.java:81 |
| 01_download_confirmed/fillbardownloadscreen.png | `/fillbardownloadscreen` | nx.java:84 |
| 01_download_confirmed/avatardownloadscreen.png  | `/offline/avatardownloadscreen` | nx.java:78, oc.java:25 |

## Candidate paths

Not found as literal `f.d("...")` strings in the decompiled source. Likely loaded via a dynamic name or from a separate resource loader.

| File | Hypothesis |
|------|------------|
| 02_logo_candidate/_mglogo.png       | Publisher logo (MG = Mobile Game) shown during splash |
| 02_logo_candidate/_partnerLogo.png  | Partner / distributor logo shown alongside |
| 02_logo_candidate/splash-icon.png   | Splash-screen icon (likely Expo manifest) |
| 03_sound_toggle_candidate/_sound1.png | Sound-on icon on intro |
| 03_sound_toggle_candidate/_sound2.png | Sound-off icon on intro |
| 04_font_candidate/_blackfont.png    | Black bitmap font glyph sheet |
| 04_font_candidate/_fontcap.png      | Cap-letter bitmap font glyph sheet |

## Expo manifest icons

The three files in `05_expo_manifest_icons/` are NOT J2ME assets. They are Expo / React Native app-manifest icons used by the native launcher. Kept here for folder completeness, but they should be wired up via `app.json`, not via the in-game asset loader.

| File | Purpose |
|------|---------|
| adaptive-icon.png | Android adaptive launcher icon |
| favicon.png       | Web favicon |
| icon.png          | iOS launcher icon |
