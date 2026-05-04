using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class PlayerMissionStateRepository : IPlayerMissionStateRepository
    {
        private readonly IDbConnectionFactory _connectionFactory;

        public PlayerMissionStateRepository(IDbConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<MissionPlayerStatus> GetStatusAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            var status = await conn.QueryFirstOrDefaultAsync<string>(
                @"SELECT pm.Status
                  FROM PlayerMissions pm
                  INNER JOIN Players p ON p.Id = pm.PlayerId
                  INNER JOIN MissionCatalog m ON m.Id = pm.MissionCatalogId
                  WHERE p.Username = @Username
                    AND m.MissionKey = @MissionKey",
                new { Username = username, MissionKey = missionKey });

            if (string.IsNullOrWhiteSpace(status))
                return MissionPlayerStatus.Available;

            if (Enum.TryParse(status, ignoreCase: true, out MissionPlayerStatus parsed))
                return parsed;

            return MissionPlayerStatus.Available;
        }

        public async Task AcceptAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            await conn.ExecuteAsync(
                @"INSERT INTO PlayerMissions (PlayerId, MissionCatalogId, Status)
                  SELECT p.Id, m.Id, 'Accepted'
                  FROM Players p
                  INNER JOIN MissionCatalog m ON m.MissionKey = @MissionKey
                  WHERE p.Username = @Username
                  ON CONFLICT (PlayerId, MissionCatalogId) DO UPDATE SET
                      Status = CASE
                          WHEN PlayerMissions.Status = 'RewardClaimed' THEN PlayerMissions.Status
                          ELSE 'Accepted'
                      END,
                      UpdatedAt = NOW();

                  INSERT INTO PlayerMissionObjectives (PlayerId, MissionObjectiveId, CurrentAmount, IsCompleted)
                  SELECT p.Id, o.Id, 0, FALSE
                  FROM Players p
                  INNER JOIN MissionCatalog m ON m.MissionKey = @MissionKey
                  INNER JOIN MissionObjectives o ON o.MissionCatalogId = m.Id
                  WHERE p.Username = @Username
                  ON CONFLICT (PlayerId, MissionObjectiveId) DO NOTHING;",
                new { Username = username, MissionKey = missionKey });
        }

        public async Task CancelAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            await conn.ExecuteAsync(
                @"UPDATE PlayerMissions pm
                  SET Status = 'Canceled', UpdatedAt = NOW()
                  FROM Players p, MissionCatalog m
                  WHERE pm.PlayerId = p.Id
                    AND pm.MissionCatalogId = m.Id
                    AND p.Username = @Username
                    AND m.MissionKey = @MissionKey
                    AND pm.Status IN ('Accepted', 'Completed');",
                new { Username = username, MissionKey = missionKey });
        }

        public async Task<bool> TryBeginRewardClaimAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            var changedRows = await conn.ExecuteAsync(
                @"UPDATE PlayerMissions pm
                  SET Status = 'RewardClaimed',
                      RewardClaimedAt = NOW(),
                      UpdatedAt = NOW()
                  FROM Players p, MissionCatalog m
                  WHERE pm.PlayerId = p.Id
                    AND pm.MissionCatalogId = m.Id
                    AND p.Username = @Username
                    AND m.MissionKey = @MissionKey
                    AND pm.Status = 'Completed';",
                new { Username = username, MissionKey = missionKey });

            return changedRows > 0;
        }

        public async Task CompleteRewardClaimAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            await conn.ExecuteAsync(
                @"UPDATE PlayerMissions pm
                  SET Status = 'RewardClaimed',
                      RewardClaimedAt = COALESCE(pm.RewardClaimedAt, NOW()),
                      UpdatedAt = NOW()
                  FROM Players p, MissionCatalog m
                  WHERE pm.PlayerId = p.Id
                    AND pm.MissionCatalogId = m.Id
                    AND p.Username = @Username
                    AND m.MissionKey = @MissionKey
                    AND pm.Status = 'RewardClaimed';",
                new { Username = username, MissionKey = missionKey });
        }

        public async Task RestoreCompletedAsync(string username, string missionKey)
        {
            using var conn = _connectionFactory.CreateConnection();
            await conn.ExecuteAsync(
                @"UPDATE PlayerMissions pm
                  SET Status = 'Completed',
                      RewardClaimedAt = NULL,
                      UpdatedAt = NOW()
                  FROM Players p, MissionCatalog m
                  WHERE pm.PlayerId = p.Id
                    AND pm.MissionCatalogId = m.Id
                    AND p.Username = @Username
                    AND m.MissionKey = @MissionKey
                    AND pm.Status = 'RewardClaimed';",
                new { Username = username, MissionKey = missionKey });
        }

        public async Task<IReadOnlyList<MissionProgressUpdate>> AddProgressAsync(string username, string objectiveType, string targetKey, int amount)
        {
            if (amount <= 0 || string.IsNullOrWhiteSpace(objectiveType) || string.IsNullOrWhiteSpace(targetKey))
                return [];

            using var conn = _connectionFactory.CreateConnection();
            var updates = await conn.QueryAsync<MissionProgressUpdate>(
                @"WITH changed AS (
                      UPDATE PlayerMissionObjectives pmo
                      SET CurrentAmount = LEAST(o.RequiredAmount, pmo.CurrentAmount + @Amount),
                          IsCompleted = (pmo.CurrentAmount + @Amount) >= o.RequiredAmount,
                          UpdatedAt = NOW()
                      FROM Players p, MissionObjectives o, MissionCatalog m, PlayerMissions pm
                      WHERE pmo.PlayerId = p.Id
                        AND pmo.MissionObjectiveId = o.Id
                        AND o.MissionCatalogId = m.Id
                        AND pm.PlayerId = p.Id
                        AND pm.MissionCatalogId = m.Id
                        AND p.Username = @Username
                        AND pm.Status = 'Accepted'
                        AND o.ObjectiveType = @ObjectiveType
                        AND o.TargetKey = @TargetKey
                        AND pmo.IsCompleted = FALSE
                      RETURNING
                          p.Id AS PlayerId,
                          m.Id AS MissionCatalogId,
                          m.MissionKey,
                          m.Title AS MissionTitle,
                          o.ObjectiveType,
                          o.TargetKey,
                          LEAST(o.RequiredAmount, pmo.CurrentAmount) AS CurrentAmount,
                          o.RequiredAmount,
                          (pmo.CurrentAmount >= o.RequiredAmount) AS ObjectiveCompleted
                  ), completed AS (
                      UPDATE PlayerMissions pm
                      SET Status = 'Completed',
                          CompletedAt = COALESCE(pm.CompletedAt, NOW()),
                          UpdatedAt = NOW()
                      FROM changed c
                      WHERE pm.PlayerId = c.PlayerId
                        AND pm.MissionCatalogId = c.MissionCatalogId
                        AND pm.Status = 'Accepted'
                        AND NOT EXISTS (
                            SELECT 1
                            FROM MissionObjectives o
                            LEFT JOIN PlayerMissionObjectives pmo
                              ON pmo.PlayerId = c.PlayerId
                             AND pmo.MissionObjectiveId = o.Id
                            WHERE o.MissionCatalogId = c.MissionCatalogId
                              AND COALESCE(pmo.IsCompleted, FALSE) = FALSE
                        )
                      RETURNING pm.PlayerId, pm.MissionCatalogId
                  )
                  SELECT
                      c.MissionKey,
                      c.MissionTitle,
                      c.ObjectiveType || ': ' || c.TargetKey || ' ' || c.CurrentAmount || '/' || c.RequiredAmount AS ObjectiveText,
                      c.ObjectiveCompleted,
                      EXISTS (
                          SELECT 1
                          FROM completed done
                          WHERE done.PlayerId = c.PlayerId
                            AND done.MissionCatalogId = c.MissionCatalogId
                      ) AS MissionCompleted
                  FROM changed c;",
                new
                {
                    Username = username,
                    ObjectiveType = objectiveType,
                    TargetKey = targetKey,
                    Amount = amount
                });

            return updates.AsList();
        }
    }
}
