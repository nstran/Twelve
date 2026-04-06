using System.Buffers.Binary;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Twelve.Core.Tlv
{
    public static class TlvCodec
    {
        public static PacketRequest ReadAuthRequest(BinaryReader reader)
        {
            // PacketType (1 byte)
            int packetType = reader.ReadByte();
            // Salt (4 bytes) - Skip for now
            reader.ReadBytes(4);
            // PayloadLength (4 bytes, Big-Endian)
            byte[] lengthBytes = reader.ReadBytes(4);
            int payloadLength = BinaryPrimitives.ReadInt32BigEndian(lengthBytes);
            // Command (1 byte)
            int command = reader.ReadByte();
            // Payload
            byte[] payload = reader.ReadBytes(System.Math.Max(0, payloadLength));
            
            return new PacketRequest(packetType, payloadLength, command, payload, ParseTags(payload));
        }

        public static PacketRequest ReadGameRequest(BinaryReader reader)
        {
            // TotalLength (4 bytes, Big-Endian)
            byte[] totalLengthBytes = reader.ReadBytes(4);
            int totalLength = BinaryPrimitives.ReadInt32BigEndian(totalLengthBytes);
            // PacketType (1 byte)
            int packetType = reader.ReadByte();
            // Salt (4 bytes) - Skip for now
            reader.ReadBytes(4);
            // Command (1 byte)
            int command = reader.ReadByte();
            // PayloadLength = TotalLength - PacketType(1) - Salt(4) - Command(1)
            int payloadLength = System.Math.Max(0, totalLength - 6);
            byte[] payload = reader.ReadBytes(payloadLength);

            return new PacketRequest(packetType, payloadLength, command, payload, ParseTags(payload));
        }

        public static Dictionary<int, byte[]> ParseTags(byte[] payload)
        {
            var tags = new Dictionary<int, byte[]>();
            using (var ms = new MemoryStream(payload))
            using (var reader = new BinaryReader(ms))
            {
                while (ms.Position <= ms.Length - 5)
                {
                    int tagId = reader.ReadByte();
                    byte[] lengthBytes = reader.ReadBytes(4);
                    int length = BinaryPrimitives.ReadInt32BigEndian(lengthBytes);

                    if (length < 0 || ms.Position + length > ms.Length)
                    {
                        break;
                    }

                    byte[] value = reader.ReadBytes(length);
                    tags[tagId] = value;
                }
            }
            return tags;
        }

        public static byte[] MakeTag(int tagId, byte[] data)
        {
            byte[] tag = new byte[1 + 4 + data.Length];
            tag[0] = (byte)(tagId & 0xFF);
            BinaryPrimitives.WriteInt32BigEndian(tag.AsSpan(1, 4), data.Length);
            data.CopyTo(tag, 5);
            return tag;
        }

        public static byte[] MakeTag(int tagId, string value)
        {
            return MakeTag(tagId, Encoding.UTF8.GetBytes(value));
        }

        public static byte[] MakeTag(int tagId, int value)
        {
            byte[] data = new byte[4];
            BinaryPrimitives.WriteInt32BigEndian(data, value);
            return MakeTag(tagId, data);
        }

        public static byte[] MakeTag(int tagId, long value)
        {
            byte[] data = new byte[8];
            BinaryPrimitives.WriteInt64BigEndian(data, value);
            return MakeTag(tagId, data);
        }

        public static byte[] MakeTag(int tagId, byte value)
        {
            return MakeTag(tagId, new byte[] { value });
        }

        public static byte[] BuildPacket(CommandCode command, byte[] payload, int subCount = 0)
        {
            return BuildPacket((int)command, payload, subCount);
        }

        public static byte[] BuildPacket(int command, byte[] payload, int subCount = 0)
        {
            using (var ms = new MemoryStream())
            using (var bw = new BinaryWriter(ms))
            {
                // SubCount (Short, 2 bytes, Big-Endian)
                byte[] subCountBytes = new byte[2];
                BinaryPrimitives.WriteInt16BigEndian(subCountBytes, (short)subCount);
                bw.Write(subCountBytes);

                // PayloadLength (Int, 4 bytes, Big-Endian)
                byte[] lengthBytes = new byte[4];
                BinaryPrimitives.WriteInt32BigEndian(lengthBytes, payload.Length);
                bw.Write(lengthBytes);

                // Command (1 byte)
                bw.Write((byte)command);

                // Payload
                bw.Write(payload);

                return ms.ToArray();
            }
        }

        public static byte[] BuildEmptyPacket(CommandCode command)
        {
            return BuildPacket((int)command, System.Array.Empty<byte>(), 0);
        }

        public static byte[] BuildEmptyPacket(int command)
        {
            return BuildPacket(command, System.Array.Empty<byte>(), 0);
        }

        public static PacketRequest? Decode(byte[] header, Stream stream)
        {
            if (header.Length < 7) return null;

            // SubCount (2 bytes)
            int subCount = BinaryPrimitives.ReadInt16BigEndian(header.AsSpan(0, 2));
            // PayloadLength (4 bytes)
            int payloadLength = BinaryPrimitives.ReadInt32BigEndian(header.AsSpan(2, 4));
            // Command (1 byte)
            int command = header[6];

            byte[] payload = new byte[payloadLength];
            int read = 0;
            while (read < payloadLength)
            {
                int r = stream.Read(payload, read, payloadLength - read);
                if (r <= 0) break;
                read += r;
            }

            return new PacketRequest(0, payloadLength, command, payload, ParseTags(payload));
        }

        public static byte[] BuildSingleTagPacket(CommandCode command, TagCode tagId, string value)
        {
            return BuildSingleTagPacket((int)command, (int)tagId, value);
        }

        public static byte[] BuildSingleTagPacket(int command, int tagId, string value)
        {
            var tag = MakeTag(tagId, value);
            return BuildPacket(command, tag, 1);
        }
    }
}
