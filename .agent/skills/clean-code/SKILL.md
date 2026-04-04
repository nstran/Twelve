# L12SQ Clean Code Standards

## Patterns

- **SRP/DRY/KISS**: Applied to all files.
- **Async/Await**: Mandatory for all socket, network, and DB operations to prevent thread pool starvation.
- **Naming**: Intent-revealing. Use `CamelCase` for C# methods and `camelCase` for JS functions.

## Protocol

- **Code Review**: No boilerplate. Guard clauses first.
- **Layering**: Ensure Domain (Core) logic doesn't leak into Infrastructure.
- **Binary Data**: Use named constants for Tag IDs instead of magic numbers.

## Verification Checklist

- **Backend**: `dotnet build` passes. All unit tests (`dotnet test`) on logic green.
- **Frontend**: `npx expo check` and linting pass.
- **Protocol**: Manual verification with the J2ME client where possible.
