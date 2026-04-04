using System;
using System.IO;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace Twelve.Core
{
    public class TcpCommunicationChannel : ICommunicationChannel
    {
        private readonly NetworkStream _stream;
        private readonly Socket _socket;

        public TcpCommunicationChannel(Socket socket)
        {
            _socket = socket;
            _stream = new NetworkStream(socket);
        }

        public async Task SendAsync(byte[] data)
        {
            if (_stream.CanWrite)
            {
                await _stream.WriteAsync(data.AsMemory(0, data.Length));
                await _stream.FlushAsync();
            }
        }

        public void Close()
        {
            _stream?.Close();
            _socket?.Close();
        }

        public void Dispose()
        {
            Close();
            _stream?.Dispose();
        }
    }
}
