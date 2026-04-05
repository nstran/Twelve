using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Handlers
{
    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 1 — Đăng ký tài khoản
    //  Tags nhận vào:
    //    9  = username      (string)
    //   10  = password      (string)
    //   11  = fullName      (string)
    //   12  = dateOfBirth   (string, DD-MM-YYYY)
    //   13  = phone         (string)
    //   14  = gender        (byte, 0=Nam 1=Nữ)
    //  Response: CMD 131, tag 1 = message
    // ═══════════════════════════════════════════════════════════════════════════
    public class RegisterHandler : IPacketHandler
    {
        private readonly IAccountRepository      _accountRepository;
        private readonly IPasswordHasher         _passwordHasher;
        private readonly IPlayerRepository       _playerRepository;
        private readonly ILogger<RegisterHandler> _logger;

        public RegisterHandler(
            IAccountRepository      accountRepository,
            IPasswordHasher         passwordHasher,
            IPlayerRepository       playerRepository,
            ILogger<RegisterHandler> logger)
        {
            _accountRepository = accountRepository;
            _passwordHasher    = passwordHasher;
            _playerRepository  = playerRepository;
            _logger            = logger;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            _logger.LogInformation("[Register] ── HandleAsync fired ──");

            // ── Đọc các trường bắt buộc ──────────────────────────────────────
            string username = (request.GetStringTag(9)  ?? "").Trim();
            string password =  request.GetStringTag(10) ?? "";
            string fullName = (request.GetStringTag(11) ?? "").Trim();
            string dob      = (request.GetStringTag(12) ?? "").Trim();
            string phone    = (request.GetStringTag(13) ?? "").Trim();
            byte   genderB  = request.GetByteTag(14);

            _logger.LogInformation("[Register] username={Username} fullName={FullName} dob={Dob} phone={Phone} gender={Gender}",
                username, fullName, dob, phone, genderB);
            _logger.LogInformation("[Register] password length={Len}", password.Length);

            // ── Validate ─────────────────────────────────────────────────────
            if (string.IsNullOrEmpty(username))
            {
                _logger.LogWarning("[Register] FAIL: username empty");
                await SendError(session, "Ten dang nhap khong duoc de trong.");
                return;
            }
            if (username.Length < 4 || username.Length > 20)
            {
                _logger.LogWarning("[Register] FAIL: username length={Len}", username.Length);
                await SendError(session, "Ten dang nhap phai tu 4-20 ky tu.");
                return;
            }
            if (string.IsNullOrEmpty(password) || password.Length < 6)
            {
                _logger.LogWarning("[Register] FAIL: password too short");
                await SendError(session, "Mat khau phai co it nhat 6 ky tu.");
                return;
            }

            // ── Kiểm tra trùng tên ────────────────────────────────────────────
            var existing = await _accountRepository.GetByUsernameAsync(username);
            if (existing != null)
            {
                _logger.LogWarning("[Register] FAIL: username '{Username}' already exists", username);
                await SendError(session, "Ten tai khoan da ton tai.");
                return;
            }

            // ── Tạo Account ───────────────────────────────────────────────────
            string hash = _passwordHasher.HashPassword(password, out string salt);
            var account = new Account
            {
                Username     = username,
                PasswordHash = hash,
                Salt         = salt,
                FullName     = fullName,
                DateOfBirth  = dob,
                Phone        = phone,
                Gender       = genderB,
            };
            int accountId = await _accountRepository.CreateAsync(account);
            _logger.LogInformation("[Register] Account created, Id={Id}", accountId);

            // ── Auto-tạo Player (nhân vật mặc định) ──────────────────────────
            var player = new Player
            {
                Username    = username,
                Level       = 1,
                Gold        = 0,
                Exp         = 0,
                CurrentMap  = "M99",
                CurrentRoom = 1,
                Hp          = 100,
                MaxHp       = 100,
                Mp          = 50,
                MaxMp       = 50,
            };
            await _playerRepository.CreateAsync(player);
            _logger.LogInformation("[Register] Player created for '{Username}'", username);

            // ── Phản hồi thành công ───────────────────────────────────────────
            _logger.LogInformation("[Register] → Sending CMD 131 success response");
            var tags = ConcatBytes(
                TlvCodec.MakeTag(1, "Dang ky thanh cong!")
            );
            await session.SendPacketAsync(TlvCodec.BuildPacket(131, tags, subCount: 1));
        }

        // ── Helpers ───────────────────────────────────────────────────────────
        private static async Task SendError(GameSession session, string message)
        {
            var response = TlvCodec.MakeTag(1, message);
            await session.SendPacketAsync(TlvCodec.BuildPacket(131, response, subCount: 1));
        }

        private static byte[] ConcatBytes(params byte[][] arrays)
        {
            int total = 0;
            foreach (var a in arrays) total += a.Length;
            var result = new byte[total];
            int pos = 0;
            foreach (var a in arrays) { a.CopyTo(result, pos); pos += a.Length; }
            return result;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  CMD 2 — Đăng nhập
    //  Tags nhận vào: 9=username, 10=password
    //  Response: CMD 4 (success) hoặc CMD 0 + tag 1 message (fail)
    // ═══════════════════════════════════════════════════════════════════════════
    public class AuthHandler : IPacketHandler
    {
        private readonly IAccountRepository _accountRepository;
        private readonly IPasswordHasher    _passwordHasher;
        private readonly IPlayerRepository  _playerRepository;

        public AuthHandler(
            IAccountRepository accountRepository,
            IPasswordHasher    passwordHasher,
            IPlayerRepository  playerRepository)
        {
            _accountRepository = accountRepository;
            _passwordHasher    = passwordHasher;
            _playerRepository  = playerRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            string username = request.GetStringTag(9)  ?? "";
            string password = request.GetStringTag(10) ?? "";

            var account = await _accountRepository.GetByUsernameAsync(username);
            if (account == null || !_passwordHasher.VerifyPassword(password, account.PasswordHash, account.Salt))
            {
                var error = TlvCodec.MakeTag(1, "Dang nhap that bai.");
                await session.SendPacketAsync(TlvCodec.BuildPacket(0, error, subCount: 1));
                return;
            }

            session.Username        = username;
            session.IsAuthenticated = true;
            await _accountRepository.UpdateLastLoginAsync(account.Id);

            // CMD 4 — Login success (empty payload)
            await session.SendPacketAsync(TlvCodec.BuildEmptyPacket(4));
        }
    }
}
