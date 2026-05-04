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

export interface MapMissionState {
  missions: MapMissionRecord[];
  activeMission: MapMissionRecord | null;
  notifications: MapMissionRecord[];
  taskNotifications: MapMissionTask[];
  messages: string[];
}

export const EMPTY_MAP_MISSION_STATE: MapMissionState = {
  missions: [],
  activeMission: null,
  notifications: [],
  taskNotifications: [],
  messages: [],
};
