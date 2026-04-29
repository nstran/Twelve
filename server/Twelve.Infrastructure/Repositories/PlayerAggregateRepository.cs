using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Dapper;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public class PlayerAggregateRepository : IPlayerAggregateRepository
    {
        private readonly IDbConnectionFactory _connectionFactory;
        private readonly IPlayerRepository _playerRepository;

        public PlayerAggregateRepository(
            IDbConnectionFactory connectionFactory,
            IPlayerRepository playerRepository)
        {
            _connectionFactory = connectionFactory;
            _playerRepository = playerRepository;
        }

        public async Task<PlayerAggregate?> GetByUsernameAsync(string username)
        {
            var player = await _playerRepository.GetByUsernameAsync(username);
            if (player is null)
            {
                return null;
            }

            return await GetByPlayerInternalAsync(player);
        }

        public async Task<PlayerAggregate?> GetByPlayerIdAsync(long playerId)
        {
            var player = await _playerRepository.GetByIdAsync(playerId);
            if (player is null)
            {
                return null;
            }

            return await GetByPlayerInternalAsync(player);
        }

        public async Task<IReadOnlyList<PlayerAggregate>> ListAsync(int limit = 50)
        {
            using var connection = _connectionFactory.CreateConnection();
            var players = await connection.QueryAsync<Player>(
                @"SELECT *
                  FROM Players
                  ORDER BY LastSeenAt DESC, Level DESC, Username
                  LIMIT @Limit",
                new { Limit = Math.Max(1, Math.Min(200, limit)) });

            var aggregates = new List<PlayerAggregate>();
            foreach (var player in players)
            {
                aggregates.Add(await GetByPlayerInternalAsync(player));
            }

            return aggregates;
        }

        private async Task<PlayerAggregate> GetByPlayerInternalAsync(Player player)
        {
            using var connection = _connectionFactory.CreateConnection();

            var equipment = await connection.QueryAsync<PlayerEquipmentEntry>(
                @"SELECT pe.EquipKey, c.TemplateKey, pe.Slot, pe.ResourceId, pe.Level, pe.IsEquipped, pe.RawJson::text AS RawJson
                  FROM PlayerEquipment pe
                  INNER JOIN EquipmentCatalog c ON c.Id = pe.EquipmentCatalogId
                  WHERE pe.PlayerId = @PlayerId
                  ORDER BY pe.IsEquipped DESC, pe.Slot, pe.EquipKey",
                new { PlayerId = player.Id });

            var inventory = await connection.QueryAsync<PlayerItemStack>(
                @"SELECT ItemId, Quantity, RawJson::text AS RawJson
                  FROM PlayerInventory
                  WHERE PlayerId = @PlayerId
                  ORDER BY ItemId",
                new { PlayerId = player.Id });

            var skills = await connection.QueryAsync<PlayerSkillEntry>(
                @"SELECT SkillId, Level, RawJson::text AS RawJson
                  FROM PlayerSkills
                  WHERE PlayerId = @PlayerId
                  ORDER BY SkillId",
                new { PlayerId = player.Id });

            var overlays = await connection.QueryAsync<PlayerMapOverlay>(
                @"SELECT IconId, EndsAt, DurationMs, RawJson::text AS RawJson
                  FROM PlayerMapOverlays
                  WHERE PlayerId = @PlayerId
                  ORDER BY IconId",
                new { PlayerId = player.Id });

            var worldState = await connection.QueryFirstOrDefaultAsync<PlayerWorldState>(
                @"SELECT MapId, RoomId, X, Y, Direction, ActionState, ActiveBattleSessionId
                  FROM PlayerWorldState
                  WHERE PlayerId = @PlayerId",
                new { PlayerId = player.Id });

            return new PlayerAggregate
            {
                Core = player,
                Appearance = new PlayerAppearance
                {
                    Gender = player.Gender,
                    StorageElement = player.Element ?? 0,
                    RawElementCode = player.RawElementCode,
                    FaceStyle = player.FaceStyle,
                    HairStyle = player.HairStyle,
                    HairColor = player.HairColor,
                    SkinColor = player.SkinColor,
                    Hidden0 = player.AppearanceHidden0,
                    Hidden1 = player.AppearanceHidden1,
                    SpecialActorForm = player.SpecialActorForm
                },
                Stats = BuildStatSnapshot(player, equipment.AsList()),
                Equipment = equipment.AsList(),
                Inventory = inventory.AsList(),
                Skills = skills.AsList(),
                MapOverlays = overlays.AsList(),
                WorldState = worldState ?? new PlayerWorldState
                {
                    MapId = player.CurrentMap,
                    RoomId = player.CurrentRoom
                }
            };
        }

        private static PlayerStatSnapshot BuildStatSnapshot(
            Player player,
            System.Collections.Generic.IReadOnlyList<PlayerEquipmentEntry> equippedEntries)
        {
            // Compute derived stats on-the-fly — never read from stale cached DB columns.
            var derived = PlayerStatPipeline.Calculate(player);
            return new PlayerStatSnapshot
            {
                CuongLuc = player.CuongLuc,
                ThanPhap = player.ThanPhap,
                NoiLuc = player.NoiLuc,
                TheLuc = player.TheLuc,
                BonusCuongLuc = player.BonusCuongLuc,
                BonusThanPhap = player.BonusThanPhap,
                BonusNoiLuc = player.BonusNoiLuc,
                BonusTheLuc = player.BonusTheLuc,
                FreePoints = player.FreePoints,
                SkillPoints = player.SkillPoints,
                MinDamage = derived.MinDamage,
                MaxDamage = derived.MaxDamage,
                Defense = derived.Defense,
                Dodge = derived.Dodge,
                Hit = derived.Hit,
                Crit = derived.Crit
            };
        }

        public async Task InitializeForCharacterAsync(Player player)
        {
            using var connection = _connectionFactory.CreateConnection();

            await connection.ExecuteAsync(
                @"INSERT INTO PlayerWorldState (PlayerId, MapId, RoomId, X, Y, Direction, ActionState)
                  VALUES (@PlayerId, @MapId, @RoomId, 0, 0, 0, 0)
                  ON CONFLICT (PlayerId) DO UPDATE
                  SET MapId = EXCLUDED.MapId,
                      RoomId = EXCLUDED.RoomId,
                      UpdatedAt = CURRENT_TIMESTAMP",
                new
                {
                    PlayerId = player.Id,
                    MapId = player.CurrentMap,
                    RoomId = player.CurrentRoom
                });
        }

        public async Task SaveCollectionsAsync(
            long playerId,
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            IReadOnlyList<PlayerItemStack> inventory,
            IReadOnlyList<PlayerSkillEntry> skills)
        {
            using var connection = _connectionFactory.CreateConnection();
            connection.Open();
            using var transaction = connection.BeginTransaction();

            await connection.ExecuteAsync(
                @"DELETE FROM PlayerEquipment WHERE PlayerId = @PlayerId;
                  DELETE FROM PlayerInventory WHERE PlayerId = @PlayerId;
                  DELETE FROM PlayerSkills WHERE PlayerId = @PlayerId;",
                new { PlayerId = playerId },
                transaction);

            var catalogIdByKey = new Dictionary<string, long>(System.StringComparer.Ordinal);
            const string resolveSql = @"SELECT Id FROM EquipmentCatalog WHERE TemplateKey = @TemplateKey";

            const string insertEquipmentSql = @"
                INSERT INTO PlayerEquipment (PlayerId, EquipKey, EquipmentCatalogId, Slot, ResourceId, Level, IsEquipped, RawJson)
                VALUES (@PlayerId, @EquipKey, @EquipmentCatalogId, @Slot, @ResourceId, @Level, @IsEquipped, CAST(@RawJson AS jsonb))";

            foreach (var entry in equipment)
            {
                var templateKey = entry.TemplateKey ?? string.Empty;
                if (!catalogIdByKey.TryGetValue(templateKey, out var catalogId))
                {
                    catalogId = await connection.QuerySingleAsync<long>(resolveSql, new { TemplateKey = templateKey }, transaction);
                    catalogIdByKey[templateKey] = catalogId;
                }

                await connection.ExecuteAsync(
                    insertEquipmentSql,
                    new
                    {
                        PlayerId = playerId,
                        entry.EquipKey,
                        EquipmentCatalogId = catalogId,
                        entry.Slot,
                        entry.ResourceId,
                        entry.Level,
                        entry.IsEquipped,
                        entry.RawJson
                    },
                    transaction);
            }

            const string insertInventorySql = @"
                INSERT INTO PlayerInventory (PlayerId, ItemId, Quantity, RawJson)
                VALUES (@PlayerId, @ItemId, @Quantity, CAST(@RawJson AS jsonb))";

            foreach (var entry in inventory)
            {
                await connection.ExecuteAsync(
                    insertInventorySql,
                    new
                    {
                        PlayerId = playerId,
                        entry.ItemId,
                        entry.Quantity,
                        entry.RawJson
                    },
                    transaction);
            }

            const string insertSkillsSql = @"
                INSERT INTO PlayerSkills (PlayerId, SkillId, Level, RawJson)
                VALUES (@PlayerId, @SkillId, @Level, CAST(@RawJson AS jsonb))";

            foreach (var entry in skills)
            {
                await connection.ExecuteAsync(
                    insertSkillsSql,
                    new
                    {
                        PlayerId = playerId,
                        entry.SkillId,
                        entry.Level,
                        entry.RawJson
                    },
                    transaction);
            }

            transaction.Commit();
        }

        public async Task UpsertWorldStateAsync(
            long playerId,
            string mapId,
            int roomId,
            int x,
            int y,
            int direction,
            int actionState)
        {
            using var connection = _connectionFactory.CreateConnection();

            await connection.ExecuteAsync(
                @"UPDATE Players
                  SET CurrentMap = @MapId,
                      CurrentRoom = @RoomId,
                      LastSeenAt = CURRENT_TIMESTAMP
                  WHERE Id = @PlayerId;

                  INSERT INTO PlayerWorldState (PlayerId, MapId, RoomId, X, Y, Direction, ActionState)
                  VALUES (@PlayerId, @MapId, @RoomId, @X, @Y, @Direction, @ActionState)
                  ON CONFLICT (PlayerId) DO UPDATE
                  SET MapId = EXCLUDED.MapId,
                      RoomId = EXCLUDED.RoomId,
                      X = EXCLUDED.X,
                      Y = EXCLUDED.Y,
                      Direction = EXCLUDED.Direction,
                      ActionState = EXCLUDED.ActionState,
                      UpdatedAt = CURRENT_TIMESTAMP",
                new
                {
                    PlayerId = playerId,
                    MapId = mapId,
                    RoomId = roomId,
                    X = x,
                    Y = y,
                    Direction = direction,
                    ActionState = actionState
                });
        }
    }
}
