# Binary Protocol Skill (Loạn 12 Sứ Quân)

## 📡 Protocol Overview (TLV)
- **Format**: [TotalLength (4)] [PacketType (1)] [Salt (4)] [CommandId (1)] [Payload (N)]
- **Tag Structure**: [TagId (1)] [DataLength (4)] [Data (N)]

## 🛠️ .NET Implementation Standards
- **Serialization**: Use `BinaryReader` and `BinaryWriter` for efficient stream processing.
- **Encoding**: Strings must use `UTF-8` (StandardCharsets.UTF_8 equivalent in Java).
- **Endianness**: J2ME `DataInputStream` is Big-Endian. .NET is Little-Endian by default; use `BinaryPrimitives.ReadInt32BigEndian` or custom wrappers.

## 📦 Core Cmd IDs (Ref: Java Source)
- **1**: Register / Login
- **11**: Map Info / Hotspots
- **13**: Select Map
- **29**: Join Map
- **43**: Scene Ready / Actors

## 🚀 Efficiency Rules
- **Buffers**: Use `ArrayPool<byte>` to avoid GC pressure for high-frequency packets.
- **Handlers**: Map `CmdId` to a specific Class inheriting from `IPacketHandler`.
- **Validation**: Strict boundary checks for all `DataLength` reads to prevent buffer overflow attacks.
