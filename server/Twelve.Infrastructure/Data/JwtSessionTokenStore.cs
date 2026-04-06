using System;
using System.Collections.Concurrent;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;
using Twelve.Core.Interfaces;

namespace Twelve.Infrastructure.Data
{
    /// <summary>
    /// JWT-based session token store — BLACKLIST strategy.
    ///
    /// Cơ chế bảo mật:
    ///  • Token là JWT chuẩn, ký HMAC-SHA256 với secret key từ config.
    ///  • Payload: sub (username), jti (ID duy nhất), iat, exp, iss, aud.
    ///  • Validate: kiểm tra chữ ký + thời hạn + JTI chưa bị thu hồi.
    ///  • Blacklist strategy: mặc định chấp nhận mọi JWT hợp lệ, chỉ từ chối
    ///    token bị thu hồi tường minh (đăng xuất / rolling login).
    ///    → Server restart KHÔNG làm mất session của người dùng.
    ///  • Mỗi user chỉ có 1 token active — login mới thu hồi token cũ (rolling).
    ///  • Blacklist tự dọn entry hết hạn để không tốn bộ nhớ vô tận.
    /// </summary>
    public class JwtSessionTokenStore : ISessionTokenStore
    {
        private readonly SymmetricSecurityKey _signingKey;
        private readonly string _issuer;
        private readonly string _audience;
        private readonly TimeSpan _lifetime;
        private readonly ILogger<JwtSessionTokenStore> _logger;
        // Tắt auto-mapping claim (ví dụ "sub" → ClaimTypes.NameIdentifier)
        // để giữ nguyên tên claim như trong JWT payload
        private readonly JwtSecurityTokenHandler _handler = new()
        {
            InboundClaimTypeMap = new System.Collections.Generic.Dictionary<string, string>()
        };
        private readonly TokenValidationParameters _validationParams;

        // Blacklist: jti → thời điểm hết hạn (để dọn bộ nhớ)
        private readonly ConcurrentDictionary<string, DateTimeOffset> _revokedJtis = new();
        // username → jti hiện tại (để thu hồi token cũ khi rolling)
        private readonly ConcurrentDictionary<string, string> _userCurrentJti = new();

        public JwtSessionTokenStore(IConfiguration config, ILogger<JwtSessionTokenStore> logger)
        {
            _logger = logger;

            var secret = config["Jwt:Secret"]
                ?? throw new InvalidOperationException("Jwt:Secret chưa được cấu hình trong appsettings.json");
            _issuer   = config["Jwt:Issuer"]   ?? "TwelveServer";
            _audience = config["Jwt:Audience"] ?? "TwelveGame";
            int hours = int.TryParse(config["Jwt:ExpiryHours"], out var h) ? h : 24;
            _lifetime = TimeSpan.FromHours(hours);

            if (secret.Length < 32)
                throw new InvalidOperationException("Jwt:Secret phải có ít nhất 32 ký tự để đảm bảo bảo mật HMAC-SHA256.");

            _signingKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(secret));

            _validationParams = new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,
                IssuerSigningKey         = _signingKey,
                ValidateIssuer           = true,
                ValidIssuer              = _issuer,
                ValidateAudience         = true,
                ValidAudience            = _audience,
                ValidateLifetime         = true,
                ClockSkew                = TimeSpan.FromSeconds(30),
            };

