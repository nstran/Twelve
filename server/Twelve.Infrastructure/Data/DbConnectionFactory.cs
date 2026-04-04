using System.Data;
using Microsoft.Extensions.Configuration;
using Npgsql;

namespace Twelve.Infrastructure.Data
{
    public interface IDbConnectionFactory
    {
        IDbConnection CreateConnection();
    }

    public class DbConnectionFactory : IDbConnectionFactory
    {
        private readonly string _connectionString;

        public DbConnectionFactory(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("Default") 
                ?? throw new System.ArgumentNullException("Connection string 'Default' not found in configuration.");
        }

        public IDbConnection CreateConnection()
        {
            return new NpgsqlConnection(_connectionString);
        }
    }
}
