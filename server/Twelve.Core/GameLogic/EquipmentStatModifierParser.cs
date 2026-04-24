using System;
using System.Text.Json;

namespace Twelve.Core.GameLogic
{
    public static class EquipmentStatModifierParser
    {
        public static PlayerStatModifier Parse(string? rawJson)
        {
            if (string.IsNullOrWhiteSpace(rawJson) || rawJson == "{}")
            {
                return new PlayerStatModifier();
            }

            try
            {
                using var document = JsonDocument.Parse(rawJson);
                var root = document.RootElement;
                var modifier = ResolveModifierElement(root);

                return new PlayerStatModifier(
                    CuongLuc: ReadInt(modifier, "cuongLuc", "strength", "a"),
                    ThanPhap: ReadInt(modifier, "thanPhap", "agility", "b"),
                    NoiLuc: ReadInt(modifier, "noiLuc", "magic", "c"),
                    TheLuc: ReadInt(modifier, "theLuc", "vitality", "d"),
                    FlatAttack: ReadInt(modifier, "flatAttack", "attack", "e"),
                    AttackPercent: ReadInt(modifier, "attackPercent", "attackPct", "n"),
                    Crit: ReadInt(modifier, "crit", "critical", "g"),
                    Defense: ReadInt(modifier, "defense", "pThu", "f"),
                    Dodge: ReadInt(modifier, "dodge", "neTranh", "h"),
                    MaxHp: ReadInt(modifier, "maxHp", "hp", "i"));
            }
            catch (JsonException)
            {
                return new PlayerStatModifier();
            }
        }

        private static JsonElement ResolveModifierElement(JsonElement root)
        {
            foreach (var propertyName in new[] { "modifier", "modifiers", "stats", "r" })
            {
                if (root.ValueKind == JsonValueKind.Object &&
                    root.TryGetProperty(propertyName, out var nested) &&
                    nested.ValueKind == JsonValueKind.Object)
                {
                    return nested;
                }
            }

            return root;
        }

        private static int ReadInt(JsonElement element, params string[] propertyNames)
        {
            if (element.ValueKind != JsonValueKind.Object)
            {
                return 0;
            }

            foreach (var propertyName in propertyNames)
            {
                if (!element.TryGetProperty(propertyName, out var property))
                {
                    continue;
                }

                if (property.ValueKind == JsonValueKind.Number && property.TryGetInt32(out var value))
                {
                    return value;
                }

                if (property.ValueKind == JsonValueKind.String &&
                    int.TryParse(property.GetString(), out var stringValue))
                {
                    return stringValue;
                }
            }

            return 0;
        }
    }
}

