export interface CharacterAppearance {
  genderIndex: number;
  faceIndex: number;
  hairIndex: number;
  hairColorIndex: number;
  skinColorIndex: number;
  elementIndex?: number;
  level?: number;
  quanHam?: string;
  xepHang?: string;
  danhVong?: number;
  thangThua?: string;
  ken?: string;
  hp?: { cur: number; max: number };
  exp?: { cur: number; max: number };
  power?: { cur: number; max: number };
  stats?: { cuongLuc: number; noiLuc: number; thanPhap: number; theLuc: number };
  points?: number;
  combat?: { attack: number; def: number; acc: number; dodge: number; hp: number; crit: string };
}
