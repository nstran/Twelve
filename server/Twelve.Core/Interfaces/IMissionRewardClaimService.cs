using System.Threading.Tasks;
using Twelve.Core.Npcs;

namespace Twelve.Core.Interfaces
{
    public interface IMissionRewardClaimService
    {
        Task<MissionRewardClaimResult> ClaimAsync(string username, string missionKey);
    }
}
