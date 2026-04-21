# UI System Reconstruction

Tài liệu khôi phục **hệ thống UI chrome dùng chung** — các mảnh khung panel, badge thông báo, tab, số đếm, emote, focus marker được chia sẻ cho mọi scene (login, world map, inventory, battle, chat, shop…). Tất cả được trích xuất từ client Java gốc.

## Source Code Reference

| File | Class | Vai trò |
|------|-------|---------|
| [ap.java](/d:/Twelve/reference/redecoded/decompiled/ap.java) | `ap` | Base renderer cho khung panel generic — load `/_corner` và vẽ 9-slice |
| [ig.java](/d:/Twelve/reference/redecoded/decompiled/ig.java) | `ig extends ap` | Biến thể khung panel dùng `/corner/1`, `/corner/3`, `/corner/cornerskb` (soft keyboard) |
| [fc.java](/d:/Twelve/reference/redecoded/decompiled/fc.java) | `fc` | Panel friend / skill list dùng `/corner/4`, `/corner/5`, quest/news badge, `/focustab` |
| [pc.java](/d:/Twelve/reference/redecoded/decompiled/pc.java) | `pc` | Static image cache — `/notifygtmicon`, `/tab`, `/corner/2`, `/olaicons` |
| [fj.java](/d:/Twelve/reference/redecoded/decompiled/fj.java) | `fj` | Inventory / shop panel — `/shoppingcarticon` |
| [fg.java](/d:/Twelve/reference/redecoded/decompiled/fg.java) | `fg` | Equipment grid — `/slotlock` cho slot bị khóa |
| [ly.java](/d:/Twelve/reference/redecoded/decompiled/ly.java) | `ly` | Tiny-number font renderer — load `/tinynumber` |
| [ic.java](/d:/Twelve/reference/redecoded/decompiled/ic.java) | `ic` | Action indicator — `/push` |
| [p.java](/d:/Twelve/reference/redecoded/decompiled/p.java) | `p` | Chat window — `/smileys` |
| [oy.java](/d:/Twelve/reference/redecoded/decompiled/oy.java) | `oy` | Chat emote pack bổ sung — `/onions` |
| [dc.java](/d:/Twelve/reference/redecoded/decompiled/dc.java) | `dc` | Defeat / life-lost marker — `/broken_heart` |
| [fo.java](/d:/Twelve/reference/redecoded/decompiled/fo.java) | `fo` | Me-commands menu — `/mecommands` |
| [ia.java](/d:/Twelve/reference/redecoded/decompiled/ia.java) | `ia` | Tay Thuy Tinh accessory screen — `/taythuytinh` |
| [mp.java](/d:/Twelve/reference/redecoded/decompiled/mp.java) | `mp` | Battle controller — load `/arrowfocus1` qua `f.b()` (byte-array, không phải Image) |

## Main Working Folder

- [client/assets/ui](/d:/Twelve/client/assets/ui)

## Loader Contract

### 1. Base panel frame (`ap.java`)

```java
// ap.java line 12
private Image a = f.d("/_corner");
```

Class `ap` vẽ khung panel generic bằng 9-slice từ `_corner.png`. Hàm `a(Graphics g, int x, int y, int w, int h)` (line 35) vẽ 4 góc qua `drawRegion` với cờ `transform` 0/1/2/3 (tương ứng identity / flip-V / flip-H / rotate-180), và fill phần thân bằng 3 màu lấy từ `v.aj`, `v.ak`, `v.al` (theme color).

### 2. Soft keyboard / chat input panel (`ig.java`)

```java
// ig.java line 13
private final Image b = f.d("/corner/3");

// ig.java constructor (line 17-20)
public ig() {
    a = f.d("/corner/cornerskb");   // static — soft keyboard wrap
    this.c = f.d("/corner/1");      // instance — panel body corners
}
```