            _logger.LogInformation("[JwtTokenStore] Khởi tạo — issuer={Issuer} lifetime={Hours}h blacklist strategy",
                _issuer, hours);
        }

        // ─── Tạo JWT mới, thu hồi token cũ của user (rolling) ──────────────────
        public Task<string> CreateTokenAsync(string username)
        {
            // Thu hồi token cũ → thêm vào blacklist
            if (_userCurrentJti.TryRemove(username, out var oldJti))
            {
                _revokedJtis[oldJti] = DateTimeOffset.UtcNow.Add(_lifetime);
                _logger.LogDebug("[JwtTokenStore] Thu hồi token cũ jti={Jti} của '{Username}'", oldJti, username);
            }

            var jti = Guid.NewGuid().ToString("N");
            var now = DateTime.UtcNow;

            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, username),
                new Claim(JwtRegisteredClaimNames.Jti, jti),
                new Claim(JwtRegisteredClaimNames.Iat,
                          new DateTimeOffset(now).ToUnixTimeSeconds().ToString(),
                          ClaimValueTypes.Integer64),
            };

            var descriptor = new SecurityTokenDescriptor
            {
                Subject           = new ClaimsIdentity(claims),
                Expires           = now.Add(_lifetime),
                Issuer            = _issuer,
                Audience          = _audience,
                SigningCredentials = new SigningCredentials(_signingKey, SecurityAlgorithms.HmacSha256),
            };

            var tokenStr = _handler.WriteToken(_handler.CreateJwtSecurityToken(descriptor));

            // Ghi nhớ jti hiện tại của user để rolling
            _userCurrentJti[username] = jti;

            // Dọn blacklist cũ
            CleanupExpiredRevocations();

            _logger.LogDebug("[JwtTokenStore] Token mới jti={Jti} cho '{Username}'", jti, username);
            return Task.FromResult(tokenStr);
        }

        // ─── Xác thực JWT: chữ ký + hết hạn + không bị thu hồi ────────────────
        public Task<string?> ValidateTokenAsync(string tokenStr)
        {
            try
            {
                var principal = _handler.ValidateToken(tokenStr, _validationParams, out _);

                var username = principal.FindFirst(JwtRegisteredClaimNames.Sub)?.Value;
                var jti      = principal.FindFirst(JwtRegisteredClaimNames.Jti)?.Value;

                if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(jti))
                    return Task.FromResult<string?>(null);

                // Từ chối nếu JTI nằm trong blacklist (đã bị thu hồi tường minh)
                if (_revokedJtis.ContainsKey(jti))
                {
                    _logger.LogWarning("[JwtTokenStore] Từ chối token đã bị thu hồi: jti={Jti}", jti);
                    return Task.FromResult<string?>(null);
                }

                // Hợp lệ → trả về username
                return Task.FromResult<string?>(username);
            }
            catch (SecurityTokenExpiredException)
            {
                _logger.LogDebug("[JwtTokenStore] Token hết hạn");
                return Task.FromResult<string?>(null);
            }
            catch (SecurityTokenException ex)
            {
                _logger.LogWarning("[JwtTokenStore] Token không hợp lệ: {Msg}", ex.Message);
                return Task.FromResult<string?>(null);
            }
        }

        // ─── Thu hồi token (đăng xuất tức thì) ────────────────────────────────
        public Task RevokeTokenAsync(string tokenStr)
        {
            try
            {
                var jwt = _handler.ReadJwtToken(tokenStr);
                if (!string.IsNullOrEmpty(jwt.Id))
                {
                    _revokedJtis[jwt.Id] = jwt.ValidTo;
                    _userCurrentJti.TryRemove(jwt.Subject ?? "", out _);
                    _logger.LogDebug("[JwtTokenStore] Revoke jti={Jti} user='{Sub}'", jwt.Id, jwt.Subject);
                }
            }
            catch (Exception ex)
            {
                _logger.LogWarning("[JwtTokenStore] RevokeTokenAsync lỗi: {Msg}", ex.Message);
            }
            return Task.CompletedTask;
        }

        // ─── Dọn blacklist: xoá entry đã hết hạn (không cần lock — ConcurrentDict)
        private void CleanupExpiredRevocations()
        {
            var now = DateTimeOffset.UtcNow;
            foreach (var kv in _revokedJtis)
                if (kv.Value < now)
                    _revokedJtis.TryRemove(kv.Key, out _);
        }
    }
}
