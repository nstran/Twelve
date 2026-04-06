// Platform-specific file cho WEB — Metro tự dùng file này thay SessionStorage.ts khi bundle web
// Dùng localStorage trực tiếp, không cần AsyncStorage
import { AppState, AppStateStatus } from 'react-native';

const KEY_TOKEN      = '@twelve:session_token';
const KEY_USERNAME   = '@twelve:session_username';
const KEY_EXPIRES_AT = '@twelve:session_expires_at';

export interface SavedSession {
  token:     string;
  username:  string;
  expiresAt: number;
}

export async function saveSession(session: SavedSession): Promise<void> {
  try {
    localStorage.setItem(KEY_TOKEN,      session.token);
    localStorage.setItem(KEY_USERNAME,   session.username);
    localStorage.setItem(KEY_EXPIRES_AT, String(session.expiresAt));
    console.log('[SessionStorage.web] Saved session for', session.username,
      '— expires', new Date(session.expiresAt * 1000).toLocaleString());
  } catch (e) {
    console.error('[SessionStorage.web] saveSession error:', e);
  }
}

export async function loadSession(): Promise<SavedSession | null> {
  try {
    const token      = localStorage.getItem(KEY_TOKEN);
    const username   = localStorage.getItem(KEY_USERNAME);
    const expiresStr = localStorage.getItem(KEY_EXPIRES_AT);
    const expiresAt  = expiresStr ? parseInt(expiresStr, 10) : 0;

    console.log('[SessionStorage.web] loadSession →', { token: token?.slice(0, 8), username, expiresAt });

    if (!token || !username || !expiresAt) return null;

    const nowSeconds = Math.floor(Date.now() / 1000);
    if (nowSeconds >= expiresAt) {
      console.log('[SessionStorage.web] Session expired — clearing');
      await clearSession();
      return null;
    }

    return { token, username, expiresAt };
  } catch (e) {
    console.error('[SessionStorage.web] loadSession error:', e);
    return null;
  }
}

export async function clearSession(): Promise<void> {
  try {
    localStorage.removeItem(KEY_TOKEN);
    localStorage.removeItem(KEY_USERNAME);
    localStorage.removeItem(KEY_EXPIRES_AT);
    console.log('[SessionStorage.web] Session cleared');
  } catch (e) {
    console.error('[SessionStorage.web] clearSession error:', e);
  }
}

// Trên web không cần AppState listener — localStorage tự persist qua F5
export function setupMobileClearOnClose(): () => void {
  return () => {};
}
