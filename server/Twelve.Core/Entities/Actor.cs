namespace Twelve.Core.Entities
{
    public class Actor
    {
        public string Id { get; set; } = string.Empty;
        public string Label { get; set; } = string.Empty;
        public int Kind { get; set; } // Sprite ID
        public byte Variant { get; set; }
        public int X { get; set; }
        public int Y { get; set; }
        public int Power { get; set; }
        public int Copies { get; set; }
        public byte Palette { get; set; }

        public bool IsPlayer { get; set; }
    }
}
