using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core.Entities;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerAggregateRepository
    {
        Task<PlayerAggregate?> GetByUsernameAsync(string username);
        Task<PlayerAggregate?> GetByPlayerIdAsync(int playerId);
        Task InitializeForCharacterAsync(Player player);
        Task SaveCollectionsAsync(
            int playerId,
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            IReadOnlyList<PlayerItemStack> inventory,
            IReadOnlyList<PlayerSkillEntry> skills);
        Task UpsertWorldStateAsync(
            int playerId,
            string mapId,
            int roomId,
            int x,
            int y,
            int direction,
            int actionState);
    }
}
