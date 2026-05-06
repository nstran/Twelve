# /reconstruction — Evidence-First Reconstruction Workflow

$ARGUMENTS

## Purpose

This workflow is for gameplay/protocol/UI reconstruction tasks where Java client evidence must guide implementation, not AI inference.

## When to Use

- Implementing game mechanics from Java/J2ME source
- Reconstructing binary protocol (commands, tags, raw values)
- Rebuilding UI flows from Java client
- Porting equipment/skill/battle/NPC systems
- Any task where original behavior must be preserved

## Steps

### 1. Evidence Scan

Read relevant sources:
- Java decompiled files in `reference/redecoded/decompiled/`
- Config/asset files in `reference/redecoded/extracted_meta/`
- Video/screenshot evidence in `reference/raw/`
- Existing reconstruction docs: `*_SYSTEM_RECONSTRUCTION.md` and `docs/`

### 2. Create Evidence Brief

Use template: `plans/_templates/evidence-brief-template.md`

Required sections:
- User request
- Java evidence (files, methods, raw values)
- Config/asset/video evidence
- Existing reconstruction docs
- Unknowns and missing evidence
- Remake policy pending approval
- Risk of incorrect inference

**Critical**: Separate what is PROVEN from what is PROPOSED.

### 3. Validate Evidence with User

If evidence is incomplete or remake policy is needed:
- Use `ask_followup_question` to confirm decisions
- Do NOT proceed with invented behavior
- Mark all assumptions as "pending approval"

### 4. Create Reconstruction Scope

Use template: `plans/_templates/reconstruction-scope-template.md`

Required sections:
- Goal and non-goals
- Java evidence-backed behavior
- Remake policy behavior (clearly marked)
- Protocol impact (commands, tags, raw values)
- Backend impact (Core/Application/Infrastructure)
- Client impact (screens, hooks, assets)
- Database impact
- Acceptance criteria
- Documentation targets

### 5. Architecture Plan (if multi-layer)

If feature touches multiple layers or has complex dependencies:
- Identify Core domain changes
- Identify Application service/handler changes
- Identify Infrastructure repository/database changes
- Identify Client screen/component changes
- Document interface contracts
- Document validation strategy

### 6. Story Breakdown

Use template: `plans/_templates/reconstruction-story-template.md`

Each story should:
- Be implementable in one Code mode session
- Have explicit evidence links
- List files likely touched
- Have step-by-step implementation checklist
- Have testable acceptance criteria
- Have validation commands
- Have documentation/changelog checklist
- Have explicit constraints (no invented fallback, preserve raw values, etc.)

### 7. Implementation (Code Mode)

Switch to Code mode only after story is approved.

During implementation:
- Follow story checklist exactly
- Add code comments citing evidence sources
- Mark remake policy decisions clearly
- Use enum/constants for raw values (no magic numbers)
- Preserve Java command/tag/enum values exactly
- No ternary operator for complex gameplay logic
- No invented fallback without evidence or approval

### 8. Build and Check

After implementation:
- Run `dotnet build` for backend changes
- Run `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` for client changes
- Fix all errors before proceeding

### 9. QA Gate

Use template: `plans/_templates/qa-gate-template.md`

Required checks:
- Build/check passes
- Behavior matches acceptance criteria
- Protocol/raw values match Java evidence
- Evidence traceability (code comments cite sources)
- Code quality (Clean Architecture, SOLID, no magic numbers)
- Regression risks identified
- Documentation/changelog updated
- Security/validation checks

Decision: Pass / Pass with notes / Block

### 10. Update Documentation

After QA pass:
- Update relevant `*_SYSTEM_RECONSTRUCTION.md` file
- Add entry to `CHANGELOG.md` with:
  - Feature summary
  - Affected files
  - Java evidence sources
  - Remake policy decisions (if any)

## Anti-Patterns to Avoid

- **Inventing behavior**: If Java evidence is missing, ask user or mark as remake policy.
- **Trusting client**: Server must validate all gameplay state, never trust client packets.
- **Magic numbers**: Use named constants or enums for command IDs, tag IDs, slot types, etc.
- **Ternary for gameplay**: Use explicit `if/else` to match Java flow and enable evidence comments.
- **Fallback without evidence**: If Java has no fallback, remake should not add one without approval.
- **Mixing evidence and policy**: Always separate Java evidence from remake decisions in docs and comments.

## Example Flow

```
User: "Implement equipment upgrade system"
  ↓
Evidence Scan: Read ll.java, lm.java, ks.java, equipment_seed.sql
  ↓
Evidence Brief: Document command 84, tag 114, upgrade formula (if found)
  ↓
User Approval: Confirm remake policy for missing formula
  ↓
Reconstruction Scope: Define backend/client/database changes
  ↓
Story 1: Add upgrade_level field and Core calculator
Story 2: Implement Application service and handler
Story 3: Add Client UI and API hook
  ↓
Code Mode: Implement Story 1
  ↓
Build Check: dotnet build passes
  ↓
QA Gate: Verify behavior, evidence, docs
  ↓
Update Docs: EQUIPMENT_SYSTEM_RECONSTRUCTION.md + CHANGELOG.md
  ↓
Repeat for Story 2, Story 3
```

## Success Criteria

- All behavior is traceable to Java evidence or explicit remake policy
- No invented fallback or default values
- Build passes
- Documentation updated
- User approves final result

---

**Workflow version**: 1.0  
**Last updated**: 2026-05-06
