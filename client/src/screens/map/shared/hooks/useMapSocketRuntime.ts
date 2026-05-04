import { useEffect } from 'react';
import type React from 'react';
import type {
  MapMonsterRosterPacket,
  MapNpcRosterPacket,
  MapNpcRosterRecord,
  MissionDetailPacket,
  MissionListPacket,
  MissionNotificationPacket,
  MissionTaskNotificationPacket,
  MissionUpdatePacket,
  NpcTalkSocketResponse,
  SocketClient,
} from '../../../../network/SocketClient';
import {
  applyMapMonsterRuntimePacket,
  type MapMissionAction,
  type MapMonsterRosterEntry,
} from '../../core';
import type { NpcTalkDialogState } from '../components/NpcTalkDialog';

interface UseMapSocketRuntimeArgs {
  client: SocketClient;
  mapId: string;
  roomId: number;
  setMonsterRoster: React.Dispatch<React.SetStateAction<MapMonsterRosterEntry[]>>;
  setNpcRoster: React.Dispatch<React.SetStateAction<MapNpcRosterRecord[]>>;
  setNpcTalkDialog: React.Dispatch<React.SetStateAction<NpcTalkDialogState | null>>;
  dispatchMission: React.Dispatch<MapMissionAction>;
}

export const useMapSocketRuntime = ({
  client,
  mapId,
  roomId,
  setMonsterRoster,
  setNpcRoster,
  setNpcTalkDialog,
  dispatchMission,
}: UseMapSocketRuntimeArgs): void => {
  useEffect(() => {
    const request = { mapId, roomId };

    const handleRosterPacket = (packet: MapMonsterRosterPacket) => {
      if (packet.mapId !== mapId || packet.roomId !== roomId) {
        return;
      }

      setMonsterRoster((current) => applyMapMonsterRuntimePacket(current, request, packet));
    };

    const handleNpcRosterPacket = (packet: MapNpcRosterPacket) => {
      if (packet.mapId !== mapId) {
        return;
      }

      if (packet.mode === 0) {
        setNpcRoster([]);
        return;
      }

      setNpcRoster(packet.npcs);
    };

    const handleNpcTalkResponse = (response: NpcTalkSocketResponse) => {
      if (!response.ok || !response.npcId || !response.message) {
        return;
      }

      setNpcTalkDialog({ npcId: response.npcId, message: response.message });
    };

    const handleMissionList = (packet: MissionListPacket) => {
      dispatchMission({ type: 'list', missions: packet.missions });
    };
    const handleMissionDetail = (packet: MissionDetailPacket) => {
      dispatchMission({ type: 'detail', mission: packet.mission });
    };
    const handleMissionTaskNotification = (packet: MissionTaskNotificationPacket) => {
      dispatchMission({ type: 'taskNotify', task: packet.task, message: packet.message });
    };
    const handleMissionNotification = (packet: MissionNotificationPacket) => {
      dispatchMission({ type: 'notify', mission: packet.mission, message: packet.message });
    };
    const handleMissionUpdate = (packet: MissionUpdatePacket) => {
      dispatchMission({ type: 'update', mission: packet.mission });
    };

    client.on('mapMonsterRoster', handleRosterPacket);
    client.on('mapNpcRoster', handleNpcRosterPacket);
    client.on('npcTalkResponse', handleNpcTalkResponse);
    client.on('missionList', handleMissionList);
    client.on('missionDetail', handleMissionDetail);
    client.on('missionTaskNotification', handleMissionTaskNotification);
    client.on('missionNotification', handleMissionNotification);
    client.on('missionUpdate', handleMissionUpdate);
    return () => {
      client.off('mapMonsterRoster', handleRosterPacket);
      client.off('mapNpcRoster', handleNpcRosterPacket);
      client.off('npcTalkResponse', handleNpcTalkResponse);
      client.off('missionList', handleMissionList);
      client.off('missionDetail', handleMissionDetail);
      client.off('missionTaskNotification', handleMissionTaskNotification);
      client.off('missionNotification', handleMissionNotification);
      client.off('missionUpdate', handleMissionUpdate);
    };
  }, [client, dispatchMission, mapId, roomId, setMonsterRoster, setNpcRoster, setNpcTalkDialog]);
};
