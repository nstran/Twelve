using System.Collections.Generic;

namespace Twelve.Core.Tlv
{
    public class PacketRequest
    {
        public int PacketType { get; }
        public int PayloadLength { get; }
        public int Command { get; }
        public byte[] Payload { get; }
        public IReadOnlyDictionary<int, byte[]> Tags { get; }

        public PacketRequest(int packetType, int payloadLength, int command, byte[] payload, IReadOnlyDictionary<int, byte[]> tags)
        {
            PacketType = packetType;
            PayloadLength = payloadLength;
            Command = command;
            Payload = payload;
            Tags = tags;
        }

        public string? GetStringTag(int tagId)
        {
            if (Tags.TryGetValue(tagId, out var value))
            {
                return System.Text.Encoding.UTF8.GetString(value);
            }
            return null;
        }

        public byte[]? GetRawTag(int tagId)
        {
            Tags.TryGetValue(tagId, out var value);
            return value;
        }
    }
}
