using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleEnemyTurnPlannerService : IBattleEnemyTurnPlannerService
    {
        private const int KeepMoveScoreThreshold = 6;
        private const int WeakMoveScoreThreshold = 3;
        private const int SkillPressureMargin = 12;

        private enum MonsterSkillBehaviorTier
        {
            Weak = 0,
            Standard = 1,
            Elite = 2,
        }

        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;
        private readonly IBattleTurnEngine _battleTurnEngine;
        private readonly IBattleSkillPacketFactory _battleSkillPacketFactory;

        public BattleEnemyTurnPlannerService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService,
            IBattleTurnEngine battleTurnEngine,
            IBattleSkillPacketFactory battleSkillPacketFactory)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
            _battleTurnEngine = battleTurnEngine;
            _battleSkillPacketFactory = battleSkillPacketFactory;
        }

        public BattleEnemyTurnPlanResponse? CreatePlan(BattleEnemyTurnPlanRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null)
            {
                return null;
            }

            if (session.IsCompleted)
            {
                return null;
            }

            if (session.ActiveTurn != BattleSide.Enemy)
            {
                return null;
            }

            var normalizedBoard = _battleBoardService.NormalizeBoard(request.Board);
            var syncedBoard = normalizedBoard ?? session.Board;
            session = session with { Board = syncedBoard };
            _battleSessionStore.Save(session);

            var bestMove = _battleBoardService.EvaluateEnemyMove(syncedBoard);
            if (ShouldPreferSkill(session, bestMove))
            {
                var skillSeed = _battleTurnEngine.ResolveEnemyTurn(new BattleEnemyTurnRequest(
                    request.SessionId,
                    syncedBoard));
                if (skillSeed is not null)
                {
                    return new BattleEnemyTurnPlanResponse(
                        BattleEnemyTurnPlanKind.Skill,
                        SkillPacket: _battleSkillPacketFactory.CreatePacket(skillSeed));
                }
            }

            if (bestMove is not null)
            {
                return new BattleEnemyTurnPlanResponse(
                    BattleEnemyTurnPlanKind.Move,
                    Move: bestMove.Move);
            }

            return new BattleEnemyTurnPlanResponse(BattleEnemyTurnPlanKind.Pass);
        }

        private static bool ShouldPreferSkill(
            BattleSessionState session,
            BattleBoardMoveEvaluation? bestMove)
        {
            var behaviorTier = ResolveBehaviorTier(session.Enemy);
            if (behaviorTier == MonsterSkillBehaviorTier.Weak || session.Enemy.Skills.Count == 0)
            {
                return false;
            }

            var affordableSkills = session.Enemy.Skills
                .Where(skill => skill.ManaCost <= session.Enemy.CurrentMp)
                .ToArray();
            if (affordableSkills.Length == 0)
            {
                return false;
            }

            var strongestSkillPressure = affordableSkills
                .Select(skill => EstimateSkillPressure(session, skill))
                .DefaultIfEmpty(0)
                .Max();

            if (bestMove is null)
            {
                return strongestSkillPressure > 0;
            }

            var movePressure = (bestMove.Score * 4) + (bestMove.SwordMatchCount * 12);
            if (bestMove.SwordMatchCount > 0)
            {
                return false;
            }

            if (behaviorTier == MonsterSkillBehaviorTier.Standard)
            {
                if (bestMove.Score >= 5)
                {
                    return false;
                }

                if (session.Player.CurrentHp <= strongestSkillPressure)
                {
                    return true;
                }

                if (session.Enemy.CurrentHp <= System.Math.Max(1, session.Enemy.MaxHp / 4) &&
                    strongestSkillPressure >= movePressure + 4)
                {
                    return true;
                }

                return bestMove.Score <= 2 &&
                    strongestSkillPressure >= movePressure + 16;
            }

            if (bestMove.Score >= KeepMoveScoreThreshold)
            {
                return false;
            }

            if (session.Player.CurrentHp <= strongestSkillPressure)
            {
                return true;
            }

            if (session.Enemy.CurrentHp <= System.Math.Max(1, session.Enemy.MaxHp / 3) &&
                strongestSkillPressure >= movePressure)
            {
                return true;
            }

            return bestMove.Score <= WeakMoveScoreThreshold &&
                strongestSkillPressure >= movePressure + SkillPressureMargin;
        }

        private static MonsterSkillBehaviorTier ResolveBehaviorTier(BattleSessionCombatantState enemy)
        {
            if (enemy.Skills.Count == 0)
            {
                return MonsterSkillBehaviorTier.Weak;
            }

            if (string.Equals(enemy.AiProfileId, "beast", System.StringComparison.OrdinalIgnoreCase))
            {
                return MonsterSkillBehaviorTier.Weak;
            }

            if (string.Equals(enemy.AiProfileId, "tactician", System.StringComparison.OrdinalIgnoreCase) ||
                string.Equals(enemy.AiProfileId, "elite", System.StringComparison.OrdinalIgnoreCase))
            {
                return MonsterSkillBehaviorTier.Elite;
            }

            if (string.Equals(enemy.AiProfileId, "move_first", System.StringComparison.OrdinalIgnoreCase) ||
                string.Equals(enemy.AiProfileId, "standard", System.StringComparison.OrdinalIgnoreCase))
            {
                return MonsterSkillBehaviorTier.Standard;
            }

            var threatScore = CalculateThreatScore(enemy);
            if (enemy.IqValue >= 10 && (enemy.Level >= 9 || threatScore >= 165))
            {
                return MonsterSkillBehaviorTier.Elite;
            }

            if (enemy.IqValue >= 7 || enemy.Level >= 8 || threatScore >= 120)
            {
                return MonsterSkillBehaviorTier.Standard;
            }

            return MonsterSkillBehaviorTier.Weak;
        }

        private static int CalculateThreatScore(BattleSessionCombatantState enemy)
        {
            var averageDamage = (enemy.MinDamage + enemy.MaxDamage) / 2;
            var statBudget = enemy.Strength + enemy.Agility + enemy.Magic + enemy.Vitality;
            return
                (enemy.Level * 6) +
                (enemy.IqValue * 5) +
                (enemy.Skills.Count * 18) +
                (averageDamage * 3) +
                (enemy.Defense * 2) +
                statBudget +
                (enemy.MaxHp / 4) +
                (enemy.MaxMp / 6);
        }

        private static int EstimateSkillPressure(
            BattleSessionState session,
            BattleSessionSkillInstance skill)
        {
            var baseDamage = (session.Enemy.MinDamage + session.Enemy.MaxDamage) / 2;
            var attackStat = (skill.SkillId / 1000) switch
            {
                1 => session.Enemy.Strength + (session.Enemy.Agility / 3),
                2 => session.Enemy.Magic + (session.Enemy.Agility / 2),
                4 => session.Enemy.Magic + (session.Enemy.Vitality / 3),
                _ => session.Enemy.Strength,
            };
            var scalingPercent = 100 + ((skill.Level - 1) * 8) + ((skill.SkillId % 10) * 2);
            var rawDamage = ((baseDamage + attackStat + (skill.Level * 3)) * scalingPercent) / 100;
            var mitigatedDamage = rawDamage - (session.Player.Defense + (session.Player.Vitality / 2));
            return System.Math.Max(1, mitigatedDamage);
        }
    }
}
