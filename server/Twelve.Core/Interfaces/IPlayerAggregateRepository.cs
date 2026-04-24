using System.Threading.Tasks;
using Twelve.Core.Entities;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerAggregateRepository
    {
        Task<PlayerAggregate?> GetByUsernameAsync(string username);
        Task InitializeForCharacterAsync(Player player);
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
