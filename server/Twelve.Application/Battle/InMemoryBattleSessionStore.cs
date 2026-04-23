using System.Collections.Concurrent;
using Twelve.Core.Battle;
using Twelve.Core.Interfaces;

namespace Twelve.Application.Battle
{
    public sealed class InMemoryBattleSessionStore : IBattleSessionStore
    {
        private readonly ConcurrentDictionary<string, BattleSessionState> _sessions =
            new(System.StringComparer.OrdinalIgnoreCase);

        public BattleSessionState? Get(string sessionId) =>
            _sessions.TryGetValue(sessionId, out var session) ? session : null;

        public void Save(BattleSessionState session) =>
            _sessions[session.SessionId] = session;

        public void Remove(string sessionId) =>
            _sessions.TryRemove(sessionId, out _);
    }
}
