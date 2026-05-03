using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using Twelve.Core.Battle;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Core.Players;

namespace Twelve.Application.Players
{
    public sealed class PlayerContentCatalog
    {
        private readonly IReadOnlyDictionary<int, PlayerItemDefinition> _items;
        private readonly IReadOnlyDictionary<int, IReadOnlyList<PlayerSkillDefinition>> _skillsByElement;
        private readonly IEquipmentCatalogRepository _equipmentCatalogRepository;
        private readonly object _equipmentLock = new();
        private IReadOnlyDictionary<string, PlayerEquipmentDefinition>? _equipment;

        public PlayerContentCatalog(IEquipmentCatalogRepository equipmentCatalogRepository)
        {
            _equipmentCatalogRepository = equipmentCatalogRepository;
            _items = CreateItemDefinitions();
            _skillsByElement = CreateSkillDefinitions();
        }

        /// <summary>
        /// Grants a fixed starter kit only when all required rows exist in <c>EquipmentCatalog</c>.
        /// If the catalog is empty or incomplete, returns an empty list so create-character still succeeds.
        /// </summary>
        public IReadOnlyList<PlayerEquipmentEntry> CreateStarterEquipment(Player player)
        {
            var catalog = GetEquipmentDefinitions();
            var primaryKey = (player.Element ?? 0) switch
            {
                1 => "starter_zap_blade",
                2 => "starter_water_blade",
                _ => "starter_fire_blade"
            };

            if (!catalog.TryGetValue(primaryKey, out var primary)
                || !catalog.TryGetValue("fire_guard_vest", out var vest)
                || !catalog.TryGetValue("zap_hunter_helm", out var helm))
            {
                return Array.Empty<PlayerEquipmentEntry>();
            }

            var seed = $"starter-{player.Username}";
            return new[]
            {
                CreateStarterEquipmentEntry(primary, seed, isEquipped: true),
                CreateStarterEquipmentEntry(vest, seed, isEquipped: false),
                CreateStarterEquipmentEntry(helm, seed, isEquipped: false)
            };
        }

        public IReadOnlyList<PlayerItemStack> CreateStarterInventory(Player player)
        {
            var potionId = player.Level >= 10 ? 5005 : 5001;
            return new[]
            {
                BuildInventoryStack(_items[potionId], quantity: 3)
            };
        }

        public IReadOnlyList<PlayerSkillEntry> CreateStarterSkills(Player player)
        {
            var starterFamily = (player.Element ?? 0) switch
            {
                1 => 2000,
                2 => 4000,
                _ => 1000
            };

            return new[]
            {
                new PlayerSkillEntry
                {
                    SkillId = starterFamily,
                    Level = 1,
                    RawJson = JsonSerializer.Serialize(new
                    {
                        familyCode = starterFamily,
                        source = "starter"
                    })
                }
            };
        }

        public IReadOnlyList<PlayerSkillNodeView> BuildSkillViews(Player player, IReadOnlyList<PlayerSkillEntry> learnedSkills)
        {
            var learned = learnedSkills.ToDictionary(skill => skill.SkillId, skill => skill.Level);
            var definitions = GetSkillDefinitionsForElement(player.Element ?? 0);

            return definitions
                .Select(definition =>
                {
                    learned.TryGetValue(definition.FamilyCode, out var level);
                    var canUpgrade = player.SkillPoints >= definition.Cost
                        && player.Level >= definition.RequiredLevel
                        && level < definition.MaxLevel;

                    return new PlayerSkillNodeView(
                        FamilyCode: definition.FamilyCode,
                        Level: level,
                        MaxLevel: definition.MaxLevel,
                        RequiredLevel: definition.RequiredLevel,
                        Cost: definition.Cost,
                        CanUpgrade: canUpgrade);
                })
                .ToArray();
        }

        public PlayerInventoryItemView ToInventoryView(PlayerItemStack stack)
        {
            var definition = ResolveItem(stack.ItemId, stack.RawJson);
            return new PlayerInventoryItemView(
                ItemId: stack.ItemId,
                DisplayName: definition.DisplayName,
                Description: definition.Description,
                Quantity: stack.Quantity,
                StackCap: definition.StackCap,
                IsUsable: definition.IsUsable,
                HealAmount: definition.HealAmount,
                IconKind: definition.IconKind,
                Kind: definition.Kind,
                EvidenceStatus: definition.EvidenceStatus,
                ResourceId: definition.ResourceId,
                IconId: definition.IconId);
        }

