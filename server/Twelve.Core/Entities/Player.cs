using System;

namespace Twelve.Core.Entities
{
    public class Player
    {
        public int Id { get; set; }
        public string Username { get; set; } = string.Empty;
        public int Level { get; set; } = 1;
        public long Gold { get; set; } = 0;
        public long Exp { get; set; } = 0;
        
        // Location info
        public string CurrentMap { get; set; } = "M99";
        public int CurrentRoom { get; set; } = 1;
        
        // Stats
        public int Hp { get; set; } = 100;
        public int MaxHp { get; set; } = 100;
        public int Mp { get; set; } = 50;
        public int MaxMp { get; set; } = 50;
        // Visual & Elemental Traits
        public int Element { get; set; } = 0; // 0=Kim, 1=Mộc, 2=Thủy, 3=Hỏa, 4=Thổ
        public int FaceStyle { get; set; } = 0;
        public int HairStyle { get; set; } = 0;
        public int HairColor { get; set; } = 0;
        public int SkinColor { get; set; } = 0;

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
        public DateTime LastSeenAt { get; set; } = DateTime.UtcNow;
    }
}
