export interface MapInfo {
  index: number;
  id: string;
  name: string;
  displayName: string;
  runtimeMapId: string;
  defaultRoomId: number;
  roomLabel: string;
  sceneKind: 'sideScroll' | 'legacy';
  unlocked: boolean;
  labelX: number;
  labelY: number;
  lockX: number;
  lockY: number;
  hitX: number;
  hitY: number;
  hitWidth: number;
  hitHeight: number;
  description: string;
  requiredLevel: number;
  type: 'historical' | 'arena' | 'special';
  x: number;
  y: number;
}

export interface WorldMapCatalogEntry {
  index: number;
  id: string;
  displayName: string;
  runtimeMapId: string;
  defaultRoomId: number;
  roomLabel: string;
  sceneKind: 'sideScroll' | 'legacy';
  isLocked: boolean;
  labelX: number;
  labelY: number;
  lockX: number;
  lockY: number;
  hitX: number;
  hitY: number;
  hitWidth: number;
  hitHeight: number;
}

const WORLD_MAP_WIDTH = 512;
const WORLD_MAP_HEIGHT = 512;

// Fallback mirrors server RuntimeMapCatalog.AllWorldMaps.
// Source: Java client og.java name order + oh.java j/k/i arrays.
const FALLBACK_WORLD_MAP_CATALOG: WorldMapCatalogEntry[] = [
  { index: 0, id: 'hoalu', displayName: 'Hoa Lư', runtimeMapId: 'Hoa Lu', defaultRoomId: 1, roomLabel: 'Hoa Lư', sceneKind: 'sideScroll', isLocked: false, labelX: 183, labelY: 399, lockX: 149, lockY: 363, hitX: 146, hitY: 342, hitWidth: 61, hitHeight: 48 },
  { index: 1, id: 'kybo', displayName: 'Kỷ Bố', runtimeMapId: 'Ky Bo', defaultRoomId: 1, roomLabel: 'Kỷ Bố', sceneKind: 'legacy', isLocked: true, labelX: 405, labelY: 311, lockX: 389, lockY: 345, hitX: 381, hitY: 319, hitWidth: 62, hitHeight: 48 },
  { index: 2, id: 'binhkieu', displayName: 'Bình Kiều', runtimeMapId: 'Binh Kieu', defaultRoomId: 1, roomLabel: 'Bình Kiều', sceneKind: 'legacy', isLocked: true, labelX: 26, labelY: 432, lockX: 15, lockY: 408, hitX: 9, hitY: 386, hitWidth: 64, hitHeight: 48 },
  { index: 3, id: 'dangchau', displayName: 'Đằng Châu', runtimeMapId: 'Dang Chau', defaultRoomId: 1, roomLabel: 'Đằng Châu', sceneKind: 'legacy', isLocked: true, labelX: 408, labelY: 236, lockX: 411, lockY: 266, hitX: 407, hitY: 245, hitWidth: 65, hitHeight: 47 },
  { index: 4, id: 'dodonggiang', displayName: 'Đỗ Động Giang', runtimeMapId: 'Do Dong Giang', defaultRoomId: 1, roomLabel: 'Đỗ Động Giang', sceneKind: 'legacy', isLocked: true, labelX: 206, labelY: 261, lockX: 243, lockY: 289, hitX: 236, hitY: 271, hitWidth: 64, hitHeight: 44 },
  { index: 5, id: 'tegiang', displayName: 'Tế Giang', runtimeMapId: 'Te Giang', defaultRoomId: 1, roomLabel: 'Tế Giang', sceneKind: 'legacy', isLocked: true, labelX: 304, labelY: 220, lockX: 305, lockY: 247, hitX: 300, hitY: 227, hitWidth: 63, hitHeight: 44 },
  { index: 6, id: 'sieuloai', displayName: 'Siêu Loại', runtimeMapId: 'Sieu Loai', defaultRoomId: 1, roomLabel: 'Siêu Loại', sceneKind: 'legacy', isLocked: true, labelX: 350, labelY: 184, lockX: 341, lockY: 161, hitX: 332, hitY: 142, hitWidth: 62, hitHeight: 48 },
  { index: 7, id: 'tayphuliet', displayName: 'Tây Phù Liệt', runtimeMapId: 'Tay Phu Liet', defaultRoomId: 1, roomLabel: 'Tây Phù Liệt', sceneKind: 'legacy', isLocked: true, labelX: 183, labelY: 216, lockX: 214, lockY: 192, hitX: 173, hitY: 169, hitWidth: 64, hitHeight: 48 },
  { index: 8, id: 'duonglam', displayName: 'Đường Lâm', runtimeMapId: 'Duong Lam', defaultRoomId: 1, roomLabel: 'Đường Lâm', sceneKind: 'legacy', isLocked: true, labelX: 80, labelY: 181, lockX: 77, lockY: 203, hitX: 68, hitY: 185, hitWidth: 62, hitHeight: 48 },
  { index: 9, id: 'coloa', displayName: 'Cổ Loa', runtimeMapId: 'Co Loa', defaultRoomId: 1, roomLabel: 'Cổ Loa', sceneKind: 'legacy', isLocked: true, labelX: 256, labelY: 119, lockX: 284, lockY: 150, hitX: 242, hitY: 128, hitWidth: 62, hitHeight: 48 },
  { index: 10, id: 'tiendu', displayName: 'Tiên Du', runtimeMapId: 'Tien Du', defaultRoomId: 1, roomLabel: 'Tiên Du', sceneKind: 'legacy', isLocked: true, labelX: 367, labelY: 116, lockX: 374, lockY: 90, hitX: 369, hitY: 70, hitWidth: 64, hitHeight: 44 },
  { index: 11, id: 'tamdai', displayName: 'Tam Đái', runtimeMapId: 'Tam Dai', defaultRoomId: 1, roomLabel: 'Tam Đái', sceneKind: 'legacy', isLocked: true, labelX: 229, labelY: 100, lockX: 245, lockY: 90, hitX: 229, hitY: 74, hitWidth: 63, hitHeight: 45 },
  { index: 12, id: 'phongchau', displayName: 'Phong Châu', runtimeMapId: 'Phong Chau', defaultRoomId: 1, roomLabel: 'Phong Châu', sceneKind: 'legacy', isLocked: true, labelX: 58, labelY: 119, lockX: 84, lockY: 93, hitX: 84, hitY: 73, hitWidth: 63, hitHeight: 47 },
  { index: 13, id: 'hoiho', displayName: 'Hồi Hồ', runtimeMapId: 'Hoi Ho', defaultRoomId: 1, roomLabel: 'Hồi Hồ', sceneKind: 'legacy', isLocked: true, labelX: 16, labelY: 91, lockX: 23, lockY: 65, hitX: 18, hitY: 38, hitWidth: 62, hitHeight: 48 },
  { index: 14, id: 'luyennguc', displayName: 'Luyện Ngục', runtimeMapId: 'Luyen Nguc', defaultRoomId: 1, roomLabel: 'Luyện Ngục', sceneKind: 'legacy', isLocked: true, labelX: 34, labelY: 317, lockX: 30, lockY: 297, hitX: 34, hitY: 271, hitWidth: 52, hitHeight: 46 },
  { index: 15, id: 'thienmon', displayName: 'Thiên Môn', runtimeMapId: 'Thien Mon', defaultRoomId: 1, roomLabel: 'Thiên Môn', sceneKind: 'legacy', isLocked: true, labelX: 402, labelY: 170, lockX: 414, lockY: 155, hitX: 391, hitY: 131, hitWidth: 66, hitHeight: 44 },
  { index: 16, id: 'mauson', displayName: 'Mẫu Sơn', runtimeMapId: 'Mau Son', defaultRoomId: 1, roomLabel: 'Mẫu Sơn', sceneKind: 'legacy', isLocked: true, labelX: 166, labelY: 59, lockX: 190, lockY: 39, hitX: 157, hitY: 27, hitWidth: 71, hitHeight: 26 },
];

