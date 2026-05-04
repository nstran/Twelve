using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Players;

namespace Twelve.Application.Players
{
    public sealed class PlayerRuntimeService : IPlayerRuntimeService
    {
        private readonly IPlayerRepository _playerRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly PlayerContentCatalog _contentCatalog;
        private readonly IEquipmentUpgradeService _equipmentUpgradeService;
        private readonly IEquipmentCombineService _equipmentCombineService;

        public PlayerRuntimeService(
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            PlayerContentCatalog contentCatalog,
            IEquipmentUpgradeService equipmentUpgradeService,
            IEquipmentCombineService equipmentCombineService)
        {
            _playerRepository = playerRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _contentCatalog = contentCatalog;
            _equipmentUpgradeService = equipmentUpgradeService;
            _equipmentCombineService = equipmentCombineService;
        }

        public PlayerRuntimeResponse? GetSnapshot(PlayerRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            return aggregate is null
                ? null
                : new PlayerRuntimeResponse(BuildSnapshot(aggregate));
        }

        public PlayerRuntimeResponse? AllocateStat(PlayerAllocateStatRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var player = aggregate.Core;
            var amount = System.Math.Max(1, request.Amount);
            var applied = 0;
            while (applied < amount && player.FreePoints > 0)
            {
                switch (request.Stat)
                {
                    case PlayerStatKind.CuongLuc:
                        player.CuongLuc++;
                        break;
                    case PlayerStatKind.ThanPhap:
                        player.ThanPhap++;
                        break;
                    case PlayerStatKind.NoiLuc:
                        player.NoiLuc++;
                        break;
                    case PlayerStatKind.TheLuc:
                        player.TheLuc++;
                        break;
                }

                player.FreePoints--;
                applied++;
            }

            if (applied <= 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong con diem tiem nang.");
            }

            RecalculateAndSave(player, aggregate.Equipment);
            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(player.Id)),
                $"Da cong {applied} diem.");
        }

        public PlayerRuntimeResponse? AllocateSkill(PlayerAllocateSkillRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var player = aggregate.Core;
            var definition = _contentCatalog.GetSkillDefinition(player.Element ?? 0, request.FamilyCode);
            if (definition is null)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Skill khong thuoc he hien tai.");
            }

            if (player.SkillPoints < definition.Cost)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong du diem ky nang.");
            }

            if (player.Level < definition.RequiredLevel)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), $"Can cap {definition.RequiredLevel}.");
            }

            var skills = aggregate.Skills.ToList();
            var index = skills.FindIndex(skill => skill.SkillId == request.FamilyCode);
            var currentLevel = index >= 0 ? skills[index].Level : 0;
            if (currentLevel >= definition.MaxLevel)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Skill da dat cap toi da.");
            }

            player.SkillPoints -= definition.Cost;
            var nextEntry = new PlayerSkillEntry
            {
                SkillId = request.FamilyCode,
                Level = currentLevel + 1,
                RawJson = index >= 0 ? skills[index].RawJson : $"{{\"familyCode\":{request.FamilyCode}}}"
            };

            if (index >= 0)
            {
                skills[index] = nextEntry;
            }
            else
            {
                skills.Add(nextEntry);
            }

            _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
            _playerAggregateRepository.SaveCollectionsAsync(player.Id, aggregate.Equipment, aggregate.Inventory, skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(player.Id)),
                "Da nang skill.");
        }

        public PlayerRuntimeResponse? UpdateEquipment(PlayerEquipmentRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var player = aggregate.Core;
            var equipment = aggregate.Equipment.ToList();
            var index = equipment.FindIndex(entry => entry.EquipKey == request.EquipKey);
            if (index < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay trang bi.");
            }

            var desiredKeys = equipment
                .Where(entry => entry.IsEquipped)
                .Select(entry => entry.EquipKey)
                .ToHashSet(System.StringComparer.Ordinal);

            if (request.Equip)
            {
                var target = equipment[index];
                foreach (var entry in equipment)
                {
                    if (entry.Slot == target.Slot)
                    {
                        desiredKeys.Remove(entry.EquipKey);
                    }
                }

                desiredKeys.Add(target.EquipKey);
            }
            else
            {
                desiredKeys.Remove(equipment[index].EquipKey);
            }

            return CommitEquipmentLoadoutInternal(
                aggregate,
                desiredKeys,
                request.Equip ? "Da mac trang bi." : "Da thao trang bi.");
        }

        public PlayerRuntimeResponse? CommitEquipmentLoadout(PlayerEquipmentLoadoutRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var equipKeys = request.EquipKeys ?? System.Array.Empty<string>();
            return CommitEquipmentLoadoutInternal(
                aggregate,
                equipKeys.ToHashSet(System.StringComparer.Ordinal),
                "Da cap nhat trang bi.");
        }

        public PlayerRuntimeResponse? PreviewEquipmentLoadout(PlayerEquipmentLoadoutRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var equipKeys = request.EquipKeys ?? System.Array.Empty<string>();
            return PreviewEquipmentLoadoutInternal(aggregate, equipKeys.ToHashSet(System.StringComparer.Ordinal));
        }

        public PlayerRuntimeResponse? UseItem(PlayerUseItemRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var inventory = aggregate.Inventory.ToList();
            var index = inventory.FindIndex(entry => entry.ItemId == request.ItemId);
            if (index < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay vat pham.");
            }

            var definition = _contentCatalog.GetItemDefinition(request.ItemId);
            if (definition is null || !definition.IsUsable)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Vat pham nay chua dung duoc.");
            }

            var player = aggregate.Core;
            var restore = CalculateItemRestore(player, definition);

            if (restore.Hp <= 0 && restore.Mp <= 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Sinh luc/noi luc da day.");
            }

            player.Hp = System.Math.Min(player.MaxHp, player.Hp + restore.Hp);
            player.Mp = System.Math.Min(player.MaxMp, player.Mp + restore.Mp);

            var nextQuantity = inventory[index].Quantity - 1;
            if (nextQuantity <= 0)
            {
                inventory.RemoveAt(index);
            }
            else
            {
                inventory[index] = new PlayerItemStack
                {
                    ItemId = inventory[index].ItemId,
                    Quantity = nextQuantity,
                    RawJson = inventory[index].RawJson
                };
            }

            _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
            _playerAggregateRepository.SaveCollectionsAsync(player.Id, aggregate.Equipment, inventory, aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(player.Id)),
                BuildUseItemMessage(definition.DisplayName, restore));
        }

        public PlayerRuntimeResponse? DiscardEquipment(PlayerDiscardEquipmentRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var equipKeys = request.EquipKeys ?? System.Array.Empty<string>();
            if (equipKeys.Count == 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong co trang bi nao duoc chon.");
            }

            var equipment = aggregate.Equipment.ToList();
            var equippedKeys = equipment
                .Where(entry => entry.IsEquipped)
                .Select(entry => entry.EquipKey)
                .ToHashSet(System.StringComparer.Ordinal);

            foreach (var key in equipKeys)
            {
                if (equippedKeys.Contains(key))
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Phai thao trang bi truoc khi vut bo.");
                }
            }

            var discardSet = equipKeys.ToHashSet(System.StringComparer.Ordinal);
            var remaining = equipment.Where(entry => !discardSet.Contains(entry.EquipKey)).ToList();

            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id, remaining, aggregate.Inventory, aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                "Da vut bo trang bi.");
        }

        public PlayerRuntimeResponse? DiscardItem(PlayerDiscardItemRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var inventory = aggregate.Inventory.ToList();
            var index = inventory.FindIndex(entry => entry.ItemId == request.ItemId);
            if (index < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay vat pham.");
            }

            var qty = System.Math.Max(1, request.Quantity);
            var current = inventory[index];
            var nextQty = current.Quantity - qty;

            if (nextQty <= 0)
            {
                inventory.RemoveAt(index);
            }
            else
            {
                inventory[index] = new PlayerItemStack
                {
                    ItemId = current.ItemId,
                    Quantity = nextQty,
                    RawJson = current.RawJson
                };
            }

            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id, aggregate.Equipment, inventory, aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                "Da vut bo vat pham.");
        }

        // cmd 48: áp vật phẩm sửa chữa lên equipment → ll.p = ll.q
        public PlayerRuntimeResponse? RepairEquipment(PlayerRepairEquipmentRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var equipment = aggregate.Equipment.ToList();
            var equipIndex = equipment.FindIndex(entry => entry.EquipKey == request.EquipKey);
            if (equipIndex < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay trang bi.");
            }

            // Remake policy (2026-05-03): server-authoritative repair accepts only
            // PlayerItemId.RepairHammer (raw itemId 30099), consumes exactly one hammer,
            // restores ll.p = ll.q, and does not consume Quan.
            if (!_contentCatalog.IsRepairMaterial(request.RepairItemId))
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Vat pham nay khong phai nguyen lieu sua chua.");
            }

            var targetView = _contentCatalog.ToEquipmentView(equipment[equipIndex]);
            if (targetView.MaxDurability <= 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Trang bi nay khong co do ben.");
            }

            if (targetView.Durability >= targetView.MaxDurability)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Trang bi van con nguyen ven.");
            }

            var inventory = aggregate.Inventory.ToList();
            var repairItemIndex = inventory.FindIndex(entry => entry.ItemId == request.RepairItemId && entry.Quantity > 0);
            if (repairItemIndex < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Can 1 bua sua chua trong tui do.");
            }

            equipment[equipIndex] = _contentCatalog.RestoreDurability(equipment[equipIndex]);

            var repairItem = inventory[repairItemIndex];
            var nextQty = repairItem.Quantity - 1;
            if (nextQty <= 0)
            {
                inventory.RemoveAt(repairItemIndex);
            }
            else
            {
                inventory[repairItemIndex] = new PlayerItemStack
                {
                    ItemId = repairItem.ItemId,
                    Quantity = nextQty,
                    RawJson = repairItem.RawJson
                };
            }

            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id, equipment, inventory, aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                "Da sua chua trang bi.");
        }

        public PlayerRuntimeResponse? UpgradeEquipment(PlayerUpgradeEquipmentRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            // Remake policy 2026-05-04: user approved Huyet thach/Kim thach/luck charms
            // as upgrade materials while Java server material ids/rates remain pending.
            var result = _equipmentUpgradeService.Apply(aggregate, request);
            if (result.QuanCost > 0)
            {
                aggregate.Core.Gold -= result.QuanCost;
                if (aggregate.Core.Gold < 0)
                {
                    aggregate.Core.Gold = 0;
                }

                _playerRepository.UpdateAsync(aggregate.Core).GetAwaiter().GetResult();
            }

            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id,
                result.Equipment,
                result.Inventory,
                aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                result.Message);
        }

        public PlayerRuntimeResponse? CombineEquipment(PlayerCombineEquipmentRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            // Java evidence: cmd 99/100 TLV shapes only. Recipe and cost are remake policy.
            var result = _equipmentCombineService.Apply(aggregate, request);
            if (result.QuanCost > 0)
            {
                aggregate.Core.Gold -= result.QuanCost;
                if (aggregate.Core.Gold < 0)
                {
                    aggregate.Core.Gold = 0;
                }

                _playerRepository.UpdateAsync(aggregate.Core).GetAwaiter().GetResult();
            }

            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id,
                result.Equipment,
                result.Inventory,
                aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                result.Message);
        }

        public PlayerShopRuntimeResponse? GetShop(PlayerShopRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            // Java evidence: shop screenshot/ia.java prove system shop UI; offer ids/prices are remake policy.
            return _contentCatalog.BuildSystemShop(request.ShopKey);
        }

        public PlayerRuntimeResponse? BuyShopOffer(PlayerShopBuyRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            var offer = _contentCatalog.ResolveShopOffer(request.ShopKey, request.OfferKey);
            if (offer is null || offer.Equipment is null)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay vat pham trong cua hang.");
            }

            if (aggregate.Core.Gold < offer.PriceQuan)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong du Ken de mua vat pham.");
            }

            if (IsInventoryFullForNewEquipment(aggregate))
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Tui do da day.");
            }

            var rewardEntry = _contentCatalog.BuildShopEquipmentReward(
                request.ShopKey,
                request.OfferKey,
                $"shop:{request.ShopKey}:{aggregate.Core.Id}:{DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()}");
            if (rewardEntry is null)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Cau hinh cua hang khong hop le.");
            }

            aggregate.Core.Gold -= offer.PriceQuan;
            if (aggregate.Core.Gold < 0)
            {
                aggregate.Core.Gold = 0;
            }

            var equipment = aggregate.Equipment.ToList();
            equipment.Add(rewardEntry);
            _playerRepository.UpdateAsync(aggregate.Core).GetAwaiter().GetResult();
            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id,
                equipment,
                aggregate.Inventory,
                aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                $"Da mua {offer.DisplayName}.");
        }

        public PlayerRuntimeResponse? OpenEgg(PlayerOpenEggRuntimeRequest request)
        {
            var aggregate = LoadAggregate(request.Username);
            if (aggregate is null)
            {
                return null;
            }

            // Remake policy / user confirmation 2026-05-03:
            // Open-egg costs are server-authoritative config. Java client currently proves only
            // partial item/icon identity; Java server reward rates and pools are Pending/Unverified.
            var eggDefinition = _contentCatalog.GetEggDefinition(request.EggItemId);
            if (eggDefinition is null)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Vat pham nay khong phai trung co the dap.");
            }

            var inventory = aggregate.Inventory.ToList();
            var eggIndex = inventory.FindIndex(entry => entry.ItemId == request.EggItemId && entry.Quantity > 0);
            if (eggIndex < 0)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Can 1 trung trong tui do.");
            }

            if (aggregate.Core.Gold < eggDefinition.OpenCostQuan)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong du Quan de dap trung.");
            }

            if (IsInventoryFullForNewEquipment(aggregate))
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Tui do da day.");
            }

            // Strict reconstruction boundary: each egg type must have explicit configured reward templates.
            // Do not fallback to random equipment, and never include Wing/e=8 in egg rewards.
            if (eggDefinition.AllowedEquipmentTemplateKeys.Count == 0)
            {
                return new PlayerRuntimeResponse(
                    BuildSnapshot(aggregate),
                    "Chua cau hinh reward pool cho loai trung nay.");
            }

            var rewardPool = new List<string>();
            foreach (var templateKey in eggDefinition.AllowedEquipmentTemplateKeys)
            {
                var template = _contentCatalog.GetEquipmentDefinition(templateKey);
                if (template is null)
                {
                    return new PlayerRuntimeResponse(
                        BuildSnapshot(aggregate),
                        $"Reward template '{templateKey}' chua ton tai trong EquipmentCatalog.");
                }

                if (!eggDefinition.AllowedRewardSlots.Contains(template.Slot))
                {
                    return new PlayerRuntimeResponse(
                        BuildSnapshot(aggregate),
                        $"Reward template '{templateKey}' khong thuoc slot trung cho phep.");
                }

                if (template.Slot == (int)PlayerEquipmentSlot.Wing)
                {
                    return new PlayerRuntimeResponse(
                        BuildSnapshot(aggregate),
                        "Trung khong duoc mo ra canh.");
                }

                rewardPool.Add(templateKey);
            }

            var rewardIndex = StableIndex(
                $"{aggregate.Core.Id}:{request.EggItemId}:{inventory[eggIndex].Quantity}:{aggregate.Equipment.Count}",
                rewardPool.Count);
            var rewardEntry = _contentCatalog.BuildEquipmentEntryFromTemplate(
                rewardPool[rewardIndex],
                $"egg-{aggregate.Core.Id}-{request.EggItemId}");

            var eggStack = inventory[eggIndex];
            var nextEggQuantity = eggStack.Quantity - 1;
            if (nextEggQuantity <= 0)
            {
                inventory.RemoveAt(eggIndex);
            }
            else
            {
                inventory[eggIndex] = new PlayerItemStack
                {
                    ItemId = eggStack.ItemId,
                    Quantity = nextEggQuantity,
                    RawJson = eggStack.RawJson
                };
            }

            aggregate.Core.Gold -= eggDefinition.OpenCostQuan;
            var equipment = aggregate.Equipment.ToList();
            equipment.Add(rewardEntry);

            _playerRepository.UpdateAsync(aggregate.Core).GetAwaiter().GetResult();
            _playerAggregateRepository.SaveCollectionsAsync(
                aggregate.Core.Id, equipment, inventory, aggregate.Skills).GetAwaiter().GetResult();

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(aggregate.Core.Id)),
                $"Da dap {eggDefinition.DisplayName}.");
        }

        private PlayerAggregate? LoadAggregate(string username) =>
            string.IsNullOrWhiteSpace(username)
                ? null
                : _playerAggregateRepository.GetByUsernameAsync(username).GetAwaiter().GetResult();

        private PlayerAggregate ReloadAggregate(long playerId) =>
            _playerAggregateRepository.GetByPlayerIdAsync(playerId).GetAwaiter().GetResult()
            ?? throw new System.InvalidOperationException("Failed to reload player aggregate.");

        private bool IsInventoryFullForNewEquipment(PlayerAggregate aggregate)
        {
            // Java evidence: go.b() starts from all equipment, subtracts equipped items,
            // then adds inventory item usage before comparing with go.n default capacity 50.
            const int DefaultInventoryCapacity = 50;
            var occupiedSlots = aggregate.Equipment.Count(entry => !entry.IsEquipped);

            foreach (var stack in aggregate.Inventory)
            {
                var definition = _contentCatalog.GetItemDefinition(stack.ItemId);
                if (definition is not null && definition.StackCap > 1)
                {
                    // Java evidence: lm.e == 7 stacks count by quantity in go.b().
                    // Backend currently preserves stack semantics through StackCap, not raw lm.e.
                    occupiedSlots += stack.Quantity;
                }
                else
                {
                    occupiedSlots++;
                }
            }

            return occupiedSlots >= DefaultInventoryCapacity;
        }

        private static int StableIndex(string value, int count)
        {
            if (count <= 0)
            {
                throw new System.ArgumentOutOfRangeException(nameof(count), "Reward pool must not be empty.");
            }

            unchecked
            {
                var hash = 17;
                foreach (var ch in value)
                {
                    hash = hash * 31 + ch;
                }

                return System.Math.Abs(hash % count);
            }
        }

        private PlayerRuntimeSnapshot BuildSnapshot(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
            var equippedModifierTotal = _contentCatalog.GetEquippedModifierTotal(aggregate.Equipment);
            var derived = PlayerStatPipeline.Calculate(
                player, _contentCatalog.GetEquippedModifiers(aggregate.Equipment));
            var mapMovement = MapMovementCalculator.Calculate(player.Level);

            return new PlayerRuntimeSnapshot(
                Username: player.Username,
                Element: player.Element ?? 0,
                Level: player.Level,
                CurrentHp: player.Hp,
                MaxHp: player.MaxHp,
                Exp: player.Exp,
                ExpFloor: player.ExpFloor,
                ExpCeiling: player.ExpCeiling,
                Gold: player.Gold,
                QuanProgress: player.QuanProgress,
                QuanProgressCap: player.QuanProgressCap,
                FreePoints: player.FreePoints,
                SkillPoints: player.SkillPoints,
                CurrentMp: player.Mp,
                MaxMp: player.MaxMp,
                CurrentPower: player.Power,
                MaxPower: 100,
                CuongLuc: player.CuongLuc,
                ThanPhap: player.ThanPhap,
                NoiLuc: player.NoiLuc,
                TheLuc: player.TheLuc,
                BonusCuongLuc: player.BonusCuongLuc,
                BonusThanPhap: player.BonusThanPhap,
                BonusNoiLuc: player.BonusNoiLuc,
                BonusTheLuc: player.BonusTheLuc,
                MinDamage: derived.MinDamage,
                MaxDamage: derived.MaxDamage,
                Defense: derived.Defense,
                Dodge: derived.Dodge,
                Hit: derived.Hit,
                Crit: derived.Crit,
                EquipCuongLuc: equippedModifierTotal.CuongLuc,
                EquipThanPhap: equippedModifierTotal.ThanPhap,
                EquipNoiLuc: equippedModifierTotal.NoiLuc,
                EquipTheLuc: equippedModifierTotal.TheLuc,
                EquipFlatAttack: equippedModifierTotal.FlatAttack,
                EquipAttackPercent: equippedModifierTotal.AttackPercent,
                EquipCrit: equippedModifierTotal.Crit,
                EquipDefense: equippedModifierTotal.Defense,
                EquipDodge: equippedModifierTotal.Dodge,
                EquipMaxHp: equippedModifierTotal.MaxHp,
                MapMoveSpeed: mapMovement.MoveSpeed,
                Inventory: aggregate.Inventory.Select(_contentCatalog.ToInventoryView).ToArray(),
                Equipment: aggregate.Equipment.Select(_contentCatalog.ToEquipmentView).ToArray(),
                Skills: _contentCatalog.BuildSkillViews(player, aggregate.Skills));
        }

        private void RecalculateAndSave(Player player, IReadOnlyList<PlayerEquipmentEntry> equipment)
        {
            PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
            _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
        }

        private static ItemRestoreResult CalculateItemRestore(Player player, PlayerContentCatalog.PlayerItemDefinition definition)
        {
            // Remake rule documented in docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.3.
            // Java client only proves lh.s/r HP and lh.u/t MP fields; old server formula for eating peach/potion is unavailable.
            // Use real persisted Player stats (base + equipment bonuses from PlayerStatPipeline), never client-side fake values.
            var totalStrength = System.Math.Max(0, player.CuongLuc + player.BonusCuongLuc);
            var totalMagic = System.Math.Max(0, player.NoiLuc + player.BonusNoiLuc);
            var restoreKind = definition.RestoreKind ?? string.Empty;

            var hp = 0;
            if (definition.HealAmount > 0 && restoreKind.Contains("hp", System.StringComparison.OrdinalIgnoreCase))
            {
                var percent = CalculateRestorePercent(
                    player.Element ?? ElementMapper.StorageHoa,
                    ElementMapper.StorageHoa,
                    totalStrength);
                hp = System.Math.Min(
                    System.Math.Max(0, player.MaxHp - player.Hp),
                    definition.HealAmount * percent / 100);
            }

            var mp = 0;
            if (definition.ManaAmount > 0 && restoreKind.Contains("mp", System.StringComparison.OrdinalIgnoreCase))
            {
                var percent = CalculateRestorePercent(
                    player.Element ?? ElementMapper.StorageHoa,
                    ElementMapper.StorageThuy,
                    totalMagic);
                mp = System.Math.Min(
                    System.Math.Max(0, player.MaxMp - player.Mp),
                    definition.ManaAmount * percent / 100);
            }

            return new ItemRestoreResult(hp, mp);
        }

        private static int CalculateRestorePercent(int playerElement, int primaryElement, int totalStat)
        {
            var statOverBase = totalStat - 10;
            var scalePerPoint = playerElement == primaryElement ? 3 : 2;
            var maxPercent = playerElement == primaryElement ? 180 : 150;
            return System.Math.Clamp(100 + statOverBase * scalePerPoint, 80, maxPercent);
        }

        private static string BuildUseItemMessage(string displayName, ItemRestoreResult restore)
        {
            if (restore.Hp > 0 && restore.Mp > 0)
            {
                return $"Da dung {displayName}: +{restore.Hp} HP, +{restore.Mp} MP.";
            }

            if (restore.Hp > 0)
            {
                return $"Da dung {displayName}: +{restore.Hp} HP.";
            }

            return $"Da dung {displayName}: +{restore.Mp} MP.";
        }

        private sealed record ItemRestoreResult(int Hp, int Mp);

        private PlayerRuntimeResponse PreviewEquipmentLoadoutInternal(
            PlayerAggregate aggregate,
            IReadOnlySet<string> desiredEquipKeys)
        {
            var player = ClonePlayer(aggregate.Core);
            var equipment = aggregate.Equipment.ToList();
            var equippedSlots = new HashSet<int>();

            foreach (var equipKey in desiredEquipKeys)
            {
                var target = equipment.FirstOrDefault(entry => entry.EquipKey == equipKey);
                if (target is null)
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay trang bi.");
                }

                var targetView = _contentCatalog.ToEquipmentView(target);
                var validationMessage = ValidateEquipmentForEquip(player, targetView);
                if (validationMessage is not null)
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), validationMessage);
                }

                if (!equippedSlots.Add(target.Slot))
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Moi o chi duoc mac mot trang bi.");
                }
            }

            for (var i = 0; i < equipment.Count; i++)
            {
                equipment[i] = CloneEquipmentEntry(
                    equipment[i],
                    isEquipped: desiredEquipKeys.Contains(equipment[i].EquipKey));
            }

            PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));

            var previewAggregate = new PlayerAggregate
            {
                Core = player,
                Appearance = aggregate.Appearance,
                Stats = aggregate.Stats,
                Equipment = equipment,
                Inventory = aggregate.Inventory,
                Skills = aggregate.Skills,
                MapOverlays = aggregate.MapOverlays,
                WorldState = aggregate.WorldState
            };

            return new PlayerRuntimeResponse(BuildSnapshot(previewAggregate), "Xem truoc trang bi.");
        }

        private PlayerRuntimeResponse CommitEquipmentLoadoutInternal(
            PlayerAggregate aggregate,
            IReadOnlySet<string> desiredEquipKeys,
            string successMessage)
        {
            var player = aggregate.Core;
            var equipment = aggregate.Equipment.ToList();
            var equippedSlots = new HashSet<int>();

            foreach (var equipKey in desiredEquipKeys)
            {
                var target = equipment.FirstOrDefault(entry => entry.EquipKey == equipKey);
                if (target is null)
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Khong tim thay trang bi.");
                }

                var targetView = _contentCatalog.ToEquipmentView(target);
                var validationMessage = ValidateEquipmentForEquip(player, targetView);
                if (validationMessage is not null)
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), validationMessage);
                }

                if (!equippedSlots.Add(target.Slot))
                {
                    return new PlayerRuntimeResponse(BuildSnapshot(aggregate), "Moi o chi duoc mac mot trang bi.");
                }
            }

            for (var i = 0; i < equipment.Count; i++)
            {
                equipment[i] = CloneEquipmentEntry(
                    equipment[i],
                    isEquipped: desiredEquipKeys.Contains(equipment[i].EquipKey));
            }

            _playerAggregateRepository.SaveCollectionsAsync(player.Id, equipment, aggregate.Inventory, aggregate.Skills).GetAwaiter().GetResult();
            RecalculateAndSave(player, equipment);

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(player.Id)),
                successMessage);
        }

        private static string? ValidateEquipmentForEquip(Player player, PlayerEquipmentItemView equipment)
        {
            if (!IsValidEquipmentSlot(equipment.Slot))
            {
                return "O trang bi khong hop le.";
            }

            if (player.Level < equipment.RequiredLevel)
            {
                return $"Can cap {equipment.RequiredLevel} de mac.";
            }

            if (equipment.Gender != 2 && equipment.Gender != player.Gender)
            {
                return "Trang bi khong dung gioi tinh.";
            }

            return null;
        }

        private static bool IsValidEquipmentSlot(int slot)
        {
            // Java evidence: ll.e is authoritative for slot.
            // Code-readiness gate 2026-05-03 enables only Armor/Weapon/Helmet/Ring/Wing(e=8).
            // Remake policy: broken equipment may still be equipped, but GetEquippedModifiers skips it.
            return slot == (int)PlayerEquipmentSlot.Armor
                || slot == (int)PlayerEquipmentSlot.Weapon
                || slot == (int)PlayerEquipmentSlot.Helmet
                || slot == (int)PlayerEquipmentSlot.Ring
                || slot == (int)PlayerEquipmentSlot.Wing;
        }

        private static Player ClonePlayer(Player source) =>
            new()
            {
                Id = source.Id,
                Username = source.Username,
                Level = source.Level,
                Gold = source.Gold,
                Exp = source.Exp,
                ExpFloor = source.ExpFloor,
                ExpCeiling = source.ExpCeiling,
                QuanProgress = source.QuanProgress,
                QuanProgressCap = source.QuanProgressCap,
                CurrentMap = source.CurrentMap,
                CurrentRoom = source.CurrentRoom,
                Hp = source.Hp,
                MaxHp = source.MaxHp,
                Mp = source.Mp,
                MaxMp = source.MaxMp,
                Power = source.Power,
                CuongLuc = source.CuongLuc,
                ThanPhap = source.ThanPhap,
                NoiLuc = source.NoiLuc,
                TheLuc = source.TheLuc,
                FreePoints = source.FreePoints,
                BonusCuongLuc = source.BonusCuongLuc,
                BonusThanPhap = source.BonusThanPhap,
                BonusNoiLuc = source.BonusNoiLuc,
                BonusTheLuc = source.BonusTheLuc,
                SkillPoints = source.SkillPoints,
                Honor = source.Honor,
                Gender = source.Gender,
                Element = source.Element,
                RawElementCode = source.RawElementCode,
                FaceStyle = source.FaceStyle,
                HairStyle = source.HairStyle,
                HairColor = source.HairColor,
                SkinColor = source.SkinColor,
                AppearanceHidden0 = source.AppearanceHidden0,
                AppearanceHidden1 = source.AppearanceHidden1,
                SpecialActorForm = source.SpecialActorForm,
                TitleMain = source.TitleMain,
                TitleSub = source.TitleSub,
                TitleRank = source.TitleRank,
                CreatedAt = source.CreatedAt,
                LastSeenAt = source.LastSeenAt
            };

        private static PlayerEquipmentEntry CloneEquipmentEntry(PlayerEquipmentEntry source, bool isEquipped) =>
            new()
            {
                EquipKey = source.EquipKey,
                TemplateKey = source.TemplateKey,
                Slot = source.Slot,
                ResourceId = source.ResourceId,
                Level = source.Level,
                Durability = source.Durability,
                MaxDurability = source.MaxDurability,
                IsEquipped = isEquipped,
                RawJson = source.RawJson
            };
    }
}
