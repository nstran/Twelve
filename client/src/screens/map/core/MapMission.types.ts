export interface MapMissionTask {
  rawValue: number;
  questId: string;
  text: string;
}

export interface MapMissionRecord {
  questId: string;
  title: string;
  description: string;
  price: number;
  statusFlag: boolean;
  tasks: MapMissionTask[];
  rewardLines: string[];
}

export interface MapMissionToast {
  id: number;
  title: string;
  message: string;
  lines: string[];
  kind: 'task' | 'mission' | 'reward';
}

export interface MapMissionProgressUpdate {
  missionKey: string;
  missionTitle: string;
  objectiveText: string;
  objectiveCompleted: boolean;
  missionCompleted: boolean;
}

export interface MapMissionState {
  missions: MapMissionRecord[];
  activeMission: MapMissionRecord | null;
  notifications: MapMissionRecord[];
  taskNotifications: MapMissionTask[];
  messages: string[];
  toasts: MapMissionToast[];
}

export const EMPTY_MAP_MISSION_STATE: MapMissionState = {
  missions: [],
  activeMission: null,
  notifications: [],
  taskNotifications: [],
  messages: [],
  toasts: [],
};
