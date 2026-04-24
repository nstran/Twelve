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

            var targetView = _contentCatalog.ToEquipmentView(equipment[index]);
            if (request.Equip && player.Level < targetView.RequiredLevel)
            {
                return new PlayerRuntimeResponse(BuildSnapshot(aggregate), $"Can cap {targetView.RequiredLevel} de mac.");
            }

            var target = equipment[index];
            if (request.Equip)
            {
                for (var i = 0; i < equipment.Count; i++)
                {
                    if (equipment[i].Slot == target.Slot && equipment[i].IsEquipped)
                    {
                        equipment[i] = CloneEquipmentEntry(equipment[i], isEquipped: false);
                    }
                }
            }

            equipment[index] = CloneEquipmentEntry(target, isEquipped: request.Equip);

            _playerAggregateRepository.SaveCollectionsAsync(player.Id, equipment, aggregate.Inventory, aggregate.Skills).GetAwaiter().GetResult();
            RecalculateAndSave(player, equipment);

            return new PlayerRuntimeResponse(
                BuildSnapshot(ReloadAggregate(player.Id)),
                request.Equip ? "Da mac trang bi." : "Da thao trang bi.");
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
            player.Hp = System.Math.Min(player.MaxHp, player.Hp + definition.HealAmount);

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
                $"Da dung {definition.DisplayName}.");
        }

        private PlayerAggregate? LoadAggregate(string username) =>
            string.IsNullOrWhiteSpace(username)
                ? null
                : _playerAggregateRepository.GetByUsernameAsync(username).GetAwaiter().GetResult();

        private PlayerAggregate ReloadAggregate(int playerId) =>
            _playerAggregateRepository.GetByPlayerIdAsync(playerId).GetAwaiter().GetResult()
            ?? throw new System.InvalidOperationException("Failed to reload player aggregate.");

        private PlayerRuntimeSnapshot BuildSnapshot(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
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
                CuongLuc: player.CuongLuc,
                ThanPhap: player.ThanPhap,
                NoiLuc: player.NoiLuc,
                TheLuc: player.TheLuc,
                BonusCuongLuc: player.BonusCuongLuc,
                BonusThanPhap: player.BonusThanPhap,
                BonusNoiLuc: player.BonusNoiLuc,
                BonusTheLuc: player.BonusTheLuc,
                MinDamage: player.DerivedMinDamage,
                MaxDamage: player.DerivedMaxDamage,
                Defense: player.DerivedDefense,
                Dodge: player.DerivedDodge,
                Hit: player.DerivedHit,
                Crit: player.DerivedCrit,
                Inventory: aggregate.Inventory.Select(_contentCatalog.ToInventoryView).ToArray(),
                Equipment: aggregate.Equipment.Select(_contentCatalog.ToEquipmentView).ToArray(),
                Skills: _contentCatalog.BuildSkillViews(player, aggregate.Skills));
        }

        private void RecalculateAndSave(Player player, IReadOnlyList<PlayerEquipmentEntry> equipment)
        {
            PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
            _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
        }

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
