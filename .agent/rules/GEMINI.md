# 🐲 GEMINI.md - Protocol Elite (Loạn 12 Sứ Quân)

## 🌐 Language Protocol

- **Check Language**: Always read `.agent/rules/LANGUAGE.md` for response language.

## 🤖 Standard Protocols

- **Elite Standards**: Follow the protocols in [elite_protocols.md](file:///.agent/rules/shared/elite_protocols.md).
- **Clean Architecture**: Use .NET 9. Logic MUST reside in the **Core (Domain)** layer. Use Dapper for high-perf DB access.
- **Protocol**: Raw TCP + TLV (Tag-Length-Value) for game packets. Use `BinaryReader`/`BinaryWriter`.
- **Frontend (Mobile)**: React Native + Skia. Use `service-proxies.ts` (NSwag) for Web API sync.
- **Aesthetics**: No purple. Use "Technical, Sharp" aesthetics. Maintain the "Loạn 12 Sứ Quân" 2026 vibe.

## 🛠️ Verification

- **Stick to JAR**: Use `loan-12-su-quan.jar` and `d:\Twelve\decompiled\` as the absolute source of truth for all logic, packets, and assets.
- Build: `dotnet build`.
- Skills Check: Use `game-mechanics` skill for all battle/map logic.
- Specialist Audit: Every feature must be verified by the relevant agent.
