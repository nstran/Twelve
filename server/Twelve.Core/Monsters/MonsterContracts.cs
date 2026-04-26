using System.Collections.Generic;
using Twelve.Core.Battle;
using Twelve.Core.Players;

namespace Twelve.Core.Monsters
{
    public enum MonsterSharedSheetFamily
    {
        Monster = 0,
        Zap = 1,
        Ice = 2,
    }

    public sealed record MonsterAssetCatalogEntry(
        string AssetCatalogId,
        MonsterSharedSheetFamily SharedSheetFamily,
        int? SpeciesCode = null,
        int? Slot = null
    );

    public sealed record MonsterSpawnTemplate(
        string SpawnTemplateKey,
        string DisplayName,
        byte VisualTypeByte,
        int DisplayLevel,
        int IqValue,
        int SpawnCount,
        byte NameColorMode,
        string BattleTemplateId,
        string? AssetCatalogId = null
    );

    public sealed record MapMonsterEncounter(
        string MonsterKey,
        string SpawnGroupKey,
        int SpawnInstanceIndex,
        string MapId,
        int RoomId,
        string SpawnTemplateKey,
        int SpawnCellRow,
        int SpawnCellCol,
        string SurfaceId,
        float PatrolStartRatio,
        float PatrolEndRatio,
        float SpawnRatio,
        float MoveSpeed,
        bool IsActive = true
    );

    public sealed record MapMonsterSpawnGroup(
        string SpawnGroupKey,
        string MapId,
        int RoomId,
        string SpawnTemplateKey,
        int SpawnCellRow,
        int SpawnCellCol,
        string SurfaceId,
        float PatrolStartRatio,
        float PatrolEndRatio,
        float SpawnStartRatio,
        float SpawnEndRatio,
        float MoveSpeed,
        bool IsActive = true
    );

    public sealed record MapMonsterRosterEntry(
        string MonsterKey,
        string SpawnGroupKey,
        int SpawnInstanceIndex,
        string SpawnTemplateKey,
        string DisplayName,
        byte VisualTypeByte,
        int DisplayLevel,
        int IqValue,
        byte NameColorMode,
        MonsterSharedSheetFamily SharedSheetFamily,
        string SurfaceId,
        float PatrolStartRatio,
        float PatrolEndRatio,
        float SpawnRatio,
        float MoveSpeed
    );

    public sealed record MapMonsterRosterResponse(
        string MapId,
        int RoomId,
        IReadOnlyList<MapMonsterRosterEntry> Encounters
    );

    public sealed record MonsterSkillTemplate(
        int SkillId,
        int Level,
        int ManaCost
    );

    public sealed record MonsterSkillInstance(
        int SkillId,
        int Level,
        int ManaCost
    );

    public sealed record MonsterAppearanceTemplate(
        string? AssetCatalogId = null,
        int? BaseBodyId = null,
        int? WeaponBodyId = null,
        int? HairBodyId = null
    );

    public sealed record MonsterBattleTemplate(
        string BattleTemplateId,
        byte Element,
        int Level,
        int MaxHp,
        int MaxMp,
        int MaxPower,
        int Strength,
        int Agility,
        int Magic,
        int Vitality,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int HitRate,
        int DodgeRate,
        int CriticalRate,
        IReadOnlyList<MonsterSkillTemplate> Skills,
        MonsterAppearanceTemplate Appearance,
        string? AiProfileId = null,
        int ExpReward = 0,
        int GoldReward = 0,
        int QuanReward = 0
    );

    public sealed record MonsterBattleInstance(
        string CombatantId,
        string MonsterKey,
        string BattleTemplateId,
        string DisplayName,
        byte Element,
        int Level,
        int CurrentHp,
        int MaxHp,
        int CurrentMp,
        int MaxMp,
        int CurrentPower,
        int MaxPower,
        int Strength,
        int Agility,
        int Magic,
        int Vitality,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int HitRate,
        int DodgeRate,
        int CriticalRate,
        IReadOnlyList<MonsterSkillInstance> Skills,
        MonsterAppearanceTemplate Appearance,
        // Server-owned resource gain coefficients — §5 of 08-level-stat-exp-and-element-balance.md
        int HealGainPercent = 100,
        int ManaGainPercent = 100,
        int PowerGainPercent = 100
    );

