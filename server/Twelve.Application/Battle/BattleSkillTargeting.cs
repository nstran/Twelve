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
    }
}
