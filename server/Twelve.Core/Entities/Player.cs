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
        
        // HP / Mana / Power
        public int Hp { get; set; } = 60;
        public int MaxHp { get; set; } = 60;
        public int Mp { get; set; } = 0;
        public int MaxMp { get; set; } = 0;
        public int Power { get; set; } = 0;
        public int MaxPower { get; set; } = 0;

        // Base stats (4 chỉ số gốc — phân điểm tiềm năng)
        // Giá trị mặc định Level 1 theo element, xem CreateCharacterHandler
        public int CuongLuc { get; set; } = 10;   // lh.h / jp.a — Strength
        public int ThanPhap { get; set; } = 10;   // lh.j / jp.b — Agility
        public int NoiLuc   { get; set; } = 10;   // lh.i / jp.c — Magic
        public int TheLuc   { get; set; } = 10;   // lh.k / jp.d — Vitality
        public int FreePoints { get; set; } = 5;  // lh.K / Tag 53 — điểm chưa phân

        // Visual & Elemental Traits
        public int Gender { get; set; } = 0;   // 0=Nam, 1=Nữ
        public int? Element { get; set; }       // 0=Hỏa, 1=Lôi, 2=Thủy
        public int? FaceStyle { get; set; }
        public int? HairStyle { get; set; }
        public int? HairColor { get; set; }
        public int? SkinColor { get; set; }

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
        public DateTime LastSeenAt { get; set; } = DateTime.UtcNow;
    }
}
