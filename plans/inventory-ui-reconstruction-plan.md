# Inventory UI Reconstruction Plan

## 1. Goal

Dựng lại giao diện hành trang/trang bị của Twelve mobile sao cho bám sát client gốc nhất có thể, chạy ổn định trên Android và iOS bằng React Native TypeScript.

Phạm vi plan này tập trung vào màn hình inventory/equipment chính tương đương `hh.java`:

- Khung hành trang chính.
- 6 ô trang bị gốc.
- Preview nhân vật.
- Bag grid trang bị + item.
- Cell renderer giống `dc.java`.
- Tooltip/detail nhanh giống `fw.java`.
- Detail dialog đầy đủ giống `hg.java`.
- Repair entry giống `hl.java` ở mức mở dialog/action, không mở rộng shop/upgrade/combine/trade trong plan đầu.

## 2. Java Evidence

### 2.1 Screen class

Source: `reference/redecoded/decompiled/hh.java`

`hh.java` là màn hình hành trang chính:

- `F = new dc[6]`: chỉ có 6 equipped cells gốc.
- `u = new k[]{slot0, slot1, slot2, slot3, slot4, slot5, bagRect}`: 6 slot + grid region.
- `s`: avatar preview rectangle.
- `t`: bag grid rectangle.
- `v = new fg(...)`: grid/list container.
- `k = f.d("/info/hidenobj")`: sprite sheet icon slot trống.
- `p = 32`: slot-placeholder frame width/height from `hidenobj`.

### 2.2 Original layout coordinates

`hh.java` có hai layout mode theo `B() = com.mg.sq.a.k == 1`.

#### Portrait / normal mode

Source: `hh.java` constructor lines around layout block.

```text
screen logical width = 240
screen logical height = 320 - softkeyHeight
avatar s = x:95, y:21, w:54, h:60
slot0 = x:60,  y:18, w:32, h:32
slot1 = x:60,  y:53, w:32, h:32
slot2 = x:153, y:18, w:32, h:32
slot3 = x:153, y:53, w:32, h:32
slot4 = x:189, y:18, w:32, h:32
slot5 = x:189, y:53, w:32, h:32
bag t = x:9, y:88, w:220, h:panelHeight - 96
capacity text = x:t.x, y:t.y - 16, align left
```

#### Landscape / wide mode

```text
screen logical width = 320
screen logical height = deviceHeight - softkeyHeight
avatar s = x:13, y:24, w:54, h:60
slot0 = x:6,  y:90,  w:32, h:32
slot1 = x:6,  y:126, w:32, h:32
slot2 = x:42, y:90,  w:32, h:32
slot3 = x:42, y:126, w:32, h:32
slot4 = x:6,  y:162, w:32, h:32
slot5 = x:42, y:162, w:32, h:32
bag t = x:79, y:23, w:226, h:panelHeight - 33
capacity text = x:t.x, y:t.y + t.h - 16, align center/right-ish
```

### 2.3 Render order from `hh.java`

Source: `hh.java` draw method.

Render order:

1. Draw player name at `r = cu(22,6)` and element/faction icon at `q = cu(6,4)`.
2. Draw level text at top right.
3. Draw 6 slot backgrounds using `pc.b(...)`.
4. Draw empty slot placeholder from `/info/hidenobj`, frame `n * 32`.
5. Draw equipped cells `F[n]` at `u[e]`.
6. Draw bag grid container `D` / `fg`.
7. If selected bag equipment and not currently in equipped slot, draw yellow 3-rect target highlight over target slot `u[e]`:
   - `#FEFF77`
   - `#FFF930` approx from `16776624`
   - `#FFFDD3`
8. Draw current focus frame with `pc.a(...)` and blink offset `J = 0/-2`.
9. Draw capacity text `v.s()/go.n`.
10. Draw tooltip `S` if active.
11. Draw avatar frame and animated character preview `y`.

### 2.4 Inventory population rules

Source: `hh.java` constructor.

- Source equipment list is `go.l`.
- Source item list is `go.m`.
- If `go.l[n].c` matches an equipped item in `M.D[]`:
  - if `e != 8`, put into `F[e]`.
  - if `e == 8`, do not put into `F`.
