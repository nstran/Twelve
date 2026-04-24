using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Core.Players;

namespace Twelve.Core.Interfaces
{
    public interface IEquipmentCatalogRepository
    {
        Task<IReadOnlyList<PlayerEquipmentDefinition>> GetAllAsync();
    }
}
