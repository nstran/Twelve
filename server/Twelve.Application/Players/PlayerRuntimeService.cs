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

        public PlayerRuntimeService(
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            PlayerContentCatalog contentCatalog)
        {
            _playerRepository = playerRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _contentCatalog = contentCatalog;
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
            var repairItemIndex = inventory.FindIndex(entry => entry.ItemId == request.RepairItemId);
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

        private PlayerAggregate? LoadAggregate(string username) =>
            string.IsNullOrWhiteSpace(username)
                ? null
                : _playerAggregateRepository.GetByUsernameAsync(username).GetAwaiter().GetResult();

        private PlayerAggregate ReloadAggregate(long playerId) =>
            _playerAggregateRepository.GetByPlayerIdAsync(playerId).GetAwaiter().GetResult()
            ?? throw new System.InvalidOperationException("Failed to reload player aggregate.");

        private PlayerRuntimeSnapshot BuildSnapshot(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
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

            if (equipment.MaxDurability > 0 && equipment.Durability <= 0)
            {
                return "Trang bi da hong, can sua truoc khi mac.";
            }

            return null;
        }

        private static bool IsValidEquipmentSlot(int slot) =>
            slot is 0 or 1 or 2 or 3 or 4 or 5 or 7 or 8;

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
                IsEquipped = isEquipped,
                RawJson = source.RawJson
            };
    }
}