        public PlayerEquipmentItemView ToEquipmentView(PlayerEquipmentEntry entry)
        {
            var definition = ResolveEquipment(entry);
            var isBroken = entry.MaxDurability > 0 && entry.Durability <= 0;
            var contributesStats = entry.IsEquipped && !isBroken;
            var canRepair = entry.MaxDurability > 0 && entry.Durability < entry.MaxDurability;

            return new PlayerEquipmentItemView(
                EquipKey: entry.EquipKey,
                DisplayName: definition.DisplayName,
                Summary: definition.Summary,
                Slot: entry.Slot,
                ResourceId: entry.ResourceId,
                Level: entry.Level,
                RequiredLevel: definition.RequiredLevel,
                IsEquipped: entry.IsEquipped,
                IconKind: definition.IconKind,
                Rank: definition.Rank,
                ElementIcon: definition.ElementIcon,
                Gender: definition.Gender,
                Durability: entry.Durability,
                MaxDurability: entry.MaxDurability,
                IsBroken: isBroken,
                ContributesStats: contributesStats,
                CanRepair: canRepair,
                Tradeable: definition.Tradeable,
                CanUpgrade: !entry.IsEquipped,
                UpgradeStatus: entry.IsEquipped
                    ? "Phai thao trang bi truoc khi nang cap."
                    : "Chua bat roll nang cap: pending danh sach da/bua goc.",
                BonusCuongLuc: definition.Modifier.CuongLuc,
                BonusThanPhap: definition.Modifier.ThanPhap,
                BonusNoiLuc: definition.Modifier.NoiLuc,
                BonusTheLuc: definition.Modifier.TheLuc,
                BonusAttack: definition.Modifier.FlatAttack,
                BonusAttackPercent: definition.Modifier.AttackPercent,
                BonusDefense: definition.Modifier.Defense,
                BonusDodge: definition.Modifier.Dodge,
                BonusCrit: definition.Modifier.Crit,
                BonusMaxHp: definition.Modifier.MaxHp);
        }

        public PlayerSkillDefinition? GetSkillDefinition(int element, int familyCode) =>
            GetSkillDefinitionsForElement(element)
                .FirstOrDefault(definition => definition.FamilyCode == familyCode);

        public PlayerItemDefinition? GetItemDefinition(int itemId) =>
            _items.TryGetValue(itemId, out var definition) ? definition : null;

        public PlayerEquipmentDefinition? GetEquipmentDefinition(string templateKey)
        {
            if (string.IsNullOrWhiteSpace(templateKey))
            {
                return null;
            }

            return GetEquipmentDefinitions().TryGetValue(templateKey, out var definition)
                ? definition
                : null;
        }

        public PlayerEquipmentEntry BuildEquipmentEntryFromTemplate(string templateKey, string uniqueSeed)
        {
            var definition = GetRequiredEquipmentDefinition(templateKey);
            return BuildEquipmentEntry(definition, uniqueSeed);
        }

        public BattleLootReward CreateBattleLoot(BattleSessionState session, MonsterBattleTemplate battleTemplate)
        {
            var itemDefinitions = new List<PlayerItemDefinition>();
            var quantity = battleTemplate.Level >= 9 ? 2 : 1;

            itemDefinitions.Add((battleTemplate.Element & 0xFF) switch
            {
                1 => _items[5004],
                2 => _items[5003],
                _ => _items[5002]
            });

            if (battleTemplate.Level >= 8)
            {
                itemDefinitions.Add(_items[battleTemplate.Level >= 9 ? 5005 : 5001]);
            }

            var itemRewards = itemDefinitions
                .GroupBy(definition => definition.ItemId)
                .Select(group =>
                {
                    var definition = group.First();
                    var rewardQuantity = definition.IsUsable ? 1 : quantity;
                    return new BattleLootItemReward(
                        Stack: BuildInventoryStack(definition, rewardQuantity),
                        View: ToInventoryView(BuildInventoryStack(definition, rewardQuantity)));
                })
                .ToArray();

            var equipmentRewards = Array.Empty<BattleLootEquipmentReward>();
            var dropRoll = StablePercent($"{session.SessionId}:{session.MonsterKey}:{battleTemplate.Id}");
            if (battleTemplate.Level >= 8 && dropRoll < (battleTemplate.Level >= 9 ? 45 : 22))
            {
                var template = (battleTemplate.Element & 0xFF) switch
                {
                    1 => GetRequiredEquipmentDefinition("zap_hunter_helm"),
                    2 => GetRequiredEquipmentDefinition("water_guard_cloak"),
                    _ => GetRequiredEquipmentDefinition("fire_guard_vest")
                };

                var equipmentEntry = BuildEquipmentEntry(template, session.SessionId);
                equipmentRewards =
                [
                    new BattleLootEquipmentReward(
                        Entry: equipmentEntry,
                        View: ToEquipmentView(equipmentEntry))
                ];
            }

            return new BattleLootReward(itemRewards, equipmentRewards);
        }

