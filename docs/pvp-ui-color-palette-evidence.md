# PvP UI Color Palette Evidence (Java → React Native)

**Date:** 2026-05-05  
**Purpose:** Document exact Java color constants for 100% UI parity

---

## Core Global Colors (from `com/mg/sq/SQMIDlet.java` + `v.java`)

### Primary Background & Frame Colors
```java
// SQMIDlet.f() line 41-44
v.a(0xF0FBFF);      // Set base background
v.am = 0xF0FBFF;    // Main background color (light blue-white)
v.al = 0xFFFFFF;    // White border/outline
v.ak = 15698432;    // = 0xEF0000 (red accent border)
```

**Derived color `v.aj`** (computed in `v.a(int)` line 145-150):
```java
public static void a(int n2) {
    aj = n2;  // Store original
    int n3 = n2 >> 16 & 0xFF;  // R
    int n4 = n2 >> 8 & 0xFF;   // G
    int n5 = n2 & 0xFF;        // B
    // Lighten by 1/6 toward white
    ak = (255 - n3) / 6 + n3 << 16 
       | (255 - n4) / 6 + n4 << 8 
       | (255 - n5) / 6 + n5;
}
```
For `0xF0FBFF`:
- R: 240 → 240 + (255-240)/6 = 240 + 2 = 242 → `0xF2`
- G: 251 → 251 + (255-251)/6 = 251 + 0 = 251 → `0xFB`
- B: 255 → 255 + (255-255)/6 = 255 + 0 = 255 → `0xFF`
- **Result: `v.aj = 0xF2FBFF`** (slightly lighter fill)

### Frame/Dialog Colors (from `ap.java` - CornerFrame base)
```java
// ap.java line 12-20
private int b = v.am & 0xFFFFFF;  // = 0xF0FBFF (background)
private int c;  // border shade

// Constructor computes darker border:
int n2 = this.b >> 16 & 0xFF;  // R
int n3 = this.b >> 8 & 0xFF;   // G
int n4 = this.b & 0xFF;        // B
this.c = (255 - n2) / 6 + n2 << 16 
       | (255 - n3) / 6 + n3 << 8 
       | (255 - n4) / 6 + n4;
// Same formula → c = 0xF2FBFF (lighter shade for border)
```

**Frame rendering (ap.a() line 35-41):**
```java
graphics.setColor(v.aj);        // Fill: 0xF2FBFF
graphics.fillRect(n2 + 3, n3 + 3, n4 - 6, n5 - 6);
graphics.setColor(v.al);        // Outer border: 0xFFFFFF (white)
graphics.drawRect(n2 + 2, n3 + 2, n4 - 5, n5 - 5);
graphics.setColor(v.ak);        // Inner border: 0xEF0000 (red)
graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
// Plus corner sprites from "/_corner" image
```

---

## PvP-Specific Colors

### Arena Lobby Row Colors (from `os.java` + `ew.java`)
```java
// os.java line 30 - Arena list uses gradient font
private if y = new if(new int[]{5939728, 0xFFFF00});
// 5939728 = 0x5AA890 (teal-green base)
// 0xFFFF00 = yellow highlight
```

**Row background (from `os.java` line 108):**
```java
graphics.setColor(v.am);  // 0xF0FBFF
graphics.fillRect(0, 0, ((aq)object).e(), ((aq)object).f());
```

### Battle Intro / Versus Dialog Colors (from `ha.java`)
```java
// ha.java line 221-223
if (this.N == 11) {
    n3 = 0xFDBDBD;  // Pink/rose tint for certain state
}
```

### Button/Panel Accent Colors (from `pc.java` - UI primitives)

**Standard button frame (pc.c() line 142-147):**
```java
graphics.setColor(19, 87, 151);    // = 0x135797 (dark blue)
graphics.drawRect(n2, n3, n4, n5);
graphics.drawRect(++n2, ++n3, n4 -= 2, n5 -= 2);
graphics.setColor(32, 165, 222);   // = 0x20A5DE (bright blue)
graphics.drawLine(++n2, n3, n2 + (n4 -= 2), n3);
```

