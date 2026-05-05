# PvP UI Java → JSX Audit (100% Pixel-Accurate)

**Date:** 2026-05-05  
**Purpose:** Line-by-line Graphics operation mapping from Java to React Native JSX

---

## 1. Container Rendering (`os.java` lines 104-130)

### Java Graphics Operations

```java
// os.java line 108-109: Background fill
graphics.setColor(v.am);  // v.am = 0xF0FBFF (light blue)
graphics.fillRect(0, 0, ((aq)object).e(), ((aq)object).f());
// Maps to: Full container background

// Line 110: Bottom decoration image
graphics.drawImage(pc.d, ((aq)object).c() + ((aq)object).e(), 
                   ((aq)object).d() + ((aq)object).f() - ba.a, 40);
// pc.d = "/hiddendragon" image
// Position: bottom-right corner, anchor 40 (bottom-right)
// ba.a = bottom action bar height

// Line 116: Horizontal separator line
pc.a(graphics, ((os)object).n.a, ((os)object).n.b, ((os)object).n.c);
// n = k(0, 5, v.t, 1) for mode 0 (arena list)
// n = k(0, 73, v.t, 1) for mode 1 (challenge)
// Draws 1px horizontal line at y=5 or y=73

// Line 117-120: Timer display (challenge mode only)
if (((os)object).z != null) {
    long l2 = ((os)object).A - System.currentTimeMillis() > 0L 
              ? ((os)object).A - System.currentTimeMillis() : 0L;
    n3 = v.t - bx.c.a(((os)object).z) - bx.c.a(i.b(l2, "hh:mm:ss")) - 5;
    com.mg.sq.a.h.a(graphics, String.valueOf(((os)object).z) + " " 
                    + i.b(l2, "hh:mm:ss"), n3, v.u - 35, 0);
}
// Timer text at bottom-right, 35px from bottom

// Line 123-127: Scroll container rendering
if (((os)object).p != null) {
    cw.a(graphics);  // Save clip
    cw.b(graphics, ((os)object).p.h());  // Set clip to scroll bounds
    ((os)object).p.a(graphics, ((aq)object).c(), ((aq)object).d());
    cw.b(graphics);  // Restore clip
}
```

### Current JSX (PvpDialog.tsx)

```tsx
// Line 99-100: Backdrop
<Pressable style={styles.backdrop} onPress={isBusy ? undefined : onClose} />

// Line 101-105: Frame with corners
<PvpCornerFrame
  style={styles.frame}
  showCorners={true}
  cornerAsset={require('../../../../assets/ui/00_corner_frames/_corner.png')}
>

// Line 106-111: Header with title and refresh
<View style={styles.header}>
  <Text style={styles.title}>{mode === 'arena' ? 'Lôi Đài' : 'Khiêu Chiến'}</Text>
  <TouchableOpacity activeOpacity={0.85} onPress={onRefresh} disabled={isBusy}>
    <Text style={[styles.headerAction, isBusy && styles.actionDisabled]}>Cập nhật</Text>
  </TouchableOpacity>
</View>

// Line 151-179: Arena board with ScrollView
{mode === 'arena' ? (
  <View style={styles.arenaBoard}>
    <ScrollView style={styles.list} contentContainerStyle={styles.listContent}>
      {opponents.map((opponent) => { /* rows */ })}
    </ScrollView>
  </View>
) : null}
```

### Gaps Identified

1. **Background color**: JSX uses frame background, Java explicitly fills with `v.am = #f0fbff`
2. **Bottom decoration**: Java has `/hiddendragon` image at bottom-right, JSX missing
3. **Horizontal separator**: Java draws 1px line at y=5 (arena) or y=73 (challenge), JSX missing
4. **Timer display**: Java shows timer at bottom-right in challenge mode, JSX missing
5. **Header position**: JSX has header at top, Java separator suggests different layout

---

## 2. Row Rendering (`ew.java` lines 58-84)

### Java Graphics Operations

