using System.Threading.Tasks;
using Twelve.Core.Monsters;
using Twelve.Core.Players;

namespace Twelve.Core.Interfaces
{
    public interface IPvpArenaService
    {
        Task<PvpArenaPresenceResponse?> EnterArenaAsync(PvpArenaPresenceRequest request);
        Task<PvpArenaPresenceResponse?> LeaveArenaAsync(PvpArenaPresenceRequest request);
        Task<PvpOpponentListResponse?> ListOpponentsAsync(PvpOpponentListRequest request);
        Task<MonsterBattleBootstrapResponse?> BootstrapAsync(PvpBattleBootstrapRequest request);
        Task<PvpChallengeTicketResponse?> CreateChallengeAsync(PvpChallengeCreateRequest request);
        Task<PvpChallengeInboxResponse?> ListChallengesAsync(string username);
        Task<PvpChallengeStatusResponse?> GetChallengeStatusAsync(string ticketId, string username);
        Task<PvpChallengeAcceptResponse?> AcceptChallengeAsync(string ticketId, PvpChallengeActionRequest request);
        Task<PvpChallengeTicketResponse?> DeclineChallengeAsync(string ticketId, PvpChallengeActionRequest request);
        Task<PvpChallengeTicketResponse?> CancelChallengeAsync(string ticketId, PvpChallengeActionRequest request);
    }
}
