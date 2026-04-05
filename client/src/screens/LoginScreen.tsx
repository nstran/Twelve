import React, { useState, useEffect, useMemo } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ImageBackground,
  Alert,
  ActivityIndicator,
  BackHandler,
  useWindowDimensions,
} from 'react-native';

import { SocketClient } from '../network/SocketClient';
import { getStyles } from './LoginScreen.styles';
import { SoftkeyBar } from '../components/SoftkeyBar';

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

          {/* ── Nick Ola input ── */}
          <View style={styles.inputBoxNick}>
            <TextInput
              style={styles.transparentInput}
              value={username}
              onChangeText={setUsername}
              autoCapitalize="none"
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              autoFocus={true} // Tự động tập trung vào Nick
              selectionColor="red" // Con trỏ chuột nhấp nháy màu đỏ
            />
          </View>

          {/* ── Mật khẩu input ── */}
          <View style={styles.inputBoxPass}>
            <TextInput
              style={styles.transparentInput}
              value={password}
              onChangeText={setPassword}
              secureTextEntry
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
              selectionColor="red" // Con trỏ chuột màu đỏ
            />
          </View>

          {/* ── Checkbox: Nhớ mật khẩu ── */}
          <TouchableOpacity
            style={styles.checkboxArea1}
            onPress={() => setRememberMe(v => !v)}
            activeOpacity={0.5}
          >
            {rememberMe && <Text style={styles.tickText}>✓</Text>}
          </TouchableOpacity>

          {/* ── Checkbox: Đăng nhập tự động ── */}
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

          {menuVisible && (
            <>
              <TouchableOpacity
                style={styles.menuBackdrop}
                activeOpacity={1}
                onPress={() => setMenuVisible(false)}
              />
              <View style={styles.menuBox}>
                {MENU_ITEMS.map((item, idx) => (
                  <TouchableOpacity
                    key={item.id}
                    style={[
                      styles.menuItem,
                      idx === selectedIndex && styles.menuItemSelected,
                    ]}
                    onPressIn={() => setSelectedIndex(idx)}
                    onPress={() => handleMenuSelect(item.id)}
                    activeOpacity={0.9}
                  >
                    <Text
                      style={[
                        styles.menuItemText,
                        idx === selectedIndex && styles.menuItemTextSelected,
                      ]}
                    >
                      {item.label}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </>
          )}

          <SoftkeyBar
            width={width}
            onLeftPress={handleLeftSoftkey}
            onCenterPress={handleCenterKey}
            onRightPress={handleRightSoftkey}
            menuVisible={menuVisible}
          />

        </View>
      </ImageBackground>
    </View>
  );
};
