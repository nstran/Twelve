using System.Collections.Generic;
using System;
using Twelve.Application.Battle;
using Twelve.Core.Battle;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;

namespace Twelve.Application.Monsters
{
    public sealed class MonsterBattleBootstrapService : IMonsterBattleBootstrapService
    {
        private readonly IMapMonsterRosterService _mapMonsterRosterService;
        private readonly IMonsterSpawnCatalog _monsterSpawnCatalog;
        private readonly IMonsterBattleCatalog _monsterBattleCatalog;
        private readonly IMonsterAssetCatalog _monsterAssetCatalog;
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;

        public MonsterBattleBootstrapService(
            IMapMonsterRosterService mapMonsterRosterService,
            IMonsterSpawnCatalog monsterSpawnCatalog,
            IMonsterBattleCatalog monsterBattleCatalog,
            IMonsterAssetCatalog monsterAssetCatalog,
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService)
        {
            _mapMonsterRosterService = mapMonsterRosterService;
            _monsterSpawnCatalog = monsterSpawnCatalog;
            _monsterBattleCatalog = monsterBattleCatalog;
            _monsterAssetCatalog = monsterAssetCatalog;
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
        }

        public MonsterBattleBootstrapResponse? Bootstrap(
            MonsterBattleBootstrapRequest request,
            PlayerAggregate? playerAggregate = null)
        {
            var encounter = _mapMonsterRosterService.FindEncounter(request.MapId, request.RoomId, request.MonsterKey);
            if (encounter is null || !encounter.IsActive)
            {
                return null;
            }

            var spawnTemplate = _monsterSpawnCatalog.GetBySpawnTemplateKey(encounter.SpawnTemplateKey);
            if (spawnTemplate is null)
            {
                return null;
            }

            var battleTemplate = _monsterBattleCatalog.GetById(spawnTemplate.BattleTemplateId);
            if (battleTemplate is null)
            {
                return null;
            }

            var asset = !string.IsNullOrWhiteSpace(spawnTemplate.AssetCatalogId)
                ? _monsterAssetCatalog.GetById(spawnTemplate.AssetCatalogId)
                : null;

            var enemy = new MonsterBattleInstance(
                CombatantId: $"enemy:{request.MonsterKey}",
                MonsterKey: request.MonsterKey,
                BattleTemplateId: battleTemplate.Id,
                DisplayName: (!string.IsNullOrWhiteSpace(asset?.DisplayName) ? asset!.DisplayName : spawnTemplate.DisplayName),
                Element: battleTemplate.Element,
                Level: battleTemplate.Level,
                CurrentHp: battleTemplate.MaxHp,
                MaxHp: battleTemplate.MaxHp,
                CurrentMp: 0,
                MaxMp: battleTemplate.MaxMp,
                CurrentPower: 0,
                MaxPower: battleTemplate.MaxPower,
                Strength: battleTemplate.Strength,
                Agility: battleTemplate.Agility,
                Magic: battleTemplate.Magic,
                Vitality: battleTemplate.Vitality,
                MinDamage: battleTemplate.MinDamage,
                MaxDamage: battleTemplate.MaxDamage,
                Defense: battleTemplate.Defense,
                HitRate: battleTemplate.HitRate,
                DodgeRate: battleTemplate.DodgeRate,
                CriticalRate: battleTemplate.CriticalRate,
                Skills: CreateSkillInstances(battleTemplate.Skills),
                Appearance: battleTemplate.Appearance,
                HealGainPercent: ComputeStrengthResourceGainPercent(battleTemplate.Strength),
                ManaGainPercent: ComputeMagicResourceGainPercent(battleTemplate.Magic),
                PowerGainPercent: ComputeStrengthResourceGainPercent(battleTemplate.Strength),
                AssetCatalogId: asset?.AssetCatalogId,
                FramePaths: asset?.FramePaths);

            var sessionId = Guid.NewGuid().ToString("N");
            var initialBoard = _battleBoardService.CreateInitialBoard();
            var playerState = playerAggregate is null
                ? PlayerBattleStateFactory.CreateDefault()
                : PlayerBattleStateFactory.Create(playerAggregate, BattleSide.Player);
            var enemyState = CreateEnemySessionState(enemy, spawnTemplate.IqValue, battleTemplate.AiProfileId);

            _battleSessionStore.Save(new BattleSessionState(
                SessionId: sessionId,
                MonsterKey: encounter.MonsterKey,
                ActiveTurn: request.InitialTurnSide,
                Board: initialBoard,
                Player: playerState,
                Enemy: enemyState,
                CreatedAtUtc: DateTime.UtcNow,
                SpawnTemplateKey: encounter.SpawnTemplateKey,
                BattleTemplateId: battleTemplate.Id,
                // Source: MAP_SYSTEM_RECONSTRUCTION.md §3 Entity layer + §5 runtime/server sync.
                // Battle result needs original runtime room to despawn/respawn the exact map encounter.
                MapId: request.MapId,
                RoomId: request.RoomId));

            return new MonsterBattleBootstrapResponse(
                SessionId: sessionId,
                MonsterKey: encounter.MonsterKey,
                SpawnTemplateKey: encounter.SpawnTemplateKey,
                BattleTemplateId: battleTemplate.Id,
                VisualTypeByte: spawnTemplate.VisualTypeByte,
                DisplayLevel: spawnTemplate.DisplayLevel,
                IqValue: spawnTemplate.IqValue,
                NameColorMode: spawnTemplate.NameColorMode,
                InitialTurnSide: request.InitialTurnSide,
                SharedSheetFamily: asset?.SharedSheetFamily,
                InitialBoard: initialBoard,
                Player: PlayerBattleStateFactory.CreateSnapshot(playerState),
                Enemy: enemy,
                // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
                // Server-owned remake v1 base resource table; FE must not hardcode HP/MP/Power formulas.
                GemResourceConfig: BattleGemResourceConfig.CreateRemakeV1());
        }

        // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
        // Java client proves HP/MP/Power bars (`lh.u/t`, `lh.w/v`) but not old server resource formula.
        // Remake rule v1 keeps Java-like integer math and moves resource scaling to server authority.
        // Balance (W) reduced scale 3→1 %/point and cap 180→140 to align with PlayerBattleStateFactory.
        private static int ComputeStrengthResourceGainPercent(int strength) =>
            Math.Clamp(100 + ((strength - 10) * 1), 80, 140);

        private static int ComputeMagicResourceGainPercent(int magic) =>
            Math.Clamp(100 + ((magic - 10) * 1), 80, 140);

        private static BattleSessionCombatantState CreateEnemySessionState(
            MonsterBattleInstance enemy,
            int iqValue,
            string? aiProfileId) =>
            new(
                CombatantId: enemy.CombatantId,
                DisplayName: enemy.DisplayName,
                Side: BattleSide.Enemy,
                CurrentHp: enemy.CurrentHp,
                MaxHp: enemy.MaxHp,
                CurrentMp: enemy.CurrentMp,
                MaxMp: enemy.MaxMp,
                CurrentPower: enemy.CurrentPower,
                MaxPower: enemy.MaxPower,
                Strength: enemy.Strength,
                Agility: enemy.Agility,
                Magic: enemy.Magic,
                Vitality: enemy.Vitality,
                MinDamage: enemy.MinDamage,
                MaxDamage: enemy.MaxDamage,
                Defense: enemy.Defense,
                HitRate: enemy.HitRate,
                DodgeRate: enemy.DodgeRate,
                ElementCode: enemy.Element,
                CriticalRate: enemy.CriticalRate,
                Skills: CreateSessionSkills(enemy.Skills),
                Level: enemy.Level,
                IqValue: iqValue,
                AiProfileId: aiProfileId,
                HealGainPercent: enemy.HealGainPercent,
                ManaGainPercent: enemy.ManaGainPercent,
                PowerGainPercent: enemy.PowerGainPercent);

        private static IReadOnlyList<MonsterSkillInstance> CreateSkillInstances(
            IReadOnlyList<MonsterSkillTemplate> templates)
        {
            if (templates.Count == 0)
            {
                return [];
            }

            var instances = new List<MonsterSkillInstance>(templates.Count);
            foreach (var template in templates)
            {
                instances.Add(new MonsterSkillInstance(
                    SkillId: template.SkillId,
                    Level: template.Level,
                    ManaCost: template.ManaCost));
            }

            return instances;
        }

        private static IReadOnlyList<BattleSessionSkillInstance> CreateSessionSkills(
            IReadOnlyList<MonsterSkillInstance> skills)
        {
            if (skills.Count == 0)
            {
                return [];
            }

            var instances = new List<BattleSessionSkillInstance>(skills.Count);
            foreach (var skill in skills)
            {
                instances.Add(new BattleSessionSkillInstance(
                    SkillId: skill.SkillId,
                    Level: skill.Level,
                    ManaCost: skill.ManaCost));
            }

            return instances;
        }
    }
}
