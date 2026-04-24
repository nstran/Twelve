using System;
using System.Collections.Generic;
using Twelve.Core.Players;

namespace Twelve.Core.Battle
{
    public enum BattleSessionKind
    {
        Monster = 0,
        PvpShadow = 1,
    }

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
        bool IsCompleted = false,
        string? SpawnTemplateKey = null,
        string? BattleTemplateId = null,
        BattleSessionKind Kind = BattleSessionKind.Monster,
        long Stake = 0,
        bool AllowSpectators = false,
        bool OneWay = false,
        bool DisableSpecialSkills = false,
        string? LinkedSessionId = null,
        bool IsPerspectiveReversed = false,
        int TurnSeq = 0,
        BattlePvpActionEvent? LastPvpAction = null
    );

    public enum BattlePvpActionKind
    {
        Swap = 0,
        Skill = 1,
        Pass = 2,
    }

    public sealed record BattlePvpActionRequest(
        string SessionId,
        BattlePvpActionKind Action,
        int TurnSeq,
        int? FromRow = null,
        int? FromCol = null,
        int? ToRow = null,
        int? ToCol = null,
        int? SkillFamilyCode = null,
        int? SelectedRow = null,
        int? SelectedCol = null
    );

    public sealed record BattlePvpActionEvent(
        int TurnSeq,
        BattleSide ActorSide,
        BattlePvpActionKind Action,
        BattleBoardMove? Move,
        bool Accepted,
        string? RejectReason = null
    );

    public sealed record BattlePvpActionResponse(
        string SessionId,
        BattleSide ActiveTurn,
        IReadOnlyList<IReadOnlyList<int?>> Board,
        int PlayerCurrentHp,
        int PlayerCurrentMp,
        int PlayerCurrentPower,
        int EnemyCurrentHp,
        int EnemyCurrentMp,
        int EnemyCurrentPower,
        bool IsCompleted,
        int TurnSeq,
        BattlePvpActionEvent LastAction
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

    public sealed record BattleSessionSnapshotRequest(
        string SessionId
    );

    public sealed record BattleSessionSnapshotResponse(
        string SessionId,
        BattleSide ActiveTurn,
        IReadOnlyList<IReadOnlyList<int?>> Board,
        int PlayerCurrentHp,
        int PlayerCurrentMp,
        int PlayerCurrentPower,
        int EnemyCurrentHp,
        int EnemyCurrentMp,
        int EnemyCurrentPower,
        bool IsCompleted,
        BattleSessionKind Kind,
        int TurnSeq = 0,
        BattlePvpActionEvent? LastPvpAction = null
    );


    public enum BattleResultKind
    {
        Victory = 0,
        Defeat = 1,
    }

    public sealed record BattleResultClaimRequest(
        string SessionId,
        BattleResultKind Result,
        int PlayerCurrentHp,
        int PlayerCurrentMp,
        int PlayerCurrentPower
    );

    public sealed record BattleResultRewardResponse(
        BattleResultKind Result,
        int LevelBefore,
        int LevelAfter,
        int LevelUps,
        int CurrentHp,
        int MaxHp,
        long ExpBefore,
        long ExpAfter,
        long ExpFloor,
        long ExpCeiling,
        long ExpGained,
        long QuanBefore,
        long QuanAfter,
        long QuanGained,
        IReadOnlyList<PlayerInventoryItemView>? ItemRewards = null,
        IReadOnlyList<PlayerEquipmentItemView>? EquipmentRewards = null
    );
}
