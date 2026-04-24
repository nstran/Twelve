using Twelve.Core.GameLogic;

namespace Twelve.Core.Players
{
    public sealed record PlayerEquipmentDefinition(
        string TemplateKey,
        string DisplayName,
        string Summary,
        int Slot,
        int ResourceId,
        int Level,
        int RequiredLevel,
        string IconKind,
        int Rank,
        int ElementIcon,
        int Gender,
        int Durability,
        int MaxDurability,
        bool Tradeable,
        long RepairCost,
        bool IsEnabled,
        PlayerStatModifier Modifier);
}
