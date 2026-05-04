using System.Collections.Generic;
using System.Threading.Tasks;
using Twelve.Application.Npcs;
using Twelve.Core;
using Twelve.Core.Interfaces;
using Twelve.Core.Npcs;
using Twelve.Core.Tlv;

namespace Twelve.Application.Handlers
{
    public sealed class MissionHandler : IPacketHandler
    {
        private const int TagMissionId = 77;

        private readonly INpcMissionCatalog _missionCatalog;
        private readonly IPlayerMissionStateRepository _missionStateRepository;
        private readonly IMissionRewardClaimService _rewardClaimService;

        public MissionHandler(
            INpcMissionCatalog missionCatalog,
            IPlayerMissionStateRepository missionStateRepository,
            IMissionRewardClaimService rewardClaimService)
        {
            _missionCatalog = missionCatalog;
            _missionStateRepository = missionStateRepository;
            _rewardClaimService = rewardClaimService;
        }

        public Task HandleAsync(GameSession session, PacketRequest request)
        {
            if (!session.IsAuthenticated)
                return Task.CompletedTask;

            if (request.Command == (int)CommandCode.MissionList)
            {
                return SendMissionListAsync(session);
            }

            if (request.Command == (int)CommandCode.MissionDetail)
            {
                var missionKey = request.GetStringTag(TagMissionId);
                if (string.IsNullOrWhiteSpace(missionKey))
                    return Task.CompletedTask;

                return SendMissionDetailAsync(session, missionKey);
            }

            if (request.Command == (int)CommandCode.MissionAccept)
            {
                var missionKey = request.GetStringTag(TagMissionId);
                if (string.IsNullOrWhiteSpace(missionKey) || string.IsNullOrWhiteSpace(session.Username))
                    return Task.CompletedTask;

                return AcceptAndSendDetailAsync(session, missionKey);
            }

            if (request.Command == (int)CommandCode.MissionCancel)
            {
                var missionKey = request.GetStringTag(TagMissionId);
                if (string.IsNullOrWhiteSpace(missionKey) || string.IsNullOrWhiteSpace(session.Username))
                    return Task.CompletedTask;

                return CancelAndSendListAsync(session, missionKey);
            }

            return Task.CompletedTask;
        }

        private async Task SendMissionListAsync(GameSession session)
        {
            var missions = _missionCatalog.GetAvailableMissions("Hoa Lu", 1);
            var statusAware = new List<MissionSummary>(missions.Count);
            foreach (var mission in missions)
            {
                var status = await _missionStateRepository.GetStatusAsync(session.Username ?? "", mission.MissionKey);
                statusAware.Add(mission with { PlayerStatus = status });
            }

            await session.SendPacketAsync(MissionPacketFactory.BuildMissionList(statusAware));
        }

        private async Task SendMissionDetailAsync(GameSession session, string missionKey)
        {
            var mission = _missionCatalog.GetMissionDetail(missionKey);
            if (mission is null)
                return;

            var status = await _missionStateRepository.GetStatusAsync(session.Username ?? "", missionKey);
            await session.SendPacketAsync(MissionPacketFactory.BuildMissionDetail(mission with { PlayerStatus = status }));
        }

        private async Task AcceptAndSendDetailAsync(GameSession session, string missionKey)
        {
            var status = await _missionStateRepository.GetStatusAsync(session.Username ?? "", missionKey);
            if (status == MissionPlayerStatus.Completed)
            {
                var mission = _missionCatalog.GetMissionDetail(missionKey);
                var claim = await _rewardClaimService.ClaimAsync(session.Username ?? "", missionKey);
                if (mission is not null && claim.Status == MissionRewardClaimStatus.Claimed)
                {
                    await session.SendPacketAsync(MissionPacketFactory.BuildRewardClaimNotification(mission, claim));
                }

                await SendMissionDetailAsync(session, missionKey);
                return;
            }

            await _missionStateRepository.AcceptAsync(session.Username ?? "", missionKey);
            await SendMissionDetailAsync(session, missionKey);
        }

        private async Task CancelAndSendListAsync(GameSession session, string missionKey)
        {
            await _missionStateRepository.CancelAsync(session.Username ?? "", missionKey);
            await SendMissionListAsync(session);
        }
    }
}
