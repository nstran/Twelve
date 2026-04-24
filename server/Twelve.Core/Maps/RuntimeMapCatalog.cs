using System;
using System.Collections.Generic;

namespace Twelve.Core.Maps
{
    public sealed class RuntimeMapSurface
    {
        public RuntimeMapSurface(
            string id,
            int x1,
            int x2,
            int y,
            string kind = "ground",
            bool oneWay = false)
        {
            Id = id;
            X1 = Math.Min(x1, x2);
            X2 = Math.Max(x1, x2);
            Y = y;
            Kind = kind;
            OneWay = oneWay;
        }

        public string Id { get; }
        public int X1 { get; }
        public int X2 { get; }
        public int Y { get; }
        public string Kind { get; }
        public bool OneWay { get; }

        public bool ContainsX(int x) =>
            x >= X1 && x <= X2;

        public int ClampX(int x) =>
            Math.Clamp(x, X1, X2);

        public int DistanceToX(int x)
        {
            if (ContainsX(x))
            {
                return 0;
            }

            return x < X1 ? X1 - x : x - X2;
        }
    }

    public readonly record struct RuntimeMapPosition(int X, int Y);

    public sealed class RuntimeMapRoom
    {
        public RuntimeMapRoom(
            string mapId,
            int roomId,
            string label,
            int nativeWidth,
            int nativeHeight,
            int spawnX,
            int spawnY,
            int tileSize = 32,
            IReadOnlyList<RuntimeMapSurface>? surfaces = null)
        {
            MapId = mapId;
            RoomId = roomId;
            Label = label;
            NativeWidth = nativeWidth;
            NativeHeight = nativeHeight;
            SpawnX = spawnX;
            SpawnY = spawnY;
            TileSize = tileSize;
            Surfaces = surfaces ?? Array.Empty<RuntimeMapSurface>();
        }

        public string MapId { get; }
        public int RoomId { get; }
        public string Label { get; }
        public int NativeWidth { get; }
        public int NativeHeight { get; }
        public int SpawnX { get; }
        public int SpawnY { get; }
        public int TileSize { get; }
        public int MinX => 0;
        public int MaxX => NativeWidth;
        public int MinY => 0;
        public int MaxY => NativeHeight;
        public IReadOnlyList<RuntimeMapSurface> Surfaces { get; }
        public byte[] LogicLayer { get; } = Array.Empty<byte>();

        public RuntimeMapPosition ClampPosition(int x, int y)
        {
            var clampedX = Math.Clamp(x, MinX, MaxX);
            var clampedY = Math.Clamp(y, MinY, MaxY);

            if (Surfaces.Count == 0)
            {
                return new RuntimeMapPosition(clampedX, clampedY);
            }

            RuntimeMapSurface? nearestSurface = null;
            var nearestDistance = int.MaxValue;
            foreach (var surface in Surfaces)
            {
                var distance = surface.DistanceToX(clampedX);
                if (distance < nearestDistance)
                {
                    nearestSurface = surface;
                    nearestDistance = distance;
                }
            }

            return nearestSurface is null
                ? new RuntimeMapPosition(clampedX, clampedY)
                : new RuntimeMapPosition(nearestSurface.ClampX(clampedX), clampedY);
        }
    }

    public static class RuntimeMapCatalog
    {
        public const string DefaultMapId = "Hoa Lu";
        public const int DefaultRoomId = 1;

        private static readonly RuntimeMapRoom FallbackRoom = new(
            mapId: DefaultMapId,
            roomId: DefaultRoomId,
            label: "Khu 1",
            nativeWidth: 1536,
            nativeHeight: 1024,
            spawnX: 123,
            spawnY: 738,
            surfaces: BuildHoaLuSurfaces());

        private static readonly IReadOnlyList<RuntimeMapRoom> RoomDefinitions = new[]
        {
            new RuntimeMapRoom(
                mapId: "Hoa Lu",
                roomId: 1,
                label: "Khu 1",
                nativeWidth: 1536,
                nativeHeight: 1024,
                spawnX: 123,
                spawnY: 738,
                surfaces: BuildHoaLuSurfaces()),
        };

        private static readonly Dictionary<string, RuntimeMapRoom> Rooms = BuildRoomIndex(RoomDefinitions);

        public static IReadOnlyCollection<RuntimeMapRoom> AllRooms => Rooms.Values;

        public static RuntimeMapRoom ResolveRoom(string? mapId, int roomId)
        {
            var resolvedMapId = string.IsNullOrWhiteSpace(mapId) ? DefaultMapId : mapId;
            var resolvedRoomId = Math.Max(1, roomId);

            if (Rooms.TryGetValue(BuildKey(resolvedMapId, resolvedRoomId), out var room))
            {
                return room;
            }

            return new RuntimeMapRoom(
                mapId: resolvedMapId,
                roomId: resolvedRoomId,
                label: $"Khu {resolvedRoomId}",
                nativeWidth: FallbackRoom.NativeWidth,
                nativeHeight: FallbackRoom.NativeHeight,
                spawnX: FallbackRoom.SpawnX,
                spawnY: FallbackRoom.SpawnY,
                surfaces: FallbackRoom.Surfaces);
        }

        private static Dictionary<string, RuntimeMapRoom> BuildRoomIndex(IEnumerable<RuntimeMapRoom> rooms)
        {
            var index = new Dictionary<string, RuntimeMapRoom>(StringComparer.OrdinalIgnoreCase);
            foreach (var room in rooms)
            {
                var key = BuildKey(room.MapId, room.RoomId);
                if (!index.TryAdd(key, room))
                {
                    throw new InvalidOperationException($"Duplicate runtime map room definition: {key}");
                }
            }

            return index;
        }

        private static IReadOnlyList<RuntimeMapSurface> BuildHoaLuSurfaces() =>
            new[]
            {
                new RuntimeMapSurface("ground_main", 0, 1536, 738),
                new RuntimeMapSurface("island_lower_left", 6, 132, 620, "platform", oneWay: true),
                new RuntimeMapSurface("island_mid_left", 586, 760, 472, "platform", oneWay: true),
                new RuntimeMapSurface("island_mid_right", 798, 1126, 520, "platform", oneWay: true),
                new RuntimeMapSurface("island_top_left", 506, 612, 353, "platform", oneWay: true),
            };

        private static string BuildKey(string mapId, int roomId) =>
            $"{mapId.Trim()}:{roomId}";
    }
}