```java
// Line 60-61: Base position adjustment
n3 += this.d();  // Add component y offset
n2 += this.c() + 2;  // Add component x offset + 2px

// Line 62-67: Selection highlight background
if (this.g) {  // Selected state
    pc.e(graphics, n2 - 2, n3, this.e(), this.f());
    // pc.e draws focus frame with corners
    n3 += 7;  // Selected: +7px vertical offset
} else {
    n3 += 3;  // Normal: +3px vertical offset
}

// Line 68-71: Text clipping for marquee (if text overflows)
if (bl2 = this.g && this.l > this.e() - 25 - 2) {
    cw.a(graphics);  // Save clip
    cw.a(graphics, n2, n3, this.e(), this.f());  // Set clip rect
}

// Line 72-73: Primary text (name + status message)
int n4 = n2 + 25;  // 25px left margin
this.o.a(graphics, this.j, n4 + (this.g ? this.m : 0), n3, 0);
// this.j = username + " - " + statusMessage (if not empty)
// this.m = marquee scroll offset (animated when selected)
// this.o = bx.d font

// Line 74-76: Restore clip if was clipping
if (bl2) {
    cw.b(graphics);
}

// Line 77: Status icon
pc.a(graphics, n2, n3, this.i.c);
// Draws icon based on status byte (0=ready, 1=battle, 2=arena, 3=recovering)
// Position: n2 (left edge), n3 (current y)

// Line 78: Secondary text (level + honor)
this.p.a(graphics, this.k, n4, n3 + 13, 0);
// this.k = "Cấp X  --  Danh vọng Y"
// Position: same x as name, +13px below
// this.p = com.mg.sq.a.g font

// Line 79-82: Timer text (if present)
if (this.q != null) {
    long l2 = this.r - System.currentTimeMillis() > 0L 
              ? this.r - System.currentTimeMillis() : 0L;
    n2 = v.t - bx.c.a(this.q) - bx.c.a(i.b(l2, "hh:mm:ss")) - 5;
    com.mg.sq.a.h.a(graphics, String.valueOf(this.q) + " " 
                    + i.b(l2, "hh:mm:ss"), n2, v.u - 35, 0);
}
```

### Java Marquee Animation (`ew.java` lines 86-104)

```java
public final void n() {  // Update/tick method
    int n2 = this.e() - 25 - 2;  // Available width for text
    if (this.g && this.l > n2) {  // Selected AND text overflows
        if (this.n) {  // Scrolling left
            --this.m;  // Decrease offset (move text left)
            if (this.m < (n2 -= this.l)) {  // Reached left limit
                this.m = n2;
                this.n = false;  // Reverse direction
            }
        } else {  // Scrolling right
            ++this.m;  // Increase offset (move text right)
            if (this.m > 0) {  // Reached right limit
                this.m = 0;
                this.n = true;  // Reverse direction
            }
        }
        this.c = true;  // Mark dirty for redraw
    }
}
```

### Current JSX (PvpDialog.tsx lines 154-178)

```tsx
{opponents.map((opponent) => {
  const selected = opponent.username.toLowerCase() === trimmedTarget.toLowerCase();
  return (
    <TouchableOpacity
      key={opponent.username}
      activeOpacity={0.86}
      style={[styles.legacyRow, selected && styles.legacyRowActive]}
      onPress={() => onSelectTarget(opponent.username)}
      disabled={isBusy}
    >
      <View style={[styles.legacyBadge, selected ? styles.legacyBadgeActive : undefined]}>
        <PvpStatusBadge status={opponent.currentHp > 0 ? 'alive' : 'dead'} />
      </View>
      <View style={[styles.legacyTextWrap, selected ? styles.legacyTextWrapActive : undefined]}>
        <Text style={styles.legacyName} numberOfLines={1}>
          {opponent.statusMessage ? `${opponent.username} - ${opponent.statusMessage}` : opponent.username}
        </Text>
        <Text style={styles.legacyMeta} numberOfLines={1}>
          {formatPvpHonorLine(opponent.level, opponent.honor)}
        </Text>
      </View>
      <Text style={styles.legacyStake}>{opponent.stake > 0 ? 'Đánh!' : 'K.Chiến'}</Text>
    </TouchableOpacity>
  );
})}
```

### Current Styles (PvpDialog.styles.ts lines 66-115)

