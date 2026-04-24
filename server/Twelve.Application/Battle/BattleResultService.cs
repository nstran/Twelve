using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Application.Players;

namespace Twelve.Application.Battle
{
    public sealed class BattleResultService : IBattleResultService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;
        private readonly IMonsterBattleCatalog _monsterBattleCatalog;
        private readonly IPlayerRepository _playerRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly PlayerContentCatalog _contentCatalog;

        public BattleResultService(
            IBattleSessionStore battleSessionStore,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IMonsterBattleCatalog monsterBattleCatalog,
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            PlayerContentCatalog contentCatalog)
        {
            _battleSessionStore = battleSessionStore;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _monsterBattleCatalog = monsterBattleCatalog;
            _playerRepository = playerRepository;
            _playerAggregateRepository = playerAggregateRepository;
            _contentCatalog = contentCatalog;
        }

        public BattleResultRewardResponse? Claim(BattleResultClaimRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null || session.IsCompleted)
            {
                return null;
            }

            var playerId = ResolvePlayerId(session.Player.CombatantId);
            if (!playerId.HasValue)
            {
                return null;
            }

            var player = _playerRepository.GetByIdAsync(playerId.Value).GetAwaiter().GetResult();
            if (player is null)
            {
                return null;
            }
            var aggregate = _playerAggregateRepository.GetByPlayerIdAsync(player.Id).GetAwaiter().GetResult();
            var equipment = aggregate?.Equipment.ToList() ?? [];
            var inventory = aggregate?.Inventory.ToList() ?? [];
            var skills = aggregate?.Skills ?? [];

            var levelBefore = player.Level;
            var expBefore = player.Exp;
            var quanBefore = player.Gold;
            var clampedHp = Math.Clamp(request.PlayerCurrentHp, 0, Math.Max(1, player.MaxHp));
            player.Hp = clampedHp;

            var expGained = 0L;
            var quanGained = 0L;
            var loot = new PlayerContentCatalog.BattleLootReward([], []);
            if (request.Result == BattleResultKind.Victory)
            {
                var rewards = ResolveRewards(session);
                expGained = rewards.Exp;
                quanGained = rewards.Quan;
                player.Gold += quanGained;
                PlayerLevelProgression.ApplyExperience(player, expGained);
                loot = ResolveLoot(session);
                ApplyLoot(inventory, equipment, loot);
                if (player.Level > levelBefore)
                {
                    PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
                    clampedHp = player.Hp;
                }

                _playerAggregateRepository
                    .SaveCollectionsAsync(player.Id, equipment, inventory, skills)
                    .GetAwaiter()
                    .GetResult();
            }
            else
            {
                var expLost = PlayerLevelProgression.ApplyDefeatPenalty(player);
                expGained = -expLost;
                player.Hp = player.MaxHp;
                clampedHp = player.Hp;
            }

            player.LastSeenAt = DateTime.UtcNow;
            _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
            _battleSessionStore.Save(session with { IsCompleted = true });

        return new BattleResultRewardResponse(
                Result: request.Result,
                LevelBefore: levelBefore,
                LevelAfter: player.Level,
                LevelUps: Math.Max(0, player.Level - levelBefore),
                CurrentHp: clampedHp,
                MaxHp: player.MaxHp,
                ExpBefore: expBefore,
                ExpAfter: player.Exp,
                ExpFloor: player.ExpFloor,
                ExpCeiling: player.ExpCeiling,
                ExpGained: expGained,
                QuanBefore: quanBefore,
                QuanAfter: player.Gold,
                QuanGained: quanGained,
                ItemRewards: loot.Items.Select(reward => reward.View).ToArray(),
                EquipmentRewards: loot.Equipment.Select(reward => reward.View).ToArray());
        }

        private (long Exp, long Quan) ResolveRewards(BattleSessionState session)
        {
            var battleTemplateId = session.BattleTemplateId;
            if (string.IsNullOrWhiteSpace(battleTemplateId) && !string.IsNullOrWhiteSpace(session.SpawnTemplateKey))
            {
                battleTemplateId = _monsterSpawnCatalog.GetBySpawnTemplateKey(session.SpawnTemplateKey)?.BattleTemplateId;
            }

            if (string.IsNullOrWhiteSpace(battleTemplateId))
            {
                return (0, 0);
            }

            var battleTemplate = _monsterBattleCatalog.GetByBattleTemplateId(battleTemplateId);
            if (battleTemplate is null)
            {
                return (0, 0);
            }

            return (
                Math.Max(0, battleTemplate.ExpReward),
                Math.Max(0, battleTemplate.QuanReward));
        }

        private static int? ResolvePlayerId(string combatantId)
        {
            const string prefix = "player:";
            if (!combatantId.StartsWith(prefix, StringComparison.OrdinalIgnoreCase))
            {
                return null;
            }

            return int.TryParse(combatantId[prefix.Length..], out var playerId)
                ? playerId
                : null;
        }

        private PlayerContentCatalog.BattleLootReward ResolveLoot(BattleSessionState session)
        {
            var battleTemplateId = session.BattleTemplateId;
            if (string.IsNullOrWhiteSpace(battleTemplateId) && !string.IsNullOrWhiteSpace(session.SpawnTemplateKey))
            {
                battleTemplateId = _monsterSpawnCatalog.GetBySpawnTemplateKey(session.SpawnTemplateKey)?.BattleTemplateId;
            }

            if (string.IsNullOrWhiteSpace(battleTemplateId))
            {
                return new PlayerContentCatalog.BattleLootReward([], []);
            }

            var battleTemplate = _monsterBattleCatalog.GetByBattleTemplateId(battleTemplateId);
            return battleTemplate is null
                ? new PlayerContentCatalog.BattleLootReward([], [])
                : _contentCatalog.CreateBattleLoot(session, battleTemplate);
        }

        private static void ApplyLoot(
            List<Twelve.Core.Entities.PlayerItemStack> inventory,
            List<Twelve.Core.Entities.PlayerEquipmentEntry> equipment,
            PlayerContentCatalog.BattleLootReward loot)
        {
            foreach (var itemReward in loot.Items)
            {
                var existingIndex = inventory.FindIndex(stack => stack.ItemId == itemReward.Stack.ItemId);
                if (existingIndex >= 0)
                {
                    inventory[existingIndex] = new Twelve.Core.Entities.PlayerItemStack
                    {
                        ItemId = inventory[existingIndex].ItemId,
                        Quantity = inventory[existingIndex].Quantity + itemReward.Stack.Quantity,
                        RawJson = itemReward.Stack.RawJson
                    };
                }
                else
                {
                    inventory.Add(itemReward.Stack);
                }
            }

            foreach (var equipmentReward in loot.Equipment)
            {
                equipment.Add(equipmentReward.Entry);
            }
        }
    }
}
