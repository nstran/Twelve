using System.Threading.Tasks;
using System.Collections.Generic;
using Twelve.Core;
using Twelve.Core.Tlv;
using Twelve.Core.Interfaces;
using Twelve.Core.Maps;

namespace Twelve.Application.Handlers
{
    public class MoveHandler : IPacketHandler
    {
        private const int TagMapId = 20;
        private const int TagRoomId = 30;
        private const int TagPlayerX = 102;
        private const int TagPlayerY = 103;
        private const int TagPlayerDirection = 104;
        private const int TagPlayerActionState = 105;
        private const int MaxActionState = 16;

        private readonly IPlayerAggregateRepository _playerAggregateRepository;

        public MoveHandler(IPlayerAggregateRepository playerAggregateRepository)
        {
            _playerAggregateRepository = playerAggregateRepository;
        }

        public async Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (request.Command == 44) // Move Request
            {
                if (!session.IsAuthenticated || string.IsNullOrWhiteSpace(session.Username))
                {
                    return;
                }

                var aggregate = await _playerAggregateRepository.GetByUsernameAsync(session.Username);
                var resolvedMapId = request.GetStringTag(TagMapId);
                var resolvedRoomId = request.GetIntTag(TagRoomId);
                resolvedMapId ??= aggregate?.WorldState.MapId ?? RuntimeMapCatalog.DefaultMapId;
                resolvedRoomId = System.Math.Max(1, resolvedRoomId ?? aggregate?.WorldState.RoomId ?? 1);

                var room = RuntimeMapCatalog.ResolveRoom(resolvedMapId, resolvedRoomId.Value);
                var targetPosition = room.ClampPosition(
                    request.GetIntTag(TagPlayerX) ?? room.SpawnX,
                    request.GetIntTag(TagPlayerY) ?? room.SpawnY);
                int targetX = targetPosition.X;
                int targetY = targetPosition.Y;
                int direction = NormalizeDirection(request.GetIntTag(TagPlayerDirection) ?? 0);
                int actionState = NormalizeActionState(request.GetIntTag(TagPlayerActionState) ?? 0);

                // 1. Update In-Session state (In-memory)
                System.Console.WriteLine($"[MoveHandler] User {session.Username} moving to {room.MapId}/{room.RoomId} ({targetX}, {targetY})");

                // 2. Persist to PostgreSQL (Twelve)
                if (aggregate != null)
                {
                    await _playerAggregateRepository.UpsertWorldStateAsync(
                        aggregate.Core.Id,
                        room.MapId,
                        room.RoomId,
                        targetX,
                        targetY,
                        direction,
                        actionState);
                }

                // 3. Echo canonical move state back to the user.
                var (echoPayload, tagCount) = BuildMoveAckPayload(
                    room.MapId,
                    room.RoomId,
                    targetX,
                    targetY,
                    direction,
                    actionState);
                byte[] echo = TlvCodec.BuildPacket(44, echoPayload, tagCount);
                await session.SendPacketAsync(echo);
            }
        }

        private static int NormalizeDirection(int value) =>
            value <= 0 ? 0 : 1;

        private static int NormalizeActionState(int value) =>
            System.Math.Clamp(value, 0, MaxActionState);

        private static (byte[] Payload, int TagCount) BuildMoveAckPayload(
            string? mapId,
            int? roomId,
            int x,
            int y,
            int direction,
            int actionState)
        {
            var tags = new List<byte>();
            var tagCount = 0;
            if (!string.IsNullOrWhiteSpace(mapId))
            {
                tags.AddRange(TlvCodec.MakeTag(TagMapId, mapId));
                tagCount++;
            }
            if (roomId.HasValue)
            {
                tags.AddRange(TlvCodec.MakeTag(TagRoomId, System.Math.Max(1, roomId.Value)));
                tagCount++;
            }

            tags.AddRange(TlvCodec.MakeTag(TagPlayerX, x));
            tags.AddRange(TlvCodec.MakeTag(TagPlayerY, y));
            tags.AddRange(TlvCodec.MakeTag(TagPlayerDirection, direction));
            tags.AddRange(TlvCodec.MakeTag(TagPlayerActionState, actionState));
            tagCount += 4;
            return (tags.ToArray(), tagCount);
        }
    }
}
