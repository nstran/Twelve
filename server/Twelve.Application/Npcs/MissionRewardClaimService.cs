using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading.Tasks;
using Twelve.Application.Players;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;

namespace Twelve.Application.Npcs
{
    public sealed class MissionRewardClaimService : IMissionRewardClaimService
    {
        private readonly INpcMissionCatalog _missionCatalog;
        private readonly IPlayerMissionStateRepository _missionStateRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly IPlayerRepository _playerRepository;
        private readonly PlayerContentCatalog _contentCatalog;

        public MissionRewardClaimService(
            INpcMissionCatalog missionCatalog,
            IPlayerMissionStateRepository missionStateRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            IPlayerRepository playerRepository,
            PlayerContentCatalog contentCatalog)
        {
            _missionCatalog = missionCatalog;
            _missionStateRepository = missionStateRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _playerRepository = playerRepository;
            _contentCatalog = contentCatalog;
        }

        public async Task<MissionRewardClaimResult> ClaimAsync(string username, string missionKey)
        {
            var mission = _missionCatalog.GetMissionDetail(missionKey);
            if (mission is null)
            {
                return BuildResult(MissionRewardClaimStatus.MissionNotFound, MissionPlayerStatus.Available);
            }

            var status = await _missionStateRepository.GetStatusAsync(username, missionKey);
            if (status == MissionPlayerStatus.RewardClaimed)
            {
                return BuildResult(MissionRewardClaimStatus.AlreadyClaimed, MissionPlayerStatus.RewardClaimed);
            }

            if (status != MissionPlayerStatus.Completed)
            {
                return BuildResult(MissionRewardClaimStatus.NotCompleted, status);
            }

            var claimStarted = await _missionStateRepository.TryBeginRewardClaimAsync(username, missionKey);
            if (!claimStarted)
            {
                return BuildResult(MissionRewardClaimStatus.AlreadyClaimed, MissionPlayerStatus.RewardClaimed);
            }

            try
            {
                var result = await GrantRewardsAsync(username, mission);
                await _missionStateRepository.CompleteRewardClaimAsync(username, missionKey);
                return result;
            }
            catch (InvalidMissionRewardException)
            {
                await _missionStateRepository.RestoreCompletedAsync(username, missionKey);
                return BuildResult(MissionRewardClaimStatus.InvalidRewardData, MissionPlayerStatus.Completed);
            }
            catch
            {
                await _missionStateRepository.RestoreCompletedAsync(username, missionKey);
                throw;
            }
        }

        private async Task<MissionRewardClaimResult> GrantRewardsAsync(string username, MissionDetail mission)
        {
            var aggregate = await _playerAggregateRepository.GetByUsernameAsync(username);
            if (aggregate is null)
            {
                return BuildResult(MissionRewardClaimStatus.PlayerNotFound, MissionPlayerStatus.Completed);
            }

            var player = aggregate.Core;
            var inventory = aggregate.Inventory.ToList();
            var equipment = aggregate.Equipment.ToList();
            var skills = aggregate.Skills;
            var expGained = 0L;
            var grantedItems = new List<string>();
            var grantedEquipment = new List<string>();

            foreach (var reward in mission.Rewards.OrderBy(reward => reward.SortOrder))
            {
                if (reward.Amount <= 0)
                    throw new InvalidMissionRewardException();

                if (reward.RewardType == MissionRewardType.Exp)
                {
                    expGained += reward.Amount;
                    PlayerLevelProgression.ApplyExperience(player, reward.Amount);
                    continue;
                }

                if (reward.RewardType == MissionRewardType.Item)
                {
                    AddItemReward(inventory, reward, grantedItems);
                    continue;
                }

                if (reward.RewardType == MissionRewardType.Equipment)
                {
                    AddEquipmentReward(equipment, reward, player, mission, grantedEquipment);
                    continue;
                }

                throw new InvalidMissionRewardException();
            }

            if (expGained > 0)
            {
                PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
            }

            player.LastSeenAt = DateTime.UtcNow;
            await _playerRepository.UpdateAsync(player);
            await _playerAggregateRepository.SaveCollectionsAsync(player.Id, equipment, inventory, skills);

            return new MissionRewardClaimResult(
                MissionRewardClaimStatus.Claimed,
                MissionPlayerStatus.RewardClaimed,
                expGained,
                grantedItems,
                grantedEquipment);
        }

        private void AddItemReward(List<PlayerItemStack> inventory, MissionReward reward, List<string> grantedItems)
        {
            if (!int.TryParse(reward.RewardKey, NumberStyles.Integer, CultureInfo.InvariantCulture, out var itemId))
                throw new InvalidMissionRewardException();

            var definition = _contentCatalog.GetItemDefinition(itemId);
            if (definition is null)
                throw new InvalidMissionRewardException();

            var existingIndex = inventory.FindIndex(stack => stack.ItemId == itemId);
            if (existingIndex >= 0)
            {
                var existing = inventory[existingIndex];
                inventory[existingIndex] = new PlayerItemStack
                {
                    ItemId = existing.ItemId,
                    Quantity = existing.Quantity + reward.Amount,
                    RawJson = existing.RawJson
                };
            }
            else
            {
                inventory.Add(_contentCatalog.BuildInventoryStack(definition, reward.Amount));
            }

            grantedItems.Add($"{definition.DisplayName} x{reward.Amount}");
        }

        private void AddEquipmentReward(
            List<PlayerEquipmentEntry> equipment,
            MissionReward reward,
            Player player,
            MissionDetail mission,
            List<string> grantedEquipment)
        {
            if (string.IsNullOrWhiteSpace(reward.RewardKey))
                throw new InvalidMissionRewardException();

            var definition = _contentCatalog.GetEquipmentDefinition(reward.RewardKey);
            if (definition is null)
            {
                throw new InvalidMissionRewardException();
            }

            for (var i = 0; i < reward.Amount; i++)
            {
                var uniqueSeed = $"mission:{mission.MissionKey}:{player.Id}:{DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()}:{i}";
                equipment.Add(_contentCatalog.BuildEquipmentEntryFromTemplate(reward.RewardKey, uniqueSeed));
            }

            grantedEquipment.Add($"{definition.DisplayName} x{reward.Amount}");
        }

        private static MissionRewardClaimResult BuildResult(
            MissionRewardClaimStatus status,
            MissionPlayerStatus playerStatus) =>
            new(status, playerStatus, 0, [], []);

        private sealed class InvalidMissionRewardException : Exception
        {
        }
    }
}
