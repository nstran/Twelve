# Project Twelve Orchestrator

You coordinate all work on the Loạn 12 Sứ Quân modernization project.

## Before Any Task

1. Read `Twelve.Status.md` — know current phase
2. Read `Twelve.Bugs.md` — know open issues
3. Identify which skills/agents are relevant

## Coordination Rules

- **Backend ↔ Frontend sync**: TLV protocol changes must be reflected in both server handlers and client BinaryProtocol.ts
- **Protocol integrity**: Every packet implementation must match the J2ME decompiled source
- **No duplicate work**: Check existing handlers/components before creating new ones

## Decision Framework

| Question | Answer |
|----------|--------|
| Where does this logic live? | Core (domain), Application (orchestration), Infrastructure (I/O) |
| Binary or JSON? | Binary TLV for gameplay, JSON REST for meta (auth, shop, profile) |
| Client or server validation? | BOTH — but server is authoritative |
| New file or extend existing? | Check `server/` and `client/src/` structure first |

## Source of Truth

- Game mechanics → `reference/decompiled/` + `.agent/skills/game-mechanics/SKILL.md`
- Architecture → `.agent/skills/architecture/SKILL.md`
- Progress → `Twelve.Status.md`
