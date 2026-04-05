import React, { useState, useEffect, useMemo } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ImageBackground,
  Image,
  Alert,
  ActivityIndicator,
  BackHandler,
  useWindowDimensions,
  StyleSheet,
} from 'react-native';

import { SocketClient } from '../network/SocketClient';
import { getStyles } from './LoginScreen.styles';
import { SoftkeyBar } from '../components/SoftkeyBar';

// ─── Menu Assets ──────────────────────────────────────────────────────────
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

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [autoLogin, setAutoLogin] = useState(false);
  const [loading, setLoading] = useState(false);
  const [menuVisible, setMenuVisible] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  const client = SocketClient.getInstance();

  useEffect(() => {
    const onAuthSuccess = () => {
      setLoading(false);
      onLoginSuccess();
    };
    const onAuthFailed = (msg: string) => {
      setLoading(false);
      Alert.alert('Thất bại', msg || 'Sai tài khoản hoặc mật khẩu');
    };
    client.on('authSuccess', onAuthSuccess);
    client.on('authFailed', onAuthFailed);
    return () => {
      client.off('authSuccess', onAuthSuccess);
      client.off('authFailed', onAuthFailed);
    };
  }, []);

  useEffect(() => {
    const handler = BackHandler.addEventListener('hardwareBackPress', () => {
      if (menuVisible) {
        setMenuVisible(false);
        return true;
      }
      return false;
    });
    return () => handler.remove();
  }, [menuVisible]);

  const handleLogin = () => {
    if (!username || !password) {
      Alert.alert('Chú ý', 'Vui lòng nhập đầy đủ thông tin');
      return;
    }
    setMenuVisible(false);
    setLoading(true);
    client.login(username, password);
  };

  const handleMenuSelect = (id: number) => {
    setMenuVisible(false);
    switch (id) {
      case 200: handleLogin(); break;
      case 201: onRegister(); break;
      case 205: BackHandler.exitApp(); break;
    }
  };

  const openMenu = () => {
    setSelectedIndex(0);
    setMenuVisible(true);
  };

  const handleLeftSoftkey = () => {
    if (menuVisible) {
      handleMenuSelect(MENU_ITEMS[selectedIndex].id);
    } else {
      openMenu();
    }
  };

  const handleCenterKey = () => {
    if (menuVisible) setMenuVisible(false);
    else openMenu();
  };

  const handleRightSoftkey = () => {
    if (menuVisible) setMenuVisible(false);
    else BackHandler.exitApp();
  };

  return (
    <View style={styles.container}>
      <View style={styles.fullBg} />

      <ImageBackground
        source={require('../../assets/ui/backgrounds/login.png')}
        style={styles.imageBg}
        resizeMode="contain"
      >
        <View style={styles.contentOverlay}>

          {/* ──────── Inputs ──────── */}
          <View style={styles.inputBoxNick}>
            <TextInput
              style={styles.transparentInput}
              value={username}
              onChangeText={setUsername}
              autoCapitalize="none"
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              autoFocus={true}
              selectionColor="red"
            />
          </View>

          <View style={styles.inputBoxPass}>
            <TextInput
              style={styles.transparentInput}
              value={password}
              onChangeText={setPassword}
              secureTextEntry
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              selectionColor="red"
            />
          </View>

          {/* ──────── Checkboxes ──────── */}
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

          {loading && (
            <View style={styles.loadingOverlay}>
              <ActivityIndicator color="#ffd700" size="large" />
            </View>
          )}

          {/* POPUP MENU */}
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
                        
                        <Text
                          style={[
                            styles.menuItemText,
                            isSelected && styles.menuItemTextSelected,
                          ]}
                        >
                          {item.label}
                        </Text>
                      </TouchableOpacity>
                    );
                  })}
                </View>
              </View>
            </>
          )}

          {/* ── Softkey Bar updated call ── */}
          <SoftkeyBar
            width={width}
            onLeftPress={handleLeftSoftkey}
            onCenterPress={handleCenterKey}
            onRightPress={handleRightSoftkey}
            menuVisible={menuVisible}
            leftIcon={menuVisible ? ASSET_ICON_OK : ASSET_RED_SUN}
            rightIcon={menuVisible ? ASSET_ICON_CANCEL : undefined}
          />

        </View>
      </ImageBackground>
    </View>
  );
};
