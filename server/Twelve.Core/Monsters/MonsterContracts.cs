using System.Collections.Generic;
using Twelve.Core.Battle;

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
        string MapId,
        int RoomId,
        string SpawnTemplateKey,
        int SpawnCellRow,
        int SpawnCellCol,
        bool IsActive = true
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
        int CriticalDamage,
        IReadOnlyList<MonsterSkillTemplate> Skills,
        MonsterAppearanceTemplate Appearance,
        string? AiProfileId = null
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
        int CriticalDamage,
        IReadOnlyList<MonsterSkillInstance> Skills,
        MonsterAppearanceTemplate Appearance
    );

    public sealed record MonsterBattleBootstrapRequest(
        string MapId,
        int RoomId,
        string MonsterKey,
        BattleSide InitialTurnSide = BattleSide.Player
    );

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
        MonsterBattleInstance Enemy
    );
}
