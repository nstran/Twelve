using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleSessionSyncService : IBattleSessionSyncService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public BattleSessionSyncService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
        }

        public bool Sync(BattleSessionSyncRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                return false;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null)
            {
                return false;
            }

            var normalizedBoard = _battleBoardService.NormalizeBoard(request.Board) ?? session.Board;
            var updatedPlayer = session.Player with
            {
                CurrentHp = Clamp(request.PlayerCurrentHp, session.Player.MaxHp),
                CurrentMp = Clamp(request.PlayerCurrentMp, session.Player.MaxMp),
                CurrentPower = Clamp(request.PlayerCurrentPower, session.Player.MaxPower),
            };
            var updatedEnemy = session.Enemy with
            {
                CurrentHp = Clamp(request.EnemyCurrentHp, session.Enemy.MaxHp),
                CurrentMp = Clamp(request.EnemyCurrentMp, session.Enemy.MaxMp),
                CurrentPower = Clamp(request.EnemyCurrentPower, session.Enemy.MaxPower),
            };

            var hasCanonicalRuntimeChange =
                !BoardsEqual(session.Board, normalizedBoard) ||
                session.ActiveTurn != request.ActiveTurn ||
                !RuntimeBarsEqual(session.Player, updatedPlayer) ||
                !RuntimeBarsEqual(session.Enemy, updatedEnemy);
            var nextTurnSeq = session.Kind == BattleSessionKind.PvpShadow && hasCanonicalRuntimeChange
                ? session.TurnSeq + 1
                : session.TurnSeq;

            // PvP shadow sessions use TurnSeq as the canonical state version, not only as
            // the raw action counter. The action endpoint first mirrors a validated swap,
            // then the reconstructed Java-like client resolves match/cascade locally and
            // calls this sync endpoint. Bumping TurnSeq here lets the opponent accept the
            // post-cascade board instead of discarding it as "same turnSeq" stale data.
            var updatedSession = session with
            {
                Board = normalizedBoard,
                ActiveTurn = request.ActiveTurn,
                Player = updatedPlayer,
                Enemy = updatedEnemy,
                TurnSeq = nextTurnSeq,
            };
            _battleSessionStore.Save(updatedSession);

            MirrorLinkedPvpSession(updatedSession, normalizedBoard, request.ActiveTurn, updatedPlayer, updatedEnemy);

            return true;
        }

        private void MirrorLinkedPvpSession(
            BattleSessionState sourceSession,
            System.Collections.Generic.IReadOnlyList<System.Collections.Generic.IReadOnlyList<int?>> board,
            BattleSide activeTurn,
            BattleSessionCombatantState updatedPlayer,
            BattleSessionCombatantState updatedEnemy)
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
                Board = board,
                ActiveTurn = FlipSide(activeTurn),
                Player = CopyRuntimeBars(linked.Player, updatedEnemy),
                Enemy = CopyRuntimeBars(linked.Enemy, updatedPlayer),
                TurnSeq = sourceSession.TurnSeq,
            });
        }

        private static bool BoardsEqual(
            System.Collections.Generic.IReadOnlyList<System.Collections.Generic.IReadOnlyList<int?>> left,
            System.Collections.Generic.IReadOnlyList<System.Collections.Generic.IReadOnlyList<int?>> right)
        {
            if (left.Count != right.Count)
            {
                return false;
            }

            for (var row = 0; row < left.Count; row++)
            {
                if (left[row].Count != right[row].Count)
                {
                    return false;
                }

                for (var col = 0; col < left[row].Count; col++)
                {
                    if (left[row][col] != right[row][col])
                    {
                        return false;
                    }
                }
            }

            return true;
        }

        private static bool RuntimeBarsEqual(BattleSessionCombatantState left, BattleSessionCombatantState right) =>
            left.CurrentHp == right.CurrentHp &&
            left.CurrentMp == right.CurrentMp &&
            left.CurrentPower == right.CurrentPower;

        private static BattleSessionCombatantState CopyRuntimeBars(
            BattleSessionCombatantState target,
            BattleSessionCombatantState source) =>
            target with
            {
                CurrentHp = Clamp(source.CurrentHp, target.MaxHp),
                CurrentMp = Clamp(source.CurrentMp, target.MaxMp),
                CurrentPower = Clamp(source.CurrentPower, target.MaxPower),
            };

        private static BattleSide FlipSide(BattleSide side) =>
            side == BattleSide.Player ? BattleSide.Enemy : BattleSide.Player;

        private static int Clamp(int current, int max) =>
            System.Math.Clamp(current, 0, max);
    }
}
