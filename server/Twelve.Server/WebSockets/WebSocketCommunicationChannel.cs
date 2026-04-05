using System;
using System.Net.WebSockets;
using System.Threading;
using System.Threading.Tasks;
using Twelve.Core;

namespace Twelve.Server.WebSockets
{
    public class WebSocketCommunicationChannel : ICommunicationChannel
    {
        private readonly WebSocket _webSocket;

        public WebSocketCommunicationChannel(WebSocket webSocket)
        {
            _webSocket = webSocket;
        }

        public async Task SendAsync(byte[] data)
        {
            if (_webSocket.State == WebSocketState.Open)
            {
                await _webSocket.SendAsync(
                    new ArraySegment<byte>(data),
                    WebSocketMessageType.Binary,
                    endOfMessage: true,
                    cancellationToken: CancellationToken.None);
            }
        }

        public void Close()
        {
            if (_webSocket.State == WebSocketState.Open)
            {
                // Use a short-lived token to avoid blocking indefinitely on close
                using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(3));
                try
                {
                    _webSocket.CloseAsync(WebSocketCloseStatus.NormalClosure, "Closed by server", cts.Token)
                              .GetAwaiter().GetResult();
                }
                catch (OperationCanceledException) { /* Timeout on close is acceptable */ }
            }
        }

        public void Dispose()
        {
            Close();
            _webSocket?.Dispose();
        }
    }
}
