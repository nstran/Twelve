using System;
using System.Collections.Generic;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    // This factory intentionally does not compute battle outcomes. It only
    // converts Java-shaped battle arrays into the normalized client packet
    // contract once server battle logic already knows the true targets.
    public sealed class BattleSkillPacketFactory : IBattleSkillPacketFactory
    {
        public BattleSkillRuntimePacket CreatePacket(BattleSkillPacketSeed seed)
        {
            ArgumentNullException.ThrowIfNull(seed);

            var boardMutationCells = NormalizeJavaCells(seed.BoardMutationCells);
            var cellTargets = NormalizeJavaCells(seed.CellTargets);

            return new BattleSkillRuntimePacket(
                CastId: string.IsNullOrWhiteSpace(seed.CastId) ? Guid.NewGuid().ToString("N") : seed.CastId,
                FamilyCode: seed.FamilyCode,
                CasterSide: seed.CasterSide,
                ActorTarget: seed.ActorTarget,
                BoardMutation: new BattleSkillBoardMutation(
                    seed.BoardMutationKind,
                    boardMutationCells,
                    seed.StateId
                ),
                CellTargets: cellTargets,
                Impact: seed.Impact ?? new BattleSkillImpact(HitsActor: false, Damage: null),
                ActorDeltas: seed.ActorDeltas,
                TurnDelta: seed.TurnDelta,
                SkillLevelSource: seed.SkillLevelSource,
                GrantsExtraTurn: seed.GrantsExtraTurn,
                ExtraTurnChancePercent: seed.ExtraTurnChancePercent,
                ImpactDelayMs: seed.ImpactDelayMs,
                DurationMs: seed.DurationMs
            );
        }

        private static IReadOnlyList<BattleCell> NormalizeJavaCells(IReadOnlyList<BattleSkillJavaCell>? cells)
        {
            if (cells is null || cells.Count == 0)
            {
                return Array.Empty<BattleCell>();
            }

            var normalized = new List<BattleCell>(cells.Count);
            var seen = new HashSet<string>(StringComparer.Ordinal);

            foreach (var cell in cells)
            {
                if (!TryNormalizeJavaCell(cell, out var normalizedCell))
                {
                    continue;
                }

                var key = $"{normalizedCell.Row},{normalizedCell.Col}";
                if (!seen.Add(key))
                {
                    continue;
                }

                normalized.Add(normalizedCell);
            }

            return normalized;
        }

        private static bool TryNormalizeJavaCell(BattleSkillJavaCell cell, out BattleCell normalizedCell)
        {
            // Java battle board lives at rows 2..9, cols 2..9 in `ms.l`.
            // The RN client uses a compact 0..7 x 0..7 board. Accept both forms
            // so the server can ingest raw Java captures or already-normalized data.
            var normalizedRow = cell.Row >= 2 && cell.Row <= 9 ? cell.Row - 2 : cell.Row;
            var normalizedCol = cell.Col >= 2 && cell.Col <= 9 ? cell.Col - 2 : cell.Col;

            if (normalizedRow < 0 || normalizedRow > 7 || normalizedCol < 0 || normalizedCol > 7)
            {
                normalizedCell = new BattleCell(0, 0);
                return false;
            }

            normalizedCell = new BattleCell(normalizedRow, normalizedCol);
            return true;
        }
    }
}
