import type {
  MapMissionRecord,
  MapMissionState,
  MapMissionTask,
} from './MapMission.types';

export type MapMissionAction =
  | { type: 'list'; missions: MapMissionRecord[] }
  | { type: 'detail'; mission: MapMissionRecord }
  | { type: 'update'; mission: MapMissionRecord }
  | { type: 'notify'; mission: MapMissionRecord; message: string }
  | { type: 'taskNotify'; task: MapMissionTask; message: string };

const mergeMission = (missions: MapMissionRecord[], incoming: MapMissionRecord): MapMissionRecord[] => {
  const index = missions.findIndex((mission) => mission.questId === incoming.questId);
  if (index < 0) {
    return [...missions, incoming];
  }

  const next = [...missions];
  next[index] = {
    ...next[index],
    ...incoming,
    title: incoming.title || next[index].title,
    description: incoming.description || next[index].description,
    tasks: incoming.tasks.length > 0 ? incoming.tasks : next[index].tasks,
    rewardLines: incoming.rewardLines.length > 0 ? incoming.rewardLines : next[index].rewardLines,
  };
  return next;
};

export const reduceMapMissionState = (
  state: MapMissionState,
  action: MapMissionAction,
): MapMissionState => {
  switch (action.type) {
    case 'list':
      return {
        ...state,
        missions: action.missions,
      };
    case 'detail':
      return {
        ...state,
        missions: mergeMission(state.missions, action.mission),
        activeMission: action.mission,
      };
    case 'update':
      return {
        ...state,
        missions: mergeMission(state.missions, action.mission),
        notifications: [...state.notifications, action.mission],
      };
    case 'notify':
      return {
        ...state,
        missions: mergeMission(state.missions, action.mission),
        notifications: [...state.notifications, action.mission],
        messages: action.message ? [...state.messages, action.message] : state.messages,
      };
    case 'taskNotify':
      return {
        ...state,
        taskNotifications: [...state.taskNotifications, action.task],
        messages: action.message ? [...state.messages, action.message] : state.messages,
      };
  }
};