- If not equipped, add to bag grid `v`.
- Equipment icon is `N.a(mb.a(equip) + 98, true)`.
- Capacity baseline is `go.n`, but logical capacity `Y` expands to current occupied count if count exceeds `go.n`.

### 2.5 Action menu rules

Source: `hh.java` `a(k k2)` and command handlers.

For equipment cell `dc.j == 0`:

- If damaged and repairable: `Sửa chữa`.
- If selected equipped slot:
  - `Chi Tiết`.
  - `Cởi ra`, or `Vứt bỏ` when bag is full.
  - If armor slot and helmet visibility flag: `Ẩn Nón` / `Hiện Nón`.
- If selected bag equipment and equip conditions pass:
  - `Trang bị`.
  - For `e == 8`: `Dùng`.
  - `Chi Tiết`.
  - `Nâng cấp`.
  - `Rao bán` if tradeable.
  - `Vứt bỏ`.
- If selected bag equipment but cannot equip:
  - `Chi Tiết`.
  - `Nâng cấp`.
  - `Rao bán` if tradeable.
  - `Vứt bỏ`.

For item cell `dc.j == 1/2`:

- item type `e == 1`: `Dùng`.
- item type `e == 3`: `Dùng`.
- item type `e == 9`: `Mở`.
- `Rao bán` if tradeable.
- `Vứt bỏ`.

### 2.6 Cell renderer rules

Source: `reference/redecoded/decompiled/dc.java`.

Equipment cell:

- Draw equipment icon image at cell origin.
- If `equip.p == 0`, draw `/broken_heart` overlay using Java anchor `40` at `x + brokenHeartWidth`, `y + cellHeight`.
- If rank `m == 4 || m == 7 || m == 8`, draw animated star from `pc.b`, 3 frames.
- If enhancement `j > 0`, draw `+N` at bottom-right with gradient font.

Item/material cell:

- Draw item icon via `pc.g(graphics, item.j, x, y, 0)`.
- If `item.l != 1`, draw quantity at bottom-right.

### 2.7 Tooltip quick detail rules

Source: `reference/redecoded/decompiled/fw.java`.

`fw` appears after selected cell is focused for 7 ticks in `hh.java`.

Equipment tooltip:

- Name with rank color from `ll.a(rank)`.
- Element icon before name when `ll.f > 0`.
- Enhancement suffix `+N`.
- Required level line, red if not enough level.
- Durability line if `q > 0`:
  - includes `Đã hư hoàn toàn` when `p == 0`.
  - includes `Đã hư hỏng nặng` when durability ratio below 30%.
- Stat lines from `com.mg.sq.a.a(equip)`.
- Description wrapped when available.
- Gender restriction warning.
- Repair hint:
  - `Không thể sửa chữa` if `!equip.c()`.
  - `Cần {k} búa để sửa chữa` if repairable.
- Trade warning: `Không thể giao dịch` if `!equip.a()`.

Item tooltip:

- Name.
- Description wrapped.
- Trade warning when not tradeable.
- Item type notes such as battle-only for some type.

### 2.8 Full equipment detail dialog

Source: `reference/redecoded/decompiled/hg.java`.

`hg.java` is used for full equipment detail, including pickup/drop dialog style:

- Width starts at `240` or `320`, then `f -= 20`.
- Icon centered at top.
- Equipment name line includes element icon and enhancement.
- Separator line after name.
- Required level with red warning.
- Durability red if below 30%.
- Stat lines in green.
- Description lines.
- Gender restriction warning at bottom when wrong gender.
- Softkeys: `Bỏ qua` and `Nhặt` in drop flow; inventory detail can use `Đóng`.

## 3. Current React Native State

Current implementation is in `client/src/screens/map/core/MapCharacterDialogs.tsx` and `client/src/screens/map/core/MapCharacterDialogs.styles.ts`.

Already present:

- `InventoryShell` component.
- 6 equipped slots.
- Avatar preview via `CharacterRenderer`.
- Bag grid with 6 columns.
- Detail panel.
- Context action menu through `PopupMenu`.
- Equipment icon resolver using Java formula in `client/src/screens/character/shared/equipmentAssets.ts`:
  - `band = resourceId - resourceId % 10`.
  - icon asset by band.
- Item icon resolver through `iconKind`.
- Repair hammer id `30099`.
- Draft loadout preview and commit flow.

