namespace Twelve.Core.GameLogic
{
    public class MapLogic
    {
        public const int TileSize = 32;
        public const int Width = 10;
        public const int Height = 8;

        // Simple collision map based on legacy Hoa Lu spec
        // Rows 5, 6, 7 are walkable
        public static bool IsWalkable(int x, int y)
        {
            int tileX = x / TileSize;
            int tileY = y / TileSize;

            if (tileX < 0 || tileX >= Width || tileY < 0 || tileY >= Height)
                return false;

            // In legacy, rows 5, 6, 7 (index 5, 6, 7) are walkable
            return tileY >= 5 && tileY <= 7;
        }

        public static (int clampedX, int clampedY) ClampToWalkable(int x, int y)
        {
            int tileX = System.Math.Clamp(x / TileSize, 0, Width - 1);
            int tileY = System.Math.Clamp(y / TileSize, 5, 7); // Force to walkable rows

            return (tileX * TileSize + TileSize / 2, tileY * TileSize + TileSize / 2);
        }
    }
}
