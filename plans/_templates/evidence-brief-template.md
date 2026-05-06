# Evidence Brief Template

> Use this template before planning any gameplay/protocol/UI reconstruction task.
> Goal: Gather Java/client/config/video/document evidence to prevent AI from inventing behavior.

## 1. User Request

[Describe what the user wants to implement or fix]

## 2. Feature/System Area

[Which system: Battle, Equipment, NPC/Mission, Map, Character, Skill, etc.]

## 3. Java Evidence

### 3.1 Decompiled Source

[List relevant Java files from `reference/redecoded/decompiled/`]

Example:
- `reference/redecoded/decompiled/ks.java` - packet sender methods
- `reference/redecoded/decompiled/om.java` - main game loop

### 3.2 Key Methods/Fields

[Document specific methods, fields, constants, or logic flows]

Example:
```java
// From ks.java line 450
public void sendEquipPacket(byte slot, ll equipment) {
    // Tag 114 contains equipment data
    this.send(84, TlvCodec.makeTag(114, equipment.serialize()));
}
```

### 3.3 Raw Values

[Document command IDs, tag IDs, enum values, constants that must be preserved]

Example:
- Command ID: `84` = Equipment action
- Tag ID: `114` = Equipment payload
- Slot enum: `0` = weapon, `1` = armor, `2` = accessory

## 4. Config/Asset/Video Evidence

### 4.1 Configuration Files

[List relevant `.meta` files, CSV/JSON manifests, or database seeds]

Example:
- `reference/redecoded/extracted_meta/offline/97199.meta` - equipment catalog
- `server/Database/Equipment/equipment_seed.sql` - current seed data

### 4.2 Asset Evidence

[List sprites, icons, UI images that confirm behavior]

Example:
- `client/assets/equipment/ui/30099.png` - repair hammer icon
- `reference/raw/images/inventory1.jpg` - inventory screen layout

### 4.3 Video/Screenshot Evidence

[List video frames or screenshots showing runtime behavior]

Example:
- `reference/raw/videos/_analysis_frames/crop_hoa_popup_47_25.png` - mission accept dialog

## 5. Existing Reconstruction Docs

[Link to relevant `*_SYSTEM_RECONSTRUCTION.md` or `docs/` files]

Example:
- [`EQUIPMENT_SYSTEM_RECONSTRUCTION.md`](../EQUIPMENT_SYSTEM_RECONSTRUCTION.md) - equipment protocol evidence
- [`docs/player-character-reconstruction/05-protocol-trade-upgrade-market.md`](../docs/player-character-reconstruction/05-protocol-trade-upgrade-market.md) - upgrade flow

## 6. Unknowns and Missing Evidence

[List what is NOT proven by Java/config/docs]

Example:
- NPC coordinates in Hoa Lu map are not in Java server evidence.
- Mission reward amounts are not confirmed from Java client.
- Dialog text for tutorial NPC is missing.

## 7. Remake Policy Pending Approval

[List decisions that are NOT original behavior but are proposed for the remake]

Example:
- Proposed NPC coordinates: `(10, 23)` for Trưởng làng Gia Viễn.
- Proposed mission reward: `80 EXP` for first mission.
- Proposed fallback: if equipment durability is missing, default to max durability.

**IMPORTANT**: Mark these clearly so they are not treated as Java evidence.

## 8. Risk of Incorrect Inference

[Describe what could go wrong if AI invents behavior without evidence]

Example:
- If we invent equipment upgrade formula, players may get different results than original game.
- If we invent NPC dialog, it may not match original game tone/story.
- If we invent packet tag structure, client may fail to parse server response.

## 9. Next Planning Decision

[What should happen next]

Options:
- [ ] Proceed to Reconstruction Scope if evidence is sufficient.
- [ ] Request more Java decompilation or asset extraction.
- [ ] Ask user to confirm remake policy decisions.
- [ ] Defer feature until more evidence is available.

---

**Evidence Brief completed by**: [AI/Human name]  
**Date**: [YYYY-MM-DD]
