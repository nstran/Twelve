export interface FrameTrim {
  cropX: number;
  cropY: number;
  cropWidth: number;
  cropHeight: number;
}

export const ASSET_FRAME_TRIM: Record<number, Partial<Record<number, FrameTrim>>> = {
  50000: {
    1: { cropX: 0, cropY: 0, cropWidth: 37, cropHeight: 38 },
  },
  50001: {
    1: { cropX: 0, cropY: 0, cropWidth: 59, cropHeight: 33 },
    2: { cropX: 0, cropY: 0, cropWidth: 58, cropHeight: 32 },
  },
  50002: {
    3: { cropX: 0, cropY: 0, cropWidth: 45, cropHeight: 31 },
  },
  50102: {
    1: { cropX: 0, cropY: 0, cropWidth: 35, cropHeight: 29 },
    2: { cropX: 0, cropY: 0, cropWidth: 38, cropHeight: 29 },
    3: { cropX: 0, cropY: 0, cropWidth: 33, cropHeight: 26 },
  },
  50201: {
    2: { cropX: 0, cropY: 0, cropWidth: 35, cropHeight: 25 },
  },
  50202: {
    1: { cropX: 0, cropY: 0, cropWidth: 30, cropHeight: 23 },
    2: { cropX: 0, cropY: 0, cropWidth: 30, cropHeight: 25 },
    3: { cropX: 1, cropY: 0, cropWidth: 29, cropHeight: 27 },
  },
  50302: {
    1: { cropX: 0, cropY: 0, cropWidth: 35, cropHeight: 25 },
    2: { cropX: 0, cropY: 0, cropWidth: 35, cropHeight: 25 },
    3: { cropX: 0, cropY: 0, cropWidth: 35, cropHeight: 24 },
  },
  50401: {
    0: { cropX: 0, cropY: 1, cropWidth: 16, cropHeight: 20 },
    1: { cropX: 0, cropY: 0, cropWidth: 16, cropHeight: 30 },
  },
  50402: {
    2: { cropX: 0, cropY: 0, cropWidth: 32, cropHeight: 28 },
    3: { cropX: 0, cropY: 0, cropWidth: 32, cropHeight: 29 },
  },
  50501: {
    2: { cropX: 0, cropY: 0, cropWidth: 43, cropHeight: 31 },
  },
  50502: {
    1: { cropX: 0, cropY: 0, cropWidth: 42, cropHeight: 28 },
    2: { cropX: 0, cropY: 0, cropWidth: 42, cropHeight: 30 },
    3: { cropX: 0, cropY: 0, cropWidth: 39, cropHeight: 40 },
  },
  60002: {
    1: { cropX: 0, cropY: 0, cropWidth: 7, cropHeight: 8 },
    2: { cropX: 0, cropY: 0, cropWidth: 8, cropHeight: 10 },
  },
  60102: {
    1: { cropX: 0, cropY: 0, cropWidth: 5, cropHeight: 7 },
    2: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 7 },
  },
  60202: {
    1: { cropX: 0, cropY: 0, cropWidth: 5, cropHeight: 7 },
    2: { cropX: 0, cropY: 0, cropWidth: 8, cropHeight: 8 },
  },
  60302: {
    1: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 8 },
    2: { cropX: 0, cropY: 0, cropWidth: 8, cropHeight: 9 },
  },
  60402: {
    1: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 8 },
    2: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 9 },
  },
  60502: {
    1: { cropX: 0, cropY: 0, cropWidth: 4, cropHeight: 7 },
    2: { cropX: 0, cropY: 0, cropWidth: 4, cropHeight: 7 },
    3: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 7 },
  },
  60600: {
    0: { cropX: 0, cropY: 1, cropWidth: 5, cropHeight: 6 },
  },
  60602: {
    1: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 7 },
    2: { cropX: 0, cropY: 1, cropWidth: 5, cropHeight: 7 },
  },
  60702: {
    1: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 8 },
    2: { cropX: 0, cropY: 0, cropWidth: 8, cropHeight: 8 },
  },
  60802: {
    1: { cropX: 0, cropY: 0, cropWidth: 5, cropHeight: 8 },
    2: { cropX: 0, cropY: 0, cropWidth: 8, cropHeight: 9 },
  },
  60902: {
    1: { cropX: 0, cropY: 0, cropWidth: 6, cropHeight: 7 },
    2: { cropX: 0, cropY: 0, cropWidth: 5, cropHeight: 7 },
  },
} as const;
