using System;
using System.Collections.Generic;

namespace Twelve.Core.Battle
{
    public sealed record BattleSessionSkillInstance(
        int SkillId,
        int Level,
        int ManaCost
    );

    public sealed record BattleSessionCombatantState(
        string CombatantId,
        string DisplayName,
        BattleSide Side,
        int CurrentHp,
        int MaxHp,
        int CurrentMp,
        int MaxMp,
        int CurrentPower,
        int MaxPower,
        int Strength,
        int Agility,
        int Magic,
        int Vitality,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int HitRate,
        int DodgeRate,
        int CriticalDamage,
        IReadOnlyList<BattleSessionSkillInstance> Skills,
        int Level = 1,
        int IqValue = 0,
        string? AiProfileId = null
    );

    public sealed record BattleSessionState(
        string SessionId,
        string MonsterKey,
        BattleSide ActiveTurn,
        IReadOnlyList<IReadOnlyList<int?>> Board,
        BattleSessionCombatantState Player,
        BattleSessionCombatantState Enemy,
        DateTime CreatedAtUtc,
        bool IsCompleted = false
    );

    public sealed record BattleEnemyTurnRequest(
        string SessionId,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );

    public sealed record BattleBoardMove(
        int FromRow,
        int FromCol,
        int ToRow,
        int ToCol
    );

    public sealed record BattleBoardMoveEvaluation(
        BattleBoardMove Move,
        int Score,
        int SwordMatchCount
    );

    public sealed record BattleEnemyMoveRequest(
        string SessionId,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );

    public sealed record BattleEnemyMoveResponse(
        BattleBoardMove Move
    );

    public enum BattleEnemyTurnPlanKind
    {
        Move = 0,
        Skill = 1,
        Pass = 2,
    }

    public sealed record BattleEnemyTurnPlanRequest(
        string SessionId,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );

    public sealed record BattleEnemyTurnPlanResponse(
        BattleEnemyTurnPlanKind Action,
        BattleBoardMove? Move = null,
        BattleSkillRuntimePacket? SkillPacket = null
    );

    public sealed record BattleSessionSyncRequest(
        string SessionId,
        IReadOnlyList<IReadOnlyList<int?>>? Board,
        BattleSide ActiveTurn,
        int PlayerCurrentHp,
        int PlayerCurrentMp,
        int PlayerCurrentPower,
        int EnemyCurrentHp,
        int EnemyCurrentMp,
        int EnemyCurrentPower
    );
}
