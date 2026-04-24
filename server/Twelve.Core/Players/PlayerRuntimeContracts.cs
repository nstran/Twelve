using System.Collections.Generic;

namespace Twelve.Core.Players
{
    public enum PlayerStatKind
    {
        CuongLuc = 0,
        ThanPhap = 1,
        NoiLuc = 2,
        TheLuc = 3,
    }

    public sealed record PlayerInventoryItemView(
        int ItemId,
        string DisplayName,
        string Description,
        int Quantity,
        int StackCap,
        bool IsUsable,
        int HealAmount,
        string IconKind
    );

    public sealed record PlayerEquipmentItemView(
        string EquipKey,
        string DisplayName,
        string Summary,
        int Slot,
        int ResourceId,
        int Level,
        int RequiredLevel,
        bool IsEquipped,
        string IconKind,
        int Rank,
        int ElementIcon,
        int Gender,
        int Durability,
        int MaxDurability,
        bool Tradeable,
        int BonusCuongLuc,
        int BonusThanPhap,
        int BonusNoiLuc,
        int BonusTheLuc,
        int BonusAttack,
        int BonusDefense,
        int BonusDodge,
        int BonusCrit,
        int BonusMaxHp
    );

    public sealed record PlayerSkillNodeView(
        int FamilyCode,
        int Level,
        int MaxLevel,
        int RequiredLevel,
        int Cost,
        bool CanUpgrade
    );

    public sealed record PlayerRuntimeSnapshot(
        string Username,
        int Element,
        int Level,
        int CurrentHp,
        int MaxHp,
        long Exp,
        long ExpFloor,
        long ExpCeiling,
        long Gold,
        long QuanProgress,
        long QuanProgressCap,
        int FreePoints,
        int SkillPoints,
        int CuongLuc,
        int ThanPhap,
        int NoiLuc,
        int TheLuc,
        int BonusCuongLuc,
        int BonusThanPhap,
        int BonusNoiLuc,
        int BonusTheLuc,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int Dodge,
        int Hit,
        int Crit,
        IReadOnlyList<PlayerInventoryItemView> Inventory,
        IReadOnlyList<PlayerEquipmentItemView> Equipment,
        IReadOnlyList<PlayerSkillNodeView> Skills
    );

    public sealed record PlayerRuntimeResponse(
        PlayerRuntimeSnapshot Snapshot,
        string? Message = null
    );

    public sealed record PlayerRuntimeRequest(
        string Username
    );

    public sealed record PlayerAllocateStatRuntimeRequest(
        string Username,
        PlayerStatKind Stat,
        int Amount = 1
    );

    public sealed record PlayerAllocateSkillRuntimeRequest(
        string Username,
        int FamilyCode
    );

    public sealed record PlayerEquipmentRuntimeRequest(
        string Username,
        string EquipKey,
        bool Equip
    );

    public sealed record PlayerEquipmentLoadoutRuntimeRequest(
        string Username,
        IReadOnlyList<string> EquipKeys
    );

    public sealed record PlayerUseItemRuntimeRequest(
        string Username,
        int ItemId
    );
}
