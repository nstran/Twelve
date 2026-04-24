using System.Collections.Generic;
using System.Threading.Tasks;
using Dapper;
using Twelve.Core.Entities;
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

        public async Task<PlayerAggregate?> GetByPlayerIdAsync(int playerId)
        {
            var player = await _playerRepository.GetByIdAsync(playerId);
            if (player is null)
            {
                return null;
            }

            return await GetByPlayerInternalAsync(player);
        }

        private async Task<PlayerAggregate> GetByPlayerInternalAsync(Player player)
        {
            using var connection = _connectionFactory.CreateConnection();

            var equipment = await connection.QueryAsync<PlayerEquipmentEntry>(
                @"SELECT EquipKey, Slot, ResourceId, Level, IsEquipped, RawJson::text AS RawJson
                  FROM PlayerEquipment
                  WHERE PlayerId = @PlayerId
                  ORDER BY IsEquipped DESC, Slot, EquipKey",
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
                    SpecialActorForm = player.SpecialActorForm,
                    AppearanceJson = player.AppearanceJson
                },
                Stats = new PlayerStatSnapshot
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
                    MinDamage = player.DerivedMinDamage,
                    MaxDamage = player.DerivedMaxDamage,
                    Defense = player.DerivedDefense,
                    Dodge = player.DerivedDodge,
                    Hit = player.DerivedHit,
                    Crit = player.DerivedCrit
                },
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
            int playerId,
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            IReadOnlyList<PlayerItemStack> inventory,
            IReadOnlyList<PlayerSkillEntry> skills)
        {
            using var connection = _connectionFactory.CreateConnection();
            using var transaction = connection.BeginTransaction();

            await connection.ExecuteAsync(
                @"DELETE FROM PlayerEquipment WHERE PlayerId = @PlayerId;
                  DELETE FROM PlayerInventory WHERE PlayerId = @PlayerId;
                  DELETE FROM PlayerSkills WHERE PlayerId = @PlayerId;",
                new { PlayerId = playerId },
                transaction);

            const string insertEquipmentSql = @"
                INSERT INTO PlayerEquipment (PlayerId, EquipKey, Slot, ResourceId, Level, IsEquipped, RawJson)
                VALUES (@PlayerId, @EquipKey, @Slot, @ResourceId, @Level, @IsEquipped, CAST(@RawJson AS jsonb))";

            foreach (var entry in equipment)
            {
                await connection.ExecuteAsync(
                    insertEquipmentSql,
                    new
                    {
                        PlayerId = playerId,
                        entry.EquipKey,
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
            int playerId,
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