        public PlayerEquipmentEntry BuildEquipmentEntry(PlayerEquipmentDefinition definition, string uniqueSeed)
        {
            var normalizedSeed = string.IsNullOrWhiteSpace(uniqueSeed)
                ? "loot"
                : uniqueSeed[..Math.Min(uniqueSeed.Length, 8)];
            var guidSuffix = Guid.NewGuid().ToString("N")[..10];
            var equipKey = $"{definition.TemplateKey}-{normalizedSeed}-{guidSuffix}";
            if (equipKey.Length > 48)
            {
                equipKey = equipKey[..48];
            }

            // Java evidence: new equipment instance inherits durability from template (ll.p = ll.q at creation).
            return new PlayerEquipmentEntry
            {
                EquipKey = equipKey,
                TemplateKey = definition.TemplateKey,
                Slot = definition.Slot,
                ResourceId = definition.ResourceId,
                Level = definition.Level,
                Durability = definition.MaxDurability,
                MaxDurability = definition.MaxDurability,
                IsEquipped = false,
                RawJson = BuildEquipmentRawJson(definition)
            };
        }

        public PlayerItemStack BuildInventoryStack(PlayerItemDefinition definition, int quantity) =>
            new()
            {
                ItemId = definition.ItemId,
                Quantity = quantity,
                RawJson = BuildItemRawJson(definition)
            };

        /// <summary>
        /// Returns stat modifiers from equipped items. Broken equipment (Durability == 0)
        /// is still worn but does NOT contribute stats.
        /// Java evidence: broken item stays equipped but effect is skipped.
        /// Remake policy (2026-05-03): equipment hỏng p==0 vẫn mặc nhưng không cộng stat/effect.
        /// </summary>
        public IEnumerable<PlayerStatModifier> GetEquippedModifiers(IEnumerable<PlayerEquipmentEntry> equipment) =>
            equipment
                .Where(entry => entry.IsEquipped && entry.Durability > 0)
                .Select(entry => EquipmentStatModifierParser.Parse(entry.RawJson));

        /// <summary>
        /// cmd 48: khôi phục ll.p = ll.q (current durability = max durability).
        /// Remake policy (2026-05-03): consume exactly 1 repair hammer <see cref="PlayerItemId.RepairHammer"/>
        /// (raw itemId 30099), restore full durability, do not consume Quan.
        /// </summary>
        public PlayerEquipmentEntry RestoreDurability(PlayerEquipmentEntry entry)
        {
            var definition = ResolveEquipment(entry);
            var fullyRepaired = definition with { Durability = definition.MaxDurability };
            return new PlayerEquipmentEntry
            {
                EquipKey = entry.EquipKey,
                TemplateKey = entry.TemplateKey,
                Slot = entry.Slot,
                ResourceId = entry.ResourceId,
                Level = entry.Level,
                Durability = entry.MaxDurability,
                MaxDurability = entry.MaxDurability,
                IsEquipped = entry.IsEquipped,
                RawJson = BuildEquipmentRawJson(fullyRepaired)
            };
        }

