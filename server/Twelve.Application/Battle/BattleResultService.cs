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
        private static readonly TimeSpan NormalMonsterRespawnDelay = TimeSpan.FromMinutes(3);
        private static readonly TimeSpan BossMonsterRespawnDelay = TimeSpan.FromDays(1);

        private readonly IMonsterBattleCatalog _monsterBattleCatalog;
        private readonly IMapMonsterRosterService _mapMonsterRosterService;
        private readonly IPlayerRepository _playerRepository;
        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly PlayerContentCatalog _contentCatalog;

        public BattleResultService(
            IBattleSessionStore battleSessionStore,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IMonsterBattleCatalog monsterBattleCatalog,
            IMapMonsterRosterService mapMonsterRosterService,
            IPlayerRepository playerRepository,
            IPlayerAggregateRepository playerAggregateRepository,
            PlayerContentCatalog contentCatalog)
        {
            _battleSessionStore = battleSessionStore;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _monsterBattleCatalog = monsterBattleCatalog;
            _mapMonsterRosterService = mapMonsterRosterService;
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
            var goldBefore = player.Gold;
            var clampedHp = Math.Clamp(request.PlayerCurrentHp, 0, Math.Max(1, player.MaxHp));
            // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5:
            // HP/MP/Power gains during battle are server-owned remake rules. At battle end, Java client
            // only proves lh.s/u/w are current bars; remake policy is:
            // - victory keeps surviving HP so train attrition matters;
            // - MP/Power are temporary battle resources and reset to 0 after result claim.
            var clampedMp = 0;
            var clampedPower = 0;

            if (session.Kind == BattleSessionKind.PvpShadow)
            {
                player.Hp = player.MaxHp;
                player.Mp = 0;
                player.Power = 0;
                player.LastSeenAt = DateTime.UtcNow;
                _playerRepository.UpdateAsync(player).GetAwaiter().GetResult();
                _battleSessionStore.Save(session with { IsCompleted = true });

                return new BattleResultRewardResponse(
                    Result: request.Result,
                    LevelBefore: levelBefore,
                    LevelAfter: player.Level,
                    LevelUps: 0,
                    CurrentHp: player.Hp,
                    MaxHp: player.MaxHp,
                    ExpBefore: expBefore,
                    ExpAfter: player.Exp,
                    ExpFloor: player.ExpFloor,
                     ExpCeiling: player.ExpCeiling,
                     ExpGained: 0,
                     GoldBefore: goldBefore,
                     GoldAfter: player.Gold,
                     GoldGained: 0,
                     ItemRewards: [],
                    EquipmentRewards: []);
            }

            player.Hp = clampedHp;
            player.Mp = clampedMp;
            player.Power = clampedPower;

            var expGained = 0L;
            var goldGained = 0L;
            var loot = new PlayerContentCatalog.BattleLootReward([], []);
            if (request.Result == BattleResultKind.Victory)
            {
                var rewards = ResolveRewards(session);
                expGained = rewards.Exp;
                goldGained = rewards.Gold;
                player.Gold += goldGained;
                PlayerLevelProgression.ApplyExperience(player, expGained);
                loot = ResolveLoot(session);
                ApplyLoot(inventory, equipment, loot);
                var durabilityChanged = ApplyEquippedEquipmentDurabilityLoss(equipment, lossAmount: 1);
                if (player.Level > levelBefore || durabilityChanged)
                {
                    PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
                    clampedHp = Math.Min(player.Hp, player.MaxHp);
                    player.Hp = clampedHp;
                }

                player.Mp = 0;
                player.Power = 0;
                clampedMp = player.Mp;
                clampedPower = player.Power;

                _playerAggregateRepository
                    .SaveCollectionsAsync(player.Id, equipment, inventory, skills)
                    .GetAwaiter()
                    .GetResult();
            }
            else
            {
                var expLost = PlayerLevelProgression.ApplyDefeatPenalty(player);
                expGained = -expLost;
                var durabilityChanged = ApplyEquippedEquipmentDurabilityLoss(equipment, lossAmount: 3);
                if (durabilityChanged)
                {
                    PlayerStatPipeline.RecalculateAndApply(player, _contentCatalog.GetEquippedModifiers(equipment));
                }

                player.Hp = player.MaxHp;
                player.Mp = 0;
                player.Power = 0;
                clampedHp = player.Hp;
                clampedMp = player.Mp;
                clampedPower = player.Power;

                _playerAggregateRepository
                    .SaveCollectionsAsync(player.Id, equipment, inventory, skills)
                    .GetAwaiter()
                    .GetResult();
            }

            DeactivateEncounterAfterBattle(session);

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
                GoldBefore: goldBefore,
                GoldAfter: player.Gold,
                GoldGained: goldGained,
                ItemRewards: loot.Items.Select(reward => reward.View).ToArray(),
                EquipmentRewards: loot.Equipment.Select(reward => reward.View).ToArray());
        }

        private void DeactivateEncounterAfterBattle(BattleSessionState session)
        {
            if (session.Kind != BattleSessionKind.Monster ||
                string.IsNullOrWhiteSpace(session.MapId) ||
                !session.RoomId.HasValue ||
                string.IsNullOrWhiteSpace(session.MonsterKey))
            {
                return;
            }

            var respawnDelay = ResolveRespawnDelay(session);
            // Source: MAP_SYSTEM_RECONSTRUCTION.md §5 marks map entity runtime/server sync as missing.
            // Java client has map monsters but no authoritative old server respawn table in recovered sources;
            // remake policy: any completed monster encounter (victory/defeat/surrender) disappears for its
            // respawn window, so returning/surrendering cannot leave the same overlap-trigger active.
            _mapMonsterRosterService.DeactivateEncounterUntil(
                session.MapId,
                session.RoomId.Value,
                session.MonsterKey,
                DateTime.UtcNow.Add(respawnDelay));
        }

        private static TimeSpan ResolveRespawnDelay(BattleSessionState session)
        {
            // Source: Java/data not yet recovered with explicit boss flag. Until DB has IsBoss,
            // classify boss-like encounters by legacy spawn/template/key naming convention only.
            if (ContainsBossToken(session.MonsterKey) ||
                ContainsBossToken(session.SpawnTemplateKey) ||
                session.Enemy.Level >= 100)
            {
                return BossMonsterRespawnDelay;
            }

            return NormalMonsterRespawnDelay;
        }

        private static bool ContainsBossToken(string? value) =>
            !string.IsNullOrWhiteSpace(value) &&
            value.Contains("boss", StringComparison.OrdinalIgnoreCase);

        private static bool ApplyEquippedEquipmentDurabilityLoss(
            List<Twelve.Core.Entities.PlayerEquipmentEntry> equipment,
            int lossAmount)
        {
            // Java evidence: ll.p/tag 139 is current durability and ll.q/tag 144 is max durability.
            // Remake policy confirmed by user on 2026-05-03: after battle, victory loses 1 durability,
            // defeat loses 3 durability; broken equipment stays equipped but contributes no stats/effects.
            if (lossAmount <= 0)
            {
                return false;
            }

            var changed = false;
            for (var i = 0; i < equipment.Count; i++)
            {
                var entry = equipment[i];
                if (!entry.IsEquipped || entry.Durability <= 0)
                {
                    continue;
                }

                var nextDurability = Math.Max(0, entry.Durability - lossAmount);
                if (nextDurability == entry.Durability)
                {
                    continue;
                }

                equipment[i] = new Twelve.Core.Entities.PlayerEquipmentEntry
                {
                    EquipKey = entry.EquipKey,
                    TemplateKey = entry.TemplateKey,
                    Slot = entry.Slot,
                    ResourceId = entry.ResourceId,
                    Level = entry.Level,
                    Durability = nextDurability,
                    MaxDurability = entry.MaxDurability,
                    IsEquipped = entry.IsEquipped,
                    RawJson = entry.RawJson
                };
                changed = true;
            }

            return changed;
        }

        private (long Exp, long Gold) ResolveRewards(BattleSessionState session)
        {
            var battleTemplateId = session.BattleTemplateId;
            if (!battleTemplateId.HasValue && !string.IsNullOrWhiteSpace(session.SpawnTemplateKey))
            {
                battleTemplateId = _monsterSpawnCatalog.GetBySpawnTemplateKey(session.SpawnTemplateKey)?.BattleTemplateId;
            }

            if (!battleTemplateId.HasValue || battleTemplateId.Value <= 0)
            {
                return (0, 0);
            }

            var battleTemplate = _monsterBattleCatalog.GetById(battleTemplateId.Value);
            if (battleTemplate is null)
            {
                return (0, 0);
            }

            return (
                Math.Max(0, battleTemplate.ExpReward),
                Math.Max(0, battleTemplate.GoldReward));
        }

        private static long? ResolvePlayerId(string combatantId)
        {
            const string prefix = "player:";
            if (!combatantId.StartsWith(prefix, StringComparison.OrdinalIgnoreCase))
            {
                return null;
            }

            return long.TryParse(combatantId[prefix.Length..], out var playerId)
                ? playerId
                : null;
        }

        private PlayerContentCatalog.BattleLootReward ResolveLoot(BattleSessionState session)
        {
            var battleTemplateId = session.BattleTemplateId;
            if (!battleTemplateId.HasValue && !string.IsNullOrWhiteSpace(session.SpawnTemplateKey))
            {
                battleTemplateId = _monsterSpawnCatalog.GetBySpawnTemplateKey(session.SpawnTemplateKey)?.BattleTemplateId;
            }

            if (!battleTemplateId.HasValue || battleTemplateId.Value <= 0)
            {
                return new PlayerContentCatalog.BattleLootReward([], []);
            }

            var battleTemplate = _monsterBattleCatalog.GetById(battleTemplateId.Value);
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
