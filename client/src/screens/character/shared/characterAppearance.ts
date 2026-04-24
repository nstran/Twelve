export interface CharacterInventoryItem {
  itemId: number;
  displayName: string;
  description: string;
  quantity: number;
  stackCap: number;
  isUsable: boolean;
  healAmount: number;
  iconKind: string;
}

export interface CharacterEquipmentItem {
  equipKey: string;
  displayName: string;
  summary: string;
  slot: number;
  resourceId: number;
  level: number;
  requiredLevel: number;
  isEquipped: boolean;
  iconKind: string;
  rank: number;
  durability: number;
  maxDurability: number;
  tradeable: boolean;
  bonusCuongLuc: number;
  bonusThanPhap: number;
  bonusNoiLuc: number;
  bonusTheLuc: number;
  bonusAttack: number;
  bonusDefense: number;
  bonusDodge: number;
  bonusCrit: number;
  bonusMaxHp: number;
}

export interface CharacterSkillNode {
  familyCode: number;
  level: number;
  maxLevel: number;
  requiredLevel: number;
  cost: number;
  canUpgrade: boolean;
}

export interface CharacterAppearance {
  genderIndex: number;
  faceIndex: number;
  hairIndex: number;
  hairColorIndex: number;
  skinColorIndex: number;
  username?: string;
  elementIndex?: number;
  level?: number;
  quanHam?: string;
  xepHang?: string;
  danhVong?: number;
  thangThua?: string;
  quan?: string;
  walletQuan?: number;
  quanProgress?: { cur: number; max: number };
  hp?: { cur: number; max: number };
  exp?: { cur: number; max: number };
  expRange?: { value: number; floor: number; ceiling: number };
  power?: { cur: number; max: number };
  stats?: { cuongLuc: number; noiLuc: number; thanPhap: number; theLuc: number };
  points?: number;
  freePoints?: number;
  skillPoints?: number;
  combat?: { attack: number; def: number; acc: number; dodge: number; hp: number; crit: string };
  inventory?: CharacterInventoryItem[];
  equipment?: CharacterEquipmentItem[];
  skills?: CharacterSkillNode[];
}
