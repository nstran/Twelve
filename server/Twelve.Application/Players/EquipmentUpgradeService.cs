using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Entities;
using Twelve.Core.Players;

namespace Twelve.Application.Players
{
    public interface IEquipmentUpgradeService
    {
        EquipmentUpgradeApplyResult Apply(PlayerAggregate aggregate, PlayerUpgradeEquipmentRuntimeRequest request);
    }

    public sealed record EquipmentUpgradeApplyResult(
        IReadOnlyList<PlayerEquipmentEntry> Equipment,
        IReadOnlyList<PlayerItemStack> Inventory,
        string Message,
        bool Success,
        int ResultLevel,
        long QuanCost);

    /// <summary>
    /// Remake policy 2026-05-04, user-approved: every upgrade level uses both Huyet thach,
    /// Kim thach, Quan fee and optional luck charms. Java evidence currently proves cmd 96/97
    /// TLV shape only; original server material ids/rates are still pending.
    /// </summary>
    public sealed class EquipmentUpgradeService : IEquipmentUpgradeService
    {
        private const int MaxLevel = 15;
        private static readonly Random Rng = new();
        private static readonly object RngLock = new();

        public EquipmentUpgradeApplyResult Apply(PlayerAggregate aggregate, PlayerUpgradeEquipmentRuntimeRequest request)
        {
            var equipment = aggregate.Equipment.ToList();
            var inventory = aggregate.Inventory.ToList();
            var index = equipment.FindIndex(entry => entry.EquipKey == request.EquipKey);
            if (index < 0)
            {
                return Fail(equipment, inventory, "Khong tim thay trang bi.", 0);
            }

            var target = equipment[index];
            if (target.IsEquipped)
            {
                return Fail(equipment, inventory, "Phai thao trang bi truoc khi nang cap.", target.Level);
            }

            if (target.Level >= MaxLevel)
            {
                return Fail(equipment, inventory, "Trang bi da dat cap nang cap toi da.", target.Level);
            }

            var materialIds = request.MaterialItemIds ?? Array.Empty<int>();
            var policy = EquipmentUpgradePolicy.Resolve(target.Level);
            if (!Contains(materialIds, (int)PlayerItemId.HuyetThach))
            {
                return Fail(equipment, inventory, "Can Huyet thach de nang cap.", target.Level);
            }
            if (!Contains(materialIds, (int)PlayerItemId.KimThach))
            {
                return Fail(equipment, inventory, "Can Kim thach de nang cap.", target.Level);
            }
            if (aggregate.Core.Gold < policy.QuanCost)
            {
                return Fail(equipment, inventory, "Khong du Quan de nang cap.", target.Level);
            }

            if (!HasItemQuantity(inventory, (int)PlayerItemId.HuyetThach, policy.HuyetThachQuantity) ||
                !HasItemQuantity(inventory, (int)PlayerItemId.KimThach, policy.KimThachQuantity))
            {
                return Fail(equipment, inventory, "Khong du so luong Huyet thach/Kim thach.", target.Level);
            }

            var consumedIds = new List<int>();
            foreach (var charmId in EquipmentUpgradePolicy.LuckCharmIds)
            {
                if (Contains(materialIds, charmId) && HasItem(inventory, charmId))
                {
                    consumedIds.Add(charmId);
                }
            }

            ConsumeQuantity(inventory, (int)PlayerItemId.HuyetThach, policy.HuyetThachQuantity);
            ConsumeQuantity(inventory, (int)PlayerItemId.KimThach, policy.KimThachQuantity);

            var chanceBasisPoints = policy.SuccessBasisPoints + ResolveLuckBonus(consumedIds);
            if (chanceBasisPoints > 10000)
            {
                chanceBasisPoints = 10000;
            }

            var roll = NextRollBasisPoint();
            if (roll <= chanceBasisPoints)
            {
                var nextLevel = target.Level + 1;
                equipment[index] = CloneWithLevel(target, nextLevel);
                return new EquipmentUpgradeApplyResult(
                    equipment,
                    inventory,
                    $"Nang cap thanh cong len +{nextLevel}. Phi: {policy.QuanCost} Quan.",
                    Success: true,
                    ResultLevel: nextLevel,
                    QuanCost: policy.QuanCost);
            }

            var failedLevel = target.Level;
            if (target.Level >= 10)
            {
                failedLevel = target.Level - 1;
                equipment[index] = CloneWithLevel(target, failedLevel);
                return new EquipmentUpgradeApplyResult(
                    equipment,
                    inventory,
                    $"Nang cap that bai, trang bi giam xuong +{failedLevel}. Phi: {policy.QuanCost} Quan.",
                    Success: false,
                    ResultLevel: failedLevel,
                    QuanCost: policy.QuanCost);
            }

            return new EquipmentUpgradeApplyResult(
                equipment,
                inventory,
                $"Nang cap that bai, trang bi giu nguyen cap. Phi: {policy.QuanCost} Quan.",
                Success: false,
                ResultLevel: failedLevel,
                QuanCost: policy.QuanCost);
        }

