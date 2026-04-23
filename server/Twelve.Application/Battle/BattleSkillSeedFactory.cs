using System;
using System.Collections.Generic;
using System.Linq;
using Twelve.Core.Battle;

namespace Twelve.Application.Battle
{
    internal sealed record BattleSkillSeedRequest(
        int FamilyCode,
        BattleSide CasterSide,
        int SelectedRow,
        int SelectedCol,
        int SkillLevel,
        BattleSkillLevelSource SkillLevelSource,
        IReadOnlyList<IReadOnlyList<int?>>? Board
    );

    internal static class BattleSkillSeedFactory
    {
        private const int JavaTickMs = 40;
        private const int MaxSkillLevel = 12;

        public static BattleSkillPacketSeed? CreateSeed(BattleSkillSeedRequest request)
        {
            if (!IsValidRequest(request))
            {
                return null;
            }

            var victimSide = request.CasterSide == BattleSide.Player
                ? BattleSide.Enemy
                : BattleSide.Player;
            var victimCenter = new BattleSkillActorTarget(victimSide, BattleSkillActorAnchor.Center);
            var victimBottom = new BattleSkillActorTarget(victimSide, BattleSkillActorAnchor.Bottom);
            var skillLevel = ClampSkillLevel(request.SkillLevel);
            var fireballRegionAnchors = BattleSkillTargeting.SelectRandomTwoByTwoRegionAnchors(
                CalculateSkill1000RegionCount(skillLevel));
            var fireballClearCells = BattleSkillTargeting.ExpandTwoByTwoRegionAnchors(fireballRegionAnchors);
            var fireMarkCells = SelectSkill1001MarkedCells(request, skillLevel);
            var skill1001ExtraTurnChancePercent = CalculateSkill1001ExtraTurnChancePercent(skillLevel);
            var skill1001GrantsExtraTurn =
                fireMarkCells.Count > 0 && RollPercent(skill1001ExtraTurnChancePercent);
            var skill1001TurnDelta = skill1001GrantsExtraTurn
                ? new BattleSkillTurnDelta(RemainingTurnsDelta: 1)
                : null;

            return request.FamilyCode switch
            {
                1000 => CreateClearSeed(request, fireballClearCells, fireballRegionAnchors, victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1001 => fireMarkCells.Count == 0
                    ? null
                    : CreateMarkSeed(
                        request,
                        fireMarkCells,
                        stateId: 10,
                        turnDelta: skill1001TurnDelta,
                        impactDelayMs: 10 * JavaTickMs,
                        durationMs: CalculateSkill1001DurationMs(fireMarkCells.Count),
                        grantsExtraTurn: skill1001GrantsExtraTurn,
                        extraTurnChancePercent: skill1001ExtraTurnChancePercent),
                1002 => CreateHelperSeed(request, actorTarget: null),
                1003 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                1004 => CreateNoneSeed(request, victimBottom, hitsActor: true, impactDelayMs: 16 * JavaTickMs),
                1005 => CreateNoneSeed(request, victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1006 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1008 => CreateClearSeed(request, BattleSkillTargeting.SelectColumn(ToCastRequest(request)), new[] { BattleSkillTargeting.ToJavaCell(7, request.SelectedCol) }, victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                2000 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                2001 => CreateHelperSeed(request, actorTarget: null),
                2002 => CreateHelperSeed(request, actorTarget: null),
                2003 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs, durationMs: 14 * JavaTickMs),
                2004 => CreateHelperSeed(request, victimCenter, durationMs: 10 * JavaTickMs),
                2005 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                2006 => CreateClearSeed(request, BattleSkillTargeting.SelectColumn(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 5 * JavaTickMs),
                2007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), victimBottom, hitsActor: true, impactDelayMs: 15 * JavaTickMs),
                2008 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), victimCenter, hitsActor: false),
                4000 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4001 => CreateNoneSeed(request, victimBottom, hitsActor: false, durationMs: 14 * JavaTickMs),
                4002 => CreateHelperSeed(request, victimCenter, durationMs: 15 * JavaTickMs),
                4003 => CreateNoneSeed(request, victimBottom, hitsActor: true, impactDelayMs: 16 * JavaTickMs, durationMs: 13 * JavaTickMs),
                4004 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                4005 => CreateNoneSeed(request, victimCenter, hitsActor: true, impactDelayMs: 16 * JavaTickMs),
                4006 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), BattleSkillTargeting.SelectCellsBySelectedCategory(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4008 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), BattleSkillTargeting.SelectSingleCell(ToCastRequest(request)), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                _ => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
            };
        }

        private static BattleSkillCastRequest ToCastRequest(BattleSkillSeedRequest request) =>
            new(
                SessionId: string.Empty,
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                SelectedRow: request.SelectedRow,
                SelectedCol: request.SelectedCol,
                DebugSkillLevel: request.SkillLevel,
                Board: request.Board);

        private static int ClampSkillLevel(int skillLevel) =>
            Math.Clamp(skillLevel, 1, MaxSkillLevel);

        private static int CalculateSkill1000RegionCount(int skillLevel)
        {
            var upperRegionRoll = Random.Shared.Next(100);

            if (skillLevel <= 6)
            {
                return upperRegionRoll < CalculateSkill1000UpperRegionChancePercent(skillLevel)
                    ? 2
                    : 1;
            }

            return upperRegionRoll < CalculateSkill1000UpperRegionChancePercent(skillLevel)
                ? 3
                : 2;
        }

        private static int CalculateSkill1000UpperRegionChancePercent(int skillLevel)
        {
            if (skillLevel <= 6)
            {
                return 10 + ((skillLevel - 1) * 15);
            }

            return 20 + ((skillLevel - 7) * 12);
        }

        private static int CalculateSkill1001DurationMs(int markedCellCount)
        {
            var safeCount = Math.Max(1, markedCellCount);
            var lastStartTick = 10 + ((safeCount - 1) * 4);
            var trailingAnimationTicks = 15;
            return (lastStartTick + trailingAnimationTicks) * JavaTickMs;
        }

        private static IReadOnlyList<BattleSkillJavaCell> SelectSkill1001MarkedCells(
            BattleSkillSeedRequest request,
            int skillLevel)
        {
            var minMarks = 3 + ((skillLevel - 1) * 5 / 11);
            var maxMarks = 5 + ((skillLevel - 1) * 5 / 11);
            var rolledCount = Random.Shared.Next(minMarks, maxMarks + 1);
            return BattleSkillTargeting.SelectRandomCellsExcludingGems(
                ToCastRequest(request),
                rolledCount,
                0,
                8,
                10,
                20);
        }

        private static int CalculateSkill1001ExtraTurnChancePercent(int skillLevel) =>
            12 + ((skillLevel - 1) * 4);

        private static bool RollPercent(int percent)
        {
            var clampedPercent = Math.Clamp(percent, 0, 100);
            return Random.Shared.Next(100) < clampedPercent;
        }

        private static BattleSkillPacketSeed CreateHelperSeed(
            BattleSkillSeedRequest request,
            BattleSkillActorTarget? actorTarget,
            BattleSkillTurnDelta? turnDelta = null,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.Helper,
                ActorTarget: actorTarget,
                Impact: new BattleSkillImpact(HitsActor: false, Damage: null),
                TurnDelta: turnDelta,
                SkillLevelSource: request.SkillLevelSource,
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateNoneSeed(
            BattleSkillSeedRequest request,
            BattleSkillActorTarget? actorTarget,
            bool hitsActor,
            BattleSkillTurnDelta? turnDelta = null,
            int? impactDelayMs = null,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.None,
                ActorTarget: actorTarget,
                Impact: new BattleSkillImpact(HitsActor: hitsActor, Damage: null),
                TurnDelta: turnDelta,
                SkillLevelSource: request.SkillLevelSource,
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateClearSeed(
            BattleSkillSeedRequest request,
            IReadOnlyList<BattleSkillJavaCell> boardCells,
            IReadOnlyList<BattleSkillJavaCell> cellTargets,
            BattleSkillActorTarget? actorTarget,
            bool hitsActor,
            BattleSkillTurnDelta? turnDelta = null,
            int? impactDelayMs = null,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.Clear,
                BoardMutationCells: boardCells,
                CellTargets: cellTargets,
                ActorTarget: actorTarget,
                Impact: new BattleSkillImpact(HitsActor: hitsActor, Damage: null),
                TurnDelta: turnDelta,
                SkillLevelSource: request.SkillLevelSource,
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateMarkSeed(
            BattleSkillSeedRequest request,
            IReadOnlyList<BattleSkillJavaCell> cells,
            int stateId,
            BattleSkillTurnDelta? turnDelta = null,
            bool grantsExtraTurn = false,
            int? extraTurnChancePercent = null,
            int? impactDelayMs = null,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.Mark,
                BoardMutationCells: cells,
                CellTargets: cells,
                Impact: new BattleSkillImpact(HitsActor: false, Damage: null),
                TurnDelta: turnDelta,
                SkillLevelSource: request.SkillLevelSource,
                GrantsExtraTurn: grantsExtraTurn,
                ExtraTurnChancePercent: extraTurnChancePercent,
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs,
                StateId: stateId
            );
        }

        private static bool IsValidRequest(BattleSkillSeedRequest request)
        {
            if (request.SelectedRow < 0 || request.SelectedRow > 7 ||
                request.SelectedCol < 0 || request.SelectedCol > 7)
            {
                return false;
            }

            if (request.Board is null)
            {
                return true;
            }

            if (request.Board.Count != 8)
            {
                return false;
            }

            return request.Board.All(row => row is not null && row.Count == 8);
        }
    }
}
