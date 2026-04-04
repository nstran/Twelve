---
trigger: always_on
---

# 🌐 Elite Shared Protocols (Game Dev Edition)

This file contains the common rules for all specialist agents to ensure consistency across the project.

## 🤖 Chief Engineer & Delegation

- **Direct Execution**: For all implementation tasks, you MUST execute code writing and modifications directly using your own tools.
- **Self-Reliance**: You are fully capable of handling architecture, planning, code generation, and debugging internally.
- **Full-Stack Role**: You plan, review, verify, and execute all tasks yourself.

## 🧹 Housekeeping & Cleanup

- **Build Logs**: Standardize on `build_error.log` for error output.
- **Immediate Cleanup**: ANY temporary build or log files MUST be deleted immediately after resolution/fix.
- **Git Hygiene**: Root plans `./{task-slug}.md` are ignored by `.gitignore`.

## 🏗️ Architecture Standards (.NET Core)

- **Clean Architecture**: Use **Core**, **Application**, and **Infrastructure** layers.
- **Core Layer (Domain)**: All game business logic (Combat calculations, EXP formulas, Map rules) MUST reside here.
- **Application Layer**: For orchestration, REST API controllers, and Socket handlers.
- **Infrastructure Layer**: For Database access (Dapper) and File storage.
- **No Over-engineering**: Avoid complex design patterns unless they solve a specific scalability problem for the game.

## 🎨 Mobile Frontend Standards (React Native)

- **Performance**: Use `react-native-skia` for heavy graphics (Map rendering, Battle board).
- **Styling**: Use standard `css-in-js` or separate Stylesheets. Avoid inline styles for complex components.
- **Assets**: All game assets (Sprites, Maps) must be optimized for mobile loading.
- **State Management**: Use lightweight state management (Zustand or Redux Toolkit) to handle player stats and inventory.

## 🛡️ Elite Configuration & Security

- **Environment-First**: Sensitive settings (DB Connection, API Keys, Secrets) MUST be stored in `.env` files. NEVER hardcode or commit them to source control.
- **Typed Options**: Use `IOptions<T>` and `IOptionsSnapshot<T>` in the Core layer to access settings in a type-safe manner.
- **Example Template**: Always maintain a `.env.example` file with placeholders for all required environment variables.
- **Payment Abstraction**: All payment portal logic MUST be hidden behind an `IPaymentProvider` interface to facilitate easy provider switching (e.g., Momo to VNPay).

## ⚔️ Game Dev Integrity

- **Binary Protocol**: Always verify Packet CMD IDs against the `game-mechanics` skill.
- **Anti-Cheat**: Never trust the client for game state (HP, Gold, Battle Result). All critical calculations happen on the server.
