import React, { useState, useEffect, useRef } from 'react';
import { SafeAreaView, StatusBar, StyleSheet, View, Text } from 'react-native';
import { LoginScreen }    from './src/screens/LoginScreen';
import { RegisterScreen } from './src/screens/RegisterScreen';
import { MainScreen }           from './src/screens/MainScreen';
import { CreateCharacterScreen } from './src/screens/CreateCharacterScreen';
import { SocketClient }         from './src/network/SocketClient';

// ── Screen states (mirrors J2ME screen stack) ────────────────────────────────
type Screen = 'login' | 'register' | 'main' | 'createCharacter';

// Đổi thành LAN IP của máy khi test trên thiết bị thật, ví dụ: ws://192.168.1.x:5102/game
const SERVER_URL = 'ws://localhost:5102/game';
const RECONNECT_DELAY_MS = 2000;

export default function App() {
  const [screen, setScreen]         = useState<Screen>('login');
  const [isConnected, setIsConnected] = useState(false);
  const [connectMsg, setConnectMsg]   = useState('ĐANG KẾT NỐI CHIẾN TRƯỜNG...');

  const client          = SocketClient.getInstance();
  const reconnectTimer  = useRef<ReturnType<typeof setTimeout> | null>(null);
  const attemptRef      = useRef(0);

  const doConnect = () => {
    const attempt = ++attemptRef.current;
    console.log(`[App] Connecting (attempt #${attempt}) → ${SERVER_URL}`);
    setConnectMsg(`ĐANG KẾT NỐI CHIẾN TRƯỜNG... (#${attempt})`);
    client.connect(SERVER_URL);
  };

  useEffect(() => {
    const onConnected = () => {
      console.log('[App] WebSocket connected ✓');
      attemptRef.current = 0;
      setIsConnected(true);
      setConnectMsg('ĐANG KẾT NỐI CHIẾN TRƯỜNG...');
    };

    const onDisconnected = () => {
      console.log('[App] WebSocket disconnected — reconnecting in', RECONNECT_DELAY_MS, 'ms');
      setIsConnected(false);
      // Auto-reconnect sau 2 giây
      reconnectTimer.current = setTimeout(doConnect, RECONNECT_DELAY_MS);
    };

    const onError = (err: any) => {
      console.error('[App] WebSocket error:', err);
    };

    const onCharacterRequired = () => {
      console.log('[App] Character missing → switching to createCharacter screen');
      setScreen('createCharacter');
    };

    client.on('connected',         onConnected);
    client.on('disconnected',      onDisconnected);
    client.on('error',             onError);
    client.on('characterRequired', onCharacterRequired);

    // Kết nối lần đầu
    doConnect();

    return () => {
      client.off('connected',         onConnected);
      client.off('disconnected',      onDisconnected);
      client.off('error',             onError);
      client.off('characterRequired', onCharacterRequired);
      if (reconnectTimer.current) clearTimeout(reconnectTimer.current);
    };
  }, []);

  const renderScreen = () => {
    if (!isConnected) {
      return (
        <View style={styles.loadingContainer}>
          <Text style={styles.loadingText}>{connectMsg}</Text>
          <Text style={styles.loadingSubText}>Đảm bảo server đang chạy tại{'\n'}{SERVER_URL}</Text>
        </View>
      );
    }

    switch (screen) {
      case 'main':
        return <MainScreen />;

      case 'register':
        return (
          <RegisterScreen
            onBack={() => setScreen('login')}
            onRegisterSuccess={() => setScreen('login')}
          />
        );

      case 'createCharacter':
        return (
          <CreateCharacterScreen 
            onSuccess={() => setScreen('main')}
            onCancel={() => setScreen('login')}
          />
        );

      case 'login':
      default:
        return (
          <LoginScreen
            onLoginSuccess={() => setScreen('main')}
            onRegister={() => setScreen('register')}
          />
        );
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#000" />
      {renderScreen()}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000',
    gap: 16,
  },
  loadingText: {
    color: '#FFD700',
    fontSize: 18,
    fontWeight: 'bold',
    letterSpacing: 2,
    textAlign: 'center',
  },
  loadingSubText: {
    color: '#666666',
    fontSize: 12,
    textAlign: 'center',
    lineHeight: 18,
  },
});