```typescript
legacyRow: {
  minHeight: 32,
  borderBottomWidth: 1,
  borderBottomColor: '#b9c7df',
  backgroundColor: '#f0fbff',
  flexDirection: 'row',
  alignItems: 'center',
  paddingHorizontal: 8,
  paddingVertical: 3,
},
legacyRowActive: {
  minHeight: 42,
  backgroundColor: '#6ef0ef',
  borderTopWidth: 2,
  borderTopColor: '#20a5de',
  borderBottomColor: '#20a5de',
  paddingVertical: 7,
},
legacyBadge: {
  width: 28,
  alignItems: 'center',
  justifyContent: 'center',
},
legacyTextWrap: {
  flex: 1,
  minWidth: 0,
  marginLeft: 2,
},
legacyTextWrapActive: {
  marginLeft: 4,
},
legacyName: {
  color: '#1d2f59',
  ...PvpFontStyles.arenaPrimary,
},
legacyMeta: {
  color: '#2b5ec4',
  ...PvpFontStyles.arenaSecondary,
  marginTop: 1,
},
legacyStake: {
  width: 34,
  color: '#486ea8',
  fontSize: 11,
  fontWeight: '700',
  textAlign: 'right',
},
```

### Gaps Identified

1. **Horizontal offset**: Java uses `n2 + 2` (2px left padding), JSX uses `paddingHorizontal: 8`
2. **Vertical offset**: Java uses `+3px` normal / `+7px` selected, JSX uses `paddingVertical: 3/7` (correct!)
3. **Text left margin**: Java uses `n2 + 25` (25px from left edge), JSX badge is 28px but text has `marginLeft: 2/4`
4. **Selection background**: Java calls `pc.e()` which draws focus frame with corners, JSX uses solid `#6ef0ef`
5. **Text clipping**: Java implements clip rect for marquee, JSX uses `numberOfLines={1}` (ellipsis, not scroll)
6. **Marquee animation**: Java has `this.m` offset that animates, JSX has no animation
7. **Status icon position**: Java draws at `n2, n3` (left edge), JSX badge is separate View
8. **Secondary text offset**: Java uses `n3 + 13` (13px below), JSX uses `marginTop: 1` (incorrect!)
9. **Right label**: Java doesn't show stake in row (only in context menu), JSX shows "Đánh!" / "K.Chiến"

---

## 3. Icon Rendering (`fz.java` lines 41-68)

### Java Graphics Operations

```java
// Line 45-46: Base position adjustment
n3 += this.d();  // Add component y offset
n2 += this.c() + 2;  // Add component x offset + 2px

// Line 47-48: Font setup
d d2 = bx.d;
d2.c(true);  // Enable bold

// Line 49-51: Selection highlight
if (this.g) {
    pc.e(graphics, n2 - 2, n3, this.e(), this.f());
    // Same focus frame as ew.java
}

// Line 52-54: Status message (if present)
if (this.i.e != null) {
    bx.a(graphics, com.mg.sq.a.g, this.i.e, n2 + 50, n3 + 6 + d2.a(), 
          this.e(), this.f(), 0);
    // Wrapped text at x=50, y=6+fontHeight
}

// Line 55-56: Room name
d2.a(graphics, this.i.c, n2 + 50, n3 + 4, 0);
// this.i.c = room name
// Position: x=50, y=4

// Line 57-63: Room type icon
if (this.i.g == 2) {  // Arena room
    if (l != null) {
        graphics.drawImage(l, n2 + 3, n3 + 3, 0);
        // l = "/m/arena" image at x=3, y=3
    }
} else {  // Normal room
    if (k != null) {
        graphics.drawImage(k, n2 + 3, n3 + 3, 0);
        // k = "/m/room" image at x=3, y=3
    }
}

// Line 64-66: Lock icon (if room is locked)
if (this.i.a() && j != null) {
    graphics.drawImage(j, n2 + v.t - j.getWidth() - 4, n3 + 6, 0);
    // j = "/m/lock" image at right edge, y=6
}
```

### Current JSX

**Not implemented** - PvpDialog only shows player list (ew.java), not room list (fz.java)

### Gaps Identified

1. **Room list mode**: Java has separate room list view (fz.java), JSX doesn't implement this
2. **Room icons**: Java loads `/m/arena` and `/m/room` images, JSX missing
3. **Lock icon**: Java shows lock icon for password-protected rooms, JSX missing

