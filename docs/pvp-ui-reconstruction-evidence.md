# PvP UI Reconstruction Evidence

## Java Evidence Summary

### Core Classes
- **`os.java`**: Main PvP lobby/room tab container
- **`do.java`**: Room/player model with fields:
  - `a`: username (String)
  - `b`: level (int)
  - `c`: status byte (0=ready, 1=in battle, 2=arena, 3=recovering)
  - `d`: honor/prestige (int)
  - `e`: status message (String)
  - `f`: stake/wager (long)
- **`ew.java`**: Individual row renderer for each player entry
- **`fz.java`**: Room icon renderer (arena vs normal room)
- **`v.java`**: Global constants (screen dimensions `t`, `u`)

### Layout Constants from Java

#### Row Dimensions (`ew.java`)
```java
// Line 48: selected row height
this.e(42);  // 42px when selected

// Line 50: normal row height
this.e(32);  // 32px when not selected

// Line 61-66: vertical offset adjustments
n2 += this.c() + 2;  // horizontal offset +2
n3 += 7;  // selected: +7 vertical
n3 += 3;  // normal: +3 vertical

// Line 72: left margin for text
int n4 = n2 + 25;  // 25px left margin for name/text

// Line 78: second line offset
n3 + 13  // 13px below first line for meta text
```

#### Status Icon (`pc.a` at line 77)
```java
pc.a(graphics, n2, n3, this.i.c);
// Draws status icon based on `do.c` (status byte)
// Position: n2 (left edge), n3 (top)
```

#### Text Labels (`ew.java` line 36-38)
```java
// Line 36: Primary label format
this.j = String.valueOf(do_.a) + (!i.b(do_.e) ? " - " + do_.e : "");

// Line 38: Secondary label format
this.k = "Cấp " + do_.b + "  --  Danh vọng" + " " + do_.d;
```

#### Fonts
- `bx.d`: Primary font (line 18, 37, 73)
- `com.mg.sq.a.g`: Secondary/meta font (line 19, 78)
- `bx.c`: Monospace/timer font (line 81)

### Color Evidence

From `os.java` line 30:
```java
private if y = new if(new int[]{5939728, 0xFFFF00});
// 5939728 = 0x5A9E70 (greenish)
// 0xFFFF00 = yellow
```

From `v.java` line 121-122:
```java
am = 200038;  // 0x030D66 (dark blue background)
v.a(200038);
```

From color search results:
- `0xFFFFFF`: White (common text/border)
- `0xFF0000`: Red (error/alert states)
- `0xF0FBFF`: Light blue background (common dialog bg)
- `0xFBB5B5`: Light pink/red tint
- `0xF88989`: Pink highlight

### Status Byte Mapping (`do.c`)

From `os.java` lines 285-314 (menu context):
```java
case 2: // Arena/PvP active
case 3: // Recovering
case 1: // In battle
default: // Ready (0)
```

From `ew.java` line 77:
```java
pc.a(graphics, n2, n3, this.i.c);
// Renders icon based on status byte
```

### Menu Actions (`os.java` lines 318-366)

```java
"Đánh!" (10104)           // Challenge with stake
"Khiêu Chiến" (10105)     // Challenge without stake
"Giao dịch" (10100)       // Trade
"Chat!" (10101)           // Chat
"Xem ME" (10102)          // View profile
"Xem Trận đánh" (10103)   // Spectate battle
```

### Room Icons (`fz.java` lines 26-31)

```java
if (l == null) {
    l = f.d("/m/arena");  // Arena icon
}
if (k == null) {
    k = f.d("/m/room");   // Normal room icon
}
```

## Current React Native Implementation Gap Analysis

### What's Already Correct
- Dialog frame structure with `CornerFrame`
- Opponent list with `ScrollView`
- Status badge rendering (`><` for ready, `[]` for recovering)
- Preview card with character renderer
- Challenge form with stake input and checkboxes

### What Needs Pixel-Perfect Adjustment

1. **Row Height**
   - Current: `minHeight: 42` (always)
   - Java: 32px normal, 42px selected
   - **Action**: Add selected state height variation

2. **Row Padding/Spacing**
   - Current: `paddingHorizontal: 8, paddingVertical: 4`
   - Java: Left margin 25px, vertical offset 3px/7px
   - **Action**: Adjust to match Java offsets

3. **Status Badge Position**
   - Current: `width: 28` badge at left
   - Java: Icon at `n2` (left edge), before 25px text margin
   - **Action**: Verify badge size matches Java icon dimensions

4. **Text Line Spacing**
   - Current: `lineHeight: 18` for name, `lineHeight: 15` for meta
   - Java: 13px offset between lines
   - **Action**: Adjust spacing to match Java 13px gap

5. **Preview Card Position**
   - Current: `position: 'absolute', right: 12, top: 74`
   - Java: Calculated dynamically based on selected row position
   - **Action**: Keep current approach but verify dimensions

6. **Colors**
   - Current: Mix of custom colors
   - Java: Specific hex values from evidence
   - **Action**: Map exact Java colors to RN styles

## Responsive Strategy

Since Java client was fixed 176x220 (or similar J2ME resolution), but RN needs to support modern Android/iOS:

1. **Base Unit**: Use Java px as reference, scale proportionally
2. **Breakpoints**: Define small/medium/large screen multipliers
3. **Min/Max Constraints**: Prevent UI from being too small or too large
4. **Relative Sizing**: Use percentages for container widths
5. **Absolute Sizing**: Keep critical UI elements (badges, icons) at fixed sizes

### Proposed Scaling Formula

```typescript
const BASE_WIDTH = 176;  // Java J2ME width
const BASE_HEIGHT = 220; // Java J2ME height
const SCREEN_WIDTH = Dimensions.get('window').width;
const SCREEN_HEIGHT = Dimensions.get('window').height;

const SCALE_FACTOR = Math.min(
  SCREEN_WIDTH / BASE_WIDTH,
  SCREEN_HEIGHT / BASE_HEIGHT,
  3.0  // Max 3x scale
);

// Apply to Java constants
const ROW_HEIGHT_NORMAL = 32 * SCALE_FACTOR;
const ROW_HEIGHT_SELECTED = 42 * SCALE_FACTOR;
const TEXT_LEFT_MARGIN = 25 * SCALE_FACTOR;
```

## Next Steps

1. Create layout constants file with Java-derived values
2. Update `PvpDialog.styles.ts` with pixel-perfect dimensions
3. Add selected state styling for rows
4. Implement responsive scaling system
5. Test on multiple screen sizes
6. Compare with reference screenshots/videos
7. Document any intentional deviations from Java

## References

- Java classes: `os.java`, `do.java`, `ew.java`, `fz.java`, `v.java`
- Current RN: `client/src/screens/map/shared/components/PvpDialog.tsx`
- Styles: `client/src/screens/map/shared/components/PvpDialog.styles.ts`
- Architecture doc: `docs/player-character-reconstruction/07-arena-pvp-flow.md`
