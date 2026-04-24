import type { MenuItem } from '../../../components/controls/PopupMenu/PopupMenu';

export interface MapGameMenuActions {
  onOpenArena?: () => void;
  onOpenChallenge?: () => void;
  onOpenCharacterInfo?: () => void;
  onOpenPotential?: () => void;
  onOpenSkills?: () => void;
  onOpenEquipment?: () => void;
  onOpenInventory?: () => void;
  onOpenCrafting?: () => void;
  onOpenRanking?: () => void;
  onOpenWeaponShop?: () => void;
  onOpenArmorShop?: () => void;
  onOpenConsumableShop?: () => void;
  onOpenMarket?: () => void;
  onOpenTrade?: () => void;
  onOpenQuests?: () => void;
  onOpenIntro?: () => void;
  onOpenSupport?: () => void;
  onOpenChangePhone?: () => void;
  onOpenSettings?: () => void;
  onLogout: () => void | Promise<void>;
}

const run = (action?: () => void | Promise<void>) => () => {
  void action?.();
};

export const createMapGameMenuItems = (actions: MapGameMenuActions): MenuItem[] => [
  {
    id: 'loi-dai',
    label: 'Lôi Đài',
    onPress: run(actions.onOpenArena),
  },
  {
    id: 'khieu-chien',
    label: 'Khiêu Chiến',
    onPress: run(actions.onOpenChallenge),
  },
  {
    id: 'nhan-vat',
    label: 'Nhân Vật',
    children: [
      { id: 'thong-tin', label: 'Thông tin', onPress: run(actions.onOpenCharacterInfo) },
      { id: 'tiem-nang', label: 'Tiềm năng', onPress: run(actions.onOpenPotential) },
      { id: 'tuyet-chieu', label: 'Tuyệt Chiêu', onPress: run(actions.onOpenSkills) },
      { id: 'trang-bi', label: 'Trang bị', onPress: run(actions.onOpenEquipment) },
      { id: 'tui-do', label: 'Túi đồ', onPress: run(actions.onOpenInventory) },
      { id: 'che-tao', label: 'Chế tạo', onPress: run(actions.onOpenCrafting) },
      { id: 'xep-hang', label: 'Xếp hạng', onPress: run(actions.onOpenRanking) },
    ],
  },
  {
    id: 'mua-ban',
    label: 'Mua bán',
    children: [
      {
        id: 'cua-hang',
        label: 'Cửa hàng',
        children: [
          { id: 'cua-hang-vu-khi', label: 'Vũ khí', onPress: run(actions.onOpenWeaponShop) },
          { id: 'cua-hang-giap', label: 'Giáp', onPress: run(actions.onOpenArmorShop) },
          { id: 'cua-hang-tieu-hao', label: 'Tiêu hao', onPress: run(actions.onOpenConsumableShop) },
        ],
      },
      { id: 'cho-troi', label: 'Chợ trời', onPress: run(actions.onOpenMarket) },
      { id: 'giao-dich', label: 'Giao dịch', onPress: run(actions.onOpenTrade) },
    ],
  },
  {
    id: 'nhiem-vu',
    label: 'Nhiệm Vụ',
    onPress: run(actions.onOpenQuests),
  },
  {
    id: 'ho-tro',
    label: 'Hỗ trợ',
    children: [
      { id: 'gioi-thieu', label: 'Giới thiệu', onPress: run(actions.onOpenIntro) },
      { id: 'ho-tro-sub', label: 'Hỗ trợ', onPress: run(actions.onOpenSupport) },
      { id: 'doi-sdt', label: 'Đổi SĐT', onPress: run(actions.onOpenChangePhone) },
      { id: 'cai-dat', label: 'Cài đặt', onPress: run(actions.onOpenSettings) },
    ],
  },
  {
    id: 'dang-xuat',
    label: 'Đăng Xuất',
    onPress: run(actions.onLogout),
  },
];
