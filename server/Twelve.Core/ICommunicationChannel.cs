using System;
using System.Threading.Tasks;

namespace Twelve.Core
{
    public interface ICommunicationChannel : IDisposable
    {
        Task SendAsync(byte[] data);
        void Close();
    }
}
