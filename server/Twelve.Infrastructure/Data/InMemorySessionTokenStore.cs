using System;
using System.Collections.Concurrent;
using System.Threading.Tasks;
using Twelve.Core.Interfaces;

namespace Twelve.Infrastructure.Data
{
    /// <summary>
    /// In-memory session token store.
    /// Token hết hạn sau 24 giờ. Dữ liệu mất khi restart server (hành vi mong muốn).
    /// </summary>
    public class InMemorySessionTokenStore : ISessionTokenStore
    {
        private static readonly TimeSpan TokenLifetime = TimeSpan.FromHours(24);

        private record TokenEntry(string Username, DateTimeOffset ExpiresAt);

        private readonly ConcurrentDictionary<string, TokenEntry> _tokens = new();

        public Task<string> CreateTokenAsync(string username)
        {
            // Xoá token cũ của user này (nếu có) — mỗi user chỉ có 1 token active
            foreach (var kv in _tokens)
            {
                if (kv.Value.Username == username)
                    _tokens.TryRemove(kv.Key, out _);
            }

            var token    = Guid.NewGuid().ToString("N"); // 32-char hex, không có dấu gạch
            var expiresAt = DateTimeOffset.UtcNow.Add(TokenLifetime);
            _tokens[token] = new TokenEntry(username, expiresAt);
            return Task.FromResult(token);
        }

        public Task<string?> ValidateTokenAsync(string token)
        {
            if (_tokens.TryGetValue(token, out var entry))
            {
                if (DateTimeOffset.UtcNow < entry.ExpiresAt)
                    return Task.FromResult<string?>(entry.Username);

                // Đã hết hạn → xoá luôn
                _tokens.TryRemove(token, out _);
            }
            return Task.FromResult<string?>(null);
        }

        public Task RevokeTokenAsync(string token)
        {
            _tokens.TryRemove(token, out _);
            return Task.CompletedTask;
        }
    }
}
