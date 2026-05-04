using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Entities;
using Twelve.Core.Players;

namespace Twelve.Application.Players
{
    public interface IEquipmentCombineService
    {
        EquipmentCombineApplyResult Apply(PlayerAggregate aggregate, PlayerCombineEquipmentRuntimeRequest request);
    }

    public sealed record EquipmentCombineApplyResult(
        IReadOnlyList<PlayerEquipmentEntry> Equipment,
        IReadOnlyList<PlayerItemStack> Inventory,
        string Message,
        bool Success,
        long QuanCost);

    /// <summary>
    /// Java evidence: ks.java/ky.java only proves cmd 99/100 TLV shape.
    /// Remake policy 2026-05-05: combine consumes one unequipped equipment target plus
    /// Huyet thach/Kim thach to add one enhancement level deterministically, capped at +15.
    /// </summary>
    public sealed class EquipmentCombineService : IEquipmentCombineService
    {
        private const int MaxLevel = 15;

        public EquipmentCombineApplyResult Apply(PlayerAggregate aggregate, PlayerCombineEquipmentRuntimeRequest request)
        {
            var equipment = aggregate.Equipment.ToList();
            var inventory = aggregate.Inventory.ToList();
            var index = equipment.FindIndex(entry => entry.EquipKey == request.EquipKey);
            if (index < 0)
            {
                return Fail(equipment, inventory, "Khong tim thay trang bi.");
            }

            var target = equipment[index];
            if (target.IsEquipped)
            {
                return Fail(equipment, inventory, "Phai thao trang bi truoc khi ket hop.");
            }

            if (target.Level >= MaxLevel)
            {
                return Fail(equipment, inventory, "Trang bi da dat cap ket hop toi da.");
            }

            var materialIds = request.MaterialItemIds ?? [];
            var policy = EquipmentCombinePolicy.Resolve(target.Level);
            if (!Contains(materialIds, (int)PlayerItemId.HuyetThach))
            {
                return Fail(equipment, inventory, "Can Huyet thach de ket hop.");
            }
            if (!Contains(materialIds, (int)PlayerItemId.KimThach))
            {
                return Fail(equipment, inventory, "Can Kim thach de ket hop.");
            }
            if (aggregate.Core.Gold < policy.QuanCost)
            {
                return Fail(equipment, inventory, "Khong du Quan de ket hop.");
            }

            if (!HasItemQuantity(inventory, (int)PlayerItemId.HuyetThach, policy.HuyetThachQuantity) ||
                !HasItemQuantity(inventory, (int)PlayerItemId.KimThach, policy.KimThachQuantity))
            {
                return Fail(equipment, inventory, "Khong du so luong Huyet thach/Kim thach.");
            }

            ConsumeQuantity(inventory, (int)PlayerItemId.HuyetThach, policy.HuyetThachQuantity);
            ConsumeQuantity(inventory, (int)PlayerItemId.KimThach, policy.KimThachQuantity);

            var nextLevel = target.Level + 1;
            equipment[index] = CloneWithLevel(target, nextLevel);
            return new EquipmentCombineApplyResult(
                equipment,
                inventory,
                $"Ket hop thanh cong len +{nextLevel}. Phi: {policy.QuanCost} Quan.",
                Success: true,
                policy.QuanCost);
        }

        private static EquipmentCombineApplyResult Fail(
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            IReadOnlyList<PlayerItemStack> inventory,
            string message) =>
            new(equipment, inventory, message, Success: false, QuanCost: 0);

        private static bool Contains(IReadOnlyList<int> values, int target)
        {
            foreach (var value in values)
            {
                if (value == target)
                {
                    return true;
                }
            }
            return false;
        }

        private static bool HasItemQuantity(IReadOnlyList<PlayerItemStack> inventory, int itemId, int quantity)
        {
            foreach (var stack in inventory)
            {
                if (stack.ItemId == itemId && stack.Quantity >= quantity)
                {
                    return true;
                }
            }
            return false;
        }

        private static void ConsumeQuantity(List<PlayerItemStack> inventory, int itemId, int quantity)
        {
            var index = inventory.FindIndex(entry => entry.ItemId == itemId && entry.Quantity >= quantity);
            if (index < 0)
            {
                return;
            }

            var current = inventory[index];
            var nextQty = current.Quantity - quantity;
            if (nextQty <= 0)
            {
                inventory.RemoveAt(index);
                return;
            }

            inventory[index] = new PlayerItemStack
            {
                ItemId = current.ItemId,
                Quantity = nextQty,
                RawJson = current.RawJson
            };
        }

        private static PlayerEquipmentEntry CloneWithLevel(PlayerEquipmentEntry source, int level) =>
            new()
            {
                EquipKey = source.EquipKey,
                TemplateKey = source.TemplateKey,
                Slot = source.Slot,
                ResourceId = source.ResourceId,
                Level = level,
                Durability = source.Durability,
                MaxDurability = source.MaxDurability,
                IsEquipped = source.IsEquipped,
                RawJson = source.RawJson
            };
    }

    public sealed record EquipmentCombinePolicy(
        int FromLevel,
        int HuyetThachQuantity,
        int KimThachQuantity,
        long QuanCost)
    {
        public static EquipmentCombinePolicy Resolve(int currentLevel)
        {
            var targetLevel = currentLevel + 1;
            var huyetQty = targetLevel * 2;
            var kimQty = 1;
            if (targetLevel >= 5)
            {
                kimQty = 2;
            }
            if (targetLevel >= 10)
            {
                kimQty = 4;
            }

            return new EquipmentCombinePolicy(
                currentLevel,
                huyetQty,
                kimQty,
                targetLevel * targetLevel * 1500L);
        }
    }
}
