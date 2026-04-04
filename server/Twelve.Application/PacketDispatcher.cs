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
            if (_handlers.TryGetValue(request.Command, out var handler))
            {
                await handler.HandleAsync(session, request);
            }
            else
            {
                // Log unhandled command
                System.Console.WriteLine($"[Dispatcher] Warning: No handler for CMD {request.Command}");
            }
        }
    }
}
