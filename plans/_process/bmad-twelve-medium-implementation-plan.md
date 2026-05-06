# BMAD Twelve Medium Implementation Plan

## 1. Decision

User selected medium adoption level for BMAD in Twelve.

Scope:

- Create reusable planning templates in `plans/_templates/`.
- Create a reconstruction workflow in `.agent/workflows/reconstruction.md`.
- Update existing `.agent/workflows/plan.md` to require evidence-first planning.
- Update existing `.agent/workflows/debug.md` to include gameplay/protocol evidence regression checks.
- Update `CHANGELOG.md` after workflow/template changes.

No production server/client code is required for this task.

## 2. Files to create

### 2.1 `plans/_templates/evidence-brief-template.md`

Purpose: capture Java/client/config/video/document evidence before feature planning.

Required sections:

- User request
- Feature/system area
- Java evidence
- Config/asset/video evidence
- Existing reconstruction docs
- Unknowns and missing evidence
- Remake policy pending approval
- Risk of incorrect inference
- Next planning decision

### 2.2 `plans/_templates/reconstruction-scope-template.md`

Purpose: define feature scope without treating remake decisions as original behavior.

Required sections:

- Goal
- Non-goals
- Java evidence-backed behavior
- Remake policy behavior
- Protocol impact
- Backend impact
- Client impact
- Database impact
- Acceptance criteria
- Documentation targets

### 2.3 `plans/_templates/reconstruction-story-template.md`

Purpose: make each implementable story explicit and safe for Code mode.

Required sections:

- Story title
- Context summary
- Evidence links
- Files likely touched
- Implementation checklist
- Acceptance criteria
- Validation commands
- Documentation and changelog checklist
- Constraints

### 2.4 `plans/_templates/qa-gate-template.md`

Purpose: review implementation before marking task complete.

Required sections:

- Story/feature under review
- Build/check result
- Behavior verification
- Protocol/raw value verification
- Evidence traceability verification
- Regression risks
- Documentation/changelog verification
- Final QA decision

## 3. Files to update/create in `.agent/workflows`

### 3.1 Create `.agent/workflows/reconstruction.md`

Purpose: canonical workflow for gameplay/protocol/UI reconstruction tasks.

Proposed flow:

1. Gather user request.
2. Scan Java/client/config/docs evidence.
3. Write or update Evidence Brief.
4. Separate Java evidence from Remake policy.
5. Define Reconstruction Scope.
6. Draft Architecture Plan when multiple layers are affected.
7. Split into implementation stories.
8. Switch to Code mode only after story is approved.
9. Verify build/check.
10. Update reconstruction docs and changelog.
11. Run QA Gate.

### 3.2 Update `.agent/workflows/plan.md`

Current plan workflow is generic. Add evidence-first requirements:

- Read relevant reconstruction docs.
- Identify Java evidence and missing evidence.
- Mark remake policy explicitly.
- Use templates from `plans/_templates/` for reconstruction tasks.

### 3.3 Update `.agent/workflows/debug.md`

Add gameplay/protocol bug rules:

- Compare observed behavior with Java evidence and reconstruction docs.
- Verify raw command/tag values are unchanged.
- Check whether bug fix introduces fallback or invented behavior.
- Update docs/changelog when fix changes behavior.

## 4. Validation plan

Because this task only changes markdown/workflow documents and does not modify server/client code:

- No `dotnet build` is required.
- Review created markdown files for completeness.
- Ensure templates do not encourage invented fallback.
- Ensure file references use project-relative paths.

## 5. Implementation mode

Switch to Code mode to create/update the markdown files, because Architect mode should plan and Code mode should apply edits.
