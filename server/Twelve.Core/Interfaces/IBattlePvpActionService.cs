using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattlePvpActionService
    {
        BattlePvpActionResponse? Submit(BattlePvpActionRequest request);
    }
}