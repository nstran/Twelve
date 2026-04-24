using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Entities;
using Twelve.Core.Tlv;

namespace Twelve.Application.Players
{
    public sealed class PlayerCharacterPacketFactory
    {
        public byte[] CreateCharacterInfoPacket(PlayerAggregate aggregate)
        {
            var tags = BuildCharacterInfoTags(aggregate);
            var payload = tags.SelectMany(tag => tag).ToArray();
            return TlvCodec.BuildPacket(CommandCode.CharacterInfo, payload, tags.Count);
        }

        private static List<byte[]> BuildCharacterInfoTags(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
            var appearance = aggregate.Appearance;

            return new List<byte[]>
            {
                // Current client appearance tags.
                TlvCodec.MakeTag((int)TagCode.GenderStyle, appearance.Gender),
                TlvCodec.MakeTag((int)TagCode.Element, appearance.StorageElement),
                TlvCodec.MakeTag((int)TagCode.Face, appearance.FaceStyle ?? 0),
                TlvCodec.MakeTag((int)TagCode.HairStyle, appearance.HairStyle ?? 0),
                TlvCodec.MakeTag((int)TagCode.HairColor, appearance.HairColor ?? 0),
                TlvCodec.MakeTag((int)TagCode.SkinColor, appearance.SkinColor ?? 0),

                // Java lh truth tags used by profile/status/map/battle.
                TlvCodec.MakeTag((int)TagCode.Username, player.Username),
                TlvCodec.MakeTag((int)TagCode.RawElement, appearance.RawElementCode),
                TlvCodec.MakeTag((int)TagCode.RawGender, appearance.Gender),
                TlvCodec.MakeTag((int)TagCode.Level, player.Level),
                TlvCodec.MakeTag((int)TagCode.CurrentHp, player.Hp),
                TlvCodec.MakeTag((int)TagCode.JavaMaxHp, player.MaxHp),
                TlvCodec.MakeTag((int)TagCode.CurrentMp, player.Mp),
                TlvCodec.MakeTag((int)TagCode.MaxMp, player.MaxMp),
                TlvCodec.MakeTag((int)TagCode.CurrentPower, player.Power),
                TlvCodec.MakeTag((int)TagCode.MaxPower, player.MaxPower),
                TlvCodec.MakeTag((int)TagCode.ExperienceValue, player.Exp),
                TlvCodec.MakeTag((int)TagCode.ExperienceFloor, player.ExpFloor),
                TlvCodec.MakeTag((int)TagCode.ExperienceCeiling, player.ExpCeiling),
                TlvCodec.MakeTag((int)TagCode.KenProgress, player.KenProgress),
                TlvCodec.MakeTag((int)TagCode.KenProgressCap, player.KenProgressCap),
                TlvCodec.MakeTag((int)TagCode.CuongLuc, player.CuongLuc),
                TlvCodec.MakeTag((int)TagCode.ThanPhap, player.ThanPhap),
                TlvCodec.MakeTag((int)TagCode.NoiLuc, player.NoiLuc),
                TlvCodec.MakeTag((int)TagCode.TheLuc, player.TheLuc),
                TlvCodec.MakeTag((int)TagCode.BonusCuongLuc, player.BonusCuongLuc),
                TlvCodec.MakeTag((int)TagCode.BonusThanPhap, player.BonusThanPhap),
                TlvCodec.MakeTag((int)TagCode.BonusNoiLuc, player.BonusNoiLuc),
                TlvCodec.MakeTag((int)TagCode.BonusTheLuc, player.BonusTheLuc),
                TlvCodec.MakeTag((int)TagCode.FreePoints, player.FreePoints),
                TlvCodec.MakeTag((int)TagCode.SkillPoints, player.SkillPoints),
                TlvCodec.MakeTag((int)TagCode.Honor, player.Honor),
                TlvCodec.MakeTag((int)TagCode.TitlePrimary, ResolveTitlePrimary(player)),
                TlvCodec.MakeTag((int)TagCode.TitleSecondary, player.TitleSub ?? string.Empty),

                // Temporary client-new combat tags until Java-faithful derived tags are finalized.
                TlvCodec.MakeTag((int)TagCode.TanCong, player.DerivedMinDamage),
                TlvCodec.MakeTag((int)TagCode.ChinhXac, player.DerivedHit),
                TlvCodec.MakeTag((int)TagCode.PThu, player.DerivedDefense),
                TlvCodec.MakeTag((int)TagCode.NeTranh, player.DerivedDodge),
                TlvCodec.MakeTag((int)TagCode.ChiMang, player.DerivedCrit),
            };
        }

        private static string ResolveTitlePrimary(Player player)
        {
            var title = !string.IsNullOrWhiteSpace(player.TitleMain)
                ? player.TitleMain
                : player.TitleRank ?? string.Empty;

            return title switch
            {
                "Tan thu" => "Tân Binh",
                "Hao kiet" => "Hào Kiệt",
                "Cao thu" => "Cao Thủ",
                _ => title
            };
        }
    }
}
