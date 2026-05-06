# /debug — Systematic Debug

$ARGUMENTS

## Steps

1. **Gather**: Read `build_error.log` or error output. Get reproduction steps.
2. **Hypothesize**: List 3 likely causes ranked by probability.
3. **Isolate**: Trace through code from entry point. Check:
   - `Core/` for logic errors
   - `Application/Handlers/` for packet handling issues
   - `Infrastructure/` for DB/network failures
   - `client/src/` for rendering/socket bugs
4. **Evidence Check** (for gameplay/protocol bugs):
   - Compare observed behavior with Java evidence in `reference/redecoded/decompiled/`
   - Check relevant `*_SYSTEM_RECONSTRUCTION.md` for expected behavior
   - Verify raw command/tag/enum values match Java evidence
   - Check if bug fix introduces invented fallback or default values
5. **Fix**: Apply fix in the correct layer (Core first, then outward).
   - For gameplay/protocol fixes: Add code comment citing evidence source
   - Mark any remake policy decisions clearly
6. **Verify**:
   - `dotnet build` passes (for backend changes)
   - `client\node_modules\.bin\tsc.cmd -p client\tsconfig.json --noEmit` passes (for client changes)
   - Error is gone
   - No regressions in related systems
7. **Documentation** (for gameplay/protocol fixes):
   - Update relevant `*_SYSTEM_RECONSTRUCTION.md` if behavior changed
   - Update `CHANGELOG.md` if fix affects user-visible behavior
8. **Cleanup**: Delete `build_error.log` after resolution.

## Gameplay/Protocol Bug Checklist

For bugs related to game mechanics, binary protocol, or UI behavior:

- [ ] Behavior compared with Java evidence
- [ ] Raw values (command/tag/enum) verified against Java
- [ ] No invented fallback introduced
- [ ] Code comments cite evidence source
- [ ] Reconstruction docs updated if needed
- [ ] Regression risk assessed

## Related Workflows

- Use `/reconstruction` workflow for implementing new gameplay/protocol features
- Use `/plan` workflow for planning complex fixes