        /// <summary>
        /// Reduce current durability by <paramref name="amount"/>, clamped to 0.
        /// Java evidence: combat causes durability loss on worn equipment.
        /// Returns a new entry with updated durability; caller must persist.
        /// </summary>
        public PlayerEquipmentEntry ReduceDurability(PlayerEquipmentEntry entry, int amount)
        {
            if (amount <= 0 || entry.MaxDurability <= 0)
            {
                return entry;
            }

            var newDurability = System.Math.Max(0, entry.Durability - amount);
            if (newDurability == entry.Durability)
            {
                return entry;
            }

            return new PlayerEquipmentEntry
            {
                EquipKey = entry.EquipKey,
                TemplateKey = entry.TemplateKey,
                Slot = entry.Slot,
                ResourceId = entry.ResourceId,
                Level = entry.Level,
                Durability = newDurability,
                MaxDurability = entry.MaxDurability,
                IsEquipped = entry.IsEquipped,
                RawJson = entry.RawJson
            };
        }

        /// <summary>
        /// Aggregate stat modifiers from all equipped, non-broken equipment.
        /// Used to expose total equipment contribution in the client snapshot.
        /// </summary>
        public PlayerStatModifier GetEquippedModifierTotal(IEnumerable<PlayerEquipmentEntry> equipment)
        {
            var modifiers = GetEquippedModifiers(equipment);
            return SumModifiers(modifiers);
        }

        private static PlayerStatModifier SumModifiers(IEnumerable<PlayerStatModifier> modifiers)
        {
            var cuongLuc = 0;
            var thanPhap = 0;
            var noiLuc = 0;
            var theLuc = 0;
            var flatAttack = 0;
            var attackPercent = 0;
            var crit = 0;
            var defense = 0;
            var dodge = 0;
            var maxHp = 0;

            foreach (var m in modifiers)
            {
                cuongLuc += m.CuongLuc;
                thanPhap += m.ThanPhap;
                noiLuc += m.NoiLuc;
                theLuc += m.TheLuc;
                flatAttack += m.FlatAttack;
                attackPercent += m.AttackPercent;
                crit += m.Crit;
                defense += m.Defense;
                dodge += m.Dodge;
                maxHp += m.MaxHp;
            }

            return new PlayerStatModifier(
                CuongLuc: cuongLuc,
                ThanPhap: thanPhap,
                NoiLuc: noiLuc,
                TheLuc: theLuc,
                FlatAttack: flatAttack,
                AttackPercent: attackPercent,
                Crit: crit,
                Defense: defense,
                Dodge: dodge,
                MaxHp: maxHp);
        }

        public bool IsRepairMaterial(int itemId) => itemId == (int)PlayerItemId.RepairHammer;

        public PlayerEggDefinition? GetEggDefinition(int itemId)
        {
            // Remake policy / user confirmation 2026-05-03:
            // Open-egg costs are server-authoritative config, not Java server evidence.
            // Pending/Unverified: official Java server reward rates and most reward pools.
            var allowedRewardSlots = new[]
            {
                (int)PlayerEquipmentSlot.Armor,
                (int)PlayerEquipmentSlot.Weapon,
                (int)PlayerEquipmentSlot.Helmet,
                (int)PlayerEquipmentSlot.Ring
            };

            if (itemId == (int)PlayerItemId.ChickenEgg)
            {
                return new PlayerEggDefinition(
                    ItemId: itemId,
                    DisplayName: "Trứng thường",
                    OpenCostQuan: 20000,
                    AllowedRewardSlots: allowedRewardSlots,
                    AllowedEquipmentTemplateKeys: Array.Empty<string>());
            }

            if (itemId == (int)PlayerItemId.OstrichEgg)
            {
                return new PlayerEggDefinition(
                    ItemId: itemId,
                    DisplayName: "Trứng đà điểu",
                    OpenCostQuan: 30000,
                    AllowedRewardSlots: allowedRewardSlots,
                    AllowedEquipmentTemplateKeys:
                    [
                        "fire_guard_vest",
                        "zap_hunter_helm",
                        "water_guard_cloak"
                    ]);
            }

            if (itemId == (int)PlayerItemId.DinosaurEgg)
            {
                return new PlayerEggDefinition(
                    ItemId: itemId,
                    DisplayName: "Trứng khủng long",
                    OpenCostQuan: 100000,
                    AllowedRewardSlots: allowedRewardSlots,
                    AllowedEquipmentTemplateKeys: Array.Empty<string>());
            }

            if (itemId == (int)PlayerItemId.PhoenixEgg)
            {
                return new PlayerEggDefinition(
                    ItemId: itemId,
                    DisplayName: "Trứng phượng",
                    OpenCostQuan: 300000,
                    AllowedRewardSlots: allowedRewardSlots,
                    AllowedEquipmentTemplateKeys: Array.Empty<string>());
            }

            if (itemId == (int)PlayerItemId.DragonEgg)
            {
                return new PlayerEggDefinition(
                    ItemId: itemId,
                    DisplayName: "Trứng rồng",
                    OpenCostQuan: 300000,
                    AllowedRewardSlots: allowedRewardSlots,
                    AllowedEquipmentTemplateKeys: Array.Empty<string>());
            }

            return null;
        }

