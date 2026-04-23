using Twelve.Core.Battle;
using Twelve.Core.Interfaces;
using Microsoft.Extensions.Logging;
using System.IO;
using System.Linq;

namespace Twelve.Application.Battle
{
    public sealed class BattleEnemyTurnPlannerService : IBattleEnemyTurnPlannerService
    {
        private static readonly object TraceLock = new();
        private static readonly string TraceFilePath = Path.Combine(
            AppContext.BaseDirectory,
            "logs",
            "battle-enemy-turn.log");
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;
        private readonly ILogger<BattleEnemyTurnPlannerService> _logger;

        public BattleEnemyTurnPlannerService(
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService,
            ILogger<BattleEnemyTurnPlannerService> logger)
        {
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
            _logger = logger;
        }

        public BattleEnemyTurnPlanResponse? CreatePlan(BattleEnemyTurnPlanRequest request)
        {
            Trace($"request session={request.SessionId} boardShape={DescribeBoardShape(request.Board)}");
            _logger.LogInformation(
                "[EnemyTurnPlanner] request session={SessionId} boardShape={BoardShape}",
                request.SessionId,
                DescribeBoardShape(request.Board));

            if (string.IsNullOrWhiteSpace(request.SessionId))
            {
                Trace("reject empty-session-id");
                _logger.LogWarning("[EnemyTurnPlanner] reject: empty session id");
                return null;
            }

            var session = _battleSessionStore.Get(request.SessionId);
            if (session is null)
            {
                Trace($"reject session-not-found session={request.SessionId}");
                _logger.LogWarning(
                    "[EnemyTurnPlanner] reject: session not found session={SessionId}",
                    request.SessionId);
                return null;
            }

            if (session.IsCompleted)
            {
                Trace($"reject completed session={request.SessionId}");
                _logger.LogWarning(
                    "[EnemyTurnPlanner] reject: completed session={SessionId}",
                    request.SessionId);
                return null;
            }

            if (session.ActiveTurn != BattleSide.Enemy)
            {
                Trace($"reject wrong-turn session={request.SessionId} activeTurn={session.ActiveTurn}");
                _logger.LogWarning(
                    "[EnemyTurnPlanner] reject: wrong turn session={SessionId} activeTurn={ActiveTurn}",
                    request.SessionId,
                    session.ActiveTurn);
                return null;
            }

            var normalizedBoard = _battleBoardService.NormalizeBoard(request.Board);
            if (normalizedBoard is null)
            {
                Trace($"request-board-invalid session={request.SessionId} fallbackShape={DescribeBoardShape(session.Board)}");
                _logger.LogWarning(
                    "[EnemyTurnPlanner] request board invalid -> fallback session board session={SessionId} sessionBoardShape={BoardShape}",
                    request.SessionId,
                    DescribeBoardShape(session.Board));
            }

            var syncedBoard = normalizedBoard ?? session.Board;
            session = session with { Board = syncedBoard };
            _battleSessionStore.Save(session);

            var bestMove = _battleBoardService.EvaluateEnemyMove(syncedBoard);
            Trace(
                $"evaluated session={request.SessionId} activeTurn={session.ActiveTurn} enemyHp={session.Enemy.CurrentHp}/{session.Enemy.MaxHp} enemyMp={session.Enemy.CurrentMp}/{session.Enemy.MaxMp} move={FormatMove(bestMove?.Move)} score={(bestMove is null ? "null" : bestMove.Score.ToString())} swordMatches={(bestMove is null ? "null" : bestMove.SwordMatchCount.ToString())}");
            _logger.LogInformation(
                "[EnemyTurnPlanner] session={SessionId} activeTurn={ActiveTurn} enemyHp={EnemyHp}/{EnemyMaxHp} enemyMp={EnemyMp}/{EnemyMaxMp} move={Move} score={Score} swordMatches={SwordMatches}",
                request.SessionId,
                session.ActiveTurn,
                session.Enemy.CurrentHp,
                session.Enemy.MaxHp,
                session.Enemy.CurrentMp,
                session.Enemy.MaxMp,
                FormatMove(bestMove?.Move),
                bestMove?.Score,
                bestMove?.SwordMatchCount);

            if (bestMove is not null)
            {
                Trace($"response session={request.SessionId} action=Move move={FormatMove(bestMove.Move)}");
                _logger.LogInformation(
                    "[EnemyTurnPlanner] response session={SessionId} action=Move move={Move}",
                    request.SessionId,
                    FormatMove(bestMove.Move));
                return new BattleEnemyTurnPlanResponse(
                    BattleEnemyTurnPlanKind.Move,
                    Move: bestMove.Move);
            }

            Trace($"response session={request.SessionId} action=Pass reason=no-valid-move");
            _logger.LogWarning(
                "[EnemyTurnPlanner] response session={SessionId} action=Pass reason=no-valid-move",
                request.SessionId);
            return new BattleEnemyTurnPlanResponse(BattleEnemyTurnPlanKind.Pass);
        }

        private static string DescribeBoardShape(IReadOnlyList<IReadOnlyList<int?>>? board)
        {
            if (board is null)
            {
                return "null";
            }

            var rowCount = board.Count;
            var colCounts = string.Join(",", board.Select(row => row?.Count.ToString() ?? "null"));
            return $"{rowCount}x[{colCounts}]";
        }

        private static string FormatMove(BattleBoardMove? move)
        {
            if (move is null)
            {
                return "null";
            }

            return $"({move.FromRow},{move.FromCol})->({move.ToRow},{move.ToCol})";
        }

        private static void Trace(string message)
        {
            try
            {
                lock (TraceLock)
                {
                    var directory = Path.GetDirectoryName(TraceFilePath);
                    if (!string.IsNullOrWhiteSpace(directory))
                    {
                        Directory.CreateDirectory(directory);
                    }

                    File.AppendAllText(
                        TraceFilePath,
                        $"{DateTime.Now:yyyy-MM-dd HH:mm:ss.fff} [EnemyTurnPlanner] {message}{Environment.NewLine}");
                }
            }
            catch
            {
                // Intentionally swallow trace file failures; planner must stay functional.
            }
        }
    }
}
