using Twelve.Core.Battle;

namespace Twelve.Core.Interfaces
{
    public interface IBattleSessionStore
    {
        BattleSessionState? Get(string sessionId);
        void Save(BattleSessionState session);
        void Remove(string sessionId);
    }
}