**Panel with gradient (pc.a() line 112-128):**
```java
// Inner fill
graphics.setColor(n6);  // Param color
graphics.fillRect(n2 + 3, n3 + 4, n4 - 6, n5 - 8);

// Borders
graphics.setColor(51967);      // = 0x0CAFFF (cyan)
graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
graphics.setColor(9975807);    // = 0x985FFF (purple)
graphics.drawRect(n2 + 2, n3 + 3, n4 - 5, n5 - 7);
graphics.setColor(8972031);    // = 0x88F8FF (light cyan)
graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, 1);
graphics.fillRect(n2 + 2, n3 + n5 - 3, n4 - 4, 1);
graphics.setColor(22246);      // = 0x0056E6 (dark blue)
graphics.fillRect(n2 + 4, n3, n4 - 8, 1);
```

**Red panel frame (pc.b() line 206-228):**
```java
graphics.setColor(230911);     // = 0x038600 (green outline)
graphics.drawLine(...);
graphics.setColor(14612735);   // = 0xDF0000 (red fill)
graphics.drawLine(...);
if (bl2) {  // Filled variant
    graphics.setColor(0xFFFFFF);
    graphics.fillRect(n2 + 2, n3 + 2, n4 - 3, n5 - 3);
    graphics.setColor(n6);  // Param inner color
    graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
}
graphics.setColor(14612735);   // = 0xDF0000
graphics.drawLine(...);
```

**Yellow/gold panel (pc.a() with yellow - line 247-261):**
```java
graphics.setColor(16742661);   // = 0xFF8085 (salmon)
graphics.fillRect(n2, n3, n4, 1);
graphics.fillRect(n2, n3 + n5 - 1, n4, 1);
graphics.setColor(16167168);   // = 0xF68000 (orange)
graphics.fillRect(n2, n3 + 1, n4, 1);
graphics.fillRect(n2, n3 + n5 - 2, n4, 1);
graphics.setColor(0xFFFF8B);   // Yellow
graphics.fillRect(n2, n3 + 2, n4, 2);
graphics.fillRect(n2, n3 + n5 - 4, n4, 2);
graphics.setColor(0xFFFFB7);   // Light yellow
graphics.fillRect(n2, n3 + 4, n4, 1);
graphics.fillRect(n2, n3 + n5 - 5, n4, 1);
graphics.setColor(n6);         // Param center fill
graphics.fillRect(n2, n3 + 5, n4, n5 - 10);
```

---

## Selected/Hover State Colors

### Row selection (from `ew.java`, `fe.java`, `fm.java`, etc.)
```java
// fe.java line 117-118, fm.java line 30-33
if (this.g) {  // Selected/focused
    graphics.setColor(7267055);  // = 0x6EF0EF (cyan highlight)
} else {
    graphics.setColor(14722016); // = 0xE0B060 (gold/tan normal)
}
graphics.fillRect(...);
```

### Button pressed state (from `fs.java` line 89-93)
```java
if (this.g) {  // Pressed
    graphics.setColor(7267055);  // = 0x6EF0EF (cyan)
} else {
    graphics.setColor(2401717);  // = 0x24A835 (green)
}
```

---

## Text/Font Colors (from gradient fonts)

### Standard text gradients (from `if.java`, `com/mg/sq/a.java`)
```java
// com/mg/sq/a.java line 49-50
h = new if(new int[]{0xFFFFFF, 0xFE0000});  // White → red
h.a(0xFE0000);

// da.java line 110 (character UI)
this.aJ = new if(new int[]{0xFFFFFF, 0xFF7F00});  // White → orange
```

### Arena/PvP text (from `os.java` line 30)
```java
private if y = new if(new int[]{5939728, 0xFFFF00});
// 5939728 = 0x5AA890 (teal) → 0xFFFF00 (yellow)
```

