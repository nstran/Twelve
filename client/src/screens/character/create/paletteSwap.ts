/**
 * paletteSwap.ts
 *
 * Client-side PNG palette swap — mirrors Java h.a(byte[], int[], int[]).
 *
 * Flow:
 *   raw PNG bytes → find PLTE chunk → replace matching RGB entries → recalc CRC32 → return
 *
 * Không dùng bất kỳ native module nào — thuần JS, chạy được trên RN/Expo.
 */

// ---------------------------------------------------------------------------
// CRC32 (IEEE 802.3 polynomial — standard PNG CRC)
// ---------------------------------------------------------------------------

const CRC_TABLE = (() => {
  const t = new Uint32Array(256);
  for (let n = 0; n < 256; n++) {
    let c = n;
    for (let k = 0; k < 8; k++) {
      c = c & 1 ? 0xedb88320 ^ (c >>> 1) : c >>> 1;
    }
    t[n] = c;
  }
  return t;
})();

function crc32(data: Uint8Array, offset: number, length: number): number {
  let crc = 0xffffffff;
  for (let i = offset; i < offset + length; i++) {
    crc = (crc >>> 8) ^ CRC_TABLE[(crc ^ data[i]) & 0xff];
  }
  return (crc ^ 0xffffffff) >>> 0;
}

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

function readUint32BE(buf: Uint8Array, offset: number): number {
  return (
    ((buf[offset] << 24) | (buf[offset + 1] << 16) | (buf[offset + 2] << 8) | buf[offset + 3]) >>>
    0
  );
}

function writeUint32BE(buf: Uint8Array, offset: number, value: number): void {
  buf[offset]     = (value >>> 24) & 0xff;
  buf[offset + 1] = (value >>> 16) & 0xff;
  buf[offset + 2] = (value >>> 8)  & 0xff;
  buf[offset + 3] = value & 0xff;
}

// ---------------------------------------------------------------------------
// Core — swap PLTE entries
// ---------------------------------------------------------------------------

/**
 * Swap palette colors trong raw PNG bytes.
 * Giống hệt Java h.a(byte[] pngBytes, int[] fromColors, int[] toColors).
 *
 * @param pngBytes   - raw PNG file bytes
 * @param fromColors - palette màu nguồn (0xRRGGBB)
 * @param toColors   - palette màu đích  (0xRRGGBB)
 * @returns new Uint8Array với PLTE đã được swap + CRC cập nhật
 */
export function swapPngPalette(
  pngBytes: Uint8Array,
  fromColors: readonly number[],
  toColors: readonly number[],
): Uint8Array {
  // Clone — không mutate ảnh gốc
  const buf = new Uint8Array(pngBytes);

  // PNG signature: 8 bytes — bắt đầu duyệt chunks từ byte 8
  let i = 8;
  while (i + 12 <= buf.length) {
    const chunkLen  = readUint32BE(buf, i);
    const chunkType = String.fromCharCode(buf[i + 4], buf[i + 5], buf[i + 6], buf[i + 7]);

    if (chunkType === 'PLTE') {
      const dataStart = i + 8;

      // Swap từng entry RGB (3 bytes mỗi entry)
      for (let j = 0; j < chunkLen; j += 3) {
        const r   = buf[dataStart + j];
        const g   = buf[dataStart + j + 1];
        const b   = buf[dataStart + j + 2];
        const rgb = (r << 16) | (g << 8) | b;

        for (let k = 0; k < fromColors.length; k++) {
          if (rgb === fromColors[k]) {
            const to = toColors[k];
            buf[dataStart + j]     = (to >>> 16) & 0xff;
            buf[dataStart + j + 1] = (to >>> 8)  & 0xff;
            buf[dataStart + j + 2] = to & 0xff;
            break;
          }
        }
      }

      // Tính lại CRC32 cho chunk (type[4] + data[N])
      const newCrc = crc32(buf, i + 4, 4 + chunkLen);
      writeUint32BE(buf, dataStart + chunkLen, newCrc);

      break; // Chỉ có 1 PLTE trong PNG
    }

    // Chunk format: length(4) + type(4) + data(N) + crc(4)
    i += 12 + chunkLen;

    // IDAT đến trước PLTE là PNG corrupt — dừng
    if (chunkType === 'IDAT') break;
  }

  return buf;
}

// ---------------------------------------------------------------------------
// Uint8Array → base64 (chunk để tránh stack overflow với ảnh lớn)
// ---------------------------------------------------------------------------

export function uint8ArrayToBase64(bytes: Uint8Array): string {
  const CHUNK = 8192;
  let binary  = '';
  for (let i = 0; i < bytes.length; i += CHUNK) {
    binary += String.fromCharCode(...bytes.subarray(i, Math.min(i + CHUNK, bytes.length)));
  }
  return btoa(binary);
}
