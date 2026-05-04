using System.Collections.Generic;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Npcs
{
    internal static class NpcRosterPacketFactory
    {
        private const int TagMapId = 20;
        private const int TagMode = 40;
        private const int TagRecord = 9;
        private const int TagNpcId = 9;
        private const int TagDisplayName = 26;
        private const int TagDisplayLevel = 27;
        private const int TagVisualTypeByte = 15;
        private const int TagTileX = 129;
        private const int TagTileY = 106;
        private const int TagNameColorMode = 107;

        public static byte[] BuildRosterPacket(
            string mapId,
            NpcRosterMode mode,
            IReadOnlyList<MapNpcRosterEntry> entries)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(TagMapId, mapId));
            tags.AddRange(TlvCodec.MakeTag(TagMode, (byte)mode));

            foreach (var entry in entries)
            {
                tags.AddRange(TlvCodec.MakeTag(TagRecord, BuildNpcRecord(entry)));
            }

            return tags.ToArray();
        }

        private static byte[] BuildNpcRecord(MapNpcRosterEntry entry)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(TagNpcId, entry.NpcId));
            tags.AddRange(TlvCodec.MakeTag(TagDisplayName, entry.DisplayName));
            tags.AddRange(TlvCodec.MakeTag(TagDisplayLevel, entry.DisplayLevel));
            tags.AddRange(TlvCodec.MakeTag(TagVisualTypeByte, entry.VisualTypeByte));
            tags.AddRange(TlvCodec.MakeTag(TagTileX, entry.TileX));
            tags.AddRange(TlvCodec.MakeTag(TagTileY, entry.TileY));
            tags.AddRange(TlvCodec.MakeTag(TagNameColorMode, entry.NameColorMode));
            return tags.ToArray();
        }
    }
}
