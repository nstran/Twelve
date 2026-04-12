# Project Twelve — Agent Context

> Long-term memory for all specialist agents. Read this + status files before any action.

## Mission

Revive and modernize "Loạn 12 Sứ Quân" (J2ME → .NET 9 + React Native, 2026 Edition).

## Before Any Action

1. Read `Twelve.Status.md` — current phase and progress
2. Read `Twelve.Bugs.md` — open issues
3. Read `research_notes.md` — J2ME source findings

## Stack

| Layer | Tech | Notes |
|-------|------|-------|
| Backend | .NET 9 (Clean Architecture) | Dapper, NOT EF Core |
| Frontend | React Native (Expo + Skia) | 60FPS target |
| Protocol | Custom TLV Binary | Big-Endian (Java) → Little-Endian (.NET) |
| Database | PostgreSQL | JSONB, FluentMigrator |
| Auth | JWT | REST endpoints |
| Modern Bridge | SignalR/WebSocket | For React Native client |

## Rules

- Read `.agent/rules/RULES.md` for full agent rules
- Read `.agent/rules/shared/elite_protocols.md` for shared protocols
- Language: respond in Vietnamese, code in English
