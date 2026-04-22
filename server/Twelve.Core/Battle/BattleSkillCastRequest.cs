using System.Collections.Generic;

namespace Twelve.Core.Battle
{
    public sealed record BattleSkillCastRequest(
        int FamilyCode,
        BattleSide CasterSide,
        int SelectedRow,
        int SelectedCol,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );
}
