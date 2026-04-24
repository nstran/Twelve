using System.Collections.Generic;
using System;
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

            var battleTemplate = _monsterBattleCatalog.GetByBattleTemplateId(spawnTemplate.BattleTemplateId);
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
                BattleTemplateId: battleTemplate.BattleTemplateId,
                DisplayName: spawnTemplate.DisplayName,
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
                CriticalDamage: battleTemplate.CriticalDamage,
                Skills: CreateSkillInstances(battleTemplate.Skills),
                Appearance: battleTemplate.Appearance);

            var sessionId = Guid.NewGuid().ToString("N");
            var initialBoard = _battleBoardService.CreateInitialBoard();
            var playerState = playerAggregate is null
                ? CreateDefaultPlayerState()
                : CreatePlayerSessionState(playerAggregate);
            var enemyState = CreateEnemySessionState(enemy, spawnTemplate.IqValue, battleTemplate.AiProfileId);

            _battleSessionStore.Save(new BattleSessionState(
                SessionId: sessionId,
                MonsterKey: encounter.MonsterKey,
                ActiveTurn: request.InitialTurnSide,
                Board: initialBoard,
                Player: playerState,
                Enemy: enemyState,
                CreatedAtUtc: DateTime.UtcNow));

            return new MonsterBattleBootstrapResponse(
                SessionId: sessionId,
                MonsterKey: encounter.MonsterKey,
                SpawnTemplateKey: encounter.SpawnTemplateKey,
                BattleTemplateId: battleTemplate.BattleTemplateId,
                VisualTypeByte: spawnTemplate.VisualTypeByte,
                DisplayLevel: spawnTemplate.DisplayLevel,
                IqValue: spawnTemplate.IqValue,
                NameColorMode: spawnTemplate.NameColorMode,
                InitialTurnSide: request.InitialTurnSide,
                SharedSheetFamily: asset?.SharedSheetFamily,
                InitialBoard: initialBoard,
                Player: CreateCombatantSnapshot(playerState),
                Enemy: enemy);
        }

        private static BattleSessionCombatantState CreatePlayerSessionState(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
            var stats = aggregate.Stats;
            var maxHp = Math.Max(1, player.MaxHp);
            var currentHp = Math.Clamp(player.Hp <= 0 ? maxHp : player.Hp, 1, maxHp);
            var maxMp = Math.Max(0, player.MaxMp);
            var maxPower = Math.Max(0, player.MaxPower);
            var minDamage = Math.Max(0, stats.MinDamage);
            var maxDamage = Math.Max(minDamage, stats.MaxDamage);

            return new BattleSessionCombatantState(
                CombatantId: $"player:{player.Id}",
                DisplayName: player.Username,
                Side: BattleSide.Player,
                CurrentHp: currentHp,
                MaxHp: maxHp,
                CurrentMp: Math.Clamp(player.Mp, 0, maxMp),
                MaxMp: maxMp,
                CurrentPower: Math.Clamp(player.Power, 0, maxPower),
                MaxPower: maxPower,
                Strength: stats.CuongLuc + stats.BonusCuongLuc,
                Agility: stats.ThanPhap + stats.BonusThanPhap,
                Magic: stats.NoiLuc + stats.BonusNoiLuc,
                Vitality: stats.TheLuc + stats.BonusTheLuc,
                MinDamage: minDamage,
                MaxDamage: maxDamage,
                Defense: stats.Defense,
                HitRate: stats.Hit,
                DodgeRate: stats.Dodge,
                CriticalDamage: stats.Crit,
                Skills: CreateSessionSkills(aggregate.Skills),
                Level: player.Level,
                IqValue: 0,
                AiProfileId: null);
        }

        private static BattleSessionCombatantState CreateDefaultPlayerState() =>
            new(
                CombatantId: "player:self",
                DisplayName: "Player",
                Side: BattleSide.Player,
                CurrentHp: 100,
                MaxHp: 100,
                CurrentMp: 30,
                MaxMp: 100,
                CurrentPower: 40,
                MaxPower: 100,
                Strength: 12,
                Agility: 10,
                Magic: 8,
                Vitality: 10,
                MinDamage: 10,
                MaxDamage: 16,
                Defense: 5,
                HitRate: 85,
                DodgeRate: 5,
                CriticalDamage: 110,
                Skills: [],
                Level: 10,
                IqValue: 0,
                AiProfileId: null);

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
                CriticalDamage: enemy.CriticalDamage,
                Skills: CreateSessionSkills(enemy.Skills),
                Level: enemy.Level,
                IqValue: iqValue,
                AiProfileId: aiProfileId);

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

        private static IReadOnlyList<BattleSessionSkillInstance> CreateSessionSkills(
            IReadOnlyList<PlayerSkillEntry> skills)
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
                    Level: Math.Max(1, skill.Level),
                    ManaCost: 0));
            }

            return instances;
        }

        private static BattleCombatantSnapshot CreateCombatantSnapshot(BattleSessionCombatantState state) =>
            new(
                CombatantId: state.CombatantId,
                DisplayName: state.DisplayName,
                Level: state.Level,
                CurrentHp: state.CurrentHp,
                MaxHp: state.MaxHp,
                CurrentMp: state.CurrentMp,
                MaxMp: state.MaxMp,
                CurrentPower: state.CurrentPower,
                MaxPower: state.MaxPower,
                Strength: state.Strength,
                Agility: state.Agility,
                Magic: state.Magic,
                Vitality: state.Vitality,
                MinDamage: state.MinDamage,
                MaxDamage: state.MaxDamage,
                Defense: state.Defense,
                HitRate: state.HitRate,
                DodgeRate: state.DodgeRate,
                CriticalDamage: state.CriticalDamage,
                Skills: CreateSkillInstances(state.Skills));

        private static IReadOnlyList<MonsterSkillInstance> CreateSkillInstances(
            IReadOnlyList<BattleSessionSkillInstance> skills)
        {
            if (skills.Count == 0)
            {
                return [];
            }

            var instances = new List<MonsterSkillInstance>(skills.Count);
            foreach (var skill in skills)
            {
                instances.Add(new MonsterSkillInstance(
                    SkillId: skill.SkillId,
                    Level: skill.Level,
                    ManaCost: skill.ManaCost));
            }

            return instances;
        }
    }
}
