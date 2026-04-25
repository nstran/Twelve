// Platform-specific file cho NATIVE (iOS / Android)
// Metro dùng SessionStorage.web.ts cho web, file này cho native
import AsyncStorage from '@react-native-async-storage/async-storage';
import { AppState, AppStateStatus } from 'react-native';

const KEY_TOKEN      = '@twelve:session_token';
const KEY_USERNAME   = '@twelve:session_username';
const KEY_EXPIRES_AT = '@twelve:session_expires_at';
const KEY_LAST_SCREEN = '@twelve:last_screen';

export interface SavedSession {
  token:      string;
  username:   string;
  expiresAt:  number;
  lastScreen?: string;
}

export async function saveSession(session: SavedSession): Promise<void> {
  try {
    await AsyncStorage.setItem(KEY_TOKEN,      session.token);
    await AsyncStorage.setItem(KEY_USERNAME,   session.username);
    await AsyncStorage.setItem(KEY_EXPIRES_AT, String(session.expiresAt));
  } catch {
    // Không ghi console trong production/dev runtime UI.
  }
}

export async function loadSession(): Promise<SavedSession | null> {
  try {
    const token      = await AsyncStorage.getItem(KEY_TOKEN);
    const username   = await AsyncStorage.getItem(KEY_USERNAME);
    const expiresStr = await AsyncStorage.getItem(KEY_EXPIRES_AT);
    const expiresAt  = expiresStr ? parseInt(expiresStr, 10) : 0;

    if (!token || !username || !expiresAt) return null;

    const nowSeconds = Math.floor(Date.now() / 1000);
    if (nowSeconds >= expiresAt) {
      await clearSession();
      return null;
    }

    return { token, username, expiresAt };
  } catch {
    return null;
  }
}

export async function clearSession(): Promise<void> {
  try {
    await AsyncStorage.removeItem(KEY_TOKEN);
    await AsyncStorage.removeItem(KEY_USERNAME);
    await AsyncStorage.removeItem(KEY_EXPIRES_AT);
  } catch {
    // Không ghi console trong production/dev runtime UI.
  }
}

export async function saveLastScreen(screen: string): Promise<void> {
  try {
    await AsyncStorage.setItem(KEY_LAST_SCREEN, screen);
  } catch {
    // Không ghi console trong production/dev runtime UI.
  }
}

export function setupMobileClearOnClose(): () => void {
  const handleAppState = (state: AppStateStatus) => {
    if (state === 'background' || state === 'inactive') {
      clearSession();
    }
  };
  const sub = AppState.addEventListener('change', handleAppState);
  return () => sub.remove();
}
