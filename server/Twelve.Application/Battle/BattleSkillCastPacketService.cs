using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    // The original Java client only tells us how to render after the server has
    // already decided byArray/byArray2/objectArray/byArray3. Because that Java
    // server source is missing, this service reconstructs those packet shapes
    // from the current board + selected cell using family-specific rules.
    //
    // Rule tiers:
    // - Java-confirmed board shape: column sweep/mark families like 1008, 2006.
    // - Logic reconstruction: selected gem category -> multi-cell targets.
    // - Conservative fallback: selected cell only for simple projectile families.
    public sealed class BattleSkillCastPacketService : IBattleSkillCastPacketService
    {
        private const int JavaTickMs = 40;
        private const int Skill1001FallbackMaxMarks = 5;
        private const int SkillLevelFallback = 12;

        private readonly IBattleSkillPacketFactory _packetFactory;

        public BattleSkillCastPacketService(IBattleSkillPacketFactory packetFactory)
        {
            _packetFactory = packetFactory;
        }

        public BattleSkillRuntimePacket? CreatePacket(BattleSkillCastRequest request)
        {
            if (!IsValidRequest(request))
            {
                return null;
            }

            return _packetFactory.CreatePacket(CreateSeed(request));
        }

        private static BattleSkillPacketSeed CreateSeed(BattleSkillCastRequest request)
        {
            var victimSide = request.CasterSide == BattleSide.Player
                ? BattleSide.Enemy
                : BattleSide.Player;
            var victimCenter = new BattleSkillActorTarget(victimSide, BattleSkillActorAnchor.Center);
            var victimBottom = new BattleSkillActorTarget(victimSide, BattleSkillActorAnchor.Bottom);
            var skillLevel = ClampSkillLevel(request.SkillLevel);
            var fireballRegionAnchors = BattleSkillTargeting.SelectRandomTwoByTwoRegionAnchors(
                CalculateSkill1000RegionCount(skillLevel));
            var fireballClearCells = BattleSkillTargeting.ExpandTwoByTwoRegionAnchors(fireballRegionAnchors);
            var fireMarkCells = BattleSkillTargeting.SelectNearestCellsBySelectedCategory(request, Skill1001FallbackMaxMarks);

            return request.FamilyCode switch
            {
                1000 => CreateClearSeed(request, fireballClearCells, fireballRegionAnchors, victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1001 => CreateMarkSeed(request, fireMarkCells, stateId: 10, impactDelayMs: 10 * JavaTickMs, durationMs: CalculateSkill1001DurationMs(fireMarkCells.Count)),
                1002 => CreateHelperSeed(request, actorTarget: null),
                1003 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                1004 => CreateNoneSeed(request, victimBottom, hitsActor: true, impactDelayMs: 16 * JavaTickMs),
                1005 => CreateNoneSeed(request, victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1006 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(request), BattleSkillTargeting.SelectSingleCell(request), victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(request), BattleSkillTargeting.SelectCellsBySelectedCategory(request), victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                1008 => CreateClearSeed(request, BattleSkillTargeting.SelectColumn(request), new[] { BattleSkillTargeting.ToJavaCell(7, request.SelectedCol) }, victimBottom, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                2000 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(request), BattleSkillTargeting.SelectCellsBySelectedCategory(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                2001 => CreateHelperSeed(request, actorTarget: null),
                2002 => CreateHelperSeed(request, actorTarget: null),
                2003 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(request), BattleSkillTargeting.SelectSingleCell(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs, durationMs: 14 * JavaTickMs),
                2004 => CreateHelperSeed(request, victimCenter, durationMs: 10 * JavaTickMs),
                2005 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                2006 => CreateClearSeed(request, BattleSkillTargeting.SelectColumn(request), BattleSkillTargeting.SelectSingleCell(request), victimCenter, hitsActor: true, impactDelayMs: 5 * JavaTickMs),
                2007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(request), BattleSkillTargeting.SelectCellsBySelectedCategory(request), victimBottom, hitsActor: true, impactDelayMs: 15 * JavaTickMs),
                2008 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(request), BattleSkillTargeting.SelectCellsBySelectedCategory(request), victimCenter, hitsActor: false),
                4000 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(request), BattleSkillTargeting.SelectSingleCell(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4001 => CreateNoneSeed(request, victimBottom, hitsActor: false, durationMs: 14 * JavaTickMs),
                4002 => CreateHelperSeed(request, victimCenter, durationMs: 15 * JavaTickMs),
                4003 => CreateNoneSeed(request, victimBottom, hitsActor: true, impactDelayMs: 16 * JavaTickMs, durationMs: 13 * JavaTickMs),
                4004 => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
                4005 => CreateNoneSeed(request, victimCenter, hitsActor: true, impactDelayMs: 16 * JavaTickMs),
                4006 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(request), BattleSkillTargeting.SelectSingleCell(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4007 => CreateClearSeed(request, BattleSkillTargeting.SelectCellsBySelectedCategory(request), BattleSkillTargeting.SelectCellsBySelectedCategory(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                4008 => CreateClearSeed(request, BattleSkillTargeting.SelectSingleCell(request), BattleSkillTargeting.SelectSingleCell(request), victimCenter, hitsActor: true, impactDelayMs: 10 * JavaTickMs),
                _ => CreateNoneSeed(request, actorTarget: null, hitsActor: false),
            };
        }

        private static int ClampSkillLevel(int? skillLevel) =>
            Math.Clamp(skillLevel ?? SkillLevelFallback, 1, SkillLevelFallback);

        private static int CalculateSkill1000RegionCount(int skillLevel)
        {
            // Reconstructed from the level-dialog range:
            // - level 1..6 lives in the 1..2-region band
            // - level 7..12 lives in the 2..3-region band
            // The count should stay probabilistic even at max level, so roll
            // the upper bound chance per cast instead of fixing the result.
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
                // level 1 -> 10% chance to reach 2 regions
                // level 6 -> 85% chance to reach 2 regions
                return 10 + ((skillLevel - 1) * 15);
            }

            // level 7 -> 20% chance to reach 3 regions
            // level 12 -> 80% chance to reach 3 regions
            return 20 + ((skillLevel - 7) * 12);
        }

        private static int CalculateSkill1001DurationMs(int markedCellCount)
        {
            // Java `mt` starts at tick 10 and adds +4 ticks per marked cell for `1001`.
            // The exact targeting logic belongs to the missing server, so keep the
            // fallback conservative and let the packet duration scale with the cells
            // we actually emit.
            var safeCount = Math.Max(1, markedCellCount);
            var lastStartTick = 10 + ((safeCount - 1) * 4);
            var trailingAnimationTicks = 12;
            return (lastStartTick + trailingAnimationTicks) * JavaTickMs;
        }

        private static BattleSkillPacketSeed CreateHelperSeed(
            BattleSkillCastRequest request,
            BattleSkillActorTarget? actorTarget,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.Helper,
                ActorTarget: actorTarget,
                Impact: new BattleSkillImpact(HitsActor: false, Damage: null),
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateNoneSeed(
            BattleSkillCastRequest request,
            BattleSkillActorTarget? actorTarget,
            bool hitsActor,
            int? impactDelayMs = null,
            int? durationMs = null)
        {
            return new BattleSkillPacketSeed(
                FamilyCode: request.FamilyCode,
                CasterSide: request.CasterSide,
                BoardMutationKind: BattleSkillBoardMutationKind.None,
                ActorTarget: actorTarget,
                Impact: new BattleSkillImpact(HitsActor: hitsActor, Damage: null),
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateClearSeed(
            BattleSkillCastRequest request,
            IReadOnlyList<BattleSkillJavaCell> boardCells,
            IReadOnlyList<BattleSkillJavaCell> cellTargets,
            BattleSkillActorTarget? actorTarget,
            bool hitsActor,
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
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs
            );
        }

        private static BattleSkillPacketSeed CreateMarkSeed(
            BattleSkillCastRequest request,
            IReadOnlyList<BattleSkillJavaCell> cells,
            int stateId,
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
                ImpactDelayMs: impactDelayMs,
                DurationMs: durationMs,
                StateId: stateId
            );
        }
        private static bool IsValidRequest(BattleSkillCastRequest request)
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

            foreach (var row in request.Board)
            {
                if (row is null || row.Count != 8)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
