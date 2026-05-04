using System.Collections.Generic;
using System.IO;
using System.Text;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Npcs
{
    internal static class MissionPacketFactory
    {
        private const int TagMissionId = 77;
        private const int TagTitle = 26;
        private const int TagDescription = 79;
        private const int TagTaskRawValue = 80;
        private const int TagTaskText = 81;
        private const int TagStatusFlag = 100;
        private const int TagPrice = 132;
        private const int TagRewardLine = 1;
        private const int TagMessage = 149;

        public static byte[] BuildMissionList(IReadOnlyList<MissionSummary> missions)
        {
            using var payload = new MemoryStream();
            foreach (var mission in missions)
            {
                using var record = new MemoryStream();
                record.Write(TlvCodec.MakeTag(TagTitle, mission.Title));
                record.Write(TlvCodec.MakeTag(TagStatusFlag, ToStatusFlag(mission.PlayerStatus)));
                var recordBytes = record.ToArray();

                payload.WriteByte(TagMissionId);
                WriteInt32(payload, recordBytes.Length + Encoding.UTF8.GetByteCount(mission.MissionKey));
                var missionKeyBytes = Encoding.UTF8.GetBytes(mission.MissionKey);
                payload.Write(missionKeyBytes, 0, missionKeyBytes.Length);
                payload.Write(recordBytes, 0, recordBytes.Length);
            }

            return TlvCodec.BuildPacket(CommandCode.MissionList, payload.ToArray(), missions.Count);
        }

        public static byte[] BuildMissionDetail(MissionDetail mission)
        {
            using var payload = new MemoryStream();
            payload.Write(TlvCodec.MakeTag(TagMissionId, mission.MissionKey));
            payload.Write(TlvCodec.MakeTag(TagTitle, mission.Title));
            payload.Write(TlvCodec.MakeTag(TagDescription, mission.Description));
            payload.Write(TlvCodec.MakeTag(TagPrice, 0L));
            payload.Write(TlvCodec.MakeTag(TagStatusFlag, ToStatusFlag(mission.PlayerStatus)));

            foreach (var objective in mission.Objectives)
            {
                using var task = new MemoryStream();
                task.Write(TlvCodec.MakeTag(TagTaskText, BuildObjectiveText(objective)));
                payload.Write(TlvCodec.MakeTag(TagTaskRawValue, task.ToArray()));
            }

            foreach (var rewardLine in BuildRewardLines(mission))
            {
                payload.Write(TlvCodec.MakeTag(TagRewardLine, rewardLine));
            }

            return TlvCodec.BuildPacket(CommandCode.MissionDetail, payload.ToArray());
        }

        public static byte[] BuildMissionTaskNotification(MissionProgressUpdate update)
        {
            using var payload = new MemoryStream();
            payload.Write(TlvCodec.MakeTag(TagMissionId, update.MissionKey));
            payload.Write(TlvCodec.MakeTag(TagTaskRawValue, 0));
            payload.Write(TlvCodec.MakeTag(TagTaskText, update.ObjectiveText));
            payload.Write(TlvCodec.MakeTag(TagMessage, update.ObjectiveCompleted ? "Muc tieu nhiem vu da hoan thanh." : "Tien do nhiem vu da cap nhat."));
            return TlvCodec.BuildPacket(CommandCode.MissionTaskNotify, payload.ToArray());
        }

        public static byte[] BuildMissionUpdate(MissionProgressUpdate update)
        {
            using var payload = new MemoryStream();
            payload.Write(TlvCodec.MakeTag(TagMissionId, update.MissionKey));
            using var task = new MemoryStream();
            task.Write(TlvCodec.MakeTag(TagTaskText, update.ObjectiveText));
            payload.Write(TlvCodec.MakeTag(TagTaskRawValue, task.ToArray()));
            return TlvCodec.BuildPacket(CommandCode.MissionUpdate, payload.ToArray());
        }

        public static byte[] BuildMissionNotification(MissionProgressUpdate update)
        {
            using var payload = new MemoryStream();
            payload.Write(TlvCodec.MakeTag(TagMissionId, update.MissionKey));
            payload.Write(TlvCodec.MakeTag(TagTitle, update.MissionTitle));
            payload.Write(TlvCodec.MakeTag(TagMessage, update.MissionCompleted ? "Nhiem vu da hoan thanh." : "Nhiem vu da cap nhat."));
            return TlvCodec.BuildPacket(CommandCode.MissionNotify, payload.ToArray());
        }

        public static byte[] BuildRewardClaimNotification(MissionDetail mission, MissionRewardClaimResult claim)
        {
            using var payload = new MemoryStream();
            payload.Write(TlvCodec.MakeTag(TagMissionId, mission.MissionKey));
            payload.Write(TlvCodec.MakeTag(TagTitle, mission.Title));
            payload.Write(TlvCodec.MakeTag(TagMessage, "Da nhan thuong nhiem vu."));

            if (claim.ExpGained > 0)
                payload.Write(TlvCodec.MakeTag(TagRewardLine, $"EXP x{claim.ExpGained}"));

            foreach (var item in claim.GrantedItems)
            {
                payload.Write(TlvCodec.MakeTag(TagRewardLine, item));
            }

            foreach (var equipment in claim.GrantedEquipment)
            {
                payload.Write(TlvCodec.MakeTag(TagRewardLine, equipment));
            }

            return TlvCodec.BuildPacket(CommandCode.MissionNotify, payload.ToArray());
        }

        private static byte ToStatusFlag(MissionPlayerStatus status)
        {
            if (status == MissionPlayerStatus.Accepted || status == MissionPlayerStatus.Completed)
                return 1;

            return 0;
        }

        private static string BuildObjectiveText(MissionObjective objective) =>
            $"{objective.ObjectiveType}: {objective.TargetKey} x{objective.RequiredAmount}";

        private static IReadOnlyList<string> BuildRewardLines(MissionDetail mission)
        {
            if (!string.IsNullOrWhiteSpace(mission.RewardText))
                return mission.RewardText.Split(';', System.StringSplitOptions.TrimEntries | System.StringSplitOptions.RemoveEmptyEntries);

            var lines = new List<string>();
            foreach (var reward in mission.Rewards)
            {
                var key = string.IsNullOrWhiteSpace(reward.RewardKey) ? "" : $" {reward.RewardKey}";
                lines.Add($"{reward.RewardType}{key} x{reward.Amount}");
            }

            return lines;
        }

        private static void WriteInt32(Stream stream, int value)
        {
            stream.WriteByte((byte)((value >> 24) & 0xFF));
            stream.WriteByte((byte)((value >> 16) & 0xFF));
            stream.WriteByte((byte)((value >> 8) & 0xFF));
            stream.WriteByte((byte)(value & 0xFF));
        }
    }
}
