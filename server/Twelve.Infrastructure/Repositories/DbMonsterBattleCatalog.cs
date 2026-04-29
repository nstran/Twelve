using System;
using System.Collections.Generic;
using System.Text.Json;
using Dapper;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Infrastructure.Data;

namespace Twelve.Infrastructure.Repositories
{
    public sealed class DbMonsterBattleCatalog : IMonsterBattleCatalog
    {
        private readonly Dictionary<long, MonsterBattleTemplate> _cache = new();

        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true,
        };

        public DbMonsterBattleCatalog(IDbConnectionFactory connectionFactory)
        {
            using var conn = connectionFactory.CreateConnection();
            var rows = conn.Query<BattleRow>(
                @"SELECT Id, Element, Level,
                         MaxHp, MaxMp, MaxPower,
                         Strength, Agility, Magic, Vitality,
                         MinDamage, MaxDamage, Defense,
                         HitRate, DodgeRate, CriticalRate,
                         Skills, Appearance, AiProfileId,
                         ExpReward, GoldReward, QuanReward
                  FROM MonsterBattles");

            foreach (var row in rows)
            {
                var skills = ParseSkills(row.Skills);
                var appearance = ParseAppearance(row.Appearance);

                _cache[row.Id] = new MonsterBattleTemplate(
                    Id: row.Id,
                    Element: (byte)(row.Element ?? 0),
                    Level: row.Level ?? 1,
                    MaxHp: row.MaxHp ?? 100,
                    MaxMp: row.MaxMp ?? 50,
                    MaxPower: row.MaxPower ?? 100,
                    Strength: row.Strength ?? 10,
                    Agility: row.Agility ?? 10,
                    Magic: row.Magic ?? 10,
                    Vitality: row.Vitality ?? 10,
                    MinDamage: row.MinDamage ?? 1,
                    MaxDamage: row.MaxDamage ?? 5,
                    Defense: row.Defense ?? 0,
                    HitRate: row.HitRate ?? 70,
                    DodgeRate: row.DodgeRate ?? 5,
                    CriticalRate: row.CriticalRate ?? 5,
                    Skills: skills,
                    Appearance: appearance,
                    AiProfileId: row.AiProfileId,
                    ExpReward: row.ExpReward ?? 0,
                    GoldReward: row.GoldReward ?? 0,
                    QuanReward: row.QuanReward ?? 0);
            }
        }

        public MonsterBattleTemplate? GetById(long id) =>
            _cache.TryGetValue(id, out var template) ? template : null;

        private static IReadOnlyList<MonsterSkillTemplate> ParseSkills(string? json)
        {
            if (string.IsNullOrWhiteSpace(json))
                return Array.Empty<MonsterSkillTemplate>();

            try
            {
                var dtos = JsonSerializer.Deserialize<SkillDto[]>(json, JsonOptions);
                if (dtos is null || dtos.Length == 0)
                    return Array.Empty<MonsterSkillTemplate>();

                var result = new MonsterSkillTemplate[dtos.Length];
                for (var i = 0; i < dtos.Length; i++)
                {
                    result[i] = new MonsterSkillTemplate(
                        SkillId: dtos[i].SkillId,
                        Level: dtos[i].Level,
                        ManaCost: dtos[i].ManaCost);
                }
                return result;
            }
            catch
            {
                return Array.Empty<MonsterSkillTemplate>();
            }
        }

        private static MonsterAppearanceTemplate ParseAppearance(string? json)
        {
            if (string.IsNullOrWhiteSpace(json))
                return new MonsterAppearanceTemplate();

            try
            {
                var dto = JsonSerializer.Deserialize<AppearanceDto>(json, JsonOptions);
                return new MonsterAppearanceTemplate(
                    AssetCatalogId: dto?.AssetCatalogId,
                    BaseBodyId: dto?.BaseBodyId,
                    WeaponBodyId: dto?.WeaponBodyId,
                    HairBodyId: dto?.HairBodyId);
            }
            catch
            {
                return new MonsterAppearanceTemplate();
            }
        }

        private sealed class BattleRow
        {
            public long Id { get; set; }
            public int? Element { get; set; }
            public int? Level { get; set; }
            public int? MaxHp { get; set; }
            public int? MaxMp { get; set; }
            public int? MaxPower { get; set; }
            public int? Strength { get; set; }
            public int? Agility { get; set; }
            public int? Magic { get; set; }
            public int? Vitality { get; set; }
            public int? MinDamage { get; set; }
            public int? MaxDamage { get; set; }
            public int? Defense { get; set; }
            public int? HitRate { get; set; }
            public int? DodgeRate { get; set; }
            public int? CriticalRate { get; set; }
            public string? Skills { get; set; }
            public string? Appearance { get; set; }
            public string? AiProfileId { get; set; }
            public int? ExpReward { get; set; }
            public int? GoldReward { get; set; }
            public int? QuanReward { get; set; }
        }

        private sealed class SkillDto
        {
            public int SkillId { get; set; }
            public int Level { get; set; }
            public int ManaCost { get; set; }
        }

        private sealed class AppearanceDto
        {
            public string? AssetCatalogId { get; set; }
            public int? BaseBodyId { get; set; }
            public int? WeaponBodyId { get; set; }
            public int? HairBodyId { get; set; }
        }
    }
}