        private PlayerEquipmentEntry CreateStarterEquipmentEntry(
            PlayerEquipmentDefinition definition,
            string uniqueSeed,
            bool isEquipped)
        {
            var entry = BuildEquipmentEntry(definition, uniqueSeed);
            return new PlayerEquipmentEntry
            {
                EquipKey = entry.EquipKey,
                TemplateKey = entry.TemplateKey,
                Slot = entry.Slot,
                ResourceId = entry.ResourceId,
                Level = entry.Level,
                Durability = entry.Durability,
                MaxDurability = entry.MaxDurability,
                IsEquipped = isEquipped,
                RawJson = entry.RawJson
            };
        }

        private IReadOnlyList<PlayerSkillDefinition> GetSkillDefinitionsForElement(int element) =>
            _skillsByElement.TryGetValue(element, out var definitions)
                ? definitions
                : _skillsByElement[0];

        private PlayerEquipmentDefinition GetRequiredEquipmentDefinition(string templateKey)
        {
            var equipment = GetEquipmentDefinitions();
            return equipment.TryGetValue(templateKey, out var definition)
                ? definition
                : throw new InvalidOperationException($"Equipment template '{templateKey}' was not found in EquipmentCatalog.");
        }

        private IReadOnlyDictionary<string, PlayerEquipmentDefinition> GetEquipmentDefinitions()
        {
            if (_equipment is not null)
            {
                return _equipment;
            }

            lock (_equipmentLock)
            {
                if (_equipment is not null)
                {
                    return _equipment;
                }

                var definitions = _equipmentCatalogRepository.GetAllAsync().GetAwaiter().GetResult();
                _equipment = definitions.ToDictionary(
                    definition => definition.TemplateKey,
                    StringComparer.OrdinalIgnoreCase);
                return _equipment;
            }
        }

        private PlayerItemDefinition ResolveItem(int itemId, string rawJson)
        {
            if (_items.TryGetValue(itemId, out var definition))
            {
                return definition;
            }

            var payload = ParseRawPayload(rawJson);
            return new PlayerItemDefinition(
                ItemId: itemId,
                DisplayName: payload.TryGetValue("displayName", out var displayName) ? displayName : $"Vật phẩm {itemId}",
                Description: payload.TryGetValue("description", out var description) ? description : string.Empty,
                StackCap: ParseInt(payload, "stackCap", 99),
                IsUsable: ParseBool(payload, "isUsable"),
                HealAmount: ParseInt(payload, "healAmount", 0),
                ManaAmount: ParseInt(payload, "manaAmount", 0),
                RestoreKind: payload.TryGetValue("restoreKind", out var restoreKind) ? restoreKind : "hp",
                IconKind: payload.TryGetValue("iconKind", out var iconKind) ? iconKind : "item",
                Kind: ParseEnum(payload, "kind", PlayerItemKind.Material),
                EvidenceStatus: ParseEnum(payload, "evidenceStatus", PlayerItemEvidenceStatus.PendingUnverified),
                ResourceId: ParseNullableInt(payload, "resourceId"),
                IconId: ParseNullableInt(payload, "iconId"));
        }

