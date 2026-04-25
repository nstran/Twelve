/**
 * usePaletteSwappedImage.ts
 *
 * React hook: nhận ImageSourcePropType + palette fromColors/toColors,
 * trả về source mới (data URI) với palette đã swap.
 *
 * - Async: fetch raw PNG bytes từ bundled asset URI
 * - Cache: mỗi (assetUri + palette key) chỉ decode 1 lần
 * - Fallback: nếu fetch fail → trả original source
 */

import { useEffect, useMemo, useRef, useState } from 'react';
import { Image as RNImage } from 'react-native';
import type { ImageSourcePropType } from 'react-native';
import { swapPngPalette, uint8ArrayToBase64 } from './paletteSwap';

// ---------------------------------------------------------------------------
// Module-level cache — tồn tại suốt app lifecycle
// ---------------------------------------------------------------------------

const SWAP_CACHE = new Map<string, string>(); // key → "data:image/png;base64,..."

interface SwappedState {
  key: string;
  uri: string;
}

// ---------------------------------------------------------------------------
// Hook
// ---------------------------------------------------------------------------

/**
 * @param source     - ImageSourcePropType (require(...))
 * @param fromColors - palette gốc (0xRRGGBB)
 * @param toColors   - palette đích (0xRRGGBB), cùng độ dài với fromColors
 */
export function usePaletteSwappedImage(
  source: ImageSourcePropType,
  fromColors: readonly number[],
  toColors: readonly number[],
): ImageSourcePropType {
  // ------ Stable keys để tránh re-run effect không cần thiết ------

  // URI của bundled asset — stable vì Metro gán ID cố định cho từng require()
  const assetUri = useMemo(() => {
    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      return RNImage.resolveAssetSource(source as any)?.uri ?? null;
    } catch {
      return null;
    }
  // source là số (Metro asset id) — object reference stable
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [source]);

  // Stable string key cho palette (chỉ encode entries thực sự thay đổi)
  const paletteKey = useMemo(() => {
    const changed: string[] = [];
    const len = Math.min(fromColors.length, toColors.length);
    for (let i = 0; i < len; i++) {
      if (fromColors[i] !== toColors[i]) {
        changed.push(`${i}:${fromColors[i].toString(16)}->${toColors[i].toString(16)}`);
      }
    }
    return changed.join('|');
  }, [fromColors, toColors]);

  const needsSwap = paletteKey.length > 0;
  const cacheKey  = assetUri ? `${assetUri}||${paletteKey}` : '';

  // ------ State ------
  const [swappedState, setSwappedState] = useState<SwappedState | null>(() => {
    // Khởi tạo từ cache nếu đã có sẵn (hot reload / re-mount)
    if (cacheKey && SWAP_CACHE.has(cacheKey)) {
      return { key: cacheKey, uri: SWAP_CACHE.get(cacheKey)! };
    }
    return null;
  });

  const mountedRef = useRef(true);
  useEffect(() => {
    mountedRef.current = true;
    return () => { mountedRef.current = false; };
  }, []);

  // Reset khi source hoặc palette thay đổi
  useEffect(() => {
    if (!needsSwap || !assetUri || !cacheKey) {
      setSwappedState(null);
      return;
    }

    // Cache hit
    if (SWAP_CACHE.has(cacheKey)) {
      setSwappedState({ key: cacheKey, uri: SWAP_CACHE.get(cacheKey)! });
      return;
    }

    let cancelled = false;

    (async () => {
      try {
        const response    = await fetch(assetUri);
        const arrayBuffer = await response.arrayBuffer();
        const bytes       = new Uint8Array(arrayBuffer);
        const swapped     = swapPngPalette(bytes, fromColors, toColors);
        const base64      = uint8ArrayToBase64(swapped);
        const dataUri     = `data:image/png;base64,${base64}`;

        SWAP_CACHE.set(cacheKey, dataUri);

        if (!cancelled && mountedRef.current) {
          setSwappedState({ key: cacheKey, uri: dataUri });
        }
      } catch {
        // fallback → giữ nguyên null → render source gốc
      }
    })();

    return () => { cancelled = true; };
  }, [assetUri, cacheKey, needsSwap, fromColors, toColors]);

  // Trả về swapped source nếu ready, không thì trả source gốc
  if (swappedState?.key === cacheKey) return { uri: swappedState.uri };
  return source;
}
