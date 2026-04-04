using System;
using System.Collections.Generic;

namespace Twelve.Core.Maps
{
    public class MapRoom
    {
        public int Id { get; set; }
        public string Label { get; set; } = "Khu 1";
        public int Width { get; set; } = 10;
        public int Height { get; set; } = 8;
        public int TileSize { get; set; } = 32;
        public int CenterX { get; set; } = 124;
        public int CenterY { get; set; } = 132;
        public int EntryWidth { get; set; } = 180;
        public int EntryHeight { get; set; } = 28;
        public byte[] LogicLayer { get; set; } = Array.Empty<byte>();
        public int TilesetId { get; set; } = 5120; // Default from legacy
        public int BackgroundId { get; set; } = 1001; // Example ID
    }

    public static class MapDataStore
    {
        public static Dictionary<string, List<MapRoom>> Maps { get; } = new();

        static MapDataStore()
        {
            // Initialize Hoa Lu
            var hoaLu = new List<MapRoom>
            {
                new MapRoom
                {
                    Id = 1,
                    Label = "Hoa Lư - Khu 1",
                    Width = 10,
                    Height = 8,
                    LogicLayer = BuildHoaLuLogicLayer()
                }
            };
            Maps["Hoa Lu"] = hoaLu;
            Maps["M99"] = new List<MapRoom>(); // World Map Placeholder
        }

        private static byte[] BuildHoaLuLogicLayer()
        {
            // 10x8 = 80 cells
            byte[] layer = new byte[80];
            // Rows 5, 6, 7 are walkable (value 32)
            for (int r = 5; r <= 7; r++)
            {
                for (int c = 0; c <= 9; c++)
                {
                    layer[r * 10 + c] = 32;
                }
            }
            // Spawn points (value 2)
            layer[5 * 10 + 3] = 2;
            layer[5 * 10 + 4] = 2;
            layer[5 * 10 + 5] = 2;
            layer[5 * 10 + 6] = 2;

            return layer;
        }
    }
}
