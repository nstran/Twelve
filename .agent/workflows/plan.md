# /plan — Strategic Planning

$ARGUMENTS

## Steps

1. **Context**: Read `Twelve.Status.md` and `Twelve.Bugs.md` first.
2. **Evidence-First Check** (for reconstruction tasks):
   - Is this a gameplay/protocol/UI reconstruction task?
   - If yes, read relevant `*_SYSTEM_RECONSTRUCTION.md` docs
   - Identify Java evidence available in `reference/redecoded/decompiled/`
   - Identify missing evidence or remake policy decisions
   - Consider using `plans/_templates/evidence-brief-template.md`
3. **Identify**: Map affected layers and dependencies:
   - Core (domain logic changes?)
   - Application (new handlers/services?)
   - Infrastructure (DB schema/network changes?)
   - Client (new screens/components?)
4. **Output**: Create `implementation_plan.md` with:
   - Goals (what success looks like)
   - Proposed Changes (grouped by layer)
   - Open Questions (unknowns to resolve)
   - Verification Plan (how to test)
   - For reconstruction tasks: Evidence sources and remake policy decisions
5. **Review**: Get user feedback before proceeding.

**No-Code Zone**: No production code during planning phase.

## Templates Available

- `plans/_templates/evidence-brief-template.md` — For reconstruction tasks
- `plans/_templates/reconstruction-scope-template.md` — For feature scope
- `plans/_templates/reconstruction-story-template.md` — For implementation stories
- `plans/_templates/qa-gate-template.md` — For post-implementation review

## Related Workflows

- Use `/reconstruction` workflow for gameplay/protocol/UI reconstruction tasks
- Use `/debug` workflow for troubleshooting issues