Main gaps vs Java:

- React Native layout is custom `340x600`, not original logical `240x(320-softkey)` / `320x(height-softkey)` scaled layout.
- Slot coordinates differ from original coordinates.
- Bag grid currently uses 51 px cells and 6 columns, not derived from original `fg` layout.
- Fullscreen original screen is currently embedded in a scrollable overlay dialog.
- Tooltip/detail behavior is panel-like, not delayed `fw` tooltip plus `hg` full detail.
- Cell renderer lacks exact broken-heart anchor, animated rank star, enhancement gradient position fidelity.
- Capacity always displays `rawCells.length/50`, not Java `v.s()/go.n` with expanded capacity baseline.
- Slot `e == 8` is currently not clearly separated as special UI slot/policy in the main 6-cell layout.

## 4. Reconstruction Strategy

### 4.1 Use original layout as source of truth

Implement an RN inventory scene using a logical coordinate system:

```text
portraitBase = 240 x (320 - softkeyHeight)
landscapeBase = 320 x (screenHeightLogical - softkeyHeight)
```

RN renders by scale transform:

```text
scale = min(availableWidth / logicalWidth, availableHeight / logicalHeight)
canvasWidth = logicalWidth * scale
canvasHeight = logicalHeight * scale
```

All original coordinates are converted using `logicalPx * scale`.

Do not hand-tune slot coordinates unless screenshot proves decompile misses device-specific offset.

### 4.2 Prefer fullscreen inventory for fidelity

Recommended UI mode:

- Inventory should be a fullscreen overlay/screen, not nested inside generic `CornerFrame` + `ScrollView`.
- Keep current `MapCharacterDialogs` entry point, but route inventory/equipment kind to a dedicated component such as:
  - `client/src/screens/map/core/inventory/InventoryScreen.tsx`
  - `client/src/screens/map/core/inventory/InventoryScreen.styles.ts`
  - `client/src/screens/map/core/inventory/InventoryCellView.tsx`
  - `client/src/screens/map/core/inventory/InventoryTooltip.tsx`

Reason:

- Original `hh.java` owns its full screen dimensions and softkeys.
- `ScrollView` can break absolute coordinates and popup/menu stacking on Android/iOS.
- A fixed logical canvas is safer and easier to compare with the original.

### 4.3 Keep server authority unchanged

UI must not invent gameplay rules.

- Equip validation remains server-authoritative.
- Client can pre-check level/gender for menu availability like the original, but commit still sends server request.
- Broken equipment remains wearable but does not contribute stats, per existing backend policy.
- Repair uses raw item id `30099`, but UI should display it as búa sửa chữa only because user confirmed policy.

## 5. Proposed Component Design

### 5.1 `InventoryScreen`

Responsibilities:

- Choose portrait vs landscape logical layout.
- Compute scale and canvas offset for safe area.
- Build equipped cells following `hh.java` evidence:
  - equipped visible slots from equipment where `isEquipped && slot != 8 && slot < 6`.
  - bag equipment where `!isEquipped`.
  - `slot == 8` handled by bag/use flow or separate remake slot only if user confirms visual position.
- Render background `pc.a` equivalent using existing RN frame primitives/assets.
- Render name/level/capacity text.
- Render slot backgrounds and hidden slot placeholders.
- Render avatar preview.
- Render bag grid.
- Manage focus/selection/action menu/tooltip timer.

### 5.2 `InventoryLayout.ts`

Pure constants and helpers:

```ts
export const INVENTORY_LAYOUT = {
  portrait: {
    width: 240,
    avatar: { x: 95, y: 21, w: 54, h: 60 },
    slots: [
      { x: 60, y: 18, w: 32, h: 32 },
      { x: 60, y: 53, w: 32, h: 32 },
      { x: 153, y: 18, w: 32, h: 32 },
      { x: 153, y: 53, w: 32, h: 32 },
      { x: 189, y: 18, w: 32, h: 32 },
      { x: 189, y: 53, w: 32, h: 32 },
    ],
    bag: { x: 9, y: 88, w: 220 },
  },
  landscape: {
    width: 320,
    avatar: { x: 13, y: 24, w: 54, h: 60 },
    slots: [
      { x: 6, y: 90, w: 32, h: 32 },
      { x: 6, y: 126, w: 32, h: 32 },
      { x: 42, y: 90, w: 32, h: 32 },
      { x: 42, y: 126, w: 32, h: 32 },
      { x: 6, y: 162, w: 32, h: 32 },
      { x: 42, y: 162, w: 32, h: 32 },
    ],
    bag: { x: 79, y: 23, w: 226 },
  },
};
```

