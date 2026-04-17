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
  90999: {
    frameWidthDivisor: 2,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 17, yOffset: 1 },
      { sourceIndex: 1, xOffset: 17, yOffset: 0 },
    ],
  },
  91099: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 17, yOffset: -4 },
      { sourceIndex: 0, xOffset: 17, yOffset: -5 },
    ],
  },
  91299: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 14, yOffset: -4 },
      { sourceIndex: 0, xOffset: 14, yOffset: -5 },
    ],
  },
  98099: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 18, yOffset: -6 },
      { sourceIndex: 0, xOffset: 18, yOffset: -7 },
    ],
  },
  98199: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 16, yOffset: 0 },
      { sourceIndex: 0, xOffset: 16, yOffset: -1 },
    ],
  },
  98299: {
    frameWidthDivisor: 1,
    frameCount: 2,
    frames: [
      { sourceIndex: 0, xOffset: 16, yOffset: 1 },
      { sourceIndex: 0, xOffset: 16, yOffset: 0 },
    ],
  },
};