        private PlayerEquipmentDefinition ResolveEquipment(PlayerEquipmentEntry entry)
        {
            var resourceId = entry.ResourceId;
            var payload = ParseRawPayload(entry.RawJson);
            var templateKey = !string.IsNullOrWhiteSpace(entry.TemplateKey)
                ? entry.TemplateKey
                : payload.TryGetValue("templateKey", out var resolvedTemplateKey)
                ? resolvedTemplateKey
                : string.Empty;
            if (!string.IsNullOrWhiteSpace(templateKey) &&
                GetEquipmentDefinitions().TryGetValue(templateKey, out var definition))
            {
                return definition with
                {
                    Rank = ParseInt(payload, "rank", definition.Rank),
                    ElementIcon = ParseInt(payload, "elementIcon", definition.ElementIcon),
                    Gender = ParseInt(payload, "gender", definition.Gender),
                    Durability = entry.Durability,
                    MaxDurability = entry.MaxDurability,
                    Tradeable = ParseBool(payload, "tradeable", definition.Tradeable),
                    RepairCost = ParseInt(payload, "repairCost", (int)definition.RepairCost)
                };
            }

            return new PlayerEquipmentDefinition(
                TemplateKey: templateKey,
                DisplayName: payload.TryGetValue("displayName", out var displayName) ? displayName : $"Trang bị {resourceId}",
                Summary: payload.TryGetValue("summary", out var summary) ? summary : string.Empty,
                Slot: ParseInt(payload, "slot", 0),
                ResourceId: resourceId,
                Level: ParseInt(payload, "level", 1),
                RequiredLevel: ParseInt(payload, "requiredLevel", 1),
                IconKind: payload.TryGetValue("iconKind", out var iconKind) ? iconKind : "equipment",
                Rank: ParseInt(payload, "rank", 0),
                ElementIcon: ParseInt(payload, "elementIcon", 7),
                Gender: ParseInt(payload, "gender", 2),
                Durability: ParseInt(payload, "durability", -1),
                MaxDurability: ParseInt(payload, "maxDurability", 0),
                Tradeable: ParseBool(payload, "tradeable", fallback: true),
                RepairCost: ParseInt(payload, "repairCost", -1),
                IsEnabled: true,
                Modifier: EquipmentStatModifierParser.Parse(entry.RawJson));
        }

        private static string BuildItemRawJson(PlayerItemDefinition definition) =>
            JsonSerializer.Serialize(new
            {
                displayName = definition.DisplayName,
                description = definition.Description,
                stackCap = definition.StackCap,
                isUsable = definition.IsUsable,
                healAmount = definition.HealAmount,
                manaAmount = definition.ManaAmount,
                restoreKind = definition.RestoreKind,
                iconKind = definition.IconKind,
                kind = definition.Kind,
                evidenceStatus = definition.EvidenceStatus,
                resourceId = definition.ResourceId,
                iconId = definition.IconId
            });

        private static string BuildEquipmentRawJson(PlayerEquipmentDefinition definition) =>
            JsonSerializer.Serialize(new
            {
                templateKey = definition.TemplateKey,
                displayName = definition.DisplayName,
                summary = definition.Summary,
                slot = definition.Slot,
                resourceId = definition.ResourceId,
                level = definition.Level,
                requiredLevel = definition.RequiredLevel,
                iconKind = definition.IconKind,
                rank = definition.Rank,
                elementIcon = definition.ElementIcon,
                gender = definition.Gender,
                durability = definition.Durability,
                maxDurability = definition.MaxDurability,
                tradeable = definition.Tradeable,
                repairCost = definition.RepairCost,
                legacyTags = new
                {
                    key = "string",
                    slot = 84,
                    resourceId = 4,
                    enhancementLevel = 27,
                    requiredLevel = 135,
                    rank = 138,
                    durability = 139,
                    maxDurability = 144,
                    tradeable = 85,
                    repairCost = 190
                },
                modifier = new
                {
                    cuongLuc = definition.Modifier.CuongLuc,
                    thanPhap = definition.Modifier.ThanPhap,
                    noiLuc = definition.Modifier.NoiLuc,
                    theLuc = definition.Modifier.TheLuc,
                    attack = definition.Modifier.FlatAttack,
                    attackPercent = definition.Modifier.AttackPercent,
                    crit = definition.Modifier.Crit,
                    defense = definition.Modifier.Defense,
                    dodge = definition.Modifier.Dodge,
                    maxHp = definition.Modifier.MaxHp
                }
            });

        private static int StableIndex(string value, int count)
        {
            if (count <= 0)
            {
                throw new ArgumentOutOfRangeException(nameof(count), "Reward pool must not be empty.");
            }

            return StablePercent(value) % count;
        }

        private static int StablePercent(string value)
        {
            unchecked
            {
                var hash = 17;
                foreach (var ch in value)
                {
                    hash = hash * 31 + ch;
                }

                return Math.Abs(hash % 100);
            }
        }

