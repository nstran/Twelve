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

    public sealed record WorldMapEntry(
        int Index,
        string Id,
        string DisplayName,
        string RuntimeMapId,
        bool IsLocked,
        int LabelX,
        int LabelY,
        int LockX,
        int LockY,
        int HitX,
        int HitY,
        int HitWidth,
        int HitHeight);

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

        // World map selection reconstructed from Java client:
        // - og.java line 17: fixed display-name array and index order.
        // - oh.java lines 28-50: label coordinates, lock coordinates and clickable rectangles.
        // - og.f()/ks.a().b("M99", go.x): entering world-map city sends map hub id M99 + selected index.
        // Server owns the catalog/unlock flags so React Native only renders server truth.
        //
        // Manual coordinate tuning guide:
        // new WorldMapEntry(Index, Id, DisplayName, RuntimeMapId, IsLocked, LabelX, LabelY, LockX, LockY, HitX, HitY, HitWidth, HitHeight)
        // - TEXT: LabelX/LabelY. X = ngang (trái/phải), Y = dọc (lên/xuống).
        // - LOCK: LockX = LabelX + 8, LockY = LabelY - 30 để icon khóa nằm cân trên text.
        // - DO NOT TOUCH HitX/HitY/HitWidth/HitHeight unless changing the selectable/touch rectangle.
        private static readonly IReadOnlyList<WorldMapEntry> WorldMapDefinitions = new[]
        {
            new WorldMapEntry(0, "hoalu", "Hoa Lư", "Hoa Lu", false, 160, 385, 168, 355, 146, 342, 61, 48),
            new WorldMapEntry(1, "kybo", "Kỷ Bố", "Ky Bo", true, 403, 370, 410, 340, 381, 319, 62, 48),
            new WorldMapEntry(2, "binhkieu", "Bình Kiều", "Binh Kieu", true, 24, 432, 32, 402, 9, 386, 64, 48),
            new WorldMapEntry(3, "dangchau", "Đằng Châu", "Dang Chau", true, 425, 290, 433, 260, 407, 245, 65, 47),
            new WorldMapEntry(4, "dodonggiang", "Đỗ Động Giang", "Do Dong Giang", true, 256, 310, 262, 280, 236, 271, 64, 44),
            new WorldMapEntry(5, "tegiang", "Tế Giang", "Te Giang", true, 314, 270, 322, 240, 300, 227, 63, 44),
            new WorldMapEntry(6, "sieuloai", "Siêu Loại", "Sieu Loai", true, 348, 184, 354, 154, 332, 142, 62, 48),
            new WorldMapEntry(7, "tayphuliet", "Tây Phù Liệt", "Tay Phu Liet", true, 188, 216, 196, 186, 173, 169, 64, 48),
            new WorldMapEntry(8, "duonglam", "Đường Lâm", "Duong Lam", true, 82, 225, 90, 195, 68, 185, 62, 48),
            new WorldMapEntry(9, "coloa", "Cổ Loa", "Co Loa", true, 258, 169, 266, 139, 242, 128, 62, 48),
            new WorldMapEntry(10, "tiendu", "Tiên Du", "Tien Du", true, 385, 110, 393, 80, 369, 70, 64, 44),
            new WorldMapEntry(11, "tamdai", "Tam Đái", "Tam Dai", true, 238, 100, 246, 70, 229, 74, 63, 45),
            new WorldMapEntry(12, "phongchau", "Phong Châu", "Phong Chau", true, 98, 115, 106, 85, 84, 73, 63, 47),
            new WorldMapEntry(13, "hoiho", "Hồi Hồ", "Hoi Ho", true, 32, 88, 40, 58, 18, 38, 62, 48),
            new WorldMapEntry(14, "luyennguc", "Luyện Ngục", "Luyen Nguc", true, 43, 315, 51, 285, 34, 271, 52, 46),
            new WorldMapEntry(15, "thienmon", "Thiên Môn", "Thien Mon", true, 415, 165, 424, 135, 391, 131, 66, 44),
            new WorldMapEntry(16, "mauson", "Mẫu Sơn", "Mau Son", true, 175, 55, 183, 25, 157, 27, 71, 26),
        };

        private static readonly Dictionary<string, RuntimeMapRoom> Rooms = BuildRoomIndex(RoomDefinitions);

        public static IReadOnlyCollection<RuntimeMapRoom> AllRooms => Rooms.Values;

        public static IReadOnlyList<WorldMapEntry> AllWorldMaps => WorldMapDefinitions;

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
