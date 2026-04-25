using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattlePvpActionService : IBattlePvpActionService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public BattlePvpActionService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
        }

        public BattlePvpActionResponse? Submit(BattlePvpActionRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null || session.Kind != BattleSessionKind.PvpShadow || session.IsCompleted)
            {
                return null;
            }

            if (request.TurnSeq != session.TurnSeq)
            {
                return ToResponse(session, Reject(session, request.Action, "stale_turn"));
            }

            var actorSide = session.ActiveTurn;
            var move = CreateMove(request);
            var rejectReason = ValidateAction(session, request, move);
            if (rejectReason is not null)
            {
                var rejected = Reject(session, request.Action, rejectReason, move);
                var rejectedSession = session with { LastPvpAction = rejected };
                _battleSessionStore.Save(rejectedSession);
                MirrorLinkedPvpSession(rejectedSession);
                return ToResponse(rejectedSession, rejected);
            }

            var nextBoard = session.Board;
            if (request.Action == BattlePvpActionKind.Swap && move is not null)
            {
                nextBoard = Swap(session.Board, move);
            }

            var accepted = new BattlePvpActionEvent(
                session.TurnSeq + 1,
                actorSide,
                request.Action,
                move,
                Accepted: true);

            // PvP board action is split in two phases in the reconstructed client:
            // 1) /battle/pvp-action validates and mirrors the raw Java-like swap.
            // 2) the acting client resolves match/clear/drop/cascade locally, then persists
            //    the canonical post-cascade board through /battle/session-sync.
            //
            // Source: BATTLE_SYSTEM_RECONSTRUCTION.md "Local Board Simulation" and
            // "Client Port Status". Java client performs swap -> match -> clear -> drop
            // locally, while packet/server state remains the canonical synchronization
            // boundary. Do not pass the turn on the raw swap snapshot; otherwise the
            // opponent stops polling as soon as it receives the pre-collapse board and
            // never sees the final board after pieces are pushed down.
            var nextActiveTurn = request.Action == BattlePvpActionKind.Swap
                ? session.ActiveTurn
                : FlipSide(session.ActiveTurn);
            var nextSession = session with
            {
                Board = nextBoard,
                ActiveTurn = nextActiveTurn,
                TurnSeq = session.TurnSeq + 1,
                LastPvpAction = accepted,
            };

            _battleSessionStore.Save(nextSession);
            MirrorLinkedPvpSession(nextSession);
            return ToResponse(nextSession, accepted);
        }

        private string? ValidateAction(BattleSessionState session, BattlePvpActionRequest request, BattleBoardMove? move)
        {
            if (request.Action == BattlePvpActionKind.Pass)
            {
                return null;
            }

            if (request.Action == BattlePvpActionKind.Skill)
            {
                // Skill damage is still resolved by the existing skill packet endpoint; this reserves/serializes the turn.
                return request.SkillFamilyCode.HasValue ? null : "missing_skill";
            }

            if (move is null)
            {
                return "missing_move";
            }

            return _battleBoardService.EvaluateMove(session.Board, move) is null ? "invalid_swap" : null;
        }

        private void MirrorLinkedPvpSession(BattleSessionState sourceSession)
        {
            if (string.IsNullOrWhiteSpace(sourceSession.LinkedSessionId))
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
                Board = sourceSession.Board,
                ActiveTurn = FlipSide(sourceSession.ActiveTurn),
                Player = CopyRuntimeBars(linked.Player, sourceSession.Enemy),
                Enemy = CopyRuntimeBars(linked.Enemy, sourceSession.Player),
                IsCompleted = sourceSession.IsCompleted,
                TurnSeq = sourceSession.TurnSeq,
                LastPvpAction = FlipAction(sourceSession.LastPvpAction),
            });
        }

        private static BattlePvpActionEvent Reject(
            BattleSessionState session,
            BattlePvpActionKind action,
            string reason,
            BattleBoardMove? move = null) =>
            new(session.TurnSeq, session.ActiveTurn, action, move, Accepted: false, RejectReason: reason);

        private static BattlePvpActionResponse ToResponse(BattleSessionState session, BattlePvpActionEvent lastAction) =>
            new(
                session.SessionId,
                session.ActiveTurn,
                session.Board,
                session.Player.CurrentHp,
                session.Player.CurrentMp,
                session.Player.CurrentPower,
                session.Enemy.CurrentHp,
                session.Enemy.CurrentMp,
                session.Enemy.CurrentPower,
                session.IsCompleted,
                session.TurnSeq,
                lastAction);

        private static BattleBoardMove? CreateMove(BattlePvpActionRequest request) =>
            request.FromRow.HasValue && request.FromCol.HasValue && request.ToRow.HasValue && request.ToCol.HasValue
                ? new BattleBoardMove(request.FromRow.Value, request.FromCol.Value, request.ToRow.Value, request.ToCol.Value)
                : null;

        private static IReadOnlyList<IReadOnlyList<int?>> Swap(IReadOnlyList<IReadOnlyList<int?>> board, BattleBoardMove move)
        {
            var next = board.Select(row => row.ToArray()).ToArray();
            (next[move.FromRow][move.FromCol], next[move.ToRow][move.ToCol]) =
                (next[move.ToRow][move.ToCol], next[move.FromRow][move.FromCol]);
            return next;
        }

        private static BattlePvpActionEvent? FlipAction(BattlePvpActionEvent? action) =>
            action is null ? null : action with { ActorSide = FlipSide(action.ActorSide) };

        private static BattleSessionCombatantState CopyRuntimeBars(
            BattleSessionCombatantState target,
            BattleSessionCombatantState source) =>
            target with
            {
                CurrentHp = Math.Clamp(source.CurrentHp, 0, target.MaxHp),
                CurrentMp = Math.Clamp(source.CurrentMp, 0, target.MaxMp),
                CurrentPower = Math.Clamp(source.CurrentPower, 0, target.MaxPower),
            };

        private static BattleSide FlipSide(BattleSide side) =>
            side == BattleSide.Player ? BattleSide.Enemy : BattleSide.Player;
    }
}