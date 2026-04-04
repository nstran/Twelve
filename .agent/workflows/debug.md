# /debug - Systematic Debug

$ARGUMENTS

When triggered:

1. **Gather**: Get error logs (`build_error.log`) and reproduction steps.
2. **Hypothesize**: List 3 most likely causes (e.g., Logic error, Socket timeout, DB deadlock).
3. **Isolate**: Use logs and code investigation to find the root cause.
4. **Fix**: Apply a Clean Architecture fix (Core first).
5. **Verify**: Ensure the error is gone and no regressions.
