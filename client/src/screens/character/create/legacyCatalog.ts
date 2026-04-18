import type { ImageSourcePropType } from 'react-native';

export interface LegacyFrame {
  sourceIndex: number;
  xOffset: number;
  yOffset: number;
}

export interface LegacySlotMeta {
  frameWidthDivisor: number;
  frameCount: number;
  frames: LegacyFrame[];
}

export interface LegacyImageOption {
  key: string;
  label: string;
  metaId: number;
  imageId: number;
  source: ImageSourcePropType;
  width: number;
  height: number;
}

export interface LegacyVariantStyleOption {
  key: string;
  label: string;
  metaId: number;
  baseImageId: number;
  width: number;
  height: number;
  previewImageIds: readonly number[];
}

export const PREVIEW_SLOT = 0;

export const BODY_SHEET = {
  source: require('../../../../assets/createcs_legacy/01_core_compositor/body_sheet_family_990xx_confirmed/99000.png'),
  width: 110,
  height: 53,
} as const;

export const GENDER_OPTIONS = [
  {
    key: 'male',
    label: 'Nam',
    metaId: 79899,
    imageId: 79800,
    source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79899_base_79800_candidate/images/79800.png'),
    width: 38,
    height: 23,
  },
  {
    key: 'female',
    label: 'Nữ',
    metaId: 79999,
    imageId: 79900,
    source: require('../../../../assets/createcs_legacy/01_core_compositor/gender_base_candidates/meta_79999_base_79900_candidate/images/79900.png'),
    width: 32,
    height: 23,
  },
] as const;

export const DEFAULT_OVERLAY = {
  metaId: 89999,
  imageId: 89900,
  source: require('../../../../assets/createcs_legacy/01_core_compositor/default_overlay_candidates/meta_89999_base_89900_candidate/images/89900.png'),
  width: 41,
  height: 15,
} as const;

export const ELEMENT_OPTIONS = [
  { key: 'hoa', label: 'Hỏa', value: 0 },
  { key: 'loi', label: 'Lôi', value: 1 },
  { key: 'thuy', label: 'Thủy', value: 2 },
] as const;

export const HAIR_STYLE_OPTIONS = {
  male: [
    { key: '501', label: 'Bộ 1', metaId: 50199, baseImageId: 50100, width: 33, height: 26, previewImageIds: [50100, 50103, 50105, 50107, 50108, 50109] },
    { key: '504', label: 'Bộ 2', metaId: 50499, baseImageId: 50400, width: 33, height: 31, previewImageIds: [50400, 50401, 50405, 50407, 50408, 50409] },
    { key: '505', label: 'Bộ 3', metaId: 50599, baseImageId: 50500, width: 27, height: 47, previewImageIds: [50500, 50505, 50507] },
  ],
  female: [
    { key: '500', label: 'Bộ 1', metaId: 50099, baseImageId: 50000, width: 38, height: 38, previewImageIds: [50000, 50003, 50005, 50007, 50008, 50009] },
    { key: '502', label: 'Bộ 2', metaId: 50299, baseImageId: 50200, width: 32, height: 31, previewImageIds: [50200, 50203, 50205, 50207, 50208, 50209] },
    { key: '503', label: 'Bộ 3', metaId: 50399, baseImageId: 50300, width: 40, height: 24, previewImageIds: [50300, 50303, 50307, 50308, 50309] },
  ],
} as const satisfies Record<'male' | 'female', readonly LegacyVariantStyleOption[]>;

