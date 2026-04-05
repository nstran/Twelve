import React, { useState, useEffect, useMemo } from 'react';
import { 
  View, 
  Text, 
  TextInput, 
  TouchableOpacity, 
  ImageBackground, 
  Alert,
  ActivityIndicator,
  useWindowDimensions
} from 'react-native';
import { SocketClient } from '../network/SocketClient';
import { getStyles } from './LoginScreen.styles';

export const LoginScreen = ({ onLoginSuccess }: { onLoginSuccess: () => void }) => {
  const { width, height } = useWindowDimensions();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [autoLogin, setAutoLogin] = useState(false);
  const [loading, setLoading] = useState(false);

  // Tính toán lại styles mỗi khi kích thước màn hình thay đổi (Xoay, Resize)
  const styles = useMemo(() => getStyles(width, height), [width, height]);
  const client = SocketClient.getInstance();

  useEffect(() => {
    client.on('authSuccess', () => {
      setLoading(false);
      onLoginSuccess();
    });

    client.on('authFailed', (msg) => {
      setLoading(false);
      Alert.alert('Thất bại', msg || 'Sai tài khoản hoặc mật khẩu');
    });

    return () => {
      client.removeAllListeners('authSuccess');
      client.removeAllListeners('authFailed');
    };
  }, []);

  const handleAction = () => {
    if (!username || !password) {
      Alert.alert('Chú ý', 'Vui lòng nhập đầy đủ thông tin');
      return;
    }
    setLoading(true);
    client.login(username, password);
  };

  return (
    <View style={styles.container}>
      <View style={styles.fullBg} />

      <ImageBackground 
        source={require('../../assets/original/login.png')} 
        style={styles.imageBg}
        resizeMode="contain"
      >
        <View style={styles.contentOverlay}>
          
          {/* Input Tài khoản - Sweet Spot v2.3 */}
          <View style={[styles.inputBox, { top: '60.3%', left: '39.8%' }]}>
            <TextInput 
              style={styles.transparentInput} 
              value={username}
              onChangeText={setUsername}
              autoCapitalize="none"
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
            />
          </View>

          {/* Input Mật khẩu - Sweet Spot v2.3 */}
          <View style={[styles.inputBox, { top: '66.3%', left: '39.8%' }]}>
            <TextInput 
              style={styles.transparentInput} 
              value={password}
              onChangeText={setPassword}
              secureTextEntry
              underlineColorAndroid="transparent"
              spellCheck={false}
              autoCorrect={false}
            />
          </View>

          {/* Checkbox: Nhớ mật khẩu */}
          <TouchableOpacity 
            style={[styles.checkboxArea, { top: '70.8%', left: '39.5%' }]} 
            onPress={() => setRememberMe(!rememberMe)}
            activeOpacity={0.5}
          >
            {rememberMe && <Text style={styles.tickText}>✓</Text>}
          </TouchableOpacity>

          {/* Checkbox: Đăng nhập tự động */}
          <TouchableOpacity 
            style={[styles.checkboxArea, { top: '76.1%', left: '39.5%' }]} 
            onPress={() => setAutoLogin(!autoLogin)}
            activeOpacity={0.5}
          >
            {autoLogin && <Text style={styles.tickText}>✓</Text>}
          </TouchableOpacity>

          {/* Phantom Tham Chiến Button */}
          <TouchableOpacity 
            style={styles.phantomButton} 
            onPress={handleAction}
            activeOpacity={0.6}
          >
            {loading && <ActivityIndicator color="#000" />}
          </TouchableOpacity>

        </View>
      </ImageBackground>
    </View>
  );
};