        private static EquipmentUpgradeApplyResult Fail(
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            IReadOnlyList<PlayerItemStack> inventory,
            string message,
            int resultLevel) =>
            new(equipment, inventory, message, Success: false, ResultLevel: resultLevel, QuanCost: 0);

        private static int ResolveLuckBonus(IReadOnlyCollection<int> consumedIds)
        {
            var bonus = 0;
            if (consumedIds.Contains((int)PlayerItemId.LuckCharm1))
            {
                bonus += 500;
            }
            if (consumedIds.Contains((int)PlayerItemId.LuckCharm2))
            {
                bonus += 1000;
            }
            if (consumedIds.Contains((int)PlayerItemId.LuckCharm3))
            {
                bonus += 1500;
            }
            return bonus;
        }

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

        private static bool HasItem(IReadOnlyList<PlayerItemStack> inventory, int itemId)
        {
            foreach (var stack in inventory)
            {
                if (stack.ItemId == itemId && stack.Quantity > 0)
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

        private static int NextRollBasisPoint()
        {
            lock (RngLock)
            {
                return Rng.Next(1, 10001);
            }
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

    public sealed record EquipmentUpgradePolicy(
        int FromLevel,
        int HuyetThachQuantity,
        int KimThachQuantity,
        long QuanCost,
        int SuccessBasisPoints)
    {
        public static readonly int[] LuckCharmIds =
        [
            (int)PlayerItemId.LuckCharm1,
            (int)PlayerItemId.LuckCharm2,
            (int)PlayerItemId.LuckCharm3
        ];

        public static EquipmentUpgradePolicy Resolve(int currentLevel)
        {
            var targetLevel = currentLevel + 1;
            var huyetQty = targetLevel;
            var kimQty = 1;
            if (targetLevel >= 4)
            {
                kimQty = 2;
            }
            if (targetLevel >= 7)
            {
                kimQty = 3;
            }
            if (targetLevel >= 10)
            {
                kimQty = 5;
            }
            if (targetLevel >= 13)
            {
                kimQty = 8;
            }

            long quanCost = targetLevel * targetLevel * 1000L;

            int chance;
            if (currentLevel < 1)
            {
                chance = 10000;
            }
            else if (currentLevel == 1)
            {
                chance = 9000;
            }
            else if (currentLevel == 2)
            {
                chance = 8000;
            }
            else if (currentLevel == 3)
            {
                chance = 7000;
            }
            else if (currentLevel == 4)
            {
                chance = 6000;
            }
            else if (currentLevel == 5)
            {
                chance = 5000;
            }
            else if (currentLevel == 6)
            {
                chance = 4000;
            }
            else if (currentLevel == 7)
            {
                chance = 3000;
            }
            else if (currentLevel == 8)
            {
                chance = 2000;
            }
            else if (currentLevel == 9)
            {
                chance = 1000;
            }
            else if (currentLevel == 10)
            {
                chance = 500;
            }
            else if (currentLevel == 11)
            {
                chance = 400;
            }
            else if (currentLevel == 12)
            {
                chance = 300;
            }
            else if (currentLevel == 13)
            {
                chance = 200;
            }
            else if (currentLevel == 14)
            {
                chance = 150;
            }
            else
            {
                chance = 100;
            }

            return new EquipmentUpgradePolicy(currentLevel, huyetQty, kimQty, quanCost, chance);
        }
    }
}
