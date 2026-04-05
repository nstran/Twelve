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
            string username = (request.GetStringTag((int)TagCode.Username)    ?? "").Trim();
            string password =  request.GetStringTag((int)TagCode.Password)    ?? "";
            string fullName = (request.GetStringTag((int)TagCode.FullName)    ?? "").Trim();
            string dob      = (request.GetStringTag((int)TagCode.DateOfBirth) ?? "").Trim();
            string phone    = (request.GetStringTag((int)TagCode.Phone)       ?? "").Trim();
            byte   genderB  = request.GetByteTag((int)TagCode.Gender);

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

            // ── Phản hồi thành công ───────────────────────────────────────────
            _logger.LogInformation("[Register] → Sending Success response");
            var tags = ConcatBytes(
                TlvCodec.MakeTag((int)TagCode.Message, "Dang ky thanh cong!")
            );
            await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.RegisterResponse, tags, subCount: 1));
        }

        // ── Helpers ───────────────────────────────────────────────────────────
        private static async Task SendError(GameSession session, string message)
        {
            var response = TlvCodec.MakeTag((int)TagCode.Message, message);
            await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.RegisterResponse, response, subCount: 1));
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
        private readonly IAccountRepository   _accountRepository;
        private readonly IPasswordHasher      _passwordHasher;
        private readonly IPlayerRepository    _playerRepository;
        private readonly ILogger<AuthHandler> _logger;

        public AuthHandler(
            IAccountRepository   accountRepository,
            IPasswordHasher      passwordHasher,
            IPlayerRepository    playerRepository,
            ILogger<AuthHandler> logger)
        {
            _accountRepository = accountRepository;
            _passwordHasher    = passwordHasher;
            _playerRepository  = playerRepository;
            _logger            = logger;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            string username = (request.GetStringTag((int)TagCode.Username) ?? "").Trim();
            string password = (request.GetStringTag((int)TagCode.Password) ?? "");

            _logger.LogInformation("[Login] ── HandleAsync fired, username='{Username}'", username);

            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                _logger.LogWarning("[Login] FAIL: username hoặc password rỗng");
                var err = TlvCodec.MakeTag((int)TagCode.Message, "Vui long nhap day du thong tin.");
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.LoginFailed, err, subCount: 1));
                return;
            }

            var account = await _accountRepository.GetByUsernameAsync(username);
            if (account == null)
            {
                _logger.LogWarning("[Login] FAIL: không tìm thấy tài khoản '{Username}'", username);
                var err = TlvCodec.MakeTag((int)TagCode.Message, "Tài khoản không tồn tại.");
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.LoginFailed, err, subCount: 1));
                return;
            }

            if (!_passwordHasher.VerifyPassword(password, account.PasswordHash, account.Salt))
            {
                _logger.LogWarning("[Login] FAIL: sai mật khẩu cho '{Username}'", username);
                var err = TlvCodec.MakeTag((int)TagCode.Message, "Sai mật khẩu.");
                await session.SendPacketAsync(TlvCodec.BuildPacket(CommandCode.LoginFailed, err, subCount: 1));
                return;
            }

            // ── Đăng nhập phần Account thành công ──────────────────────────
            session.Username        = username;
            session.IsAuthenticated = true;
            await _accountRepository.UpdateLastLoginAsync(account.Id);

            // ── Kiểm tra Nhân vật (Player) ──────────────────────────────────
            var player = await _playerRepository.GetByUsernameAsync(username);
            
            // Nếu không có nhân vật HOẶC nhân vật chưa được khởi tạo đầy đủ (chưa có Hệ/Mặt/Tóc)
            bool isNewChar = (player == null) || (player.Element == 0 && player.Face == 0);

            if (isNewChar)
            {
                _logger.LogInformation("[Login] Character Missing or Incomplete → Redirecting to creation screen");
                await session.SendPacketAsync(TlvCodec.BuildEmptyPacket(CommandCode.CharacterRequired));
                return;
            }

            _logger.LogInformation("[Login] ✓ '{Username}' login success & fully initialized → Entering Game", username);
            await session.SendPacketAsync(TlvCodec.BuildEmptyPacket(CommandCode.LoginSuccess));
        }
    }
}
