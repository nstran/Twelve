using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class ReconstructedBattleBoardService : IBattleBoardService
    {
        // Java reconstruction source:
        // - reference/redecoded/cfr_fresh/mq.java:
        //   board logic scans playable cells 2..9 inside a 12x12 padded matrix, i.e. an 8x8 battle board.
        //   A swap is accepted only if either swapped endpoint creates a horizontal/vertical run >= 3.
        //   Match lengths are encoded in low byte, with left/up counters in bits 16..23 and right/down in 8..15.
        // - reference/redecoded/cfr_fresh/mr.java:
        //   mr.x = 10..15 are special gems created by run >= 4.
        //   mr.y = 20..25 are stronger special gems created by cross run >= 3x3 or run >= 5.
        // Gameplay memory/user evidence chốt board active dùng 8 icon `chess0..8` bỏ `chess7`.
        // Java `nj` vẫn dùng mask/type/imageIndex; node 8 được port như kiếm đỏ/fire sword mask kiếm,
        // match chung kiếm trắng và trigger nổ 3x3 ở client board resolver.
        private static readonly int[] BaseGems = [0, 1, 2, 3, 4, 5, 6, 8];
        private static readonly int[] LineSpecialGems = [10, 11, 12, 13, 14, 15];
        private static readonly int[] CrossSpecialGems = [20, 21, 22, 23, 24, 25];

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

        public BattleBoardMoveEvaluation? EvaluateMove(IReadOnlyList<IReadOnlyList<int?>> board, BattleBoardMove move)
        {
            var normalized = NormalizeBoard(board);
            if (normalized is null || !IsAdjacentInBounds(move))
            {
                return null;
            }

            return TryScoreMove(normalized, move.FromRow, move.FromCol, move.ToRow, move.ToCol, out var score, out var swordMatchCount)
                ? new BattleBoardMoveEvaluation(move, score, swordMatchCount)
                : null;
        }

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

        private static bool IsAdjacentInBounds(BattleBoardMove move)
        {
            if (!IsInBounds(move.FromRow, move.FromCol) || !IsInBounds(move.ToRow, move.ToCol))
            {
                return false;
            }

            return Math.Abs(move.FromRow - move.ToRow) + Math.Abs(move.FromCol - move.ToCol) == 1;
        }

        private static bool IsInBounds(int row, int col) =>
            row >= 0 && row < 8 && col >= 0 && col < 8;

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

            var firstMatch = ResolveJavaMatch(swapped, row1, col1);
            var secondMatch = ResolveJavaMatch(swapped, row2, col2);

            score = matched.Count;
            foreach (var cell in matched)
            {
                var parts = cell.Split(',');
                var row = int.Parse(parts[0]);
                var col = int.Parse(parts[1]);
                if (!TryGetCategory(swapped[row][col], out var category))
                {
                    continue;
                }

                // Keep the existing sword preference because current enemy AI uses it as a simple
                // "attack pressure" heuristic, but layer Java-derived match quality on top of it.
                if (category == 0)
                {
                    swordMatchCount++;
                    score += 2;
                }

                if (IsLineSpecial(swapped[row][col]))
                {
                    score += 4;
                }
                else if (IsCrossSpecial(swapped[row][col]))
                {
                    score += 8;
                }
            }

            score += CalculateJavaMatchQualityBonus(firstMatch);
            score += CalculateJavaMatchQualityBonus(secondMatch);

            return true;
        }

        private static bool HasMatchAt(IReadOnlyList<IReadOnlyList<int?>> board, int row, int col)
        {
            var match = ResolveJavaMatch(board, row, col);
            return match.HorizontalLength >= 3 || match.VerticalLength >= 3;
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

        private static JavaMatchInfo ResolveJavaMatch(IReadOnlyList<IReadOnlyList<int?>> board, int row, int col)
        {
            var gem = board[row][col];
            if (!TryGetCategory(gem, out var category))
            {
                return JavaMatchInfo.Empty;
            }

            var left = 0;
            for (var current = col - 1; current >= 0 && TryGetCategory(board[row][current], out var other) && other == category; current--)
            {
                left++;
            }

            var right = 0;
            for (var current = col + 1; current < 8 && TryGetCategory(board[row][current], out var other) && other == category; current++)
            {
                right++;
            }

            var up = 0;
            for (var current = row - 1; current >= 0 && TryGetCategory(board[current][col], out var other) && other == category; current--)
            {
                up++;
            }

            var down = 0;
            for (var current = row + 1; current < 8 && TryGetCategory(board[current][col], out var other) && other == category; current++)
            {
                down++;
            }

            return new JavaMatchInfo(
                HorizontalLength: left + right + 1,
                VerticalLength: up + down + 1,
                Left: left,
                Right: right,
                Up: up,
                Down: down);
        }

        private static int CalculateJavaMatchQualityBonus(JavaMatchInfo match)
        {
            var bonus = 0;
            if (match.HorizontalLength >= 3)
            {
                bonus += match.HorizontalLength;
            }

            if (match.VerticalLength >= 3)
            {
                bonus += match.VerticalLength;
            }

            if (match.CreatesCrossSpecial)
            {
                bonus += 16;
            }
            else if (match.CreatesLineSpecial)
            {
                bonus += 8;
            }

            return bonus;
        }

        private static bool IsLineSpecial(int? gem) =>
            gem.HasValue && LineSpecialGems.Contains(gem.Value);

        private static bool IsCrossSpecial(int? gem) =>
            gem.HasValue && CrossSpecialGems.Contains(gem.Value);

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
            0 or 8 => 0,
            1 => 1,
            2 => 2,
            3 => 3,
            4 => 4,
            5 => 5,
            6 => 6,
            _ => 0,
        };

        private readonly record struct JavaMatchInfo(
            int HorizontalLength,
            int VerticalLength,
            int Left,
            int Right,
            int Up,
            int Down)
        {
            public static JavaMatchInfo Empty { get; } = new(0, 0, 0, 0, 0, 0);

            public bool CreatesLineSpecial =>
                HorizontalLength >= 4 || VerticalLength >= 4;

            public bool CreatesCrossSpecial =>
                HorizontalLength >= 5 ||
                VerticalLength >= 5 ||
                (HorizontalLength >= 3 && VerticalLength >= 3);
        }
    }
}
