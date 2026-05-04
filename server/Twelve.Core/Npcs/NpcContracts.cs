namespace Twelve.Core.Npcs
{
    public enum NpcRosterMode : byte
    {
        Remove = 0,
        Update = 1,
        Spawn = 3
    }

    public enum NpcSharedSheetFamily : byte
    {
        Monster = 0,
        Zap = 1,
        Ice = 2
    }

    public sealed record MapNpcRosterEntry(
        string NpcId,
        string DisplayName,
        byte VisualTypeByte,
        int DisplayLevel,
        int TileX,
        int TileY,
        byte NameColorMode);
}