        private static Dictionary<string, string> ParseRawPayload(string rawJson)
        {
            if (string.IsNullOrWhiteSpace(rawJson) || rawJson == "{}")
            {
                return new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            }

            try
            {
                using var document = JsonDocument.Parse(rawJson);
                return document.RootElement.ValueKind == JsonValueKind.Object
                    ? document.RootElement.EnumerateObject()
                        .Where(property => property.Value.ValueKind is JsonValueKind.String or JsonValueKind.Number or JsonValueKind.True or JsonValueKind.False)
                        .ToDictionary(
                            property => property.Name,
                            property => property.Value.ToString(),
                            StringComparer.OrdinalIgnoreCase)
                    : new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            }
            catch (JsonException)
            {
                return new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
            }
        }

        private static int ParseInt(IReadOnlyDictionary<string, string> payload, string key, int fallback = 0) =>
            payload.TryGetValue(key, out var value) && int.TryParse(value, out var parsed)
                ? parsed
                : fallback;

        private static bool ParseBool(IReadOnlyDictionary<string, string> payload, string key, bool fallback = false) =>
            payload.TryGetValue(key, out var value) && bool.TryParse(value, out var parsed)
                ? parsed
                : fallback;

        private static int? ParseNullableInt(IReadOnlyDictionary<string, string> payload, string key)
        {
            if (!payload.TryGetValue(key, out var value) || string.IsNullOrWhiteSpace(value))
            {
                return null;
            }

            return int.TryParse(value, out var parsed) ? parsed : null;
        }

        private static TEnum ParseEnum<TEnum>(
            IReadOnlyDictionary<string, string> payload,
            string key,
            TEnum fallback)
            where TEnum : struct, Enum
        {
            if (!payload.TryGetValue(key, out var value) || string.IsNullOrWhiteSpace(value))
            {
                return fallback;
            }

            return Enum.TryParse<TEnum>(value, ignoreCase: true, out var parsed) ? parsed : fallback;
        }

