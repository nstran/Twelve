using System.Threading.Tasks;
using Twelve.Core.Npcs;

namespace Twelve.Core.Interfaces
{
    public interface IPlayerMissionStateRepository
    {
        Task<MissionPlayerStatus> GetStatusAsync(string username, string missionKey);

        Task AcceptAsync(string username, string missionKey);

        Task CancelAsync(string username, string missionKey);

        Task<bool> TryBeginRewardClaimAsync(string username, string missionKey);

        Task CompleteRewardClaimAsync(string username, string missionKey);

        Task RestoreCompletedAsync(string username, string missionKey);

        Task<IReadOnlyList<MissionProgressUpdate>> AddProgressAsync(string username, string objectiveType, string targetKey, int amount);
    }
}
