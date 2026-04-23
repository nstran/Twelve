using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleEnemyTurnPlannerService : IBattleEnemyTurnPlannerService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public BattleEnemyTurnPlannerService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
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

            if (bestMove is not null)
            {
                return new BattleEnemyTurnPlanResponse(
                    BattleEnemyTurnPlanKind.Move,
                    Move: bestMove.Move);
            }

            return new BattleEnemyTurnPlanResponse(BattleEnemyTurnPlanKind.Pass);
        }
    }
}