---

## 4. Focus Frame Rendering (`pc.e` in pc.java lines 185-192)

### Java Graphics Operations

```java
public static void e(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
    int n7 = i.getWidth() - 7;  // i = "/focusmovechess1" image
    int n8 = i.getHeight() - 7;
    
    // Top-left corner
    cw.a(graphics, i, 0, 0, 7, 7, n2 + n6, n3 + n6, 20);
    
    // Top-right corner
    cw.a(graphics, i, n7, 0, 7, 7, n2 + n4 - n6, n3 + n6, 24);
    
    // Bottom-left corner
    cw.a(graphics, i, 0, n8, 7, 7, n2 + n6, n3 + n5 - n6, 36);
    
    // Bottom-right corner
    cw.a(graphics, i, n7, n8, 7, 7, n2 + n4 - n6, n3 + n5 - n6, 40);
}
```

### Current JSX

```tsx
// PvpDialog.styles.ts line 76-83
legacyRowActive: {
  minHeight: 42,
  backgroundColor: '#6ef0ef',  // Solid color, not frame
  borderTopWidth: 2,
  borderTopColor: '#20a5de',
  borderBottomColor: '#20a5de',
  paddingVertical: 7,
},
```

### Gaps Identified

1. **Focus frame**: Java draws 4 corner sprites from `/focusmovechess1`, JSX uses solid background
2. **Corner positioning**: Java uses `n6` offset (likely 2px) for corner inset, JSX has no corners
3. **Background fill**: Java focus frame is transparent with corners, JSX is solid `#6ef0ef`

---

## 5. Status Icon Rendering (`pc.a` - status byte mapping)

### Java Evidence

From `ew.java` line 77:
```java
pc.a(graphics, n2, n3, this.i.c);
// this.i.c = status byte (0, 1, 2, 3)
```

From `os.java` lines 285-314 (context menu logic):
```java
switch (((os)aq2).s.c) {
    case 2: // Arena/PvP active - shows trade option
    case 3: // Recovering - no actions
    case 1: // In battle - shows spectate option
    default: // Ready (0) - shows challenge/trade options
}
```

### Current JSX (PvpStatusBadge.tsx)

```tsx
export const PvpStatusBadge: React.FC<{ status: 'alive' | 'dead' }> = ({ status }) => (
  <Text style={styles.badge}>{status === 'alive' ? '><' : '[]'}</Text>
);
```

### Gaps Identified

1. **Status mapping**: Java uses byte (0/1/2/3), JSX uses string ('alive'/'dead')
2. **Icon rendering**: Java calls `pc.a()` which likely draws sprite, JSX uses text symbols
3. **Status semantics**: Java has 4 states (ready/battle/arena/recovering), JSX has 2 (alive/dead)
4. **Missing states**: JSX doesn't distinguish between "in battle" vs "in arena" vs "recovering"

---

## 6. Color Mapping (Java → JSX)

### Java Colors (from evidence)

```java
// Container background
v.am = 0xF0FBFF;  // Light blue background

// Selection highlight
7267055 = 0x6EF0EF;  // Cyan highlight (from fe.java, fm.java)

// Focus frame borders
0x20A5DE;  // Bright blue (from pc.c line 98)
0x135797;  // Dark blue (from pc.c line 95)

// Text colors (from gradient fonts)
0x5AA890;  // Teal (arena text base, os.java line 30)
0xFFFF00;  // Yellow (arena text highlight)
```

### Current JSX Colors

```typescript
// PvpDialog.styles.ts
arenaBoard: {
  backgroundColor: '#f0fbff',  // ✓ Correct (v.am)
},
legacyRow: {
  backgroundColor: '#f0fbff',  // ✓ Correct
  borderBottomColor: '#b9c7df',  // Unknown origin
},
legacyRowActive: {
  backgroundColor: '#6ef0ef',  // ✓ Correct (7267055)
  borderTopColor: '#20a5de',   // ✓ Correct
  borderBottomColor: '#20a5de', // ✓ Correct
},
legacyName: {
  color: '#1d2f59',  // Unknown origin
},
legacyMeta: {
  color: '#2b5ec4',  // Unknown origin
},
```