const resolveSceneKind = (entry: WorldMapCatalogEntry): MapInfo['sceneKind'] => {
  // Source: Java client og.f()/ks.a().b("M99", go.x) only sends the
  // selected world-map index. Runtime reconstruction currently has a real
  // side-scroll scene only for Hoa Lư; older server payloads do not include
  // sceneKind, so client must derive it from the Java catalog id/index.
  if (entry.sceneKind === 'sideScroll' || entry.sceneKind === 'legacy') {
    return entry.sceneKind;
  }

  return entry.index === 0 || entry.id === 'hoalu' ? 'sideScroll' : 'legacy';
};

const resolveRuntimeMapId = (entry: WorldMapCatalogEntry): string => (
  entry.runtimeMapId?.trim() || (entry.index === 0 || entry.id === 'hoalu' ? 'Hoa Lu' : entry.displayName)
);

export const toMapInfo = (entry: WorldMapCatalogEntry): MapInfo => {
  const sceneKind = resolveSceneKind(entry);

  return {
    ...entry,
    runtimeMapId: resolveRuntimeMapId(entry),
    defaultRoomId: entry.defaultRoomId || 1,
    roomLabel: entry.roomLabel || entry.displayName,
    sceneKind,
    name: entry.displayName,
    unlocked: !entry.isLocked,
    description: '',
    requiredLevel: entry.isLocked ? entry.index + 1 : 1,
    type: entry.id === 'luyennguc' ? 'special' : 'historical',
    x: (entry.labelX / WORLD_MAP_WIDTH) * 100,
    y: (entry.labelY / WORLD_MAP_HEIGHT) * 100,
  };
};

export const toMapInfoList = (entries: WorldMapCatalogEntry[]): MapInfo[] =>
  entries.slice().sort((a, b) => a.index - b.index).map(toMapInfo);

export const MAPS: MapInfo[] = toMapInfoList(FALLBACK_WORLD_MAP_CATALOG);

export const fetchWorldMapCatalog = async (apiBaseUrl: string): Promise<MapInfo[]> => {
  const response = await fetch(`${apiBaseUrl}/map/world-catalog`);
  if (!response.ok) {
    throw new Error(`World map catalog request failed: HTTP ${response.status}`);
  }

  const entries = (await response.json()) as WorldMapCatalogEntry[];
  return toMapInfoList(entries);
};