using System.Collections.Generic;
using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleBoardService
    {
        IReadOnlyList<IReadOnlyList<int?>> CreateInitialBoard();
        IReadOnlyList<IReadOnlyList<int?>>? NormalizeBoard(IReadOnlyList<IReadOnlyList<int?>>? board);
        BattleBoardMoveEvaluation? EvaluateEnemyMove(IReadOnlyList<IReadOnlyList<int?>> board);
        BattleBoardMove? SelectEnemyMove(IReadOnlyList<IReadOnlyList<int?>> board);
    }
}
