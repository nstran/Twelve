using System;
using System.Security.Cryptography;
using System.Threading.Tasks;
using Dapper;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;

namespace Twelve.Infrastructure.Repositories
{
    public class AccountRepository : IAccountRepository
    {
        private readonly Data.IDbConnectionFactory _connectionFactory;

        public AccountRepository(Data.IDbConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<Account?> GetByUsernameAsync(string username)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = "SELECT * FROM Accounts WHERE Username = @Username";
            return await connection.QueryFirstOrDefaultAsync<Account>(sql, new { Username = username });
        }

        public async Task<long> CreateAsync(Account account)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = @"
                INSERT INTO Accounts
                    (Username, PasswordHash, Salt, FullName, DateOfBirth, Phone, Gender, CreatedAt)
                VALUES
                    (@Username, @PasswordHash, @Salt, @FullName, @DateOfBirth, @Phone, @Gender, @CreatedAt)
                RETURNING Id";
            return await connection.ExecuteScalarAsync<long>(sql, account);
        }

        public async Task UpdateLastLoginAsync(long accountId)
        {
            using var connection = _connectionFactory.CreateConnection();
            const string sql = "UPDATE Accounts SET LastLoginAt = @Now WHERE Id = @Id";
            await connection.ExecuteAsync(sql, new { Id = accountId, Now = DateTime.UtcNow });
        }
    }

    public class PasswordHasher : IPasswordHasher
    {
        public string HashPassword(string password, out string salt)
        {
            byte[] saltBytes = RandomNumberGenerator.GetBytes(16);
            salt = Convert.ToBase64String(saltBytes);

            using var rfc = new Rfc2898DeriveBytes(password, saltBytes, 10000, HashAlgorithmName.SHA256);
            return Convert.ToBase64String(rfc.GetBytes(32));
        }

        public bool VerifyPassword(string password, string hash, string salt)
        {
            byte[] saltBytes = Convert.FromBase64String(salt);
            using var rfc = new Rfc2898DeriveBytes(password, saltBytes, 10000, HashAlgorithmName.SHA256);
            string newHash = Convert.ToBase64String(rfc.GetBytes(32));
            return newHash == hash;
        }
    }
}
