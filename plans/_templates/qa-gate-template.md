# QA Gate Template

> Use this template to review implementation before marking task complete.
> Goal: Verify build, behavior, evidence traceability, and documentation.

## 1. Story/Feature Under Review

[Link to story or feature scope]

Example: Equipment Upgrade System (command 84)

## 2. Build/Check Result

### 2.1 Backend Build

```powershell
# Command run
dotnet build server/Twelve.sln

# Result
[ ] Pass
[ ] Fail

# Error summary (if fail)
[Paste error summary here]
```

### 2.2 Client TypeScript Check

```powershell
# Command run
client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit

# Result
[ ] Pass
[ ] Fail
[ ] Not applicable (no client changes)

# Error summary (if fail)
[Paste error summary here]
```

## 3. Behavior Verification

[Check each acceptance criterion from story]

Example:
- [ ] Command 84 with tag 114 triggers upgrade flow
- [ ] Server validates equipment ownership
- [ ] Server validates material availability
- [ ] Server calculates success/failure correctly
- [ ] Server updates equipment level on success
- [ ] Server deducts gold and materials
- [ ] Server sends command 112 with updated equipment state

**Behavior notes**:
[Any observations or deviations]

## 4. Protocol/Raw Value Verification

[Verify command IDs, tag IDs, enum values match Java evidence]

Example:
- [ ] Command ID `84` preserved
- [ ] Tag ID `114` preserved
- [ ] Equipment slot enum: `0` = weapon, `1` = armor, `2` = accessory (matches Java)
- [ ] Upgrade result enum: `0` = success, `1` = failure (matches Java)

**Protocol notes**:
[Any deviations or unknowns]

## 5. Evidence Traceability Verification

[Verify code comments cite evidence sources]

Example:
- [ ] `EquipmentUpgradeCalculator` has comment citing Java evidence or remake policy
- [ ] Upgrade success rate formula is marked as Java evidence or remake policy
- [ ] Gold cost formula is marked as Java evidence or remake policy
- [ ] No invented fallback without explicit remake policy comment

**Traceability notes**:
[Any missing evidence citations]

## 6. Code Quality Checks

[Verify code follows project standards]

Example:
- [ ] No ternary operator for complex gameplay logic
- [ ] Enum used instead of raw int for type-safe values
- [ ] Service/repository/factory separation follows Clean Architecture
- [ ] Async/await used for I/O operations
- [ ] Guard clauses used for validation
- [ ] No magic numbers (constants defined)

**Code quality notes**:
[Any violations or concerns]

## 7. Regression Risks

[Identify potential side effects]

Example:
- [ ] Equipment equip/unequip still works
- [ ] Equipment repair still works
- [ ] Inventory display still works
- [ ] Player gold deduction does not affect other systems

**Regression notes**:
[Any risks or areas to watch]

## 8. Documentation/Changelog Verification

[Verify docs and changelog are updated]

Example:
- [ ] [`EQUIPMENT_SYSTEM_RECONSTRUCTION.md`](../EQUIPMENT_SYSTEM_RECONSTRUCTION.md) updated with upgrade flow
- [ ] Command 84 and tag 114 documented
- [ ] Remake policy decisions marked clearly
- [ ] [`CHANGELOG.md`](../CHANGELOG.md) updated with feature summary and affected files

**Documentation notes**:
[Any missing or incomplete docs]

## 9. Security/Validation Checks

[Verify security and input validation]

Example:
- [ ] Server validates equipment ownership (no trust client)
- [ ] Server validates material availability (no trust client)
- [ ] Server validates upgrade level bounds
- [ ] No SQL injection risk (parameterized queries)
- [ ] No packet parsing buffer overflow risk

**Security notes**:
[Any concerns]

## 10. Final QA Decision

[Overall assessment]

Options:
- [ ] **Pass**: Implementation is complete, build passes, behavior matches acceptance criteria, docs updated.
- [ ] **Pass with notes**: Implementation is acceptable but has minor issues documented above.
- [ ] **Block**: Implementation has critical issues and must be fixed before completion.

**Decision rationale**:
[Explain decision]

**Blocking issues** (if block):
1. [Issue 1]
2. [Issue 2]

---

**QA completed by**: [AI/Human name]  
**Date**: [YYYY-MM-DD]
