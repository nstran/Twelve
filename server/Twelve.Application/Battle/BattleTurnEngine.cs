using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleTurnEngine : IBattleTurnEngine
    {
        private const int DefaultPlayerSkillLevel = 12;
        private const int MinimumPowerGain = 3;
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public BattleTurnEngine(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
        }

        public BattleSkillPacketSeed? ResolvePlayerCast(BattleSkillCastRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId) || request.CasterSide != BattleSide.Player)
            {
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null || session.IsCompleted || session.ActiveTurn != BattleSide.Player)
            {
                return null;
            }

            session = SyncSessionBoard(session, request.Board);

            var skillLevel = Math.Clamp(request.DebugSkillLevel ?? DefaultPlayerSkillLevel, 1, DefaultPlayerSkillLevel);
            var baseSeed = BattleSkillSeedFactory.CreateSeed(new BattleSkillSeedRequest(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                SelectedRow: request.SelectedRow,
                SelectedCol: request.SelectedCol,
                SkillLevel: skillLevel,
                SkillLevelSource: request.DebugSkillLevel.HasValue
                    ? BattleSkillLevelSource.ClientDebugRequest
                    : BattleSkillLevelSource.ServerFallback,
                Board: session.Board));

            return baseSeed is null
                ? null
                : FinalizeTurn(
                    session,
                    casterSide: BattleSide.Player,
                    familyCode: request.FamilyCode,
                    skillLevel: skillLevel,
                    manaCost: ResolvePlayerManaCost(session.Player, request.FamilyCode),
                    baseSeed: baseSeed);
        }

        public BattleSkillPacketSeed? ResolveEnemyTurn(BattleEnemyTurnRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null || session.IsCompleted || session.ActiveTurn != BattleSide.Enemy)
            {
                return null;
            }

            session = SyncSessionBoard(session, request.Board);

            var decision = ChooseEnemyDecision(session);
            if (decision is null)
            {
                return null;
            }

            var baseSeed = BattleSkillSeedFactory.CreateSeed(new BattleSkillSeedRequest(
                FamilyCode: decision.Skill.SkillId,
                CasterSide: BattleSide.Enemy,
                SelectedRow: decision.SelectedRow,
                SelectedCol: decision.SelectedCol,
                SkillLevel: decision.Skill.Level,
                SkillLevelSource: BattleSkillLevelSource.ServerAuthority,
                Board: session.Board));

            return baseSeed is null
                ? null
                : FinalizeTurn(
                    session,
                    casterSide: BattleSide.Enemy,
                    familyCode: decision.Skill.SkillId,
                    skillLevel: decision.Skill.Level,
                    manaCost: decision.Skill.ManaCost,
                    baseSeed: baseSeed);
        }

        private BattleSessionState SyncSessionBoard(
            BattleSessionState session,
            IReadOnlyList<IReadOnlyList<int?>>? requestBoard)
        {
            var syncedBoard = _battleBoardService.NormalizeBoard(requestBoard) ?? session.Board;
            var updatedSession = session with { Board = syncedBoard };
            _battleSessionStore.Save(updatedSession);
            return updatedSession;
        }

        private BattleSkillPacketSeed? FinalizeTurn(
            BattleSessionState session,
            BattleSide casterSide,
            int familyCode,
            int skillLevel,
            int manaCost,
            BattleSkillPacketSeed baseSeed)
        {
            var caster = casterSide == BattleSide.Player ? session.Player : session.Enemy;
            var target = casterSide == BattleSide.Player ? session.Enemy : session.Player;
            if (manaCost > Math.Max(0, caster.CurrentMp))
            {
                return null;
            }

            var actorDeltas = new List<BattleSkillActorDelta>();
            var impact = baseSeed.Impact ?? new BattleSkillImpact(HitsActor: false, Damage: null);
            int? damage = null;
            var resolvedTurnDelta = ResolveTurnDelta(baseSeed);
            var rageBurstActive = IsRageBurstActive(caster, baseSeed, impact);

            if (baseSeed.ActorTarget is not null && impact.HitsActor)
            {
                damage = CalculateDamage(caster, target, familyCode, skillLevel, rageBurstActive);
                actorDeltas.Add(new BattleSkillActorDelta(target.Side, HpDelta: -damage.Value));
                actorDeltas.Add(new BattleSkillActorDelta(target.Side, PowerDelta: CalculateTargetPowerGain(damage.Value)));
            }

            if (manaCost > 0)
            {
                actorDeltas.Add(new BattleSkillActorDelta(caster.Side, ManaDelta: -manaCost));
            }

            if (rageBurstActive && caster.CurrentPower > 0)
            {
                actorDeltas.Add(new BattleSkillActorDelta(caster.Side, PowerDelta: -caster.CurrentPower));
            }

            var casterPowerGain = CalculateCasterPowerGain(baseSeed, skillLevel, damage);
            if (casterPowerGain != 0)
            {
                actorDeltas.Add(new BattleSkillActorDelta(caster.Side, PowerDelta: casterPowerGain));
            }

            var updatedPlayer = ApplyDeltas(session.Player, actorDeltas.Where(delta => delta.Side == BattleSide.Player));
            var updatedEnemy = ApplyDeltas(session.Enemy, actorDeltas.Where(delta => delta.Side == BattleSide.Enemy));
            var isCompleted = updatedPlayer.CurrentHp <= 0 || updatedEnemy.CurrentHp <= 0;
            var remainingTurnsDelta = Math.Max(0, resolvedTurnDelta?.RemainingTurnsDelta ?? 0);
            var nextTurn = isCompleted
                ? session.ActiveTurn
                : remainingTurnsDelta > 0
                    ? casterSide
                    : FlipSide(casterSide);

            _battleSessionStore.Save(session with
            {
                Board = ApplyBoardMutation(session.Board, baseSeed),
                Player = updatedPlayer,
                Enemy = updatedEnemy,
                ActiveTurn = nextTurn,
                IsCompleted = isCompleted,
            });

            MirrorLinkedPvpSession(session, baseSeed, updatedPlayer, updatedEnemy, nextTurn, isCompleted);

            return baseSeed with
            {
                ActorDeltas = actorDeltas.Count == 0 ? null : actorDeltas,
                TurnDelta = resolvedTurnDelta,
                Impact = impact with
                {
                    Damage = damage,
                    HitShakePx = damage.HasValue ? Math.Clamp((damage.Value / 4) + 4, 6, 20) : null,
                },
            };
        }

        private void MirrorLinkedPvpSession(
            BattleSessionState sourceSession,
            BattleSkillPacketSeed baseSeed,
            BattleSessionCombatantState updatedPlayer,
            BattleSessionCombatantState updatedEnemy,
            BattleSide nextTurn,
            bool isCompleted)
        {
            if (sourceSession.Kind != BattleSessionKind.PvpShadow || string.IsNullOrWhiteSpace(sourceSession.LinkedSessionId))
            {
                return;
            }

            var linked = _battleSessionStore.Get(sourceSession.LinkedSessionId);
            if (linked is null || linked.Kind != BattleSessionKind.PvpShadow)
            {
                return;
            }

            _battleSessionStore.Save(linked with
            {
                Board = ApplyBoardMutation(sourceSession.Board, baseSeed),
                Player = CopyRuntimeBars(linked.Player, updatedEnemy),
                Enemy = CopyRuntimeBars(linked.Enemy, updatedPlayer),
                ActiveTurn = FlipSide(nextTurn),
                IsCompleted = isCompleted,
            });
        }

        private static BattleSessionCombatantState CopyRuntimeBars(
            BattleSessionCombatantState target,
            BattleSessionCombatantState source) =>
            target with
            {
                CurrentHp = Math.Clamp(source.CurrentHp, 0, target.MaxHp),
                CurrentMp = Math.Clamp(source.CurrentMp, 0, target.MaxMp),
                CurrentPower = Math.Clamp(source.CurrentPower, 0, target.MaxPower),
            };

        private static BattleSessionCombatantState ApplyDeltas(
            BattleSessionCombatantState state,
            IEnumerable<BattleSkillActorDelta> deltas)
        {
            var currentHp = state.CurrentHp;
            var currentMp = state.CurrentMp;
            var currentPower = state.CurrentPower;

            foreach (var delta in deltas)
            {
                currentHp = Math.Clamp(currentHp + delta.HpDelta, 0, state.MaxHp);
                currentMp = Math.Clamp(currentMp + delta.ManaDelta, 0, state.MaxMp);
                currentPower = Math.Clamp(currentPower + delta.PowerDelta, 0, state.MaxPower);
            }

            return state with
            {
                CurrentHp = currentHp,
                CurrentMp = currentMp,
                CurrentPower = currentPower,
            };
        }

        private static int CalculateDamage(
            BattleSessionCombatantState caster,
            BattleSessionCombatantState target,
            int familyCode,
            int skillLevel,
            bool rageBurstActive)
        {
            var baseDamage = (caster.MinDamage + caster.MaxDamage) / 2;
            var attackStat = ResolveAttackStat(caster, familyCode);
            var scalingPercent = 100 + ((skillLevel - 1) * 8) + ((familyCode % 10) * 2);
            var rawDamage = ((baseDamage + attackStat + (skillLevel * 3)) * scalingPercent) / 100;
            var mitigatedDamage = rawDamage - (target.Defense + (target.Vitality / 2));
            var variancePercent = 92 + Random.Shared.Next(17);
            var variedDamage = (Math.Max(1, mitigatedDamage) * variancePercent) / 100;
            var critChance = Math.Clamp(5 + (caster.Agility / 4) + skillLevel - (target.DodgeRate / 3), 5, 30);

            if (RollPercent(critChance))
            {
                variedDamage = (variedDamage * Math.Max(110, caster.CriticalDamage)) / 100;
            }

            if (rageBurstActive)
            {
                variedDamage *= 2;
            }

            return Math.Max(1, variedDamage);
        }

        private static bool IsRageBurstActive(
            BattleSessionCombatantState caster,
            BattleSkillPacketSeed baseSeed,
            BattleSkillImpact impact) =>
            caster.MaxPower > 0 &&
            caster.CurrentPower >= caster.MaxPower &&
            baseSeed.ActorTarget is not null &&
            impact.HitsActor;

        private static BattleSide FlipSide(BattleSide side) =>
            side == BattleSide.Player ? BattleSide.Enemy : BattleSide.Player;

        private static EnemyTurnDecision? ChooseEnemyDecision(BattleSessionState session)
        {
            if (session.Enemy.Skills.Count == 0)
            {
                return null;
            }

            var affordableSkills = session.Enemy.Skills
                .Where(skill => skill.ManaCost <= session.Enemy.CurrentMp)
                .ToList();
            if (affordableSkills.Count == 0)
            {
                return null;
            }

            var selectedSkill = affordableSkills
                .OrderByDescending(skill => EstimateSkillPressure(session, skill))
                .ThenBy(skill => skill.ManaCost)
                .First();
            var (selectedRow, selectedCol) = ResolveEnemySelectedCell(session.Board);
            return new EnemyTurnDecision(selectedSkill, selectedRow, selectedCol);
        }

        private static int ResolvePlayerManaCost(BattleSessionCombatantState player, int familyCode)
        {
            if (player.Skills.Count == 0)
            {
                return 0;
            }

            foreach (var skill in player.Skills)
            {
                if (skill.SkillId == familyCode)
                {
                    return Math.Max(0, skill.ManaCost);
                }
            }

            return 0;
        }

        private static BattleSkillTurnDelta? ResolveTurnDelta(BattleSkillPacketSeed baseSeed)
        {
            if (baseSeed.TurnDelta is not null)
            {
                return baseSeed.TurnDelta;
            }

            return baseSeed.GrantsExtraTurn
                ? new BattleSkillTurnDelta(RemainingTurnsDelta: 1)
                : null;
        }

        private static int ResolveAttackStat(BattleSessionCombatantState caster, int familyCode) =>
            (familyCode / 1000) switch
            {
                1 => caster.Strength + (caster.Agility / 3),
                2 => caster.Magic + (caster.Agility / 2),
                4 => caster.Magic + (caster.Vitality / 3),
                _ => caster.Strength,
            };

        private static int CalculateCasterPowerGain(
            BattleSkillPacketSeed baseSeed,
            int skillLevel,
            int? damage)
        {
            var baseGain = baseSeed.BoardMutationKind switch
            {
                BattleSkillBoardMutationKind.Helper => 4,
                BattleSkillBoardMutationKind.Mark => 5,
                BattleSkillBoardMutationKind.Clear => 6,
                _ => 3,
            };

            if (damage.HasValue && damage.Value > 0)
            {
                baseGain += Math.Clamp(damage.Value / 12, 1, 6);
            }

            return Math.Clamp(baseGain + (skillLevel / 3), MinimumPowerGain, 16);
        }

        private static int CalculateTargetPowerGain(int damage) =>
            Math.Clamp(Math.Max(1, damage / 14), 1, 8);

        private static int EstimateSkillPressure(
            BattleSessionState session,
            BattleSessionSkillInstance skill)
        {
            var baseDamage = (session.Enemy.MinDamage + session.Enemy.MaxDamage) / 2;
            var attackStat = ResolveAttackStat(session.Enemy, skill.SkillId);
            var scalingPercent = 100 + ((skill.Level - 1) * 8) + ((skill.SkillId % 10) * 2);
            var rawDamage = ((baseDamage + attackStat + (skill.Level * 3)) * scalingPercent) / 100;
            var mitigatedDamage = rawDamage - (session.Player.Defense + (session.Player.Vitality / 2));
            return Math.Max(1, mitigatedDamage);
        }

        private static (int SelectedRow, int SelectedCol) ResolveEnemySelectedCell(
            IReadOnlyList<IReadOnlyList<int?>>? board)
        {
            var bestCell = PickSelectedCell(board);
            if (board is null || board.Count == 0)
            {
                return bestCell;
            }

            var preferredCategory = 0;
            var bestScore = -1;
            for (var row = 0; row < Math.Min(board.Count, 8); row++)
            {
                var rowData = board[row];
                if (rowData is null)
                {
                    continue;
                }

                for (var col = 0; col < Math.Min(rowData.Count, 8); col++)
                {
                    var category = ResolveGemCategory(rowData[col]);
                    if (!category.HasValue)
                    {
                        continue;
                    }

                    var score = category.Value == preferredCategory ? 2 : 1;
                    if (score <= bestScore)
                    {
                        continue;
                    }

                    bestScore = score;
                    bestCell = (row, col);
                }
            }

            return bestCell;
        }

        private static int? ResolveGemCategory(int? gem) =>
            gem switch
            {
                0 or 8 or 10 or 20 => 0,
                1 or 11 or 21 => 1,
                2 or 12 or 22 => 2,
                3 or 13 or 23 => 3,
                4 or 14 or 24 => 4,
                5 or 15 or 25 => 5,
                6 or 70 => 6,
                _ => null,
            };

        private static bool RollPercent(int percent) =>
            Random.Shared.Next(100) < Math.Clamp(percent, 0, 100);

        private static (int SelectedRow, int SelectedCol) PickSelectedCell(
            IReadOnlyList<IReadOnlyList<int?>>? board)
        {
            if (board is null || board.Count == 0 || board[0] is null || board[0].Count == 0)
            {
                return (3, 4);
            }

            var row = Random.Shared.Next(Math.Min(board.Count, 8));
            var colCount = Math.Min(board[row].Count, 8);
            if (colCount <= 0)
            {
                return (3, 4);
            }

            var col = Random.Shared.Next(colCount);
            return (row, col);
        }

        private sealed record EnemyTurnDecision(
            BattleSessionSkillInstance Skill,
            int SelectedRow,
            int SelectedCol
        );

        private static IReadOnlyList<IReadOnlyList<int?>> ApplyBoardMutation(
            IReadOnlyList<IReadOnlyList<int?>> board,
            BattleSkillPacketSeed seed)
        {
            if (board.Count != 8)
            {
                return board;
            }

            var mutated = board
                .Select(row => row.ToArray())
                .ToArray();
            var mutationCells = seed.BoardMutationCells;
            if (mutationCells is null || mutationCells.Count == 0)
            {
                return mutated;
            }

            switch (seed.BoardMutationKind)
            {
                case BattleSkillBoardMutationKind.Clear:
                    foreach (var cell in mutationCells)
                    {
                        var row = cell.Row - 2;
                        var col = cell.Col - 2;
                        if (row is < 0 or > 7 || col is < 0 or > 7)
                        {
                            continue;
                        }

                        mutated[row][col] = null;
                    }
                    break;

                case BattleSkillBoardMutationKind.Mark:
                    foreach (var cell in mutationCells)
                    {
                        var row = cell.Row - 2;
                        var col = cell.Col - 2;
                        if (row is < 0 or > 7 || col is < 0 or > 7)
                        {
                            continue;
                        }

                        mutated[row][col] = seed.StateId ?? mutated[row][col];
                    }
                    break;
            }

            return mutated;
        }
    }
}
