# ui_legacy

Shared UI chrome asset bundle — the pieces used by the generic panel / HUD / notification layer across every screen. Every asset here is referenced by a literal `f.d("/name")` (or `f.b(...)`) call in the decompiled Java source unless marked candidate.

## Layout

| Folder | Purpose | Files | Confidence |
|--------|---------|-------|------------|
| `00_corner_frames/`         | 9-slice corner frames used by dialogs / panels / soft keyboard | 7 | confirmed |
| `01_notification_badges/`   | Notification icons (GTM, quest, news) shown on HUD corner | 3 | confirmed |
| `02_shop_and_cart/`         | Shop / cart icon used in inventory & shop screens | 1 | confirmed |
| `03_inventory_slot/`        | Locked inventory slot overlay | 1 | confirmed |
| `04_tabs_and_numbers/`      | Tab chrome + tiny-number font sheet | 2 | confirmed |
| `05_action_indicator/`      | Push / tap action indicator | 1 | confirmed |
| `06_emotes/`                | Chat emote sheets (smileys, onions) | 2 | confirmed |
| `07_ola_icons/`             | OLA (online activity) icon sheet | 1 | confirmed |
| `08_misc_confirmed/`        | Broken-heart, me-commands, Tay Thuy Tinh | 3 | confirmed |
| `09_focus_candidate/`       | arrowfocus2 — no literal string ref (paired with arrowfocus1) | 1 | candidate |
| `10_focus_confirmed/`       | arrowfocus1 + focustab — literal string refs in source | 2 | confirmed |
| `11_softkey_icons_confirmed/` | Softkey login/menu icon set carried from the restored RN client | 5 | confirmed |

Total: 29 files. 28 confirmed + 1 candidate.

## Confirmed paths

| File | `f.d()` / `f.b()` path | Java callsite |
|------|------------------------|---------------|
| 00_corner_frames/_corner.png          | `/_corner`              | ap.java:12 |
| 00_corner_frames/1.png                | `/corner/1`             | ig.java:19 |
| 00_corner_frames/2.png                | `/corner/2`             | pc.java:18 |
| 00_corner_frames/3.png                | `/corner/3`             | ig.java:13 |
| 00_corner_frames/4.png                | `/corner/4`             | fc.java:38 |
| 00_corner_frames/5.png                | `/corner/5`             | fc.java:39 |
| 00_corner_frames/cornerskb.png        | `/corner/cornerskb`     | ig.java:18 |
| 01_notification_badges/notifygtmicon.png       | `/notifygtmicon`         | pc.java:15 |
| 01_notification_badges/questnotifyicon.png     | `/questnotifyicon`       | fc.java:46 |
| 01_notification_badges/notificationnewsicon.png| `/notificationnewsicon`  | fc.java:244 |
| 02_shop_and_cart/shoppingcarticon.png  | `/shoppingcarticon`     | fj.java:23 |
| 03_inventory_slot/slotlock.png        | `/slotlock`             | fg.java:41 |
| 04_tabs_and_numbers/tab.png           | `/tab`                  | pc.java:16 |
| 04_tabs_and_numbers/tinynumber.png    | `/tinynumber`           | ly.java:34 |
| 05_action_indicator/push.png          | `/push`                 | ic.java:27 |
| 06_emotes/smileys.png                 | `/smileys`              | p.java:116 |
| 06_emotes/onions.png                  | `/onions`               | oy.java:69 |
| 07_ola_icons/olaicons.png             | `/olaicons`             | pc.java:236 |
| 08_misc_confirmed/broken_heart.png    | `/broken_heart`         | dc.java:13 |
| 08_misc_confirmed/mecommands.png      | `/mecommands`           | fo.java:22 |
| 08_misc_confirmed/taythuytinh.png     | `/taythuytinh`          | ia.java:33 |
| 10_focus_confirmed/arrowfocus1.png    | `/arrowfocus1`          | mp.java:400 (`f.b`) |
| 10_focus_confirmed/focustab.png       | `/focustab`             | fc.java:49 |
| 11_softkey_icons_confirmed/icon_cancel.png    | `softkey/icon_cancel`   | restored React Native login softkey |
| 11_softkey_icons_confirmed/icon_fixed_2.png   | `softkey/icon_fixed_2`  | restored React Native softkey icon pack |
| 11_softkey_icons_confirmed/icon_ok.png        | `softkey/icon_ok`       | restored React Native login softkey |
| 11_softkey_icons_confirmed/icon_sharpest_1.png| `softkey/icon_sharpest_1` | restored React Native login menu icon |
| 11_softkey_icons_confirmed/skicon.png         | `softkey/skicon`        | restored React Native softkey sprite sheet |

## Candidate paths

No literal string reference in the decompiled source. Likely loaded via a dynamic name or used only by a code path the CFR decompiler did not unroll.

| File | Hypothesis |
|------|------------|
| 09_focus_candidate/arrowfocus2.png | Paired with `arrowfocus1`; second frame of the arrow focus animation |
