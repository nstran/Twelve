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
            _battleSessionStore.Save(session with
            {
                Board = normalizedBoard,
                ActiveTurn = request.ActiveTurn,
                Player = updatedPlayer,
                Enemy = updatedEnemy,
            });

            return true;
        }

        private static int Clamp(int current, int max) =>
            System.Math.Clamp(current, 0, max);
    }
}