### 5.3 `InventoryCellView`

Responsibilities:

- Render equipment icon at 32x32 logical size.
- Render item icon at 32x32 logical size.
- Render quantity bottom-right if item stack rule requires.
- Render broken heart overlay when `durability <= 0`.
- Render rank star animation for ranks `4/7/8`.
- Render enhancement `+N` bottom-right.

Implementation note:

- Use `Animated` or timer-based frame index for rank star effect.
- Use `StyleSheet` only; no complex inline styles in JSX except computed absolute coordinates.

### 5.4 `InventoryGrid`

Responsibilities:

- Convert bag `fg` grid behavior into RN.
- Use Java cell base `32x32` plus spacing derived from visual comparison.
- Support capacity slots, including empty buy-capacity cells if needed.
- Maintain focus index and selected cell.
- Avoid `FlatList` unless grid becomes very large; for current inventory capacity 50, absolute/manual grid is acceptable.

Pending Java audit:

- Need inspect `fg.java` to extract exact grid columns/cell spacing. Current `hh.java` only gives grid rectangle `t` and capacity count.
- If `fg.java` confirms columns/spacing, use it. If not, derive from Java screenshot/reference.

### 5.5 `InventoryTooltip`

Responsibilities:

- Implement delayed quick tooltip equivalent to `fw.java`.
- Show after selected cell remains focused for roughly 7 update ticks.
- Position like Java:
  - `x = 9`
  - `y = screenHeight - tooltipHeight - 7` after `S.a(9, n3 - S.q() - 7)`
  - width `screenWidth - 20`
  - height starts at `screenHeight / 4`, then recalculated.
- Equipment content follows `fw.java` order.

### 5.6 `EquipmentDetailDialog`

Responsibilities:

- Render full detail dialog equivalent to `hg.java`.
- Reuse for `Chi Tiết` from inventory and drop pickup flow later.
- Render full stat list, description, restrictions, durability warning, rank color, element icon.

### 5.7 `InventoryActionMenu`

Use existing `PopupMenu` if it can match Java menu visually enough.

Required changes:

- Ensure menu item list includes disabled/hidden logic exactly like `hh.java`.
- Position menu near selected slot/cell using Java formula:
  - `x = slot.x + screenX + (slot.w - menuWidth) / 2`
  - `y = slot.y + screenY + slot.h`
  - if below screen, place above selected rect.
  - clamp within panel bounds.
- Existing `PopupMenu` already supports absolute `top/left` and original-style selected frame.

## 6. Android/iOS Stability Requirements

- Use `SafeAreaView` or `react-native-safe-area-context` if already installed; otherwise use `Dimensions` and conservative padding.
- Avoid relying on negative z-index; use `zIndex` + Android `elevation` together.
- Avoid wrapping fixed-coordinate inventory canvas in `ScrollView`.
- Use `Image` `resizeMode="stretch"` for UI panels and `contain` for item/equipment icons.
- Use stable touch targets by scaling logical 32x32 slots to at least 44 physical px when possible; if screen is too small, keep original coordinates but add invisible hitSlop.
- Avoid `any`; type all cells as discriminated unions.
- Keep styles in `InventoryScreen.styles.ts` / `InventoryCellView.styles.ts`.
- Run `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` after implementation.

## 7. Implementation Todo

### Phase A - Evidence extraction and constants

- [x] Read `reference/redecoded/decompiled/fg.java` and related grid classes to extract exact bag grid cell size, spacing, columns and focus behavior.
- [x] Create `InventoryLayout.ts` with portrait/landscape coordinates proven by `hh.java`.
- [x] Document source comments next to every coordinate block, citing `hh.java`.

### Phase B - Component split

