using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbNpcMissionCatalog : INpcMissionCatalog
    {
        private readonly ConcurrentDictionary<string, NpcTalkContext> _talkContexts = new(StringComparer.OrdinalIgnoreCase);
        private readonly ConcurrentDictionary<string, List<MissionSummary>> _missionsByRosterNpc = new(StringComparer.OrdinalIgnoreCase);
        private readonly ConcurrentDictionary<string, MissionDetail> _missionDetails = new(StringComparer.OrdinalIgnoreCase);

        public DbNpcMissionCatalog(IDbConnectionFactory connectionFactory)
        {
            using var conn = connectionFactory.CreateConnection();
            LoadMissionDetails(conn.Query<MissionDetailRow>(
                @"SELECT m.MissionKey,
                         m.Title,
                         m.Description,
                         m.RewardText,
                         o.ObjectiveType,
                         o.TargetKey,
                         o.RequiredAmount,
                         o.SortOrder AS ObjectiveSortOrder,
                         rw.RewardType,
                         rw.RewardKey,
                         rw.Amount AS RewardAmount,
                         rw.SortOrder AS RewardSortOrder
                  FROM MissionCatalog m
                  LEFT JOIN MissionObjectives o ON o.MissionCatalogId = m.Id
                  LEFT JOIN MissionRewards rw ON rw.MissionCatalogId = m.Id
                  ORDER BY m.MissionKey, o.SortOrder, rw.SortOrder"));

            LoadNpcMissionLinks(conn.Query<NpcMissionLinkRow>(
                @"SELECT r.MapId,
                         r.RoomId,
                         c.NpcKey,
                         COALESCE(r.DisplayNameOverride, c.DisplayName) AS DisplayName,
                         m.MissionKey,
                         m.Title,
                         m.Description,
                         m.RewardText,
                         l.SortOrder
                  FROM NpcMapRosters r
                  INNER JOIN NpcCatalog c ON c.Id = r.NpcCatalogId
                  INNER JOIN NpcMissionLinks l ON l.NpcCatalogId = c.Id
                  INNER JOIN MissionCatalog m ON m.Id = l.MissionCatalogId
                  WHERE r.IsActive = TRUE
                    AND l.IsActive = TRUE
                  ORDER BY r.MapId, r.RoomId, c.NpcKey, l.SortOrder, m.MissionKey"));
        }

        public NpcTalkContext? GetTalkContext(string mapId, int roomId, string npcId)
        {
            _talkContexts.TryGetValue(ToRosterNpcKey(mapId, roomId, npcId), out var context);
            return context;
        }

        public IReadOnlyList<MissionSummary> GetAvailableMissions(string mapId, int roomId, string? npcId = null)
        {
            if (!string.IsNullOrWhiteSpace(npcId))
            {
                if (_missionsByRosterNpc.TryGetValue(ToRosterNpcKey(mapId, roomId, npcId), out var npcMissions))
                    return npcMissions;

                return [];
            }

            var result = new List<MissionSummary>();
            foreach (var (key, missions) in _missionsByRosterNpc)
            {
                if (!key.StartsWith(ToRosterKey(mapId, roomId), StringComparison.OrdinalIgnoreCase))
                    continue;

                result.AddRange(missions);
            }

            return result;
        }

        public MissionDetail? GetMissionDetail(string missionKey)
        {
            _missionDetails.TryGetValue(missionKey, out var detail);
            return detail;
        }

        private void LoadMissionDetails(IEnumerable<MissionDetailRow> rows)
        {
            var builders = new Dictionary<string, MissionDetailBuilder>(StringComparer.OrdinalIgnoreCase);
            foreach (var row in rows)
            {
                if (!builders.TryGetValue(row.MissionKey, out var builder))
                {
                    builder = new MissionDetailBuilder(row.MissionKey, row.Title, row.Description, row.RewardText);
                    builders[row.MissionKey] = builder;
                }

                if (!string.IsNullOrWhiteSpace(row.ObjectiveType) && !string.IsNullOrWhiteSpace(row.TargetKey))
                {
                    var objectiveKey = $"{row.ObjectiveType}#{row.TargetKey}#{row.ObjectiveSortOrder}";
                    if (builder.ObjectiveKeys.Add(objectiveKey))
                    {
                        builder.Objectives.Add(new MissionObjective(
                            row.ObjectiveType,
                            row.TargetKey,
                            row.RequiredAmount,
                            row.ObjectiveSortOrder));
                    }
                }

                if (!string.IsNullOrWhiteSpace(row.RewardType) && TryParseRewardType(row.RewardType, out var rewardType))
                {
                    var rewardKey = row.RewardKey ?? "";
                    var rewardIdentity = $"{row.RewardType}#{rewardKey}#{row.RewardSortOrder}";
                    if (builder.RewardKeys.Add(rewardIdentity))
                    {
                        builder.Rewards.Add(new MissionReward(
                            rewardType,
                            row.RewardKey,
                            row.RewardAmount,
                            row.RewardSortOrder));
                    }
                }
            }

            foreach (var builder in builders.Values)
            {
                _missionDetails[builder.MissionKey] = new MissionDetail(
                    builder.MissionKey,
                    builder.Title,
                    builder.Description,
                    builder.RewardText,
                    builder.Objectives,
                    builder.Rewards);
            }
        }

        private void LoadNpcMissionLinks(IEnumerable<NpcMissionLinkRow> rows)
        {
            var contextBuilders = new Dictionary<string, NpcTalkContextBuilder>(StringComparer.OrdinalIgnoreCase);
            foreach (var row in rows)
            {
                var rosterNpcKey = ToRosterNpcKey(row.MapId, row.RoomId, row.NpcKey);
                if (!contextBuilders.TryGetValue(rosterNpcKey, out var builder))
                {
                    builder = new NpcTalkContextBuilder(row.NpcKey, row.DisplayName);
                    contextBuilders[rosterNpcKey] = builder;
                }

                if (builder.MissionKeys.Add(row.MissionKey))
                {
                    builder.Missions.Add(new MissionSummary(
                        row.MissionKey,
                        row.Title,
                        row.Description,
                        row.RewardText,
                        row.SortOrder));
                }
            }

            foreach (var (rosterNpcKey, builder) in contextBuilders)
            {
                _missionsByRosterNpc[rosterNpcKey] = builder.Missions;
                _talkContexts[rosterNpcKey] = new NpcTalkContext(builder.NpcId, builder.DisplayName, builder.Missions);
            }
        }

        private static bool TryParseRewardType(string value, out MissionRewardType rewardType)
        {
            if (Enum.TryParse(value, ignoreCase: true, out rewardType))
                return true;

            rewardType = MissionRewardType.Exp;
            return false;
        }

        private static string ToRosterKey(string mapId, int roomId) => $"{mapId}#{roomId}";

        private static string ToRosterNpcKey(string mapId, int roomId, string npcId) => $"{ToRosterKey(mapId, roomId)}#{npcId}";

        private sealed class MissionDetailRow
        {
            public string MissionKey { get; set; } = "";
            public string Title { get; set; } = "";
            public string Description { get; set; } = "";
            public string RewardText { get; set; } = "";
            public string? ObjectiveType { get; set; }
            public string? TargetKey { get; set; }
            public int RequiredAmount { get; set; }
            public int ObjectiveSortOrder { get; set; }
            public string? RewardType { get; set; }
            public string? RewardKey { get; set; }
            public int RewardAmount { get; set; }
            public int RewardSortOrder { get; set; }
        }

        private sealed class NpcMissionLinkRow
        {
            public string MapId { get; set; } = "";
            public int RoomId { get; set; }
            public string NpcKey { get; set; } = "";
            public string DisplayName { get; set; } = "";
            public string MissionKey { get; set; } = "";
            public string Title { get; set; } = "";
            public string Description { get; set; } = "";
            public string RewardText { get; set; } = "";
            public int SortOrder { get; set; }
        }

        private sealed class MissionDetailBuilder
        {
            public MissionDetailBuilder(string missionKey, string title, string description, string rewardText)
            {
                MissionKey = missionKey;
                Title = title;
                Description = description;
                RewardText = rewardText;
            }

            public string MissionKey { get; }
            public string Title { get; }
            public string Description { get; }
            public string RewardText { get; }
            public List<MissionObjective> Objectives { get; } = new();
            public List<MissionReward> Rewards { get; } = new();
            public HashSet<string> ObjectiveKeys { get; } = new(StringComparer.OrdinalIgnoreCase);
            public HashSet<string> RewardKeys { get; } = new(StringComparer.OrdinalIgnoreCase);
        }

        private sealed class NpcTalkContextBuilder
        {
            public NpcTalkContextBuilder(string npcId, string displayName)
            {
                NpcId = npcId;
                DisplayName = displayName;
            }

            public string NpcId { get; }
            public string DisplayName { get; }
            public List<MissionSummary> Missions { get; } = new();
            public HashSet<string> MissionKeys { get; } = new(StringComparer.OrdinalIgnoreCase);
        }
    }
}
