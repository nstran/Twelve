using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;

namespace Twelve.Application.Battle
{
    internal static class BattleSkillTargeting
    {
        public static IReadOnlyList<BattleSkillJavaCell> SelectSingleCell(BattleSkillCastRequest request) =>
            new[] { ToJavaCell(request.SelectedRow, request.SelectedCol) };

        public static IReadOnlyList<BattleSkillJavaCell> SelectColumn(BattleSkillCastRequest request)
        {
            var cells = new List<BattleSkillJavaCell>(8);
            var javaCol = request.SelectedCol + 2;

            for (var javaRow = 2; javaRow <= 9; javaRow++)
            {
                cells.Add(new BattleSkillJavaCell(javaRow, javaCol));
            }

            return cells;
        }

        public static IReadOnlyList<BattleSkillJavaCell> SelectCellsBySelectedCategory(BattleSkillCastRequest request)
        {
            var category = TryGetBoardCategory(request.Board, request.SelectedRow, request.SelectedCol);
            if (category is null || request.Board is null)
            {
                return SelectSingleCell(request);
            }

            var weightedCells = new List<(int Distance, int RowDelta, int ColDelta, BattleSkillJavaCell Cell)>();
            for (var row = 0; row < request.Board.Count; row++)
            {
                var boardRow = request.Board[row];
                for (var col = 0; col < boardRow.Count; col++)
                {
                    if (TryMapGemCategory(boardRow[col]) != category.Value)
                    {
                        continue;
                    }

                    weightedCells.Add((
                        Distance: Math.Abs(row - request.SelectedRow) + Math.Abs(col - request.SelectedCol),
                        RowDelta: Math.Abs(row - request.SelectedRow),
                        ColDelta: Math.Abs(col - request.SelectedCol),
                        Cell: ToJavaCell(row, col)
                    ));
                }
            }

            if (weightedCells.Count == 0)
            {
                return SelectSingleCell(request);
            }

            return weightedCells
                .OrderBy(item => item.Distance)
                .ThenBy(item => item.RowDelta)
                .ThenBy(item => item.ColDelta)
                .ThenBy(item => item.Cell.Row)
                .ThenBy(item => item.Cell.Col)
                .Select(item => item.Cell)
                .ToArray();
        }

        public static IReadOnlyList<BattleSkillJavaCell> SelectNearestCellsBySelectedCategory(
            BattleSkillCastRequest request,
            int maxCells)
        {
            if (maxCells <= 0)
            {
                return Array.Empty<BattleSkillJavaCell>();
            }

            var ordered = SelectCellsBySelectedCategory(request);
            if (ordered.Count <= maxCells)
            {
                return ordered;
            }

            return ordered
                .Take(maxCells)
                .ToArray();
        }

        public static IReadOnlyList<BattleSkillJavaCell> SelectRandomTwoByTwoRegionAnchors(
            int maxRegions)
        {
            if (maxRegions <= 0)
            {
                return Array.Empty<BattleSkillJavaCell>();
            }

            var candidates = new List<(int TopRow, int TopCol)>(49);
            for (var topRow = 0; topRow <= 6; topRow++)
            {
                for (var topCol = 0; topCol <= 6; topCol++)
                {
                    candidates.Add((topRow, topCol));
                }
            }

            ShuffleInPlace(candidates);

            var selected = new List<(int TopRow, int TopCol)>(maxRegions);
            foreach (var candidate in candidates)
            {
                if (selected.Any(existing => RegionsOverlap(existing.TopRow, existing.TopCol, candidate.TopRow, candidate.TopCol)))
                {
                    continue;
                }

                selected.Add((candidate.TopRow, candidate.TopCol));
                if (selected.Count >= maxRegions)
                {
                    break;
                }
            }

            if (selected.Count == 0)
            {
                var fallback = candidates.Count > 0 ? candidates[0] : (TopRow: 0, TopCol: 0);
                return new[] { ToJavaCell(fallback.TopRow, fallback.TopCol) };
            }

            return selected
                .Select(candidate => ToJavaCell(candidate.TopRow, candidate.TopCol))
                .ToArray();
        }

        public static IReadOnlyList<BattleSkillJavaCell> ExpandTwoByTwoRegionAnchors(
            IReadOnlyList<BattleSkillJavaCell> anchors)
        {
            if (anchors is null || anchors.Count == 0)
            {
                return Array.Empty<BattleSkillJavaCell>();
            }

            var cells = new List<BattleSkillJavaCell>(anchors.Count * 4);
            var seen = new HashSet<string>(StringComparer.Ordinal);

            foreach (var anchor in anchors)
            {
                for (var rowOffset = 0; rowOffset <= 1; rowOffset++)
                {
                    for (var colOffset = 0; colOffset <= 1; colOffset++)
                    {
                        var cell = new BattleSkillJavaCell(anchor.Row + rowOffset, anchor.Col + colOffset);
                        if (cell.Row < 2 || cell.Row > 9 || cell.Col < 2 || cell.Col > 9)
                        {
                            continue;
                        }

                        var key = $"{cell.Row},{cell.Col}";
                        if (!seen.Add(key))
                        {
                            continue;
                        }

                        cells.Add(cell);
                    }
                }
            }

            return cells;
        }

        public static BattleSkillJavaCell ToJavaCell(int clientRow, int clientCol) =>
            new(clientRow + 2, clientCol + 2);

        public static int? TryGetBoardCategory(
            IReadOnlyList<IReadOnlyList<int?>>? board,
            int row,
            int col)
        {
            if (board is null || row < 0 || row >= board.Count)
            {
                return null;
            }

            var boardRow = board[row];
            if (boardRow is null || col < 0 || col >= boardRow.Count)
            {
                return null;
            }

            return TryMapGemCategory(boardRow[col]);
        }

        public static int? TryMapGemCategory(int? gem)
        {
            return gem switch
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
        }

        private static bool RegionsOverlap(
            int leftTopRow,
            int leftTopCol,
            int rightTopRow,
            int rightTopCol) =>
            Math.Abs(leftTopRow - rightTopRow) <= 1 &&
            Math.Abs(leftTopCol - rightTopCol) <= 1;

        private static void ShuffleInPlace<T>(IList<T> items)
        {
            for (var index = items.Count - 1; index > 0; index--)
            {
                var swapIndex = Random.Shared.Next(index + 1);
                (items[index], items[swapIndex]) = (items[swapIndex], items[index]);
            }
        }
    }
}
