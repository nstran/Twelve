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
4. **Fix**: Apply fix in the correct layer (Core first, then outward).
5. **Verify**: `dotnet build` passes. Error is gone. No regressions.
6. **Cleanup**: Delete `build_error.log` after resolution.
