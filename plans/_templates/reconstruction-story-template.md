# Reconstruction Story Template

> Use this template to create implementable stories for Code mode.
> Goal: Make each story explicit, safe, and traceable to evidence.

## 1. Story Title

[Short, descriptive title]

Example: Implement Equipment Upgrade Command Handler

## 2. Context Summary

[Brief background and why this story exists]

Example:
This story implements server-side handling for equipment upgrade (command 84). Java evidence shows client sends tag 114 with equipment ID and expects command 112 response with updated equipment state. This is part of the Equipment System reconstruction.

## 3. Evidence Links

[Link to Evidence Brief, Reconstruction Scope, or reconstruction docs]

Example:
- Evidence Brief: [`plans/equipment-upgrade-evidence-brief.md`](../equipment-upgrade-evidence-brief.md)
- Reconstruction Scope: [`plans/equipment-upgrade-reconstruction-scope.md`](../equipment-upgrade-reconstruction-scope.md)
- System Doc: [`EQUIPMENT_SYSTEM_RECONSTRUCTION.md`](../EQUIPMENT_SYSTEM_RECONSTRUCTION.md)

## 4. Files Likely Touched

[List files that will be created or modified]

Example:
- `server/Twelve.Application/Handlers/EquipmentCommandHandler.cs` - add command 84 routing
- `server/Twelve.Application/Players/EquipmentUpgradeService.cs` - new service
- `server/Twelve.Core/Interfaces/IEquipmentUpgradeService.cs` - new interface
- `server/Twelve.Core/GameLogic/EquipmentUpgradeCalculator.cs` - upgrade formula
- `server/Twelve.Core/Players/PlayerEquipmentDefinition.cs` - add upgrade level field
- `server/Database/Equipment/equipment_schema.sql` - add upgrade_level column

## 5. Implementation Checklist

[Step-by-step tasks for Code mode]

Example:
- [ ] Add `upgrade_level` field to `PlayerEquipmentDefinition` record
- [ ] Create `IEquipmentUpgradeService` interface in `Twelve.Core/Interfaces/`
- [ ] Implement `EquipmentUpgradeCalculator` in `Twelve.Core/GameLogic/`
  - [ ] Success rate formula: preserve Java formula if proven, otherwise mark as remake policy
  - [ ] Gold cost calculation
  - [ ] Material consumption logic
- [ ] Implement `EquipmentUpgradeService` in `Twelve.Application/Players/`
  - [ ] Validate player has equipment
  - [ ] Validate player has upgrade materials
  - [ ] Call calculator for success/failure
  - [ ] Update equipment state in repository
  - [ ] Build response packet
- [ ] Update `EquipmentCommandHandler` to route command 84
- [ ] Add database migration for `upgrade_level` column
- [ ] Register service in `ServiceCollectionExtensions`

## 6. Acceptance Criteria

[Specific, testable criteria]

Example:
- [ ] Command 84 with tag 114 triggers upgrade flow
- [ ] Server validates equipment ownership
- [ ] Server validates material availability
- [ ] Server calculates success/failure correctly
- [ ] Server updates equipment level on success
- [ ] Server deducts gold and materials
- [ ] Server sends command 112 with updated equipment
- [ ] Build passes: `dotnet build`
- [ ] No TypeScript errors: `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit`
- [ ] No invented fallback without evidence or user approval

## 7. Validation Commands

[Commands to run after implementation]

Example:
```powershell
# Backend build check
dotnet build server/Twelve.sln

# Client TypeScript check (if client changes)
client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit
```

## 8. Documentation and Changelog Checklist

[Docs to update after implementation]

Example:
- [ ] Update [`EQUIPMENT_SYSTEM_RECONSTRUCTION.md`](../EQUIPMENT_SYSTEM_RECONSTRUCTION.md)
  - [ ] Add upgrade flow section
  - [ ] Document command 84 and tag 114 structure
  - [ ] Mark remake policy decisions clearly
- [ ] Update [`CHANGELOG.md`](../CHANGELOG.md)
  - [ ] Add entry: "Implemented equipment upgrade system (command 84)"
  - [ ] List affected files
  - [ ] Note any remake policy decisions

## 9. Constraints

[Explicit rules for this story]

Example:
- **No invented fallback**: If upgrade success rate formula is not proven from Java, mark as remake policy and ask user.
- **Preserve raw values**: Command ID `84`, tag ID `114`, slot enum values must match Java exactly.
- **No ternary for gameplay logic**: Use explicit `if/else` for upgrade success/failure to match Java flow.
- **Enum over int**: Use `EquipmentSlot` enum, not raw `int`, in business logic.
- **Evidence comments**: Add comment in `EquipmentUpgradeCalculator` citing evidence source.

## 10. Dependencies

[Other stories or tasks that must complete first]

Example:
- Equipment catalog repository must be implemented first.
- Player inventory service must support material consumption.

## 11. Risks

[What could go wrong]

Example:
- If upgrade formula is wrong, players will get different results than original game.
- If packet structure is wrong, client will fail to parse response.

---

**Story created by**: [AI/Human name]  
**Date**: [YYYY-MM-DD]  
**Ready for implementation**: [ ] Yes / [ ] No
