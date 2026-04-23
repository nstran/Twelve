using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class ReconstructedBattleBoardService : IBattleBoardService
    {
        private static readonly int[] BaseGems = [0, 1, 2, 3, 4, 5, 6];

        public IReadOnlyList<IReadOnlyList<int?>> CreateInitialBoard()
        {
            for (var attempt = 0; attempt < 64; attempt++)
            {
                var board = CreateNoMatchBoard();
                if (HasValidMove(board))
                {
                    return board;
                }
            }

            return CreateNoMatchBoard();
        }

        public IReadOnlyList<IReadOnlyList<int?>>? NormalizeBoard(IReadOnlyList<IReadOnlyList<int?>>? board)
        {
            if (board is null || board.Count != 8)
            {
                return null;
            }

            var normalized = new List<IReadOnlyList<int?>>(8);
            foreach (var row in board)
            {
                if (row is null || row.Count != 8)
                {
                    return null;
                }

                normalized.Add(row.Select(cell => cell).ToArray());
            }

            return normalized;
        }

        public BattleBoardMoveEvaluation? EvaluateEnemyMove(IReadOnlyList<IReadOnlyList<int?>> board)
        {
            var normalized = NormalizeBoard(board);
            if (normalized is null)
            {
                return null;
            }

            BattleBoardMoveEvaluation? bestMove = null;
            var bestScore = -1;

            for (var row = 0; row < 8; row++)
            {
                for (var col = 0; col < 8; col++)
                {
                    if (col + 1 < 8 && TryScoreMove(normalized, row, col, row, col + 1, out var horizontalScore, out var horizontalSwordMatchCount))
                    {
                        if (horizontalScore > bestScore)
                        {
                            bestScore = horizontalScore;
                            bestMove = new BattleBoardMoveEvaluation(
                                new BattleBoardMove(row, col, row, col + 1),
                                horizontalScore,
                                horizontalSwordMatchCount);
                        }
                    }

                    if (row + 1 < 8 && TryScoreMove(normalized, row, col, row + 1, col, out var verticalScore, out var verticalSwordMatchCount))
                    {
                        if (verticalScore > bestScore)
                        {
                            bestScore = verticalScore;
                            bestMove = new BattleBoardMoveEvaluation(
                                new BattleBoardMove(row, col, row + 1, col),
                                verticalScore,
                                verticalSwordMatchCount);
                        }
                    }
                }
            }

            return bestMove;
        }

        public BattleBoardMove? SelectEnemyMove(IReadOnlyList<IReadOnlyList<int?>> board) =>
            EvaluateEnemyMove(board)?.Move;

        private static IReadOnlyList<IReadOnlyList<int?>> CreateNoMatchBoard()
        {
            var board = new int?[8][];
            for (var row = 0; row < 8; row++)
            {
                board[row] = new int?[8];
                for (var col = 0; col < 8; col++)
                {
                    var forbidden = new HashSet<int>();
                    if (col >= 2 &&
                        TryGetCategory(board[row][col - 1], out var left1) &&
                        TryGetCategory(board[row][col - 2], out var left2) &&
                        left1 == left2)
                    {
                        forbidden.Add(left1);
                    }

                    if (row >= 2 &&
                        TryGetCategory(board[row - 1][col], out var up1) &&
                        TryGetCategory(board[row - 2][col], out var up2) &&
                        up1 == up2)
                    {
                        forbidden.Add(up1);
                    }

                    var pool = BaseGems
                        .Where(gem => !forbidden.Contains(GetCategory(gem)))
                        .ToArray();
                    var source = pool.Length > 0 ? pool : BaseGems;
                    board[row][col] = source[Random.Shared.Next(source.Length)];
                }
            }

            return board;
        }

        private static bool HasValidMove(IReadOnlyList<IReadOnlyList<int?>> board)
        {
            for (var row = 0; row < 8; row++)
            {
                for (var col = 0; col < 8; col++)
                {
                    if (col + 1 < 8 && IsValidSwap(board, row, col, row, col + 1))
                    {
                        return true;
                    }

                    if (row + 1 < 8 && IsValidSwap(board, row, col, row + 1, col))
                    {
                        return true;
                    }
                }
            }

            return false;
        }

        private static bool IsValidSwap(
            IReadOnlyList<IReadOnlyList<int?>> board,
            int row1,
            int col1,
            int row2,
            int col2)
        {
            var first = board[row1][col1];
            var second = board[row2][col2];
            if (first is null || second is null || first == second)
            {
                return false;
            }

            var swapped = CloneBoard(board);
            (swapped[row1][col1], swapped[row2][col2]) = (swapped[row2][col2], swapped[row1][col1]);
            return HasMatchAt(swapped, row1, col1) || HasMatchAt(swapped, row2, col2);
        }

        private static bool TryScoreMove(
            IReadOnlyList<IReadOnlyList<int?>> board,
            int row1,
            int col1,
            int row2,
            int col2,
            out int score,
            out int swordMatchCount)
        {
            score = 0;
            swordMatchCount = 0;
            var first = board[row1][col1];
            var second = board[row2][col2];
            if (first is null || second is null || first == second)
            {
                return false;
            }

            var swapped = CloneBoard(board);
            (swapped[row1][col1], swapped[row2][col2]) = (swapped[row2][col2], swapped[row1][col1]);

            var matched = new HashSet<string>(System.StringComparer.Ordinal);
            CollectMatchedCells(swapped, row1, col1, matched);
            CollectMatchedCells(swapped, row2, col2, matched);
            if (matched.Count == 0)
            {
                return false;
            }

            score = matched.Count;
            foreach (var cell in matched)
            {
                var parts = cell.Split(',');
                var row = int.Parse(parts[0]);
                var col = int.Parse(parts[1]);
                if (TryGetCategory(swapped[row][col], out var category) && category == 0)
                {
                    swordMatchCount++;
                    score += 2;
                }
            }

            return true;
        }

        private static bool HasMatchAt(IReadOnlyList<IReadOnlyList<int?>> board, int row, int col)
        {
            var gem = board[row][col];
            if (!TryGetCategory(gem, out var category))
            {
                return false;
            }

            var horizontal = 1;
            for (var current = col - 1; current >= 0 && TryGetCategory(board[row][current], out var other) && other == category; current--)
            {
                horizontal++;
            }

            for (var current = col + 1; current < 8 && TryGetCategory(board[row][current], out var other) && other == category; current++)
            {
                horizontal++;
            }

            if (horizontal >= 3)
            {
                return true;
            }

            var vertical = 1;
            for (var current = row - 1; current >= 0 && TryGetCategory(board[current][col], out var other) && other == category; current--)
            {
                vertical++;
            }

            for (var current = row + 1; current < 8 && TryGetCategory(board[current][col], out var other) && other == category; current++)
            {
                vertical++;
            }

            return vertical >= 3;
        }

        private static void CollectMatchedCells(
            IReadOnlyList<IReadOnlyList<int?>> board,
            int row,
            int col,
            ISet<string> matched)
        {
            var gem = board[row][col];
            if (!TryGetCategory(gem, out var category))
            {
                return;
            }

            var horizontalCells = new List<(int Row, int Col)> { (row, col) };
            for (var current = col - 1; current >= 0 && TryGetCategory(board[row][current], out var other) && other == category; current--)
            {
                horizontalCells.Add((row, current));
            }

            for (var current = col + 1; current < 8 && TryGetCategory(board[row][current], out var other) && other == category; current++)
            {
                horizontalCells.Add((row, current));
            }

            if (horizontalCells.Count >= 3)
            {
                foreach (var cell in horizontalCells)
                {
                    matched.Add($"{cell.Row},{cell.Col}");
                }
            }

            var verticalCells = new List<(int Row, int Col)> { (row, col) };
            for (var current = row - 1; current >= 0 && TryGetCategory(board[current][col], out var other) && other == category; current--)
            {
                verticalCells.Add((current, col));
            }

            for (var current = row + 1; current < 8 && TryGetCategory(board[current][col], out var other) && other == category; current++)
            {
                verticalCells.Add((current, col));
            }

            if (verticalCells.Count >= 3)
            {
                foreach (var cell in verticalCells)
                {
                    matched.Add($"{cell.Row},{cell.Col}");
                }
            }
        }

        private static int?[][] CloneBoard(IReadOnlyList<IReadOnlyList<int?>> board) =>
            board.Select(row => row.ToArray()).ToArray();

        private static bool TryGetCategory(int? gem, out int category)
        {
            var mapped = gem switch
            {
                0 or 8 or 10 or 20 => (int?)0,
                1 or 11 or 21 => 1,
                2 or 12 or 22 => 2,
                3 or 13 or 23 => 3,
                4 or 14 or 24 => 4,
                5 or 15 or 25 => 5,
                6 or 70 => 6,
                _ => (int?)null,
            };

            if (!mapped.HasValue)
            {
                category = 0;
                return false;
            }

            category = mapped.Value;
            return true;
        }

        private static int GetCategory(int gem) => gem switch
        {
            0 => 0,
            1 => 1,
            2 => 2,
            3 => 3,
            4 => 4,
            5 => 5,
            6 => 6,
            _ => 0,
        };
    }
}
