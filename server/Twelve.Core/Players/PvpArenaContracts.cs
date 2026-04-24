using System.Collections.Generic;
using Twelve.Core.Battle;
using Twelve.Core.Monsters;

namespace Twelve.Core.Players
{
    public sealed record PlayerRuntimeBar(
        int Cur,
        int Max
    );

    public sealed record PvpCharacterAppearance(
        int GenderIndex,
        int FaceIndex,
        int HairIndex,
        int HairColorIndex,
        int SkinColorIndex,
        string Username,
        int ElementIndex,
        int Level,
        int DanhVong,
        PlayerRuntimeBar Hp,
        IReadOnlyList<PlayerEquipmentItemView> Equipment
    );

    public sealed record PvpOpponentEntry(
        string Username,
        int Level,
        byte StatusByte,
        int Honor,
        string StatusMessage,
        long Stake,
        int Element,
        int CurrentHp,
        int MaxHp,
        PvpCharacterAppearance Appearance
    );

    public sealed record PvpOpponentListResponse(
        string Username,
        IReadOnlyList<PvpOpponentEntry> Opponents
    );

    public sealed record PvpOpponentListRequest(
        string Username
    );

    public sealed record PvpArenaPresenceRequest(
        string Username
    );

    public sealed record PvpArenaPresenceResponse(
        string Username,
        bool IsPresent
    );

    public sealed record PvpBattleBootstrapRequest(
        string Username,
        string TargetUsername,
        BattleSide InitialTurnSide = BattleSide.Player,
        long Stake = 0,
        bool AllowSpectators = true,
        bool OneWay = false,
        bool DisableSpecialSkills = false
    );

    public sealed record PvpChallengeCreateRequest(
        string Username,
        string TargetUsername,
        long Stake = 0
    );

    public sealed record PvpChallengeActionRequest(
        string Username
    );

    public sealed record PvpChallengeStatusRequest(
        string Username
    );

    public sealed record PvpChallengeTicketResponse(
        string TicketId,
        string ChallengerUsername,
        string TargetUsername,
        long Stake,
        string State,
        long CreatedAtUnixMs,
        long ExpiresAtUnixMs
    );

    public sealed record PvpChallengeAcceptResponse(
        PvpChallengeTicketResponse Ticket,
        MonsterBattleBootstrapResponse Bootstrap
    );

    public sealed record PvpChallengeInboxResponse(
        string Username,
        IReadOnlyList<PvpChallengeTicketResponse> Incoming,
        IReadOnlyList<PvpChallengeTicketResponse> Outgoing
    );

    public sealed record PvpChallengeStatusResponse(
        PvpChallengeTicketResponse Ticket,
        MonsterBattleBootstrapResponse? Bootstrap
    );
}
