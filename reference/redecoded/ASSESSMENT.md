# Re-Decoding Assessment

## Asset Organization

The current organization is usable, but only partly canonical.

Reliable groups:
- `monster`
- `skill_effects`
- `board_chess`
- `ui_hud`
- `fonts_logos`
- `items_objects`
- `social_emotes`
- `character_resources/create_screen`

Why these are reliable:
- Their names come directly from jar paths such as `/monster`, `/play/*`, `/info/*`, `/m/*`, `/createcs/*`, and other named `.mg` files.

Less certain groups:
- `character_resources/appearance_group_798xx_uncertain`
- `character_resources/appearance_group_799xx_uncertain`
- `character_resources/skin_899xx_likely`
- `offline_unclassified`

Why they are less certain:
- The client loads many assets from `/offline/<id>.mg` as numeric cache resources.
- Numeric IDs do not preserve semantic folder names in the original jar.
- The create-character flow proves these IDs are appearance-related, but not enough to confidently rename every range as `hair`, `face`, or `npc`.

Confirmed point:
- `character_resources/body_990xx_confirmed` is the strongest mapping. The body-part compositor builds character frames from base body sheets at `99000 + frameGroup`.

## Java Reuse Value

The decompiled Java should be treated as reference specification, not code to reuse directly.

High-value reference for restoration:
- `f.java`
  Decodes `.mg` into PNG-compatible bytes.
- `pa.java`
  Resource cache loader, offline asset lookup, and install-cache flow.
- `mb.java`
  Body-part metadata parser and character sprite compositor.
- `nw.java`
  Create-character UI and appearance composition flow.
- `du.java`
  Main socket client flow and reconnect logic.
- `eg.java`
  Hardcoded host list.
- `om.java`
  Main map/combat scene loads monster and battle effect assets.
- `mv.java` and `mx.java`
  Board and battle HUD assets.

Medium-value reference:
- Data/model classes around packets, UI state, and map state.
- Anything that helps infer command IDs, actor state, and rendering flow.

Low direct reuse:
- MIDlet shell classes and mobile platform glue.
- SMS, OLA social, billing, and `platformRequest` integrations.
- RecordStore wrappers as-is, unless you want to emulate legacy persistence behavior exactly.

Practical recommendation:
- Port logic from these Java files into the current TypeScript/C# stack.
- Do not try to revive the original Java client as production code.
- Use the Java mostly to recover protocol rules, asset composition, rendering order, and gameplay behavior.
