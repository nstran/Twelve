using System;
using System.Collections.Generic;

namespace Twelve.Core.Battle
{
    public enum BattleSide
    {
        Player = 0,
        Enemy = 1,
    }

    public enum BattleSkillActorAnchor
    {
        Center = 0,
        Bottom = 1,
    }

    public enum BattleSkillBoardMutationKind
    {
        Clear = 0,
        Mark = 1,
        Helper = 2,
        None = 3,
    }

    public enum BattleSkillLevelSource
    {
        ServerAuthority = 0,
        ClientDebugRequest = 1,
        ServerFallback = 2,
    }

    public sealed record BattleCell(int Row, int Col);

    public sealed record BattleSkillActorTarget(
        BattleSide Side,
        BattleSkillActorAnchor Anchor
    );

    public sealed record BattleSkillBoardMutation(
        BattleSkillBoardMutationKind Kind,
        IReadOnlyList<BattleCell> Cells,
        int? StateId = null
    );

    public sealed record BattleSkillImpact(
        bool HitsActor,
        int? Damage,
        int? HitShakePx = null
    );

    public sealed record BattleSkillActorDelta(
        BattleSide Side,
        int HpDelta = 0,
        int ManaDelta = 0,
        int PowerDelta = 0
    );

    public sealed record BattleSkillTurnDelta(
        int RemainingTurnsDelta = 0,
        int TimeLeftSecondsDelta = 0
    );

    public sealed record BattleSkillRuntimePacket(
        string CastId,
        int FamilyCode,
        BattleSide CasterSide,
        BattleSkillActorTarget? ActorTarget,
        BattleSkillBoardMutation BoardMutation,
        IReadOnlyList<BattleCell> CellTargets,
        BattleSkillImpact Impact,
        IReadOnlyList<BattleSkillActorDelta>? ActorDeltas = null,
        BattleSkillTurnDelta? TurnDelta = null,
        BattleSkillLevelSource SkillLevelSource = BattleSkillLevelSource.ServerFallback,
        bool GrantsExtraTurn = false,
        int? ExtraTurnChancePercent = null,
        int? ImpactDelayMs = null,
        int? DurationMs = null
    );

    public sealed record BattleSkillJavaCell(int Row, int Col);

    public sealed record BattleSkillPacketSeed(
        int FamilyCode,
        BattleSide CasterSide,
        BattleSkillBoardMutationKind BoardMutationKind,
        IReadOnlyList<BattleSkillJavaCell>? BoardMutationCells = null,
        IReadOnlyList<BattleSkillJavaCell>? CellTargets = null,
        BattleSkillActorTarget? ActorTarget = null,
        BattleSkillImpact? Impact = null,
        IReadOnlyList<BattleSkillActorDelta>? ActorDeltas = null,
        BattleSkillTurnDelta? TurnDelta = null,
        BattleSkillLevelSource SkillLevelSource = BattleSkillLevelSource.ServerFallback,
        bool GrantsExtraTurn = false,
        int? ExtraTurnChancePercent = null,
        int? ImpactDelayMs = null,
        int? DurationMs = null,
        int? StateId = null,
        string? CastId = null
    );
}
