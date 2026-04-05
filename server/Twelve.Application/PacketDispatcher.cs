using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core;
using Twelve.Core.Tlv;

namespace Twelve.Application
{
    public interface IPacketHandler
    {
        Task HandleAsync(GameSession session, PacketRequest request);
    }

    public class PacketDispatcher
    {
        private readonly Dictionary<int, IPacketHandler> _handlers = new();

        public void RegisterHandler(int command, IPacketHandler handler)
        {
            _handlers[command] = handler;
        }

        public async Task DispatchAsync(GameSession session, PacketRequest request)
        {
            Console.WriteLine($"[Dispatcher] ← CMD {request.Command}, payload={request.PayloadLength} bytes");

            if (!_handlers.TryGetValue(request.Command, out var handler))
            {
                Console.WriteLine($"[Dispatcher] ✗ No handler registered for CMD {request.Command}");
                return;
            }

            Console.WriteLine($"[Dispatcher] → Routing CMD {request.Command} to {handler.GetType().Name}");

            try
            {
                await handler.HandleAsync(session, request);
                Console.WriteLine($"[Dispatcher] ✓ CMD {request.Command} handled OK");
            }
            catch (Exception ex)
            {
                // Bắt toàn bộ exception từ handler — KHÔNG để exception đóng WebSocket
                Console.WriteLine($"[Dispatcher] ✗ Exception in CMD {request.Command} handler: {ex}");

                // Gửi thông báo lỗi chung về cho client (CMD 0 = error)
                try
                {
                    var errPayload = TlvCodec.MakeTag(1, $"Loi server khi xu ly lenh {request.Command}. Chi tiet: {ex.Message}");
                    await session.SendPacketAsync(TlvCodec.BuildPacket(0, errPayload, subCount: 1));
                }
                catch (Exception sendEx)
                {
                    Console.WriteLine($"[Dispatcher] ✗ Could not send error to client: {sendEx.Message}");
                }
            }
        }
    }
}
