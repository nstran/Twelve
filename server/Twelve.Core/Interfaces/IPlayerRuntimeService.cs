using Twelve.Core.Players;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerRuntimeService
    {
        PlayerRuntimeResponse? GetSnapshot(PlayerRuntimeRequest request);
        PlayerRuntimeResponse? AllocateStat(PlayerAllocateStatRuntimeRequest request);
        PlayerRuntimeResponse? AllocateSkill(PlayerAllocateSkillRuntimeRequest request);
        PlayerRuntimeResponse? UpdateEquipment(PlayerEquipmentRuntimeRequest request);
        PlayerRuntimeResponse? UseItem(PlayerUseItemRuntimeRequest request);
    }
}
