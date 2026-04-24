using System;
using Twelve.Core.Entities;

namespace Twelve.Core.GameLogic
{
    public static class PlayerLevelProgression
    {
        private const int MaxLevel = 170;
        private const long ExpStep = 100;

        public static long GetLevelFloor(int level)
        {
            var safeLevel = Math.Clamp(level, 1, MaxLevel);
            var completedLevels = safeLevel - 1L;
            return ExpStep * completedLevels * completedLevels;
        }

        public static long GetLevelCeiling(int level)
        {
            var safeLevel = Math.Clamp(level, 1, MaxLevel);
            return safeLevel >= MaxLevel
                ? GetLevelFloor(MaxLevel)
                : ExpStep * safeLevel * safeLevel;
        }

        public static int ApplyExperience(Player player, long expGained)
        {
            var levelBefore = Math.Clamp(player.Level, 1, MaxLevel);
            player.Level = levelBefore;
            player.Exp = Math.Max(0, player.Exp) + Math.Max(0, expGained);

            var levelUps = 0;
            while (player.Level < MaxLevel && player.Exp >= GetLevelCeiling(player.Level))
            {
                player.Level++;
                levelUps++;
            }

            player.ExpFloor = GetLevelFloor(player.Level);
            player.ExpCeiling = GetLevelCeiling(player.Level);
            if (levelUps > 0)
            {
                player.FreePoints += levelUps * 5;
                player.SkillPoints += levelUps;
            }

            return levelUps;
        }

        public static long ApplyDefeatPenalty(Player player)
        {
            var safeLevel = Math.Clamp(player.Level, 1, MaxLevel);
            player.Level = safeLevel;

            var floor = GetLevelFloor(safeLevel);
            var ceiling = GetLevelCeiling(safeLevel);
            var levelSpan = Math.Max(1, ceiling - floor);
            var currentExp = Math.Max(0, player.Exp);
            var currentProgress = Math.Max(0, currentExp - floor);
            if (currentProgress <= 0)
            {
                player.Exp = Math.Max(floor, currentExp);
                player.ExpFloor = floor;
                player.ExpCeiling = ceiling;
                return 0;
            }

            var penalty = Math.Max(10, levelSpan / 20);
            var appliedPenalty = Math.Min(currentProgress, penalty);

            player.Exp = Math.Max(floor, currentExp - appliedPenalty);
            player.ExpFloor = floor;
            player.ExpCeiling = ceiling;

            return appliedPenalty;
        }
    }
}
