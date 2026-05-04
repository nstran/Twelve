using System;
using System.Collections.Generic;

namespace Twelve.Core.Entities
{
    public sealed class PlayerAggregate
    {
        public required Player Core { get; init; }
        public required PlayerAppearance Appearance { get; init; }
        public required PlayerStatSnapshot Stats { get; init; }
        public IReadOnlyList<PlayerEquipmentEntry> Equipment { get; init; } = Array.Empty<PlayerEquipmentEntry>();
        public IReadOnlyList<PlayerItemStack> Inventory { get; init; } = Array.Empty<PlayerItemStack>();
        public IReadOnlyList<PlayerSkillEntry> Skills { get; init; } = Array.Empty<PlayerSkillEntry>();
        public IReadOnlyList<PlayerMapOverlay> MapOverlays { get; init; } = Array.Empty<PlayerMapOverlay>();
        public PlayerWorldState WorldState { get; init; } = new();
    }

    public sealed class PlayerAppearance
    {
        public int Gender { get; init; }
        public int StorageElement { get; init; }
        public int RawElementCode { get; init; }
        public int? FaceStyle { get; init; }
        public int? HairStyle { get; init; }
        public int? HairColor { get; init; }
        public int? SkinColor { get; init; }
        public bool Hidden0 { get; init; }
        public bool Hidden1 { get; init; }
        public int SpecialActorForm { get; init; }
    }

    public sealed class PlayerStatSnapshot
    {
        public int CuongLuc { get; init; }
        public int ThanPhap { get; init; }
        public int NoiLuc { get; init; }
        public int TheLuc { get; init; }
        public int BonusCuongLuc { get; init; }
        public int BonusThanPhap { get; init; }
        public int BonusNoiLuc { get; init; }
        public int BonusTheLuc { get; init; }
        public int FreePoints { get; init; }
        public int SkillPoints { get; init; }
        public int MinDamage { get; init; }
        public int MaxDamage { get; init; }
        public int Defense { get; init; }
        public int Dodge { get; init; }
        public int Hit { get; init; }
        public int Crit { get; init; }
    }

    public enum PlayerEquipmentSlot
    {
        // Java evidence: ll.e is the authoritative equipment slot raw value.
        // ll.a[] slot table: index 0=Armor, 1=Weapon, 2=Helmet, 3=Boots, 5=Ring, 8=Wing.
        // Current code-readiness gate (EQUIPMENT_SYSTEM_RECONSTRUCTION.md §13.10) only enables
        // the gameplay slots proven/chosen for remake scope below. Unknown ll.e values must not
        // be remapped or inferred from decompile field names.
        // FIX 2026-05-04: Ring was incorrectly mapped to e=3 (Boots); corrected to e=5 per
        // Java evidence ll.a[] and EQUIPMENT_SYSTEM_RECONSTRUCTION.md §13.2.
        Armor = 0,
        Weapon = 1,
        Helmet = 2,
        // Boots = 3,  // Java evidence: e=3 is Boots/Giày; not enabled in gameplay Phase 1
        Ring = 5,      // Java evidence: e=5 is Ring/Nhẫn (ll.a[5] priority 9)
        Wing = 8
    }

    public enum PlayerEquipmentLocation
    {
        // Remake policy: PlayerEquipment currently stores inventory/equipped state as IsEquipped.
        // This enum names the domain state while preserving raw slot values at DB/API boundaries.
        Inventory = 0,
        Equipped = 1
    }

    public sealed class PlayerEquipmentEntry
    {
        public required string EquipKey { get; init; }
        public string? TemplateKey { get; init; }
        public int Slot { get; init; }
        public int ResourceId { get; init; }

        // Java evidence: ll.j/tag 27 is enhancement level; ll.p/tag 139 is current durability;
        // ll.q/tag 144 is max durability. Equipment instances persist durability separately from template.
        public int Level { get; init; }
        public int Durability { get; init; }
        public int MaxDurability { get; init; }
        public bool IsEquipped { get; init; }
        public PlayerEquipmentLocation Location => IsEquipped
            ? PlayerEquipmentLocation.Equipped
            : PlayerEquipmentLocation.Inventory;
        public string RawJson { get; init; } = "{}";
    }

    public sealed class PlayerItemStack
    {
        public int ItemId { get; init; }
        public int Quantity { get; init; }
        public string RawJson { get; init; } = "{}";
    }

    public sealed class PlayerSkillEntry
    {
        public int SkillId { get; init; }
        public int Level { get; init; }
        public string RawJson { get; init; } = "{}";
    }

    public sealed class PlayerMapOverlay
    {
        public int IconId { get; init; }
        public DateTime? EndsAt { get; init; }
        public long DurationMs { get; init; }
        public string RawJson { get; init; } = "{}";
    }

    public sealed class PlayerWorldState
    {
        public string MapId { get; init; } = "M1";
        public int RoomId { get; init; } = 1;
        public int X { get; init; }
        public int Y { get; init; }
        public int Direction { get; init; }
        public int ActionState { get; init; }
        public string? ActiveBattleSessionId { get; init; }
    }

}