    public sealed record BattleCombatantSnapshot(
        string CombatantId,
        string DisplayName,
        int Level,
        int CurrentHp,
        int MaxHp,
        int CurrentMp,
        int MaxMp,
        int CurrentPower,
        int MaxPower,
        int Strength,
        int Agility,
        int Magic,
        int Vitality,
        int MinDamage,
        int MaxDamage,
        int Defense,
        int HitRate,
        int DodgeRate,
        int CriticalRate,
        IReadOnlyList<MonsterSkillInstance> Skills,
        // Server-owned resource gain coefficients — §5 of 08-level-stat-exp-and-element-balance.md
        int HealGainPercent = 100,
        int ManaGainPercent = 100,
        int PowerGainPercent = 100
    );

    public sealed record MonsterBattleBootstrapRequest(
        string MapId,
        int RoomId,
        string MonsterKey,
        BattleSide InitialTurnSide = BattleSide.Player
    );

    public sealed record BattleGemResourceBase(
        int GemType,
        int BaseHeal,
        int BaseMana,
        int BasePower
    );

    /// <summary>
    /// Server-owned base gem resource values per match.
    /// Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
    /// Java client proves HP/MP/Power bars and board gem families, but not the old server-side
    /// resource table. This remake v1 table therefore lives on the server and the client only
    /// applies values received in bootstrap responses.
    /// Values represent the base amount gained when matching 3 gems of that resource family.
    /// Client formula: gain = Math.Truncate(baseValue * matchedCount / 3) then scaled by
    /// HealGainPercent / ManaGainPercent / PowerGainPercent.
    /// </summary>
    public sealed record BattleGemResourceConfig(
        int BaseHealPerGem = 18,
        int BaseManaPerGem = 5,
        int BasePowerPerGem = 5,
        IReadOnlyList<BattleGemResourceBase>? PerGemBases = null
    )
    {
        private static readonly BattleGemResourceBase[] RemakeV1PerGemBases =
        [
            // Source: 08-level-stat-exp-and-element-balance.md §5.
            // Java client proves resource bars and board color families, but not the old server
            // table. Keep the Java-feel per-color proportions on the server so FE does not
            // hardcode HP/MP/Power math and mixed HP/MP gems do not inherit one global scalar.
            new(0, 0, 0, 3),
            new(1, 12, 0, 1),
            new(2, 2, 8, 1),
            new(3, 0, 0, 3),
            new(4, 4, 3, 1),
            new(5, 0, 5, 2),
            new(6, 0, 0, 1),
            new(8, 0, 0, 3)
        ];

        /// <summary>
        /// Remake v1 server authority for gem resource bases.
        /// Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
        /// The legacy scalar fields remain for old clients; current clients prefer PerGemBases.
        /// </summary>
        public static BattleGemResourceConfig CreateRemakeV1() =>
            new(PerGemBases: RemakeV1PerGemBases);
    }

    public sealed record MonsterBattleBootstrapResponse(
        string SessionId,
        string MonsterKey,
        string SpawnTemplateKey,
        string BattleTemplateId,
        byte VisualTypeByte,
        int DisplayLevel,
        int IqValue,
        byte NameColorMode,
        BattleSide InitialTurnSide,
        MonsterSharedSheetFamily? SharedSheetFamily,
        IReadOnlyList<IReadOnlyList<int?>> InitialBoard,
        BattleCombatantSnapshot Player,
        MonsterBattleInstance Enemy,
        BattleGemResourceConfig GemResourceConfig,
        string BattleKind = "monster",
        PvpCharacterAppearance? EnemyPlayerAppearance = null
    );
}
