using System.Threading.Tasks;
using Twelve.Core.Entities;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerRepository
    {
        Task<Player?> GetByIdAsync(long id);
        Task<Player?> GetByUsernameAsync(string username);
        Task<long> CreateAsync(Player player);
        Task UpdateAsync(Player player);
    }
}
