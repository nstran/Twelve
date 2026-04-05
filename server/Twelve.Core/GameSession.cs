using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;

namespace Twelve.Core
{
    public class GameSession : IDisposable
    {
        public string? Username { get; set; } = "Guest";
        public bool IsAuthenticated { get; set; } = false;

        private readonly ICommunicationChannel _channel;
        private readonly SemaphoreSlim _sendLock = new SemaphoreSlim(1, 1);
        private bool _isDisposed = false;

        public GameSession(ICommunicationChannel channel)
        {
            _channel = channel;
        }

        public async Task SendPacketAsync(byte[] packet)
        {
            if (_isDisposed) return;
            await _sendLock.WaitAsync();
            try
            {
                await _channel.SendAsync(packet);
            }
            finally
            {
                _sendLock.Release();
            }
        }

        public void Dispose()
        {
            if (!_isDisposed)
            {
                _isDisposed = true;
                _sendLock.Dispose();
                _channel?.Dispose();
            }
        }
    }
}
