# CLAUDE.md - Project Twelve (Loạn 12 Sứ Quân)

> Auto-loaded by Claude Code. Consolidated from `.agent/` rules, skills, and protocols.
> Last synced: 2026-04-12

## Language

Phản hồi người dùng hoàn toàn bằng **Tiếng Việt**.
Code, variables, và technical comments trong files luôn giữ bằng **English**.

## Project Overview

Revive and modernize the J2ME game "Loạn 12 Sứ Quân" (L12SQ) using modern tech stack (2026 Edition).

- **Backend**: .NET 9 (Clean Architecture, Dapper, PostgreSQL)
- **Frontend**: React Native (Expo, Skia)
- **Protocol**: Custom TLV Binary Protocol over TCP + SignalR for mobile
- **Source of Truth**: `loan-12-su-quan.jar` and `reference/decompiled/`

## Status Files (Read FIRST before any action)

- `Twelve.Status.md` — Current phase and completed tasks
- `Twelve.Bugs.md` — Open issues and technical logs
- `research_notes.md` — Study findings from J2ME source

## Architecture Standards

### Clean Architecture Layers

- **Twelve.Core (Domain)**: ALL game business logic — combat, EXP formulas, map rules, validators, entities, BattleEngine, TlvCodec
- **Twelve.Application**: Handlers, services, DTOs, PacketDispatcher — orchestration only
- **Twelve.Infrastructure**: Dapper/PostgreSQL, TCP network, JWT auth, file storage

### Rules

- No over-engineering. No ABP framework. Keep it lean.
- Domain logic MUST be in Core layer only.
- Use Dapper for high-performance DB access (NOT EF Core for game queries).
- DTOs kept flat for performance.
- Guard clauses first. Mandatory async/await.
- Named constants for all Tag IDs — no magic numbers.

## Binary Protocol (TLV)

### Packet Format

```
[TotalLength: 4 bytes] [PacketType: 1 byte] [Salt: 4 bytes] [CommandId: 1 byte] [Payload: N bytes]
```

### Tag Format

```
[TagId: 1 byte] [DataLength: 4 bytes] [Data: N bytes]
```

### Key Command IDs

| CMD | Name | Direction |
|-----|------|-----------|
| 1 | Login | C→S |
| 11 | MapInfo | S→C |
| 13 | SelectMap | C→S |
| 29 | JoinMap | C→S |
| 43 | SceneReady | C→S |

### Map Tags (CMD 11)

| Tag | Purpose |
|-----|---------|
| 55 | Ground layer |
| 54 | Decoration layer |
| 56 | Map Width |
| 57 | Map Height |
| 60 | Tileset index |
| 61 | Logic layer |

### Implementation Rules

- Big-Endian (Java) → Little-Endian (.NET) conversion required
- Use `ArrayPool<byte>` and `Span<byte>` for zero-allocation parsing
- Use `BinaryPrimitives.ReadInt32BigEndian` for endian conversion
- Strict boundary checks on every packet read
- Map `IPacketHandler` per CommandId

## Game Mechanics

### Match-3 System

- Grid: 8x8 or 8x9
- Gem types: EXP (level), MP (mana), Rage (Thanh Nộ), Gold (KEN), Physical (damage)
- Elemental skills: Hỏa, Lôi, Thủy — triggered by Match-4/5

### Battle Rules

- Server validates ALL battle outcomes. Never trust client for HP, Gold, or Battle Result.
- All critical calculations happen server-side.

## Frontend Standards (React Native + Skia)

- Use `react-native-skia` for heavy graphics (map rendering, battle board)
- Target 60FPS. Use `useClock`/`useValue` for sprite animation, batched rendering.
- State management: Zustand (NOT Redux)
- Navigation: react-navigation (Stack/Tabs)
- Theme: "Technical, Sharp" aesthetic — **No purple**. Gradients, glassmorphism, Inter/Roboto.

## Database (PostgreSQL + Dapper)

- Primary models: Players, Inventories, Maps
- Use JSONB for flexible data storage
- Composite indexes on `(PlayerId, IsEquipped)`
- Raw SQL optimization, connection pooling, async `QueryAsync`/`ExecuteAsync`
- FluentMigrator for migrations
- Parameterized queries always — prevent SQL injection

## Security

- Verify every packet command server-side (Zero Trust)
- Disconnect malformed clients immediately (fail-secure)
- Stateless verification — pull sensitive data from DB/Redis, not client
- Rate limiting per CommandId per session
- JWT authentication for REST API
- TLV validation with bounds checking
- Secrets in `.env` only, never in source. Maintain `.env.example`.

## Code Standards

- C#: PascalCase. Private fields: _camelCase. JS/TS: camelCase. Constants: UPPER_SNAKE.
- SRP/DRY/KISS principles.
- Intent-revealing naming.
- No boilerplate code.
- Build: `dotnet build` | Frontend: `npx expo check`
- Always validate against J2ME decompiled source.

## Workflows

- `/debug` — Gather → Hypothesize (3 causes) → Isolate → Fix (Core first) → Verify → Cleanup
- `/plan` — Read status → Identify layers → Output implementation_plan.md → No code during planning
- `/status` — Read status/bugs → Scan directories → Report phase/tasks/blockers

## Reference Skills (in .agent/skills/)

| Skill | Use When |
|-------|----------|
| `architecture/` | Architecture decisions, layer design, API patterns |
| `binary-protocol/` | TLV packet implementation |
| `clean-code/` | Code style, naming, file organization |
| `database-design/` | PostgreSQL schema, Dapper queries |
| `frontend-design/` | React Native + Skia, UI/UX |
| `game-mechanics/` | Match-3, battle, map protocol |
| `vulnerability-scanner/` | Security audit, OWASP checks |
| `powershell-windows/` | Windows scripting pitfalls |

## Validation Scripts

```bash
python .agent/skills/vulnerability-scanner/scripts/security_scan.py   # OWASP scan
python .agent/skills/database-design/scripts/schema_validator.py      # Schema check
python .agent/skills/api-patterns/scripts/api_validator.py            # API check
python .agent/skills/frontend-design/scripts/accessibility_checker.py # WCAG audit
python .agent/skills/frontend-design/scripts/ux_audit.py              # UX review (80+ checks)
```
