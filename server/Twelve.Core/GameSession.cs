using System;
using System.IO;
using System.Threading.Tasks;

namespace Twelve.Core
{
    public class GameSession : IDisposable
    {
        public string? Username { get; set; } = "Guest";
        public bool IsAuthenticated { get; set; } = false;
        
        private readonly ICommunicationChannel _channel;
        private bool _isDisposed = false;

        public GameSession(ICommunicationChannel channel)
        {
            _channel = channel;
        }

        public async Task SendPacketAsync(byte[] packet)
        {
            if (_isDisposed) return;
            await _channel.SendAsync(packet);
        }

        public void Dispose()
        {
            if (!_isDisposed)
            {
                _channel?.Dispose();
                _isDisposed = true;
            }
        }
    }
}
