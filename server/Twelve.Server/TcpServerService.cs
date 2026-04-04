using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Twelve.Application;
using Twelve.Core;
using Twelve.Core.Tlv;

namespace Twelve.Server
{
    public class TcpServerService : BackgroundService
    {
        private readonly ILogger<TcpServerService> _logger;
        private readonly IServiceProvider _serviceProvider;
        private readonly int _port = 2026;
        private TcpListener? _listener;

        public TcpServerService(ILogger<TcpServerService> logger, IServiceProvider serviceProvider)
        {
            _logger = logger;
            _serviceProvider = serviceProvider;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            _listener = new TcpListener(IPAddress.Any, _port);
            _listener.Start();
            _logger.LogInformation("Twelve TCP Server started on port {Port}", _port);

            while (!stoppingToken.IsCancellationRequested)
            {
                try
                {
                    var socket = await _listener.AcceptSocketAsync(stoppingToken);
                    _ = HandleClientAsync(socket, stoppingToken);
                }
                catch (Exception ex) when (!stoppingToken.IsCancellationRequested)
                {
                    _logger.LogError(ex, "Error accepting TCP client");
                }
            }

            _listener.Stop();
        }

        private async Task HandleClientAsync(Socket socket, CancellationToken stoppingToken)
        {
            using var scope = _serviceProvider.CreateScope();
            var dispatcher = scope.ServiceProvider.GetRequiredService<PacketDispatcher>();
            
            using var channel = new TcpCommunicationChannel(socket);
            using var session = new GameSession(channel);
            
            _logger.LogInformation("New J2ME client connected: {RemoteEndPoint}", socket.RemoteEndPoint);

            var buffer = new byte[8192];
            using var networkStream = new NetworkStream(socket);

            try
            {
                while (!stoppingToken.IsCancellationRequested && socket.Connected)
                {
                    // Read header: 2 (sub) + 4 (len) + 1 (cmd) = 7 bytes
                    byte[] header = new byte[7];
                    int read = await networkStream.ReadAsync(header.AsMemory(0, 7), stoppingToken);
                    if (read == 0) break;

                    var request = TlvCodec.Decode(header, networkStream);
                    if (request != null)
                    {
                        await dispatcher.DispatchAsync(session, request);
                    }
                }
            }
            catch (Exception ex)
            {
                _logger.LogWarning("J2ME client disconnected: {Message}", ex.Message);
            }
            finally
            {
                _logger.LogInformation("J2ME session ended: {Username}", session.Username);
            }
        }
    }
}
