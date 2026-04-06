import { AppState, AppStateStatus, Platform } from 'react-native';

const KEY_TOKEN      = '@twelve:session_token';
const KEY_USERNAME   = '@twelve:session_username';
const KEY_EXPIRES_AT = '@twelve:session_expires_at';

export interface SavedSession {
  token:     string;
  username:  string;
  expiresAt: number; // Unix seconds
}

// ─────────────────────────────────────────────────────────────────────────────
//  Storage abstraction: localStorage trên web, AsyncStorage trên native
// ─────────────────────────────────────────────────────────────────────────────
const store = {
  async set(key: string, value: string): Promise<void> {
    if (Platform.OS === 'web') {
      localStorage.setItem(key, value);
    } else {
      const AS = require('@react-native-async-storage/async-storage').default;
      await AS.setItem(key, value);
    }
  },
  async get(key: string): Promise<string | null> {
    if (Platform.OS === 'web') {
      return localStorage.getItem(key);
    } else {
      const AS = require('@react-native-async-storage/async-storage').default;
      return await AS.getItem(key);
    }
  },
  async remove(key: string): Promise<void> {
    if (Platform.OS === 'web') {
      localStorage.removeItem(key);
    } else {
      const AS = require('@react-native-async-storage/async-storage').default;
      await AS.removeItem(key);
    }
  },
};

// ─────────────────────────────────────────────────────────────────────────────
//  Lưu session sau khi đăng nhập thành công
// ─────────────────────────────────────────────────────────────────────────────
export async function saveSession(session: SavedSession): Promise<void> {
  try {
    await store.set(KEY_TOKEN,      session.token);
    await store.set(KEY_USERNAME,   session.username);
    await store.set(KEY_EXPIRES_AT, String(session.expiresAt));
    console.log('[SessionStorage] Saved session for', session.username,
      '— expires', new Date(session.expiresAt * 1000).toLocaleString());
  } catch (e) {
    console.error('[SessionStorage] saveSession error:', e);
  }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Tải session khi mở app — trả về null nếu không có hoặc đã hết hạn
// ─────────────────────────────────────────────────────────────────────────────
export async function loadSession(): Promise<SavedSession | null> {
  try {
    const token     = await store.get(KEY_TOKEN);
    const username  = await store.get(KEY_USERNAME);
    const expiresStr = await store.get(KEY_EXPIRES_AT);
    const expiresAt = expiresStr ? parseInt(expiresStr, 10) : 0;

    console.log('[SessionStorage] loadSession →', { token: token?.slice(0,8), username, expiresAt });

    if (!token || !username || !expiresAt) return null;

    const nowSeconds = Math.floor(Date.now() / 1000);
    if (nowSeconds >= expiresAt) {
      console.log('[SessionStorage] Session expired — clearing');
      await clearSession();
      return null;
    }

    console.log('[SessionStorage] Loaded session for', username,
      '— expires', new Date(expiresAt * 1000).toLocaleString());
    return { token, username, expiresAt };
  } catch (e) {
    console.error('[SessionStorage] loadSession error:', e);
    return null;
  }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Xoá session (logout, mobile close)
// ─────────────────────────────────────────────────────────────────────────────
export async function clearSession(): Promise<void> {
  try {
    await store.remove(KEY_TOKEN);
    await store.remove(KEY_USERNAME);
    await store.remove(KEY_EXPIRES_AT);
    console.log('[SessionStorage] Session cleared');
  } catch (e) {
    console.error('[SessionStorage] clearSession error:', e);
  }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Tự động xoá khi app đi vào background/inactive (mobile only)
//  Trên web không làm vì F5 cần giữ lại session.
// ─────────────────────────────────────────────────────────────────────────────
export function setupMobileClearOnClose(): () => void {
  if (Platform.OS === 'web') {
    return () => {};
  }

  const handleAppState = (state: AppStateStatus) => {
    if (state === 'background' || state === 'inactive') {
      console.log('[SessionStorage] App going to background — clearing session');
      clearSession();
    }
  };

  const sub = AppState.addEventListener('change', handleAppState);
  return () => sub.remove();
}
