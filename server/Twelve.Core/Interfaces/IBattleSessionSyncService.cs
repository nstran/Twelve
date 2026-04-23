using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleSessionSyncService
    {
        bool Sync(BattleSessionSyncRequest request);
    }
}