### Gaps Identified

1. **Border colors**: `#b9c7df` not found in Java evidence
2. **Text colors**: `#1d2f59` and `#2b5ec4` not found in Java evidence
3. **Gradient fonts**: Java uses gradient fonts (`if` class), JSX uses solid colors

---

## 7. Layout Dimensions (Java → JSX)

### Java Constants

```java
// Row heights (ew.java lines 48, 50)
this.e(42);  // Selected: 42px
this.e(32);  // Normal: 32px

// Offsets (ew.java lines 61, 64, 66)
n2 += this.c() + 2;  // Horizontal: +2px
n3 += 7;  // Selected vertical: +7px
n3 += 3;  // Normal vertical: +3px

// Text margins (ew.java line 72)
int n4 = n2 + 25;  // Text left margin: 25px

// Line spacing (ew.java line 78)
n3 + 13;  // Secondary text: +13px below primary

// Icon position (fz.java lines 59, 62, 65)
n2 + 3, n3 + 3;  // Room icon: x=3, y=3
n2 + v.t - j.getWidth() - 4, n3 + 6;  // Lock icon: right edge, y=6
```

### Current JSX Dimensions

```typescript
// PvpDialog.styles.ts
legacyRow: {
  minHeight: 32,  // ✓ Correct
  paddingHorizontal: 8,  // ✗ Java uses 2px
  paddingVertical: 3,  // ✓ Correct
},
legacyRowActive: {
  minHeight: 42,  // ✓ Correct
  paddingVertical: 7,  // ✓ Correct
},
legacyBadge: {
  width: 28,  // ✗ Java uses 25px text margin (icon width unknown)
},
legacyTextWrap: {
  marginLeft: 2,  // ✗ Java uses 25px total from left edge
},
legacyTextWrapActive: {
  marginLeft: 4,  // ✗ Java uses same 25px
},
legacyMeta: {
  marginTop: 1,  // ✗ Java uses 13px
},
```

### Gaps Identified

1. **Horizontal padding**: JSX uses 8px, Java uses 2px
2. **Text left margin**: JSX uses badge(28px) + margin(2/4px) = 30/32px, Java uses 25px
3. **Line spacing**: JSX uses 1px, Java uses 13px
4. **Badge width**: JSX uses 28px, Java icon width unknown (need asset dimensions)

---

## Summary of Critical Gaps

### Must Fix (Pixel-Accurate)

1. **Row horizontal padding**: Change from 8px → 2px
2. **Text left margin**: Adjust badge + margin to total 25px from left edge
3. **Secondary text spacing**: Change from `marginTop: 1` → `marginTop: 13`
4. **Selection background**: Replace solid color with focus frame corners (or keep solid if corners not available)
5. **Marquee animation**: Implement text scroll for selected row when text overflows

### Should Fix (Functional Parity)

6. **Status icon**: Map 4 Java states (0/1/2/3) to proper icons/badges
7. **Horizontal separator**: Add 1px line at y=5 (arena) or y=73 (challenge)
8. **Bottom decoration**: Add `/hiddendragon` image at bottom-right (if asset available)
9. **Timer display**: Add timer text at bottom-right in challenge mode

### Nice to Have (Enhancement)

10. **Room list mode**: Implement fz.java room list view (separate feature)
11. **Gradient fonts**: Implement gradient text rendering (complex, may skip)
12. **Text clipping**: Use proper clip rect instead of `numberOfLines={1}`

---

## Next Steps

1. Fix critical layout dimensions in `PvpDialog.styles.ts`
2. Adjust JSX structure in `PvpDialog.tsx` to match Java offsets
3. Implement marquee animation for selected row
4. Add horizontal separator line
5. Map status byte to 4 states
6. Run TypeScript check
7. Test on device/emulator
8. Update CHANGELOG

---

## References

- `os.java` lines 104-130 (container rendering)
- `ew.java` lines 58-104 (row rendering + marquee)
- `fz.java` lines 41-68 (icon rendering)
- `pc.java` lines 185-192 (focus frame)
- `PvpDialog.tsx` lines 154-178 (current row JSX)
- `PvpDialog.styles.ts` lines 66-115 (current row styles)
