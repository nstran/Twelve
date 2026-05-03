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

    /// <summary>
    /// Raw item ids that are already used by server-authoritative gameplay flows.
    /// Keep values identical to original/remake-config ids; cast only at API/storage boundaries.
    /// </summary>
    public enum PlayerItemId
    {
        /// <summary>
        /// Remake policy (2026-05-03): chicken/normal egg raw itemId from current ItemCatalog seed.
        /// Official Java server reward pool/rate is still pending/unverified.
        /// </summary>
        ChickenEgg = 30094,

        /// <summary>
        /// Remake policy (2026-05-03): ostrich egg raw itemId from current ItemCatalog seed.
        /// Official Java server reward pool/rate is still pending/unverified.
        /// </summary>
        OstrichEgg = 30095,

        /// <summary>
        /// Remake policy (2026-05-03): dinosaur egg raw itemId from current ItemCatalog seed.
        /// Official Java server reward pool/rate is still pending/unverified.
        /// </summary>
        DinosaurEgg = 30096,

        /// <summary>
        /// Remake policy (2026-05-03): phoenix egg raw itemId from current ItemCatalog seed.
        /// Official Java server reward pool/rate is still pending/unverified.
        /// </summary>
        PhoenixEgg = 30097,

        /// <summary>
        /// Remake policy (2026-05-03): dragon egg raw itemId from current ItemCatalog seed.
        /// Official Java server reward pool/rate is still pending/unverified.
        /// </summary>
        DragonEgg = 30098,

        /// <summary>
        /// Remake policy (2026-05-03): consumed by cmd 48 repair flow, restores equipment durability to max.
        /// </summary>
        RepairHammer = 30099,
    }

    public enum PlayerItemKind
    {
        Consumable = 0,
        Material = 1,
        Egg = 2,
        RepairMaterial = 3,
    }

    public enum PlayerItemEvidenceStatus
    {
        PendingUnverified = 0,
        RemakePolicy = 1,
        JavaEvidence = 2,
    }

    public sealed record PlayerInventoryItemView(
        int ItemId,
        string DisplayName,
        string Description,
        int Quantity,
        int StackCap,
        bool IsUsable,
        int HealAmount,
        string IconKind,
        PlayerItemKind Kind,
        PlayerItemEvidenceStatus EvidenceStatus,
        int? ResourceId,
        int? IconId
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
        bool IsBroken,
        bool ContributesStats,
        bool CanRepair,
        bool Tradeable,
        bool CanUpgrade,
        string UpgradeStatus,
        int BonusCuongLuc,
        int BonusThanPhap,
        int BonusNoiLuc,
        int BonusTheLuc,
        int BonusAttack,
        int BonusAttackPercent,
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
        int CurrentMp,
        int MaxMp,
        int CurrentPower,
        int MaxPower,
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
        int EquipCuongLuc,
        int EquipThanPhap,
        int EquipNoiLuc,
        int EquipTheLuc,
        int EquipFlatAttack,
        int EquipAttackPercent,
        int EquipCrit,
        int EquipDefense,
        int EquipDodge,
        int EquipMaxHp,
        float MapMoveSpeed,
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

    // cmd 37 mode 2 — vứt bỏ equipment theo key, chỉ cho phép khi không đang mặc
    public sealed record PlayerDiscardEquipmentRuntimeRequest(
        string Username,
        IReadOnlyList<string> EquipKeys
    );

    // cmd 83 — bỏ item theo quantity
    public sealed record PlayerDiscardItemRuntimeRequest(
        string Username,
        int ItemId,
        int Quantity
    );

    // cmd 48 — áp vật phẩm sửa chữa lên equipment, khôi phục ll.p = ll.q
    public sealed record PlayerRepairEquipmentRuntimeRequest(
        string Username,
        string EquipKey,
        int RepairItemId
    );

    // Upgrade skeleton — remake policy 2026-05-03:
    // must unequip first; real roll is disabled until original stone/charm list is verified.
    public sealed record PlayerUpgradeEquipmentRuntimeRequest(
        string Username,
        string EquipKey,
        IReadOnlyList<int> MaterialItemIds
    );

    // Open-egg / đập trứng skeleton — remake policy 2026-05-03:
    // egg costs and reward-pool requirement are user-confirmed policy, not Java server evidence.
    public sealed record PlayerOpenEggRuntimeRequest(
        string Username,
        int EggItemId
    );
}
