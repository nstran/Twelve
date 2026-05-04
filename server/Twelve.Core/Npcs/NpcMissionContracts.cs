using System.Collections.Generic;

namespace Twelve.Core.Npcs
{
    public sealed record NpcTalkContext(
        string NpcId,
        string DisplayName,
        IReadOnlyList<MissionSummary> Missions);

    public sealed record MissionSummary(
        string MissionKey,
        string Title,
        string Description,
        string RewardText,
        int SortOrder,
        MissionPlayerStatus PlayerStatus = MissionPlayerStatus.Available);

    public sealed record MissionDetail(
        string MissionKey,
        string Title,
        string Description,
        string RewardText,
        IReadOnlyList<MissionObjective> Objectives,
        IReadOnlyList<MissionReward> Rewards,
        MissionPlayerStatus PlayerStatus = MissionPlayerStatus.Available);

    public sealed record MissionObjective(
        string ObjectiveType,
        string TargetKey,
        int RequiredAmount,
        int SortOrder);

    public sealed record MissionReward(
        MissionRewardType RewardType,
        string? RewardKey,
        int Amount,
        int SortOrder);

    public sealed record MissionProgressUpdate(
        string MissionKey,
        string MissionTitle,
        string ObjectiveText,
        bool ObjectiveCompleted,
        bool MissionCompleted);

    public sealed record MissionRewardClaimResult(
        MissionRewardClaimStatus Status,
        MissionPlayerStatus PlayerStatus,
        long ExpGained,
        IReadOnlyList<string> GrantedItems,
        IReadOnlyList<string> GrantedEquipment);

    public enum MissionRewardClaimStatus
    {
        Claimed,
        NotCompleted,
        AlreadyClaimed,
        MissionNotFound,
        PlayerNotFound,
        InvalidRewardData
    }

    public enum MissionPlayerStatus
    {
        Available,
        Accepted,
        Completed,
        RewardClaimed,
        Canceled
    }

    public enum MissionRewardType
    {
        Exp,
        Item,
        Equipment
    }
}
