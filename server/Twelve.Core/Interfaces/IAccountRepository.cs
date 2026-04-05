using System.Threading.Tasks;
using Twelve.Core.Entities;

namespace Twelve.Core.Interfaces
{
    public interface IAccountRepository
    {
        Task<Account?> GetByUsernameAsync(string username);
        Task<int> CreateAsync(Account account);
        Task UpdateLastLoginAsync(int accountId);
    }

    public interface IPasswordHasher
    {
        string HashPassword(string password, out string salt);
        bool VerifyPassword(string password, string hash, string salt);
    }
}
