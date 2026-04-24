using Twelve.Core.Players;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerRuntimeService
    {
        PlayerRuntimeResponse? GetSnapshot(PlayerRuntimeRequest request);
        PlayerRuntimeResponse? AllocateStat(PlayerAllocateStatRuntimeRequest request);
        PlayerRuntimeResponse? AllocateSkill(PlayerAllocateSkillRuntimeRequest request);
        PlayerRuntimeResponse? UpdateEquipment(PlayerEquipmentRuntimeRequest request);
        PlayerRuntimeResponse? PreviewEquipmentLoadout(PlayerEquipmentLoadoutRuntimeRequest request);
        PlayerRuntimeResponse? CommitEquipmentLoadout(PlayerEquipmentLoadoutRuntimeRequest request);
        PlayerRuntimeResponse? UseItem(PlayerUseItemRuntimeRequest request);
        PlayerRuntimeResponse? DiscardEquipment(PlayerDiscardEquipmentRuntimeRequest request);
        PlayerRuntimeResponse? DiscardItem(PlayerDiscardItemRuntimeRequest request);
        PlayerRuntimeResponse? RepairEquipment(PlayerRepairEquipmentRuntimeRequest request);
    }
}
