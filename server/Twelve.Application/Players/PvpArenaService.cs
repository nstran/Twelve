using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Twelve.Application.Battle;
using Twelve.Core.Battle;
using Twelve.Core.Entities;
using Twelve.Core.Interfaces;
using Twelve.Core.Monsters;
using Twelve.Core.Players;

namespace Twelve.Application.Players
{
    public sealed class PvpArenaService : IPvpArenaService
    {
        /// <summary>Not a row in MonsterBattles; avoids catalog lookup for PvP shadow enemies.</summary>
        private const long PvpBattleTemplateSentinel = 0;
        private const string PvpSpawnTemplateKey = "pvp_challenge";
        private static readonly TimeSpan ChallengeTtl = TimeSpan.FromSeconds(20);
        private static readonly TimeSpan ArenaPresenceTtl = TimeSpan.FromMinutes(2);

        private readonly IPlayerAggregateRepository _playerAggregateRepository;
        private readonly IBattleSessionStore _battleSessionStore;
        private readonly IBattleBoardService _battleBoardService;
        private readonly PlayerContentCatalog _contentCatalog;
        private readonly ConcurrentDictionary<string, ChallengeTicket> _challengeTickets;
        private readonly ConcurrentDictionary<string, AcceptedChallengeBootstraps> _acceptedBootstraps;
        private readonly ConcurrentDictionary<string, ArenaPresence> _arenaPresence;

        public PvpArenaService(
            IPlayerAggregateRepository playerAggregateRepository,
            IBattleSessionStore battleSessionStore,
            IBattleBoardService battleBoardService,
            PlayerContentCatalog contentCatalog)
        {
            _playerAggregateRepository = playerAggregateRepository;
            _battleSessionStore = battleSessionStore;
            _battleBoardService = battleBoardService;
            _contentCatalog = contentCatalog;
            _challengeTickets = new ConcurrentDictionary<string, ChallengeTicket>(StringComparer.OrdinalIgnoreCase);
            _acceptedBootstraps = new ConcurrentDictionary<string, AcceptedChallengeBootstraps>(StringComparer.OrdinalIgnoreCase);
            _arenaPresence = new ConcurrentDictionary<string, ArenaPresence>(StringComparer.OrdinalIgnoreCase);
        }

        public async Task<PvpArenaPresenceResponse?> EnterArenaAsync(PvpArenaPresenceRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Username))
            {
                return null;
            }

            var player = await _playerAggregateRepository.GetByUsernameAsync(request.Username.Trim());
            if (player is null)
            {
                return null;
            }

