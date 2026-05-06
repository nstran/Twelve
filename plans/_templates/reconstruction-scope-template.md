# Reconstruction Scope Template

> Use this template to define feature scope after Evidence Brief is complete.
> Goal: Separate Java evidence-backed behavior from remake policy decisions.

## 1. Goal

[What is the primary objective of this reconstruction task]

Example: Implement equipment upgrade system matching Java client behavior for command 84, tag 114.

## 2. Non-Goals

[What is explicitly OUT of scope for this task]

Example:
- Equipment trading between players (deferred to trade system).
- Equipment enchantment (no Java evidence found).
- Equipment visual effects on character sprite (client-side only).

## 3. Java Evidence-Backed Behavior

[List behavior that is PROVEN by Java/config/docs]

Example:
- Equipment upgrade consumes gold and upgrade stones.
- Upgrade success rate decreases with equipment level.
- Failed upgrade does not destroy equipment but consumes materials.
- Command ID `84` with tag `114` is used for upgrade request.
- Server responds with command `112` containing updated equipment state.

## 4. Remake Policy Behavior

[List behavior that is NOT proven but is proposed for remake]

Example:
- Upgrade stone item ID: `5002` (not confirmed from Java).
- Base success rate formula: `80% - (equipmentLevel * 5%)` (not confirmed).
- Gold cost formula: `equipmentLevel * 100` (not confirmed).

**IMPORTANT**: These must be approved by user and marked as remake policy in code comments.

## 5. Protocol Impact

### 5.1 Commands Affected

[List command IDs and their purpose]

Example:
- Command `84`: Client → Server equipment upgrade request
- Command `112`: Server → Client equipment state update

### 5.2 Tags Affected

[List tag IDs and their data structure]

Example:
- Tag `114`: Equipment payload (nested TLV with equipment fields)
- Tag `115`: Gold amount (4-byte int)
- Tag `116`: Item consumption list (array of item ID + count)

### 5.3 Raw Values to Preserve

[List enum values, constants, magic numbers that must match Java]

Example:
- Equipment slot enum: `0` = weapon, `1` = armor, `2` = accessory
- Upgrade result enum: `0` = success, `1` = failure, `2` = insufficient materials

## 6. Backend Impact

### 6.1 Core Domain Changes

[List changes in `server/Twelve.Core/`]

Example:
- Add `EquipmentUpgradeCalculator` in `Twelve.Core/GameLogic/`
- Add `UpgradeResult` record in `Twelve.Core/Players/`
- Update `PlayerEquipmentDefinition` to include upgrade level field

### 6.2 Application Service Changes

[List changes in `server/Twelve.Application/`]

Example:
- Add `EquipmentUpgradeService` in `Twelve.Application/Players/`
- Update `EquipmentCommandHandler` to route command `84` to upgrade service
- Update `EquipmentPacketFactory` to build command `112` response

### 6.3 Infrastructure Changes

[List changes in `server/Twelve.Infrastructure/`]

Example:
- Update `EquipmentCatalogRepository` to load upgrade stone catalog
- Add database migration for equipment upgrade level column

## 7. Client Impact

### 7.1 Screen/Component Changes

[List React Native screens or components affected]

Example:
- Add `EquipmentUpgradeDialog` component in `client/src/components/equipment/`
- Update `InventoryScreen` to show upgrade button
- Add upgrade confirmation modal

### 7.2 API/Hook Changes

[List API calls or custom hooks]

Example:
- Add `useEquipmentUpgrade` hook
- Add `upgradeEquipment(equipmentId, stoneId)` API method
- Update `usePlayerEquipment` to refresh after upgrade

### 7.3 Asset Changes

[List new or updated assets]

Example:
- Add upgrade stone icon: `client/assets/items/5002.png`
- Add upgrade success/failure animation sprites

## 8. Database Impact

[List schema or seed data changes]

Example:
- Add `upgrade_level` column to `player_equipment` table (default: 0)
- Add upgrade stone item to `equipment_seed.sql`
- Add upgrade cost configuration table

## 9. Acceptance Criteria

[List specific, testable criteria for this feature]

Example:
- [ ] Client can send command `84` with equipment ID and stone ID
- [ ] Server validates player has equipment and stone
- [ ] Server calculates success rate based on equipment level
- [ ] Server deducts gold and stone on upgrade attempt
- [ ] Server updates equipment level on success
- [ ] Server sends command `112` with updated equipment state
- [ ] Client displays upgrade result (success/failure)
- [ ] Build passes: `dotnet build` and `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit`
- [ ] No invented fallback or default values without evidence

## 10. Documentation Targets

[List docs to update after implementation]

Example:
- Update [`EQUIPMENT_SYSTEM_RECONSTRUCTION.md`](../EQUIPMENT_SYSTEM_RECONSTRUCTION.md) with upgrade flow
- Update [`CHANGELOG.md`](../CHANGELOG.md) with feature summary
- Add upgrade protocol to [`CLAUDE.md`](../.agent/rules/RULES.md) if needed

---

**Reconstruction Scope completed by**: [AI/Human name]  
**Date**: [YYYY-MM-DD]  
**Approved by user**: [ ] Yes / [ ] No