`ig extends ap`, ghi đè hàm vẽ để dùng bộ corner `/corner/1` + `/corner/3` + `/corner/cornerskb`. Đây là panel dùng cho input field / chat / soft keyboard.

### 3. List panel with focus tab (`fc.java`)

```java
// fc.java
private Image H = f.d("/corner/4");       // line 38
private Image I = f.d("/corner/5");       // line 39
this.y = f.d("/questnotifyicon");          // line 46
this.s = f.d("/focustab");                 // line 49
this.z = f.d("/notificationnewsicon");     // line 244
```

Panel cho friend list / skill list / quest list. `focustab.png` là highlight strip trượt theo mục đang chọn.

### 4. Static icon cache (`pc.java`)

```java
// pc.java
public static Image c = f.d("/notifygtmicon");   // line 15 — GTM badge
private static Image j = f.d("/tab");            // line 16 — tab chrome
private static Image l = f.d("/corner/2");       // line 18 — corner variant 2
m = f.d("/olaicons");                            // line 236 — OLA icon sheet (lazy init)
```

Class `pc` là nơi cache các icon static dùng chung. Load ngay khi class được init (trừ `olaicons` là lazy).

### 5. Tiny-number font (`ly.java`)

```java
// ly.java line 34
c = f.d("/tinynumber");
```

Font số nhỏ (digits 0-9) dùng cho HP counter, quest count, item count, notification badge count. Dispatch qua glyph index = char - '0'.

### 6. Arrow focus (`mp.java`)

```java
// mp.java line 400
byte[] byArray = f.b("/arrowfocus1");
```

**Quan trọng:** `arrowfocus1` load bằng `f.b()` (byte-array) chứ không phải `f.d()` (Image). Đây là payload animation frames, được giải mã trong battle scene. `arrowfocus2` KHÔNG có string ref — là candidate (có thể là frame thứ 2 của cùng animation, hoặc fallback).

## Confirmed Assets

23 file có literal string reference trong source.

| File | `f.d()` / `f.b()` path | Java callsite |
|------|------------------------|---------------|
| `_corner.png`              | `/_corner`                | ap.java:12 |
| `corner/1.png`             | `/corner/1`               | ig.java:19 |
| `corner/2.png`             | `/corner/2`               | pc.java:18 |
| `corner/3.png`             | `/corner/3`               | ig.java:13 |
| `corner/4.png`             | `/corner/4`               | fc.java:38 |
| `corner/5.png`             | `/corner/5`               | fc.java:39 |
| `corner/cornerskb.png`     | `/corner/cornerskb`       | ig.java:18 |
| `notifygtmicon.png`        | `/notifygtmicon`          | pc.java:15 |
| `questnotifyicon.png`      | `/questnotifyicon`        | fc.java:46 |
| `notificationnewsicon.png` | `/notificationnewsicon`   | fc.java:244 |
| `shoppingcarticon.png`     | `/shoppingcarticon`       | fj.java:23 |
| `slotlock.png`             | `/slotlock`               | fg.java:41 |
| `tab.png`                  | `/tab`                    | pc.java:16 |
| `tinynumber.png`           | `/tinynumber`             | ly.java:34 |
| `push.png`                 | `/push`                   | ic.java:27 |
| `smileys.png`              | `/smileys`                | p.java:116 |
| `onions.png`               | `/onions`                 | oy.java:69 |
| `olaicons.png`             | `/olaicons`               | pc.java:236 |
| `broken_heart.png`         | `/broken_heart`           | dc.java:13 |
| `mecommands.png`           | `/mecommands`             | fo.java:22 |
| `taythuytinh.png`          | `/taythuytinh`            | ia.java:33 |
| `arrowfocus1.png`          | `/arrowfocus1`            | mp.java:400 (`f.b`) |
| `focustab.png`             | `/focustab`               | fc.java:49 |

## Candidate Assets

Không có literal string ref trong decompiled source. Giữ trong bundle để hoàn chỉnh, promote khi runtime trace xác nhận.

