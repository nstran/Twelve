import type {
  MapMissionProgressUpdate,
  MapMissionRecord,
  MapMissionState,
  MapMissionTask,
  MapMissionToast,
} from './MapMission.types';

export type MapMissionAction =
  | { type: 'list'; missions: MapMissionRecord[] }
  | { type: 'detail'; mission: MapMissionRecord }
  | { type: 'update'; mission: MapMissionRecord }
  | { type: 'notify'; mission: MapMissionRecord; message: string }
  | { type: 'taskNotify'; task: MapMissionTask; message: string }
  | { type: 'battleProgress'; updates: MapMissionProgressUpdate[] };

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

const nextToastId = (state: MapMissionState): number => state.toasts.length + state.messages.length + 1;

const trimToasts = (toasts: MapMissionToast[]): MapMissionToast[] => toasts.slice(-3);

const buildTaskToast = (state: MapMissionState, task: MapMissionTask, message: string): MapMissionToast => ({
  id: nextToastId(state),
  title: 'Nhiem vu',
  message: message || 'Tien do nhiem vu da cap nhat.',
  lines: [task.text].filter(Boolean),
  kind: 'task',
});

const buildMissionToast = (state: MapMissionState, mission: MapMissionRecord, message: string): MapMissionToast => ({
  id: nextToastId(state),
  title: mission.title || 'Nhiem vu',
  message: message || 'Nhiem vu da cap nhat.',
  lines: mission.rewardLines.length > 0 ? mission.rewardLines : mission.tasks.map(task => task.text),
  kind: mission.rewardLines.length > 0 ? 'reward' : 'mission',
});

const buildProgressToast = (state: MapMissionState, update: MapMissionProgressUpdate, index: number): MapMissionToast => ({
  id: nextToastId(state) + index,
  title: update.missionTitle || 'Nhiem vu',
  message: update.missionCompleted ? 'Nhiem vu da hoan thanh.' : 'Tien do nhiem vu da cap nhat.',
  lines: [update.objectiveText],
  kind: update.missionCompleted ? 'mission' : 'task',
});

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
        toasts: trimToasts([...state.toasts, buildMissionToast(state, action.mission, 'Tien do nhiem vu da cap nhat.')]),
      };
    case 'notify':
      return {
        ...state,
        missions: mergeMission(state.missions, action.mission),
        notifications: [...state.notifications, action.mission],
        messages: action.message ? [...state.messages, action.message] : state.messages,
        toasts: trimToasts([...state.toasts, buildMissionToast(state, action.mission, action.message)]),
      };
    case 'taskNotify':
      return {
        ...state,
        taskNotifications: [...state.taskNotifications, action.task],
        messages: action.message ? [...state.messages, action.message] : state.messages,
        toasts: trimToasts([...state.toasts, buildTaskToast(state, action.task, action.message)]),
      };
    case 'battleProgress':
      return {
        ...state,
        messages: action.updates.length > 0
          ? [...state.messages, action.updates[action.updates.length - 1].missionCompleted ? 'Nhiem vu da hoan thanh.' : 'Tien do nhiem vu da cap nhat.']
          : state.messages,
        toasts: trimToasts([
          ...state.toasts,
          ...action.updates.map((update, index) => buildProgressToast(state, update, index)),
        ]),
      };
  }
};
