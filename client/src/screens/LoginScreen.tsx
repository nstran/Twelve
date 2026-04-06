import React, { useState, useEffect, useMemo } from 'react';
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

import { SocketClient } from '../network/SocketClient';
import { getStyles } from './LoginScreen.styles';
import { SoftkeyBar } from '../components/SoftkeyBar';

// ─── Assets ───────────────────────────────────────────────────────────────
const ASSET_ICON_OK     = require('../../assets/ui/icons/icon_ok.png');
const ASSET_ICON_CANCEL = require('../../assets/ui/icons/icon_cancel.png');
const ASSET_ORNATE      = require('../../assets/ui/frames/cornerskb.png');
const ASSET_BASE_FRAME  = require('../../assets/ui/frames/1.png');
const ASSET_RED_SUN     = require('../../assets/ui/icons/icon_sharpest_1.png');

const MENU_ITEMS = [
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

  const [username, setUsername]         = useState('trans');
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
    console.log('[Login] Mounting: subscribing to authSuccess/authFailed');

    const onAuthSuccess = () => {
      console.log('[Login] ← CMD 4 authSuccess → navigate to main');
      setLoading(false);
      clearError();
      onLoginSuccess();
    };

    const onAuthFailed = (msg: string) => {
      console.log('[Login] ← CMD 0 authFailed:', JSON.stringify(msg));
      setLoading(false);
      showError(msg || 'Sai tài khoản hoặc mật khẩu. Vui lòng thử lại.');
    };

    const onCharacterRequired = () => {
      console.log('[Login] ← CMD 5 characterRequired → app will switch screen');
      setLoading(false);
      clearError();
      // App.tsx is also listening to this, so it will switch to 'createCharacter'
    };

    client.on('authSuccess',       onAuthSuccess);
    client.on('authFailed',        onAuthFailed);
    client.on('characterRequired', onCharacterRequired);

    return () => {
      console.log('[Login] Unmounting');
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
    console.log('[Login] handleLogin fired, username:', JSON.stringify(username));
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

    console.log('[Login] Sending CMD 2 login...');
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

  const handleLeftSoftkey  = () => menuVisible ? handleMenuSelect(MENU_ITEMS[selectedIndex].id) : openMenu();
  const handleCenterKey    = () => menuVisible ? setMenuVisible(false) : openMenu();
  const handleRightSoftkey = () => menuVisible ? setMenuVisible(false) : BackHandler.exitApp();

  return (
    <View style={styles.container}>
      <View style={styles.fullBg} />

      <ImageBackground
        source={require('../../assets/ui/backgrounds/login.png')}
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
              onChangeText={(v) => { setUsername(v); clearError(); }}
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

          {/* ── Popup menu ────────────────────────────────────────────── */}
          {menuVisible && (
            <>
              <TouchableOpacity
                style={styles.menuBackdrop}
                activeOpacity={1}
                onPress={() => setMenuVisible(false)}
              />
              <View style={styles.menuBox}>
                <View style={styles.menuInnerBox}>
                  {MENU_ITEMS.map((item, idx) => {
                    const isSelected = idx === selectedIndex;
                    return (
                      <TouchableOpacity
                        key={item.id}
                        style={styles.menuItem}
                        onPressIn={() => setSelectedIndex(idx)}
                        onPress={() => handleMenuSelect(item.id)}
                        activeOpacity={1}
                      >
                        {isSelected && (
                          <View style={styles.menuItemSelectedBg}>
                            <Image
                              source={ASSET_BASE_FRAME}
                              style={styles.menuSelectedBaseImage}
                              resizeMode="stretch"
                            />
                            <View style={[styles.menuOrnateClip, { left: 0 }]}>
                              <Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" />
                            </View>
                            <View style={[styles.menuOrnateClip, { right: 0, transform: [{ scaleX: -1 }] }]}>
                              <Image source={ASSET_ORNATE} style={styles.menuOrnateImage} resizeMode="stretch" />
                            </View>
                          </View>
                        )}
                        <Text style={[styles.menuItemText, isSelected && styles.menuItemTextSelected]}>
                          {item.label}
                        </Text>
                      </TouchableOpacity>
                    );
                  })}
                </View>
              </View>
            </>
          )}

          {/* ── Softkey bar ───────────────────────────────────────────── */}
          <SoftkeyBar
            width={width}
            onLeftPress={handleLeftSoftkey}
            onCenterPress={handleCenterKey}
            onRightPress={handleRightSoftkey}
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
