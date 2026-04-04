# 🎮 Game Mechanics Skill (Loạn 12 Sứ Quân)

This skill defines the core game logic and binary protocols for the 'Loạn 12 Sứ Quân' modernization project.

## 🍱 Match-3 Battle Logic
- **Grid**: 8x8 or 8x9.
- **Gem Effects**:
  - ⭐ **EXP**: Increases level (bottom bar).
  - 💧 **MP**: Increases Mana (green bar).
  - 🍑 **Rage**: Increases 'Thanh Nộ' (yellow bar).
  - 🏆 **Gold**: 10,000 Gold = 10,000 KEN (Max Kill).
  - ⚔️ **Physical**: Immediate damage.
- **Elemental Skills**: Match-4/5 triggers specific effects based on the gem's element (Hỏa, Lôi, Thủy).

## 🗺️ Map Navigation Protocol (Cmd 11)
- **Tag 56/57**: Width/Height.
- **Tag 55**: Ground Layer (Raw Tile IDs).
- **Tag 54**: Decoration Layer.
- **Tag 61**: Logic/Collision Layer.
- **Tag 60**: Tileset ID.

## 🛠️ Implementation Rules
- **Binary Format**: Use TLV (Tag-Length-Value) as defined in `TlvCodec.java`.
- **C# Porting**: Map Java's `DataOutputStream` writes to C#'s `BinaryWriter` or `NetworkStream`.
- **Latency**: All battle logic must be validated on the server side to prevent cheating.
