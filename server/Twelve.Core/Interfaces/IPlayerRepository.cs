using System.Threading.Tasks;
using Twelve.Core.Entities;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerRepository
    {
        Task<Player?> GetByIdAsync(int id);
        Task<Player?> GetByUsernameAsync(string username);
        Task<int> CreateAsync(Player player);
        Task UpdateAsync(Player player);
    }
}
