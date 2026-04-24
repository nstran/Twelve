using System;
using Twelve.Core.Battle;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Battle
{
    public sealed class BattleResultService : IBattleResultService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;
        private readonly IMonsterBattleCatalog _monsterBattleCatalog;
        private readonly IPlayerRepository _playerRepository;

        public BattleResultService(
            IBattleSessionStore battleSessionStore,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IMonsterBattleCatalog monsterBattleCatalog,
            IPlayerRepository playerRepository)
        {
            _battleSessionStore = battleSessionStore;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _monsterBattleCatalog = monsterBattleCatalog;
            _playerRepository = playerRepository;
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

            var levelBefore = player.Level;
            var expBefore = player.Exp;
            var quanBefore = player.Gold;
            var clampedHp = Math.Clamp(request.PlayerCurrentHp, 0, Math.Max(1, player.MaxHp));
            player.Hp = clampedHp;

            var expGained = 0L;
            var quanGained = 0L;
            if (request.Result == BattleResultKind.Victory)
            {
                var rewards = ResolveRewards(session);
                expGained = rewards.Exp;
                quanGained = rewards.Quan;
                player.Gold += quanGained;
                PlayerLevelProgression.ApplyExperience(player, expGained);
                if (player.Level > levelBefore)
                {
                    PlayerStatPipeline.RecalculateAndApply(player);
                    clampedHp = player.Hp;
                }
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
                QuanGained: quanGained);
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
    }
}
