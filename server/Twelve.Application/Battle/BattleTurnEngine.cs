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

            var syncedBoard = _battleBoardService.NormalizeBoard(request.Board) ?? session.Board;
            session = session with { Board = syncedBoard };

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
                Board: syncedBoard));

            return baseSeed is null
                ? null
                : FinalizeTurn(
                    session,
                    casterSide: BattleSide.Player,
                    familyCode: request.FamilyCode,
                    skillLevel: skillLevel,
                    manaCost: 0,
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

            var syncedBoard = _battleBoardService.NormalizeBoard(request.Board) ?? session.Board;
            session = session with { Board = syncedBoard };

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
                Board: syncedBoard));

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

        private BattleSkillPacketSeed FinalizeTurn(
            BattleSessionState session,
            BattleSide casterSide,
            int familyCode,
            int skillLevel,
            int manaCost,
            BattleSkillPacketSeed baseSeed)
        {
            var caster = casterSide == BattleSide.Player ? session.Player : session.Enemy;
            var target = casterSide == BattleSide.Player ? session.Enemy : session.Player;
            var actorDeltas = new List<BattleSkillActorDelta>();
            var impact = baseSeed.Impact ?? new BattleSkillImpact(HitsActor: false, Damage: null);
            int? damage = null;

            if (baseSeed.ActorTarget is not null && impact.HitsActor)
            {
                damage = CalculateDamage(caster, target, familyCode, skillLevel);
                actorDeltas.Add(new BattleSkillActorDelta(target.Side, HpDelta: -damage.Value));
            }

            if (manaCost > 0)
            {
                actorDeltas.Add(new BattleSkillActorDelta(caster.Side, ManaDelta: -manaCost));
            }

            var updatedPlayer = ApplyDeltas(session.Player, actorDeltas.Where(delta => delta.Side == BattleSide.Player));
            var updatedEnemy = ApplyDeltas(session.Enemy, actorDeltas.Where(delta => delta.Side == BattleSide.Enemy));
            var isCompleted = updatedPlayer.CurrentHp <= 0 || updatedEnemy.CurrentHp <= 0;
            var remainingTurnsDelta = Math.Max(0, baseSeed.TurnDelta?.RemainingTurnsDelta ?? 0);
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

            return baseSeed with
            {
                ActorDeltas = actorDeltas.Count == 0 ? null : actorDeltas,
                Impact = impact with
                {
                    Damage = damage,
                    HitShakePx = damage.HasValue ? Math.Clamp((damage.Value / 4) + 4, 6, 20) : null,
                },
            };
        }

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
            int skillLevel)
        {
            var baseDamage = (caster.MinDamage + caster.MaxDamage) / 2;
            var attackStat = (familyCode / 1000) switch
            {
                1 => caster.Strength + (caster.Agility / 3),
                2 => caster.Magic + (caster.Agility / 2),
                4 => caster.Magic + (caster.Vitality / 3),
                _ => caster.Strength,
            };
            var scalingPercent = 100 + ((skillLevel - 1) * 8) + ((familyCode % 10) * 2);
            var rawDamage = ((baseDamage + attackStat) * scalingPercent) / 100;
            var mitigatedDamage = rawDamage - (target.Defense + (target.Vitality / 2));
            return Math.Max(1, mitigatedDamage);
        }

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
            var skillPool = affordableSkills.Count > 0 ? affordableSkills : session.Enemy.Skills;
            var selectedSkill = skillPool[Random.Shared.Next(skillPool.Count)];
            var (selectedRow, selectedCol) = PickSelectedCell(session.Board);
            return new EnemyTurnDecision(selectedSkill, selectedRow, selectedCol);
        }

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
