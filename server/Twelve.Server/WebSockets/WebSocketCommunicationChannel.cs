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
                    true,
                    CancellationToken.None);
            }
        }

        public void Close()
        {
            if (_webSocket.State == WebSocketState.Open)
            {
                _webSocket.CloseAsync(WebSocketCloseStatus.NormalClosure, "Closed by server", CancellationToken.None).Wait();
            }
        }

        public void Dispose()
        {
            Close();
            _webSocket?.Dispose();
        }
    }
}
