// Platform-specific file cho WEB — Metro tự dùng file này thay SessionStorage.ts khi bundle web
// Dùng localStorage trực tiếp, không cần AsyncStorage
import { AppStateStatus } from 'react-native';

const KEY_TOKEN       = '@twelve:session_token';
const KEY_USERNAME    = '@twelve:session_username';
const KEY_EXPIRES_AT  = '@twelve:session_expires_at';
const KEY_LAST_SCREEN = '@twelve:last_screen';

export interface SavedSession {
  token:       string;
  username:    string;
  expiresAt:   number;
  lastScreen?: string;
}

// ─── Lưu session sau khi đăng nhập thành công ───────────────────────────────
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

// ─── Lưu màn hình hiện tại để khôi phục khi F5 ─────────────────────────────
export async function saveLastScreen(screen: string): Promise<void> {
  try {
    localStorage.setItem(KEY_LAST_SCREEN, screen);
  } catch (e) {
    console.error('[SessionStorage.web] saveLastScreen error:', e);
  }
}

// ─── Tải session khi mở app ─────────────────────────────────────────────────
export async function loadSession(): Promise<SavedSession | null> {
  try {
    const token      = localStorage.getItem(KEY_TOKEN);
    const username   = localStorage.getItem(KEY_USERNAME);
    const expiresStr = localStorage.getItem(KEY_EXPIRES_AT);
    const lastScreen = localStorage.getItem(KEY_LAST_SCREEN);
    const expiresAt  = expiresStr ? parseInt(expiresStr, 10) : 0;

    console.log('[SessionStorage.web] loadSession →', {
      token: token?.slice(0, 8),
      username,
      expiresAt,
      lastScreen,
    });

    if (!token || !username || !expiresAt) return null;

    const nowSeconds = Math.floor(Date.now() / 1000);
    if (nowSeconds >= expiresAt) {
      console.log('[SessionStorage.web] Session expired — clearing');
      await clearSession();
      return null;
    }

    return { token, username, expiresAt, lastScreen: lastScreen || undefined };
  } catch (e) {
    console.error('[SessionStorage.web] loadSession error:', e);
    return null;
  }
}

// ─── Xoá session (đăng xuất) ────────────────────────────────────────────────
export async function clearSession(): Promise<void> {
  try {
    localStorage.removeItem(KEY_TOKEN);
    localStorage.removeItem(KEY_USERNAME);
    localStorage.removeItem(KEY_EXPIRES_AT);
    localStorage.removeItem(KEY_LAST_SCREEN);
    console.log('[SessionStorage.web] Session cleared');
  } catch (e) {
    console.error('[SessionStorage.web] clearSession error:', e);
  }
}

// ─── Mobile only: không làm gì trên web ─────────────────────────────────────
export function setupMobileClearOnClose(): () => void {
  return () => {};
}
