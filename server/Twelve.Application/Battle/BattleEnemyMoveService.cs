using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class BattleEnemyMoveService : IBattleEnemyMoveService
    {
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public BattleEnemyMoveService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
        }

        public BattleEnemyMoveResponse? CreateMove(BattleEnemyMoveRequest request)
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
            var move = _battleBoardService.SelectEnemyMove(syncedBoard);
            if (move is null)
            {
                return null;
            }

            _battleSessionStore.Save(session with
            {
                Board = syncedBoard,
            });

            return new BattleEnemyMoveResponse(move);
        }
    }
}
