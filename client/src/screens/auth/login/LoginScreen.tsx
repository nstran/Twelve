import React, { useState, useEffect, useMemo, useRef } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ImageBackground,
  Image,
  ActivityIndicator,
  BackHandler,
  useWindowDimensions,
  StyleSheet,
} from 'react-native';

import { SocketClient } from '../../../network/SocketClient';
import { getStyles } from './LoginScreen.styles';
import { SoftkeyBar } from '../../../components/controls/SoftkeyBar/SoftkeyBar';
import { PopupMenu, MenuItem } from '../../../components/controls/PopupMenu/PopupMenu';
import { LOGIN_ASSETS } from './assets';

const ASSET_ICON_OK = LOGIN_ASSETS.iconOk;
const ASSET_ICON_CANCEL = LOGIN_ASSETS.iconCancel;
const ASSET_RED_SUN = LOGIN_ASSETS.redSun;

const MENU_ITEMS: MenuItem[] = [
  { label: 'Đăng nhập', id: 200 },
  { label: 'Đăng ký',   id: 201 },
  { label: 'Thoát',     id: 205 },
];

interface Props {
  onLoginSuccess: () => void;
  onRegister: () => void;
}

export const LoginScreen = ({ onLoginSuccess, onRegister }: Props) => {
  const { width, height } = useWindowDimensions();
  const styles = useMemo(() => getStyles(width, height), [width, height]);

  const [username, setUsername]         = useState('tranns');
  const usernameRef = useRef(username); // ref luôn có giá trị mới nhất, tránh stale closure
  const [password, setPassword]         = useState('123456');
  const [rememberMe, setRememberMe]     = useState(true);
  const [autoLogin, setAutoLogin]       = useState(true);
  const [loading, setLoading]           = useState(false);
  const [menuVisible, setMenuVisible]   = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  // ── Banner lỗi hiển thị trực tiếp (Alert.alert không hoạt động trên web) ──
  const [errorMsg, setErrorMsg] = useState('');
  const showError = (msg: string) => setErrorMsg(msg);
  const clearError = () => setErrorMsg('');

  const client = SocketClient.getInstance();

  useEffect(() => {
    const onAuthSuccess = ({ token, expiresAt }: { token?: string; expiresAt?: number } = {}) => {
      setLoading(false);
      clearError();
      // Emit authSuccessWithUser so App.tsx can persist the full session
      if (token && expiresAt) {
        // Dùng ref để lấy username mới nhất, tránh stale closure
        client.emit('authSuccessWithUser', { token, expiresAt, username: usernameRef.current.trim() });
      }
      onLoginSuccess();
    };

    const onAuthFailed = (msg: string) => {
      setLoading(false);
      showError(msg || 'Sai tài khoản hoặc mật khẩu. Vui lòng thử lại.');
    };

    const onCharacterRequired = () => {
      setLoading(false);
      clearError();
      // App.tsx is also listening to this and currently bypasses character setup.
    };

    client.on('authSuccess',       onAuthSuccess);
    client.on('authFailed',        onAuthFailed);
    client.on('characterRequired', onCharacterRequired);

    return () => {
      client.off('authSuccess',       onAuthSuccess);
      client.off('authFailed',        onAuthFailed);
      client.off('characterRequired', onCharacterRequired);
    };
  }, []);

  useEffect(() => {
    const handler = BackHandler.addEventListener('hardwareBackPress', () => {
      if (menuVisible) { setMenuVisible(false); return true; }
      return false;
    });
    return () => handler.remove();
  }, [menuVisible]);

  // ── Xử lý đăng nhập ──────────────────────────────────────────────────────
  const handleLogin = () => {
    clearError();

    const trimUser = username.trim();
    if (!trimUser) {
      showError('Vui lòng nhập tên đăng nhập');
      return;
    }
    if (!password) {
      showError('Vui lòng nhập mật khẩu');
      return;
    }

    setMenuVisible(false);
    setLoading(true);
    client.login(trimUser, password);
  };

  const handleMenuSelect = (id: number) => {
    setMenuVisible(false);
    switch (id) {
      case 200: handleLogin();         break;
      case 201: onRegister();          break;
      case 205: BackHandler.exitApp(); break;
    }
  };

  const openMenu = () => { setSelectedIndex(0); setMenuVisible(true); };

  const handleLeftSoftkey  = () => menuVisible ? handleMenuSelect(MENU_ITEMS[selectedIndex].id as number) : openMenu();
  const handleCenterKey    = () => menuVisible ? setMenuVisible(false) : undefined;
  const handleRightSoftkey = () => {
    if (menuVisible) {
      setMenuVisible(false);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.fullBg} />

      <ImageBackground
        source={LOGIN_ASSETS.bg}
        style={styles.imageBg}
        resizeMode="contain"
      >
        <View style={styles.contentOverlay}>

          {/* ── Banner lỗi nổi trên ảnh nền ─────────────────────────── */}
          {!!errorMsg && (
            <View style={bannerStyles.container}>
              <Text style={bannerStyles.text}>⚠ {errorMsg}</Text>
            </View>
          )}

          {/* ── Username ──────────────────────────────────────────────── */}
          <View style={styles.inputBoxNick}>
            <TextInput
              style={styles.transparentInput}
              value={username}
              onChangeText={(v) => { setUsername(v); usernameRef.current = v; clearError(); }}
              autoCapitalize="none"
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              autoFocus={true}
              selectionColor="red"
              onSubmitEditing={handleLogin}
            />
          </View>

          {/* ── Password ──────────────────────────────────────────────── */}
          <View style={styles.inputBoxPass}>
            <TextInput
              style={styles.transparentInput}
              value={password}
              onChangeText={(v) => { setPassword(v); clearError(); }}
              secureTextEntry
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              selectionColor="red"
              onSubmitEditing={handleLogin}
            />
          </View>

          {/* ── Checkboxes ────────────────────────────────────────────── */}
          <TouchableOpacity
            style={styles.checkboxArea1}
            onPress={() => setRememberMe(v => !v)}
            activeOpacity={0.5}
          >
            {rememberMe && <Text style={styles.tickText}>✓</Text>}
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.checkboxArea2}
            onPress={() => setAutoLogin(v => !v)}
            activeOpacity={0.5}
          >
            {autoLogin && <Text style={styles.tickText}>✓</Text>}
          </TouchableOpacity>

          {/* ── Loading ───────────────────────────────────────────────── */}
          {loading && (
            <View style={styles.loadingOverlay}>
              <ActivityIndicator color="#ffd700" size="large" />
            </View>
          )}

          <PopupMenu
            visible={menuVisible}
            items={MENU_ITEMS}
            selectedIndex={selectedIndex}
            onSelect={(item) => handleMenuSelect(item.id as number)}
            onIndexChange={setSelectedIndex}
            onClose={() => setMenuVisible(false)}
          />

          {/* ── Softkey bar ───────────────────────────────────────────── */}
          <SoftkeyBar
            width={width}
            onLeftPress={handleLeftSoftkey}
            onCenterPress={menuVisible ? handleCenterKey : undefined}
            onRightPress={menuVisible ? handleRightSoftkey : undefined}
            leftIcon={menuVisible ? ASSET_ICON_OK : ASSET_RED_SUN}
            rightIcon={menuVisible ? ASSET_ICON_CANCEL : undefined}
          />

        </View>
      </ImageBackground>
    </View>
  );
};

// ── Banner style (độc lập, không phụ thuộc width/height) ─────────────────
const bannerStyles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 8,
    left: 8,
    right: 8,
    backgroundColor: 'rgba(180,0,0,0.92)',
    borderRadius: 6,
    paddingVertical: 8,
    paddingHorizontal: 12,
    zIndex: 50,
  },
  text: {
    color: '#ffffff',
    fontSize: 13,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
