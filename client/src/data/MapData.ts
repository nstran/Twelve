export interface MapInfo {
  id: string;
  name: string;
  description: string;
  runtimeMapId: string;
  defaultRoomId: number;
  roomLabel: string;
  sceneKind: 'sideScroll' | 'legacy';
  requiredLevel: number;
  unlocked: boolean;
  type: 'historical' | 'arena' | 'special';
  x: number; // Percentage from left (0-100)
  y: number; // Percentage from top (0-100)
}

export const MAPS: MapInfo[] = [
  { id: 'hoalu', name: 'Hoa Lư', description: 'Kỳ quan đá của tỉnh Ninh Bình.', runtimeMapId: 'Hoa Lu', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'sideScroll', requiredLevel: 1, unlocked: true, type: 'historical', x: 33, y: 80 },
  { id: 'luyennguc', name: 'Luyện Ngục', description: 'Nơi rèn luyện ý chí sắt đá.', runtimeMapId: 'Luyen Nguc', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 5, unlocked: false, type: 'special', x: 9, y: 60 },
  { id: 'binhkieu', name: 'Bình Kiều', description: 'Vùng đất chiến thuật hiểm trở.', runtimeMapId: 'Binh Kieu', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 10, unlocked: false, type: 'historical', x: 5.5, y: 88 },
  { id: 'coloa', name: 'Cổ Loa', description: 'Di tích Loa Thành linh thiêng.', runtimeMapId: 'Co Loa', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 15, unlocked: false, type: 'historical', x: 54, y: 30 },
  { id: 'tamdai', name: 'Tam Đái', description: 'Ngọn núi linh thiêng của Sơn Tinh.', runtimeMapId: 'Tam Dai', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 18, unlocked: false, type: 'historical', x: 50, y: 14 },
  { id: 'tiendu', name: 'Tiên Du', description: 'Miền quan họ hữu tình.', runtimeMapId: 'Tien Du', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 20, unlocked: false, type: 'historical', x: 80.5, y: 15 },
  { id: 'thienmon', name: 'Thiên Môn', description: 'Cửa ngõ lên trời xanh.', runtimeMapId: 'Thien Mon', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 25, unlocked: false, type: 'historical', x: 87, y: 28 },
  { id: 'sieuloai', name: 'Siêu Loại', description: 'Vùng đất trù phú tài nguyên.', runtimeMapId: 'Sieu Loai', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 30, unlocked: false, type: 'historical', x: 72.5, y: 31 },
  { id: 'tegiang', name: 'Tế Giang', description: 'Dòng sông của những truyền thuyết.', runtimeMapId: 'Te Giang', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 35, unlocked: false, type: 'historical', x: 66, y: 52 },
  { id: 'dangchau', name: 'Đằng Châu', description: 'Vùng đầm lầy bí ẩn.', runtimeMapId: 'Dang Chau', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 40, unlocked: false, type: 'historical', x: 88.5, y: 55 },
  { id: 'dodonggiang', name: 'Đỗ Động Giang', description: 'Căn cứ quân sự kiên cố.', runtimeMapId: 'Do Dong Giang', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 42, unlocked: false, type: 'historical', x: 53, y: 62 },
  { id: 'kybo', name: 'Kỷ Bố', description: 'Vùng biên cương xa xôi.', runtimeMapId: 'Ky Bo', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 45, unlocked: false, type: 'historical', x: 84, y: 74 },
  { id: 'phongchau', name: 'Phong Châu', description: 'Kinh đô cổ của các vua Hùng.', runtimeMapId: 'Phong Chau', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 50, unlocked: false, type: 'historical', x: 20.5, y: 18 },
  { id: 'mauson', name: 'Mẫu Sơn', description: 'Đỉnh núi quanh năm sương phủ.', runtimeMapId: 'Mau Son', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 55, unlocked: false, type: 'historical', x: 38, y: 2 },
  { id: 'duonglam', name: 'Đường Lâm', description: 'Làng cổ hai vua.', runtimeMapId: 'Duong Lam', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 60, unlocked: false, type: 'historical', x: 17.5, y: 42 },
  { id: 'tayphuliet', name: 'Tây Phù Liệt', description: 'Gia binh tướng sĩ hùng mạnh.', runtimeMapId: 'Tay Phu Liet', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 65, unlocked: false, type: 'historical', x: 39.5, y: 39 },
  { id: 'hoiho', name: 'Hồi Hồ', description: 'Hồ nước của những sự trở lại.', runtimeMapId: 'Hoi Ho', defaultRoomId: 1, roomLabel: 'Khu 1', sceneKind: 'legacy', requiredLevel: 70, unlocked: false, type: 'historical', x: 7, y: 11 },
];