        private static IReadOnlyDictionary<int, PlayerItemDefinition> CreateItemDefinitions() =>
            new Dictionary<int, PlayerItemDefinition>
            {
                [5001] = new PlayerItemDefinition(5001, "Tiểu Hồi Phục", "Khôi phục HP ngoài battle; lượng hồi scale theo Cường Lực/thiếu HP.", 20, true, 35, 0, "hp", "potion_red", PlayerItemKind.Consumable, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5002] = new PlayerItemDefinition(5002, "Tiểu Nội Dược", "Khôi phục MP ngoài battle; lượng hồi scale theo Nội Lực/thiếu MP.", 20, true, 0, 35, "mp", "potion_blue", PlayerItemKind.Consumable, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5003] = new PlayerItemDefinition(5003, "Huyết thạch", "Nguyên liệu remake dùng cho nâng cấp/trang bị; công thức gốc pending danh sách đá/bùa Java.", 99, false, 0, 0, "none", "huyet_thach", PlayerItemKind.Material, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5004] = new PlayerItemDefinition(5004, "Kim thạch", "Nguyên liệu remake dùng cho nâng cấp/trang bị; công thức gốc pending danh sách đá/bùa Java.", 99, false, 0, 0, "none", "kim_thach", PlayerItemKind.Material, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5005] = new PlayerItemDefinition(5005, "Trung Hồi Phục", "Khôi phục HP ngoài battle; lượng hồi scale theo Cường Lực/thiếu HP.", 20, true, 70, 0, "hp", "potion_blue", PlayerItemKind.Consumable, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5006] = new PlayerItemDefinition(5006, "Tiểu Nội Dược", "Khôi phục MP ngoài battle; lượng hồi scale theo Nội Lực/thiếu MP.", 20, true, 0, 35, "mp", "potion_blue", PlayerItemKind.Consumable, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [5007] = new PlayerItemDefinition(5007, "Trái Đào", "Khôi phục HP/MP ngoài battle; lượng hồi scale theo Cường Lực và Nội Lực.", 20, true, 500, 250, "hp_mp", "peach", PlayerItemKind.Consumable, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.ChickenEgg] = new PlayerItemDefinition((int)PlayerItemId.ChickenEgg, "Trứng gà", "Đập trứng bằng Quan để có cơ hội nhận trang bị từ danh sách mở thưởng đã cấu hình cho trứng gà.", 20, false, 0, 0, "item", "chicken_egg", PlayerItemKind.Egg, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.OstrichEgg] = new PlayerItemDefinition((int)PlayerItemId.OstrichEgg, "Trứng đà điểu", "Đập trứng bằng Quan để có cơ hội nhận trang bị từ danh sách mở thưởng của trứng đà điểu.", 20, false, 0, 0, "item", "ostrich_egg", PlayerItemKind.Egg, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.DinosaurEgg] = new PlayerItemDefinition((int)PlayerItemId.DinosaurEgg, "Trứng khủng long", "Đập trứng bằng Quan để có cơ hội nhận trang bị từ danh sách mở thưởng đã cấu hình cho trứng khủng long.", 20, false, 0, 0, "item", "dinosaur_egg", PlayerItemKind.Egg, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.PhoenixEgg] = new PlayerItemDefinition((int)PlayerItemId.PhoenixEgg, "Trứng phượng hoàng", "Đập trứng bằng Quan để có cơ hội nhận trang bị từ danh sách mở thưởng đã cấu hình cho trứng phượng hoàng.", 20, false, 0, 0, "item", "phoenix_egg", PlayerItemKind.Egg, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.DragonEgg] = new PlayerItemDefinition((int)PlayerItemId.DragonEgg, "Trứng rồng", "Đập trứng bằng Quan để có cơ hội nhận trang bị từ danh sách mở thưởng đã cấu hình cho trứng rồng.", 20, false, 0, 0, "item", "dragon_egg", PlayerItemKind.Egg, PlayerItemEvidenceStatus.RemakePolicy, null, null),
                [(int)PlayerItemId.RepairHammer] = new PlayerItemDefinition((int)PlayerItemId.RepairHammer, "Búa Sửa Chữa", "Dụng cụ sửa trang bị; dùng 1 búa để phục hồi độ bền cho một trang bị bị hư hỏng.", 20, false, 0, 0, "item", "repair_hammer", PlayerItemKind.RepairMaterial, PlayerItemEvidenceStatus.RemakePolicy, null, null),
            };

        private static IReadOnlyDictionary<int, IReadOnlyList<PlayerSkillDefinition>> CreateSkillDefinitions() =>
            new Dictionary<int, IReadOnlyList<PlayerSkillDefinition>>
            {
                [0] =
                [
                    new PlayerSkillDefinition(1000, 3, 1, 1),
                    new PlayerSkillDefinition(1006, 3, 6, 1),
                    new PlayerSkillDefinition(1007, 2, 12, 1),
                ],
                [1] =
                [
                    new PlayerSkillDefinition(2000, 3, 1, 1),
                    new PlayerSkillDefinition(2003, 3, 6, 1),
                    new PlayerSkillDefinition(2006, 2, 12, 1),
                ],
                [2] =
                [
                    new PlayerSkillDefinition(4000, 3, 1, 1),
                    new PlayerSkillDefinition(4006, 3, 6, 1),
                    new PlayerSkillDefinition(4007, 2, 12, 1),
                ],
            };

        public sealed record BattleLootReward(
            IReadOnlyList<BattleLootItemReward> Items,
            IReadOnlyList<BattleLootEquipmentReward> Equipment);

        public sealed record BattleLootItemReward(
            PlayerItemStack Stack,
            PlayerInventoryItemView View);

        public sealed record BattleLootEquipmentReward(
            PlayerEquipmentEntry Entry,
            PlayerEquipmentItemView View);

        public sealed record PlayerItemDefinition(
            int ItemId,
            string DisplayName,
            string Description,
            int StackCap,
            bool IsUsable,
            int HealAmount,
            int ManaAmount,
            string RestoreKind,
            string IconKind,
            PlayerItemKind Kind,
            PlayerItemEvidenceStatus EvidenceStatus,
            int? ResourceId,
            int? IconId);

        public sealed record PlayerSkillDefinition(
            int FamilyCode,
            int MaxLevel,
            int RequiredLevel,
            int Cost);

        public sealed record PlayerEggDefinition(
            int ItemId,
            string DisplayName,
            long OpenCostQuan,
            IReadOnlyList<int> AllowedRewardSlots,
            IReadOnlyList<string> AllowedEquipmentTemplateKeys);
    }
}