---

## React Native Mapping

### Current RN Colors (to be replaced)
```typescript
// PvpDialog.styles.ts (BEFORE)
overlay: { backgroundColor: 'rgba(0,0,0,0.6)' }  // OK (modal overlay)
frame: { backgroundColor: '#f7fbff' }            // WRONG: should be #f0fbff
content: { backgroundColor: '#f7fbff' }          // WRONG: should be #f2fbff (v.aj)
header: { backgroundColor: '#5aa8ff' }           // WRONG: should be transparent or match frame
arenaBoard: { backgroundColor: '#edf7ff' }       // WRONG: should be #f0fbff (v.am)
legacyBadge: { backgroundColor: '#e0f0ff' }      // WRONG: should be #f0fbff or selection color
legacyBadgeActive: { backgroundColor: '#5aa8ff' } // WRONG: should be #6ef0ef (7267055)
```

### Corrected RN Palette
```typescript
// Global frame/background
const JAVA_BG_MAIN = '#f0fbff';      // v.am
const JAVA_BG_FILL = '#f2fbff';      // v.aj (computed lighter)
const JAVA_BORDER_WHITE = '#ffffff'; // v.al
const JAVA_BORDER_RED = '#ef0000';   // v.ak (15698432)

// Selection/hover
const JAVA_SELECT_CYAN = '#6ef0ef';  // 7267055 (selected row)
const JAVA_NORMAL_TAN = '#e0b060';   // 14722016 (normal row)

// Arena text gradient
const JAVA_ARENA_TEXT_BASE = '#5aa890';  // 5939728 (teal)
const JAVA_ARENA_TEXT_HIGHLIGHT = '#ffff00'; // yellow

// Button/panel accents
const JAVA_BUTTON_BLUE_DARK = '#135797';   // 19,87,151
const JAVA_BUTTON_BLUE_BRIGHT = '#20a5de'; // 32,165,222
const JAVA_PANEL_CYAN = '#0cafff';         // 51967
const JAVA_PANEL_PURPLE = '#985fff';       // 9975807

// Battle intro pink tint
const JAVA_BATTLE_PINK = '#fdbdbd';  // 0xFDBDBD (ha.java state 11)
```

---

## Action Items

1. **PvpDialog.styles.ts:**
   - Change `frame.backgroundColor` from `#f7fbff` → `#f0fbff`
   - Change `content.backgroundColor` from `#f7fbff` → `#f2fbff`
   - Change `arenaBoard.backgroundColor` from `#edf7ff` → `#f0fbff`
   - Change `legacyBadge.backgroundColor` from `#e0f0ff` → `#f0fbff`
   - Change `legacyBadgeActive.backgroundColor` from `#5aa8ff` → `#6ef0ef`
   - Remove or adjust `header.backgroundColor` (should inherit frame)

2. **PvpIncomingPrompt.styles.ts:**
   - Apply same `#f0fbff` / `#f2fbff` background scheme
   - Use `#6ef0ef` for any active/highlight states

3. **BattleIntroScreen.styles.ts:**
   - Apply `#f0fbff` / `#f2fbff` for card backgrounds
   - Consider `#fdbdbd` pink tint if matching Java state 11 logic

4. **Border rendering:**
   - Implement triple-border pattern: outer white (`#ffffff`), inner red (`#ef0000`), fill (`#f2fbff`)
   - Match `ap.a()` rendering: 3px inset fill, 2px white rect, 1px red rect

---

## References
- `com/mg/sq/SQMIDlet.java` line 41-44 (color init)
- `v.java` line 145-150 (color derivation formula)
- `ap.java` line 12-20, 35-41 (frame rendering)
- `os.java` line 30, 108 (arena colors)
- `pc.java` line 90-261 (panel/button primitives)
- `ew.java`, `fe.java`, `fm.java`, `fs.java` (selection states)
- `ha.java` line 221-223 (battle intro tint)
