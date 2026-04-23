using System.Collections.Generic;

namespace Twelve.Core.Battle
{
    public sealed record BattleSkillCastRequest(
        string SessionId,
        int FamilyCode,
        BattleSide CasterSide,
        int SelectedRow,
        int SelectedCol,
        int? DebugSkillLevel,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );
}