| File | Hypothesis |
|------|------------|
| `arrowfocus2.png` | Frame thứ 2 của arrow-focus animation — paired với `arrowfocus1`. Có thể load qua tên dynamic `"arrowfocus" + (frame+1)`. |

## Runtime Rules

- **9-slice panels**: Mọi panel generic kế thừa từ `ap.java` dùng `_corner.png` làm khung. Thứ tự vẽ: fill body → draw borders (3 màu theme từ `v.aj/ak/al`) → draw 4 corners qua `drawRegion` với flag transform 0/1/2/3.
- **Corner variants 1-5 + cornerskb** là các panel frame theme riêng. Không phải 9-slice của `_corner` — mà là frame hoàn chỉnh cho từng loại scene (list, soft keyboard, dialog).
- **tinynumber.png** là glyph sheet — giả định bố cục 1 hàng × 10 cột, width = sheetWidth / 10.
- **Notification badges** (`notifygtmicon`, `questnotifyicon`, `notificationnewsicon`) vẽ overlay góc HUD, không blink (không có animation frames rời trong source).
- **Focus markers** (`focustab`, `arrowfocus1`, `arrowfocus2`) di chuyển theo mục đang chọn; `focustab` là highlight strip, `arrowfocus*` là mũi tên trỏ.
- **Emotes** (`smileys`, `onions`) là spritesheet — glyph size cố định, chọn theo emote index do protocol chat trả về.
- **OLA icons** (`olaicons.png`) là sheet hiển thị trạng thái online / offline / busy / away — dispatch theo user status từ server.

## Port Order

1. Port `00_corner_frames/` thành `CornerFrame.tsx` — component React Native + Skia nhận `(x, y, w, h, variant: '_corner' | '1' | '2' | '3' | '4' | '5' | 'cornerskb')` và vẽ 9-slice.
2. Port `04_tabs_and_numbers/tinynumber.png` thành `TinyNumber.tsx` — nhận `(value: number, x: number, y: number)` và render digit-by-digit.
3. Port `01_notification_badges/` thành `NotificationBadge.tsx` — props `(type: 'gtm' | 'quest' | 'news')`.
4. Port `06_emotes/` thành `EmoteSheet.tsx` — hỗ trợ chat renderer mà chọn emote theo index.
5. Port `10_focus_confirmed/` thành `FocusMarker.tsx` — nhận target bbox, animate mỗi frame.
6. Mount `07_ola_icons/olaicons.png` vào `OnlineStatusIcon.tsx`.
7. Các icon lẻ (shoppingcarticon, slotlock, push, broken_heart, mecommands, taythuytinh) map thẳng vào file nguồn trong `<Icon />` component dùng chung.

## Reference Skills

| Skill | Use When |
|-------|----------|
| `frontend-design/`  | React Native + Skia 9-slice, Expo render loop, Zustand UI store |
| `clean-code/`       | Naming `CornerFrame`, `NotificationBadge`, `TinyNumber`, `EmoteSheet` |
| `game-mechanics/`   | Notification triggers từ TLV packet (quest update, news push) |
| `binary-protocol/`  | Tag IDs cho chat/emote payload, GTM notification, news feed |

## Next Practical Step

Viết `UIChromeKit` package:

- `<CornerFrame variant x y w h>` — tự load đúng frame từ `00_corner_frames/` theo variant
- `<TinyNumber value x y>` — dùng `04_tabs_and_numbers/tinynumber.png`
- `<NotificationBadge type count>` — combo icon + tinynumber overlay
- `<EmoteGlyph pack="smileys" | "onions" index />` — chat renderer
- `<FocusMarker target>` — chuyển giữa `focustab` (strip) và `arrowfocus*` (pointer) theo context

Tất cả component phải nhận theme color từ Zustand theme store (tương đương `v.aj/ak/al` của bản Java) để giữ khả năng reskin về sau.
