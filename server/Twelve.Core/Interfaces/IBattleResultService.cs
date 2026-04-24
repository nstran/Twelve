using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleResultService
    {
        BattleResultRewardResponse? Claim(BattleResultClaimRequest request);
    }
}
