# Project Twelve - AI Agent Rules

> Universal rules for ALL AI agents (Claude, Gemini, Copilot, etc.)
> Source of truth for agent behavior in the Loạn 12 Sứ Quân project.

## Language Protocol

- **Response language**: Tiếng Việt (Vietnamese)
- **Code/variables/comments**: English
- No exceptions. No language toggle needed.

## Before Any Action

1. Read `Twelve.Status.md` — current phase and progress
2. Read `Twelve.Bugs.md` — open issues
3. Identify which `.agent/skills/` are relevant to the task

## Architecture (Non-negotiable)

- **Clean Architecture**: Core → Application → Infrastructure
- **Core (Domain)**: ALL game logic lives here. Combat, EXP, Map rules, Validators.
- **Application**: Handlers, Services, DTOs. Orchestration only.
- **Infrastructure**: Dapper/PostgreSQL, TCP/Network, File storage.
- **No ABP, no over-engineering.** Keep it lean.

## Protocol

- **Binary TLV** for J2ME legacy and game packets.
- **JSON REST** for modern React Native client.
- Reference: `.agent/skills/binary-protocol/SKILL.md`

## Frontend

- React Native + Expo + Skia for game rendering.
- 60FPS target. Zustand for state. No purple.
- Reference: `.agent/skills/frontend-design/SKILL.md`

## Security

- Zero Trust: verify every packet server-side.
- Disconnect malformed clients immediately.
- Parameterized Dapper queries only.
- Secrets in `.env` only.

## Verification

- Backend: `dotnet build` must pass
- Frontend: `npx expo check` must pass
- Always validate against J2ME JAR source of truth

## Source of Truth

- `loan-12-su-quan.jar` and `reference/decompiled/` for all game logic
- Never guess game mechanics — always verify from decompiled source
