---
trigger: always_on
---

# Shared Protocols (Project Twelve)

> Common rules for all specialist agents. Avoid duplicating what's already in RULES.md.

## Execution Model

- **Direct Execution**: Write code and modifications directly using available tools.
- **Self-Reliance**: Plan, review, verify, and execute all tasks yourself.
- **No Delegation Loops**: Don't pass tasks between agents endlessly.

## Housekeeping

- **Build Logs**: Use `build_error.log` for error output. Delete immediately after fix.
- **Git Hygiene**: Root plans `./{task-slug}.md` are ignored by `.gitignore`.
- **Temp Files**: Clean up any temporary files after use.

## Code Quality (Quick Reference)

- Guard clauses first. Mandatory async/await.
- SRP/DRY/KISS. Intent-revealing naming.
- C#: PascalCase. JS/TS: camelCase.
- Named constants for Tag IDs — no magic numbers.
- Domain logic in Core layer ONLY.

## Anti-Cheat (Game-Specific)

- Never trust client for game state (HP, Gold, Battle Result).
- All critical calculations happen on the server.
- Binary protocol: verify Packet CMD IDs against `game-mechanics` skill.