- [x] Extract inventory UI from `MapCharacterDialogs.tsx` into dedicated `client/src/screens/map/core/inventory/` components.
- [x] Keep old `InventoryShell` behavior available until new component renders and compiles.
- [x] Replace only `activeDialog === 'inventory'` / `equipment` route after parity baseline is ready.

### Phase C - Logical canvas and slots

- [x] Implement fixed logical canvas with portrait/landscape scaling.
- [x] Render screen background/frame equivalent of `pc.a(..., v.aj, true)` using current UI frame assets.
- [x] Render name, element icon, level text using original positions.
- [x] Render 6 slot backgrounds at original positions.
- [x] Render hidden slot placeholder using `hidenobj.png` with 32x32 frame slicing.
- [x] Render equipped equipment into `F[e]` for `e != 8` and `e < 6`.
- [x] Keep `e == 8` out of the 6 equipped slots; show as bag item/use-flow unless separate remake wing slot is explicitly approved.

### Phase D - Bag grid

- [x] Build bag cells from non-equipped equipment + inventory items.
- [x] Use original capacity display `visibleCellCount/go.n`, not raw array length only.
- [x] Support empty slots up to `max(go.n, occupiedCount)`.
- [ ] Preserve original equipment sorting if server/client source currently returns sorted list; otherwise add UI sort only if backed by `gp.java` evidence.

### Phase E - Cell renderer fidelity

- [x] Draw equipment/item icons at 32x32 logical size.
- [x] Add broken heart overlay exactly when `durability <= 0`.
- [x] Add rank star animation for ranks `4/7/8`.
- [x] Add enhancement `+N` bottom-right with Java bitmap font at `dc.java` bottom-right anchor.
- [x] Draw quantity for stackable item cells with Java bitmap font at `dc.java` bottom-right anchor.

### Phase F - Focus, highlight, menu

- [x] Implement selected cell state for slots and bag grid.
- [ ] Add blink focus frame equivalent to Java `J = 0/-2`.
- [x] Add 3-rect yellow target slot highlight when selected bag equipment can target a slot.
- [x] Implement action menu rules from `hh.java`.
- [x] Ensure menu positioning follows original clamp behavior.

### Phase G - Tooltip and detail dialog

- [x] Implement delayed quick tooltip matching `fw.java` content order.
- [x] Implement full detail dialog matching `hg.java` for `Chi Tiết`.
- [x] Show required-level and durability warning colors.
- [ ] Show element icon before equipment name.
- [x] Show rank color mapping from Java `ll.a(rank)`.
- [x] Show repair/trade/gender warnings.

### Phase H - Backend/API integration safety

- [x] Keep existing preview/commit loadout API flow.
- [x] Ensure equip/unequip only mutates draft until `Cập nhật`/commit where required by original flow.
- [x] Repair calls existing repair handler with equip key and uses server response.
- [x] `Nâng cấp`, `Rao bán`, `Vứt bỏ`, `Mở` should only call existing handlers if implemented; otherwise show disabled/pending state rather than fake behavior.

### Phase I - Verification

- [x] Run TypeScript check with `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit`.
- [ ] Manually verify Android portrait small screen.
- [ ] Manually verify Android landscape/wide mode if app supports rotation.
- [ ] Manually verify iOS safe area/notch layout.
- [ ] Verify high z-index/elevation for menu and tooltip on Android.
- [x] Update `EQUIPMENT_SYSTEM_RECONSTRUCTION.md` task status and edit log if implementation changes code.
- [x] Update `CHANGELOG.md` after implementation day summary.

## 8. Open Questions Before Code

1. Should the inventory UI be changed from current overlay dialog to fullscreen screen?
2. Should slot `e == 8` remain outside the 6-slot UI for phase one, or should we add a separate remake wing slot in a clearly marked position?
3. Should quick tooltip (`fw.java` evidence) and full detail (`hg.java` evidence) both be implemented in the first UI pass, or only full detail first?
4. Should landscape mode be supported now using the wide layout, or should phase one lock portrait and keep wide layout constants ready?

## 9. Recommended Decision

Recommended implementation path:

- Use fullscreen inventory for fidelity.
- Implement portrait layout first with constants already supporting wide mode.
- Keep `e == 8` out of the 6 equipped slots in phase one, but document a separate remake wing-slot decision for later.
- Implement cell renderer, action menu, and full detail dialog first.
- Implement delayed quick tooltip after core layout is stable.

