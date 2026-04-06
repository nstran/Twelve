using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Handlers
{
    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 3 — Đăng nhập bằng token (auto-login khi mở lại app)
    //  Tag nhận vào:
    //    2 = token (string, UUID 32 ký tự)
    //  Response:
    //    CMD 4 = thành công, kèm token mới + expiresAt
    //    CMD 0 = thất bại (token hết hạn / không hợp lệ)
    // ═══════════════════════════════════════════════════════════════════════════
    public class TokenAuthHandler : IPacketHandler
    {
        private readonly ISessionTokenStore      _tokenStore;
        private readonly AuthHandler             _authHandler;   // tái dùng SendLoginSuccessAsync
        private readonly ILogger<TokenAuthHandler> _logger;

        public TokenAuthHandler(
            ISessionTokenStore       tokenStore,
            AuthHandler              authHandler,
            ILogger<TokenAuthHandler> logger)
        {
            _tokenStore  = tokenStore;
            _authHandler = authHandler;
            _logger      = logger;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            string token = (request.GetStringTag((int)TagCode.Token) ?? "").Trim();

            _logger.LogInformation("[TokenLogin] token={Token}", token.Length > 8 ? token[..8] + "…" : token);

            if (string.IsNullOrEmpty(token))
            {
                _logger.LogWarning("[TokenLogin] FAIL: token rỗng");
                await SendFail(session, "Token khong hop le.");
                return;
            }

            string? username = await _tokenStore.ValidateTokenAsync(token);
            if (username == null)
            {
                _logger.LogWarning("[TokenLogin] FAIL: token hết hạn hoặc không tồn tại");
                await SendFail(session, "Phien dang nhap het han. Vui long dang nhap lai.");
                return;
            }

            // Token hợp lệ — set session và tiếp tục như login thường
            session.Username        = username;
            session.IsAuthenticated = true;

            _logger.LogInformation("[TokenLogin] ✓ token hợp lệ cho '{Username}'", username);

            // Xoá token cũ, cấp token mới (rolling token)
            await _tokenStore.RevokeTokenAsync(token);
            await _authHandler.SendLoginSuccessAsync(session, username);
        }

        private static async Task SendFail(GameSession session, string message)
        {
            var err = TlvCodec.MakeTag((int)TagCode.Message, message);
            await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.LoginFailed, err, subCount: 1));
        }
    }
}