export const EYE_STYLE_OPTIONS = {
  male: [
    { key: '605', label: 'Bộ 1', metaId: 60599, baseImageId: 60500, width: 20, height: 13 },
    { key: '606', label: 'Bộ 2', metaId: 60699, baseImageId: 60600, width: 19, height: 13 },
    { key: '607', label: 'Bộ 3', metaId: 60799, baseImageId: 60700, width: 20, height: 13 },
    { key: '603', label: 'Bộ 4', metaId: 60399, baseImageId: 60300, width: 20, height: 13 },
    { key: '604', label: 'Bộ 5', metaId: 60499, baseImageId: 60400, width: 20, height: 13 },
  ],
  female: [
    { key: '600', label: 'Bộ 1', metaId: 60099, baseImageId: 60000, width: 20, height: 13 },
    { key: '601', label: 'Bộ 2', metaId: 60199, baseImageId: 60100, width: 20, height: 12 },
    { key: '602', label: 'Bộ 3', metaId: 60299, baseImageId: 60200, width: 20, height: 12 },
    { key: '608', label: 'Bộ 4', metaId: 60899, baseImageId: 60800, width: 20, height: 12 },
    { key: '609', label: 'Bộ 5', metaId: 60999, baseImageId: 60900, width: 19, height: 11 },
  ],
} as const satisfies Record<'male' | 'female', readonly LegacyVariantStyleOption[]>;

export function buildHairColorOptions(count: number) {
  return Array.from({ length: count }, (_, index) => ({
    key: `hair-${index}`,
    label: `Tông ${index + 1}`,
    value: index,
  }));
}

export const SKIN_COLOR_OPTIONS = [
  { key: 'skin-0', label: 'Tông 1', value: 0 },
  { key: 'skin-1', label: 'Tông 2', value: 1 },
  { key: 'skin-2', label: 'Tông 3', value: 2 },
  { key: 'skin-3', label: 'Tông 4', value: 3 },
  { key: 'skin-4', label: 'Tông 5', value: 4 },
  { key: 'skin-5', label: 'Tông 6', value: 5 },
  { key: 'skin-6', label: 'Tông 7', value: 6 },
  { key: 'skin-7', label: 'Tông 8', value: 7 },
  { key: 'skin-8', label: 'Tông 9', value: 8 },
  { key: 'skin-9', label: 'Tông 10', value: 9 },
] as const;

export const SLOT_ZERO_META: Record<number, LegacySlotMeta> = {
  79899: {
    frameWidthDivisor: 2,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 22, yOffset: 28 },
      { sourceIndex: 1, xOffset: 22, yOffset: 27 },
    ],
  },
  79999: {
    frameWidthDivisor: 2,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 24, yOffset: 29 },
      { sourceIndex: 1, xOffset: 24, yOffset: 28 },
    ],
  },
  89999: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 7, yOffset: 37 },
      { sourceIndex: 0, xOffset: 7, yOffset: 36 },
    ],
  },
  50099: {
    frameWidthDivisor: 2,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 14, yOffset: -2 },
      { sourceIndex: 1, xOffset: 14, yOffset: -3 },
    ],
  },
  50199: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 13, yOffset: -3 },
      { sourceIndex: 0, xOffset: 13, yOffset: -4 },
    ],
  },
  50299: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 15, yOffset: 0 },
      { sourceIndex: 0, xOffset: 15, yOffset: -1 },
    ],
  },
  50399: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 14, yOffset: -2 },
      { sourceIndex: 0, xOffset: 14, yOffset: -3 },
    ],
  },
  50499: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 17, yOffset: -3 },
      { sourceIndex: 0, xOffset: 17, yOffset: -4 },
    ],
  },
  50599: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: -6 },
      { sourceIndex: 0, xOffset: 19, yOffset: -7 },
    ],
  },
  60099: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
      { sourceIndex: 0, xOffset: 19, yOffset: 13 },
    ],
  },
  60199: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 15 },
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
    ],
  },
  60299: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 15 },
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
    ],
  },
  60399: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
      { sourceIndex: 0, xOffset: 19, yOffset: 13 },
    ],
  },
  60499: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
      { sourceIndex: 0, xOffset: 19, yOffset: 13 },
    ],
  },
  60599: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
      { sourceIndex: 0, xOffset: 19, yOffset: 13 },
    ],
  },
  60699: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 20, yOffset: 14 },
      { sourceIndex: 0, xOffset: 20, yOffset: 13 },
    ],
  },
  60799: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
      { sourceIndex: 0, xOffset: 19, yOffset: 13 },
    ],
  },
  60899: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 19, yOffset: 15 },
      { sourceIndex: 0, xOffset: 19, yOffset: 14 },
    ],
  },
  60999: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 20, yOffset: 16 },
      { sourceIndex: 0, xOffset: 20, yOffset: 15 },
    ],
  },
};
