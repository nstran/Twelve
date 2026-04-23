using System.Collections.Generic;
using System.Text;
using Twelve.Core.Monsters;
using Twelve.Core.Tlv;

namespace Twelve.Application.Monsters
{
    internal static class MonsterRuntimePacketFactory
    {
        private const int TagMapId = 20;
        private const int TagRoomId = 30;
        private const int TagMode = 40;
        private const int TagRecord = 9;
        private const int TagMonsterKey = 9;
        private const int TagDisplayName = 26;
        private const int TagDisplayLevel = 27;
        private const int TagVisualTypeByte = 15;
        private const int TagIqValue = 129;
        private const int TagSpawnCount = 106;
        private const int TagNameColorMode = 107;
        private const int TagSpawnGroupKey = 108;
        private const int TagSpawnInstanceIndex = 109;

        public static byte[] BuildRuntimePacket(
            string mapId,
            int roomId,
            byte mode,
            IReadOnlyList<MapMonsterEncounter> encounters,
            IReadOnlyDictionary<string, MonsterSpawnTemplate> spawnTemplates)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(TagMapId, mapId));
            tags.AddRange(TlvCodec.MakeTag(TagRoomId, roomId));
            tags.AddRange(TlvCodec.MakeTag(TagMode, mode));

            foreach (var encounter in encounters)
            {
                spawnTemplates.TryGetValue(encounter.SpawnTemplateKey, out var spawnTemplate);
                tags.AddRange(TlvCodec.MakeTag(TagRecord, BuildEncounterRecord(encounter, spawnTemplate)));
            }

            return tags.ToArray();
        }

        public static byte[] BuildBootstrapJsonPayload(string json) =>
            Encoding.UTF8.GetBytes(json);

        private static byte[] BuildEncounterRecord(
            MapMonsterEncounter encounter,
            MonsterSpawnTemplate? spawnTemplate)
        {
            var tags = new List<byte>();
            tags.AddRange(TlvCodec.MakeTag(TagMonsterKey, encounter.MonsterKey));
            tags.AddRange(TlvCodec.MakeTag(TagSpawnGroupKey, encounter.SpawnGroupKey));
            tags.AddRange(TlvCodec.MakeTag(TagSpawnInstanceIndex, encounter.SpawnInstanceIndex));

            if (spawnTemplate is not null)
            {
                tags.AddRange(TlvCodec.MakeTag(TagDisplayName, spawnTemplate.DisplayName));
                tags.AddRange(TlvCodec.MakeTag(TagDisplayLevel, spawnTemplate.DisplayLevel));
                tags.AddRange(TlvCodec.MakeTag(TagVisualTypeByte, spawnTemplate.VisualTypeByte));
                tags.AddRange(TlvCodec.MakeTag(TagIqValue, spawnTemplate.IqValue));
                tags.AddRange(TlvCodec.MakeTag(TagSpawnCount, spawnTemplate.SpawnCount));
                tags.AddRange(TlvCodec.MakeTag(TagNameColorMode, spawnTemplate.NameColorMode));
            }

            return tags.ToArray();
        }
    }
}
