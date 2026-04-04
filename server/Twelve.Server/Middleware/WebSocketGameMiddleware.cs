using System;
using System.Net.WebSockets;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Twelve.Application;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Server.WebSockets;

namespace Twelve.Server.Middleware
{
    public class WebSocketGameMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<WebSocketGameMiddleware> _logger;

        public WebSocketGameMiddleware(RequestDelegate next, ILogger<WebSocketGameMiddleware> logger)
        {
            _next = next;
            _logger = logger;
        }

        public async Task InvokeAsync(HttpContext context, IServiceProvider serviceProvider)
        {
            if (context.Request.Path == "/game")
            {
                if (context.WebSockets.IsWebSocketRequest)
                {
                    using var webSocket = await context.WebSockets.AcceptWebSocketAsync();
                    await HandleWebSocketAsync(webSocket, serviceProvider);
                }
                else
                {
                    context.Response.StatusCode = StatusCodes.Status400BadRequest;
                }
            }
            else
            {
                await _next(context);
            }
        }

        private async Task HandleWebSocketAsync(WebSocket webSocket, IServiceProvider serviceProvider)
        {
            using var scope = serviceProvider.CreateScope();
            var dispatcher = scope.ServiceProvider.GetRequiredService<PacketDispatcher>();
            
            using var channel = new WebSocketCommunicationChannel(webSocket);
            using var session = new GameSession(channel);

            _logger.LogInformation("New WebSocket client connected!");

            var buffer = new byte[8192];
            try
            {
                while (webSocket.State == WebSocketState.Open)
                {
                    // WebSocket is slightly different from Stream: we receive a full message or fragments
                    var result = await webSocket.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                    
                    if (result.MessageType == WebSocketMessageType.Close)
                    {
                        await webSocket.CloseAsync(WebSocketCloseStatus.NormalClosure, "Closing", CancellationToken.None);
                    }
                    else if (result.MessageType == WebSocketMessageType.Binary)
                    {
                        // Decode TLV from the received buffer
                        // Note: Our TlvCodec.Decode currently expects a Stream for the payload.
                        // Let's adapt it or use a MemoryStream.
                        using var ms = new System.IO.MemoryStream(buffer, 0, result.Count);
                        
                        // Header is the first 7 bytes
                        byte[] header = new byte[7];
                        ms.Read(header, 0, 7);
                        
                        var request = TlvCodec.Decode(header, ms);
                        if (request != null)
                        {
                            await dispatcher.DispatchAsync(session, request);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                _logger.LogWarning("WebSocket disconnected: {Message}", ex.Message);
            }
            finally
            {
                 _logger.LogInformation("WebSocket session ended: {Username}", session.Username);
            }
        }
    }
}