            CleanupExpiredArenaPresence();
            _arenaPresence[player.Core.Username] = new ArenaPresence(player.Core.Username, DateTimeOffset.UtcNow.Add(ArenaPresenceTtl));
            return new PvpArenaPresenceResponse(player.Core.Username, true);
        }

        public async Task<PvpArenaPresenceResponse?> LeaveArenaAsync(PvpArenaPresenceRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Username))
            {
                return null;
            }

            var player = await _playerAggregateRepository.GetByUsernameAsync(request.Username.Trim());
            if (player is null)
            {
                return null;
            }

            _arenaPresence.TryRemove(player.Core.Username, out _);
            return new PvpArenaPresenceResponse(player.Core.Username, false);
        }

        public async Task<PvpOpponentListResponse?> ListOpponentsAsync(PvpOpponentListRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Username))
            {
                return null;
            }

            var current = await _playerAggregateRepository.GetByUsernameAsync(request.Username);
            if (current is null)
            {
                return null;
            }

            CleanupExpiredArenaPresence();
            RefreshArenaPresence(current.Core.Username);

            var allPlayers = await _playerAggregateRepository.ListAsync();
            var opponents = allPlayers
                .Where(candidate => candidate.Core.Id != current.Core.Id)
                // Opening the map alone does not make a player attackable; only
                // explicit Lôi Đài entrants are listed here.
                .Where(candidate => IsInArena(candidate.Core.Username))
                // Arena list only shows players currently in the same runtime room.
                .Where(candidate => IsSameRuntimeRoom(current, candidate))
                // Hide players already locked in another battle session.
                .Where(candidate => string.IsNullOrWhiteSpace(candidate.WorldState.ActiveBattleSessionId))
                .OrderBy(candidate => Math.Abs(candidate.Core.Level - current.Core.Level))
                .ThenByDescending(candidate => candidate.Core.Honor)
                .ThenBy(candidate => candidate.Core.Username, StringComparer.OrdinalIgnoreCase)
                .Take(24)
                .Select(BuildOpponentEntry)
                .ToArray();

            return new PvpOpponentListResponse(current.Core.Username, opponents);
        }

        public async Task<MonsterBattleBootstrapResponse?> BootstrapAsync(PvpBattleBootstrapRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Username) ||
                string.IsNullOrWhiteSpace(request.TargetUsername))
            {
                return null;
            }

            var player = await _playerAggregateRepository.GetByUsernameAsync(request.Username.Trim());
            var enemyPlayer = await _playerAggregateRepository.GetByUsernameAsync(request.TargetUsername.Trim());
            if (player is null || enemyPlayer is null || player.Core.Id == enemyPlayer.Core.Id)
            {
                return null;
            }

            // PvP can only start when both players are in the same map room
            // and neither side is already bound to another active battle session.
            if (!CanStartPvpNow(player, enemyPlayer))
            {
                return null;
            }

            return CreateBootstrap(player, enemyPlayer, request.InitialTurnSide, Math.Max(0, request.Stake), request.AllowSpectators, request.OneWay, request.DisableSpecialSkills);
        }

        public async Task<PvpChallengeTicketResponse?> CreateChallengeAsync(PvpChallengeCreateRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Username) || string.IsNullOrWhiteSpace(request.TargetUsername))
            {
                return null;
            }

            var challenger = await _playerAggregateRepository.GetByUsernameAsync(request.Username.Trim());
            var target = await _playerAggregateRepository.GetByUsernameAsync(request.TargetUsername.Trim());
            if (challenger is null || target is null || challenger.Core.Id == target.Core.Id)
            {
                return null;
            }

            if (!CanStartPvpNow(challenger, target))
            {
                return null;
            }

            CleanupExpiredTickets();
            if (HasPendingTicket(challenger.Core.Username, target.Core.Username))
            {
                return null;
            }

            var now = DateTimeOffset.UtcNow;
            var ticket = new ChallengeTicket(
                TicketId: Guid.NewGuid().ToString("N"),
                ChallengerUsername: challenger.Core.Username,
                TargetUsername: target.Core.Username,
                Stake: Math.Max(0, request.Stake),
                State: "Pending",
                CreatedAt: now,
                ExpiresAt: now.Add(ChallengeTtl));

            _challengeTickets[ticket.TicketId] = ticket;
            return ToResponse(ticket);
        }

        public async Task<PvpChallengeInboxResponse?> ListChallengesAsync(string username)
        {
            if (string.IsNullOrWhiteSpace(username))
            {
                return null;
            }

            var player = await _playerAggregateRepository.GetByUsernameAsync(username.Trim());
            if (player is null)
            {
                return null;
            }

            CleanupExpiredTickets();
            var incoming = new List<PvpChallengeTicketResponse>();
            var outgoing = new List<PvpChallengeTicketResponse>();
            foreach (var ticket in _challengeTickets.Values.OrderByDescending(ticket => ticket.CreatedAt))
            {
                if (!string.Equals(ticket.State, "Pending", StringComparison.OrdinalIgnoreCase) &&
                    !string.Equals(ticket.State, "Accepted", StringComparison.OrdinalIgnoreCase))
                {
                    continue;
                }

                if (string.Equals(ticket.TargetUsername, player.Core.Username, StringComparison.OrdinalIgnoreCase))
                {
                    incoming.Add(ToResponse(ticket));
                }
                else if (string.Equals(ticket.ChallengerUsername, player.Core.Username, StringComparison.OrdinalIgnoreCase))
                {
                    outgoing.Add(ToResponse(ticket));
                }
            }

            return new PvpChallengeInboxResponse(player.Core.Username, incoming.Take(8).ToArray(), outgoing.Take(8).ToArray());
        }

        public Task<PvpChallengeStatusResponse?> GetChallengeStatusAsync(string ticketId, string username)
        {
            if (string.IsNullOrWhiteSpace(ticketId) || string.IsNullOrWhiteSpace(username))
            {
                return Task.FromResult<PvpChallengeStatusResponse?>(null);
            }

            CleanupExpiredTickets();
            if (!_challengeTickets.TryGetValue(ticketId, out var ticket) || !IsTicketParticipant(ticket, username.Trim()))
            {
                return Task.FromResult<PvpChallengeStatusResponse?>(null);
            }

            MonsterBattleBootstrapResponse? bootstrap = null;
            if (string.Equals(ticket.State, "Accepted", StringComparison.OrdinalIgnoreCase) &&
                _acceptedBootstraps.TryGetValue(ticketId, out var accepted))
            {
                bootstrap = string.Equals(ticket.ChallengerUsername, username.Trim(), StringComparison.OrdinalIgnoreCase)
                    ? accepted.ChallengerBootstrap
                    : accepted.TargetBootstrap;
            }

            return Task.FromResult<PvpChallengeStatusResponse?>(new PvpChallengeStatusResponse(ToResponse(ticket), bootstrap));
        }

        public async Task<PvpChallengeAcceptResponse?> AcceptChallengeAsync(string ticketId, PvpChallengeActionRequest request)
        {
            if (string.IsNullOrWhiteSpace(ticketId) || string.IsNullOrWhiteSpace(request.Username))
            {
                return null;
            }

            CleanupExpiredTickets();
            if (!_challengeTickets.TryGetValue(ticketId, out var ticket) ||
                !string.Equals(ticket.TargetUsername, request.Username.Trim(), StringComparison.OrdinalIgnoreCase) ||
                !string.Equals(ticket.State, "Pending", StringComparison.OrdinalIgnoreCase))
            {
                return null;
            }

            var challenger = await _playerAggregateRepository.GetByUsernameAsync(ticket.ChallengerUsername);
            var target = await _playerAggregateRepository.GetByUsernameAsync(ticket.TargetUsername);
            if (challenger is null || target is null)
            {
                return null;
            }

            if (!CanStartPvpNow(challenger, target))
            {
                return null;
            }

            var accepted = ticket with { State = "Accepted" };
            _challengeTickets[ticketId] = accepted;

            var sharedBoard = _battleBoardService.CreateInitialBoard();
            var sharedPairId = Guid.NewGuid().ToString("N");
            var challengerSessionId = $"{sharedPairId}:a";
            var targetSessionId = $"{sharedPairId}:b";

            var challengerBootstrap = CreateBootstrap(
                challenger,
                target,
                BattleSide.Player,
                accepted.Stake,
                allowSpectators: true,
                oneWay: false,
                disableSpecialSkills: false,
                sessionId: challengerSessionId,
                linkedSessionId: targetSessionId,
                isPerspectiveReversed: false,
                initialBoard: sharedBoard);
            var targetBootstrap = CreateBootstrap(
                target,
                challenger,
                BattleSide.Enemy,
                accepted.Stake,
                allowSpectators: true,
                oneWay: false,
                disableSpecialSkills: false,
                sessionId: targetSessionId,
                linkedSessionId: challengerSessionId,
                isPerspectiveReversed: true,
                initialBoard: sharedBoard);

            if (challengerBootstrap is null || targetBootstrap is null)
            {
                return null;
            }

            _acceptedBootstraps[ticketId] = new AcceptedChallengeBootstraps(challengerBootstrap, targetBootstrap);

            return new PvpChallengeAcceptResponse(ToResponse(accepted), targetBootstrap);
        }

        public Task<PvpChallengeTicketResponse?> DeclineChallengeAsync(string ticketId, PvpChallengeActionRequest request)
        {
            return Task.FromResult(UpdateTicketState(ticketId, request.Username, expectedTarget: true, nextState: "Declined"));
        }

        public Task<PvpChallengeTicketResponse?> CancelChallengeAsync(string ticketId, PvpChallengeActionRequest request)
        {
            return Task.FromResult(UpdateTicketState(ticketId, request.Username, expectedTarget: false, nextState: "Cancelled"));
        }

        private PvpOpponentEntry BuildOpponentEntry(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
            var maxHp = Math.Max(1, player.MaxHp);
            var currentHp = Math.Clamp(player.Hp, 0, maxHp);
            var statusByte = currentHp > 0 ? (byte)0 : (byte)1;

            return new PvpOpponentEntry(
                Username: player.Username,
                Level: player.Level,
                StatusByte: statusByte,
                Honor: player.Honor,
                StatusMessage: statusByte == 0 ? "San sang" : "Hoi phuc",
                Stake: 0,
                Element: player.Element ?? 0,
                CurrentHp: currentHp,
                MaxHp: maxHp,
                Appearance: BuildAppearance(aggregate));
        }

        private PvpCharacterAppearance BuildAppearance(PlayerAggregate aggregate)
        {
            var player = aggregate.Core;
            var maxHp = Math.Max(1, player.MaxHp);
            var currentHp = Math.Clamp(player.Hp, 0, maxHp);

            return new PvpCharacterAppearance(
                GenderIndex: Math.Max(0, player.Gender),
                FaceIndex: Math.Max(0, player.FaceStyle ?? 0),
                HairIndex: Math.Max(0, player.HairStyle ?? 0),
                HairColorIndex: Math.Max(0, player.HairColor ?? 0),
                SkinColorIndex: Math.Max(0, player.SkinColor ?? 0),
                Username: player.Username,
                ElementIndex: player.Element ?? 0,
                Level: player.Level,
                DanhVong: player.Honor,
                Hp: new PlayerRuntimeBar(currentHp, maxHp),
                Equipment: aggregate.Equipment
                    .Select(_contentCatalog.ToEquipmentView)
                    .ToArray());
        }

        private MonsterBattleBootstrapResponse? CreateBootstrap(
            PlayerAggregate player,
            PlayerAggregate enemyPlayer,
            BattleSide initialTurnSide,
            long stake,
            bool allowSpectators,
            bool oneWay,
            bool disableSpecialSkills,
            string? sessionId = null,
            string? linkedSessionId = null,
            bool isPerspectiveReversed = false,
            IReadOnlyList<IReadOnlyList<int?>>? initialBoard = null)
        {
            sessionId ??= Guid.NewGuid().ToString("N");
            initialBoard ??= _battleBoardService.CreateInitialBoard();
            var playerState = PlayerBattleStateFactory.Create(player, BattleSide.Player);
            var enemyState = PlayerBattleStateFactory.Create(
                enemyPlayer,
                BattleSide.Enemy,
                iqValue: Math.Max(7, Math.Min(14, 7 + enemyPlayer.Core.Level / 4 + enemyPlayer.Core.Honor / 250)),
                aiProfileId: "standard");

            var monsterKey = $"pvp:{enemyPlayer.Core.Username}";
            _battleSessionStore.Save(new BattleSessionState(
                SessionId: sessionId,
                MonsterKey: monsterKey,
                ActiveTurn: initialTurnSide,
                Board: initialBoard,
                Player: playerState,
                Enemy: enemyState,
                CreatedAtUtc: DateTime.UtcNow,
                SpawnTemplateKey: PvpSpawnTemplateKey,
                BattleTemplateId: PvpBattleTemplateSentinel,
                Kind: BattleSessionKind.PvpShadow,
                Stake: Math.Max(0, stake),
                AllowSpectators: allowSpectators,
                OneWay: oneWay,
                DisableSpecialSkills: disableSpecialSkills,
                LinkedSessionId: linkedSessionId,
                IsPerspectiveReversed: isPerspectiveReversed));

            var enemy = new MonsterBattleInstance(
                CombatantId: enemyState.CombatantId,
                MonsterKey: monsterKey,
                BattleTemplateId: PvpBattleTemplateSentinel,
                DisplayName: enemyState.DisplayName,
                Element: (byte)Math.Max(0, enemyPlayer.Core.Element ?? 0),
                Level: enemyState.Level,
                CurrentHp: enemyState.CurrentHp,
                MaxHp: enemyState.MaxHp,
                CurrentMp: enemyState.CurrentMp,
                MaxMp: enemyState.MaxMp,
                CurrentPower: enemyState.CurrentPower,
                MaxPower: enemyState.MaxPower,
                Strength: enemyState.Strength,
                Agility: enemyState.Agility,
                Magic: enemyState.Magic,
                Vitality: enemyState.Vitality,
                MinDamage: enemyState.MinDamage,
                MaxDamage: enemyState.MaxDamage,
                Defense: enemyState.Defense,
                HitRate: enemyState.HitRate,
                DodgeRate: enemyState.DodgeRate,
                CriticalRate: enemyState.CriticalRate,
                Skills: PlayerBattleStateFactory.CreateSkillInstances(enemyState.Skills),
                Appearance: new MonsterAppearanceTemplate(),
                // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
                // PvP enemy shadow must expose the same server-owned resource gain coefficients
                // as its BattleSessionCombatantState; FE only applies bootstrap coefficients.
                HealGainPercent: enemyState.HealGainPercent,
                ManaGainPercent: enemyState.ManaGainPercent,
                PowerGainPercent: enemyState.PowerGainPercent);

            return new MonsterBattleBootstrapResponse(
                SessionId: sessionId,
                MonsterKey: monsterKey,
                SpawnTemplateKey: PvpSpawnTemplateKey,
                BattleTemplateId: PvpBattleTemplateSentinel,
                VisualTypeByte: 0,
                DisplayLevel: enemyPlayer.Core.Level,
                IqValue: enemyState.IqValue,
                NameColorMode: 0,
                InitialTurnSide: initialTurnSide,
                SharedSheetFamily: null,
                InitialBoard: initialBoard,
                Player: PlayerBattleStateFactory.CreateSnapshot(playerState),
                Enemy: enemy,
                // Source: docs/player-character-reconstruction/08-level-stat-exp-and-element-balance.md §5.
                // PvP uses the same server-owned remake v1 resource table as PvE so FE does not hardcode HP/MP/Power formulas.
                GemResourceConfig: BattleGemResourceConfig.CreateRemakeV1(),
                BattleKind: "pvp",
                EnemyPlayerAppearance: BuildAppearance(enemyPlayer));
        }

        private PvpChallengeTicketResponse? UpdateTicketState(string ticketId, string username, bool expectedTarget, string nextState)
        {
            if (string.IsNullOrWhiteSpace(ticketId) || string.IsNullOrWhiteSpace(username))
            {
                return null;
            }

            CleanupExpiredTickets();
            if (!_challengeTickets.TryGetValue(ticketId, out var ticket) ||
                !string.Equals(ticket.State, "Pending", StringComparison.OrdinalIgnoreCase))
            {
                return null;
            }

            var actorName = username.Trim();
            var actorOk = expectedTarget
                ? string.Equals(ticket.TargetUsername, actorName, StringComparison.OrdinalIgnoreCase)
                : string.Equals(ticket.ChallengerUsername, actorName, StringComparison.OrdinalIgnoreCase);

            if (!actorOk)
            {
                return null;
            }

            var updated = ticket with { State = nextState };
            _challengeTickets[ticketId] = updated;
            return ToResponse(updated);
        }

        private void CleanupExpiredTickets()
        {
            var now = DateTimeOffset.UtcNow;
            foreach (var entry in _challengeTickets.ToArray())
            {
                if (entry.Value.ExpiresAt > now || !string.Equals(entry.Value.State, "Pending", StringComparison.OrdinalIgnoreCase))
                {
                    continue;
                }

                _challengeTickets[entry.Key] = entry.Value with { State = "Expired" };
            }
        }

        private void CleanupExpiredArenaPresence()
        {
            var now = DateTimeOffset.UtcNow;
            foreach (var entry in _arenaPresence.ToArray())
            {
                if (entry.Value.ExpiresAt <= now)
                {
                    _arenaPresence.TryRemove(entry.Key, out _);
                }
            }
        }

        private void RefreshArenaPresence(string username)
        {
            if (_arenaPresence.TryGetValue(username, out _))
            {
                _arenaPresence[username] = new ArenaPresence(username, DateTimeOffset.UtcNow.Add(ArenaPresenceTtl));
            }
        }

        private bool IsInArena(string username)
        {
            return _arenaPresence.TryGetValue(username, out var presence) && presence.ExpiresAt > DateTimeOffset.UtcNow;
        }

        private bool HasPendingTicket(string challengerUsername, string targetUsername)
        {
            foreach (var ticket in _challengeTickets.Values)
            {
                if (!string.Equals(ticket.State, "Pending", StringComparison.OrdinalIgnoreCase))
                {
                    continue;
                }

                var direct = string.Equals(ticket.ChallengerUsername, challengerUsername, StringComparison.OrdinalIgnoreCase) &&
                             string.Equals(ticket.TargetUsername, targetUsername, StringComparison.OrdinalIgnoreCase);
                var reverse = string.Equals(ticket.ChallengerUsername, targetUsername, StringComparison.OrdinalIgnoreCase) &&
                              string.Equals(ticket.TargetUsername, challengerUsername, StringComparison.OrdinalIgnoreCase);
                if (direct || reverse)
                {
                    return true;
                }
            }

            return false;
        }

        private static PvpChallengeTicketResponse ToResponse(ChallengeTicket ticket)
        {
            return new PvpChallengeTicketResponse(
                TicketId: ticket.TicketId,
                ChallengerUsername: ticket.ChallengerUsername,
                TargetUsername: ticket.TargetUsername,
                Stake: ticket.Stake,
                State: ticket.State,
                CreatedAtUnixMs: ticket.CreatedAt.ToUnixTimeMilliseconds(),
                ExpiresAtUnixMs: ticket.ExpiresAt.ToUnixTimeMilliseconds());
        }

        private static bool IsTicketParticipant(ChallengeTicket ticket, string username)
        {
            return string.Equals(ticket.ChallengerUsername, username, StringComparison.OrdinalIgnoreCase) ||
                   string.Equals(ticket.TargetUsername, username, StringComparison.OrdinalIgnoreCase);
        }

        private static bool CanStartPvpNow(PlayerAggregate player, PlayerAggregate enemy)
        {
            if (!IsSameRuntimeRoom(player, enemy))
            {
                return false;
            }

            if (!string.IsNullOrWhiteSpace(player.WorldState.ActiveBattleSessionId) ||
                !string.IsNullOrWhiteSpace(enemy.WorldState.ActiveBattleSessionId))
            {
                return false;
            }

            var playerHpOk = player.Core.Hp > 0;
            var enemyHpOk = enemy.Core.Hp > 0;
            return playerHpOk && enemyHpOk;
        }

        private static bool IsSameRuntimeRoom(PlayerAggregate player, PlayerAggregate enemy)
        {
            var playerMapId = player.WorldState.MapId?.Trim() ?? string.Empty;
            var enemyMapId = enemy.WorldState.MapId?.Trim() ?? string.Empty;
            if (!string.Equals(playerMapId, enemyMapId, StringComparison.OrdinalIgnoreCase))
            {
                return false;
            }

            return player.WorldState.RoomId == enemy.WorldState.RoomId;
        }

        private sealed record ChallengeTicket(
            string TicketId,
            string ChallengerUsername,
            string TargetUsername,
            long Stake,
            string State,
            DateTimeOffset CreatedAt,
            DateTimeOffset ExpiresAt);

        private sealed record ArenaPresence(
            string Username,
            DateTimeOffset ExpiresAt);

        private sealed record AcceptedChallengeBootstraps(
            MonsterBattleBootstrapResponse ChallengerBootstrap,
            MonsterBattleBootstrapResponse TargetBootstrap);
    }
}
