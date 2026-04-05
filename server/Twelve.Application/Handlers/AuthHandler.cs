using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Handlers
{
    public class RegisterHandler : IPacketHandler
    {
        private readonly IAccountRepository _accountRepository;
        private readonly IPasswordHasher _passwordHasher;

        public RegisterHandler(IAccountRepository accountRepository, IPasswordHasher passwordHasher)
        {
            _accountRepository = accountRepository;
            _passwordHasher = passwordHasher;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            string username = request.GetStringTag(9) ?? "";
            string password = request.GetStringTag(10) ?? "";

            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                await SendError(session, "Thieu thong tin dang ky.");
                return;
            }

            var existing = await _accountRepository.GetByUsernameAsync(username);
            if (existing != null)
            {
                await SendError(session, "Ten tai khoan da ton tai.");
                return;
            }

            string hash = _passwordHasher.HashPassword(password, out string salt);
            var account = new Account
            {
                Username = username,
                PasswordHash = hash,
                Salt = salt
            };

            await _accountRepository.CreateAsync(account);
            
            // Send CMD 131 Response (Success message)
            var response = TlvCodec.MakeTag(1, "Dang ky thanh cong!");
            await session.SendPacketAsync(TlvCodec.BuildPacket(131, response));
        }

        private async Task SendError(GameSession session, string message)
        {
            var response = TlvCodec.MakeTag(1, message);
            await session.SendPacketAsync(TlvCodec.BuildPacket(131, response));
        }
    }

    public class AuthHandler : IPacketHandler
    {
        private readonly IAccountRepository _accountRepository;
        private readonly IPasswordHasher _passwordHasher;
        private readonly IPlayerRepository _playerRepository;

        public AuthHandler(IAccountRepository accountRepository, IPasswordHasher passwordHasher, IPlayerRepository playerRepository)
        {
            _accountRepository = accountRepository;
            _passwordHasher = passwordHasher;
            _playerRepository = playerRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            string username = request.GetStringTag(9) ?? "";
            string password = request.GetStringTag(10) ?? "";

            var account = await _accountRepository.GetByUsernameAsync(username);
            if (account == null || !_passwordHasher.VerifyPassword(password, account.PasswordHash, account.Salt))
            {
                var error = TlvCodec.MakeTag(1, "Dang nhap that bai.");
                await session.SendPacketAsync(TlvCodec.BuildPacket(0, error));
                return;
            }

            session.Username = username;
            session.IsAuthenticated = true;
            await _accountRepository.UpdateLastLoginAsync(account.Id);

            // Send Success (CMD 4 Empty)
            await session.SendPacketAsync(TlvCodec.BuildPacket(4, new byte[0]));
        }
    }
}