This gives the closest match to the original without inventing unsupported UI behavior.

## 10. Edit Log

### 2026-05-05 — Source-code parity pass for inventory frame/cells

- Java evidence applied from `hh.java`, `fg.java`, `dc.java`, `pc.java`, and `ba.java`; screenshot analysis was not used as source of truth.
- Updated `client/src/screens/map/core/inventory/InventoryLayout.ts` to use source logical height `320 - ba.a` with default `ba.a = 17` instead of the earlier remake-safe `288` height.
- Updated `client/src/screens/map/core/inventory/InventoryCellView.tsx` to replace text fallback overlays with real assets: `/broken_heart`, `/crystalblue`, and `/focusmovechess1`.
- Updated `client/src/screens/map/core/inventory/InventoryScreen.tsx` to render `/corner/2`, `/hiddendragon`, and `/tab` assets in the main inventory frame path.
- Updated `client/src/screens/map/core/inventory/InventoryScreen.styles.ts` with Java-derived color constants and 3-rect target highlight evidence.
- Updated `client/src/screens/map/core/inventory/InventoryTooltip.tsx` and `client/src/screens/map/core/inventory/EquipmentDetailDialog.tsx` wording from remake `Cấp yêu cầu` to Java text `Yêu cầu cấp`.
- Updated `client/src/components/controls/PopupMenu/PopupMenu.tsx` and `client/src/components/controls/PopupMenu/PopupMenu.styles.ts` with opt-in `javaCompact` mode for inventory menu: Java item height `20`, compact frame padding, left text inset from `bs.java`/`br.java`; Java compact selected row no longer renders ornate remake menu assets.
- Replaced panel fill approximation with runtime Java `v.aj = 0xF0FBFF` from `SQMIDlet.java` -> `v.a(...)`, rendered as `#F0FBFF`.
- Enabled automatic portrait/wide layout selection in `client/src/screens/map/core/inventory/InventoryLayout.ts` and `client/src/screens/map/core/inventory/InventoryScreen.tsx` instead of forcing portrait.
- Corrected focus rendering in `client/src/screens/map/core/inventory/InventoryCellView.tsx` to crop four `7x7` corners from `/focusmovechess1`, matching `pc.e(...)` instead of stretching the whole image.
- Added explicit panel line primitives in `client/src/screens/map/core/inventory/InventoryScreen.tsx`/styles to mirror `pc.a(...)` top/bottom/edge fillRect calls.
- Fixed route wiring in `client/src/screens/map/core/MapCharacterDialogs.tsx`: both `equipment` and `inventory` now early-return the parity `InventoryScreen`; old embedded `CornerFrame` equipment/inventory branches are no longer reachable.
- Replaced remaining RN `borderColor` bevel approximations for slots, grid cells, and bag container with layered Java primitives from `pc.b(...)` / `pc.a(..., color, fill)` evidence: outer `230911`, inner `14612735`, fill color, and right/bottom accent color.
- Fixed additional 1px parity issues in `client/src/screens/map/core/inventory/InventoryScreen.tsx`: `/tab` element icon now uses Java's fixed `35x37` frame width from `pc.java:85-86`, bag grid X padding now follows `fg.java` centered `m` formula, target highlight is drawn over the target equipped slot from `hh.java:1382-1394`, and avatar frame uses line primitives based on `pc.c(...)` instead of a generic border.
- Added a follow-up text/menu parity pass from Java font evidence: `bx.java` initializes bitmap fonts from `/_blackfont`, `/_fontcap`, and `/f/ver`; `c.java` confirms bitmap text height/width and bold offset behavior. `client/src/screens/map/core/inventory/javaFont/JavaBitmapText.tsx` now renders glyph regions from `_blackfont.png`, and `InventoryScreen.tsx` / `InventoryCellView.tsx` use it for name, level, capacity, enhancement and quantity text to avoid iOS/Android RN font metric drift. `PopupMenu.tsx` now also uses the same bitmap font in Java compact mode and sizes the menu from `bs.java` width formula `max(itemWidth,50)+42`, so inventory action labels/menu width no longer use native RN font metrics.
- Asset check: no missing icons for this pass; required assets already exist under `client/assets`.
- Verification: `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` passed.
